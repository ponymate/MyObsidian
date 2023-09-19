SpringBoot提供了异步方法支持注解

```java
@EnableAsync // 使用异步方法时需要提前开启(在启动类上或配置类上)
@Async // 被async注解修饰的方法由SpringBoot默认线程池(SimpleAsyncTaskExecutor)执行
```

注意！

1. 在同一个Service中调用另一个异步方法不会生效，因为异步方法的实现依赖于AOP，因此必须走代理，而在同一个Service中调用另一个方法是直接调用，不走代理，因此需要将异步方法所在了类注册为Bean，通过依赖注入的形式获取实例进行调用才可生效！

2. 异步方法执行失败后对Controller前半部分的非异步操作无影响, 因此说异步方法在整个业务逻辑中不是100%可靠的, 对于强一致性的业务来说不适用。

## 使用注解方式实现异步方法

Service层:

```java
@Service
public class ArticleServiceImpl {

    // 查询文章
    public String selectArticle() {
        // TODO 模拟文章查询操作
        System.out.println("查询任务线程"+Thread.currentThread().getName());
        return "文章详情";
    }

    // 文章阅读量+1
    @Async
    public void updateReadCount() {
        // TODO 模拟耗时操作
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("更新任务线程"+Thread.currentThread().getName());
    }
}
```

Controller层:

```java
@RestController
public class AsyncTestController {

    @Autowired
    private ArticleServiceImpl articleService;

    /**
     * 模拟获取文章后阅读量+1
     */
    @PostMapping("/article")
    public String getArticle() {
        // 查询文章
        String article = articleService.selectArticle();
        // 阅读量+1
        articleService.updateReadCount();
        System.out.println("文章阅读业务执行完毕");
        return article;
    }

}
```

## 自定义线程池执行异步方法

​SpringBoot为我们默认提供了线程池(SimpleAsyncTaskExecutor)来执行我们的异步方法, 我们也可以自定义自己的线程池.

第一步配置自定义线程池

```java
@EnableAsync // 开启多线程, 项目启动时自动创建
@Configuration
public class AsyncConfig {
    @Bean("customExecutor")
    public ThreadPoolTaskExecutor asyncOperationExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(8);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 设置队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 设置线程活跃时间(秒)
        executor.setKeepAliveSeconds(60);
        // 设置线程名前缀+分组名称
        executor.setThreadNamePrefix("AsyncOperationThread-");
        executor.setThreadGroupName("AsyncOperationGroup");
        // 所有任务结束后关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化
        executor.initialize();
        return executor;
    }
}
```

第二步, 在@Async注解上指定执行的线程池即可

```java
// 文章阅读量+1
@Async("customExecutor")
public void updateReadCount() {
    // TODO 模拟耗时操作
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("更新文章阅读量线程"+Thread.currentThread().getName());
}
```

## 捕获异步方法中的异常

#### 捕获无返回值的异步方法中的异常

1. 实现```AsyncConfigurer```接口的```getAsyncExecutor```方法和```getAsyncUncaughtExceptionHandler```
方法改造配置类
2. 自定义异常处理类```CustomAsyncExceptionHandler```
```java
@EnableAsync // 开启多线程, 项目启动时自动创建
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(8);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 设置队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 设置线程活跃时间(秒)
        executor.setKeepAliveSeconds(60);
        // 设置线程名前缀+分组名称
        executor.setThreadNamePrefix("AsyncOperationThread-");
        executor.setThreadGroupName("AsyncOperationGroup");
        // 所有任务结束后关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}
```

```java
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
 
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        System.out.println("异常捕获---------------------------------");
        System.out.println("Exception message - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) {
            System.out.println("Parameter value - " + param);
        }
        System.out.println("异常捕获---------------------------------");
    }
     
}
```

#### 捕获有返回值异步方法的返回值

使用Future类及其子类来接收异步方法返回值

无返回值的异步方法抛出异常不会影响Controller的主要业务逻辑
有返回值的异步方法抛出异常会影响Controller的主要业务逻辑

```java
// 异步方法---------------------------------------------------------------------
@Async
public CompletableFuture<Integer> updateReadCountHasResult() {
	// TODO 模拟耗时操作
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	System.out.println("更新文章阅读量线程"+Thread.currentThread().getName());
	return CompletableFuture.completedFuture(100 + 1);
}
```

```java
// Controller调用-------------------------------------------------------------------
@GetMapping("/article")
public String getArticle() throws ExecutionException, InterruptedException {
    // 查询文章
    String article = articleService.selectArticle();
    // 阅读量+1
    CompletableFuture<Integer> future = articleService.updateReadCountHasResult();
    int count = 0;
    // 循环等待异步请求结果
    while (true) {
        if(future.isCancelled()) {
            System.out.println("异步任务取消");
            break;
        }
        if (future.isDone()) {
            count = future.get();
            System.out.println(count);
            break;
        }
    }
    System.out.println("文章阅读业务执行完毕");
    return article + count;
}
```



