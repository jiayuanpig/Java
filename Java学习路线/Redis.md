# Redis



## Nosql

### Nosql背景

为什么要用nosql？

数据库太大--索引太大--缓存+多服务器+垂直拆分--分库分表+水平拆分

什么是nosql？





### 阿里巴巴架构演进



### Nosql四大分类

K/V键值对





## Redis

redis使用场景

| 数据类型 | 使用场景                                                     |
| -------- | ------------------------------------------------------------ |
| String   | 什么时候封锁一个IP地址                                       |
| Hash     | 存储用户信息（不建议使用String类型），格式为Hset（key，field，value） |
| List     | 最新消息的排行；使用list模拟消息队列：list的push命令将任务存储到集合中，使用pop命令取出任务 |
| Set      | 可以自动排重                                                 |
| Zset     | 以某一个条件作为权重进行排序                                 |







redis持久化

RDB、AOF







### Redis学习

> ##### 入门介绍

- ##### `入门概述`

> **1. redis是什么？**
> Redis:`REmote DIctionary Server`(远程字典服务器)
> 是完全开源免费的，用C语言编写的，遵守BSD协议，是一个高性能的(key/value)分布式内存数据库，基于内存运行并支持持久化的NoSQL数据库，是当前最热门的NoSql数据库之一,也被人们称为数据结构服务器。
> Redis 与其他 key - value 缓存产品有以下三个特点：
>
> - Redis支持数据的持久化，可以将内存中的数据保持在磁盘中，重启的时候可以再次加载进行使用；
> - Redis不仅仅支持简单的key-value类型的数据，同时还提供list，set，zset，hash等数据结构的存储；
> - Redis支持数据的备份，即master-slave模式的数据备份；

> **2. redis能干什么？**
>
> - 内存存储和持久化：redis支持异步将内存中的数据写到硬盘上，同时不影响继续服务；
> - 取最新N个数据的操作，如：可以将最新的10条评论的ID放在Redis的List集合里面；
> - 模拟类似于HttpSession这种需要设定过期时间的功能；
> - 发布、订阅消息系统；
> - 定时器、计数器；

> **3. redis官网？**
>
> - http://redis.io/
> - http://www.redis.cn/

> **4. 从哪些方面去学习redis？**
>
> - 数据类型、基本操作和配置；
> - 持久化和复制，RDB/AOF；
> - 事务的控制；
> - 复制；

- ##### `Redis的安装及启动关闭`

> 由于企业里面做Redis开发，99%都是Linux版的运用和安装，几乎不会涉及到Windows版，所以Windows版不作为讲解;

> **1. Linux（CentOS 6.9）下安装redis（3.0.4）**
>
> - 下载获得redis-3.0.4.tar.gz后将它放入我们的Linux目录/opt；
> - /opt目录下，解压命令:`tar -zxvf redis-3.0.4.tar.gz`；
> - 解压完成后出现文件夹：redis-3.0.4；
> - 进入目录:cd redis-3.0.4；
> - 在redis-3.0.4目录下执行`make`命令；
>
> > 运行make命令时出现的错误：
> >
> > - 安装gcc（能上网：`yum install gcc-c++`）；
> > - 再次`make`；
> > - jemalloc/jemalloc.h：没有那个文件或目录（运行`make distclean`之后再`make`）；
>
> - 如果make完成后继续执行make install；

> **2. 查看默认安装目录：usr/local/bin**
>
> - `redis-benchmark`：服务启动起来后执行性能测试工具，可以在自己本子运行，看看自己本子性能如何；
> - `redis-check-aof`：修复有问题的aof文件；
> - `redis-check-dump`：修复有问题的dump.rdb文件；
> - `redis-cli`：客户端，操作入口；
> - `redis-sentinel`：redis集群使用；
> - `redis-server`：Redis服务器启动命令；

> **3. 启动**
>
> - 修改redis.conf文件将里面的`daemonize no` 改成 `yes`，让服务在后台启动；
> - 将默认的`redis.conf`拷贝到自己定义好的一个路径下，比如`/myconf/redis.conf`；
> - 进入`/usr/local/bin`目录下运行`redis-server`，运行拷贝出存放了自定义`myconf`文件目录下的`redis.conf`文件（`redis-server /myconf/redis.conf`）;
> - 在`/usr/local/bin`目录下运行`redis-cli`，启动客户端（`redis-cli -p 6379`）；

> **4. 关闭**
>
> - 单实例关闭：`redis-cli shutdown`；
> - 多实例关闭，指定端口关闭：`redis-cli -p 6379 shutdown`；

- ##### `Redis启动后杂项基础知识`

> **1. 单进程**
>
> - 单进程模型来处理客户端的请求。对读写等事件的响应是通过对epoll函数的包装来做到的。Redis的实际处理速度完全依靠主进程的执行效率；
> - epoll是Linux内核为处理大批量文件描述符而作了改进的epoll，是Linux下多路复用IO接口select/poll的增强版本，它能显著提高程序在大量并发连接中只有少量活跃的情况下的系统CPU利用率；

> **2. redis数据库的一些概念及操作**
>
> - 默认16个数据库，类似数组下表从零开始，初始默认使用零号库；
> - 统一密码管理，16个库都是同样密码，要么都OK要么一个也连接不上，redis默认端口是`6379`；
> - select命令切换数据库：`select 0-15`；
> - `dbsize`：查看当前数据库的key的数量；
> - `flushdb`：清空当前库；
> - `flushall`；通杀全部库；

> ##### Redis数据类型

- ##### `redis的五大数据类型`

> **1. string（字符串）**
>
> - string是redis最基本的类型，你可以理解成与Memcached一模一样的类型，一个key对应一个value；
> - string类型是二进制安全的。意思是redis的string可以包含任何数据。如jpg图片或者序列化的对象 ；
> - string类型是Redis最基本的数据类型，一个redis中字符串value最多可以是512M；

> **2. list（列表）**
> redis 列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素导列表的头部（左边）或者尾部（右边）。它的底层实际是个链表。

> **3. set（集合）**
> redis的set是string类型的无序集合。它是通过HashTable实现的。

> **4. hash（哈希，类似java里的Map）**
>
> - redis的hash 是一个键值对集合；
> - redis hash是一个string类型的field和value的映射表，hash特别适合用于存储对象；
> - 类似Java里面的Map<String,Object>；

> **5. zset(sorted set：有序集合)**
>
> - redis的zset 和 set 一样也是string类型元素的集合,且不允许重复的成员；
> - 不同的是每个元素都会关联一个double类型的分数；
> - redis正是通过分数来为集合中的成员进行从小到大的排序。zset的成员是唯一的,但分数(score)却可以重复；

- ##### `redis常见数据类型操作命令参考网址`

> http://redisdoc.com/

- ##### `redis 键(key) --（常用命令介绍）`

> `keys *`：查看所有key；
> `exists key的名字`：判断某个key是否存在；
> `move key dbID（0-15）`： 当前库就没有了，被移除了；
> `expire key 秒钟`： 为给定的key设置过期时间；
> `ttl key`： 查看还有多少秒过期，-1表示永不过期，-2表示已过期；
> `type key`： 查看你的key是什么类型；

- ##### `redis 字符串(String) --（常用命令介绍）`

> `set`/`get`/`del`/`append`/`strlen`；
> `Incr`/`decr`/`incrby`/`decrby`：一定要是数字才能进行加减；
> `getrange`/`setrange`：
>
> - `getrange`：获取指定区间范围内的值，类似between and的关系从零到负一表示全部；
> - `setrange`：设置指定区间范围内的值，格式是`setrange key 位置值 具体值`；
>
> ```
> setex(set with expire) 键 秒值 值`/`setnx(set if not exist) 键
> ```
>
> - setex：设置带过期时间的key，动态设置 : `setex 键 秒值 真实值`
> - setnx：只有在 key 不存在时设置 key 的值：`setnx 键 值`
>
> ```
> mset/mget/msetnx
> ```
>
> - mset：同时设置一个或多个 key-value 对。
> - mget：获取所有(一个或多个)给定 key 的值。
> - msetnx：同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在（如果存在key，则都不会操作，因为msetnx是原子性型操作）。
>
> `getset`：将给定 key 的值设为 value ，并返回 key 的旧值(old value)。简单一句话，先get然后立即set。

- ##### `redis 列表(List) --（常用命令介绍）`

> `lpush`/`rpush`/`lrange`；
> `lpop`/`rpop`，移除列表key的头/尾元素；
> `lindex`，按照索引下标获得元素(从上到下)（格式：`lindex key index`）；
> `llen`：返回列表 key 的长度（格式：`llen key`）；
> `lerm`：根据参数 count 的值，移除列表中与参数 value 相等的元素（格式： `lerm key count value`）；
>
> - count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
> - count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
> - count = 0 : 移除表中所有与 value 相等的值。
>
> `ltrim` ，截取指定范围的值后再赋值给key（格式：`ltrim key start stop`）；
> `rpoplpush`，移除列表的最后一个元素，并将该元素添加到另一个列表头部并返回（格式：`rpoplpush source—key destination—key`）；
> `lset`，将列表 key 下标为 index 的元素的值设置为 value（格式：`lset key index value`）；
> `linsert`，（格式：linsert key before|after pivot value）将值 value 插入到列表 key 当中，位于值 pivot 之前或之后；
>
> - 当 pivot 不存在于列表 key 时，不执行任何操作。
> - 当 key 不存在时， key 被视为空列表，不执行任何操作。
> - 如果 key 不是列表类型，返回一个错误。
>
> **性能总结**
> 它是一个字符串链表，left、right都可以插入添加；
>
> - 如果键不存在，创建新的链表；
> - 如果键已存在，新增内容；
> - 如果值全移除，对应的键也就消失了。
> - 链表的操作无论是头和尾效率都极高，但假如是对中间元素进行操作，效率就很惨淡了。

- ##### `redis 集合(Set) --（常用命令介绍）`

> `sadd`/`smembers`/`sismember`，格式：
>
> - `sadd key member [member ...]`
> - `smembers key`
> - `sismember key member`
>
> `scard`：获取集合里面的元素个数（格式：`scard key`）；
> `srem`：删除集合中元素（格式：`srem key member [member ...]`）；
> `srandmember`，（格式：`srandmember key [count]`）（不会修改set集合）
>
> - 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素；
> - 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合；
> - 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值；
>
> `spop`，移除并返回集合中的一个随机元素（格式：`spop key`）；
> `smove`，（格式：`smove source destination member`）将 member 元素从 source 集合移动到 destination 集合；
> **数学集合类**
>
> - 差集：`sdiff`（格式：`sdiff key [key ...]`）
> - 交集：`sinter`（格式：`sinter key [key ...]`）
> - 并集：`sunion`（格式：`sunion key [key ...]`）

- ##### `redis 哈希(Hash) --（常用命令介绍）`

> `hset`/`hget`/`hmset`/`hmget`/`hgetall`/`hdel`，格式：
>
> - `hset key field value`：将哈希表 key 中的域 field 的值设为 value ；
> - `hget key field`：返回哈希表 key 中给定域 field 的值；
> - `hmset key field value [field value ...]`：同时将多个 field-value (域-值)对设置到哈希表 key 中；
> - `hmget key field [field ...]`：返回哈希表 key 中，一个或多个给定域的值；
> - `hgetall key`：返回哈希表 key 中，所有的域和值；
> - `hdel key field [field ...]`：删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略；
>
> `hlen`，返回哈希表 key 中域的数量（格式：`hlen key`）；
> `hexists`，查看哈希表 key 中，给定域 field 是否存在（格式：`hexists key field`）；
> `hkeys`/`hvals`，格式：
>
> - `hkeys key`：返回哈希表 key 中的所有域；
> - `hvals key`：返回哈希表 key 中所有域的值；
>
> `hincrby`/`hincrbyfloat`，格式：
>
> - `hincrby key field increment`：为哈希表 key 中的域 field 的值加上增量 increment；
> - `hincrbyfloat key field increment`：为哈希表 key 中的域 field 加上浮点数增量 increment ；
>
> `hsetnx`，将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在（格式：`hsetnx key field value`）

- ##### `redis 有序集合Zset(sorted set) --（常用命令介绍）`

> `zadd`/`zrange`，格式：
>
> - `zadd key score member [[score member] [score member] ...]`：将一个或多个 member 元素及其 score 值加入到有序集 key 当中；
> - `zrange key start stop [WITHSCORES]`：返回有序集 key 中，指定区间内的成员，其中成员的位置按 score 值递增(从小到大)来排列；
>
> `zrangebyscore`：（格式：`zrangebuscore key min max [WITHSCORES] [LIMIT offset count]`），返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员；
> `zrem`：移除有序集 key 中的一个或多个成员，不存在的成员将被忽略（格式：`zrem key member [member ...]`）；
> `zcard`/`zcount` /`zrank`/`zscore`，格式：
>
> - `zcard key`：返回有序集 key 的基数；
> - `zcount key min max`：返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量；
> - `zrank key member`：返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列，排名以 0 为底，也就是说， score 值最小的成员排名为 0 ；
> - `zscore key member`：返回有序集 key 中，成员 member 的 score 值；
>
> `zrevrank`：返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序，排名以 0 为底，也就是说， score 值最大的成员排名为 0 （格式：`zrevrank key member`）；
> `zrevrange`：返回有序集 key 中，指定区间内的成员，其中成员的位置按 score 值递减(从大到小)来排列（格式：`zrevrange key start stop [WITHSCORES]`）；
> `zrevrangebyscore`：返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列（格式：`zrevrangebyscore key max min [WITHSCORES] [LIMIT offset count]`）；

> ##### 解析配置文件（redis.conf）

- ##### `### INCLUDES（包含） ###`

> 和我们的Struts2配置文件类似，可以通过includes包含，redis.conf可以作为总闸，包含其他；

- ##### `### GENERAL（通用） ###`

> - **`daemonize no`**
>   Redis默认不是以守护进程的方式运行，可以通过该配置项修改，使用yes启用守护进程；
>   启用守护进程后，Redis会把pid写到一个pidfile中，在/var/run/redis.pid；
> - **`pidfile /var/run/redis.pid`**
>   当Redis以守护进程方式运行时，Redis默认会把pid写入/var/run/redis.pid文件，可以通过pidfile指定；
> - **`port 6379`**
>   指定Redis监听端口，默认端口为6379；
>   如果指定0端口，表示Redis不监听TCP连接；
> - **`tcp-backlog 511`**
>   设置tcp的backlog，backlog其实是一个连接队列，backlog队列总和=未完成三次握手队列 + 已经完成三次握手队列。在高并发环境下你需要一个高backlog值来避免慢客户端连接问题（注意Linux内核会将这个值减小到/proc/sys/net/core/somaxconn的值），所以需要增大somaxconn和tcp_max_syn_backlog两个值来达到想要的效果；
> - **`bind 127.0.0.1`**
>   绑定的主机地址；
>   你可以绑定单一接口，如果没有绑定，所有接口都会监听到来的连接；
> - **`timeout 0`**
>   当客户端闲置多长时间后关闭连接，如果指定为0，表示关闭该功能；
> - **`tcp-keepalive 0`**
>   TCP连接保活策略；
>   单位为秒，如果设置为0，则不会进行Keepalive检测，建议设置成60 ；
> - **`loglevel notice`**
>   指定日志记录级别，Redis总共支持四个级别：debug、verbose、notice、warning；
> - **`logfile ""`**
>   指定了记录日志的文件，空字符串的话，日志会打印到标准输出设备。后台运行的redis标准输出是/dev/null；
> - **`syslog-enabled no`**
>   是否把日志输出到syslog中；
> - **`syslog-ident redis`**
>   指定syslog里的日志标志
> - **`syslog-facility local0`**
>   指定syslog设备，值可以是USER或LOCAL0-LOCAL7；
> - **`databases 16`**
>   设置数据库的数量，默认数据库为0；

- ##### `### SNAPSHOTTING（快照） ###`

> - **`save`**
>   指定在多长时间内，有多少次更新操作，就将数据同步到数据文件，可以多个条件配合；
>   `save 900 1`：900秒（15分钟）内有1个更改
>   `save 300 10`：300秒（5分钟）内有10个更改
>   `save 60 10000`：60秒（1分钟）内有10000个更改
> - **`stop-writes-on-bgsave-error yes`**
>   后台存储错误停止写；
> - **`rdbcompression yes`**
>   指定存储至本地数据库时是否压缩数据，默认为yes，Redis采用LZF压缩，如果为了节省CPU时间，可以关闭该选项，但会导致数据库文件变的巨大；
> - **`rdbchecksum yes`**
>   在存储快照后，还可以让redis使用CRC64算法来进行数据校验，但是这样做会增加大约10%的性能消耗，如果希望获取到最大的性能提升，可以关闭此功能；
> - **`dbfilename dump.rdb`**
>   指定本地数据库文件名，默认值为dump.rdb；
> - **`dir ./`**
>   指定本地数据库存放目录（rdb、aof文件也会写在这个目录）；

- ##### `### REPLICATION（复制） ###`

> 详细请看下文Redis的复制（Master | Slave）；

- ##### `### SECURITY（安全） ###`

> - `requirepass foobared`
>   设置Redis连接密码，如果配置了连接密码，客户端在连接Redis时需要通过`auth password`命令提供密码，默认关闭；

- ##### `### LIMITS（极限） ###`

> - `maxclients 10000`
>   设置redis同时可以与多少个客户端进行连接。默认情况下为10000个客户端。当你无法设置进程文件句柄限制时，redis会设置为当前的文件句柄限制值减去32，因为redis会为自身内部处理逻辑留一些句柄出来。如果达到了此限制，redis则会拒绝新的连接请求，并且向这些连接请求方发出“max number of clients reached”以作回应；
> - `maxmemory <bytes>`
>   设置redis可以使用的内存量。一旦到达内存使用上限，redis将会试图移除内部数据，移除规则可以通过maxmemory-policy来指定。如果redis无法根据移除规则来移除内存中的数据，或者设置了“不允许移除”，那么redis则会针对那些需要申请内存的指令返回错误信息，比如SET、LPUSH等。但是对于无内存申请的指令，仍然会正常响应，比如GET等。如果你的redis是主redis（说明你的redis有从redis），那么在设置内存使用上限时，需要在系统中留出一些内存空间给同步队列缓存，只有在你设置的是“不移除”的情况下，才不用考虑这个因素
> - `maxmemory-policy noeviction`
>   数据淘汰策略，Reids 具体有 6 种淘汰策略：
>   （1）volatile-lru：使用LRU算法移除key，只对设置了过期时间的键；
>   （2）allkeys-lru：使用LRU算法移除key；
>   （3）volatile-random：在过期集合中移除随机的key，只对设置了过期时间的键；
>   （4）allkeys-random：移除随机的key；
>   （5）volatile-ttl：移除那些TTL值最小的key，即那些最近要过期的key；
>   （6）noeviction：不进行移除。针对写操作，只是返回错误信息；
> - `maxmemory-samples 5`
>   设置样本数量，LRU算法和最小TTL算法都并非是精确的算法，而是估算值，所以你可以设置样本的大小，redis默认会检查这么多个key并选择其中LRU的那个；

- ##### `### APPEND ONLY MODE（追加） ###`

> - `appendonly no`
>   指定是否在每次更新操作后进行日志记录，Redis在默认情况下是异步的把数据写入磁盘，如果不开启，可能会在断电时导致一段时间内的数据丢失；因为redis本身同步数据文件是按上面save条件来同步的，所以有的数据会在一段时间内只存在于内存中。默认为no；
> - `appendfilename "appendonly.aof"`
>   指定更新日志文件名，默认为appendonly.aof；
> - `appendfsync everysec`
>   `always`：同步持久化，每次发生数据变更会被立即记录到磁盘 性能较差但数据完整性比较好；
>   `everysec`：出厂默认推荐，异步操作，每秒记录 如果一秒内宕机，有数据丢失；
>   `no`：让操作系统来决定何时同步，不能给服务器性能带来多大的提升，而且也会增加系统奔溃时数据丢失的数量；
> - `no-appendfsync-on-rewrite no`
>   重写时是否可以运用Appendfsync，用默认no即可，保证数据安全性；
> - `auto-aof-rewrite-percentage 100`
>   重写指定百分比，为0会禁用AOF自动重写特性；
> - `auto-aof-rewrite-min-size 64mb`
>   设置重写的基准值；

- ##### `### 常见的一些配置总结 ###`

> **redis.conf 配置项说明如下：**
>
> 1. Redis默认不是以守护进程的方式运行，可以通过该配置项修改，使用yes启用守护进程
>    `daemonize yes`
> 2. 当Redis以守护进程方式运行时，Redis默认会把pid写入/var/run/redis.pid文件，可以通过pidfile指定
>    `pidfile /var/run/redis.pid`
> 3. 指定Redis监听端口，默认端口为6379，作者在自己的一篇博文中解释了为什么选用6379作为默认端口，因为6379在手机按键上MERZ对应的号码，而MERZ取自意大利歌女Alessia Merz的名字
>    `port 6379`
> 4. 绑定的主机地址
>    `bind 127.0.0.1`
> 5. 当 客户端闲置多长时间后关闭连接，如果指定为0，表示关闭该功能
>    `timeout 300`
> 6. 指定日志记录级别，Redis总共支持四个级别：debug、verbose、notice、warning，默认为verbose
>    `loglevel verbose`
> 7. 日志记录方式，默认为标准输出，如果配置Redis为守护进程方式运行，而这里又配置为日志记录方式为标准输出，则日志将会发送给/dev/null
>    `logfile stdout`
> 8. 设置数据库的数量，默认数据库为0，可以使用SELECT 命令在连接上指定数据库id
>    `databases 16`
> 9. 指定在多长时间内，有多少次更新操作，就将数据同步到数据文件，可以多个条件配合
>    `save seconds changes`
>    Redis默认配置文件中提供了三个条件：
>    save 900 1
>    save 300 10
>    save 60 10000
>    分别表示900秒（15分钟）内有1个更改，300秒（5分钟）内有10个更改以及60秒内有10000个更改。
> 10. 指定存储至本地数据库时是否压缩数据，默认为yes，Redis采用LZF压缩，如果为了节省CPU时间，可以关闭该选项，但会导致数据库文件变的巨大
>     `rdbcompression yes`
> 11. 指定本地数据库文件名，默认值为dump.rdb
>     `dbfilename dump.rdb`
> 12. 指定本地数据库存放目录
>     `dir ./`
> 13. 设置当本机为slav服务时，设置master服务的IP地址及端口，在Redis启动时，它会自动从master进行数据同步
>     `slaveof <masterip> <masterport>`
> 14. 当master服务设置了密码保护时，slav服务连接master的密码
>     `masterauth <master-password>`
> 15. 设置Redis连接密码，如果配置了连接密码，客户端在连接Redis时需要通过`AUTH <password>`命令提供密码，默认关闭
>     `requirepass foobared`
> 16. 设置同一时间最大客户端连接数，默认无限制，Redis可以同时打开的客户端连接数为Redis进程可以打开的最大文件描述符数，如果设置 maxclients 0，表示不作限制。当客户端连接数到达限制时，Redis会关闭新的连接并向客户端返回max number of clients reached错误信息
>     `maxclients 128`
> 17. 指定Redis最大内存限制，Redis在启动时会把数据加载到内存中，达到最大内存后，Redis会先尝试清除已到期或即将到期的Key，当此方法处理 后，仍然到达最大内存设置，将无法再进行写入操作，但仍然可以进行读取操作。Redis新的vm机制，会把Key存放内存，Value会存放在swap区
>     `maxmemory <bytes>`
> 18. 指定是否在每次更新操作后进行日志记录，Redis在默认情况下是异步的把数据写入磁盘，如果不开启，可能会在断电时导致一段时间内的数据丢失。因为 redis本身同步数据文件是按上面save条件来同步的，所以有的数据会在一段时间内只存在于内存中。默认为no
>     `appendonly no`
> 19. 指定更新日志文件名，默认为appendonly.aof
>     `appendfilename appendonly.aof`
> 20. 指定更新日志条件，共有3个可选值：
>     `no`：表示等操作系统进行数据缓存同步到磁盘（快）
>     `always`：表示每次更新操作后手动调用fsync()将数据写到磁盘（慢，安全）
>     `everysec`：表示每秒同步一次（折衷，默认值）
>     `appendfsync everysec`
> 21. 指定是否启用虚拟内存机制，默认值为no，简单的介绍一下，VM机制将数据分页存放，由Redis将访问量较少的页即冷数据swap到磁盘上，访问多的页面由磁盘自动换出到内存中（在后面的文章我会仔细分析Redis的VM机制）
>     `vm-enabled no`
> 22. 虚拟内存文件路径，默认值为/tmp/redis.swap，不可多个Redis实例共享
>     `vm-swap-file /tmp/redis.swap`
> 23. 将所有大于vm-max-memory的数据存入虚拟内存,无论vm-max-memory设置多小,所有索引数据都是内存存储的(Redis的索引数据 就是keys),也就是说,当vm-max-memory设置为0的时候,其实是所有value都存在于磁盘。默认值为0
>     `vm-max-memory 0`
> 24. Redis swap文件分成了很多的page，一个对象可以保存在多个page上面，但一个page上不能被多个对象共享，vm-page-size是要根据存储的 数据大小来设定的，作者建议如果存储很多小对象，page大小最好设置为32或者64bytes；如果存储很大大对象，则可以使用更大的page，如果不 确定，就使用默认值
>     `vm-page-size 32`
> 25. 设置swap文件中的page数量，由于页表（一种表示页面空闲或使用的bitmap）是在放在内存中的，，在磁盘上每8个pages将消耗1byte的内存。
>     `vm-pages 134217728`
> 26. 设置访问swap文件的线程数,最好不要超过机器的核数,如果设置为0,那么所有对swap文件的操作都是串行的，可能会造成比较长时间的延迟。默认值为4
>     `vm-max-threads 4`
> 27. 设置在向客户端应答时，是否把较小的包合并为一个包发送，默认为开启
>     `glueoutputbuf yes`
> 28. 指定在超过一定的数量或者最大的元素超过某一临界值时，采用一种特殊的哈希算法
>     `hash-max-zipmap-entries 64`
>     `hash-max-zipmap-value 512`
> 29. 指定是否激活重置哈希，默认为开启（后面在介绍Redis的哈希算法时具体介绍）
>     `activerehashing yes`
> 30. 指定包含其它的配置文件，可以在同一主机上多个Redis实例之间使用同一份配置文件，而同时各个实例又拥有自己的特定配置文件
>     `include /path/to/local.conf`

> ##### Redis的持久化

- ##### `RDB（Redis DataBase）`

> - `RDB介绍`
>   在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话讲的Snapshot快照，它恢复时是将快照文件直接读到内存里；
>   RDB保存的是dump.rdb文件；
>   Redis会单独创建（`fork`）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。整个过程中，主进程是不进行任何IO操作的，这就确保了极高的性能如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那RDB方式要比AOF方式更加的高效。RDB的缺点是最后一次持久化后的数据可能丢失；
>   `fork`：复制一个与当前进程一样的进程。新进程的所有数据（变量、环境变量、程序计数器等）数值都和原进程一致，但是是一个全新的进程，并作为原进程的子进程；
> - `如何触发RDB快照`
>
> 1. 配置文件中默认的快照配置（冷拷贝后重新使用：可以`cp dump.rdb dump_new.rdb`）；
> 2. 使用命令save或者bgsave
>    `save`：save时只管保存，其它不管，全部阻塞；
>    `bgsave`：Redis会在后台异步进行快照操作，快照同时还可以响应客户端请求。可以通过`lastsave`命令获取最后一次成功执行快照的时间；
> 3. 执行`flushall`命令，也会产生dump.rdb文件，但里面是空的，无意义；
>
> - `如何恢复`
>   将备份文件 (`dump.rdb`) 移动到 redis 安装目录并启动服务即可，通过`config get dir`可获取目录；
> - `如何停止`
>   动态所有停止RDB保存规则的方法：`redis-cli config set save ""`；
> - `优势`
>
> 1. 适合大规模的数据恢复；
> 2. 对数据完整性和一致性要求不高；
>
> - `劣势`
>
> 1. 在一定间隔时间做一次备份，所以如果redis意外宕掉的话，就会丢失最后一次快照后的所有修改；
> 2. fork的时候，内存中的数据被克隆了一份，大致2倍的膨胀性需要考虑；

- ##### `AOF（Append Only File）`

> - `AOF介绍`
>   以日志的形式来记录每个写操作，将Redis执行过的所有写指令记录下来(读操作不记录)，只许追加文件但不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作（AOF保存的是appendonly.aof文件）；
> - `AOF启动/修复/恢复`
>
> 1. 正常恢复
>    启动：修改默认的`appendonly no`，改为`yes`；
>    将有数据的aof文件复制一份保存到对应目录(目录通过`config get dir`命令获取)；
>    恢复：重启redis然后重新加载；
> 2. 异常恢复
>    启动：修改默认的`appendonly no`，改为`yes`；
>    备份被破坏的aof文件；
>    修复：使用`redis-check-aof --fix`命令进行修复；
>    恢复：重启redis然后重新加载；
>
> - `rewrite`
>
> 1. rewrite介绍
>    AOF采用文件追加方式，文件会越来越大为避免出现此种情况，新增了重写机制，当AOF文件的大小超过所设定的阈值时，Redis就会启动AOF文件的内容压缩，只保留可以恢复数据的最小指令集.可以使用命令`bgrewriteaof`；
> 2. 重写原理
>    AOF文件持续增长而过大时，会fork出一条新进程来将文件重写(也是先写临时文件最后再rename)，遍历新进程的内存中数据，每条记录有一条的Set语句。重写aof文件的操作，并没有读取旧的aof文件，而是将整个内存中的数据库内容用命令的方式重写了一个新的aof文件，这点和快照有点类似；
> 3. 触发机制
>    Redis会记录上次重写时的AOF大小，默认配置是当AOF文件大小是上次rewrite后大小的一倍且文件大于64M时触发；
>
> - `优势`
>   每修改同步：`appendfsync always` 同步持久化，每次发生数据变更会被立即记录到磁盘 性能较差但数据完整性比较好；
>   每秒同步：`appendfsync everysec` 异步操作，每秒记录，如果一秒内宕机，有数据丢失；
>   不同步：`appendfsync no` 从不同步；
> - `劣势`
>
> 1. 相同数据集的数据而言aof文件要远大于rdb文件，恢复速度慢于rdb；
> 2. aof运行效率要慢于rdb,每秒同步策略效率较好，不同步效率和rdb相同；

- ##### `总结（Which One）`

> 1. RDB持久化方式能够在指定的时间间隔能对你的数据进行快照存储；
> 2. AOF持久化方式记录每次对服务器写的操作,当服务器重启的时候会重新执行这些命令来恢复原始的数据，AOF命令以redis协议追加保存每次写的操作到文件末尾，Redis还能对AOF文件进行后台重写,使得AOF文件的体积不至于过大；
> 3. 只做缓存：如果你只希望你的数据在服务器运行的时候存在,你也可以不使用任何持久化方式；
> 4. 同时开启两种持久化方式
>    在这种情况下，当redis重启的时候会优先载入AOF文件来恢复原始的数据，因为在通常情况下AOF文件保存的数据集要比RDB文件保存的数据集要完整；
>    同时使用两者时服务器重启也只会找AOF文件，那要不要只使用AOF呢？作者建议不要，因为RDB更适合用于备份数据库(AOF在不断变化不好备份)，快速重启，而且不会有AOF可能潜在的bug，留着作为一个万一的手段。
> 5. 性能建议
>    因为RDB文件只用作后备用途，建议只在Slave上持久化RDB文件，而且只要15分钟备份一次就够了，只保留save 900 1这条规则。
>    如果Enalbe AOF，好处是在最恶劣情况下也只会丢失不超过两秒数据，启动脚本较简单只load自己的AOF文件就可以了。代价一是带来了持续的IO，二是AOF rewrite的最后将rewrite过程中产生的新数据写到新文件造成的阻塞几乎是不可避免的。只要硬盘许可，应该尽量减少AOF rewrite的频率，AOF重写的基础大小默认值64M太小了，可以设到5G以上。默认超过原大小100%大小时重写可以改到适当的数值。
>    如果不Enable AOF ，仅靠Master-Slave Replication 实现高可用性也可以。能省掉一大笔IO也减少了rewrite时带来的系统波动。代价是如果Master/Slave同时倒掉，会丢失十几分钟的数据，启动脚本也要比较两个Master/Slave中的RDB文件，载入较新的那个。新浪微博就选用了这种架构；

> ##### Redis的事务

- ##### `redis事务简介`

> 可以一次执行多个命令，本质是一组命令的集合。一个事务中的所有命令都会序列化，按顺序地串行化执行而不会被其它命令插入，不许加塞；

- ##### `redis事务能干什么`

> 一个队列中，一次性、顺序性、排他性的执行一系列命令；

- ##### `redis事务执行五种情况`

> case1：正常执行 执行`exec`全部成功；
> case2：放弃事务 执行`discard`；
> case3：全体连坐 在向事物队列中添加命令的时候报错，然后执行`exec`会全部失败；
> case4：冤头债主 在向事物队列中添加命令的时候没有报错，但在执行`exec`的时候某一条命令执行失败，只会影响这一个，其他的会执行成功，这种为部分成功；
> case5：watch监控
> **Redis部分支持事务**

- ##### `悲观锁/乐观锁/CAS(Check And Set)`

> 悲观锁
> 悲观锁(Pessimistic Lock), 顾名思义，就是很悲观，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁。
> 传统的关系型数据库里边就用到了很多这种锁机制，比如行锁，表锁等，读锁，写锁等，都是在做操作之前先上锁。
> 乐观锁
> 乐观锁(Optimistic Lock), 顾名思义，就是很乐观，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，可以使用版本号等机制。乐观锁适用于多读的应用类型，这样可以提高吞吐量。
> CAS(Check And Set)
> `witch`命令可以为 Redis 事务提供 check-and-set （CAS）行为，类似乐观锁。
> 被 `witch`的键会被监视，并会发觉这些键是否被改动过了。 如果有至少一个被监视的键在 `exec`执行之前被修改了，那么整个事务都会被取消，`exec`返回空多条批量回复（null multi-bulk reply）来表示事务已经失败。

- ##### `redis事务执行的过程`

> 开启：以`multi`开始一个事务；　　
> 入队：将多个命令入队到事务中，接到这些命令并不会立即执行，而是放到等待执行的事务队列里面；
> 执行：由`exec`命令触发事务；

- ##### `redis事务的特性`

> 1. 单独的隔离操作：事务中的所有命令都会序列化、按顺序地执行。事务在执行的过程中，不会被其他客户端发送来的命令请求所打断；
> 2. 没有隔离级别的概念：队列中的命令没有提交之前都不会实际的被执行，因为事务提交前任何指令都不会被实际执行，也就不存在”事务内的查询要看到事务里的更新，在事务外查询不能看到”这个让人万分头痛的问题；
> 3. 不保证原子性：redis同一个事务中如果有一条命令执行失败，其后的命令仍然会被执行，没有回滚；

> ##### Redis的复制（Master | Slave）

- ##### `Redis复制简介`

> 行话：也就是我们所说的主从复制，主机数据更新后根据配置和策略，自动同步到备机的master/slaver机制，master以写为主，slave以读为主；

- ##### `Redis的复制能干什么`

> - 读写分离；
> - 容灾恢复；

- ##### `Redis复制如何去应用`

> 1. 配从(库)不配主(库)；
> 2. 从库配置：执行命令`slaveof 主库IP 主库端口`：
>    每次与master断开之后，都需要重新连接，除非你配置进redis.conf文件；
>    执行命令`info replication`查看主从关系；
> 3. 修改配置文件细节操作：
>    拷贝多个redis.conf文件；
>    开启daemonize yes；
>    修改pid文件名字；
>    修改指定端口；
>    修改log文件名字；
>    修改dump.rdb名字；
> 4. 常用三招
>
> - 一主多仆
>   一个master两个slave；
>   **一些问题？**
>   (1) 切入点问题？slave1、slave2是从头开始复制还是从切入点开始复制?
>   答：从头开始复制；
>   (2) 从机是否可以写？set可否？
>   答：从机不可以写，也就不能set；
>   (3) 主机shutdown后情况如何？从机是上位还是原地待命?
>   答：原地待命；
>   (4) 主机又回来了后，主机新增记录，从机还能否顺利复制？
>   答：可以；
>   (5) 其中一台从机宕掉后情况如何？恢复它能跟上主机吗？
>   答：不能，需要重新建立主从关系；
> - 薪火相传
>   上一个Slave可以是下一个slave的Master，Slave同样可以接收其他slaves的连接和同步请求，那么该slave作为了链条中下一个的master,可以有效减轻master的写压力。
>   中途变更转向:会清除之前的数据，重新建立拷贝最新的。
> - 反客为主
>   主机宕掉后，从机升级为主机：
>   选择一个从机手动执行`slaveof no one`命令变更为主机，其他从机与该主机建立主从关系。

- ##### `Redis复制的原理`

> master接到命令启动后台的存盘进程，同时收集所有接收到的用于修改数据集命令，在后台进程执行完毕之后，master将传送整个数据文件到slave,以完成一次完全同步
> 全量复制：而slave服务在接收到数据库文件数据后，将其存盘并加载到内存中。slave第一次同步为全量复制。
> 增量复制：master继续将新的所有收集到的修改命令依次传给slave,完成同步
> 但是只要是重新连接master,第一次完全同步（全量复制)将被自动执行。

- ##### `哨兵模式(sentinel)`

> - 哨兵模式简介
>   反客为主的自动版，能够后台监控主机是否故障，如果故障了根据投票数自动将从库转换为主库；
> - 启动哨兵模式步骤：
>
> 1. 自定义的/myredis目录下新建sentinel.conf文件，名字绝不能错；
> 2. 配置哨兵,填写内容在sentinel.conf文件中配置：
>    `sentinel monitor 被监控数据库名字(自己起个名字) 127.0.0.1 6379 1`
>    上面最后一个数字1，表示主机挂掉后salve投票看让谁接替成为主机，得票数多的成为主机；
> 3. 启动哨兵
>    执行命令：`redis-sentinel /myredis/sentinel.conf` （目录依照各自的实际情况配置，可能目录不同）；
>
> - **问题**
>   如果之前的master重启回来，会不会双master冲突？
>   不会造成双冲突，之前的master会成为slave。

- ##### `复制的缺点`

> **复制延时**
> 由于所有的写操作都是先在Master上操作，然后同步更新到Slave上，所以从Master同步到Slave机器有一定的延迟，当系统很繁忙的时候，延迟问题会更加严重，Slave机器数量的增加也会使这个问题更加严重。

> ##### Redis的Java客户端Jedis

- ##### `Jedis所需要的jar包`

> 1. commons-pool-1.6.jar
> 2. jedis-2.1.0.jar

- ##### `Jedis常用操作`

> 1. 测试连通性

```
public class Demo01 {
   public static void main(String[] args) {
   	//连接本地的 Redis 服务
    	Jedis jedis = new Jedis("127.0.0.1",6379);
    	//查看服务是否运行，打出pong表示OK
    	System.out.println(jedis.ping());
   }
}
12345678
```

- ##### `JedisPool配置参数`

> `JedisPool`的配置参数大部分是由`JedisPoolConfig`的对应项来赋值的；
> `maxActive`：控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted；
> `maxIdle`：控制一个pool最多有多少个状态为idle(空闲)的jedis实例；
> `whenExhaustedAction`：表示当pool中的jedis实例都被allocated完时，pool要采取的操作；默认有三种：
>
> - `WHEN_EXHAUSTED_FAIL`：表示无jedis实例时，直接抛出`NoSuchElementException`；
> - `WHEN_EXHAUSTED_BLOCK`： 表示阻塞住，或者达到`maxWait`时抛出`JedisConnectionException`；
> - `WHEN_EXHAUSTED_GROW` ：表示新建一个jedis实例，也就说设置的`maxActive`无用；
>
> `maxWait`：表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛JedisConnectionException；
> `testOnBorrow`：获得一个jedis实例的时候是否检查连接可用性（ping()）；如果为true，则得到的jedis实例均是可用的；
> `testOnReturn`：return 一个jedis实例给pool时，是否检查连接可用性（ping()）；　　
> `testWhileIdle`：如果为true，表示有一个idle object evitor线程对idle object进行扫描，如果validate失败，此object会被从pool中drop掉；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；　　
> `timeBetweenEvictionRunsMillis`：表示idle object evitor两次扫描之间要sleep的毫秒数；
> `numTestsPerEvictionRun`：表示idle object evitor每次扫描的最多的对象数；
> `minEvictableIdleTimeMillis`：表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
> `softMinEvictableIdleTimeMillis`：在minEvictableIdleTimeMillis基础上，加入了至少minIdle个对象已经在pool里面了。如果为-1，evicted不会根据idle time驱逐任何对象。如果minEvictableIdleTimeMillis>0，则此项设置无意义，且只有在timeBetweenEvictionRunsMillis大于0时才有意义；
> `lifo`：borrowObject返回对象时，是采用DEFAULT_LIFO（last in first out，即类似cache的最频繁使用队列），如果为False，则表示FIFO队列；
> 其中JedisPoolConfig对一些参数的默认设置如下：
>
> - `testWhileIdle=true`
> - `minEvictableIdleTimeMills=60000`
> - `timeBetweenEvictionRunsMillis=30000`
> - `numTestsPerEvictionRun=-1`

```
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {

    private static volatile JedisPool jedisPool = null;

    private JedisPoolUtils() { }

    public static JedisPool getJedisPoolInstance() {
        if (jedisPool == null) {
             synchronized (JedisPoolUtils.class) {
                  if (jedisPool == null) {
                      JedisPoolConfig poolConfig = new JedisPoolConfig();
                      poolConfig.setMaxActive(1000);
                      poolConfig.setMaxIdle(30);
                      poolConfig.setMaxWait(100*1000);
                      poolConfig.setTestOnBorrow(true);
                      jedisPool = new JedisPool(poolConfig, "192.168.15.128", 6379);
                  }
            }
        }
        return jedisPool;
    }
  
    public static void release(JedisPool jedisPool,Jedis jedis){
        if(jedis!=null){
            jedisPool.returnResourceObject(jedis);
        }
    }
}
```

































​                                                                                                                                                                                                                                                