# Spring Boot 日志文件配置

默认情况下，SpringBoot 只在控制台输出日志，可以通过配置项输出到文件

### 1、使用 application.properties 配置日志文件

    # 日志输出文件路径
    logging.file.path=/Users/leon1942/Documents/temp/springboot.log
    # 日志级别 debug->info->warn->error,
    # 默认级别为info
    # 如果设置了debug=true时，会自动降级到debug级别
    # ROTT代表默认全局设置
    logging.level.ROOT=INFO
    # 对单独的包设置输出级别
    logging.level.org.springframework=ERROR
    logging.level.org.apache=ERROR


### 2、使用 logback.xml 配置日志文件
在 resources 目录下创建 logback.xml，此时注释掉 application.properties 相应的日志配置项即可

参考模板

    <?xml version="1.0" encoding="UTF-8"?>
    <configuration debug="false" scan="true" scanPeriod="30 second">
    
        <property name="PROJECT" value="myproject" />
        <property name="ROOT" value="logs/${PROJECT}/" />
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




