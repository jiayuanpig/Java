# IO流

输入流、输出流



## IO流分类

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

  



## IO实例

```java
package com.test;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
public class FileCopy {
    
    /*如果操作文件是纯文本则使用字符流，如果不是纯文本则使用字节流*/
	public static void main(String[] args) {
		//向文件中写入数据(会把原来的数据覆盖掉)
		//writeFile();
 
		//按照单个字符读取
		//readByCharacter();
 
		//按照字符组读取
		//readByCharacterArray();
 
		//对已存在的文件进行续写(不会覆盖原来的数据但是,只能写一次)
		//writeFileContinue();
 
		//将F盘的文件拷贝到D盘
		//copyFileFromFtoD();
 
		//字符缓冲流的读取
		/*缓冲区的出现时为了提高流的操作效率而出现的.
		需要被提高效率的流作为参数传递给缓冲区的构造函数
		在缓冲区中封装了一个数组，存入数据后一次取出*/
		/*读取的内容是：
				窗前明月光，疑是地上霜。
				举头望明月，低头思故乡。
				--李白*/
		//bufferedReader();
 
		//字符缓冲流的写
		//bufferedWriter();
 
 
		//媒体流的时候就会用到字节流
		//将F盘的图片拷贝到D盘
		//copyPictureFromDtoF();
		
		//将F盘的音乐复制到D盘
		//copyMP3FromFtoD();
 
		
 
	}
 
	private static void copyMP3FromFtoD() {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			fi = new FileInputStream("F:\\aa\\guoge.mp3");
			fo = new FileOutputStream("D:/guoge_copy.mp3");
			byte[] buf = new byte[1024];
			int n=0;
			while((n=(fi.read(buf)))!=-1){
				fo.write(buf, 0, n);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fo.close();
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
 
	private static void copyPictureFromDtoF() {
		FileInputStream fi = null;
		FileOutputStream fo = null;
 
		try {
			fi = new FileInputStream("F:\\aa\\004.png");
			fo = new FileOutputStream("D:\\004_copy.png");
			byte[] buf = new byte[1024];
			int n=0;
			while((n=fi.read(buf))!=-1){
				fo.write(buf, 0, n);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fo.close();
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 
	}
 
 
	private static void bufferedWriter() {
		FileWriter file = null;
		BufferedWriter bw = null;
		try {
			file = new FileWriter("F:\\aa\\bb.txt",true);
			bw = new BufferedWriter(file);
 
			//跨平台的换行符
			bw.newLine();
			bw.write("天行健，君子以自强不息；");
			bw.newLine();
			bw.write("地势坤，君子以厚德载物。");
			bw.newLine();
			//缓冲区的写必须有刷新
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bw.close();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
 
	private static void bufferedReader() {
		FileReader file = null;
		try {
			file = new FileReader("F:\\aa\\bb.txt");
 
			BufferedReader br = new BufferedReader(file);
			while(true){
				String s;
				try {
					s = br.readLine();
					if(s==null)break;
					System.out.println(s);
				} catch (IOException e) {
					e.printStackTrace();
				}
 
			}
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	private static void copyFileFromFtoD() {
		FileWriter fw = null;
		FileReader fr = null;
		try {
			fw = new FileWriter("D:\\test20180224.txt",true);
			fr = new FileReader("F:\\aa\\test.txt");
			char[] buf = new char[11];
			int n=0;
			while((n=fr.read(buf))!=-1){
				fw.write(new String(buf, 0, n));
				System.out.println(new String(buf, 0, n));
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fw.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	private static void writeFileContinue() {
		FileWriter file = null;
		try {
			file= new FileWriter("F:\\aa\\test.txt",true);
			file.write("(这是续写内容)");
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 
	}
 
	private static void readByCharacter() {
		FileReader file = null;
		try {
			//创建FileReader并指定要读取的文件
			file = new FileReader("F:\\aa\\test.txt");
 
			int n =0;
			while((n=file.read())!=-1){
				System.out.println((char)n);
			}
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	private static void readByCharacterArray() {
		FileReader file = null;
		try {
			//创建FileReader并指定要读取的文件
			file = new FileReader("F:\\aa\\test.txt");
			char[] buf = new char[11];
 
			int n =0;
			while((n=file.read(buf))!=-1){
				System.out.println(new String(buf,0,n));
			}
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	private static void writeFile() {
		//创建一个FileWriter对象，该对象初始化的时候就要指定被操作的文件   该文件不存在就会新建一个文件
		FileWriter file = null;
		try {
			file = new FileWriter("F:\\aa\\test.txt");
			file.write("HelloWorld!");
			//刷新缓存数据将数据写入文件
			file.flush();
 
			file.write("你好世界！");
 
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

```















































