# Javascript

**JS的三大组成部分**

- ECMAScript规定核心的语法
- DOM（document object model）:文档对象模型 
- BOM（browser object model）：浏览器对象模型



## 基础语法

### JS的数据类型 

#### 数据类型

- **值类型(基本类型)**：字符串（String）、数字(Number)、布尔(Boolean)、为空（Null）、未定义（Undefined）、Symbol。

- **引用数据类型**：对象(Object)、数组(Array)、函数(Function)。

  **注意事项：**

  - JS中不区分字符类型和字符串类型，内部会自动处理
  - null和undefined的区别：null表示变量被赋予空值，undefined表示变量还没被赋值
  - Symbol是ES6新添加的数据类型，它表示凡是属性名属于 Symbol 类型，就都是独一无二的，可以保证不会与其他属性名产生冲突
  - NaN属于数字类型，但是NaN不等于NaN；Infinity也属于数字类型，表示正无限大
  - 重复声明变量，变量的值不会丢失
  
- 使用typeof检测变量的数据类型

  清空对象可以设置为null，清除变量可以设置为undefined

  - 数组返回object
  - null返回object
  - undefined返回undefined
  - null == undefined为true；null===undefined为false

- 各种数据类型转换结果：https://www.runoob.com/js/js-type-conversion.html

#### 创建对象

```js
//方式1：字面量方式
var Student1 = {
    name: 'xiaofang',     // 对象中的属性
    age:  18,
    sex:  'male',
    sayHello: function () {
        console.log('hello,我是字面量对象中的方法');
    },
    doHomeword: function () {
        console.log("我正在做作业");
    }
};
console.log(Student1);
console.log(Student1.name);
Student1.sayHello();

//方式2：工厂模式
function createStudent(name, age, sex) {
    var Student = new Object();
    Student.name = name;
    Student.age  = age;
    Student.sex  = sex;
    Student.sayHello = function () {
        console.log("hello, 我是工厂模式创建的对象中的方法");
    }
    return Student;
}
var student2 = createStudent('小红', 19, 'female');
console.log(student2);
console.log(student2.name);
student2.sayHello();

//方式3：构造器模式
function Student (name, age, sex) {
    this.name = name;
    this.age = age;
    this.sex = sex;
    this.sayHello = function () {
        console.log("hello, 我是利用构造函数创建的对象中的方法");
    }
}
var student3 = new Student('小明', 20, 'male');
console.log(student3);
console.log(student3.name);
student3.sayHello();
```

#### 创建变量

**声明变量和不声明变量的区别**

```js
//1、全局变量（函数外的情况）
//创建声明的全局变量，不可以删除，表示不可配置属性
var var1 = 1;
delete var1 	//false：不可以删除
//创建不声明的全局变量，可以删除，可以配置
var2 = 2;
delete var2;	//成功

//2、局部变量（函数内的情况）
function test(){
    //声明的局部变量，表示声明一个私有变量
    var var1 = 1;
    
    //不声明的局部变量，处理机制为：
    //第一步：沿作用域链向上查找该变量，是哪个上下文中声明的变量，就改变哪个上下文中的变量
    //第二步：如果所有上下文中都没有该变量，则给全局对象 window，添加一个同名属性
    var2 = 2;
}
```

**变量提升**

- **函数及变量的声明都将被提升到函数的最顶部**
- 变量可以在使用后声明，也就是变量可以先使用再声明
  - **声明和初始化是不一样的，初始化不会进行提升**，这意味着你可以在使用后面定义变量，但必须在使用之前对变量进行赋值



**和变量对应的概念是作用域**

- 在函数内部创建的变量为局部变量，作用域为函数内部
- 在函数外部创建的变量为全局变量，网页中所有脚本和函数均可使用
- 局部变量在函数执行完毕后销毁；全局变量在页面关闭后销毁。

**！！！在js中只有全局作用域和局部作用域，所以在函数外面进行循环时，循环变量属于全局作用域，在循环体外就能够使用。如果要进行循环，循环变量不要使用var，使用`let`进行声明可以让该变量只在当前代码块起作用**



### JS保留字

| 保留字   |            |            |              |
| -------- | ---------- | ---------- | ------------ |
| abstract | else       | instanceof | super        |
| boolean  | enum       | int        | switch       |
| break    | export     | interface  | synchronized |
| byte     | extends    | let        | this         |
| case     | false      | long       | throw        |
| catch    | final      | native     | throws       |
| char     | finally    | new        | transient    |
| class    | float      | null       | true         |
| const    | for        | package    | try          |
| continue | function   | private    | typeof       |
| debugger | goto       | protected  | var          |
| default  | if         | public     | void         |
| delete   | implements | return     | volatile     |
| do       | import     | short      | while        |
| double   | in         | static     | with         |

### JS字符串

```js
//以下字符串无法正确解析
"We are the so-called "Vikings" from the north."
//处理方式1
"We are the so-called \"Vikings\" from the north."
//处理方式2
"We are the so-called 'Vikings' from the north."
```

- 尽量不要创建字符串对象，会拖慢执行速度，还可能产生其他副作用
- ==判断两个变量值是否相等，===判断两个变量值和数据类型是否都相等

**和字符串相关的计算操作**

- 字符串+字符串=字符串
- 数字+数字=数字
- 数字+字符串=字符串



**字符串的相关方法**

| 方法                | 描述                                                         |
| :------------------ | :----------------------------------------------------------- |
| charAt()            | 返回指定索引位置的字符                                       |
| charCodeAt()        | 返回指定索引位置字符的 Unicode 值                            |
| concat()            | 连接两个或多个字符串，返回连接后的字符串                     |
| fromCharCode()      | 将 Unicode 转换为字符串                                      |
| indexOf()           | 返回字符串中检索指定字符第一次出现的位置                     |
| lastIndexOf()       | 返回字符串中检索指定字符最后一次出现的位置                   |
| localeCompare()     | 用本地特定的顺序来比较两个字符串                             |
| match()             | 找到一个或多个正则表达式的匹配                               |
| replace()           | 替换与正则表达式匹配的子串                                   |
| search()            | 检索与正则表达式相匹配的值                                   |
| slice()             | 提取字符串的片断，并在新的字符串中返回被提取的部分           |
| split()             | 把字符串分割为子字符串数组                                   |
| substr()            | 从起始索引号提取字符串中指定数目的字符                       |
| substring()         | 提取字符串中两个指定的索引号之间的字符                       |
| toLocaleLowerCase() | 根据主机的语言环境把字符串转换为小写，只有几种语言（如土耳其语）具有地方特有的大小写映射 |
| toLocaleUpperCase() | 根据主机的语言环境把字符串转换为大写，只有几种语言（如土耳其语）具有地方特有的大小写映射 |
| toLowerCase()       | 把字符串转换为小写                                           |
| toString()          | 返回字符串对象值                                             |
| toUpperCase()       | 把字符串转换为大写                                           |
| trim()              | 移除字符串首尾空白                                           |
| valueOf()           | 返回某个字符串对象的原始值                                   |



### 正则表达式

**语法**：`/正则表达式主体/修饰符(可选)`

- 修饰符

  - i：忽略大小写
  - g：全局匹配
  - m：多行匹配

- 表达式主体

  - | 表达式 | 描述                       |
    | :----- | :------------------------- |
    | [abc]  | 查找方括号之间的任何字符。 |
    | [0-9]  | 查找任何从 0 至 9 的数字。 |
    | (x\|y) | 查找任何以 \| 分隔的选项。 |

  - | 元字符 | 描述                                        |
    | :----- | :------------------------------------------ |
    | \d     | 查找数字。                                  |
    | \s     | 查找空白字符。                              |
    | \b     | 匹配单词边界。                              |
    | \uxxxx | 查找以十六进制数 xxxx 规定的 Unicode 字符。 |

  - | 量词 | 描述                                  |
    | :--- | :------------------------------------ |
    | n+   | 匹配任何包含至少一个 *n* 的字符串。   |
    | n*   | 匹配任何包含零个或多个 *n* 的字符串。 |
    | n?   | 匹配任何包含零个或一个 *n* 的字符串。 |

**使用方式**

```js
//search：返回字符串中符合正则表达式的起始位置，找不到返回-1
var str = "Visit Runoob!"; 
var n = str.search(/Runoob/i);	//返回6

//replace：返回被替换后的字符串
var str = "123index123index";
var n = str.replace(/index/g,"god");//返回123god123god

//使用RegExp对象
var patt = /best/;
patt.test("The best things in life are free!");	//返回true
patt.exec("The best things in life are free!"); //返回best
```



### this、let、const关键字

this用于指向对应对象；let用于定义变量；const用于定义常量

#### this

- 在方法中，this 表示该方法所属的对象。

- 如果单独使用，this 表示全局对象。

- 在函数中，this 表示全局对象。

- 在函数中，在严格模式下，this 是未定义的(undefined)。

- **在事件中，this 表示接收事件的元素。**

- 类似 call() 和 apply() 方法可以将 this 引用到任何对象。

  - ```js
    var person1 = {
      fullName: function() {
        return this.firstName + " " + this.lastName;
      }
    }
    var person2 = {
      firstName:"John",
      lastName: "Doe",
    }
    person1.fullName.call(person2);  // 返回 "John Doe"
    ```

#### let与var的比较

- 使用 var 关键字声明的变量不具备块级作用域的特性，它在 {} 外依然能被访问到。

- let 声明的变量只在 let 命令所在的代码块 **{}** 内有效，在 **{}** 之外不能访问。

  - ```js
    var x = 10;
    // 这里输出 x 为 10
    { 
        var x = 2;
        // 这里输出 x 为 2
    }
    // 这里输出 x 为 2
    
    var x = 10;
    // 这里输出 x 为 10
    { 
        let x = 2;
        // 这里输出 x 为 2
    }
    // 这里输出 x 为 10
    ```

    

- 在函数体外或代码块外使用 **var** 和 **let** 关键字声明的变量，它们的作用域都是 **全局的**

  - 使用 **var** 关键字声明的全局作用域变量属于 window 对象；使用 **let** 关键字声明的全局作用域变量不属于 window 对象
  - **重置变量**
    - 使用 **var** 关键字声明的变量在任何地方都可以修改；
    - 在相同的作用域或块级作用域中，不能使用 **let** 关键字来重置 **var** 关键字声明的变量；
    - 在相同的作用域或块级作用域中，不能使用 **let** 关键字来重置 **let** 关键字声明的变量；
    - 在相同的作用域或块级作用域中，不能使用 **var** 关键字来重置 **let** 关键字声明的变量
    - **let** 关键字在不同作用域，或不同块级作用域中是可以重新声明赋值的
  - **变量提升**：var 关键字定义的变量可以在使用后声明，也就是变量可以先使用再声明；let 关键字定义的变量则不可以在使用后声明

#### const

- const 用于声明一个或多个常量，声明时必须进行初始化，且初始化后值不可再修改

- `const`定义常量与使用`let` 定义的变量相似：

  - 二者都是块级作用域
  - 都不能和它所在作用域内的其他变量或函数拥有相同的名称

  两者还有以下两点区别：

  - `const`声明的常量必须初始化，而`let`声明的变量不用
  - const 定义常量的值不能通过再赋值修改，也不能再次声明。而 let 定义的变量值可以修改。

- const 的本质: const 定义的变量并非常量，并非不可变，它定义了一个常量引用一个值。使用 const 定义的对象或者数组，其实是可变的。

### JS同步、异步与回调

**promise函数与异步函数**

```js
function print(delay, message) {
    return new Promise(function (resolve, reject) {
        setTimeout(function () {
            console.log(message);
            resolve();
        }, delay);
    });
}
//方式1
print(1000, "First").then(function () {
    return print(4000, "Second");
}).then(function () {
    print(3000, "Third");
});
//方式2
async function asyncFunc() {
    await print(1000, "First");
    await print(4000, "Second");
    await print(3000, "Third");
}
asyncFunc();
```



### 闭包

使用场景：**在函数中，使用var定义的变量属于局部变量**，在函数外不可以访问。变量声明时如果不使用 **var** 关键字，那么它就是一个全局变量，即便它在函数内定义。为了避免函数内变量对全局变量造成污染，使用闭包的方式解决此类问题：

-  闭包是一种保护私有变量的机制，在函数执行时形成私有的作用域，保护里面的私有变量不受外界干扰。直观的说就是形成一个不销毁的栈环境。

```js
var add = (function () {
    var counter = 0;
    return function () {return counter += 1;}
})();
// 计数器为 3
add();
add();
add();
 
//这样做的好处是：可以达到计数的效果，而且变量不会污染外部环境


//注意事项：
var add = function () {
    var counter = 0;
    return function () {return counter += 1;}
};
add();	//此时输出 function () {return counter += 1;}
//原因分析：【上面的add本质上是一个执行函数的过程】上面的方式会对add进行变量提升，在真正使用add()时才会执行函数；【下面的add本质上是一个函数】当调用add()时会返回一个函数，而不是执行这个函数

```









## DOM（文档对象模型）

通过 HTML DOM，可访问 JavaScript HTML 文档的所有元素。

通过可编程的对象模型，JavaScript 获得了足够的能力来创建动态的 HTML。

- JavaScript 能够改变页面中的所有 HTML 元素
- JavaScript 能够改变页面中的所有 HTML 属性
- JavaScript 能够改变页面中的所有 CSS 样式
- JavaScript 能够对页面中的所有事件做出反应



### 功能一：查找元素

通过标签名、类名、id名等方式查找元素

常见方法：

- document.getElementById
- document.getElementsByClassName
- document.getElementsByTagName



### 功能二：改变元素

#### 改变HTML输出流

在 JavaScript 中，`document.write() `可用于直接向 HTML 输出流写内容。

#### 改变 HTML 内容

修改 HTML 内容的最简单的方法是使用` innerHTML `属性。

向文档中添加和移除节点：

```js
//创建p标签
var para = document.createElement("p");
//创建文本节点
var node = document.createTextNode("这是一个新的段落。");
//将文本节点加到p标签中
para.appendChild(node);
```

- insertBefore
- removeChild
- replaceChild
- 。。。

#### 改变 HTML 属性

如需改变 HTML 元素的属性，请使用：`document.getElementById(id).attribute=新属性值`

#### 改变HTML样式

- 直接修改样式

  ```js
  document.getElementById(id).style.property=新样式
  div.style.cssText='width:600px;';
  ```

- 修改class

  ```js
  document.getElementsByTagName('body')[0].className = 'snow-container'; //设置为新的
  document.getElementsByTagName('body')[0].className += 'snow-container'; //在原来的后面加这个
  document.getElementsByTagName('body')[0].classList.add("snow-container"); //与第一个等价
  ```



#### JS事件

##### 调用事件的方式

- 链接的`href`：单独连接到某个地址
  - href只能get参数（采用get方式请求），一般用于单个连接，可以带参数（URL重写），在地址栏中可以看到所有的参数
  - 使用细节
    - 死链接：`href="javascript:void(0)"`
    - 普通空链接：`href="#"`
- 表单的`action`
  - action能get参数又能post参数（可采用两种方式提交），action一样用于表单的提交（如：注册）等，他可以提交大量和比较复杂的参数。如果选择post方式 则在地址栏中看不到提交的信息。
- 鼠标事件、键盘事件、表单事件

**在href中调用js方法**

1. a href=”javascript:js_method();”

   这是我们平台上常用的方法，但是这种方法在传递this等参数的时候很容易出问题，而且javascript:协议作为a的href属性的时候不仅会导致不必要的触发window.onbeforeunload事件，在IE里面更会使gif动画图片停止播放。W3C标准不推荐在href里面执行javascript语句。

2. **a href=”javascript:void(0);” onclick=”js_method()”**

   这种方法是很多网站最常用的方法，也是最周全的方法，onclick方法负责执行js函数，而void是一个操作符，void(0)返回undefined，地址不发生跳转。而且这种方法不会像第一种方法一样直接将js方法暴露在浏览器的状态栏。

3. a href=”javascript:;” onclick=”js_method()”

   这种方法跟跟2种类似，区别只是执行了一条空的js代码。

4. a href=”#” onclick=”js_method()”

   这种方法也是网上很常见的代码，#是标签内置的一个方法，代表top的作用。所以用这种方法点击后网页后返回到页面的最顶端。

5. a href=”#” onclick=”js_method();return false;”

   这种方法点击执行了js函数后return false，页面不发生跳转，执行后还是在页面的当前位置。



taobao的主页，他们采用的是第2种方法，而alibaba的主页是采用的第1种方法，和我们的区别是每个href里的javascript方法都用try、catch包围。

综合上述，在a中调用js函数最适当的方法推荐使用： 
a href=”javascript:void(0);” onclick=”js_method()” 
a href=”javascript:;” onclick=”js_method()” 
a href=”#” onclick=”js_method();return false;”



**get和post区别**

- a标签使用get提交，form标签使用get和post提交
- form表单可以给后台传递数据，在后台直接可以用request对象去向前台请求数据。
- form表单传递数据有两种方式：
  - method="post":这是传递大量数据时用的，在数据传递之前会先将数据打包，因此这种传递数据的方式会效率会比较慢，但是穿过的数据都能正确解析，因此传中文不会有乱码。
  - method="get"：以URL传递的，因为地址栏长度有限，所以对数据量是有限制的，而且传递的数据必须是ASCCI码值范围内的，因此，传中文会有乱码，需特殊处理。



##### **常见事件**

**鼠标事件**

| 鼠标事件    | 描述                         |
| :---------- | :--------------------------- |
| onclick     | 鼠标单击 HTML 元素           |
| ondblclick  | 鼠标双击时触发               |
| onmouseover | 用户在一个HTML元素上移动鼠标 |
| onmouseout  | 用户从一个HTML元素上移开鼠标 |
| onmousemove | 鼠标移动时触发               |

**键盘事件**

| 键盘事件   | 描述                   |
| ---------- | ---------------------- |
| onkeydown  | 键盘按下时触发         |
| onkeyup    | 从键盘按键抬起是触发   |
| onkeypress | 按下键盘上的键以后触发 |

**表单事件**

| 表单事件 | 描述                                                         |
| -------- | ------------------------------------------------------------ |
| onblur   | 失去焦点触发，即光标不在该文本框内时触发，常会用这种方式验证用户名是否存在 |
| onfocus  | 获得焦点触发                                                 |
| onchange | 内容变化触发                                                 |

##### 监听器

监听器的作用：设置监听事件

语法：`element.addEventListener/removeEventListener(event, function, useCapture);`

- 第一个参数是事件的类型 (如 "click" 或 "mousedown")【不要使用 "on" 前缀】
- 第二个参数是事件触发后调用的函数。
- 第三个参数是个布尔值用于描述事件是冒泡还是捕获。该参数是可选的。
  - 默认值为 false, 即冒泡传递，当值为 true 时, 事件使用捕获传递。

注意：

- 一个元素可以有多个监听事件
- 一个元素可以有多个相同触发条件的事件



##### 事件冒泡与事件捕获与事件委托？？？

事件传递有两种方式：冒泡与捕获。

事件传递定义了元素事件触发的顺序。 如果你将 <p> 元素插入到 <div> 元素中，用户点击 <p> 元素, 哪个元素的 "click" 事件先被触发呢？

- 在 *冒泡* 中，内部元素的事件会先被触发，然后再触发外部元素，即： <p> 元素的点击事件先触发，然后会触发 <div> 元素的点击事件。
- 在 *捕获* 中，外部元素的事件会先被触发，然后才会触发内部元素的事件，即： <div> 元素的点击事件先触发 ，然后再触发 <p> 元素的点击事件。









## BOM（浏览器对象模型）

### Window对象

在使用window对象可以不加window前缀

- screen
- location
- history
- navigator









## JS面向对象

### 创建对象



### 原型prototype



### JS内置对象

- Number
- String
- Date
- Array
- Boolean
- Math
- RegExp





## JS中的问题

变量作用域问题











































































变量：变量定义、作用域、变量提升、this的范围



面向对象













JS模块化：实现模块化需要进行暴露接口供外部使用

- 服务器端模块化
  - commonJS：node、browserify
- 客户端模块化
  - AMD：requireJS
  - CMD：SeaJS，阿里使用，市场很少用
  - ES6：babel将es6的实现转为es5的代码

