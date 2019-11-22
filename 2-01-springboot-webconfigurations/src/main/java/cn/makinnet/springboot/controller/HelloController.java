package cn.makinnet.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class HelloController {

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
