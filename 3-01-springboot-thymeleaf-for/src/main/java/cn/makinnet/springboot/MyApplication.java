package cn.makinnet.springboot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import javax.swing.*;



@SpringBootApplication
//启动时加载 config.properties
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
