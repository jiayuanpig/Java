

# Java框架



## Java Web

常见技术：jdbc、servlet、jsp



### 一. WEB基础

#### **JDBC流程**

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









### 二. 前后端数据如何传输

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



##### **HTTP三点注意事项**

**HTTP是无连接**：无连接的含义是限制每次连接只处理一个请求。服务器处理完客户的请求，并收到客户的应答后，即断开连接。采用这种方式可以节省传输时间。

**HTTP是媒体独立的**：这意味着，只要客户端和服务器知道如何处理的数据内容，任何类型的数据都可以通过HTTP发送。客户端以及服务器指定使用适合的MIME-type内容类型。

**HTTP是无状态：**HTTP协议是无状态协议。无状态是指协议对于事务处理没有记忆能力。缺少状态意味着如果后续处理需要前面的信息，则它必须重传，这样可能导致每次连接传送的数据量增大。另一方面，在服务器不需要先前信息时它的应答就较快。



##### HTTP结构

**HTTP请求：客户端发送一个HTTP请求到服务器的请求消息包括以下格式：请求行（request line）、请求头部（header）、空行和请求数据四个部分组成**

**HTTP响应：由四个部分组成，分别是：状态行、消息报头、空行和响应正文**



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



##### 基本响应码

- 200("OK")
  一切正常。实体主体中的文档（若存在的话）是某资源的表示。
- 400("Bad Request")
  客户端方面的问题。实体主题中的文档（若存在的话）是一个错误消息。希望客户端能够理解此错误消息，并改正问题。
- 500("Internal Server Error")
  服务期方面的问题。实体主体中的文档（如果存在的话）是一个错误消息。该错误消息通常无济于事，因为客户端无法修复服务器方面的问题。
- 301("Moved Permanently")
  当客户端触发的动作引起了资源URI的变化时发送此响应代码。另外，当客户端向一个资源的旧URI发送请求时，也发送此响应代码。
- 404("Not Found") 和410("Gone")
  当客户端所请求的URI不对应于任何资源时，发送此响应代码。404用于服务器端不知道客户端要请求哪个资源的情况；410用于服务器端知道客户端所请求的资源曾经存在，但现在已经不存在了的情况。
- 409("Conflict")
  当客户端试图执行一个”会导致一个或多个资源处于不一致状态“的操作时，发送此响应代码。





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

Servlet、Filter、Listener



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









## 开发框架

### 单例和多例的原理和实现方式

单例和多例：所谓单例就是所有的请求都用一个对象来处理，比如我们常用的service和dao层的对象通常都是单例的；而多例则指每个请求用一个新的对象来处理，比如action; 

单例实现：在类中创建一个静态对象，随着构造器直接创建。使用时调用方法获取对象，而不通过new的方式



### Spring

Spring的使用包括注解和配置（XML）

配置文件：applicationContext.xml

使用方式：创建实体类并依赖注入。获取Application对象，并通过getBean方法创建对象

Bean实例化方法：无参构造、静态工厂、实例工厂



#### Spring组件

![spring组件](https://img-blog.csdn.net/20180828154641214?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM3ODQwOTkz/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

> spring core：框架的最基础部分，提供 ioc 和依赖注入特性。
> spring context：构建于 core 封装包基础上的 context 封装包，提供了一种框架式的对象访问方法。
> spring dao：Data Access Object 提供了JDBC的抽象层。
> spring aop：提供了面向切面的编程实现，让你可以自定义拦截器、切点等。
> spring Web：提供了针对 Web 开发的集成特性，例如文件上传，利用 servlet listeners 进行 ioc 容器初始化和针对 Web 的 ApplicationContext。
> spring Web mvc：spring 中的 mvc 封装包提供了 Web 应用的 Model-View-Controller（MVC）的实现。



> **Spring核心组件只有Core、Context、Beans三个**。core包侧重于帮助类，操作工具，beans包更侧重于bean实例的描述。context更侧重全局控制，功能衍生。
>
> Bean组件主要解决：**Bean 的定义、Bean 的创建以及对 Bean 的解析**。顶级接口是 BeanFactory。
>
> Context组件主要解决：为Spirng**提供一个运行时环境，同时为spring的控制反转添加依赖注入**。顶级父类是ApplicationContext 。
>
> ​	Context 在 Spring 的 org.springframework.context 包下，给 Spring 提供一个**运行时的环境，用以保存各个对象的状态。**Context 作为 Spring 的 Ioc 容器，基本上整合了 Spring 的大部分功能，或者说是大部分功能的基础。**控制反转（Inverse of Control) 将实例的创建过程交由容器实现，调用者将控制权交出，是所谓控制反转。依赖注入（Dependence Injection) 在控制反转的基础上更进一步。如果没有依赖注入，容器创建实例并保存后，调用者需要使用 getBean(String beanName) 才能获取到实例。使用依赖注入时，容器会将 Bean 实例自动注入到完成相应配置的调用者，供其进一步使用。Context 组件借助上述的控制反转和依赖注入，协助实现了 Spring 的 Ioc 容器。
>
> Core组件一个重要的组成部分就是**定义了资源的访问方式**。
>
> ​	Core组价把所有的资源都抽象成一个接口，这样，对于资源使用者来说，不需要考虑文件的类型。对资源提供者来说，也不需要考虑如何将资源包装起来交给别人使用（Core组件内所有的资源都可以通过InputStream类来获取）。另外，Core组件内资源的加载都是由ResourceLoader接口完成的，只要实现这个接口就可以加载所有的资源。



#### Spring的ioc和aop

**依赖注入**

依赖注入是在编译阶段尚未知所需的功能是来自哪个的类的情况下，将其他对象所依赖的功能对象实例化的模式。

注入方式：构造器注入、set方法注入、接口注入



**ioc**（Inversion of Control）

控制反转：将传统的对象创建流程转变为交由框架进行创建和管理。在Spring框架中我们通过配置创建类对象，由Spring在运行阶段实例化、组装对象。

特点：可以从Ioc容器中直接获得一个对象然后直接使用，无需事先创建它们，对象的创建和销毁都无需考虑。但是，生成一个对象的步骤变复杂了（其实上操作上还是挺简单的），对于不习惯这种方式的人，会觉得有些别扭和不直观。对象 生成因为是使用反射编程，在效率上有些损耗。但相对于IoC提高的维护性和灵活性来说，这点损耗是微不足道的，除非某对象的生成对效率要求特别高。



**aop**（Aspect Oriented Programming）

面向切面编程：对已有方法的增强，其思想是在执行某些代码前后执行另外的代码，使程序更灵活、扩展性更好，可以随便地添加、删除某些功能。

通知类型：前置通知、后置通知、异常通知、最终通知、环绕通知

我们也可以在aop的基础上添加事务控制



#### Spring Bean

##### **bean的生命周期**

1. 实例化 Instantiation
2. 属性赋值 Populate
3. 初始化 Initialization
4. 销毁 Destruction

>  在说明前可以思考一下Servlet的生命周期：实例化，初始init，接收请求service，销毁destroy；
>
>   Spring上下文中的Bean也类似，如下
>
>   1、实例化一个Bean－－也就是我们常说的new；
>
>   2、按照Spring上下文对实例化的Bean进行配置－－也就是IOC注入；
>
>   3、如果这个Bean已经实现了BeanNameAware接口，会调用它实现的setBeanName(String)方法，此处传递的就是Spring配置文件中Bean的id值
>
>   4、如果这个Bean已经实现了BeanFactoryAware接口，会调用它实现的setBeanFactory(setBeanFactory(BeanFactory)传递的是Spring工厂自身（可以用这个方式来获取其它Bean，只需在Spring配置文件中配置一个普通的Bean就可以）；
>
>   5、如果这个Bean已经实现了ApplicationContextAware接口，会调用setApplicationContext(ApplicationContext)方法，传入Spring上下文（同样这个方式也可以实现步骤4的内容，但比4更好，因为ApplicationContext是BeanFactory的子接口，有更多的实现方法）；
>
>   6、如果这个Bean关联了BeanPostProcessor接口，将会调用postProcessBeforeInitialization(Object obj, String s)方法，BeanPostProcessor经常被用作是Bean内容的更改，并且由于这个是在Bean初始化结束时调用那个的方法，也可以被应用于内存或缓存技术；
>
>   7、如果Bean在Spring配置文件中配置了init-method属性会自动调用其配置的初始化方法。
>
>   8、如果这个Bean关联了BeanPostProcessor接口，将会调用postProcessAfterInitialization(Object obj, String s)方法、；
>
>   注：以上工作完成以后就可以应用这个Bean了，那这个Bean是一个Singleton的，所以一般情况下我们调用同一个id的Bean会是在内容地址相同的实例，当然在Spring配置文件中也可以配置非Singleton，这里我们不做赘述。
>
>   9、当Bean不再需要时，会经过清理阶段，如果Bean实现了DisposableBean这个接口，会调用那个其实现的destroy()方法；
>
>   10、最后，如果这个Bean的Spring配置中配置了destroy-method属性，会自动调用其配置的销毁方法。



##### **bean的作用范围**

> singleton ：这种 bean 范围是**默认**的，这种范围确保不管接受到多少个请求，每个容器中只有一个 bean 的实例，单例的模式由 bean factory 自身来维护 
>
> prototype ：原形范围与单例范围相反，为每一个 bean 请求提供一个实例 
>
> request ：在请求 bean 范围内会每一个来自客户端的网络请求创建一个实例，在请求完成以后， bean 会失效并被垃圾回收器回收 
>
> Session ：与请求范围类似，确保每个 session 中有一个 bean 的实例，在 session 过期后， bean 会随之失效 
>
> global-session ： global-session 和 Portlet 应用相关 。 当你的应用部署在 Portlet 容器中工作时，它包含很多 portlet。 如果你想要声明让所有的 portlet 共用全局的存储变量的话，那么这全局变量需要存储在 global-session 中 



#### Spring事务控制

在开发中需要操作数据库，进行增、删、改操作的过程中属于一次操作，如果在一个业务中需要更新多张表，那么任意一张表的更新失败，整个业务的更新就是失败，这时那些更新成功的表必须回滚，否则业务会出错，这时就要用到事务，即这个业务的操作属于一个事务。

spring提供了对事务的支持，在spring中主要有两种方式使用事务，一、编程式事务控制；二、声明式事务控制。

首先先开启事务支持

```xml
<!--配置事务管理器-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"></property>
     
</bean>
<!--事务管理器模板 方便使用事务-->
<bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
    <property name="transactionManager" ref="transactionManager"></property>
</bean>
```

编程式事务控制（不推荐）：使用事务管理器模板transactionTemplate的execute方法，需要一个TransactionCallBack的实例，这里使用匿名内部类的方式，把要执行的方法放在doInTransactionWithoutResult中执行，保证了事务的控制。

```
@Component
public class AccountServiceImpl implements AccountServiceIter {
    @Autowired
    private AccountDaoInter adi;
    @Autowired
    private TransactionTemplate tt;
    //转账方法，由out向in转money元
    @Override
    public void transfer(final String out, final String in, final double money) {
        // TODO Auto-generated method stub
    //使用事务管理器模板进行事务控制
    tt.execute(new TransactionCallbackWithoutResult() {

        @Override
        protected void doInTransactionWithoutResult(TransactionStatus status) {
            // TODO Auto-generated method stub
            adi.outMoney(out, money);
            //一个异常，使用了事务控制，在出现了异常之后，事务会回滚
            int i = 1 / 0;
            adi.inMoney(in, money);
        }
    });
}

```

声明式事务控制（推荐）

> 声明式事务控制又分为三种方式
>
> 一、基于TransactionProxyFactoryBean代理的声明式事务控制；
>
> ```
> <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
>     <property name="dataSource" ref="dataSource"></property>
> </bean>
> <!--配置业务层代理-->
> <bean id="accountServiceProxy" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
>     <property name="target" ref="accountServiceImpl"></property>
>     <property name="transactionManager" ref="transactionManager"></property>
>     <property name="transactionAttributes">
>         <props>
>             <prop key="transfer"></prop>
>         </props>
>     </property>
> </bean>
> ```
>
> 二、使用AOP的声明式事务控制；
>
> ```
> <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
>     <property name="dataSource" ref="dataSource"></property>
> </bean>
> <!--配置事务增强-->
> <tx:advice id="advicer" transaction-manager="transactionManager">
>     <tx:attributes>
>         <tx:method name="transfer*" propagation="REQUIRED"/>
>     </tx:attributes>
> </tx:advice>
> <!--配置切点、事务通知-->
> <aop:config>
>     <aop:pointcut id="myPointcut" expression="execution(* com.cn.study.day555.service.inter.impl.*.*(..))"/>
>     <aop:advisor advice-ref="advicer" pointcut-ref="myPointcut"/>
> </aop:config>
> ```
>
> 三、基于@Transactional注解的声明式事务控制。
>
> 使用@Transactional注解需要再配置文件中开启对这个注解的扫描：<tx:annotation-driven transaction-manager=“transactionManager” />，引用了事务管理器，然后就可以使用@Transactional注解，此注解可以使用在类上，也可以使用在方法上，使用在类上即对此类的所有方法都起作用，使用在方法上则表示对单个方法起作用，还可以配置一些属性。











#### Spring 框架中用到了哪些设计模式

Spring 框架中使用到了大量的设计模式，下面列举了比较有代表性的：

- 代理模式 — 在 AOP 和 remoting 中被用的比较多 。
- 单例模式 — 在 spring 配置文件中定义的 bean 默认为单例模式 。
- 模板方法 — 用来解决代码重复的问题 。 比如 RestTemplate,  JmsTemplate,  JpaTemplate。
- 前端控制器 —Spring 提供了 DispatcherServlet 来对请求进行分发 。
- 视图帮助 (View Helper  )—Spring 提供了一系列的 JSP 标签，高效宏来辅助将分散的代码整合在视图里 。
- 依赖注入 — 贯穿于 BeanFactory  /  ApplicationContext 接口的核心理念 。
- 工厂模式 —BeanFactory 用来创建对象的实例 。



#### Spring常见注解

**1、声明bean的注解**

@Component 组件，没有明确的角色

@Service 在业务逻辑层使用（service层）

@Repository 在数据访问层使用（dao层）

@Controller 在展现层使用，控制器的声明（C）

**2、注入bean的注解**

@Autowired：由Spring提供

@Inject：由JSR-330提供

@Resource：由JSR-250提供

都可以注解在set方法和属性上，推荐注解在属性上（一目了然，少写代码）。

**3、java配置类相关注解**

@Configuration 声明当前类为配置类，相当于xml形式的Spring配置（类上）

@Bean 注解在方法上，声明当前方法的返回值为一个bean，替代xml中的方式（方法上）

@Configuration 声明当前类为配置类，其中内部组合了@Component注解，表明这个类是一个bean（类上）

@ComponentScan 用于对Component进行扫描，相当于xml中的（类上）

@WishlyConfiguration 为@Configuration与@ComponentScan的组合注解，可以替代这两个注解

**4、切面（AOP）相关注解**

Spring支持AspectJ的注解式切面编程。

@Aspect 声明一个切面（类上）
使用@After、@Before、@Around定义建言（advice），可直接将拦截规则（切点）作为参数。

@After 在方法执行之后执行（方法上）
@Before 在方法执行之前执行（方法上）
@Around 在方法执行之前与之后执行（方法上）

@PointCut 声明切点
在java配置类中使用@EnableAspectJAutoProxy注解开启Spring对AspectJ代理的支持（类上）

**5、@Bean的属性支持**

@Scope 设置Spring容器如何新建Bean实例（方法上，得有@Bean）
其设置类型包括：

Singleton （单例,一个Spring容器中只有一个bean实例，默认模式）,
Protetype （每次调用新建一个bean）,
Request （web项目中，给每个http request新建一个bean）,
Session （web项目中，给每个http session新建一个bean）,
GlobalSession（给每一个 global http session新建一个Bean实例）

@StepScope 在Spring Batch中还有涉及

@PostConstruct 由JSR-250提供，在构造函数执行完之后执行，等价于xml配置文件中bean的initMethod

@PreDestory 由JSR-250提供，在Bean销毁之前执行，等价于xml配置文件中bean的destroyMethod

**6、@Value注解**

@Value 为属性注入值（属性上）



### Mybatis

Mybatis是一个半ORM（对象关系映射）框架，它内部封装了JDBC，开发时只需要关注SQL语句本身，不需要花费精力去处理加载驱动、创建连接、创建statement等繁杂的过程。程序员直接编写原生态sql，可以严格控制sql执行性能，灵活度高。

Mybatis的使用包括注解和配置（XML）



配置文件：sqlMapConfig.xml

使用方式：在配置文件中配置数据库连接池，配置数据库的映射文件（使用注解或xml文件）。通过读取配置文件，使用sqlSessionFactory创建sqlsession对象，从而获取对应的dao接口对象。dao接口和xml中的id同名，从而完成对应方法的执行。



#### jdbc缺点

> 1、数据库链接创建、释放频繁造成系统资源浪费从而影响系统性能，如果使用数据库链接池可解决此问题。
> 2、Sql语句在代码中硬编码，造成代码不易维护，实际应用sql变化的可能较大，sql变动需要改变java代码。
> 3、使用preparedStatement向占有位符号传参数存在硬编码，因为sql语句的where条件不一定，可能多也可能少，修改sql还要修改代码，系统不易维护。 
> 4、对结果集解析存在硬编码（查询列名），sql变化导致解析代码变化，系统不易维护，如果能将数据库记录封装成pojo对象解析比较方便。



#### #和$区别

\#{}是预编译处理，`${}`是字符串替换。

Mybatis在处理`#{}`时，会将sql中的#{}替换为?号，调用PreparedStatement的set方法来赋值；Mybatis在处理`${}`时，就是把`${}`替换成变量的值。使用#{}可以有效的防止SQL注入，提高系统安全性。

`${}`**不会修改和转译字符串**，一般用在order by, limit, group by等场所。`#{}`传入的数据都当成一个字符串，会**对自动传入的数据加一个双引号**，在进行order by操作会报错





#### 对象注入和封装

注入数据：parameterType或parameterMap

返回封装对象：resultType或resultMap

自动获取生成值：

```xml
<insert id="createPet" parameterType="java.util.Map"
    useGeneratedKeys="true" keyProperty="id">
    INSERT INTO Pet (NAME, OWNER, SPECIES, SEX, BIRTH)
    VALUES (#{name}, #{owner}, #{species}, #{sex}, #{birth})
</insert>
```



一对一中有resultMap使用association封装；一对多由resultMap使用collection封装



#### xml映射文件常见标签

```
包裹if标签可省略sql语句中的where 1=1语句
<select id="findAllUser" resultMap="user">
    select * from user
    <where>
        <if test="username!=null">
            and  username = #{username}
        </if>
    </where>
</select>
传入数组进行条件查询得到批量结果
<select id="findUserByForeach" parameterType="queryvo" resultType="user">
	select * from user
	<if test="ids != null and ids.size > 0 ">
		<foreach collection="ids" item="id" open="where id in (" separator=", " close=")">
			#{id}
		</foreach>
	</if>
</select>
```



顶级元素：

- `resultMap` – 是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。
- `parameterMap` – 已被废弃！老式风格的参数映射。更好的办法是使用内联参数，此元素可能在将来被移除。
- `sql` – 可被其他语句引用的可重用语句块。
- `insert` – 映射插入语句
- `update` – 映射更新语句
- `delete` – 映射删除语句
- `select` – 映射查询语句

常见属性：

-   id
-   parameterType
-   parameterMap
-   resultType
-   resultMap
-   resultSetType



#### 数据库配置

创建自己的数据库连接池类继承UnpooledDataSourceFactory的类： Mybatis没有帮开发者实现c3p0 数据库连接池，故需要使用者自己实现c3p0来加载数据连接池。其实很简单的，只要继承UnpooledDataSourceFactory并把dataSourc 实现。我们的mybatis就实现了c3p0 数据库连接池。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
  <properties resource="/com/cn/cs/config.properties"/>
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC"/>
      <dataSource type="POOLED">
        <property name="driver" value="${oracle.driver}"/>
        <property name="url" value="${oracle.url}"/>
        <property name="username" value="${oracle.username}"/>
        <property name="password" value="${oracle.password}"/>
        <property name="poolMaximumActiveConnections" value="10"/> <!--最大活跃连接数 -->
        <property name="poolMaximumIdleConnections" value="5"/>  <!--最大空闲连接数-->
        <property name="poolTimeToWait" value="20000"/>　　<!--创建连接时最大等待时间20s-->
      </dataSource>
    </environment>
  </environments>
  <mappers>
      <mapper resource="/com/cn/cs/mapping/UserCsMapper.xml"/>  
  </mappers>
</configuration>
```





#### 分页

分页方法有四种：利用原生的sql关键字limit来实现；利用interceptor来拼接sql，实现和limit一样的功能；利用PageHelper来实现；使用Mybatis自带的RowBounds。

直接使用sql属于物理分页，RowBounds是逻辑分页。





#### 缓存与延迟加载

延迟加载需要在配置文件中设置：在MyBatis 的配置文件中通过设置settings的lazyLoadingEnabled属性为true进行开启全局的延迟加载，通过aggressiveLazyLoading属性开启立即加载。

**延迟加载**：按需加载，在使用的时候加载（用于一对多和多对多）

**立即加载**：只要调用方法就加载（多对一和一对一）



缓存都是为了减轻数据库压力，二级缓存应用范围大于一级缓存。SqlSessionFactory层面上的二级缓存默认是不开启的，二级缓存的开启需要进行配置，实现二级缓存的时候，MyBatis要求返回的POJO必须是可序列化的。，也就是要求实现Serializable接口。

**一级缓存**：SqlSession中的缓存（随SqlSession存在，可以调用方法清除）

**二级缓存**：SqlSessionFactory中的缓存（使用时需要在框架、映射文件和当前操作语句中同时开启）



### SpringMVC

Spring MVC是一个基于Java的实现了MVC设计模式的请求驱动类型的轻量级Web框架，通过把Model，View，Controller分离，将web层进行职责解耦，把复杂的web应用分成逻辑清晰的几部分，简化开发。



#### 工作流程

![SpringMVC工作流程](https://img-blog.csdn.net/20180708224853769?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E3NDUyMzM3MDA=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

1. spring mvc 先将请求发送给 DispatcherServlet。
2. DispatcherServlet 查询一个或多个 HandlerMapping，找到处理请求的 Controller。
3. DispatcherServlet 再把请求提交到对应的 Controller。
4. Controller 进行业务逻辑处理后，会返回一个ModelAndView。
5. Dispathcher 查询一个或多个 ViewResolver 视图解析器，找到 ModelAndView 对象指定的视图对象。
6. 视图对象负责渲染返回给客户端。





#### **Spring MVC的主要组件？**

（1）前端控制器 DispatcherServlet（不需要程序员开发）

作用：接收请求、响应结果，相当于转发器，有了DispatcherServlet 就减少了其它组件之间的耦合度。

（2）处理器映射器HandlerMapping（不需要程序员开发）

作用：根据请求的URL来查找Handler

（3）处理器适配器HandlerAdapter

注意：在编写Handler的时候要按照HandlerAdapter要求的规则去编写，这样适配器HandlerAdapter才可以正确的去执行Handler。

（4）处理器Handler（需要程序员开发）

（5）视图解析器 ViewResolver（不需要程序员开发）

作用：进行视图的解析，根据视图逻辑名解析成真正的视图（view）

（6）视图View（需要程序员开发jsp）

View是一个接口， 它的实现类支持不同的视图类型（jsp，freemarker，pdf等等）



#### 相关对象

Controller有三种写法

- 方法返回String：return "system/userList";
- 方法返回ModelAndView ：view.setViewName("system/userList");return view;

- 方法添加@ResponseBody，返回ajax：返回一个Map类型的数据



#### 重定向和转发

重定向：浏览器地址栏会改变，，是两次请求，所以对应的request域会发生变化

转发：服务器内部转发，一次请求，对应的request域不会发生变化

```java
 	/** 实现转发
	 * @throws Exception 
	 */
	@RequestMapping("/hello11.action")
	public String hello11(HttpServletRequest request) throws IOException, Exception{
		request.setAttribute("name", "zsf");
		return "forward:/hello.action";
	}
	
	/**
	 * 实现重定向
	 * @throws Exception 
	 */
	@RequestMapping("/hello12.action")
	public String hello12(HttpServletRequest request) throws IOException, Exception{
		request.setAttribute("name", "zsf");
		return "redirect:/hello.action";
	}
```



#### 如何实现ajax调用

**配置**

（1）导入jar包

（2）xxxx-servlet.xml配置文件

主要添加“从请求和响应读写/编写字符串”和“将对象转换为json”两项配置，其他都是基本的配置。

**传入后台**、**返回前台**





#### 乱码问题如何解决

在web.xml中配置一个CharacterEncodingFilter过滤器，设置成utf-8；







### SSM

> SpringMVC实现前后台交互处理（Controller）
>
> Spring完成业务逻辑（Service、Bean、Utils）
>
> Mybatis完成数据库操作（Dao）



### Spring Boot

Spring Boot 是 Spring 开源组织下的子项目，是 Spring 组件一站式解决方案，主要是简化了使用 Spring 的难度，简省了繁重的配置，提供了各种启动器，开发者能快速上手。



配置文件、日志、web开发、thymeleaf、Docker、jpa



**Spring Boot 的核心注解是哪个？它主要由哪几个注解组成的？**

启动类上面的注解是@SpringBootApplication，它也是 Spring Boot 的核心注解，主要组合包含了以下 3 个注解：

@SpringBootConfiguration：组合了 @Configuration 注解，实现配置文件的功能。

@EnableAutoConfiguration：打开自动配置的功能，也可以关闭某个自动配置的选项，如关闭数据源自动配置功能： @SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })。

@ComponentScan：Spring组件扫描。



**你如何理解 Spring Boot 配置加载顺序？**

在 Spring Boot 里面，可以使用以下几种方式来加载配置。

1）properties文件；

2）YAML文件；

3）系统环境变量；

4）命令行参数；















