# Java常用API

查看一个类的作用，要先看其所属的包，看它的构造方法，再看对应返回类型的方法。



## 1.Scanner

获取键盘输入的数字、字符串

```java
import java.util.Scanner; 

public class ScannerDemo {

  public static void main(String[] args) {
      Scanner scan = new Scanner(System.in);
      // 从键盘接收数据
      // next方式接收字符串
      System.out.println("next方式接收：");
      // 判断是否还有输入
      if (scan.hasNext()) {
          String str1 = scan.next();
          System.out.println("输入的数据为：" + str1);
      }
      scan.close();
  }
}
```



## 2.Random

根据区间生成随机数

```java
import java.util.Random;

public class RandomDemo {

  public static void main(String[] args) {
    Random r = new Random();
    //获取0-9之间的随机数
    int number = r.nextInt(10);
    System.out.println(number);
    //获取1-10之间的随机数
    int num = r.nextInt(10)+1;
    System.out.println(num);
  }

}
```

 

## 3.     String & StringBuilder & StringBuffer

String在创建后不可更改，实际赋值过程是销毁原对象并创建新对象；

StringBuilder和StringBuffer属于可变长字符串，本质是可变长数组。

### String常见方法

**构造方法**

- public String(String value)
- public String(char[] value)
- public String(char chars[], int startIndex, int numChars)
- public String(byte[] values)

**常见方法**

- public int length()
- public char charAt(int index)
- 获取子串：public String substring(int beginIndex)/public String substring(int beginIndex, int endIndex)
- 字符串比较：public int compareTo(String anotherString)/public boolean equals(Object anotherObject)/public boolean equalsIgnoreCase(String anotherString)
  - **比较字符串**相等的推荐写法：“abc”.equals(str);//避免str空指针异常
- 连接字符串：public String concat(String str)
- 单个字符查找：public int indexOf(int ch/String str)/indexOf(int ch/String str, int fromIndex)/lastIndexOf(int ch/String str)/ lastIndexOf(int ch/String str, int fromIndex)
- 大小写转换：public String toLowerCase()/toUpperCase()
- 字符串替换：public String replace(char oldChar, char newChar)
- String trim()//截去字符串两端的空格，但对于中间的空格不处理。
- **String[] split(String str)**//将str作为分隔符进行字符串分解，分解后的字字符串在字符串数组中返回。
  - **分割字符串**：使用split方法，其参数为正则表达式，如果是  .  进行分割，使用split无法分割开，需要使用 \\.



**字符串类型和其他类型相互转换**：

​	int变string：1+””;

​	string变int：Integer.parseInt(String str);

 

### StringBuilder与StringBuffer

StringBuilder线程不安全，效率高；StringBuffer线程安全，效率低

```java
public class TestStringBuilder {
    public static void main(String[] args) {
        StringBuilder sb=new StringBuilder();
        //字符串的追加
        sb.append("hello");
        sb.append(true);
        sb.append('你');
        sb.append(100);
        System.out.println(sb.toString());//hellotrue你100

        sb.delete(3, 5);//含头不含尾
        System.out.println(sb);//heltrue你100
        sb.deleteCharAt(1);//删除指定位置上的字符
        System.out.println(sb);//hltrue你100

        sb.insert(2, '好');
        System.out.println(sb);//hl好true你100
        System.out.println(sb.indexOf("t")+"\t"+sb.indexOf("k"));//3    -1
    }
}
```



## 4.Object

类 Object 是类层次结构的根类。每个类都使用 Object 作为超类。所有对象（包括数组）都实现这个类的方法。简单来说就是，Object类是所有类的父类，包括我们所写的类，我们在使用类的时候就会利用Object类中的方法

- toString方法：如果不重写，直接打印对象则是显示其地址值；重写后，会显示重写的内容。
- equals方法：基本数据类型比较数据；引用数据类型比较地址值。                
- hashCode方法：返回哈希值
- **clone()：复制一个对象**
- notify()/wait()：线程控制

 

### 克隆clone()

浅克隆和深克隆，具体用法见其他文件

### 线程方法

具体用法见其他文件



## 5.Date与calendar

### Date

最常用的就是Date date = new Date();创建系统当前时间

**注意：Date的其他构造函数和相关方法已经废弃不用，常用方法请使用Calendar类**

使用SimpleDateFormat进行自定义的时间格式输出。

```java
import java.util.*;
import java.text.*;

public class DateDemo {
  public static void main(String args[]) {
   Date dNow = new Date( );
   SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
   System.out.println("当前时间为: " + ft.format(dNow));
  }
}
```

 

### Calendar

Calendar类的功能要比Date类强大很多，可以方便的进行日期的计算,获取日期中的信息时考虑了时区等问题。而且在实现方式上也比Date类要复杂一些

**1、Calendar类对象的创建**

Calendar类是一个抽象类，由于Calendar类是抽象类，且Calendar类的构造方法是protected的，所以无法使用Calendar类的构造方法来创建对象，API中提供了getInstance方法用来创建对象。

**2、创建一个代表系统当前日期的Calendar对象**

Calendar c = Calendar.getInstance();//默认是当前日期

**3、创建一个指定日期的Calendar对象**

使用Calendar类代表特定的时间，需要首先创建一个Calendar的对象，然后再设定该对象中的年月日参数来完成。

```java
//创建一个代表2014年5月9日的Calendar对象
Calendar c1 = Calendar.getInstance();
c1.set(2014, 5 - 1, 9)；//调用：public final void set(int year,int month,int date)
```

**4、Calendar类对象信息的设置与获得**

 **1）Calendar类对象信息的设置**

 **A、Set设置**

​    如：Calendar c1 = Calendar.getInstance();

​    调用：public final void set(int year,int month,int date)

  c1.set(2014, 6- 1, 9);//把Calendar对象c1的年月日分别设这为：2014、6、9

 **B、利用字段类型设置**

 如果只设定某个字段，例如日期的值，则可以使用public void set(int field,int value)

```java
//把 c1对象代表的日期设置为10号，其它所有的数值会被重新计算
c1.set(Calendar.DATE,10);
//把c1对象代表的年份设置为2014年，其他的所有数值会被重新计算
c1.set(Calendar.YEAR,2015);
//其他字段属性set的意义以此类推
```

**Calendar类中用一下这些常量表示不同的意义，jdk内的很多类其实都是采用的这种思想**

- Calendar.YEAR——年份
- Calendar.MONTH——月份
- Calendar.DATE——日期
- Calendar.DAY_OF_MONTH——日期，和上面的字段意义相同
- Calendar.HOUR——12小时制的小时
- Calendar.HOUR_OF_DAY——24小时制的小时
- Calendar.MINUTE——分钟
- Calendar.SECOND——秒
- Calendar.DAY_OF_WEEK——星期几

 **C、Add设置(可用与计算时间)**

```java
 Calendar c1 = Calendar.getInstance();
 //把c1对象的日期加上10，也就是c1所表的日期的10天后的日期，其它所有的数值会被重新计算
 c1.add(Calendar.DATE, 10);
 //把c1对象的日期加上-10，也就是c1所表的日期的10天前的日期，其它所有的数值会被重新计算
 c1.add(Calendar.DATE, -10);
 其他字段属性的add的意义以此类推
```

**2）、Calendar类对象信息的获得(使用get（）)**

```java
Calendar c1 = Calendar.getInstance();
// 获得年份
int year = c1.get(Calendar.YEAR);
// 获得月份
int month = c1.get(Calendar.MONTH) + 1;（MONTH+1）
// 获得日期
int date = c1.get(Calendar.DATE);
// 获得小时
int hour = c1.get(Calendar.HOUR_OF_DAY);
// 获得分钟
int minute = c1.get(Calendar.MINUTE);
// 获得秒
int second = c1.get(Calendar.SECOND);
// 获得星期几（注意（这个与Date类是不同的）：1代表星期日、2代表星期1、3代表星期二，以此类推）
int day = c1.get(Calendar.DAY_OF_WEEK);
```



**GregorianCalendar类**

GregorianCalendar 是 Calendar 的一个具体子类，提供了世界上大多数国家使用的标准日历系统。

**1、GregorianCalendar类对象的创建**

GregorianCalendar有自己的构造方法，而其父类Calendar没有公开的构造方法。

GregorianCalendar() 在具有默认语言环境的默认时区内使用当前时间构造一个默认的 GregorianCalendar。

在具有默认语言环境的默认时区内构造一个带有给定日期设置的 GregorianCalendar。

- GregorianCalendar(int year, int month, int dayOfMonth) 
- GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute) 。
- GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second)

**2、创建一个代表当前日期的GregorianCalendar对象**

 GregorianCalendar gc = new GregorianCalendar();

//创建一个代表2014年6月19日的GregorianCalendar对象(注意参数设置，与其父类是一样，月份要减去1)

GregorianCalendar gc = new GregorianCalendar(2014,6-1,19);

**3、另外：GregorianCalendar有一个方法：**boolean isLeapYear(int year) 确定给定的年份是否为闰年



<img src="https://images0.cnblogs.com/i/453754/201406/011053088847429.png" alt="三种对象的转换" style="zoom:50%;" />



## 6.System类

- public static long currentTimeMillis()：返回毫秒为单位的当前时间

- public staitc void exit(int status)：结束正在运行的java程序

  参数传入一个数字即可。通常传入0记为正常状态，其它为异常状态。

- public static void gc()：用来运行JVM中的垃圾回收器，完成内存中垃圾的清除。

- public static getProperties  getProperties()：确定当前的系统属性

- public static notive void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)：数组数据拷贝


```java
public class SystemDemo {
   public static void main(String[] args) {
     long start = System.currentTimeMillis();
     int[] src = {1,22,333,4444,5555,666666,7777777};
     int[] dest = {10,20,30};
     System.arraycopy(src, 2, dest, 0, 2);
     //输出：333，4444，30
     for(int i=0;i<dest.length;i++) {
       System.out.println(dest[i]);
     }
     long end = System.currentTimeMillis();
     System.out.printf("程序运行时间为[%d]毫秒！",(end-start));
   }
}
```

 

## 7.包装类

java并不是纯面向对象的语言，java语言是一个面向对象的语言，但是java中的基本数据类型却不是面向对象的，但是我们在实际使用中经常将基本数据类型转换成对象，便于操作，比如，集合的操作中，这时，我们就需要将基本类型数据转化成对象！

自动装箱&拆箱：将基本类型转化为类来使用

![基本数据类型的继承关系](https://img2018.cnblogs.com/blog/1504650/201901/1504650-20190122173636211-1359168032.png)









