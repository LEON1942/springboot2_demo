package cn.makinnet.springboot.controller;

import cn.makinnet.springboot.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
public class HelloController {

    @Value("${app.name}")
    private String appName;

    @Value("${app.website}")
    private String website;

    @Value("${app.page-size}")
    private Integer pageSize;

    // @Autowired
    @Resource
    private AppConfig appConfig;

    @RequestMapping("/")
    public String hello(){

        return "index";
    }

    @RequestMapping("/date")
    @ResponseBody // 对象序列化输出
    public Date getDate(Date sDate){

        return sDate;
    }
}
