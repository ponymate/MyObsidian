# Mybatis-Plus

## 配置文件注意事项：

1、驱动类driver-class-name

spring boot 2.0(内置jdbc5驱动)，驱动类使用:

**driver-class-name: com.mysql.jdbc.Driver**

spring boot 2.1及以上(内置jdbc8驱动)，驱动类使用:

**driver-class-name: com.mysql.cj.jdbc.Driver**

否则运行测试用例的时候会有 WARN 信息



2、连接地址url

MySQL5.7版本的url:

**jdbc:mysql://localhost:3306/mybatis_plus?characterEncoding=utf-8&useSSL=false**

MySQL8.0版本的url:

**jdbc:mysql://localhost:3306/mybatis_plus?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false**

否则运行测试用例报告如下错误:

java.sql.SQLException: The server time zone value 'ÖÐ1ú±ê×1⁄4Ê±1⁄4ä' is unrecognized or represents more

## 加入日志功能

```yaml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

```java
/** 测试BaseMapper的 修改功能  **/
@Test
public void testUpdate() {
    // 修改用户信息
    // UPDATE user SET name=?, email=? WHERE id=?
    User user = new User();
    user.setIdƔL);
    user.setName(";李四";);
    user.setEmail(";lisi@atguigu.com";);
    userMapper.updateById(user);
    System.out.println(";user = "; + user);
}
```

## 设置表名前缀

mybatis-plus:
  configuration:
    # 配置MyBatis日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  # 设置MyBatis-Plus的全局配置
```yaml
mybatis-plus:
  configuration:
    # 配置MyBatis日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  # 设置MyBatis-Plus的全局配置
  global-config:
    db-config:
      # 设置实体类所对应的表的统一前缀
      table-prefix: t_
```

## @Tableld

经过以上的测试，**MyBatis-Plus在实现CRUD时，会默认将id作为主键列**，并在插入数据时，**默认** 

**基于雪花算法的策略生成id**

若实体类和表中表示**主键的不是id，而是其他字段**，例如uid，MyBatis-Plus不会自动识别uid为主 

键

程序抛出异常，**Field 'uid' doesn't have a default value**，说明MyBatis-Plus没有将uid作为主键 

赋值

在实体类中uid属性上通过**@TableId**将其标识为主键，即可成功执行SQL语句

### @Tableld的type属性

type属性用来定义主键策略

#### 常用的主键策略：

![img](https://cdn.nlark.com/yuque/0/2022/png/26045818/1646267160557-739e631c-0f8c-4ade-9ffc-ea6a7bf177dd.png)

#### 配置全局主键策略：

```yaml
# 加入日志功能
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  # 设置MyBatis-Plus的全局配置
  global-config:
    db-config:
      # 设置实体类所对应的表的统一前缀
      table-prefix: t_
      # 设置统一的主键生成策略
      id-type: auto
```



## @TableField

经过以上的测试，我们可以发现，MyBatis-Plus在执行SQL语句时，要保证实体类中的属性名和表中的字段名一致 



如果**实体类中的属性名和字段名不一致的情况**，会出现什么问题呢？

### a>情况1

若实体类中的属性使用的是驼峰命名风格，而表中的字段使用的是下划线命名风格 

例如实体类属性userName，表中字段user_name 

此时**MyBatis-Plus会自动将下划线命名风格转化为驼峰命名风格** 

相当于在MyBatis中配置

### a>情况2

若实体类中的属性和表中的字段不满足情况1 



例如实体类属性name，表中字段username 

此时需要在实体类属性上使用**@TableField("username")**设置**属性所对应的字段名**

## @TableLogic

### a>逻辑删除

- 物理删除：真实删除，将对应数据从数据库中删除，之后查询不到此条被删除的数据 
- **逻辑删除**：假删除，**将对应数据中代表是否被删除字段的状态修改为“被删除状态”**，之后在数据库 

中仍旧能看到此条数据记录 

- 使用场景：可以进行数据恢复



### b>实现逻辑删除

**step1：****数据库中创建逻辑删除状态列，设置默认值为0**

![img](https://cdn.nlark.com/yuque/0/2022/png/26045818/1646267623556-c9f349d2-2ba4-44fc-9463-d77e355ccbdc.png)

**step2：**实体类中添加逻辑删除属性

<img src="https://cdn.nlark.com/yuque/0/2022/png/26045818/1646301871678-59327a23-17e2-4606-913d-a887358f764a.png" alt="img" style="zoom:67%;" />

**step3****：**测试 



测试删除功能，真正执行的是修改 

**UPDATE t_user SET is_deleted=1 WHERE id=? AND is_deleted=0** 

测试查询功能，被逻辑删除的数据默认不会被查询 

**SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0**

## wrapper介绍

![img](https://cdn.nlark.com/yuque/0/2022/png/26045818/1646267666342-40494034-6b13-496f-bbd9-585ee6e1bb78.png)

Wrapper ： **条件构造抽象类**，最顶端父类 

- AbstractWrapper ： 用于查询条件封装，生成 sql 的 where 条件 

- - QueryWrapper ： 查询条件封装 
  - UpdateWrapper ： Update 条件封装 
  - AbstractLambdaWrapper ： 使用Lambda 语法 

- - - LambdaQueryWrapper ：用于Lambda语法使用的查询Wrapper 
    - LambdaUpdateWrapper ： Lambda 更新封装Wrapper

### 例6：实现子查询

```java
/** 组装子查询 查询id小于100的用户信息**/
@Test
public void test07() {
    /*
        SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user
        WHERE is_deleted=0 AND (uid IN (select uid from t_user where uid <= 100))
     */
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.inSql("uid", "select uid from t_user where uid <= 100");
    List<User> list = userMapper.selectList(queryWrapper);
    list.forEach(System.out::println);
}
```





## condition

上面的实现方案没有问题，但是代码比较复杂，我们可以使用带condition参数的重载方法构建查询条件**，****简化代码的编写**

```java
/** 使用condition组装条件 **/
@Test
public void test10() {
    /*
        SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user
        WHERE is_deleted=0 AND (user_name LIKE ? AND age <= ?)
     */
    String username = "a";
    Integer ageBegin = null;
    Integer ageEnd = 30;
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.like(StringUtils.isNotBlank(username), "user_name", username)
            .gt(ageBegin != null, "age", ageBegin)
            .le(ageEnd != null, "age", ageEnd);
    List<User> list = userMapper.selectList(queryWrapper);
    list.forEach(System.out::println);
}
```

## 5、LambdaQueryWrapper

```java
/** LambdaQueryWrapper **/
@Test
public void test11() {
    /*
        SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user
        WHERE is_deleted=0 AND (user_name LIKE ? AND age <= ?)
     */
    String username = "a";
    Integer ageBegin = null;
    Integer ageEnd = 30;
    //组装set子句
    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    //避免使用字符串表示字段，防止运行时错误
    queryWrapper.like(StringUtils.isNotBlank(username), User::getName, username)
            .gt(ageBegin != null, User::getAge, ageBegin)
            .le(ageEnd != null, User::getAge, ageEnd);
    List<User> list = userMapper.selectList(queryWrapper);
    list.forEach(System.out::println);
}
```





## 6、LambdaUpdateWrapper

```java
/** LambdaUpdateWrapper **/
@Test
public void test12() {
    /*
        UPDATE t_user SET user_name=?,email=?
        WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
     */
    LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.like(User::getName, "a")
            //lambda表达式内的逻辑优先运算
            .and(i -> i.gt(User::getAge, 20).or().isNull(User::getEmail)); 
    updateWrapper.set(User::getName, "小黑").set(User::getEmail, "abc@atguigu.com");
    int result = userMapper.update(null, updateWrapper);
    System.out.println("result = " + result);
}
```



## 、分页插件

MyBatis Plus自带分页插件，只要简单的配置即可实现分页功能



### a>添加配置类

```java
@Configuration
@MapperScan("scan.your.mapper.package")
public class MybatisPlusConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
}
```

### b>测试

```java
@Autowired
private ProductMapper productMapper;

/** MyBatis-Plus分页插件的配置和使用和分页相关数据的获取 **/
@Test
public void testPage() {
    /*
        SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user
        WHERE is_deleted=0 LIMIT ?
     */
    Page<User> page = new Page<>(1, 3);
    userMapper.selectPage(page, null);
    System.out.println( page.getRecords());
    System.out.println("总页数:"  + page.getPages());
    System.out.println("总记录数" + page.getTotal());
    System.out.println("是否有上一页" + page.hasNext());
    System.out.println("是否有下一页:" + page.hasPrevious());
}
```

测试结果：

[User(id=1, name=Jone, age=18, email=test1@baomidou.com, sex=null, isDeleted=0), User(id=2, name=Jack, age=18, email=test2@baomidou.com, sex=null, isDeleted=0), User(id=3, name=Tom, age=18, email=test3@baomidou.com, sex=null, isDeleted=0)]



总页数:3

总记录数8

是否有上一页true

是否有下一页:false



## 2、xml自定义分页

### a>UserMapper中定义接口方法

```java
/**
* 根据年龄查询用户列表，分页显示
* @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位 * @param age 年龄
* @return
*/
Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);
```



### b>UserMapper.xml中编写SQL

```java
<!--Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);-->
<select id="selectPageVo" resultType="User">
    select uid as `id`,user_name as `name`,age,email from t_user where age > #{age}
</select>
```

### c>测试

```java
@Test
public void testSelectPageVo(){ //设置分页参数
    Page<User> page = new Page<>(1, 5); 
    userMapper.selectPageVo(page, 20);
    //获取分页数据
    List<User> list = page.getRecords(); 
    list.forEach(System.out::println); 
    System.out.println("当前页:"+page.getCurrent()); 
    System.out.println("每页显示的条数:"+page.getSize()); 
    System.out.println("总记录数:"+page.getTotal()); 
    System.out.println("总页数:"+page.getPages()); 
    System.out.println("是否有上一页:"+page.hasPrevious()); 
    System.out.println("是否有下一页:"+page.hasNext());
}
```

User(id=3, name=Tom, age=28, email=test3@baomidou.com, isDeleted=null) User(id=4, 

name=Sandy, age=21, email=test4@baomidou.com, isDeleted=null) User(id=5, name=Billie, 

age=24, email=test5@baomidou.com, isDeleted=null) User(id=8, name=ybc1, age=21, 

email=null, isDeleted=null) User(id=9, name=ybc2, age=22, email=null, isDeleted=null) 

### 

## 3、乐观锁

### a>场景

一件商品，成本价是80元，售价是100元。老板先是通知小李，说你去把商品价格增加50元。小 

李正在玩游戏，耽搁了一个小时。正好一个小时后，老板觉得商品价格增加到150元，价格太 

高，可能会影响销量。又通知小王，你把商品价格降低30元。 



此时，小李和小王同时操作商品后台系统。小李操作的时候，系统先取出商品价格100元；小王 

也在操作，取出的商品价格也是100元。小李将价格加了50元，并将100+50=150元存入了数据 

库；小王将商品减了30元，并将100-30=70元存入了数据库。是的，如果没有锁，小李的操作就 

完全被小王的覆盖了。 



现在商品价格是70元，比成本价低10元。几分钟后，这个商品很快出售了1千多件商品，老板亏1 

万多。



### b>乐观锁和悲观锁

上面的故事，如果是乐观锁，小王保存价格前，会检查下价格是否被人修改过了。如果被修改过了，则重新取出的被修改后的价格，150元，这样他会将120元存入数据库。 

如果是悲观锁，小李取出数据后，小王只能等小李操作完之后，才能对价格进行操作，也会保证 

最终的价格是120元。

#### 修改实体类

```java
@Data
public class Product {

    private Long id;
    private String name;
    private Integer price;
    @Version // 设置乐观锁版本号字段
    private Integer version;

}
```

#### 添加乐观锁插件配置

```java
@Configuration
// 扫描mapper接口所在的包
@MapperScan("com.atguigu.mybatisplus.mapper")
public class myBatisPlusConfig {

    /** 添加MyBatisPlus分页插件 **/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 添加乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

}
```

### 创建通过枚举类型

```java
@Getter // 因为枚举里面都是常量
public enum SexEnum {
    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue // 将注解所标识的属性的值存储到数据库中
    private Integer sex;
    private String sexName;

    SexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
```



### c>配置扫描通过枚举

```yaml
# 加入日志功能
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  # 设置MyBatis-Plus的全局配置
  global-config:
    db-config:
      # 设置实体类所对应的表的统一前缀
      table-prefix: t_
      # 设置统一的主键生成策略
      id-type: auto
  # 配置类型别名所对应的包
  type-aliases-package: com.atguigu.mybatisplus.pojo
  # 扫描枚举的包
  type-enums-package: com.atguigu.mybatisplus.enums
```

### ## 多数据源

```xml
<!--多数据源依赖-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
    <version>3.5.0</version>
</dependency>
```

## 配置多数据源

说明:注释掉之前的数据库连接，添加新配置

```yaml
spring:
  datasource:
    # 配置数据源信息 datasource:
    dynamic:
    # 设置默认的数据源或者数据源组,默认值即为master
      primary: master
      # 严格匹配数据源,默认false.true未匹配到指定数据源时抛异常,false使用默认数据源
      strict: false
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/mybatis_plus?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false
          driver-class-name: com.mysql.cj.jdbc.Driver
          username: root
          password: 'root'
        slave_1:
           # 我的数据库是8.0.27 5版本的可以使用jdbc:mysql://localhost:3306/mybatis_plus?characterEncoding=utf-8&useSSL=false
          url: jdbc:mysql://localhost:3306/mybatis_plus_1?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false
          driver-class-name: com.mysql.cj.jdbc.Driver
          username: root
          password: 'root'
```

# 十、MyBatisX插件

MyBatis-Plus为我们提供了强大的mapper和service模板，能够大大的提高开发效率



但是在真正开发过程中，MyBatis-Plus并不能为我们解决所有问题，例如一些复杂的SQL，多表 联查，我们就需要自己去编写代码和SQL语句，我们该如何快速的解决这个问题呢，这个时候可 以使用MyBatisX插件



MyBatisX一款基于 IDEA 的快速开发插件，为效率而生

[MyBatisX插件用法](https://baomidou.com/pages/ba5b24/)

<img src="https://cdn.nlark.com/yuque/0/2022/png/26045818/1646303427004-512acd2e-f903-4f9f-a23b-342497382389.png" alt="img" style="zoom: 67%;" />

![img](https://cdn.nlark.com/yuque/0/2022/png/26045818/1646303471018-0b3f3e51-7b09-48db-a789-9798bcd70063.png)

![img](https://cdn.nlark.com/yuque/0/2022/png/26045818/1646303486690-5b0c7599-10b3-49eb-a8f8-c36ed19750ef.png)

![img](https://cdn.nlark.com/yuque/0/2022/png/26045818/1646303542109-e450e33c-0529-4948-8591-4a0f17f8d62d.png)

## 快速入门

### [#](https://baomidou.com/pages/779a6e/#安装)安装

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.5.2</version>
</dependency>
```

1
2
3
4
5

注意

当前包未传递依赖 MP 包，需要自己引入！

### [#](https://baomidou.com/pages/779a6e/#使用)使用

#### [#](https://baomidou.com/pages/779a6e/#快速生成)快速生成

```java
FastAutoGenerator.create("url", "username", "password")
    .globalConfig(builder -> {
        builder.author("baomidou") // 设置作者
            .enableSwagger() // 开启 swagger 模式
            .fileOverride() // 覆盖已生成文件
            .outputDir("D://"); // 指定输出目录
    })
    .packageConfig(builder -> {
        builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
            .moduleName("system") // 设置父包模块名
            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://")); // 设置mapperXml生成路径
    })
    .strategyConfig(builder -> {
        builder.addInclude("t_simple") // 设置需要生成的表名
            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
    })
    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
    .execute();
```