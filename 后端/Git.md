![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1652256698512-f93cd8af-cf12-4723-bd7d-64cb965a1ca0.png#clientId=u8f3a67ad-5c2c-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=251&id=u9a912bb0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=343&originWidth=629&originalType=binary&ratio=1&rotation=0&showTitle=false&size=33910&status=done&style=none&taskId=u3f2201d3-3a44-4de0-aa2a-8cdd441f1ee&title=&width=461.20001220703125)
# Git
## 基本操作
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1652257911897-9fd84f0e-871a-4012-a00b-87ee6b65ea3d.png#clientId=u8f3a67ad-5c2c-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=260&id=uf625409d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=247&originWidth=532&originalType=binary&ratio=1&rotation=0&showTitle=false&size=38184&status=done&style=none&taskId=u494b1d93-6647-43ad-805e-4dfa2ed803e&title=&width=560.6000366210938)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1652261025016-a5a0e2ea-e9da-4d6d-b0c8-f6e3f1fe73c3.png#clientId=u8f3a67ad-5c2c-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=215&id=u0bb37c5c&margin=%5Bobject%20Object%5D&name=image.png&originHeight=269&originWidth=786&originalType=binary&ratio=1&rotation=0&showTitle=false&size=41313&status=done&style=none&taskId=u2a0303f6-7ec5-47d6-a5ac-8fe23a01905&title=&width=628.8)

- 冲突合并
   -  编辑有冲突的文件，删除特殊符号，决定要使用的内容 特殊符号：
   - <<<<<<< HEAD 当前分支的代码 ======= 合并过来的代码 >>>>>>> hot-fix  
   - 当新分支中的内容和老分修改的地方相同，系统不知道保存哪一个文件时发生
   - 需要手动确认
   - 进入文件进行修改的确认
   - 重新提交文件（注意：此时使用 git commit 命令时不能带文件名）

![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1656999361691-4cfcb60a-ae6f-44e8-a395-b6bd81b5a1c6.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=194&id=udefc62fa&margin=%5Bobject%20Object%5D&name=image.png&originHeight=242&originWidth=780&originalType=binary&ratio=1&rotation=0&showTitle=false&size=15433&status=done&style=none&taskId=ue503f062-bc8f-41d1-97b4-505f313a3cd&title=&width=624)
 master、hot-fix 其实都是指向具体版本记录的指针。当前所在的分支，其实是由 HEAD 决定的。所以创建分支的本质就是多创建一个指针。 HEAD 如果指向 master，那么我们现在就在 master 分支上。 HEAD 如果执行 hotfix，那么我们现在就在 hotfix 分支上。  
## 团队协作
团队内协作
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1652267745336-19b4d386-c289-4e70-b073-b3c4894658b2.png#clientId=u8f3a67ad-5c2c-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=291&id=u824d6c61&margin=%5Bobject%20Object%5D&name=image.png&originHeight=364&originWidth=742&originalType=binary&ratio=1&rotation=0&showTitle=false&size=117368&status=done&style=none&taskId=u473b6e22-92c4-4456-b467-1538b7d4fa6&title=&width=593.6) 
跨团队协作
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1652267751831-518a4df0-571f-4957-9822-0dd9fd648e9a.png#clientId=u8f3a67ad-5c2c-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=295&id=u565bb69e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=369&originWidth=801&originalType=binary&ratio=1&rotation=0&showTitle=false&size=167161&status=done&style=none&taskId=u6217fd68-8a61-4376-9165-96cd44f7701&title=&width=640.8)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1652268721412-cdf9bab4-816e-4e3f-bc91-4c568fcc6232.png#clientId=u8f3a67ad-5c2c-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=223&id=Gi2cW&margin=%5Bobject%20Object%5D&name=image.png&originHeight=279&originWidth=870&originalType=binary&ratio=1&rotation=0&showTitle=false&size=66969&status=done&style=none&taskId=u76e65726-28a5-4094-b8b1-701c1c13aee&title=&width=696)
# GitHub
## ssh免密登录

- cd  进入当前用户的家目录  cd
- rm -rvf .ssh 删除.ssh目录
- $ ssh-keygen -t rsa -C atguiguyueyue@aliyun.com  创建.ssh密匙目录
- 将.ssh文件中id_rsa.pub文件中的内容复制到github中的ssh and gpg keys（ 点击用户头像→Settings→SSH and GPG keys  ）中
- ![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657000642316-3438fa8d-a9ae-4c66-bc66-b63a3fa50f44.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=395&id=ub873adb4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=494&originWidth=879&originalType=binary&ratio=1&rotation=0&showTitle=false&size=165309&status=done&style=none&taskId=ua13b1964-178e-4b35-8ec6-d84e2846a73&title=&width=703.2)
-  接下来再往远程仓库 push 东西的时候使用 SSH 连接就不需要登录了。  
# IDEA集成Git
## .ignore文件
 创建忽略规则文件 xxxx.ignore（前缀名随便起，建议是 git.ignore）  
 这个文件的存放位置原则上在哪里都可以，为了便于让~/.gitconfig 文件引用，建议也放在用 户家目录下  
git.ignore文件模板
```java
# Compiled class file
*.class
 尚硅谷技术课程系列之 GIT 
—————————————————————————————
46
更多 Java –大数据 –前端 –python 人工智能资料下载，可访问百度：尚硅谷官网
# Log file
*.log
# BlueJ files
*.ctxt
# Mobile Tools for Java (J2ME)
.mtj.tmp/
# Package Files #
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar
# virtual machine crash logs, see 
http://www.java.com/en/download/help/error_hotspot.xml
hs_err_pid*
.classpath
.project
.settings
target
.idea
*.iml
```

## 使用
**1.定位Git程序**
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657001614234-66b60e0a-8f83-4414-8f48-635280a568d6.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=520&id=u01d2b238&margin=%5Bobject%20Object%5D&name=image.png&originHeight=650&originWidth=863&originalType=binary&ratio=1&rotation=0&showTitle=false&size=161811&status=done&style=none&taskId=uf7e4d7d3-2ae0-4706-9e20-c99cfefbc87&title=&width=690.4)
**2.初始化本地仓库**
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657001716185-58c18d7f-34db-4845-8fdb-e08a56f834dc.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=398&id=ud1f756c0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=497&originWidth=873&originalType=binary&ratio=1&rotation=0&showTitle=false&size=170335&status=done&style=none&taskId=u47278519-17fe-4d73-be84-5a813d28f81&title=&width=698.4)
**3.添加到暂存区 **
 右键点击项目选择 Git -> Add 将项目添加到暂存区  
**4.添加到本地库**
**5.切换版本  **
在 IDEA 的左下角，点击 Version Control，然后点击 Log 查看版本  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657001810018-dd12c49c-8167-43c9-9883-c3566b3d7b45.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=218&id=u686c16c0&margin=%5Bobject%20Object%5D&name=image.png&originHeight=272&originWidth=888&originalType=binary&ratio=1&rotation=0&showTitle=false&size=53597&status=done&style=none&taskId=u115a1560-9e73-4daa-9e42-924b796915c&title=&width=710.4)
 右键选择要切换的版本，然后在菜单里点击 Checkout Revision。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657001841392-ec811bc7-c357-4f52-87bc-82ec6cda7b94.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=377&id=udf1a7db8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=471&originWidth=588&originalType=binary&ratio=1&rotation=0&showTitle=false&size=105385&status=done&style=none&taskId=u5c615402-ab75-4b2d-bf52-17cfe6e1c92&title=&width=470.4)
**6.创建分支**
 选择 Git，在 Repository 里面，点击 Branches 按钮。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657001958428-0b1ba251-bfc2-41e9-a976-7d55d74d7b82.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=219&id=ub6155921&margin=%5Bobject%20Object%5D&name=image.png&originHeight=274&originWidth=873&originalType=binary&ratio=1&rotation=0&showTitle=false&size=83996&status=done&style=none&taskId=u0ad6b5a4-6546-45d2-a2bb-f08b40c1d35&title=&width=698.4)
**7.切换分支**
 在 IDEA 窗口的右下角，切换到 master 分支。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657002004994-1953f403-34dc-428a-8e26-84351dff6de5.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=288&id=u3e1bd16b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=360&originWidth=695&originalType=binary&ratio=1&rotation=0&showTitle=false&size=77213&status=done&style=none&taskId=u951c6c4d-8216-443f-b9da-3af102995fa&title=&width=556)
**8.合并分支**
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657002073528-fd5e8633-709b-4950-8221-d4473e7d73be.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=315&id=u08718b3a&margin=%5Bobject%20Object%5D&name=image.png&originHeight=394&originWidth=683&originalType=binary&ratio=1&rotation=0&showTitle=false&size=93746&status=done&style=none&taskId=u7d88108a-7fa6-4087-92da-ff0be0ffc41&title=&width=546.4)
**9.冲突合并**
 代码冲突解决，自动提交本地库。  
## Github
设置github账号
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657002237847-aac0dec1-ca3e-4950-a16e-1c8faf055173.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=549&id=u8fdac3ee&margin=%5Bobject%20Object%5D&name=image.png&originHeight=686&originWidth=875&originalType=binary&ratio=1&rotation=0&showTitle=false&size=146364&status=done&style=none&taskId=ucfae6154-8f05-4138-b04b-8a634f8e447&title=&width=700)
 如果出现 401 等情况连接不上的，是因为网络原因，可以使用以下方式连接：  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657002285470-6f564c2a-e128-4629-9213-375befb2543f.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=244&id=ubfce5afe&margin=%5Bobject%20Object%5D&name=image.png&originHeight=305&originWidth=867&originalType=binary&ratio=1&rotation=0&showTitle=false&size=60907&status=done&style=none&taskId=u56a38e74-7b90-490e-8d66-af9a13ff077&title=&width=693.6)
 然后去 GitHub 账户上设置 token。  
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657002313717-213f1783-9ba3-48a1-9511-825efd1a74cc.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=622&id=u3984c0b6&margin=%5Bobject%20Object%5D&name=image.png&originHeight=777&originWidth=845&originalType=binary&ratio=1&rotation=0&showTitle=false&size=147816&status=done&style=none&taskId=uae513a59-ac8c-4cc4-b26c-84ecfa2cb88&title=&width=676)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657002328984-d5e67832-f0a9-4c79-8a95-78077c44f126.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=373&id=uacba319d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=466&originWidth=879&originalType=binary&ratio=1&rotation=0&showTitle=false&size=85663&status=done&style=none&taskId=u3d52b925-4a51-457f-adaa-647a2c48c5b&title=&width=703.2)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657002420677-9b66a51a-b8c2-4470-a92e-86e439f08a43.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=539&id=uf997a3fb&margin=%5Bobject%20Object%5D&name=image.png&originHeight=674&originWidth=869&originalType=binary&ratio=1&rotation=0&showTitle=false&size=185683&status=done&style=none&taskId=u260a3af1-63b0-4481-ba37-6afbee2a5cf&title=&width=695.2)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657002442370-04a5e58a-04c8-47d5-8895-d0571e2be484.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=284&id=u076b8efa&margin=%5Bobject%20Object%5D&name=image.png&originHeight=355&originWidth=874&originalType=binary&ratio=1&rotation=0&showTitle=false&size=67859&status=done&style=none&taskId=u23f7b6a3-140f-41e5-ac52-2249d4e95c8&title=&width=699.2)
分享工程到GitHub
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657002705976-0b470f6e-a85e-4ad7-aefe-909343cacd35.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=534&id=u4e801f29&margin=%5Bobject%20Object%5D&name=image.png&originHeight=668&originWidth=870&originalType=binary&ratio=1&rotation=0&showTitle=false&size=143440&status=done&style=none&taskId=u20aa5a46-d638-473c-a5ca-e98f184f4d3&title=&width=696)
clone克隆远程库到本地
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657002896190-4dbb8061-0d74-46ee-b4e0-1a50aa1d3f22.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=223&id=u8b3a81ad&margin=%5Bobject%20Object%5D&name=image.png&originHeight=279&originWidth=876&originalType=binary&ratio=1&rotation=0&showTitle=false&size=83285&status=done&style=none&taskId=u095696be-b927-437b-8c00-fa6da5d2997&title=&width=700.8)
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1657008275802-17c6f5cf-d02a-43a7-8f29-bf123edbe817.png#clientId=uf1649eea-e7f1-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=510&id=u237c499b&margin=%5Bobject%20Object%5D&name=image.png&originHeight=637&originWidth=731&originalType=binary&ratio=1&rotation=0&showTitle=false&size=40411&status=done&style=none&taskId=u59885eed-89e7-4d6a-8f24-d95e8fdd26f&title=&width=584.8)

# GitHub使用技巧
按下“T”快速进入文件目录
按下“L”快速跳转到某一行
![20190801215746807.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1652409890881-9feb2680-f503-49b7-973a-cb185aa24f1e.png#clientId=u53256788-e7f7-4&crop=0&crop=0&crop=1&crop=1&from=drop&id=u5918123d&margin=%5Bobject%20Object%5D&name=20190801215746807.png&originHeight=778&originWidth=863&originalType=binary&ratio=1&rotation=0&showTitle=false&size=128639&status=done&style=none&taskId=ued62f847-9fe6-4fa1-b1e8-e079c1b9a50&title=)
输入“。”在网页端查看源码
在网址之前输入gitpod.io/#/ 运行代码
![image.png](https://cdn.nlark.com/yuque/0/2022/png/27198522/1652409957941-d6721de6-df75-45a6-8a57-d2c8e4a34800.png#clientId=u53256788-e7f7-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=205&id=u126d8db4&margin=%5Bobject%20Object%5D&name=image.png&originHeight=256&originWidth=1230&originalType=binary&ratio=1&rotation=0&showTitle=false&size=153462&status=done&style=none&taskId=u5f80f450-de94-41cb-9996-9f659565b34&title=&width=984)
网址后面加/compare对比版本之间得代码
githubpages 部署代码
