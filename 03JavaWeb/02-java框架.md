

## Java Web

常见技术：jdbc、servlet、jsp



### 一、WEB基础

#### JDBC

执行流程

1. 加载数据库驱动器
2. 打开数据库连接
3. 执行sql语句
4. 返回处理结果
5. 关闭资源

相关类：PrepareStatement预编译sql语句；CollableStatement调用存储过程



#### Servlet

Servlet（Server Applet）是Java Servlet的简称，称为小服务程序或服务连接器，用Java编写的服务器端程序，主要功能在于交互式地浏览和修改数据，生成动态Web内容。

- 两个方法，doGet和doPost方法

- 两个参数，HttpServletRequest对象和HttpServletResponse对象
> **HttpServletRequest对象方法：**
>
> 获取客户机信息：完整URL、参数URL、IP地址、主机名（包括发送方和接收方）
>
> 获取请求参数：根据参数名获取参数值
>
> 设置编码方式：post方式就直接使用setCharacterEncoding("UTF-8")，get方式就在后台进行编码转换
>
> **请求转发**：
>
> ```java
> RequestDispatcher reqDispatcher =this.getServletContext().getRequestDispatcher("/test.jsp");
> reqDispatcher.forward(request, response);
> 或
> request.getRequestDispatcher("/test.jsp").forward(request, response);
> ```
>
> 在request中保存数据信息：request.setAttribute(key, value);

>
  > HttpServletResponse对象方法：
  >
  > 给客户机发送响应头、状态码
  >
  > **请求重定向**：response.sendRedirect("/ServletDemo/login.html");
  >
  > 发送数据：
  >
  > ```java
  > response.setCharacterEncoding("utf-8");
  >     PrintWriter out = response.getWriter();
  >     JSONObject json = new JSONObject();
  >     for (Entry<String, Object> map : ControJsonMap.entrySet()) {
  >     	json.put(map.getKey(), map.getValue());
  >     }
  >     char[] buf = json.toString().toCharArray();
  >     if(buf.length>0){
  >         out.write(buf);//发送
  >         out.flush();
  >         out.close();
  >         System.out.println("*******************数据发送到前台*******************");
  >         ControJsonMap.clear();
  > }
  > ```

  

**Servlet生命周期**

涉及方法

1．init()。当Servlet第一次被装载时，Servlet引擎调用这个Servlet的init()方法，只调用一次。如果某个Sevlet需要特殊的初始化需要。那么Servlet编写人员可以重写该方法来执行初始化任务。这是一个可选的方法。如果某个 Servlet不需要初始化，那么默认情况下将调用它父类的init方法。系统保证，在init方法成功完成以前，是不会调用Servlet去处理任何请求的。

2．service()。这是Servlet最重要的方法，是真正处理请求的地方。对于每个请求，Servlet引擎将调用Servlet的service方法，并把Servlet请求对象和Servlet响应对象最为参数传递给它。

3．destroy()。这是相对于init的可选方法，当Servlet即将被卸载时由Servlet引擎来调用，这个方法用来清除并释放在init方法中所分配的资源。



生命周期：

　　（1） 装载Servlet，这一项操作一般是动态执行的。然而，Servlet通常会提供一个管理的选项，用于在Servlet启动时强制装载和初始化特定的Servlet
　　（2） Server创建一个Servlet实例
　　（3） Server调用Servlet的init方法
　　（4） 一个客户端请求到达Server
　　（5） Server创建一个请求对象
　　（6） Server创建一个响应对象
　　（7） Server激活Servlet的service方法，传递请求和响应对象作为参数
　　（8） service方法获得关于请求对象的信息，处理请求，访问其他资源，获得需要的信息
　　（9） service方法使用响应对象的方法。将响应传回Server，最终到达客户端。Service方法可能激活其他方法以处理请求。如doGet，doPost或其他程序员自己开发的方法
　　（10） 对于更多的客户端请求，Server创建新的请求和响应对象，仍然激活此servlet的service方法，将这两个对象作为参数传递给它，如此重复以上的循环，但无需再次调用init方法，Servlet一般只初始化一次
　　（11） 当Server不再需要Servlet时，比如当Server要关闭时，Server调用Servlet的destroy



#### JSP







### 二、前后端数据如何传输

**传输流程：**`前端-->服务器-->后台-->服务器-->前端`

#### 1. WEB服务器原理

##### **构成**

Tomcat内部结构是Server包含多个Service，一个Service包含多个Connector和一个Container。

**Server**：通过接口让其他程序访问其内部的服务。

在单个**Service**中，**Connector**将在某个指定的端口上来监听客户请求，把从socket传递过来的数据封装成Request，传递给Engine来进行处理，并从Engine处获得响应并返回给客户。

> Tomcat通常会用到两种Connector：
>
> Http Connector，在端口8080处监听来自客户browser的http请求；
>
> AJP Connector，在端口8009处监听来自其他webServer的Servlet/jsp代理请求。

**Container**：包含了以下四个核心部分 Wrapper、Host、Engine、Context

> **Engine：**是**负责处理所有相关联的service请求，并将结果返回给service**。而Connector则是作为service和engine之间的桥梁。一个engine下可以配置一个默认主机，每个虚拟主机都有一个域名。当engine接收到一个请求时，它会将该请求匹配到虚拟主机（host）上，然后将请求交给host来处理。若无法匹配到虚拟主机，则将其交给默认host来处理，以线程方式来启动host。
>
> **Host：**代表一个虚拟主机，每个虚拟主机和某网络域名相匹配。每个虚拟主机下可以有一个或多个web应用，每个web应用对应于一个context，相对应有contextpath。当主机接收到请求时，会将该请求匹配到某个context上，然后把请求交给该context来处理。
>
> **Context：**一个Context对应于一个web应用。一个web应用由一个或多个servlet组成。context在创建时将根据配置文件`$ CATALINA_HOME/conf/web.xml`和`$ WEBAPP_HOME/WEB-INF/web.xml`载入Servlet类。当Context获得请求时，将在自己的映射表(mapping table)中寻找相匹配的Servlet类，如果找到，则执行该类，获得请求的回应，并返回。
>
> **Wrapper**代表了一个**Servlet，**负责**管理Servlet的装载、初始化、执行以及资源回收**。wrapper的实现类是StandardWrapper，该类还实现了拥有一个Servlet初始化信息的ServletConfig。
>
> **Lifecycle**：在编程中也有很多对象是具有生命周期的，从初始化、运行、回收等 会经历几个不同的阶段。 在tomcat中容器相关的好多组建都实现了Lifecycle接口，当tomcat启动时，其依赖的下层组件会全部进行初始化。 并且可以**对每个组件生命周期中的事件添加监听器**。例如当服务器启动的时候，tomcat需要去调用servlet的init方法和初始化容器等一系列操作，而停止的时候，也需要调用servlet的destory方法。而这些都是通过**org.apache.catalina.Lifecycle**接口来实现的。由这个类来制定各个组件生命周期的规范。



##### **运行流程**

服务器启动，server中多个service开始加载，service中包含connector和container，container包含engine、host、context、wrapper

假设来自客户的请求为：`http://localhost:8080/test/index.jsp`

1) 请求被发送到本机端口8080，被在那里侦听的Coyote HTTP/1.1 Connector获得
2) Connector把该请求交给它所在的Service的Engine来处理，并等待来自Engine的回应
3) Engine获得请求localhost/test/index.jsp，匹配它所拥有的所有虚拟主机Host
4) Engine匹配到名为localhost的Host（即使匹配不到也把请求交给该Host处理，因为该Host被定义为该Engine的默认主机）
5) localhost Host获得请求/test/index.jsp，匹配它所拥有的所有Context
6) Host匹配到路径为/test的Context（如果匹配不到就把该请求交给路径名为""的Context去处理）
7) path="/test"的Context获得请求/index.jsp，在它的mapping table中寻找对应的servlet
8) Context匹配到URL PATTERN为*.jsp的servlet，对应于JspServlet类
**9) 构造HttpServletRequest对象和HttpServletResponse对象，作为参数调用JspServlet的doGet或doPost方法**
10)Context把执行完了之后的HttpServletResponse对象返回给Host
11)Host把HttpServletResponse对象返回给Engine
12)Engine把HttpServletResponse对象返回给Connector
13)Connector把HttpServletResponse对象返回给客户browser



#### 2. Servlet运行

Servlet是一种独立于平台和协议的服务器端的java应用程序，运行于java服务器中，可以动态扩展服务器能力，并采用请求-响应模式来提供web服务。

**Servlet是一个单实例多线程的。只能被实例化一次，而每次service服务会开启新线程进行处理新请求。**



##### servlet生命周期

> **init方法会在Servlet对象创建之后马上执行，且只执行一次**。
>
> **destroy方法会在Servlet被销毁之前调用**，**也只执行一次**。
>
> 对于**service**方法，则**可以被多次调用**，每次处理请求时都是在调用该方法。
>
> getServletConfig可以获得Servlet的配置信息。
>
> getServletInfo方法可以获得Servlet信息。



##### servlet工作过程

> 1）browser发出一个http请求；
>
> 2）Tomcat的Connector组件监听到该请求，其主线程会创建HttpServletRequest对象和HTTPServletResponse对象；
>
> 3）从请求URL中找到正确Servlet后，Tomcat为其创建或分配一个线程，同时将2中对象传递给该线程；
>
> 4）Tomcat调用Servlet的service()方法，会根据请求参数的不同来调用doGet()或doPost()等方法，并将结果返回到HTTPServletResponse对象中；
>
> 5）Tomcat将响应结果返回到browser。



#### 3. HTTP与服务器交互

HTTP协议，即超文本传输协议，基于TCP/IP通信协议来传输数据，工作于客户端-服务端架构上，通过URL向Web服务器（Apache服务器等）传输请求并得到响应。默认端口为80，也可以设置为8080等。

##### **HTTP请求方式**

一共八种请求方式：get、post、head、put、delete、connect、options、trace

<img src="https://images2018.cnblogs.com/blog/1445861/201807/1445861-20180723010545712-61214685.png" alt="img" style="zoom: 80%;" />

其中较为常用的为get、post、delete和put，这大致对应着对该资源的查、改、删、增四个操作。

> **1）Get请求用于向服务器进行信息获取，是安全和幂等的。**它仅仅是为了获取信息，不会影响资源的状态；所谓幂等，即对于同一个URL的多个请求返回的结果都一致。
>
> **get请求会将数据附在URL之后，以？来进行分割，参数之间以&来进行连接。**对于非英文字母/数字等，都需要进行格式的转换。而由于其在URL进行拼接，对于涉及到密码等请求，是不安全的。
>
> **在HTTP协议中对URL长度并没有作出限制，而URL的最大长度其实和用户浏览器以及web服务器有关。如IE为2048，Google为8182，Apache(Server)为8192。**
>
> 2）**Post请求表示向服务器提交数据的一种请求，可能修改服务器上的资源**，类似数据库的insert一样。**post对于数据的提交是放置在http包的包体当中的**。**理论上post请求是没有大小限制的，起限制作用的是服务器处理程序的处理能力**。如IIS 6.0默认post数据最大为200KB，每个表单限制为100KB。**post 的安全性比get高。**
>
> 3）Put请求也是向服务端发送数据从而改变信息，类似于数据库的update一般。
>
> 4）Delete请求就是删除某一资源的，类似于数据库的delete操作。



##### HTTP状态

> 1xx：指示信息--表示请求已接收，继续处理
>
> 2xx：成功--表示请求已被成功接收、理解、接受
>
> 3xx：重定向--要完成请求必须进行更进一步的操作
>
> 4xx：客户端错误--请求有语法错误或请求无法实现
>
> 5xx：服务器端错误--服务器未能实现合法的请求





#### 4. SpringMVC前后端交互

**相关组件**

> **前端控制器DispatcherServlet**：接收请求响应结果，相当于转发器、中央处理器，减少了其他组件之间的耦合度；
>
> **处理器映射器HandlerMapping**：根据请求URL查找handler；
>
> **处理器适配器HandlerAdapter**：按特定规则去执行handler，故编写handler时按HandlerAdapter要求去做，这样适配器才可正确执行handler；
>
> **视图解析器ViewResolver**：根据逻辑视图解析成真正的视图（View对象）
>
> **视图View**：View是一个接口，实现类支持不同的View类型（jsp，PDF，Excel...）



**交互过程**

> 1）用户发送一个URL请求到**前端控制器DispatcherServlet**；
>
> 2）前端控制器将请求发给**处理器映射器HandlerMapping**，它会根据xml配置、注解等进行查找handler；
>
> 3）处理器映射器返回执行链HandlerExecutionChain(里面有handler对象，在它之前有多个interceptor拦截器)；
>
> 4）前端控制器通过**处理器适配器HandlerAdapter**去执行Handler，不同的Handler由不同的适配器执行；
>
> 5）通过**Handler处理器，即我们熟悉的Controller**来处理业务逻辑；
>
> 6）处理完之后，返回ModelAndView对象，其中有视图名称，模型数据等；
>
> 7）HandlerAdapter将ModelAndView返回给DispatcherServlet；
>
> 8）DispatcherServlet将得到的ModelAndView传递给**视图解析器ViewResolver**进行解析；
>
> 9）ViewResolver解析后返回具体的**视图View**；
>
> 10）前端控制器对视图View和数据进行渲染，将模型数据等填充到request域中；
>
> 11）将最终的视图返回给客户，产生response响应。



#### 5. 前端数据提交方式

##### **使用form表单**

```html
<!DOCTYPE html>
<html>
<body>
	<form action="/demo/demo_form.asp">
		First name:<br>
		<input type="text" name="firstname" value="Mickey">
		<br>
		Last name:<br>
		<input type="text" name="lastname" value="Mouse">
		<br><br>
		<input type="submit" value="提交">
</form> 
</body>
</html>
```

如果点击提交，表单数据会被发送到名为 demo_form.asp 的页面。

1. 在后台可通过对应的name属性获取相应的值。
2. from表单中的action属性标识提交数据的地址。
3. method属性指明表单提交的方式



##### **使用ajax**

```js
$.ajax({
    url: "TestJsonServlet", //提价的路径
    type: "post",       //提交方式
    data: {
        //向后台提交的数据
    },
    dataType: "JSON",       //规定请求成功后返回的数据
    success: function (data) {
        //请求成功之后进入该方法，data为成功后返回的数据
    },
    error: function (errorMsg) {
        //请求失败之后进入该方法，errorMsg为失败后返回的错误信息
    }
});
```



### 三、Java Web三大组件

**Servlet、Filter、Listener**

#### Servlet

在Java web b/s架构中，servlet扮演了重要的角色，作为一个中转处理的容器，他连接了**客户端和服务器端的信息交互和处理**。简单来说，客户端发送请求，传递到servlet容器，而servlet将数据转换成服务器端可以处理的数据再发送给服务器端，再数据处理之后，再传递到servlet容器，servlet再转译到客户端，完成了一次客户端和服务器端的信息交互。

Servlet是通过Java编写的，因为他也具备了Java的一些特点，比如跨平台性，可扩展性高，然而他的优点不仅仅是局限于语言方面，因为Servlet的出现，可以使我们将JSP页面中的一些JAVA代码移植到Servlet中来，可无疑使前端人员深受喜欢，方便了项目的修改完善，而Servlet的使用也是非常的简单。

Servlet的生命周期有四个阶段，第一个阶段，实例化，会调用构造方法，第二个阶段是初始化，会调用init（）方法，第三个阶段是请求处理，调用service方法，第四个阶段，服务终止也就是销毁阶段，调用destroy方法。



#### Filter

filter用于拦截用户请求，在服务器作出响应前，可以在拦截后修改request和response，这样实现很多开发者想得到的功能。

filter是一个可以复用的代码片段，可以用来转换HTTP请求、响应和头信息。Filter不像Servlet，它不能产生一个请求或者响应，它只是修改对某一资源的请求，或者修改从某一的响应。Filter可以理解一个一种特殊Servlet，主要用于对用户请求进行预处理。



使用Filter的方法：

- 实现filter接口，重写生命周期的三个方法
- 在web.xml中进行过滤器配置



生命周期

> 构造器：创建Filter实例时调用，Filter实例服务器一旦启动就会被创建
>
> init()：实例创建后马上被调用，用来对Filter做一些初始化的操作，接收一个FilterConfig类型的参数，该参数是对Filter的一些配置
>
> doFilter()：Filter的主要方法，用来完成过滤器主要功能的方法，每次访问目标资源时都会调用。
>
> ​	参数ServletRequest req, ServletResponse resp, FilterChain chain
>
> destroy()：服务器停止时调用，用来释放资源。



功能

> - 处理全站中文乱码问题
> - 实现自动登录
> - 过滤敏感词汇
> - 压缩网页
> - 选择性让浏览器缓存
>
> 这几种功能的实现采用同样的原理，那就是使用包装模式或动态代理增强request或response对象的功能。



#### Listener

监听器用于监听Web应用中某些对象的创建、销毁、增加，修改，删除等动作的发生，然后作出相应的响应处理。

![img](https://img2018.cnblogs.com/blog/693744/201904/693744-20190428175943386-2067995645.png)



用途

> 统计在线人数，利用HttpSessionLisener
>
> 加载初始化信息：利用ServletContextListener
>
> 统计网站访问量
>
> 实现访问监控



用法：

实现ServletContextListener接口，重写方法后，当发生对应事件后会自动调用该方法。















