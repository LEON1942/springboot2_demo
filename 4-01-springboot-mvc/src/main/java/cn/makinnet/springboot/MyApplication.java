package cn.makinnet.springboot;

import cn.makinnet.springboot.common.filter.AccessRecorderFilter;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.swing.*;



@SpringBootApplication
//启动时加载 messages.properties
@PropertySource("classpath:messages.properties")
public class MyApplication {


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


    public static void main(String[] args) {
        // SpringApplication.run(MyApplication.class, args);
        SpringApplication app = new SpringApplication(MyApplication.class);
        // 关闭Banner
        app.setBannerMode(Banner.Mode.OFF);

        app.run(args);
    }

}
