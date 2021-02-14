package org.example.vmware.controller;

import org.example.vmware.model.Employee;
import org.example.vmware.model.EmployeeDTO;
import org.example.vmware.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping(value="{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id){
        try{
            Employee emp = employeeService.get(id);
            return new ResponseEntity(emp, HttpStatus.OK);
        }
        catch(NullPointerException e){
            return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity addEmployee(@RequestBody EmployeeDTO EmployeeData){
        try{
            //System.out.println(""+EmployeeData);
            employeeService.save(EmployeeData);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(""+e.toString());
        }

    }

    @PutMapping("{id}")
    public ResponseEntity updateEmployee(@PathVariable("id") Long id,
                                         @RequestBody EmployeeDTO EmployeeData){
        try{
            employeeService.update(id, EmployeeData);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NullPointerException n){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee Not Found");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(""+e.toString());
        }
    }
}
