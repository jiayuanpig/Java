

# Java框架



## 单例和多例的原理和实现方式

单例和多例：所谓单例就是所有的请求都用一个对象来处理，比如我们常用的service和dao层的对象通常都是单例的；而多例则指每个请求用一个新的对象来处理，比如action; 

单例实现：在类中创建一个静态对象，随着构造器直接创建。使用时调用方法获取对象，而不通过new的方式



## Spring

Spring的使用包括注解和配置（XML）

配置文件：applicationContext.xml

使用方式：创建实体类并依赖注入。获取Application对象，并通过getBean方法创建对象

Bean实例化方法：无参构造、静态工厂、实例工厂



### Spring组件

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



### Spring的ioc和aop

**依赖注入**

依赖注入是在编译阶段尚未知所需的功能是来自哪个的类的情况下，将其他对象所依赖的功能对象实例化的模式。

注入方式：构造器注入、set方法注入、接口注入



#### **ioc**控制反转

（Inversion of Control）

控制反转：将传统的对象创建流程转变为交由框架进行创建和管理。在Spring框架中我们通过配置创建类对象，由Spring在运行阶段实例化、组装对象。

特点：可以从Ioc容器中直接获得一个对象然后直接使用，无需事先创建它们，对象的创建和销毁都无需考虑。但是，生成一个对象的步骤变复杂了（其实上操作上还是挺简单的），对于不习惯这种方式的人，会觉得有些别扭和不直观。对象 生成因为是使用反射编程，在效率上有些损耗。但相对于IoC提高的维护性和灵活性来说，这点损耗是微不足道的，除非某对象的生成对效率要求特别高。



#### **aop**面向切面编程

（Aspect Oriented Programming）

面向切面编程：对已有方法的增强，其思想是在执行某些代码前后执行另外的代码，使程序更灵活、扩展性更好，可以随便地添加、删除某些功能。

通知类型：前置通知、后置通知、异常通知、最终通知、环绕通知

我们也可以在aop的基础上添加事务控制



### Spring Bean

#### **bean的生命周期**

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



#### **bean的作用范围**

```xml
<bean scope=""></bean>
```

> singleton（单例模式） ：这种 bean 范围是**默认**的，这种范围确保不管接受到多少个请求，每个容器中只有一个 bean 的实例，单例的模式由 bean factory 自身来维护 
>
> prototype（多例模式） ：原型范围与单例范围相反，为每一个 bean 请求提供一个实例 
>
> request ：为每一个来自客户端的http请求创建一个实例，在请求完成以后， bean 会失效并被垃圾回收器回收。仅适用于WebApplicationContext环境 
>
> Session ：同一个HTTp session中共享一个bean，在 session 过期后， bean 会随之失效 。仅适用于WebApplicationContext环境 
>
> global-session ： global-session 和 Portlet 应用相关 。 当你的应用部署在 Portlet 容器中工作时，它包含很多 portlet。 如果你想要声明让所有的 portlet 共用全局的存储变量的话，那么这全局变量需要存储在 global-session 中 



### Spring事务控制

在开发中需要操作数据库，进行增、删、改操作的过程中属于一次操作，如果在一个业务中需要更新多张表，那么任意一张表的更新失败，整个业务的更新就是失败，这时那些更新成功的表必须回滚，否则业务会出错，这时就要用到事务，即这个业务的操作属于一个事务。



#### 开启事务

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

```java
@Component
public class AccountServiceImpl implements AccountServiceIter {
    @Autowired
    private AccountDaoInter adi;
    @Autowired
    private TransactionTemplate tt;
    
    //转账方法，由out向in转money元
    @Override
    public void transfer(final String out, final String in, final double money) {
    //使用事务管理器模板进行事务控制
    tt.execute(new TransactionCallbackWithoutResult() {
        @Override
        protected void doInTransactionWithoutResult(TransactionStatus status) {
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
> ```xml
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
> ```xml
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



#### 事务的传播行为

```java
@Transactional(propagation=Propagation.REQUIRED)
```

当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。Spring定义了七种传播行为，默认是required。

![事务传播行为](https://img-blog.csdn.net/20170420212829825?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvc29vbmZseQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

#### 事务的隔离级别

```java
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
```

- DEFAULT（默认）：默认使用数据库的隔离级别。
- READ UNCOMMITTED（读未提交数据）：允许事务读取未被其他事务提交的变更数据，会出现脏读、不可重复读和幻读问题。
- READ COMMITTED（读已提交数据）：只允许事务读取已经被其他事务提交的变更数据，可避免脏读，仍会出现不可重复读和幻读问题。
- REPEATABLE READ（可重复读）：确保事务可以多次从一个字段中读取相同的值，在此事务持续期间，禁止其他事务对此字段的更新，可以避免脏读和不可重复读，仍会出现幻读问题。
- SERIALIZABLE（序列化）：确保事务可以从一个表中读取相同的行，在这个事务持续期间，禁止其他事务对该表执行插入、更新和删除操作，可避免所有并发问题，但性能非常低。







### Spring 框架中用到了哪些设计模式

Spring 框架中使用到了大量的设计模式，下面列举了比较有代表性的：

- 代理模式 — 在 AOP 和 remoting 中被用的比较多 。
- 单例模式 — 在 spring 配置文件中定义的 bean 默认为单例模式 。
- 模板方法 — 用来解决代码重复的问题 。 比如 RestTemplate,  JmsTemplate,  JpaTemplate。
- 前端控制器 —Spring 提供了 DispatcherServlet 来对请求进行分发 。
- 视图帮助 (View Helper  )—Spring 提供了一系列的 JSP 标签，高效宏来辅助将分散的代码整合在视图里 。
- 依赖注入 — 贯穿于 BeanFactory  /  ApplicationContext 接口的核心理念 。
- 工厂模式 —BeanFactory 用来创建对象的实例 。



### Spring常见注解

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



## Mybatis

Mybatis是一个半ORM（对象关系映射）框架，它内部封装了JDBC，开发时只需要关注SQL语句本身，不需要花费精力去处理加载驱动、创建连接、创建statement等繁杂的过程。程序员直接编写原生态sql，可以严格控制sql执行性能，灵活度高。

Mybatis的使用包括注解和配置（XML）



配置文件：sqlMapConfig.xml

使用方式：在配置文件中配置数据库连接池，配置数据库的映射文件（使用注解或xml文件）。通过读取配置文件，使用sqlSessionFactory创建sqlsession对象，从而获取对应的dao接口对象。dao接口和xml中的id同名，从而完成对应方法的执行。



### jdbc缺点

> 1、数据库链接创建、释放频繁造成系统资源浪费从而影响系统性能，如果使用数据库链接池可解决此问题。
> 2、Sql语句在代码中硬编码，造成代码不易维护，实际应用sql变化的可能较大，sql变动需要改变java代码。
> 3、使用preparedStatement向占有位符号传参数存在硬编码，因为sql语句的where条件不一定，可能多也可能少，修改sql还要修改代码，系统不易维护。 
> 4、对结果集解析存在硬编码（查询列名），sql变化导致解析代码变化，系统不易维护，如果能将数据库记录封装成pojo对象解析比较方便。



### #和$区别

\#{}是预编译处理，`${}`是字符串替换。

Mybatis在处理`#{}`时，会将sql中的#{}替换为?号，调用PreparedStatement的set方法来赋值；Mybatis在处理`${}`时，就是把`${}`替换成变量的值。使用#{}可以有效的防止SQL注入，提高系统安全性。

`${}`**不会修改和转译字符串**，一般用在order by, limit, group by等场所。`#{}`传入的数据都当成一个字符串，会**对自动传入的数据加一个双引号**，在进行order by操作会报错





### 对象注入和封装

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



**当实体类属性名和数据库字段名不一样如何处理？**

方法一：sql语句起别名

方法二：在Mybatis全局配置文件中开启驼峰命名规则（将数据库的下划线映射为驼峰命名）

```java
<settings>
	<setting name="mapUnderscoreToCameCase" value="true"/>
</settings>
```

方法三：在Mapper映射文件中使用resultMap来自定义映射规则

```xml
<resultMap type="com.zjy.entities.Employee" id="myMap">
	<id column="id" property="id"/>
    <result column="last_name" property="lastName"/>
    <result column="salary" property="money"/>
</resultMap>

<select id="findAll" resultMap="myMap">
	select * from employees
</select>
```





### xml映射文件常见标签

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



### 数据库配置

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



### 分页

分页方法有四种：利用原生的sql关键字limit来实现；利用interceptor来拼接sql，实现和limit一样的功能；利用PageHelper来实现；使用Mybatis自带的RowBounds。

直接使用sql属于物理分页，RowBounds是逻辑分页。



### 缓存与延迟加载

延迟加载需要在配置文件中设置：在MyBatis 的配置文件中通过设置settings的lazyLoadingEnabled属性为true进行开启全局的延迟加载，通过aggressiveLazyLoading属性开启立即加载。

**延迟加载**：按需加载，在使用的时候加载（用于一对多和多对多）

**立即加载**：只要调用方法就加载（多对一和一对一）



缓存都是为了减轻数据库压力，二级缓存应用范围大于一级缓存。SqlSessionFactory层面上的二级缓存默认是不开启的，二级缓存的开启需要进行配置，实现二级缓存的时候，MyBatis要求返回的POJO必须是可序列化的。，也就是要求实现Serializable接口。

**一级缓存**：SqlSession中的缓存（随SqlSession存在，可以调用方法清除）

**二级缓存**：SqlSessionFactory中的缓存（使用时需要在框架、映射文件和当前操作语句中同时开启）



## SpringMVC

Spring MVC是一个基于Java的实现了MVC设计模式的请求驱动类型的轻量级Web框架，通过把Model，View，Controller分离，将web层进行职责解耦，把复杂的web应用分成逻辑清晰的几部分，简化开发。



### 工作流程

![SpringMVC工作流程](https://img-blog.csdn.net/20180708224853769?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E3NDUyMzM3MDA=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

1. spring mvc 先将请求发送给 DispatcherServlet。
2. DispatcherServlet 会从HandlerMapping处获取HandlerExecuteChain，里面包括所有的handler以及拦截器信息
3. DispatcherServlet 将对应的handler交给HandlerAdapter，再执行对应的Handler（Controller中的方法）返回模型数据和视图名称。
4. Controller 获取到对应的视图和模型数据后会交给视图解析器进行处理。
6. 视图对象负责渲染返回给客户端。





### Spring MVC的主要组件

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



### Controller返回值

Controller有四种写法

- 方法返回String

```java
//跳转到内部资源
@RequestMapping("/welcome.do")
public String welcome() throws Exception{
	//直接填写要跳转的jsp的名称 跳转到welcome.jsp上
	return "welcome";
}

//跳转到外部资源
1：配置springmvc.xml配置文件
    <!-- 视图解析器 -->
    <bean class="org.springframework.web.servlet.view.BeanNameViewResolver"/>

    <!--定义外部资源view对象-->
    <bean id="monkey1024" class="org.springframework.web.servlet.view.RedirectView">
        <property name="url" value="http://www.doaoao.com"/>
    </bean>
    
    // id为Controller方法的返回值
    // view为要跳转的外部资源的地址

2：创建一个Controller
    @RequestMapping("/welcome.do")
    public String welcome() throws Exception{
        //直接填写要跳转的jsp的名称
        return "monkey1024";
    }

//ModelAndView和String一起使用
@RequestMapping("/welcome1.do")
public String welcome1(String name,Model model) throws Exception{
    //这种写法spring mvc会自动为传入的参数取名
    model.addAttribute(name);
    // 自定义名称
    model.addAttribute("username", name);
    //直接填写要跳转的jsp的名称
    return "welcome";
}
```

- 方法返回ModelAndView 

```java
@RequestMapping("")
public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception
{
    ModelAndView mv = new ModelAndView();
    mv.addObject("hello", "hello first spring mvc");
    mv.setViewName("/WEB-INF/jsp/first.jsp");
    return mv;
}
```

- 方法返回void

```java
//请求转发
@RequestMapping("/returnVoid.do")
public void welcome(HttpServletRequest request, HttpServletResponse response, Student student) throws Exception {
	request.setAttribute("student", student);
	request.getRequestDispatcher("/jsp/welcome.jsp").forward(request, response);
}

//返回json数据
@RequestMapping("/ajaxResponse.do")
public void ajaxResponse(HttpServletRequest request, HttpServletResponse response,Student student) throws Exception{
    PrintWriter out = response.getWriter();
    String jsonString = JSON.toJSONString(student);
    out.write(jsonString);
}
```

- 方法返回自定义对象：框架会自动将对象转为json数据，需要添加@ResponseBody注解

```java
@RequestMapping(value = "/returnMap.do")
@ResponseBody
public Map<String, String> returnString() throws Exception{
    Map<String, String> map = new HashMap<>();
    map.put("hello", "你好");
    map.put("world", "世界");
    return map;
}
```



### 重定向和转发

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



### 实现ajax调用

一共四种方式：

1. 直接请求资源，通过HttpServletResponse返回
2. 通过注解@ResponseBody返回
3. 请求和响应的JSON格式，使用jackson.jar包的支持
4. 直接返回对象，容器会自动将其解析成json格式字符串



```java
@Controller
@RequestMapping(value="/ajax/")
public class AjaxController {
	//方式1：
	@RequestMapping(value="m1")
	public void m1(HttpServletResponse resp){
		try {
			resp.getWriter().print("hello ajax request");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//方式2：
	@RequestMapping(value="m2")
	@ResponseBody
	public String m2(){
		return "hello ajax request";
	}
	
	//方式3：
    //注意：需要导入jackson-all.jar
	@RequestMapping(value="m3")
	@ResponseBody
	public String m3(){
		Person p = new Person();
		p.setId(88);
		p.setName("zhangsan");
		p.setPwd("123456");
		p.setSex("man");
		p.setAge(25);
		ObjectMapper om = new ObjectMapper();
		try {
			String json = om.writeValueAsString(p);
			//{"name":"zhangsan","id":88,"age":25,"sex":"man","pwd":"123456"}
			return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//方式4：直接返回对象，容器会帮我们将它自动转为json格式字符串
    //需要开启：<mvc:annotation-driven/>
	@RequestMapping(value="m4")
	@ResponseBody
	public Person m4(String name){
		Person p = new Person();
		p.setId(88);
		p.setName("zhangsan");
		p.setPwd("123456");
		p.setSex("man");
		p.setAge(25);
		return p;
	}

}
```



### 解决乱码问题

#### POST请求乱码问题

表单提交一般使用post方法

**这里需要在`web.xml`中配置**

```xml
<filter>  
    <filter-name>characterEncodingFilter</filter-name>  
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>  
    <init-param>  
        <param-name>encoding</param-name>  
        <param-value>UTF-8</param-value>  
    </init-param>  
    <init-param>  
        <param-name>forceEncoding</param-name>  
        <param-value>true</param-value>  
    </init-param>  
</filter>  
<filter-mapping>  
    <filter-name>characterEncodingFilter</filter-name>  
    <url-pattern>/*</url-pattern>  
</filter-mapping> 
```



#### GET请求乱码问题

链接一般使用get方法

**修改`server.xml`**

```xml
<Connector port="8080"  protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" URIEncoding="UTF-8" /> 
```



#### 乱码解决方案补充

jsp文件

```jsp
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
```

html文件

```html
<meta charset="UTF-8">
```

web项目

​	项目设置utf-8编码

controller中配置

```java
URLDecoder.decode(request.getParameter("test"),"utf-8");
```

数据库设置

```properties
jdbc.url=jdbc:mysql://localhost:3306/homeeducation?useUnicode=true&characterEncoding=UTF-8
```

对参数进行转换

```java
new String(title.getBytes("ISO-8859-1"), "utf-8"); 
```





## SSM

> SpringMVC实现前后台交互处理（Controller）
>
> Spring完成业务逻辑（Service、Bean、Utils）
>
> Mybatis完成数据库操作（Dao）















