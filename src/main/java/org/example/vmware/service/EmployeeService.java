package org.example.vmware.service;

import org.example.vmware.helper.CSVHelper;
import org.example.vmware.model.Employee;
import org.example.vmware.model.EmployeeDTO;
import org.example.vmware.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {

        return (List<Employee>) employeeRepository.findAll();
    }

    public Employee get(long id){
        Employee emp = employeeRepository.findById(id).orElse(null);
        return emp;
    }

    public void save(EmployeeDTO ed){
        Employee emp = new Employee(ed.getName(), ed.getAge());
        employeeRepository.save(emp);
    }

    public void delete(Long id){
        employeeRepository.deleteById(id);
    }

    public void update(long id, EmployeeDTO ed) throws Exception {
        Employee emp = employeeRepository.findById(id).orElse(null);
        if(emp == null){
            throw new NullPointerException();
        }
        if(CSVHelper.verifyName(ed.getName())){
            emp.setAge(ed.getAge());
            emp.setName(ed.getName());
            employeeRepository.save(emp);
        } else {
            throw new Exception("Name not correct");
        }
    }
}
