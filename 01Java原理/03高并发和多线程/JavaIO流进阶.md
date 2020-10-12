## NIO流（New IO）

和IO流的目的相同，但是实现方式不同。NIO使用块，效率比IO更高。

主要包括两种API：**标准输入输出NIO和网络编程NIO**



标准NIO包括**channel（管道）**、**Buffer（缓冲区)**

> **Buffer**是一个对象，它包含一些要写入或读出的数据。在NIO中，数据是放入buffer对象的，而在IO中，数据是直接写入或者读到Stream对象的。**应用程序不能直接对 Channel 进行读写操作，而必须通过 Buffer 来进行**，即 Channel 是通过 Buffer 来读写数据的。
>
> 在NIO中，所有的数据都是用Buffer处理的，它是NIO读写数据的中转池。Buffer实质上是一个数组，通常是一个字节数据，但也可以是其他类型的数组。但一个缓冲区不仅仅是一个数组，重要的是它提供了对数据的结构化访问，而且还可以跟踪系统的读写进程。
>
> 使用 Buffer 读写数据一般遵循以下四个步骤：
>
> 1. 写入数据到 Buffer；
>
> 2. 调用 flip() 方法；
>
> 3. 从 Buffer 中读取数据；
>
> 4. 调用 clear() 方法或者 compact() 方法。
>
>    解释：当向 Buffer 写入数据时，Buffer 会记录下写了多少数据。一旦要读取数据，需要通过 flip() 方法将 Buffer **从写模式切换到读模式**。在读模式下，可以读取之前写入到 Buffer 的所有数据。

> **Channel**是一个对象，可以通过它读取和写入数据。可以把它看做IO中的流。但是它和流相比还有一些不同：
>
> 1. Channel是**双向的**，既可以读又可以写，而流是单向的
> 2. Channel可以进行异步的读写
> 3. 对Channel的读写必须通过buffer对象
>
> 正如上面提到的，所有数据都通过Buffer对象处理，所以，您永远不会将字节直接写入到Channel中，相反，您是将数据写入到Buffer中；同样，您也不会从Channel中读取字节，而是将数据从Channel读入Buffer，再从Buffer获取这个字节。
>
> 因为Channel是双向的，所以Channel可以比流更好地反映出底层操作系统的真实情况。特别是在Unix模型中，底层操作系统通常都是双向的。
>
> 在Java NIO中Channel主要有如下几种类型：
>
> - FileChannel：从文件读取数据的
> - DatagramChannel：读写UDP网络协议数据
> - SocketChannel：读写TCP网络协议数据
> - ServerSocketChannel：可以监听TCP连接



网络NIO加上**Selector**

> Selector是一个对象，它可以注册到很多个Channel上，监听各个Channel上发生的事件，并且能够根据事件情况决定Channel读写。这样，通过一个线程管理多个Channel，就可以处理大量网络连接了。





