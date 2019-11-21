# 使用Maven手动配置 Spring Boot

## 1、pom.xml 中生命父节点，添加web依赖项 和 maven 插件

    <!-- Inherit defaults from Spring Boot -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.1.RELEASE</version>
    </parent>
    
    <!-- Add typical dependencies for a web application -->
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
    
    <!-- Package as an executable jar -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
    
## 2、 在resources目录下新建static目录、templates目录和application.properties文件

static目录用于存放静态资源文件；

templates目录用于存放前端页面模板文件；

application.properties文件用于写入Spring的配置项；

## 3、在main项目根目录中编写 SpringBoot 的启动类
    
    package cn.makinnet.springboot;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    
    
    @SpringBootApplication
    public class MyApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(MyApplication.class, args);
        }
    }
    
@SpringBootApplication 注解告诉 SpringBoot 这是启动入口类。

通过SpringApplication.run()方法启动项目。

项目启动时，SpringBoot 会自动扫描同级目录及子目录的Web组件，包括：

@Repository \ @Controller \ @Service \ @Component \ @Entity









    
