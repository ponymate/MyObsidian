# 概述
MQ，本质是一个队列，FIFO。使得消息发送上游只需要依赖MQ，不用依赖其他服务。
作用

- 流量削峰

使用消息队列做缓冲，使得能够处理大量信息。

- 应用解耦

将子系统间的消息传递转换为消息队列的方式，减少系统调用
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663663298677-4807a08f-cbf6-4589-9f2d-d4c933d3dbcb.png#clientId=u91d8077d-82e8-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=306&id=u71da346d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=383&originWidth=1204&originalType=binary&ratio=1&rotation=0&showTitle=false&size=56764&status=done&style=none&taskId=u88a47ea9-a759-45fe-a67c-da330ad5241&title=&width=963.2)

- 异步处理

A调用B，如果A需要知道B什么时候执行完，可以通过A主动查询B，调用B的api来实现。或者B执行完后调用A的api来通知，都不优雅。
使用消息队列，A只需要监听B，B完成时，会发送一条消息给MQ，MQ会将消息给A，这样就不用调用API

## 核心概念
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663664642647-724d7790-c9f6-4498-bf84-3dbfebcca3b0.png#clientId=u91d8077d-82e8-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=486&id=u4a5646fa&margin=%5Bobject%20Object%5D&name=image.png&originHeight=608&originWidth=1247&originalType=binary&ratio=1&rotation=0&showTitle=false&size=264587&status=done&style=none&taskId=u89704d85-ae86-46d0-9d5d-ad03f395363&title=&width=997.6)
队列 Queue  ：
队列是RabbitMQ内部使用的一种数据结构，是消息存储的地方，队列仅受主机的内存和磁盘的限制，本质上是一个巨大的消息缓冲区。

交换机 Exchange  ：
接收生产者的消息，将消息推送到队列中。交换机决定如何处理接收到的消息，是将消息推送到特定队列还是到多个队列，亦或是将消息丢弃。
交换机的类型
direct

- direct模式的交换机只将收到的消息发送到bingings为指定的routingkey的队列中

topic

- 一个交换机和队列之间可以有多个routingkey
- routingkey的格式：是一个单词列表，以点号分开。比如：pony.mate.mjw

*可以代替一个单词，
#号可以代替0个或者多个单词
fanout

- Fanout将接收到的所有消息广播到它知道的 所有队列中  

Connection：publisher／consumer 和 broker 之间的 TCP 连接  

Broker：
接收和分发消息的应用，RabbitMQ Server 就是 Message Broker

Virtual host：
出于多租户和安全因素设计的，把 AMQP 的基本组件划分到一个虚拟的分组中，类似 于网络中的 namespace 概念。当多个不同的用户使用同一个 RabbitMQ server 提供的服务时，可以划分出 多个 vhost，每个用户在自己的 vhost 创建 exchange／queue 等  

Channel：
如果每一次访问 RabbitMQ 都建立一个 Connection，在消息量大的时候建立 TCP Connection 的开销将是巨大的，效率也较低。Channel 是在 connection 内部建立的逻辑连接，如果应用程支持多线程，通常每个 thread 创建单独的 channel 进行通讯，AMQP method 包含了 channel id 帮助客户端和 message broker 识别 channel，所以 channel 之间是完全隔离的。Channel 作为轻量级的 Connection 极大减少了操作系统建立 TCP connection 的开销  

Binding：
exchange 和 queue 之间的虚拟连接，binding 中可以包含 routing key，Binding 信息被保 存到 exchange 中的查询表中，用于 message 的分发依据  

# Work Queues（工作队列）

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663668189279-4c1efd1e-dd75-4578-8dc4-6c736c261bc7.png#clientId=u91d8077d-82e8-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=96&id=u344fc072&margin=%5Bobject%20Object%5D&name=image.png&originHeight=87&originWidth=299&originalType=binary&ratio=1&rotation=0&showTitle=false&size=5563&status=done&style=none&taskId=u263365fa-9784-4b66-933c-1cb38bb8ee9&title=&width=329.1999969482422)
主要思想是避免立即执行资源密集型任务，不用等待它完成，而是安排在之后完成。
当有多个工作线程时，这些工作线程将一起处理这些任务。
## 临时队列
 一个具有随机名称 的队列，服务器随机队列名称。一旦断开了消费者的连接，队列将被自动删除。
 创建临时队列的方式如下: String queueName = channel.queueDeclare().getQueue(); 
## 消费者接收信息方式

- 轮询发布消息

按照顺序轮流接收信息

- 不公平分发

为了防止运行速度快的消费者与运行速度慢的消费者因为轮询策略导致的资源浪费
本身消息的发送就是异步发送的，所以在任何时候，channel 上肯定不止只有一个消息另外来自消费 者的手动确认本质上也是异步的。因此这里就存在一个未确认的消息缓冲区，因此希望开发人员能限制此 缓冲区的大小，以避免缓冲区里面无限制的未确认消息问题。这个时候就可以通过使用 basic.qos 方法设 置“预取计数”值来完成的。该值定义通道上允许的未确认消息的最大数量。一旦数量达到配置的数量， RabbitMQ 将停止在通道上传递更多消息，除非至少有一个未处理的消息被确认，例如，假设在通道上有 未确认的消息 5、6、7，8，并且通道的预取计数设置为 4，此时RabbitMQ 将不会在该通道上再传递任何 消息，除非至少有一个未应答的消息被 ack。比方说 tag=6 这个消息刚刚被确认 ACK，RabbitMQ 将会感知 这个情况到并再发送一条消息。消息应答和 QoS 预取值对用户吞吐量有重大影响。通常，增加预取将提高 向消费者传递消息的速度。虽然自动应答传输消息速率是最佳的，但是，在这种情况下已传递但尚未处理 的消息的数量也会增加，从而增加了消费者的 RAM 消耗(随机存取存储器)应该小心使用具有无限预处理 的自动确认模式或手动确认模式，消费者消费了大量的消息如果没有确认的话，会导致消费者连接节点的 内存消耗变大，所以找到合适的预取值是一个反复试验的过程，不同的负载该值取值也不同 100 到 300 范 围内的值通常可提供最佳的吞吐量，并且不会给消费者带来太大的风险。预取值为 1 是最保守的。当然这 将使吞吐量变得很低，特别是消费者连接延迟很严重的情况下，特别是在消费者连接等待时间较长的环境 中。对于大多数应用来说，稍微高一点的值将是最佳的。 
设置参数channel.basicQos(1);
指该消费者在接收到队列里的消息但没有返回确认结果之前,队列不会将新的消息分发给该消费者。队列中没有被消费的消息不会被删除，还是存在于队列中。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663722846163-e3a6ac52-cab8-494a-a85f-4b60cc15a68e.png#clientId=u8f8ac55c-431b-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=466&id=XQEy4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=582&originWidth=1215&originalType=binary&ratio=1&rotation=0&showTitle=false&size=291443&status=done&style=none&taskId=u660d0922-bcc3-4d10-8eb7-a3c85b1aa19&title=&width=972)
意思就是如果这个任务我还没有处理完或者我还没有应答你，你先别分配给我，我目前只能处理一个任务，然后 rabbitmq 就会把该任务分配给没有那么忙的那个空闲消费者，当然如果所有的消费者都没有完 成手上任务，队列还在不停的添加新任务，队列有可能就会遇到队列被撑满的情况，这个时候就只能添加新的消费者或者改变其他存储任务的策略。 
     
## 消息应答
    因为RabbitMQ一旦向消费者发送了一条信息，便立即将消息标记为删除。如果消费者在处理任务时突然挂掉，将会导致丢失正在处理的信息，以及后续发送给消费者的信息，因为它将无法收到。
     为了保证消息不会丢失，Rabbit 引入了消息应答机制，即消费者在接收到消息并处理后，告诉rabbitmq它已经处理了，rabbitmq可以把消息删除了。
### 自动应答
 消息发送后立即被认为已经传送成功，这种模式需要在高吞吐量和数据传输安全性方面做权衡。
因为这种模式如果消息在接收到之前，消费者那边出现连接或者 channel 关闭，那么消息就丢失 了,
当然另一方面这种模式消费者可以接收过载的消息，当然这样有可能使得消费者这边由于接收太多还来不及处理的消息，导致这些消息的积压，最终使得内存耗尽，最终这些消费者线程被操作系统杀死，所以这种模式仅适用在消费者可以高效并以某种速率能够处理这些消息的情况下使用  
### ###手动应答的类型
1.Channel.basicAck 用于肯定确认，告知mq可以丢弃消息
2.Channel.basicNack 用于否定确认
3.Channel.basicReject 用于否定确认 ，但是告诉mq可以丢弃

手动应答可以实现批量应答，一次应答多个消息。
### 消息自动重新入队
如果消费者由于某些原因失去连接，导致消息未发送ACK确认，rabbitmq将了解到消息未完全处理，并将其重新排队，如果此时其他消费者可以处理，他将很快重新分发给另一个消费者。这样，即使某个消费者偶尔死亡，也可以确 保不会丢失任何消息  

## 持久化
当rabbitmq服务停掉后，默认情况下将会忽视队列和消息。
为了保障数据不丢失，需要将队列和消息都标记为持久化。
### 队列持久化
实现持久化，需要在声明队列时将参数设置为持久化：durable
持久化后，即使重启服务队列依然存在
### ###消息持久化
需要在生产者修改代码， MessageProperties.PERSISTENT_TEXT_PLAIN 添 加这个属性。  

将消息标记为持久化并不能完全保证不会丢失消息。尽管它告诉 RabbitMQ 将消息保存到磁盘，但是 这里依然存在当消息刚准备存储在磁盘的时候 但是还没有存储完，消息还在缓存的一个间隔点。此时并没 有真正写入磁盘。持久性保证并不强，但是对于我们的简单任务队列而言，这已经绰绰有余了。

# 发布确认
## 概述
生产者将信道设置成confirm模式, 一旦信道进入 confirm 模式，所有在该信道上面发布的消 息都将会被指派一个唯一的 ID(从 1 开始)，一旦消息被投递到所有匹配的队列之后，broker 就会 发送一个确认给生产者(包含消息的唯一 ID)，这就使得生产者知道消息已经正确到达目的队列了， 如果消息和队列是可持久化的，那么确认消息会在将消息写入磁盘之后发出，broker 回传给生产者的确认消息中 delivery-tag 域包含了消息的序列号，此外 broker 也可以设置basic.ack 的 multiple 域，表示到这个序列号之前的所有消息都已经得到了处理.
confirm 模式最大的好处在于他是异步的，一旦发布一条消息，生产者应用程序就可以在等信道 返回确认的同时继续发送下一条消息，当消息最终得到确认之后，生产者应用便可以通过回调方 法来处理该确认消息，如果 RabbitMQ 因为自身内部错误导致消息丢失，就会发送一条 nack 消息， 生产者应用程序同样可以在回调方法中处理该 nack 消息。  

发布确认的策略：
单个确认发布

- 这是一种同步的确认方式,只有发布的消息被确认后才能发下一个信息,发布速度很慢

批量确认发布

- 同样是同步的确认方式,和单个确认发布相比,可以极大的提高吞吐量,但是当故障发生时,不知道是哪个消息发生了问题,并且需要将整个批处理保存到内存中

异步确认发布

- 可靠性和效率都很高,通过回调函数来进行可靠性传递.

## 发布确认
当rabbitmq服务因某些原因关闭时，无法传递的消息如何处理
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663747339427-0363e80e-9045-40a3-ab05-4cd1a0390510.png#clientId=u3ce085cd-ed5b-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=279&id=gzJEc&margin=%5Bobject%20Object%5D&name=image.png&originHeight=349&originWidth=1161&originalType=binary&ratio=1&rotation=0&showTitle=false&size=181604&status=done&style=none&taskId=u24af56da-59f4-4d91-93ba-c139eb82835&title=&width=928.8)
```yaml
spring.rabbitmq.host=182.92.234.71
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=123
spring.rabbitmq.publisher-confirm-type=correlated
```
在配置文件当中需要添加 spring.rabbitmq.publisher-confirm-type=correlated  
none ：禁用发布确认（默认值）
correlated：发布消息成功到交换机后会触发回调方法
simple： 经测试有两种效果，其一效果和 CORRELATED 值一样会触发回调方法， 其二在发布消息成功后使用 rabbitTemplate 调用 waitForConfirms 或 waitForConfirmsOrDie 方法 等待 broker 节点返回发送结果，根据返回结果来判定下一步的逻辑，要注意的点是 waitForConfirmsOrDie 方法如果返回 false 则会关闭 channel，则接下来无法发送消息到 broker  

```java
	@RestController
    @RequestMapping("/confirm")
    @Slf4j
    public class Producer {
        public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
        @Autowired
        private RabbitTemplate rabbitTemplate;
        @Autowired
        private MyCallBack myCallBack;
        //依赖注入 rabbitTemplate 之后再设置它的回调对象
        @PostConstruct
        public void init(){
            rabbitTemplate.setConfirmCallback(myCallBack);
        }
        
        @GetMapping("sendMessage/{message}")
        public void sendMessage(@PathVariable String message){
            //指定消息 id 为 1
            CorrelationData correlationData1=new CorrelationData("1");
            String routingKey="key1";
            rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE_NAME,routingKey,message+routingKey,correlationData1);
            CorrelationData correlationData2=new CorrelationData("2");
            routingKey="key2";
            rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE_NAME,routingKey,message+routingKey,correlationData2);
            log.info("发送消息内容:{}",message);
        }
    }
```
```java
	@Component
    @Slf4j
    public class MyCallBack implements RabbitTemplate.ConfirmCallback {
        /**
	* 交换机不管是否收到消息的一个回调方法
	* CorrelationData
	* 消息相关数据
	* ack
	* 交换机是否收到消息
	*/
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            String id=correlationData!=null?correlationData.getId():"";
            if(ack){
                log.info("交换机已经收到 id 为:{}的消息",id);
            }else{
                log.info("交换机还未收到 id 为:{}消息,由于原因:{}",id,cause);
            }
        }
    }
```

## 消息回退
 在仅开启了生产者确认机制的情况下，交换机接收到消息后，会直接给消息生产者发送确认消息，如 果发现该消息不可路由（比如routingkey不存在），那么消息会被直接丢弃，此时生产者是不知道消息被丢弃这个事件的。  
 通过设置 mandatory 参数可以在当消息传递过程中不可达目的地时将消息返回给生产者。  
```java
	@Slf4j
    @Component
    public class MessageProducer implements RabbitTemplate.ConfirmCallback ,RabbitTemplate.ReturnCallback {
        @Autowired
        private RabbitTemplate rabbitTemplate;
        //rabbitTemplate 注入之后就设置该值
        @PostConstruct
        private void init() {
            rabbitTemplate.setConfirmCallback(this);
            /**
* true：
* 交换机无法将消息进行路由时，会将该消息返回给生产者
* false：
* 如果发现消息无法进行路由，则直接丢弃
*/
            rabbitTemplate.setMandatory(true);
            //设置回退消息交给谁处理
            rabbitTemplate.setReturnCallback(this);
        }

        @GetMapping("sendMessage")
        public void sendMessage(String message){
            //让消息绑定一个 id 值
            CorrelationData correlationData1 = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend("confirm.exchange","key1",message+"key1",correlationData1);
            log.info("发送消息 id 为:{}内容为{}",correlationData1.getId(),message+"key1");
            CorrelationData correlationData2 = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend("confirm.exchange","key2",message+"key2",correlationData2);
            log.info("发送消息 id 为:{}内容为{}",correlationData2.getId(),message+"key2");
        }

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) 
        {String id = correlationData != null ? correlationData.getId() : "";
         if (ack) {
             log.info("交换机收到消息确认成功, id:{}", id);
         } else {
             log.error("消息 id:{}未成功投递到交换机,原因是:{}", id, cause);
         }
        }

        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String
                                    exchange, String routingKey) {
            log.info("消息:{}被服务器退回，退回原因:{}, 交换机是:{}, 路由 key:{}",
                     new String(message.getBody()),replyText, exchange, routingKey);
        }
    }

```
## 备份交换机
 有了 mandatory 参数和回退消息，我们获得了对无法投递消息的感知能力，有机会在生产者的消息无法被投递时发现并处理。
但有时候，我们并不知道该如何处理这些无法路由的消息，最多打个日志，然 后触发报警，再来手动处理。而通过日志来处理这些无法路由的消息是很不优雅的做法，特别是当生产者 所在的服务有多台机器的时候，手动复制日志会更加麻烦而且容易出错。
而且设置 mandatory 参数会增 加生产者的复杂性，需要添加处理这些被退回的消息的逻辑。如果既不想丢失消息，又不想增加生产者的 复杂性，该怎么做呢？前面在设置死信队列的文章中，我们提到，可以为队列设置死信交换机来存储那些 处理失败的消息，可是这些不可路由消息根本没有机会进入到队列，因此无法使用死信队列来保存消息。
在 RabbitMQ 中，有一种备份交换机的机制存在，可以很好的应对这个问题。什么是备份交换机呢？备份 交换机可以理解为 RabbitMQ 中交换机的“备胎”，当我们为某一个交换机声明一个对应的备份交换机时，就 是为它创建一个备胎，当交换机接收到一条不可路由消息时，将会把这条消息转发到备份交换机中，由备 份交换机来进行转发和处理，通常备份交换机的类型为 Fanout ，这样就能把所有消息都投递到与其绑定 的队列中，然后我们在备份交换机下绑定一个队列，这样所有那些原交换机无法被路由的消息，就会都进 入这个队列了。当然，我们还可以建立一个报警队列，用独立的消费者来进行监测和报警。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663749935528-d94e2e89-4872-4a5b-80b1-5f14f72058c1.png#clientId=u3ce085cd-ed5b-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=322&id=CxyW7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=402&originWidth=1140&originalType=binary&ratio=1&rotation=0&showTitle=false&size=103703&status=done&style=none&taskId=u6a9e6640-6ba7-462f-aa55-d7647567eca&title=&width=912)
```java
	@Configuration
    public class ConfirmConfig {
        public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
        public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
        public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";
        public static final String BACKUP_QUEUE_NAME = "backup.queue";
        public static final String WARNING_QUEUE_NAME = "warning.queue";
        // 声明确认队列
        @Bean("confirmQueue")
        public Queue confirmQueue(){
            return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
        }
        //声明确认队列绑定关系
        @Bean
        public Binding queueBinding(@Qualifier("confirmQueue") Queue queue,
                                    @Qualifier("confirmExchange") DirectExchange exchange){
            return BindingBuilder.bind(queue).to(exchange).with("key1");
        }
        //声明备份 Exchange
        @Bean("backupExchange")
        public FanoutExchange backupExchange(){
            return new FanoutExchange(BACKUP_EXCHANGE_NAME);
        }
        
        //声明确认 Exchange 交换机的备份交换机
        @Bean("confirmExchange")
        public DirectExchange confirmExchange(){
            ExchangeBuilder exchangeBuilder =
            	ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME)
            	.durable(true)
            	//设置该交换机的备份交换机
            	.withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME);
            return (DirectExchange)exchangeBuilder.build();
        }
        
        // 声明警告队列
        @Bean("warningQueue")
        public Queue warningQueue(){
            return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
        }
        // 声明报警队列绑定关系
        @Bean
        public Binding warningBinding(@Qualifier("warningQueue") Queue queue,
                                      @Qualifier("backupExchange") FanoutExchange backupExchange){
            return BindingBuilder.bind(queue).to(backupExchange);
        }
        // 声明备份队列
        @Bean("backQueue")
        public Queue backQueue(){
            return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
        }
        // 声明备份队列绑定关系
        @Bean
        public Binding backupBinding(@Qualifier("backQueue") Queue queue,
                                     @Qualifier("backupExchange") FanoutExchange backupExchange){
            return BindingBuilder.bind(queue).to(backupExchange);
        }
    }
```




# 死信队列
 先从概念解释上搞清楚这个定义，死信，顾名思义就是无法被消费的消息，字面意思可以这样理 解，一般来说，producer 将消息投递到 broker 或者直接到queue 里了，consumer 从 queue 取出消息 进行消费，但某些时候由于特定的原因导致 queue 中的某些消息无法被消费，这样的消息如果没有 后续的处理，就变成了死信，有死信自然就有了死信队列。  
 应用场景:为了保证订单业务的消息数据不丢失，需要使用到 RabbitMQ 的死信队列机制，当消息 消费发生异常时，将消息投入死信队列中.还有比如说: 用户在商城下单成功并点击去支付后在指定时 间未支付时自动失效  
## 死信的来源
消息TTL过期
队列达到最大长度
消息被拒绝（basic.reject或者basic.nack）并且requeue=false

声明死信交换机和死信队列，将死信交换机和死信队列绑定，并将死信交换机和普通队列绑定

# 延迟队列
## rabbitmq中的ttl
ttl是rabbitmq中消息或者队列的属性，表明一条消息或者该队列中所有消息的最大存活时间，
如果一条信息设置了ttl属性，或者进入了设置ttl属性的队列，那么这条消息如果在ttl设置的时间内没有被消费，则会成为死信，如果同时配置了队列的ttl和消息的ttl，那么较小的那个值将会被使用。
如果不设置ttl，消息永远不会过期，如果为0，则表示除非此时可以直接投递该消息到消费者，否则会被直接丢弃

## 延迟队列基础代码
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663746935439-932fa130-367f-45f2-880c-b53d96beeced.png#clientId=u3ce085cd-ed5b-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=257&id=u6a2b3b66&margin=%5Bobject%20Object%5D&name=image.png&originHeight=321&originWidth=1192&originalType=binary&ratio=1&rotation=0&showTitle=false&size=49395&status=done&style=none&taskId=u9b7cfb77-64aa-4982-92fb-fa3c4b5947f&title=&width=953.6)

```java
    @Configuration
    public class TtlQueueConfig {
        public static final String X_EXCHANGE = "X";
        public static final String QUEUE_A = "QA";
        public static final String QUEUE_B = "QB";
        public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
        public static final String DEAD_LETTER_QUEUE = "QD";
        // 声明 xExchange
        @Bean("xExchange")
        public DirectExchange xExchange(){
            return new DirectExchange(X_EXCHANGE);
        }
        // 声明 yExchange
        @Bean("yExchange")
        public DirectExchange yExchange(){
            return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
        }
        //声明队列 A ttl 为 10s 并绑定到对应的死信交换机 
        @Bean("queueA")
        public Queue queueA(){
            Map<String, Object> args = new HashMap<>(3);
            //声明当前队列绑定的死信交换机
            args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
            //声明当前队列的死信路由 key
            args.put("x-dead-letter-routing-key", "YD");
            //声明队列的 TTL
            args.put("x-message-ttl", 10000);
            return QueueBuilder.durable(QUEUE_A).withArguments(args).build();
        }
        // 声明队列 A 绑定 X 交换机
        @Bean
        public Binding queueaBindingX(@Qualifier("queueA") Queue queueA,
                                      @Qualifier("xExchange") DirectExchange xExchange){
            return BindingBuilder.bind(queueA).to(xExchange).with("XA");
        }
        //声明队列 B ttl 为 40s 并绑定到对应的死信交换机
        @Bean("queueB")
        public Queue queueB(){
            Map<String, Object> args = new HashMap<>(3);
            //声明当前队列绑定的死信交换机
            args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
            //声明当前队列的死信路由 key
            args.put("x-dead-letter-routing-key", "YD");
            //声明队列的 TTL
            args.put("x-message-ttl", 40000);
            return QueueBuilder.durable(QUEUE_B).withArguments(args).build();
        }
        //声明队列 B 绑定 X 交换机
        @Bean
        public Binding queuebBindingX(@Qualifier("queueB") Queue queue1B,
                                      @Qualifier("xExchange") DirectExchange xExchange){
            return BindingBuilder.bind(queue1B).to(xExchange).with("XB");
        }
        //声明死信队列 QD
        @Bean("queueD")
        public Queue queueD(){
            return new Queue(DEAD_LETTER_QUEUE);
        }
        //声明死信队列 QD 绑定关系
        @Bean
        public Binding deadLetterBindingQAD(@Qualifier("queueD") Queue queueD,
                                            @Qualifier("yExchange") DirectExchange yExchange){
            return BindingBuilder.bind(queueD).to(yExchange).with("YD");
        }
    }
```
```java
@Slf4j
@RequestMapping("ttl")
@RestController
public class SendMsgController {
 @Autowired
 private RabbitTemplate rabbitTemplate;
 @GetMapping("sendMsg/{message}")
 public void sendMsg(@PathVariable String message){
     log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);
     rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 10S 的队列: "+message);
     rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl 为 40S 的队列: "+message);
 }
}
```
```java
	@Slf4j
    @Component
    public class DeadLetterQueueConsumer {
        @RabbitListener(queues = "QD")
        public void receiveD(Message message, Channel channel) throws IOException {
         String msg = new String(message.getBody());
         log.info("当前时间：{},收到死信队列信息{}", new Date().toString(), msg);
        }
    }
```

## 延迟队列优化
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663746926502-b5cb9b87-e64f-4067-97e8-d29ea5661e97.png#clientId=u3ce085cd-ed5b-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=290&id=PtnNo&margin=%5Bobject%20Object%5D&name=image.png&originHeight=362&originWidth=1181&originalType=binary&ratio=1&rotation=0&showTitle=false&size=54841&status=done&style=none&taskId=u98d870e2-6e13-4499-be53-ff59903a16a&title=&width=944.8)
通过路径参数设置消息的ttl而不是提前设置队列的ttl
```java
	@Component
    public class MsgTtlQueueConfig {
        public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
        public static final String QUEUE_C = "QC";
        //声明队列 C 死信交换机
        @Bean("queueC")
        public Queue queueB(){
            Map<String, Object> args = new HashMap<>(3);
            //声明当前队列绑定的死信交换机
            args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
            //声明当前队列的死信路由 key
            args.put("x-dead-letter-routing-key", "YD");
            //没有声明 TTL 属性
            return QueueBuilder.durable(QUEUE_C).withArguments(args).build();
        }
        //声明队列 B 绑定 X 交换机
        @Bean
        public Binding queuecBindingX(@Qualifier("queueC") Queue queueC,
                                      @Qualifier("xExchange") DirectExchange xExchange){
            return BindingBuilder.bind(queueC).to(xExchange).with("XC");
        }
    }

```
```java
	@GetMapping("sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message,@PathVariable String ttlTime) {
        rabbitTemplate.convertAndSend("X", "XC", message, correlationData 
               ->{correlationData.getMessageProperties().setExpiration(ttlTime);
        return correlationData;});
		log.info("当前时间：{},发送一条时长{}毫秒 TTL 信息给队列 C:{}", new Date(),ttlTime, message);
	}

```
问题：rabbitmq 只会检查第一个消息是否过期，如果过期则丢到死信队列，如果第一个消息的演示时间很长，而第二个消息的延时时常很短，第二个消息并不会优先得到执行。

## 插件实现延迟队列
 在官网上下载 https://www.rabbitmq.com/community-plugins.html，下载 rabbitmq_delayed_message_exchange 插件，然后解压放置到 RabbitMQ 的插件目录。  
 进入 RabbitMQ 的安装目录下的 plgins 目录，执行下面命令让该插件生效，然后重启 RabbitMQ /usr/lib/rabbitmq/lib/rabbitmq_server-3.8.8/plugins rabbitmq-plugins enable rabbitmq_delayed_message_exchange  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663746904753-6e23f862-9efc-4739-967e-deed1d9c489b.png#clientId=u3ce085cd-ed5b-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=259&id=ub96faafc&margin=%5Bobject%20Object%5D&name=image.png&originHeight=324&originWidth=1215&originalType=binary&ratio=1&rotation=0&showTitle=false&size=75044&status=done&style=none&taskId=u5fc35a34-9c6e-4465-ba95-1bd0f5913c4&title=&width=972)
 在我们自定义的交换机中，这是一种新的交换类型，该类型消息支持延迟投递机制 消息传递后并 不会立即投递到目标队列中，而是存储在 mnesia(一个分布式数据系统)表中，当达到投递时间时，才 投递到目标队列中。  

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663746973105-834e83d6-ea5c-4a76-8a7d-46df66d61b8c.png#clientId=u3ce085cd-ed5b-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=186&id=uc16c8616&margin=%5Bobject%20Object%5D&name=image.png&originHeight=233&originWidth=1206&originalType=binary&ratio=1&rotation=0&showTitle=false&size=35756&status=done&style=none&taskId=ua51b050c-7b2d-4774-8567-deb4aea5fbd&title=&width=964.8)
```java
@Configuration
    public class DelayedQueueConfig {
        public static final String DELAYED_QUEUE_NAME = "delayed.queue";
        public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
        public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";
        @Bean
        public Queue delayedQueue() {
            return new Queue(DELAYED_QUEUE_NAME);
        }
        //自定义交换机 我们在这里定义的是一个延迟交换机
        @Bean
        public CustomExchange delayedExchange() {
            Map<String, Object> args = new HashMap<>();
            //自定义交换机的类型
            args.put("x-delayed-type", "direct");
            return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false,
                                      args);
        }
        @Bean
        public Binding bindingDelayedQueue(@Qualifier("delayedQueue") Queue queue,
                                           @Qualifier("delayedExchange") CustomExchange
                                           delayedExchange) {
            return
                BindingBuilder.bind(queue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
        }
    }

```

# 其他
## 幂等性
### 概述
 用户对于同一操作发起的一次请求或者多次请求的结果是一致的，不会因为多次点击而产生了副作用。  
 举个最简单的例子，那就是支付，用户购买商品后支付，支付扣款成功，但是返回结果的时候网络异常， 此时钱已经扣了，用户再次点击按钮，此时会进行第二次扣款，返回结果成功，用户查询余额发现多扣钱 了，流水记录也变成了两条。在以前的单应用系统中，我们只需要把数据操作放入事务中即可，发生错误 立即回滚，但是再响应客户端的时候也有可能出现网络中断或者异常等等  
### 消息重复消费 
消费者在消费 MQ 中的消息时，MQ 已把消息发送给消费者，消费者在给MQ 返回 ack 时网络中断， 故 MQ 未收到确认信息，该条消息会重新发给其他的消费者，或者在网络重连后再次发送给该消费者，但 实际上该消费者已成功消费了该条消息，造成消费者消费了重复的消息。 
### 解决思路 
MQ 消费 者的幂  等性的解决一般使用全局 ID 或者写个唯一标识比如时间戳 或者 UUID 或者订单消费 者消费 MQ 中的消息也可利用 MQ 的该 id 来判断，或者可按自己的规则生成一个全局唯一 id，每次消费消 息时用该 id 先判断该消息是否已消费过。 
### 消费端的幂等性保障 
在海量订单生成的业务高峰期，生产端有可能就会重复发生了消息，这时候消费端就要实现幂等性， 这就意味着我们的消息永远不会被消费多次，即使我们收到了一样的消息。业界主流的幂等性有两种操作:a. 唯一 ID+指纹码机制,利用数据库主键去重, b.利用 redis 的原子性去实现 
### 唯一ID+指纹码机制 
指纹码:我们的一些规则或者时间戳加别的服务给到的唯一信息码,它并不一定是我们系统生成的，基 本都是由我们的业务规则拼接而来，但是一定要保证唯一性，然后就利用查询语句进行判断这个 id 是否存 在数据库中,优势就是实现简单就一个拼接，然后查询判断是否重复；劣势就是在高并发时，如果是单个数 据库就会有写入性能瓶颈当然也可以采用分库分表提升性能，但也不是我们最推荐的方式。 
### Redis 原子性 
利用 redis 执行 setnx 命令，天然具有幂等性。从而实现不重复消费  
## 优先级队列
## 惰性队列
 RabbitMQ 从 3.6.0 版本开始引入了惰性队列的概念。惰性队列会尽可能的将消息存入磁盘中，而在消 费者消费到相应的消息时才会被加载到内存中，它的一个重要的设计目标是能够支持更长的队列，即支持 更多的消息存储。当消费者由于各种各样的原因(比如消费者下线、宕机亦或者是由于维护而关闭等)而致 使长时间内不能消费消息造成堆积时，惰性队列就很有必要了。  
 默认情况下，当生产者将消息发送到 RabbitMQ 的时候，队列中的消息会尽可能的存储在内存之中， 这样可以更加快速的将消息发送给消费者。即使是持久化的消息，在被写入磁盘的同时也会在内存中驻留 一份备份。当 RabbitMQ 需要释放内存的时候，会将内存中的消息换页至磁盘中，这个操作会耗费较长的 时间，也会阻塞队列的操作，进而无法接收新的消息。虽然 RabbitMQ 的开发者们一直在升级相关的算法， 但是效果始终不太理想，尤其是在消息量特别大的时候。  

# api

```java
CachingConnectionFactory connectionFactory = new CachingConnectionFactory("somehost");
connectionFactory.setUsername("guest");
connectionFactory.setPassword("guest");

Connection connection = connectionFactory.createConnection();
```
##### Adding Retry Capabilities
```java
@Bean
public RabbitTemplate rabbitTemplate() {
    RabbitTemplate template = new RabbitTemplate(connectionFactory());
    RetryTemplate retryTemplate = new RetryTemplate();
    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
    backOffPolicy.setInitialInterval(500);
    backOffPolicy.setMultiplier(10.0);
    backOffPolicy.setMaxInterval(10000);
    retryTemplate.setBackOffPolicy(backOffPolicy);
    template.setRetryTemplate(retryTemplate);
    return template;
}
```
```java
retryTemplate.execute(
    new RetryCallback<Object, Exception>() {

        @Override
        public Object doWithRetry(RetryContext context) throws Exception {
            context.setAttribute("message", message);
            return rabbitTemplate.convertAndSend(exchange, routingKey, message);
        }

    }, new RecoveryCallback<Object>() {

        @Override
        public Object recover(RetryContext context) throws Exception {
            Object message = context.getAttribute("message");
            Throwable t = context.getLastThrowable();
            // Do something with message
            return null;
        }
    });
}
```
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663816173709-cc6913fd-3e9a-4f84-8203-0cbd35e40e19.png#clientId=u3fcf61c7-ceb1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=766&id=u4ce5dd41&margin=%5Bobject%20Object%5D&name=image.png&originHeight=957&originWidth=1603&originalType=binary&ratio=1&rotation=0&showTitle=false&size=156384&status=done&style=none&taskId=u9768282e-595d-4b54-8ad4-e5beab9277d&title=&width=1282.4)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1663816178987-30ef50ec-06fe-49f1-b3c5-41653cde3e71.png#clientId=u3fcf61c7-ceb1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=779&id=u2cf57ea3&margin=%5Bobject%20Object%5D&name=image.png&originHeight=974&originWidth=1665&originalType=binary&ratio=1&rotation=0&showTitle=false&size=155564&status=done&style=none&taskId=u5e67c2f8-d5fc-4221-9180-844f5fd2ca9&title=&width=1332)


```java
void send(Message message) throws AmqpException;

void send(String routingKey, Message message) throws AmqpException;

void send(String exchange, String routingKey, Message message) throws AmqpException;
```


messageAPI
Each of the properties defined on the [MessageProperties](https://docs.spring.io/spring-amqp/docs/latest-ga/api/org/springframework/amqp/core/MessageProperties.html) can be set. Other methods include setHeader(String key, String value), removeHeader(String key), removeHeaders(), and copyProperties(MessageProperties properties). Each property setting method has a set*IfAbsent() variant. In the cases where a default initial value exists, the method is named set*IfAbsentOrDefault().
```java
Message message = MessageBuilder.withBody("foo".getBytes())
    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
    .setMessageId("123")
    .setHeader("bar", "baz")
    .build();
```
```java
MessageProperties props = MessagePropertiesBuilder.newInstance()
    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
    .setMessageId("123")
    .setHeader("bar", "baz")
    .build();
Message message = MessageBuilder.withBody("foo".getBytes())
    .andProperties(props)
    .build();
```

```java
Message receive() throws AmqpException;

Message receive(String queueName) throws AmqpException;

Message receive(long timeoutMillis) throws AmqpException;

Message receive(String queueName, long timeoutMillis) throws AmqpException;
```

```java
Object receiveAndConvert() throws AmqpException;

Object receiveAndConvert(String queueName) throws AmqpException;

Object receiveAndConvert(long timeoutMillis) throws AmqpException;

Object receiveAndConvert(String queueName, long timeoutMillis) throws AmqpException;
```



In the first example, a queue myQueue is declared automatically (durable) together with the exchange, if needed, and bound to the exchange with the routing key. In the second example, an anonymous (exclusive, auto-delete) queue is declared and bound; the queue name is created by the framework using the Base64UrlNamingStrategy. You cannot declare broker-named queues using this technique; they need to be declared as bean definitions; see [Containers and Broker-Named queues](https://docs.spring.io/spring-amqp/docs/current/reference/html/#containers-and-broker-named-queues). Multiple QueueBinding entries can be provided, letting the listener listen to multiple queues. In the third example, a queue with the name retrieved from property my.queue is declared, if necessary, with the default binding to the default exchange using the queue name as the routing key.
```java
@Component
public class MyService {

  @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "myQueue", durable = "true"),
        exchange = @Exchange(value = "auto.exch", ignoreDeclarationExceptions = "true"),
        key = "orderRoutingKey")
  )
  public void processOrder(Order order) {
    ...
  }

  @RabbitListener(bindings = @QueueBinding(
        value = @Queue,
        exchange = @Exchange(value = "auto.exch"),
        key = "invoiceRoutingKey")
  )
  public void processInvoice(Invoice invoice) {
    ...
  }

  @RabbitListener(queuesToDeclare = @Queue(name = "${my.queue}", durable = "true"))
  public String handleWithSimpleDeclare(String data) {
      ...
  }

}
```

```java
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "auto.headers", autoDelete = "true",
                        arguments = @Argument(name = "x-message-ttl", value = "10000",
                                                type = "java.lang.Integer")),
        exchange = @Exchange(value = "auto.headers", type = ExchangeTypes.HEADERS, autoDelete = "true"),
        arguments = {
                @Argument(name = "x-match", value = "all"),
                @Argument(name = "thing1", value = "somevalue"),
                @Argument(name = "thing2")
        })
)
public String handleWithHeadersExchange(String foo) {
    ...
}
```

```java
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RabbitListener(bindings = @QueueBinding(
        value = @Queue,
        exchange = @Exchange(value = "metaFanout", type = ExchangeTypes.FANOUT)))
public @interface MyAnonFanoutListener {
}

public class MetaListener {

    @MyAnonFanoutListener
    public void handle1(String foo) {
        ...
    }

    @MyAnonFanoutListener
    public void handle2(String foo) {
        ...
    }

}
```
