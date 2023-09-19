## CompletableFuture简介

CompletableFuture是由Java 8引入的，在Java8之前我们一般通过Future实现异步。

- Future用于表示异步计算的结果，只能通过阻塞或者轮询的方式获取结果，而且不支持设置回调方法，Java 8之前若要设置回调一般会使用guava的ListenableFuture，回调的引入又会导致臭名昭著的回调地狱（下面的例子会通过ListenableFuture的使用来具体进行展示）。
- CompletableFuture对Future进行了扩展，可以通过设置回调的方式处理计算结果，同时也支持组合操作，支持进一步的编排，同时一定程度解决了回调地狱的问题。

![[Pasted image 20230712160308.png]]

CompletableFuture实现了两个接口（如上图所示）：Future、CompletionStage。Future表示异步计算的结果，CompletionStage用于表示异步执行过程中的一个步骤（Stage），这个步骤可能是由另外一个CompletionStage触发的，随着当前步骤的完成，也可能会触发其他一系列CompletionStage的执行。从而我们可以根据实际业务对这些步骤进行多样化的编排组合，CompletionStage接口正是定义了这样的能力，我们可以通过其提供的thenAppy、thenCompose等函数式编程方法来组合编排这些步骤。

使用CompletableFuture也是构建依赖树的过程。一个CompletableFuture的完成会触发另外一系列依赖它的CompletableFuture的执行：

![[Pasted image 20230712160618.png]]

如上图所示，这里描绘的是一个业务接口的流程，其中包括CF1\CF2\CF3\CF4\CF5共5个步骤，并描绘了这些步骤之间的依赖关系，每个步骤可以是一次RPC调用、一次数据库操作或者是一次本地方法调用等，在使用CompletableFuture进行异步化编程时，图中的每个步骤都会产生一个CompletableFuture对象，最终结果也会用一个CompletableFuture来进行表示。

CompletableFuture实现了CompletionStage接口，通过丰富的回调方法，支持各种组合操作，每种组合场景都有同步和异步两种方法。

同步方法（即不带Async后缀的方法）有两种情况。

- 如果注册时被依赖的操作已经执行完成，则直接由当前线程执行。
- 如果注册时被依赖的操作还未执行完，则由回调线程执行。

异步方法（即带Async后缀的方法）：可以选择是否传递线程池参数Executor运行在指定线程池中；当不传递Executor时，会使用ForkJoinPool中的共用线程池CommonPool（CommonPool的大小是CPU核数-1，如果是IO密集的应用，线程数可能成为瓶颈）。

这里我们建议**强制传线程池，且根据实际情况做线程池隔离**。

否则所有调用将共用该线程池，核心线程数=处理器数量-1（单核核心线程数为1），所有异步回调都会共用该CommonPool，核心与非核心业务都竞争同一个池中的线程，很容易成为系统瓶颈。手动传递线程池参数可以更方便的调节参数，并且可以给不同的业务分配不同的线程池，以求资源隔离，减少不同业务之间的相互干扰。

## CompletableFuture使用

### 串行任务

![[Pasted image 20230712192635.png]]

![[Pasted image 20230712192643.png]]

```java
CompletableFuture
	.supplyAsync(
		() -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task1";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  return taskName;
		}, THREAD_POOL_EXECUTOR)
	//使用上一个任务的线程，需要上一个任务的返回值，并且自身有返回值
	.thenApply(
		(task1Result) -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task2";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  System.out.println("拿到上一个任务的返回值：" + task1Result);
		  return taskName;
		})
	//开启新线程异步执行，需要上一个任务的返回值，并且自身有返回值
	.thenApplyAsync(
		(task2Result) -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task3";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  System.out.println("拿到上一个任务的返回值：" + task1Result);
		  return taskName;
		}, THREAD_POOL_EXECUTOR)
	//使用上一个任务的线程，需要上一个任务的返回值，自身没有返回值
	.thenAccept(
		 (task3Result) -> {
		 Thread currentThread = Thread.currentThread();
		 String ThreadName = currentThread.getName();
		 String taskName = "task4";
		 System.out.println(ThreadName + "开始执行任务：" + taskName);
		 System.out.println("拿到上一个任务的返回值：" + task2Result);
	   });
	// 不需要上一个任务的返回值，自身也没有返回值
	.thenRunAsync(
		() -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task5";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  System.out.println("thenRunAsync()不需要上一个任务的返回值");
		}, THREAD_POOL_EXECUTOR);
```

> 1.入口函数`supplyAsync()`代表一个异步的有返回值的函数，从线程池中的拿一个线程来执行。需要传入一个有返回值的函数作为参数；如果想要没有返回值的函数传进来的话，可以使用`CompletableFuture.runAsync()`;
> 
> 2.`thenApply()`和`thenAccept()`没有`Async`，意味着是和前面的任务共用一个线程，从执行结果上我们也可以看到线程名称相同。
> 
> 3.`thenApply()`需要接收上一个任务的返回值，并且自己也要有返回值。
> 
> 4.`thenAccept()`需要接收上一个任务的返回值，但是它不需要返回值。
> 
> 5.`thenApplyAsync()`和`thenRunAsync()`分别表示里面的任务都是异步执行的，和执行前面的任务不是同一个线程；
> 
> 6.`thenRunAsync()`需要传入一个既不需要参数，也没有返回值的任务；

### 并行任务

![[Pasted image 20230712192652.png]]

> **并行**代表任务1、任务2、任务3没有依赖关系，分别由不同的线程执行；

```java
CompletableFuture<String> future1 = CompletableFuture
	.supplyAsync(
		() -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task1";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  System.out.println("正在执行任务" + taskName);
		  System.out.println(taskName + "执行结束");
		  return taskName;
		}, THREAD_POOL_EXECUTOR);
		
CompletableFuture<Void> future2 = CompletableFuture
	.runAsync(
		() -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task2";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  System.out.println("正在执行任务" + taskName);
		  System.out.println(taskName + "执行结束");
		}, THREAD_POOL_EXECUTOR);
		
CompletableFuture<String> future3 = CompletableFuture
	.supplyAsync(
		() -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task3";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  System.out.println("正在执行任务" + taskName);
		  System.out.println(taskName + "执行结束");
		  return taskName;
		}, THREAD_POOL_EXECUTOR);
```

### 多任务结果合并计算

- 两个任务结果的合并

![[Pasted image 20230712192703.png]]

> 任务3的执行依赖于任务1、任务2的返回值，并且任务1和任务3由同一个线程执行，任务2单独一个线程执行；

```java
CompletableFuture
	// 任务1
	.supplyAsync(
		() -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task1";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  return taskName;
		}, THREAD_POOL_EXECUTOR)
	.thenCombine(
		CompletableFuture
			// 任务2
			.supplyAsync(
				() -> {
				  Thread currentThread = Thread.currentThread();
				  String ThreadName = currentThread.getName();
				  String taskName = "task2";
				  System.out.println(ThreadName + "开始执行任务：" + taskName);
				  return taskName;
				}, THREAD_POOL_EXECUTOR),
		// 任务3
		(task1Result, task2Result) -> {
			Thread currentThread = Thread.currentThread();
			String ThreadName = currentThread.getName();
			String taskName = "task3";
			System.out.println(ThreadName + "开始执行任务：" + taskName);
			System.out.println("task1结果：" + task1Result + "\ttask2结果：" + task2Result);
			return taskName;
		});
```

`CompletableFuture`提供了`thenCombine()`来合并另一个`CompletableFuture`的执行结果，所以`thenCombine()`需要两个参数，第一个参数是另一个`CompletableFuture`，第二个参数会收集前两个任务的返回值

![[Pasted image 20230712193048.png]]
 
如果小伙伴们想要实现任务3也是单独的线程执行的话，可以使用`thenCombineAsync()`这个方法。

如果任务3中不需要返回结果，可以使用`thenAcceptBoth()`或`thenAcceptBothAsync()`，使用方式与`thenCombineAsync()`类似；

- 多任务结果合并

![[Pasted image 20230712193103.png]]

```java
CompletableFuture future1 = CompletableFuture
	// 任务1
	.supplyAsync(
		() -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task1";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  return taskName;
		}, THREAD_POOL_EXECUTOR);
		
CompletableFuture future2 = CompletableFuture
	// 任务2
	.supplyAsync(
		() -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task2";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  return taskName;
		}, THREAD_POOL_EXECUTOR);
		
CompletableFuture future3 = CompletableFuture
	// 任务3
	.supplyAsync(
		() -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task3";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  return taskName;
		}, THREAD_POOL_EXECUTOR);
		
CompletableFuture[] futures = new CompletableFuture[]{future1, future2, future3};

// 所有任务都执行完才会执行
CompletableFuture.allOf(futures)
	// 任务4
	.whenCompleteAsync(
		(v, e) -> {
		  List<Object> values = new ArrayList<>();
		  for (CompletableFuture future : futures) {
			try {
			  values.add(future.get());
			} catch (Exception ex) {
			}
		  }
		  String taskName = "task4";
		  System.out.println("正在执行任务" + taskName);
		}, THREAD_POOL_EXECUTOR);

//任意任务执行完都可以执行
CompletableFuture.anyOf(future1, future2, future3)
	.thenApplyAsync((taskResult) -> {
	  String taskName = "task4";
	  System.out.println("前面任务的处理结果：" + taskResult);
	  System.out.println("正在执行任务" + taskName);
	  return taskName;
	}, THREAD_POOL_EXECUTOR);

//监听异常，出现异常就报错
CompletableFuture allCompletableFuture = CompletableFuture.allOf(futures);
// 创建一个任务来监听异常
CompletableFuture<?> anyException = new CompletableFuture<>();

for (CompletableFuture<?> completableFuture : futures) {
	completableFuture.exceptionally((t) -> {
		// 任何一个任务异常都会让anyException任务完成
		anyException.completeExceptionally(t);
		return null;
	});
}
// 要么allCompletableFuture全部成功，要么一个出现异常就结束任务
CompletableFuture.anyOf(allCompletableFuture, anyException)
	.whenComplete((value, exception) -> {
		if (Objects.nonNull(exception)) {
			System.out.println("产生异常，提前结束！");
			exception.printStackTrace();
			return;
		}
		System.out.println("所有任务正常完成！");
	});
```

`allOf()`，它的作用是要求所有的任务全部完成才能执行后面的任务。

`anyOf()`，在一批任务中，只要有一个任务完成，那么就可以向后继续执行其他任务。

在一批任务当中，只要有任意一个任务执行产生异常了，那么就直接结束；否则就要等待所有任务成功执行完毕。

> `CompletableFuture`没有现成的api实现快速失败的功能，所以我们只能结合`allOf()`和`anyOf()`来逻辑来自定义方法完成快速失败的逻辑；
> 
> 1.我们需要额外创建一个CompletableFuture来监听所有的CompletableFuture，一旦其中一个CompletableFuture产生异常，我们就设置额外的CompletableFuture立即完成。
> 
> 2.把所有的CompletableFuture和额外的CompletableFuture放在`anyOf()`方法中，这样一旦额外的CompletableFuture完成，说明产生异常了；否则就需要等待所有的CompletableFuture完成。

### 注意

- 异常处理

最后需要注意的是，所有的`CompletableFuture`任务一定要加上异常处理：

```java
CompletableFuture
	// 任务1
	.supplyAsync(
		() -> {
		  Thread currentThread = Thread.currentThread();
		  String ThreadName = currentThread.getName();
		  String taskName = "task1";
		  System.out.println(ThreadName + "开始执行任务：" + taskName);
		  System.out.println("正在执行任务" + taskName);
		  System.out.println(taskName + "执行结束");
		  return taskName;
		}, THREAD_POOL_EXECUTOR)
	.whenComplete((v,e)->{
		if(Objects.nonNull(e)){
			// todo
			// 处理异常
		}
		if(Objects.nonNull(v)){
			// todo
		}
	});
```


还可以通过另外两个方法处理：`exceptionally()`或者`handle()`；



