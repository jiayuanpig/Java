# Java

我的Java学习之路



## **Java学习路线**

JavaSE：环境搭建、基础语法、面向对象、数组、集合、常用API、IO流、反射机制、多线程、网络编程

数据库：Mysql

前端：HTML、CSS、Javascript（Jquery、Bootstrap）

后端：XML、MVC架构模式、Servlet、Filter、JSP、EL、JSTL、Ajax、代理模式、工厂模式、数据库连接池

项目管理：Maven（进阶Gradle）、Git

框架：Spring（解耦MVC）、SpringMVC（Servlet进阶）、Mybatis（JDBC进阶）、（Struts2、Hibernate、SSM客户关系管理系统）

市场需求：SpringBoot

**进阶：**

Dubbo、ZooKeeper、SpringCloud、MQ、Nginx、Redis、Liunx、vue、多线程、设计模式、JVM优化、算法



## Java知识点



### JavaSE

#### 修饰符

权限修饰符：public > protected > (default) > private

类型修饰符：final、static、abstract



#### 面向对象

封装、继承、多态

重载、重写

上下转型

抽象类、接口、函数式接口：抽象类和接口的子类（实现类）必须实现所有抽象方法

内部类：成员内部类 + 局部内部类（包括匿名内部类）



#### 枚举类的用法？？？



#### 集合

单列集合Collection 和双列集合Map

Collection接口：list接口（实现类：ArrayList、LinkedList、Vector）和set接口（实现类：TreeSet、HashSet、LinkedHashSet）

Map接口：（实现类：HashMap、LinkedHashMap、HashTable）



#### 泛型？？？



#### java内存分配？？？

堆、栈、方法区、本地方法栈、寄存器



#### java内存回收机制？？？





#### 异常？

处理异常的两种方式：捕获（try...catch）和抛出（throws）



**自定义异常？？？**



#### 多线程？

创建多线程的方法：继承线程类Thread和实现线程接口Runable

以上两种方法都需要重写**run**方法，使用时调用**start**方法



**线程同步？？？**

同步代码块、同步方法、线程锁



#### IO流

字符流：InputStream、OutputStream

字节流：Reader、Writer

缓冲流：BufferInputStream、BufferReader...



#### 反射

JAVA反射机制是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意方法和属性；这种动态获取信息以及动态调用对象方法的功能称为java语言的反射机制。



框架设计的灵魂，降低程序间耦合，减少编译期异常

```java
/**
 * 获取Class对象的三种方式
 * 1 Object ——> getClass();
 * 2 任何数据类型（包括基本数据类型）都有一个“静态”的class属性
 * 3 通过Class类的静态方法：forName（String  className）(常用)
 *
 * 说明：三种方式常用第三种，第一种对象都有了还要反射干什么。第二种需要导入类的包，依赖太强，不导包就抛编译错误。一般都第三种，一个字符串可以传入也可写在配置文件中等多种方法。
 */
public class Fanshe {
    public static void main(String[] args) {
        //第一种方式获取Class对象  
        Student stu1 = new Student();//这一new 产生一个Student对象，一个Class对象。
        Class stuClass = stu1.getClass();//获取Class对象
        System.out.println(stuClass.getName());
        
        //第二种方式获取Class对象
        Class stuClass2 = Student.class;
        System.out.println(stuClass == stuClass2);//判断第一种方式获取的Class对象和第二种方式获取的是否是同一个
        
        //第三种方式获取Class对象
        try {
            Class stuClass3 = Class.forName("fanshe.Student");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
            System.out.println(stuClass3 == stuClass2);//判断三种方式是否获取的是同一个Class对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }
}
```



#### 注解

用于编译器进行编译检查，作为框架的重要组成部分



### Mysql

sql：关系型数据库的结构化查询语言



#### SQL语言分类

DDL：数据定义语言（数据库、表的定义）

```sql
# 操作：create/show/alter/drop

create database 数据库名称 character set 字符集名;

create table 表名(
	列名 数据类型
);
```

DML：数据操作语言（表数据的增删改）

```sql
insert into 表名(列名) values(值);
delete from 表名 where 条件
update 表名 set 列名=值 where 条件
```

DQL：数据查询语言（查询表）

```sql
select * from 表名
where(条件：分组前限定，后面不可以跟聚合函数)
group by(分组字段)
having(分组条件：分组后限定)
order by(排序)
limit(分页)
```

DCL：数据控制语言（访问权限、安全级别）

```sql
# 操作：create、grant、revoke

create user '用户'@'主机名' identified by '密码'
grant 权限列表 on 数据库名.表名 to '用户'@'主机名'
```



#### 多表查询

内连接：通过主键连接两个表

外连接：左连接、右连接、自然连接



#### 范式

第一范式：列为原子项，不可分割

第二范式：非码属性完全依赖主属性

第三范式：无传递依赖



#### 事务（transaction）

开启（start）、提交（commit）、回滚（rollback）

事务四大特征：原子性（全部执行）、持久性（事务提交后数据库改变）、隔离性（事务之间相互隔离）、一致性（一致性状态，所有事务对数据库状态保持一致）

存在问题：脏读、不可重复读、幻读

事务隔离级别：read uncommit/read commit/repeatable read/serializable



### Java Web



#### JDBC

java连接数据库的底层实现



步骤：

1. 导入jar包：mysql-connector-java-8.0.20.jar
2. 注册驱动
3. 获取数据库连接对象Connection
4. 定义sql
5. 获取执行sql的对象Statement
6. 执行sql，返回结果
7. 释放资源

代码

```java
public class Test {

	public static void main(String[] args) throws Exception {
	
		//注册驱动
		Class.forName("com.mysql.jdbc.Driver");
        //获取连接对象
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu-test?serverTimezone=GMT", "root","123456");
        //获取执行sql的对象
//		Statement stmt = conn.createStatement();
//		String sql= "select * from class";
        //返回结果集
//		ResultSet resultSet = stmt.executeQuery(sql);
//		while(resultSet.next()) {
//			System.out.println(resultSet.getInt("cno"));
//			System.out.println(resultSet.getString("cname"));
//			System.out.println(resultSet.getString("cteacher"));
//		}
		
//		String sql = "insert into class values (5,'神经网络','大五')";
//		
//		try {
//			System.out.println(stmt.executeUpdate(sql));
//			System.out.println(stmt.execute(sql));
//		} catch (Exception e) {
//			System.out.println("插入失败");
//		}
		
		
		String sql = "insert into class values (?,?,?)";
		PreparedStatement prepareStatement = conn.prepareStatement(sql);
		prepareStatement.setInt(1, 8);
		prepareStatement.setString(2, "经济学");
		prepareStatement.setString(3, "高六");
		
		System.out.println(prepareStatement.execute());
	}

}
```

注意Statement和Preparedstatement区别



Connection的方法

- Statement createStatement()
- PreparedStatement prepareStatement(String sql)
- setAutoCommit(boolean autoCommit)
- commit()
- rollback()



Statement的方法

- boolean execute(String sql)：执行任何sql方法
- int executeUpdate(String sql)：执行DDL、DML
- ResultSet executeQuery(String sql)：执行DQL



#### Servlet

web服务器与浏览器之间的连接

##### 运行过程

前端通过url，经过web.xml的映射找到对应的servlet实现类，从而执行service方法

##### 生命周期

实例化-->初始化-->服务-->销毁	对应方法 构造器-->init()-->service()-->destroy()

##### 创建方式

- 实现Servlet接口：重写所有方法
- 继承GenericServlet类：根据需要重写方法
- 继承HttpServlet类：重写doGet()和doPost()方法，不能重写service方法



ServletContext对象作为整个应用的全局对象，依赖获取配置信息以及全局资源用于共享数据

- getParameter()是获取POST/GET传递的参数值；
- getInitParameter获取Tomcat的server.xml中设置Context的初始化参数
- getAttribute()是获取对象容器中的数据值；
- getRequestDispatcher是请求转发。



#### JSP

java前端页面，包含html等前端信息，还嵌入实现java代码



需要将项目发布到Tomcat服务器中运行



##### JSP语法

```jsp
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" 
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="keywords" content="k1,k2,k3"/>
<!--
<link rel="stylesheet" type="text/css" href="styles.css">
-->
<title>JSP首页</title>
</head>
<body>
    
    <%-- 
    	注释 
    --%>
    <%!
    	定义变量、方法
    	int num=0;
    %>
    <%=num %>
    <%
	//输出
	out.println("输出内容");
	%>


</body>
</html>

```



##### JSP九大内置对象

1.request对象
客户端的请求信息被封装在request对象中，通过它才能了解到客户的需求，然后做出响应。它是HttpServletRequest类的实例。
2.response对象
response对象包含了响应客户请求的有关信息，但在JSP中很少直接用到它。它是HttpServletResponse类的实例。
3.session对象
session对象指的是客户端与服务器的一次会话，从客户连到服务器的一个WebApplication开始，直到客户端与服务器断开连接为止。它是HttpSession类的实例.
4.out对象
out对象是JspWriter类的实例,是向客户端输出内容常用的对象
5.page对象
page对象就是指向当前JSP页面本身，有点象类中的this指针，它是java.lang.Object类的实例
6.application对象
application对象实现了用户间数据的共享，可存放全局变量。它开始于服务器的启动，直到服务器的关闭，在此期间，此对象将一直存在；这样在用户的前后连接或不同用户之间的连接中，可以对此对象的同一属性进行操作；在任何地方对此对象属性的操作，都将影响到其他用户对此的访问。服务器的启动和关闭决定了application对象的生命。它是ServletContext类的实例。
7.exception对象
exception对象是一个例外对象，当一个页面在运行过程中发生了例外，就产生这个对象。如果一个JSP页面要应用此对象，就必须把isErrorPage设为true，否则无法编译。他实际上是java.lang.Throwable的对象
8.pageContext对象
pageContext对象提供了对JSP页面内所有的对象及名字空间的访问，也就是说他可以访问到本页所在的SESSION，也可以取本页面所在的application的某一属性值，他相当于页面中所有功能的集大成者，它的本 类名也叫pageContext。
9.config对象
config对象是在一个Servlet初始化时，JSP引擎向它传递信息用的，此信息包括Servlet初始化时所要用到的参数（通过属性名和属性值构成）以及服务器的有关信息（通过传递一个ServletContext对象）



##### jsp四种作用域

page：代表与一个页面相关的对象和属性。
request：代表与客户端发出的一个请求相关的对象和属性。一个请求可能跨越多个页面，涉及多个 Web 组件；需要在页面显示的临时数据可以置于此作用域。
session：代表与某个用户与服务器建立的一次会话相关的对象和属性。跟某个用户相关的数据应该放在用户自己的 session 中。
application：代表与整个 Web 应用程序相关的对象和属性，它实质上是跨越整个 Web 应用程序，包括多个页面、请求和会话的一个全局作用域。



##### JSP动作？



##### javaBean？



##### cookie与session







### 项目管理

#### Maven

自动化构建工具，用于管理java的项目构建和项目依赖



配置文件pom.xml

依赖范围：compile、test、provided



### Java框架

#### Mybatis







#### Spring





#### SpringMVC











