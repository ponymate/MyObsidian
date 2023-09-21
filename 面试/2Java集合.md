1. 说说 List,Set,Map 三者的区别？三者底层的数据结构？
2. 有哪些集合是线程不安全的？怎么解决呢？
3. ArrayList 和 LinkedList 的区别
4. ArrayList的扩容机制
5. Queue 和 Deque 的区别
6. 比较 HashSet、LinkedHashSet 和 TreeSet 三者的异同
7. HashMap 和 Hashtable 的区别？HashMap 和 HashSet 区别？HashMap 和 TreeMap 区别？
8. HashMap 的底层实现
9. HashMap 的长度为什么是 2 的幂次方
10. ConcurrentHashMap 和 Hashtable 的区别？
11. ConcurrentHashMap 线程安全的具体实现方式/底层具体实现

![[java-collection-hierarchy.png]]

### 集合底层数据结构

1. List

-   `ArrayList`： `Object[]` 数组
-   `Vector`：`Object[]` 数组
-   `LinkedList`： 双向链表

2. Map
-   `HashMap`： 
	- JDK1.8 之前 `HashMap` 由数组+链表组成的，数组是 `HashMap` 的主体，链表则是主要为了解决哈希冲突而存在的（“拉链法”解决冲突）。
	- JDK1.8 以后在解决哈希冲突时有了较大的变化，当链表长度大于阈值（默认为 8），并且数组的长度大于 64，将链表转化为红黑树，以减少搜索时间。如果红黑树节点个数<6 ，转为链表。
	- 红黑树：查找，删除，添加最坏的元素复杂度为o(logn)，避免了二叉树最坏情况下的O(n)时间复杂度。而平衡二叉树为了保持保持平衡，需要旋转的次数更多，保持平衡的效率更低。
-   `LinkedHashMap`： 在HashMap的基础上增加了一条双向链表，可以保持键值对插入的顺序。
-   `Hashtable`： 数组+链表
-   `TreeMap`： 红黑树

3. Queue

-   `ArrayDeque`: `Object[]` 数组 + 双指针
-   `PriorityQueue`: `Object[]` 数组来实现二叉堆

## Collection

### ArrayList扩容机制

`ArrayList`的扩容机制如下：

1.  当向`ArrayList`中添加第一个元素时，`ArrayList`会创建一个大小为10的数组。
2.  当继续向`ArrayList`中添加元素时，如果当前元素个数已经等于数组大小，就需要扩容。扩容后数组的大小默认原来容量大小的1.5倍。
3.  `ArrayList`在扩容时，会创建一个新的数组，将原有的元素复制到新的数组中，并将新的元素添加到新的数组中。

`ArrayList`的扩容是一种比较耗时的操作。因此，在预知需要存储大量元素时，可以通过指定初始容量来减少扩容的次数，从而提高`ArrayList`的性能。另外，如果我们已经知道要添加的元素个数，也可以使用`ArrayList`的构造函数来指定初始容量，以避免不必要的扩容操作。

### ArrayList vs LinkedList 

-   **底层数据结构：** `ArrayList` 底层使用的是 **`Object` 数组**；`LinkedList` 底层使用的是 **双向链表** 
-   **时间复杂度：**
    -   `ArrayList` : `add(E e)` 将指定的元素追加到此列表的末尾，时间复杂度为 O(1)。`add(int index, E element)`  在指定位置 i 插入和删除元素，时间复杂度就为 O(n-i)。
    -   `LinkedList`：`add(E e)` 使用头插法，时间复杂度为 O(1)，`add(int index, E element)`  在指定位置 i 插入和删除元素，时间复杂度为 O(n) ，因为需要先移动到指定位置再插入。
-   **是否支持快速随机访问：** `LinkedList` 不支持高效的随机元素访问，而 `ArrayList`支持。
-   **内存空间占用：** `ArrayList` 的空间浪费主要体现在在 list 列表的结尾会预留一定的容量空间，而 LinkedList 的空间花费则体现在它的每一个元素都需要消耗比 ArrayList 更多的空间。

我们在项目中一般是不会使用到 `LinkedList` 的，需要用到 `LinkedList` 的场景几乎都可以使用 `ArrayList` 来代替，并且，性能通常会更好！就连 `LinkedList` 的作者约书亚 · 布洛克（Josh Bloch）自己都说从来不会使用 `LinkedList` 。

### fail-fast fail-safe

**快速失败（fail—fast）**：快速失败是Java集合的一种错误检测机制

- 在用迭代器遍历一个集合对象时，如果线程A遍历过程中，线程B对集合对象的内容进行了修改（增加、删除、修改），则会抛出Concurrent Modification Exception。
- 原理：迭代器在遍历时直接访问集合中的内容，并且在遍历过程中使用一个 `modCount` 变量。集合在被遍历期间如果内容发生变化，就会改变`modCount`的值。每当迭代器使用hashNext()/next()遍历下一个元素之前，都会检测modCount变量是否为expectedmodCount值，是的话就返回遍历；否则抛出异常，终止遍历。
- 注意：这里异常的抛出条件是检测到 modCount！=expectedmodCount 这个条件。如果集合发生变化时修改modCount值刚好又设置为了expectedmodCount值，则异常不会抛出。因此，不能依赖于这个异常是否抛出而进行并发操作的编程，这个异常只建议用于检测并发修改的bug。
- 场景：java.util包下的集合类都是快速失败的，不能在多线程下发生并发修改（迭代过程中被修改），比如ArrayList 类。

**安全失败（fail—safe）**

- 采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。
- 原理：由于迭代时是对原集合的拷贝进行遍历，所以在遍历过程中对原集合所作的修改并不能被迭代器检测到，所以不会触发Concurrent Modification Exception。
- 缺点：基于拷贝内容的优点是避免了Concurrent Modification Exception，但同样地，迭代器并不能访问到修改后的内容，即：迭代器遍历的是开始遍历那一刻拿到的集合拷贝，在遍历期间原集合发生的修改迭代器是不知道的。
- 场景：java.util.concurrent包下的容器都是安全失败，可以在多线程下并发使用，并发修改，比如CopyOnWriteArrayList类。

### CopyOnWriteArrayList

CopyOnWriteArrayList就是线程安全版本的ArrayList。

它的名字叫`CopyOnWrite`——写时复制，已经明示了它的原理。

CopyOnWriteArrayList采用了一种读写分离的并发策略。
- CopyOnWriteArrayList容器允许并发读，读操作是无锁的，性能较高。
- 写操作是首先将当前容器复制一份，然后在新副本上执行写操作，结束之后再将原容器的引用指向新容器。

### Queue vs Deque

`Queue` 是单端队列，只能从一端插入元素，另一端删除元素，实现上一般遵循 **先进先出（FIFO）** 规则。

`Deque` 是双端队列，在队列的两端均可以插入或删除元素。

`Deque` 扩展了 `Queue` 的接口, 增加了在队首和队尾进行插入和删除的方法，`Deque` 还提供有 `push()` 和 `pop()` 等其他方法，可用于模拟栈。

### PriorityQueue

元素出队顺序是与优先级相关的，即总是优先级最高的元素先出队。

- 用了二叉堆的数据结构来实现的，底层使用可变长的数组来存储数据，可以在 O(logn) 的时间复杂度内插入元素和删除堆顶元素。
- 非线程安全。
- 不支持存储 `NULL` 和 `non-comparable` 的对象。
## Map

### 负载因子

负载因子：static final float DEFAULT_LOAD_FACTOR = 0.75f
map扩容判断时用的，也能够在构造函数中进行指定（不推荐），每次进行put的时候都会进行判断是否需要扩容，当size超过了阈值=总容量 * 负载因子，则会扩容，默认情况下初始总容量是16，负载因子是0.75，0.75是经过大量的实验证明该系数是最合适的。

如果设置过小，HashMap扩容频率太高，消耗大量的性能。

如果设置过大的话，当HashMap存储的数据数量接近总容量时，发生hash碰撞的概率将达到接近1，这违背的HashMap减少hash碰撞的原则。

### HashMap的长度

数组下标的计算方法是“ `(n - 1) & hash`”。（n 代表数组长度）。

取余(%)操作中如果除数是 2 的幂次方则等价于与其除数减一的与(&)操作。
采用二进制位操作 &，相对于%能够提高运算效率，这就解释了 HashMap 的长度为什么是 2 的幂次方。

### jdk1.8优化

jdk1.8 的HashMap主要有五点优化：

1. **数据结构**：数组 + 链表改成了数组 + 链表或红黑树
    
    `原因`：发生 hash 冲突，元素会存入链表，链表过长转为红黑树，将时间复杂度由`O(n)`降为`O(logn)`
    
2. **链表插入方式**：链表的插入方式从头插法改成了尾插法
    
    简单说就是插入时，如果数组位置上已经有元素，1.7 将新元素放到数组中，原始节点作为新节点的后继节点，1.8 遍历链表，将元素放置到链表的最后。
    
    `原因`：因为 1.7 头插法扩容时，头插法会使链表发生反转，多线程环境下会产生环。
    
3. **扩容rehash**：扩容的时候 1.7 需要对原数组中的元素进行重新 hash 定位在新数组的位置，1.8 采用更简单的判断逻辑，不需要重新通过哈希函数计算位置，新的位置不变或索引 + 新增容量大小。
    
    `原因：`提高扩容的效率，更快地扩容。
    
4. **扩容时机**：在插入时，1.7 先判断是否需要扩容，再插入，1.8 先进行插入，插入完成再判断是否需要扩容；
    
5. **散列函数**：1.7 做了四次移位和四次异或，jdk1.8只做一次。
    
    `原因`：做 4 次的话，边际效用也不大，改为一次，提升效率。

### HashTable vs HashMap

-   **线程安全：**`HashMap` 是非线程安全的，`Hashtable` 是线程安全的,因为 `Hashtable` 内部的方法基本都经过`synchronized` 修饰。
-   **效率：** 因为线程安全的问题，`HashMap` 要比 `Hashtable` 效率高。另外，`Hashtable` 基本被淘汰，不要在代码中使用它，整个**`Hashtable`** 使用同一把锁，使用`synchronized` 来保证线程安全，效率非常低下。
-   **Null key 和Null value：** `HashMap` 可以存储 null 的 key 和 value，但 null 作为键只能有一个，null 作为值可以有多个；`Hashtable` 不允许有 null 键和 null 值，否则会抛出 `NullPointerException`。
-   **初始容量大小和每次扩充容量大小的不同 ：** 创建时如果不指定容量初始值，`Hashtable` 默认的初始大小为 11，之后每次扩充，容量变为原来的 2n+1。`HashMap` 默认的初始化大小为 16。之后每次扩充，容量变为原来的 2 倍。创建时如果给定了容量初始值，那么 `Hashtable` 会直接使用你给定的大小，而 `HashMap` 会将其扩充为 2 的幂次方大小。
-   **底层数据结构：** JDK1.8 以后的 `HashMap` 为数组+链表+红黑树，以减少搜索时间。`Hashtable` 没有这样的机制。

### ConcurrentHashMap

-   JDK1.7： `ConcurrentHashMap` 对整个桶数组进行了分段(`Segment`，分段锁) ，`Segment` 继承自 `ReentrantLock`，`Segment` 则包含HashEntry的数组。每一把锁只锁容器其中一部分数据，多线程访问容器里不同数据段的数据，就不会存在锁竞争，提高并发访问率。最大并发度是 Segment 的个数，默认是 16。
-   JDK1.8 ：`ConcurrentHashMap` 已经摒弃了 `Segment` 的概念，基于`CAS+synchronized`实现。并发度更大。锁粒度更细，只锁定当前链表或红黑二叉树的首节点，这样只要 hash 不冲突，就不会产生并发，就不会影响其他 Node 的读写，效率大幅提升。
	- 如果node数组为空，或者当前node数组位置为空，则使用 `CAS` +自旋写入数据。
	- 如果当前node数组位置中包含数据，则使用 `synchronized` 写入数据

**JDK1.7 的 ConcurrentHashMap** ：

![[java7_concurrenthashmap.png]]

**JDK1.8 的 ConcurrentHashMap** ：

![[java8_concurrenthashmap.png]]