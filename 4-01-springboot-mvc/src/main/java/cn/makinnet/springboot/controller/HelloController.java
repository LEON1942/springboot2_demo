package cn.makinnet.springboot.controller;

import cn.makinnet.springboot.AppConfig;
import cn.makinnet.springboot.entity.Department;
import cn.makinnet.springboot.entity.Emoloyee;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HelloController {

    @Autowired
    private AppConfig appConfig;

    private List<Emoloyee> users = new ArrayList<Emoloyee>();
    private List<Department> depts = new ArrayList<Department>();

    private Emoloyee loginUser = null;

    public HelloController(){
        users.add(new Emoloyee(7001, "LEON", "MANAGER", 7002, "1990-07-23", 8403.00, 8240.0f, 10, "ACCOUNTING", "123", "leon@test.com"));
        users.add(new Emoloyee(7002, "JOHN", "PRESIDENT", null, "1990-07-23", 29232.00, null, 10, "ACCOUNTING", "123", "a@test.com"));
        users.add(new Emoloyee(7003, "Smith", "CLARK", 7002, "1990-07-23", 4454.00, null, 10, "ACCOUNTING", "123", "b@test.com"));
        users.add(new Emoloyee(7004, "JANY", "CLARK", 7007, "1990-07-23", 2437.00, null, 20, "RESEARCH", "123", "c@test.com"));
        users.add(new Emoloyee(7005, "Alia", "ANALYST", 7007, "1990-07-23", 4483.00, null, 20, "RESEARCH", "123", "d@test.com"));
        users.add(new Emoloyee(7006, "SNOW", "CLARK", 7007, "1990-07-23", 4469.00, null, 20, "RESEARCH", "123", "e@test.com"));
        users.add(new Emoloyee(7007, "SAM", "MANAGER", null, "1990-07-23", 6476.00, null, 20, "RESEARCH", "123", "f@test.com"));
        users.add(new Emoloyee(7008, "Danny", "CLARK", 7007, "1990-07-23", 7303.00, null, 20, "RESEARCH", "123", "g@test.com"));
        users.add(new Emoloyee(7009, "AMY", "MANAGER", 7007, "1990-07-23", 7473.00, null, 20, "RESEARCH", "123", "h@test.com"));
        users.add(new Emoloyee(7010, "ALICE", "ANALYST", 7015, "1990-07-23", 8203.00, null, 30, "SALES", "123", "i@test.com"));
        users.add(new Emoloyee(7011, "DRAGON", "CLARK", 7015, "1990-07-23", 8903.00, null, 30, "SALES", "123", "j@test.com"));
        users.add(new Emoloyee(7012, "Channel", "CLARK", 7015, "1990-07-23", 9245.60, null, 30, "SALES", "123", "k@test.com"));
        users.add(new Emoloyee(7013, "Wedding", "SALESMAN", 7015, "1990-07-23", 7649.40, null, 30, "SALES", "123", "l@test.com"));
        users.add(new Emoloyee(7014, "Martin", "SALESMAN", 7015, "1990-07-23", 5432.50, null, 30, "SALES", "123", "m@test.com"));
        users.add(new Emoloyee(7015, "LORD", "MANAGER", null, "1990-07-23", 6403.00, null, 30, "SALES", "123", "n@test.com"));

        depts.add(new Department(10, "ACCOUNTING", "GUANG ZHOU"));
        depts.add(new Department(20, "RESEARCH", "SHANG HAI"));
        depts.add(new Department(30, "SALES", "SHEN ZHEN"));
        depts.add(new Department(40, "OPERATIONS", "BEI JING"));

    }

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response){

        ModelAndView mav = new ModelAndView();
        String loginUserEmail = null;
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("loginUserEmail")){
                    loginUserEmail = cookie.getValue();
                }
            }
        }
        mav.addObject("email", loginUserEmail);

        if(request.getMethod().equalsIgnoreCase("POST")){
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember");

            for (Emoloyee user:users) {
                if(email.equals(user.getEmail()) && password.equals(user.getPassword())){
                    loginUser = user;
                    if(null!=remember && remember.equalsIgnoreCase("on")){
                        Cookie cookie=new Cookie("loginUserEmail",user.getEmail());
                        response.addCookie(cookie);
                    }else{
                        if(loginUserEmail!=null){
                            Cookie cookie=new Cookie("loginUserEmail",null);
                            cookie.setMaxAge(0);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                    }
                    mav.setViewName("redirect:/");
                    return mav;
                }
            }
        }
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(){
        ModelAndView mav = new ModelAndView();
        this.loginUser = null;
        mav.setViewName("redirect:/");
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("register");
        if(request.getMethod().equalsIgnoreCase("POST")) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String pwdconfirm = request.getParameter("pwdconfirm");
            String agreeTerms = request.getParameter("agreeTerms");

            Boolean checkPass = true;

            if(username.isEmpty() || email.isEmpty() || password.isEmpty() || pwdconfirm.isEmpty() || agreeTerms == null){
                checkPass = false;
            } else if(!password.equals(pwdconfirm) || !agreeTerms.equals("on")){
                checkPass = false;
            }
            if(checkPass) {
                Emoloyee user = new Emoloyee(7001, username, "MANAGER", 7002, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 8403.00, 8240.0f, 10, "ACCOUNTING", password, username+"@test.com");
                user.setEmail(email);
                users.add(user);
                mav.setViewName("redirect:/");
            }
        }

        return mav;
    }

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        if(null == loginUser){
            mav.setViewName("redirect:/login");
            return mav;
        }
        mav.setViewName("index");
        Map params = new LinkedHashMap();
        params.put("key word", "all");
        params.put("deptno", 10);


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
