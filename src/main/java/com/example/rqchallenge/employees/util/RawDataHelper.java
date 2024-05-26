package com.example.rqchallenge.employees.util;

import com.example.rqchallenge.employees.model.Employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RawDataHelper {

    public static List<Employee> getAllEmployees() {
        log.info("Fetching raw data for getAllEmployee");
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("1000", "Anish_" + Constants.RAWDATA, 2432432, 30, ""));
        list.add(new Employee("2000", "Ranjan_" + Constants.RAWDATA, 24234, 32, ""));
        list.add(new Employee("3000", "Anuj_" + Constants.RAWDATA, 345600, 32, ""));
        return list;
    }

    public static List<Employee> getEmployeeByName(String name) {
        log.info("Fetching raw data for getAllEmployee");
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("1000", name + " " + Constants.RAWDATA, 55000, 30, ""));
        return list;
    }

    public static Employee getEmployeeById(String id) {
        log.debug("Providing raw data for getEmployeeById : {}", id);
        return new Employee(id, "Test_" + Constants.RAWDATA, 31, 435552, "");
    }

    public static List<String> getTopTenEarningEmployees(){
        return List.of("Employee1_" + Constants.RAWDATA,"Employee2_" + Constants.RAWDATA,
                "Employee3_" + Constants.RAWDATA, "Employee4_" + Constants.RAWDATA,
                "Employee5_" + Constants.RAWDATA, "Employee6_" + Constants.RAWDATA,
                "Employee7_" + Constants.RAWDATA, "Employee8_" + Constants.RAWDATA,
                "Employee9_" + Constants.RAWDATA, "Employee10_" + Constants.RAWDATA);
    }
}
