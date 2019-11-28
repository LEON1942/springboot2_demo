# Spring Boot 多环境变量配置与多环境日志输出配置

SpringBoot 支持 .properties 与 .yml 的方式配置项目，其中.yml 支持多环境变量配置。

为了更好地区分开发环境与线上环境的配置差异，这里讲述如何使用.yml进行环境配置.

### 1、不同环境的 SpringBoot 配置

将 application.properties 修改为 application.yml

在resources目录下新建 application.yml 文件, 同时把 application.properties 从项目中移除。

application.yml 中存放通用的配置信息，例如：

    spring:
      profiles:
        active: dev
      http:
        encoding:
          charset: UTF-8
    
      thymeleaf:
        encoding: UTF-8
    
      mvc:
        date-format: yyyy-MM-dd
      jackson:
        date-format: yyyy-MM-dd HH:mm:ss SSS
        time-zone: GMT+8

另外添加其他的环境配置文件，例如这里添加两个环境变量配置项：

用于测试环境的dev：application-dev.yml
    
    debug: true
    server:
      port: 8086
      servlet:
        context-path: /
    spring:
      thymeleaf:
        cache: false
      datasource:
        username: root
        password: root
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/test
    
    
用于线上环境的prd：application-prd.yml

    debug: false
    server:
      port: 80
      servlet:
        context-path: /
        
    spring:
      thymeleaf:
        cache: true
    
      datasource:
        username: dbManger
        password: passwordOfManager
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://0.0.0.0:3310/production
    
通过设置主文件 application.yml 中的 spring.profiles.active=dev 来指定使用的环境配置

### 2、不同环境的日志文件配置

使用 logback.xml 的方式配置日志文件时，SpringBoot不支持多环境日志文件输出配置，
需要将 logback.xml 修改为 logback-spring.xml,同时将 logback.xml 从项目中移除。

这里只是简单演示了不同环境的配置（仅修改了日志文件的存放目录）：

logback-spring.xml 
    
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration debug="false" scan="true" scanPeriod="30 second">
        
        <!--
            logback.xml 无法做到环境动态切换，
            1、为了区分不同环境的日志环境配置，需要更改默认的logback.xml 为 logback-spring.xml
            当SpringBoot检测到 logback-spring.xml 文件存在时，才会启用日志的环境配置功能
            2、在需要切换的环境上增加 springProfile 属性
        -->
        <property name="PROJECT" value="myproject" />
        <springProfile name="dev">
            <property name="ROOT" value="logs/${PROJECT}-dev/" />
        </springProfile>
        <springProfile name="prd">
            <property name="ROOT" value="logs/${PROJECT}-prd/" />
        </springProfile>
    
    
        <!--<property name="ROOT" value="logs/${PROJECT}/" />-->
        <property name="FILESIZE" value="50MB" />
        <property name="MAXHISTORY" value="100" />
        <timestamp key="DATETIME" datePattern="yyyy-MM-dd HH:mm:ss" />
        <!-- 控制台打印 -->
        <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            <encoder charset="utf-8">
                <pattern>[%-5level] %d{${DATETIME}} [%thread] %logger{36} - %m%n
                </pattern>
            </encoder>
        </appender>
        <!-- ERROR 输入到文件，按日期和文件大小 -->
        <appender name="ERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <encoder charset="utf-8">
                <pattern>[%-5level] %d{${DATETIME}} [%thread] %logger{36} - %m%n
                </pattern>
            </encoder>
            <filter class="ch.qos.logback.classic.filter.LevelFilter">
                <level>ERROR</level>
                <onMatch>ACCEPT</onMatch>
                <onMismatch>DENY</onMismatch>
            </filter>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>${ROOT}%d/error.%i.log</fileNamePattern>
                <maxHistory>${MAXHISTORY}</maxHistory>
                <timeBasedFileNamingAndTriggeringPolicy
                        class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                    <maxFileSize>${FILESIZE}</maxFileSize>
                </timeBasedFileNamingAndTriggeringPolicy>
            </rollingPolicy>
        </appender>
    
        <!-- WARN 输入到文件，按日期和文件大小 -->
        <appender name="WARN" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <encoder charset="utf-8">
                <pattern>[%-5level] %d{${DATETIME}} [%thread] %logger{36} - %m%n
                </pattern>
            </encoder>
            <filter class="ch.qos.logback.classic.filter.LevelFilter">
                <level>WARN</level>
                <onMatch>ACCEPT</onMatch>
                <onMismatch>DENY</onMismatch>
            </filter>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>${ROOT}%d/warn.%i.log</fileNamePattern>
                <maxHistory>${MAXHISTORY}</maxHistory>
                <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                    <maxFileSize>${FILESIZE}</maxFileSize>
                </timeBasedFileNamingAndTriggeringPolicy>
            </rollingPolicy>
        </appender>
    
        <!-- INFO 输入到文件，按日期和文件大小 -->
        <appender name="INFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <encoder charset="utf-8">
                <pattern>[%-5level] %d{${DATETIME}} [%thread] %logger{36} - %m%n
                </pattern>
            </encoder>
            <filter class="ch.qos.logback.classic.filter.LevelFilter">
                <level>INFO</level>
                <onMatch>ACCEPT</onMatch>
                <onMismatch>DENY</onMismatch>
            </filter>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>${ROOT}%d/info.%i.log</fileNamePattern>
                <maxHistory>${MAXHISTORY}</maxHistory>
                <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                    <maxFileSize>${FILESIZE}</maxFileSize>
                </timeBasedFileNamingAndTriggeringPolicy>
            </rollingPolicy>
        </appender>
        <!-- DEBUG 输入到文件，按日期和文件大小 -->
        <appender name="DEBUG" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <encoder charset="utf-8">
                <pattern>[%-5level] %d{${DATETIME}} [%thread] %logger{36} - %m%n
                </pattern>
            </encoder>
            <filter class="ch.qos.logback.classic.filter.LevelFilter">
                <level>DEBUG</level>
                <onMatch>ACCEPT</onMatch>
                <onMismatch>DENY</onMismatch>
            </filter>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>${ROOT}%d/debug.%i.log</fileNamePattern>
                <maxHistory>${MAXHISTORY}</maxHistory>
                <timeBasedFileNamingAndTriggeringPolicy
                        class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                    <maxFileSize>${FILESIZE}</maxFileSize>
                </timeBasedFileNamingAndTriggeringPolicy>
            </rollingPolicy>
        </appender>
        <!-- TRACE 输入到文件，按日期和文件大小 -->
        <appender name="TRACE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <encoder charset="utf-8">
                <pattern>[%-5level] %d{${DATETIME}} [%thread] %logger{36} - %m%n
                </pattern>
            </encoder>
            <filter class="ch.qos.logback.classic.filter.LevelFilter">
                <level>TRACE</level>
                <onMatch>ACCEPT</onMatch>
                <onMismatch>DENY</onMismatch>
            </filter>
            <rollingPolicy
                    class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>${ROOT}%d/trace.%i.log</fileNamePattern>
                <maxHistory>${MAXHISTORY}</maxHistory>
                <timeBasedFileNamingAndTriggeringPolicy
                        class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                    <maxFileSize>${FILESIZE}</maxFileSize>
                </timeBasedFileNamingAndTriggeringPolicy>
            </rollingPolicy>
        </appender>
    
        <!-- SQL相关日志输出-->
        <logger name="org.apache.ibatis" level="INFO" additivity="false" />
        <logger name="org.mybatis.spring" level="INFO" additivity="false" />
        <logger name="com.github.miemiedev.mybatis.paginator" level="INFO" additivity="false" />
    
        <!-- Logger 根目录 -->
        <root level="DEBUG">
            <appender-ref ref="STDOUT" />
            <appender-ref ref="DEBUG" />
            <appender-ref ref="ERROR" />
            <appender-ref ref="WARN" />
            <appender-ref ref="INFO" />
            <appender-ref ref="TRACE" />
        </root>
    </configuration>
    
上述文件中，通过配置 springProfile 节点来区分不同环境的日志文件配置（springProfile的名称需与application.yml 中的定义一致）

    <springProfile name="dev">
         <property name="ROOT" value="logs/${PROJECT}-dev/" />
    </springProfile>
    <springProfile name="prd">
         <property name="ROOT" value="logs/${PROJECT}-prd/" />
    </springProfile>   

### 3、在 SpringBoot 中使用自定义环境变量配置

SpringBoot 支持自定义环境变量，这里演示通过 config.properties 演示具体使用方式

在resources目录下新建一个配置文件 config.properties

config.properties:

    # 自定义属性
    app.name=SpringBoot2学习样例
    app.version=1.0.0
    app.description=这是SpringBoot2的学习样例的小DEMO
    app.page-size=20
    app.show-advert=true
    app.website=http://www.makinnet.cn

为了让 SpringBoot 可以解析这些资源，需要在程序启动类中对配置文件进行声明。

MyApplication启动文件:

    @SpringBootApplication
    //启动时加载 config.properties
    @PropertySource("classpath:config.properties")
    public class MyApplication {   
        public static void main(String[] args) {
            SpringApplication.run(MyApplication.class, args);         
        }
    }

这样配置完成后，就可以通过IOC容器来使用自定义环境变量，例如在Controller中使用

HelloController:

    @Controller
    public class HelloController {
    
        @Value("${app.name}")
        private String appName;
    
        @Value("${app.website}")
        private String website;
    
        @Value("${app.page-size}")
        private Integer pageSize;
    }
    
除此之外，更建议的方式是通过配置 Bean 实体类来使用这些自定义变量。

这里我们在项目中定义一个AppConfig.java类来存放这些数据    
    
AppConfig.java:

    @Component // 这个注解表示为组件类，SpringBoot启动时会加载
    @ConfigurationProperties(prefix = "app") //将 app 开头的配置项自动赋值给bean属性
    public class AppConfig {
    
        private String name;
        private String version;
        private String description;
        private Integer pageSize;
        private Boolean showAdvert;
        private String website;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getVersion() {
            return version;
        }
    
        public void setVersion(String version) {
            this.version = version;
        }
    
        public String getDescription() {
            return description;
        }
    
        public void setDescription(String description) {
            this.description = description;
        }
    
        public Integer getPageSize() {
            return pageSize;
        }
    
        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }
    
        public Boolean getShowAdvert() {
            return showAdvert;
        }
    
        public void setShowAdvert(Boolean showAdvert) {
            this.showAdvert = showAdvert;
        }
    
        public String getWebsite() {
            return website;
        }
    
        public void setWebsite(String website) {
            this.website = website;
        }
    }    
    
这样我们就可以通过IOC自动注入到实体类中了。

    @Controller
    public class HelloController {
        
        //@Autowired
        @Resource
        private AppConfig appConfig;
        
    }
  
    
    