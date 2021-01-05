# 泛型

- 泛型存在的意义？
-  泛型类，泛型接口，泛型方法如何定义？
-  如何限定类型变量？
-  泛型中使用的约束和局限性有哪些？
-  泛型类型的继承规则是什么？
-  泛型中的通配符类型是什么？
-  如何获取泛型的参数类型？
-  虚拟机是如何实现泛型的？
-  在日常开发中是如何运用泛型的？

<img src="https://upload-images.jianshu.io/upload_images/2516326-7bbe8045e54e21c5.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp" alt="泛型详解" style="zoom:50%;" />



## 1.泛型的定义以及存在意义

泛型，即“**参数化类型**”。就是将类型由原来的具体的类型参数化，类似于方法中的变量参数，此时类型也定义成参数形式（可以称之为类型形参），然后在使用/调用时传入具体的类型（类型实参）。
 例如：`GenericClass<T>{  }`

一些常用的泛型类型变量：

- E：元素（Element），多用于java集合框架
- K：关键字（Key）
- N：数字（Number）
- T：类型（Type）
- V：值（Value）

使用泛型的意义在于

1. 适用于多种数据类型执行相同的代码（代码复用）

2. 泛型中的类型在使用时指定，不需要强制类型转换（类型安全，编译器会检查类型）



## 2.泛型的使用

**没有使用泛型**

`Container`类保存了一对`key-value`键值对，但是类型是定死的，也就说如果我想要创建一个键值对是`String-Integer`类型的，当前这个`Container`是做不到的，必须再自定义。那么这明显重用性就非常低。

当然，我可以用`Object`来代替`String`，并且在Java SE5之前，我们也只能这么做，由于`Object`是所有类型的基类，所以可以直接转型。但是这样灵活性还是不够，因为还是指定类型了，只不过这次指定的类型层级更高而已，有没有可能不指定类型？有没有可能在运行时才知道具体的类型是什么？

所以，就出现了泛型。

```java
public class Container {
    private String key;
    private String value;

    public Container(String k, String v) {
        key = k;
        value = v;
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
```

**泛型类和泛型变量**

在编译期，是无法知道`K`和`V`具体是什么类型，只有在运行时才会真正根据类型来构造和分配内存。

```java
public class Container<K, V> {
    private K key;
    private V value;

    public Container(K k, V v) {
        key = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

//测试
public static void main(String[] args) {
    Container<String, String> c1 = new Container<String, String>("name", "findingsea");
    Container<String, Integer> c2 = new Container<String, Integer>("age", 24);
    Container<Double, Double> c3 = new Container<Double, Double>(1.1, 2.2);
    System.out.println(c1.getKey() + " : " + c1.getValue());
    System.out.println(c2.getKey() + " : " + c2.getValue());
    System.out.println(c3.getKey() + " : " + c3.getValue());
}
```

**泛型接口**

```java
public interface Generator<T> {
    public T next();
}


public class FruitGenerator implements Generator<String> {

    private String[] fruits = new String[]{"Apple", "Banana", "Pear"};

    @Override
    public String next() {
        Random rand = new Random();
        return fruits[rand.nextInt(3)];
    }
}
```

**泛型方法**

一个基本的原则是：**无论何时，只要你能做到，你就应该尽量使用泛型方法。**也就是说，如果使用泛型方法可以取代将整个类泛化，那么应该有限采用泛型方法。

可以看到方法的参数彻底泛化了，这个过程涉及到编译器的类型推导和自动打包，也就说原来需要我们自己对类型进行的判断和处理，现在编译器帮我们做了。这样在定义方法的时候不必考虑以后到底需要处理哪些类型的参数，大大增加了编程的灵活性。

```java
public class Main {

    public static <T> void out(T t) {
        System.out.println(t);
    }

    public static void main(String[] args) {
        out("findingsea");
        out(123);
        out(11.11);
        out(true);
    }
}
```



## 3.泛型中的约束和局限性

1. 不能实例化泛型类
2. 静态变量或方法不能引用泛型类型变量，但是静态泛型方法是可以的
3. 基本类型无法作为泛型类型
4. 无法使用instanceof关键字或==判断泛型类的类型
5. 泛型类的原生类型与所传递的泛型无关，无论传递什么类型，原生类是一样的
6. 泛型数组可以声明但无法实例化
7. 泛型类不能继承Exception或者Throwable
8. 不能捕获泛型类型限定的异常但可以将泛型限定的异常抛出



## 4.泛型类型继承规则

1. 对于泛型参数是继承关系的泛型类之间是没有继承关系的

2. 泛型类可以继承其它泛型类，例如: public class ArrayList<E> extends AbstractList<E>

3. 泛型类的继承关系在使用中同样会受到泛型类型的影响



## 5.通配符类型

1. `<? extends Parent>` 指定了泛型类型的上界

2. `<? super Child>` 指定了泛型类型的下界

3. `<?>` 指定了没有限制的泛型类型



## 6.获取泛型的参数类型

**Type是什么**
 这里的Type指java.lang.reflect.Type, 是Java中所有类型的公共高级接口, 代表了Java中的所有类型. Type体系中类型的包括：数组类型(GenericArrayType)、参数化类型(ParameterizedType)、类型变量(TypeVariable)、通配符类型(WildcardType)、原始类型(Class)、基本类型(Class), 以上这些类型都实现Type接口.

- 参数化类型,就是我们平常所用到的泛型List、Map；
- 数组类型,并不是我们工作中所使用的数组String[] 、byte[]，而是带有泛型的数组，即T[] ；
- 通配符类型, 指的是<?>, <? extends T>等等
- 原始类型, 不仅仅包含我们平常所指的类，还包括枚举、数组、注解等；
- 基本类型, 也就是我们所说的java的基本类型，即int,float,double等



## 应用









