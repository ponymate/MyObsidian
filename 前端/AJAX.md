## 概述
AJAX的全称是Asynchronous Javascript And Xml（异步javascript和XML）
通俗的讲，就是网页中利用XMLHttpRequest对象和服务器进行数据交互的方式

## form表单和模板引擎
### form表单基本使用
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661567330680-84106e86-b3d4-4a36-813a-6f5a0fdc8655.png#averageHue=%23feffff&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=398&id=ue6c73563&margin=%5Bobject%20Object%5D&name=image.png&originHeight=497&originWidth=1133&originalType=binary&ratio=1&rotation=0&showTitle=false&size=44475&status=done&style=none&taskId=ub2d7398c-e48e-42be-afe8-e5a677844b3&title=&width=906.4)
注意：当提交表单后，页面会立即跳转到action属性指定的URL地址
#### target
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661567383244-57d55a28-a74c-4009-8cf2-5c7093556b36.png#averageHue=%23bdd8f5&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=262&id=u796d2ec0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=328&originWidth=1063&originalType=binary&ratio=1&rotation=0&showTitle=false&size=19837&status=done&style=none&taskId=u4d434c20-ce43-41fe-bc6f-3d7d81afa29&title=&width=850.4)
#### enctype
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661567416660-e2739927-dbe7-4654-a5bd-946c5a9ac3ca.png#averageHue=%23cfdced&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=294&id=u695e5a87&margin=%5Bobject%20Object%5D&name=image.png&originHeight=367&originWidth=1196&originalType=binary&ratio=1&rotation=0&showTitle=false&size=40086&status=done&style=none&taskId=uc9f3964c-70a1-4826-a9b8-1ba39310604&title=&width=956.8)
注意：在涉及到文件上传的操作时，必须将enctype的值设置为multipart/form-data
如果表单的提交不涉及到文件上传操作，直接将enctype的值设置为application/x-www-form-urlencoded
#### 表单同步提交
点击submit按钮,触发表单提交的动作,从而使页面跳转到action URL的行为,叫做表单的同步提交
缺点:

- 直接发生跳转,用户体验差
- 同步提交后,页面之前的状态和数据会消失

解决:表单只负责手机数据,Ajax负责将数据传递到服务器
### 模板引擎
可以根据指定的模板结构和数据,自动生成HTML界面
优点:

- 减少了字符串拼接操作
- 使代码结构更加清晰
- 使代码更易于阅读和维护
#### art-template
使用步骤:

- 导入art-template
- 定义数据
- 定义模板
- 调用template函数
- 渲染HTML结构
#### art-template标准语法
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661568019617-766e881f-0a10-4f06-871d-aef01ffe3052.png#averageHue=%23edf1f9&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=318&id=u163d72dd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=397&originWidth=1110&originalType=binary&ratio=1&rotation=0&showTitle=false&size=21729&status=done&style=none&taskId=u7805b12d-7743-4cf5-b4fc-ec4b8fbeb5f&title=&width=888)

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661568019628-e58331a5-3103-4510-b713-bc2d3999f1ab.png#averageHue=%23f7f6f6&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=235&id=u7f4198cf&margin=%5Bobject%20Object%5D&name=image.png&originHeight=294&originWidth=1053&originalType=binary&ratio=1&rotation=0&showTitle=false&size=17249&status=done&style=none&taskId=uef6fbf8b-47d9-40ec-8911-13566ce62f9&title=&width=842.4)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661568021813-f5312adf-3f89-4b3d-a90d-38fc2751c38c.png#averageHue=%23eff2f7&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=269&id=u0e16cf8b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=336&originWidth=1075&originalType=binary&ratio=1&rotation=0&showTitle=false&size=23284&status=done&style=none&taskId=ubbc83cb2-5a6a-4ddf-b2c8-2b40a922062&title=&width=860)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661568025424-57e367d8-2ced-400d-9833-b310d0cbb8c8.png#averageHue=%23eef2f8&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=282&id=uaef0d6b7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=353&originWidth=1044&originalType=binary&ratio=1&rotation=0&showTitle=false&size=24800&status=done&style=none&taskId=u8f6e83dc-3e3c-4aac-bea7-b8cda98c305&title=&width=835.2)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661568032421-141867f2-d7f1-4527-83b0-b3647014e1f0.png#averageHue=%23f2f4f9&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=317&id=u47f1c22e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=396&originWidth=1135&originalType=binary&ratio=1&rotation=0&showTitle=false&size=26470&status=done&style=none&taskId=u04a12fbc-7fc8-4d15-8c3a-44352811009&title=&width=908)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661568041684-eb7957e8-9224-4c07-848e-0a73d40147a3.png#averageHue=%23ebf0f9&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=437&id=u40af0d58&margin=%5Bobject%20Object%5D&name=image.png&originHeight=546&originWidth=1088&originalType=binary&ratio=1&rotation=0&showTitle=false&size=36262&status=done&style=none&taskId=u7c58207a-d172-44ea-918f-ebda5fdcd7b&title=&width=870.4)

## Ajax加强
### XMLHttpRequest
简称xhr是浏览器提供的javascript对象,通过他,可以请求服务器上的数据资源.
jQuery中的Ajax函数就是基于xhr封装出来的
#### get请求
步骤  :

- 创建xhr对象
- 调用xhr.open()函数
- 调用xhr.send()函数
- 监听xhr.onreadystatechange事件
```javascript
// 1. 创建 XHR 对象
var xhr = new XMLHttpRequest()
// 2. 调用 open 函数，指定 请求方式 与 URL地址
xhr.open('GET', 'http://www.liulongbin.top:3006/api/getbooks')
// 3. 调用 send 函数，发起 Ajax 请求
xhr.send()
// 4. 监听 onreadystatechange 事件
xhr.onreadystatechange = function() {
    // 4.1 监听 xhr 对象的请求状态 readyState ；与服务器响应的状态 status
    if (xhr.readyState === 4 && xhr.status === 200) {
        // 4.2 打印服务器响应回来的数据
        console.log(xhr.responseText)
    }
}
```
#### xhr对象的readyState属性
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661568663608-6f390319-43d7-4922-96ca-03ff932e90d3.png#averageHue=%23feffff&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=327&id=ue8073d48&margin=%5Bobject%20Object%5D&name=image.png&originHeight=409&originWidth=1078&originalType=binary&ratio=1&rotation=0&showTitle=false&size=44194&status=done&style=none&taskId=u03595b80-8e6e-425b-abb3-3430a686c0e&title=&width=862.4)
#### URL编码与解码
URL 地址中，只允许出现英文相关的字母、标点符号、数字，因此，在 URL 地址中不允许出现中文字符。
如果 URL 中需要包含中文这样的字符，则必须对中文字符进行**编码**（转义）。
**URL编码的原则**：使用安全的字符（没有特殊用途或者特殊意义的可打印字符）去表示那些不安全的字符。
URL编码原则的通俗理解：使用英文字符去表示非英文字符。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661568881599-f8fa90f1-0bb0-4f21-a77e-8353196ead9e.png#averageHue=%23020000&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=101&id=u47cedffc&margin=%5Bobject%20Object%5D&name=image.png&originHeight=126&originWidth=1133&originalType=binary&ratio=1&rotation=0&showTitle=false&size=9922&status=done&style=none&taskId=ub86f1590-2501-4c6a-9c41-d9a62e2e3f0&title=&width=906.4)

浏览器提供了URL编码和解码的API
encodeURI() 编码函数
decodeURI() 解码函数
**encodeURI**('黑马程序员')
// 输出字符串  %E9%BB%91%E9%A9%AC%E7%A8%8B%E5%BA%8F%E5%91%98
**decodeURI**('**%E9%BB%91%E9%A9%AC**')
// 输出字符串  **黑马**

由于浏览器会自动对 URL 地址进行编码操作，因此，大多数情况下，程序员不需要关心 URL 地址的编码与解码操作。

#### post请求
步骤

- 创建xhr对象
- 调用xhr.open()函数
- 设置Content-Type属性(固定写法)
- 调用xhr.send()函数,同时指定要发送的数据
- 监听xhr.onreadystatechange事件
```javascript
// 1. 创建 xhr 对象
var xhr = new XMLHttpRequest()
// 2. 调用 open()
xhr.open('POST', 'http://www.liulongbin.top:3006/api/addbook')
// 3. 设置 Content-Type 属性（固定写法）
xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded')
// 4. 调用 send()，同时将数据以查询字符串的形式，提交给服务器
xhr.send('bookname=水浒传&author=施耐庵&publisher=天津图书出版社')
// 5. 监听 onreadystatechange 事件
xhr.onreadystatechange = function() {
    if (xhr.readyState === 4 && xhr.status === 200) {
        console.log(xhr.responseText)
    }
}
```
### 数据交换格式
#### XML:
格式臃肿,效率低下,javascript中解析麻烦
#### json
javascript object notation

json中包含对象和数组两种结构

对象结构:
数据结构为{key: value,key:value,...}
key为英文双引号包裹的字符串,value可以为数字,字符串,布尔值,null,数组,对象

数组结构:
数据结构:[value,value,..]
数组中的类型可以是数字,字符串,布尔值,null,数组,对象

注意事项:

- 属性名必须使用双引号包裹
- 字符串类型使用双引号包裹
- json最外层必须是对象或者数组

#### json和js对象
json是js对象的字符串表示方法
json->js对象
使用Json.parse()方法
```javascript
var obj = JSON.parse('{"a":"hello","b":"world"}')
```
js对象->json
```javascript
var json = JSON.stringify({a: 'Hello', b: 'World'})
```
把数据对象转换为字符串的过程，叫做**序列化**，例如：调用 JSON.stringify() 函数的操作，叫做 JSON 序列化。
把字符串转换为数据对象的过程，叫做**反序列化**，例如：调用 JSON.parse() 函数的操作，叫做 JSON 反序列化

## XMLHttpRequest Level2的新特性
### 概述
#### 旧版XMLHttpRequest缺点
只支持文本数据的传输,无法读取和上传文件
传送和接收数据时,没有进度信息,只能提示有没有完成
#### 新功能
可以设置HTTP请求的时限
可以使用FormData对象管理表单数据
可以上传文件
可以获得数据传输的进度信息
### 设置HTTP**请求时限**
```javascript
xhr.timeout = 3000
 xhr.ontimeout = function(event){
     alert('请求超时！')
 }
//上面的语句，将最长等待时间设为 3000 毫秒。过了这个时限，就自动停止HTTP
//请求。与之配套的还有一个 timeout 事件，用来指定回调函数：
```
### **FormData对象管理表单数据**
模拟表单操作
```javascript
      // 1. 新建 FormData 对象
      var fd = new FormData()
      // 2. 为 FormData 添加表单项
      fd.append('uname', 'zs')
      fd.append('upwd', '123456')
      // 3. 创建 XHR 对象
      var xhr = new XMLHttpRequest()
      // 4. 指定请求类型与URL地址
      xhr.open('POST', 'http://www.liulongbin.top:3006/api/formdata')
      // 5. 直接提交 FormData 对象，这与提交网页表单的效果，完全一样
      xhr.send(fd)
```
获取网页表单的值
```javascript
 // 获取表单元素
 var form = document.querySelector('#form1')
 // 监听表单元素的 submit 事件
 form.addEventListener('submit', function(e) {
    e.preventDefault()
     // 根据 form 表单创建 FormData 对象，会自动将表单数据填充到 FormData 对象中
     var fd = new FormData(form)
     var xhr = new XMLHttpRequest()
     xhr.open('POST', 'http://www.liulongbin.top:3006/api/formdata')
     xhr.send(fd)
     xhr.onreadystatechange = function() {}
})
```
### 上传文件
实现步骤：
①定义 UI 结构
②验证是否选择了文件
③向 FormData中追加文件
④使用 xhr发起上传文件的请求
⑤监听 onreadystatechange事件
```javascript
 // 1. 获取上传文件的按钮
 var btnUpload = document.querySelector('#btnUpload')
 // 2. 为按钮添加 click 事件监听
 btnUpload.addEventListener('click', function() {
     // 3. 获取到选择的文件列表
     var files = document.querySelector('#file1').files
     if (files.length <= 0) {
         return alert('请选择要上传的文件！')
     }
     // ...后续业务逻辑
      // 1. 创建 FormData 对象
 var fd = new FormData()
 // 2. 向 FormData 中追加文件
 fd.append('avatar', files[0])
    var xhr = new XMLHttpRequest()
 // 2. 调用 open 函数，指定请求类型与URL地址。其中，请求类型必须为 POST
 xhr.open('POST', 'http://www.liulongbin.top:3006/api/upload/avatar')
 // 3. 发起请求
 xhr.send(fd)
xhr.onreadystatechange = function() {
  if (xhr.readyState === 4 && xhr.status === 200) {
    var data = JSON.parse(xhr.responseText)
    if (data.status === 200) { // 上传文件成功
      // 将服务器返回的图片地址，设置为 <img> 标签的 src 属性
      document.querySelector('#img').src = 'http://www.liulongbin.top:3006' + data.url
    } else { // 上传文件失败
      console.log(data.message)
    }
  }
}
 })

```
### 显示文件上传速度
新版本的 XMLHttpRequest 对象中，可以通过监听 xhr.upload.onprogress事件，来获取到文件的上传进度。
```javascript
// 创建 XHR 对象
 var xhr = new XMLHttpRequest()
 // 监听 xhr.upload 的 onprogress 事件
 xhr.upload.onprogress = function(e) {
    // e.lengthComputable 是一个布尔值，表示当前上传的资源是否具有可计算的长度
    if (e.lengthComputable) {
        // e.loaded 已传输的字节
        // e.total  需传输的总字节
        var percentComplete = Math.ceil((e.loaded / e.total) * 100)
    }
 }
 xhr.upload.onprogress = function(e) {
    if (e.lengthComputable) {
    // 1. 计算出当前上传进度的百分比
    var percentComplete = Math.ceil((e.loaded / e.total) * 100)
    $('#percent')
        // 2. 设置进度条的宽度
        .attr('style', 'width:' + percentComplete + '%')
        // 3. 显示当前的上传进度百分比
        .html(percentComplete + '%')
    }
 } xhr.upload.onload = function() {
     $('#percent')
         // 移除上传中的类样式
         .removeClass()
         // 添加上传完成的类样式
         .addClass('progress-bar progress-bar-success')
 }


```
### axios
Axios 是专注于**网络数据请求**的库。
相比于原生的 XMLHttpRequest 对象，axios **简单易用**。
相比于 jQuery，axios 更加**轻量化**，只专注于网络数据请求。
#### get
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661582724381-81bd9780-f80b-4de0-821a-277fe26d48a1.png#averageHue=%232fa2b4&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=450&id=u4ee725a9&margin=%5Bobject%20Object%5D&name=image.png&originHeight=562&originWidth=1017&originalType=binary&ratio=1&rotation=0&showTitle=false&size=48902&status=done&style=none&taskId=u1d7ca5e4-aa79-4a8c-a481-06dfddaee5c&title=&width=813.6)
#### post
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661582734642-a8523762-32c2-45a4-90be-2460200c8224.png#averageHue=%2342af46&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=457&id=u497a9ba9&margin=%5Bobject%20Object%5D&name=image.png&originHeight=571&originWidth=1061&originalType=binary&ratio=1&rotation=0&showTitle=false&size=51393&status=done&style=none&taskId=ua0b4c7f4-e061-4318-87c3-4dbe4c3557b&title=&width=848.8)
#### 直接使用
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661582783205-8593bf76-d0a3-4bf2-a56e-c5500a7544b1.png#averageHue=%2342af47&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=284&id=uc985fd3b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=355&originWidth=1089&originalType=binary&ratio=1&rotation=0&showTitle=false&size=23518&status=done&style=none&taskId=uaa113283-e668-48f9-8692-f7b7147bc1d&title=&width=871.2)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661582790173-14d9c25e-1f4a-4650-8d7d-7c45f1a6bf7f.png#averageHue=%2347a2e4&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=406&id=ub316e3f0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=508&originWidth=1070&originalType=binary&ratio=1&rotation=0&showTitle=false&size=32021&status=done&style=none&taskId=u22c815bb-a140-4545-a4c8-fa7e2cc1e6a&title=&width=856)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661582794739-cdc39b12-0530-41fa-af4d-29486b9b4dd6.png#averageHue=%2341af46&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=419&id=u425401da&margin=%5Bobject%20Object%5D&name=image.png&originHeight=524&originWidth=1091&originalType=binary&ratio=1&rotation=0&showTitle=false&size=35741&status=done&style=none&taskId=u29754bb8-8036-4b02-b5ff-a5096ceb37c&title=&width=872.8)
## 跨域与JSONP
### 同源策略与跨域
#### 同源
如果两个页面的协议，域名和端口都相同，则两个页面具有**相同的源**
**同源策略**（英文全称 Same origin policy）是浏览器提供的一个安全功能。
MDN 官方给定的概念：同源策略限制了从同一个源加载的文档或脚本如何与来自另一个源的资源进行交互。这是一个用于隔离潜在恶意文件的重要安全机制。
通俗的理解：浏览器规定，A 网站的 JavaScript，不允许和非同源的网站 C 之间，进行资源的交互，例如：
①无法读取非同源网页的 Cookie、LocalStorage和 IndexedDB
②无法接触非同源网页的 DOM
③无法向非同源地址发送 Ajax 请求
#### 跨域
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661582987797-067c43a2-5e60-49ac-8fd8-eea325ab22d7.png#averageHue=%23e9e3df&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=365&id=ua54a51d3&margin=%5Bobject%20Object%5D&name=image.png&originHeight=456&originWidth=1156&originalType=binary&ratio=1&rotation=0&showTitle=false&size=53287&status=done&style=none&taskId=uf13724b6-ea77-4c66-a191-f3366f713f3&title=&width=924.8)
现如今，实现跨域数据请求，最主要的两种解决方案，分别是 JSONP和 CORS。
JSONP：出现的早，兼容性好（兼容低版本IE）。是前端程序员为了解决跨域问题，被迫想出来的一种临时解决方案。缺点是只支持 GET 请求，不支持 POST 请求。
CORS：出现的较晚，它是 W3C 标准，属于跨域 Ajax 请求的根本解决方案。支持 GET 和 POST 请求。缺点是不兼容某些低版本的浏览器。
### JSONP
JSONP (JSON with Padding) 是 JSON 的一种“使用模式”，可用于解决主流浏览器的跨域数据访问的问题
由于浏览器同源策略的限制，网页中无法通过 Ajax 请求非同源的接口数据。但是 <script> 标签不受浏览器同源策略的影响，可以通过 src 属性，请求非同源的 js 脚本。
因此，JSONP 的实现原理，就是通过 <script> 标签的 src 属性，请求跨域的数据接口，并通过**函数调用**的形式，接收跨域接口响应回来的数据。![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661583139228-1446d14f-c5b3-447c-93b4-a7710fc3c8f6.png#averageHue=%2346b14b&clientId=u13fcf4e9-c2dc-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=325&id=u67816780&margin=%5Bobject%20Object%5D&name=image.png&originHeight=545&originWidth=1178&originalType=binary&ratio=1&rotation=0&showTitle=false&size=36677&status=done&style=none&taskId=ua97a36b0-85e1-4b22-901e-e6c269eff53&title=&width=702)
由于 JSONP 是通过 <script> 标签的 src 属性，来实现跨域数据获取的，所以，JSONP 只支持 GET 数据请求，不支持 POST 请求。
注意：**JSONP 和 Ajax 之间没有任何关系**，不能把 JSONP 请求数据的方式叫做 Ajax，因为 JSONP 没有用到 XMLHttpRequest 这个对象。
### 防抖和节流
防抖：如果事件被频繁触发，防抖能保证只有最有一次触发生效！前面 N 多次的触发都会被忽略！
节流：如果事件被频繁触发，节流能够减少事件触发的频率，因此，节流是有选择性地执行一部分事件！
## HTTP
day6
