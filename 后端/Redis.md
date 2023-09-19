# 概述
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656573078703-ffab8b82-9c1a-4d41-8c90-a20385b8afd2.png#clientId=ua1d73c72-6dc4-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=425&id=u42a8147e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=531&originWidth=861&originalType=binary&ratio=1&rotation=0&showTitle=false&size=345182&status=error&style=none&taskId=ud30582a1-c9a4-4bb2-bc95-90117d0daf3&title=&width=688.8)NoSQL(NoSQL = **Not Only SQL** )，意即“不仅仅是SQL”，泛指**非关系型的数据库**
**NoSQL适用场景 **
对数据高并发的读写
海量数据的读写
对数据高可扩展性的
**NoSQL不适用场景**
需要事务支持
基于sql的结构化查询存储，处理复杂的关系
**应用场景**
配合关系型数据库做高速缓存
高频次，热门访问的数据，降低数据库IO
分布式架构，做session共享
## 安装步骤
以redis-6.2.1为例
**1.下载最新版的gcc编译器**
yum install centos-release-scl scl-utils-build
yum install -y devtoolset-8-toolchain
scl enable devtoolset-8 bash
测试 gcc版本 
gcc --version
**2.下载redis 放到/opt目录**
**3.解压：tar -zxvf redis-6.2.1.tar.gz**
**4. 解压完成后进入目录：cd redis-6.2.1**
**5.在redis-6.2.1目录下再次执行make命令（需要有c语言编译环境）**
**6.make install**
**7. 安装目录：/**usr/local/bin
查看默认安装目录：
redis-benchmark:性能测试工具，可以在自己本子运行，看看自己本子性能如何
redis-check-aof：修复有问题的AOF文件，rdb和aof后面讲
redis-check-dump：修复有问题的dump.rdb文件
redis-sentinel：Redis集群使用
redis-server：Redis服务器启动命令
redis-cli：客户端，操作入口
**8.前台启动（不推荐）**
在安装目录下执行redis-server，命令行窗口不能关闭，否则服务器停止
**9.后台启动**
备份redis.conf   cp  /opt/redis-6.2.1/redis.conf  /myredis
[https://blog.csdn.net/ailaohuyou211/article/details/123827564?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-123827564-blog-79681387.topnsimilarv1&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-123827564-blog-79681387.topnsimilarv1&utm_relevant_index=1](https://blog.csdn.net/ailaohuyou211/article/details/123827564?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-123827564-blog-79681387.topnsimilarv1&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-123827564-blog-79681387.topnsimilarv1&utm_relevant_index=1)
**Redis启动 **redis-server  /myredis/redis.conf
**用客户端访问：redis-cli**
**Redis关闭 **单实例关闭：redis-cli shutdown 也可以进入终端后再关闭 输入 shutdown

## Redis介绍相关知识
端口号：6379
默认16个数据库，类似数组下标从0开始，初始默认使用0号库
使用命令select   <dbid>来切换数据库。如: select 8
Redis是单线程+多路IO复用技术
## Redis配置文件
### Units 单位
配置大小单位,开头定义了一些基本的度量单位，只支持bytes，不支持bit，大小写不敏感
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656602957198-98731632-2afd-4a13-904e-8b8b628e80e5.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=358&id=tAtcL&margin=%5Bobject%20Object%5D&name=image.png&originHeight=448&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=307637&status=error&style=none&taskId=u5fb7e31b-3984-4c6c-b620-a10f8a52198&title=&width=694.4)
### Includes 包含
多实例的情况可以把公用的配置文件提取出来
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603159725-546b0345-5d83-495f-ba9e-a0256a33fac2.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=302&id=R3RHA&margin=%5Bobject%20Object%5D&name=image.png&originHeight=377&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=381567&status=error&style=none&taskId=u0ddad3f4-da00-4e5e-a762-6d122c93b1b&title=&width=694.4)
### 网络相关配置
默认情况bind=127.0.0.1只能接受本机的访问请求
不写的情况下，无限制接受任何ip地址的访问
如果开启了protected-mode，那么在没有设定bind ip且没有设密码的情况下，Redis只允许接受本机的响应
端口号，默认 6379
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603272774-6e8f3892-27ce-410c-8691-1d09b027819f.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=390&id=z6mqb&margin=%5Bobject%20Object%5D&name=image.png&originHeight=488&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=467196&status=error&style=none&taskId=u4a538943-4d16-44eb-8db7-5d53a44eeca&title=&width=694.4)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603282254-ca4aef50-07d1-46e7-a246-31bbced41ce5.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=340&id=OaaFL&margin=%5Bobject%20Object%5D&name=image.png&originHeight=425&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=388442&status=error&style=none&taskId=uc804bdf8-0a17-4697-bf4e-0ff64cf73cc&title=&width=694.4)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603299125-93f65bce-2e95-4c90-9e5b-5d6645b51f61.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=59&id=W0YBv&margin=%5Bobject%20Object%5D&name=image.png&originHeight=74&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=75695&status=error&style=none&taskId=ufc3f0789-5db1-4d64-ba24-d9b0ddbae68&title=&width=694.4)
设置tcp的backlog，backlog其实是一个连接队列，backlog队列总和=未完成三次握手队列+ 已经完成三次握手队列。
在高并发环境下你需要一个高backlog值来避免慢客户端连接问题。
注意Linux内核会将这个值减小到/proc/sys/net/core/somaxconn的值（128），所以需要增大/proc/sys/net/core/somaxconn和/proc/sys/net/ipv4/tcp_max_syn_backlog（128）两个值来达到想要的效果
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603381550-1f2db40e-9d95-464b-bb96-10d9c77fe28a.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=148&id=jgwsU&margin=%5Bobject%20Object%5D&name=image.png&originHeight=185&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=179091&status=error&style=none&taskId=u8ee31467-1240-4e05-bf6a-527872090c5&title=&width=694.4)
一个空闲的客户端维持多少秒会关闭，0表示关闭该功能。即永不关闭。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603392083-e4ba5f77-afcc-4da0-a6bf-c7359f72850a.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=207&id=I8sWv&margin=%5Bobject%20Object%5D&name=image.png&originHeight=259&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=178330&status=error&style=none&taskId=u832a8b89-a33d-4b0f-91d8-f5d931fa1a2&title=&width=694.4)
对访问客户端的一种心跳检测，每个n秒检测一次，单位为秒，如果设置为0，则不会进行Keepalive检测，
建议设置成60 
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603412203-a5605b9f-e656-4cbc-a374-6f6df22884f2.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=287&id=hGvU7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=359&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=299482&status=error&style=none&taskId=u5ea243ed-c254-4173-a0be-dd47a92a875&title=&width=694.4)
### General通用
是否为后台进程，设置为yes，守护进程，后台启动
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603480500-59a7724d-99de-4a6f-9fe1-6cb8a4718a57.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=54&id=sBM06&margin=%5Bobject%20Object%5D&name=image.png&originHeight=67&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=70230&status=error&style=none&taskId=uc2d27cce-a1b7-48ea-a0d6-93b9df6f8ba&title=&width=694.4)
存放pid文件的位置，每个实例会产生一个不同的pid文件
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603498653-81430af7-3b70-480a-8c56-9ed19731a216.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=182&id=UoH4C&margin=%5Bobject%20Object%5D&name=image.png&originHeight=228&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=235696&status=error&style=none&taskId=u6324f6d9-7d4f-484c-b0a3-2dd52977c41&title=&width=694.4)
指定日志记录级别，Redis总共支持四个级别：debug、verbose、notice、warning，默认为**notice**
四个级别根据使用阶段来选择，生产环境选择notice 或者warning![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603512175-104494a3-2555-422b-84d3-8244c6a3b3ef.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=135&id=KlMbA&margin=%5Bobject%20Object%5D&name=image.png&originHeight=169&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=177673&status=error&style=none&taskId=ud29f1d13-9d00-4ed3-a449-afa14e5f8a1&title=&width=694.4)
日志文件名称![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603522882-f231c1fe-a699-4111-aff1-acc633c83bdd.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=79&id=CT2YH&margin=%5Bobject%20Object%5D&name=image.png&originHeight=99&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=113626&status=error&style=none&taskId=u43c66076-b221-48ee-ac8d-e6865f3f1dc&title=&width=694.4)
设定库的数量默认16，默认数据库为0，可以使用SELECT <dbid>命令在连接上指定数据库id![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603535407-3fc857ed-0b12-4827-ae21-549321a381d7.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=72&id=eicDA&margin=%5Bobject%20Object%5D&name=image.png&originHeight=90&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=94921&status=error&style=none&taskId=u3dca833b-e282-411d-969c-b7311fb35eb&title=&width=694.4)
### Limits 限制
设置redis同时可以与多少个客户端进行连接
如果达到了此限制，redis则会拒绝新的连接请求，并且向这些连接请求方发出“max number of clients reached”以作回应
建议**必须设置**，否则，将内存占满，造成服务器宕机
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603704052-9f4893eb-ec92-4f00-9880-297a17c80c69.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=182&id=Agz5m&margin=%5Bobject%20Object%5D&name=image.png&originHeight=228&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=243150&status=error&style=none&taskId=u74506baa-acb3-48e0-b0c3-fd552c2e14f&title=&width=694.4)
设置redis可以使用的内存量。一旦到达内存使用上限，redis将会试图移除内部数据，移除规则可以通过maxmemory-policy来指定。
如果redis无法根据移除规则来移除内存中的数据，或者设置了“不允许移除”，那么redis则会针对那些需要申请内存的指令返回错误信息，比如SET、LPUSH等。
但是对于无内存申请的指令，仍然会正常响应，比如GET等。如果你的redis是主redis（说明你的redis有从redis），那么在设置内存使用上限时，需要在系统中留出一些内存空间给同步队列缓存，只有在你设置的是“不移除”的情况下，才不用考虑这个因素
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603850179-18dc8534-23a4-4e61-8a1e-a8c6949dc61a.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=422&id=sahrM&margin=%5Bobject%20Object%5D&name=image.png&originHeight=528&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=592486&status=error&style=none&taskId=u56bc1c73-068c-4410-a505-ad1a0919230&title=&width=694.4)
volatile-lru：使用LRU算法移除key，只对设置了过期时间的键；（最近最少使用）
allkeys-lru：在所有集合key中，使用LRU算法移除key
volatile-random：在过期集合中移除随机的key，只对设置了过期时间的键
allkeys-random：在所有集合key中，移除随机的key
volatile-ttl：移除那些TTL值最小的key，即那些最近要过期的key
noeviction：不进行移除。针对写操作，只是返回错误信息![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656603877986-cead4a32-3033-4993-8dcf-a524358fbc98.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=393&id=X9vSD&margin=%5Bobject%20Object%5D&name=image.png&originHeight=491&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=466065&status=error&style=none&taskId=u386fa2c0-c642-429e-8293-4ba314b8665&title=&width=694.4)
设置样本数量，LRU算法和最小TTL算法都并非是精确的算法，而是估算值，所以你可以设置样本的大小，redis默认会检查这么多个key并选择其中LRU的那个。
一般设置3到7的数字，数值越小样本越不准确，但性能消耗越小。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656604064288-de8b1eed-c8ac-41f7-95d0-980d3de660f1.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=185&id=Poh8L&margin=%5Bobject%20Object%5D&name=image.png&originHeight=231&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=243062&status=error&style=none&taskId=u692e3b19-5e7a-408b-950a-9cfceaefc26&title=&width=694.4)

# 数据类型
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656573677638-1a3154d5-388b-40ae-8a7f-7ede7b7b5b28.png#clientId=ua1d73c72-6dc4-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=292&id=HauxQ&margin=%5Bobject%20Object%5D&name=image.png&originHeight=365&originWidth=860&originalType=binary&ratio=1&rotation=0&showTitle=false&size=229616&status=error&style=none&taskId=u6965d34c-104b-4678-a59c-84f8ee31cf0&title=&width=688)
## key
keys *查看当前库所有key    (匹配：keys *1)
exists key判断某个key是否存在
type key 查看你的key是什么类型
del key       删除指定的key数据
unlink key   根据value选择非阻塞删除（仅将keys从keyspace元数据中删除，真正的删除会在后续异步操作。）
expire key 10   10秒钟：为给定的key设置过期时间
ttl key 查看还有多少秒过期，-1表示永不过期，-2表示已过期

select 0~16命令切换数据库
dbsize查看当前数据库的key的数量
flushdb清空当前库
flushall通杀全部库
## String
String是Redis最基本的类型，一个key对应一个value。
String类型是二进制安全的。意味着Redis的string可以包含任何数据。比如jpg图片或者序列化的对象。
String类型是Redis最基本的数据类型，一个Redis中字符串value最多可以是512M
**设置**
set   <key><value>添加键值对
*NX：当数据库中key不存在时，可以将key-value添加数据库
*XX：当数据库中key存在时，可以将key-value添加数据库，与NX参数互斥
*EX：key的过期时间秒数
*PX：key的过期时间毫秒数，与EX互斥
**setex  <key><过期时间><value>  **设置键值的同时，设置过期时间，单位秒。
**获取**
get   <key>查询对应键值
append  <key><value>将给定的<value> 追加到原值的末尾
strlen  <key>获得值的长度
setnx  <key><value>只有在 key 不存在时    设置key 的值
**数字增加**
incr  <key>  将 key 中储存的数字值增1，只能对数字值操作，如果为空，新增值为1
decr  <key> 将 key 中储存的数字值减1，只能对数字值操作，如果为空，新增值为-1
incrby / decrby  <key><步长>将 key 中储存的数字值增减。自定义步长。
**批量操作**
mset  <key1><value1><key2><value2>  .....  同时设置一个或多个 key-value对  
mget  <key1><key2><key3> .....  同时获取一个或多个 value  
msetnx <key1><value1><key2><value2>  .....   同时设置一个或多个 key-value 对，当且仅当所有给定key 都不存在 

getrange  <key><起始位置><结束位置> 获得值的范围，类似java中的substring，**前包，后包**
setrange  <key><起始位置><value>  用 <value>  覆写<key>所储存的字符串值，从<起始位置>开始(**索引从0开始**)。

getset <key><value>  以新换旧，设置了新值同时获得旧值。

**原子性，有一个失败则都失败**
（1）在单线程中，能够在单条指令中完成的操作都可以认为是"原子操作"，因为中断只能发生于指令之间。
（2）在多线程中，不能被其它进程（线程）打断的操作就叫原子操作。
Redis单命令的原子性主要得益于Redis的单线程。

**数据结构**
String的数据结构为简单动态字符串(Simple Dynamic String,缩写SDS)。是可以修改的字符串，内部结构实现上类似于Java的ArrayList，采用预分配冗余空间的方式来减少内存的频繁分配
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656575475923-9ba7f537-b0e1-434a-95b9-1e9db4af3cbc.png#clientId=ua1d73c72-6dc4-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=126&id=ud80c23c2&margin=%5Bobject%20Object%5D&name=image.png&originHeight=157&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=11922&status=error&style=none&taskId=u62fb8240-1725-4b28-9523-862acbf4e6f&title=&width=692)
如图中所示，内部为当前字符串实际分配的空间capacity一般要高于实际字符串长度len。当字符串长度小于1M时，扩容都是加倍现有的空间，如果超过1M，扩容时一次只会多扩1M的空间。需要注意的是字符串最大长度为512M。

## List
Redis 列表是简单的字符串列表，按照插入顺序排序。
你可以添加一个元素到列表的头部（左边）或者尾部（右边）
**插入**
lpush/rpush  <key><value1><value2><value3> .... 从左边/右边插入一个或多个值。
**取出**
lpop/rpop  <key>从左边/右边吐出一个值。值在键在，值亡键亡。

rpoplpush  <key1><key2>从<key1>列表右边吐出一个值，插到<key2>列表左边。

lrange <key><start><stop> 按照索引下标获得元素。
0左边第一个，-1右边第一个
lrange mylist 0 -1   

lindex <key><index>按照索引下标获得元
llen <key>获得列表长度
linsert <key>  before <value><newvalue>  在<value>的前插入<newvalue>
lrem <key><n><value>从左边删除n个value(从左到右)
lset<key><index><value>将列表key下标为index的值替换成value

**数据结构**
它的底层实际是个双向链表，对两端的操作性能很高，通过索引下标的操作中间的节点性能会较差
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656575536542-0c2ed991-2a0e-4e4f-a5df-9df7845eb52f.png#clientId=ua1d73c72-6dc4-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=104&id=v8rJV&margin=%5Bobject%20Object%5D&name=image.png&originHeight=130&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=73968&status=error&style=none&taskId=ufbf9e2dc-d74c-4242-9fa3-5bc1501ede7&title=&width=694.4)
List的数据结构为快速链表quickList
首先在列表元素较少的情况下会使用一块连续的内存存储，这个结构是ziplist，也即是压缩列表，它将所有的元素紧挨着一起存储，分配的是一块连续的内存，当数据量比较多的时候才会改成quicklist。
因为普通的链表需要的附加指针空间太大，会比较浪费空间。比如这个列表里存的只是int类型的数据，结构上还需要两个额外的指针prev和next。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656601807519-bf2c456a-60a4-4537-bbae-287c33b5c2b4.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=72&id=ub6ecf394&margin=%5Bobject%20Object%5D&name=image.png&originHeight=90&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=22802&status=error&style=none&taskId=u5e679ef3-ac4b-433c-bed8-7c2e31f4370&title=&width=692)
Redis将链表和ziplist结合起来组成了quicklist。也就是将多个ziplist使用双向指针串起来使用。这样既满足了快速的插入删除性能，又不会出现太大的空间冗余。

## Set
Redis set对外提供的功能与list类似是一个列表的功能，特殊之处在于set是可以**自动排重**
并且set提供了判断某个成员是否在一个set集合内的重要接口，这个也是list所不能提供的
Redis的Set是string类型的无序集合。它底层其实是一个value为null的hash表，所以添加，删除，查找的**复杂度都是O(1)**

sadd <key><value1><value2> ..... 
将一个或多个 member 元素加入到集合key 中，已经存在的member 元素将被忽略
smembers <key>取出该集合的所有值。
sismember <key><value>判断集合<key>是否为含有该<value>值，有1，没有0
scard<key>返回该集合的元素个数。
srem <key><value1><value2> .... 删除集合中的某个元素。
spop <key>随机从该集合中吐出一个值
srandmember <key><n>随机从该集合中取出n个值。不会从集合中删除。
smove <source><destination><value>把集合中一个值从一个集合移动到另一个集合
sinter <key1><key2>返回两个集合的交集元素。
sunion <key1><key2>返回两个集合的并集元素。
sdiff <key1><key2>返回两个集合的差集元素(key1中的，不包含key2中的)

**数据结构**
Set数据结构是dict字典，字典是用哈希表实现的。
Java中HashSet的内部实现使用的是HashMap，所有的value都指向同一个对象。
Redis的set结构也是一样，它的内部也使用hash结构，所有的value都指向同一个内部值。

## Zset（有序集合）
Redis有序集合zset与普通集合set非常相似，是一个没有重复元素的字符串集合。
不同之处是有序集合的每个成员都关联了一个**评分（score）**,这个评分（score）被用来按照从最低分到最高分的方式排序集合中的成员。集合的成员是唯一的，但是评分可以是重复了。
因为元素是有序的, 所以你也可以很快的根据评分（score）或者次序（position）来获取一个范围的元素。
访问有序集合的中间元素也是非常快的,因此你能够使用有序集合作为一个没有重复成员的智能列表。

zadd  <key><score1><value1><score2><value2>…
将一个或多个 member 元素及其score 值加入到有序集key 当中。
**zrange <key><start><stop>  [WITHSCORES]   **
返回有序集 key 中，下标在<start><stop>之间的元素
带WITHSCORES，可以让分数一起和值返回到结果集。
zrangebyscore key min max [withscores] [limit offset count]
返回有序集 key 中，所有score 值介于min 和max 之间(包括等于min 或max )的成员。有序集成员按score 值递增(从小到大)次序排列。
zrevrangebyscore key maxmin [withscores] [limit offset count]               
同上，改为从大到小排列。
zincrby <key><increment><value>      为元素的score加上增量
zrem  <key><value>删除该集合下，指定值的元素
zcount <key><min><max>统计该集合，分数区间内的元素个数
zrank <key><value>返回该值在集合中的排名，从0开始。

**数据结构**
SortedSet(zset)是Redis提供的一个非常特别的数据结构，一方面它等价于Java的数据结构Map<String, Double>，可以给每一个元素value赋予一个权重score，另一方面它又类似于TreeSet，内部的元素会按照权重score进行排序，可以得到每个元素的名次，还可以通过score的范围来获取元素的列表。
zset底层使用了两个数据结构
（1）hash，hash的作用就是关联元素value和权重score，保障元素value的唯一性，可以通过元素value找到相应的score值。
（2）跳跃表，跳跃表的目的在于给元素value排序，根据score的范围获取元素列表。
跳跃表
有序集合在生活中比较常见，例如根据成绩对学生排名，根据得分对玩家排名等。对于有序集合的底层实现，可以用数组、平衡树、链表等。数组不便元素的插入、删除；平衡树或红黑树虽然效率高但结构复杂；链表查询需要遍历所有效率低。Redis采用的是跳跃表。跳跃表效率堪比红黑树，实现远比红黑树简单。
2、实例
（1）   有序链表
要查找值为51的元素，需要从第一个元素开始依次查找、比较才能找到。共需要6次比较。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656602861926-50424ff7-766e-41db-b3ef-bab030cb5a95.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=50&id=u091a7ad9&margin=%5Bobject%20Object%5D&name=image.png&originHeight=62&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=18083&status=error&style=none&taskId=u723c3705-99f8-4615-b9f3-52a10a929cd&title=&width=692)
（2）   跳跃表
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656602866928-aeebf740-c6f6-4439-a31d-ccccb84d99ee.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=203&id=u04133a26&margin=%5Bobject%20Object%5D&name=image.png&originHeight=254&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=49305&status=error&style=none&taskId=ub9c1fee0-15a5-4483-a0d3-050d8116612&title=&width=692)
从第2层开始，1节点比51节点小，向后比较。
21节点比51节点小，继续向后比较，后面就是NULL了，所以从21节点向下到第1层
在第1层，41节点比51节点小，继续向后，61节点比51节点大，所以从41向下
在第0层，51节点为要查找的节点，节点被找到，共查找4次。

从此可以看出跳跃表比有序链表效率要高

## Hash
Redis hash 是一个键值对集合。
Redis hash是一个string类型的field和value的映射表，hash特别适合用于存储对象。
类似Java里面的Map<String,Object>

hset <key><field><value>给<key>集合中的  <field>键赋值<value>
hget <key1><field>从<key1>集合<field>取出 value 
hmset <key1><field1><value1><field2><value2>... 批量设置hash的值
hexists<key1><field>查看哈希表 key 中，给定域 field 是否存在。 
hkeys <key>列出该hash集合的所有field
hvals <key>列出该hash集合的所有value
hincrby <key><field><increment>为哈希表 key 中的域 field 的值加上增量 
hsetnx <key><field><value>将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在

**数据结构**
Hash类型对应的数据结构是两种：ziplist（压缩列表），hashtable（哈希表)
当field-value长度较短且个数较少时，使用ziplist，否则使用hashtable

# Redis的发布和订阅
## 概述
Redis 发布订阅 (pub/sub) 是一种消息通信模式：发送者 (pub) 发送消息，订阅者 (sub) 接收消息
Redis 客户端可以订阅任意数量的频道
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656604156582-56853e20-1168-45c3-83b0-277d45223a05.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=253&id=u37b7833d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=316&originWidth=491&originalType=binary&ratio=1&rotation=0&showTitle=false&size=36989&status=error&style=none&taskId=ucb62474b-0eb2-4db7-821a-01303230b8e&title=&width=392.8)
## 实现
1.打开一个客户端 subscribe channel1 订阅频道channel1
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656604223083-3a4008ce-6594-444b-8171-768f70a30311.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=106&id=u67eb2996&margin=%5Bobject%20Object%5D&name=image.png&originHeight=133&originWidth=469&originalType=binary&ratio=1&rotation=0&showTitle=false&size=29340&status=error&style=none&taskId=u4f3c9197-248e-4ab6-8991-1743512d177&title=&width=375.2)
2.打开另一个客户端 publish channel1 hello 给channel1发布信息hello，返回订阅者数量
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656604273605-6eae0edf-8a84-481f-9241-4dd1e0791a2b.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=40&id=ub2c1df8f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=50&originWidth=497&originalType=binary&ratio=1&rotation=0&showTitle=false&size=15602&status=error&style=none&taskId=u596e088e-3e66-4018-bb04-f473433ab96&title=&width=397.6)
3.第一个客户端接收到信息
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656604381619-55973477-d71e-41ce-a842-2a7acf77386f.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=164&id=uae0b7ef7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=205&originWidth=498&originalType=binary&ratio=1&rotation=0&showTitle=false&size=40470&status=error&style=none&taskId=u423a59bd-ae52-4abc-8398-b458933a99b&title=&width=398.4)

# Redis新数据类型
## Bitmaps
Redis提供了Bitmaps这个“数据类型”可以实现对位的操作：
（1）Bitmaps本身不是一种数据类型，实际上它就是字符串（key-value）但是它可以对字符串的位进行操作。
（2）Bitmaps单独提供了一套命令，所以在Redis中使用Bitmaps和使用字符串的方法不太相同。可以把Bitmaps想象成一个以位为单位的数组，数组的每个单元只能存储0和1，数组的下标在Bitmaps中叫做偏移量。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656604666673-efc3bd3a-ea12-40e0-8de6-8e24127b6365.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=69&id=u6ace2301&margin=%5Bobject%20Object%5D&name=image.png&originHeight=86&originWidth=864&originalType=binary&ratio=1&rotation=0&showTitle=false&size=21153&status=error&style=none&taskId=u7673fadf-58ee-4a50-a1a1-fc15f2e482b&title=&width=691.2)

1、setbit
（1）格式
setbit<key><offset><value>设置Bitmaps中某个偏移量的值（0或1）
*offset:偏移量从0开始
（2）实例
每个独立用户是否访问过网站存放在Bitmaps中，将访问的用户记做1，没有访问的用户记做0，用偏移量作为用户的id。
设置键的第offset个位的值（从0算起），假设现在有20个用户，userid=1， 6， 11， 15， 19的用户对网站进行了访问，那么当前Bitmaps初始化结果如图
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656604932882-f3c0d1fe-fc10-4d28-967e-70ac7476060c.png#clientId=ua9f3644d-11b1-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=113&id=ua865606a&margin=%5Bobject%20Object%5D&name=image.png&originHeight=141&originWidth=674&originalType=binary&ratio=1&rotation=0&showTitle=false&size=17717&status=error&style=none&taskId=ua8c62f29-d7bc-4d6b-a9aa-b245957ccf4&title=&width=539.2)
在第一次初始化Bitmaps时，假如偏移量非常大，那么整个初始化过程执行会比较慢，可能会造成Redis的阻塞。

2、getbit
（1）格式
getbit<key><offset>获取Bitmaps中某个偏移量的值
获取键的第offset位的值（从0开始算）

3、bitcount
（1）格式
bitcount<key>[start end] 统计字符串从start字节到end字节比特值为1的数量
注意：redis的setbit设置或清除的是bit位置，而bitcount计算的是byte位置。

4、bitop
(1)格式
bitop  and(or/not/xor) <destkey> [key…]
bitop是一个复合操作，它可以做多个Bitmaps的and（交集）、 or（并集）、 not（非）、 xor（异或）操作并将结果保存在destkey中。
(2)实例
计算出两天都访问过网站的用户数量：bitop and unique:users:and:20201104_03 unique:users:20201103 unique:users:20201104
计算出任意一天都访问过网站的用户数量（例如月活跃就是类似这种），可以使用or求并集

**Bitmaps和set对比**
假设网站有1亿用户，每天独立访问的用户有5千万，如果每天用集合类型和Bitmaps分别存储活跃用户可以得到表

| set和Bitmaps存储一天活跃用户对比 |  |  |  |
| --- | --- | --- | --- |
| 数据
类型 | 每个用户id占用空间 | 需要存储的用户量 | 全部内存量 |
| 集合 | 64位 | 50000000 | 64位*50000000 = 400MB |
| Bitmaps | 1位 | 100000000 | 1位*100000000 = 12.5MB |

很明显，这种情况下使用Bitmaps能节省很多的内存空间，尤其是随着时间推移节省的内存还是非常可观的

| set和Bitmaps存储独立用户空间对比 |  |  |  |
| --- | --- | --- | --- |
| 数据类型 | 一天 | 一个月 | 一年 |
| 集合类型 | 400MB | 12GB | 144GB |
| Bitmaps | 12.5MB | 375MB | 4.5GB |

但Bitmaps并不是万金油，假如该网站每天的独立访问用户很少，例如只有10万（大量的僵尸用户），那么两者的对比如下表所示，很显然，这时候使用Bitmaps就不太合适了，因为基本上大部分位都是0。

| set和Bitmaps存储一天活跃用户对比（独立用户比较少） |  |  |  |
| --- | --- | --- | --- |
| 数据类型 | 每个userid占用空间 | 需要存储的用户量 | 全部内存量 |
| 集合类型 | 64位 | 100000 | 64位*100000 = 800KB |
| Bitmaps | 1位 | 100000000 | 1位*100000000 = 12.5MB |

## HyperLogLog
在工作当中，我们经常会遇到与统计相关的功能需求，比如统计网站PV（PageView页面访问量）,可以使用Redis的incr、incrby轻松实现。
但像UV（UniqueVisitor，独立访客）、独立IP数、搜索记录数等需要去重和计数的问题如何解决？这种求集合中不重复元素个数的问题称为基数问题。
解决基数问题有很多种方案：
（1）数据存储在MySQL表中，使用distinct count计算不重复个数
（2）使用Redis提供的hash、set、bitmaps等数据结构来处理
以上的方案结果精确，但随着数据不断增加，导致占用空间越来越大，对于非常大的数据集是不切实际的。
能否能够降低一定的精度来平衡存储空间？Redis推出了HyperLogLog
Redis HyperLogLog 是用来做基数统计的算法，HyperLogLog 的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需的空间总是固定的、并且是很小的。
在 Redis 里面，每个 HyperLogLog 键只需要花费 12 KB 内存，就可以计算接近 2^64 个不同元素的基数。这和计算基数时，元素越多耗费内存就越多的集合形成鲜明对比。
但是，因为 HyperLogLog 只会根据输入元素来计算基数，而不会储存输入元素本身，所以 HyperLogLog 不能像集合那样，返回输入的各个元素。
什么是基数?
比如数据集 {1, 3, 5, 7, 5, 7, 8}，那么这个数据集的基数集为 {1, 3, 5 ,7, 8}, 基数(不重复元素)为5。基数估计就是在误差可接受的范围内，快速计算基数。

1、pfadd 
（1）格式
pfadd <key>< element> [element ...]   添加指定元素到 HyperLogLog 中，如果执行命令后HLL估计的近似基数发生变化，则返回1，否则返回0。

2、pfcount
（1）格式
pfcount<key> [key ...] 计算HLL的近似基数，可以计算多个HLL，比如用HLL存储每天的UV，计算一周的UV可以使用7天的UV合并计算即可

3、pfmerge
（1）格式
pfmerge<destkey><sourcekey> [sourcekey ...]  将一个或多个HLL合并后的结果存储在另一个HLL中，比如每月活跃用户可以使用每天的活跃用户来合并计算可得

## Genosopatial
Redis 3.2 中增加了对GEO类型的支持。GEO，Geographic，地理信息的缩写。该类型，就是元素的2维坐标，在地图上就是经纬度。redis基于该类型，提供了经纬度设置，查询，范围查询，距离查询，经纬度Hash等常见操作。

1、geoadd
（1）格式
geoadd<key>< longitude><latitude><member> [longitude latitude member...]   添加地理位置（经度，纬度，名称）
（2）实例
geoadd china:city 121.47 31.23 shanghai
geoadd china:city 106.50 29.53 chongqing 114.05 22.52 shenzhen 116.38 39.90 beijing
两极无法直接添加，一般会下载城市数据，直接通过 Java 程序一次性导入。
有效的经度从 -180 度到 180 度。有效的纬度从 -85.05112878 度到 85.05112878 度。
当坐标位置超出指定范围时，该命令将会返回一个错误。
已经添加的数据，是无法再次往里面添加的。

2、geopos  
（1）格式
geopos  <key><member> [member...]  获得指定地区的坐标

3、geodist
（1）格式
geodist<key><member1><member2>  [m|km|ft|mi ]  获取两个位置之间的直线距离
（2）实例
获取两个位置之间的直线距离
单位：
m 表示单位为米[默认值]。
km 表示单位为千米。
mi 表示单位为英里。
ft 表示单位为英尺。
如果用户没有显式地指定单位参数，那么 GEODIST 默认使用米作为单位

4、georadius
（1）格式
georadius<key>< longitude><latitude>radius  m|km|ft|mi   以给定的经纬度为中心，找出某一半径内的元素

# Redis 事务_锁
## 概述
Redis事务是一个单独的隔离操作：事务中的所有命令都会序列化、按顺序地执行。
事务在执行的过程中，不会被其他客户端发送来的命令请求所打断。
Redis事务的主要作用就是串联多个命令防止别的命令插队

**Redis事务的三特性**
单独的隔离操作
事务中的所有命令都会序列化、按顺序地执行。事务在执行的过程中，不会被其他客户端发送来的命令请求所打断。
没有隔离级别的概念
队列中的命令没有提交之前都不会实际被执行，因为事务提交前任何指令都不会被实际执行
不保证原子性
事务中如果有一条命令执行失败，其后的命令仍然会被执行，没有回滚

## 事务相关命令
### Multi、Exec、discard
从输入Multi命令开始，输入的命令都会依次进入命令队列中，但不会执行，直到输入Exec后，Redis会将之前的命令队列中的命令依次执行
组队的过程中可以通过discard来放弃组队。![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656641333699-2ff711c8-f929-45ad-9bae-9b6a2f3fd3d7.png#clientId=ud81ac10a-ed41-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=309&id=u0cdbf3c9&margin=%5Bobject%20Object%5D&name=image.png&originHeight=386&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=69354&status=error&style=none&taskId=u21016155-0e09-478f-b262-b4ec2142672&title=&width=692)
组队中某个命令出现了报告错误，执行时整个的所有队列都会被取消。
如果执行阶段某个命令报出了错误，则只有报错的命令不会被执行，而其他的命令都会执行，不会回滚。

### watch  、unwatch
在执行multi之前，先执行watch key1 [key2],可以监视一个(或多个) key ，如果在事务**执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断。**

unwatch 取消WATCH命令对所有key 的监视。
如果在执行WATCH命令之后，EXEC命令或DISCARD命令先被执行了的话，那么就不需要再执行UNWATCH了。
## 锁的类型
### 悲观锁
**悲观锁(Pessimistic Lock)**, 顾名思义，就是很悲观，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁。**传统的关系型数据库里边就用到了很多这种锁机制**，比如**行锁**，**表锁**等，**读锁**，**写锁**等，都是在做操作之前先上锁。
### 乐观锁
**乐观锁(Optimistic Lock), **顾名思义，就是很乐观，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，可以使用版本号等机制。**乐观锁适用于多读的应用类型，这样可以提高吞吐量**。Redis就是利用这种check-and-set机制实现事务的。![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656641482618-4a2ad243-a80a-44ce-b54e-c720f88033d6.png#clientId=ud81ac10a-ed41-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=221&id=u8fb36eb8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=276&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=165288&status=error&style=none&taskId=u5c159a5e-137c-4a55-9ace-8f4f24f7744&title=&width=694.4)

# Redis 事务_秒杀案例
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656641897491-d201947e-3667-440b-9598-c99ddbcf8f02.png#clientId=ud81ac10a-ed41-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=214&id=u32f962cf&margin=%5Bobject%20Object%5D&name=image.png&originHeight=267&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=122584&status=error&style=none&taskId=u17d11da3-86a4-42d4-82d6-acf67c199d2&title=&width=694.4)
使用ab工具模拟并发秒杀，CentOS6 默认安装，CentOS7需要手动安装
**yum install httpd-tools**
**出现超卖问题**
## 使用乐观锁

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656642061550-68822551-cbbf-459b-9be8-370da71733d4.png#clientId=ud81ac10a-ed41-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=330&id=uc2b8b16d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=413&originWidth=875&originalType=binary&ratio=1&rotation=0&showTitle=false&size=238534&status=error&style=none&taskId=u1beded98-a8f3-4eff-94d6-246aa80f80d&title=&width=700)
```java
//增加乐观锁
jedis.watch(qtkey);

//3.判断库存
String qtkeystr = jedis.get(qtkey);
if(qtkeystr==null || "".equals(qtkeystr.trim())) {
    System.out.println("未初始化库存");
    jedis.close();
    return false ;
}

int qt = Integer.parseInt(qtkeystr);
if(qt<=0) {
    System.err.println("已经秒光");
    jedis.close();
    return false;
}

//增加事务
Transaction multi = jedis.multi();

//4.减少库存
//jedis.decr(qtkey);
multi.decr(qtkey);

//5.加人
//jedis.sadd(usrkey, uid);
multi.sadd(usrkey, uid);

//执行事务
List<Object> list = multi.exec();

//判断事务提交是否失败
if(list==null || list.size()==0) {
    System.out.println("秒杀失败");
    jedis.close();
    return false;
}
System.err.println("秒杀成功");
jedis.close();

```
增加并发测试次数可能出现已经秒光，可是还有库存。
原因，就是乐观锁导致很多请求都失败。先点的没秒到，后点的可能秒到了。
## 使用连接池
节省每次连接redis服务带来的消耗，把连接好的实例反复利用。
连接池参数：
MaxTotal：控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；如果赋值为-1，则表示不限制；如果pool已经分配了MaxTotal个jedis实例，则此时pool的状态为exhausted。
maxIdle：控制一个pool最多有多少个状态为idle(空闲)的jedis实例；
MaxWaitMillis：表示当borrow一个jedis实例时，最大的等待毫秒数，如果超过等待时间，则直接抛JedisConnectionException；
testOnBorrow：获得一个jedis实例的时候是否检查连接可用性（ping()）；如果为true，则得到的jedis实例均是可用的
## LUA脚本解决库存遗留问题
Lua 是一个小巧的[脚本语言](http://baike.baidu.com/item/%E8%84%9A%E6%9C%AC%E8%AF%AD%E8%A8%80)，Lua脚本可以很容易的被C/C++ 代码调用，也可以反过来调用C/C++的函数，Lua并没有提供强大的库，一个完整的Lua解释器不过200k，所以Lua不适合作为开发独立应用程序的语言，而是作为嵌入式脚本语言。
很多应用程序、游戏使用LUA作为自己的嵌入式脚本语言，以此来实现可配置性、可扩展性。
将复杂的或者多步的redis操作，写为一个脚本，一次提交给redis执行，减少反复连接redis的次数。提升性能。
LUA脚本是类似redis事务，有一定的原子性，不会被其他命令插队，可以完成一些redis事务性的操作。
利用lua脚本淘汰用户，解决超卖问题。
redis 2.6版本以后，通过lua脚本解决**争抢问题**，实际上是**redis 利用其单线程的特性，用任务队列的方式解决多任务并发问题**。
# Redis 持久化
Redis提供了两种方式RDB和AOF
## RDB（Redis DataBase）
### 概述
在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话讲的Snapshot快照，它恢复时是将快照文件直接读到内存
Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。
整个过程中，主进程是不进行任何IO操作的，这就确保了极高的性能如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那RDB方式要比AOF方式更加的高效。
**RDB的缺点是最后一次持久化后的数据可能丢失**
优势
l  适合大规模的数据恢复
l  节省磁盘空间
l  恢复速度快
劣势
l  Fork的时候，内存中的数据被克隆了一份，大致2倍的膨胀性需要考虑
l  虽然Redis在fork时使用了**写时拷贝技术**,但是如果数据庞大时还是比较消耗性能。
l  在备份周期在一定间隔时间做一次备份，所以如果Redis意外down掉的话，就会丢失最后一次快照后的所有修改。

### 配置文件相关属性
在redis.conf中配置文件名称，默认为dump.rdb
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656851741827-bfc40268-0bc2-4820-a60d-0b9da11c00ed.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=105&id=ucaf1bdc0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=131&originWidth=739&originalType=binary&ratio=1&rotation=0&showTitle=false&size=22635&status=error&style=none&taskId=u44b371b2-eef5-4846-afa5-8d4c6e8c554&title=&width=591.2)
rdb文件的保存路径，也可以修改。默认为Redis启动时命令行所在的目录下：dir "/myredis/"
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656851791462-31fbed9b-7b16-41d0-b5b1-616830816798.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=192&id=ucd9fc48b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=240&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=49840&status=error&style=none&taskId=u0f8fed3b-d81b-4f3a-a6df-6bb34212b3f&title=&width=692)
RDB的触发方式
1.save 秒钟 写操作次数
**默认是1分钟内改了1万次，或5分钟内改了10次，或15分钟内改了1次。**
禁用：不设置save指令，或者给save传入空字符串
save时只管保存，占用主线程。不建议。
**2.bgsave：Redis会在后台异步进行快照操作，快照同时还可以响应客户端请求。**![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656851901483-5606f552-869b-44cd-b16e-e5f16750e357.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=218&id=u9dbcb326&margin=%5Bobject%20Object%5D&name=image.png&originHeight=272&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=64457&status=error&style=none&taskId=ue1f8ea73-0a19-4402-a338-1dc367c7f1a&title=&width=692)
可以通过lastsave 命令获取最后一次成功执行快照的时间

当Redis无法写入磁盘的话，直接关掉Redis的写操作。推荐yes.
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656852154842-7d6bf0b9-1697-4300-aceb-e09d60fd34ac.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=128&id=u6be89841&margin=%5Bobject%20Object%5D&name=image.png&originHeight=160&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=42400&status=error&style=none&taskId=u34d8fd07-49d0-4769-9722-8a026a859d5&title=&width=692)
对于存储到磁盘中的快照，可以设置是否进行压缩存储。如果是的话，redis会采用LZF算法进行压缩
如果你不想消耗CPU来进行压缩的话，可以设置为关闭此功能。推荐yes.
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656852176201-e128ded8-6a04-499f-be04-370ebd62df0f.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=123&id=u98cd5206&margin=%5Bobject%20Object%5D&name=image.png&originHeight=154&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=45274&status=error&style=none&taskId=u37c6f9bc-6eae-447f-9571-4ea1a8c340d&title=&width=692)
**检查完整性：**在存储快照后，还可以让redis使用CRC64算法来进行数据校验，但是这样做会增加大约10%的性能消耗，如果希望获取到最大的性能提升，可以关闭此功能，推荐yes.
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656852206171-382b4a37-5906-40cd-a24b-057fee59fdbe.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=101&id=u40ef72ea&margin=%5Bobject%20Object%5D&name=image.png&originHeight=126&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=25649&status=error&style=none&taskId=ub4d13727-e80d-4b6d-a8d5-ed3cf0d5a51&title=&width=692)
### 使用
**恢复数据**
先通过config get dir  查询rdb文件的目录
关闭Redis
把备份的文件拷贝到工作目录下
启动Redis, 备份数据会直接加载
**停止**
输入命令，save后为空字符串：config set save ""

## AOF（AppendOnly File）
### 概述
以**日志**的形式来记录每个写操作（增量保存），将Redis执行过的所有写指令记录下来(**读操作不记录**)，**只许追加文件但不可以改写文件**，redis启动之初会读取该文件重新构建数据。

优势

- 备份机制更稳健，丢失数据概率更低。
- 可读的日志文本，通过操作AOF文件，可以处理误操作。

劣势

- 比起RDB占用更多的磁盘空间。
- 恢复备份速度要慢。
- 每次读写都同步的话，有一定的性能压力。
- 存在个别Bug，造成不能恢复。
### 配置文件相关属性
默认为appendonly no，使用时改为yes
可以在redis.conf中配置文件名称，默认为appendonly.aof
AOF文件的保存路径，同RDB的路径一致。
AOF和RDB同时开启，系统默认取AOF的数据（数据不会存在丢失）

**AOF同步频率设置**
appendfsync always
始终同步，每次Redis的写入都会立刻记入日志；性能较差但数据完整性比较好
appendfsync everysec
每秒同步，每秒记入日志一次，如果宕机，本秒的数据可能丢失。
appendfsync no
redis不主动进行同步，把同步时机交给操作系统
### 使用
AOF的备份机制和性能虽然和RDB不同, 但是备份和恢复的操作同RDB一样，都是拷贝备份文件，需要恢复时再拷贝到Redis工作目录下，启动系统即加载

- 如遇到**AOF文件损坏**，通过/usr/local/bin/**redis-check-aof--fix appendonly.aof**进行恢复（没有损坏就不需要）
- 将有数据的aof文件复制一份保存到对应目录(查看目录：config get dir)
- 恢复：重启redis然后重新加载
### Rewrite
AOF采用文件追加方式，文件会越来越大为避免出现此种情况，新增了重写机制, 当AOF文件的大小超过所设定的阈值时，Redis就会启动AOF文件的内容压缩，只保留可以恢复数据的最小指令集.可以使用命令bgrewriteaof
**重写原理**
AOF文件持续增长而过大时，会fork出一条新进程来将文件重写(也是先写临时文件最后再rename)
**no-appendfsync-on-rewrite：**
如果 no-appendfsync-on-rewrite=yes ,不写入aof文件只写入缓存，用户请求不会阻塞，但是在这段时间如果宕机会丢失这段时间的缓存数据。（降低数据安全性，提高性能）
如果 no-appendfsync-on-rewrite=no,  还是会把数据往磁盘里刷，但是遇到重写操作，可能会发生阻塞。（数据安全，但是性能降低）
**触发机制**
Redis会记录上次重写时的AOF大小，默认配置是当AOF文件大小是上次rewrite后大小的一倍且文件大于64M时触发
重写虽然可以节约大量磁盘空间，减少恢复时间。但是每次重写还是有一定的负担的，因此设定Redis要满足一定条件才会进行重写。
auto-aof-rewrite-percentage：设置重写的基准值，文件达到100%时开始重写（文件是原来重写后文件的2倍时触发）
auto-aof-rewrite-min-size：设置重写的基准值，最小文件64MB。达到这个值开始重写。
**重写流程**
（1）bgrewriteaof触发重写，判断是否当前有bgsave或bgrewriteaof在运行，如果有，则等待该命令结束后再继续执行。
（2）主进程fork出子进程执行重写操作，保证主进程不会阻塞。
（3）子进程遍历redis内存中数据到临时文件，客户端的写请求同时写入aof_buf缓冲区和aof_rewrite_buf重写缓冲区保证AOF文件完整
（4）子进程写完新的AOF文件后，向主进程发信号，父进程更新统计信息。主进程把aof_rewrite_buf中的数据写入到新的AOF文件。
（5）使用新的AOF文件覆盖旧的AOF文件，完成AOF重写。
## 使用建议和总结
官方推荐两个都启用。
如果对数据不敏感，可以选单独用RDB。
不建议单独用 AOF，因为可能会出现Bug。
如果只是做纯内存缓存，可以都不用

RDB的数据不实时，同时使用两者时服务器重启也只会找AOF文件。那要不要只使用AOF呢？
建议不要，因为RDB更适合用于备份数据库(AOF在不断变化不好备份)，快速重启，而且不会有AOF可能潜在的bug，留着作为一个万一的手段。

性能建议
因为RDB文件只用作后备用途，建议只在Slave上持久化RDB文件，而且只要15分钟备份一次就够了，只保留save 900 1这条规则。

代价：一是带来了持续的IO，二是AOF rewrite的最后将rewrite过程中产生的新数据写到新文件造成的阻塞几乎是不可避免的。
只要硬盘许可，应该尽量减少AOF rewrite的频率，AOF重写的基础大小默认值64M太小了，可以设到5G以上。
默认超过原大小100%大小时重写可以改到适当的数值。
# Redis主从复制
## 概述
主机数据更新后根据配置和策略，自动同步到备机的master/slaver机制，**Master以写为主，Slave以读为主**

读写分离，性能扩展
容灾快速恢复
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656855319641-e5f7585e-3626-41ee-9714-9cb14b8c52e7.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=258&id=u311d0bb6&margin=%5Bobject%20Object%5D&name=image.png&originHeight=322&originWidth=506&originalType=binary&ratio=1&rotation=0&showTitle=false&size=124347&status=error&style=none&taskId=u1cc98e59-9b19-4bd7-b3e9-367fe99535d&title=&width=404.8)

当一个master宕机后，后面的slave可以立刻升为master，其后面的slave不用做任何修改。
slaveof  no one  将从机变为主机

上一个Slave可以是下一个slave的Master，Slave同样可以接收其他slaves的连接和同步请求，那么该Slave作为了链条中下一个的master, 可以有效减轻master的写压力,去中心化降低风险。
用 slaveof  <ip><port>
中途变更转向:会清除之前的数据，重新建立拷贝最新的
风险是一旦某个slave宕机，后面的slave都没法备份
主机挂了，从机还是从机，无法写数据了
## 使用
拷贝多个redis.conf文件 include(写绝对路径)
开启daemonize yes
修改Pid文件名字pidfile
修改指定端口port
修改Log文件名字
修改dump.rdb名字dbfilename
Appendonly 关掉或者换名字
## 案例
6379为主机，6380和6381为从机
**1.新建redis6381.conf和redis6380.conf填写以下内容**
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656855449488-7426425f-b43d-4e27-a206-60fb06b11fec.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=98&id=ud605b225&margin=%5Bobject%20Object%5D&name=image.png&originHeight=123&originWidth=422&originalType=binary&ratio=1&rotation=0&showTitle=false&size=64871&status=error&style=none&taskId=ube36ed17-3b7f-4c8e-8897-b7432959e56&title=&width=337.6)
slave-priority 10
设置从机的优先级，值越小，优先级越高，用于选举主机时使用。默认100
**2.启动三台redis服务器**
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656855486727-11a13624-a238-4003-aa16-cc57718383ac.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=85&id=u04678301&margin=%5Bobject%20Object%5D&name=image.png&originHeight=106&originWidth=669&originalType=binary&ratio=1&rotation=0&showTitle=false&size=102237&status=error&style=none&taskId=u69ecfcb5-67ba-419f-9b6a-fbed7fe6c54&title=&width=535.2)
3.**查看系统进程，看看三台服务器是否启动**![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656855500530-f47a15be-c647-4055-a562-ecdab88baec9.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=121&id=u885ed17d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=151&originWidth=868&originalType=binary&ratio=1&rotation=0&showTitle=false&size=158913&status=error&style=none&taskId=u3cbf2cc4-19ed-4791-9eef-6bbcf05d78f&title=&width=694.4)
**4.查看三台主机运行情况**
info replication  打印主从复制的相关信息
**5**.**配从不配主**
slaveof  <ip><port>  成为某个实例的从服务器
在6380和6381上执行: slaveof 127.0.0.1 6379
6.使用
在主机上写，在从机上可以读取数据
主机挂掉，重启就行，一切如初
从机重启需重设：slaveof 127.0.0.1 6379
可以将配置增加到文件中。永久生效。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656855653063-a86473f3-437d-4c9d-8a2f-41960ed9df5a.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=316&id=u52c4d64e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=395&originWidth=858&originalType=binary&ratio=1&rotation=0&showTitle=false&size=284289&status=error&style=none&taskId=ufd2ec58f-9589-444e-8bcc-745e8e8d78b&title=&width=686.4)
## 哨兵模式
能够后台监控主机是否故障，如果故障了根据投票数自动将从库转换为主库

**使用案例**
**1.调整为一主二仆模式，6379带着6380、6381**
**2.自定义的/myredis目录下新建sentinel.conf文件，名字绝不能错**
**3.配置哨兵,填写内容**
sentinel monitor mymaster 127.0.0.1 6379 1
其中mymaster为监控对象起的服务器名称，1 为至少有多少个哨兵同意迁移的数量。
**4.启动哨兵**
/usr/local/bin
redis做压测可以用自带的redis-benchmark工具
执行redis-sentinel  /myredis/sentinel.conf 
**5.当主机挂掉，从机选举中产生新的主机**
(大概10秒左右可以看到哨兵窗口日志，切换了新的主机)
哪个从机会被选举为主机呢？根据优先级别：slave-priority 
原主机重启后会变为从机。

由于所有的写操作都是先在Master上操作，然后同步更新到Slave上，所以从Master同步到Slave机器有一定的延迟，当系统很繁忙的时候，延迟问题会更加严重，Slave机器数量的增加也会使这个问题更加严重。
# Redis集群
## 概述
容量不够，redis如何进行扩容？
并发写操作， redis如何分摊？
另外，主从模式，薪火相传模式，主机宕机，导致ip地址发生变化，应用程序中配置需要修改对应的主机地址、端口等信息。
之前通过代理主机来解决，但是redis3.0中提供了解决方案。就是无中心化集群配置。
Redis 集群实现了对Redis的水平扩容，即启动N个redis节点，将整个数据库分布存储在这N个节点中，每个节点存储总数据的1/N。
Redis 集群通过分区（partition）来提供一定程度的可用性（availability）：即使集群中有一部分节点失效或者无法进行通讯，集群也可以继续处理命令请求。

主节点下线，从节点变成主节点，原来的主节点恢复后，变成从机
如果某一节点的主从都挂掉，而cluster-require-full-coverage 为yes ，那么，整个集群都挂掉
cluster-require-full-coverage 为no ，那么，该插槽数据全都不能使用，也无法存储。

优点
实现扩容
分摊压力
无中心配置相对简单
缺点
多键的Redis事务是不被支持的。lua脚本不被支持
由于集群方案出现较晚，很多公司已经采用了其他的集群方案，而代理或者客户端分片的方案想要迁移至redis cluster，需要整体迁移而不是逐步过渡，复杂度较大。
## 实例
**1.删除持久化数据**
将rdb,aof文件都删除掉。
**2.制作6个实例**
6379,6380,6381,6389,6390,6391
**3.给6个实例配置基本信息**
开启daemonize yes
Pid文件名字
指定端口
Log文件名字
dump.rdb名字
Appendonly 关掉或者换名字
4.redis cluster 配置修改
cluster-enabled yes    打开集群模式
cluster-config-file nodes-6379.conf  设定节点配置文件名
cluster-node-timeout 15000   设定节点失联时间，超过该时间（毫秒），集群自动进行主从切换。
```java
include /home/bigdata/redis.conf
port 6379
pidfile "/var/run/redis_6379.pid"
dbfilename "dump6379.rdb"
dir "/home/bigdata/redis_cluster"
logfile "/home/bigdata/redis_cluster/redis_err_6379.log"
cluster-enabled yes
cluster-config-file nodes-6379.conf
cluster-node-timeout 15000
```
**5.启动6个redis服务**
**6.将6个节点合成一个集群**
组合之前，请确保所有redis实例启动后，nodes-xxxx.conf文件都生成正常。
cd  /opt/redis-6.2.1/src
redis-cli --cluster create --cluster-replicas 1 192.168.11.101:6379 192.168.11.101:6380 192.168.11.101:6381 192.168.11.101:6389 192.168.11.101:6390 192.168.11.101:6391
此处不要用127.0.0.1，请用真实IP地址
--replicas 1 采用最简单的方式配置集群，一台主机，一台从机，正好三组。
**7.登录**
-c 采用集群策略连接，设置数据会自动切换到相应的写主机
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656858220267-b0400191-a76c-41ae-ba3b-6c2d9b786965.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=170&id=u2e91ccc4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=213&originWidth=852&originalType=binary&ratio=1&rotation=0&showTitle=false&size=140532&status=error&style=none&taskId=ue58de520-fc27-4648-8105-9e902e10c0c&title=&width=681.6)
**8.查看信息**
通过 cluster nodes 命令查看集群信息
**9.redis cluster 如何分配这六个节点**
一个集群至少要有三个主节点。
选项--cluster-replicas 1 表示我们希望为集群中的每个主节点创建一个从节点。
分配原则尽量保证每个主数据库运行在不同的IP地址，每个从库和主库不在一个IP地址上。

# Redis应用问题
## 缓存穿透
key对应的数据在数据源并不存在，每次针对此key的请求从缓存获取不到，请求都会压到数据源，从而可能压垮数据源。比如用一个不存在的用户id获取用户信息，不论缓存还是数据库都没有，若黑客利用此漏洞进行攻击可能压垮数据库

解决方案：
（1）**对空值缓存：**如果一个查询返回的数据为空（不管是数据是否不存在），我们仍然把这个空结果（null）进行缓存，设置空结果的过期时间会很短，最长不超过五分钟
（2）**设置可访问的名单（白名单）：**
使用bitmaps类型定义一个可以访问的名单，名单id作为bitmaps的偏移量，每次访问和bitmap里面的id进行比较，如果访问id不在bitmaps里面，进行拦截，不允许访问。
（3）**采用布隆过滤器**：(布隆过滤器（Bloom Filter）是1970年由布隆提出的。它实际上是一个很长的二进制向量(位图)和一系列随机映射函数（哈希函数）。
布隆过滤器可以用于检索一个元素是否在一个集合中。它的优点是空间效率和查询时间都远远超过一般的算法，缺点是有一定的误识别率和删除困难。)
将所有可能存在的数据哈希到一个足够大的bitmaps中，一个一定不存在的数据会被这个bitmaps拦截掉，从而避免了对底层存储系统的查询压力。
**（4）进行实时监控：**当发现Redis的命中率开始急速降低，需要排查访问对象和访问的数据，和运维人员配合，可以设置黑名单限制服务
## 缓存击穿
key对应的数据存在，但在redis中刚好过期，此时若有大量并发请求过来，这些请求发现缓存过期一般都会从后端DB加载数据并回设到缓存，这个时候大并发的请求可能会瞬间把后端DB压垮。

解决方案
**（1）预先设置热门数据：**在redis高峰访问之前，把一些热门数据提前存入到redis里面，加大这些热门数据key的时长
**（2）实时调整：**现场监控哪些数据热门，实时调整key的过期时长
**（3）使用锁：**
先使用缓存工具的某些带成功操作返回值的操作（比如Redis的SETNX）去set一个mutex key
当操作返回成功时，再进行load db的操作，并回设缓存,最后删除mutex key；
当操作返回失败，证明有线程在load db，当前线程睡眠一段时间再重试整个get缓存的方法。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656860086030-0b800e94-0db3-4c5a-8b1c-702dd7cd99f6.png#clientId=uf12365ca-dd01-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=514&id=u32f5c255&margin=%5Bobject%20Object%5D&name=image.png&originHeight=642&originWidth=700&originalType=binary&ratio=1&rotation=0&showTitle=false&size=132590&status=error&style=none&taskId=ud1ace681-1a50-4ed4-8864-096047be3e6&title=&width=560)
## 缓存雪崩
key对应的数据存在，但在redis中过期，此时若有大量并发请求过来，这些请求发现缓存过期一般都会从后端DB加载数据并回设到缓存，这个时候大并发的请求可能会瞬间把后端DB压垮。
缓存雪崩与缓存击穿的区别在于这里针对很多key缓存，前者则是某一个key访问

**解决方案**
**（1）   构建多级缓存架构：**nginx缓存 + redis缓存 +其他缓存（ehcache等）
**（2）   使用锁或队列：**
用加锁或者队列的方式保证来保证不会有大量的线程对数据库一次性进行读写，从而避免失效时大量的并发请求落到底层存储系统上。不适用高并发情况
**（3）   设置过期标志更新缓存：**
记录缓存数据是否过期（设置提前量），如果过期会触发通知另外的线程在后台去更新实际key的缓存。
**（4）   将缓存失效时间分散开：**
比如我们可以在原有的失效时间基础上增加一个随机值，比如1-5分钟随机，这样每一个缓存的过期时间的重复率就会降低，就很难引发集体失效的事件。
## 分布式锁
使用setnx自带锁的机制

分布式锁主流的实现方案：
1. 基于数据库实现分布式锁
2. 基于缓存（Redis等）
3. 基于Zookeeper
每一种分布式锁解决方案都有各自的优缺点：
1. 性能：redis最高
2. 可靠性：zookeeper最高

**使用**
```java
@GetMapping("testLock")
public void testLock(){
    //1获取锁，setnx
    Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", "111");
    //2获取锁成功、查询num的值
    if(lock){
        Object value = redisTemplate.opsForValue().get("num");
        //2.1判断num为空return
        if(StringUtils.isEmpty(value)){
            return;
        }
        //2.2有值就转成成int
        int num = Integer.parseInt(value+"");
        //2.3把redis的num加1
        redisTemplate.opsForValue().set("num", ++num);
        //2.4释放锁，del
        redisTemplate.delete("lock");

    }else{
        //3获取锁失败、每隔0.1秒再获取
        try {
            Thread.sleep(100);
            testLock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

```

问题：获取到锁后，业务逻辑异常，导致锁无法释放
优化，设置锁的过期时间，两种方式
1. 首先想到通过expire设置过期时间（缺乏原子性：如果在setnx和expire之间出现异常，锁也无法释放）
2. 在set时指定过期时间（推荐）
```java
 Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", "111"，3，TimeUnit.SECONDS);
```

问题：场景：如果业务逻辑的执行时间是7s。执行流程如下
1. index1业务逻辑没执行完，3秒后锁被自动释放，并继续执行。
2. index2获取到锁，执行业务逻辑，3秒后锁被自动释放，并继续执行。
3. index3获取到锁，执行业务逻辑
4. index1业务逻辑执行完成，开始调用del释放锁，这时释放的是index3的锁，导致index3的业务只执行1s就被别人释放。
最终等于没锁的情况。
优化：setnx获取锁时，设置一个指定的唯一值，释放前判断是否是自己的锁。（UUID防误删）
```java


String uuid = UUID.randomUUID().toString();
Boolean lock = this.redisTemplete.opsForValue().setIfAbsent("lock",uuid,2,TimeUnit.SECOND);












if(uuid.equals((String)redisTemplete.opsForValue().get("lock"))){
    this.redisTemplete.delete("lock");
}


```
问题：删除操作缺乏原子性
# Redis6.0新功能
## ACL
Redis ACL是Access Control List（访问控制列表）的缩写，该功能允许根据可以执行的命令和可以访问的键来限制某些连接。
在Redis 5版本之前，Redis 安全规则只有密码控制还有通过rename 来调整高危命令比如 flushdb ， KEYS* ， shutdown 等。Redis 6 则提供ACL的功能对用户进行更细粒度的权限控制：
（1）接入权限:用户名和密码
（2）可以执行的命令
（3）可以操作的 KEY
参考官网：https://redis.io/topics/acl

**命令**
acl list   展现用户权限列表
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656987297751-e2b240a8-f298-4f13-9e2b-f2dbc1197c78.png#clientId=u8cd58656-096c-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=224&id=u3c700de4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=280&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=121770&status=error&style=none&taskId=u41ac7aae-23d7-48ef-8fdb-3c8c6e71913&title=&width=692)
acl cat  [具体指令]  看添加权限指令类别，加参数类型名可以查看类型下具体命令
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656987706863-8bfbdc9b-206f-4e84-a1e8-5f51486d7d32.png#clientId=u8cd58656-096c-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=420&id=u748af24b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=525&originWidth=402&originalType=binary&ratio=1&rotation=0&showTitle=false&size=101470&status=error&style=none&taskId=uac370b43-41c8-4556-866b-125e5fac7f1&title=&width=321.6)
acl whoami   命令查看当前用户
acl setuser [username]      创建和编辑用户ACL
（1）ACL规则
下面是有效ACL规则的列表。某些规则只是用于激活或删除标志，或对用户ACL执行给定更改的单个单词。其他规则是字符前缀，它们与命令或类别名称、键模式等连接在一起。

| ACL规则 |  |  |
| --- | --- | --- |
| 类型 | 参数 | 说明 |
| 启动和禁用用户 | **on** | 激活某用户账号 |
|  | **off** | 禁用某用户账号。注意，已验证的连接仍然可以工作。如果默认用户被标记为off，则新连接将在未进行身份验证的情况下启动，并要求用户使用AUTH选项发送AUTH或HELLO，以便以某种方式进行身份验证。 |
| 权限的添加删除 | **+<command>** | 将指令添加到用户可以调用的指令列表中 |
|  | **-<command>** | 从用户可执行指令列表移除指令 |
|  | **+@<category>** | 添加该类别中用户要调用的所有指令，有效类别为@admin、@set、@sortedset…等，通过调用ACL CAT命令查看完整列表。特殊类别@all表示所有命令，包括当前存在于服务器中的命令，以及将来将通过模块加载的命令。 |
|  | **-@<category>** | 从用户可调用指令中移除类别 |
|  | **allcommands** | +@all的别名 |
|  | **nocommand** | -@all的别名 |
| 可操作键的添加或删除 | **~<pattern>** | 添加可作为用户可操作的键的模式。例如~*允许所有的键 |

（2）通过命令创建新用户默认权限
acl setuser user1
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656987946653-fdd6d65e-0d0e-4bf8-bf80-31d600272e07.png#clientId=u8cd58656-096c-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=102&id=u2cf09dd8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=128&originWidth=461&originalType=binary&ratio=1&rotation=0&showTitle=false&size=41712&status=error&style=none&taskId=u43f9e3dd-fc96-4271-93d1-36b754104f6&title=&width=368.8)
在上面的示例中，我根本没有指定任何规则。如果用户不存在，这将使用just created的默认属性来创建用户。如果用户已经存在，则上面的命令将不执行任何操作。

（3）设置有用户名、密码、ACL权限、并启用的用户
acl setuser user2 on >password ~cached:* +get
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656987951428-25b21372-262d-4042-8ea6-799b022281b0.png#clientId=u8cd58656-096c-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=131&id=u19590efd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=164&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=74168&status=error&style=none&taskId=u2c340bad-fd41-4a4e-a582-9badaec8a35&title=&width=692)
(4)切换用户，验证权限
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656987955881-6b1b761b-4e35-4b89-8475-0e3413316689.png#clientId=u8cd58656-096c-4&crop=0&crop=0&crop=1&crop=1&errorMessage=unknown%20error&from=paste&height=249&id=uc873ea01&margin=%5Bobject%20Object%5D&name=image.png&originHeight=311&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&size=120518&status=error&style=none&taskId=ud156bc4c-58fe-4b7b-a86c-e31978d3ea0&title=&width=692)
## IO多线程
IO多线程其实指**客户端交互部分**的**网络IO**交互处理模块**多线程**，而非**执行命令多线程**。Redis6执行命令依然是单线程。
Redis 6 加入多线程,但跟 Memcached 这种从 IO处理到数据访问多线程的实现模式有些差异。Redis 的多线程部分只是用来处理网络数据的读写和协议解析，执行命令仍然是单线程。之所以这么设计是不想因为多线程而变得复杂，需要去控制 key、lua、事务，LPUSH/LPOP 等等的并发问题。整体的设计大体如下:
另外，多线程IO默认也是不开启的，需要再配置文件中配置
io-threads-do-reads  yes 
io-threads 4
