# springmvc

## 1、xml配置

快速创建maven项目配置

```java
archetypeCatalog
internal
```



### web.xml配置

```Java
<!DOCTYPE web-app PUBLIC
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
        "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>

  <!--配置前端控制器-->
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--初始化的时候加载配置文件-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <!--启动服务器就开始创建-->
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>

  <!--配置解决中文乱码的过滤器-->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

</web-app>
```

### springmvc.xml配置

```java 
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 开启注解扫描 -->
    <context:component-scan base-package="com.zsy"/>

    <!-- 视图解析器对象 -->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"></property>
        <property name="suffix" value=".jsp"></property>
    </bean>
    <!--配置自定义类型转换器-->
    <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="com.zsy.utils.StringToDate"/>
            </set>
        </property>
    </bean>

    <!-- 开启SpringMVC框架注解的支持 -->
    <mvc:annotation-driven conversion-service="conversionService" />

</beans>
```

```java
//自定义类型转换器
public class StringToDate implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        if(s == null){
            throw new RuntimeException("传入参数为空");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
```

## 2、 注解

```Java
//必须传递username属性 
@RequestMapping(value = "/hello",method = {RequestMethod.GET},params = {"username"})
@ModelAttribute()//请求方法先执行
//请求参数
@RequestParam(name="")//指定传入参数名
@RequestBody//请求实体
@PathVariable()//拿到url中的占位符进行赋值
@CookieValue (value="")//拿到cookie的值
```

## 3、文件上传

```Java
<%--
    1、enctype必须改
    2、name属性必须和MultipartFile中变量名一致
    3、配置文件解析器对象，id必须是multiparResolver--%>
<form action="/user/fileUpload" method="post" enctype="multipart/form-data">
    请选择文件：<input type="file" name="Upload"/><br/>
    <input type="submit" value="上传">
</form>
```

```java 
@RequestMapping("/fileUpload")
public String fileUpload(HttpServletRequest request, MultipartFile Upload) throws IOException {
    String path = request.getSession().getServletContext().getRealPath("/file/");
    File file = new File(path);
    if(!file.exists()){
        file.mkdirs();
    }
    //获取文件名称

    String filename = Upload.getOriginalFilename();
    String uuid = UUID.randomUUID().toString().replace("-","");
    filename = filename + uuid;
    Upload.transferTo(new File(path,filename));
    System.out.println("文件上传成功");
    return "success";
}
```

