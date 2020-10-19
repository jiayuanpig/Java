# 序列化（Serializable）

一个java中的类只有实现了Serializable接口，它的对象才是可序列化的。如果要序列化某些类的对象，这些类就必须实现Serializable接口。Serializable是一个空接口，没有什么具体内容，它的目的只是简单的标识一个类的对象可以被序列化。



## **序列化是做什么的**

- **序列化：将对象写入到IO流中**
- **反序列化：从IO流中恢复对象**
- **意义：序列化机制允许将实现序列化的Java对象转换为字节序列，这些字节序列可以保存在磁盘上，或通过网络传输，以达到以后恢复成原来的对象。序列化机制使得对象可以脱离程序的运行而独立存在。**
- **使用场景：所有可在网络上传输的对象都必须是可序列化的，**比如RMI（remote method invoke,即远程方法调用），传入的参数或返回的对象都是可序列化的，否则会出错；**所有需要保存到磁盘的java对象都必须是可序列化的。通常建议：程序创建的每个JavaBean类都实现Serializeable接口。**



## 序列化实现方式

如果需要将某个对象保存到磁盘上或者通过网络传输，那么这个类应该实现**Serializable**接口或者**Externalizable**接口之一。

**注意事项:**

   1).序列化时,只对对象的状态保存,而不管对象的方法
   2).当一个父类实现序列化时,子类自动序列化,不需要显式实现Serializable接口
   3).当一个对象的实例变量引用其他对象,序列化该对象时也把引用对象序列化.
   4).并非所有的对象都可以序列化.



### Serializable

**普通序列化**

Serializable接口是一个标记接口，不用实现任何方法。一旦实现了此接口，该类的对象就是可序列化的。

**序列化步骤：**

- **步骤一：创建一个ObjectOutputStream输出流；**
- **步骤二：调用ObjectOutputStream对象的writeObject输出可序列化对象。**

```java
public class Person implements Serializable {
  private String name;
  private int age;
  
  public Person(String name, int age) {
      this.name = name;
      this.age = age;
  }

  @Override
  public String toString() {
      return "Person{" +
              "name='" + name + '\'' +
              ", age=" + age +
              '}';
  }
}

public class WriteObject {
  public static void main(String[] args) {
      try (
          //创建一个ObjectOutputStream输出流
          ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.txt"))) {
          //将对象序列化到文件person.txt
          Person person = new Person("张三", 23);
          oos.writeObject(person);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}
```

**反序列化步骤：**

- **步骤一：创建一个ObjectInputStream输入流；**
- **步骤二：调用ObjectInputStream对象的readObject()得到序列化的对象。**

**注意：反序列化不调用构造函数**

```java
public static void main(String[] args) {
    try (
        //创建一个ObjectInputStream输入流
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.txt"))) {
        Person person = (Person) ois.readObject();
        System.out.println(person);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```



**成员为引用序列化**

- **如果一个可序列化的类的成员不是基本类型，也不是String类型，那这个引用类型也必须是可序列化的；否则，会导致此类不能序列化。**

**同一对象序列化多次的机制**

- **反序列化的顺序与序列化时的顺序一致**。**Java序列化同一对象，并不会将此对象序列化多次得到多个对象。**所以如果使用==判断会返回true。

**Java序列化算法**

1. **所有保存到磁盘的对象都有一个序列化编码号**
2. **当程序试图序列化一个对象时，会先检查此对象是否已经序列化过，只有此对象从未（在此虚拟机）被序列化过，才会将此对象序列化为字节序列输出。**
3. **如果此对象已经序列化过，则直接输出编号即可。**

**java序列化算法潜在的问题**

由于java序利化算法不会重复序列化同一个对象，只会记录已序列化对象的编号。**如果序列化一个可变对象（对象内的内容可更改）后，更改了对象内容，再次序列化，并不会再次将此对象转换为字节序列，而只是保存序列化编号。**

**可选的自定义序列化**

1. 有些时候，我们有这样的需求，某些属性不需要序列化。**使用transient关键字选择不需要序列化的字段。**

2. 使用transient虽然简单，但将此属性完全隔离在了序列化之外。java提供了**可选的自定义序列化。**可以进行控制序列化的方式，或者对序列化数据进行编码加密等。

   通过重写writeObject与readObject方法，可以自己选择哪些属性需要序列化， 哪些属性不需要。如果writeObject使用某种规则序列化，则相应的readObject需要相反的规则反序列化，以便能正确反序列化出对象。这里展示对名字进行反转加密。

   当序列化流不完整时，readObjectNoData()方法可以用来正确地初始化反序列化的对象。例如，使用不同类接收反序列化对象，或者序列化流被篡改时，系统都会调用readObjectNoData()方法来初始化反序列化的对象。

3. **更彻底的自定义序列化**

   ANY-ACCESS-MODIFIER Object writeReplace() throws ObjectStreamException;
   ANY-ACCESS-MODIFIER Object readResolve() throws ObjectStreamException;

   - **writeReplace：在序列化时，会先调用此方法，再调用writeObject方法。此方法可将任意对象代替目标序列化对象**

   - **readResolve：反序列化时替换反序列化出的对象，反序列化出来的对象被立即丢弃。此方法在readeObject后调用。**

   - **readResolve常用来反序列单例类，保证单例类的唯一性。**

     **注意：readResolve与writeReplace的访问修饰符可以是private、protected、public，如果父类重写了这两个方法，子类都需要根据自身需求重写，这显然不是一个好的设计。通常建议对于final修饰的类重写readResolve方法没有问题；否则，重写readResolve使用private修饰。**



### Externalizable：强制自定义序列化

通过实现Externalizable接口，必须实现writeExternal、readExternal方法。

**注意：Externalizable接口不同于Serializable接口，实现此接口必须实现接口中的两个方法实现自定义序列化，这是强制性的；特别之处是必须提供pulic的无参构造器，因为在反序列化的时候需要反射创建对象。**

```java
public interface Externalizable extends java.io.Serializable {
     void writeExternal(ObjectOutput out) throws IOException;
     void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
}

public class ExPerson implements Externalizable {

    private String name;
    private int age;
    //注意，必须加上pulic 无参构造器
    public ExPerson() {
    }

    public ExPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        //将name反转后写入二进制流
        StringBuffer reverse = new StringBuffer(name).reverse();
        System.out.println(reverse.toString());
        out.writeObject(reverse);
        out.writeInt(age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        //将读取的字符串反转后赋值给name实例变量
        this.name = ((StringBuffer) in.readObject()).reverse().toString();
        System.out.println(name);
        this.age = in.readInt();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ExPerson.txt"));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ExPerson.txt"))) {
            oos.writeObject(new ExPerson("brady", 23));
            ExPerson ep = (ExPerson) ois.readObject();
            System.out.println(ep);
        }
    }
}
//输出结果
//ydarb
//brady
//ExPerson{name='brady', age=23}
```



### 两种序列化对比

| 实现Serializable接口                                         | 实现Externalizable接口   |
| :----------------------------------------------------------- | :----------------------- |
| 系统自动存储必要的信息                                       | 程序员决定存储哪些信息   |
| Java内建支持，易于实现，只需要实现该接口即可，无需任何代码支持 | 必须实现接口内的两个方法 |
| 性能略差                                                     | 性能略好                 |

**虽然Externalizable接口带来了一定的性能提升，但变成复杂度也提高了，所以一般通过实现Serializable接口进行序列化。**





## 序列化版本号serialVersionUID

我们知道，**反序列化必须拥有class文件，但随着项目的升级，class文件也会升级，序列化怎么保证升级前后的兼容性呢？**

java序列化提供了一个private static final long serialVersionUID 的序列化版本号，只有版本号相同，即使更改了序列化属性，对象也可以正确被反序列化回来。

```java
public class Person implements Serializable {
    //序列化版本号
    private static final long serialVersionUID = 1111013L;
    private String name;
    private int age;
    //省略构造方法及get,set
}
```

如果反序列化使用的**class的版本号**与序列化时使用的**不一致**，反序列化会**报InvalidClassException异常。**

**序列化版本号可自由指定，如果不指定，JVM会根据类信息自己计算一个版本号，这样随着class的升级，就无法正确反序列化；不指定版本号另一个明显隐患是，不利于jvm间的移植，可能class文件没有更改，但不同jvm可能计算的规则不一样，这样也会导致无法反序列化。**

什么情况下需要修改serialVersionUID呢？分三种情况。

- 如果只是修改了方法，反序列化不容影响，则无需修改版本号；
- 如果只是修改了静态变量，瞬态变量（transient修饰的变量），反序列化不受影响，无需修改版本号；
- 如果修改了非瞬态变量，则可能导致反序列化失败。**如果新类中实例变量的类型与序列化时类的类型不一致，则会反序列化失败，这时候需要更改serialVersionUID。**如果只是新增了实例变量，则反序列化回来新增的是默认值；如果减少了实例变量，反序列化时会忽略掉减少的实例变量。





## 使用场景

- 当想把的内存中的对象状态保存到一个文件中或者数据库中时候；
- 当想用套接字在网络上传送对象的时候；
- 当想通过RMI传输对象的时候；
- 对象克隆







