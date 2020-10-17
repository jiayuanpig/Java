# Java常用API

查看一个类的作用，要先看其所属的包，看它的构造方法，再看对应返回类型的方法。

### 1.     Scanner

获取键盘输入的数字、字符串

import java.util.Scanner; 

 

public class ScannerDemo {

  public static void main(String[] args) {

​    Scanner scan = new Scanner(System.in);

​    // 从键盘接收数据

 

​    // next方式接收字符串

​    System.out.println("next方式接收：");

​    // 判断是否还有输入

​    if (scan.hasNext()) {

​      String str1 = scan.next();

​      System.out.println("输入的数据为：" + str1);

​    }

​    scan.close();

  }

}

### 2.     Random

根据区间生成随机数

import java.util.Random;

public class RandomDemo {

  public static void main(String[] args) {

​    Random r = new Random();

​    //获取0-9之间的随机数

​    int number = r.nextInt(10);

​    System.out.println(number);

 

​    //获取1-10之间的随机数

​    int num = r.nextInt(10)+1;

​    System.out.println(num);

  }

}

 

### 3.     String & StringBuilder & StringBuffer

String在创建后不可更改，实际赋值过程是销毁原对象并创建新对象；StringBuilder和StringBuffer属于可变长字符串，本质是可变长数组。

**比较字符串**相等的推荐写法：

“abc”.equals(str);//避免str空指针异常

**分割字符串**：

使用split方法，其参数为正则表达式，如果是  .  进行分割，使用split无法分割开，需要使用 \\.

**字符串类型和其他类型相互转换**：

int变string：1+””;

string变int：Integer.parseInt(String str);

 

**StringBuilder与StringBuffer区别**：

StringBuilder线程不安全，效率高；StringBuffer线程安全，效率低



### 1.     Object

#### toString方法

如果不重写，直接打印对象则是显示其地址值；重写后，会显示重写的内容。

#### equals方法

基本数据类型比较数据；引用数据类型比较地址值。

避免使用equals方法出现空指针异常：

​                               

#### hashCode方法

返回哈希值

 

### 2.     Date（与之类似calendar）

日期和毫秒互相转换

日期文本相互转化：DateFormat抽象类

import java.util.*;

import java.text.*;

 

public class DateDemo {

  public static void main(String args[]) {

   Date dNow = new Date( );

   SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");

   System.out.println("当前时间为: " + ft.format(dNow));

  }

}

 

### 3.     System类

CurrentTimeMillis：毫秒为单位的当前时间

ArrayCopy：数组数据拷贝

public class SystemDemo {

   public static void main(String[] args) {

​     long start = System.currentTimeMillis();

 

​     int[] src = {1,22,333,4444,5555,666666,7777777};

​     int[] dest = {10,20,30};

​     System.arraycopy(src, 2, dest, 0, 2);

​       //输出：333，4444，30

​     for(int i=0;i<dest.length;i++) {

​       System.out.println(dest[i]);

​     }

​     long end = System.currentTimeMillis();

​     System.out.printf("程序运行时间为[%d]毫秒！",(end-start));

   }

}

 

### 4.     包装类

基本类型所属类

自动装箱&拆箱：将基本类型转化为类来使用





