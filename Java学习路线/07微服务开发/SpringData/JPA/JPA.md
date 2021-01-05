# JPA

## JPA介绍

### 背景

**如果要将数据库的数据保存到Java对象中,应该怎么去做?**

原生java方法：通过JDBC访问数据库数据，创建Java对象，将数据库数据一一赋值给java对象



**ORM（Object Relational Mapping）对象关系映射**

在操作数据库之前，先把数据表与实体类关联起来。然后通过实体类的对象操作（增删改查）数据库表，这个就是ORM的行为！ 

所以：ORM是一个实现使用对象操作数据库的设计思想！！！ 

**半ORM**：**Mybatis是一种半ORM的应用**。它使用配置将实体类的属性和数据库的字段进行一一对应，使用者通过自己写sql语句，得到的结果会被框架自动封装到对象中。

**ORM**：**JPA使用完全对象关系映射**。在创建实体类的时候，使用注解的方式和数据库字段进行对应，使用者直接调用相关对象的方法来完成数据库操作，**不需要写sql语句**。



### 概述

JPA （Java Persistence API）Java持久化API。是一套Sun公司Java官方制定的**ORM** 方案,是规范，是标准 ，sun公司自己并没有实现。目的是让应用程序以统一的方式访问持久层

**JPA的实现者**

既然我们说JPA是一套标准，意味着，它只是一套实现ORM理论的接口。没有实现的代码。 那么我们必须要有具体的实现者才可以完成ORM操作功能的实现！ 

市场上的主流的JPA框架（实现者）有： 

**Hibernate** （JBoos）、EclipseTop（Eclipse社区）、OpenJPA （Apache基金会）。 

提醒：学习一个JPA框架，其他的框架都是一样使用

**JPA的作用是什么**

JPA是ORM的一套标准，既然JPA为ORM而生，那么JPA的作用就是实现使用对象操作数据库，不用写SQL！！！

问题：数据库是用sql操作的，那用对象操作，由谁来产生SQL？ 

答：JPA实现框架

优点：不同的数据库的SQL语法是有差异，如果不需要编写SQL语句。就屏蔽各种数据库SQL的差异。那么，编写的代码就可以一套代码兼容多种数据库！！！！ 



### JPA核心点

- 如何实现JPA对象和数据库映射
- JPA的API
- 查询语言JPQL



## 入门示例

### 使用流程

1. 我们需要一个总配置文件persistence.xml存储框架需要的信息 

   指定和哪个数据库进行交互

   指定JPA使用哪个持久化框架，以及配置这个框架的相关属性

   **（注意，文件名和位置都是固定的，必须放在classpath/META-INF文件夹里面）** 

2. 创建实体类，完成数据库和实体类之间的映射关系

3. 调用JPA的API完成数据的增删查改

   - 创建实体管理工厂EntityManagerFactory（对应Hibernate的SessionFactory）
   - 使用工厂创建实体管理对象EntityManager（对应Hibernate的Session）



### 详细步骤及代码

**第一步：创建Maven项目**

说明：我们这里是基于hibernate实现的，所以要导入Hibernate的JPA规范包

**pom.xml**

```xml
<!--pom文件-->
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <groupId>cn.zj</groupId>
    <artifactId>jpa-demo01-start</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <dependencies>
        <!--hibernate框架 实现JPA 依赖 -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>4.3.6.Final</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.40</version>
        </dependency>
    </dependencies>

</project>
```

**第二步：创建一个总配置文件**

注意：文件必须放在**classpath:/META-INF/persistence.xml** 

**persistence.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.0" xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
    <persistence-unit name="mysql-jpa">
        <properties> 
            <property name="hibernate.connection.driver_class" value="com.mysql.jdbc.Driver" /> 
            <property name="hibernate.connection.url" value="jdbc:mysql://localhost:3306/jpa" /> 
            <property name="hibernate.connection.username" value="root" /> 
            <property name="hibernate.connection.password" value="root" /> 
            <!--可选配置--> 
            <!--控制台打印sql语句--> 
            <property name="hibernate.show_sql" value="true" /> 
            <property name="hibernate.format_sql" value="true" /> 
        </properties> 
    </persistence-unit>
</persistence>
```

**第三步：创建映射实体类**

创建一个映射的实体类，将JPA的映射注解写在实体类里面。 

```java
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//1.指定实体类与表名的关系
//@Entity注解，指定该实体类是一个基于JPA规范的实体类
//@Table注解，指定当前实体类关联的表
@Entity 
@Table(name="tb_student")
public class Student {
    //@Id注解：声明属性为一个OID属性
    //@GeneratedValue注解，指定主键生成策略
    //@Column注解，设置属性与数据库字段的关系，如果属性名和表的字段名相同，可以不设置
    @Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)    
    @Column(name="stu_id")
    private Long stuId;			//BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '学生编号',
    @Column(name="stu_name")
    private String stuName;		//VARCHAR(50) NULL DEFAULT NULL COMMENT '学生名字',
    @Column(name="stu_age")
    private Integer stuAge;		//INT(11) NULL DEFAULT NULL COMMENT '学生年龄',
    @Column(name="stu_password")
    private String stuPassword;	//VARCHAR(50) NULL DEFAULT NULL COMMENT '登录密码',

    //set、get方法略
}
```

**第四步：封装JPAUtils工具类**

创建一个工具类JPAUtils，获得操作对象（EntityManager） 

```java
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
    //同一个应用中，应该保证只有一个实例工厂。
    public static EntityManagerFactory emf = createEntityManagerFactory();
    //1.获得实体管理工厂
    private static EntityManagerFactory createEntityManagerFactory(){
        //参数对应：配置文件的<persistence-unit name="mysql-jpa">
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql-jpa");
        return emf;
    }
    //2.获得实体管理类对象
    public static EntityManager getEntityManger(){
        EntityManager entityManager = emf.createEntityManager();
        return entityManager;
    }
}
```

**第五步：在总配置文件中加载映射实体类**

```xml
<?xml version="1.0" encoding="UTF-8"?>

<persistence version="2.0" xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd ">
    <persistence-unit name="mysql-jpa">
        <--基于hibernate框架的JPA已经实现了自动载入映射实体类 ，所以不配置也是可以的。建议还是加上配置。如果不写容易忽略加载的实体类有哪些 --> 
        <class>cn.zj.jpa.entity.Student</class>
        <properties> 
          <property name="hibernate.connection.driver_class" value="com.mysql.jdbc.Driver" /> 
          <property name="hibernate.connection.url" value="jdbc:mysql://localhost:3306/jpa" /> 
          <property name="hibernate.connection.username" value="root" /> 
          <property name="hibernate.connection.password" value="zj" /> 
          <!--可选配置--> 
          <!--控制台打印sql语句--> 
          <property name="hibernate.show_sql" value="true" /> 
          <property name="hibernate.format_sql" value="true" /> 
       </properties> 
     </persistence-unit>
</persistence>
```

**第六步：操作实体类保存数据**

创建一个StudentDAOTest类，测试保存一个学生。

```java
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.junit.Test;
import cn.zj.jpa.entity.Student;
import cn.zj.jpa.util.JPAUtils;

public class StudentDAOTest {

	@Test
	public void persist(){
		//1.获得实体管理类
		EntityManager manager = JPAUtils.getEntityManger();
		//2、获取事物管理器
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		//3、创建实体对象
		Student s=new Student();
		s.setStuName("张三");
		s.setStuAge(18);
		s.setStuPassword("zj");
		//4、保存到数据库
		manager.persist(s);
		//5、提交事物
		transaction.commit();
		//6、关闭资源
		manager.close();
	}

}
```



## JPA

### 注解

实体类中的注解

**基本注解**

- @Entity

- @Table

- @Id

  ​	@GenerateValue：四种方式

  - IDENTITY：数据库自增长主键，Oracle不支持
  - AUTO：JPA自动选择
  - SEQUENCE：通过序列产生主键，通过@SequenceGenerator注解指定序列名，Mysql不支持
  - TABLE：通过表产生主键，便于数据库移植

- @Column

  可以对表的字段进行详细限制，属性包括name、unique、nullable、length

- @Basic：默认注解

**特殊注解**

- @Transient：表示该实体类属性不加到数据库表中

- @Temporal：对日期类型进行对应，类型包括Date、Timestamp、Time

**用数据库表生成主键**

```java
@TableGenerator(name="ID_GENERATOR",
                table="tb_id_generators"//对应数据库哪个表
                //通过表的行列确定字段的位置
                pkColumnName="pk_name",pkColumnValue="studentid",valueColumnName="pk_value",
                //主键增长步长
                allocationSize=10
               )
@GenratedValue(strategy=GenerationType.TABLE,generator="ID_GENERATOR")
@Id
```



### JPA的API

**生产对象**

- Persistence
  - 调用createEntityManagerFactory(持久化单元名字)方法
  - isOpen()、close()关闭EntityManagerFactory
- EntityManagerFactory

**使用对象**

- EntityManager

**事务**

- EntityTransaction



#### EntityManager对象

注意：Hibernate的方法名称和实现细节会有所区别

##### 简单方法

**查找**

find(Class.class,id)：通过id查找对应的实体类

getReference(Class.class,id)：也是通过id查找对应的实体类，但是会等需要使用的时候才加载实际数值【懒加载】

**保存/更新**

【这里需要注意临时对象（没有OID）和游离对象（有OID）的区别】

persist(Class)：将对象保存到数据库，原对象id由空变为有值【注意如果对象有id，则会发生异常】

merge(Class)：返回的对象有id值（临时对象），而原对象（游离对象）没有id值

**删除**

delete(Class)

**其他方法**

flush()：同步持久上下文环境，将所有未保存实体的状态信息保存到数据库

refresh()、clear()、contains()



实例代码

```java
//通过OID删除
@Test
public void remove(){
    //1.获得实体管理类对象
    EntityManager entityManager = JPAUtils.getEntityManger();
    //2.打开事务
    EntityTransaction transaction = entityManager.getTransaction();
    //3.启动事务
    transaction.begin();
    //4.创建数据,删除数据必须使用持久化对象
    Student s=entityManager.find(Student.class, 2L);
    //5.插入
    entityManager.remove(s);;
    //6。提交
    transaction.commit();
    //7.关闭
    entityManager.close();
}

//更新
@Test
public void merge(){
    //1.获得实体管理类对象
    EntityManager entityManager = JPAUtils.getEntityManger();
    //2.打开事务
    EntityTransaction transaction = entityManager.getTransaction();
    //3.启动事务
    transaction.begin();
    //4.创建数据
    Student s=new Student();
    s.setStuName("李四");
    //更新必须要有一个OID
    s.setStuId(3L);
    //5.更新
    entityManager.merge(s);
    //6。提交
    transaction.commit();
    //7.关闭
    entityManager.close();
}
//通过OID获得数据
@Test
public void find(){
    //1.获得实体管理类对象
    EntityManager entityManager = JPAUtils.getEntityManger();
    //通过OID查询数据
    Student student = entityManager.find(Student.class, 1L);
    System.out.println(student.getStuName());
    entityManager.close();
}
//通过OID获得数据
@Test
public void getReference(){
    //1.获得实体管理类对象
    EntityManager entityManager = JPAUtils.*getEntityManger*();**
        /**
		* getReference()和find()方法的区别：
		* getReference基于懒加载机制，即需要使用对象的时候，才执行查询。
		**/
        Student student = entityManager.getReference(Student.class, 1L);
    System.out.println(student.getStuName());
    entityManager.close();
}
```



##### 复杂查询

createQuery(string)：使用JPQL查询

createNativeQuery(string)：使用sql查询



#### EntityTransaction对象

进行事务管理

- begin()
- commit()
- rollback()
- setRollbackOnly()/getRollbackOnly()
- isActive()



### JPA映射关联关系

单向一对多、**单向多对一**；双向一对一、双向多对一、双向多对多

- @JoinTable关联查询时，表与表是多对多的关系时，指定多对多关联表中间表的参数。

- @JoinColumn关联查询时，表与表是一对一、一对多、多对一以及多对多的关系时，声明表关联的外键字段作为连接表的条件。必须配合关联表的注解一起使用 

- @OneToMany关联表注解，表示对应的实体和本类是一对多的关系

- @ManyToOne关联表注解，表示对应的实体和本类是多对一的关系

- @ManyToMany关联表注解，表示对应的实体和本类是多对多的关系

- @OneToOne关联表注解，表示对应的实体和本类是一对一的关系






**级联操作类型**

- CascadeType.PERSIST
  Cascade persist operation，级联持久化（保存）操作（持久保存拥有方实体时，也会持久保存该实体的所有相关数据。）
  理解是：**给当前设置的实体操作另一个实体的权限。**这个理解可以推广到每一个CascadeType。

- CascadeType.REMOVE
  Cascade remove operation，级联删除操作。
  删除当前实体时，与它有映射关系的实体也会跟着被删除。
- CascadeType.MERGE
  Cascade merge operation，级联更新（合并）操作。
  当Student中的数据改变，会相应地更新Course中的数据。
- CascadeType.DETACH
  Cascade detach operation，级联脱管/游离操作。
  如果你要删除一个实体，但是它有外键无法删除，你就需要这个级联权限了。它会撤销所有相关的外键关联。
- CascadeType.REFRESH
  Cascade refresh operation，级联刷新操作。
  假设场景 有一个订单,订单里面关联了许多商品,这个订单可以被很多人操作,那么这个时候A对此订单和关联的商品进行了修改,与此同时,B也进行了相同的操作,但是B先一步比A保存了数据,那么当A保存数据的时候,就需要先刷新订单信息及关联的商品信息后,再将订单及商品保存。
- CascadeType.ALL
  Cascade all operations，清晰明确，拥有以上所有级联操作权限。



单向多对一&单向一对多：一个客户有多个订单，一个订单只能对应一个客户

```java
class Customer{
	private Long customerid;
    
    @JoinColumn(name="orderid")
    @OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.Remove})
    //删除顾客时，会把对应的全部订单删掉
    List<Order> orders;
    
    //另一种写法，使用mapperedBy就不能使用JoinColumn
    // @OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.Remove}，mapperedBy="customer")
    //删除顾客时，会把对应的全部订单删掉
    //List<Order> orders;
}

class Order{
    private Long orderid;
    
    @JoinColumn(name="customerid")
    @ManyToOne()
    private Customer customer;
}

//在使用保存操作时，建议先保存一再保存多，可以不会使用额外的Update语句
//不能直接删除一的数据，因为存在外键关系
```

双向一对一

@OneToOne，对于不维护关联关系，没有外键的一方，需要设置mapperedBy映射

双向多对多

@ManyToMany



### JPA的二级缓存

**一级缓存默认开启**：同一个EntityManager下执行的操作会作为一级缓存，如果再次进行相同操作可以直接读取缓存

**二级缓存需要在配置中开启**

```xml
pom.xml
<!-- ehcache缓存 -->
<dependency>
    <groupId>net.sf.ehcache</groupId>
    <artifactId>ehcache-core</artifactId>
    <version>2.6.9</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-ehcache</artifactId>
    <version>${hibernate.version}</version>
</dependency>


persistence.xml
<!--
配置二级缓存的策略
ALL：所有的实体类都被缓存
NONE：所有的实体类都不被缓存.
ENABLE_SELECTIVE：标识 @Cacheable(true) 注解的实体类将被缓存
DISABLE_SELECTIVE：缓存除标识 @Cacheable(false) 以外的所有实体类
UNSPECIFIED：默认值，JPA 产品默认值将被使用
-->
<shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
<!-- 开启二级缓存 和 查询缓存（使用jpa时必须开启） 默认ehcache.xml配置路径为根目录 classpath：下 -->
<properties>
    <property name="hibernate.cache.region.factory_class" value="org.hibernate.cache.ehcache.EhCacheRegionFactory"></property>
    <property name="hibernate.cache.use_second_level_cache" value="true"></property>
    <property name="hibernate.cache.use_query_cache" value="true"></property>
</properties>


src/ehcache.xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache>
<!-- 
maxElementsInMemory:缓存中最大允许创建的对象数
eternal:缓存中对象是否为永久的，如果是，超时设置将被忽略，对象从不过期
timeToIdleSeconds:缓存数据钝化时间(设置对象在它过期之前的空闲时间)
timeToLiveSeconds:缓存数据的生存时间(设置对象在它过期之前的生存时间)
overflowToDisk:内存不足时，是否启用磁盘缓存
clearOnFlush:内存数量最大时是否清除
-->
<defaultCache 
    maxElementsInMemory="1000"
    eternal="false"
    timeToIdleSeconds="1200"
    timeToLiveSeconds="1200"
    overflowToDisk="false"
    clearOnFlush="true">
</defaultCache>
<!-- 单独对某个entity的缓存策略设置-->
<!-- <cache name="com.mushi.core.entity.user.User" 
    maxElementsInMemory="100"
     eternal="false"
     timeToIdleSeconds="1200" 
     timeToLiveSeconds="1200" 
     overflowToDisk="false"
     clearOnFlush="true">
</cache> -->
</ehcache>



实体类加上注解
@Cacheable(true)

```



## JPQL

Java Persistence Query Language的简称，是和sql类似的中间性和对象化的查询语言。它最终会被编译成针对不同数据库的sql语言，从而屏蔽不同数据库的差异。

JPA的查询语言,类似于sql

1. 里面不能出现表名,列名,只能出现java的类名,属性名，区分大小写

2. 出现的sql关键字是一样的意思,关键字不区分大小写

3. 不能写select * 要写select 别名



### 创建Query对象

- createQuery(string)：使用JPQL查询
- createNamedQuery(“name”)：调用实体类@NamedQuery内部的jpql
- createNativeQuery(string)：使用sql查询



### Query的相关方法

**javax.persistence.Query** 接口封装了执行数据查询的相关方法。主要方法如下：

- int executeUpdate()：用于执行 update 或 delete 语句。
- List getResult()：用于执行 select 语句并返回结果集实体列表。
- Object getSingleResult()：用于执行返回单个结果实体的 select 语句。
- Query setFirstResult(int startPosition)：用于设置从指定行数返回查询结果。
- Query setMaxResults(int maxResult)：用于设置结果实体的最大数目。
- setParameter(int position, Object value)：为查询语句的指定位置参数赋值。**下标从 1 开始**。



**常用的JPQL语句**

```sql
//简单查询
select stu from Student stu
select stu from Student stu where id= ?1

//分组、聚合
select stu from Student stu group by stu.age having count(stu.id) > 2

//连接查询
select stu from Student stu left outer join fetch stu.classes

//子查询
select stu from Student stu where stu.class = (select c from Class c where c.name = ?)

//相关函数
略


//jpql也可以执行update\delete
update Student stu set stu.name = ? where stu.id = ?
```



用法举例：

```java
public class MappingTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-1");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void destroy() {
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testSelectJPQL() {
        String jpql = "FROM Customer WHERE id = ?1";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1, 1);
        Customer customer = (Customer) query.getSingleResult();
        System.out.println(customer.getName());
    }

}
```



### 调用缓存

前提是已经启用缓存

```sql
String jpql = "FROM Customer WHERE id = ?1";
Query query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE,true);
query.setParameter(1, 1);
Customer customer = (Customer) query.getSingleResult();
System.out.println(customer.getName());
```



## 整合其他框架

整合Spring

整合SpringBoot

