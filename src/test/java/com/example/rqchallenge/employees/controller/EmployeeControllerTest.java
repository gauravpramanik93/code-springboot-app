package com.example.rqchallenge.employees.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.example.rqchallenge.employees.util.RawDataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;

    @Test
    void testGetAllEmployees(){
        Mockito.when(
                employeeService.getAllEmployees()
        ).thenReturn(RawDataHelper.getAllEmployees());

        ResponseEntity<List<Employee>> responseEntity = employeeController.getAllEmployees();

        Assertions.assertEquals(3, responseEntity.getBody().size());
        Assertions.assertEquals(32, responseEntity.getBody().get(1).getAge());
        Assertions.assertEquals(24234, responseEntity.getBody().get(1).getSalary());
    }

    @Test
    void testGetAllEmployeesEmpty() {
        Mockito.when(
                employeeService.getAllEmployees()
        ).thenReturn(new ArrayList<>());

        ResponseEntity<List<Employee>> responseEntity = employeeController.getAllEmployees();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetEmployeesByNameSearch(){
        Mockito.when(
                employeeService.getEmployeesByNameSearch("Anuj_rawdata")
        ).thenReturn(RawDataHelper.getEmployeeByName("Anuj_rawdata"));

        ResponseEntity<List<Employee>> responseEntity = employeeController.getEmployeesByNameSearch("Anuj_rawdata");

        Assertions.assertEquals(1, responseEntity.getBody().size());
        Assertions.assertEquals(30, responseEntity.getBody().get(0).getAge());
        Assertions.assertEquals(55000, responseEntity.getBody().get(0).getSalary());
    }

    @Test
    void testGetEmployeesByNameSearchEmptyResponse(){

        Mockito.when(
                employeeService.getEmployeesByNameSearch("GP")
        ).thenReturn(new ArrayList<>());

        ResponseEntity<List<Employee>> responseEntity = employeeController.getEmployeesByNameSearch("GP");

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testGetEmployeeById() throws Exception{
        Employee e = new Employee("1", "SP", 1, 1, "");
        Mockito.when(
                employeeService.getEmployeeById("1")
        ).thenReturn(e);
        ResponseEntity<Employee> responseEntity = employeeController.getEmployeeById("1");
        Assertions.assertEquals("SP", responseEntity.getBody().getName());
    }

    @Test
    void testGetHighestSalaryOfEmployees() {

        Mockito.when(
                employeeService.getHighestSalary()
        ).thenReturn(101);

        ResponseEntity<Integer> responseEntity = employeeController.getHighestSalaryOfEmployees();

        Assertions.assertEquals(101, responseEntity.getBody());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        Mockito.when(
                employeeService.getTopHighestEarningEmployeeNames()
        ).thenReturn(RawDataHelper.getTopTenEarningEmployees());
        ResponseEntity<List<String>> responseEntity = employeeController.getTopTenHighestEarningEmployeeNames();
        Assertions.assertEquals(10, responseEntity.getBody().size());
    }

    @Test
    void testCreateEmployee() {

        Employee employee = new Employee("1","GP",100000,55,"");
        HashMap<String, Object> employeeInput = new HashMap<>();
        Mockito.when(
                employeeService.createEmployee(employeeInput)
        ).thenReturn(employee);

        ResponseEntity<Employee> responseEntity = employeeController.createEmployee(employeeInput);

        Assertions.assertEquals("GP", responseEntity.getBody().getName());
        Assertions.assertEquals("1", responseEntity.getBody().getId());
        Assertions.assertEquals(55, responseEntity.getBody().getAge());
        Assertions.assertEquals(100000, responseEntity.getBody().getSalary());
    }

}
