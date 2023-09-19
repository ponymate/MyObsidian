项目介绍：

基于Spring Boot + Dubbo + Gateway 的 API 接口开放调用平台。管理员可以接入并发布接口，查看接口调用情况等；用户可以开通接口调用权限，浏览及通过客户端SDK调用接口等。

主要工作：

1.根据业务流程,将整个项目后端划分为web系统、API 网关、模拟接口、公共模块、客户端SDK这5个子项目，并使用Maven进行多模块依赖管理和打包。

2.为防止接口被恶意调用，设计API签名认证算法，为用户分配唯一ak / sk以鉴权，保障调用的安全性、可溯源性(指便于统计接口调用次数)。

3.选用Spring Cloud Gateway作为API网关，实现了路由转发、访问控制并集中处理签名校验、请求参数校验、接口调用统计等业务逻辑，提高安全性的同时、便于系统开发维护。

4.为解决开发者调用成本过高的问题(自己封装签名使用HTTP客户端调用接口)，基于Spring Boot Starter开发了客户端SDK，一行代码即可调用接口，提高开发体验。

5.为解决多个子系统内代码大量重复的问题，抽象模型层和业务层代码，使用Dubbo RPC框架实现子系统间的高性能接口调用，大幅减少重复代码。

简介：

管理员可以对接口信息进行增删改查

用户可以访问前台，查看接口信息

系统架构：

![[Pasted image 20230325093945.png]]

技术选型:

Spring Boot
Spring Boot Starter（SDK）
Dubbo（RPC）
Nacos
Spring Cloud Gateway（网关，限流，日志）


### 调用http接口，签名认证，SDK

**SDK模块的作用**：

通过hutool工具发送http请求调用接口，并且将签名认证的信息加入请求头。便于用户方便使用接口。

**签名认证的实现和作用**

![[Pasted image 20230325095034.png]]

在用户登录和认证系统中，用户的账号和密码和用户的签名有什么区别

用户的账号和密码是一种常见的认证方式，用于验证用户的身份。用户在注册时设置一个账号和相应的密码，当用户需要登录时，系统会要求用户输入账号和密码，系统会对输入的账号和密码进行验证，如果验证通过，则认为用户是合法的，并且允许用户访问系统。

用户的签名则是一种数字签名，用于验证消息的完整性和来源。数字签名是由消息的发送者使用自己的私钥对消息进行加密生成的一段数据。当消息接收者收到消息时，可以使用发送者的公钥对签名进行解密，以验证消息的完整性和来源。

在用户登录和认证系统中，用户的账号和密码用于验证用户的身份，以确定用户是否有权访问系统。用户的签名则用于验证消息的完整性和来源，以确定消息是否是合法的。因此，两者有着不同的用途和目的。


签名相比用户名和密码是无状态的是什么意思

"无状态"（stateless）通常指的是系统或者协议不需要保存任何关于之前交互的信息来完成后续的操作。

在用户登录和认证系统中，用户的账号和密码是有状态的，因为系统需要在之前的交互中保存用户的账号和密码，以便验证用户的身份。一旦用户进行了登录操作并且系统验证通过，系统就可以在之后的交互中识别用户并授权其访问。

相比之下，数字签名是无状态的，因为数字签名本身就包含了消息的完整性和来源的验证信息，而不需要系统在之前的交互中保存任何关于签名的信息。因此，每次验证数字签名时，系统只需要使用相应的公钥对签名进行解密验证，而不需要保存任何状态信息。

总的来说，无状态的设计可以提高系统的可扩展性和性能，因为系统不需要保存大量的状态信息来处理大量的请求。但是，对于某些场景下，比如需要在多个请求之间维持用户会话状态的应用程序，无状态的设计可能并不适用


![[Pasted image 20230325102413.png]]
![[Pasted image 20230325102439.png]]

### 后台

1. 基础功能：增删改查

2. 接口发布下线

3. 统计接口调用次数

### 网关

统计接口次数的功能需要写在哪？？

否定aop，拦截器和通用方法，引出网关

aop的缺点：只能存在于单个项目中，如果每个团队都要开发自己的接口，都要引入我们的aop或者自己开发。

![[Pasted image 20230325110021.png]]

什么是网关?理解成火车站的检票口，统一去检票。

作用

统一去进行些操作、处理一些问题。
比如:
1.路由
2.负载均衡
3.统一鉴权
4.跨域
5.统一业务处理(缓存)
6.访问控制
7.发布控制
8.流量染色
9.接口保护
	a.限制请求
	b.信息脱敏
	c.降级(熔断)
	d.限流:学习令牌桶算法、学习漏桶算法,学习一下RedisLimitHandler
	e.超时时间
10.统一日志
11.统一文档

1. 路由
起到转发的作用，比如有接口A和接口B,网关会记录这些信息,根据用户访问的地址和参数,转发请求到对应的接口(服务器/集群)
/a=>接口A
/b=>接口B
https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#gateway-request-predicates-factories

2. 负载均衡
在路由的基础.上
/c=>服务A/集群A (随机转发到其中的某一个机器)
uri从固定地址改成lb:xxxx

3. 统一处理跨域
网关统一处理跨域，不用在每个项目里单独处理
https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#cors-configuration

4. 发布控制
灰度发布，比如上线新接口，先给新接口分配20%的流量，老接口80%，再慢慢调整比重。
https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-weight-route-predicate-factory

5. 流量染色
给请求(流量)添加一些标识，一般是设置请求头中，添加新的请求头
https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-addrequestheader-gatewayfilter-factory
全局染色: https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#default-filters

6. 统一接口保护
	1. 限制请求: https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#requestheadersize-gatewayfilter-factory
	2. 信息脱敏: https://docs .spring.io/spring-cloud-gateway/docs/current/reference/html/#the-removerequestheader-gatewayfilter-factory
	3. 降级(熔断) : https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#fallback-headers
	4. 限流: https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-requestratelimiter-gatewayfilter-factory
	5. 超时时间: https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#http-timeouts-configuration
	6. 重试(业务保护) : https://docs spring.io/spring-cloud-gateway/docs/current/reference/html/#the-retry-gatewayfilter-factory

7. 统一业务处理
把一些每个项目中都要做的通用逻辑放到上层(网关),统一处理，比如本项目的次数统计
统一鉴权，判断用户是否有权限进行操作，无论访问什么接口，我都统一去判断权限， 不用重复写。

8. 访问控制
黑白名单,比如限制DDOS IP

9. 统一日志
统一的请求、响应信息记录

10. 统一文档
将下游项目的文档进行聚合,在一个页面统一查看，建议用: https://doc.xiaominfo.com/docs/middleware-sources/aggregation-introduction


网关的分类

1. 全局网关(接入层网关) :作用负载均衡、请求日志等,不和业务逻辑绑定
2. 业务网关(微服务网关) :会有一些业务逻辑，作用是将请求转发到不同的业务/项目/接口/服务

参考文章: https://blog.csdn.net/qq_21040559/article/details/122961395

网关实现
1. Nginx (全局网关)、Kong 网关(API 网关, Kong: https://github.com/Kong/kong) , 编程成本相对高一点
2. Spring Cloud Gateway (取代了Zuul)性能高、可以用Java代码来写逻辑，适于学习
网关技术选型: https://zhuanlan.zhihu.com/p/500587132


### Spring Cloud Gateway

核心概念

路由(根据什么条件,转发请求到哪里)
断言: 一组规则、条件,用来确定如何转发路由
过滤器:对请求进行一系列的处理， 比如添加请求头、添加请求参数

请求流程:
1. 客户端发起请求
2. Handler Mapping:根据断言,去将请求转发到对应的路由
3. Web Handler:处理请求(一-层层经过过滤器)
4. 实际调用服务
![[Pasted image 20230325125840.png]]

断言
1. After 在xx时间之后
2. Before在xx时间之前
3. Between在xx时间之间
4. 请求类别
5. 请求头(包含Cookie)
6. 查询参数
7. 客户端地址
8. 权重

过滤器
基本功能:对请求头、请求参数、响应头的增删改查
1. 添加请求头
2. 添加请求参数
3. 添加响应头
4. 降级
5. 限流
6. 重试

### Gateway实战

要用到的特性
1. 路由(转发请求到模拟接口项目)
~~2. 负载均衡(需要用到注册中心)~~
3. 统-鉴权(accesskey, secretKey)
~~4. 跨域~~
5. 统一业务处理(每次请求接口后，接口调用次数+1)
~~6.访问控制(黑白名单)~~
~~7.发布控制~~
8. 流量染色(记录请求是否为网关来的)
~~9.接口保护~~
	a.限制请求
	b.信息脱敏
	c.降级(熔断)
	d.限流:学习令牌桶算法、学习漏桶算法,学习一下RedisLimitHandler
	e.超时时间
10.统一日志(记录每次的请求和响应日志)
~~11.统一文档~~


业务逻辑
1. 用户发送请求到API网关
2. 用户鉴权(判断ak、sk 是否合法)
3. 请求的模拟接口是否存在?
4. 请求转发，调用模拟接口
5. 调用成功,接口调用次数+ 1/调用失败,返回一个规范的错误码

编写业务逻辑
使用了GlobalFilter (编程式) ,全局请求拦截处理(类似AOP)
因为网关项目没引入MyBatis等操作数据库的类库，如果该操作较为复杂,可以由backend增删改查项目提供接口,我们直接调用，不用再重复写逻辑了。
- HTTP 请求(用HTTPClient、用RestTemplate、Feign)
- RPC (Dubbo)


问题
预期是等模拟接口调用完成，才记录响应日志、统计调用次数。
但现实是chain.filter方法立刻返回了，直到filter过滤器return后才调用了模拟接口。
原因是: chain.filter 是个异步操作，理解为前端的promise
解决方案:利用response装饰者,增强原有response的处理能力
参考博客: https://blog.csdn.net/qq_19636353/article/details/126759522 (以这个为主)

### Dubbo

RPC (Remote Procedure Call) 和 HTTP (Hypertext Transfer Protocol) 都是用于不同应用程序之间进行通信的协议。它们之间的区别在于：

1.  传输方式

RPC 协议使用 TCP 或 UDP 协议进行传输，而 HTTP 协议使用 TCP 协议进行传输。

2.  语法

RPC 协议使用自己的协议格式，如 gRPC 使用 Protocol Buffers 格式。而 HTTP 协议使用 HTTP 报文格式。

3.  方法调用

在 RPC 中，方法调用类似于本地方法调用，调用方直接调用方法，并将参数传递给服务端。而在 HTTP 中，需要使用 HTTP 请求方法（如 GET、POST、PUT、DELETE 等）来调用远程方法，并将参数作为请求体或查询参数传递。

4.  性能

由于 RPC 协议的语法和传输方式都比 HTTP 简单，因此 RPC 的性能往往比 HTTP 更好。此外，由于 RPC 使用二进制数据格式，因此可以更有效地利用网络带宽和资源。

整合运用
1. backend项目作为服务提供者,提供3个方法:
	a.查询用户是否有接口的调用次数（权限）
	b.从数据库中查询模拟接口是否存在，以及请求方法是否匹配(还可以校验请求参数)
	c.调用成功,接口调用次数+ 1 invokeCount
2. gateway项目作为服务调用者,调用这3个方法

使用：
1.服务接口类必须要在同一个包下，建议是抽象出一个公共项目(放接口、实体类等)
2.设置注解(比如启动类的EnableDubbo.接口实现类和Bean引用的注解)
3.添加配置和依赖


如何获取接口转发服务器的地址？
思路:网关启动时,获取所有的接口信息,维护到内存的hashmap中;有请求时,根据请求的url路径或者其他参数(比如host请求头)来判断应该转发到哪台服务器、以及用于校验接口是否存在

### 业务逻辑

invoke方法利用了反射

