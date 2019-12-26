package cn.makinnet.springboot.service;

import cn.makinnet.springboot.entity.Employee;
import cn.makinnet.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository empRepository;

    public Employee insertEmployee(Employee employee){
        return empRepository.save(employee);
    }

    public Optional<Employee> findById(Integer id){
        return empRepository.findById(id);
    }
}
