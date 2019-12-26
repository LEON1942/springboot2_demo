package cn.makinnet.springboot.repository;

import cn.makinnet.springboot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Employee, Integer> 泛型需要设置 实体类和相应主键类型
// JpaRepository 封装常用的数据操作方法，我们只要定义Repository接口就可以了。
// Spring Boot 启动时会自动帮我们生成具体的实现类来实现CRUD方法
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
