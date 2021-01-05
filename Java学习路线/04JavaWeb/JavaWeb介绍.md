### 前后端数据如何传输

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

















