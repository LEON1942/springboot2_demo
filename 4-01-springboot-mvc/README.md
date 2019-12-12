# SpringBoot 与 MVC

Spring Boot 帮助我们简化了架构的依赖和配置过程，但在web开发层面仍然沿用SpringMVC开发的方式。
SpringMVC 中最重要的环节就是 Controller 控制器的开发。

### 1、常规配置，环境变量
pom.xml 相关配置

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.1.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>
    
    <dependencies>
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
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
    


### 2、SpringMVC + Thymeleaf 上下文取值
    
    

### 3、Ajax


### 4、文件上传


### 5、输入日期格式的转换


### 6、更换 tomcat 配置 -> Jetty
    
    
### 7、注册 Filter，日志打印


### 8、自动跳转 404，500


### 9、 启动时加载 Listener

    SpringBoot + Redis + Cookie 管理登录信息例子
    https://www.cnblogs.com/liuxiaoming123/p/7997509.html

    
 



