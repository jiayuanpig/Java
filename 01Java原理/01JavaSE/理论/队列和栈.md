# Java中的栈和队列

## 栈的实现

栈的实现，有两个方法：一个是用java本身的集合类型Stack类型；另一个是借用LinkedList来间接实现Stack。

### 1.Stack实现

`Stack<E> Stack = new Stack<>();`

**直接用Stack来实现非常方便，常用的api函数如下：**

- boolean    isEmpty() // 判断当前栈是否为空

- synchronized E    peek() //获得当前栈顶元素

- synchronized E    pop() //获得当前栈顶元素并删除

- ​       E    push(E object) //将元素加入栈顶

- synchronized int   search(Object o) //查找元素在栈中的位置，由栈底向栈顶方向数

### 2.LinkedList实现

`LinkedList<E> Stack = new LinkedList<>();`

-  LinkedList 是一个继承于AbstractSequentialList的双向链表。它也可以被当作堆栈、队列或双端队列进行操作。
-  LinkedList 实现 List 接口，能对它进行队列操作。
-  LinkedList 实现 Deque 接口，即能将LinkedList当作双端队列使用。

当LinkedList被当做栈来使用时，常用api及对应关系如下：

栈方法    等效方法

- push(e)   addFirst(e)
- pop()    removeFirst()
- peek()    peekFirst()   isEmpty() //判断是否为空



## 队列的实现

java中虽然有Queue接口，但java并没有给出具体的队列实现类，而Java中让LinkedList类实现了Queue接口，所以使用队列的时候，一般采用LinkedList。因为LinkedList是双向链表，可以很方便的实现队列的所有功能。

Queue使用时要尽量避免Collection的add()和remove()方法，而是要使用offer()来加入元素，使用poll()来获取并移出元素。它们的优点是通过返回值可以判断成功与否，add()和remove()方法在失败的时候会抛出异常。 如果要使用前端而不移出该元素，使用element()或者peek()方法。

java中定义队列 一般这样定义： `Queue<E> queue = new LinkedList<E>();`

**当采用LinkedList来实现时，api的使用和对用关系如下：**

队列方法    等效方法

- offer(e)   offer(e)/offerLast(e) //进队列，将元素加入队列末尾
- poll()    poll()/pollFirst() //获取队列头的元素并移除
- peek()    peek()/peekFirst() //获取队列头的元素    isEmpty() //判断是否为空



## LinkedList辨析

LinkedList涉及到元素的增删有三组：

【push和offer的插入位置不一样】

- 用于栈：push、pop
- 用于队列：offer、poll
- 用于队列2【可能会抛出异常】：add、remove

**（peek返回栈顶/队首元素，但不会移除该元素）**

```java
//当栈来使用，先进后出
//主要调用方法：进栈push;出栈pop;
LinkedList<Integer> stack = new LinkedList<>();
stack.push(1);
stack.push(2);
stack.push(3);
System.out.println(stack.peek());
while (!stack.isEmpty()) {
    System.out.print(stack.pop());
}
System.out.println();
//当队列来使用，先进先出
//主要调用方法：进栈push;出栈pop;
Queue<Integer> queue = new LinkedList<>();
queue.offer(1);
queue.offer(2);
queue.offer(3);
System.out.println(queue.peek());
while (!queue.isEmpty()) {
    System.out.print(queue.poll());
}

输出：
3
321
1
123
```

