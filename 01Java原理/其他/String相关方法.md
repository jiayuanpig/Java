# String类的相关方法

字符串的相关操作

## 基本操作方法

首先说一下基本操作方法，字符串的基本操作方法中包含以下几种：

- 获取字符串长度length() ：int length = str.length();

- 获取字符串中的第i个字符charAt(i)：char ch = str.charAt(i);

- 获取指定位置的字符方法getChars(4个参数)： 

  char array[] = new char[80];

  str.getChars(indexBegin,indexEnd,array,arrayBegin);

  - indexBegin：需要复制的字符串的开始索引
  - indexEnd:  需要复制的字符串的结束索引，indexEnd-1
  - array:       前面定义的char型数组的数组名
  - arrayBegin:数组array开始存储的位置索引号

  与getChars()类似的方法有一个getBytes(),两者使用上基本相同，只是getBytes()方法创建的是byte类型的数组，而byte编码是默认字符集编码，它是用编码表示的字符。

- 判断是否为空isEmpty()
- 去掉首尾的空格trim()







## 字符串比较

我们知道，明确的数值之间可以很方便地进行比较，那么字符串该如何进行比较呢？字符串的比较是将两个字符串从左到右逐个字符逐个字符进行比较，比较的依据是当前字符的Uncode编码值，直到比较出两个不同字符的大小。

字符串比较也分为两大类：一类是字符串大小的比较，这样的比较有三种结果，大于、等于以及小于；还有一类比较方法就是比较两个字符串是否相等，这样产生的比较结果无非就两种，ture和false。

-  比较大小：

(1)不忽略字符串大小写情况下字符串的大小比较方法compareTo(another str)

格式：int result = str1.compareTo(str2);

输出三种比较结果：若该字符串的Unicode值<参数字符串的Unicode值，结果返回一负整数；若若该字符串的Unicode值=参数字符串的Unicode值，结果返回0；若该字符串的Unicode值>参数字符串的Unicode值，结果返回一正整数。

(2) 忽略字符串大小写情况下字符串的大小比较方法compareTOIgnoreCase(another str)

格式：int result = str1.compareToIgnoreCase(str2);

在忽略字符串大小写情况下，返回三种比较结果：输出三种比较结果：若该字符串的Unicode值<参数字符串的Unicode值，结果返回一负整数；若若该字符串的Unicode值=参数字符串的Unicode值，结果返回0；若该字符串的Unicode值>参数字符串的Unicode值，结果返回一正整数。

- 字符串是否相等(相等情况下必须保证二者长度相等)：

(1)不忽略字符串大小写情况下判别字符串相等的方法eaquals(another str)

格式：boolean result = str1.equals(str2);

当且仅当str1和str2的长度相等，且对应位置字符的Unicode编码完全相等，返回true,否则返回false

(2)  忽略字符串大小写情况下判别字符串相等的方法equalsIgnoreCase(another str)

格式：boolean result = str1.equalsIgnoreCase(str2);



## 字符串与其他数据类型的转换

- 其他数据转为字符串

- 字符串转为其他数据类型

| 数据类型 | 字符串转换为其他数据类型 | 其它数据类型转换为字符串的方法1 | 其他数据类型转换为字符串的方法2 |
| -------- | ------------------------ | ------------------------------- | ------------------------------- |
| boolean  | Boolean.getBoolean(str)  | String.valueOf([boolean] b)     | Boolean.toString([boolean] b)   |
| int      | Integer.parseInt(str)    | String.valueOf([int] i)         | Int.toString([int] i)           |
| long     | Long.parseLong(str)      | String.valueOf([long] l)        | Long.toString([long] l)         |
| float    | Float.parseFloat(str)    | String.valueOf([float] f)       | Float.toString([float] f)       |
| double   | double.parseFloat(str)   | String.valueOf([double] d)      | Double.toString([double] d)     |
| byte     | Byte.parseByte(str)      | String.valueOf([byte] bt)       | Byte.toString([byte] bt)        |
| char     | str.charAt(i)            | String.valueOf([char] c)        | Character.toString([char] c)    |



## 字符串查找

我们有时候需要在一段很长的字符串中查找我们需要的其中一部分字符串或者某个字符，String类恰恰提供了相应的查找方法，这些方法返回的都是目标查找对象在字符串中的索引值，所以都是整形值。具体分类情况如下：

- 查找字符出现的位置

1、indexOf()方法

格式：1、str.indexOf(ch);

​            2、str.indexOf(ch,fromIndex); //包含fromIndex位置

​        	格式1返回指定字符在字符串中第一次出现位置的索引 

​        	格式2返回指定索引位置之后第一次出现该字符的索引号

2、lastIndexOf()方法

格式：1、str.lastIndexOf(ch);

​            2、str.lastIndexOf(ch,fromIndex);

​        	格式1返回指定字符在字符串中最后一次出现位置的索引

​        	格式2返回指定索引位置之前最后一次出现该字符的索引号

- 查找字符串出现的位置

1、indexOf()方法 

格式：1、str.indexOf(str);

​            2、str.indexOf(str,fromIndex);

​        	格式1返回指定子字符串在字符串中第一次出现位置的索引

​        	格式2返回指定索引位置之前第一次出现该子字符串的索引号

2、lastIndexOf()方法

格式：1、str.lastIndexOf(str);

​        	2、str.lastIndexOf(str,fromIndex); 

​        	格式1返回指定子字符串在字符串中最后一次出现位置的索引

​        	格式2返回指定索引位置之前最后一次出现该子字符串的索引号



## 字符串截取与拆分

这类方法是截取出一个长字符串中的一个子字符串或将字符串按照正则表达式的要求全部拆分保存到一个字符串数组中。具体方法如下所示：

- 截取方法：substring()方法  

格式1：String result = str.substring(index)；

格式2：String result = str.substring(beginIndex,EndIndex)；//实际索引号[beginIndex,EndIndex-1]

结果：截取出范围内的字符串

- 拆分方法：split()方法

格式1 ：String strArray[] = str.split(正则表达式);// 拆分的结果保存到字符串数组中

格式2：String strArray[] = str.split(正则表达式，limit);



## 字符串替换和修改

- concat()方法合并字符串：String result = str1.concat(str2);  //将str1和str2合并

- toLowerCase()方法 将字符全部转化为小写: String result = str.toLowerCase();

- toUpperCase()方法 将字符全部转化为大写：String result = str.toUpperCase();     

- replace()、replaceAll()、replaceFirst()方法     



## String的格式化方法（format）

### 字符串打印

```java
  String str=null;

  str=String.format("Hi,%s", "Tom");     // 格式化字符串

  System.out.println(str);               // 输出字符串变量str的内容

  System.out.printf("字母a的大写是：%c %n", 'A');

  System.out.printf("3>7的结果是：%b %n", 3>7);

  System.out.printf("100的一半是：%d %n", 100/2);

  System.out.printf("100的16进制数是：%x %n", 100);

  System.out.printf("100的8进制数是：%o %n", 100);

  System.out.printf("50元的书打8.5折扣是：%f 元%n", 50*0.85);

  System.out.printf("上面价格的16进制数是：%a %n", 50*0.85);

  System.out.printf("上面价格的指数表示：%e %n", 50*0.85);

  System.out.printf("上面价格的指数和浮点数结果的长度较短的是：%g %n", 50*0.85);

  System.out.printf("上面的折扣是%d%% %n", 85);

  System.out.printf("字母A的散列码是：%h %n", 'A');
```

### 格式化

#### 对整数进行格式化

规范：`%[index$][标识][最小宽度]转换方式`

%

> 占位符的起始字符，若要在占位符内部使用%，则需要写成%%。

[index$]

> 位置索引从1开始计算，用于指定对索引相应的实参进行格式化并替换掉该占位符

标识

> '-'  在最小宽度内左对齐，不可以与“用0填充”同时使用
>
> '#'  只适用于8进制和16进制，8进制时在结果前面增加一个0，16进制时在结果前面增加0x
>
> '+'  结果总是包括一个符号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制）
>
> ' '  正值前加空格，负值前加负号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制）
>
> '0'  结果将用零来填充
>
> ','  只适用于10进制，每3位数字之间用“，”分隔
>
> '('  若参数是负数，则结果中不添加负号而是用圆括号把数字括起来（同‘+'具有同样的限制）

转换方式

> d-十进制  
>
> o-八进制  
>
> x或X-十六进制



```java
 System.out.println(String.format("%1$,09d", -3123));
 System.out.println(String.format("%1$9d", -31));
 System.out.println(String.format("%1$-9d", -31));
 System.out.println(String.format("%1$(9d", -31));
 System.out.println(String.format("%1$#9x", 5689));

//结果为：
//-0003,123
//   -31
//-31   
//   (31)
//  0x1639


```



#### 对浮点数进行格式化

规范：`%[index$][标识][最少宽度][.精度]转换方式`

标识

> '-'  在最小宽度内左对齐，不可以与“用0填充”同时使用
> '+'  结果总是包括一个符号
> ' '  正值前加空格，负值前加负号
> '0'  结果将用零来填充
> ','  每3位数字之间用“，”分隔（只适用于fgG的转换）
> '('  若参数是负数，则结果中不添加负号而是用圆括号把数字括起来（只适用于eEfgG的转换）

转换方式

> 'e', 'E' -- 结果被格式化为用计算机科学记数法表示的十进制数
> 'f'     -- 结果被格式化为十进制普通表示方式
> 'g', 'G'  -- 根据具体情况，自动选择用普通表示方式还是科学计数法方式
> 'a', 'A'  --  结果被格式化为带有效位数和指数的十六进制浮点数



#### 对字符进行格式化

对字符进行格式化是非常简单的，c表示字符，标识中'-'表示左对齐，其他就没什么了。



#### 对百分比符号进行格式化

看了上面的说明，大家会发现百分比符号“%”是特殊格式的一个前缀。

那么我们要输入一个百分比符号该怎么办呢？肯定是需要转义字符的,但是要注意的是，在这里转义字符不是“\”，而是“%”。

```java
//换句话说，下面这条语句可以输出一个“12%”：
System.out.println(String.format("%1$d%%", 12));
```



#### 取得平台独立的行分隔符

​    System.getProperty("line.separator")可以取得平台独立的行分隔符，但是用在format中间未免显得过于烦琐了。于是format函数自带了一个平台独立的行分隔符那就是String.format("%n")。



#### 对日期类型进行格式化

​    以下日期和时间转换的后缀字符是为 't' 和 'T' 转换定义的。这些类型相似于但不完全等同于那些由 GNU date 和 POSIX strftime(3c) 定义的类型。提供其他转换类型是为了访问特定于 Java 的功能（如将 'L' 用作秒中的毫秒）。

格式：时间

> 'H'   24 小时制的小时，被格式化为必要时带前导零的两位数，即 00 - 23。
> 'I'   12 小时制的小时，被格式化为必要时带前导零的两位数，即 01 - 12。
> 'k'   24 小时制的小时，即 0 - 23。
> 'l'   12 小时制的小时，即 1 - 12。
> 'M'   小时中的分钟，被格式化为必要时带前导零的两位数，即 00 - 59。
> 'S'   分钟中的秒，被格式化为必要时带前导零的两位数，即 00 - 60 （"60" 是支持闰秒所需的一个特殊值）。
> 'L'   秒中的毫秒，被格式化为必要时带前导零的三位数，即 000 - 999。
> 'N'   秒中的毫微秒，被格式化为必要时带前导零的九位数，即 000000000 - 999999999。
> 'p'   特定于语言环境的 上午或下午 标记以小写形式表示，例如 "am" 或 "pm"。使用转换前缀 'T' 可以强行将此输出转换为大写形式。
> 'z'   相对于 GMT 的 RFC 822 格式的数字时区偏移量，例如 -0800。
> 'Z'   表示时区缩写形式的字符串。Formatter 的语言环境将取代参数的语言环境（如果有）。
> 's'   自协调世界时 (UTC) 1970 年 1 月 1 日 00:00:00 至现在所经过的秒数，即 Long.MIN_VALUE/1000 与 Long.MAX_VALUE/1000 之间的差值。
> 'Q'   自协调世界时 (UTC) 1970 年 1 月 1 日 00:00:00 至现在所经过的毫秒数，即 Long.MIN_VALUE 与 Long.MAX_VALUE 之间的差值。

日期

> 'B'   特定于语言环境的月份全称，例如 "January" 和 "February"。
> 'b'   特定于语言环境的月份简称，例如 "Jan" 和 "Feb"。
> 'h'   与 'b' 相同。
> 'A'   特定于语言环境的星期几全称，例如 "Sunday" 和 "Monday"
> 'a'   特定于语言环境的星期几简称，例如 "Sun" 和 "Mon"
> 'C'   除以 100 的四位数表示的年份，被格式化为必要时带前导零的两位数，即 00 - 99
> 'Y'   年份，被格式化为必要时带前导零的四位数（至少），例如，0092 等于格里高利历的 92 CE。
> 'y'   年份的最后两位数，被格式化为必要时带前导零的两位数，即 00 - 99。
> 'j'   一年中的天数，被格式化为必要时带前导零的三位数，例如，对于格里高利历是 001 - 366。
> 'm'   月份，被格式化为必要时带前导零的两位数，即 01 - 13。
> 'd'   一个月中的天数，被格式化为必要时带前导零两位数，即 01 - 31
> 'e'   一个月中的天数，被格式化为两位数，即 1 - 31。

常见组合

> 'R'   24 小时制的时间，被格式化为 "%tH:%tM"
> 'T'   24 小时制的时间，被格式化为 "%tH:%tM:%tS"。
> 'r'   12 小时制的时间，被格式化为 "%tI:%tM:%tS %Tp"。上午或下午标记 ('%Tp') 的位置可能与语言环境有关。
> 'D'   日期，被格式化为 "%tm/%td/%ty"。
> 'F'   ISO 8601 格式的完整日期，被格式化为 "%tY-%tm-%td"。
> 'c'   日期和时间，被格式化为 "%ta %tb %td %tT %tZ %tY"，例如 "Sun Jul 20 16:17:00 EDT 1969"。



```java
    Date date=new Date();                                           // 创建日期对象

    System.out.printf("全部日期和时间信息：%tc%n",date);               // 格式化输出日期或时间

    System.out.printf("年-月-日格式：%tF%n",date);

    System.out.printf("月/日/年格式：%tD%n",date);

    System.out.printf("HH:MM:SS PM格式（12时制）：%tr%n",date);

    System.out.printf("HH:MM:SS格式（24时制）：%tT%n",date);

    System.out.printf("HH:MM格式（24时制）：%tR",date);
```



