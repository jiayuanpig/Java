# Sql语句训练





## 简单查询

语法：

> select
> from
> where
> group by
> having
> order by
> limit

执行顺序：

> **from**确定从哪个表读数据
>
> -->**where**分组前对表进行条件筛选
>
> -->**group by...having**分组并对分组后进行条件过滤
>
> -->**聚合函数**执行在group by 之后，having之前，因此where中无法使用聚合函数-
>
> ->**select**查询需要的字段
>
> -->**order by**根据字段值进行排序
>
> -->**limit**对最终结果进行分页



```
//常见规则
where：条件查询【注意：是在分组前进行条件限制，不允许使用聚合函数】
order by：按某一字段排序（ASC升序/DESC降序）
group by：按某一字段进行分组，字段值相同的分为一组【注意：没有group by的字段是不能放在select里面，但可以使用聚合函数】
having：条件查询，和group by搭配使用【注意：是在分组后进行条件限制，允许使用聚合函数】
limit：分页

//判断条件
=
>
>=
<
<=
<>：不等于
like

//判断条件关键字
between and
in
not


//like匹配规则
%：出现0-无数次
_：出现1次

//聚合函数
sum：求和
avg：平均
max：最大
min：最小
```



```sql
#示例：学生表student，字段(id,name,age,sex)

#查看所有学生信息
select * from student;
#查看id不等于1且年龄大于18的学生
select * from student where id <> 1 and age > 18;
#查看学生人数
select count(*) from student;
#查看性别以及对应的学生人数
select sex,count(*) from student group by sex;
#查看所有学生信息，并根据年龄降序排序
select * from student order by age desc;
#查看人数大于5人的不同年龄对应人数
select age,count(*) from student group by age having count(*) >5;

# 剔除名字重复的部分
select distinct name from student where name is not null;

# 不使用组函数，求出分数最大的成绩(原理：自己和自己左连接，条件是成绩小于表二，最后为空表示当前成绩最大)
select * from grade t1 
	left join grade t2 
	on t1.grade < t2.grade 
	where t2.id is null;



```



## 连接查询

连接包括：自然连接、左连接、右连接、全连接、笛卡尔积

- 连接后面的条件是on不是where！！！
- on后面的条件会返回一个临时表，where是对该临时表进行进一步过滤
- 如果表中有引用自身属性的字段，可以自己和自己连接
- 通过连接查询效率比使用where效率高
- 尽量不要使用in和not in，使用连接来代替

```sql
#示例：
#学生表student(id,name,age,sex)
#课程表class(id,name)
#成绩表grade(id,sid,cid,grade)

#笛卡尔积
select * from student,class;
#自然连接:查询学生，课程以及对应的成绩
select * from student,class,grade where student.id = grade.sid and grade.cid = class.id;

#inner join：只保存符合连接的成绩，等同于上面的自然连接的结果 
select * from student inner join grade on student.id = grade.sid;
#left join/right join：一边的信息全部保存
略
#full outer join：两个表全部数据都会保留


#复杂查询
#1、查询平均成绩大于60的学生姓名、平均成绩
#第一步：查出学生的平均成绩
select sid,avg(grade) from grade group by sid;
#第二步：查出需要的信息
select t1.name,t2.avg_grade from student as t1 
	inner join (select sid,avg(grade) as avg_grade from grade group by sid) as t2 
	on t1.id = t2.sid and t2.avg_grade > 60;
#2、查询平均成绩大于60的学生姓名、课程名、对应成绩
#第一步：查出学生的平均成绩
select sid,avg(grade) from grade group by sid;
#第二步：查出平均成绩大于60的学生id
select sid from grade group by sid having avg(grade) > 60;
#第三步：查出需要的信息
select student.name,class.name,grade.grade from student,class,grade 
	where grade.sid = student.id and grade.cid = class.id and
	grade.sid in (select sid from grade group by sid having avg(grade) > 60);
==>上一步等价于
select student.name,class.name,grade.grade from student 
	inner join student,class,grade,(select sid from grade group by sid having avg(grade) > 60) as t4
	on grade.sid = student.id and grade.cid = class.id and grade.sid = t4.sid;

```



## 子查询

**注意：连接查询和子查询的区别在于，子查询会将查询结果返回交给上一层查询**

子查询效率低于连接查询

子查询常用关键字：in 、not int