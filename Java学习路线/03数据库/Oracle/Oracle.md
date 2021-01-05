# Oracle

## oracle体系结构

### 相关概念

- 数据库

  oracle数据库是数据的物理存储，包括了数据文件ORA或DBF、控制文件、联机日志、参数文件。oracle数据库的概念和其他数据库不一样，这里的数据库表示的是**一个操作系统只有一个库**。

- 实例

  一个oracle实例由一系列的后台进程和内存结构组成。一个数据库可以有多个实例。

- 用户

  用户是在实例下建立的，**不同实例可以建立名字相同的用户**。【用户是管理oracle表的基本单位】

- 表空间

  表空间是oracle对物理数据库上相关数据文件的逻辑映射。一个数据库在逻辑上被划分为若干个表空间，每个表空间包含了在逻辑上相关联的一组结构，每个数据库至少有一个表空间（称之为system表空间）

- 数据文件（DBF、ORA）

  数据文件是数据库的物理存储单位。数据库的数据是逻辑上存储在表空间中的，实际上存储在一个个数据文件中。一个表空间可以有多个数据文件一个数据文件只能属于某一个表空间。当数据文件被加入到表空间中，这个文件就不能被删除，如果想删除这个文件，就需要删除对应的表空间。

**具体关系：**

一个数据库有多个实例；一个实例有多个用户和多个表空间；多个用户可以对一个表空间进行操作；一个表空间有多个数据文件



## Oracle用户

### sys和system用户的区别

system用户只能用normal身份登陆em。

sys用户具有“SYSDBA”或者“SYSOPER”权限，登陆em也只能用这两个身份，不能用normal。

### dba和sysdba的区别

dba、sysdba这两个系统角色有什么区别呢？在说明这一点之前我需要说一下oracle服务的创建过程：

- 创建实例
- 启动实例
- 创建数据库(system表空间是必须的)
- 启动过程
- 实例启动
- 装载数据库
- 打开数据库

sysdba，是管理oracle实例的，它的存在不依赖于整个数据库完全启动，只要实例启动了，它就已经存在，以sysdba身份登陆，装载数据库、打开数据库，只有数据库打开了，或者说整个数据库完全启动后，dba角色才有了存在的基础，dba只是个角色而已。

### 连接时身份区别

- Normal：普通用户
  - 只有被sys授权过的权限，才能进行数据库操作
- SYSDBA：数据库管理员
  - 拥有最高的数据库权限，只能是sys登录dba
  - 权限包括： 打开数据库服务器、关闭数据库服务器、备份数据库、恢复数据库、日志归档、会话限制
- SYSOPER：数据库操作员
  - 主要用来启动、关闭数据库，登陆后用户是public
  - 权限包括：打开数据库服务器、关闭数据库服务器、备份数据库、恢复数据库、日志归档、会话限制、管理功能、创建数据库



**补充：**

scott用户，密码为tiger

该用户用于初学者学习，里面自带一些初始表可以模拟完成一些复杂的查询操作。

- emp：员工信息表
- dept：部门表
- salgrade：工资等级表
- bonus：工资表

如果想要使用scott，需要超级管理员进行解锁，解锁方式：

- 解锁用户：`alter user scott account unlock;`
- 解锁密码（也可以用来重置密码）：`alter user scott identified by tiger;`



### Oracle数据类型

| 数据类型                      | 含义                                                         |
| ----------------------------- | ------------------------------------------------------------ |
| char                          | 不可变长字符串                                               |
| varchar、varchar2             | 可变长字符串，多用varchar2（varchar是sql标准，varchar2是oracle独有数据类型） |
| LONG                          | 长字符串，可存2G                                             |
| NUMBER                        | NUMBER(n)表示一个长度为n的整数；NUMBER(m,n)表示一个总长度为m，其中小数占n位 |
| INT                           | 整型（sql标准定义的数据类型）                                |
| DATA                          | 日期类型                                                     |
| CLOB                          | 大对象，表示大文本类型，可存4G                               |
| BLOB                          | 大对象，表示二进制数据，可存4G                               |
|                               |                                                              |
| **补充**                      | **注意：Oracle在存储的时候会将所有的标准类型都转成自己的数据类型格式。** |
| Oracle 特有数据类型（不常用） | RAW、LOG RAW、LOB、CLOB、BLOB、BFILE                         |
| ROWID                         | 保存的为某一行数据保存到磁盘上的物理的位置                   |
| ROWNUM                        | 行号                                                         |
|                               |                                                              |





## 相关操作

### 表操作

```plsql
-------------------------------------------------------------
--创建表空间
create tablespace tablename;
--指定表对应数据文件的创建位置，注意文件后缀
datafile 'c:\oracle.dbf';
--设置数据文件大小100m，并自动扩展，每次扩展10m
size 100m
autoextend on
next 10m;
--删除表空间
drop tablespace tablename;

--表的定义、删除和修改-----------------------------------------------------------
--创建一个person表
create table person(
	pid number(20),
    pname varchar2(10)
)
--修改表结构
--添加一列
alter table person add (gender number(1)[,others]);
--修改列类型
alter table person modify gender char(1);
--修改列名称
alter table person rename column gender to sex;
--删除一列
alter table person drop column sex;

```

### 用户操作

```plsql
--用户操作-----------------------------------------------------------
--创建用户
create user username
identified by password
default tablespace tablename;
--登陆前必须要给用户授权
grant dba to username;
/*
	oracle常用角色：
	connect：连接角色，基本角色
	resource：开发者角色
	dba：超级管理员角色
*/
```

### 数据操作

```plsql
--表中数据的增删改-----------------------------------------------------------
--添加一条记录
insert into person(pid,pname) values(1,'小明');
commit;	--注意：在plsql中，默认事务不是主动提交，需要手动提交
--修改一条记录
update person set pname = '小马' where pid = 1;
commit;
--三个删除
delete from person;		--删除表中全部记录
drop table person;		--删除表结构
truncate table person;  --先删除表后再次创建表，效果等同于删除表中全部记录

--序列：默认从1开始，依次递增，主要用来给主键赋值使用
--序列不真的属于一张表，但是可以逻辑上和表做绑定
--dual：虚表，只是为了补全语法，没有任何意义
--.currval表示当前序号/.nextval表示创建一个序号
create sequence s_person;
select s_person.currval/.nextval from dual;
--修改之前的添加记录
insert into person(pid,pname) values(s_person.nextval,'小明');
commit;		--注意：如果添加发生回滚，序号依旧继续添加，不会回滚
```

### 查询

#### 单表查询

```plsql
--单行函数：作用于一行，返回一个值
---常见的字符串函数：字符串大小写转换、字符串连接、字符串截取、字符串替换、获取字符串长度等
select upper('yes') from dual --返回YES
select lower('YES') from dual --返回yes
---常见的数值函数：四舍五入
select round(25.61,1) from dual	--四舍五入，返回25.6
select round(25.66,1) from dual	--直接截取，返回25.6
select mod(10,3) from dual	--取余，返回1
---常见的日期函数：months_between、转换函数
--查看所有员工入职距离现在几天
select sysdate-e.hiredate from emp e;
--转换函数
select to_char(sysdate,'yyyy-mm-dd hh:mi:ss') from dual; --返回：2020-10-06 01:30:20
select to_char(sysdate,'fm yyyy-mm-dd hh:mi:ss') from dual; --返回：2020-10-6 1:30:20
---常见的通用函数：nvl(字段名,默认值)将字段值为null的替换为默认值

--多行函数（聚合函数），作用于多行，返回一个值
--常见的聚合函数：count、sum、max、min、avg

--条件表达式：case when then
select e.ename,
	case e.ename
		when 'SMITH' then '史密斯'
		when 'TOM' then '汤姆'
		else '外国人'
	end
from emp e;
或
select e.sal,
	case
		when e.sal > 3000 then '高收入'
		when e.sal > 1500 then '中等收入'
		else '低收入'
	end
from emp e;
--oracle专用表达式【推荐使用通用写法，兼容性更强】
--注意：oracle除了起别名，都用单引号；别名不能使用单引号
select e.ename,
	decode(e.ename,
		 'SMITH','史密斯',
		 'TOM','汤姆',
		 '外国人') "中文名"
from emp e;

--分组查询group by	where 条件 having 条件
---注意：
---只有出现在group by后面的原始列才能直接出现在select后面，否则需要使用聚合函数才能查询
---所有条件都不能使用别名来判断
---where过滤分组前的数据，having过滤分组后的数据，所以必须一前一后

--查询每个部门工资高于800的员工的平均工资，然后再查询平均工资高于2000的部门
select e.depno,avg(e.sal) asal
from emp e
where  e.sal > 800
group by e.depno
having avg(e.sal) > 2000

```

**单行函数补充**

```plsql
1、大写转小写
select lower ('SQL EE') from dual

2、小写转大写
select upper ('xxx y') from dual

3、首字母大写
select initcap ('xxx yda') from dual

4、拼接函数
select concat ('xxx','yda') from dual

5、字符串截取函数
select substr ('qwertsst',2,5) from dual

6、返回长度函数
select length ('qwertsst') from dual

7字符查找函数（第一次出现的位置）
select instr('FFEADA','A') from dual

8字符左填充函数
select lpad ('adasda',9,'%') from dual

9字符右填充函数
select rpad ('adasda',9,'%') from dual

10指定字符串里面去掉开头字母
select trim ('g' from 'geadg') from dual

11、四舍五入函数
select round(89.36876,1) from dual

12、截断函数
select trunc(89.36876,1) from dual

13、求余函数
select mod(800,3) from dual

14、日期函数取整，到下一个月的第一天
select round(sysdate,'month') from dual

15、截取日期函数
select trunc(sysdate,'year') from dual

16、日期的to_char
select to_char(sysdate) from dual

17、返回两个日期之间的月份数
select months_between ('18-7月-19','19-7月-19') from dual

18、增加月份函数
select add_months('18-3月-19',6) from dual

19、数字to_char
select to_char(9700,'$99,999.00') from dual

20、字符转换数字（16进制转换成10进制）
select to_number('19f','xxx') from dual;

21、字符转换日期
select sysdate,to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual

22、nvl嵌套函数
select nvl(to_char(3123.32),'null') from u1


```



#### 多表查询

```plsql
--笛卡尔积，意义不大
select * from emp e,dept d;

--等值连接（自然连接\内连接）！！！三者概念等价
select * from emp e,dept d where e.deptno = d.deptno;
--内连接
select * from emp e inner join dept d on e.deptno = d.deptno;

--外连接
---外连接包括：自身连接、左连接、外连接
----查询所有部门以及部门下的员工信息
select * from dept d left join emp e on d.deptno = e.deptno;
----查询所有员工以及对应的部门
select * from dept d right join emp e on d.deptno = e.deptno;
---oracle专用外连接
select * from emp e,dept d where e.deptno(+) = d.deptno; --保留dept的全部内容

----查询员工姓名和对应的领导姓名
select e1.ename,e2.ename
from emp e1,emp e2
where e1.mgr = e2.empno;	--自连接
----进阶：查询员工姓名、员工部门和对应的领导姓名、领导部门
select e1.ename,d1.dname,e2.ename,d2.dname
from emp e1,emp e2,dept d1,dept d2
where e1.mgr = e2.empno and e1.deptno = d1.deptno and e2.deptno = d2.deptno;

```



#### 子查询

```plsql
--子查询返回一个值
---查询工资和scott一样的员工信息
select * from emp where sal = 
(select sal from emp where ename = 'scott');

--子查询返回一个集合
---查询出工资和10号部门任意员工一样的员工信息
select * from emp where sal in 
(select sal from emp where deptno = 10)

--子查询返回一个表
---查询出每个部门最低工资和最低工资员工姓名和该员工所在的部门名称
select t.deptno,t.msal,e.ename,d.dname
from (select deptno,min(sal) msal
     from emp group by deptno) t,emp e,dept d
where
	t.deptno = e.deptno and t.deptno = d.deptno and t.msal = e.sal;

```

#### 分页查询

```plsql
--oracle中的分页，不能像mysql使用limit语句
---rownum行号：在进行select时，每查询一行记录就会加上一个行号，从1开始，依次递增，不能跳着走
---注意：排序操作会影响rownum的顺序；rownum也不能写上大于一个正数
----emp表工资倒排后，每页5条记录，查询第2页
--极其麻烦的写法
select * from (
	select rownum rn,tt.* from(
    	select * from emp order by sal desc
    ) tt where rownum < 11
) where rn > 5;
--或不使用排序
select *
from (select rownum r,emp.* from emp) b
where b.r > 5 and b.r < 11;
```

##### mysql和oracle分页的区别

- #### mysql分页

```sql
  select * from stu limit m, n; 
  //m = (startPage-1)*pageSize,n = pageSize
```

> （1）第一个参数值m表示起始行，第二个参数表示取多少行（页面大小）
> （2）m= (2-1)*10+1,n=10 ，表示 limit 11,10从11行开始，取10行，即第2页数据
> （3）m、n参数值不能在语句当中写计算表达式，写到语句之前必须计算好值。

- #### oracle分页

```sql
select * from (
select rownum rn,a.* from table_name a where rownum <= x
//结束行，x = startPage*pageSize
)
where rn >= y; //起始行，y = (startPage-1)*pageSize+1
```

> （1）>= y，<= x表示从第y行（起始行）~x行（结束行） 。
> （2）rownum只能比较小于，不能比较大于，因为rownum是先查询后排序的，例如你的条件为rownum>1，当查询到第一条数据，rownum为1，则不符合条件。第2、3…类似，一直不符合条件，所以一直没有返回结果。所以查询的时候需要设置别名，然后查询完成之后再通过调用别名进行大于的判断。

#### 行转列与列转行？？？









## 视图

- 视图就是提供一个查询的窗口，所有数据来自于原表
- 创建视图必须有dba权限
- 视图的作用
  - 视图可以屏蔽一些敏感字段
  - 保证总部和分部数据及时统一

```plsql
--创建视图
create view v_emp as select ename,job from emp;
--查询视图
select * from v_emp;
--修改视图
--修改视图会相应修改原表的数据，但是一般不推荐这样做
update v_emp set job='CLERK' where ename='ALLEN';
commit;
--创建只读视图
create view v_emp2 as select ename,job from emp with read only;
```



## 索引

索引就是在表的列上构建一个二叉树，以此来大幅提高查询效率

索引会影响数据增删改的效率

```plsql
--创建单列索引
--单列索引触发规则：条件必须是索引列中的原始值
--使用单行函数、模糊查询都会影响索引的触发
create index idx_ename on emp(ename);
select * from emp where ename = 'SCOTT';	--可以触发索引

--创建复合索引
--复合索引中的第一列为优点检索列
--如果要触发复合索引，必须包含优先检索列的原始值
--如果某一字段既有单列索引又有双列索引，会触发单列索引
create index idx_enamejob on emp(ename,job);
select * from emp where ename = 'SCOTT' and job = 'xx';	--可以触发复合索引
select * from emp where ename = 'SCOTT' or job = 'xx';	--不可以触发索引
select * from emp where ename = 'SCOTT';	--可以触发单列索引

```



## plsql

pl/sql编程语言时对sql语言的扩展，使得sql语言具备过程化编程的特性

pl/sql主要用来编写存储过程和存储函数

```plsql
--声明方法
declare
	i number(2) := 10;	--声明变量
	s varchar2(10) := '小明';
	--引用型变量
	ena emp.ename%type;	--声明变量类型为emp表中ename字段的数据类型
	--记录型变量
	emprow emp%rowtype;	--声明变量类型为emp表的一行数据
begin
	dbms_output.put_line(i);
	--将表的一个数据赋值给ena变量
	select ename into ena from emp where empno = 001;
	--将表的一行数据赋值给emprow变量
	select * into emprow from emp where empno = 001;
	--双竖杠表示字符串的连接
	dbms_output.put_line(emprow.ename || '的工作为：' || emprow.job);
end;

--if判断
declare
	i number(3) := &i;	--表示需要输入一个变量
begin
	if i < 18 then
		dbms_output.put_line('未成年');
	elsif i < 40 then
		dbms_output.put_line('成年');
	else
		dbms_output.put_line('中年');
	end if;
end;

--loop循环
--方式1：while循环
declare 
	i number(2) := 1;
begin
	while i<11 loop
		dbms_output.put_line(i);
		i := i + 1;	--没有i++
	end loop;
end;
--方式2：exit循环
declare 
	i number(2) := 1;
begin
	loop
		exit when i > 10;
		dbms_output.put_line(i);
		i := i + 1;	--没有i++
	end loop;
end;
--方式3：for循环
declare 	
begin
	for i in 1..10 loop
		dbms_output.put_line(i);
	end loop;
end;

--游标：可以存放多个对象、多行记录
--输出emp表中所有员工的姓名
declare 
	cursor c1 is select * from emp;
	emprow emp%rowtype;
begin	
	open c1;
		loop
			fetch c1 into emprow;
			exit when c1%notfound;
			dbms_output.put_line(emprow.ename);
		end loop;
	close c1;
end;
--给指定部门员工涨工资
declare
	--定义一个游标记录部门编号为eno的所有员工编号
	cursor c2(eno emp.deptno%type) is select empno from where deptno = eno;
	en empno.empno%type;
begin
	open c2(10);
		loop
			fetch c2 into en;
			exit when c2%notfound;
			update emp set sal = sal + 100 where empno = en;
			commit;
		end loop;
	close c2;
end;

```



## 存储过程与存储函数

**存储过程**就是提前已经编译好的一段pl/sql语言，放置在数据库端可以直接调用。这段pl/sql一般都是固定步骤的业务。

```plsql
--or replace表示对名称为p1的存储过程可以进行修改
--存储过程的参数有两种：一种是in，如果没有声明默认是in；另一种是out。
--凡是涉及into查询语句赋值或:=赋值的参数，必须用out来修饰

--给指定员工涨100块钱
create or replace procedure p1(eno emp.empno%type)
is

begin
	update emp set sal = sal + 100 where empno = eno;
	commit;
end;
--测试p1
declare

begin
	p1(001);	--给员工编号为001的员工加100工资
end;

```

**存储函数**

```plsql
--存储过程和存储函数的参数都不能带长度
--存储函数的返回值类型也不能带长度

--通过存储函数实现计算指定员工的年薪
create or replace function f_yearsal(eno emp.empno%type) return number
is
	s number(10);
begin
	select sal * 12 + nvl(commm,0) into s from emp where empno = eno;
	return s;
end;
--测试
--存储函数在调用时必须接收返回值
declare
	s number(10);
begin
	s := f_yearsal(001);	--查询编号为001的员工的年薪
	dbms_output.put_line(s);
end;


--使用out类型的参数
--使用存储过程计算年薪
create or replace procedure p_yearsal(eno emp.empno%type,yearsal out number)
is
	s number(10);		--存放工资
	c emp.comm%type;	--存放奖金
begin
	select sal * 12 , nvl(commm,0) into s,c from emp where empno = eno;
	yearsal := s + c;
end;
--测试
declare
	s number(10);
begin
	p_yearsal(001,s);	--查询编号为001的员工的年薪
	dbms_output.put_line(s);
end;
```



**存储函数和存储过程的区别**

- 本质区别：存储函数可以有一个返回值，存储过程没有返回值

  但是存储过程和存储函数都可以通过out指定一个或多个输出参数，从而实现返回多个返回值

- 语法区别：关键字不一样，存储函数比存储过程多两个return

  我们可以使用存储函数有返回值的特性来自定义函数，而存储过程不能自定义函数

  ```plsql
  --案例：查询员工姓名、员工所在的部门名称
  ---准备工作：将scott用户下的dept表复制到当前用户下
  create table dept as select * from scott.dept;
  ---使用传统方式实现功能
  select e.ename,d.dname from emp e,dept d where e.deptno = d.deptno;
  ---使用存储函数实现通过一个部门编号，输出一个部门名称
  create or replace function fdname(dno dept.deptno%type) return dept.dname%type
  is
  	dna dept.dname%type;
  begin
  	select dname into dna from dept where dept.deptno = dno;
  	return dna;
  end;
  ---使用fdname实现功能
  select e.ename,fdname(e.deptno)
  from emp e;
  ```



## 触发器

触发器：指定一个规则，当进行**增删改**的时候，只要满足该规则，自动触发，无需调用

```plsql
--语句级触发器
---插入一条记录，就输出一个新员工入职
create or replace trigger t1
after 
insert
on person
declare

begin
	dbms_output.put_line('一个新员工入职');
end;

---触发触发器
insert into person values(1,'小红');
commit;


--行级触发器：包含for each row，用来使用:old或:new对象
---不能给员工降薪
create or replace trigger t2
before 
updae
on emp
for each row
declare

begin
	if :old.sal > new.sal then 
		raise_application_error(-20001,'不能给员工降薪');	--异常提示，必须在-20001~-20999之间
	end if;
end;
---触发触发器
update emp set sal = sal - 1 where empno = 001;
commit;
```

**使用触发器实现主键自增**

```plsql
--创建行级触发器：在用户插入数据之前拿到即将插入的数据，给主键赋值
create or replace trigger auid
before 
insert
on person
for each row
declare

begin
	select s_person.nextval into new.pid from dual
end;
--执行插入操作
insert into person(pname) values('tom');
commit;
```



## Java调用

调用存储过程/存储函数

> 略



