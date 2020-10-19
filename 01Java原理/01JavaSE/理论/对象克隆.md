# 对象克隆

## **什么时候克隆？**

克隆为了获取一个对象，通过clone方法赋值的对象跟原来的对象时同时独立存在的。一方面克隆的对象可能包含一些已经修改过的属性，另一方面，使用底层克隆方法效率更高。

## **浅克隆和深克隆**

### 浅克隆

![浅克隆](https://images2015.cnblogs.com/blog/690102/201607/690102-20160727132640216-1387063948.png)

不对对象内部的引用类型进行克隆【仅复制引用类型地址，所以当内部引用类型的引用内容被改变时，两个对象都会改变】

- 被复制的类需要实现Clonenable接口
- 覆盖clone()方法，访问修饰符设为public。方法中调用super.clone()方法得到需要的复制对象。

```java
//实体类
public class Father implements Cloneable {
    public String name;
    public int age;

    public Father() {
    }

    public Father(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void speak(){
        System.out.println("I am Father");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Father{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

//测试
public static void main(String[] args) throws CloneNotSupportedException {
    Father father = new Father("zhangsan", 22);
    Father father1 = (Father)father.clone();
    //返回false
    System.out.println(father==father1);
    System.out.println(father);
    System.out.println(father1);
}
```



### 深克隆

![深克隆](https://images2015.cnblogs.com/blog/690102/201607/690102-20160727132838528-120069275.png)

对对象内部的引用类型进行同步克隆

深度克隆有两种实现方法：

- 可以通过实现Cloneable接口并重写Object类中的clone()方法
- 实现Serializable接口，通过对象的序列化和反序列化实现克隆

（**如果引用类型里面还包含很多引用类型，或者内层引用类型的类里面又包含引用类型，使用clone方法就会很麻烦。这时我们可以用序列化的方式来实现对象的深克隆。**）

序列化就是将对象写到流的过程，写到流中的对象是原有对象的一个拷贝，而原对象仍然存在于内存中。通过序列化实现的拷贝不仅可以复制对象本身，而且可以复制其引用的成员对象，因此通过序列化将对象写到一个流中，再从流里将其读出来，可以实现深克隆。需要注意的是能够实现序列化的对象其类必须实现Serializable接口，否则无法实现序列化操作。



方式一：重写clone方法，较为复杂，且不适用于多层克隆

```java
@Override  
public Object clone() {  
    Student stu = null;  
    try{  
        stu = (Student)super.clone();   //浅复制  
    }catch(CloneNotSupportedException e) {  
        e.printStackTrace();  
    }  
    stu.addr = (Address)addr.clone();   //深度复制  
    return stu;  
}  
```

方式二：序列化

**注意：**基于序列化和反序列化实现的克隆不仅仅是深度克隆，更重要的是通过泛型限定，可以检查出要克隆的对象是否支持序列化，这项检查是编译器完成的，不是在运行时抛出异常，这种是方案明显优于使用Object类的clone方法克隆对象。让问题在编译的时候暴露出来总是优于把问题留到运行时。

```java
public class Inner implements Serializable{
    private static final long serialVersionUID = 872390113109L; //最好是显式声明ID
    public String name = "";

    public Inner(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Inner的name值为：" + name;
    }
}

public class Outer implements Serializable{
    private static final long serialVersionUID = 369285298572941L;  //最好是显式声明ID
    public Inner inner;
    //Discription:[深度复制方法,需要对象及对象所有的对象属性都实现序列化]　
    public Outer myclone() {
        Outer outer = null;
        try { 
            // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            // 将流序列化成对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            outer = (Outer) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return outer;
    }
}

```













