# Spring Boot 中常用的配置项

### 1、常用配置项

web常用配置项 | 默认值 | 说明
 
- | - | -
 
debug | false | 开启/关闭调试模式
 
server.port | 80 | 服务端口
 
server.servlet.context-path | / | 应用上下文

spring.http.encoding.charset | utf-8 | 默认字符编码

spring.thymeleaf.cache | true | 开启/关闭页面缓存

spring.mvc.date-format |  | 日期输入格式

spring.jackson.date-format |  | json输出的日期格式

spring.jackson.time-zone |  | 设置GMT时区

### 2、配置项说明

* 设置 spring.thymeleaf.cache=false

在 IntelliJ IDEA 中关闭 thymeleaf 的页面缓存时，若想可以动态修改页面，
需同时确保勾上了IDEA编译配置项中的 Build project automatically.

位置：
Preferences->Build, Execution, Deployment->Compiler. 


* spring.thymeleaf.encoding=UTF-8

设置 thymeleaf 页面渲染时的字符编码





