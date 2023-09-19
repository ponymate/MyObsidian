## Web Apis简介
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661237195993-3513af04-35b7-420e-b3cf-6b9cc8c24f3e.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=433&id=ub406b025&margin=%5Bobject%20Object%5D&name=image.png&originHeight=541&originWidth=907&originalType=binary&ratio=1&rotation=0&showTitle=false&size=20275&status=done&style=none&taskId=ud895dbbe-148f-4fa5-9386-970ee639078&title=&width=725.6)
## DOM
### 简介
文档对象模型（Document Object Model）
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661237303248-c6203d11-ec6c-4f0b-a76e-2a0cdf7181fd.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=442&id=u01457469&margin=%5Bobject%20Object%5D&name=image.png&originHeight=553&originWidth=951&originalType=binary&ratio=1&rotation=0&showTitle=false&size=47118&status=done&style=none&taskId=ud7fe705c-c5cb-4363-9671-7ea3d0cfbf0&title=&width=760.8)
### 获取元素
根据id获取
document.ElementById('id');
返回带有指定标签名的对象集合
document.getElementByTagName('标签名');  
 获取某元素内部指定标签名的子元素
element.getElementByTagName('标签名');
根据类名获取
document.getElementByClassName('类名');
根据选择器获取(返回第一个元素和返回全部元素）
document.querySelector('xuanzeqi'); 
document.querySelectorAll('xuanzeqi');
获取特殊元素
document.body //返回body元素对象
document.documentElement //返回html元素对象

### 事件基础
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661237865587-da1d0dec-2ad2-4971-94ba-54be30c39cf6.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=351&id=u08aefa9b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=439&originWidth=1023&originalType=binary&ratio=1&rotation=0&showTitle=false&size=89535&status=done&style=none&taskId=u2a80f38a-fb32-45ab-9dc8-16986cc37b9&title=&width=818.4)
### 操作元素
去除html标签，同时空格和换行也会去掉
element.innerText
识别html标签，保留空格和换行
element.innerHTML
#### 常见的元素属性操作
innerText，innerHTML
src，href
id，alt，title

#### 表单元素的属性操作
type，value，checked，selected，disabled

#### 样式属性操作
element.style.~  行内样式操作
element.className 类名样式操作

#### 自定义属性的操作
element.属性 获取元素自带属性值
element.getAttribute('shuxing');  获取自定义属性

element.属性='值'
element.setAttribute('shuxing','zhi');

element.removeAttribute('shuxing');

#### H5自定义属性
规定自定义属性以data-开头
<div data-index='1'> </div>
element.setAttribute('data-index',1)

element.dataset.index 或者 element.dataset['index']

### 节点操作
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661239029453-af2ad1fb-8e4c-4e35-816f-7f33982505ab.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=253&id=u08294896&margin=%5Bobject%20Object%5D&name=image.png&originHeight=316&originWidth=1011&originalType=binary&ratio=1&rotation=0&showTitle=false&size=28477&status=done&style=none&taskId=u45f91461-8fa9-4336-981c-26da720243c&title=&width=808.8)
一般的节点至少拥有nodeType(节点类型),nodeName(节点名称)和nodeValue(节点值)这三个基本属性

- 元素节点 nodeType 为1
- 属性节点nodeType 为2
- 文本节点nodeType 为3

父级节点
node.parentNode 返回最近的一个父节点
parentNode.chileNodes 返回子节点集合,包括元素节点和文本节点等,一般不使用
parentNode.chileren 返回子元素节点,重点掌握
parentNode.firstChild 包括所有节点
parentNode.lastChild 包括所有节点
parentNode.firstElementChild 元素节点
parentNode.lastElementChild 元素节点

兄弟节点
node.nextSibling 所有节点
node.previousSibling 所有节点
node.nextElementSibling 
node.previousElementSibling

创建节点
document.createElement('tagName') 
node.appendChild(child) 添加节点至父节点末尾
node.insertBefore(child,指定元素) 添加child至指定元素之前
![22d3c822-7371-4639-b2ba-536d57fbbb54.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661240403194-2c987c77-b680-487f-a65f-1a681bc9af48.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=drop&id=u44295f61&margin=%5Bobject%20Object%5D&name=22d3c822-7371-4639-b2ba-536d57fbbb54.png&originHeight=509&originWidth=970&originalType=binary&ratio=1&rotation=0&showTitle=false&size=40040&status=done&style=none&taskId=u6245e8a0-5976-4865-8738-03c08d99e51&title=)

删除节点
node.removeChild(child)

复制节点
node.cloneNode()
如果括号参数为空或者false,则为浅拷贝,只复制节点自身,不复制子节点
如果括号参数为true,为深度拷贝,会复制节点自身以及里面所有的子节点

### 事件高级
#### 注册事件
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661240644428-380cd6de-f880-4112-9b1f-1b31956fde21.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=282&id=u0e29adaf&margin=%5Bobject%20Object%5D&name=image.png&originHeight=353&originWidth=1112&originalType=binary&ratio=1&rotation=0&showTitle=false&size=34831&status=done&style=none&taskId=u406399a7-d668-4672-9db9-54bfae1090d&title=&width=889.6)
eventTarget.addEventListener(type,listener[,useCapture])

eventTarget.addEventListener()方法将指定的监听器注册到eventTarget（目标对象）上，当该对象触发指定的事件时，就会执行事件处理函数。
该方法接收三个参数：
type：事件类型字符串，比如click 、mouseover ，注意这里不要带on
listener：事件处理函数，事件发生时，会调用该监听函数
useCapture：可选参数，是一个布尔值，默认是 false。学完 DOM 事件流后，我们再进一步学习

#### 删除事件
传统注册方式:
eventTarget.onclick = null;
eventTarget.removeEventListener(type,listener[,useCapture]);

#### DOM事件流
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661241311725-840782a8-c339-4b89-8198-15cc4ef162d1.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=255&id=ub41efc73&margin=%5Bobject%20Object%5D&name=image.png&originHeight=319&originWidth=877&originalType=binary&ratio=1&rotation=0&showTitle=false&size=47548&status=done&style=none&taskId=u4fd826ab-a310-4aa2-a540-dd27779adb6&title=&width=701.6)
**注意**
1.JS 代码中只能执行捕获或者冒泡其中的一个阶段。
2.onclick 和 attachEvent 只能得到冒泡阶段。
3.addEventListener(type, listener[, useCapture])第三个参数如果是 true，表示在事件捕获阶段调用事件处理程序；如果是 false（不写默认就是false），表示在事件冒泡阶段调用事件处理程序。
4.实际开发中我们很少使用事件捕获，我们更关注事件冒泡。
5.有些事件是没有冒泡的，比如 onblur、onfocus、onmouseenter、onmouseleave
6.事件冒泡有时候会带来麻烦，有时候又会帮助很巧妙的做某些事件，我们后面讲解。

#### 事件对象                    
eventTarget.onclick = function(event){}
eventTarget.addEventListener('click',function(event){})

event事件对象,可以写成evt或者e
event对象代表事件的状态,比如键盘按键的状态,鼠标的位置,鼠标按钮的开关.有很多属性和方法

e.target和this的区别
this 是事件绑定的元素,这个函数的调用者(绑定这个事件的元素)
e.target 是事件触发的元素
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661243189710-dc8dc999-3b78-494c-a9e1-a830accf7e95.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=289&id=u7bffca6e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=361&originWidth=889&originalType=binary&ratio=1&rotation=0&showTitle=false&size=136217&status=done&style=none&taskId=udf1712f0-c4ea-4c75-bd89-15a5104b24c&title=&width=711.2)

#### 阻止事件冒泡
e.stopPropagation()
#### 事件委托(代理,委派)
**事件委托的原理**
不是每个子节点单独设置事件监听器，而是事件监听器设置在其父节点上，然后利用冒泡原理影响设置每个子节点。
以上案例：给 ul注册点击事件，然后利用事件对象的 target 来找到当前点击的 li，因为点击 li，事件会冒泡到 ul上， ul有注册事件，就会触发事件监听器。
**事件委托的作用**
我们只操作了一次 DOM ，提高了程序的性能。

#### 常用的鼠标事件
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661243441438-f32da7db-7a95-4e66-b9eb-f2a19fa0b72a.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=308&id=u8ca19753&margin=%5Bobject%20Object%5D&name=image.png&originHeight=385&originWidth=872&originalType=binary&ratio=1&rotation=0&showTitle=false&size=74553&status=done&style=none&taskId=u8f6cca4f-6689-4312-b427-0d2d1b54f38&title=&width=697.6)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661243513809-02ce94b1-241d-4653-818d-752ea01ccf74.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=298&id=u42e1613b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=373&originWidth=889&originalType=binary&ratio=1&rotation=0&showTitle=false&size=21896&status=done&style=none&taskId=ue07791c5-f0ef-4a71-8f0b-8b2d1868454&title=&width=711.2)
**如果使用addEventListener不需要加 on**
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661243588865-23c23ef2-1780-4c47-bbe1-f6618771e893.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=297&id=uec95089f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=371&originWidth=1085&originalType=binary&ratio=1&rotation=0&showTitle=false&size=136052&status=done&style=none&taskId=ud8e29c18-2db0-4358-b9e2-850f35e8921&title=&width=868)
#### 常用键盘事件
onkeypress和前面2个的区别是，它不识别功能键，比如左右箭头，shift 等。
三个事件的执行顺序是： keydown --  keypress  --- keyup
onkeydown和 onkeyup  不区分字母大小写，onkeypress区分字母大小写。
在我们实际开发中，我们更多的使用keydown和keyup， 它能识别所有的键（包括功能键）
Keypress 不识别功能键，但是keyCode属性能区分大小写，返回不同的ASCII值
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661243610844-9814e629-c3bb-4df4-9b1e-fa83ccf12645.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=178&id=u96cd3b12&margin=%5Bobject%20Object%5D&name=image.png&originHeight=222&originWidth=1096&originalType=binary&ratio=1&rotation=0&showTitle=false&size=83361&status=done&style=none&taskId=u5d96e2bd-7114-4434-b736-8611ebe84fc&title=&width=876.8)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661243655205-86f05009-e97f-462e-aa2e-8b3bffbda266.png#clientId=uf436af27-d756-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=98&id=ud506f2a7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=122&originWidth=1094&originalType=binary&ratio=1&rotation=0&showTitle=false&size=28272&status=done&style=none&taskId=u728f43d6-10e1-43ed-ae45-a3d1db74918&title=&width=875.2)

## BOM
### 概述
BOM(Browser Object Model) 浏览器对象模型,它独立于内容而与浏览器窗口进行交互的对象,其核心为window
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661339671722-7796e3f2-e3b3-4f6d-9828-b2ba2dee46d5.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=251&id=u831ebd83&margin=%5Bobject%20Object%5D&name=image.png&originHeight=314&originWidth=1226&originalType=binary&ratio=1&rotation=0&showTitle=false&size=32370&status=done&style=none&taskId=u74dbe612-9c18-4ca0-bf1c-cb0d16e7461&title=&width=980.8)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661339671757-3cc8356d-7213-4934-a851-a80cb197846a.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=301&id=u75776b9e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=376&originWidth=1059&originalType=binary&ratio=1&rotation=0&showTitle=false&size=44146&status=done&style=none&taskId=uf890a7b7-17bc-4755-b04f-e0c99a9167b&title=&width=847.2)
window对象是浏览器的顶级对象,它具有双重角色.是一个全局对象,调用时可以省略window
### Window对象常见事件
#### 窗口加载事件
window.onload = function(){}
或者
window.addEventListener('load',function(){});
当文档完全加载完成时会触发该事件,
有了window.onload就可以把JS代码写到页面元素上方,因为onload是等页面内容全部加载完毕再去处理函数
window.onload()只能写一个,以最后一个为准
window.addEventListener()没有数量限制

document.addEventListener('DOMCContentLoaded',function(){})
DOMContentLoaded事件触发时,仅当Dom加载完成,不包括样式表,图片,flash等等
当图片等数量很多时,为了使交互效果先实现,可以使用

#### 调整窗口大小事件
window.onresize = function(){}
window.addEventListener('resize',function(){});

当窗口的大小发生变化时就会触发
window.innerWidth为当前屏幕的宽度

### 定时器

#### 两种定时器
setTimeout()
setInterval()
#### window.setTimeout()
window.setTimeout(调用函数,[延迟的毫秒数]);
window可以忽略,调用函数直接写函数名
延迟毫秒数默认是0,单位为毫秒
定时器多的时候,所以经常为定时器赋值一个标识符

#### 停止setTimeout定时器
window.clearTimeout(timeoutId)
window可以忽略,参数为定时器的标识符

####  setInterval() 
window.setInterval(回调函数,[间隔的毫秒数]);

#### 停止setInterval()定时器
window.clearInterval(intervalID);
window可以忽略

#### JS执行队列

### location对象
window对象提供的location属性,用于获取和设置窗体的URL

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661341335380-df990ae5-e3c9-4ef9-b2e4-1a1767cf0044.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=280&id=uae546813&margin=%5Bobject%20Object%5D&name=image.png&originHeight=350&originWidth=984&originalType=binary&ratio=1&rotation=0&showTitle=false&size=105629&status=done&style=none&taskId=u0e096b9a-d9d4-4d41-bb8a-feca44715e8&title=&width=787.2)

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661341347127-ced1b6cb-e6a8-4a42-9b30-f38c5a96a22a.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=197&id=uc94db4cf&margin=%5Bobject%20Object%5D&name=image.png&originHeight=246&originWidth=1089&originalType=binary&ratio=1&rotation=0&showTitle=false&size=87963&status=done&style=none&taskId=uef24b6db-8185-4995-8021-215d38296ff&title=&width=871.2)

### navigator
navigator对象包含有关浏览器的信息,有很多属性,我们最常用的时userAgent,该属性可以返回由客户机发送服务器的user-agent头部的值

### history
window对象给我们提供了一个history对象,与浏览器历史记录进行交互.该对象包含用户(在浏览器窗口中)访问过的URL
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661341959904-00d5341a-9ce2-4579-9aaf-136a986498a7.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=178&id=u804f444f&margin=%5Bobject%20Object%5D&name=image.png&originHeight=223&originWidth=1062&originalType=binary&ratio=1&rotation=0&showTitle=false&size=51883&status=done&style=none&taskId=u4fca0b09-f926-4439-ac75-75275dc5b99&title=&width=849.6)

## PC端网页特效
### offset系列元素偏移量
可以动态的得到该元素的位置(偏移),大小等.

- 获得元素距离带有定位父元素的位置
- 获得元素自身的大小
- 返回的数值不带单位

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661342366523-b4097d12-1aaf-45e6-a80c-86b0c7ffd1c3.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=261&id=u0fca0f58&margin=%5Bobject%20Object%5D&name=image.png&originHeight=326&originWidth=998&originalType=binary&ratio=1&rotation=0&showTitle=false&size=140473&status=done&style=none&taskId=ucf18517f-a29b-4623-869e-0fa3eb3cf54&title=&width=798.4)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661342400296-e6d09e5a-1354-4caf-ae61-d7a470114fde.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=296&id=u46dbe720&margin=%5Bobject%20Object%5D&name=image.png&originHeight=370&originWidth=1205&originalType=binary&ratio=1&rotation=0&showTitle=false&size=41834&status=done&style=none&taskId=u83b5ed49-e3d9-45ee-a20e-dd4d53a9d45&title=&width=964)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661342417026-4aecb533-5140-4d42-9c1a-349f64c60cf2.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=306&id=u2232cc61&margin=%5Bobject%20Object%5D&name=image.png&originHeight=382&originWidth=715&originalType=binary&ratio=1&rotation=0&showTitle=false&size=40791&status=done&style=none&taskId=u92d20d80-f636-4be0-82b7-7a01a93a745&title=&width=572)
### client系列
可以获取元素边框大小,元素大小等
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661342471016-4c573211-ef46-4e8f-8fa0-66e427eea85c.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=222&id=u7bbdb56c&margin=%5Bobject%20Object%5D&name=image.png&originHeight=277&originWidth=1017&originalType=binary&ratio=1&rotation=0&showTitle=false&size=92915&status=done&style=none&taskId=u06caf738-a17b-46de-8011-33f4e4a124b&title=&width=813.6)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661342475119-478b2730-62ce-461d-848e-f6f31bbda42a.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=248&id=u9bf73be4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=310&originWidth=685&originalType=binary&ratio=1&rotation=0&showTitle=false&size=29604&status=done&style=none&taskId=ud2e5b4db-d286-4a78-9f15-e1f4b947b9d&title=&width=548)

### scroll系列
可以动态的获得该元素的大小,滚动距离等

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661342514809-9863236f-40d4-4ea2-8350-3a9308a2eef6.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=213&id=u5bf77028&margin=%5Bobject%20Object%5D&name=image.png&originHeight=266&originWidth=1002&originalType=binary&ratio=1&rotation=0&showTitle=false&size=100412&status=done&style=none&taskId=u44728f42-7665-44b1-93d0-c1f5cec47e1&title=&width=801.6)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661342524362-d7936d0e-25a9-48c7-84e7-53dfd9f31cb3.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=347&id=u8ec08945&margin=%5Bobject%20Object%5D&name=image.png&originHeight=434&originWidth=448&originalType=binary&ratio=1&rotation=0&showTitle=false&size=82931&status=done&style=none&taskId=u315f0f08-ecde-4577-ab88-933c6346e2c&title=&width=358.4)
如果浏览器的高（或宽）度不足以显示整个页面时，会自动出现滚动条。当滚动条向下滚动时，页面上面被隐藏掉的高度，我们就称为页面被卷去的头部。滚动条在滚动时会触发 onscroll 事件。
### 三大系列总结
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661342580118-a66533b4-743a-4121-9eba-5cfbde291a1b.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=468&id=u6d4c931b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=585&originWidth=1264&originalType=binary&ratio=1&rotation=0&showTitle=false&size=204968&status=done&style=none&taskId=ue950f9be-c501-4647-8f1f-3a33fb9152c&title=&width=1011.2)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661342613675-87d01a99-4b6e-47e4-9918-dc179a431a9a.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=228&id=u7cfeae28&margin=%5Bobject%20Object%5D&name=image.png&originHeight=285&originWidth=681&originalType=binary&ratio=1&rotation=0&showTitle=false&size=18345&status=done&style=none&taskId=u5bb92591-837c-40b2-b925-5d5f2721b7d&title=&width=544.8)
### 页面滚动距离
window.pageXOffset左右距离
window.pageYOffset上下距离

### mouseenter和mouseover区别
mouseenter和mouseover都是鼠标移动到元素上触发
mouseover 鼠标经过自身盒子和经过子盒子都触发,mouseenter只有经过自身盒子触发
因为mouseenter不会冒泡
与mouseleave鼠标离开也不会冒泡

### 动画函数封装
### 常用动画案例

## 本地存储
1、数据存储在用户浏览器中
2、设置、读取方便、甚至页面刷新不丢失数据
3、容量较大，sessionStorage约5M、localStorage约20M
4、只能存储字符串，可以将对象JSON.stringify() 编码后存储
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661343357781-23765ebf-e12b-473b-9ed5-47aa55040c5b.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=525&id=u20e82593&margin=%5Bobject%20Object%5D&name=image.png&originHeight=656&originWidth=1030&originalType=binary&ratio=1&rotation=0&showTitle=false&size=42669&status=done&style=none&taskId=u95bfbb63-4df5-4a10-b001-6b4cea076ba&title=&width=824)![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1661343362832-e1c3fd03-6465-45a8-b53b-6ed03066fd8d.png#clientId=u7217480d-edef-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=542&id=u936aa0a4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=677&originWidth=1048&originalType=binary&ratio=1&rotation=0&showTitle=false&size=43408&status=done&style=none&taskId=u787ad509-2f6e-4176-af15-44bad10ff6d&title=&width=838.4)
