## ES6与异步编程高级用法
### ES6模块化
#### 导出导入
默认导出

- export default 默认导出的成员
- 只允许使用一次

默认导入

- import 接收名称 form '模块标识符'

按需导出

- export 导出成员

按需导入

- import { 名称 } from '模块标识符'

直接导入代码

- import '模块标识符'

 ① 每个模块中可以使用多次按需导出
 ② 按需导入的成员名称必须和按需导出的名称保持一致
 ③ 按需导入时，可以使用 as 关键字进行重命名 
 ④ 按需导入可以和默认导入一起使用  

### Promise
#### 概述
 Promise 是一个构造函数  
 创建 Promise 的实例 const p = new Promise()  
 new 出来的 Promise 实例对象，代表一个异步操作  
 Promise.prototype 上包含一个 .then() 方法   
 每一次 new Promise() 构造函数得到的实例对象都可以通过原型链的方式访问到 .then() 方法，例如 p.then()  
 .then() 方法用来预先指定成功和失败的回调函数 
 p.then(成功的回调函数，失败的回调函数) 
 p.then(result => { }, error => { }) 调用 .then() 方法时，成功的回调函数是必选的、失败的回调函数是可选的  

#### 读取文件内容
 安装 then-fs 这个第三方包，从而支持我们基于 Promise 的方式读取文件的内容：  
在 Promise 的链式操作中如果发生了错误，可以使用 Promise.prototype.catch 方法进行捕获和处理：  
 Promise.all() 方法会发起并行的 Promise 异步操作，等所有的异步操作全部结束后才会执行下一步的 .then 操作（等待机制）
 Promise.race() 方法会发起并行的 Promise 异步操作，只要任何一个异步操作完成，就立即执行下一步的 .then 操作（赛跑机制）  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662297875454-4d975050-782d-472e-95f6-fc9fb7c313c9.png#clientId=u15aa7738-7cf7-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=106&id=u5c15a4bd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=133&originWidth=1031&originalType=binary&ratio=1&rotation=0&showTitle=false&size=25968&status=done&style=none&taskId=u0768e36b-6996-4c1d-afe8-aa75fab5416&title=&width=824.8)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662345184841-f046e3cf-d006-4b3d-81c8-f4b2534068bd.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=444&id=u1f6b1ae1&margin=%5Bobject%20Object%5D&name=image.png&originHeight=555&originWidth=893&originalType=binary&ratio=1&rotation=0&showTitle=false&size=123632&status=done&style=none&taskId=u76bbc7e4-01d5-48e2-ba60-492e78941d0&title=&width=714.4)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662345206844-fcfc8cdc-816a-441a-bccd-4ae139b46a35.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=406&id=u09e35992&margin=%5Bobject%20Object%5D&name=image.png&originHeight=508&originWidth=864&originalType=binary&ratio=1&rotation=0&showTitle=false&size=119582&status=done&style=none&taskId=ud86b92e8-ae27-44ee-a776-5535bd4ac70&title=&width=691.2)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662345213712-02baf6e9-e15d-42f7-977f-68d9df2e3f04.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=412&id=u3e50acd8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=515&originWidth=864&originalType=binary&ratio=1&rotation=0&showTitle=false&size=128483&status=done&style=none&taskId=u497e6b8c-0983-4127-a0d0-54e0e9162dd&title=&width=691.2)
 
### async/await
 async/await 是 ES8（ECMAScript 2017）引入的新语法，用来简化 Promise 异步操作。  
 在 async 方法中，第一个 await 之前的代码会同步执行，await 之后的代码会异步执行  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662345519944-a0335ee9-a3ae-4e44-9a3e-70e9ee559fd3.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=447&id=ufed06332&margin=%5Bobject%20Object%5D&name=image.png&originHeight=559&originWidth=996&originalType=binary&ratio=1&rotation=0&showTitle=false&size=110317&status=done&style=none&taskId=uf8b09b03-6379-45d0-be93-ccd64a71b69&title=&width=796.8)

### EventLoop
① 同步任务由 JavaScript 主线程次序执行 
② 异步任务委托给宿主环境执行 
③ 已完成的异步任务对应的回调函数，会被加入到任务队列中等待执行 
④ JavaScript 主线程的执行栈被清空后，会读取任务队列中的回调函数，次序执行 
⑤ JavaScript 主线程不断重复上面的第 4 步  

### 宏任务和微任务
 JavaScript 把异步任务又做了进一步的划分，异步任务又分为两类
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662345748496-94857945-d632-42c6-a48c-9cbdeafdcbb2.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=409&id=u2210ecad&margin=%5Bobject%20Object%5D&name=image.png&originHeight=511&originWidth=534&originalType=binary&ratio=1&rotation=0&showTitle=false&size=61130&status=done&style=none&taskId=u6b9ac99f-15cd-45ae-9aa4-289148dec5a&title=&width=427.2)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662345749721-2eb0ce69-a95b-4967-9f1a-cfcb57b634ab.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=172&id=u003762f2&margin=%5Bobject%20Object%5D&name=image.png&originHeight=215&originWidth=955&originalType=binary&ratio=1&rotation=0&showTitle=false&size=193673&status=done&style=none&taskId=u5ab33f25-2078-4720-a6a9-6018bc06feb&title=&width=764)


## 概述
 vue 框架的特性，主要体现在如下两方面： 
① 数据驱动视图 
 在使用了 vue 的页面中，vue 会监听数据的变化，从而自动重新渲染页面的结构。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346147318-431ffa47-87a4-45d0-9649-02c92a244edf.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=257&id=u8eb47ec6&margin=%5Bobject%20Object%5D&name=image.png&originHeight=321&originWidth=1000&originalType=binary&ratio=1&rotation=0&showTitle=false&size=253828&status=done&style=none&taskId=ub9eb18ed-bd4f-499e-bde5-22c9b113f3c&title=&width=800)
② 双向数据绑定  
在填写表单时，双向数据绑定可以辅助开发者在不操作 DOM 的前提下，自动把用户填写的内容同步到数据源 中。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346158634-9f115e79-ca83-4e9a-bbbf-968d2e663e92.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=245&id=u82ac2446&margin=%5Bobject%20Object%5D&name=image.png&originHeight=306&originWidth=844&originalType=binary&ratio=1&rotation=0&showTitle=false&size=215644&status=done&style=none&taskId=uf5e99cc6-c315-48b4-99ba-8e151e532c8&title=&width=675.2)

 MVVM 是 vue 实现数据驱动视图和双向数据绑定的核心原理。它把每个 HTML 页面都拆分成了如下三个部分：  
View 表示当前页面所渲染的 DOM 结构。
Model 表示当前页面渲染时所依赖的数据源。 
ViewModel 表示 vue 的实例，它是 MVVM 的核心。  

 ViewModel 作为 MVVM 的核心，是它把当前页面的数据源（Model）和页面的结构（View）连接在了一起  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346245341-403c0dec-c141-46bc-a39c-e9079bd43e2b.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=191&id=ucb71f83e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=239&originWidth=1232&originalType=binary&ratio=1&rotation=0&showTitle=false&size=274048&status=done&style=none&taskId=u396f87f4-de47-4ad7-82b0-7801d917cc5&title=&width=985.6)


## Vue指令
 指令（Directives）是 vue 为开发者提供的模板语法，用于辅助开发者渲染页面的基本结构  
 vue 中的指令按照不同的用途可以分为如下 6 大类： 
① 内容渲染指令 
② 属性绑定指令 
③ 事件绑定指令 
④ 双向绑定指令 
⑤ 条件渲染指令 
⑥ 列表渲染指令  
### 内容渲染指令
 内容渲染指令用来辅助开发者渲染 DOM 元素的文本内容。常用的内容渲染指令有如下 3 个： 

- v-text  

v-text 指令会覆盖元素内默认的值  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346535490-56b63e60-c89e-4745-ab8e-dd75df68f77f.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=279&id=u3b3642ec&margin=%5Bobject%20Object%5D&name=image.png&originHeight=349&originWidth=1029&originalType=binary&ratio=1&rotation=0&showTitle=false&size=118281&status=done&style=none&taskId=u59959803-838d-410a-8f7c-f66f321574a&title=&width=823.2)

- {{ }}  

 相对于 v-text 指令来说，插值表达式在开发中更常用一些！因为它不会覆盖元素中默认的文本内容  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346579600-dd1bee56-254c-451c-a906-9e516f9206d0.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=216&id=u28bdcde3&margin=%5Bobject%20Object%5D&name=image.png&originHeight=270&originWidth=1029&originalType=binary&ratio=1&rotation=0&showTitle=false&size=88485&status=done&style=none&taskId=ufe98222c-05e5-44de-b0af-ff9acadec83&title=&width=823.2)

- v-html  

 如果要把包含 HTML 标签的字符串渲染为页面的 HTML 元素， 则需要用到 v-html 这个指令  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346596344-d10ea3c1-19d4-45be-9d6a-e6ca5053b1f0.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=216&id=u7c707b1f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=270&originWidth=1036&originalType=binary&ratio=1&rotation=0&showTitle=false&size=94135&status=done&style=none&taskId=u489433db-dd57-48a2-97a7-ea16e683d1d&title=&width=828.8)

### 属性绑定指令
 为元素的属性动态绑定属性值，则需要用到 v-bind 属性绑定指令  
 由于 v-bind 指令在开发中使用频率非常高，因此，vue 官方为其提供了简写形式（简写为英文的 : ）  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346675371-7f3d30ee-ec94-4fdf-9828-8ca9b8e77d3f.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=474&id=u808d221d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=593&originWidth=1028&originalType=binary&ratio=1&rotation=0&showTitle=false&size=147724&status=done&style=none&taskId=u41c6d885-5cfd-4bf5-bc99-70999837f73&title=&width=822.4)

### 使用Javascript表达式
 在 vue 提供的模板渲染语法中，除了支持绑定简单的数据值之外，还支持 Javascript 表达式的运算  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346766245-6e6212c2-a498-49bc-9a4b-49ac66c0a81f.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=313&id=u1a8b26fa&margin=%5Bobject%20Object%5D&name=image.png&originHeight=391&originWidth=1030&originalType=binary&ratio=1&rotation=0&showTitle=false&size=80573&status=done&style=none&taskId=ucbbabe04-9a2f-4fad-8cf3-a44f3bdb593&title=&width=824)

### 事件绑定指令
 vue 提供了 v-on 事件绑定指令，用来辅助程序员为 DOM 元素绑定事件监听。  
 由于 v-on 指令在开发中使用频率非常高，因此，vue 官方为其提供了简写形式（简写为英文的 @ ）  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346853388-f18e3c09-1919-4ebb-bc68-715f3a83243e.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=410&id=u0241c6ef&margin=%5Bobject%20Object%5D&name=image.png&originHeight=513&originWidth=1036&originalType=binary&ratio=1&rotation=0&showTitle=false&size=129351&status=done&style=none&taskId=u4d06d351-6ea0-4016-9585-e85072fe1b5&title=&width=828.8)
 通过 v-on 绑定的事件处理函数，需要在 methods 节点中进行声明  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346836952-cf6026e1-c236-46d7-a9db-18ee8efae8c0.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=438&id=uffc4b921&margin=%5Bobject%20Object%5D&name=image.png&originHeight=548&originWidth=1041&originalType=binary&ratio=1&rotation=0&showTitle=false&size=124471&status=done&style=none&taskId=ua541391d-4c2e-4fc8-957d-76c4a537aee&title=&width=832.8) 在原生的 DOM 事件绑定中，可以在事件处理函数的形参处，接收事件对象 event。同理，在 v-on 指令（简 写为 @ ）所绑定的事件处理函数中，同样可以接收到事件对象 event  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346886688-f915cf1e-e484-4aee-a10d-6a9a1475b4da.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=409&id=u372a4e01&margin=%5Bobject%20Object%5D&name=image.png&originHeight=511&originWidth=1035&originalType=binary&ratio=1&rotation=0&showTitle=false&size=125451&status=done&style=none&taskId=u2cb952bb-caa9-408b-a8e2-7d4a9738a11&title=&width=828)
 $event 是 vue 提供的特殊变量，用来表示原生的事件参数对象 event。$event 可以解决事件参数对象 event 被覆盖的问题。示例用法如下  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346950681-db953a02-6268-4c07-80e6-6d89d0c49ac2.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=446&id=uc8bc3c38&margin=%5Bobject%20Object%5D&name=image.png&originHeight=558&originWidth=1030&originalType=binary&ratio=1&rotation=0&showTitle=false&size=144544&status=done&style=none&taskId=ud9239657-94fc-45cd-bdd8-00fcdf30c1f&title=&width=824) 在事件处理函数中调用 preventDefault() 或 stopPropagation() 是非常常见的需求。因此，vue 提供了事件 修饰符的概念，来辅助程序员更方便的对事件的触发进行控制。常用的 5 个事件修饰符如下：  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662346983813-9fa0269b-a585-4110-9e7d-0bcd88ee2b83.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=449&id=u32536160&margin=%5Bobject%20Object%5D&name=image.png&originHeight=561&originWidth=943&originalType=binary&ratio=1&rotation=0&showTitle=false&size=171702&status=done&style=none&taskId=uec8efde9-b2ff-483c-ba71-5e1f1ef0d3d&title=&width=754.4) 在监听键盘事件时，我们经常需要判断详细的按键。此时，可以为键盘相关的事件添加按键修饰符  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662347029859-ead8e5e1-71e4-4af1-be33-59ff74f6b90c.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=249&id=uceed02dd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=311&originWidth=1037&originalType=binary&ratio=1&rotation=0&showTitle=false&size=93259&status=done&style=none&taskId=uec041130-d824-4ee5-a78a-a92317ef809&title=&width=829.6)

### 双向绑定指令
 vue 提供了 v-model 双向数据绑定指令，用来辅助开发者在不操作 DOM 的前提下，快速获取表单的数据  
 v-model 指令只能配合表单元素一起使用  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662347075401-93e3c4fc-5f25-4822-9ac5-316299ba6547.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=408&id=u3c905f64&margin=%5Bobject%20Object%5D&name=image.png&originHeight=510&originWidth=1032&originalType=binary&ratio=1&rotation=0&showTitle=false&size=135452&status=done&style=none&taskId=ud813077c-1c7c-4eb3-b060-e2bc786350b&title=&width=825.6)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662347116366-4d7dac8c-f4a4-48ec-a52c-2652c9f5cdaf.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=266&id=uc5439d57&margin=%5Bobject%20Object%5D&name=image.png&originHeight=333&originWidth=1362&originalType=binary&ratio=1&rotation=0&showTitle=false&size=156518&status=done&style=none&taskId=udf97264e-cecc-4757-bbbd-a777d6f3e3e&title=&width=1089.6)

### 条件渲染指令
 条件渲染指令用来辅助开发者按需控制 DOM 的显示与隐藏。条件渲染指令有如下两个，分别是： 
v-if 
v-show  

实现原理不同： 
 v-if 指令会动态地创建或移除 DOM 元素，从而控制元素在页面上的显示与隐藏； 
 v-show 指令会动态为元素添加或移除 style="display: none;" 样式，从而控制元素的显示与隐藏； 

性能消耗不同： 
v-if 有更高的切换开销，而 v-show 有更高的初始渲染开销。 
 如果需要非常频繁地切换，则使用 v-show 较好 
 如果在运行时条件很少改变，则使用 v-if 较好  

 v-if 可以单独使用，或配合 v-else 指令一起使用  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662347200077-c750256c-6581-4a80-989f-215a4058c24e.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=284&id=u95258e2a&margin=%5Bobject%20Object%5D&name=image.png&originHeight=355&originWidth=1034&originalType=binary&ratio=1&rotation=0&showTitle=false&size=79686&status=done&style=none&taskId=u8d2c1d0b-12b8-4897-9087-91dc1ae4f86&title=&width=827.2)
 v-else-if 指令，顾名思义，充当 v-if 的“else-if 块”，可以连续使用  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662347225054-afb5bd9b-2d8b-4562-ba87-0efd7054add8.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=211&id=u4ba35f88&margin=%5Bobject%20Object%5D&name=image.png&originHeight=264&originWidth=1022&originalType=binary&ratio=1&rotation=0&showTitle=false&size=80264&status=done&style=none&taskId=u0fd280bd-f224-4492-9477-7b5eca079d1&title=&width=817.6)

### 列表渲染指令
 vue 提供了 v-for 指令，用来辅助开发者基于一个数组来循环渲染相似的 UI 结构。
 v-for 指令需要使用 item in items 的特殊语法，其中： items 是待循环的数组 , item 是当前的循环项  
 v-for 指令还支持一个可选的第二个参数，即当前项的索引。语法格式为 (item, index) in items  
 v-for 指令中的 item 项和 index 索引都是形参，可以根据需要进行重命名。例如 (user, i) in userlist  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662347356277-13202ba1-2768-4b27-9a78-5f416dfe2c9b.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=407&id=uf1b81737&margin=%5Bobject%20Object%5D&name=image.png&originHeight=509&originWidth=1026&originalType=binary&ratio=1&rotation=0&showTitle=false&size=100614&status=done&style=none&taskId=uca2c1bf1-5006-46dd-8921-31cac0270f0&title=&width=820.8)
当列表的数据变化时，默认情况下，vue 会尽可能的复用已存在的 DOM 元素，从而提升渲染的性能。
但这种 默认的性能优化策略，会导致有状态的列表无法被正确更新。 
为了给 vue 一个提示，以便它能跟踪每个节点的身份，从而在保证有状态的列表被正确更新的前提下，提升渲 染的性能。此时，需要为每项提供一个唯一的 key 属性  
① key 的值只能是字符串或数字类型 
② key 的值必须具有唯一性（即：key 的值不能重复） 
③ 建议把数据项 id 属性的值作为 key 的值（因为 id 属性的值具有唯一性） 
④ 使用 index 的值当作 key 的值没有任何意义（因为 index 的值不具有唯一性）
⑤ 建议使用 v-for 指令时一定要指定 key 的值（既提升性能、又防止列表状态紊乱）  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662347432330-c4c8f02f-2598-4fc6-bcfd-d5a028b530a3.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=354&id=u0a87445f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=442&originWidth=893&originalType=binary&ratio=1&rotation=0&showTitle=false&size=79851&status=done&style=none&taskId=ud5cc15a4-3f3c-4883-86a0-620249dad2b&title=&width=714.4)

## Vite
### 概述
创建vite项目
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662347732033-63ffc66b-0d51-40cf-b05e-f16aa8d5ea0e.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=264&id=u3d80b6c4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=330&originWidth=1234&originalType=binary&ratio=1&rotation=0&showTitle=false&size=72977&status=done&style=none&taskId=ud5e2c7f8-4ef1-43ce-941f-a736aefc1ae&title=&width=987.2)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662347754551-c4d1c587-ec12-42a8-84a5-dbb361af8329.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=446&id=uf0e93f35&margin=%5Bobject%20Object%5D&name=image.png&originHeight=557&originWidth=1444&originalType=binary&ratio=1&rotation=0&showTitle=false&size=276400&status=done&style=none&taskId=u5fcad9bd-ceaf-4e61-9b50-cef882ad49c&title=&width=1155.2)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662348112845-fd1a46a3-6817-4b89-96ae-e934f46ce27d.png#clientId=u84d212d2-84d1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=287&id=u40df7b46&margin=%5Bobject%20Object%5D&name=image.png&originHeight=359&originWidth=996&originalType=binary&ratio=1&rotation=0&showTitle=false&size=131050&status=done&style=none&taskId=u93957e1c-b0fe-4881-a43c-cb79a9542fb&title=&width=796.8)
 在工程化的项目中，vue 要做的事情很单纯：通过 main.js 把 App.vue 渲染到 index.html 的指定区域中  
① App.vue 用来编写待渲染的模板结构 
② index.html 中需要预留一个 el 区域 
③ main.js 把 App.vue 渲染到了 index.html 所预留的区域中  

### 组件的构成
 每个 .vue 组件都由 3 部分构成，分别是： 
 template -> 组件的模板结构 
 script -> 组件的 JavaScript 行为 
 style -> 组件的样式 其中，每个组件中必须包含 template 模板结构，而 script 行为和 style 样式是可选的组成部分  

#### templete节点
 vue 规定：每个组件对应的模板结构，需要定义到  节点中 
 在组件的  节点中，支持使用前面所学的指令语法，来辅助开发者渲染当前组件的 DOM 结构 

#### script节点
 可以通过 name 节点为当前组件定义一个名称  
 vue 组件渲染期间需要用到的数据，可以定义在 data 节点中   
 vue 规定：组件中的 data 必须是一个函数，不能直接指向一个数据对象  
 组件中的事件处理函数，必须定义到 methods 节点中  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662362348619-4a1ad4e9-b881-4456-8289-31b06954f19d.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=480&id=u0bea8432&margin=%5Bobject%20Object%5D&name=image.png&originHeight=600&originWidth=1085&originalType=binary&ratio=1&rotation=0&showTitle=false&size=94236&status=done&style=none&taskId=u83c78a17-3f1c-4f47-9a49-f8814a83186&title=&width=868)

#### style
 如果希望使用 less 语法编写组件的 style 样式，可以按照如下两个步骤进行配置： 
① 运行 npm install less -D 命令安装依赖包，从而提供 less 语法的编译支持 
② 在 <style> 标签上添加 lang="less" 属性，即可使用 less 语法编写组件的样式  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662362561300-2df6642b-c3d2-47fc-ae5c-262c986e5548.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=376&id=u29e4a9c0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=470&originWidth=1128&originalType=binary&ratio=1&rotation=0&showTitle=false&size=61113&status=done&style=none&taskId=u25bd57d7-90a9-47f9-95f5-bc55b0d221c&title=&width=902.4)

## 组件的基本使用
### 组件的注册
 vue 中组件的引用原则：先注册后使用  
 vue 中注册组件的方式分为“全局注册”和“局部注册”两种，其中： 
 被全局注册的组件，可以在全局任何一个组件内使用 
 被局部注册的组件，只能在当前注册的范围内使用  
#### 全局注册
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662362778363-414e93ba-9da2-48f4-a819-153af4a9f94d.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=499&id=udec6da6f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=624&originWidth=1128&originalType=binary&ratio=1&rotation=0&showTitle=false&size=162939&status=done&style=none&taskId=u8890a308-38f5-4641-9f8b-47797ce744e&title=&width=902.4)
 使用 app.component() 方法注册的全局组件，直接以标签的形式进行使用即可  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662362789733-82a982ea-4da5-4a3a-9d89-789b7d2e6eb2.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=433&id=u362ae9b4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=541&originWidth=1125&originalType=binary&ratio=1&rotation=0&showTitle=false&size=140223&status=done&style=none&taskId=u241f12d1-351c-4435-b25f-04b0ec2410e&title=&width=900)

#### 局部注册
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662362843143-22afdc35-933d-4f6a-a379-75d9846fd6e7.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=530&id=ued46dc94&margin=%5Bobject%20Object%5D&name=image.png&originHeight=662&originWidth=1134&originalType=binary&ratio=1&rotation=0&showTitle=false&size=137714&status=done&style=none&taskId=u7a07c1f4-f1e6-4f0d-9ea9-292d8c288c1&title=&width=907.2)

#### 通过name属性注册组件
 在注册组件期间，除了可以直接提供组件的注册名称之外，还可以把组件的 name 属性作为注册后组件的名称  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662362920175-22174397-fd55-4b0e-af56-8e256b549b67.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=442&id=uc7981d8f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=552&originWidth=1061&originalType=binary&ratio=1&rotation=0&showTitle=false&size=137801&status=done&style=none&taskId=u886a55ef-8f11-4c1b-bb04-82ff0eab539&title=&width=848.8)

### 组件之间的样式冲突问题
默认情况下，写在 .vue 组件中的样式会全局生效，因此很容易造成多个组件之间的样式冲突问题。导致组件 之间样式冲突的根本原因是： 
① 单页面应用程序中，所有组件的 DOM 结构，都是基于唯一的 index.html 页面进行呈现的 
② 每个组件中的样式，都会影响整个 index.html 页面中的 DOM 元素  

 为了提高开发效率和开发体验，vue 为 style 节点提供了 scoped 属性，从而防止组件之间的样式冲突问题  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662363206367-ec0a97d8-786b-456d-bb04-b08ac963bcc6.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=484&id=u06d00ba3&margin=%5Bobject%20Object%5D&name=image.png&originHeight=605&originWidth=1084&originalType=binary&ratio=1&rotation=0&showTitle=false&size=141759&status=done&style=none&taskId=u2f76d527-8f53-41f8-93a4-04660272389&title=&width=867.2)

 如果给当前组件的 style 节点添加了 scoped 属性，则当前组件的样式对其子组件是不生效的。如果想让某些样 式对子组件生效，可以使用 :deep()  深度选择器  
 /deep/ 是 vue2.x 中实现样式穿透的方案。在 vue3.x 中推荐使用 :deep() 替代 /deep/  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662363249170-4a99fbab-adf8-478e-ac3b-7ca029fe7e95.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=372&id=u69ee4c3b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=465&originWidth=1131&originalType=binary&ratio=1&rotation=0&showTitle=false&size=109705&status=done&style=none&taskId=u3dbd7b69-830a-4165-8a56-96f299566d3&title=&width=904.8)

### 组件的props
 为了提高组件的复用性，在封装 vue 组件时需要遵守如下的原则： 
 组件的 DOM 结构、Style 样式要尽量复用 
 组件中要展示的数据，尽量由组件的使用者提供  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662363392635-0c167eb0-d1c0-45ea-9ddb-4cdc9a99ea71.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=147&id=u7aac7141&margin=%5Bobject%20Object%5D&name=image.png&originHeight=184&originWidth=1130&originalType=binary&ratio=1&rotation=0&showTitle=false&size=69534&status=done&style=none&taskId=u84b52e61-9b06-4e38-8fc8-997cecd0a9b&title=&width=904)
 props 是组件的自定义属性，组件的使用者可以通过 props 把数据传递到子组件内部，供子组件内部进行使 用  
 props 的作用：父组件通过 props 向子组件传递要展示的数据。
 props 的好处：提高了组件的复用性  

 在封装 vue 组件时，可以把动态的数据项声明为 props 自定义属性。自定义属性可以在当前组件的模板结构 中被直接使用。  
 如果父组件给子组件传递了未声明的 props 属性，则这些属性会被忽略，无法被子组件使用  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662363461623-3087d9e0-2da5-4d17-a97f-f43925e1f2d3.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=434&id=ub9df80cf&margin=%5Bobject%20Object%5D&name=image.png&originHeight=543&originWidth=1134&originalType=binary&ratio=1&rotation=0&showTitle=false&size=113663&status=done&style=none&taskId=ua24f5b2b-aa64-4e41-a025-5eb61c581c5&title=&width=907.2)
 可以使用 v-bind 属性绑定的形式，为组件动态绑定 props 的值  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662363523755-46855a5b-6dc2-4817-9d91-b949e19442d5.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=178&id=uf1a90a08&margin=%5Bobject%20Object%5D&name=image.png&originHeight=222&originWidth=1126&originalType=binary&ratio=1&rotation=0&showTitle=false&size=91314&status=done&style=none&taskId=u1f9a3225-f584-4405-b03a-05d1644b391&title=&width=900.8)

在实际开发中经常会遇到动态操作元素样式的需求。因此，vue 允许开发者通过 v-bind 属性绑定指令，为元 素动态绑定 class 属性的值和行内的 style 样式  
可以通过三元表达式，动态的为元素绑定 class 的类名。   
如果元素需要动态绑定多个 class 的类名，此时可以使用数组的语法格式  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662363786947-11e43c9b-fd76-4d21-8a14-ebd818193e92.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=457&id=ud07cde80&margin=%5Bobject%20Object%5D&name=image.png&originHeight=571&originWidth=1041&originalType=binary&ratio=1&rotation=0&showTitle=false&size=117430&status=done&style=none&taskId=u3e109a96-6525-4d40-b852-ffb3c919eac&title=&width=832.8) 使用数组语法动态绑定 class 会导致模板结构臃肿的问题。此时可以使用对象语法进行简化  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662363840794-6360040d-7828-4874-b1d3-6f5389604fab.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=469&id=u1da13e74&margin=%5Bobject%20Object%5D&name=image.png&originHeight=586&originWidth=1125&originalType=binary&ratio=1&rotation=0&showTitle=false&size=139289&status=done&style=none&taskId=u2457a743-1581-4343-bde9-f9b3a25dcd2&title=&width=900) 以对象语法绑定内联的 style  
 :style 的对象语法十分直观——看着非常像 CSS，但其实是一个 JavaScript 对象。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662367018642-4fc10d49-f080-4952-91e2-f97adaa4d084.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=432&id=u104e73a0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=540&originWidth=993&originalType=binary&ratio=1&rotation=0&showTitle=false&size=98759&status=done&style=none&taskId=ud588f021-8aad-461a-901c-9b3bd0a2c20&title=&width=794.4)

### props验证
 使用对象类型的 props 节点，可以对每个 prop 进行数据类型的校验
 可以直接为组件的 prop 属性指定基础的校验类型，从而防止组件的使用者为其绑定错误类型的数据  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662367236405-0524baa3-f181-4799-923a-08e7102b75cc.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=531&id=u703b3969&margin=%5Bobject%20Object%5D&name=image.png&originHeight=664&originWidth=1278&originalType=binary&ratio=1&rotation=0&showTitle=false&size=170989&status=done&style=none&taskId=u4f63d7d7-7da9-4e09-aa22-fa55bdbcd00&title=&width=1022.4) 如果某个 prop 属性值的类型不唯一，此时可以通过数组的形式，为其指定多个可能的类型  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662367248656-e1852f7f-6758-48b0-a2f7-1b7e20a914ce.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=270&id=udcaa5de2&margin=%5Bobject%20Object%5D&name=image.png&originHeight=338&originWidth=1133&originalType=binary&ratio=1&rotation=0&showTitle=false&size=78511&status=done&style=none&taskId=ub486d2e1-d386-4b2a-8bae-780e5ef75eb&title=&width=906.4) 如果组件的某个 prop 属性是必填项，必须让组件的使用者为其传递属性的值。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662367298810-45be8781-a540-40ca-a3cf-4e1cddda1156.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=365&id=u348c8726&margin=%5Bobject%20Object%5D&name=image.png&originHeight=456&originWidth=1124&originalType=binary&ratio=1&rotation=0&showTitle=false&size=120075&status=done&style=none&taskId=u0d095522-3290-48fe-a3a3-55be05dd32b&title=&width=899.2) 在封装组件时，可以为某个 prop 属性指定默认值。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662367334721-97bb1876-0106-4063-a29b-d5234dcb11b2.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=374&id=u9563e093&margin=%5Bobject%20Object%5D&name=image.png&originHeight=468&originWidth=1128&originalType=binary&ratio=1&rotation=0&showTitle=false&size=110265&status=done&style=none&taskId=u9cdb9d8f-aa92-40f9-99f4-c3176f24b0b&title=&width=902.4) 在封装组件时，可以为 prop 属性指定自定义的验证函数，从而对 prop 属性的值进行更加精确的控制  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662367346840-85d70120-6ab4-4c5d-962e-d6a3bc3d0430.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=485&id=u96d2b9dd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=606&originWidth=1102&originalType=binary&ratio=1&rotation=0&showTitle=false&size=160243&status=done&style=none&taskId=ud908b225-af6f-4f5e-a3ac-9f9056ac520&title=&width=881.6)

### 计算属性
 计算属性需要以 function 函数的形式声明到组件的 computed 选项中
 计算属性侧重于得到一个计算的结果，因此计算属性中必须有 return 返回值    
 计算属性必须当做普通属性使用  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662367427957-ce2c2763-f559-4a19-ac9c-c88bee2d2884.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=416&id=u103cb910&margin=%5Bobject%20Object%5D&name=image.png&originHeight=520&originWidth=1142&originalType=binary&ratio=1&rotation=0&showTitle=false&size=111058&status=done&style=none&taskId=u7bf489ac-1c24-48fa-a5a9-5fcc46bdb8b&title=&width=913.6) 相对于方法来说，计算属性会缓存计算的结果，只有计算属性的依赖项发生变化时，才会重新进行运算。因此 计算属性的性能更好  

### 自定义事件
 在封装组件时，为了让组件的使用者可以监听到组件内状态的变化，此时需要用到组件的自定义事件  

 在封装组件时： 
① 声明自定义事件 ② 触发自定义事件 
在使用组件时： 
③ 监听自定义事件  

 开发者为自定义组件封装的自定义事件，必须事先在 emits 节点中声明  
 在 emits 节点下声明的自定义事件，可以通过 this.$emit('自定义事件的名称') 方法进行触发  
 在调用 this.$emit() 方法触发自定义事件时，可以通过第 2 个参数为自定义事件传参 ,参数传递至监听事件后发生的函数
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662367786991-c7fde01d-bd05-4189-b7ca-fa13365224fe.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=452&id=b77rP&margin=%5Bobject%20Object%5D&name=image.png&originHeight=565&originWidth=921&originalType=binary&ratio=1&rotation=0&showTitle=false&size=101157&status=done&style=none&taskId=u2480ff2b-a8f7-49e4-8a88-64ebfc107f4&title=&width=736.8)
 在使用自定义的组件时，可以通过 v-on 的形式监听自定义事件  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662367757619-17e06e5d-b5df-4b74-828d-b4984e342d03.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=318&id=uc8535603&margin=%5Bobject%20Object%5D&name=image.png&originHeight=397&originWidth=1133&originalType=binary&ratio=1&rotation=0&showTitle=false&size=85982&status=done&style=none&taskId=u567d13ae-bda2-4f3d-b70a-c5c2ceacdd8&title=&width=906.4)

### v-model
 父组件在使用子组件期间，可以使用 v-model 指令维护组件内外数据的双向同步  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662368890528-98d243a5-4643-4002-a978-75e125264c63.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=411&id=u2169ff78&margin=%5Bobject%20Object%5D&name=image.png&originHeight=514&originWidth=961&originalType=binary&ratio=1&rotation=0&showTitle=false&size=363690&status=done&style=none&taskId=u7dafd6e6-0b56-4130-ac28-5b59748f4ae&title=&width=768.8)
### watch监听器
 开发者需要在 watch 节点下，定义自己的侦听器。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662368293923-f2c1ff87-e0a9-4154-ab19-21be474a8a11.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=463&id=u3bf5a542&margin=%5Bobject%20Object%5D&name=image.png&originHeight=579&originWidth=1121&originalType=binary&ratio=1&rotation=0&showTitle=false&size=118264&status=done&style=none&taskId=ub94201b6-56e9-4d1f-af19-17a42fb1c68&title=&width=896.8) 默认情况下，组件在初次加载完毕后不会调用 watch 侦听器。如果想让 watch 侦听器立即被调用，则需要使 用 immediate 选项。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662368424847-11398309-9da0-46c4-897a-232514119f03.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=445&id=u8af7dc53&margin=%5Bobject%20Object%5D&name=image.png&originHeight=556&originWidth=1096&originalType=binary&ratio=1&rotation=0&showTitle=false&size=130639&status=done&style=none&taskId=u12a6b528-02a5-4244-8b9a-25ae685ea98&title=&width=876.8) 当 watch 侦听的是一个对象，如果对象中的属性值发生了变化，则无法被监听到。此时需要使用 deep 选项  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662368479641-7f499b32-e1fd-4bee-8005-1b92dd3d5a0a.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=443&id=u97a359d3&margin=%5Bobject%20Object%5D&name=image.png&originHeight=554&originWidth=1038&originalType=binary&ratio=1&rotation=0&showTitle=false&size=119092&status=done&style=none&taskId=u6df27368-4cbe-4f5c-bce7-2b9b5ff3dec&title=&width=830.4) 如果只想监听对象中单个属性的变化，则可以按照如下的方式定义 watch 侦听器  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662368502132-343f6933-778c-4a52-83e3-4eeb8b9a4e89.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=479&id=uf42a0a18&margin=%5Bobject%20Object%5D&name=image.png&originHeight=599&originWidth=1100&originalType=binary&ratio=1&rotation=0&showTitle=false&size=128853&status=done&style=none&taskId=u30527e16-3c5a-498b-bd88-ab4efa752bc&title=&width=880)

### 组件的生命周期
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662368574194-760a26a8-f1f0-4b8b-a9e4-7d2157bb8126.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=282&id=u6879078e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=353&originWidth=1022&originalType=binary&ratio=1&rotation=0&showTitle=false&size=281344&status=done&style=none&taskId=u7bd462e4-5c5c-4a8b-82c0-ca3619b711b&title=&width=817.6) vue 框架为组件内置了不同时刻的生命周期函数，生命周期函数会伴随着组件的运行而自动调用。例如： 
① 当组件在内存中被创建完毕之后，会自动调用 created 函数 
② 当组件被成功的渲染到页面上之后，会自动调用 mounted 函数 
③ 当组件被销毁完毕之后，会自动调用 unmounted 函数  

当组件的 data 数据更新之后，vue 会自动重新渲染组件的 DOM 结构，从而保证 View 视图展示的数据和 Model 数据源保持一致。 
当组件被重新渲染完毕之后，会自动调用 updated 生命周期函数。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662368643919-51bff5ae-4280-494c-8324-9c9e70c9f134.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=321&id=uceeb849f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=401&originWidth=1400&originalType=binary&ratio=1&rotation=0&showTitle=false&size=200495&status=done&style=none&taskId=u171af9bc-b52e-4038-abe1-1d7fa0b0d4e&title=&width=1120)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662368656358-128e2fee-4e24-4651-a4f5-595c46d750a4.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=480&id=u5ad13f8f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=600&originWidth=1382&originalType=binary&ratio=1&rotation=0&showTitle=false&size=263451&status=done&style=none&taskId=uf260b7d6-98c0-4c6f-93ed-630492feda8&title=&width=1105.6)

### 组件之间的数据共享
 在项目开发中，组件之间的关系分为如下 3 种： 
① 父子关系 ② 兄弟关系 ③ 后代关系  

 父组件通过 v-bind 属性绑定向子组件共享数据。同时，子组件需要使用 props 接收数据。  
 子组件通过自定义事件的方式向父组件共享数据。  

 父组件在使用子组件期间，可以使用 v-model 指令维护组件内外数据的双向同步  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662368828479-d3a2899c-92e3-473d-a725-989e4280c810.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=423&id=udf85b293&margin=%5Bobject%20Object%5D&name=image.png&originHeight=529&originWidth=995&originalType=binary&ratio=1&rotation=0&showTitle=false&size=369304&status=done&style=none&taskId=u551de853-b48d-4b20-bbe0-275b7ad0176&title=&width=796)

 兄弟组件之间实现数据共享的方案是 EventBus。可以借助于第三方的包 mitt 来创建 eventBus 对象，从而实 现兄弟组件之间的数据共享  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662369022167-1f6254f0-0cc9-49d0-9e31-add9d6d62399.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=114&id=u1677792c&margin=%5Bobject%20Object%5D&name=image.png&originHeight=142&originWidth=1129&originalType=binary&ratio=1&rotation=0&showTitle=false&size=28796&status=done&style=none&taskId=u9013f5a5-be50-491f-b2a3-2d0869860a1&title=&width=903.2) 在项目中创建公共的 eventBus 模块  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662369037116-cc7fc422-9234-4602-ba9a-f564db8e81a7.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=371&id=u263b29b1&margin=%5Bobject%20Object%5D&name=image.png&originHeight=464&originWidth=1213&originalType=binary&ratio=1&rotation=0&showTitle=false&size=95101&status=done&style=none&taskId=u4da13aa7-de43-4b76-a578-5b180bc7cbe&title=&width=970.4) 在数据接收方，调用 bus.on('事件名称', 事件处理函数) 方法注册一个自定义事件  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662369064873-1a7443b7-ebca-49ee-9c1c-512c830afbfb.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=472&id=u5a25bce8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=590&originWidth=1207&originalType=binary&ratio=1&rotation=0&showTitle=false&size=130672&status=done&style=none&taskId=u3d634ddf-ad16-4e2b-b71f-06d12d0418b&title=&width=965.6) 在数据发送方，调用 bus.emit('事件名称', 要发送的数据) 方法触发自定义事件。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662369085592-15bae13d-5013-4463-9ae0-a47d153b87bb.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=466&id=u1ca8d326&margin=%5Bobject%20Object%5D&name=image.png&originHeight=583&originWidth=1199&originalType=binary&ratio=1&rotation=0&showTitle=false&size=126787&status=done&style=none&taskId=u2bc1af03-d465-4c07-9bcc-65bcb410ff6&title=&width=959.2)

 后代关系组件之间共享数据，指的是父节点的组件向其子孙组件共享数据。此时组件之间的嵌套关系比较复杂， 可以使用 provide 和 inject 实现后代关系组件之间的数据共享。  
 父节点的组件可以通过 provide 方法，对其子孙组件共享数据  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662370304373-099e1714-a397-4bb8-914e-bce8304ef7dc.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=462&id=u0913a120&margin=%5Bobject%20Object%5D&name=image.png&originHeight=577&originWidth=1128&originalType=binary&ratio=1&rotation=0&showTitle=false&size=113054&status=done&style=none&taskId=ub4c7bcc3-7921-43cc-b1ca-5f4b60b5d7e&title=&width=902.4) 子孙节点可以使用 inject 数组，接收父级节点向下共享的数据。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662370312508-614a89ed-e461-4304-be28-8c8922e2cf07.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=406&id=u7a6ede5c&margin=%5Bobject%20Object%5D&name=image.png&originHeight=508&originWidth=1127&originalType=binary&ratio=1&rotation=0&showTitle=false&size=106968&status=done&style=none&taskId=u351b2542-0bdc-43aa-867f-eec93b0ad21&title=&width=901.6) 父节点使用 provide 向下共享数据时，可以结合 computed 函数向下共享响应式的数据。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662370340285-436d24f5-6aea-4d8f-89c5-b1d5d458241f.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=484&id=u66a52be8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=605&originWidth=1097&originalType=binary&ratio=1&rotation=0&showTitle=false&size=120458&status=done&style=none&taskId=u1e3ec927-d33e-4c01-922a-1578bf54253&title=&width=877.6) 如果父级节点共享的是响应式的数据，则子孙节点必须以 .value 的形式进行使用  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662370351072-250991f3-3d75-4eae-bc24-1d8e17be141d.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=436&id=u1645763a&margin=%5Bobject%20Object%5D&name=image.png&originHeight=545&originWidth=1129&originalType=binary&ratio=1&rotation=0&showTitle=false&size=118740&status=done&style=none&taskId=u1c4ef290-9936-4ad1-8827-9f292bb5289&title=&width=903.2)

### 全局配置axios
 在实际项目开发中，几乎每个组件中都会用到 axios 发起数据请求。此时会遇到如下两个问题：
 ① 每个组件中都需要导入 axios（代码臃肿）
 ② 每次发请求都需要填写完整的请求路径（不利于后期的维护）  

 在 main.js 入口文件中，通过 app.config.globalProperties 全局挂载 axios  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662370439860-21aa7476-6d19-4ec6-b8e4-f48ec1016a23.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=386&id=ubf17b54d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=483&originWidth=1147&originalType=binary&ratio=1&rotation=0&showTitle=false&size=489723&status=done&style=none&taskId=u0856a657-6282-4e97-aac2-31c63eb0438&title=&width=917.6)

### ref引用
ref 用来辅助开发者在不依赖于 jQuery 的情况下，获取 DOM 元素或组件的引用。 
每个 vue 的组件实例上，都包含一个 $refs 对象，里面存储着对应的 DOM 元素或组件的引用。
默认情况下， 组件的 $refs 指向一个空对象。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662382308847-0e98c37b-1fbd-4660-84d2-fd580c621c3d.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=472&id=ua1a1a510&margin=%5Bobject%20Object%5D&name=image.png&originHeight=590&originWidth=1130&originalType=binary&ratio=1&rotation=0&showTitle=false&size=150784&status=done&style=none&taskId=ub5a92b8b-c8bb-4df6-8caa-ef680938bf3&title=&width=904)
 如果想要使用 ref 引用页面上的组件实例，则可以按照如下的方式进行操作  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662382505851-2eff6ff9-0ab8-42df-8dbd-996b4d46e8b9.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=466&id=uf7109a18&margin=%5Bobject%20Object%5D&name=image.png&originHeight=583&originWidth=1129&originalType=binary&ratio=1&rotation=0&showTitle=false&size=161240&status=done&style=none&taskId=u7cee1997-75d4-4b09-85cb-c6e1eb7315c&title=&width=903.2)
 组件的 $nextTick(cb) 方法，会把 cb 回调推迟到下一个 DOM 更新周期之后执行。通俗的理解是：等组件的 DOM 异步地重新渲染完成后，再执行 cb 回调函数。从而能保证 cb 回调函数可以操作到最新的 DOM 元素。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662382752132-4a66bf3d-9d5d-4a4f-935f-1bf28fa383f3.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=444&id=u950aa550&margin=%5Bobject%20Object%5D&name=image.png&originHeight=555&originWidth=1066&originalType=binary&ratio=1&rotation=0&showTitle=false&size=123891&status=done&style=none&taskId=u17aee110-9eca-46e0-89d5-fbcc6e0dec1&title=&width=852.8)

### 动态组件
 动态组件指的是动态切换组件的显示与隐藏。vue 提供了一个内置的组件，专门用来实现组件的动态渲染 
①  是组件的占位符 
② 通过 is 属性动态指定要渲染的组件名称 
③ <component is = "要渲染的组件的名称"></component>
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383002518-d845c9f7-9ade-4f6a-8e1d-de4a76d19968.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=486&id=u07e273a5&margin=%5Bobject%20Object%5D&name=image.png&originHeight=607&originWidth=1093&originalType=binary&ratio=1&rotation=0&showTitle=false&size=148791&status=done&style=none&taskId=u18b377e2-7b79-4c1f-85c0-cac0538455d&title=&width=874.4)
 默认情况下，切换动态组件时无法保持组件的状态。此时可以使用 vue 内置的  组件保持动态组 件的状态。 
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383050334-5c3eff4c-682c-4e2c-88c5-09eec99f8570.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=181&id=u7fdfcfef&margin=%5Bobject%20Object%5D&name=image.png&originHeight=226&originWidth=1133&originalType=binary&ratio=1&rotation=0&showTitle=false&size=56229&status=done&style=none&taskId=ueb97da57-1462-44ab-b596-c590e2882f4&title=&width=906.4)

### 插槽
插槽（Slot）是 vue 为组件的封装者提供的能力。允许开发者在封装组件时，把不确定的、希望由用户指定的部分定义为插槽。  
 可以把插槽认为是组件封装期间，为用户预留的内容的占位符  

 在封装组件时，可以通过元素定义插槽，从而为用户预留内容占位符 
 如果在封装组件时没有预留任何  插槽，则用户提供的任何自定义内容都会被丢弃。 ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383376851-f6f92c41-00d2-4429-bc9b-0f5588ed25d6.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=492&id=ucb98a23f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=615&originWidth=1125&originalType=binary&ratio=1&rotation=0&showTitle=false&size=141913&status=done&style=none&taskId=ue1b3604b-71f9-467c-a9ba-d60abfc5cf8&title=&width=900)封装组件时，可以为预留的  插槽提供后备内容（默认内容）。如果组件的使用者没有为插槽提供任何 内容，则后备内容会生效。 ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383425628-9ff7b246-7a71-4624-8836-c441b9c1c0a3.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=242&id=u7d314129&margin=%5Bobject%20Object%5D&name=image.png&originHeight=302&originWidth=1128&originalType=binary&ratio=1&rotation=0&showTitle=false&size=84942&status=done&style=none&taskId=u656d7f8d-4355-4528-9994-5762fc7b630&title=&width=902.4)如果在封装组件时需要预留多个插槽节点，则需要为每个  插槽指定具体的 name 名称。这种带有具体 名称的插槽叫做“具名插槽”。 
 没有指定 name 名称的插槽， 会有隐含的名称叫做 “default”  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383460503-408522e0-ba76-4359-a821-1ef4ba1aa02d.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=446&id=u70e6c9f8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=558&originWidth=940&originalType=binary&ratio=1&rotation=0&showTitle=false&size=100768&status=done&style=none&taskId=ue5d68a96-a0a3-4c9a-a40a-841dbcb1f76&title=&width=752)在向具名插槽提供内容的时候，我们可以在一个  元素上使用 v-slot 指令,并以 v-slot 的参数的 形式提供其名称。
跟 v-on 和 v-bind 一样，v-slot 也有缩写，即把参数之前的所有内容 (v-slot:) 替换为字符 #。例如 v-slot:header 可以被重写为 #header  
 ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383606684-9ceda650-e958-46d2-8cb4-d865ee1c074b.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=443&id=u5efebdc7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=554&originWidth=888&originalType=binary&ratio=1&rotation=0&showTitle=false&size=103228&status=done&style=none&taskId=uab174fa8-0a1d-407d-9cdc-e4e443c0361&title=&width=710.4)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383647771-af0b98a9-9f26-4556-a8e1-3e2757e1d8dc.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=446&id=u563b2f45&margin=%5Bobject%20Object%5D&name=image.png&originHeight=557&originWidth=891&originalType=binary&ratio=1&rotation=0&showTitle=false&size=100657&status=done&style=none&taskId=u20a103ea-f469-4fde-9a55-08e3652c6e8&title=&width=712.8)

 在封装组件的过程中，可以为预留的  插槽绑定 props 数据，这种带有 props 数据的  叫做“作用 域插槽”。 ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383671070-41c04df5-1e77-4cc8-83e8-b259aa35d27f.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=442&id=u6d9c6910&margin=%5Bobject%20Object%5D&name=image.png&originHeight=552&originWidth=1202&originalType=binary&ratio=1&rotation=0&showTitle=false&size=97008&status=done&style=none&taskId=u8c69cdc6-1cda-44a1-85ef-e09cd70579f&title=&width=961.6)

 作用域插槽对外提供的数据对象，可以使用解构赋值简化数据的接收过程。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383709558-07283e81-9474-4e8e-b5ef-a00cc5799ce0.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=406&id=ud4f5a92e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=507&originWidth=1211&originalType=binary&ratio=1&rotation=0&showTitle=false&size=137308&status=done&style=none&taskId=u220ed54b-6038-4950-b474-10ca17e086b&title=&width=968.8)
 可以通过作用域插槽把表格每一行的数据传递给组件的使用者  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383941771-47813e06-efe5-4a3b-b08c-cc352b0dfcad.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=341&id=ufac641b5&margin=%5Bobject%20Object%5D&name=image.png&originHeight=426&originWidth=1208&originalType=binary&ratio=1&rotation=0&showTitle=false&size=94770&status=done&style=none&taskId=ub5538295-f3d6-464f-89cf-aab403f556b&title=&width=966.4)
 自定义单元格的渲染方式，并接收作用域插槽对外提供的数据  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662383983893-453c56e9-385b-4f5f-972e-12b48d11257a.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=401&id=uad7e66cf&margin=%5Bobject%20Object%5D&name=image.png&originHeight=501&originWidth=1201&originalType=binary&ratio=1&rotation=0&showTitle=false&size=132989&status=done&style=none&taskId=u9b9da046-b074-4e45-9c86-4aa5ac64973&title=&width=960.8)

### 自定义指令
 在每个 vue 组件中，可以在 directives 节点下声明私有自定义指令。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662384026109-1eb5e7b4-25d3-4b80-80b9-2845f8b0e325.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=369&id=uff9ecf36&margin=%5Bobject%20Object%5D&name=image.png&originHeight=461&originWidth=1130&originalType=binary&ratio=1&rotation=0&showTitle=false&size=100900&status=done&style=none&taskId=u3792beef-7130-492a-ad78-34c4b2f33e0&title=&width=904)
 在使用自定义指令时，需要加上 v- 前缀。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662384155025-33e586da-7436-4d7c-800d-cbad6338af64.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=180&id=u0249429d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=225&originWidth=1126&originalType=binary&ratio=1&rotation=0&showTitle=false&size=70309&status=done&style=none&taskId=ubc588fe2-a379-44c6-8926-8b77a1049c9&title=&width=900.8) 全局共享的自定义指令需要通过“单页面应用程序的实例对象”进行声明  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662384206620-8dda2dd2-584f-43e0-9b93-c3e22e466000.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=402&id=u1f4e010b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=503&originWidth=1120&originalType=binary&ratio=1&rotation=0&showTitle=false&size=110775&status=done&style=none&taskId=u12c57224-2eea-49c0-b3a4-bf32451c412&title=&width=896) mounted 函数只在元素第一次插入 DOM 时被调用，当 DOM 更新时 mounted 函数不会被触发。 updated 函数会在每次 DOM 更新完成后被调用。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662384221673-8887f9f6-dc95-4d17-b299-eefce494a60e.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=340&id=u2fd4da62&margin=%5Bobject%20Object%5D&name=image.png&originHeight=425&originWidth=1128&originalType=binary&ratio=1&rotation=0&showTitle=false&size=91222&status=done&style=none&taskId=uafc48fc8-f643-43a8-ab55-31f267e9be3&title=&width=902.4) 如果 mounted 和updated 函数中的逻辑完全相同，则可以简写成如下格式  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662384235790-41631aeb-51ce-4f54-aaea-1a48181aac2b.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=214&id=u78787971&margin=%5Bobject%20Object%5D&name=image.png&originHeight=267&originWidth=1130&originalType=binary&ratio=1&rotation=0&showTitle=false&size=72756&status=done&style=none&taskId=u99a7ac40-e8cf-4921-8f4d-b3d05b3f8b9&title=&width=904)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662384314443-fcf5cf32-b140-4393-a7e0-11f0449c9633.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=435&id=udaa2e192&margin=%5Bobject%20Object%5D&name=image.png&originHeight=544&originWidth=1134&originalType=binary&ratio=1&rotation=0&showTitle=false&size=149956&status=done&style=none&taskId=u21da7ce2-dc6e-4f2c-ae0f-1d88e8c03e2&title=&width=907.2)

## 路由
### 概述
 后端路由指的是：请求方式、请求地址与 function 处理函数之间的对应关系。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662390786334-5f24b187-86ec-4b26-95a7-ce215da954a8.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=279&id=u3857d454&margin=%5Bobject%20Object%5D&name=image.png&originHeight=349&originWidth=1025&originalType=binary&ratio=1&rotation=0&showTitle=false&size=89902&status=done&style=none&taskId=uf5cb49ef-c81b-4bfd-aa33-489c8c56766&title=&width=820)

 SPA 指的是一个 web 网站只有唯一的一个 HTML 页面，所有组件的展示与切换都在这唯一的一个页面内完成。 此时，不同组件之间的切换需要通过前端路由来实现  
 在 SPA 项目中，不同功能之间的切换，要依赖于前端路由来完成
 前端路由通俗易懂的概念：Hash 地址与组件之间的对应关系  

① 用户点击了页面上的路由链接 
② 导致了 URL 地址栏中的 Hash 值发生了变化 
③ 前端路由监听了到 Hash 地址的变化 
④ 前端路由把当前 Hash 地址对应的组件渲染都浏览器中  

### vue-router
 vue-router 是 vue.js 官方给出的路由解决方案。它只能结合 vue 项目进行使用，能够轻松的管理 SPA 项目 中组件的切换。  

① 在项目中安装 vue-router 
② 定义路由组件 
③ 声明路由链接和占位符 
④ 创建路由模块 
⑤ 导入并挂载路由模块  

#### ① 在项目中安装 vue-router 
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662391345253-2614f0d8-30ec-4f62-81e3-06eb85d427f7.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=106&id=ub4698041&margin=%5Bobject%20Object%5D&name=image.png&originHeight=133&originWidth=1045&originalType=binary&ratio=1&rotation=0&showTitle=false&size=27973&status=done&style=none&taskId=u40a46e3c-7fd9-4e43-a0ca-49864ddbad6&title=&width=836)
#### ② 定义路由组件
 在项目中定义 MyHome.vue、MyMovie.vue、MyAbout.vue 三个组件，将来要使用 vue-router 来控制它们 的展示与切换  
#### ③ 声明路由链接和占位符 
 可以使用  标签来声明路由链接，并使用  标签来声明路由占位符。 ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662391393464-dc00ae9b-173e-4cad-99c9-e914f612c9da.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=371&id=u3462bc0f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=464&originWidth=1031&originalType=binary&ratio=1&rotation=0&showTitle=false&size=105109&status=done&style=none&taskId=ubf71299b-0923-4ae8-951a-03c50f30ed0&title=&width=824.8)
#### ④ 创建路由模块 
 在项目中创建 router.js 路由模块，在其中按照如下 4 个步骤创建并得到路由的实例对象： 
① 从 vue-router 中按需导入两个方法 ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662391607536-5e83fb29-7948-4ab2-9dd5-430240d11ac2.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=192&id=u27dc5bf8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=240&originWidth=1036&originalType=binary&ratio=1&rotation=0&showTitle=false&size=82538&status=done&style=none&taskId=u189865cb-ca23-4561-9817-f440dbea3e3&title=&width=828.8)
② 导入需要使用路由控制的组件![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662391614394-8cb7ff1b-50e5-4d77-a66a-ff446c6cc8f1.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=190&id=u6b1400ff&margin=%5Bobject%20Object%5D&name=image.png&originHeight=238&originWidth=1029&originalType=binary&ratio=1&rotation=0&showTitle=false&size=77396&status=done&style=none&taskId=u806686a3-faf7-4838-a32a-55a316491c3&title=&width=823.2)
③ 创建路由实例对象 ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662391621173-03c6945e-2a32-404c-b6e4-f796022d38af.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=429&id=ub742c8ee&margin=%5Bobject%20Object%5D&name=image.png&originHeight=536&originWidth=1030&originalType=binary&ratio=1&rotation=0&showTitle=false&size=132754&status=done&style=none&taskId=ucc4cbf49-2a9f-425b-b498-2bc8d893f24&title=&width=824)
④ 向外共享路由实例对象 ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662391633275-ddeda103-94ba-4072-9cc5-50607e2d166e.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=165&id=u430494e5&margin=%5Bobject%20Object%5D&name=image.png&originHeight=206&originWidth=1027&originalType=binary&ratio=1&rotation=0&showTitle=false&size=45824&status=done&style=none&taskId=uee874faf-365e-4a2e-983e-c1e35283e6a&title=&width=821.6)
#### ⑤ 导入并挂载路由模块  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662391644887-e50eb2ec-5a50-424d-aa2c-01cfeb68a924.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=458&id=ucdd9d8f9&margin=%5Bobject%20Object%5D&name=image.png&originHeight=572&originWidth=1024&originalType=binary&ratio=1&rotation=0&showTitle=false&size=112445&status=done&style=none&taskId=u530a696d-6c8a-449b-9ca9-b7c29d57da7&title=&width=819.2)

#### 路由重定向
 路由重定向指的是：用户在访问地址 A 的时候，强制用户跳转到地址 C ，从而展示特定的组件页面。 通过路由规则的 redirect 属性，指定一个新的路由地址，可以很方便地设置路由的重定向：  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662391842904-2d24f8a6-f821-4c82-99da-abda50530894.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=374&id=u32b3147d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=467&originWidth=1030&originalType=binary&ratio=1&rotation=0&showTitle=false&size=83413&status=done&style=none&taskId=u46448352-e66b-4c24-a78f-0b7a8519ecb&title=&width=824)

####  路由高亮  
 可以通过如下的两种方式，将激活的路由链接进行高亮显示： 
① 使用默认的高亮 class 类 
被激活的路由链接，默认自带一个叫做 router-link-active 的类名。开发者可以使用此类名选择器，为激活的路由链接设置高亮的样式：  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662391910772-283d8c2f-9403-4213-88a3-c07bd645498a.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=248&id=ud439049b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=310&originWidth=1030&originalType=binary&ratio=1&rotation=0&showTitle=false&size=60457&status=done&style=none&taskId=u535b656c-7242-4f75-b11f-eae56d44cdb&title=&width=824)
② 自定义路由高亮的 class 类  
 在创建路由的实例对象时，开发者可以基于 linkActiveClass 属性，自定义路由链接被激活时所应用的类名  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662391950722-c2c6a863-b6ae-457b-845b-1b578d076b7e.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=426&id=u94f0140f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=532&originWidth=1030&originalType=binary&ratio=1&rotation=0&showTitle=false&size=131294&status=done&style=none&taskId=u4ef1a71b-c6f4-47dd-9b27-fb04390ff61&title=&width=824)

#### 嵌套路由
 通过路由实现组件的嵌套展示，叫做嵌套路由。  
① 声明子路由链接和子路由占位符 
② 在父路由规则中，通过 children 属性嵌套声明子路由规则  

 声明子路由链接和子路由占位符  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662392083503-8e539434-cadd-4cdc-bde2-c0fed80a14e5.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=338&id=u013e5cbd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=422&originWidth=1033&originalType=binary&ratio=1&rotation=0&showTitle=false&size=109090&status=done&style=none&taskId=u6694715d-be13-45c0-9d1e-578e8c3b32c&title=&width=826.4) 通过 children 属性声明子路由规则   
 注意：子路由规则的 path 不要以 /  开头
在 router.js 路由模块中，导入需要的组件，并使用 children 属性声明子路由规则。示例代码如下  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662392161584-a3c7bee5-42b7-421e-9444-ac979ac53bfe.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=442&id=ufb06068d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=552&originWidth=872&originalType=binary&ratio=1&rotation=0&showTitle=false&size=121698&status=done&style=none&taskId=u42a80ac4-6c2d-4412-8f15-d3e870a1eff&title=&width=697.6)

#### 动态路由匹配
 动态路由指的是：把 Hash 地址中可变的部分定义为参数项，从而提高路由规则的复用性。在 vue-router 中 使用英文的冒号（:）来定义路由的参数项。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662392297103-be32f8f1-623d-4f6a-8b9c-1497df4b4c34.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=281&id=uef150b6b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=351&originWidth=1039&originalType=binary&ratio=1&rotation=0&showTitle=false&size=102814&status=done&style=none&taskId=u8a237f99-c062-4e43-9f88-18c84b3ddd1&title=&width=831.2)

####  $route.params 参数对象  
 通过动态路由匹配的方式渲染出来的组件中，可以使用 $route.params 对象访问到动态匹配的参数值  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662392826071-bb18056f-1dac-4512-87fe-c7f4266988b1.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=363&id=ud5c84bcb&margin=%5Bobject%20Object%5D&name=image.png&originHeight=454&originWidth=1027&originalType=binary&ratio=1&rotation=0&showTitle=false&size=76317&status=done&style=none&taskId=u782e4910-408c-42c3-9057-8e8c3d2793a&title=&width=821.6)

####  使用 props 接收路由参数  
 为了简化路由参数的获取形式，vue-router 允许在路由规则中开启 props 传参。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662392876898-47b99d6d-321e-4fdb-b4a8-29f3f7e34446.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=443&id=uf81117de&margin=%5Bobject%20Object%5D&name=image.png&originHeight=554&originWidth=936&originalType=binary&ratio=1&rotation=0&showTitle=false&size=127939&status=done&style=none&taskId=u6af81567-d356-4e49-aac4-59857662177&title=&width=748.8)

#### 编程式导航
 通过调用 API 实现导航的方式，叫做编程式导航。与之对应的，通过点击链接实现导航的方式，叫做声明式导 航。
普通网页中点击  链接、vue 项目中点击  都属于声明式导航 
普通网页中调用 location.href 跳转到新页面的方式，属于编程式导航 

 vue-router 提供了许多编程式导航的 API，其中最常用的两个 API 分别是： 
① this.$router.push('hash 地址')  跳转到指定 Hash 地址，从而展示对应的组件 
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662394181732-55ad09c2-30fb-4955-9ead-5dbe937d33ce.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=442&id=uc04df0c0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=552&originWidth=935&originalType=binary&ratio=1&rotation=0&showTitle=false&size=96329&status=done&style=none&taskId=ud520f90f-0151-4cf7-993a-0a2732ea454&title=&width=748)
② this.$router.go(数值 n)  实现导航历史的前进、后退  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662394181756-81ca48ed-7a9d-4f62-aee8-0fd2b70d9986.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=445&id=u0a20ebd0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=556&originWidth=1003&originalType=binary&ratio=1&rotation=0&showTitle=false&size=97468&status=done&style=none&taskId=u39e856eb-79e8-42ce-bc81-e3dc1c870fd&title=&width=802.4)

####  命名路由  
 通过 name 属性为路由规则定义名称的方式，叫做命名路由。  
 命名路由的 name 值不能重复，必须保证唯一性  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662394204028-20feaa35-73cc-4ce6-9226-c56f654ca6e6.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=283&id=u4a08a6e0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=354&originWidth=1026&originalType=binary&ratio=1&rotation=0&showTitle=false&size=71444&status=done&style=none&taskId=u59bf075d-65b2-4097-8209-663fd92e148&title=&width=820.8) 为  标签动态绑定 to 属性的值，并通过 name 属性指定要跳转到的路由规则。期间还可以用 params 属性指定跳转期间要携带的路由参数。 ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662394237671-53955875-88a3-4a93-8794-713cd42fd4b1.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=370&id=u62909eaf&margin=%5Bobject%20Object%5D&name=image.png&originHeight=462&originWidth=1028&originalType=binary&ratio=1&rotation=0&showTitle=false&size=68254&status=done&style=none&taskId=udc0bca75-a5b1-4e8a-9cf3-da091f2efa7&title=&width=822.4) 使用命名路由实现编程式导航  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662394256974-8a4d5114-b86f-4832-88bd-878dd96050a5.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=438&id=ubb372a61&margin=%5Bobject%20Object%5D&name=image.png&originHeight=547&originWidth=937&originalType=binary&ratio=1&rotation=0&showTitle=false&size=92691&status=done&style=none&taskId=ua95a0b17-027a-431e-b595-35d91d7c9aa&title=&width=749.6)

####  导航守卫  
 导航守卫可以控制路由的访问权限  
 全局导航守卫会拦截每个路由规则，从而对每个路由进行访问权限的控制。  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662394306104-8abdf579-842c-40f3-8b32-b8ff4afd5fd2.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=282&id=u28c16a26&margin=%5Bobject%20Object%5D&name=image.png&originHeight=353&originWidth=1035&originalType=binary&ratio=1&rotation=0&showTitle=false&size=95726&status=done&style=none&taskId=ud03246c3-3792-4268-8a7a-6d1fad7f39d&title=&width=828) 全局导航守卫的守卫方法中接收 3 个形参，格式为  ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1662394335214-bcb0a743-8ee8-4237-aae4-35176f4de014.png#clientId=ua1513f91-86a3-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=316&id=uef16a3ac&margin=%5Bobject%20Object%5D&name=image.png&originHeight=395&originWidth=943&originalType=binary&ratio=1&rotation=0&showTitle=false&size=87989&status=done&style=none&taskId=uca1f72b5-bee4-432b-b9d6-70c418419ae&title=&width=754.4) 注意： ① 在守卫方法中如果不声明 next 形参，则默认允许用户访问每一个路由！
 ② 在守卫方法中如果声明了 next 形参，则必须调用 next() 函数，否则不允许用户访问任何一个路由！  
 直接放行：next() 强制其停留在当前页面：next(false) 强制其跳转到登录页面：next('/login')  
