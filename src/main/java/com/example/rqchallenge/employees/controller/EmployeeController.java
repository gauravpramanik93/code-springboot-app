package com.example.rqchallenge.employees.controller;


import java.util.List;
import java.util.Map;
import com.example.rqchallenge.employees.IEmployeeController;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController()
@Slf4j
public class EmployeeController implements IEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        List<Employee> employeeList = employeeService.getEmployeesByNameSearch(searchString);
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getHighestSalary());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<String> employeeList = employeeService.getTopHighestEarningEmployeeNames();
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        Employee employee = employeeService.createEmployee(employeeInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        String employeeName = employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body(employeeName);
    }

}

