# mybatis
## 基于xml

### xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?> <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd"> 
    <mapper namespace="com.itheima.dao.IUserDao"> 
    <!-- 配置查询所有操作 --> 
        <select id="findAll" resultType="com.itheima.domain.User"> 
            select * from user 
        </select>
        <insert id="saveUser" parameterType="com.itheima.domain.User"> 
    <!-- 配置保存时获取插入的id -->
        <selectKey keyColumn="id" keyProperty="id" resultType="int"> 
        	select last_insert_id(); 
    	</selectKey>
            insert into user(username,birthday,sex,address) 
            values(#{username},#{birthday},#{sex},#{address}) 
        </insert>
        <!-- 查询总记录条数 --> 
        <select id="findTotal" resultType="int"> 
            select count(*) from user;
		</select>
        <select id="findByUser" resultType="user" parameterType="user">
            select * from user 
            <where>
                <if test="username!=null and username != '' ">
                    and username like #{username} 
                </if> 
                <if test="address != null"> 
                    and address like #{address} 
                </if> 
              </where>
        </select>
    </mapper>
```
sql片段
```xml
<sql id="defaultSql"> 
    select * from user 
</sql>
<select id="findAll" resultType="user"> 
    <include refid="defaultSql"></include> 
</select>
```

一对一

```xml
<mapper namespace="com.itheima.dao.IAccountDao">
    <!-- 建立对应关系 -->
    <resultMap type="account" id="accountMap"> 
        <id column="aid" property="id"/> 
        <result column="uid" property="uid"/> 
        <result column="money" property="money"/>
        <!-- 它是用于指定从表方的引用实体属性的 --> 
        <association property="user" javaType="user"> 
            <id column="id" property="id"/> 
            <result column="username" property="username"/>
            <result column="sex" property="sex"/> 
            <result column="birthday" property="birthday"/> 
            <result column="address" property="address"/> 
        </association> 
    </resultMap> 
    <select id="findAll" resultMap="accountMap">
        select u.*,a.id as aid,a.uid,a.money from account a,user u where a.uid =u.id; </select>
```
一对多
```xml
<mapper namespace="com.itheima.dao.IUserDao"> 
    <resultMap type="user" id="userMap"> 
        <id column="id" property="id"></id> 
        <result column="username" property="username"/>
        <result column="address" property="address"/> 
        <result column="sex" property="sex"/> 
        <result column="birthday" property="birthday"/> 
        <!-- collection是用于建立一对多中集合属性的对应关系 ofType用于指定集合元素的数据类型 --> 			<collection property="accounts" ofType="account"> 
            <id column="aid" property="id"/> 
            <result column="uid" property="uid"/> 
            <result column="money" property="money"/>
        </collection> 
    </resultMap> 
    <!-- 配置查询所有操作 --> 
    <select id="findAll" resultMap="userMap">
        select u.*,a.id as aid ,a.uid,a.money from user u left outer join account a on u.id =a.uid 
    </select> 
</mapper>
```



### SqlMapConfig.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration> 
        <!-- 配置mybatis的环境 --> 
        <environments default="mysql"> 
        <!-- 配置mysql的环境 --> 
            <environment id="mysql"> 
                <!-- 配置事务的类型 --> 
                <transactionManager type="JDBC"></transactionManager> 
                <!-- 配置连接数据库的信息：用的是数据源(连接池) --> 
                <dataSource type="POOLED"> 
                    <property name="driver" value="com.mysql.jdbc.Driver"/>
                    <property name="url" value="jdbc:mysql://localhost:3306/ssm"/> 
                    <property name="username" value="root"/>
                    <property name="password" value="8848"/> 
                </dataSource> 
                <!--
                    UNPOOLED 不使用连接池的数据源 
                    POOLED 使用连接池的数据源 
                    JNDI 使用JNDI实现的数据源-->
              </environment> 
        </environments> 
        <!-- 告知mybatis映射配置的位置 --> 
        <mappers>
        <mapper resource="com/itheima/dao/IUserDao.xml"/></mappers> 
    </configuration>
```

> parameterType属性：代表参数的类型

### 测试类

```java
//1.读取配置文件
InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml"); 
//2.创建SqlSessionFactory的构建者对象 
SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder(); 
//3.使用构建者创建工厂对象
SqlSessionFactory SqlSessionFactory factory = builder.build(in); 
//4.使用SqlSessionFactory生产SqlSession对象 
SqlSession session = factory.openSession(); 
//5.使用SqlSession创建dao接口的代理对象 
IUserDao userDao = session.getMapper(IUserDao.class); 
//6.使用代理对象执行查询所有方法 
List<User> users = userDao.findAll();
session.commit(); //7.释放资源 
session.close(); 
in.close();
```
### 自定义返回类型

```xml
<resultMap type="com.zsy.domain.User" id="userMap"> 
    <id column="id" property="userId"/> 
    <result column="username" property="userName"/> 
    <result column="sex" property="userSex"/> 
    <result column="address" property="userAddress"/>
    <result column="birthday" property="userBirthday"/> 
</resultMap> 
id标签：用于指定主键字段 result标签：用于指定非主键字段 column属性：用于指定数据库列名 property属性：用于指定实体类属性名称
<select id="findAll" resultMap="userMap"> 
    select * from user 
</select>
```



## 基于注解

配置文件改为

```xml
<mappers> <mapper class="com.itheima.dao.IUserDao"/> </mappers>
```

Dao文件

```java
@Select("select * from user")
List<User> findAll();
```



