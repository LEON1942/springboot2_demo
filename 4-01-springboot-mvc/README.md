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

在 SpringBoot 中，可以通过 request 或 ModelAndView 对象向 thymeleaf 模板传递数据。

在 Spring 中，可以通过在 Controller 中设置 WebRequest 参数访问 request 对象， WebRequest 是对 request 对象的包装。
WebRequest 和 request 两者都是与J2EE容器强耦合，为了将来扩展需要，不推荐使用request对象传递参数，而使用ModelAndView对象来实现。

参数设置方式：
    
    // 向当前请求中放入对象，这种方式与WEB容器强耦合
    request.setAttribute(key, value) 
    
    // 向mav实例中放入对象，这种方式与WEB容器解耦合
    ModelAndView.addObject(key, value)
    
Thymeleaf 获取值:

    #{key} // #{} 代表获取全局对象
    ${key} // ${} 代表获取当前会话的局部对象

### 3、Ajax
    
    $.ajax({
         type: "POST",
         url: "/post/url",
         data: {username:$("#username").val(), content:$("#content").val()},
         dataType: "json",
         success: function(data){
             $.each(data.list, function(index, item){
                   ...
             });
         },
         error: function(e){
            ...
         }
     });

### 4、文件上传
网页具备文件上传的3个条件：
    
    1、post 提交
    2、具备 input[type=file] 组件
    3、设置表单的 enctype="multipart/form-data", 默认表单的enctype是 x-www-urlencoding
    
Controller 接收文件，需在入参中设置 MultipartFile 类型参数:

    // MultipartFile 是上传文件接口，对应了保存的临时文件
    // 参数名与前端file组件的name属性保持一致
    // @RequestParam("profile")MultipartFile photo 代表了photo参数对应于前端的 name=profile 的file
    @PostMapping("/register/check")
    public ModelAndView registUser(HttpServletRequest request, @RequestParam("profile") MultipartFile photo) throws IOException {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("xxx");

        if(null == photo){
            return mav;
        }
        ...
        
        if(checkPass) {
            // String uploadPath = "D:/java/Applications/springboot2_demo/4-01-springboot-mvc/src/main/resources/static/users/";
            // String filename = photo.getOriginalFilename();
            String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf("."));
            if(!suffix.equalsIgnoreCase(".JPG")){
                throw new RuntimeException("图片格式不支持！");
            }
            // Spring提供了一个文件操作类 FileCopyUtils
            FileCopyUtils.copy(photo.getInputStream(), new FileOutputStream(uploadPath+filename+suffix));
            ...
            mav.setViewName("redirect:/");
        }
        return mav;
    }

设置上传文件大小限制，当大小超出限制时，WEB容器会自动拦截请求，不会发送请求到Controller
在 application.yml 中加入 spring 的配置项：
    
    spring: 
      servlet:
        multipart:
          # 一个请求中总文件体积大小限制
          max-request-size: 25MB
          # 一个请求中单文件体积限制
          max-file-size: 2MB
          # 上传文件时WEB容器保存文件的临时目录
          location: d:/temp/


### 5、输入日期格式的转换


### 6、更换 tomcat 配置 -> Jetty
    
    
### 7、注册 Filter，日志打印


### 8、自动跳转 404，500
在 Springboot 中，对于常见的 Request 错误页面定义，可以通过在
templates 目录下添加 error 目录，然后添加对应错误码的 {错误码}.html文件的形式来实现。

例如常见的404和少见的500错误:

    templates/error/404.html
    templates/error/500.html


### 9、 启动时加载 Listener

自定义Filter：
    
    public class AccessRecorderFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }
    
        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            // do something
            ...    
        }
    
        @Override
        public void destroy() {
        }
    }

定义一个Filter注册类，通过 @Bean 注解使 SpringBoot 对其自动实例化到 IOC 容器：

    // 在入口类中注册Filter
    @Bean // @Bean 会将方法的返回对象在 SpringBoot 启动的时候放入IOC容器
    public FilterRegistrationBean filterRegiste(){
        FilterRegistrationBean regFilter = new FilterRegistrationBean();
        // 注册filter
        regFilter.setFilter(new AccessRecorderFilter());
        // 对所有路径拦截
        regFilter.addUrlPatterns("/*");
        // 设置过滤器名称
        regFilter.setName("AccessRecorder");
        // 设置排序, 如果系统中有多个过滤器，order就决定了哪个过滤器被先执行，越小的越靠前执行
        regFilter.setOrder(1);

        return regFilter;
    }

### 10、其他

    Springboot + sqlite 例子
    https://github.com/yodfz/springboot-sqlite

    SpringBoot + Redis + Cookie 管理登录信息例子
    https://www.cnblogs.com/liuxiaoming123/p/7997509.html

    
 




