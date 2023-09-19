## 概述
简介
Node.js是一个基于Chorm V8引擎的JS运行环境
浏览器是运行JS的前端运行环境
Node.js是JS的后端运行环境
Node.js中无法调用DOM和BOM等浏览器内置API

作用

- 基于Express框架,可以快速构建Web应用
- 基于Electron框架,可以构建跨越平台的桌面应用
- 基于restify矿建,可以快速构建API接口项目
- 读写和操作数据库,创建实用的命令行工具辅助前端开发

安装
终端: node -v 查看node.js版本号

## 内置模块
### fs文件系统模块
#### 概述
fs模块是Node.js官方提供的,用来操作文件的模块

使用fs模块需要先导入
```javascript
const fs = require('fs')
```
#### fs.readFile()
```javascript
fs.readFile(path[,options],callback)
```
参数一:必选,字符串,文件路径
参数二:可选,文件编码
参数三:必选,回调函数
案例:(通过判断err是否为空,判定文件是否读取成功)
```javascript
const fs = require('fs')

fs.readFile('./files/11.txt','utf-8',function(err,result){
    if(err)
    console.log(err)
    console.log(result)
})
```
#### fs.writeFile()
```javascript
fs.writeFile(file,data[,options],callback)
```
参数一:必选,字符串,文件路径
参数二:必选,写入的内容
参数三:可选,编码方式
参数四:必选,回调函数
```javascript
const fs = require('fs')

fs.writeFile('./1.txt',"hello,world",'utf-8',function(err,result){
        if(err)
             console.log("fail")
        console.log(result)
 })
```

### path路径模块
path模块是官方提供的.用来处理路径的模块.

使用前导入
```javascript
const path = require('path')
```
#### path.join()
可以把多个路径片段拼接为完整的路径字符串
```javascript
path.join([...paths])
```
参数:路径片段的序列
返回值:拼接完成的路径
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661677348103-ac5c6bf8-c89f-4468-be5d-f34382f9e7c0.png#clientId=u549f484f-7224-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=238&id=ue7098bcd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=297&originWidth=910&originalType=binary&ratio=1&rotation=0&showTitle=false&size=81832&status=done&style=none&taskId=uc95c48fc-a5d2-46c6-9477-0d696560ea7&title=&width=728)

#### path.basename()
```javascript
path.basename(path[,ext])
```
path:必选参数,路径字符串
ext:可选参数,文件拓展名
返回值:路径中的最后一部分
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661677568667-c2c24baa-7480-4d00-9e6a-671a0d1de6d0.png#clientId=u549f484f-7224-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=300&id=u2f2bf277&margin=%5Bobject%20Object%5D&name=image.png&originHeight=375&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=92838&status=done&style=none&taskId=u7f6c3a2d-4950-4bed-a100-444754752e7&title=&width=727.2)

#### path.extname()
获取路径中的拓展名
```javascript
path.extname(path)
```
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661677638299-e10897c8-f937-4b07-9f25-496baa5dbbc8.png#clientId=u549f484f-7224-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=206&id=u5140bc23&margin=%5Bobject%20Object%5D&name=image.png&originHeight=257&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=49023&status=done&style=none&taskId=ue9f03710-8d32-4a50-9261-b4eb3afcb6f&title=&width=727.2)

### http模块
http模块是官方提供,用来创建web服务器的模块
```javascript
const http = require('http')
```

#### 创建web服务器
```javascript
const http = require('http')

const server = http.creatServer()

//绑定request事件,只要有客户端请求就会触发
server.on('request',(req,res)=>{
  console.log('')
})

//启动服务器 server.listen(端口号,callback)
server.listen(80,(req,res)=>{
  const str = '马经纬nb'
  //防止中文乱码
  res.setHeader('Content-Type','text/html;charset=utf-8')
  //返回客户端
  res.end(str)
  console.log('')
})
```
## 模块化
### 概述
Node.js中根据模块来源的不同,将模块分为了三大类,分别是:

- 内置模块(由Node.js官方提供的,例如fs,path,http等)
- 自定义模块(用户创建的每个.js文件,都是自定义模块)
- 第三方模块(由第三方开发)
### 使用
```javascript
//内置模块
const fs = require('fs')
//自定义模块
const custom = require('./custum.js')
//第三方模块
const moment = require('moment')
```
#### 模块作用域
在自定义模块中的变量,方法等成员,只能在当前模块内被访问,这种,模块级别的访问限制,叫做模块作用域
防止了全局变量污染的问题
#### 向外共享模块作用域中的成员
module
在每个.js自定义模块中都有一个module对象,他里面存储了和当前模块有关的信息

moudle.exports
在自定义模块中,可以使用module.exports对象,将模块内的成员共享出去,供外界使用
外界使用require()方法时,得到的就是module.exports所指的对象

exports
由于module.exports单词复杂,提供了exports对象和module.exports一样

## npm
### 使用
`npm install 包名`
或者`npm i 报名`
或者`npm install 包名@版本号`
快速创建package.json:`npm init -y`

初次装包完成后,项目文件夹下会多出一个node_modules的文件夹和package-lock.json的配置文件
node_modules文件夹用来存放所有已经安装到项目中的包,require()导入第三方包时,就是从这个目录中查找并加载包
package-lock.json配置文件用来记录node_modules目录下的每一个包的下载信息,例如包的名字,版本号,下载地址等

package.json文件中.有一个dependencies节点,专门用来记录使用的npm install命令安装了哪些包
当我们拿到了一个剔除了node_modules的项目后,需要把所有的包下载到项目中,才能将项目运行起来
使用`npm install`下载所有依赖包
使用`npm uninstall 报名`卸载包

有些包只在项目开发阶段会用到,项目上线后不会用到,建议将这些包放到devDependencies节点中,
使用`npm i 包名 -D`命令等价于`npm install 包名 --save-dev`
都会用到的放到dependencies中

### 解决下包速度慢
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661685301379-1cba77fe-890a-4783-90a8-d4fcd66d2c18.png#clientId=ube08b2af-b7d4-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=270&id=u240dbefa&margin=%5Bobject%20Object%5D&name=image.png&originHeight=338&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=94764&status=done&style=none&taskId=ud38f4f49-ef80-48c9-9428-4e77a625b7a&title=&width=727.2)
为了更方便的切换下包的镜像源，我们可以安装**nrm**这个小工具，利用 nrm提供的终端命令，可以快速查看和切换下包的镜像源。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661685302399-4c6b3fe8-c1a8-4e1c-bea5-9cfa9120f52f.png#clientId=ube08b2af-b7d4-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=270&id=ufd19f2fd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=338&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=70034&status=done&style=none&taskId=ue7d39672-f2d2-4f57-ad0b-94b6d955d73&title=&width=727.2)

### 包的分类
#### 项目包
那些被安装到项目的 node_modules 目录中的包，都是项目包。
项目包又分为两类，分别是：
开发依赖包（被记录到 devDependencies节点中的包，只在开发期间会用到）
核心依赖包（被记录到 dependencies节点中的包，在开发期间和项目上线之后都会用到）

#### 全局包
在执行 npm install 命令时，如果提供了 -g参数，则会把包安装为全局包。
全局包会被安装到 C:\Users\用户目录\AppData\Roaming\npm\node_modules 目录下。
注意：
①只有工具性质的包，才有全局安装的必要性。因为它们提供了好用的终端命令。
②判断某个包是否需要全局安装后才能使用，可以参考官方提供的使用说明即可。

### 模块的加载机制
**模块在第一次加载后会被缓存**。 这也意味着多次调用 require() 不会导致模块的代码被执行多次。
注意：不论是内置模块、用户自定义模块、还是第三方模块，它们都会优先从缓存中加载，从而提高模块的加载效率。
内置模块是由 Node.js 官方提供的模块，内置模块的加载优先级最高。
例如，require('fs') 始终返回内置的 fs 模块，即使在 node_modules 目录下有名字相同的包也叫做 fs。
使用 require() 加载自定义模块时，必须指定以 ./或 ../开头的路径标识符。在加载自定义模块时，如果没有指定 ./ 或 ../ 这样的路径标识符，则 node 会把它当作内置模块或第三方模块进行加载。
同时，在使用 require() 导入自定义模块时，如果省略了文件的扩展名，则 Node.js 会按顺序分别尝试加载以下的文件：
①按照确切的文件名进行加载
②补全 .js扩展名进行加载
③补全 .json 扩展名进行加载
④补全 .node 扩展名进行加载
加载失败，终端报错如果传递给 require() 的模块标识符不是一个内置模块，也没有以 ‘./’ 或 ‘../’ 开头，则 Node.js 会从当前模块的父目录开始，尝试从 /node_modules文件夹中加载第三方模块。
如果没有找到对应的第三方模块，则移动到再上一层父目录中，进行加载，直到文件系统的根目录。
例如，假设在 'C:\Users\itheima\project\foo.js' 文件里调用了 require('tools')，则 Node.js 会按以下顺序查找：
①C:\Users\itheima\project\node_modules\tools
②C:\Users\itheima\node_modules\tools
③C:\Users\node_modules\tools
 C:\node_modules\tools

当把目录作为模块标识符，传递给 require() 进行加载的时候，有三种加载方式：
①在被加载的目录下查找一个叫做 package.json 的文件，并寻找 main 属性，作为 require() 加载的入口
②如果目录里没有 package.json 文件，或者 main 入口不存在或无法解析，则 Node.js 将会试图加载目录下的 index.js 文件。
③如果以上两步都失败了，则 Node.js 会在终端打印错误消息，报告模块的缺失：Error: Cannot find module 'xxx'

## Express
### 概述
Express是基于Node.js平台,快速,开放,极简的web开发框架
通俗的将,express的作用和node.js中内置的http模块类似,是专门用来创建web服务器的

作用

- Web网站服务器
- API接口服务器

### 基本使用
安装
```javascript
npm i express@4.17.1
```
基本使用
```javascript
const express = require('express')

//创建web服务器
const app = express()

//调用app.listen(端口号,回调函数)启动服务器
app.listen(80,()=>{
  console.log('express server running at http://127.0.0.1')
})
```

get
```javascript
app.get('URL',function(req,res){})
```
post
```javascript
app.post('URL',function(req,res){})
```

#### 把内容响应给客户端
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661742602912-169e0236-1dc2-4bb3-9057-46675930164d.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=332&id=uf38a6cb0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=415&originWidth=844&originalType=binary&ratio=1&rotation=0&showTitle=false&size=77515&status=done&style=none&taskId=u09ee2a3e-689c-44d3-8493-5057caf852b&title=&width=675.2)

#### 获取URL中的参数
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661742720342-96081bde-a46a-4e5e-8d0c-df162cc5de24.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=302&id=ued4c659c&margin=%5Bobject%20Object%5D&name=image.png&originHeight=378&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=102904&status=done&style=none&taskId=ua34cda32-021a-4f14-9fbe-85331c3e994&title=&width=727.2)
#### 获取URL中的动态参数
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661742771650-3d38e860-0daa-4213-9e58-e6ba7f5553f2.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=270&id=u8c678855&margin=%5Bobject%20Object%5D&name=image.png&originHeight=338&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=85999&status=done&style=none&taskId=u2ca6f9bb-9c23-4836-97ef-9eafdbd6e8b&title=&width=727.2)

#### 托管静态资源
express.static()
可以创建一个静态资源服务器,如下可以将public目录下的图片,CSS文件.JS文件对外开放访问
托管多个静态资源目录,可以多次调用express.static()函数
```javascript
app.use(express.static('public'))
app,use(express.static('files'))
```
现在，你就可以访问 public 目录中的所有文件了：
http://localhost:3000/images/bg.jpg
http://localhost:3000/css/style.css
http://localhost:3000/js/login.js

挂在路径前缀
```javascript
app.use('public',express.static('public'))
```
现在，你就可以通过带有 /public 前缀地址来访问 public 目录中的文件了：
http://localhost:3000/public/images/kitten.jpg
http://localhost:3000/public/css/style.css
http://localhost:3000/public/js/app.js

#### nodemon
nodemon工具,能够监听项目文件的变动,当代码被修改之后,nodemon会帮我们重启项目,极大方便了开发和测试
安装nodemon
`npm i -g nodemon`
传统的启动方式
`node app.js`
使用nodemon
`nodemon app.js`

### Express 路由
#### 概述
广义上来讲,路由就是映射关系
在express中,路由指的是客户端的请求与服务器处理函数之间的映射关系
Express中的路由分三部分组成,分别是请求的类型,请求的URL地址,处理函数
```javascript
app.METHOD(PATH,HANDLER)
```
路由的例子
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661756000163-52022a8b-710d-445f-a867-3db5b28769ac.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=366&id=u313a9193&margin=%5Bobject%20Object%5D&name=image.png&originHeight=458&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=86542&status=done&style=none&taskId=u57bdd608-1bb2-4b60-b048-a9fa2e8cf5d&title=&width=727.2)
#### 路由的匹配过程
每当一个请求到达服务器之后,需要经过路由的匹配,只有匹配成功之后,才会调用对应处理的函数
在匹配时,会按照路由的顺序进行匹配,如果请求类型和请求的URL同时匹配成功,则Express会将这次请求,转发交给对应的function函数进行处理
### 路由的使用
#### 直接挂载到app上
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661775130381-ebe902c2-be3a-42da-8eda-714c8160e4bb.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=329&id=u587d5c8b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=411&originWidth=769&originalType=binary&ratio=1&rotation=0&showTitle=false&size=97414&status=done&style=none&taskId=uf52be613-128e-4ec1-9f25-767fa37fd75&title=&width=615.2)

#### 模块化路由
为了方便对路由进行模块化管理,Express不建议将路由直接挂载到app上,而是推荐将路由抽离为单独的模块
将路由抽离为单独模块的步骤如下：
①创建路由模块对应的 .js 文件
②调用 express.Router() 函数创建路由对象
③向路由对象上挂载具体的路由
④使用 module.exports 向外共享路由对象![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661775339584-e20a443b-0d6e-4ab6-8630-97893036577a.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=394&id=u9327f007&margin=%5Bobject%20Object%5D&name=image.png&originHeight=492&originWidth=831&originalType=binary&ratio=1&rotation=0&showTitle=false&size=127856&status=done&style=none&taskId=ua1dbed4e-7e0d-44b6-9f66-4a9ecdb85f7&title=&width=664.8)
⑤使用 app.use() 函数注册路由模块
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661775353986-c99886ed-5216-480d-9a80-9ef664e8f6d7.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=238&id=ua8a8e406&margin=%5Bobject%20Object%5D&name=image.png&originHeight=298&originWidth=910&originalType=binary&ratio=1&rotation=0&showTitle=false&size=55028&status=done&style=none&taskId=u7d1dbd4c-2cab-428c-b311-87deb5e9de7&title=&width=728)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661775360829-0848afd0-bd97-482b-880e-74a17f65590b.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=238&id=u221ba4c6&margin=%5Bobject%20Object%5D&name=image.png&originHeight=298&originWidth=910&originalType=binary&ratio=1&rotation=0&showTitle=false&size=69493&status=done&style=none&taskId=ub8c647e9-550b-46d4-bd13-0577a225790&title=&width=728)

### Express中间件
#### 概述
中间件,特指业务流程的中间处理环节
当一个请求到达express的服务器之后,可以连续调用多个中间件,从而对这次请求进行预处理
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661775445738-592b911b-3181-4aaf-8e93-1541259bd53e.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=297&id=ud64138a5&margin=%5Bobject%20Object%5D&name=image.png&originHeight=371&originWidth=682&originalType=binary&ratio=1&rotation=0&showTitle=false&size=42758&status=done&style=none&taskId=u4a1dce04-abb1-4ace-9373-ec24ee9e886&title=&width=545.6)

express的中间件,本质上是一个function处理函数,格式如下
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661775478392-fa26d820-e2cf-4c67-91c7-e6c46a6b7a61.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=249&id=u17e62c75&margin=%5Bobject%20Object%5D&name=image.png&originHeight=311&originWidth=968&originalType=binary&ratio=1&rotation=0&showTitle=false&size=167905&status=done&style=none&taskId=u465d8f91-f527-42ae-9f42-9840a6e6fb1&title=&width=774.4)
注意：中间件函数的形参列表中，必须包含 next 参数。而路由处理函数中只包含 req 和 res。
**next 函数**是实现多个中间件连续调用的关键，它表示把流转关系转交给下一个中间件或路由。

#### 使用
定义中间件函数
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661775650500-623b5976-74fe-439b-95f1-0cafc731da7e.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=296&id=ud8bbe50e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=370&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=94447&status=done&style=none&taskId=u069a636e-d024-4812-a594-7144443e2ae&title=&width=727.2)

局部生效的中间件
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661776131709-f98d1b5c-f96d-4451-a7a6-a8fc8411df5c.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=333&id=u69cef411&margin=%5Bobject%20Object%5D&name=image.png&originHeight=416&originWidth=721&originalType=binary&ratio=1&rotation=0&showTitle=false&size=95221&status=done&style=none&taskId=ubcf5c40b-b71b-4854-ab45-efc206e7800&title=&width=576.8)

多个局部中间件
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661776146818-b048d66d-5315-4880-8666-6f87aa2c9dfa.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=173&id=u6e029790&margin=%5Bobject%20Object%5D&name=image.png&originHeight=216&originWidth=910&originalType=binary&ratio=1&rotation=0&showTitle=false&size=83277&status=done&style=none&taskId=u87c64f64-ae56-4119-b8be-9a58aff91f0&title=&width=728)

全局生效的中间件
客户端发起的任何请求，到达服务器之后，都会触发的中间件，叫做全局生效的中间件。
通过调用 app.use(中间件函数)，即可定义一个全局生效的中间件，示例代码如下：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661775682292-f45ad30e-e921-4ef3-82a5-5dd0c0414c7b.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=294&id=u9d678305&margin=%5Bobject%20Object%5D&name=image.png&originHeight=367&originWidth=816&originalType=binary&ratio=1&rotation=0&showTitle=false&size=59376&status=done&style=none&taskId=u0d913072-34ff-47b1-ab22-724b5fe987b&title=&width=652.8)

定义全局中间件的简化形式
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661775704939-07b626a1-4c34-4838-ab9a-e95dddb19603.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=234&id=uab9d8a70&margin=%5Bobject%20Object%5D&name=image.png&originHeight=293&originWidth=910&originalType=binary&ratio=1&rotation=0&showTitle=false&size=51738&status=done&style=none&taskId=u60d73bef-b344-4710-a3c1-8a3b6462285&title=&width=728)

#### 作用
多个中间件之间,共享一份req和res.基于这样的特性,我们可以在上游的中间件中,统一为req或者res对象添加自定义的属性或方法,供下游的中间件或路由进行使用

#### 中间件的分类
①应用级别的中间件
通过 app.use() 或 app.get() 或app.post() ，绑定到 app 实例上的中间件，叫做应用级别的中间件，代码示例如下：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661776231312-f6092976-1238-4882-af98-7c649e26223a.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=334&id=u1a89cda4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=417&originWidth=846&originalType=binary&ratio=1&rotation=0&showTitle=false&size=67259&status=done&style=none&taskId=u946d0926-8923-4162-b98e-881f5e39b92&title=&width=676.8)
②路由级别的中间件
绑定到 express.Router() 实例上的中间件，叫做路由级别的中间件。它的用法和应用级别中间件没有任何区别。只不过，应用级别中间件是绑定到 app 实例上，路由级别中间件绑定到 router 实例上，代码示例如下：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661776330753-107b5d6f-bde7-4974-8176-fe39fe1a2eec.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=302&id=ud3e7567e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=378&originWidth=690&originalType=binary&ratio=1&rotation=0&showTitle=false&size=53050&status=done&style=none&taskId=u20d67ebe-0fef-4fcc-8493-1f94662719e&title=&width=552)
③错误级别的中间件
错误级别中间件的**作用**：专门用来捕获整个项目中发生的异常错误，从而防止项目异常崩溃的问题。
**格式**：错误级别中间件的 function 处理函数中，必须有 4 个形参，形参顺序从前到后，分别是 (err, req, res, next)。
**注意：**错误级别的中间件，必须注册在所有路由之后！
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661776357643-1efc1ab2-6384-4767-8f1d-61504293ef5f.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=289&id=u9aa4cd7b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=361&originWidth=784&originalType=binary&ratio=1&rotation=0&showTitle=false&size=114545&status=done&style=none&taskId=u0c0f06d7-fba9-4bd9-8ad3-fa3a8a41e6f&title=&width=627.2)
④Express 内置的中间件
自 Express 4.16.0 版本开始，Express 内置了 3 个常用的中间件，极大的提高了 Express 项目的开发效率和体验：
①express.static快速托管静态资源的内置中间件，例如： HTML 文件、图片、CSS 样式等（无兼容性）
②express.json解析 JSON 格式的请求体数据（有兼容性，仅在 4.16.0+ 版本中可用）
③express.urlencoded解析 URL-encoded 格式的请求体数据（有兼容性，仅在 4.16.0+ 版本中可用）
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661776382505-3dd77417-b1cb-4441-b5ba-da9c79d96155.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=194&id=uf3816184&margin=%5Bobject%20Object%5D&name=image.png&originHeight=243&originWidth=853&originalType=binary&ratio=1&rotation=0&showTitle=false&size=76621&status=done&style=none&taskId=u308dc453-3c14-42c6-a7c0-cbf3d2ff8ca&title=&width=682.4)
⑤第三方的中间件
非 Express 官方内置的，而是由第三方开发出来的中间件，叫做第三方中间件。在项目中，大家可以按需下载并配置第三方中间件，从而提高项目的开发效率。
例如：在 express@4.16.0 之前的版本中，经常使用 body-parser 这个第三方中间件，来解析请求体数据。使用步骤如下：
①运行 npm install body-parser安装中间件
②使用 require导入中间件
③调用 app.use() 注册并使用中间件

### CORS跨域资源共享
#### 概述
解决接口跨域问题的方案主要有两种：
①CORS（主流的解决方案，推荐使用）
②JSONP（有缺陷的解决方案：只支持 GET 请求）
CORS （Cross-Origin Resource Sharing，跨域资源共享）由一系列 HTTP 响应头组成，**这些 HTTP 响应头决定浏览器是否阻止前端 JS 代码跨域获取资源**。

cors是 Express 的一个第三方中间件。通过安装和配置 cors中间件，可以很方便地解决跨域问题。
使用步骤分为如下 3 步：
①运行 npm install cors安装中间件
②使用 const cors = require('cors') 导入中间件
③在路由之前调用 app.use(cors()) 配置中间件

响应头部中可以携带一个 **Access-Control-Allow-Origin**字段，其语法如下:
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661777902449-9c8ee79b-fc15-43e4-9d55-a11f6f9596a3.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=111&id=u5c6cf1d2&margin=%5Bobject%20Object%5D&name=image.png&originHeight=139&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=21252&status=done&style=none&taskId=u71835e97-d922-4c18-8a3e-1247d67e139&title=&width=727.2)
其中，origin 参数的值指定了允许访问该资源的外域 URL。
例如，下面的字段值将**只允许**来自 http://itcast.cn 的请求：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661777912984-694479de-e840-4d22-9882-911d1df790e0.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=111&id=uce4db3ff&margin=%5Bobject%20Object%5D&name=image.png&originHeight=139&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=28078&status=done&style=none&taskId=ufc9e7465-cc22-4eca-a116-7b43adb23a9&title=&width=727.2)
如果指定了 Access-Control-Allow-Origin 字段的值为通配符*****，表示允许来自任何域的请求，示例代码如下：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661777926051-ef7c24ed-b8b9-460e-baad-e576c220b260.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=111&id=u7ff0b738&margin=%5Bobject%20Object%5D&name=image.png&originHeight=139&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=23112&status=done&style=none&taskId=ua6567581-5449-4aa7-893a-8d8d8eec55c&title=&width=727.2)
默认情况下，CORS **仅**支持客户端向服务器发送如下的 9 个请求头：
Accept、Accept-Language、Content-Language、DPR、Downlink、Save-Data、Viewport-Width、Width 、Content-Type （值仅限于 text/plain、multipart/form-data、application/x-www-form-urlencoded三者之一）
如果客户端向服务器发送了额外的请求头信息，则需要在服务器端，通过 Access-Control-Allow-Headers 对额外的请求头进行声明，否则这次请求会失败！
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661777943810-815b8f03-2fc4-469c-b4ab-876bcf23e39a.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=164&id=u12c9c777&margin=%5Bobject%20Object%5D&name=image.png&originHeight=205&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=77654&status=done&style=none&taskId=ucf8b9e68-adef-4f6c-968a-ee47d50d505&title=&width=727.2)
默认情况下，CORS 仅支持客户端发起 GET、POST、HEAD 请求。
如果客户端希望通过 PUT、DELETE 等方式请求服务器的资源，则需要在服务器端，通过 Access-Control-Alow-Methods来指明实际请求所允许使用的 HTTP 方法。
示例代码如下：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661777956282-09a983d6-7d28-43e4-83e5-3cb3e0bb3568.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=207&id=uee544cdd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=259&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=79127&status=done&style=none&taskId=ub2bceeb9-4d22-4a82-a401-670de355005&title=&width=727.2)

#### CORS请求类型
客户端在请求 CORS 接口时，根据请求方式和请求头的不同，可以将 CORS 的请求分为两大类，分别是：
①简单请求
同时满足以下两大条件的请求，就属于简单请求：
①请求方式：GET、POST、HEAD 三者之一
②HTTP 头部信息不超过以下几种字段：无自定义头部字段、Accept、Accept-Language、Content-Language、DPR、Downlink、Save-Data、Viewport-Width、Width 、Content-Type（只有三个值application/x-www-form-urlencoded、multipart/form-data、text/plain）

②预检请求
只要符合以下任何一个条件的请求，都需要进行预检请求：
① 请求方式为 GET、POST、HEAD 之外的请求 Method 类型
② 请求头中包含自定义头部字段
③ 向服务器发送了 application/json 格式的数据

在浏览器与服务器正式通信之前，浏览器会先发送 OPTION 请求进行预检，以获知服务器是否允许该实际请求，所以这一次的 OPTION 请求称为“预检请求”。服务器成功响应预检请求后，才会发送真正的请求，并且携带真实数据。

**简单请求的特点**：客户端与服务器之间只会发生一次请求。
**预检请求的特点**：客户端与服务器之间会发生两次请求，OPTION 预检请求成功之后，才会发起真正的请求。

## 数据库与身份认证
### 数据库使用
#### 使用步骤
安装mysql模块
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778289897-3b8ca96f-8e5a-4b68-9292-6319e455787f.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=111&id=u7e335614&margin=%5Bobject%20Object%5D&name=image.png&originHeight=139&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=11689&status=done&style=none&taskId=u96763dbf-b2bc-4982-945b-9c5951d4135&title=&width=727.2)
配置mysql模块
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778297699-bea96ead-f01e-4881-b0ab-1a9cab56df09.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=335&id=ue62d1c56&margin=%5Bobject%20Object%5D&name=image.png&originHeight=419&originWidth=831&originalType=binary&ratio=1&rotation=0&showTitle=false&size=106947&status=done&style=none&taskId=uf7e297e2-bc0a-447e-a975-518e15f2a44&title=&width=664.8)
执行sql语句
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778312139-84678e05-bc48-4839-b233-2ab2e6767c02.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=270&id=uf66e9be7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=338&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=91262&status=done&style=none&taskId=u96f0fb8a-79e8-4f1c-b4d9-3c630278321&title=&width=727.2)
查询
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778337803-97f38fde-b5d4-4c2d-8db0-69634a1f4b8e.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=302&id=ueb192869&margin=%5Bobject%20Object%5D&name=image.png&originHeight=378&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=78563&status=done&style=none&taskId=u8170582a-af0d-4621-b2bf-af832fb4192&title=&width=727.2)
插入
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778350969-ce686a16-1c2c-46fc-b785-5297475dafdd.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=302&id=ufaef47f4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=378&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=78563&status=done&style=none&taskId=ufb4156d5-e400-4d70-b382-e407b7c5895&title=&width=727.2)
如果数据对象的每个属性和数据表的字段**一一对应**，则可以通过如下方式快速插入数据：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778378043-16c79881-ba2f-49a4-98e7-37a1d79af4e9.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=334&id=uf6ad717c&margin=%5Bobject%20Object%5D&name=image.png&originHeight=417&originWidth=827&originalType=binary&ratio=1&rotation=0&showTitle=false&size=135735&status=done&style=none&taskId=u2f016d5c-d2ec-4a63-b2eb-169938a3028&title=&width=661.6)
更新
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778386382-c43e11cc-9639-4791-8f22-5de1391f4ae6.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=332&id=u04e7a5dd&margin=%5Bobject%20Object%5D&name=image.png&originHeight=415&originWidth=863&originalType=binary&ratio=1&rotation=0&showTitle=false&size=153931&status=done&style=none&taskId=ud726e948-8a94-47cc-b618-df5250e37ec&title=&width=690.4)
如果数据对象的每个属性和数据表的字段**一一对应**，则可以通过如下方式快速更新表数据：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778402141-a5e047c3-39be-42e7-905a-294e8f24baa8.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=333&id=ucb0d51be&margin=%5Bobject%20Object%5D&name=image.png&originHeight=416&originWidth=826&originalType=binary&ratio=1&rotation=0&showTitle=false&size=143780&status=done&style=none&taskId=u0ceca31e-092d-422c-82a4-c2d1b7f6890&title=&width=660.8)
删除
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778410035-217cd03d-26d9-44c5-a6ce-6377b870e617.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=333&id=u02400eac&margin=%5Bobject%20Object%5D&name=image.png&originHeight=416&originWidth=826&originalType=binary&ratio=1&rotation=0&showTitle=false&size=152188&status=done&style=none&taskId=uceb8250f-9a27-4d3b-ac3b-80e20611a0f&title=&width=660.8)

### 身份认证

#### 概述
目前主流的 Web 开发模式有两种，分别是：
①基于服务端渲染的传统 Web 开发模式
服务端渲染的概念：服务器发送给客户端的 HTML 页面，是在服务器通过字符串的拼接，动态生成的。因此，客户端不需要使用 Ajax 这样的技术额外请求页面的数据。代码示例如下：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778508187-301083b8-b912-470e-9d70-75ab13ce6560.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=304&id=u9590a991&margin=%5Bobject%20Object%5D&name=image.png&originHeight=380&originWidth=827&originalType=binary&ratio=1&rotation=0&showTitle=false&size=112862&status=done&style=none&taskId=u59393b4f-d6b0-4756-86c6-d695d164763&title=&width=661.6)
优点：
①**前端耗时少。**因为服务器端负责动态生成 HTML 内容，浏览器只需要直接渲染页面即可。尤其是移动端，更省电。
②**有利于SEO。**因为服务器端响应的是完整的 HTML 页面内容，所以爬虫更容易爬取获得信息，更有利于 SEO。
缺点：
①**占用服务器端资源。**即服务器端完成 HTML 页面内容的拼接，如果请求较多，会对服务器造成一定的访问压力。
②**不利于前后端分离，开发效率低。**使用服务器端渲染，则**无法进行分工合作**，尤其对于**前端复杂度高**的项目，不利于项目高效开发。


②基于前后端分离的新型 Web 开发模式
前后端分离的概念：前后端分离的开发模式，**依赖于 Ajax 技术的广泛应用**。简而言之，前后端分离的 Web 开发模式，就是**后端只负责提供 API 接口，前端使用 Ajax 调用接口**的开发模式。

优点：
①**开发体验好。**前端专注于 UI 页面的开发，后端专注于api的开发，且前端有更多的选择性。
②**用户体验好。**Ajax 技术的广泛应用，极大的提高了用户的体验，可以轻松实现页面的局部刷新。
③**减轻了服务器端的渲染压力。**因为页面最终是在每个用户的浏览器中生成的。
缺点：
①**不利于 SEO。**因为完整的 HTML 页面需要在客户端动态拼接完成，所以爬虫对无法爬取页面的有效信息。（解决方案：利用 Vue、React 等前端框架的 **SSR**（server side render）技术能够很好的解决 SEO 问题！）

**不谈业务场景而盲目选择使用何种开发模式都是耍流氓。**
l比如企业级网站，主要功能是展示而没有复杂的交互，并且需要良好的 SEO，则这时我们就需要使用服务器端渲染；
l而类似后台管理项目，交互性比较强，不需要考虑 SEO，那么就可以使用前后端分离的开发模式。
另外，具体使用何种开发模式并不是绝对的，为了**同时兼顾**了**首页的渲染速度**和**前后端分离的开发效率**，一些网站采用了首屏服务器端渲染 + 其他页面前后端分离的开发模式。

对于服务端渲染和前后端分离这两种开发模式来说，分别有着不同的身份认证方案：
①服务端渲染推荐使用 **Session 认证机制**
②前后端分离推荐使用 **JWT 认证机制**

#### Sesson认证机制
HTTP 协议的无状态性，指的是客户端**的每次 HTTP 请求都是独立的**，连续多个请求之间没有直接的关系，**服务器不会主动保留每次 HTTP 请求的状态**。

对于超市来说，为了方便收银员在进行结算时给 VIP 用户打折，超市可以为每个 VIP 用户发放会员卡。(cookie)
Cookie 是**存储在用户浏览器中的一段不超过 4 KB 的字符串**。它由一个名称（Name）、一个值（Value）和其它几个用于控制 Cookie 有效期、安全性、使用范围的可选属性组成。
不同域名下的 Cookie 各自独立，每当客户端发起请求时，会**自动**把**当前域名下**所有**未过期的 Cookie **一同发送到服务器。
**Cookie的几大特性：**
①自动发送
②域名独立
③过期时限
4KB 限制

客户端第一次请求服务器的时候，服务器**通过响应头的形式**，向客户端发送一个身份认证的 Cookie，客户端会自动将 Cookie 保存在浏览器中。
随后，当客户端浏览器每次请求服务器的时候，浏览器会**自动**将身份认证相关的 Cookie，**通过请求头的形式**发送给服务器，服务器即可验明客户端的身份。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778655137-749e4635-67ce-4365-9fe4-170b6cd3cd87.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=242&id=u29d867f4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=303&originWidth=1159&originalType=binary&ratio=1&rotation=0&showTitle=false&size=29310&status=done&style=none&taskId=u7accbb74-95b4-4cd6-bd30-15184faeb81&title=&width=927.2)
由于 Cookie 是存储在浏览器中的，而且**浏览器也提供了读写 Cookie 的 API**，因此 **Cookie 很容易被伪造**，不具有安全性。因此不建议服务器将重要的隐私数据，通过 Cookie 的形式发送给浏览器。

为了防止客户伪造会员卡，收银员在拿到客户出示的会员卡之后，可以**在收银机上进行刷卡认证**。只有收银机确认存在的会员卡，才能被正常使用。
这种“**会员卡+刷卡认证**”的设计理念，就是 Session 认证机制的精髓。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778737285-e30b097c-f81f-41eb-b46e-aedaba3a9817.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=376&id=u77de13d7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=470&originWidth=943&originalType=binary&ratio=1&rotation=0&showTitle=false&size=94489&status=done&style=none&taskId=u6bd05d9d-5309-4ba7-b130-e1468da3622&title=&width=754.4)

#### Session认证使用
安装中间件
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778781442-a15e0b03-068d-41a2-beec-cc5af0fc8da6.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=111&id=ua14a1f05&margin=%5Bobject%20Object%5D&name=image.png&originHeight=139&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=16152&status=done&style=none&taskId=u6cbe1dfe-9649-47af-9dbb-6778342bed2&title=&width=727.2)
配置express-session中间件
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778944323-b48f0ed9-d0dd-4147-8a91-26cf55ba74f3.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=335&id=u925fc27d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=419&originWidth=831&originalType=binary&ratio=1&rotation=0&showTitle=false&size=88719&status=done&style=none&taskId=uc0ccc76c-cc67-4029-8554-0964a3322a5&title=&width=664.8)
向session中储存数据
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778964481-80ef6951-7139-448a-beb2-400f195fe0af.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=335&id=u1d8681d8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=419&originWidth=709&originalType=binary&ratio=1&rotation=0&showTitle=false&size=108519&status=done&style=none&taskId=u992cf775-317d-41ab-8d5e-bcb574e7a48&title=&width=567.2)3
从session中取数据
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778983605-1def7351-8f22-4beb-96ba-5ef0091d2463.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=315&id=ue6335913&margin=%5Bobject%20Object%5D&name=image.png&originHeight=394&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=91018&status=done&style=none&taskId=u2597c433-770c-4c40-98bc-59dc81d9b6a&title=&width=727.2)
清空session
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661778993271-e0625b1a-8956-485b-87ff-8a1e1922c677.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=335&id=u570c3744&margin=%5Bobject%20Object%5D&name=image.png&originHeight=419&originWidth=831&originalType=binary&ratio=1&rotation=0&showTitle=false&size=68491&status=done&style=none&taskId=ue9888dac-902c-44b0-98af-09252e9f346&title=&width=664.8)

#### JWT认证机制
Session 认证机制需要配合 Cookie 才能实现。由于 Cookie 默认不支持跨域访问，所以，当涉及到前端跨域请求后端接口的时候，**需要做很多额外的配置**，才能实现跨域 Session 认证。
注意：
当前端请求后端接口**不存在跨域问题**的时候，**推荐使用 Session **身份认证机制。
当前端需要跨域请求后端接口的时候，不推荐使用 Session 身份认证机制，推荐使用 JWT 认证机制。
JWT（英文全称：JSON Web Token）是目前**最流行**的**跨域认证解决方案**。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661779044818-62a4b3c9-ea0b-4b6d-92b2-8aa9a5f43533.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=332&id=u4b5b43ca&margin=%5Bobject%20Object%5D&name=image.png&originHeight=415&originWidth=805&originalType=binary&ratio=1&rotation=0&showTitle=false&size=71075&status=done&style=none&taskId=u3cc01967-96d6-4eab-941e-1aa340c6888&title=&width=644)
总结：用户的信息通过 Token 字符串的形式，保存在客户端浏览器中。服务器通过还原 Token 字符串的形式来认证用户的身份。
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661779068609-26fb9aff-9384-445f-b3d1-2ce8c442c5cf.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=367&id=u05220696&margin=%5Bobject%20Object%5D&name=image.png&originHeight=459&originWidth=943&originalType=binary&ratio=1&rotation=0&showTitle=false&size=105610&status=done&style=none&taskId=uc83f433b-a03c-4161-b9a5-72aea1fe8a3&title=&width=754.4)
JWT 的三个组成部分，从前到后分别是 Header、Payload、Signature。
其中：
**Payload**部分**才是真正的用户信息**，它是用户信息经过加密之后生成的字符串。
Header 和 Signature 是**安全性相关**的部分，只是为了保证 Token 的安全性。

客户端收到服务器返回的 JWT 之后，通常会将它储存在 localStorage或 sessionStorage中。
此后，客户端每次与服务器通信，都要带上这个 JWT 的字符串，从而进行身份认证。推荐的做法是**把 JWT 放在 HTTP 请求头的 Authorization 字段中**，格式如下：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661779119714-cf744bce-0f12-4100-b6a5-74ee71eba52e.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=111&id=u533bcc26&margin=%5Bobject%20Object%5D&name=image.png&originHeight=139&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=17026&status=done&style=none&taskId=uddcb16a2-f214-4dc6-a052-0fbf566c9ea&title=&width=727.2)
#### JWT使用
安装包
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661779134350-695ec943-7756-4848-aef9-0c6864348594.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=111&id=u1d3270bc&margin=%5Bobject%20Object%5D&name=image.png&originHeight=139&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=20390&status=done&style=none&taskId=u187dabda-3537-49b1-89d2-b7a293ee68e&title=&width=727.2)
其中：
**jsonwebtoken**用于**生成 JWT 字符串**
**express-jwt**用于**将 JWT 字符串解析还原成 JSON 对象**

**导入包**
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661780221125-24bbcff2-7e17-4331-8874-842750142e29.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=206&id=u1c094a89&margin=%5Bobject%20Object%5D&name=image.png&originHeight=258&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=79334&status=done&style=none&taskId=u4f0d86af-8ac3-49a3-956c-b4288b581f9&title=&width=727.2)

**定义 secret 密钥**
为了保证 JWT 字符串的安全性，防止 JWT 字符串在网络传输过程中被别人破解，我们需要专门定义一个用于**加密**和**解密**的 secret 密钥：
①当生成 JWT 字符串的时候，需要使用 secret 密钥对用户的信息进行加密，最终得到加密好的 JWT 字符串
②当把 JWT 字符串解析还原成 JSON 对象的时候，需要使用 secret 密钥进行解密
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661780318925-12955685-243c-47a7-acaf-a33258e51102.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=144&id=ubd739cff&margin=%5Bobject%20Object%5D&name=image.png&originHeight=180&originWidth=910&originalType=binary&ratio=1&rotation=0&showTitle=false&size=33327&status=done&style=none&taskId=u6ee0b38f-1c63-4dfe-8f51-84a42f83252&title=&width=728)

**在登录成功后生成 JWT 字符串**
调用 **jsonwebtoken**包提供的 **sign()**方法，将用户的信息加密成 JWT 字符串，响应给客户端：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661780380521-b0250b5d-057a-4315-b4ad-c467c5471a62.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=339&id=u5b5a6192&margin=%5Bobject%20Object%5D&name=image.png&originHeight=424&originWidth=823&originalType=binary&ratio=1&rotation=0&showTitle=false&size=113849&status=done&style=none&taskId=ud23d7be8-0e95-4651-a94e-0656cbd7d8e&title=&width=658.4)

**将 JWT 字符串还原为 JSON 对象**
客户端每次在访问那些有权限接口的时候，都需要主动通过**请求头中的 Authorization 字段**，将 Token 字符串发送到服务器进行身份认证。
此时，服务器可以通过 **express-jwt**这个中间件，自动将客户端发送过来的 Token 解析还原成 JSON 对象：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661780432611-13b4ae4c-8a60-4630-a0d1-76ac5e6259c6.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=207&id=ud36a42a7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=259&originWidth=909&originalType=binary&ratio=1&rotation=0&showTitle=false&size=100953&status=done&style=none&taskId=u47039360-ec61-4df4-9904-e3e701beb72&title=&width=727.2)

**使用 req.user获取用户信息**
当 express-jwt这个中间件配置成功之后，即可在那些有权限的接口中，使用 **req.user**对象，来访问从 JWT 字符串中解析出来的用户信息了，示例代码如下：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661780459459-003b0c88-909f-44d4-8464-8f25edc3d6d4.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=302&id=ub61871d3&margin=%5Bobject%20Object%5D&name=image.png&originHeight=378&originWidth=751&originalType=binary&ratio=1&rotation=0&showTitle=false&size=60746&status=done&style=none&taskId=u285c5507-de4b-4340-9bc4-d582ecf91c2&title=&width=600.8)

**捕获解析 JWT 失败后产生的错误**
当使用 express-jwt解析 Token 字符串时，如果客户端发送过来的 Token 字符串**过期**或**不合法**，会产生一个**解析失败**的错误，影响项目的正常运行。我们可以通过 **Express 的错误中间件**，捕获这个错误并进行相关的处理，示例代码如下
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661780552148-5c1eee4b-9249-413e-8351-ec0e4f3f4faf.png#clientId=u5d0c9a25-682f-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=301&id=u542c955a&margin=%5Bobject%20Object%5D&name=image.png&originHeight=376&originWidth=834&originalType=binary&ratio=1&rotation=0&showTitle=false&size=89810&status=done&style=none&taskId=u3d256e50-10f5-4f90-b603-73d06cf1437&title=&width=667.2)
