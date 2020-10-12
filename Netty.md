# 一、Java IO概述

## 1、Netty介绍和应用场景

### 1.1、Netty介绍

1. `Netty`是由`JBOSS`提供的一个`Java`开源框架，现为`Github`上的独立项目
2. `Netty`是一个异步的、基于事件驱动的网络应用框架，用以快速开发高性能、高可靠性的网络`IO`程序
3. `Netty`主要针对在`TCP`协议下，面向`Clients`端的高并发应用，或者`Peer-to-Peer`场景下的大量数据持续传输的应用。
4. `Netty`本质是一个`NIO`框架，适用于服务器通讯相关的多种应用场景
5. 要透彻理解`Netty` ， 需要先学习 `NIO` ， 这样我们才能阅读 `Netty` 的源码。

### 1.2、Netty的应用场景

- 互联网行业
  - 分布式系统中，各个节点之间需要远程服务调用，高性能的`RPC`框架必不可少，`Netty`作为异步高性能的通信框架，往往作为基础通信组件被这些`RPC`框架使用
  - 典型的应用有：阿里分布式服务框架`Dubbo`的`RPC`框架使用`Dubbo`协议进行节点间通信，`Dubbo`协议进行节点间通信，`Dubbo`协议默认使用`Netty`作为基础通信组件，用于实现各进程节点之间的内部通信![在这里插入图片描述](https://img-blog.csdnimg.cn/20200215174640785.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- 游戏行业
  - 无论是手游服务端还是大型的网络游戏，`Java` 语言得到了越来越广泛的应用
  - `Netty` 作为高性能的基础通信组件，提供了 `TCP/UDP` 和 `HTTP` 协议栈，方便定制和开发私有协议栈，账号登录服务器
  - 地图服务器之间可以方便的通过 `Netty` 进行高性能的通信
- 大数据领域
  - 经典的 `Hadoop`的高性能通信和序列化组件`Avro` 的 `RPC` 框架，默认采用 `Netty` 进行跨界点通信
  - 它的 `Netty Service` 基于 `Netty` 框架二次封装实现。
     ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200215174702457.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

## 2、Java IO模型介绍

### 2.1、I/O模型基本说明

- `I/O` 模型简单的理解：就是用什么样的通道进行数据的发送和接收，很大程度上决定了程序通信的性能
- `Java`共支持3种网络编程模型IO模式：`BIO、NIO、AIO`
- `Java BIO` ： 同步并阻塞(**传统阻塞型**)，服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就需要启动一个线程进行处理，如果这个连接不做任何事情会造成不必要的线程开销
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200215174720432.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- `Java NIO` ： **同步非阻塞**，服务器实现模式为一个线程处理多个请求(连接)，即客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有`I/O`请求就进行处理
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020021517473418.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- `Java AIO(NIO.2)` ： **异步非阻塞**，`AIO` 引入异步通道的概念，采用了 `Proactor` 模式，简化了程序编写，有效的请求才启动线程，它的特点是先由操作系统完成后才通知服务端程序启动线程去处理，一般适用于连接数较多且连接时间较长的应用

### 2.2、I/O模型使用场景分析

- `BIO`方式适用于**连接数目比较小且固定**的架构，这种方式对服务器资源要求比较高，并发局限于应用中，`JDK1.4`以前的唯一选择，但程序简单易理解。
- `NIO`方式适用于**连接数目多且连接比较短**（轻操作）的架构，比如聊天服务器，弹幕系统，服务器间通讯等。编程比较复杂，`JDK1.4`开始支持。
- `AIO`方式使用于**连接数目多且连接比较长**（重操作）的架构，比如相册服务器，充分调用OS参与并发操作，编程比较复杂，`JDK7`开始支持。

## 3、BIO 工作机制

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200215174747726.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

### 3.1、BIO编程简单流程（Socket编程）

- 服务器端启动一个`ServerSocket`
- 客户端启动`Socket`对服务器进行通信，默认情况下服务器端需要对每个客户 建立一个线程与之通讯
- 客户端发出请求后, 先咨询服务器是否有线程响应，如果没有则会等待，或者被拒绝
- 如果有响应，客户端线程会等待请求结束后，在继续执行

### 3.2、BIO 应用实例

#### 实例说明

- 使用`BIO`模型编写一个服务器端，监听`6666`端口，当有客户端连接时，就启动一个线程与之通讯。
- 要求使用线程池机制改善，可以连接多个客户端.
- 服务器端可以接收客户端发送的数据(通过`cmd`的`telnet` 方式即可)。

#### 实例代码

```java
public class BIOServer {
    public static void main(String[] args) throws IOException {
        //1、创建一个线程池
        //2、如果有客户端连接，就创建一个线程，与之通讯（单独写一个方法）
        ExecutorService executorService = Executors.newCachedThreadPool();

        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");

        while (true) {
            System.out.println("线程信息：id= "+ Thread.currentThread().getId() + "; 线程名字：" + Thread.currentThread().getName());
            //监听，等待客户端连接
            System.out.println("等待连接");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            //创建一个线程，与之通讯
            executorService.execute(() -> {
                //重写Runnable方法，与客户端进行通讯
                handler(socket);
            });
        }
    }

    //编写一个Handler方法，和客户端通讯
    public static void handler(Socket socket) {
        try {
            System.out.println("线程信息：id= "+ Thread.currentThread().getId() + "; 线程名字：" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            //循环的读取客户端发送的数据
            while (true){
                System.out.println("线程信息：id= "+ Thread.currentThread().getId() + "; 线程名字：" + Thread.currentThread().getName());
                System.out.println("read....");
                int read = inputStream.read(bytes);
                if (read != -1){
                    System.out.println(new String(bytes, 0, read));//输出客户端发送的数据
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
12345678910111213141516171819202122232425262728293031323334353637383940414243444546474849505152535455
```

> 服务端主线程会阻塞在 `serverSocket.accept()` 这个方法处，当有新的客户端发起请求时，主线程通过线程池调用新线程与其通信，每个通信线程会阻塞在 `socket.getInputStream()` 这个方法处。这就是**阻塞**这两个字的含义所在

#### BIO 问题分析

- 每个请求都需要创建独立的线程，与对应的客户端进行数据 `Read`，业务处理，数据 `Write` 。
- 当并发数较大时，需要**创建大量线程来处理连接**，系统资源占用较大。
- 连接建立后，如果当前线程暂时没有数据可读，则线程就阻塞在 `Read` 操作上，造成线程资源浪费

## 4、NIO编程

### 4.1、NIO基本介绍

- Java NIO 全称 `java non-blocking IO`，是指 JDK 提供的新 API。从 JDK1.4 开始，Java 提供了一系列改进的输入/输出的新特性，被统称为 NIO(即 New IO)，是**同步非阻塞**的
- NIO 相关类都被放在 `java.nio` 包及子包下，并且对原 `java.io` 包中的很多类进行改写。
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200215174814116.png)
- NIO 有三大核心部分：**Channel(通道)**，**Buffer(缓冲区)**, **Selector(选择器)**
- NIO是 面向**缓**冲区 ，或者面向 **块** 编程的。数据读取到一个它稍后处理的缓冲区，需要时可在缓冲区中前后移动，这就增加了处理过程中的灵活性，使用它可以提供**非阻塞**式的高伸缩性网络
- `Java NIO`的非阻塞模式，使一个线程从某通道发送请求或者读取数据，但是它仅能得到目前可用的数据，如果目前没有数据可用时，就什么都不会获取，而**不是保持线程阻塞**，所以直至数据变的可以读取之前，该线程可以继续做其他的事情。 非阻塞写也是如此，一个线程请求写入一些数据到某通道，但不需要等待它完全写入，这个线程同时可以去做别的事情。
- 通俗理解：`NIO`是可以做到用一个线程来处理多个操作的。假设有`10000`个请求过来,根据实际情况，可以分配`50`或者`100`个线程来处理。不像之前的阻塞`IO`那样，非得分配`10000`个。
- `HTTP2.0`使用了多路复用的技术，做到同一个连接并发处理多个请求，而且并发请求的数量比`HTTP1.1`大了好几个数量级。

### 4.2、NIO 和 BIO 的比较

- `BIO` 以流的方式处理数据,而 `NIO` 以块的方式处理数据,块 `I/O` 的效率比流 `I/O` 高很多
- `BIO` 是阻塞的，`NIO` 则是非阻塞的
- `BIO`基于字节流和字符流进行操作，而 `NIO` 基于 Channel(通道)和 Buffer(缓冲区)进行操作，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中。**Selector**(选择器)用于监听多个通道的事件（比如：连接请求，数据到达等），因此使用**单个线程就可以监听多个客户端**通道





# 二、NIO详解

**关键词：Buffer、Channel、SelectionKey、Selector、事件**

**Buffer：**

- 缓存数组，就是一个内存块，底层用数组实现
- 与`Channel`进行数据的读写。
- 数据的读取写入是通过`Buffer`, 这个和`BIO` 一样, 而`BIO` 中要么是输入流，或者是输出流, 不能双向，但是`NIO`的`Buffer` 是可以读也可以写, 需要 `flip` 方法切换。

**Channel：**

- 通信通道，每个客户端连接都会建立一个`Channel`通道
- 我的理解是：客户端直接与`Channel`进行通信，当客户端发送消息时，消息就流通到`Channel`里面，本地程序需要将`Channel`里面的数据存放在`Buffer`里面，才可以查看；当本地需要发送消息时，先把消息存在`Buffer`里面，再将`Buffer`里面的数据放入`Channel`，数据就流通到了客户端
- 总而言之：`Buffer`就是本地程序与`Channel`数据交换的一个中间媒介。
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220151830203.png)
   **SelectionKey、Selector：**
- NIO之所以是非阻塞的，关键在于它一个线程可以同时处理多个客户端的通信。而`Selector`就是它一个线程如何处理多个客户端通信的关键，一个`Selector`就对应一个线程
- 首先在创建与客户端连接的`Channel`时，应该调用 `Channel.register()`方法，将Channel注册到一个`Selector`上面。调用该方法后，会返回一个`SelectionKey`对象，该对象与`Channel`是一一对应的。而`Selector`则通过管理`SelectionKey`的集合间接的去管理各个`Channel`。示例图如下：
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220151924254.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- `Selector`具体是如何管理这么多个通信的呢？这就引出了**事件**。

**事件、以及NIO的工作流程介绍**

- **事件：**当将`Channel`绑定到`Selector`上面时，必须同时为该`Channel`声明一个监听该`Channel`的事件（由`Channel`和该`Channel的事件`一起组成了`SelectionKey`），并将`SelectionKey`加入到`Selector`的`Set`集合中去
- 当有客户端建立连接或者进行通信，会在对应的各个`Channel`中产生不同的事件。
- `Selector`会一直监听所有的事件，当他监听到某个`SelectionKey`中有事件产生时，会将所有产生事件的`SelectionKey`统一加入到一个集合中去
- 而我们则需要获取到这个集合，首先对集合中的各个`SelectionKey`进行判断，判断它产生的是什么事件，再根据不同的事件进行不同的处理。
- 在操作这个`SelectionKey`集合的时候，其实我们就是在一个线程里面对几个不同客户端的连接进行操作。具体的关系图如下：
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220151943749.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

## 1、缓冲区（Buffer）

### 1.1、基本介绍

 缓冲区（Buffer）：缓冲区本质上是一个可以读写数据的内存块，可以理解成是一个容器对象（含数组），该对象提供了一组方法，可以更轻松地使用内存块，缓冲区对象内置了一些机制，能够跟踪和记录缓冲区的状态变化情况。`Channel`提供从文件、网络读取数据的渠道，但是读取或写入的数据都必须经由`Buffer`。
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152004134.png)

### 1.2、Buffer类介绍

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152029470.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

- 基类是`Buffer`抽象类
- 基类派生出基于基本数据类型的7个`xxxBuffer` 抽象类，没有`boolean`相关的`buffer`类。
- 除了`ByteBuffer`外，每个基本数据的抽象类 `xxxBuffer` 类下面都派生出转向 `ByteBuffer` 的类 `ByteBufferXxxAsBufferL` 和 `ByteBufferAsXxxBufferB`实现类；以及 `DirectXxxBufferU` 和 `DirectXxxBufferS` 和 `HeapXxxBuffer`==（具体实例对象类）==这五个类。
- 就只有抽象类`CharBuffer` 派生出了第六个类`StringCharBuffer`。
- `ByteBuffer`只派生出了 `HeapByteBuffer` 和 `MappedByteBufferR` 两个类
- 类图如下：
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152119469.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

#### 1.2.1、Buffer类主要属性

| **属性** | **描述**                                                     |
| -------- | ------------------------------------------------------------ |
| Capacity | 容量，即可以容纳的最大数据量；在缓冲区创建时被设定并且不能改变 |
| Limit    | 表示缓冲区的当前终点，不能对缓冲区超过极限的位置进行读写操作。且极限是可以修改的 |
| Position | 位置，下一个要被读或写的元素的索引，每次读写缓冲区数据时都会改变改值，为下次读写作准备 |
| Mark     | 标记 ，一般不会主动修改，在`flip()`被调用后，mark就作废了。  |

> mark <= position <= limit <= capacity

#### 1.2.2、Buffer类使用示例

```java
//创建一个Buffer，大小为5，即可以存放5个int
IntBuffer intBuffer = IntBuffer.allocate(5);
//向buffer中存放数据
for (int i = 0; i < intBuffer.capacity(); i++) {
    intBuffer.put(i * 2);
}
//如何从buffer中读取数据
//将buffer转换，读写切换
intBuffer.flip();
while (intBuffer.hasRemaining()) {
    System.out.println(intBuffer.get());
}
123456789101112
```

- `Buffer` 刚创建时，`capacity = 5` ，固定不变。`limit`指针指向`5`，`position`指向`0`，`mark`指向`-1`![在这里插入图片描述](https://img-blog.csdnimg.cn/2020022015215785.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- 之后调用 `intBuffer.put`方法，向`buffer`中添加数据，会不断移动`position`指针，最后`position`变量会和`limit`指向相同。![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152219546.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- 调用 `buffer.flip()`实际上是重置了`position`和`limit`两个变量，将`limit`放在`position`的位置，`position`放在`0`的位置。这里只是最后的`position`和`limit`位置相同，所以`flip`后`limit`位置没变。
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152235504.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- 调用 `intBuffer.get()`实际上是不断移动`position`指针，直到它移动到`limit`的位置

#### 1.2.3、Buffer类主要方法

##### Buffer基类（抽象类）

``` java
 public final int capacity();
  //直接返回了此缓冲区的容量，capacity
 public final int position();
  //直接返回了此缓冲区指针的当前位置
public final Buffer position(int newPosition);
  //设置此缓冲区的位置，设置position
 public final int limit();
  // 返回此缓冲区的限制
 public final Buffer limit(int newLimit);
  // 设置此缓冲区的限制，设置limit
 public final Buffer clear();
  //清除此缓冲区，即将各个标记恢复到初识状态， position = 0;limit = capacity; mark = -1，但是并没有删除数据。
 public final Buffer flip();
  // 反转此缓冲区， limit = position;position = 0;mark = -1。
  // 当指定数据存放在缓冲区中后，position所指向的即为此缓冲区数据最后的位置。只有当数据大小和此缓冲区大小相同时，position才和limit的指向相同。
  // flip()方法将limit置向position， position置0，那么从position读取数据到limit即为此缓冲区中所有的数据。
 public final boolean hasRemaining();
  // 告知当前位置和限制之间是否有元素。return position < limit;
 public abstract boolean isReadOnly();
  // 此方法为抽象方法，告知此缓冲区是否为只读缓冲区，具体实现在各个实现类中。
 public abstract boolean hasArray();
  // 告知此缓冲区是否具有可访问的底层实现数组
 public abstract Object array();
   //返回此缓冲区的底层实现数组
```



> 

##### Buffer具体实现类（ByteBuffer为例）

```java
 //从前面可以看出来对于Java中的基本数据类型（**boolean除外**），都有一个`Buffer`类型与之对应，最常用的自然是`ByteBuffer`类（二进制数据），该类的主要方法如下：

public static ByteBuffer allocateDirect(int capacity);

  // 创建直接缓冲区

public static ByteBuffer allocate(int capacity) ;

  // 设置缓冲区的初识容量

public abstract byte get();

 //从当前位置`position`上`get`数据，获取之后，`position`会自动加`1`
public abstract byte get(int index);

  // 通过绝对位置获取数据。

public abstract ByteBuffer put(byte b);

  // 从当前位置上添加，`put`之后，`position`会自动加`1`

public abstract ByteBuffer put(int index, byte b);
  // 从绝对位置上添加数据

public abstract ByteBuffer putXxx(Xxx value [, int index]);

  //从`position`当前位置插入元素。`Xxx`表示基本数据类型

  //此方法时类型化的 `put` 和 `get`，`put`放入的是什么数据类型，`get`就应该使用相应的数据类型来取出，否则可能有 `BufferUnderflowException` 异常。

```



```java
ByteBuffer buf = ByteBuffer.allocate(64);

//类型化方式放入数据
buf.putInt(100);
buf.putLong(20);
buf.putChar('上');
buf.putShort((short)44);

//取出，当取出的顺序和上面插入的数据类型的顺序不对时，就会抛出BufferUnderflowException异常
buf.flip();
System.out.println(buf.getInt());
System.out.println(buf.getLong());
System.out.println(buf.getChar());
System.out.println(buf.getShort());
1234567891011121314
```

- 可以将一个普通的Buffer转成只读的Buffer

  ```java
  //创建一个Buffer
  ByteBuffer byteBuffer = ByteBuffer.allocate(64);
  for (int i = 0; i < 64; i++) {
      byteBuffer.put((byte)i);
  }
  //读取
  byteBuffer.flip();
  //得到一个只读的Buffer
  ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
  System.out.println(readOnlyBuffer.getClass());
  //读取
  while (readOnlyBuffer.hasRemaining()){
      System.out.println(readOnlyBuffer.get());
  }
  readOnlyBuffer.put((byte)100); //会抛出 ReadOnlyBufferException
  123456789101112131415
  ```

- `MappedByteBuffer`可以让文件直接在内存（堆外内存）中进行修改，而如何同步到文件由NIO来完成

  ```java
  /**
  * 1、MappedByteBuffer可以让文件直接在内存中（堆外内存）修改，操作系统不需要拷贝一次
  */
  @Test
  public void test() throws IOException {
      RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
      //获取对应的文件通道
      FileChannel channel = randomAccessFile.getChannel();
      /**
      * 参数1: FileChannel.MapMode.READ_WRITE，使用的读写模式
      * 参数2: 0，可以直接修改的起始位置
      * 参数3: 5，是映射到内存的大小(不是文件中字母的索引位置），即将 1.txt 的多少个字节映射到内存，也就是可以直接修改的范围就是 [0, 5)
      * 实际的实例化类型：DirectByteBuffer
      */
      MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
  
      mappedByteBuffer.put(0,(byte)'N');
      mappedByteBuffer.put(3, (byte)'M');
      mappedByteBuffer.put(5, (byte)'Y'); //会抛出 IndexOutOfBoundsException
  
      randomAccessFile.close();
      System.out.println("修改成功~");
  }
  1234567891011121314151617181920212223
  ```

## 2、通道（Channel）

### 2.1、基本介绍

- NIO的通道类似于流，但有些区别
  - 通道可以同时进行读写，而流只能读或者只能写
  - 通道可以实现异步读写数据
  - 通道可以从缓存读数据，也可以写数据到缓存
     ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152304113.png)
- BIO 中的 `stream` 是单向的，例如：`FileInputStream`对象只能进行读取数据的操作，而NIO中的通道（Channel）是双向的，可以读操作，也可以写操作。
- `Channel` 在 NIO 中是一个接口：`public interface Channel extends Closeable{}`
- 常用的`Channel`类有：`FileChannel`、`DatagramChannel`、`ServerSocketChannel`（类似`ServerSocket`）、`SocketChannel`（类似`Socket`）
- `FileChannel` 用于**文件**数据的读写，`DatagramChannel`用于**UDP**数据的读写，`ServerSocketChannel`和`SocketChannel`用于**TCP**数据读写
- 类关系图：
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152322249.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

### 2.2、FileChannel类

#### 常见方法

- ```
  public int read(ByteBuffer dst)
  ```

  - 从通道读取数据并放到缓冲区中
  - 此操作也会移动 `Buffer` 中的`position`指针，不断往`position`中放数据，`read`完成后`position`指向`limit`。

- ```
  public int write(ByteBuffer src)
  ```

  - 把缓冲区的数据写到通道中
  - 此操作也会不断移动`Buffer`中的`position`位置直到`limit`，读取到的数据就是`position`到`limit`这两个指针之间的数据。

- ```
  public long transferFrom(ReadableByteChannel src, long position, long count)
  ```

  - 从目标通道中复制数据到当前通道

- ```
  public long transferTo(long position, long count, WritableByteChannel target)
  ```

  - 把数据从当前通道复制给目标通道
  - 该方法拷贝数据使用了**零拷贝**，通常用来在网络`IO`传输中，将`FileChannel`里面的文件数据直接拷贝到与客户端或者服务端连接的`Channel`里面从而达到文件传输。

#### 应用实例

**实例1：将数据写入到本地文件**

```java
String str = "hello,尚硅谷";
//创建一个输出流 -> Channel
FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");

//通过 FileOutputStream 获取对应的 FileChannel
//这个 FileChannel 真实类型是 FileChannelImpl
FileChannel fileChannel = fileOutputStream.getChannel();

//创建一个缓冲区 ByteBuffer
ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//将str放入ByteBuffer
byteBuffer.put(str.getBytes());
//对ByteBuffer进行反转，开始读取
byteBuffer.flip();
//将ByteBuffer数据写入到FileChannel
//此操作会不断移动 Buffer中的 position到 limit 的位置
fileChannel.write(byteBuffer);
fileOutputStream.close();
123456789101112131415161718
```

实例1、2的示例图：![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152348988.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

**实例2：从本地文件读取数据**

```java
//创建文件的输入流
File file = new File("d:\\file01.txt");
FileInputStream fileInputStream = new FileInputStream(file);
//通过fileInputStream 获取对应的FileChannel -> 实际类型 FileChannelImpl
FileChannel fileChannel = fileInputStream.getChannel();
//创建缓冲区
ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
//将通道的数据读入到buffer
fileChannel.read(byteBuffer);

//将ByteBuffer 的字节数据转成String
System.out.println(new String(byteBuffer.array()));
fileInputStream.close();
12345678910111213
```

**实例3：使用一个Buffer完成文件的读取**
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152405775.png)

```java
FileInputStream fileInputStream = new FileInputStream("1.txt");
FileChannel fileChannel1 = fileInputStream.getChannel();

FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
FileChannel fileChannel2 = fileOutputStream.getChannel();

ByteBuffer byteBuffer = ByteBuffer.allocate(512);
while (true){
    //清空buffer，由于循环的最后执行了 write 操作，会将 position 移动到 limit 的位置
    //清空 Buffer的操作才为上一次的循环重置position的位置
    // 如果没有重置position，那么上次读取后，position和limit位置一样，读取后read的值永远为0
    byteBuffer.clear();
    //将数据存入 ByteBuffer，它会基于 Buffer 此刻的 position 和 limit 的值，
    // 将数据放入position的位置，然后不断移动position直到其与limit相等；
    int read = fileChannel1.read(byteBuffer);
    System.out.println("read=" + read);
    if (read == -1) { //表示读完
        break;
    }
    //将buffer中的数据写入到 FileChannel02 ---- 2.txt
    byteBuffer.flip();
    fileChannel2.write(byteBuffer);
}

//关闭相关的流
fileInputStream.close();
fileOutputStream.close();
123456789101112131415161718192021222324252627
```

**实例4：拷贝文件 transferFrom 方法**

```java
//创建相关流
FileInputStream fileInputStream = new FileInputStream("d:\\a.gif");
FileOutputStream fileOutputStream = new FileOutputStream("d:\\a2.gif");

//获取各个流对应的FileChannel
FileChannel source = fileInputStream.getChannel();
FileChannel dest = fileOutputStream.getChannel();

//使用 transferForm 完成拷贝
dest.transferFrom(source, 0, source.size());
//关闭相关的通道和流
source.close();
dest.close();
fileInputStream.close();
fileOutputStream.close();
123456789101112131415
```

### 2.3、ServerSocketChannel 和 SocketChannel 类

#### 常见方法

**ServerSocketChannel**：主要用于在服务器监听新的客户端`Socket`连接

>> - public static ServerSocketChannel open()
>>   - 得到一个 ServerSocketChannel 通道
>> - public final ServerSocketChannel bind(SocketAddress local)
>>   - 设置服务器监听端口
>> - public final SelectableChannel configureBlocking(boolean block)
>>   - 用于设置阻塞或非阻塞模式，取值 false 表示采用非阻塞模式
>>   - 此方法位于 ServerSocketChannel 和 SocketChannel的共同父类AbstractSelectableChannel类中
>> - public abstract SocketChannel accept()
>>   - 接受一个连接，返回代表这个连接的通道对象
>> - public final SelectionKey register(Selector sel, int ops)
>>   - 将Channel注册到选择器并设置监听事件，也可以在绑定的同时注册多个事件，如下所示：
>>   - channel.register(selector,Selectionkey.OP_READ | Selectionkey.OP_CONNECT)
>
>

**SocketChannel**：网络IO通道，**具体负责进行读写操作**。NIO把缓冲区的数据写入通道，或者把通道里的数据读到缓冲区

- `public static SocketChannel open()`
  - 得到一个SocketChannel通道
- `public final SelectableChannel configureBlocking(boolean block)`
  - 设置阻塞或非阻塞模式，取值 false表示采用非阻塞模式
  - 此方法位于 `ServerSocketChannel` 和 `SocketChannel`的共同父类`AbstractSelectableChannel`类中
- `public abstract boolean connect(SocketAddress remote)`
  - 连接服务器
- `public boolean finishConnect()`
  - 如果上面的方法连接失败，接下来就要通过该方法完成连接操作
- `public int write(ByteBuffer src)`
  - 往通道里写数据
  - 这里写入的是`buffer`里面`position`到`limit`这个之间的数据
- `public int read(ByteBuffer dst)`
  - 从通道里读数据
- `public final SelectionKey register(Selector sel, int ops, Object att)`
  - 注册`Channel`到选择器并设置监听事件，最后一个参数可以设置共享数据
- `public final void close()`
  - 关闭通道

#### 应用实例

- 通过`Buffer`数组来完成读写操作，即`Scattering`和`Gathering`

```java
/**
* Scattering：将数据写入到buffer时，可以采用buffer数组，初次写入 【分散】
* Gathering：从buffer读取数据时，也可以采用buffer数组，依次读
*/
@Test
public void test() throws IOException {
    //使用 ServerSocketChannel 和 SocketChannel
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
    //绑定端口到socket，并启动
    serverSocketChannel.socket().bind(inetSocketAddress);
    //创建一个Buffer数组
    ByteBuffer[] byteBuffers = new ByteBuffer[2];
    byteBuffers[0] = ByteBuffer.allocate(5);
    byteBuffers[1] = ByteBuffer.allocate(3);

    //等待客户端的连接（Telnet）
    SocketChannel socketChannel = serverSocketChannel.accept();
    int msgLength = 8; //假定从客户端接受8个字节
    //循环的读取
    while (true) {
        int byteRead = 0;
        while (byteRead < msgLength) {
            long l = socketChannel.read(byteBuffers);
            byteRead += l; //累计读取的字节数
            System.out.println("byteRead= " + byteRead);
            //使用流打印，看看当前这个buffer的position和limit
            Arrays.stream(byteBuffers)
                .map(buffer -> "position=" + buffer.position() + ", limit = " + buffer.limit())
                .forEach(System.out::println);
        }
        //读书数据后需要将所有的buffer进行flip
        Arrays.asList(byteBuffers).forEach(Buffer::flip);

        //将数据读出显示到客户端
        long byteWrite = 0;
        while (byteWrite < msgLength) {
            long l = socketChannel.write(byteBuffers);
            byteWrite += l;
        }

        //将所有的 buffer 进行clear操作
        Arrays.asList(byteBuffers).forEach(Buffer::clear);
        System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWrite
                           + ", msgLength=" + msgLength);
    }
}
1234567891011121314151617181920212223242526272829303132333435363738394041424344454647
```

## 3、Selector（选择器）

### 3.1、基本介绍

- Java 的 NIO，用非阻塞的 IO 方式。可以用一个线程，处理多个的客户端连接，就会使用到**Selector**(选择器)
- Selector能够检测多个注册的通道上是否有事件发生(注意:多个Channel以**事件**的方式可以注册到同一个Selector)，如果有事件发生，便获取事件然后针对每个事件进行相应的处理。这样就可以只用一个单线程去管理多个通道，也就是管理多个连接和请求。
- 只有在 连接/通道 真正有读写事件发生时，才会进行读写，就大大地减少了系统开销，并且不必为每个连接都创建一个线程，不用去维护多个线程
- 避免了多线程之间的上下文切换导致的开销
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020022015243768.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- `Netty`的`IO`线程`NioEventLoop`聚合了`Selector`（选择器，也叫多路复用器），可以同时并发处理成百上千个客户端连接。
- 当线程从某客户端 `Socket` 通道进行读写数据时，若没有数据可用时，该线程可以进行其他任务。
- 线程通常将非阻塞 `IO` 的空闲时间用于在其他通道上执行 `IO` 操作，所以单独的线程可以管理多个输入和输出通道。
- 由于读写操作都是非阻塞的，这就可以充分提升 `IO` 线程的运行效率，避免由于频繁 `I/O` 阻塞导致的线程挂起。
- 一个 `I/O` 线程可以并发处理 `N` 个客户端连接和读写操作，这从根本上解决了传统同步阻塞 `I/O` 一连接一线程模型，架构的性能、弹性伸缩能力和可靠性都得到了极大的提升。
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152455847.png)

### 3.2、SelectionKey介绍

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152509586.png)
 **主要作用：**

 `Selector`通过管理`SelectionKey`的集合从而去监听各个`Channel`。当`Channel`注册到`Selector`上面时，会携带该`Channel`关注的事件**（SelectionKey包含Channel以及与之对应的事件）**，并会返回一个`SelectionKey`的对象，`Selector`将该对象加入到它统一管理的集合中去，从而对`Channel`进行管理。SelectionKey表示的是Selector和网络通道的注册关系，固FileChannel是没有办法通过SelectionKey注册到Selector上去的。

**四大事件：**

- `public static final int OP_READ = 1 << 0`
  - 值为`1`，表示读操作，
  - 代表本`Channel`已经接受到其他客户端传过来的消息，需要将`Channel`中的数据读取到`Buffer`中去
- `public static final int OP_WRITE = 1 << 2`
  - 值为`4`，表示写操作
  - 一般临时将`Channel`的事件修改为它，在处理完后又修改回去。我暂时也没明白具体的作用。
- `public static final int OP_CONNECT = 1 << 3`
  - 值为`8`，代表建立连接。
  - 一般在`ServerSocketChannel`上绑定该事件，结合 `channel.finishConnect()`在连接建立异常时进行异常处理
- `public static final int OP_ACCEPT = 1 << 4`
  - 值为`16`，表示由新的网络连接可以`accept`。
  - 与`ServerSocketChannel`进行绑定，用于创建新的`SocketChannel`，并把其注册到`Selector`上去

**相关方法**

- `public abstract Selector selector()`
  - 得到该`SelectionKey`具体是属于哪个`Selector`对象的
- `public abstract SelectableChannel channel()`
  - 通过`SelectionKey`的到对应的`Channel`
- `public final Object attachment()`
  - 得到与之关联的共享数据，一般用于获取`buffer`
  - 在使用`register`注册通道时，也可以为该`Channel`绑定一个`Buffer`，可以通过本方法获取这个`Buffer`。
  - 通过`selectionKey.attach(Object ob)`绑定的数据，也是通过该方法获取
- `public abstract SelectionKey interestOps()`
  - 获取该`SelectionKey`下面的事件
- `public abstract SelectionKey interestOps(int ops)`
  - 用于设置或改变某个`Channel`关联的事件
  - 增加事件：`key.interestOps(key.interestOps | SelectionKey.OP_WRITE)`
  - 减少事件：`key.interestOps(key.interestOps & ~SelectionKey.OP_WRITE)`
- `public final boolean isAcceptable(),isReadable(),isWritable(),isConnectable()`
  - 用于判断这个`SelectionKey`产生的是什么事件，与上面的事件类型一一对应

### 3.3、Selector常见方法

- `public static Selector open();`
  - 得到一个选择器对象，实例化出 `WindowsSelectorImpl`对象。
- `public int select(long timeout)`
  - 监控所有注册的通道，当其中有`IO`操作可以进行时，将对应的`SelectionKey`加入到内部集合中并返回，返回的结果为`Channel`响应的事件总和，当结果为`0`时，表示本`Selector`监听的所有`Channel`中没有`Channel`产生事件。
  - 如果不传入`timeout`值，就会阻塞线程，传入值则为阻塞多少毫秒，通过它设置超时时间。
  - 之所以需要传入时间，是为了让它等待几秒钟再看有没有`Channel`会产生事件，从而获取一段时间内产生事件的`Channel`的总集合再一起处理。
- `selector.selectNow();`
  - 不会阻塞，立马返回冒泡的事件数
- `public Set selectedKeys()`
  - 从内部集合中得到所有的`SelectionKey`

## 4、Demo实例

**编码步骤：**

1. 当客户端连接时，会通过ServerSocketChannel 得到 SocketChannel
2. Selector 进行监听 select 方法, 返回有事件发生的通道的个数.
3. 将socketChannel注册到Selector上, register(Selector sel, **int** ops), 一个selector上可以注册多个SocketChannel
4. 注册后返回一个 SelectionKey, 会和该Selector 关联(集合)
5. 进一步得到各个 SelectionKey (有事件发生)
6. 在通过 SelectionKey 反向获取 SocketChannel , 方法 channel()
7. 判断该Channel的事件类型，对不同事件进行不同的业务处理

### 4.1、NIO入门案例：实现服务器和客户端的简单通讯

```java
@Test
public void Server() throws IOException {
    //创建ServerSocketChannel -> ServerSocket
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    //得到一个Selector对象
    Selector selector = Selector.open();
    //绑定一个端口6666
    serverSocketChannel.socket().bind(new InetSocketAddress(6666));
    //设置非阻塞
    serverSocketChannel.configureBlocking(false);

    //把 serverSocketChannel 注册到 selector ，关心事件为：OP_ACCEPT，有新的客户端连接
    SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    System.out.println();
    //循环等待客户端连接
    while (true) {
        //等待1秒，如果没有事件发生，就返回
        if (selector.select(1000) == 0) {
            System.out.println("服务器等待了1秒，无连接");
            continue;
        }
        //如果返回的 > 0,表示已经获取到关注的事件
        // 就获取到相关的 selectionKey 集合，反向获取通道
        Set<SelectionKey> selectionKeys = selector.selectedKeys();

        //遍历 Set<SelectionKey>，使用迭代器遍历
        Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
        while (keyIterator.hasNext()) {
            //获取到SelectionKey
            SelectionKey key = keyIterator.next();
            //根据 key 对应的通道发生的事件，做相应的处理
            if (key.isAcceptable()) {//如果是 OP_ACCEPT，有新的客户端连接
                //该客户端生成一个 SocketChannel
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("客户端连接成功，生成了一个SocketChannel：" + socketChannel.hashCode());
                //将SocketChannel设置为非阻塞
                socketChannel.configureBlocking(false);
                //将socketChannel注册到selector，关注事件为 OP_READ，同时给SocketChannel关联一个Buffer
                socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
            }
            if (key.isReadable()) {
                //通过key，反向获取到对应的Channel
                SocketChannel channel = (SocketChannel) key.channel();
                //获取到该channel关联的Buffer
                ByteBuffer buffer = (ByteBuffer) key.attachment();
                channel.read(buffer);
                System.out.println("from 客户端：" + new String(buffer.array()));
            }
            //手动从集合中移除当前的 selectionKey，防止重复操作
            keyIterator.remove();
        }

    }
}

@Test
public void Client() throws IOException {
    //得到一个网络通道
    SocketChannel socketChannel = SocketChannel.open();
    //设置非阻塞
    socketChannel.configureBlocking(false);
    //提供服务器端的IP和端口
    InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 6666);
    //连接服务器
    if (!socketChannel.connect(socketAddress)){ //如果不成功
        while (!socketChannel.finishConnect()){
            System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
        }
    }

    //如果连接成功，就发送数据
    String str = "hello, 尚硅谷";
    ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
    //发送数据,实际上就是将buffer数据写入到channel
    socketChannel.write(byteBuffer);
    System.in.read();
}
1234567891011121314151617181920212223242526272829303132333435363738394041424344454647484950515253545556575859606162636465666768697071727374757677
```

### 4.2、群聊系统Demo

- 需要实现客户端和服务器端之间的数据通讯，服务端能够将数据转发给其他所有客户端。

```java
/********************服务端********************/
public class GroupChatServer {

    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6666;

    //构造器
    //初始化工作
    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将listenChannel注册到selector，绑定监听事件
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen() {
        try {
            //循环处理
            while (true) {
                int count = selector.select();
                if (count > 0) { //有事件处理
                    //遍历得到SelectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出SelectionKey
                        SelectionKey key = iterator.next();

                        //监听到accept，连接事件
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            //将该channel设置非阻塞并注册到selector
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            //提示
                            System.out.println(socketChannel.getRemoteAddress() + " 上线...");
                        }

                        if (key.isReadable()) { //通道可以读取数据，即server端收到客户端的消息，
                            //处理读（专门写方法）
                            readData(key);
                        }

                        iterator.remove();
                    }
                } else {
                    System.out.println("等待。。。");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取客户端消息
    private void readData(SelectionKey key) {
        //定义一个SocketChannel
        SocketChannel channel = null;
        try {
            //取到关联的channel
            channel = (SocketChannel) key.channel();
            //创建缓冲buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count值判断是否读取到数据
            if (count > 0) {
                //把缓冲区的数据转成字符串
                String msg = new String(buffer.array());
                //输出该消息
                System.out.println("from 客户端：" + msg);

                //向其他的客户端转发消息（去掉自己），专门写一个方法处理
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了...");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //转发消息给其他客户端（通道）
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中。。。");
        //遍历 所有注册到selector上的SocketChannel，并排除self
        for (SelectionKey key : selector.keys()) {
            //通过key取出对应的SocketChannel
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;
                //将msg，存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer的数据写入通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899100101102103104105106107108109110111112113114115116117118119120121122123
/*****************************客户端**********************/
public class GroupChatClient {
    //定义相关的属性
    private static final String HOST = "127.0.0.1"; //服务器的IP地址
    private static final int PORT = 6666; //服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    //构造器，初始化操作
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        //连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将channel注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");
    }

    //向服务器发送消息
    public void sendInfo(String info){
        info = username + " 说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //读取从服务器端回复的消息
    public void readInfo(){
        try {
            int readChannels = selector.select();
            if (readChannels > 0){//有可用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isReadable()){
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel)key.channel();
                        //得到一个buffer
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buf);
                        //把缓冲区的数据转成字符串
                        String msg = new String(buf.array());
                        System.out.println(msg.trim());
                    }
                }
            }else {
                System.out.println("没有可以用的通道...");
            }
        }catch (Exception e){

        }
    }

    public static void main(String[] args) throws IOException {
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();
        //启动一个线程用于读取服务器的消息
        new Thread(() -> {
            while (true){
                chatClient.readInfo();
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

        //主线程用于发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }
}
123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263646566676869707172737475767778798081828384
```

**注意事项：**

- 使用

  ```
  int read = channel.read(buffer)
  ```

  读取数据时，读取的结果情况：   

  - 当`read=-1`时，说明客户端的数据发送完毕，并且主动的关闭`socket`。所以这种情况下，服务器程序需要关闭`socketSocket`，并且取消`key`的注册。注意：这个时候继续使用`SocketChannel`进行读操作的话，就会抛出：==**远程主机强迫关闭一个现有的连接**==的IO异常

  - 当

    ```
    read=0
    ```

    时：     

    - 某一时刻`SocketChannel`中当前没有数据可读。
    - 客户端的数据发送完毕。
    - [详情见此博文](https://blog.csdn.net/cao478208248/article/details/41648359)
    - 但是对于博文中的这一条，经过本人测试，这种情况下返回的是读取的数据的大小，而不是`0`：**`ByteBuffer`的`position`等于`limit`，这个时候也会返回`0`**

## 5、NIO的零拷贝

> 零拷贝是网络编程的关键，很多性能优化都离不开它。零拷贝是指：从操作系统的角度来看，文件的传输不存在CPU的拷贝，只存在DMA拷贝。在Java程序中，常用的零拷贝有 mmap（内存映射）和 sendFile。
>
> 零拷贝不仅仅带来更少的数据复制，还能减少线程的上下文切换，减少CPU缓存伪共享以及无CPU校验和计算。

**传统IO的读写：**

```java
File file = new File("test.txt");
RandomAccessFile raf = new RandomAccessFile(file, "rw");

byte[] arr = new byte[(int) file.length()];
raf.read(arr);

Socket socket = new ServerSocket(8080).accept();
socket.getOutputStream().write(arr);
12345678
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152549266.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

**mmap优化的IO读写：**

- mmap通过内存映射，将文件映射到内核缓冲区，同时，用户空间可以共享内存空间的数据。这样，在进行网络传输时，就可以减少内核空间到用户空间的拷贝次数。
- 需要进行4次上下文切换，3次数据拷贝。
- 适合小数据量的读写。
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152604719.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

**sendFile优化的IO读写：**

- Linux2.1 版本提供了 `sendFile` 函数，其基本原理如下：数据根本不经过用户态，直接从内核缓冲区进入到`SocketBuffer`，同时，由于和用户态完全无关，就减少了一次上下文切换。
- 需要3次上下文切换和最少2此数据拷贝。
- 适合大文件的传输。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220152619940.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

- 而 Linux 在 2.4 版本中，做了一些修改，避免了从内核缓冲区拷贝到 Socket Buffer 的操作，直接拷贝到协议栈，从而再一次减少了数据拷贝。
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020022015263190.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- 注：这里其实有一次CPU拷贝，`kernel buffer -> socket buffer`。但是，拷贝的信息很少，只拷贝了数据的长度、偏移量等关键信息，消耗低，可以忽略不计。

**NIO中的零拷贝（transferTo）：**

```java
public static void main(String[] args) throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.connect(new InetSocketAddress("localhost", 7001));
    //得到一个文件CHANNEl
    FileChannel channel = new FileInputStream("a.zip").getChannel();

    //准备发送
    long startTime = System.currentTimeMillis();

    //在Linux下一个 transferTo 方法就可以完成传输
    //在windows 下一次调用 transferTo 只能发送 8M，就需要分段传输文件
    //传输时的位置
    //transferTo 底层使用到零拷贝
    long transferCount = channel.transferTo(0, channel.size(), socketChannel);

    System.out.println("发送的总的字节数：" + transferCount + " 耗时：" + (System.currentTimeMillis() - startTime));
    channel.close();
}
123456789101112131415161718
```

> 

# 三、Netty高性能架构设计

## 1、Netty概述

### 1.1、原生NIO存在的问题

- NIO 的类库和 API 繁杂，使用麻烦：需要熟练掌握 `Selector`、`ServerSocketChannel`、`SocketChannel`、`ByteBuffer` 等。
- 需要具备其他的额外技能：要熟悉 `Java` 多线程编程，因为 `NIO` 编程涉及到 `Reactor` 模式，你必须对多线程和网络编程非常熟悉，才能编写出高质量的 NIO 程序。
- 开发工作量和难度都非常大：例如客户端面临**断连重连**、**网络闪断**、**半包读写**、**失败缓存**、**网络拥塞和异常流的处理**等等。
- JDK NIO 的 Bug：例如臭名昭著的 `Epoll Bug`，它会导致 `Selector` 空轮询，最终导致 `CPU` 100%。直到 JDK 1.7 版本该问题仍旧存在，没有被根本解决。

### 1.2、Netty优点

 `Netty`对`JDK`自带的`NIO`的`API`进行了封装，解决了上述问题。

- 设计优雅：适用于各种传输类型的统一 `API` 阻塞和非阻塞 `Socket`；基于灵活且可扩展的事件模型，可以清晰地分离关注点；高度可定制的线程模型 - 单线程，一个或多个线程池.
- 安全：完整的 `SSL/TLS` 和 `StartTLS` 支持
- 高性能、吞吐量更高：延迟更低；减少资源消耗；最小化不必要的内存复制。

## 2、I/O线程模型

- 目前存在的线程模型主要有：
  - 传统阻塞I/O服务模型
  - Reactor模式
- 根据`Reactor`的数量和处理资源池线程的数量不同，有如下`3`种典型的实现
  - 单`Reactor`单线程
  - 单`Reactor`多线程
  - 主从`Reactor`多线程
- `Netty`线程模型主要基于**主从Reactor多线程模型**做了一定的改进，其中主从`Reactor`多线程模型有多个`Reactor`。

### 2.1、传统阻塞I/O服务模型

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200222144707983.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

图解说明：黄色的框表示对象，蓝色的框表示线程、白色的框表示方法（API）。之后的图相同。

#### 2.1.1、模型分析

**模型特点：**

- 采用阻塞`IO`模式获取输入的数据
- 每个链接都需要独立的线程完成数据的输入，业务处理、数据返回。

**问题分析：**

- 当并发数很大，就会创建大量的线程，占用很大系统资源
- 连接创建后，如果当前线程暂时没有数据可读，该线程会阻塞在`read`操作，造成线程资源浪费。

#### 2.1.2、模型实现代码示例

> 由于模型的逻辑主要集中在服务端，所以所有模型代码示例基本上都是服务端的示例

```java
public static void main(String[] args) throws IOException {
    //1、创建一个线程池
    //2、如果有客户端连接，就创建一个线程，与之通讯（单独写一个方法）
    ExecutorService executorService = Executors.newCachedThreadPool();

    //创建ServerSocket
    ServerSocket serverSocket = new ServerSocket(6666);
    System.out.println("服务器启动了");

    while (true) {
        //监听，等待客户端连接
        final Socket socket = serverSocket.accept();
        System.out.println("连接到一个客户端");
        //创建一个线程，与之通讯
        executorService.execute(() -> {
            //重写Runnable方法，与客户端进行通讯
            handler(socket);
        });
    }
}

//编写一个Handler方法，和客户端通讯。主要进行数据的读取和业务处理。
public static void handler(Socket socket) {
    try {
        byte[] bytes = new byte[1024];
        //通过socket获取输入流
        InputStream inputStream = socket.getInputStream();
        //循环的读取客户端发送的数据
        while (true){
            int read = inputStream.read(bytes);
            if (read != -1){
                System.out.println(new String(bytes, 0, read));//输出客户端发送的数据
            } else {
                break;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        System.out.println("关闭和client的连接");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
1234567891011121314151617181920212223242526272829303132333435363738394041424344454647
```

### 2.2、Reactor模型概述

**针对传统阻塞I/O服务模型的2个缺点，解决方案如下：**

- 基于 `I/O` 复用模型：多个连接共用一个阻塞对象，应用程序只需要在一个阻塞对象等待，无需阻塞等待所有连接。当某个连接有新的数据可以处理时，操作系统通知应用程序，线程从阻塞状态返回，开始进行业务处理。`Reactor` 对应的叫法: 1. 反应器模式 2. 分发者模式(`Dispatcher`) 3. 通知者模式(`notifier`)
- 基于线程池复用线程资源：不必再为每个连接创建线程，将连接完成后的业务处理任务分配给线程进行处理，一个线程可以处理多个连接的业务。

**I/O复用结合线程池，就是Reactor模式基本设计思想，如图所示：**
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200222144736861.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

- `Reactor`模式，通过一个或多个输入同时传递给服务处理器的模式（基于事件驱动）
- 服务器端程序处理传入的多个请求，并将它们同步分派到响应的处理线程，因此`Reactor`模式也叫`Dispatcher`模式。
- `Reactor`模式使用`IO`复用监听事件，收到事件后，分发的某个线程（进程），这点就是网络服务高并发处理的关键。

**Reactor模式中的核心组成部分：**

- ```
  Reactor
  ```

  ：

  ```
  Reactor
  ```

  在一个单独的线程中运行，负责监听和分发事件，分发给适当的处理程序来对

  ```
  IO
  ```

  事件作出反应。   

  - 我的理解是将`Reactor`理解成一个`Selector`，它可以对建立新的连接，也可以将产生的读写事件交换给`Handler`进行处理

- `Handlers`：处理程序执行`I/O`事件要完成的实际事件，类似于客户想要与之交谈的公司中的实际官员。`Reactor`通过调度适当的处理程序来响应`I/O`事件，处理程序执行非阻塞操作。

### 2.3、单Reactor单线程模式

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200222144749539.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

**方案说明：**

- `Select` 是前面 `I/O` 复用模型介绍的标准网络编程 API，可以实现应用程序通过一个阻塞对象监听多路连接请求
- `Reactor` 对象通过 `Select` 监控客户端请求事件，收到事件后通过 `Dispatch` 进行分发
- 如果是建立连接请求事件，则由 `Acceptor` 通过 `Accept` 处理连接请求，然后创建一个 `Handler` 对象处理连接完成后的后续业务处理
- 如果不是建立连接事件，则 `Reactor` 会分发调用连接对应的 Handler 来响应
- `Handler` 会完成 `Read`→业务处理→`Send` 的完整业务流程

**结合实例**：服务器端用一个线程通过多路复用搞定所有的 `IO` 操作（包括连接，读、写等），编码简单，清晰明了，但是如果客户端连接数量较多，将无法支撑，前面的 `NIO` 案例就属于这种模型。

#### 2.2.1、模型分析

- **优点**：模型简单，没有多线程、进程通信、竞争的问题，全部都在一个线程中完成
- **缺点**：性能问题，只有一个线程，无法完全发挥多核 `CPU` 的性能。`Handler` 在处理某个连接上的业务时，整个进程无法处理其他连接事件，很容易导致性能瓶颈
- **缺点**：可靠性问题，线程意外终止，或者进入死循环，会导致整个系统通信模块不可用，不能接收和处理外部消息，造成节点故障
- **使用场景**：客户端的数量有限，业务处理非常快速，比如 `Redis`在业务处理的时间复杂度 `O(1)` 的情况

#### 2.2.2、模型实现代码示例

> 这里面我为了简便，我将Reactor和Acceptor和Handler三个对象搞成了方法。

```java
public class SReactorSThread {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private int PORT = 6666;

    public SReactorSThread() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //对客户端进行监听
    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                //表示有客户端产生事件
                if (count > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();//取出产生事件的Channel
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();//准备对其进行遍历
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //将key交给dispatch去处理
                        dispatch(key);
                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //dispatch
    private void dispatch(SelectionKey key) {
        if (key.isAcceptable()){
            accept(key);
        }else {
            handler(key);
        }
    }

    //建立新的连接
    private void accept(SelectionKey key) {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //对请求进行处理，接收消息---业务处理---返回消息
    private void handler(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(3);
            StringBuilder msg = new StringBuilder();
            while (channel.read(buffer) > 0) {
                msg.append(new String(buffer.array()));
                buffer.clear();
            }
            System.out.println("接收到消息：" + msg.toString());
            //发送消息
            String ok = "OK";
            buffer.put(ok.getBytes());
            //这个flip非常重要哦，是将position置0，limit置于position的位置，以便下面代码进行写入操作能够正确写入buffer中的所有数据
            buffer.flip();  
            channel.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消该通道的注册并关闭通道，这里非常重要，没有这一步的话当客户端断开连接就会不断抛出IOException
                //是因为，select会一直产生该事件。
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

/********调用**************/

public static void main(String[] args) {
    SReactorSThread sReactorSThread = new SReactorSThread();
    sReactorSThread.listen();
}
1234567891011121314151617181920212223242526272829303132333435363738394041424344454647484950515253545556575859606162636465666768697071727374757677787980818283848586878889909192939495969798
```

**这里有更牛逼更完整的`Reactor`单线程模型的代码案例：**https://www.cnblogs.com/hama1993/p/10611229.html

> 我悄悄咪咪的仔细看了看，我感觉他的案例模型是单Reactor多线程模型。错了勿喷

### 2.4、单Reactor多线程模型

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200222144815561.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

**方案说明：**

- `Reactor` 对象通过`select` 监控客户端请求事件, 收到事件后，通过`dispatch`进行分发
- 如果建立连接请求, 则右`Acceptor` 通过accept 处理连接请求, 然后创建一个`Handler`对象处理完成连接后的各种事件
- 如果不是连接请求，则由`reactor`分发调用连接对应的`handler` 来处理
- handler 只负责响应事件，不做具体的业务处理, 通过`read` 读取数据后，会分发给后面的`worker`线程池的某个线程处理业务
- `worker` 线程池会分配独立线程完成真正的业务，并将结果返回给`handler`
- `handler`收到响应后，通过`send` 将结果返回给`client`

#### 2.4.1、模型分析

- **优点**：可以充分的利用多核`cpu` 的处理能力
- **缺点**：多线程数据共享和访问比较复杂， `reactor` 处理所有的事件的监听和响应，在单线程运行， 在高并发场景容易出现性能瓶颈.

#### 2.4.2、模型实现代码示例

> 我感觉，这个多线程就是在单线程的基础之上，在读取数据之后进行业务处理的时候，另起一个线程。网上也有说读取数据很消耗性能，将读取数据和业务处理统一另起一个线程。我有点迷糊，代码就写不出来了。

### 2.5、主从Reactor多线程

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200222144833750.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

**方案说明：**

- `Reactor`主线程 `MainReactor` 对象就只注册一个用于监听连接请求的`ServerSocketChannel`，通过`select` 监听连接事件, 收到事件后，通过`Acceptor` 处理连接事件
- 当 `Acceptor` 处理连接事件后，`MainReactor` 通过`accept`获取新的连接，并将连接注册到`SubReactor`
- `subreactor` 将连接加入到连接队列进行监听,并创建`handler`进行各种事件处理
- 当有新事件发生时， `subreactor` 就会调用对应的`handler`处理
- `handler` 通过`read` 读取数据，分发给后面的`worker` 线程处理
- `worker` 线程池分配独立的`worker` 线程进行业务处理，并返回结果
- `handler` 收到响应的结果后，再通过`send` 将结果返回给`client`
- `Reactor` 主线程可以对应多个`Reactor` 子线程, 即`MainRecator` 可以关联多个`SubReactor`

#### 2.5.1、模型分析

- **优点**：父线程与子线程的数据交互简单职责明确，父线程只需要接收新连接，子线程完成后续的业务处理。
- **优点**：父线程与子线程的数据交互简单，`Reactor` 主线程只需要把新连接传给子线程，子线程无需返回数据
- **缺点**：编程复杂度较高
- **结合实例**：这种模型在许多项目中广泛使用，包括 `Nginx` 主从 `Reactor` 多进程模型，`Memcached` 主从多线程，`Netty` 主从多线程模型的支持

#### 2.5.2、模型实现代码示例

> 还是去看别人大佬写的吧。https://www.cnblogs.com/eason-ou/p/11912010.html

## 3、Netty模型

### 3.1、主从Reactor进阶

 `Netty`主要是基于主从`Reactor`多线程模式做了一定的改进，其中主从`Reactor`都有单一的一个变成了多个。下面是简单的改进图。
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200222144854655.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

- 如图所示，增加了`BossGroup`来维护多个主`Reactor`，主`Reactor`还是只关注连接的`Accept`；增加了`WorkGroup`来维护多个从`Reactor`，从`Reactor`将接收到的请求交给`Handler`进行处理。
- 在主`Reactor`中接收到`Accept`事件，获取到对应的`SocketChannel`，`Netty`会将它进一步封装成`NIOSocketChannel`对象，这个封装后的对象还包含了该`Channel`对应的`SelectionKey`、通信地址等详细信息
- `Netty`会将装个封装后的`Channel`对象注册到`WorkerGroup`中的从`Reactor`中。
- 当`WorkerGroup`中的从`Reactor`监听到事件后，就会将之交给与此`Reactor`对应的`Handler`进行处理。

### 3.2、再进阶版

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200222144904423.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

- `Netty`将`Selector`以及`Selector`相关的事件及任务封装了`NioEventLoop`，这样`BossGroup`就可以通过管理`NioEventLoop`去管理各个`Selector`。
- 同时，`Netty`模型中主要存在两个大的线程池组`BossGroup`和`WorkerGroup`，用于管理主`Reactor`线程和从`Reactor`线程。

### 3.3、Netty模型

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200222144915305.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

- `Netty`抽象出两组线程池，`BossGroup`专门负责接收客户端的连接，`WorkerGroup`专门负责网络的读写

- `BossGroup`和`WorkerGroup`类型的本质都是`NioEventLoopGroup`类型。

- `NioEventLoopGroup`相当于一个线程管理器（类似于`ExecutorServevice`），它下面维护很多个`NioEventLoop`线程。（我认为图中的`NioEventGroup`的地方应该改成`NioEventLoop`，可能我的理解有点差错吧）

  - 在初始化这两个`Group`线程组时，默认会在每个`Group`中生成`CPU*2`个`NioEventLoop`线程
  - 当`n`个连接来了，`Group`默认会按照连接请求的顺序分别将这些连接分给各个`NioEventLoop`去处理。
  - 同时`Group`还负责管理`EventLoop`的生命周期。

- `NioEventLoop`表示一个不断循环的执行处理任务的线程

  - 它维护了一个线程和任务队列。
  - 每个`NioEventLoop`都包含一个`Selector`，用于监听绑定在它上面的`socket`通讯。
  - 每个`NioEventLoop`相当于`Selector`，负责处理多个`Channel`上的事件
  - 每增加一个请求连接，`NioEventLoopGroup`就将这个请求依次分发给它下面的`NioEventLoop`处理。

- 每个`Boss NioEventLoop`循环执行的步骤有3步：

  - 轮询`accept`事件
  - 处理`accept`事件，与`client`建立连接，生成`NioSocketChannel`，并将其注册到某个`Worker NioEventLoop`的`selector`上。
  - 处理任务队列到任务，即`runAllTasks`

- 每个`Worker NioEventLoop`循环执行的步骤：

  - 轮询`read`，`write`事件
  - 处理`I/O`事件，即`read`，`write`事件，在对应的`NioSocketChannel`中进行处理
  - 处理任务队列的任务，即`runAllTasks`

- 每个 `Worker NioEventLoop`处理业务时，会使用`pipeline`（管道），`pipeline`中维护了一个`ChannelHandlerContext`链表，而`ChannelHandlerContext`则保存了`Channel`相关的所有上下文信息，同时关联一个`ChannelHandler`对象。如图所示，`Channel`和`pipeline`一一对应，ChannelHandler和`ChannelHandlerContext`一一对应。

  ![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly91c2VyLWdvbGQtY2RuLnhpdHUuaW8vMjAxOC8xMS8xLzE2NmNjYmJkYzhjZDFhMmY?x-oss-process=image/format,png)

- `ChannelHandler`是一个接口，负责处理或拦截`I/O`操作，并将其转发到`Pipeline`中的下一个处理`Handler`进行处理。

  ```java
                                                   I/O Request
                                              via Channel or
                                          ChannelHandlerContext
                                                        |
    +---------------------------------------------------+---------------+
    |                           ChannelPipeline         |               |
    |                                                  \|/              |
    |    +---------------------+            +-----------+----------+    |
    |    | Inbound Handler  N  |            | Outbound Handler  1  |    |
    |    +----------+----------+            +-----------+----------+    |
    |              /|\                                  |               |
    |               |                                  \|/              |
    |    +----------+----------+            +-----------+----------+    |
    |    | Inbound Handler N-1 |            | Outbound Handler  2  |    |
    |    +----------+----------+            +-----------+----------+    |
    |              /|\                                  .               |
    |               .                                   .               |
    | ChannelHandlerContext.fireIN_EVT() ChannelHandlerContext.OUT_EVT()|
    |        [ method call]                       [method call]         |
    |               .                                   .               |
    |               .                                  \|/              |
    |    +----------+----------+            +-----------+----------+    |
    |    | Inbound Handler  2  |            | Outbound Handler M-1 |    |
    |    +----------+----------+            +-----------+----------+    |
    |              /|\                                  |               |
    |               |                                  \|/              |
    |    +----------+----------+            +-----------+----------+    |
    |    | Inbound Handler  1  |            | Outbound Handler  M  |    |
    |    +----------+----------+            +-----------+----------+    |
    |              /|\                                  |               |
    +---------------+-----------------------------------+---------------+
                    |                                  \|/
    +---------------+-----------------------------------+---------------+
    |               |                                   |               |
    |       [ Socket.read() ]                    [ Socket.write() ]     |
    |                                                                   |
    |  Netty Internal I/O Threads (Transport Implementation)            |
    +-------------------------------------------------------------------+
  
  123456789101112131415161718192021222324252627282930313233343536373839
  ```

### 3.4、代码示例

**服务端代码：**

```java
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建BossGroup 和 WorkerGroup
        //1、创建两个线程组，bossGroup 和 workerGroup
        //2、bossGroup 只是处理连接请求，真正的和客户端业务处理，会交给 workerGroup 完成
        //3、两个都是无限循环
        //4、bossGroup 和 workerGroup 含有的子线程（NioEventLoop）个数为实际 cpu 核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup worderGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程来进行设置，配置
            bootstrap.group(bossGroup, worderGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用 NioServerSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { //为accept channel的pipeline预添加的handler
                        //给 pipeline 添加处理器，每当有连接accept时，就会运行到此处。
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); //给我们的 workerGroup 的 EventLoop 对应的管道设置处理器

            System.out.println("........服务器 is ready...");
            //绑定一个端口并且同步，生成了一个ChannelFuture 对象
            //启动服务器（并绑定端口）
            ChannelFuture future = bootstrap.bind(6668).sync();

            //对关闭通道进行监听
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            worderGroup.shutdownGracefully();
        }
    }
}
12345678910111213141516171819202122232425262728293031323334353637383940
```

**服务端Handler处理：**

```java
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     *读取客户端发送过来的消息
     * @param ctx 上下文对象，含有 管道pipeline，通道channel，地址
     * @param msg 就是客户端发送的数据，默认Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
        System.out.println("server ctx = " + ctx);
        //看看Channel和Pipeline的关系
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); //本质是个双向链表，出栈入栈

        //将msg转成一个ByteBuf，比NIO的ByteBuffer性能更高
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //它是 write + flush，将数据写入到缓存buffer，并将buffer中的数据flush进通道
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~", CharsetUtil.UTF_8));
    }

    //处理异常，一般是关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
123456789101112131415161718192021222324252627282930313233343536
```

**客户端：**

```java
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            //注意：客户端使用的不是 ServerBootStrap 而是 Bootstrap
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group(group) //设置线程组
                    .channel(NioSocketChannel.class) //设置客户端通道的实现类（使用反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler()); //加入自己的处理器
                        }
                    });
            System.out.println("客户端 OK...");

            //启动客户端去连接服务器端
            //关于 channelFuture 涉及到 netty 的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
123456789101112131415161718192021222324252627282930
```

**客户端Handler处理：**

```java
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当通道就绪就会触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client: " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, server", CharsetUtil.UTF_8));
    }

    /**
     * 当通道有读取事件时，会触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("服务器回复的消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
12345678910111213141516171819202122232425262728293031
```

### 3.5、任务队列

 任务队列由`NioEventLoop`维护并不断执行。当我们就收到请求之后，在当前`channel`对应的`pipeline`中的各个`Handler`里面进行业务处理和请求过滤。当某些业务需要耗费大量时间的时候，我们可以将任务提交到由`NioEventLoop`维护的`taskQueue`或`scheduleTaskQueue`中，让当前的`NioEventLoop`线程在空闲时间去执行这些任务。下面将介绍提交任务的3种方式

**用户程序自定义的普通任务：**

 该方式会将任务提交到`taskQueue`队列中。提交到该队列中的任务会按照提交顺序依次执行。

```java
channelHandlerContext.channel().eventLoop().execute(new Runnable(){
    @Override
    public void run() {
        //...
    }
});
123456
```

**用户自定义的定时任务：**

 该方式会将任务提交到`scheduleTaskQueue`定时任务队列中。该队列是底层是优先队列`PriorityQueue`实现的，固该队列中的任务会按照时间的先后顺序定时执行。

```java
channelHandlerContext.channel().eventLoop().schedule(new Runnable() {
    @Override
    public void run() {

    }
}, 60, TimeUnit.SECONDS);
123456
```

**为其他EventLoop线程对应的Channel添加任务**
 可以在`ChannelInitializer`中，将刚创建的各个`Channel`以及对应的标识加入到统一的集合中去，然后可以根据标识获取`Channel`以及其对应的`NioEventLoop`，然后就课程调用`execute()`或者`schedule()`方法。

### 3.6、异步模型

**基本介绍**

- 异步的概念和同步相对。当一个异步过程调用发出后，调用者不能立刻得到结果。实际处理这个调用的组件在完成后，通过状态、通知和回调来通知调用者。
- `Netty` 中的 `I/O` 操作是异步的，包括 `Bind`、`Write`、`Connect` 等操作会简单的返回一个 `ChannelFuture`。
- 调用者并不能立刻获得结果，而是通过 `Future-Listener` 机制，用户可以方便的主动获取或者通过通知机制获得 `IO` 操作结果
- `Netty` 的异步模型是建立在 `future` 和 `callback` 的之上的。`callback` 就是回调。重点说 `Future`，它的核心思想是：假设一个方法 `fun`，计算过程可能非常耗时，等待 `fun`返回显然不合适。那么可以在调用 `fun` 的时候，立马返回一个 `Future`，后续可以通过 `Future`去监控方法 `fun` 的处理过程(即 ： `Future-Listener` 机制)

**Future说明**

- 表示异步的执行结果, 可以通过它提供的方法来检测执行是否完成，比如检索计算等等.
- `ChannelFuture` 是一个接口 ： `public interface ChannelFuture extends Future`。我们可以添加监听器，当监听的事件发生时，就会通知到监听器

**工作原理示意图**
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200223163106773.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020022316311864.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

- 在使用 `Netty` 进行编程时，拦截操作和转换出入站数据只需要您提供 `callback` 或利用`future` 即可。这使得链式操作简单、高效, 并有利于编写可重用的、通用的代码。
- `Netty` 框架的目标就是让你的业务逻辑从网络基础应用编码中分离出来、解脱出来

**Future-Listener机制**

- 当 `Future` 对象刚刚创建时，处于非完成状态，调用者可以通过返回的 `ChannelFuture` 来获取操作执行的状态，注册监听函数来执行完成后的操作。

- 常用方法如下：

  | 方法名称      | 方法作用                                                   |
  | ------------- | ---------------------------------------------------------- |
  | isDone()      | 判断当前操作是否完成                                       |
  | isSuccess()   | 判断已完成的当前操作是否成功                               |
  | getCause()    | 获取已完成当前操作失败的原因                               |
  | isCancelled() | 判断已完成的当前操作是否被取消                             |
  | addListener() | 注册监听器，当前操作（Future）已完成，将会通知指定的监听器 |

**举例说明**
 绑定端口操作时异步操作，当绑定操作处理完，将会调用相应的监听器处理逻辑。

```java
serverBootstrap.bind(port).addListener(future -> {
       if(future.isSuccess()) {
           System.out.println(newDate() + ": 端口["+ port + "]绑定成功!");
       } else{
           System.err.println("端口["+ port + "]绑定失败!");
       }
   });
1234567
```

### 3.7、快速入门实例-HTTP HelloWorld

 浏览器访问`Netty`服务器后，返回`HelloWorld`

**启动**

```java
public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new TestServerInitializer());
        ChannelFuture channelFuture = bootstrap.bind(8080).sync();
        channelFuture.channel().closeFuture().sync();
    } finally {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
12345678910111213141516
```

**定义ChannelInitializer**

 用于给`Channel`对应的`pipeline`添加`handler`。该`ChannelInitializer`中的代码在`SocketChannel`被创建时都会执行

```java
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入一个 netty 提供的 httpServerCodec：codec => [coder - decoder]
        //1、HttpServerCodec 是 netty 提供的处理http的编解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2、增加自定义的Handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
1234567891011121314
```

**自定义Handler**

```java
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * 读取客户端数据。
     *
     * @param channelHandlerContext
     * @param httpObject            客户端和服务器端互相通讯的数据被封装成 HttpObject
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

        //判断 msg 是不是 HTTPRequest 请求
        if (httpObject instanceof HttpRequest) {
            System.out.println("msg 类型 = " + httpObject.getClass());
            System.out.println("客户端地址：" + channelHandlerContext.channel().remoteAddress());
            //获取
            HttpRequest request = (HttpRequest) httpObject;
            //获取uri，进行路径过滤
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico，不做响应");
            }

            //回复信息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("HelloWorld", CharsetUtil.UTF_8);
            //构造一个http的响应，即HTTPResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //将构建好的 response 返回
            channelHandlerContext.writeAndFlush(response);
        }
    }
}
1234567891011121314151617181920212223242526272829303132333435
```

# 四、Netty核心组成

## 1、Bootstrap、ServerBootstrap

- `Bootstrap` 意思是引导，一个 `Netty` 应用通常由一个 `Bootstrap` 开始，主要作用是配置整个 `Netty` 程序，串联各个组件，`Netty` 中 `Bootstrap` 类是客户端程序的启动引导类，`ServerBootstrap` 是服务端启动引导类

**常见方法：**

| 方法名称                                                     | 方法介绍                                     |
| ------------------------------------------------------------ | -------------------------------------------- |
| `public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup)` | 该方法用于服务器端，用来设置两个EventLoop    |
| `public B group(EventLoopGroup group)`                       | 该方法用于客户端，用来设置一个EventLoop      |
| `public B channel(Class channelClass)`                       | 该方法用来设置一个服务器端的通道实现         |
| `public  B option(ChannelOption option, T value)`            | 用来给 ServerChannel 添加配置                |
| `public  ServerBootstrap childOption(ChannelOption childOption, T value)` | 用来给接收到的通道添加配置                   |
| `public ServerBootstrap childHandler(ChannelHandler childHandler)` | 该方法用来设置业务处理类（自定义的 handler） |
| `public ChannelFuture bind(int inetPort)`                    | 该方法用于服务器端，用来设置占用的端口号     |
| `public ChannelFuture connect(String inetHost, int inetPort)` | 该方法用于客户端，用来连接服务器             |

## 2、Future、ChannelFuture

- `Netty` 中所有的 `IO` 操作都是异步的，不能立刻得知消息是否被正确处理。但是可以过一会等它执行完成或者直接注册一个监听，具体的实现就是通过 `Future` 和 `ChannelFutures`，他们可以注册一个监听，当操作执行成功或失败时监听会自动触发注册的监听事件

**常见的方法有**

| 方法名                 | 方法介绍                                   |
| ---------------------- | ------------------------------------------ |
| `Channel channel()`    | 返回当前正在进行 IO 操作的通道             |
| `ChannelFuture sync()` | 等待异步操作执行完毕，相当于将阻塞在当前。 |

## 3、Channel

- `Netty` 网络通信的组件，能够用于执行网络 `I/O` 操作。
- 通过`Channel` 可获得当前网络连接的通道的状态
- 通过`Channel` 可获得 网络连接的配置参数 （例如接收缓冲区大小）
- `Channel` 提供异步的网络 `I/O` 操作(如建立连接，读写，绑定端口)，异步调用意味着任何 `I/O` 调用都将立即返回，并且不保证在调用结束时所请求的 `I/O` 操作已完成
- 调用立即返回一个 `ChannelFuture` 实例，通过注册监听器到 `ChannelFuture` 上，可以 `I/O` 操作成功、失败或取消时回调通知调用方
- 支持关联 `I/O` 操作与对应的处理程序
- 不同协议、不同的阻塞类型的连接都有不同的 `Channel` 类型与之对应

**常用的 `Channel` 类型**

| 名称                     | 介绍                                                         |
| ------------------------ | ------------------------------------------------------------ |
| `NioSocketChannel`       | 异步的客户端 TCP Socket 连接。                               |
| `NioServerSocketChannel` | 异步的服务器端 TCP Socket 连接                               |
| `NioDatagramChannel`     | 异步的 UDP 连接。                                            |
| `NioSctpChannel`         | 异步的客户端 Sctp 连接。                                     |
| `NioSctpServerChannel`   | 异步的 Sctp 服务器端连接，这些通道涵盖了 UDP 和 TCP 网络 IO 以及文件 IO。 |

## 4、Selector

- `Netty` 基于 `Selector` 对象实现 `I/O` 多路复用，通过 `Selector` 一个线程可以监听多个连接的 `Channel` 事件。
- 当向一个 `Selector` 中注册 `Channel` 后，`Selector` 内部的机制就可以自动不断地查询(`Select`) 这些注册的 `Channel` 是否有已就绪的 `I/O` 事件（例如可读，可写，网络连接完成等），这样程序就可以很简单地使用一个线程高效地管理多个 `Channel`
- 同时，`Netty`中对`selector`中的`selectedKey`集合进行了替换，它替换成了一个它自己实现的一个`set`集合，这样效率更高。

## 5、ChannelHandler及其实现类

- `ChannelHandler` 是一个接口，处理 `I/O` 事件或拦截 `I/O` 操作，并将其转发到其 `ChannelPipeline`(业务处理链)中的下一个处理程序。
- `ChannelHandler` 本身并没有提供很多方法，因为这个接口有许多的方法需要实现，方便使用期间，可以继承它的子类
- 我们经常需要自定义一个 `Handler` 类去继承 `ChannelInboundHandlerAdapter`，然后通过重写相应方法实现业务逻辑，我们接下来看看一般都需要重写哪些方法

```java
public class ChannelInboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelInboundHandler {

    //通道注册事件
    @Skip
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
    }

    //通道取消注册事件
    @Skip
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }

    //通道就绪事件
    @Skip
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Skip
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }

    //通道读取数据事件
    @Skip
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.fireChannelRead(msg);
    }

    //通道数据读取完毕事件
    @Skip
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
    }

    /**
     * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Skip
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelWritabilityChanged()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Skip
    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelWritabilityChanged();
    }

    //通道发生异常事件
    @Skip
    @Override
    @SuppressWarnings("deprecation")
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
12345678910111213141516171819202122232425262728293031323334353637383940414243444546474849505152535455565758596061626364656667686970717273747576777879808182
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020022621032833.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

- `ChannelInboundHandler` 用于处理入站 I/O 事件。
- `ChannelOutboundHandler` 用于处理出站 I/O 操作。

**适配器**

- `ChannelInboundHandlerAdapter` 用于处理入站 I/O 事件。
- `ChannelOutboundHandlerAdapter` 用于处理出站 I/O 操作。
- `ChannelDuplexHandler` 用于处理入站和出站事件。

## 6、Pipeline和ChannelPipeline

- `ChannelPipeline` 是一个 `Handler` 的集合，它负责处理和拦截 `inbound` 或者 `outbound` 的事件和操作，相当于一个贯穿 `Netty` 的链。(也可以这样理解：`ChannelPipeline` 是 保存 `ChannelHandler` 的 `List`，用于处理或拦截 `Channel` 的入站事件和出站操作)
- `ChannelPipeline` 实现了一种高级形式的拦截过滤器模式，使用户可以完全控制事件的处理方式，以及 `Channel` 中各个的 `ChannelHandler` 如何相互交互
- 在 `Netty` 中每个 `Channel` 都有且仅有一个 `ChannelPipeline` 与之对应，它们的组成关系如下
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200226210233302.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- 一个 `Channel` 包含了一个 `ChannelPipeline`，而 `ChannelPipeline` 中又维护了一个由 `ChannelHandlerContext` 组成的双向链表，并且每个 `ChannelHandlerContext` 中又关联着一个 `ChannelHandler`
- 入站事件和出站事件在一个双向链表中，入站事件会从链表 `head` 往后传递到最后一个入站的 `handler`，出站事件会从链表 `tail` 往前传递到最前一个出站的 `handler`，两种类型的 `handler` 互不干扰

**常用方法：**

| 方法名                                                 | 介绍                                                |
| ------------------------------------------------------ | --------------------------------------------------- |
| `ChannelPipeline addFirst(ChannelHandler... handlers)` | 把一个业务处理类（handler）添加到链中的第一个位置   |
| ChannelPipeline addLast(ChannelHandler… handlers)      | 把一个业务处理类（handler）添加到链中的最后一个位置 |

## 7、ChannelHandlerContext

- 保存 `Channel` 相关的所有上下文信息，同时关联一个 `ChannelHandler` 对象
- 即`ChannelHandlerContext` 中 包 含 一 个 具 体 的 事 件 处 理 器 `ChannelHandler` ， 同 时`ChannelHandlerContext` 中也绑定了对应的 `pipeline` 和 `Channel` 的信息，方便对 `ChannelHandler`进行调用.

**常用方法：**

| 方法名                                    | 介绍                                                         |
| ----------------------------------------- | ------------------------------------------------------------ |
| `ChannelFuture close()`                   | 关闭通道                                                     |
| `ChannelOutboundInvoker flush()`          | 刷新                                                         |
| `ChannelFuture writeAndFlush(Object msg)` | 将 数 据 写 到 ChannelPipeline 中 当 前ChannelHandler 的下一个 ChannelHandler 开始处理（出站） |

## 8、ChannelOption

- `Netty` 在创建 `Channel` 实例后,一般都需要设置 `ChannelOption` 参数。

**ChannelOption 参数如下:**

- `ChannelOption.SO_BACKLOG`
  - 对应 TCP/IP 协议 listen 函数中的 backlog 参数，用来初始化服务器可连接队列大小。
  - 服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接。多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog 参数指定了队列的大小。
- `ChannelOption.SO_KEEPALIVE`
  - 一直保持连接活动状态

## 9、EventLoopGroup和其实现类NioEventLoopGroup

- EventLoopGroup是一组 EventLoop的抽象，Netty为了更好的利用多核 CPU资源，一般会有多个 EventLoop同时工作，每个 EventLoop维护着一个 Selector 实例。
- EventLoopGroup 提供 next 接口，可以从组里面按照一定规则获取其中一个 EventLoop来处理任务。在 Netty  服务器端编程中，我们一般都需要提供两个 EventLoopGroup，例如：BossEventLoopGroup  和WorkerEventLoopGroup。
- 通常一个服务端口即一个  ServerSocketChannel对应一个Selector 和一个EventLoop线程。BossEventLoop  负责接收客户端的连接并将 SocketChannel 交给 WorkerEventLoopGroup 来进行 IO 处理，如下图所示
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200226210300805.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- BossEventLoopGroup 通常是一个单线程的 EventLoop，EventLoop 维护着一个注册了ServerSocketChannel 的 Selector 实例BossEventLoop 不断轮询 Selector 将连接事件分离出来
- 通常是 OP_ACCEPT 事件，然后将接收到的 SocketChannel 交给 WorkerEventLoopGroup
- WorkerEventLoopGroup 会由 next 选择其中一个 EventLoop来将这个 SocketChannel 注册到其维护的 Selector 并对其后续的 IO 事件进行处理

**常用方法：**

| 方法名                               | 介绍               |
| ------------------------------------ | ------------------ |
| `public NioEventLoopGroup()`         | 构造方法           |
| `public Future shutdownGracefully()` | 断开连接，关闭线程 |

## 10、Unpooled

- Netty 提供一个专门用来操作缓冲区(即Netty的数据容器)的工具类

**常用方法**

| 方法名                                                       | 介绍                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| `public static ByteBuf copiedBuffer(CharSequence string, Charset charset)` | 通过给定的数据和字符编码返回一个 ByteBuf 对象（类似于 NIO 中的 ByteBuffer 但有区别） |

**代码示例**

```java
//创建一个ByteBuf
//1、创建对象，该对象包含一个数组，是一个byte[10]
//2、在netty的buffer中，写入数据后再读取数据不需要使用 flip 进行反转
// 底层维护了 readerIndex 和 writeIndex
//往buffer中写的范围为 [writeIndex, capacity)
//往buffer中可读的范围为 [readerIndex, writeIndex)。使用 buf.readByte() 会往后移动 readerIndex 指针，使用 buf.getByte(i) 通过索引获取就不会移动该指针
ByteBuf byteBuf = Unpooled.buffer(10);
for (int i = 0; i < 10; i++) {
    byteBuf.writeByte(i);
}
//获取该buf的大小
int capacity = byteBuf.capacity();
//输出
for (int i = 0; i < byteBuf.capacity(); i++) {
    System.out.println(byteBuf.getByte(i));
    System.out.println(byteBuf.readByte());
}
byte[] content = byteBuf.array();
//将content转成字符串
String c = new String(content, StandardCharsets.UTF_8);
//数组偏移量
int offset = byteBuf.arrayOffset();
//获取读取偏移量
int readerIndex = byteBuf.readerIndex();
//获取写偏移量
int writerIndex = byteBuf.writerIndex();
//获取容量
int capacity = byteBuf.capacity();
//获取可读取的字节数
int readableBytes = byteBuf.readableBytes();
//通过索引获取某个位置的字节
byte aByte = byteBuf.getByte(0);
//获取Buf中某个范围的字符序列
CharSequence charSequence = byteBuf.getCharSequence(0, 4, StandardCharsets.UTF_8);

1234567891011121314151617181920212223242526272829303132333435
```

## 11、Netty群聊系统

**实例要求：**

- 编写一个 Netty 群聊系统，实现服务器端和客户端之间的数据简单通讯（非阻塞）
- 实现多人群聊
- 服务器端：可以监测用户上线，离线，并实现消息转发功能
- 客户端：通过channel 可以无阻塞发送消息给其它所有用户，同时可以接受其它用户发送的消息(有服务器转发得到)
- 目的：进一步理解Netty非阻塞网络编程机制

**服务器端代码：**

```java
/*************启动类**************/
public class GroupChatServer {
    private int port; //监听端口
    public GroupChatServer(int port){
        this.port = port;
    }
    //编写run 方法，处理客户端的请求
    public void run() throws InterruptedException {
        //创建两个线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder()); //向pipeline加入解码器
                            pipeline.addLast("encoder", new StringEncoder()); //加入编码器
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });
            System.out.println("netty服务器启动");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            //监听关闭事件
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        GroupChatServer groupChatServer = new GroupChatServer(7000);
        groupChatServer.run();
    }
}

/***********************Handler**********************/
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个Channel组，管理所有的channel
    //GlobalEventExecutor.INSTANCE 是全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //此方法表示连接建立，一旦建立连接，就第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //该方法会将 channelGroup 中所有 channel 遍历，并发送消息，而不需要我们自己去遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + sdf.format(new Date()) + "加入聊天\n");
        //将当前的Channel加入到 ChannelGroup
        channelGroup.add(channel);
    }

    //表示 channel 处于活动状态，提示 xxx 上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + "上线了~");
    }

    //表示 channel 处于不活动状态，提示 xxx 离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + "离线了~");
    }

    //表示 channel 断开连接，将xx客户离开信息推送给当前在线客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() +" "+ sdf.format(new Date()) + "离开了\n");
        System.out.println("当前channelGroup大小 ：" + channelGroup.size());
    }

    //读取数据，并进行消息转发
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取当前channel
        Channel channel = ctx.channel();
        //这时，遍历channelGroup，根据不同的情况，回送不同的消息
        channelGroup.forEach(item -> {
            if (item != channel) {
                item.writeAndFlush("[客户]" + channel.remoteAddress() + "发送了消息：" + msg + "\n");
            } else { //把自己发送的消息发送给自己
                item.writeAndFlush("[自己]发送了消息：" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899100101
```

**客户端代码：**

```java
/*********************启动类******************/
public class GroupChatClient {

    //属性
    private final String host;
    private final int port;

    public GroupChatClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入Handler
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            //得到channel
            Channel channel = channelFuture.channel();
            System.out.println("--------" + channel.localAddress() + "---------");
            //客户端需要输入信息，创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg = scanner.nextLine();
                //通过channel发送到服务器端
                channel.writeAndFlush(msg + "\r\n");
            }
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatClient("127.0.0.1", 7000).run();
    }
}

/*****************Handler*****************/
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg.trim());
    }
}
123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657
```

## 12、Netty心跳检测机制案例

**实例要求:**

- 编写一个 Netty心跳检测机制案例, 当服务器超过3秒没有读时，就提示读空闲
- 当服务器超过5秒没有写操作时，就提示写空闲
- 实现当服务器超过7秒没有读或者写操作时，就提示读写空闲

**启动类：**

```java
public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.INFO))  //为BossGroup中的请求添加日志处理Handler
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    //加入一个 netty 提供的 IdleStateHandler
                    /**
                             * 1、IdleStateHandler 是 netty 提供的检测空闲状态的处理器
                             * 2、long readerIdleTime：表示多长时间没有读，就会发送一个心跳检测包检测是否还是连接的状态
                             * 3、long writerIdleTime：表示多长时间没有写，就会发送一个心跳检测包检测是否还是连接的状态
                             * 4、long allIdleTime：表示多长时间没有读写，就会发送一个心跳检测包检测是否还是连接的状态
                             * 5、当 IdleStateEvent 触发后，就会传递给管道的下一个 Handler，通过调用（触发）下一个Handler的 userEventTriggered，在该方法区处理这个事件。
                             */
                    pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));

                    //加入一个对空闲检测进一步处理的Handler（自定义）
                    pipeline.addLast(new MyServerHandler());
                }
            });
        //启动服务器，设置为同步模式。
        ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
        channelFuture.channel().closeFuture().sync();
    } finally {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
1234567891011121314151617181920212223242526272829303132333435
```

**Handler：**

```java
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            //将 evt 向下转型 IdleStateEvent
            IdleStateEvent event = (IdleStateEvent)evt;
            String eventTye = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventTye = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventTye = "写空闲";
                    break;
                case ALL_IDLE:
                    eventTye = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() +"---超时时间--" + eventTye);
            System.out.println("服务器做相应处理。。");
        }
    }
}
1234567891011121314151617181920212223242526272829
```

## 13、Netty建立Websocket连接

**实例要求：**

- Http协议是无状态的, 浏览器和服务器间的请求响应一次，下一次会重新创建连接.
- 要求：实现基于webSocket的长连接的全双工的交互
- 改变Http协议多次请求的约束，实现长连接了， 服务器可以发送消息给浏览器
- 客户端浏览器和服务器端会相互感知，比如服务器关闭了，浏览器会感知，同样浏览器关闭了，服务器会感知

**启动代码：**

```java
public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    try{
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    //因为是基于Http协议，所以要使用Http的编码和解码器
                    pipeline.addLast(new HttpServerCodec());
                    //是以块方式写，添加ChunkedWriter处理器
                    pipeline.addLast(new ChunkedWriteHandler());
                    /**
                    * 1、http数据在传输过程中是分裂的,HttpObjectAggregator就可以将多个段聚合
                    * 2、这就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                    */
                    pipeline.addLast(new HttpObjectAggregator(8192));

                    /**
                    * 1、对于websocket，它的数据是以帧的形式传递的
                    * 2、可以看到 WebsocketFrame 下面有六个子类
                    * 3、浏览器请求时：ws://localhost:7000/hello 表示请求的uri
                    * 4、WebSocketServerProtocolHandler 核心功能是将 http 协议升级为 ws 协议，保持长连接
                    * 5、从Http协议升级到Websocket协议，是通过StatusCode 101（Switching Protocols）来切换的。
                    */
                    pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                    //自定义Handler，处理业务逻辑
                    pipeline.addLast(new MyTextWebSocketFrameHandler());
                }
            });
        ChannelFuture sync = serverBootstrap.bind(7000).sync();
        sync.channel().closeFuture().sync();
    } finally {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
123456789101112131415161718192021222324252627282930313233343536373839404142
```

## 14、案例简单总结

**创建启动类：**

- 首先初始化两个NioEventLoopGroup。其中BossGroup一般设置线程为1
- 初始化一个ServerBootStrap类。并调用它设置很多参数。
  - `group()`：服务端设置两个Group，客户端设置一个Group
  - `chnnel()`：服务端传入`NioServerSocketChannel`，客户端传入`NioSocketChannel`
  - `option()`：服务端给BossGroup设置`SO_BACKLOG`任务队列大小
  - `childOption()`：服务端给WorkerGroup设置连接`SO_KEEPALIVE`保持连接状态
  - `handler()`：服务端给BossGroup设置Handler，客户端设置Handler
  - `childHandler()`：服务端给WorkerGroup设置Handler。
- 通过BootStrap去绑定端口，监听关闭事件。设置为同步

**Handler：**

- `SimpleChannelInboundHandler`
  - 可以继承它来处理很多通信。经过上面几个案例推敲，一般写自己的Handler继承它就可以了
- `ChannelInboundHandlerAdapter`
  - 这个是上一个的父类，我们在心跳检测的时候通过继承它的`userEventTriggered`去判断连接状态
  - 其实通过上面那个`simple`也可以继承这个`trigger`
- `IdleStateHandler`
  - 在心跳检测时我们要通过这个Handler去触发上面的`trigger`
- `HttpServerCodec`
  - 提供好的用于Http编码解码，一般用于Http请求
- `ChunkedWriteHandler`
  - 提供好的Handler，以块方式写，添加ChunkedWriter处理器
  - 我搜了一下，它一般用于发送大文件。这个东西使我们在Websocket的时候用的。
- `HttpObjectAggregator`
  - 它会将http数据聚合在一起发送
- `WebSocketServerProtocolHandler`
  - 传入ws路径，将Http协议升级成为ws协议

**Netty中通信数据实体：**

- `TextWebSocketFrame`
  - 这是我们在websocket连接的时候用的，它表示一个文本帧，是websocket进行通信的数据形式
- `HttpObject`
  - 这是我们在建立Http连接的时候用到的，可以将它转换成一个`HttpRequest`

**Hander常用方法：**

| 方法名                                                       | 介绍                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| `channelRead0(ChannelHandlerContext channelHandlerContext, T t)` | 读取数据，并进行消息转发                                     |
| `handlerAdded(ChannelHandlerContext ctx)`                    | 连接建立，一旦建立连接，就第一个被执行                       |
| `channelActive(ChannelHandlerContext ctx)`                   | 表示 channel 处于活动状态，提示 xxx 上线                     |
| `channelInactive(ChannelHandlerContext ctx)`                 | 表示 channel 处于不活动状态，提示 xxx 离线                   |
| `handlerRemoved(ChannelHandlerContext ctx)`                  | 表示 channel 断开连接，将xx客户离开信息推送给当前在线客户    |
| `exceptionCaught(ChannelHandlerContext ctx, Throwable cause)` | 出现错误如何进行处理                                         |
| `userEventTriggered(ChannelHandlerContext ctx, Object evt)`  | 事件触发器，通过判断evt的类型去判断发生了什么事件，再通过里面的属性判断事件发生的类型。我们在`IdleStateHandler`后面加上一个触发器，可以检测心跳。 |

# 五、GoogleProtobuf

## 1、编码与解码

- 编写网络应用程序时，因为数据在网络中传输的都是二进制字节码数据，在发送数据时就需要编码，接收数据时就需要解码
- `codec`(编解码器) 的组成部分有两个：`decoder`(解码器)和 `encoder`(编码器)。`encoder` 负责把业务数据转换成字节码数据，`decoder` 负责把字节码数据转换成业务数据。
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200227152528968.png)

**Netty本身的编解码的机制和问题分析（为什么要引入Protobuf)**

- Netty 提供的编码器   
  - StringEncoder，对字符串数据进行编码
  - ObjectEncoder，对 Java 对象进行编码
- Netty 提供的解码器   
  - StringDecoder, 对字符串数据进行解码
  - ObjectDecoder，对 Java 对象进行解码
- Netty 本身自带的 ObjectDecoder 和 ObjectEncoder 可以用来实现 POJO 对象或各种业务对象的编码和解码，底层使用的仍是 Java 序列化技术 , 而Java 序列化技术本身效率就不高，存在如下问题   
  - 无法跨语言
  - 序列化后的体积太大，是二进制编码的 5 倍多。
  - 序列化性能太低
  - 引出 新的解决方案 [Google 的 Protobuf]

## 2、Protobuf简介

 首先Protobuf是用来将对象序列化的，相类似的技术还有Json序列化等等。它是一种高效的结构化数据存储格式，可以用于结构化数据串行化（序列化）。它很适合做数据存储或**RPC（远程过程调用）数据交换格式**。目前很多公司 `http+json =》 tcp+protobuf`

- Protobuf 是以 message 的方式来管理数据的
- 支持跨平台、跨语言，即[客户端和服务器端可以是不同的语言编写的] （支持目前绝大多数语言，例如 C++、C#、Java、python 等
- 高性能，高可靠性
- 使用 protobuf 编译器能自动生成代码，Protobuf 是将类的定义使用.proto 文件进行描述。说明，在idea 中编写 .proto 文件时，会自动提示是否下载 .ptotot 编写插件. 可以让语法高亮。
- 然后通过 protoc.exe 编译器根据.proto 自动生成.java 文件
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200227152546169.png)

## 3、proto文件格式

 首先我们需要在`.proto`文件中定义好实体及他们的属性，再进行编译成`java`对象为我们所用。下面将介绍`proto`文件的写法。

**文件头**

 就想我们写`java`需要写`package`包名一样，`.proto`文件也要写一些文件的全局属性，主要用于将`.proto`文件编译成`Java`文件。

| `实例`                                 | `介绍`                       |
| -------------------------------------- | ---------------------------- |
| `syntax="proto3";`                     | 声明使用到的protobuf的版本   |
| `optimize_for=SPEED;`                  | 表示                         |
| `java_package="com.mical.netty.pojo";` | 表示生成Java对象所在包名     |
| `java_outer_classname="MyWorker";`     | 表示生成的Java对象的外部类名 |

 我们一般将这些代码写在`proto`文件的开头，以表明生成`Java`对象的相关文件属性。

**定义类和属性**

```protobuf
syntax = "proto3"; //版本
option optimize_for = SPEED; //加快解析
option java_outer_classname = "MyDataInfo"; //生成的外部类名，同时也是文件名

message Student { //会在StudentPojo 外部类生成一个内部类Student，他是真正发送的pojo对象
    int32 id = 1; //Student类中有一个属性名字为ID，类型为int32（protobuf类型），1表示序号，不是值
    string name = 2;
}

enum DateType {
    StudentType = 0; //在proto3中，要求enum的编号从0开始
    WorkerType = 1;
}
12345678910111213
```

 如上图所示，我们在文件中不但声明了protobuf的版本，还声明了生成java对象的类名。当生成java对象后，`MyDataInfo`将是对象的类名，同时，它使用`message`声明了`Student`这个内部类，使用`enum`声明了`DataType`这个内部枚举类。就像下面这个样子

- `messag`：声明类。
- `enum`：声明枚举类。

```java
public final class MyDataInfo {
    public static final class Student { }
    public enum DataType { }
}
1234
```

然后需要注意的是，protobuf中的变量类型和其他语言的声明有所不同。下面是类型的对照表。

| .proto类型 | java类型    | C++类型    | 备注                                                         |
| ---------- | ----------- | ---------- | ------------------------------------------------------------ |
| **double** | **double**  | **double** |                                                              |
| **float**  | **float**   | **float**  |                                                              |
| **int32**  | **int**     | **int32**  | **使用可变长编码方式。编码负数时不够高效——如果你的字段可能含有负数，那么请使用sint32。** |
| **int64**  | **long**    | **int64**  | **使用可变长编码方式。编码负数时不够高效——如果你的字段可能含有负数，那么请使用sint64。** |
| unit32     | int[1]      | unit32     | 总是4个字节。如果数值总是比总是比228大的话，这个类型会比uint32高效。 |
| unit64     | long[1]     | unit64     | 总是8个字节。如果数值总是比总是比256大的话，这个类型会比uint64高效。 |
| sint32     | int         | int32      | 使用可变长编码方式。有符号的整型值。编码时比通常的int32高效。 |
| sint64     | long        | int64      | 使用可变长编码方式。有符号的整型值。编码时比通常的int64高效。 |
| fixed32    | int[1]      | unit32     |                                                              |
| fixed64    | long[1]     | unit64     | 总是8个字节。如果数值总是比总是比256大的话，这个类型会比uint64高效。 |
| sfixed32   | int         | int32      | 总是4个字节。                                                |
| sfixed64   | long        | int64      | 总是8个字节。                                                |
| **bool**   | **boolean** | **bool**   |                                                              |
| **string** | **String**  | **string** | **一个字符串必须是UTF-8编码或者7-bit ASCII编码的文本。**     |
| bytes      | ByteString  | string     | 可能包含任意顺序的字节数据                                   |

 类型关注之后,我们看到代码中`string name = 2`，它并不是给name这个变量赋值，而是给它标号。每个类都需要给其中的变量标号，且需要注意的是类的标号是从1开始的，枚举的标号是从0开始的。

**复杂对象**

 当我们需要统一发送对象和接受对象时，就需要使用一个对象将其他所有对象进行包装，再获取里面的某一类对象。

```java
syntax = "proto3"; //版本
option optimize_for = SPEED; //加快解析
option java_outer_classname = "MyDataInfo"; //生成的外部类名，同时也是文件名

message MyMessage {
    //定义一个枚举类型
    enum DateType {
        StudentType = 0; //在proto3中，要求enum的编号从0开始
        WorkerType = 1;
    }

    //用data_type来标识传的是哪一个枚举类型
    DateType data_type = 1;

    //标识每次枚举类型最多只能出现其中的一个类型，节省空间
    oneof dataBody {
        Student stuent = 2;
        Worker worker = 4;
    }
}

message Student { //会在StudentPojo 外部类生成一个内部类Student，他是真正发送的pojo对象
    int32 id = 1; //Student类中有一个属性名字为ID，类型为int32（protobuf类型），1表示序号，不是值
    string name = 2;
}
message Worker {
    string name = 1;
    int32 age = 2;
}
1234567891011121314151617181920212223242526272829
```

 这里面我们定义了`MyMessage`、`Student`、`Worker`三个对象，`MyMessage`里面持有了一个枚举类`DataType`和，`Student`、`Worker`这两个类对象中的其中一个。这样设计的目的是什么呢？当我们在发送对象时，设置`MyMessage`里面的对象的同时就可以给枚举赋值，这样当我们接收对象时，就可以根据枚举判断我们接受到哪个实例类了。

## 4、Netty中使用Protobuf

- 需要给发送端的`pipeline`添加编码器：`ProtobufEncoder`。

  ```java
  bootstrap.group(group)
      .channel(NioSocketChannel.class)
      .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              pipeline.addLast("encoder", new ProtobufEncoder());
              pipeline.addLast(new ProtoClientHandler());
          }
      });
  12345678910
  ```

- 需要在接收端添加解码器：`ProtobufDecoder`

  ```java
  serverBootstrap.group(bossGroup, workerGroup)
      .channel(NioServerSocketChannel.class)
      .handler(new LoggingHandler())
      .option(ChannelOption.SO_BACKLOG, 128)
      .childOption(ChannelOption.SO_KEEPALIVE, true)
      .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              //需要指定对哪种对象进行解码
              pipeline.addLast("decoder", new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
              pipeline.addLast(new ProtoServerHandler());
          }
      })
  1234567891011121314
  ```

- 在发送时，如何构造一个具体对象呢？以上面复杂对象为例，我们主要构造的是`MyMessage`对象，设置里面的枚举属性，和对应的对象。

  ```java
  MyDataInfo.MyMessage build = MyDataInfo.MyMessage.
      newBuilder().
      setDataType(MyDataInfo.MyMessage.DateType.StudentType)
      .setStuent(MyDataInfo.Student
                          .newBuilder()
                 .setId(5)
                 .setName("王五")
                 .build())
      .build();
  123456789
  ```

- 在接收对象时，我们就可以根据枚举变量去获取实例对象了。

  ```java
  MyDataInfo.MyMessage message = (MyDataInfo.MyMessage) msg;
  MyDataInfo.MyMessage.DateType dataType = message.getDataType();
  
  switch (dataType) {
      case StudentType:
          MyDataInfo.Student student = message.getStuent();
          System.out.println("学生Id = " + student.getId() + student.getName());
      case WorkerType:
          MyDataInfo.Worker worker = message.getWorker();
          System.out.println("工人：name = " + worker.getName() + worker.getAge());
      case UNRECOGNIZED:
          System.out.println("输入的类型不正确");
  }
  12345678910111213
  ```

# 六、Netty的Handler



## 1、Handler介绍

- netty的组件设计：Netty的主要组件有`Channel、EventLoop、ChannelFuture、ChannelHandler、ChannelPipe`等
- 我们先来复习一下`ChannelHandler`和`ChannelPipeline`的关系。示例图如下：我们可以将`pipeline`理解为一个双向链表，`ChannelHandlerContext`看作链表中的一个节点，`ChannelHandler`则为每个节点中保存的一个属性对象。
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200228002406548.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- `ChannelHandler`充当了处理入站和出站数据的应用程序逻辑的容器。例如，实现`ChannelInboundHandler`接口（或`ChannelInboundHandlerAdapter`），你就可以接收入站事件和数据，这些数据会被业务逻辑处理。当要给客户端发送响应时，也可以从`ChannelInboundHandler`冲刷数据。业务逻辑通常写在一个或者多个`ChannelInboundHandler`中。`ChannelOutboundHandler`原理一样，只不过它是用来处理出站数据的
- `ChannelPipeline`提供了`ChannelHandler`链的容器。以客户端应用程序为例，如果事件的运动方向是从客户端到服务端的，那么我们称这些事件为出站的，即客户端发送给服务端的数据会通过`pipeline`中的一系列`ChannelOutboundHandler`，并被这些`Handler`处理，反之则称为入站的
- 简单来说，以服务器端为例：接受数据的过程就是入站，发送数据的过程就是出站。客户端也一样。
- 下面，来看看我们常用的`Handler`的关系图：`Inbound`处理入站，`Outbound`处理出站
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200228002044542.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- 一般来说，在我们接收数据时将数据解码后，就进行业务的相关处理，所以上图的入站的常用类更多。在数据出站时，一般我们只需要将数据编码后直接发出。

## 2、Handler链式调用

 我们知道，`Pipeline`中的`Handler`可以当作一个双向链表。但是，`Handler`却又存在着入站和出站之分。那么`Netty`是如何将两种类型的`Handler`保存在一个链表中，却又能够入站的时候调用`InboundHandler`，出栈的时候调用`OutBoundHandler`呢？看下图，黄色的表示入站，以及入站的`Handler`，绿色的表示出站，以及出站的`Handler`。
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020022800205860.png)
 ​ 当我们调用如下代码时，我们就会获得一个上图所示的`Handler`链表。下面代码时在`ChannelInitializer`类中添加`Handler`的部分代码。

```java
@Override
protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new LongToByteEncoder()); //out
    pipeline.addLast(new ByteToLongDecoder()); //in
    pipeline.addLast(new OutBoundHandler()); //out
    pipeline.addLast(new InBoundHandler()); //in
}
12345678
```

 当一个请求来了的时候，首先会将请求发给`pipeline`中位于链表首部的`Handler`。如图所示，首先由`LongToByteEncoder`（这个东西不管，就是个出站的）接受到入站请求，但是这个东西是个`OutBound`。所以它收到入站请求时就不做处理，直接转发给它的下一个`ByteToLongDecoder`（这个东西也不管，它是入站的）。这个东西接受到了入站请求了，一看它自己也是一个`Inbound`，所以它就将请求的数据进行处理，然后转发给下一个。之后又是一个`Outbound`，然后再进行转发，到了最后的`InBoundHandler`，在这里我们可以进行业务的处理等等操作。

 然后如果需要返回数据，我们就调用`writeAndFlush`方法，这个方法可不简单，当他一调用，就会触发出站的请求，然后就由当前所在的`Handler`节点往回调用。往回调用的途中，如果遇到`InBound`就直接转发给下一个`Handler`，直到最后将消息返回。

通过上面的描述，我们可以总结添加`Handler`的以下节点总结：

- 调用`InboundHandler`的顺序和添加的顺序是一致的。
- 调用`OutboundHandler`的顺序和添加它的顺序是相反的。
- 链表的末尾不能有`OutHandler`，因为如果最后是`OutHandler`的话，当他前面的`InHandler`处理完数据返回消息调用`writeflush`时，它直接在前面进行反向调用了，就调用不到最后的这个`Out`了。所以我们平常可以将`OutHandler`写在前面，`InHandler`写在后面。
- `InHandler`一旦进行`writeAndFlush`，只有这个`InHandler`之前添加的`OutHandler`能够处理他

## 3、Handler编解码器

- 当Netty发送或者接受一个消息的时候，就将会发生一次数据转换。入站消息会被解码：从字节转换为另一种格式（比如java对象）；如果是出站消息，它会被编码成字节。
- Netty提供一系列实用的编解码器，他们都实现了ChannelInboundHadnler或者ChannelOutboundHandler接口。在这些类中，channelRead方法已经被重写了。以入站为例，对于每个从入站Channel读取的消息，这个方法会被调用。随后，它将调用由解码器所提供的decode()方法进行解码，并将已经解码的字节转发给ChannelPipeline中的下一个ChannelInboundHandler。

**解码器-ByteToMessageDecoder**

- 由于不可能知道远程节点是否会一次性发送一个完整的信息，tcp有可能出现粘包拆包的问题，这个类会对入站数据进行缓冲，直到它准备好被处理。

- 下面是段示例代码：

  ```java
  public class ToIntegerDecoder extends ByteToMessageDecoder {
      @Override
      protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
          if (in.readableBytes() >= 4) {
              out.add(in.readInt());
          }
      }
  }
  12345678
  ```

  - 这个例子，每次入站从`ByteBuf`中读取4字节，将其解码为一个`int`，然后将它添加到下一个`List`中。当没有更多元素可以被添加到该`List`中时，它的内容将会被发送给下一个`ChannelInboundHandler`。int在被添加到List中时，会被自动装箱为Integer。在调用`readInt()`方法前必须验证所输入的`ByteBuf`是否具有足够的数据
     ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200228002117156.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

**解码器-ReplayingDecoder**

- `public abstract class ReplayingDecoder extends ByteToMessageDecoder{ }`

- `ReplayingDecoder`扩展了`ByteToMessageDecoder`类，使用这个类，我们不必调用`readableBytes()`方法。参数S指定了用户状态管理的类型，其中Void代表不需要状态管理

- 下面是代码示例：这段代码起到了上面`ByteToMessageDecoder`一样的作用

  ```java
  public class ByteToLongDecoder2 extends ReplayingDecoder<Void> {
      @Override
      protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
          out.add(in.readLong());
      }
  }
  123456
  ```

- `ReplayingDecoder`使用方便，但它也有一些局限性：

  - 并不是所有的 `ByteBuf` 操作都被支持，如果调用了一个不被支持的方法，将会抛出一个 `UnsupportedOperationException`。
  - `ReplayingDecoder` 在某些情况下可能稍慢于 `ByteToMessageDecoder`，例如网络缓慢并且消息格式复杂时，消息会被拆成了多个碎片，速度变慢

**其他解码器**

- `LineBasedFrameDecoder`：这个类在Netty内部也有使用，它使用行尾控制字符（\n或者\r\n）作为分隔符来解析数据。
- `DelimiterBasedFrameDecoder`：使用自定义的特殊字符作为消息的分隔符。
- `HttpObjectDecoder`：一个HTTP数据的解码器
- `LengthFieldBasedFrameDecoder`：通过指定长度来标识整包消息，这样就可以自动的处理黏包和半包消息。

## 4、简单实例

**实例要求**

- 使用自定义的编码器和解码器来说明Netty的handler 调用机制
- 客户端发送long -> 服务器
- 服务端发送long -> 客户端

**实例代码**

 这里只展示，`Handler`相应的代码和添加`Handler`的关键代码。

**Decoder**：

```java
public class ByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * decode 方法会根据接收到的数据，被调用多次，知道确定没有新的元素被添加到list，或者是ByteBuf 没有更多的可读字节为止
     * 如果 list out不为空，就会将list的内容传递给下一个 Handler 进行处理，该处理器的方法也会被调用多次。
     * @param ctx 上下文对象
     * @param in 入栈的 ByteBuf
     * @param out list集合，将解码后的数据传给下一个Handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //因为long为8个字节，所以需要8个字节才能读取成一个long类型的数据
        System.out.println("ByteToLongDecoder：入栈数据被解码");
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
123456789101112131415161718
```

**Encoder：**

```java
public class LongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("LongToByteEncoder: 出栈数据，msg = " + msg);
        out.writeLong(msg);
    }
}
1234567
```

**服务端添加Handler：**

```java
@Override
protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new LongToByteEncoder()); //编码器，出站
    pipeline.addLast(new ByteToLongDecoder()); //解码器，入站
    pipeline.addLast(new ServerInBoundHandler()); //业务处理，入站
}
1234567
```

**客户端添加Handler：**

```java
@Override
protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new LongToByteEncoder()); //编码器，出站
    pipeline.addLast(new ByteToLongDecoder()); //解码器，入站
    pipeline.addLast(new ClientInBoundHandler()); //业务处理，入站。
}
1234567
```

 这里当客户端或者服务端接受消息的时候，首先会调用入站的解码器，然后业务处理，然后调用出站的编码器返回消息。后面可以在业务处理类中，增加发送消息的代码，此处省略。

## 5、Log4j整合到Netty

**添加依赖**

```xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.25</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.25</version>
    <scope>test</scope>
</dependency>
12345678910111213141516171819202122
```

**添加配置文件：**

 在`resource`目录下新建`log4j.properties`即可

```properties
log5j.rootLogger=DEBUG, stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=[%p] %C{1} - %m%n
1234
```

# 七、TCP粘包和拆包

## 1、什么是拆包和粘包

- TCP是面向连接的，面向流的，提供高可靠性服务。收发两端（客户端和服务器端）都要有一一成对的socket，因此，发送端为了将多个发给接收端的包，更有效的发给对方，使用了优化方法（Nagle算法），将多次**间隔较小且数据量小**的数据，合并成一个大的数据块，然后进行封包。这样做虽然提高了效率，但是接收端就难于分辨出完整的数据包了，因为**面向流的通信是无消息保护边界**的
- 由于TCP无消息保护边界，需要在接收端处理消息边界问题，也就是我们所说的粘包、拆包问题。
- 通常的解决方案是：发送端每发送一次消息，就需要在消息的内容之前携带消息的长度，这样，接收方每次先接受消息的长度，再根据长度去读取该消息剩余的内容。如果`socket`中还有没有读取的内容，也只能放在下一次读取事件中进行。

## 2、拆包、粘包的图解

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303141016689.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

 假设客户端同时发送了两个数据包D1和D2给服务端，由于服务端一次读取到字节数是不确定的，固可能存在以下四种情况：

1. 服务端分两次读取到了两个独立的数据包，分别是D1和D2，没有粘包和拆包
2. 服务端一次接受到了两个数据包，D1和D2粘合在一起，称之为TCP粘包
3. 服务端分两次读取到了数据包，第一次读取到了完整的D1包和D2包的部分内容，第二次读取到了D2包的剩余内容，这称之为TCP拆包
4. 服务端分两次读取到了数据包，第一次读取到了D1包的部分内容D1_1，第二次读取到了D1包的剩余部分内容D1_2和完整的D2包。

## 3、解决方案图解

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020030314102917.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

- 我们可以在数据包的前面加上一个固定字节数的数据长度，如加上一个 `int`（固定四个字节）类型的数据内容长度
- 就算客户端同时发送两个数据包到服务端，当服务端接受时，也可以先读取四个字节的长度，然后根据长度获取消息的内容，这样就不会出现多读取或者少读取的情况了。

## 4、TCP粘包代码示例

 本实例主要演示出现拆包和粘包的场景。

**客户端：**

 我们将使用循环连续发送10个`String`类型的字符串。这里相当于发送了10次。

```java
@Override
public void channelActive(ChannelHandlerContext ctx) throws Exception {
    //使用客户端发送10条数据，hello，server
    for (int i = 0; i < 10; i++) {
        String msg = "server" + i + "  ";
        System.out.println("发送消息 " + msg);
        ByteBuf byteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }
}
12345678910
```

**服务端：**

 我们接受客户端发过来的字符串。

```java
private int count = 0;
@Override
protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    byte[] bytes = new byte[msg.readableBytes()];
    msg.readBytes(bytes);

    //将buffer转成字符串
    String message = new String(bytes, CharsetUtil.UTF_8);

    System.out.println("服务器接收到数据 " + message);
    System.out.println("服务器接收到消息量 = " + (++this.count));

    //服务器回送数据到客户端，回送一个随机Id
    ByteBuf response = Unpooled.copiedBuffer(UUID.randomUUID().toString() + "--", CharsetUtil.UTF_8);
    ctx.writeAndFlush(response);
}
12345678910111213141516
```

服务端输出结果如下：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303141043195.png)

- 我们可以看到，服务端直接一次就把我们客户端10次发送的内容读取完成了。
- 这里也印证了我们开篇所说的，当**数据量小且发送间隔短**，如果我们客户端每次发送的都是不同的结果，这种情况下我们就不知道客户端返回了多少次结果以及每次结果究竟是什么。这就是我们本篇需要解决的问题。

## 5、解决方案代码示例

- 使用自定义协议 + 编解码器 来解决
- 关键就是要解决 **服务器端每次读取数据长度的问题**, 这个问题解决，就不会出现服务器多读或少读数据的问题，从而避免的TCP 粘包、拆包 。

**自定义Message对象：**

```java
public class MessageProtocol {
    private int len; //关键
    private byte[] content;
}
1234
```

**添加将ByteBuf转换成Message的解码器：**

```java
public class MessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MessageDecoder 被调用");
        //需要将获取到的二进制字节码转换成 MessageProtocol
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        //封装成 MessageProtocol 对象，放入 out，传递到下一个Handler
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);
        out.add(messageProtocol);
    }
}
12345678910111213141516
```

**添加将Message转换为ByteBuf的编码器：**

```java
public class MessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MessageEncoder 方法被调用");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
12345678
```

**客户端连续发送3个Message对象：**

```java
@Override
public void channelActive(ChannelHandlerContext ctx) throws Exception {
    //使用客户端发送10条数据，"今天天气冷，吃火锅" 编号
    for (int i = 0; i < 3; i++) {
        String message = "Server" + i;
        byte[] content = message.getBytes(CharsetUtil.UTF_8);
        int length = content.length;

        //创建协议包对象
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);
        ctx.writeAndFlush(messageProtocol);
    }
}
123456789101112131415
```

**服务端接收：**

```java
//接收的Handler继承了SimpleChannelInboundHandler，以MessageProtocol的类型接受消息
@Override
protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
    //接收到数据，并处理
    int len = msg.getLen();
    byte[] content = msg.getContent();

    System.out.println("服务器第 " + (++count) +" 次接收到信息如下：");
    System.out.println("长度：" + len);
    System.out.println("内容：" + new String(content, CharsetUtil.UTF_8));

    //回复消息
    String response = UUID.randomUUID().toString();
    int length = response.getBytes(CharsetUtil.UTF_8).length;
    MessageProtocol messageProtocol = new MessageProtocol();
    messageProtocol.setLen(length);
    messageProtocol.setContent(response.getBytes());
    ctx.writeAndFlush(messageProtocol);
}
12345678910111213141516171819
```

**结果展示：**
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303141056585.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

- 首先，当客户端的通道激活后，就直接调用方法发送10个Message对象。
- 服务端接收对象时，首先调用`MessageDecoder`进行解码，将`ByteBuf`类型的数据转换成`MessageProtocol`，然后再进入进行读取的`Handler`中读取消息。
- 最后返回给客户端消息，调用`MessageEncoder`将`MessageProtocol`转换成`Byte`然后发送出去。

# 八、Netty简单RPC调用

## 1、RPC基本介绍

- `RPC（Remote Procedure Call`,远程过程调用，是一个计算机通信协议。该协议允许运行于一台计算机的程序调用另一台计算机的子程序，而程序员无需额外地为这个交互作用编程
- 两个或多个应用程序都分布在不同的服务器上，它们之间的调用都像是本地方法调用一样(如图)
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303163149256.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)
- 常见的 RPC 框架有: 比较知名的如阿里的Dubbo、google的gRPC、Go语言的rpcx、Apache的thrift， Spring 旗下的 Spring Cloud。

**RPC调用流程：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303163202344.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM1NzUxMDE0,size_16,color_FFFFFF,t_70)

> 在RPC 中， Client 叫服务消费者，Server 叫服务提供者

**PRC调用流程说明**

1. 服务消费方(client)以本地调用方式调用服务
2. client stub 接收到调用后负责将方法、参数等封装成能够进行网络传输的消息体
3. client stub 将消息进行编码并发送到服务端
4. server stub 收到消息后进行解码
5. server stub 根据解码结果调用本地的服务
6. 本地服务执行并将结果返回给 server stub
7. server stub 将返回导入结果进行编码并发送至消费方
8. client stub 接收到消息并进行解码
9. 服务消费方(client)得到结果

小结：RPC 的目标就是将 2-8 这些步骤都封装起来，用户无需关心这些细节，可以像调用本地方法一样即可完成远程服务调用。

## 2、代码示例

**服务接口：**

```java
public interface HelloService {

    String hello(String message);
}
1234
```

**服务端服务接口实现类：**

```java
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String message) {
        System.out.println("收到客户端消息=" + message);
        //根据 message 返回不同的结果
        if(message != null) {
            return "你好客户端，我已经收到你的消息【" + message + "】";
        } else {
            return "你好客户端，我已经收到你的消息。";
        }
    }
}
123456789101112
```

**服务端初始化Netty：**

```java
public class NettyServer {

    public static  void startServer(String hostName, int port) {
        startServer0(hostName, port);
    }

    private static void startServer0(String hostname, int port) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new NettyServerHandler()); //业务处理器
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(hostname,port).sync();
            System.out.println("服务提供方开始运行");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
             bossGroup.shutdownGracefully();
             workerGroup.shutdownGracefully();
        }
    }
}
12345678910111213141516171819202122232425262728293031323334
```

**服务端Handler：**

```java
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息，并调用服务
        System.out.println("msg=" + msg);
        //客户端在调用服务器的api 时，我们需要定义一个协议
        //比如要求，每次发消息时，都必须以某个字符串开头 "HelloService#hello#你好"
        if (msg.toString().startsWith("HelloService#hello#")) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
123456789101112131415161718
```

**Netty客户端**

```java
public class NettyClient {
    //创建一个线程池
    private static ExecutorService executor= Executors.newFixedThreadPool(5);

    private static NettyClientHandler client;

    //编写方法，使用代理模式，获取一个代理对象
    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass},
                (proxy, method, args) -> {
                    System.out.println("代理被调用");
                    if (client == null)
                        initClient();
                    //设置要发给服务器端的信息
                    client.setPara(providerName + args[0]);
                    return executor.submit(client).get();
                });
    }


    //初始化客户端
    private static void initClient() {
        client = new NettyClientHandler();
        //创建EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(client);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7000).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
123456789101112131415161718192021222324252627282930313233343536373839404142434445
```

**客户端Handler**

```java
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context; //上下文
    private String result; //返回的结果
    private String para; //客户端调用方法时，传入的参数

    //与服务端创建连接后调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道连接成功");
        context = ctx; //因为我们在其他方法会使用到 ctx
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify(); //唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //被代理对象的调用，真正发送数据给服务器，发送完后就阻塞，等待被唤醒（channelRead）
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("线程被调用-----");
        context.writeAndFlush(para);
        //进行wait
        wait(); //等待 channelRead 获取到服务器的结果后，进行唤醒。
        return result; //服务方返回的结果
    }

    public void setPara(String para){
        this.para = para;
    }
}
123456789101112131415161718192021222324252627282930313233343536373839
```

**客户端启动并调用远程方法：**

```java
public class ClientBootStrap {

    //这里定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        //创建一个消费者
        NettyClient customer = new NettyClient();
        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);

        //通过代理对象调用服务提供者的方法
        String res = service.hello("你好 Dubbo");
        System.out.println("调用的结果，res = " + res);
        Thread.sleep(2000);
    }
}
1234567891011121314151617
```

> 本文档整理并出 自尚硅谷韩顺平Netty教程