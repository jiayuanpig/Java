# spring

## 一、IOC

控制反转，通过配置文件来创建对象，减少编译期异常

### 1、IOC基于xml配置

​	在rescourse创建xml文件，有三种方式创建bean对象

```Java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--告知spring在创建容器时要扫描的包，配置所需要的标签不是在beans的约束中，而是一个名称为context名称空间和约束中-->
    <context:component-scan base-package="com.zsy"></context:component-scan>
   
    <!--第一种创建对象方式：需要有默认构造函数-->
    <bean id="accountService" class="com.zsy.service.impl.AccountServiceImpl"></bean>
    <bean id="accountDao" class="com.zsy.dao.impl.AccountDaoImpl"></bean>
        
    <!--第二种创建对象方式：调用指定方法生成对象-->
    <bean id="accountService" class="com.zsy.service.impl.AccountServiceImpl"></bean>
    <bean id="account" factory-bean="accountService" factory-method="saveAccount"></bean>
    
    <!--第三种创建对象方式-->  
    <bean id="account" class="com.zsy.dao.impl.AccountDaoImpl" factory-method="静态方法"></bean>
        
</beans>
```



#### bean的作用范围：scope属性

>
> - sington 单例
> - prototype 多例
> - request 请求范围
> - session 会话范围
> - global-session 集群会话范围



#### bean的生命周期

>
> - 单例对象随着容器的创建而创建，随着容器的销毁而销毁
> - 多例对象使用时候创建，当对象长时间不用的时候被垃圾回收机制回收



#### 使用方式

```Java
//使用spring
//1、获取核心容器
ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
//2、根据id 获取对象
IAccountService as = (IAccountService)ac.getBean("accountService");
```



####  spring注入

1、构造方法注入

```Java
<bean id="accountDao" class="com.zsy.dao.impl.AccountDaoImpl">
    <constructor-arg name="name" value="haha"></constructor-arg>
    <!--ref可以注册其他的bean 类型-->
    <constructor-arg name="birthday" ref="b"></constructor-arg>
</bean>

<bean id="b" class="java.util.Date"></bean>
```

2、set方法注入

```java
<bean id="accountDao" class="com.zsy.dao.impl.AccountDaoImpl">
    <property name="name" value="haha"></property>
    <!--ref可以注册其他的bean 类型-->
    <property name="birthday" ref="b"></property>
</bean>
<bean id="b" class="java.util.Date"></bean>
```



### 2、IOC基于注解配置

配置xml文件

```Java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--告知spring在创建容器时要扫描的包，配置所需要的标签不是在beans的约束中，而是一个名称为
    context名称空间和约束中-->
    <context:component-scan base-package="com.zsy"></context:component-scan>
</beans>
```



#### 注解

> 使用注解```@Component```吧当前对象存入容器中
>
> @Controller 一般用于表现层
>
> @Service 一般用于业务逻辑层
>
> @Repository 一般用于持久层
>
> @Autowired自动注入（加上@Qualifier(value="")）
>
> @Resource(name="bean的id")
>
> @value(value="")用于注入基本类型和String类型



#### 新注解？？？

```Java
<context:component-scan base-package="com.zsy"></context:component-scan>
//相当于下面的内容
@Configuration
@ComponentScan(basePackages = "com.zsy")
public class account {
}

```

> @Bean（name=""）把当前方法的返回值存入到容器中，默认值是当前方法的名称
>
> @import(value="其他类的字节码")用于配置其他类的字节码
>
> @propertySource(value="classes:jdbc.properties")用于指定properties文件的位置

```Java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = "")//代表注解创建，指定创建的位置
//如果是xml配置，则使用locations加上classpath,指定文件位置
public class test {
}
```



## 二、AOP

面向切面：对已有方法的增强，对写死的代码进行扩充实现更多的功能

### 1、基于xml配置

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <bean id="account" class="com.zsy.service.impl.AccountServiceImpl"></bean>
    <bean id="logger" class="com.zsy.utils.Logger"></bean>
    
    <aop:config>
        <aop:aspect id="logadvice" ref="logger">
            <!--配置切面-->
            <aop:before method="printLog" pointcut="execution(public void com.zsy.service.impl.AccountServiceImpl.saveAccount())"></aop:before>
        </aop:aspect>
    </aop:config>
</beans>
```

#### AOP使用方式

1、把通知Bean也交给spring来管理
2、使用aop:config标签表明开始AOP的配置
3、使用aop:aspect标签表明配置切面
            id属性：是给切面提供一个唯一标识
            ref属性：是指定通知类bean的Id。
4、在aop:aspect标签的内部使用对应标签来配置通知的类型
           我们现在示例是让printLog方法在切入点方法执行之前之前：所以是前置通知
           aop:before：表示配置前置通知
                method属性：用于指定Logger类中哪个方法是前置通知
                pointcut属性：用于指定切入点表达式，该表达式的含义指的是对业务层中哪些方法增强

> ​        切入点表达式的写法：
> ​            关键字：execution(表达式)
> ​            表达式：
> ​                访问修饰符  返回值  包名.包名.包名...类名.方法名(参数列表)
> ​                表达式：
> ​                访问修饰符  返回值  包名.包名.包名...类名.方法名(参数列表)
> ​            标准的表达式写法：
> ​                public void com.itheima.service.impl.AccountServiceImpl.saveAccount()
> ​            访问修饰符可以省略
> ​                void com.itheima.service.impl.AccountServiceImpl.saveAccount()
> ​            返回值可以使用通配符，表示任意返回值
>
>                 * com.itheima.service.impl.AccountServiceImpl.saveAccount()
>                         包名可以使用通配符，表示任意包。但是有几级包，就需要写几个*.
>                 
>                             * *.*.*.*.AccountServiceImpl.saveAccount())
>                                         包名可以使用..表示当前包及其子包
>                                             * *..AccountServiceImpl.saveAccount()
>                                                     类名和方法名都可以使用*来实现通配
>                                                             * *..*.*()
>                                                                 参数列表：
>                                                                     ​                可以直接写数据类型：
>                     基本类型直接写名称           int
>                 引用类型写包名.类名的方式   java.lang.String
>                                                                 ​                可以使用通配符表示任意类型，但是必须有参数
>                                                             ​                可以使用..表示有无参数均可，有参数可以是任意类型
>                                                     ​            全通配写法：
>                                                                 
>                                                                                 * *..*.*(..)
>
> ​            实际开发中切入点表达式的通常写法：
> ​                切到业务层实现类下的所有方法
>                     * com.zsy.service.impl.*.*(..)-->
>

​      

```java
<bean id="logger" class="com.zsy.utils.Logger"></bean>
<aop:config>
    <!-- 
    	配置切入点表达式 id属性用于指定表达式的唯一标识。expression属性用于指定表达式内容
          此标签写在aop:aspect标签内部只能当前切面使用。
          它还可以写在aop:aspect外面，此时就变成了所有切面可用
    -->
    <aop:pointcut id="pt1" expression="execution(*.com.zsy.service.impl.*.*(..))"></aop:pointcut>
    <!--配置切面 -->
    <aop:aspect id="logAdvice" ref="logger">
        <!-- 配置前置通知：在切入点方法执行之前执行
        <aop:before method="beforePrintLog" pointcut-ref="pt1" ></aop:before>-->

        <!-- 配置后置通知：在切入点方法正常执行之后值。它和异常通知永远只能执行一个
        <aop:after-returning method="afterReturningPrintLog" pointcut-ref="pt1"></aop:after-returning>-->

        <!-- 配置异常通知：在切入点方法执行产生异常之后执行。它和后置通知永远只能执行一个
        <aop:after-throwing method="afterThrowingPrintLog" pointcut-ref="pt1"></aop:after-throwing>-->

        <!-- 配置最终通知：无论切入点方法是否正常执行它都会在其后面执行
        <aop:after method="afterPrintLog" pointcut-ref="pt1"></aop:after>-->

        <!-- 配置环绕通知 详细的注释请看Logger类中-->
        <aop:around method="aroundPringLog" pointcut-ref="pt1"></aop:around>
    </aop:aspect>
</aop:config>
```



#### spring环绕通知可以使用代码控制

```Java
public Object aroundPringLog(ProceedingJoinPoint pjp){
    Object rtValue = null;
    try{
        Object[] args = pjp.getArgs();//得到方法执行所需的参数

        System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。前置");

        rtValue = pjp.proceed(args);//明确调用业务层方法（切入点方法）

        System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。后置");

        return rtValue;
    }catch (Throwable t){
        System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。异常");
        throw new RuntimeException(t);
    }finally {
        System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。最终");
    }
}
```



### 2、基于注解？？？

```Java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
    <!--配置要扫描的包-->
    <context:component-scan base-package="com.zsy"></context:component-scan>
    <!--开启AOP注解支持-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>
```

```java
@Component("logger")//加入容器
@Aspect//配置切面
public class Logger {
    @Pointcut("execution(* com.zsy.service.impl.*.*(..))")
    public void printLog(){

    }
}

```

