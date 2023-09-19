1. Java 中的几种基本数据类型是什么？对应的包装类型是什么？各自占用多少字节呢？
2. 包装类型的缓存机制了解么？
3. 自动装箱与拆箱了解吗？原理是什么？
4. String 、 StringBuffer 和 StringBuilder 的区别是什么? String 为什么是不可变的?
5. String s1 = new String("abc");这段代码创建了几个字符串对象？
6. == 与 equals?hashCode 与 equals ?
7. 重载和重写
8. 深拷贝和浅拷贝区别了解吗？什么是引用拷贝？
9. 谈谈对 Java 注解的理解，解决了什么问题？
10. Exception 和 Error 有什么区别？
11. Java 反射？反射有什么缺点？你是怎么理解反射的（为什么框架需要反射）？
12. Java 泛型了解么？什么是类型擦除？介绍一下常用的通配符？
13. 内部类了解吗？匿名内部类了解吗？
14. BIO,NIO,AIO 有什么区别?

### Java 数据类型

![[Pasted image 20230227150817.png]]

### 字符串

1. String，StringBuilder，StringBuffer

**可变性**

String是不可变的。

String中保存字符串的数组被 `final` 和 `private`修饰，并且`String` 类没有提供修改这个字符串的方法，导致字符串数组在外部和内部都无法改变。

`StringBuilder` 与 `StringBuffer` 都继承自 `AbstractStringBuilder` 类，在 `AbstractStringBuilder` 中也是使用字符数组保存字符串，不过没有使用 `final` 和 `private` 关键字修饰，最关键的是这个 `AbstractStringBuilder` 类还提供了很多修改字符串的方法比如 `append` 方法。

**线程安全性**

`String` 中的对象是不可变的，也就可以理解为常量，线程安全。`StringBuffer` 对方法加了同步锁，是线程安全的。`StringBuilder` 并没有对方法进行加同步锁，是非线程安全的。

**性能**

每次对 `String` 类型进行改变的时候，都会生成一个新的 `String` 对象，然后将指针指向新的 `String` 对象。`StringBuffer` 每次都会对 `StringBuffer` 对象本身进行操作，而不是生成新的对象并改变对象引用。相同情况下使用 `StringBuilder` 相比使用 `StringBuffer` 仅能获得 10%~15% 左右的性能提升，但却要冒多线程不安全的风险。

**对于三者使用的总结：**

1.  操作少量的数据: 适用 `String`
2.  单线程操作字符串缓冲区下操作大量数据: 适用 `StringBuilder`
3.  多线程操作字符串缓冲区下操作大量数据: 适用 `StringBuffer`

2. String s1 = new String("abc")

会创建 1 或 2 个字符串对象。

1、如果字符串常量池中不存在字符串对象“abc”，那么会在堆中创建 2 个字符串对象“abc”。
堆中创建字符串对象“abc”并在字符串常量池中保存对应的引用

2、如果字符串常量池中已存在字符串对象“abc”，则只会在堆中创建 1 个字符串对象“abc”。

3. 常量折叠

先来看字符串不加 `final` 关键字拼接的情况（JDK1.8）：

```java
String str1 = "str"; //常量池中的对象
String str2 = "ing"; //常量池中的对象
String str3 = "str" + "ing"; //常量池中的对象
String str4 = str1 + str2; //堆中的创建的新对象
String str5 = "string";
System.out.println(str3 == str4);//false
System.out.println(str3 == str5);//true
System.out.println(str4 == str5);//false
```

对于编译期可以确定值的字符串，也就是常量字符串 ，jvm 会将其存入字符串常量池。

对于 `String str3 = "str" + "ing";` 编译器会给你优化成 `String str3 = "string";` 。

**引用的值在程序编译期是无法确定的，编译器无法对其进行优化。**

4. intern()

`String.intern()` 是一个 native（本地）方法，其作用是将指定的字符串对象的引用保存在字符串常量池中，可以简单分为两种情况：

-   如果字符串常量池中保存了对应的字符串对象，就直接返回该引用。
-   如果字符串常量池中没有保存对应的字符串对象，那就在常量池中创建该字符串对象并返回。

示例代码（JDK 1.8） :

```java
// 在堆中创建字符串对象”Java“
// 将字符串对象”Java“的引用保存在字符串常量池中
String s1 = "Java";
// 直接返回字符串常量池中字符串对象”Java“对应的引用
String s2 = s1.intern();
// 会在堆中在单独创建一个字符串对象
String s3 = new String("Java");
// 直接返回字符串常量池中字符串对象”Java“对应的引用
String s4 = s3.intern();
// s1 和 s2 指向的是堆中的同一个对象
System.out.println(s1 == s2); // true
// s3 和 s4 指向的是堆中不同的对象
System.out.println(s3 == s4); // false
// s1 和 s4 指向的是堆中的同一个对象
System.out.println(s1 == s4); //true
```

### == 、equals() 、hashcode()

-   对于基本数据类型来说 ，== 比较的是值。
-   对于引用数据类型来说，== 比较的是对象的内存地址。

`equals()` 方法存在两种使用情况：

-   **类没有重写 `equals()`方法** ：通过`equals()`比较该类的两个对象时，等价于通过 “== ” 比较这两个对象，使用的默认是 `Object`类`equals()`方法。
-   **类重写了 `equals()`方法** ：一般我们都重写 `equals()`方法来比较两个对象中的属性是否相等；若它们的属性相等，则返回 true(即，认为这两个对象相等)。

`hashCode()` 的作用是获取哈希码（`int` 整数），也称为散列码。这个哈希码的作用是确定该对象在哈希表中的索引位置。

对象加入 `HashSet` 时，`HashSet` 会先计算对象的 `hashCode` 值，如果没有相等的 `hashCode`，`HashSet` 会假设对象没有重复出现。如果有，这时会调用 `equals()` 方法来检查 `hashCode` 相等的对象是否真的相同。如果两者相同，`HashSet` 就不会让其加入操作成功。如果不同的话，就会重新散列到其他位置。这样可以减少 `equals` 的次数，提高执行速度。

-   如果两个对象的`hashCode` 值相等，那这两个对象不一定相等（哈希碰撞）。
-   如果两个对象的`hashCode` 值相等并且`equals()`方法也返回 `true`，我们才认为这两个对象相等。
-   如果两个对象的`hashCode` 值不相等，我们就可以直接认为这两个对象不相等。

-   `equals` 方法判断两个对象是相等的，那这两个对象的 `hashCode` 值也要相等。
-   两个对象有相同的 `hashCode` 值，他们也不一定是相等的（哈希碰撞）。

### 抽象类和接口

1. 接⼝的⽅法默认是 public ，所有⽅法在接⼝中不能有实现(Java 8 开始接⼝⽅法可以有默认实现）。
    
2. 接⼝中除了 static 、 final 变量，不能有其他变量。
    
3. jdk 8 的时候接⼝可以有默认⽅法和静态⽅法功能。
    
4. jdk 9 在接⼝中引⼊了私有⽅法和私有静态⽅法。
	
5. 单继承，多个实现。
	
6. 概念：实现类，子类。

### final 关键字有什么作用？

final 表示不可变的意思，可用于修饰类、属性和方法：

- 被 final 修饰的类不可以被继承
    
- 被 final 修饰的方法不可以被重写
    
- 被 final 修饰的变量不可变，被 final 修饰的变量必须被显式第指定初始值，还得注意的是，这里的不可变指的是变量的引用不可变，不是引用指向的内容的不可变。

### 重载 vs 重写

![[Pasted image 20230227150514.png]]

### 包装类型的缓存机制

Java 基本数据类型的包装类型的大部分都用到了缓存机制来提升性能。

`Byte`,`Short`,`Integer`,`Long` 这 4 种包装类默认创建了数值 **[-128，127]** 的相应类型的缓存数据，`Character` 创建了数值在 **[0,127]** 范围的缓存数据，`Boolean` 直接返回 `True` or `False`。

两种浮点数类型的包装类 `Float`,`Double` 并没有实现缓存机制。

```java
Integer i1 = 40;
Integer i2 = new Integer(40);
//`i1` 直接使用的是缓存中的对象。而`Integer i2 = new Integer(40)` 会直接创建新的对象。
System.out.println(i1==i2);//false
```

记住：**所有整型包装类对象之间值的比较，全部使用 equals 方法比较**。

### 自动装箱与拆箱

-   **装箱**：将基本类型用它们对应的引用类型包装起来；
-   **拆箱**：将包装类型转换为基本数据类型；

从字节码中，我们发现装箱其实就是调用了 包装类的`valueOf()`方法，拆箱其实就是调用了 `xxxValue()`方法。

-   `Integer i = 10` 等价于 `Integer i = Integer.valueOf(10)`
-   `int n = i` 等价于 `int n = i.intValue()`;

注意：**如果频繁拆装箱的话，也会严重影响系统的性能。我们应该尽量避免不必要的拆装箱操作。**
### 序列化

-   **序列化**： 将数据结构或对象转换成二进制字节流的过程
-   **反序列化**：将在序列化过程中所生成的二进制字节流转换成数据结构或者对象的过程

下面是序列化和反序列化常见应用场景：

-   对象在进行网络传输（比如远程方法调用 RPC 的时候）之前需要先被序列化，接收到序列化的对象之后需要再进行反序列化；
-   将对象存储到文件之前需要进行序列化，将对象从文件中读取出来需要进行反序列化；
-   将对象存储到数据库（如 Redis）之前需要用到序列化，将对象从缓存数据库中读取出来需要反序列化；
-   将对象存储到内存之前需要进行序列化，从内存中读取出来之后需要进行反序列化。

序列化协议属于 TCP/IP 协议应用层的一部分

JDK 自带的序列化方式一般不会用 ，因为序列化效率低并且存在安全问题。比较常用的序列化协议有 Hessian、Kryo、Protobuf、ProtoStuff，这些都是基于二进制的序列化协议。

像 JSON 和 XML 这种属于文本类序列化方式。虽然可读性比较好，但是性能较差，一般不会选择。

JDK 自带的序列化，只需实现 `java.io.Serializable`接口即可。

```java
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private RpcMessageTypeEnum rpcMessageTypeEnum;
}
```

**serialVersionUID 有什么作用？**

序列化号 `serialVersionUID` 属于版本控制的作用。反序列化时，会检查 `serialVersionUID` 是否和当前类的 `serialVersionUID` 一致。如果 `serialVersionUID` 不一致则会抛出 `InvalidClassException` 异常。强烈推荐每个序列化类都手动指定其 `serialVersionUID`，如果不手动指定，那么编译器会动态生成默认的 `serialVersionUID`。

**serialVersionUID 不是被 static 变量修饰了吗？为什么还会被“序列化”？**

`static` 修饰的变量是静态变量，位于方法区，本身是不会被序列化的。 `static` 变量是属于类的而不是对象。你反序列之后，`static` 变量的值就像是默认赋予给了对象一样，看着就像是 `static` 变量被序列化，实际只是假象罢了。

**如果有些字段不想进行序列化怎么办？**

对于不想进行序列化的变量，可以使用 `transient` 关键字修饰。

`transient` 关键字的作用是：阻止实例中那些用此关键字修饰的的变量序列化；当对象被反序列化时，被 `transient` 修饰的变量值不会被持久化和恢复。

关于 `transient` 还有几点注意：

-   `transient` 只能修饰变量，不能修饰类和方法。
-   `transient` 修饰的变量，在反序列化后变量值将会被置成类型的默认值。例如，如果是修饰 `int` 类型，那么反序列后结果就是 `0`。
-   `static` 变量因为不属于任何对象(Object)，所以无论有没有 `transient` 关键字修饰，均不会被序列化。

**为什么不推荐使用 JDK 自带的序列化？**

我们很少或者说几乎不会直接使用 JDK 自带的序列化方式，主要原因有下面这些原因：

-   **不支持跨语言调用** : 如果调用的是其他语言开发的服务的时候就不支持了。
-   **性能差** ：相比于其他序列化框架性能更低，主要原因是序列化之后的字节数组体积较大，导致传输成本加大。
-   **存在安全问题** ：序列化和反序列化本身并不存在问题。但当输入的反序列化的数据可被用户控制，那么攻击者即可通过构造恶意输入，让反序列化产生非预期的对象，在此过程中执行构造的任意代码。相关阅读

### 反射

反射你可以获取任意一个类的所有属性和方法，你还可以调用这些方法和属性。

**1. 知道具体类的情况下可以使用：**

```
Class alunbarClass = TargetObject.class;
```

但是我们一般是不知道具体类的，基本都是通过遍历包下面的类来获取 Class 对象，通过此方式获取 Class 对象不会进行初始化

**2. 通过 `Class.forName()`传入类的全路径获取：**

```
Class alunbarClass1 = Class.forName("cn.javaguide.TargetObject");
```

**3. 通过对象实例`instance.getClass()`获取：**

```
TargetObject o = new TargetObject();
Class alunbarClass2 = o.getClass();
```

**4. 通过类加载器`xxxClassLoader.loadClass()`传入类路径获取:**

```
ClassLoader.getSystemClassLoader().loadClass("cn.javaguide.TargetObject");
```

通过类加载器获取 Class 对象不会进行初始化，意味着不进行包括初始化等一系列步骤，静态代码块和静态对象不会得到执行

### 泛型

**一、当泛型遇到重载**

```java
public class GenericTypes {

    public static void method(List<String> list) {
        System.out.println("invoke method(List<String> list)");
    }

    public static void method(List<Integer> list) {
        System.out.println("invoke method(List<Integer> list)");
    }
}
```

上面这段代码，有两个重载的函数，因为他们的参数类型不同，一个是`List<String>`另一个是`List<Integer>` ，但是，这段代码是编译通不过的。因为我们前面讲过，参数`List<Integer>`和`List<String>`编译之后都被擦除了，变成了一样的原生类型 List，擦除动作导致这两个方法的特征签名变得一模一样。

**二、当泛型遇到 catch**

泛型的类型参数不能用在 Java 异常处理的 catch 语句中。因为异常处理是由 JVM 在运行时刻来进行的。由于类型信息被擦除，JVM 是无法区分两个异常类型`MyException<String>`和`MyException<Integer>`的

**三、当泛型内包含静态变量**

```java
public class StaticTest{
    public static void main(String[] args){
        GT<Integer> gti = new GT<Integer>();
        gti.var=1;
        GT<String> gts = new GT<String>();
        gts.var=2;
        System.out.println(gti.var);
    }
}
class GT<T>{
    public static int var=0;
    public void nothing(T x){}
}
```

以上代码输出结果为：2！

由于经过类型擦除，所有的泛型类实例都关联到同一份字节码上，泛型类的所有静态变量是共享的。

### 异常

在 Java 中，所有的异常都有一个共同的祖先 `java.lang` 包中的 `Throwable` 类。`Throwable` 类有两个重要的子类:

-   `Error`
	- `Error` 属于程序无法处理的错误 ，不建议通过`catch`捕获 。例如 Java 虚拟机运行错误（`Virtual MachineError`）、虚拟机内存不够错误(`OutOfMemoryError`)、类定义错误（`NoClassDefFoundError`）等 。这些异常发生时，Java 虚拟机（JVM）一般会选择线程终止。

-   **`Exception`** :程序本身可以处理的异常，可以通过 `catch` 来进行捕获。分为两类
	
	- `Checked Exception` 即 受检查异常 ，如果没有被 `catch`或者`throws` 关键字处理的话，就没办法通过编译。例如：IO 相关的异常、`ClassNotFoundException` 、`SQLException`...。
	
	- `RuntimeException` 即 运行时异常 
		-   `NullPointerException`(空指针错误)
		-   `IllegalArgumentException`(参数错误比如方法入参类型错误)
		-   `NumberFormatException`（字符串转换为数字格式错误，`IllegalArgumentException`的子类）
		-   `ArrayIndexOutOfBoundsException`（数组越界错误）
		-   `ClassCastException`（类型转换错误）
		-   `ArithmeticException`（算术错误）
		-   `SecurityException` （安全错误比如权限不够）
		-   `UnsupportedOperationException`(不支持的操作错误比如重复创建同一用户)

`try-with-resources` 

1.  **适用范围（资源的定义）：** 任何实现 `java.lang.AutoCloseable`或者 `java.io.Closeable` 的对象
2.  **关闭资源和 finally 块的执行顺序：** 在 `try-with-resources` 语句中，任何 catch 或 finally 块在声明的资源关闭后运行

通过使用分号分隔，可以在`try-with-resources`块中声明多个资源。

```java
try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File("test.txt")));
     BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(new File("out.txt")))) {
    int b;
    while ((b = bin.read()) != -1) {
        bout.write(b);
    }
}
catch (IOException e) {
    e.printStackTrace();
}
```

### BIO NIO AIO

  
![BIO、NIO、AIO](https://cdn.tobebetterjavaer.com/tobebetterjavaer/images/sidebar/sanfene/javase-26.png)

**BIO**(blocking I/O) ： 就是传统的 IO，同步阻塞，服务器实现模式为一个连接一个线程，即**客户端有连接请求时服务器端就需要启动一个线程进行处理**，如果这个连接不做任何事情会造成不必要的线程开销，可以通过连接池机制改善(实现多个客户连接服务器)。

![BIO、NIO、AIO](https://cdn.tobebetterjavaer.com/tobebetterjavaer/images/sidebar/sanfene/javase-27.png)


BIO 方式适用于连接数目比较小且固定的架构，这种方式对服务器资源要求比较高，并发局限于应用中，JDK1.4 以前的唯一选择，程序简单易理解。

**NIO** ：全称 java non-blocking IO，是指 JDK 提供的新 API。从 JDK1.4 开始，Java 提供了一系列改进的输入/输出的新特性，被统称为 NIO(即 New IO)。

NIO 是**同步非阻塞**的，服务器端用一个线程处理多个连接，客户端发送的连接请求会注册到多路复用器上，多路复用器轮询到连接有 IO 请求就进行处理：

NIO 的数据是面向**缓冲区 Buffer**的，必须从 Buffer 中读取或写入。

![NIO完整示意图](https://cdn.tobebetterjavaer.com/tobebetterjavaer/images/sidebar/sanfene/javase-29.png)

可以看出，NIO 的运行机制：

- 每个 Client 对应一个 Buffer。
- Selector 对应一个线程，一个线程对应多个 Channel。
- Selector 会根据不同的事件，在各个通道上切换。
- Buffer 是内存块，底层是数据。

**AIO**：JDK 7 引入了 Asynchronous I/O，是**异步不阻塞**的 IO。在进行 I/O 编程中，常用到两种模式：Reactor 和 Proactor。Java 的 NIO 就是 Reactor，当有事件触发时，服务器端得到通知，进行相应的处理，完成后才通知服务端程序启动线程去处理，一般适用于连接数较多且连接时间较长的应用。

