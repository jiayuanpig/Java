# JSON



## 简介

  JSON(JavaScript Object Notation) 是一种轻量级的数据交换格式。JSON 使用 Javascript语法来描述数据对象，但是 JSON 仍然独立于语言和平台。JSON 解析器和 JSON 库支持许多不同的编程语言。 目前非常多的动态（PHP，JSP，.NET）编程语言都支持JSON。

JSON的用途：

1. 使用基于JavaScript的应用程序，其中包括浏览器扩展和网站
2. 使用JSON格式序列化和结构化的数据传输网络连接
3. **主要用于服务器和Web应用程序之间的数据传输**
4. Web服务和API采用JSON格式提供公共数据
5. 它可以用来与现代编程语言

JSON的特点：

- 易于读写JSON(JSON 具有自我描述性，更易理解)
- 轻量级的基于文本的交换格式
- 独立语言

JSON与 XML 相同之处：

- JSON 是纯文本
- JSON 具有"自我描述性"（人类可读）
- JSON 具有层级结构（值中存在值）
- JSON 可通过 JavaScript 进行解析
- JSON 数据可使用 AJAX 进行传输

与 XML 不同之处：

- 没有结束标签
- 更短
- 读写的速度更快
- 能够使用内建的 JavaScript eval() 方法进行解析
- 使用数组
- 不使用保留字

**为什么使用 JSON？**
对于 AJAX 应用程序来说，JSON 比 XML 更快更易使用：

- 使用 XML
  - 读取 XML 文档
  - 使用 XML DOM 来循环遍历文档
  - 读取值并存储在变量中
- 使用 JSON
  - 读取 JSON 字符串
  - 用 eval() 处理 JSON 字符串



## JSON语法

```json
{
    "age":30,						//数字
    "name":"Tom",					//名字
    "salary":null,
    "class":["语文","数学","英语"]	//数组
    "student":{						//对象
    	"name":"Jerry",
    	"sex","男"
	}
}
```

JSON 语法是 JavaScript 对象表示法语法的子集：

- 数据在名称/值对中
- 数据由逗号分隔
- 大括号保存对象
- 中括号保存数组

JSON 文件：

- JSON 文件的文件类型是 ".json"
- JSON 文本的 MIME 类型是 "application/json"

JSON数据表示为键值对的形式，书写格式是：名称/值。其中“名称”需要用双引号括起来，然后紧跟一个“冒号(英文)”，然后就是“值”。JSON的值类型：

1. 数字（整数或浮点数）
2. **字符串（在双引号中）**
3. 逻辑值（true 或 false）
4. 数组（在中括号中）
5. 对象（在大/花括号中）
6. null

**JSON数组里面可以放对象，对象里面可以放数组**



## JSON转换

### JSON与JavaScript

在数据传输流程中，json是以文本，即字符串的形式传递的，而JS操作的是JSON对象，所以，JSON对象和JSON字符串之间的相互转换是关键。

JSON 是 JavaScript 原生格式，这意味着在 JavaScript 中处理 JSON 数据不需要任何特殊的 API 或工具包。

```js
JSON字符串:
var str1 = '{ "name": "Tom", "sex": "man" }';

JSON对象:
var str2 = { "name": "Tom", "sex": "man" };
```

**将json字符串转为json对象**

 特别留心：如果obj本来就是一个JSON对象，那么运用 eval（）函数转换后（哪怕是多次转换）还是JSON对象，但是运用 parseJSON（）函数处理后会有疑问（抛出语法异常）。

```js
//方式1
var obj = eval('(' + str + ')');
//方式2
var obj = str.parseJSON(); //由JSON字符串转换为JSON对象
//方式3
var obj = JSON.parse(str); //由JSON字符串转换为JSON对象
```

**将json对象转为json字符串**

```js
//方式1
var last=obj.toJSONString(); //将JSON对象转化为JSON字符
//方式2
var last=JSON.stringify(obj); //将JSON对象转化为JSON字符
```



### JSON与Java

**目前世面上的解析技术**

1. json-lib
   json-lib最开始的也是应用最广泛的json解析工具，json-lib 不好的地方确实是依赖于很多第三方包，
   包括commons-beanutils.jar，commons-collections-3.2.jar，commons-lang-2.6.jar，commons-logging-1.1.1.jar，ezmorph-1.0.6.jar，
   对于复杂类型的转换，json-lib对于json转换成bean还有缺陷，比如一个类里面会出现另一个类的list或者map集合，json-lib从json到bean的转换就会出现问题。
   json-lib在功能和性能上面都不能满足现在互联网化的需求。
2. 开源的jackson
   相比json-lib框架，Jackson所依赖的jar包较少，简单易用并且性能也要相对高些。
   而且Jackson社区相对比较活跃，更新速度也比较快。
   Jackson对于复杂类型的json转换bean会出现问题，一些集合Map，List的转换出现问题。
   Jackson对于复杂类型的bean转换Json，转换的json格式不是标准的Json格式
3. Goole的Gson
   Gson是目前功能最全的Json解析神器，Gson当初是为因应Google公司内部需求而由Google自行研发而来，
   但自从在2008年五月公开发布第一版后已被许多公司或用户应用。
   Gson的应用主要为toJson与fromJson两个转换函数，无依赖，不需要例外额外的jar，能够直接跑在JDK上。
   而在使用这种对象转换之前需先创建好对象的类型以及其成员才能成功的将JSON字符串成功转换成相对应的对象。
   类里面只要有get和set方法，Gson完全可以将复杂类型的json到bean或bean到json的转换，是JSON解析的神器。
   Gson在功能上面无可挑剔，但是性能上面比FastJson有所差距。
4. 阿里巴巴的FastJson
   Fastjson是一个Java语言编写的高性能的JSON处理器,由阿里巴巴公司开发。无依赖，不需要例外额外的jar，能够直接跑在JDK上。
   FastJson在复杂类型的Bean转换Json上会出现一些问题，可能会出现引用的类型，导致Json转换出错，需要制定引用。
   FastJson采用独创的算法，将parse的速度提升到极致，超过所有json库。

综上4种Json技术的比较，在项目选型的时候可以使用Google的Gson和阿里巴巴的FastJson两种并行使用；如果只是功能要求，没有性能要求，可以使用google的Gson；如果有性能上面的要求可以使用Gson将bean转换json确保数据的正确，使用FastJson将Json转换Bean



下面以Gson为例：（不同的jar包解析方式不同，这里仅作参考使用）

在 `pom.xml` 中引入 `Gson` 依赖

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.2</version>
</dependency>
```

**将json字符串转为java对象**

```java
//基本类型转换
Gson gson = new Gson();
int i = gson.fromJson("100", int.class);              //100
double d = gson.fromJson("\"99.99\"", double.class);  //99.99
boolean b = gson.fromJson("true", boolean.class);     // true
String str = gson.fromJson("String", String.class);   // String

//自定义对象转换
Gson gson = new Gson();
User user = new User("Tom",24);
String jsonObject = gson.toJson(user); 
```



**将java对象转为json字符串**

```java
//java基本类型转json字符串
Gson gson = new Gson();
String jsonNumber = gson.toJson(100);       // 100
String jsonBoolean = gson.toJson(false);    // false
String jsonString = gson.toJson("String"); //"String"

//自定义对象转json字符串
public static void beanToJsonTest() {
    User user = new User();

    user.setName("张三");
    user.setAge(20);
    user.setLike(new String[]{"看电影", "看书"});

    /**
     * 使用GsonBuilder 可以作一些额外处理，比如格式化输出，预处理等
     * 
     * GsonBuilder gsonBuilder = new GsonBuilder();
     * gsonBuilder.setPrettyPrinting();    
     * Gson gson = gsonBuilder.create();
     */
    Gson gson = new Gson();
    System.out.println(gson.toJson(user));
}
```

**GsonBuilder**

一般情况下Gson类提供的 API已经能满足大部分的使用场景，但我们需要更多更特殊、更强大的功能时，这时候就引入一个新的类 GsonBuilder。具体用法略。



**注意：转换中的日期对象、集合对象需要特殊处理！！！**

我们通过Gson解析字符串数组的json时，一般有两种方式：使用数组，使用List。而List对于增删都是比较方便的，所以实际使用是还是List比较多。

> JSON字符串数组：
>
> ```java
> Gson gson = new Gson();
> String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
> String[] strings = gson.fromJson(jsonArray, String[].class);
> ```
>
> 但对于List将上面的代码中的 String[].class 直接改为 List<String>.class 是行不通的。对于Java来说List<String> 和List<User> 这俩个的字节码文件只一个那就是List.class，这是Java泛型使用时要注意的问题 泛型擦除。为了解决这个问题，Gson为我们提供了TypeToken来实现对泛型的支持，所以当我们希望使用将以上的数据解析为List<String>时需要这样写：
>
> ```java
> Gson gson = new Gson();
> String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
> String[] strings = gson.fromJson(jsonArray, String[].class);
> List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());
> ```
>
> 注：TypeToken的构造方法是protected修饰的,所以上面才会写成new TypeToken<List<String>>() {}.getType() 而不是 new TypeToken<List<String>>().getType()



### 其他的转换问题

- 乱码问题
- 前后台命名转换
- 过滤



## JSON使用

### 前端

**前端接受后台传过来的json数据**

```js
//同步写法
btn.οnclick=function(){
    //jquery
    $.post("test.do",function(result){
        //得到json数据,将json字符串转为json对象
  		dates = JSON.parse(Result);
        //to do
    }
}
//异步写法：ajax
$.ajax({
   url: "test.do",
   type: "post",
   data: obj,
   contentType: 'application/json;charset=utf-8',
   success : function(data){
        //to do
   }
}); 
```

### Java后端

这里以springboot为例：默认json处理器为jackson-databind。Jackson-databind是SpringBoot默认集成在web依赖中的框架，因此我们只需要引入`spring-boot-starter-web`依赖，就可以返回json数据

**接收json**

```java
//方式1：使用@RequestBody
@RequestMapping("/person")
public Map<String, Object> getPerson(@RequestBody Person person) {
    Map<String, Object> param = new HashMap<>();
    String s = person.getPhones().toString();
    System.out.println(s);
    param.put("person", person);
    return param;
}
//方式2：通过Request获取
@ResponseBody
@RequestMapping(value = "/request/data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
public String getByRequest(HttpServletRequest request) {
  //获取到JSONObject
  JSONObject jsonParam = this.getJSONParam(request);
  // 将获取的json数据封装一层，然后在给返回
  JSONObject result = new JSONObject();
  result.put("msg", "ok");
  result.put("method", "request");
  result.put("data", jsonParam);
 
  return result.toJSONString();
}

public JSONObject getJSONParam(HttpServletRequest request){
  JSONObject jsonParam = null;
  try {
    // 获取输入流
    BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
 
    // 写入数据到Stringbuilder
    StringBuilder sb = new StringBuilder();
    String line = null;
    while ((line = streamReader.readLine()) != null) {
      sb.append(line);
    }
    jsonParam = JSONObject.parseObject(sb.toString());
    // 直接将json信息打印出来
    System.out.println(jsonParam.toJSONString());
  } catch (Exception e) {
    e.printStackTrace();
  }
  return jsonParam;
}
```

**返回json**：使用注解@ResponseBody返回json字符串

```
@Controller
@RequestMapping(value = "/good")
public class GoodController {

    @GetMapping(path = "/get")
    @ResponseBody
    public Good getGood(){
        Good good = new Good();
        good.setId(1);
        good.setName("MacBook Pro 2019");
        good.setPrice(16999.99);
        good.setDealDate(new Date());
        return good;
    }
}
```



























