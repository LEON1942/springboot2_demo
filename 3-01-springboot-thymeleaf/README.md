# Spring Boot Thymeleaf 配置及语法

SpringBoot 支持 .properties 与 .yml 的方式配置项目，其中.yml 支持多环境变量配置。

### 1、在 pom.xml 中添加 Thymeleaf 相关依赖项

    <!-- Thymeleaf 核心组件 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    
    <!-- Thymeleaf 模板语法支持 -->
    <dependency>
        <groupId>nz.net.ultraq.thymeleaf</groupId>
        <artifactId>thymeleaf-layout-dialect</artifactId>
        <version>2.4.1</version>
    </dependency>

### 2、添加全局外部变量

在 resources 目录下添加 messages.properties 文件, 同时在启动器中声明加载

/src/main/resources/messages.properties

    # 自定义属性
    app.name=SpringBoot2学习样例-Thymeleaf
    app.version=1.0.0
    app.description=这是SpringBoot2的学习样例的小DEMO
    app.page-size=20
    app.show-advert=true
    app.website=http://www.makinnet.cn

启动类 /src/main/java/cn.makinnet.springboot/MyApplication.class

    
    @SpringBootApplication
    //启动时加载 messages.properties
    @PropertySource("classpath:messages.properties")
    public class MyApplication {
    
        public static void main(String[] args) {
            // SpringApplication.run(MyApplication.class, args);
            SpringApplication app = new SpringApplication(MyApplication.class);
            // 关闭Banner
            app.setBannerMode(Banner.Mode.OFF);
    
            app.run(args);
        }
    
    }

### 3、静态资源和页面模板

在  resources/static 存放静态资源文件

在 resources/templates 目录下添加页面模板

在thymeleaf的页面模板中，必须引入thymeleaf的命名空间:

    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org">
    ...
    </html>

为了使用 thymeleaf 的模板继承功能，同时需要引入layout的命名空间

    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org"
          xmlns:layout="http://www.ultraq.net.nz/web/thymeleaf/layout">
    ...
    </html>

### 4、向页面模板传递数据

    @RequestMapping("/")
    public ModelAndView index(){

        Map params = new LinkedHashMap();
        params.put("key word", "all");
        params.put("deptno", 10);

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("params", params);
        mav.addObject("users", users);
        mav.addObject("depts", depts);
        mav.addObject("config", appConfig);

        return mav;
    }

### 5、页面模板中获取数据

thymeleaf 支持以 #{变量名} 的方式获取全局变量；

而通过 controller 传递的局部变量 则用 ${变量名} 的方式获取.

例如在页面中获取 2 中的全局变量：

    <h1 class="m-0 text-dark" th:text="#{app.name}"></h1>

获取 视图 传进来的参数：
    
    <td th:text="${user.empno}"></td>
    或
    <td>[[${user.empno}]]</td>
    以上两种方式都是对 td 标签的 text 赋值
    

### 6、页面组件定义及引入

在 thymeleaf 模板中我们可以对一些常用的页面组件进行封装，在需要的时候在页面中导入即可.

resources/templates/components/_footer.html 页面组件的定义：

    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org" style="height: auto;">
    <head>
        <meta charset="UTF-8">
        <title>Thymeleaf页面模板组件</title>
    </head>
    <body>
    
    <!-- Control Sidebar -->
    <footer class="main-footer" th:fragment="footer">
        <strong>Copyright © 2014-2019 <a href="http://adminlte.io">AdminLTE.io</a>.</strong>
        All rights reserved.
        <div class="float-right d-none d-sm-inline-block">
            <b>Version</b> 3.0.1
        </div>
    </footer>
    <!-- /.control-sidebar -->
    
    </body>
    </html>
   
在 templates/base.html 中引用组件:
    
    <!-- Main Footer -->
    <div th:insert="components/_footer::footer"></div>
    
### 7、页面模板的定义及内容重载

templates/base.html 模板定义：
    
    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org"
          xmlns:layout="http://www.ultraq.net.nz/web/thymeleaf/layout" style="height: auto;">
    <head>
    <meta ...>
    <link ...>
    <title layout:fragment="title">AdminLTE 3 | Dashboard 3</title>
    <body>
    ...
    
    <div class="container-fluid" layout:fragment="content-header">
    ...
    </div>
    ...
    <div class="container-fluid" layout:fragment="content">
    ...
    </div>
    ...
    <script...>
    </body>
    </html>
    
上述示例代码中定义了完整的 html 格式代码，并定义了三个可重载的模块：

layout:fragment="title"

layout:fragment="content-header"

layout:fragment="content"

我们在 index.html 中继承 base.html 模板, 并重载 title 模块的内容

继承方式：通过定义 layout:decorator="模板名称(文件名不带扩展名)"

    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org"
          xmlns:layout="http://www.ultraq.net.nz/web/thymeleaf/layout"
          layout:decorator="base"><!-- layout文件路径-->
          
    <title layout:fragment="title">工作台 | 互联智控</title>

    ...
    </html>

此时，index.html 不需再引入base.html中已有的css, js文件及body等html结构声明

### 8、thymeleaf 语法

① for 循环
    
    <tr th:each="user,stat:${users}"></tr>
    
    users 为迭代列表对象
    user 为循环对象载体
    stat 是循环索引对象, 对象结构为：{index = 0, count = 1, size = 15, current = item }
    
② if / unless 判断
    
    <td th:if="${user.job=='MANAGER'}">部门经理</td>
    <td th:unless="${user.job=='MANAGER'}">其他职位</td>
    
    if 代表满足判断条件时生效，unless 表示判断条件不满足时生效

③ switch 分支
    
    <td th:switch="${user.dname}">
        <span th:case="RESEARCH">研发部</span>
        <span th:case="ACCOUNTING">会计部</span>
        <span th:case="SALES">销售部</span>
        <span th:case="*">其他部门</span>
    </td>
    
    th:swith 需配合 th:case 使用， case="*" 表示其他条件

④ string格式化 

    <td>[[${#strings.toLowerCase(user.ename)}]]</td>
    
    #strings 用法与 Java 中的 String 一致

⑤ 日期格式化

    <td th:text="${#dates.format(user.hiredate, 'yyyy年MM月dd日')}"></td>
    
    #dates 用法与 Java 中的 Date 一致
    
⑥ 货币格式化

    <td>[[${#numbers.formatCurrency(user.sal)}]]</td>
    
    使用 #numbers.formatCurrency 进行本地货币格式化输出
    
⑦ 三目运算符

    <td th:style="${user.sal>5000?'color:red;':''}">[[${#numbers.formatCurrency(user.sal)}]]</td>
    
    用法与 Java 中的三目运算符用法一致， 即: 判断条件?条件满足时:条件不满足时
    
 ### 9 thymeleaf 内置对象
 
    ${param.keyword}
    相当于 request.getParameter("keyword");
    
    此外还有，${request}, ${session}, ${application}
    
    但不推荐使用(request, session, application使用的是J2EE的原生对象, 
                      SpringBoot2.0以后的版本会逐渐弱化，改为自身的对象)
    
          
    




