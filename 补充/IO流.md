# IO流

## 按照用途进行分类

### 1.1 按照数据的来源（去向）分类

- 是文件：FileInputStream, FileOutputStream, FileReader, FileWriter
- 是byte[]：ByteArrayInputStream, ByteArrayOutputStream
- 是char[]：CharArrayReader, CharArrayWriter
- 是String：StringBufferInputStream(已过时，因为其只能用于String的每个字符都是8位的字符串), StringReader, StringWriter
- 是网络数据流：InputStream, OutputStream, Reader, Writer

### 1.2 按照格式化输出

- 需要格式化输出：PrintStream（输出字节），PrintWriter（输出字符）

### 1.3 按缓冲功能分

- 要缓冲：BufferedInputStream, BufferedOuputStream, BuffereaReader, BufferedWriter

### 1.4 按照数据格式分

- 二进制格式（只要确定不是纯文本格式的），InputStream, OutputStream, 及其所有带Stream子类
- 纯文本格式（比如英文/汉字/或其他编码文字）：Reader, Writer, 及其相关子类

### 1.5 按照输入输出分

- 输入：Reader， InputStream，及其相关子类
- 输出：Writer，OutputStream，及其相关子类

### 1.6 特殊需求

- 从Stream转化为Reader，Writer：InputStreamReader，OutputStreamWriter
- 对象输入输出流：ObjectInputStream，ObjectOutputStream
- 进程间通信：PipeInputStream，PipeOutputStream，PipeReader，PipeWriter
- 合并输入：SequenceInputStream
- 更特殊的需要：PushbackInputStream, PushbackReader, LineNumberInputStream, LineNumberReader

## Inputstream/OutputStream与Reader/Writer的区别

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



**有两种没有对应Reader类型的InputStream输入流，用getInputStream()来读取数据。**

- Socket 用于套接字；
- URLConnection 用于 URL 连接。