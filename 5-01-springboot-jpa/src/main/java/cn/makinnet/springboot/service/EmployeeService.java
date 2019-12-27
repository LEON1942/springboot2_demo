package cn.makinnet.springboot.service;

import cn.makinnet.springboot.entity.Employee;
import cn.makinnet.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository empRepository;

    public Employee insertEmployee(Employee employee){
        // 在save的时候，如果对象没有ID，则是insert操作，有ID则是update操作
        // 一旦保存成功，JPA会自动将生成的主键回填到id中
        return empRepository.save(employee);
    }

    public Optional<Employee> findById(Integer id){
        return empRepository.findById(id);
    }

    public List<Employee> findByDname(String dname){

        return empRepository.findByDname(dname);
    }
}
