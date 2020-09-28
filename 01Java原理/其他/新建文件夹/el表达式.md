# EL表达式



## EL概述

EL没出现之前，开发Java Web应用程序时，经常需要将大量的Java代码片段嵌入到JSP页面中，这会使页面开起来很乱，如下例子：

```
<%**if(session.getAttribute(“username”)!= null){** 
out.println(session.getAttribute(“username”).toString()); 
}%> 
```

而是用EL则只需要下面代码即可实现：${username}



EL表达式语言，用于简化JSP的输出；EL表达式的基本语法：${expression}；expression用于指定要输出的内容，可以是字符串，也可以是由EL运算符组成的表达式。



**EL有以下几个特点：**

-  EL可以与JSTL结合使用，也可以与JavaScript语句结合使用；
-  EL中会自动进行类型转换。如果想通过EL输入两个字符串型数值的和，可以直接通过”+”号进行连接，如${num1+num2}；
-  EL不仅可以访问一般变量，还可以访问JavaBean中的属性以及嵌套属性和集合对象；
-  在EL中可以获得命名空间(PageContext对象，它是页面中所有其他内置对象的最大范围的集成对象，通过它可以访问其他内置对象)；
-  在使用EL进行除法运算时，如果除数为0，则返回无穷大Infinity，而不是错误；
-  在EL中可以访问JSP的作用域(request、session、application以及page)；
-  扩展函数可以与Java类的静态方法进行映射



## 与低版本的环境兼容——禁用EL

目前只要安装的Web服务器能够支持Servlet 2.4/JSP 2.0,就可以在JSP页面中直接使用EL。由于在JSP2.0以前版本中没有EL，所以JSP为了和以前的规范兼容，还提供了禁用EL的方法。有以下三种方法：

​    1)、使用斜杠”\”

​    只需要在EL的起始标记“$”前加上”\”即可；

​    2)、使用page指令

​    使用JSP的page指令也可以禁用EL表达式，语法格式如下；

​    <%@ page isELIgnored=”布尔值”%>  true为禁用EL

​    3)、在web.xml文件中配置<el-ignored>元素

```
<jsp-config> 
	<jsp-property-group> 
		<url-pattern>*.jsp</url-pattern> 
		<el-ignored>true</el-ignored> 
	</jsp-property-group> 
</jsp-config> 
```



## EL相关语法

### 关键字

EL中保留的关键字如下，在为变量命名时，应该避免使用这些关键字：

|关键字|||
| ---------- | ----- | ----- |
| and        | eq    | gt    |
| instanceof | div   | or    |
| le         | false | empty |
| not        | Lt    | ge    |



### 语法

#### 基本表达式格式

- 语法：＄{[作用域.]属性名[.子属性]}
- EL表达式支持将运算结果进行输出
- EL支持绝大多数对象输出，本质是执行toString()方法



#### EL访问数据

通过EL提供的”[]”和”.”运算符可以访问数据。通常情况下这两个运算符是等价的，可以相互代替。

**注意：使用[]时，里面是String类型要加双引号“”！！！，是数字，就直接写数字，不要加双引号**

二者不能代替的情况

- 当对象的属性名中包含一些特殊的符号(-或.)时，就只能使用[]来访问对应的属性。例如userInfo[user−id]是正确的，而{userInfo.user-name}是错误的。
- 访问集合和数组元素只能使用[]访问



#### 运算符

![EL运算符](https://images2017.cnblogs.com/blog/1274985/201801/1274985-20180104165234346-1487528550.png)

**算术运算符**：+ - * / %

| 运算符                 | 功能                                        | 示例                  | 结果 |
| ---------------------- | ------------------------------------------- | --------------------- | ---- |
| +                      | 加                                          | ${1+1}                | 2    |
| -                      | 减                                          | ${1-1}                | 0    |
| *                      | 乘                                          | ${2*2}                | 4    |
| /或div                 | 除                                          | 2/1或2/1或{2 div 1}   | 2    |
| 2/0或2/0或{2 div 0}    | Infinity                                    |                       |      |
| %或 mod                | 求余                                        | {3%2}或{3%2}或{3mod2} | 1    |
| {3%0}或{3%0}或{3 mod0} | 异常:java.lang.ArithmeticException:/by zero |                       |      |

**逻辑运算符**：&& || !

**关系运算符**：== != > < >= <=

**条件运算**：${条件表达式？ 表达式1 ： 表达式2}

**判空运算符**：empty

**EL运算符优先级**：

<img src="http://img.blog.csdn.net/20130815124030015?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhaTU2NTY1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast" alt="EL运算符优先级" style="zoom:67%;" />






## 作用域对象

### 页面上下文对象（PageContext）

**pageContext用于访问JSP内置对象和servletContext。**

**JSP九大内置对象**：out、request、response、session、application、【Page、pageContext、exception、config】

在获取到这些内置对象后，就可以获取属性值。这些属性与对象的getxxx()方法相对应，在使用时，去掉方法名中的get，并将首字母改为小写即可。

下面介绍如何应用页面上下文对象访问你JSP的内置对象和servletContext对象。

> ​    1)、访问request对象——${pageContext.request}
>
> ​    获取到request对象后，就可以通过该对象获取与客户端相关的信息。例如要访问getServerPort()方法，可以使用下面的代码：
>
> ​    ${pageContext.request.serverPort}
>
> ​    注意不可以通过pageContext对象获取保存到request范围内的变量。
>
> ​    2)、访问response对象——${pageContext.response}
>
> ​    3)、访问out对象——${pageContext.out}
>
> ​    4)、访问session对象——${pageContext.session}
>
> ​    5)、访问exception对象——${pageContext.exception}
>
> ​    6)、访问page对象——${pageContext.page}
>
> ​    7)、访问servletContext对象——${pageContext.servletContext}
>



### 作用域范围的隐含对象

在EL中提供了4个用于访问作用域范围的隐含对象，即**pageScope、requestScope、sessionScope和applicationScope**。

他们与JSP中的page、request、session及application内置对象类似。只不过这4个隐含对象只能用来取得指定范围内的属性值，而不能取得其他相关信息。



**不写作用域对象时**

忽略书写作用域对象时，el则按作用域从小到大依次尝试获取（不建议忽略）

- `pageScope`：从当前页面取值
- `requestScope`：从当前请求中获取属性值
- `sessionScope`：从当前会话中获取属性值
- `applicationScope`：从当前应用获取全局属性值



#### pageScope隐含对象？？？

pageScope对象用于返回包含page(页面)范围内的属性的集合，返回值为java.util.Map对象。下面是一个具体例子，通过pageScope对象读取page范围内的JavaBean属性值。

新建JavaBean为UserInfo：

```java
public class UserInfo {  
       private String name ="";        
       public void setName(Stringname) {        
              this.name = name;        
       }  
      
       public String getName() {      
              return name;        
       }  
}  
```

Index.jsp添加：

```jsp
<body>  

<jsp:useBean id="user" scope="page" class="com.UserInfo"></jsp:useBean>  
<jsp:setProperty property="user" name="user" value="张三" />  
${pageScope.user.name}  

</body>  
```



#### requestScope隐含对象

用于返回包含request范围内的属性值的集合，返回值为java.util.Map对象。例如：

```jsp
<%  
request.setAttribute("userName","张三");  
%>  
${requestScope.userName }
```

#### sessionScope隐含对象

用于返回包含session范围内的属性值的集合。例如：

```jsp
<%  
session.setAttribute("userName","张三");  
%> 
${sessionScope.userName }  
```

#### applicationScope隐含对象

用于返回包含application范围内的属性值的集合。例如：

```jsp
<%  
application.setAttribute("userName","张三");  
>  
{applicationScope.userName }  
```



### 环境信息的隐含对象

EL中提供了6个访问环境信息的隐含对象，分别为：param、paramValues、header、headerValues、initParam、cookie

#### param对象

用于获取请求参数的值，应用在参数值只有一个的情况，返回结果为字符串。例如：

```jsp
<form action="" method="get" name="form1"  >  
       <input name="user" type="text" value="张三">        
       <input type="submit">  
</form>  

${param.user}  
```

#### paramValues对象

如果一个请求参数名对应多个值，则需要使用paramValues对象获取请求参数的值，该对象返回的结果为数组。例如：

```jsp
<form action="" method="get" name="form1"  >  
       <input type="checkbox"name="affect" id="affect" value="1">       
       <input type="checkbox"name="affect" id="affect" value="2">        
       <input type="checkbox"name="affect" id="affect" value="3">       
       <input type="checkbox"name="affect" id="affect" value="4">        
       <input type="submit">  
</form>  

选择的是:
${paramValues.affect[0]}
${paramValues.affect[1]}
${paramValues.affect[2]}
${paramValues.affect[3]} 
```

####  header和headerValues对象

用于获取HTTP请求的一个具体的header值，当同一个header存在多个值时需使用headerValues对象。例如：

${header[“connection”]}：获取HTTP请求的header的是否需要持久连接这一属性

#### initParam对象

用于获取Web应用初始化参数的值。例如：

在web.xml文件中设置一个初始化参数user： 

```xml
  <context-param>
        <param-name>user</param-name>
        <param-value>张三</param-value>
  </context-param>
```

使用EL获取该参数：${initParam.user}

#### cookie对象

用于访问由请求设置的cookie。例如：

```jsp
<%  
Cookie cookie = new Cookie("user", "张三");  
response.addCookie(cookie);  
%>  

${cookie.user.value } 
```

 

## 定义和使用EL函数

> 待填
>



## 实例

Course.java

```java
public class Course {
    private int id;
    private String name;
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Course() {
    }

    public Course(int id, String name, String category) {
        super();
        this.id = id;
        this.name = name;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + ", category=" + category + "]";
    }

}
```

El.java

```java
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class El
 */
@WebServlet("/el")
public class El extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public El() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Course course = new Course(1, "小明", "计算机");
        request.setAttribute("course", course);
        request.getRequestDispatcher("/el.jsp").forward(request, response);
    }

}
```

el.jsp

```html
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
    <b style="color: red;">${ requestScope.course }</b>
</body>
</html>
```

浏览器输入：http://localhost:8080/EL/el

页面显示：Course [id=1, name=小明, category=计算机]

el2.jsp

```html
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
    <b style="color: red;">${ param.level }</b>
</body>
</html>
```

浏览器输入：http://localhost:8080/EL/el2.jsp?level=primary

页面显示：primary

























