package com.example.rqchallenge.employees.util;

import com.example.rqchallenge.employees.exception.EmployeeNotFoundException;
import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ParserUtil {
    private static RawDataHelper mock = new RawDataHelper();
    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Employee> parseEmployeeList(String employeeResponse) throws Exception {
        try {
            log.info("Parsing employee list.");
            JSONObject jsonObject = new JSONObject(employeeResponse);
            if(!isNull(jsonObject)) {
                return Arrays.asList(mapper.readValue(jsonObject.get(Constants.DATA).toString(), Employee[].class));
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            log.error("Error occurred while parsing employee list", e);
            throw e;
        }
    }

    public static Employee parseEmployee(String employeeResponse, String id) throws Exception {
        try {
            log.info("Parsing employee object.");
            JSONObject jsonObject = new JSONObject(employeeResponse);
            if (!isNull(jsonObject)) {
                return mapper.readValue(jsonObject.get(Constants.DATA).toString(), Employee.class);
            } else {
                throw new EmployeeNotFoundException("Employee not found with id "+id);
            }
        } catch (Exception e) {
            log.error("Error occurred while parsing employee", e);
            throw e;
        }
    }

    public static Employee parseEmployee(String employeeResponse) throws Exception {
        try {
            log.info("Parsing employee object.");
            JSONObject jsonObject = new JSONObject(employeeResponse);
            Employee employeeObj = null;
            if (!isNull(jsonObject)) {
                employeeObj = mapper.readValue(jsonObject.get(Constants.DATA).toString(), Employee.class);
            }
            return employeeObj;
        } catch (Exception e) {
            log.error("Error occurred while parsing employee", e);
            throw e;
        }
    }

    public static List<Employee> searchEmployeesByName(List<Employee> employeeList, String searchString) {
        return employeeList.stream()
                .filter(emp -> emp.getName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static Integer getHighestSalary(List<Employee> employeeList) {
        if(employeeList.isEmpty())
            return 0;

        return employeeList.stream()
                .sorted((e1, e2) -> Integer.compare(e2.getSalary(), e1.getSalary()))
                .limit(1)
                .collect(Collectors.toList()).get(0).getSalary();
    }

    public static List<String> getTopEarningEmployees(String employeeResponse, int n) throws Exception {
        List<Employee> employeeList = parseEmployeeList(employeeResponse);
        return employeeList.stream()
                .sorted((e1, e2) -> Integer.compare(e2.getSalary(), e1.getSalary()))
                .limit(n)
                .map(e -> String.valueOf(e.getName()))
                .collect(Collectors.toList());
    }

    private static boolean isNull(JSONObject object){
        return (Objects.isNull(object.get(Constants.DATA)) ||
                object.get(Constants.DATA).toString().equals("null"));
    }
}
