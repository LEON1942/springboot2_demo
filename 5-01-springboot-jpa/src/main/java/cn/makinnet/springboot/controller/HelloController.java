package cn.makinnet.springboot.controller;

import cn.makinnet.springboot.AppConfig;
import cn.makinnet.springboot.entity.Department;
import cn.makinnet.springboot.entity.Employee;
import cn.makinnet.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional(readOnly = false)
@Controller
public class HelloController {

    @Autowired
    private AppConfig appConfig;

    @Resource
    private EmployeeService employeeService;

    private List<Employee> users = new ArrayList<Employee>();
    private List<Department> depts = new ArrayList<Department>();

    private Employee loginUser = null;

    @Value("${app.upload.location}")
    private String uploadPath;

    public HelloController(){

    }

    @GetMapping("/init")
    public ModelAndView initDB(){

        users.add(new Employee(1, "LEON", "MANAGER", null, "1990-07-23", 8403.00, 8240.0f, 10, "ACCOUNTING", "123", "leon@test.com"));

        Optional<Employee> op = employeeService.findById(1);
        Employee emp = null;
        if(op.isPresent()){
            emp = op.get();
            System.out.println("GOT FROM DATABASE EMP[NAME:" + emp.getEname()+"]");
        }else {
            emp = users.get(1);
            employeeService.insertEmployee(emp);
            System.out.println("SAVE EMP[NAME:" + emp.getEname() + "] TO DATABASE.");
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/");
        return mav;
    }

    @GetMapping("/find/{dname}")
    public ModelAndView findByEname(@PathVariable  String dname){
        List<Employee> employee = employeeService.findByDname(dname);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/");
        return mav;
    }

    // WebRequest 是对 request 对象的包装
    // 两者都是与J2EE容器强耦合，为了将来扩展需要，不推荐使用request对象传递参数，而使用ModelAndView对象来实现
    // request.setAttribute(key, value) 是向当前请求中放入对象，这种方式与WEB容器强耦合
    // ModelAndView.addObject(key, value)是向mav实例中放入对象，这种方式与WEB容器非强耦合
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

            for (Employee user:users) {
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

        return mav;
    }

    // MultipartFile 是上传文件接口，对应了保存的临时文件
    // 参数名与前端file组件的name属性保持一致
    // @RequestParam("profile")MultipartFile photo 代表了photo参数对应于前端的 name=profile 的file
    @PostMapping("/register/check")
    public ModelAndView registUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("profile")MultipartFile photo) throws IOException {

        ModelAndView mav = new ModelAndView();

        if(null == photo){
            return mav;
        }

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
            // String uploadPath = "D:/java/Applications/springboot2_demo/4-01-springboot-mvc/src/main/resources/static/users/";
            // String filename = photo.getOriginalFilename();
            String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf("."));
            if(!suffix.equalsIgnoreCase(".JPG")){
                throw new RuntimeException("图片格式不支持！");
            }
            // Spring提供了一个文件操作类 FileCopyUtils
            FileCopyUtils.copy(photo.getInputStream(), new FileOutputStream(uploadPath+filename+suffix));
            Employee user = new Employee(7001, username, "MANAGER", 7002, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 8403.00, 8240.0f, 10, "ACCOUNTING", password, username+"@test.com");
            user.setEmail(email);
            users.add(user);
            mav.setViewName("redirect:/");
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
