# 系统展示
# ![系统展示](http://m2m.micyc.com/1573107314307.jpg)
![系统展示](http://m2m.micyc.com/1573034650269.jpg)
![系统展示](http://m2m.micyc.com/1572846664960.jpg)

## 功能介绍

> 权限系统
> 功能模块：
用户管理 
内容管理 
名片管理 
接口密钥 
评论管理 
字典管理 
我的文件夹 
登录日志 
菜单管理 
参数设置 
角色管理 
子系统管理


## 前端地址:https://github.com/chenyongcan1688/cn.chenyongcan.authority-vue

## 单体应用:https://github.com/chenyongcan1688/cn.chenyongcan.authority


## 技术栈

springboot
springsecurity

## 数据库 docker 运行

``` bash
sudo docker run --name pwc-mysql -e MYSQL_ROOT_PASSWORD=qweasd -p 3306:3306 -d mysql

–name：给新创建的容器命名，此处命名为pwc-mysql

-e：配置信息，此处配置mysql的root用户的登陆密码

-p：端口映射，此处映射主机3306端口到容器pwc-mysql的3306端口

-d：成功启动容器后输出容器的完整ID

最后一个mysql指的是mysql镜像名字
```

