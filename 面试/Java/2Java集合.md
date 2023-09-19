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
-   `HashMap`： JDK1.8 之前 `HashMap` 由数组+链表组成的，数组是 `HashMap` 的主体，链表则是主要为了解决哈希冲突而存在的（“拉链法”解决冲突）。JDK1.8 以后在解决哈希冲突时有了较大的变化，当链表长度大于阈值（默认为 8）（将链表转换成红黑树前会判断，如果当前数组的长度小于 64，那么会选择先进行数组扩容，而不是转换为红黑树）时，将链表转化为红黑树，以减少搜索时间
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

### 头插尾插

1.8采用的尾插法，1.7采用的头插法

当1.8中的桶中元素处于链表的情况，遍历的同时最后如果没有匹配的，直接将节点添加到链表尾部；而1.7在遍历的同时没有添加数据，而是另外调用了addEntry()方法，将节点添加到链表头部。

1. put元素时为了计算链表长度，判断是否要转为红黑树

2. rehash时，头插法会打乱节点顺序。容易造成死循环

### HashMap的长度

数组下标的计算方法是“ `(n - 1) & hash`”。（n 代表数组长度）。

取余(%)操作中如果除数是 2 的幂次方则等价于与其除数减一的与(&)操作。
采用二进制位操作 &，相对于%能够提高运算效率，这就解释了 HashMap 的长度为什么是 2 的幂次方。

### HashTable vs HashMap

-   **线程安全：**`HashMap` 是非线程安全的，`Hashtable` 是线程安全的,因为 `Hashtable` 内部的方法基本都经过`synchronized` 修饰。
-   **效率：** 因为线程安全的问题，`HashMap` 要比 `Hashtable` 效率高。另外，`Hashtable` 基本被淘汰，不要在代码中使用它，整个**`Hashtable`** 使用同一把锁，使用`synchronized` 来保证线程安全，效率非常低下。
-   **Null key 和Null value：** `HashMap` 可以存储 null 的 key 和 value，但 null 作为键只能有一个，null 作为值可以有多个；`Hashtable` 不允许有 null 键和 null 值，否则会抛出 `NullPointerException`。
-   **初始容量大小和每次扩充容量大小的不同 ：** 创建时如果不指定容量初始值，`Hashtable` 默认的初始大小为 11，之后每次扩充，容量变为原来的 2n+1。`HashMap` 默认的初始化大小为 16。之后每次扩充，容量变为原来的 2 倍。创建时如果给定了容量初始值，那么 `Hashtable` 会直接使用你给定的大小，而 `HashMap` 会将其扩充为 2 的幂次方大小。
-   **底层数据结构：** JDK1.8 以后的 `HashMap` 为数组+链表+红黑树，以减少搜索时间。`Hashtable` 没有这样的机制。

### ConcurrentHashMap

-   JDK1.7： `ConcurrentHashMap` 对整个桶数组进行了分段(`Segment`，分段锁) ，`Segment` 继承自 `ReentrantLock`。每一把锁只锁容器其中一部分数据，多线程访问容器里不同数据段的数据，就不会存在锁竞争，提高并发访问率。最大并发度是 Segment 的个数，默认是 16。
-   JDK1.8 ：`ConcurrentHashMap` 已经摒弃了 `Segment` 的概念，使用乐观锁，直接用 `Node` 数组+链表+红黑树的数据结构来实现，采用 `Node + CAS` 来保证并发安全。最大并发度是 Node 数组的大小，并发度更大。锁粒度更细，只锁定当前链表或红黑二叉树的首节点，这样只要 hash 不冲突，就不会产生并发，就不会影响其他 Node 的读写，效率大幅提升。

**JDK1.7 的 ConcurrentHashMap** ：

![[java7_concurrenthashmap.png]]

**JDK1.8 的 ConcurrentHashMap** ：

![[java8_concurrenthashmap.png]]