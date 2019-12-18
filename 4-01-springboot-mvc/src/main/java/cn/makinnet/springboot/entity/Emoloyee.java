package cn.makinnet.springboot.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emoloyee {

    // 员工编号
    private Integer empno;
    // 姓名
    private String ename;
    // 岗位
    private String job;
    //
    private Integer mgr;
    // 入职时间
    private Date hiredate;
    // 薪资
    private Double sal;
    // 奖金
    private Float comm;
    // 部门编号
    private Integer deptno;
    // 部门名称
    private String dname;
    // 邮箱
    private String email;
    // 密码
    private String password;

    public Emoloyee(Integer empno, String ename, String job, Integer mgr, String hiredate, Double sal, Float comm, Integer deptno, String dname, String password, String email){
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        try {
            this.hiredate = new SimpleDateFormat("yyyy-MM-dd").parse(hiredate);
        } catch (ParseException e) {
            e.printStackTrace();
            this.hiredate = new Date();
        }
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
        this.dname = dname;
        this.password = password;
        this.email = email;
    }

    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getMgr() {
        return mgr;
    }

    public void setMgr(Integer mgr) {
        this.mgr = mgr;
    }

    public String getHiredate() {

        return new SimpleDateFormat("yyyy-MM-dd").format(hiredate) ;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }

    public Float getComm() {
        return comm;
    }

    public void setComm(Float comm) {
        this.comm = comm;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
