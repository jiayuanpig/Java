# Ajax



## Ajax介绍

**A**synchronous **J**avaScript **A**nd **X**ML，异步JS和XML：在不重新加载整个页面的情况下，与服务器交换数据并更新部分网页的技术（在等待服务器响应时执行其他脚本，当响应就绪后对响应进行处理）

- 同步交互:指一个时间段内只能有一个进程在执行，当发送一个请求时，必须等待结果的返回，才能发送下一请求。

- 异步交互:指一个时间段内可以有多个进程在执行，即不需要等待返回，随时可以发送下一个请求。

Ajax可以使网页异步更新，这意味着可以在不重新加载整个网页的情况下，对网页的某部分进行更新。而传统的网页不使用AJAX,如果需要更新，需要重载整个页面。这就是AJAX最大的好处，用户体验好，无刷新。



**Ajax优缺点**

优点

- 异步模式，提升用户体验
- 优化浏览器及服务器之间的传输，减少不必要的数据往返，减少带宽占用
- AJax引擎在客户端运行，承担了部分本来由服务器承担的工作，从而减少大用户量下的服务器负载

缺点

- 不支持浏览器back按钮
- 安全问题：暴露了与服务器交互的细节
- 对搜索引擎的支持比较弱
- 破坏了程序的异常机制
- 不易调试



**Ajax使用场景**

- 页面内容改变时
- 某异步函数被调用时





## Ajax用法

### 原生js实现ajax

**步骤：**

①创建ajax对象 var xhr=new XMLHttpRequest()

②建立一个http连接 xhr.open('get',url,true);

③发送一个http请求 xhr.send(null);

④给ajax状态绑定一个回调函数 xhr.onreadystatechange=function(){};

⑤判断ajax的状态是否等于4和xhr.readyState==4 ，就做相应的业务逻辑接收字符串(xhr.responseText)或者XML(xhr.responseXML)。



```js
//1、XMLHttpRequest对象：用于后台与服务器交换数据，使之可用JS向服务器发起请求并处理响应而不阻塞用户，进行页面的局部更新
//兼容性写法
var xmlhttp;
if (window.XMLHttpRequest){
    xmlhttp = new XMLHttpRequest();
}else{
    xmlhttp = new ActiveObject('Microsoft.XMLHTTP')
}

//2、绑定响应函数
xmlhttp.onreadystatechange = function(){
    //状态成功时执行相关操作
    if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
        documemt.getElementById('myDiv').innerHTML = xmlhttp.respenseText;
    }
}

//3、向服务器发送请求
//当async=true时，应在响应处于onreadystatechange事件中的就绪状态时执行函数
//当async=false时，JS会等到服务器响应完毕再继续执行，若服务器繁忙或缓慢，应用程序会挂起或停止（此时无需编写onreadystatechange函数）
xmlhttp.open('GET', 'test1.txt', true);	// true表示异步，请求类型为get，文件在服务器上的位置为test1.txt
xmlhttp.send();	//send(string)仅用于post请求

```



**请求方式的选择**

> **使用post的情况**
>
> 可设置请求头，规定请求数据类型，比get更**安全**；**传输文件体积无限制**；常用于上传数据
>
> - 无法使用缓存文件（更新服务器上的文件或数据）
> - 向服务器发送大量数据（post没有限制）
> - 发送包含未知字符的用户输入，post比get更稳定也更可靠
> - 像表单一样post数据：**使用setRequestHeader()来添加HTTP头**，然后在**send方法中发送数据(请求体)**
>   xmlhttp.open('post', 'ajax_test.asp', 'true');
>   xmlhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
>   xmlhttp.send('fname=Bill&name=Gates');
>
> **使用get**
>
> 通过URL传递参数，相对不安全，但传输速度**快**；传输文件小；常用于获取数据
>
> - 为避免得到缓存的结果，可向URL中添加一个唯一的ID
>   xmlhttp.open('get', 'demo_get.asp?t=' + Math.random(), true);
>   xmlhttp.send();
> - 通过get方法发送信息，可向URL添加信息
>   xmlhttp.open('get', 'demo_get2.asp?fname=Bill&name=Gates', true);
>   xmlhttp.send();
>
> **使用delete**
>
> **使用put**

**onreadystatechange事件**

每当readyState改变时，就会触发onreadystatechange事件。onreadystatechange储存函数，每当readyState属性改变时调用。

readyState：存XHR的状态

- 0：请求未初始化
- 1：请求连接已建立
- 2：请求已接受
- 3：请求处理中
- 4：请求已完成，响应就绪

status：HTTP状态码（200：OK；404：未找到页面）

- `1xx`（临时响应）：表示临时响应并需要请求者继续执行操作
- `2xx`（成功）：表示成果处理了请求
- `3xx`（重定向）：表示要完成请求需要进一步操作，通常用于重定向
- `4xx`（请求错误）：表示可能出错，妨碍了服务器的处理
- `5xx`（服务器错误）：表示服务器在尝试处理请求时发生内部错误，这些错误可能是服务器本身的错误，而不是请求出错

**封装**

```js
function ajax(url, fnSucc, fnFail){
    var xmlhttp;
    
    if(window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest();
    }else{
        xmlhttp = new ActiveObject('Microsoft.XMLHTP');
    }
    
    xmlhttp.onreadystatechange = function(){
        if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
            fnSucc(xmlhttp.responseText)
        }else{
            if(fnFail){
                fnFail(xmlhttp.responseText)
            }
        }
    }
    
    xmlhttp.open('GET', url)
    xmlhttp.send()
}
```



### Jquery封装ajax

`jquery.ajax([settings])`，参数：

- type:类型，"POST"或者"GET"，默认是"GET"。
- url:发送请求的地址。
- data：是一个对象，连同请求发送到服务器的数据
- dataType：预期服务器返回的数据类型。如果不指定，jQuery将自动根据HTTP包含的MIME信息来智能判断，一般我们采用json个数，可以设置为"json"。
- success:是一个方法，请求成功后的回调函数。传入返回后的数据，以及包含成功代码的字符串。
- error:是一个方法、请求失败时调用此函数。传入XMLHttpRequest对象。



```js
$("button").click(function(){
    $.ajax({
        url:"test.do?id=test",
        type:"GET",
        contentType : "application/json; charset=utf-8",
        data:"",
        dataType : "json",
        async : false,
        success:function(data){
        	//to do
    	},
        error:function(jqXHR){
            console.log("Error: "+jqXHR.status);
        }
    });
});
```



**url传参和data传参的区别**

- 使用get请求，传参放在url中；使用post请求，传参放在data中
- jquery不对url的query参数进行utf8编码；jquery会对data参数进行utf8编码。

因此，使用get方式可能会造成乱码情况，解决方式：

```js
前台-------------------------------------------------------
var searchText = "英语";
//方式1：通过URL传递：需要编码两次
searchText = encodeURI(searchText);
searchText = encodeURI(searchText);
$.ajax({
    type: 'GET',
    url: 'search.action' + "?searchText=" + searchText,
    data: '',
    contentType: 'text/json,charset=utf-8',
    dataType: 'json',
    success: function (data) {
    }
});
//方式2：通过ajax数据传递：只需编码一次~
searchText = encodeURI(searchText);
$.ajax({
    type: 'GET',
    url: 'search.action',
    data: {searchText:searchText},
    contentType: 'text/json,charset=utf-8',
    dataType: 'json',
    success: function (data) {
    }
});

后台----------------------------------------------------
//后端接收json数据
data=URLDecoder.decode(data,"utf-8");
//后端返回json数据
//方式1：只需对中文的内容进行编码，如果对整个json字符串进行编码，json中的“”等字符也会被编码导致前端解析json错误
URLEncoder.encode("中文内容","utf-8");
//方式2：使用json解析工具类
//略

```

 

 

 

 

 

 

 

 

  

 

 

 
