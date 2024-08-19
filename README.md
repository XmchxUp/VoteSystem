# VoteSystem
> 由于技术水平有限，有许多小bug。完全是一个练手项目，是算得上我个人的第一个web全栈项目，希望以后技术提升，代码写的更好，更优雅。
> 没有太大的问题。已知的一个问题，就是和登陆相关的问题。由于提供了两种用户登陆机制(一套是基于MD5加密的前台用户登陆，一套是基于SpringSecurity的后台登陆)，所以导致了后端登陆时没有权限提示，前端必须也要登陆，并且是Admin账号。太菜了，没考虑清楚。

## 使用教程
> 需要注意Mysql版本不能超过5.7， 有BUG(关键字，[See here](https://github.com/XmchxUp/VoteSystem/issues/2))

第一步修改application.yml文件中的mysql数据库配置。

第二步在数据库中导入并执行vote.sql文件。

第三步在项目的根目录下运行mvn spring-boot:run。


### 技术栈

#### 后台

- Spring Boot
- Spring Data Jpa
- Spring Security
- Thymeleaf

#### 前端

- BootStrap
- AdminLTE v3.0

### 参考
- [Spring Boot + Spring Security + JWT + MySQL + React Full Stack](https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-1/)


### 效果图
![](./images/2.png)

![](./images/1.png)

![](./images/3.png)

![](./images/4.png)

![](./images/5.png)

![](./images/7.png)

![](./images/8.png)
