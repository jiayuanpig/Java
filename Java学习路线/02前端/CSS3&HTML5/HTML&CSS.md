# HTML&CSS



## HTML

### 行级元素和块级元素

#### 具体样式

**【注意】p标签是文本级标签，p里面只能放文字&图片&表单元素，p里面不能放h和ul，p里面也不能放p。但是p可以独占一行**

**行内元素：**

```html
<a>标签可定义锚
<abbr>表示一个缩写形式
<acronym>定义只取首字母缩写
<b>字体加粗
<bdo>可覆盖默认的文本方向
<big>大号字体加粗
<br>换行
<cite>引用进行定义
<code>定义计算机代码文本
<dfn>定义一个定义项目
<em>定义为强调的内容
<i>斜体文本效果
<img>向网页中嵌入一幅图像
<input>输入框
<kbd>定义键盘文本
<label>标签为
<input> 元素定义标注（标记）
<q>定义短的引用
<samp>定义样本文本
<select>创建单选或多选菜单
<small>呈现小号字体效果
<span>组合文档中的行内元素
<strong>语气更强的强调的内容
<sub>定义下标文本
<sup>定义上标文本
<textarea>多行的文本输入控件
<tt>打字机或者等宽的文本效果
<var>定义变量
```

**块级元素：**

```html
<address>定义地址
<caption>定义表格标题
<dd>定义列表中定义条目
<div>定义文档中的分区或节
<dl>定义列表
<dt>定义列表中的项目
<fieldset>定义一个框架集
<form>创建 HTML 表单
<h1>定义最大的标题
<h2>定义副标题
<h3>定义标题
<h4>定义标题
<h5>定义标题
<h6>定义最小的标题
<hr>创建一条水平线
<legend>元素为 
<fieldset>元素定义标题
<li>标签定义列表项目
<noframes>为那些不支持框架的浏览器显示文本，于 frameset 元素内部
<noscript>定义在脚本未被执行时的替代内容<ol>定义有序列表
<ul>定义无序列表
<p>标签定义段落
<pre>定义预格式化的文本
<table>标签定义 HTML 表格
<tbody>标签表格主体（正文）
<td>表格中的标准单元格
<tfoot>定义表格的页脚（脚注或表注）
<th>定义表头单元格
<thead>标签定义表格的表头
<tr>定义表格中的行
```

#### 行级元素和块级元素区别

1. 行内元素与块级元素直观上的区别

   行内元素会在一条直线上排列，都是同一行的，水平方向排列

   **块级元素各占据一行，垂直方向排列**。块级元素从新行开始结束接着一个换行。

2. 块级元素可以包含行内元素和块级元素。行内元素不能包含块级元素。

3. 行内元素与块级元素属性的不同，主要是盒模型属性上

   **行内元素设置width无效，height无效**(可以设置line-height)，margin上下无效，padding上下无效

#### 行级元素和块级元素的相互转换

display（inline、inline-block、block）

- 把块级元素转换成行内元素：display:inline;
- 将行内元素转换成块级元素:display:block;
- 行内块元素：display:inline-block;（**行内块元素：可以设置宽高，不独占一行，多个行内块元素左右排列，一行显示不下会自动换行**）【行内块默认底部对齐】



## CSS

### CSS选择器

**常用选择器类型**：派生选择器、id选择器、类选择器、属性选择器、元素选择器、后代选择器、子元素选择器、相邻兄弟选择器、伪类、伪元素

#### CSS2选择器

```html
基本选择器：
id选择器：#idName{}
类选择器：.className{}
标签选择器：div{}
通用元素选择器：*{}

组合选择器：
E,F{}：多元素选择器，同时匹配所有E元素或F元素，E和F之间用逗号分隔
E F{}：后代元素选择器，匹配所有属于E元素后代的F元素【E和F之间用空格分隔】
E > F{}：子元素选择器，匹配所有E元素的子元素F
E + F{}：毗邻元素选择器，匹配所有紧随E元素之后的同级元素F


属性选择器：
E[att]：匹配所有具有att属性的E元素，不考虑它的值。（注意：E在此处可以省略，比如"[cheacked]"）
E[att=val]：匹配所有att属性等于"val"的E元素
E[att~=val]：匹配所有att属性具有多个空格分隔的值、其中一个值等于"val"的E元素
E[att|=val]：匹配所有att属性具有多个连字号分隔（hyphen-separated）的值、其中一个值以"val"开头的E元素，主要用于lang属性，比如"en"、"en-us"、"en-gb"等等

伪类选择器：
E:first-child	匹配父元素的第一个子元素
E:link			匹配所有未被点击的链接
E:visited		匹配所有已被点击的链接
E:active		匹配鼠标已经其上按下、还没有释放的E元素
E:hover			匹配鼠标悬停其上的E元素
E:focus			匹配获得当前焦点的E元素
E:lang(c)		匹配lang属性等于c的E元素

伪元素：
E:first-line	匹配E元素的第一行
E:first-letter	匹配E元素的第一个字母
E:before		在E元素之前插入生成的内容
E:after			在E元素之后插入生成的内容
```

#### CSS3选择器

```
同级元素通用选择器：
E ~ F	匹配任何在E元素之后的同级F元素

属性选择器：
E[att^="val"]	属性att的值以"val"开头的元素
E[att$="val"]	属性att的值以"val"结尾的元素
E[att*="val"]	属性att的值包含"val"字符串的元素

与用户界面有关的伪类：
E:enabled	匹配表单中激活的元素
E:disabled	匹配表单中禁用的元素
E:checked	匹配表单中被选中的radio（单选框）或checkbox（复选框）元素
E::selection	匹配用户当前选中的元素

反选伪类：
E:not(s)	匹配不符合当前选择器的任何元素
```

#### 选择器优先级

> !important > 行内样式 > ID选择器 > 类选择器 > 标签 > 通配符 > 继承 > 浏览器默认属性
>
> **优先级计算算法：**
>
> CSS优先级：是由四个级别和各级别出现次数决定的
> 四个级别：行内样式，ID选择器，Class选择器，标签
>
> > 每个规则对应一个初始值0,0,0,0
> > 若是 行内选择符，则加1、0、0、0
> > 若是 ID选择符，则加0、1、0、0
> > 若是 类选择符/属性选择符/伪类选择符，则分别加0、0、1、0
> > 若是 元素选择符/伪元素选择符，则分别加0、0、0、1
>
> 算法：将每条规则中，选择符对应的数相加后得到的”四位数“，从左到右进行比较，大的优先级越高。



### CSS伪类与伪元素

**伪类**【格式：`:伪类体`】：存在DOM文档中，逻辑上存在但在文档树中却无须标识的“幽灵”分类。一是用来表示对应元素的不同状态；二是表示满足一定逻辑条件的DOM元素

- 元素状态：:active、:hover、:link、:visited、:focus、:checked、:lang等

- DOM元素：:first-child、:first-of-type等

  p:first-child：表示选择的元素既要是p标签，又要是其父元素的第一个子元素，不要错认为是表示p元素的第一个子元素；
  p:first-of-type：表示选择的元素既要是p标签，又要是其父元素的第一个p标签元素。

**伪元素**【格式：`::伪元素体`】：伪元素为DOM树没有定义的虚拟元素。不同于其他选择器，它不以元素为最小选择单元，它选择的是元素指定内容。

- ::after、::before、::selection、::first-letter、::first-line

**伪类和伪元素的根本区别在于：它们是否创造了新的元素**



### CSS样式

**样式位置分为：外部样式、内部样式、内联样式**

**重点样式**：背景、文本、字体、链接、列表、表格、轮廓



#### 文本样式(text)

1. color:设置文本颜色
2. text-align:设置文本对齐方式
   - center
   - left
   - right
3. letter-spacing:字间距/字母间距
4. word-spacing:单词间距,有空格就设置,中文看空格.
5. line-height:行高【第二行的行高=第一行底部到第三行顶部】
6. text-indent:首行文本缩进【一般30px就2个】
7. text-decoration:文本装饰
   - underline:下划线
   - overline:上划线
   - line-through:删除线
   - blink 闪烁
8. text-transform:大小写转换
   - uppercase:全部大写
   - lowercase:全部小写
   - capitalize:每个单词以大写开头
9. text-shadow:创建文本阴影(水平偏移量px,垂直偏移量px,模糊程度px,阴影颜色),模糊程度px值高越模糊.
10. **设置字体:**
    - font-style:字体样式【正常、斜体、倾斜】
    - font-size:设置字体的大小
    - font-weight:设置字体的粗细【关键字 100 ~ 900 为字体指定了 9 级加粗度，数字 400 等价于 normal，而 700 等价于 bold】
    - font-family:设置文字字体
    - font简写属性: style weight size family(空格)顺序必须按这个格式,一般用了简写属性之后把行间距letter-spacing放font属性后边,不然会覆盖.

#### 边框和背景

1. 为元素应用边框
   - border-width:边框宽度
   - border-style:边框样式
     none没有边框
     dashed虚线
     solied实线
     double双线
     groove槽线
     dotted圆点线边框
     inset内嵌 outset外凸
     ridge 脊线边框
   - border-color:边框颜色
   - 简写形式border:width style color 没有先后顺序
2. 单独设置某一条边框
   - border-top/bottom/left/right-width/style/color
   - 简写形式:border-top{width,style,color}
3. 应用圆角边框(x半径,y半径,xy相同时写一个就好了)
   - border-top/bottom-left/right-radius
   - 简写:border-radius
4. 设置元素背景
   - margin-right: 0px; margin-bottom: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; 
   - list-style: disc;
   - background-image:{url("")} 背景图片地址
   - background-repeat:背景图重复方式,一般no-repeat
   - background-size:背景图尺寸
   - background-position:背景图位置
   - 简写:background:color image repeat position,尺寸不写,会冲突
5. 创建盒子阴影
   - box-shadow:水平偏移量 垂直偏移量 模糊值 阴影的延伸半径 阴影颜色 10px 10px 5px 0px red;
   - 添加多个阴影,直接第一个之后用逗号继续写第二个,第一个设置成整数,第二个阴影设置成负数
6. 设置轮廓
   - 边框和轮廓的区别：轮廓不属于页面，不会因为应用轮廓而调整页面的布局
   - 边框占有实际大小,轮廓不占有
   - 当设置hover悬停时,轮廓修饰的div不会发生位移,边框修饰时位移
   - outline-color:轮廓颜色
   - outline-style:轮廓样式
   - outline-width:轮廓宽度
   - outline-offset:轮廓距元素边框偏移量
   - 简写: outline

#### 链接的四种状态

- a:link - 普通的、未被访问的链接
- a:visited - 用户已访问的链接
- a:hover - 鼠标指针位于链接的上方
- a:active - 链接被点击的时刻

#### 颜色

1. 颜色名：red
2. 16进制：#FF0000
3. rgb：rgb(255,255,255)

#### 单位

1. 像素：px
2. 百分比：%【可以对宽度width做设置,不可以对height做设置】
3. 相对值：em【相对所指的是相对于元素父元素的font-size】

#### 其他样式

1. 表格
   - border-collapse相邻单元格边框处理,合并表格=collapse
   - border-spacing:相邻单元格间距
   - caption-side:标题位置
   - empty-cells:空单元格是否显示
2. 列表
   - list-style-tyle:列表前边的标记样式 圆点,方点等等
   - list-style-image:列表图像标记
   - 简写:list-style:none 取消列表的所有属性 一般用于导航
   - 横向导航: li{display:inline}
3. 透明度
   - opacity:设置透明度(0-1之间取值)
4. 光标形状
   - cursor:设置光标形状,当光标放在某个div上边,设置等待状,手形状等等



### 属性简写

#### 字体font

**基本属性：**

- 字体样式：font-style                         	基本语法：font-style : normal | italic | oblique
- 设置文本是否为小型的大写字母：font-variant        基本语法：font-variant : normal | small-caps
- 设置字体的粗细： font-weight             基本语法：font-weight : normal | bold | bolder | lighter | 100 | 200 | 300 | 400 | 500 | 600 |   700 | 800 | 900
-  设置字体尺寸 ：font-size                     基本语法：font-size : xx-small | x-small | small | medium | large | x-large | xx-large | larger |smaller | length
- 设置文本的字体名称序列： font-family    基本语法：font-family : ncursive | fantasy | monospace | serif | sans-serif

**简写方式：**

font-style || font-variant || font-weight || font-size /line-height || font-family 



#### 背景background

**基本属性：**

- 背景颜色：background-color                    基本语法：background-color : transparent | color

- 背景图像：background-image                  基本语法：background-image : none | url ( url )

- 背景图像铺排：background-repeat          基本语法：background-repeat : repeat | no-repeat | repeat-x | repeat-y

- 背景图像滚动还是固定：background-attachment      基本语法：background-attachment : scroll | fixed

- 背景图像位置：background-position       基本语法：background-position : length || length或 background-position : position || position 

   length :　 百分数 | 由浮点数字和单位标识符组成的长度值。请参阅 长度单位

   position :　 top | center | bottom | left | center | right

**简写方式：**

background : background-color || background-image || background-repeat || background-attachment || background-position



#### 内外边距margin&padding

**简写方式**：margin:margin-top margin-right margin-bottom margin-left;



#### 边框border

**基本属性：**

边框宽度：border-weight        基本语法：border-width : medium | thin | thick | *length*

边框样式：border-style            基本语法：border-style : none | hidden | dotted | dashed | solid | double | groove | ridge |    inset | outset 

边框颜色： border-color          基本语法：border-width :color

**简写方式**：border : border-width || border-style || border-color





### 盒子模型

盒子模型（Box Model）就是把HTML页面中的元素看作是一个矩形的盒子，也就是一个盛装内容的容器。每个矩形都由元素的**内容(content)、内边距（padding）、边框（border）和外边距（margin）**组成。

#### 盒子模型尺寸计算

- 标准盒大小：宽高
- 外盒尺寸（所占空间）：宽高+内边距+边框+**外边距**
- 内盒尺寸（实际大小）：宽高+内边距+边框
- 使用 % 的有 content，padding，margin；边框 border 则不可以用 % 单位。
- margin可以为负值，padding不可以为负值
- **可以通过更改box-sizing改变浏览器计算盒子大小的方式**

#### 盒子模型的稳定性

按照 优先使用 宽度 （width） 其次 使用内边距（padding） 再次 外边距（margin）

　**原因：**

1. margin 会有外边距合并 还有 ie6下面margin 加倍的bug，所以最后使用。
2. padding 会影响盒子大小， 需要进行加减计算（麻烦） 其次使用。
3. width 没有问题，我们经常使用宽度剩余法 高度剩余法来做。



<img src="https://img2018.cnblogs.com/blog/1691302/201907/1691302-20190716102838609-184623210.jpg" alt="盒子模型" style="zoom: 67%;" />

#### 外边距合并问题

**外边距合并指的是，当两个垂直外边距相遇时，它们将形成一个外边距。合并后的外边距的高度等于两个发生合并的外边距的高度中的较大者。**

上下、左右和内外都会发生合并，甚至自身都会发生合并。参考：https://www.w3school.com.cn/css/css_margin_collapsing.asp

**只有普通文档流中块框的垂直外边距才会发生外边距合并。行内框、浮动框或绝对定位之间的外边距不会合并。**

#### 问题补充

> 1、margin与padding的%值，真的是继承父级width吗？
>
> ​    （1）未使用绝对定位（固定定位）的元素内外边距的%值都是继承父级盒子
> ​    （2）绝对定位（固定定位）则是根据其继承关系的定位元素，没有就是根据html元素为基准
>
> 2、一般情况下，margin：auto 为什么竖直方向不垂直居中?
>
> 块级元素，即便我们设置了宽度，他还是自己占一行，在css的规范中，元素他的左外边距+左边框宽度+左内边距+内容的宽度+右内边距+右边框+右外边距=包含块的宽度，如果我们给他的左右外边距设置为auto的时候，他会实现平分剩下的距离，从而达到一个水平居中的效果；
> 但是块级元素的高度并不会自动扩充，所以他的外部尺寸是不自动充满父元素的，也没有剩余的空间，因此margin上下设置auto不能实现垂直居中
>
> 3、margin: 0 auto可以水平居中，而padding: 0 auto不行呢？
>
> margin：auto可以做到水平居中，前提条件就是，这个标签是块状元素，并且有个确定的宽度，百分比的宽度也行；padding的话, 设置成auto它会自动继承浏览器的padding值， 当设置padding值为auto时，所有padding值都会变为0，因此一般都会以具体数值或者其他方法实现效果。



### 定位 position

静态定位、相对定位、绝对定位、固定定位、（粘性定位）

#### 四种定位比较

**静态定位（static）**

一般的标签元素不加任何定位属性都属于静态定位，在页面的最底层属于标准流。

**绝对定位（absolute）**

绝对定位的元素从文档流中拖出，使用left、right、top、bottom等属性相对于其最接近的一个最有定位设置的父级元素进行绝对定位，如果元素的父级没有设置定位属性，则依据 body 元素左上角作为参考进行定位。

绝对定位元素可层叠，层叠顺序可通过 z-index 属性控制，z-index值为无单位的整数，大的在上面，可以有负值。

> 绝对定位的定位方法：
>
> 如果它的父元素设置了除static之外的定位，比如position:relative或position:absolute及position:fixed，那么它就会相对于它的父元素来定位，位置通过left , top ,right ,bottom属性来规定。
>
> 如果它的父元素没有设置定位，那么就得看它父元素的父元素是否有设置定位，如果还是没有就继续向更高层的祖先元素类推，总之它的定位就是相对于设置了除static定位之外的定位的第一个祖先元素，如果所有的祖先元素都没有以上三种定位中的其中一种定位，那么它就会相对于文档body来定位（并非相对于浏览器窗口，相对于窗口定位的是fixed）。

​	**【注意】绝对定位会改变其他元素的布局，不会在此元素本来位置留下空白，其他元素会移动位置进行补齐**

**相对定位（relative）**

相对定位元素不可层叠，依据left、right、top、bottom等属性在正常文档流中偏移自身位置。同样可以用z-index分层设计。

​	**【注意】相对定位不会改变其他元素的布局，会在此元素本来位置留下空白**

**固定定位（fixed）**

固定定位与绝对定位类似，但它是相对于浏览器窗口定位，并且不会随着滚动条进行滚动。

固定定位的最常见的一种用途是在页面中创建一个固定头部、固定脚部或者固定侧边栏，不需使用margin、border、padding。

#### 应用场景

> **绝对定位vs相对定位**
>
> 设置了绝对定位的元素，在文档流中是不占据空间的，如果某元素设置了绝对定位，那么它在文档流中的位置会被删除，它浮了起来，后面的元素会对应补充缺失的位置。
>
> 设置了相对定位也会让元素浮起来，但它们的不同点在于，相对定位不会删除它本身在文档流中占据的空间，其他元素不可以占据该空间。
>
> **业务场景**
>
> 导航栏可以使用固定定位保证始终位于页面正上方



### 浮动 float

超级好的文章：https://www.cnblogs.com/qianguyihao/p/7297736.html

#### 标准文档流

> **标准文档流特性**
>
> - **空白折叠现象：**无论多少个空格、换行、tab，都会折叠为一个空格。比如，如果我们想让img标签之间没有空隙，必须紧密连接
> - **高矮不齐，底边对齐**
> - **自动换行，一行写不满，换行写。**
>
> 标准文档流元素分为：行级元素和块级元素，**行级元素不能设置宽高**。
>
> **脱离标准文档流的三种方式**
>
> - 浮动
> - 绝对定位
> - 相对定位

#### 左右浮动

- 浮动可以让两个块级元素左右并排
- 元素一旦设置浮动，就不再分为行内还是块级元素了，就都可以设置宽高了
- 浮动元素会出现“字围”的效果，**标准流中的文字不会被浮动的盒子遮挡住**
- **使用时应该注意，永远不是一个东西单独浮动，浮动都是一起浮动，要浮动，大家都浮动。**
- 一个浮动的元素，如果没有设置width，那么将**自动收缩为内容的宽度**（这点非常像行内元素）
- 浮动元素可以相互贴靠
  - <img src="http://img.smyhvae.com/20170730_1928.gif" alt="img" style="zoom:33%;" />
  - 分析：3号如果有足够空间，那么就会靠着2号。如果没有足够的空间，那么会靠着1号大哥。如果没有足够的空间靠着1号大哥，3号自己去贴左墙。注意，3号贴左墙的时候，并不会往1号里面挤。



#### 清除浮动的方式

- **方式一：给浮动元素的祖先元素添加高度**

  **如果一个元素要浮动，那么它的祖先元素一定要有高度。有高度的盒子，才能关住浮动**。

  需要注意的是，一般情况下父元素不能被浮动的子元素撑起来

  【注意：该方法不太建议使用，一方面需要增加高度，另一方面适应性不好】

- **方式二**：`clear:both;`

  尽量减少高度的定义，让高度由内容撑满

  `clear:both`的意思就是：**不允许左侧和右侧有浮动对象**

  【注意：使用该方式会导致margin失效，因为父元素的高度都为0】

- **方式三**：隔墙法，让两个浮动元素之间添加清除浮动的div，可以通过设置中间div的高度实现两个浮动元素上下间隔

- **方式四**：内墙法，假设浮动元素由外层div包裹，在div中添加一个清除浮动的子div，这时候父div可以被浮动元素撑起来

  【相较于外墙法，内墙法优点在于可以让父元素自适应浮动元素的高度，所以父元素有了高度就不会影响后面的内容】

- **方法五**：`overflow:hidden;`

  给浮动元素的父div设置该属性可以让父元素被浮动元素撑起来

  【该方式属于浏览器偏方，偶然发现的小功能】



#### 浮动造成的margin问题

**标准文档流中，竖直方向的margin不叠加，取较大的值作为margin【水平方向的margin是可以叠加的，即水平方向没有塌陷现象】**

标准流中，盒子居中的方式为`margin:0 auto;`，理解：上下的margin为0，左右的margin都尽可能的大，于是就居中了。

> 注意的问题：
>
> 只有标准流的盒子，才能使用`margin:0 auto;`居中。也就是说，当一个盒子浮动了、绝对定位了、固定定位了，都不能使用margin:0 auto;
>
> 使用`margin:0 auto;`的盒子，必须有width，有明确的width。（可以这样理解，如果没有明确的witdh，那么它的witdh就是霸占整行，没有意义）
>
> `margin:0 auto;`是让盒子居中，不是让盒子里的文本居中。文本的居中，要使用`text-align:center;`

**！！！margin这个属性，本质上描述的是兄弟和兄弟之间的距离； 最好不要用这个marign表达父子之间的距离。**

案例分析：

```html
<div>
	<p></p>
</div>

如果通过给儿子p一个margin-top:50px;的属性，让其与父亲保持50px的上边距。结果却看到了一个奇怪的现象：父亲和儿子都会有个margin-top=50px的现象。这是因为父div没有设置border属性

原因分析：父亲没有border属性，儿子的margin-top属性会撑起div所占的这一行，父元素也会掉下来
```



#### 浮动和定位的使用场景

原则：多用浮动，少用定位

**绝对定位元素盒是脱离了普通的文档流的，元素原本位置不会保留；相对定位则会保留原本位置；二者都通过left/right/top/bottom进行定位**

**浮动元素盒是没有脱离了普通的文档流的，但是原位置会被清空；浮动通过margin/padding进行定位**

定位有层级的概念，浮动没有

**1、标准流**
可以让盒子上下排列 或者 左右排列的

**2、浮动**
可以让多个块级元素一行显示，或者左右对齐盒子，浮动的盒子就是按照顺序左右排列

**3、定位**
定位最大的特点是有层叠的概念，就是可以让多个盒子 前后 叠压来显示。 但是每个盒子需要测量数值



### CSS对齐

#### 文字对齐

左右居中：`text-align:center`

上下居中：使用`line-height`和`height`相等

#### 盒子对齐

> **需要根据情况进行分析**
>
> 情景一：一个浏览器页面中，只有一个div模块，让其上下左右居中
>
> ```css
> { 
>   position:fixed;
>   left:0;
>   right:0;
>   top:0;
>   bottom:0;
>   margin:auto; 
> }
> position：fixed	定位是相对于整个浏览器页面的。
> ```
>
> 情景二：一个父元素div，一个已知宽度、高度的子元素div(200*300)
>
> 父元素使用相对定位，子元素使用相对或绝对定位，设置`top：50%;left:50%;`，margin-top和-left为自身的负一半。
>
> ```css
> {
>     position:absolute/fixed;
>     top:50%;
>     left:50%;
>     margin-left:-100px;
>     margin-top:-150px;
> }
> ```
>
> 情景三：一个父元素div，一个未知宽度、高度的子元素div
>
> ```css
> 解决方案：
> 1、position布局，position设为absolute，其他同情景一
> 
> 2、display：table
> 父级元素：{ display:table;}
> 子级元素： { display:table-cell;vertical-align:middle }
> 
> 3、flex布局
> 父级元素：{ display:flex;flex-direction:row;justify-content:center;align-items:center;}
> 子级元素：{flex:1}
> 
> 4、translate
> 子元素设置
> {
> position: absolute;
> top: 50%;
> left: 50%;
> -webkit-transform: translate(-50%, -50%);
> -moz-transform: translate(-50%, -50%);
> -ms-transform: translate(-50%, -50%);
> -o-transform: translate(-50%, -50%);
> transform: translate(-50%, -50%);
> }
> ```







