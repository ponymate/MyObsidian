# 不会的

### userserviceImpl

1.微信那一套

2.elerictionsearch

3.GSon

### annotation包

### aop包

### config包

### WxMpController

### fileController

### esdao包

### manager包

### mapper.job包

### utils包

### wxmp包

## annotation包

看不懂，不知道干啥

## aop包

看不懂，不知道干啥

## common包



#### BaseResponse类

通用数据返回类，所有controller返回的数据（即返回给前台的数据）均为这种类型。

~~~java
/**  
 * 通用返回类  
 */
@Data  
public class BaseResponse<T> implements Serializable {  
  
    private int code;  
  
    private T data;  
  
    private String message;  
  
    public BaseResponse(int code, T data, String message) {  
        this.code = code;  
        this.data = data;  
        this.message = message;  
    }  
  
    public BaseResponse(int code, T data) {  
        this(code, data, "");  
    }  
  
    public BaseResponse(ErrorCode errorCode) {  
        this(errorCode.getCode(), null, errorCode.getMessage());  
    }  
}
~~~

#### DeleteRequest类

因为删除一个数据库中的数据只需要 主键id即可，所以统一返回接口

~~~java
/**  
 * 删除请求  
 */
@Data  
public class DeleteRequest implements Serializable {  
  
    /**  
     * id     */    private Long id;  
  
    private static final long serialVersionUID = 1L;  
}
~~~


#### ErrorCode 枚举类

定义了所有的出错码，便于使用。

~~~java
/**  
 * 自定义错误码  
 */
public enum ErrorCode {  
  
    SUCCESS(0, "ok"),  
    PARAMS_ERROR(40000, "请求参数错误"),  
    NOT_LOGIN_ERROR(40100, "未登录"),  
    NO_AUTH_ERROR(40101, "无权限"),  
    NOT_FOUND_ERROR(40400, "请求数据不存在"),  
    FORBIDDEN_ERROR(40300, "禁止访问"),  
    SYSTEM_ERROR(50000, "系统内部异常"),  
    OPERATION_ERROR(50001, "操作失败");  
  
    /**  
     * 状态码  
     */  
    private final int code;  
  
    /**  
     * 信息  
     */  
    private final String message;  
  
    ErrorCode(int code, String message) {  
        this.code = code;  
        this.message = message;  
    }  
  
    public int getCode() {  
        return code;  
    }  
  
    public String getMessage() {  
        return message;  
    }  
  
}
~~~


#### PageRequest类

分页请求时所需要的数据，使每一个需要分页查询的实体类不需要每一个都添加分页所需要的属性或者为分页专门创建一个新的类。

~~~java
/**  
 * 分页请求  
 */
@Data  
public class PageRequest {  
  
    /**  
     * 当前页号  
     */  
    private long current = 1;  
  
    /**  
     * 页面大小  
     */  
    private long pageSize = 10;  
  
    /**  
     * 排序字段  
     */  
    private String sortField;  
  
    /**  
     * 排序顺序（默认升序）  
     */  
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;  
}
~~~

#### ResultUtils类

都是静态方法，用于在controller中将具体的BaseResponse返回出去
~~~java
/**  
 * 返回工具类  
 */  
public class ResultUtils {  
  
    /**  
     * 成功  
     *  
     * @param data  
     * @param <T>  
     * @return  
     */  
    public static <T> BaseResponse<T> success(T data) {  
        return new BaseResponse<>(0, data, "ok");  
    }  
  
    /**  
     * 失败  
     *  
     * @param errorCode  
     * @return  
     */  
    public static BaseResponse error(ErrorCode errorCode) {  
        return new BaseResponse<>(errorCode);  
    }  
  
    /**  
     * 失败  
     *  
     * @param code  
     * @param message  
     * @return  
     */  
    public static BaseResponse error(int code, String message) {  
        return new BaseResponse(code, null, message);  
    }  
  
    /**  
     * 失败  
     *  
     * @param errorCode  
     * @return  
     */  
    public static BaseResponse error(ErrorCode errorCode, String message) {  
        return new BaseResponse(errorCode.getCode(), null, message);  
    }  
}
~~~

## config包

#### CorsConfig类

不知道干啥

#### CosClientConfig

不知道干啥

#### JsonConfig类

不知道干啥

#### Knife4jConfig

不知道干啥

#### MyBatisPlusConfig

mybatisplus的配置
~~~java
/**  
 * MyBatis Plus 配置  
 */  
@Configuration  
@MapperScan("com.yupi.springbootinit.mapper")  
public class MyBatisPlusConfig {  
  
    /**  
     * 拦截器配置  
     *  
     * @return  
     */  
    @Bean  
    public MybatisPlusInterceptor mybatisPlusInterceptor() {  
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();  
        // 分页插件  
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));  
        return interceptor;  
    }  
}
~~~

#### WxOpenConfig

不到干啥

## constant包

#### CommonConstant类

~~~java
/**  
 * 通用常量  
 */  
public interface CommonConstant {  
  
    /**  
     * 升序  
     */  
    String SORT_ORDER_ASC = "ascend";  
  
    /**  
     * 降序  
     */  
    String SORT_ORDER_DESC = " descend";  
    }
}
    ~~~
    

#### FileConstant

~~~java
/**  
 * 文件常量  
 */  
public interface FileConstant {  
  
    /**  
     * COS 访问地址  
     * todo 需替换配置  
     */  
    String COS_HOST = "https://yupi.icu";  
}
~~~

#### UserConstant
~~~java
/**  
 * 文件常量  
 */  
public interface FileConstant {  
  
    /**  
     * COS 访问地址  
     * todo 需替换配置  
     */  
    String COS_HOST = "https://yupi.icu";  
}
~~~

## exception包

#### BusinessException类

带有状态码和message的自定义exception类

~~~java
/**  
 * 自定义异常类  
 */  
public class BusinessException extends RuntimeException {  
  
    /**  
     * 错误码  
     */  
    private final int code;   
    public BusinessException(int code, String message) {  
        super(message);  
        this.code = code;  
    }  
  
    public BusinessException(ErrorCode errorCode) {  
        super(errorCode.getMessage());  
        this.code = errorCode.getCode();  
    }  
  
    public BusinessException(ErrorCode errorCode, String message) {  
        super(message);  
        this.code = errorCode.getCode();  
    }  
  
    public int getCode() {  
        return code;  
    }  
}
~~~

#### GlobalExceptionHandler

具体讲解 
https://blog.csdn.net/user2025/article/details/105458842?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522167801809716800188550530%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=167801809716800188550530&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~baidu_landing_v2~default-4-105458842-null-null.142^v73^pc_new_rank,201^v4^add_ask,239^v2^insert_chatgpt&utm_term=%40RestControllerAdvice&spm=1018.2226.3001.4187

~~~java
/**  
 * 全局异常处理器  
 */  
@RestControllerAdvice  
@Slf4j  
public class GlobalExceptionHandler {  
  
    @ExceptionHandler(BusinessException.class)  
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {  
        log.error("BusinessException", e);  
        return ResultUtils.error(e.getCode(), e.getMessage());  
    }  
  
    @ExceptionHandler(RuntimeException.class)  
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {  
        log.error("RuntimeException", e);  
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");  
    }  
}
~~~

#### ThrowUtils类

如果condition为true，则抛出异常，用于检查数据是否符合条件

~~~java
/**  
 * 抛异常工具类  
 *  
 */public class ThrowUtils {  
  
    /**  
     * 条件成立则抛异常  
     *  
     * @param condition  
     * @param runtimeException  
     */  
    public static void throwIf(boolean condition, RuntimeException runtimeException) {  
        if (condition) {  
            throw runtimeException;  
        }    }  
  
    /**  
     * 条件成立则抛异常  
     *  
     * @param condition  
     * @param errorCode  
     */  
    public static void throwIf(boolean condition, ErrorCode errorCode) {  
        throwIf(condition, new BusinessException(errorCode));  
    }  
  
    /**  
     * 条件成立则抛异常  
     *  
     * @param condition  
     * @param errorCode  
     * @param message  
     */  
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {  
        throwIf(condition, new BusinessException(errorCode, message));  
    }  
}
~~~

## manager包

## mapper包

### job包

#### ××××Mapper类（xxx为实体类对象）

继承BaseMapper<××××>类，用于进行相关数据库操作
如果创建自己的sql语句方法，则创建接口方法

例如：
~~~java
/**  
 * 帖子数据库操作  
 *  
 */public interface PostMapper extends BaseMapper<Post> {  
  
    /**  
     * 查询帖子列表（包括已被删除的数据）  
     */  
    List<Post> listPostWithDelete(Date minUpdateTime);  
  
}
~~~


## model包

### entity包

数据库实体类包，和数据库中的类对应，用于操作数据库数据
**注意：**

1.在类名上添加@TableName(value="对应数据库中的表名")
2.序列化UID不需要存在
3.idDelete为逻辑删除
4.主键类型
~~~java
/**  
 * 用户  
 */  
@TableName(value = "user")  
@Data  
public class User implements Serializable {  
  
    /**  
     * id     
     * */   
    @TableId(type = IdType.ASSIGN_ID)  
    private Long id;  
  
    /**  
     * 用户账号  
     */  
    private String userAccount;  
  
    /**  
     * 用户密码  
     */  
    private String userPassword;  
  
    /**  
     * 开放平台id  
     */    private String unionId;  
  
    /**  
     * 公众号openId  
     */   
    private String mpOpenId;  
  
    /**  
     * 用户昵称  
     */  
    private String userName;  
  
    /**  
     * 用户头像  
     */  
    private String userAvatar;  
  
    /**  
     * 用户简介  
     */  
    private String userProfile;  
  
    /**  
     * 用户角色：user/admin/ban  
     */    private String userRole;  
  
    /**  
     * 创建时间  
     */  
    private Date createTime;  
  
    /**  
     * 更新时间  
     */  
    private Date updateTime;  
  
    /**  
     * 是否删除  
     */  
    @TableLogic  
    private Integer isDelete;  
  
    @TableField(exist = false)  
    private static final long serialVersionUID = 1L;  
}
~~~


### dto包

从前端接收到的数据，有多种类型

例如：
![[Pasted image 20230305205124.png]]
一个post实体类对应多个dto，用于不同场景前端传来的参数，根据实际情况减少或添加属性

**注意**
用于查询参数的类需要添加
***@EqualsAndHashCode(callSuper = true)** 参数

~~~java
/**  
 * 查询请求  
 *  
 */
 @EqualsAndHashCode(callSuper = true)  
@Data  
public class PostQueryRequest extends PageRequest implements Serializable {  
  
    /**  
     * id     */    
    private Long id;  
  
    /**  
     * id     */    private Long notId;  
  
    /**  
     * 搜索词  
     */  
    private String searchText;  
  
    /**  
     * 标题  
     */  
    private String title;  
  
    /**  
     * 内容  
     */  
    private String content;  
  
    /**  
     * 标签列表  
     */  
    private List<String> tags;  
  
    /**  
     * 至少有一个标签  
     */  
    private List<String> orTags;  
  
    /**  
     * 创建用户 id  
     */    private Long userId;  
  
    /**  
     * 收藏用户 id  
     */    private Long favourUserId;  
  
    private static final long serialVersionUID = 1L;  
}
~~~

### vo包

返回给前端的类型
一般是一个实体类型对应一个VO类型，一般属性比较全面，对象属性进行展开
对象内有VO类型和实体类型相互转化的方法

~~~java
@Data  
public class PostVO implements Serializable {  
  
    private final static Gson GSON = new Gson();  
  
    /**  
     * id     */    private Long id;  
  
    /**  
     * 标题  
     */  
    private String title;  
  
    /**  
     * 内容  
     */  
    private String content;  
  
    /**  
     * 点赞数  
     */  
    private Integer thumbNum;  
  
    /**  
     * 收藏数  
     */  
    private Integer favourNum;  
  
    /**  
     * 创建用户 id  
     */    private Long userId;  
  
    /**  
     * 创建时间  
     */  
    private Date createTime;  
  
    /**  
     * 更新时间  
     */  
    private Date updateTime;  
  
    /**  
     * 标签列表  
     */  
    private List<String> tagList;  
  
    /**  
     * 创建人信息  
     */  
    private UserVO user;  
  
    /**  
     * 是否已点赞  
     */  
    private Boolean hasThumb;  
  
    /**  
     * 是否已收藏  
     */  
    private Boolean hasFavour;  
  
    /**  
     * 包装类转对象  
     *  
     * @param postVO  
     * @return  
     */  
    public static Post voToObj(PostVO postVO) {  
        if (postVO == null) {  
            return null;  
        }        Post post = new Post();  
        BeanUtils.copyProperties(postVO, post);  
        List<String> tagList = postVO.getTagList();  
        if (tagList != null) {  
            post.setTags(GSON.toJson(tagList));  
        }        return post;  
    }  
  
    /**  
     * 对象转包装类  
     *  
     * @param post  
     * @return  
     */  
    public static PostVO objToVo(Post post) {  
        if (post == null) {  
            return null;  
        }        PostVO postVO = new PostVO();  
        BeanUtils.copyProperties(post, postVO);  
        postVO.setTagList(GSON.fromJson(post.getTags(), new TypeToken<List<String>>() {  
        }.getType()));  
        return postVO;  
    }  
}
~~~


### enums包

一般是两个方法，一个是获取value列表，一个是根据value获取枚举类型

#### FileUploadBizEnum类

~~~java
/**  
 * 文件上传业务类型枚举  
 *  
 */public enum FileUploadBizEnum {  
  
    USER_AVATAR("用户头像", "user_avatar");  
  
    private final String text;  
  
    private final String value;  
  
    FileUploadBizEnum(String text, String value) {  
        this.text = text;  
        this.value = value;  
    }  
  
    /**  
     * 获取值列表  
     *  
     * @return  
     */  
    public static List<String> getValues() {  
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());  
    }  
  
    /**  
     * 根据 value 获取枚举  
     *  
     * @param value  
     * @return  
     */  
    public static FileUploadBizEnum getEnumByValue(String value) {  
        if (ObjectUtils.isEmpty(value)) {  
            return null;  
        }        for (FileUploadBizEnum anEnum : FileUploadBizEnum.values()) {  
            if (anEnum.value.equals(value)) {  
                return anEnum;  
            }  
        }  
        return null;  
    }  
  
    public String getValue() {  
        return value;  
    }  
  
    public String getText() {  
        return text;  
    }  
}
~~~


#### UserRoleEnum类

~~~java
/**  
 * 用户角色枚举  
 *  
 */public enum UserRoleEnum {  
  
    USER("用户", "user"),  
    ADMIN("管理员", "admin"),  
    BAN("被封号", "ban");  
  
    private final String text;  
  
    private final String value;  
  
    UserRoleEnum(String text, String value) {  
        this.text = text;  
        this.value = value;  
    }  
  
    /**  
     * 获取值列表  
     *  
     * @return  
     */  
    public static List<String> getValues() {  
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());  
    }  
  
    /**  
     * 根据 value 获取枚举  
     *  
     * @param value  
     * @return  
     */  
    public static UserRoleEnum getEnumByValue(String value) {  
        if (ObjectUtils.isEmpty(value)) {  
            return null;  
        }        for (UserRoleEnum anEnum : UserRoleEnum.values()) {  
            if (anEnum.value.equals(value)) {  
                return anEnum;  
            }  
        }  
        return null;  
    }  
  
    public String getValue() {  
        return value;  
    }  
  
    public String getText() {  
        return text;  
    }  
}
~~~


## utils包

#### NetUtils

获取客户端ip

~~~java
/**  
 * 网络工具类  
 *  
 */public class NetUtils {  
  
    /**  
     * 获取客户端 IP 地址  
     *  
     * @param request  
     * @return  
     */  
    public static String getIpAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
            if (ip.equals("127.0.0.1")) {  
                // 根据网卡取本机配置的 IP                InetAddress inet = null;  
                try {  
                    inet = InetAddress.getLocalHost();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                if (inet != null) {  
                    ip = inet.getHostAddress();  
                }  
            }  
        }  
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if (ip != null && ip.length() > 15) {  
            if (ip.indexOf(",") > 0) {  
                ip = ip.substring(0, ip.indexOf(","));  
            }  
        }  
        if (ip == null) {  
            return "127.0.0.1";  
        }        return ip;  
    }  
  
}
~~~

#### SpringContextUtils

#### SqlUtils

防止sql注入，如果字符串中含有特殊符号的话，返回false

~~~java
/**  
 * SQL 工具  
 *  
 */public class SqlUtils {  
  
    /**  
     * 校验排序字段是否合法（防止 SQL 注入）  
     *  
     * @param sortField  
     * @return  
     */  
    public static boolean validSortField(String sortField) {  
        if (StringUtils.isBlank(sortField)) {  
            return false;  
        }        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");  
    }  
}
~~~


## serivice包

#### UserServiceImpl


---

**注册**

1.用户注册时的操作需要上锁，防止同时添加两个相同账户名的账号

2.使用md5算法加密

~~~java
public long userRegister(String userAccount, String userPassword, String checkPassword) {  
    // 1. 校验  
    if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {  
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");  
    }    if (userAccount.length() < 4) {  
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");  
    }    if (userPassword.length() < 8 || checkPassword.length() < 8) {  
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");  
    }    // 密码和校验密码相同  
    if (!userPassword.equals(checkPassword)) {  
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");  
    }    
    synchronized (userAccount.intern()) {  
        // 账户不能重复  
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();  
        queryWrapper.eq("userAccount", userAccount);  
        long count = this.baseMapper.selectCount(queryWrapper);  
        if (count > 0) {  
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");  
        }  
        // 2. 加密  
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());  
        // 3. 插入数据  
        User user = new User();  
        user.setUserAccount(userAccount);  
        user.setUserPassword(encryptPassword);  
        boolean saveResult = this.save(user);  
        if (!saveResult) {  
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");  
        }  
        return user.getId();  
    }
}
~~~

**用户登录**

1.将用户信息传入session
~~~java
@Override  
public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {  
    // 1. 校验  
    if (StringUtils.isAnyBlank(userAccount, userPassword)) {  
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");  
    }    if (userAccount.length() < 4) {  
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");  
    }    if (userPassword.length() < 8) {  
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");  
    }    // 2. 加密  
    String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());  
    // 查询用户是否存在  
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();  
    queryWrapper.eq("userAccount", userAccount);  
    queryWrapper.eq("userPassword", encryptPassword);  
    User user = this.baseMapper.selectOne(queryWrapper);  
    // 用户不存在  
    if (user == null) {  
        log.info("user login failed, userAccount cannot match userPassword");  
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");  
    }    // 3. 记录用户的登录态  
    request.getSession().setAttribute(USER_LOGIN_STATE, user);  
    return this.getLoginUserVO(user);  
}
~~~
**用户登出**

1.删除session中的数据进行登出
~~~java
@Override  
public boolean userLogout(HttpServletRequest request) {  
    if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {  
        throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");  
    }    // 移除登录态  
    request.getSession().removeAttribute(USER_LOGIN_STATE);  
    return true;  
}
~~~
**获取当前登录用户**

1.通过session获取，再查询数据库
~~~java
@Override  
public User getLoginUser(HttpServletRequest request) {  
    // 先判断是否已登录  
    Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);  
    User currentUser = (User) userObj;  
    if (currentUser == null || currentUser.getId() == null) {  
        throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);  
    }    // 从数据库查询（追求性能的话可以注释，直接走缓存）  
    long userId = currentUser.getId();  
    currentUser = this.getById(userId);  
    if (currentUser == null) {  
        throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);  
    }    return currentUser;  
}
~~~

2.别的接口使用，允许返回空值（比如作为别的对象的属性时）
~~~java
/**  
 * 获取当前登录用户（允许未登录）  
 *  
 * @param request  
 * @return  
 */  
@Override  
public User getLoginUserPermitNull(HttpServletRequest request) {  
    // 先判断是否已登录  
    Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);  
    User currentUser = (User) userObj;  
    if (currentUser == null || currentUser.getId() == null) {  
        return null;  
    }    // 从数据库查询（追求性能的话可以注释，直接走缓存）  
    long userId = currentUser.getId();  
    return this.getById(userId);  
}
~~~

### PostServiceImpl

**分页查询时，或者查询条件比较多时，可以提取获取QueryWrapper的方法**

**PostController类**

~~~java
/**  
 * 分页获取列表（封装类）  
 *  
 * @param postQueryRequest  
 * @param request  
 * @return  
 */  
@PostMapping("/list/page/vo")  
public BaseResponse<Page<PostVO>> listPostVOByPage(@RequestBody PostQueryRequest postQueryRequest,  
        HttpServletRequest request) {  
    long current = postQueryRequest.getCurrent();  
    long size = postQueryRequest.getPageSize();  
    // 限制爬虫  
    ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);  
    Page<Post> postPage = postService.page(new Page<>(current, size),  
            postService.getQueryWrapper(postQueryRequest));  
    return ResultUtils.success(postService.getPostVOPage(postPage, request));  
}  
  
/**  
 * 分页获取当前用户创建的资源列表  
 *  
 * @param postQueryRequest  
 * @param request  
 * @return  
 */  
@PostMapping("/my/list/page/vo")  
public BaseResponse<Page<PostVO>> listMyPostVOByPage(@RequestBody PostQueryRequest postQueryRequest,  
        HttpServletRequest request) {  
    if (postQueryRequest == null) {  
        throw new BusinessException(ErrorCode.PARAMS_ERROR);  
    }    User loginUser = userService.getLoginUser(request);  
    postQueryRequest.setUserId(loginUser.getId());  
    long current = postQueryRequest.getCurrent();  
    long size = postQueryRequest.getPageSize();  
    // 限制爬虫  
    ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);  
    Page<Post> postPage = postService.page(new Page<>(current, size),  
            postService.getQueryWrapper(postQueryRequest));  
    return ResultUtils.success(postService.getPostVOPage(postPage, request));  
}

~~~

**PostServiceImpl类**

~~~java
@Override  
public QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest) {  
    QueryWrapper<Post> queryWrapper = new QueryWrapper<>();  
    if (postQueryRequest == null) {  
        return queryWrapper;  
    }    String searchText = postQueryRequest.getSearchText();  
    String sortField = postQueryRequest.getSortField();  
    String sortOrder = postQueryRequest.getSortOrder();  
    Long id = postQueryRequest.getId();  
    String title = postQueryRequest.getTitle();  
    String content = postQueryRequest.getContent();  
    List<String> tagList = postQueryRequest.getTags();  
    Long userId = postQueryRequest.getUserId();  
    Long notId = postQueryRequest.getNotId();  
    // 拼接查询条件  
    if (StringUtils.isNotBlank(searchText)) {  
        queryWrapper.like("title", title).or().like("content", content);  
    }    queryWrapper.like(StringUtils.isNotBlank(title), "title", title);  
    queryWrapper.like(StringUtils.isNotBlank(content), "content", content);  
    if (CollectionUtils.isNotEmpty(tagList)) {  
        for (String tag : tagList) {  
            queryWrapper.like("tags", "\"" + tag + "\"");  
        }  
    }  
    queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);  
    queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);  
    queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);  
    queryWrapper.eq("isDelete", false);  
    queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),  
            sortField);  
    return queryWrapper;  
}
~~~

**批量将post对象改为postvo对象**

~~~java
@Override  
public Page<PostVO> getPostVOPage(Page<Post> postPage, HttpServletRequest request) {  
    List<Post> postList = postPage.getRecords();  
    Page<PostVO> postVOPage = new Page<>(postPage.getCurrent(), postPage.getSize(), postPage.getTotal());  
    if (CollectionUtils.isEmpty(postList)) {  
        return postVOPage;  
    }    // 1. 关联查询用户信息  
    Set<Long> userIdSet = postList.stream().map(Post::getUserId).collect(Collectors.toSet());  
    Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()  
            .collect(Collectors.groupingBy(User::getId));  
    // 2. 已登录，获取用户点赞、收藏状态  
    Map<Long, Boolean> postIdHasThumbMap = new HashMap<>();  
    Map<Long, Boolean> postIdHasFavourMap = new HashMap<>();  
    User loginUser = userService.getLoginUserPermitNull(request);  
    if (loginUser != null) {  
        Set<Long> postIdSet = postList.stream().map(Post::getId).collect(Collectors.toSet());  
        loginUser = userService.getLoginUser(request);  
        // 获取点赞  
        QueryWrapper<PostThumb> postThumbQueryWrapper = new QueryWrapper<>();  
        postThumbQueryWrapper.in("postId", postIdSet);  
        postThumbQueryWrapper.eq("userId", loginUser.getId());  
        List<PostThumb> postPostThumbList = postThumbMapper.selectList(postThumbQueryWrapper);  
        postPostThumbList.forEach(postPostThumb -> postIdHasThumbMap.put(postPostThumb.getPostId(), true));  
        // 获取收藏  
        QueryWrapper<PostFavour> postFavourQueryWrapper = new QueryWrapper<>();  
        postFavourQueryWrapper.in("postId", postIdSet);  
        postFavourQueryWrapper.eq("userId", loginUser.getId());  
        List<PostFavour> postFavourList = postFavourMapper.selectList(postFavourQueryWrapper);  
        postFavourList.forEach(postFavour -> postIdHasFavourMap.put(postFavour.getPostId(), true));  
    }    // 填充信息  
    List<PostVO> postVOList = postList.stream().map(post -> {  
        PostVO postVO = PostVO.objToVo(post);  
        Long userId = post.getUserId();  
        User user = null;  
        if (userIdUserListMap.containsKey(userId)) {  
            user = userIdUserListMap.get(userId).get(0);  
        }  
        postVO.setUser(userService.getUserVO(user));  
        postVO.setHasThumb(postIdHasThumbMap.getOrDefault(post.getId(), false));  
        postVO.setHasFavour(postIdHasFavourMap.getOrDefault(post.getId(), false));  
        return postVO;  
    }).collect(Collectors.toList());  
    postVOPage.setRecords(postVOList);  
    return postVOPage;  
}
~~~
