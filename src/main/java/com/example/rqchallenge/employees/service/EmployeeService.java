package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.exception.EmployeeNotCreatedException;
import com.example.rqchallenge.employees.exception.RestApiExecutionException;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.exception.EmployeeNotFoundException;
import com.example.rqchallenge.employees.util.Constants;
import com.example.rqchallenge.employees.util.ParserUtil;
import com.example.rqchallenge.employees.util.RawDataHelper;
import com.example.rqchallenge.employees.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class EmployeeService {

    private static final String BASE_URL = "https://dummy.restapiexample.com/api/v1";

    public List<Employee> getAllEmployees(){
        log.info("Fetching all employees data.");
        try {
            String response = RestUtil.getCall(BASE_URL + Constants.EMPLOYEES, String.class);
            log.debug("getAllEmployees response  : {}", response);
            List<Employee> employeeList = ParserUtil.parseEmployeeList(response);
            if(employeeList.isEmpty()){
                log.info("Employee list is empty. Fetching the mock employees from raw data.");
                throw new Exception();
            }
            return employeeList;
        } catch (Exception ex){
            log.error("Error occurred while retrieving employees", ex);
            return RawDataHelper.getAllEmployees();
        }
    }

    public List<Employee> getEmployeesByNameSearch(String name) {
        log.info("Fetching all the employees with the name {}", name);
        List<Employee> employeeList = ParserUtil.searchEmployeesByName(getAllEmployees(), name);
        if(employeeList == null || employeeList.isEmpty()){
            log.info("Fetching the mock employees from raw data.");
            return RawDataHelper.getEmployeeByName(name);
        }
        return employeeList;
    }

    public Employee getEmployeeById(String id) {
        log.info("Fetching data for employee id {} ", id);
        try {
            String response = RestUtil.getCall(BASE_URL + "/employee/" + id, String.class);
            log.debug("getEmployeeById response : {}", response);
            return ParserUtil.parseEmployee(response, id);
        } catch (Exception ex){
            log.error("Error while retrieving employee by id " + id, ex);
            throw new RestApiExecutionException("Internal error occurred while fetching employee information");
        }

    }

    public Integer getHighestSalary() {
        log.info("Fetching the highest salary.");
        Integer highestSalary =  ParserUtil.getHighestSalary(getAllEmployees());
        log.debug("Highest salary of all employees : {}", highestSalary);
        return highestSalary;
    }

    public List<String> getTopHighestEarningEmployeeNames() {
        log.info("Fetching top 10 highest earning employees.");
        try {
            String response = RestUtil.getCall(BASE_URL + Constants.EMPLOYEES, String.class);
            log.debug("Response of all employees : {}", response);
            List<String> employeeList = ParserUtil.getTopEarningEmployees(response, 10);
            if(employeeList == null || employeeList.isEmpty()){
                log.info("Fetching top 10 highest earning mock employees from raw data.");
                throw new Exception();
            }
            return employeeList;
        } catch (Exception ex){
            log.error("Error occurred while retrieving employees", ex);
            return RawDataHelper.getTopTenEarningEmployees();
        }
    }

    public Employee createEmployee(Map<String, Object> employeeInput) {
        log.info("Creating employee object.");
        try {
            String response = RestUtil.postCall(BASE_URL + Constants.CREATE, employeeInput, String.class);
            log.debug("createEmployee response : {}", response);
            return ParserUtil.parseEmployee(response);
        } catch (Exception ex) {
            log.error("Error occurred while creating employee object with name " + employeeInput.get(Constants.EMPLOYEE_NAME), ex);
            throw new EmployeeNotCreatedException("Employee object not created with name " + employeeInput.get(Constants.EMPLOYEE_NAME));
        }
    }

    public String deleteEmployee(String id) {
        log.info("Deleting employee with id {}", id);

        Employee employeeObj = getAllEmployees().stream()
                .filter(employee -> employee.getId().equals(id))
                .findAny()
                .orElse(null);

        if (employeeObj == null) {
            log.warn("No employee found with the id " + id);
            throw new EmployeeNotFoundException("Employee not found with id " + id);
        }

        String employeeName = employeeObj.getName();
        try {
            RestUtil.deleteCall(BASE_URL + Constants.DELETE + id);
        } catch (Exception ex){
            log.error("Error occurred while deleting the employee with id "+ id, ex);
            throw new RestApiExecutionException("Error occurred while deleting the employee with id "+ id);
        }
        return employeeName;
    }
}

