# MongoDB

## 一、什么是MongoDB

MongoDB 是由C++语言编写的，是一个基于分布式文件存储的开源**数据库系统**。在高负载的情况下，添加更多的节点，可以保证服务器性能。MongoDB 旨在为WEB应用提供可扩展的高性能数据存储解决方案。

MongoDB 将数据存储为一个文档，数据结构由键值(key=>value)对组成。MongoDB 文档类似于 JSON 对象。字段值可以包含其他文档，数组及文档数组。

MongoDB 是一个介于关系数据库和非关系数据库之间的产品，是非关系数据库当中功能最丰富，最像关系数据库的。

## 二、什么是NoSQL

NoSQL，指的是非关系型的数据库。NoSQL有时也称作Not Only SQL的缩写，是对不同于传统的关系型数据库的数据库管理系统的统称。

NoSQL用于超大规模数据的存储。（例如谷歌或Facebook每天为他们的用户收集万亿比特的数据）。这些类型的数据存储不需要固定的模式，无需多余操作就可以横向扩展。

今天我们可以通过第三方平台（如：Google,Facebook等）可以很容易的访问和抓取数据。用户的个人信息，社交网络，地理位置，用户生成的数据和用户操作日志已经成倍的增加。我们如果要对这些用户数据进行挖掘，那SQL数据库已经不适合这些应用了, NoSQL 数据库的发展却能很好的处理这些大的数据。

### NoSQL的优点/缺点

优点:

- \- 高可扩展性
- \- 分布式计算
- \- 低成本
- \- 架构的灵活性，半结构化数据
- \- 没有复杂的关系

缺点:

- \- 没有标准化
- \- 有限的查询功能（到目前为止）
- \- 最终一致是不直观的程序

## 三、数据库安装

安装教程：https://www.runoob.com/mongodb/mongodb-window-install.html

安装版本：V4.0.8

在这里记录一下自己遇到的坑：

### 1.install mongoDB compass

在第一次安装的时候，没有注意勾选上了安装这个部分，结果安装程序一直卡在那块。最后又重新安装了一次才解决了问题。mongoDB compass主要是MongoDB的一个图形化界面，后期可以单独安装。

### 2.安装中提示启动失败

之前MongoDB按照默认路径装到了C盘。后来因为C盘的空间有限，后来又卸载安装在了E盘。之前在C盘安装的时候，顺风顺水没有出一点问题。但是在E盘安装时，遇到了一些问题。在安装程序还在进行的时候，突然跳出来一个提示，显示MongoDB服务无法启动。通过查找一些资料，知道了当系统启动服务的时候需要获得管理员权限，当程序安装在C盘的时候，安装程序可以直接获得较高的权限，但是安装在其他位置，就会因为安装权限不足，导致服务无法启动。 这时点击忽略，直接完成安装，之后通过命令行获取系统管理员权限后，手动启动服务。

### 3.安装MongoDB服务出现问题

当程序安装完成之后，就需要安装windows服务了，MongoDB中已经为我们写好了配置文件，通过这个配置文件，即可安装windows服务。具体的命令是：`mongod --config "E:\MongoDB\bin\mongod.cfg" --install`

本想着，安装服务然后启动服务就可以可以使用了，但是这样却出现了问题：`Unrecognized option:mp`这就令人非常抓狂了，配置文件是官方提供的，它怎么能说mp是无效的属性呢？

无奈之下，硬着头皮去官网上查找cfg文档。最后把整个文档都翻烂了，都没找到mp属性。最后我心想，既然你没有这个属性，我试着把它删掉吧。在文档中删掉了这一行，ok问题解决。。。这简直了，官方提供的配置文件，尽然出现这样的问题，心累。。。

【注意】：使用MongoDB服务安装命令，会自动创建日志文件，但不会初始化数据库。必须使用启动`window--MongoDB服务`或`使用mongod --dbpath D:\Program\MongoDB\data`创建数据库实例（生成数据库文件）

这里补充一下配置文件修改过程中遇到的坑：

- 配置文件中不能使用Tab键进行缩进
- 属性名与属性值之间，必须用`:空格`进行连接，如果不添加空格，属性将无法识别

最后在记录一下MongoDB服务相关的命令，以备不时之需：

- 删除windows服务：`sc delete MongoDB`
- 启动服务 ：`net start MongoDB`
- 停止服务：`net stop MongoDB`
- 更新服务配置：`mongod -f E:\MongoDB\bin\mongod.cfg`—Ps：在更新服务配置的过程中出现：`Failed global initialization: FileNotOpen`的报错.根据他的提示，无法打开日志文件。出现这个问题的原因是因为，日志的地址没有改动，旧日志与新日志之间权限出现了冲突。导致无法更新。可以尝试删除服务，重新创建来解决这个问题。



## 四、数据库连接及权限管理

使用MongoDB中有一个很大的困惑，尽然不需要密码就可以登录？那它的安全性如何保证呢？权限划分又是如何进行呢？对比MySQL在安装成功之后就会有个初始密码，MongoDB是不是不安全呢？下面我们就分析一下，这些问题：

### 1.启动服务

通过上一个章节的操作，我们已将数据库安装就绪。但将数据库数据库服务运行起来还需要启动服务。MongoDB的服务有两种，一种是启动windows的服务，另一种是通过命令行来创建临时的服务。

当服务没启动，就直接链接数据库，会抛出`Error: couldn't connect to server 127.0.0.1:27017`错误。

- window服务模式：在管理员权限下，使用`net`命令进行服务控制。window服务是隐式的工作的，它将所有的日志都写入log日志文件中，本身并没有进行对运行的结果进行输出。在整个数据库，在正式服务模式下，都是使用这种方式
- 命令行模式：在不启动动window服务的前提下，也可以通过建立一个命令行窗口来提供MongoDB服务。这个模式下是显示输出的内容的，即日志内容会直接打印在命令行窗口中。这种方式更利于数据库应用的开发调试，更加直观的显示数据的操作与异常。当关闭命令行窗口后，MongoDB服务将会停止。
  - –dbpath：指定数据库路径
  - –logpath：指定日志路径
  - –fork：以守护进程的方式运行MongoDB，创建服务器进程
  - –auth：启用用户身份验证（默认是不使用的）
  - –cpu：定期显示CPU的CPU利用率和iowait
  - –port：指定端口号

#### a.命令行启动

使用命令行模式启动服务，以便于观察数据库的变化。

- 不使用身份验证启动服务：`mongod --dbpath “E:\MongoDB\data”`

命令行中抛出了这样的警告

```
WARNING: Access control is not enabled for the database.
Read and write access to data and configuration is unrestricted.
```

这个警告是因为数据库使用了非身份验证验证模式启动，即mongod命令中没有使用–auth参数。也就是说不使用身份验证，任何人登入的都是超级管理员，在实际使用中需要额外注意。

- 使用身份验证启动服务：``mongod --dbpath “E:\MongoDB\data” --auth`

这个模式下登录mongo，依然可以连接成功，但是如果不进行身份验证，将无法对数据库进行操作。

#### b.window服务启动

当启动MongoDB服务之后，会按照配置文件设置的相关属性，来启动服务。注意配置文件中默认是不需要身份验证的，当安装结束之后，要修改配置文件为身份验证模式。

```
security:
  authorization: enabled
```

### 2.用户创建

对于数据库的首次访问，还没有任何的账户权限。非身份验证模式下，可以随意对数据库进行操作。身份验证模式下（首次登陆），只能进行数据库的一个账户的创建操作，所以此时应该添加系统账户管理员（userAdminAnyDatabase）。如果添加了其他的账户，将会导致数据库因权限不足无法进行任何操作。

- 数据库的用户及权限，都保存在admin数据库的users中。我们可以使用以下语句创建用户：

```
use admin
db.createUser({
    user:"userAdmin",
    pwd:"3832414122",
    roles:[{ role: "userAdminAnyDatabase",db:"admin"}]
})
```

- user：用户名
- pwd：密码
- roles：指定用户的角色。数组的元素必须是对象，且包含两个属性（role-权限，db-已经存在的数据库）

> **数据库用户角色（Database User Roles）：**
>
> - **read：**授予User只读数据的权限
> - **readWrite：**授予User读写数据的权限
>
> **数据库管理角色（Database Administration Roles）：**
>
> - **dbAdmin：**在当前dB中执行管理操作
> - **dbOwner：**在当前DB中执行任意操作
> - **userAdmin：**在当前DB中管理User
>
> **备份和还原角色（Backup and Restoration Roles）：**
>
> - **backup**
> - **restore**
>
> **跨库角色（All-Database Roles）：**
>
> - **readAnyDatabase：**授予在所有数据库上读取数据的权限
> - **readWriteAnyDatabase：**授予在所有数据库上读写数据的权限
> - **userAdminAnyDatabase：**授予在所有数据库上管理User的权限
> - **dbAdminAnyDatabase：**授予管理所有数据库的权限
>
> **集群管理角色（Cluster Administration Roles）：**
>
> - **clusterAdmin**：授予管理集群的最高权限
> - **clusterManager**：授予管理和监控集群的权限，A user with this role can access the config and local databases, which are used in sharding and replication, respectively.
> - **clusterMonitor**：授予监控集群的权限，对监控工具具有readonly的权限
> - **hostManager**：管理Server

根据用户所需权限，添加对应数据库的管理者。

- 使用`db.auth(“user”,“pwd”);`来在shell中切换用户角色。

### 3.使用身份验证连接到数据库

**启动shell时，进行身份验证**

```
mongo -u username -p
```

**默认登录后链接不同服务**

```
mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
```

- 实例

使用用户 admin 使用密码 123456 连接到本地的 MongoDB 服务上。输出结果如下所示：

```
mongodb://admin:123456@localhost/
```

使用用户名fred，密码foobar登录132.232.169.227的4455端口的baz数据库。

```
mongodb://fred:foobar@132.232.169.227:4455/baz
```

更多实例：https://www.runoob.com/mongodb/mongodb-connections.html

### 4.修改密码

- 方法1：db.changeUserPassword(“usertest”,“changepass”);
- 方法2：db.updateUser(“usertest”,{pwd:“changepass1”})；

### 5.修改权限

- db.updateUser(“usertest”,{roles:[ {role:“read”,db:“testDB”} ]})

注：updateuser它是完全替换之前的值，如果要新增或添加roles而不是代替它 则使用方法： db.grantRolesToUser() 和 db.revokeRolesFromUser(）

- db.grantRolesToUser(“usertest”, [{role:“readWrite”, db:“testDB”},{role:“read”, db:“testDB”}]) # 修改权限
- db.revokeRolesFromUser(“usertest”,[{role:“read”, db:“testDB”}]) # 删除权限：



## 五、MongoDB数据类型

| 数据类型    | 描述                                                         | 举例                     |
| ----------- | ------------------------------------------------------------ | ------------------------ |
| 32 位整数   | 32 位整数。shell 是不支持该类型的， shell 中默认会转换成 64位浮点数 |                          |
| 64 位整数   | 64 位整数。shell 是不支持该类型的， shell 中默认会转换成 64位浮点数 |                          |
| 64 位浮点数 | 64 位浮点数。shell 中的数字就是这一种类型                    | { “x”： 3.14 ，“y” ： 3} |
| 字符串      | UTF-8 字符串                                                 | { “foo”:“bar”}           |
| 符号        | shell 不支持，shell 会将数据库中的符号类型的数据自动转换成字符串 |                          |
| 对象 id     | 文档的 12 字节的唯一id                                       | { “id”: ObjectId()}      |
| 日期        | 从标准纪元开始的毫秒数                                       | { “date”:new Date()}     |
| 正则表达式  | 文档中可以包含正则表达式，遵循 JavaScript 的语法             | { “foo”:/foobar/i}       |
| 代码        | 文档中可以包含 JavaScript 代码                               | { “x”： function() {}}   |
| 二进制数据  | 任意字节的二进制串组成， shell 不支持                        |                          |
| 最大值      | 表示可能的最大值， shell 不支持                              |                          |
| 最小值      | 表示可能的最小值， shell 不支持                              |                          |
| 未定义      | undefined                                                    | { “x”： undefined}       |
| 数组        | 值的集合或者列表                                             | { “arr”: [“a”,“b”]}      |
| 内嵌文档    | 文档可以作为文档中某个 key 的value                           | { “x”:{“foo”:“bar”}}     |
| null        | 表示空值或者未定义的对象                                     | { “x”:null}              |
| 布尔值      | 真或者假： true 或者false                                    | { “x”:true}              |

| **类型**                | **数字** | **备注**         |
| ----------------------- | -------- | ---------------- |
| Double                  | 1        |                  |
| String                  | 2        |                  |
| Object                  | 3        |                  |
| Array                   | 4        |                  |
| Binary data             | 5        |                  |
| Undefined               | 6        | 已废弃。         |
| Object id               | 7        |                  |
| Boolean                 | 8        |                  |
| Date                    | 9        |                  |
| Null                    | 10       |                  |
| Regular Expression      | 11       |                  |
| JavaScript              | 13       |                  |
| Symbol                  | 14       |                  |
| JavaScript (with scope) | 15       |                  |
| 32-bit integer          | 16       |                  |
| Timestamp               | 17       |                  |
| 64-bit integer          | 18       |                  |
| Min key                 | 255      | Query with `-1`. |
| Max key                 | 127      |                  |

上面的大部分类型都是显而易见的，但是有必要详细解释一下对象 id类型。我们上面说了，对象 id类型是一个 12字节的唯一 id。每个字节 2位 16进制数，因此整个 id类型是一个 24位的字符串。

- 前面四个字节代表从标准纪元开始的时间戳，以秒为单位。
- 接下来三个字节表示机器号，MongoDB入门2——MongoDB数据类型一般是机器名的hash值。这可以保证不同机器产生的id不会冲突。
- 接下来两个字节表示进程id号，保证统一机器不同进程产生的id不冲突
- 最后三个是计数器的计数值，对于任意一秒钟，可以产生2^24个数

 我们之前提到过，如果不在插入的时候手动的添加_id键，那么系统会自动添加一个。尽管MongoDB的id类型被设计成轻量级的，但是这样还是会不好。毕竟服务器创建这个还是会有开销的。因此，这个工作一般会放到客户端来做。

## 六、数据库常规功能使用

### 1.创建数据库

```
use server1
db.server1.insert(obj);
```

【注意】数据库创建，必须要插入一条数据后，`show dbs`命令才会显示，数据库已经添加.

### 2.查看数据库

```
show dbs//查看所有数据库
db//查看当前数据库
```

【注意】这个操作并不会显示系统所有的数据库，而只显示当前登录用户被授权的数据库。

### 3.删除数据库

```
db.dropDatabase()//删除当前数据库
```

### 4.创建集合

```js
db.createCollection(name, options)
db.createCollection("mycol", { capped : true, autoIndexId : true, size : 
   6142800, max : 10000 } )
```

参数说明：

- name: 要创建的集合名称
- options: 可选参数, 指定有关内存大小及索引的选项

options 可以是如下参数：

| 字段        | 类型 | 描述                                                         |
| ----------- | ---- | ------------------------------------------------------------ |
| capped      | 布尔 | （可选）如果为 true，则创建固定集合。固定集合是指有着固定大小的集合，当达到最大值时，它会自动覆盖最早的文档。 **当该值为 true 时，必须指定 size 参数。** |
| autoIndexId | 布尔 | （可选）如为 true，自动在 _id 字段创建索引。默认为 false。   |
| size        | 数值 | （可选）为固定集合指定一个最大值（以字节计）。 **如果 capped 为 true，也需要指定该字段。** |
| max         | 数值 | （可选）指定固定集合中包含文档的最大数量。                   |

### 5.查看集合

```
show collections
```

### 6.删除集合

```
db.collection.drop()
```

### 7.插入文档

MongoDB 使用 insert() 或 save() 方法向集合中插入文档.

```
db.COLLECTION_NAME.insert(document)
db.COLLECTION_NAME.save(document)
db.COLLECTION_NAME.insertMany([{"b": 3}, {'c': 4}])
```

【注意】：

1. 如果插入数据是不存在的集合，将会自动创建集合。
2. insert: 若新增数据的主键已经存在，则会抛 org.springframework.dao.DuplicateKeyException 异常提示主键重复，不保存当前数据。
3. save: 若新增数据的主键已经存在，则会对当前已经存在的数据进行修改操作。

### 8.查询文档

```
db.collection.find(query, projection)
```

- **query** ：可选，使用查询操作符指定查询条件

| 操作       | 格式                              | 范例                                        | RDBMS中的类似语句       |
| ---------- | --------------------------------- | ------------------------------------------- | ----------------------- |
| 等于       | `{<key>:<value>`}                 | `db.col.find({"by":"菜鸟教程"}).pretty()`   | `where by = '菜鸟教程'` |
| 小于       | `{<key>:{$lt:<value>}}`           | `db.col.find({"likes":{$lt:50}}).pretty()`  | `where likes < 50`      |
| 小于或等于 | `{<key>:{$lte:<value>}}`          | `db.col.find({"likes":{$lte:50}}).pretty()` | `where likes <= 50`     |
| 大于       | `{<key>:{$gt:<value>}}`           | `db.col.find({"likes":{$gt:50}}).pretty()`  | `where likes > 50`      |
| 大于或等于 | `{<key>:{$gte:<value>}}`          | `db.col.find({"likes":{$gte:50}}).pretty()` | `where likes >= 50`     |
| 不等于     | `{<key>:{$ne:<value>}}`           | `db.col.find({"likes":{$ne:50}}).pretty()`  | `where likes != 50`     |
| 数据类型   | `{<key>:{$type:<number/string>}}` | `db.col.find({"likes":{$type:1}}).pretty()` | `where likes != 50`     |

也可以使用正则：`db.col.find({title:/^教/})`

- **projection** ：可选，使用投影操作符指定返回的键。查询时返回文档中所有键值， 只需省略该参数即可（默认省略）。

  - 返回指定字段和_id字段：在结果集中，只有item和qty字段，默认_id字段也是返回的。代码如下:db.inventory.find( { type: ‘food’}, { item: 1, qty: 1} )
  - 仅返回指定字段：可以通过在projection中指定排除_id字段将其从结果中去掉。代码如下:db.inventory.find( { type: ‘food’}, { item: 1, qty:1, _id:0} )
  - 返回除排除掉以外的字段：可以使用一个projection排除一个或者一组字段，如下： 代码如下:db.inventory.find( { type: ‘food’}, { type:0} )8

- 实例：

  80>=likes>=50 AND (by = ‘菜鸟教程’ OR title = ‘MongoDB 教程’)

  ```
  db.col.find({"likes": {$gte:50,$lte:80}, $or: [{"by": "菜鸟教程"},{"title": "MongoDB 教程"}]}，{name：1，likes：1，_id:0}).pretty()
  ```

ps：如果你需要以易读的方式来读取数据，可以使用 pretty() 方法，pretty() 方法以格式化的方式来显示所有文档。语法格式如下：

```
db.COLLECTION_NAME.find().pretty()
```

### 9.更新文档

update() 方法用于更新已存在的文档。语法格式如下：

```
db.collection.update(
   <query>,
   <update>,
   {
     upsert: <boolean>,
     multi: <boolean>,
     writeConcern: <document>
   }
)
```

**参数说明：**

- **query** : update的查询条件，类似sql update查询内where后面的，规则与查询部分相同。

- **update** : update的对象和一些更新的操作符（如, ,,inc…）等，也可以理解为sql update查询内set后面的

- **upsert** : 可选，这个参数的意思是，如果不存在update的记录，是否插入objNew,true为插入，默认是false，不插入。

- **multi** : 可选，mongodb 默认是false,只更新找到的第一条记录，如果这个参数为true,就把按条件查出来多条记录全部更新。

- writeConcern

   

  :可选，抛出异常的级别。

  - WriteConcern.NONE:没有异常抛出
  - WriteConcern.NORMAL:仅抛出网络错误异常，没有服务器错误异常
  - WriteConcern.SAFE:抛出网络错误异常、服务器错误异常；并等待服务器完成写操作。
  - WriteConcern.MAJORITY: 抛出网络错误异常、服务器错误异常；并等待一个主服务器完成写操作。
  - WriteConcern.FSYNC_SAFE: 抛出网络错误异常、服务器错误异常；写操作等待服务器将数据刷新到磁盘。
  - WriteConcern.JOURNAL_SAFE:抛出网络错误异常、服务器错误异常；写操作等待服务器提交到磁盘的日志文件。
  - WriteConcern.REPLICAS_SAFE:抛出网络错误异常、服务器错误异常；等待至少2台服务器完成写操作。

- https://www.cnblogs.com/AK47Sonic/p/7560177.html

我们在集合 col 中插入如下数据：

```
>db.col.update({'title':'MongoDB 教程'},{$set:{'title':'MongoDB'}})
```

**update（更新的操作符）说明：**

实例：https://www.cnblogs.com/zhang-yong/p/7390393.html

##### 1.**$inc**

{ $inc : { field : value } }。意思对一个数字字段field增加value。

【注意】value可以为整数也可以为负数。

##### 2.**$set**

{ $set : { field : value } }。赋值语句

##### 3. **$unset**

{ $unset : { field : 任意内容} }。就是删除field字段

##### 4.**$push**

{ $push : { field : value } }。把value追加到field里面去，field一定要是数组类型才行，如果field不存在，会新增一个数组类型加进去。

##### 5. **$pushAll**

{ $pushAll : { field : value_array } }。将数组中的每一项，追加多个值到一个数组字段内。

##### 6.**$addToSet**

{ $addToSet : { field : value } }。增加一个值到数组内，而且只有当这个值不在数组内才增加。

##### 7.**$pop**

删除最后一个值：{ $pop : { field : 1 } }

删除第一个值：{ $pop : { field : -1 } }

##### 8.**$pull**

$pull : { field : value } }。从数组field内删除一个等于value值。

##### 9.**$pullAll**

{ $pullAll : { field : value_array } }。可以一次删除数组内的多个值。

##### 10.**$** **操作符**

$是他自己的意思，代表按条件找出的数组里面某项他自己。

```
> t.find()
{ "_id" : ObjectId("4b97e62bf1d8c7152c9ccb74"), "title" : "ABC",  "comments" : [ { "by" : "joe", "votes" : 3 }, { "by" : "jane", "votes" : 7 } ] }

> t.update( {'comments.by':'joe'}, {$inc:{'comments.$.votes':1}}, false, true )

> t.find()
{ "_id" : ObjectId("4b97e62bf1d8c7152c9ccb74"), "title" : "ABC",  "comments" : [ { "by" : "joe", "votes" : 4 }, { "by" : "jane", "votes" : 7 } ] }
```

### 10.删除文档

remove() 方法的基本语法格式如下所示：

```
db.collection.remove(
   <query>,
   {
     justOne: <boolean>,
     writeConcern: <document>
   }
)
```

**参数说明：**

- **query** :（可选）删除的文档的条件。

- **justOne** : （可选）如果设为 true 或 1，则只删除一个文档，如果不设置该参数，或使用默认值 false，则删除所有匹配条件的文档。

- **writeConcern** :（可选）抛出异常的级别。

  https://www.cnblogs.com/AK47Sonic/p/7560177.html

### 11.输出结果限制

```
db.COLLECTION_NAME.find().limit(NUMBER)
db.COLLECTION_NAME.find().limit(NUMBER).skip(NUMBER)
```

limit限制输出个数，skip跳过输出个数。两者可以单独使用也可以结合使用。

### 12.排序

```
db.COLLECTION_NAME.find().sort({title:-1,likes:1})
```

sort()函数接收一个对象，对象的第一个属性为主排序，主排序相同的内容再实现后面属性的排序。

【注意】：skip(), limilt(), sort() 三个放在一起执行的时候，执行的顺序是先 sort(), 然后是 skip()，最后是显示的 limit()。

### 13.索引

建立索引可以提升数据查找的速度，基本语法格式如下所示：

```
db.collection.createIndex(keys, options)
db.col.createIndex({"title":1})
```

keys对象参数为1表示正序建立索引，-1表示逆序建立索引。如果添加多个属性为联合索引。

**options可选属性如下表**

| Parameter          | Type          | Description                                                  |
| ------------------ | ------------- | ------------------------------------------------------------ |
| background         | Boolean       | 建索引过程会阻塞其它数据库操作，background可指定以后台方式创建索引，即增加 “background” 可选参数。 “background” 默认值为**false**。 |
| unique             | Boolean       | 建立的索引是否唯一。指定为true创建唯一索引。默认值为**false**. |
| name               | string        | 索引的名称。如果未指定，MongoDB的通过连接索引的字段名和排序顺序生成一个索引名称。用于索引删除 |
| dropDups           | Boolean       | **3.0+版本已废弃。**在建立唯一索引时是否删除重复记录,指定 true 创建唯一索引。默认值为 **false**. |
| sparse             | Boolean       | 对文档中不存在的字段数据不启用索引；这个参数需要特别注意，如果设置为true的话，在索引字段中不会查询出不包含对应字段的文档.。默认值为 **false**. |
| expireAfterSeconds | integer       | 指定一个以秒为单位的数值，完成 TTL设定，设定集合的生存时间。 |
| v                  | index version | 索引的版本号。默认的索引版本取决于mongod创建索引时运行的版本。 |
| weights            | document      | 索引权重值，数值在 1 到 99,999 之间，表示该索引相对于其他索引字段的得分权重。 |
| default_language   | string        | 对于文本索引，该参数决定了停用词及词干和词器的规则的列表。 默认为英语 |
| language_override  | string        | 对于文本索引，该参数指定了包含在文档中的字段名，语言覆盖默认的language，默认值为 language. |

**其他索引操作**

1、查看集合索引

```
db.col.getIndexes()
```

2、查看集合索引大小

```
db.col.totalIndexSize()
```

3、删除集合所有索引

```
db.col.dropIndexes()
```

4、删除集合指定索引

```
db.col.dropIndex("索引名称")
```

**设置定时删除任务**

利用 TTL 集合对存储的数据进行失效时间设置：经过指定的时间段后或在指定的时间点过期，MongoDB 独立线程去清除数据。

例如在数据记录中 createDate 为日期类型之后180删除：

```
db.col.createIndex({"createDate": 1},{expireAfterSeconds: 180})
```

由记录中设定日期点清除：使用{expireAfterSeconds: 0}

- 其他注意事项:
  - 索引关键字段必须是 Date 类型。
  - 非立即执行：扫描 Document 过期数据并删除是独立线程执行，默认 60s 扫描一次，删除也不一定是立即删除成功。
  - 单字段索引，混合索引不支持。



## 七、数据库备份与恢复

### **将数据库备份成文件**

```
mongodump -h localhost -d server1 -u server1 -p xxx --authenticationDatabase admin -o E:\log
```

参数说明：

-h：链接主机

-d：备份数据库名

-u：用户名

-p：密码

-o：导出地址

–authenticationDatabase admin：指定身份验证数据库

### **将文件导入到数据库**

将文件导入到数据库

```
mongorestore -h localhost -d server1 -u server1 -p xxx --authenticationDatabase admin --dir E:\log\server1
```

注意：

- –host <:port>, -h <:port>：

  MongoDB所在服务器地址，默认为： localhost:27017

- –db , -d ：

  需要恢复的数据库实例，例如：test，当然这个名称也可以和备份时候的不一样，比如test2

- –drop：

  恢复的时候，先删除当前数据，然后恢复备份的数据。就是说，恢复后，备份后添加修改的数据都会被删除，慎用哦！

- –dir：

  指定备份的目录

  你不能同时指定 和 --dir 选项。

## 八、数据库性能监控

【注意】在使用身份验证模式下，需要使用root权限来查看数据库性能。一般用户没有这个能力，无法打开性能监控。

### 1.mongostat 命令

```
mongostat -u root -p xxx --authenticationDatabase admin
mongostat --host 192.168.11.11:27017 --username root --password 12345678 --authenticationDatabase admin
```

**参数说明：**

–host :指定IP地址和端口，也可以只写IP，然后使用–port参数指定端口号

–username： 如果开启了认证，则需要在其后填写用户名

–password : 不用多少，肯定是密码

–authenticationDatabase：若开启了认证，则需要在此参数后填写认证库（注意是认证上述账号的数据库）

**输出字段解释说明：**

- insert/s : 官方解释是每秒插入数据库的对象数量，如果是slave，则数值前有*,则表示复制集操作

- query/s : 每秒的查询操作次数

- update/s : 每秒的更新操作次数

- delete/s : 每秒的删除操作次数

- getmore/s: 每秒查询cursor(游标)时的getmore操作数

- command: 每秒执行的命令数，在主从系统中会显示两个值(例如 3|0),分表代表 本地|复制 命令注： 一秒内执行的命令数比如批量插入，只认为是一条命令（所以意义应该不大）

- dirty: 仅仅针对WiredTiger引擎，官网解释是脏数据字节的缓存百分比

- used:仅仅针对WiredTiger引擎，官网解释是正在使用中的缓存百分比

- flushes:

  For WiredTiger引擎：指checkpoint的触发次数在一个轮询间隔期间

  For MMAPv1 引擎：每秒执行fsync将数据写入硬盘的次数

  注：一般都是0，间断性会是1， 通过计算两个1之间的间隔时间，可以大致了解多长时间flush一次。flush开销是很大的，如果频繁的flush，可能就要找找原因了

- vsize： `虚拟内存使用量，单位MB （这是 在mongostat 最后一次调用的总数据）`

- res: `物理内存使用量，单位MB （这是 在mongostat 最后一次调用的总数据）`注：这个和你用top看到的一样, vsize一般不会有大的变动， res会慢慢的上升，如果res经常突然下降，去查查是否有别的程序狂吃内存。

- qr: 客户端等待从MongoDB实例读数据的队列长度

- qw：客户端等待从MongoDB实例写入数据的队列长度

- ar: 执行读操作的活跃客户端数量

- aw: 执行写操作的活客户端数量注：如果这两个数值很大，那么就是DB被堵住了，DB的处理速度不及请求速度。看看是否有开销很大的慢查询。如果查询一切正常，确实是负载很大，就需要加机器了

- netIn:MongoDB实例的网络进流量

- netOut：MongoDB实例的网络出流量注：此两项字段表名网络带宽压力，一般情况下，不会成为瓶颈

- conn: 打开连接的总数，是qr,qw,ar,aw的总和注：MongoDB为每一个连接创建一个线程，线程的创建与释放也会有开销，所以尽量要适当配置连接数的启动参数，maxIncomingConnections，阿里工程师建议在5000以下，基本满足多数场景

### 2.mongotop 命令

```
mongotop -u root -p xxx --authenticationDatabase admin
mongotop --host 192.168.11.11:27017 --username root --password 12345678 --authenticationDatabase admin
```

**参数说明：**

–host :指定IP地址和端口，也可以只写IP，然后使用–port参数指定端口号

–username： 如果开启了认证，则需要在其后填写用户名

–password : 不用多少，肯定是密码

–authenticationDatabase：若开启了认证，则需要在此参数后填写认证库（注意是认证上述账号的数据库）

**输出结果字段说明：**

- ns：包含数据库命名空间，后者结合了数据库名称和集合。
- total：mongod花费的时间工作在这个命名空间提供总额。
- read：提供了大量的时间，这mongod花费在执行读操作，在此命名空间。
- write：提供这个命名空间进行写操作，这mongod花了大量的时间。

## 九、数据库高级查询

前面介绍的内容都是数据基本的增删改查，但是NoSQL出现了一个多个集合之间数据组织的问题。NoSQL不像关系型数据库，通过关系来实现多数据直接的结合。要想实现多文档，多集合的管理数据，就要使用应用层来回查询。使用这种方式将会消耗大量的网络数据，不利于数据库操作的效率。

但是MongoDB作为最像关系型数据库的非关系型数据库，也为我们提供了多集合联合查询的功能。

### 1.聚合通道

MongoDB中聚合的方法使用aggregate()。聚合就是可以对数据查询进行多次过滤操作，以达到复杂查询的目的。聚合查询函数接收一个数组，数组里面是若干个对象，每个对象就是一次查询的步骤。前一个查询的查询结果，作为后一个查询的筛选内容。

```js
db.getCollection("student").aggregate(
    [
        { 
            "$match" : {
                "age" : {
                    "$gt" : 20.0
                }
            }
        }, 
        { 
            "$lookup" : {
                "from" : "room", 
                "localField" : "class", 
                "foreignField" : "name", 
                "as" : "num"
            }
        }, 
        { 
            "$unwind" : {
                "path" : "$num", 
                "includeArrayIndex" : "l", 
                "preserveNullAndEmptyArrays" : false
            }
        }, 
        { 
            "$project" : {
                "num.name" : 1.0
            }
        }, 
        { 
            "$count" : "cou"
        }
    ]
```

常用的管道查询操作：

- $project：修改输入文档的结构。可以用来重命名、增加或删除域，也可以用于创建计算结果以及嵌套文档。
- m a t c h ： 用 于 过 滤 数 据 ， 只 输 出 符 合 条 件 的 文 档 。 match：用于过滤数据，只输出符合条件的文档。*m**a**t**c**h*：用于过滤数据，只输出符合条件的文档。match使用MongoDB的标准查询操作。
- $limit：用来限制MongoDB聚合管道返回的文档数。
- $skip：在聚合管道中跳过指定数量的文档，并返回余下的文档。
- $unwind：将文档中的某一个数组类型字段拆分成多条，每条包含数组中的一个值。
- $group：将集合中的文档分组，可用于统计结果。
- $sort：将输入文档排序后输出。
- $geoNear：输出接近某一地理位置的有序文档。
- $lookup：连表查询

### 2.视图

使用聚合函数可以实现服务器级别的复杂查询，但是查询数据时，编写查询语句会十分复杂，这时可以使用视图来虚拟查询。将需要的复杂查询提取成一个视图，应用层只需要查询视图内容即可。

【注意】：视图是一个虚表，系统并没有将视图数据存储，而是只记录了一下数据的来源方法，这个视图来源依据存储在System集合中。当基本集合更新之后，视图就会跟着更新了。


