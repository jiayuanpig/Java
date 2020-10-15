# IO流

IO、NIO



## IO流

主要分为字节流（InputStream、OutputStream）和字符流（Reader、Writer）

![IO流继承关系](https://img-blog.csdnimg.cn/20190418184716728.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM3ODc1NTg1,size_16,color_FFFFFF,t_70)

**缓冲区**

1. 缓冲区就是一段特殊的内存区域，很多情况下当程序需要频繁地操作一个资源（如文件或数据库）则性能会很低，所以为了提升性能就可以将一部分数据暂时读写到缓存区，以后直接从此区域中读写数据即可，这样就显著提升了性能。
2. 对于 Java 字符流的操作都是在缓冲区操作的，所以如果我们想在字符流操作中主动将缓冲区刷新到文件则可以使用 flush() 方法操作。



### 按照用途进行分类

1.1 按照数据的来源（去向）分类

- 是文件：FileInputStream, FileOutputStream, FileReader, FileWriter
- 是byte[]：ByteArrayInputStream, ByteArrayOutputStream
- 是char[]：CharArrayReader, CharArrayWriter
- 是String：StringBufferInputStream(已过时，因为其只能用于String的每个字符都是8位的字符串), StringReader, StringWriter
- 是网络数据流：InputStream, OutputStream, Reader, Writer

1.2 按照格式化输出

- 需要格式化输出：PrintStream（输出字节），PrintWriter（输出字符）

1.3 按缓冲功能分

- 要缓冲：BufferedInputStream, BufferedOuputStream, BuffereaReader, BufferedWriter

1.4 按照数据格式分

- 二进制格式（只要确定不是纯文本格式的），InputStream, OutputStream, 及其所有带Stream子类
- 纯文本格式（比如英文/汉字/或其他编码文字）：Reader, Writer, 及其相关子类

1.5 按照输入输出分

- 输入：Reader， InputStream，及其相关子类
- 输出：Writer，OutputStream，及其相关子类

1.6 特殊需求

- 从Stream转化为Reader，Writer：InputStreamReader，OutputStreamWriter
- 对象输入输出流：ObjectInputStream，ObjectOutputStream
- 进程间通信：PipeInputStream，PipeOutputStream，PipeReader，PipeWriter
- 合并输入：SequenceInputStream
- 更特殊的需要：PushbackInputStream, PushbackReader, LineNumberInputStream, LineNumberReader



### Inputstream/OutputStream与Reader/Writer的区别

**Reader/Writer和InputStream/OutputStream分别是I/O库提供的两套平行独立的等级机构**

- \```InputStream、OutputStream是用来处理``8``位元的流，也就是用于读写ASCII字符和二进制数据；``Reader、Writer是用来处理``16``位元的流，也就是用于读写Unicode编码的字符。`
- `在``JAVA语言中，``byte``类型是``8``位的，``char``类型是``16``位的，所以在处理中文的时候需要用Reader和Writer。`
- 两种等级机构下，有一道桥梁InputStreamReader、OutputStreamWriter负责进行InputStream到Reader的适配和由OutputStream到Writer的适配。 

**在Java中，有不同类型的Reader/InputStream输入流对应于不同的数据源**

- FileReader/FileInputStream 用于从文件输入；
- CharArrayReader/ByteArrayInputStream 用于从程序中的字符数组输入；
- StringReader/StringBufferInputStream 用于从程序中的字符串输入；
- PipedReader/PipeInputStream 用于读取从另一个线程中的 PipedWriter/PipeOutputStream 写入管道的数据。
- 相应的也有不同类型的Writer/OutputStream输出流对应于不同的数据源：FileWriter/FileOutputStream，CharArrayWriter/ByteArrayOutputStream，StringWriter，PipeWriter/PipedOutputStream。



## IO模型

- 阻塞IO模型（占着茅坑不拉屎）

  应用程序(也就是我们的代码)想要读取数据就会调用`recvfrom`,而`recvfrom`会通知OS来执行，OS就会判断数据报是否准备好(比如判断是否收到了一个完整的UDP报文，如果收到UDP报文不完整，那么就继续等待)。当数据包准备好了之后，OS就会将数据从内核空间拷贝到用户空间(因为我们的用户程序只能获取用户空间的内存，无法直接获取内核空间的内存)。拷贝完成之后`socket,read()`就会解除阻塞，并得到read的结果。

- 非阻塞IO模型

  NIO就是采用这种方式，当我们new了一个socket后我们可以设置它是非阻塞的。BIO 不会在`recvfrom`也就是`socket.read()`时候阻塞，但是还是会在**将数据从内核空间拷贝到用户空间**阻塞。一定要注意这个地方，**Non-Blocking还是会阻塞的**。

- 多路复用IO模型

  传统情况下client与server通信需要一个3个socket(client的socket，server监听client连接的socket，即serversocket，还有一个server中用来和client通信的socket)，而在IO多路复用中，client与server通信需要的不是socket，而是3个channel，通过channel可以完成与socket同样的操作，channel的底层还是使用的socket进行通信，但是多个channel只对应一个socket(可能不只是一个，但是socket的数量一定少于channel数量)，这样仅仅通过少量的socket就可以完成更多的连接，提高了client容量。

- 信号驱动IO模型

  在多路复用IO模型的基础上加上了信号处理。

- 异步IO模型

  **Asynchronous IO调用中是真正的无阻塞**，其他IO model中多少会有点阻塞。程序发起read操作之后，立刻就可以开始去做其它的事。而在内核角度，当它受到一个asynchronous read之后，首先它会立刻返回，所以不会对用户进程产生任何block。然后，kernel会等待数据准备完成，然后将数据拷贝到用户内存，当这一切都完成之后，kernel会给用户进程发送一个signal，告诉它read操作完成了。

  



















































