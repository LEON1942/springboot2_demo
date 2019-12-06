package cn.makinnet.springboot.controller;

import cn.makinnet.springboot.AppConfig;
import cn.makinnet.springboot.entity.Department;
import cn.makinnet.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class HelloController {

    @Autowired
    private AppConfig appConfig;

    private List<User> users = new ArrayList<User>();
    private List<Department> depts = new ArrayList<Department>();

    public HelloController(){
        users.add(new User(7001, "LEON", "MANAGER", 7002, "1990-07-23", 8403.00, 8240.0f, 10, "ACCOUNTING"));
        users.add(new User(7002, "JOHN", "PRESIDENT", null, "1990-07-23", 29232.00, null, 10, "ACCOUNTING"));
        users.add(new User(7003, "SMITH", "CLARK", 7002, "1990-07-23", 4454.00, null, 10, "ACCOUNTING"));
        users.add(new User(7004, "JANY", "CLARK", 7007, "1990-07-23", 2437.00, null, 20, "RESEARCH"));
        users.add(new User(7005, "Alia", "ANALYST", 7007, "1990-07-23", 4483.00, null, 20, "RESEARCH"));
        users.add(new User(7006, "SNOW", "CLARK", 7007, "1990-07-23", 4469.00, null, 20, "RESEARCH"));
        users.add(new User(7007, "SAM", "MANAGER", null, "1990-07-23", 6476.00, null, 20, "RESEARCH"));
        users.add(new User(7008, "DANNY", "CLARK", 7007, "1990-07-23", 7303.00, null, 20, "RESEARCH"));
        users.add(new User(7009, "AMY", "MANAGER", 7007, "1990-07-23", 7473.00, null, 20, "RESEARCH"));
        users.add(new User(7010, "ALICE", "ANALYST", 7015, "1990-07-23", 8203.00, null, 30, "SALES"));
        users.add(new User(7011, "DRAGON", "CLARK", 7015, "1990-07-23", 8903.00, null, 30, "SALES"));
        users.add(new User(7012, "CHANNEL", "CLARK", 7015, "1990-07-23", 9245.60, null, 30, "SALES"));
        users.add(new User(7013, "WEDDING", "SALESMAN", 7015, "1990-07-23", 7649.40, null, 30, "SALES"));
        users.add(new User(7014, "MARTIN", "SALESMAN", 7015, "1990-07-23", 5432.50, null, 30, "SALES"));
        users.add(new User(7015, "LORD", "MANAGER", null, "1990-07-23", 6403.00, null, 30, "SALES"));

        depts.add(new Department(10, "ACCOUNTING", "GUANG ZHOU"));
        depts.add(new Department(20, "RESEARCH", "SHANG HAI"));
        depts.add(new Department(30, "SALES", "SHEN ZHEN"));
        depts.add(new Department(40, "OPERATIONS", "BEI JING"));

    }

    @RequestMapping("/")
    public ModelAndView index(){

        Map params = new LinkedHashMap();
        params.put("key word", "all");
        params.put("deptno", 10);

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("params", params);
        mav.addObject("users", users);
        mav.addObject("depts", depts);
        mav.addObject("config", appConfig);

        return mav;
    }

    @RequestMapping("/date")
    @ResponseBody // 对象序列化输出
    public Date getDate(Date sDate){

        return sDate;
    }
}
