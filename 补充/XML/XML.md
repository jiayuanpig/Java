# XML 可扩展标记语言

xml 被用来设计结构化、存储、传输信息

涉及知识：
  - XML1.0  最底层的推荐标准，定义了XML解析器的规则
  - DTD     文档定义模型，对文档格式进行描述
  - XPath   XML路径语言，在XML中查找信息的语言，可以遍历文档元素和属性
  - XHTML   HTML的XML版
  - XLink   创建XML中超链接
  - XQuery  XML数据查询语言
  - XSD     XML schema 定义文档合法组件群类似DTD
  - DOM     文档对象模型，定义了访问XML文档的标准
  - ...

## 一、XML文档
### 1、XML文档构成

- 文档序言
    - 版本声明（不可以省略）
    - 编码声明（可以省略）
    - 是否为独立文档（可以省略）
- 文档主体
- 文档结尾

```xml简单例子

  <?xml version="1.0" encoding="utf-8" standalone="no" ?>
  <电影>
      <电影名>《警察故事》</电影名>
      <主演>成龙</主演>
      <类型>动作片</类型>
      <剧情>
          两个警察，陈警官（&quot;成龙饰演&quot;）&amp;一个忘记名字的警察
          发生了一系列的事情。
      </剧情>
      <转义字符>
          &quot;  双引号
          &apos;  单引号
          &amp;   和
          &lt;    小于号
          &gt;    大于号
          <!-- 不支持以下转义 -->
          <!-- &nbsp;  空格 -->
          <!-- &copy;  版权符 -->
          <!-- &reg;   注册符 -->
      </转义字符>
  </电影>

```


### 2、注释

格式： <!--这是注释的内容 -->  注释内容

遵循的规则：
  - 注释不能包含在标记中
  - 注释可以包含标记
  - XML声明必须在文档最前面的部分，XML注释在文档声明之后
  - 两个连字符（--）不能在注释中出现
  - 注释不能嵌套

### 3、XML处理指令

格式： <？target instructions ？>

XML声明也是一种处理指令

### 4、XML的元素和标记

**XML严格区分大小写**

4.1 标记

- 非空标记：<XX>XX</XX>
  - 开始与结束标记之间是元素的值
- 空标记：  <XX/>

4.2 元素特点:

- 定义规则
  - 第一个字符为26字符\非拉丁字符\下划线
  - 名字里不能有空格\冒号
  - 其他字符可以是数字\空格\句点
- 特性
  - 可扩展性
  - 关联性: 树形结构
  - 多样性
- 属性
  - name\value
  - 属性不能重复,不能嵌套,元素可以重复
  - 区别:
    - id 和name 都是 xml 中的一个属性，用来标识。
    - 如果起名中没有特殊字符的话，一般用id。
    - 但是如果起名中有类似 “ / ”的特殊字符，就必须使用name属性了，比如：name=”/ss/ss”

4.3 CDATA文本段:

CDATA文本段会被解释器忽视,里面的内容原样输出,如果文本存在大量转义字符,可以使用CDATA.

```
    <评价>
        <![CDATA[《警察故事》是一个很好的电影，其中"成龙"饰演的警察是一个负责人的好警察]]>
    </评价>
```
4.4 XML命名空间

命名空间用来解决元素名称冲突和多义性的问题

命名空间用 "xmlns:" 来声明

属性不与元素的命名空间进行绑定

```不知道为啥,命名空间在谷歌浏览器中不支持中文...
    <?xml version="1.0" encoding="utf-8" standalone="no"?>
    <fruit xmlns:mango ="mango" xmlns:banana ="banana">
        <mango:fruit>
            <品种>芒果</品种>
            <产地>泰国</产地>
            <季节>夏季</季节>
        </mango:fruit>
        <banana:fruit>
            <品种>香蕉</品种>
            <产地>广西</产地>
            <季节>夏季</季节>
        </banana:fruit>
    </fruit>
```



## 二、DTD文档

文档定义模型：对xml文档元素的结构和属性的描述

### 2.1 XML文档构成
- DTD文档声明，无standalone属性
- 开始标记  （  <!DOCTYPE 根元素[   ）
- 主体
- 结束标记  （  ]>  ）

内外部DTD文档区别
- 内部不需要声明
- 外部不需要开始和结束标记
- 外部需要引用

```外部DTD
<?xml version="1.0" encoding="UTF-8"?>
<!ELEMENT 图书信息 (图书+)>
<!ELEMENT 图书 (作者,报价)>
<!ATTLIST 图书 name CDATA #REQUIRED>
<!ELEMENT 作者 (#PCDATA)>
<!ELEMENT 报价 (#PCDATA)>
```
```
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE 图书信息[
	<!ELEMENT 图书信息 (图书+)>
	<!ELEMENT 图书 (作者,报价)>
	<!ATTLIST 图书 name CDATA #REQUIRED>
	<!ELEMENT 作者 (#PCDATA)>
	<!ELEMENT 报价 (#PCDATA)>
]>

<图书信息>
	<图书 name="XML">
		<作者>张三</作者>
		<报价>50￥</报价>
	</图书>
	<图书 name="HTML">
		<作者>李四</作者>
		<报价>30￥</报价>
	</图书>
	<图书 name="PHP">
		<作者>王五</作者>
		<报价>5$</报价>
	</图书>
</图书信息>

```
### 2.2 元素声明类型
- EMPTY     空元素
- #PCDATA   除标记外的一切字符（数字，字母，符号）
- ANY       包含任何可以解析的内容
    - 如果元素的子元素为ANY类型，就必须有自己的元素类型声明
- 带子元素的元素
```
    <!ELEMENT 元素名 (EMPTY)>
    <!ELEMENT 元素名 (PCDATA)>
    <!ELEMENT 元素名 (ANY)>
    <!ELEMENT 元素名 (子元素)>
```

子元素出现次数
- “+” 出现次数 >=1
- “*” 出现次数 >=0
- “?” 出现次数 0或1
```
    <!ELEMENT 元素名 (子元素+)>
```

特殊
```子元素必须出现步行或公交,但是不能同时出现
    <!ELEMENT 出行 (步行|公交)>
```
### 2.3 元素属性声明
格式:
<ATTLIST 元素名 属性名 属性类型 属性附加条件>
属性类型:
- CDATA 字符数据,即没有标记的文本
- 枚举  备选属性值的值列表
- ID    文档中唯一
- IDREF 
    - ID相当于主键,IDREF相当于外键
    - IDREF针对属性含有ID的元素,声明元素的联系,其作用和外键一样,通过对ID属性元素的引用表示元素之间的关系
- IDREFS    IDREF的复数形式
- ENTITY   在DTD中声明的实体 

附加属性:
- REQUIRED  元素的每个实例必须具有该属性
- IMPLIED   元素实例不必含有该属性
- FIXED+固定值  属性值固定
- 缺省值     如果元素不包含有该属性,则默认为缺省值

**注意:PCDATA是元素类型,CDATA是属性类型**


```属性类型实例
    <!ATTLIST 学生 姓名 #CDATA #REQUIRED>
    <!ATTLIST 学生 性别 (男|女) "男">
    <!ATTLIST 学生 学号 ID #REQUIRED>

```
```IDREF实例
DTD:---------------------------------------------------

<?xml version="1.0" encoding="UTF-8"?>
<!--项目和任务定义ID属性，负责人和负责任务定义IDREF属性-->
<!--项目任务具有唯一性，而项目负责人有多个，也可以有多个负责任务-->
<!ELEMENT 工程 (项目,负责人*)>
<!ATTLIST 项目 project_id ID #REQUIRED>
<!ATTLIST 负责人 person_id IDREF #REQUIRED>

<!ELEMENT 项目 (项目名称,任务*)>
<!ELEMENT 项目名称 (#PCDATA)>
<!ELEMENT 任务 (#PCDATA)>
<!ATTLIST 任务 job_id ID #REQUIRED>

<!ELEMENT 负责人 (姓名,负责任务)>
<!ELEMENT 姓名 (#PCDATA)>
<!ELEMENT 负责任务 (#PCDATA)>
<!ATTLIST 负责任务 project_project_id IDREF #REQUIRED>

XML:---------------------------------------------------

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE 工程 SYSTEM "I:\XML\XML_Learn\02\project.dtd">
<工程>
	<项目 project_id="p1">
		<项目名称>评分系统</项目名称>
		<任务 job_id="job1">开发</任务>
		<任务 job_id="job2">维护</任务>
	</项目>
	
	<负责人 person_id="p1">
		<姓名>张三</姓名>
		<负责任务 project_project_id="job1">开发</负责任务>
	</负责人>
	
	<负责人 person_id="p1">
		<姓名>李四</姓名>
		<负责任务 project_project_id="job1">开发</负责任务>
	</负责人>
	
	<负责人 person_id="p1">
		<姓名></姓名>
		<负责任务 project_project_id="job2">维护</负责任务>
	</负责人>
</工程>

```

**请问为什么会报错！！！！！！**
```
<?xml version="1.0" encoding="UTF-8"?>
<!ELEMENT PPT (chapter*)>

<!ELEMENT chapter (title,image*,txt*)>
<!ATTLIST chapter chapter_num ID #REQUIRED>
<!ELEMENT title (#PCDATA)>
<!ELEMENT image (#PCDATA)>
<!ATTLIST image Source ENTITY #REQUIRED>
<!--!ATTLIST image Source ENTITIES #REQUIRED>-->
<!ENTITY Picture1 SYSTEM "a.jpg">
<!--<!ENTITY Picture2 SYSTEM "b.jpg">
<!ENTITY Picture3 SYSTEM "c.jpg">-->
<!ELEMENT txt (#PCDATA)>

-------------------------------------------------------
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE PPT SYSTEM "I:\XML\XML_Learn\02\PPT.dtd">
<PPT>
	<chapter chapter_num="c1">
		<title></title>
		<image Source= "Picture1" >sdsda</image>
		<txt>hello world!</txt>
	</chapter>
	
	<chapter chapter_num="c2">
		<title></title>
		<image Source="Pic1 Pic2"/>
		<txt>hello world!</txt>
	</chapter>
</PPT>



```



## 三、Schema文档
Schema特点
- Schema比DTD更加完善，更加强大
- Schema可以针对未来的需求进行扩展
- Schema支持数据类型
- Schema支持命名空间

文档通常以单独的文件形式存在,扩展名 **.xsd**









 