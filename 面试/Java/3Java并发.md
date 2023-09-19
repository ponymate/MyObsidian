1. 线程和进程的区别。
2. 什么是上下文切换?
3. 什么是线程死锁?如何避免死锁?
4. 说说 sleep() 方法和 wait() 方法区别和共同点?
5. 乐观锁和悲观锁了解么？
6. 讲一下 JMM(Java 内存模型)。
7. 并发编程的三个重要特征
8. volatile 关键字解决了什么问题？说说 synchronized 关键字和 volatile 关键字的区别。
9. ThreadLocal
10. AQS 原理了解么？AQS 组件有哪些？
11. 用过 CountDownLatch 么？什么场景下用的？
12. 实现 Runnable 接口和 Callable 接口的区别。
13. Java 线程池有哪些参数？阻塞队列有几种？拒绝策略有几种？新线程添加的流程?
## 线程进程

一个进程中可以有多个线程，多个线程共享进程的**堆**和**方法区** (JDK1.8 之后的**元空间**)资源，但是每个线程有自己的**程序计数器**、**虚拟机栈** 和 **本地方法栈**。

线程是进程划分成的更小的运行单位。线程和进程最大的不同在于基本上各进程是独立的，而各线程则不一定，因为同一进程中的线程极有可能会相互影响。线程执行开销小，但不利于资源的管理和保护；而进程正相反。

## 并发编程三个重要特性

原子性

一次操作或者多次操作，要么所有的操作全部都得到执行并且不会受到任何因素的干扰而中断，要么都不执行。

可见性

当一个线程对共享变量进行了修改，那么另外的线程都是立即可以看到修改后的最新值。

在 Java 中，可以借助`synchronized` 、`volatile` 以及各种 `Lock` 实现可见性。

如果我们将变量声明为 `volatile` ，这就指示 JVM，这个变量是共享且不稳定的，每次使用它都到主存中进行读取。

有序性

由于指令重排序问题，代码的执行顺序未必就是编写代码时候的顺序。

指令重排序可以保证串行语义一致，但是没有义务保证多线程间的语义也一致 ，所以在多线程下，指令重排序可能会导致一些问题。

在 Java 中，`volatile` 关键字可以禁止指令进行重排序优化。

## sleep() vs wait()

**共同点** ：两者都可以暂停线程的执行。

**区别** ：

-   `sleep()` 方法没有释放锁，而 `wait()` 方法释放了锁。
-   `wait()` 通常被用于线程间交互/通信，`sleep()`通常被用于暂停执行。
-   `wait()` 方法被调用后，线程不会自动苏醒，需要别的线程调用同一个对象上的 `notify()`或者 `notifyAll()` 方法。`sleep()`方法执行完成后，线程会自动苏醒，或者也可以使用 `wait(long timeout)` 超时后线程会自动苏醒。

`sleep()` 是 `Thread` 类的静态本地方法，`wait()` 则是 `Object` 类的本地方法。

`wait()` 是让获得对象锁的线程实现等待，会自动释放当前线程占有的对象锁。每个对象都拥有对象锁，既然要释放当前线程占有的对象锁并让其进入 WAITING 状态，自然是要操作对应的对象而非当前的线程。

`sleep()` 是让当前线程暂停执行，不涉及到对象类，也不需要获得对象锁。

## JMM

JMM是Java内存模型（Java Memory Model）的缩写。

![[Pasted image 20230715150149.png]]

Java虚拟机中的所有线程共享主存储器，每个线程有自己的本地内存。当线程访问变量时，它们首先将变量从主存储器复制到本地内存中，对该变量的所有操作都在本地内存中进行，最后将变量的最新值写回主存储器。

"Happens-before" 原则是Java内存模型中定义的一组规则，定义操作之间的先后顺序，描述两个操作之间的内存可见性。

happens-before 原则的定义：

-   如果一个操作 happens-before 另一个操作，那么第一个操作的执行结果将对第二个操作可见，并且第一个操作的执行顺序排在第二个操作之前。
-   两个操作之间存在 happens-before 关系，并不意味着 Java 平台的具体实现必须要按照 happens-before 关系指定的顺序来执行。如果重排序之后的执行结果，与按 happens-before 关系来执行的结果一致，那么 JMM 也允许这样的重排序。

happens-before 原则的设计思想其实非常简单：

-   为了对编译器和处理器的约束尽可能少，只要不改变程序的执行结果，编译器和处理器怎么进行重排序优化都行。
-   对于会改变程序执行结果的重排序，JMM 要求编译器和处理器必须禁止这种重排序。

## 乐观锁

悲观锁每次在获取资源操作的时候都会上锁，**共享资源每次只给一个线程使用，其它线程阻塞，用完后再把资源转让给其它线程**。

乐观锁只是在提交修改的时候去验证对应的资源是否被其它线程修改了。

高并发的场景下，使用悲观锁存在激烈的锁竞争会造成线程阻塞，还可能会存在死锁问题。
乐观锁不存在锁竞争和死锁的问题，在性能上往往会更胜一筹。但是，如果冲突频繁发生，会频繁失败和重试，这样同样会非常影响性能。

**悲观锁通常多用于写比较多的情况下，避免频繁失败和重试影响性能。**

**乐观锁通常多于写比较少的情况下，避免频繁加锁影响性能。**

乐观锁：原子类，ConcurrentHashMap

悲观锁：`synchronized`、`ReentrantLock`

**乐观锁实现方式：**

**1.  版本号机制**

一般是在数据表中加上一个数据版本号 `version` 字段。当数据被修改时，`version` 值会加一。在提交更新时，若读取到的 version 值和当前数据库中的 `version` 值相等时才更新，否则重试更新操作，直到更新成功。

**2. CAS （Compare And Swap）算法**

CAS 是一个原子操作，底层依赖于一条 CPU 的原子指令。

CAS 涉及到三个操作数：
-   **V** ：要更新的变量值(Var)
-   **E** ：预期值(Expected)
-   **N** ：拟写入的新值(New)

当且仅当 V 的值等于 E 时，CAS 通过原子方式用新值 N 来更新 V 的值。如果不等，说明已经有其它线程更新了 V，则当前线程放弃更新。

**乐观锁的问题：**

ABA 问题：

两次读取的数据相同，但是期间数据也可能被修改，只是经过修改之后的数据和修改之前的数据相同而已。ABA 问题的解决思路是在变量前面追加上**版本号或者时间戳**。

## ThreadLocal

`ThreadLocal`类主要解决的就是让每个线程绑定自己的值，每个线程都会有`ThreadLocal`变量的本地副本。使用 `ThreadLocal` 的 `get()` 和 `set()` 方法来设置和获取本地变量副本的值，从而避免了线程安全问题。

![[threadlocal-data-structure.png]]

```java
public class Thread implements Runnable {
    //......
    //与此线程有关的ThreadLocal值。由ThreadLocal类维护
    ThreadLocal.ThreadLocalMap threadLocals = null;

    //与此线程有关的InheritableThreadLocal值。由InheritableThreadLocal类维护
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
    //......
}
```

从上面 `Thread` 类源代码可以看出 `ThreadLocalMap`是 `ThreadLocal` 的静态内部类。
`Thread` 类中有一个 `threadLocals` 和 一个 `inheritableThreadLocals` 变量，它们都是 `ThreadLocalMap` 类型的变量，我们可以把 `ThreadLocalMap` 理解为`ThreadLocal` 类实现的定制化的 `HashMap`。默认情况下这两个变量都是 null，只有当前线程调用 `ThreadLocal` 类的 `set()` 或`get()` 方法时才创建它们，实际上调用这两个方法的时候，我们调用的是`ThreadLocalMap`类对应的 `get()`、`set()`方法。

最终的变量是放在了当前线程的 `ThreadLocalMap` 中，并不是存在 `ThreadLocal` 上，`ThreadLocal` 可以理解为只是`ThreadLocalMap`的封装，传递了变量值。

`ThreadLocal`类的`set()` 和 `get()` 方法

```java
public void set(T value) {
    //获取当前请求的线程
    Thread t = Thread.currentThread();
    //取出 Thread 类内部的 threadLocals 变量(哈希表结构)
    ThreadLocalMap map = getMap(t);
    if (map != null)
        // 将需要存储的值放入到这个哈希表中
        map.set(this, value);
    else
        createMap(t, value);
}

ThreadLocalMap getMap(Thread t) {
	return t.threadLocals;
}
```

可以看到相同的 `Thread` 只有一个 `ThreadLocalMap` ， `ThreadLocalMap` 可以通过 `Thread` 获取。
比如我们在同一个线程中声明了两个 `ThreadLocal` 对象的话， `Thread` 内部都是使用同一个`ThreadLocalMap` 存放数据的，`ThreadLocalMap` 的 key 是 `ThreadLocal`对象，value 就是`ThreadLocal` 调用`set`方法设置的值。

每个`Thread`中都只有一个`ThreadLocalMap`，而`ThreadLocalMap`可以存储以`ThreadLocal`为 key ，Object 对象为 value 的键值对。

**内存泄漏问题**

如果 `ThreadLocal` 没有被外部强引用，且在垃圾回收时，`ThreadLocal` 对象被清理，那么在 `ThreadLocalMap` 中会出现 key 为 null 的 Entry。由于 `ThreadLocalMap` 的实现是使用数组和链表结构，而数组的大小是固定的，不会自动缩小，这就可能导致潜在的内存泄漏问题。
如果没有适当的措施来清理这些过期的 Entry，`ThreadLocalMap` 中的 value 对象将一直被保留，无法被垃圾回收，从而导致内存泄漏。

`ThreadLocalMap` 实现中已经考虑了这种情况，在调用 `set()`、`get()`、`remove()` 方法的时候，会清理掉 key 为 null 的记录。使用完 `ThreadLocal`方法后 最好手动调用`remove()`方法

## volatile

1. 保证变量的可见性

如果我们将变量声明为 **`volatile`** ，这就指示 JVM，这个变量是共享且不稳定的，每次使用它都到主存中进行读取。

2. 防止 JVM 的指令重排序。

如果我们将变量声明为 **`volatile`** ，在对这个变量进行读写操作的时候，会通过插入特定的 **内存屏障** 的方式来禁止指令重排序。

**双重校验锁实现对象单例（线程安全）** ：

```java
public class Singleton {

    private volatile static Singleton uniqueInstance;

    private Singleton() {
    }

    public static Singleton getUniqueInstance() {
        //先判断对象是否已经实例过，没有实例化过才进入加锁代码
        if (uniqueInstance == null) {
            //类对象加锁
            synchronized (Singleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
```

`uniqueInstance` 采用 `volatile` 关键字修饰也是很有必要的， `uniqueInstance = new Singleton();` 这段代码其实是分为三步执行：

1.  为 `uniqueInstance` 分配内存空间
2.  初始化 `uniqueInstance`
3.  将 `uniqueInstance` 指向分配的内存地址

但是由于 JVM 具有指令重排的特性，执行顺序有可能变成 1->3->2。指令重排在单线程环境下不会出现问题，但是在多线程环境下会导致一个线程获得还没有初始化的实例。例如，线程 T1 执行了 1 和 3，此时 T2 调用 `getUniqueInstance`() 后发现 `uniqueInstance` 不为空，因此返回 `uniqueInstance`，但此时 `uniqueInstance` 还未被初始化。

## synchronized

`synchronized` 是 Java 中的一个关键字，翻译成中文是同步的意思，主要解决的是多个线程之间访问资源的同步性，可以保证被它修饰的方法或者代码块在任意时刻只能有一个线程执行。

`synchronized` 关键字的使用方式主要有下面 3 种：

1.  修饰实例方法 （锁当前对象实例）
2.  修饰静态方法（锁当前类）

静态 `synchronized` 方法和非静态 `synchronized` 方法之间的调用不互斥！因为访问静态 `synchronized` 方法占用的锁是当前类的锁，而访问非静态 `synchronized` 方法占用的锁是当前实例对象锁。

3.  修饰代码块，对括号里指定的对象/类加锁：

-   `synchronized(object)` 表示进入同步代码库前要获得 **给定对象的锁**。
-   `synchronized(类.class)` 表示进入同步代码前要获得 **给定 Class 的锁**

- 尽量不要使用 `synchronized(String a)` 因为 JVM 中，字符串常量池具有缓存功能。
- 构造方法不能使用 synchronized 关键字修饰。构造方法本身就属于线程安全的，不存在同步的构造方法一说。

**锁的升级流程**

在Java中，锁的升级流程可以分为三个阶段：偏向锁、轻量级锁和重量级锁，具体流程如下：

1.  偏向锁阶段：当一个线程访问一个同步块时，JVM会将该对象的对象头标记为偏向锁，并将线程ID记录在对象头中。接下来，该线程每次访问该对象时，不需要再进行同步操作，因为偏向锁已经将该对象锁定给了该线程。
    
2.  轻量级锁阶段：当另一个线程尝试访问同一个同步块时，JVM会将该对象的对象头标记为轻量级锁，轻量级锁使用CAS操作来进行同步操作。如果同步失败，则表示有其他线程持有锁，该线程会将锁膨胀为重量级锁。
    
3.  重量级锁阶段：重量级锁使用操作系统互斥量来实现同步，当一个线程持有锁时，其他线程无法获取锁，只能等待锁的释放。

## AQS

`AbstractQueuedSynchronizer` 抽象队列同步器。

### 底层原理

AQS 核心思想是，如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且将共享资源设置为锁定状态。如果被请求的共享资源被占用，那么就将暂时获取不到锁的线程加入到CLH队列中。

CLH(Craig,Landin,and Hagersten) 队列是一个虚拟的双向队列（虚拟的双向队列即不存在队列实例，仅存在结点之间的关联关系）。AQS 是将每条请求共享资源的线程封装成一个 CLH 锁队列的一个结点。在 CLH 同步队列中，一个节点表示一个线程，它保存着线程的引用（thread）、 当前节点在队列中的状态（waitStatus）、前驱节点（prev）、后继节点（next）。

CLH 队列结构如下图所示：

![[Pasted image 20230715153914.png]]

AQS 使用 int 成员变量 `state` 表示同步状态，通过内置的线程等待队列完成获取资源线程的排队工作。

`state` 变量由 `volatile` 修饰，用于展示当前临界资源的获锁情况。

```java
// 共享变量，使用volatile修饰保证线程可见性
private volatile int state;
```

以 `ReentrantLock` 为例，`state` 初始值为 0，表示未锁定状态。
A 线程 `lock()` 时，会调用 `tryAcquire()` 独占该锁并将 `state+1` 。此后，其他线程再 `tryAcquire()` 时就会失败，直到 A 线程 `unlock()` 到 `state=0`（即释放锁）为止，其它线程才有机会获取该锁。
当然，释放锁之前，A 线程自己是可以重复获取此锁的（`state` 会累加），这就是可重入的概念。但要注意，获取多少次就要释放多少次，这样才能保证 state 是能回到零态的。
### Semaphore

`synchronized` 和 `ReentrantLock` 都是一次只允许一个线程访问某个资源，而`Semaphore`(信号量)可以用来控制同时访问特定资源的线程数量。

Semaphore 的使用简单，下面的代码表示同一时刻 N 个线程中只有 5 个线程能获取到共享资源，其他线程都会阻塞，只有获取到共享资源的线程才能执行。

```java
// 初始共享资源数量
final Semaphore semaphore = new Semaphore(5);
// 获取1个许可
semaphore.acquire();
// 释放1个许可
semaphore.release();
```

调用`semaphore.acquire()` ，线程尝试获取许可证，如果 `state >= 0` 的话，则表示可以获取成功。如果获取成功的话，使用 CAS 操作去修改 `state` 的值 `state=state-1`。如果 `state<0` 的话，则表示许可证数量不足。此时会创建一个 Node 节点加入阻塞队列，挂起当前线程。
调用`semaphore.release();` ，线程尝试释放许可证，并使用 CAS 操作去修改 `state` 的值 `state=state+1`。释放许可证成功之后，同时会唤醒同步队列中的一个线程。被唤醒的线程会重新尝试去修改 `state` 的值 `state=state-1` ，如果 `state>=0` 则获取令牌成功，否则重新进入阻塞队列，挂起线程。

```java
/**
 *
 * @author Snailclimb
 * @date 2018年9月30日
 * @Description: 需要一次性拿一个许可的情况
 */
public class SemaphoreExample1 {
	// 请求的数量
	private static final int threadCount = 550;
	
	public static void main(String[] args) throws InterruptedException {
		// 创建一个具有固定线程数量的线程池对象（如果这里线程池的线程数量给太少的话你会发现执行的很慢）
		ExecutorService threadPool = Executors.newFixedThreadPool(300);
		// 初始许可证数量
		final Semaphore semaphore = new Semaphore(20);
		
		for (int i = 0; i < threadCount; i++) {
			final int threadnum = i;
			threadPool.execute(() -> {// Lambda 表达式的运用
				try {
					semaphore.acquire();// 获取一个许可，所以可运行线程数量为20/1=20
					test(threadnum);
					semaphore.release();// 释放一个许可
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		threadPool.shutdown();
		System.out.println("finish");
	}

	public static void test(int threadnum) throws InterruptedException {
		Thread.sleep(1000);// 模拟请求的耗时操作
		System.out.println("threadnum:" + threadnum);
		Thread.sleep(1000);// 模拟请求的耗时操作
	}
}
```

### CountDownLatch

`CountDownLatch` 允许 `count` 个线程阻塞在一个地方，直至所有线程的任务都执行完毕。

`CountDownLatch` 是一次性的，计数器的值只能在构造方法中初始化一次，之后没有任何机制再次对其设置值，当 `CountDownLatch` 使用完毕后，它不能再次被使用。

`CountDownLatch` 是共享锁的一种实现，它默认构造 AQS 的 `state` 值为 `count`。当线程使用 `countDown()` 方法时,其实使用了`tryReleaseShared`方法以 CAS 的操作来减少 `state`,直至 `state` 为 0 。当调用 `await()` 方法的时候，如果 `state` 不为 0，那就证明任务还没有执行完毕，`await()` 方法就会一直阻塞，也就是说 `await()` 方法之后的语句不会被执行。然后，`CountDownLatch` 会自旋 CAS 判断 `state == 0`，如果 `state == 0` 的话，就会释放所有等待的线程，`await()` 方法之后的语句得到执行。

**CountDownLatch 代码示例** ：

```java
/**
 *
 * @author SnailClimb
 * @date 2018年10月1日
 * @Description: CountDownLatch 使用方法示例
 */
public class CountDownLatchExample1 {
	// 请求的数量
	private static final int threadCount = 550;

	public static void main(String[] args) throws InterruptedException {
		// 创建一个具有固定线程数量的线程池对象（如果这里线程池的线程数量给太少的话你会发现执行的很慢）
		ExecutorService threadPool = Executors.newFixedThreadPool(300);
		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
		for (int i = 0; i < threadCount; i++) {
			final int threadnum = i;
			threadPool.execute(() -> {// Lambda 表达式的运用
				try {
					test(threadnum);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// 表示一个请求已经被完成
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();
		threadPool.shutdown();
		System.out.println("finish");
	}

	public static void test(int threadnum) throws InterruptedException {
		Thread.sleep(1000);// 模拟请求的耗时操作
		System.out.println("threadnum:" + threadnum);
		Thread.sleep(1000);// 模拟请求的耗时操作
	}
}

```

上面的代码中，我们定义了请求的数量为 550，当这 550 个请求被处理完成之后，才会执行`System.out.println("finish");`。

**使用场景**

`CountDownLatch` 的作用就是 允许 count 个线程阻塞在一个地方，直至所有线程的任务都执行完毕。之前在项目中，有一个使用多线程读取多个文件处理的场景，我用到了 `CountDownLatch` 。具体场景是下面这样的：

我们要读取处理 6 个文件，这 6 个任务都是没有执行顺序依赖的任务，但是我们需要返回给用户的时候将这几个文件的处理的结果进行统计整理。

为此我们定义了一个线程池和 count 为 6 的`CountDownLatch`对象 。使用线程池处理读取任务，每一个线程处理完之后就将 count-1，调用`CountDownLatch`对象的 `await()`方法，直到所有文件读取完之后，才会接着执行后面的逻辑。

伪代码是下面这样的：

```java
public class CountDownLatchExample1 {
    // 处理文件的数量
    private static final int threadCount = 6;

    public static void main(String[] args) throws InterruptedException {
        // 创建一个具有固定线程数量的线程池对象（推荐使用构造方法创建）
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadnum = i;
            threadPool.execute(() -> {
                try {
                    //处理文件的业务操作
                    //......
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //表示一个文件已经被完成
                    countDownLatch.countDown();
                }

            });
        }
        countDownLatch.await();
        threadPool.shutdown();
        System.out.println("finish");
    }
}
```

## 对比

### volatile、synchronized

volatile是变量在多线程之间的可见性，synchronize是多线程之间访问资源的同步性。

Volatile只能修饰变量，synchronized可以修饰方法，静态方法，代码块。

Volatile对任意单个变量的读/写具有原子性，但是类似于i++这种复合操作不具有原子性。而锁的互斥执行的特性可以确保对整个临界区代码执行具有原子性。

多线程访问volatile不会发生阻塞，而synchronized会发生阻塞。

### CAS、synchronized

synchronized和CAS（Compare and Swap）都是Java中用于线程同步的机制，但它们的原理和使用场景有所不同。

synchronized是一种重量级锁，当多个线程同时访问同一个对象的synchronized代码块或方法时，只有一个线程能够获取到锁，其他线程则需要等待锁的释放。因此，synchronized对于高并发场景下的性能开销较大，并且容易出现死锁等问题。

CAS是一种非阻塞算法，可以在并发情况下实现线程安全的操作。CAS基于一条cpu的原子命令，其基本原理是在操作共享变量时，先比较该变量的值是否为预期值，如果是，则将该变量的值修改为新值，否则不做任何操作。

synchronized和CAS的联系在于它们都是用于线程同步和实现线程安全的机制。在Java中，synchronized是实现线程同步和保证线程安全的主要手段，而CAS则在Java中被广泛用于实现高效的无锁数据结构，如ConcurrentHashMap、AtomicInteger等。在高并发的场景下，使用CAS可以避免锁竞争和线程阻塞的问题，从而提高程序的并发性能。
### synchronized、ReentrantLock

1. **两者都是可重入锁**

**可重入锁** 也叫递归锁，指的是线程可以再次获取自己的内部锁。比如一个线程获得了某个对象的锁，此时这个对象锁还没有释放，当其再次想要获取这个对象的锁的时候还是可以获取的，如果是不可重入锁的话，就会造成死锁。

JDK 提供的所有现成的 `Lock` 实现类，包括 `synchronized` 关键字锁都是可重入的。

2. **synchronized 依赖于 JVM 而 ReentrantLock 依赖于 API**

`synchronized` 是依赖于 JVM 实现的。

`ReentrantLock` 是 JDK 层面实现的，也就是 API 层面，需要 lock() 和 unlock() 方法配合 try/finally 语句块来完成，`ReentrantLock` 里面有一个内部类 `Sync`，`Sync` 继承 AQS（`AbstractQueuedSynchronizer`），添加锁和释放锁的大部分操作实际上都是在 `Sync` 中实现的。`Sync` 有公平锁 `FairSync` 和非公平锁 `NonfairSync` 两个子类。

3. **ReentrantLock 比 synchronized 增加了一些高级功能**

- **等待可中断** : 
	- **可中断锁** ：获取锁的过程中可以被中断，不需要一直等到获取锁之后才能进行其他逻辑处理。`ReentrantLock` 就属于是可中断锁。
	- **不可中断锁** ：一旦线程申请了锁，就只能等到拿到锁以后才能进行其他的逻辑处理。`synchronized` 就属于是不可中断锁。
- **可实现公平锁** : 
	- `ReentrantLock`默认情况是非公平的，可以通过 `ReentrantLock`类的`ReentrantLock(boolean fair)`构造方法来指定是否是公平的。
	- `synchronized`只能是非公平锁。所谓的公平锁就是先等待的线程先获得锁。
- **可实现选择性通知**: 
	- `synchronized`关键字与`wait()`和`notify()`/`notifyAll()`方法相结合可以实现等待/通知机制。
	- `ReentrantLock`类需要借助于`Condition`接口与`newCondition()`方法。
- **性能**：
	- 在低竞争情况下，`synchronized` 的性能比较好，因为它是 JVM 层面的内置锁，操作系统不需要进行用户态和内核态的切换。
	- 在高竞争和高并发情况下，`ReentrantLock` 的性能可能更好，因为它提供了更多的控制和灵活性。

- 当只需要简单的锁机制，并且不需要特定的高级特性时，`synchronized` 可以满足大部分需求，而且代码更简洁易读。
- 如果需要更高的灵活性，比如可中断锁、公平锁、多条件变量等，或者需要在高竞争场景下进行性能优化时，可以选择使用 `ReentrantLock`。

总的来说，两者都是合适的工具，具体选择要根据项目需求和性能特性来决定。在 Java 5 之前，`ReentrantLock` 提供了一些额外的功能，但随着 JDK 的更新，`synchronized` 也得到了一些改进，因此在大多数情况下，选择哪一个都可以。
