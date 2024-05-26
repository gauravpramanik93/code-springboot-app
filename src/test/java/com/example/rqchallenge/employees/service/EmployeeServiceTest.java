package com.example.rqchallenge.employees.service;


import java.util.HashMap;
import java.util.List;

import com.example.rqchallenge.employees.exception.EmployeeNotCreatedException;
import com.example.rqchallenge.employees.exception.EmployeeNotFoundException;
import com.example.rqchallenge.employees.exception.RestApiExecutionException;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.util.RestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;


@SpringBootTest
public class EmployeeServiceTest {

    MockedStatic<RestUtil> restUtil;

    @Autowired
    EmployeeService employeeService;

    @BeforeEach
    void init() {
        restUtil = Mockito.mockStatic(RestUtil.class);
    }

    @AfterEach
    void destroy() {
        restUtil.close();
    }

    @Test
    void testGetAllEmployees() {

        String restApiResponse = "{\"status\":\"success\",\"data\":[{\"id\":1,\"employee_name\":\"Tiger 1\",\"employee_salary\":320800,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":2,\"employee_name\":\"GarrettWinters\",\"employee_salary\":170750,\"employee_age\":63,\"profile_image\":\"\"},{\"id\":3,\"employee_name\":\"AshtonCox\",\"employee_salary\":86000,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":4,\"employee_name\":\"CedricKelly\",\"employee_salary\":433060,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":5,\"employee_name\":\"AiriSatou\",\"employee_salary\":162700,\"employee_age\":33,\"profile_image\":\"\"},{\"id\":6,\"employee_name\":\"BrielleWilliamson\",\"employee_salary\":372000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":7,\"employee_name\":\"HerrodChandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":8,\"employee_name\":\"RhonaDavidson\",\"employee_salary\":327900,\"employee_age\":55,\"profile_image\":\"\"},{\"id\":9,\"employee_name\":\"ColleenHurst\",\"employee_salary\":205500,\"employee_age\":39,\"profile_image\":\"\"},{\"id\":10,\"employee_name\":\"SonyaFrost\",\"employee_salary\":103600,\"employee_age\":23,\"profile_image\":\"\"},{\"id\":11,\"employee_name\":\"JenaGaines\",\"employee_salary\":90560,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":12,\"employee_name\":\"QuinnFlynn\",\"employee_salary\":342000,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":13,\"employee_name\":\"ChardeMarshall\",\"employee_salary\":470600,\"employee_age\":36,\"profile_image\":\"\"},{\"id\":14,\"employee_name\":\"HaleyKennedy\",\"employee_salary\":313500,\"employee_age\":43,\"profile_image\":\"\"},{\"id\":15,\"employee_name\":\"TatyanaFitzpatrick\",\"employee_salary\":385750,\"employee_age\":19,\"profile_image\":\"\"},{\"id\":16,\"employee_name\":\"MichaelSilva\",\"employee_salary\":198500,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":17,\"employee_name\":\"PaulByrd\",\"employee_salary\":725000,\"employee_age\":64,\"profile_image\":\"\"},{\"id\":18,\"employee_name\":\"GloriaLittle\",\"employee_salary\":237500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":19,\"employee_name\":\"BradleyGreer\",\"employee_salary\":132000,\"employee_age\":41,\"profile_image\":\"\"},{\"id\":20,\"employee_name\":\"DaiRios\",\"employee_salary\":217500,\"employee_age\":35,\"profile_image\":\"\"},{\"id\":21,\"employee_name\":\"JenetteCaldwell\",\"employee_salary\":345000,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":22,\"employee_name\":\"YuriBerry\",\"employee_salary\":675000,\"employee_age\":40,\"profile_image\":\"\"},{\"id\":23,\"employee_name\":\"CaesarVance\",\"employee_salary\":106450,\"employee_age\":21,\"profile_image\":\"\"},{\"id\":24,\"employee_name\":\"DorisWilder\",\"employee_salary\":85600,\"employee_age\":23,\"profile_image\":\"\"}],\"message\":\"Successfully! All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        List<Employee> employeeList = employeeService.getAllEmployees();

        Assertions.assertEquals(
                "Tiger 1",
                employeeList
                        .stream()
                        .filter(employee -> employee.getId().equals("1"))
                        .map(Employee::getName)
                        .findFirst()
                        .get()

        );
    }

    @Test
    void testGetAllEmployeesNoDataField() {
        String response = "{\"status\":\"success\",\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(response);

        List<Employee> employeeList = employeeService.getAllEmployees();

        // In case of empty list, mock data is expected return
        Assertions.assertEquals(
                3,
                employeeList.size()
        );
    }

    @Test
    void testGetAllEmployeesEmptyData() {
        String restApiResponse = "{\"status\":\"success\",\"data\":[],\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        List<Employee> employeeList = employeeService.getAllEmployees();
        // In case of empty data, employee list should be taken from rawdata
        Assertions.assertEquals(
                3,
                employeeList.size()
        );
    }

    @Test
    void testGetAllEmployeesJsonParsingError() {

        String restApiResponse = "{\"status\":\"success\",\"data\":[{\"id\":1,\"employee_names\":\"Tiger Nixon1\",\"employee_salary\":320800,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":2,\"employee_name\":\"GarrettWinters\",\"employee_salary\":170750,\"employee_age\":63,\"profile_image\":\"\"},{\"id\":3,\"employee_name\":\"AshtonCox\",\"employee_salary\":86000,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":4,\"employee_name\":\"CedricKelly\",\"employee_salary\":433060,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":5,\"employee_name\":\"AiriSatou\",\"employee_salary\":162700,\"employee_age\":33,\"profile_image\":\"\"},{\"id\":6,\"employee_name\":\"BrielleWilliamson\",\"employee_salary\":372000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":7,\"employee_name\":\"HerrodChandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":8,\"employee_name\":\"RhonaDavidson\",\"employee_salary\":327900,\"employee_age\":55,\"profile_image\":\"\"},{\"id\":9,\"employee_name\":\"ColleenHurst\",\"employee_salary\":205500,\"employee_age\":39,\"profile_image\":\"\"},{\"id\":10,\"employee_name\":\"SonyaFrost\",\"employee_salary\":103600,\"employee_age\":23,\"profile_image\":\"\"},{\"id\":11,\"employee_name\":\"JenaGaines\",\"employee_salary\":90560,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":12,\"employee_name\":\"QuinnFlynn\",\"employee_salary\":342000,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":13,\"employee_name\":\"ChardeMarshall\",\"employee_salary\":470600,\"employee_age\":36,\"profile_image\":\"\"},{\"id\":14,\"employee_name\":\"HaleyKennedy\",\"employee_salary\":313500,\"employee_age\":43,\"profile_image\":\"\"},{\"id\":15,\"employee_name\":\"TatyanaFitzpatrick\",\"employee_salary\":385750,\"employee_age\":19,\"profile_image\":\"\"},{\"id\":16,\"employee_name\":\"MichaelSilva\",\"employee_salary\":198500,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":17,\"employee_name\":\"PaulByrd\",\"employee_salary\":725000,\"employee_age\":64,\"profile_image\":\"\"},{\"id\":18,\"employee_name\":\"GloriaLittle\",\"employee_salary\":237500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":19,\"employee_name\":\"BradleyGreer\",\"employee_salary\":132000,\"employee_age\":41,\"profile_image\":\"\"},{\"id\":20,\"employee_name\":\"DaiRios\",\"employee_salary\":217500,\"employee_age\":35,\"profile_image\":\"\"},{\"id\":21,\"employee_name\":\"JenetteCaldwell\",\"employee_salary\":345000,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":22,\"employee_name\":\"YuriBerry\",\"employee_salary\":675000,\"employee_age\":40,\"profile_image\":\"\"},{\"id\":23,\"employee_name\":\"CaesarVance\",\"employee_salary\":106450,\"employee_age\":21,\"profile_image\":\"\"},{\"id\":24,\"employee_name\":\"DorisWilder\",\"employee_salary\":85600,\"employee_age\":23,\"profile_image\":\"\"}],\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        List<Employee> employeeList = employeeService.getAllEmployees();

        // In case of Parsing exception , employee list should be taken from rawdata
        Assertions.assertEquals("1000", employeeList.get(0).getId());
    }

    @Test
    void testGetEmployeesByName() {
        String restApiResponse = "{\"status\":\"success\",\"data\":[{\"id\":1,\"employee_name\":\"Tiger 1\",\"employee_salary\":320800,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":2,\"employee_name\":\"GarrettWinters\",\"employee_salary\":170750,\"employee_age\":63,\"profile_image\":\"\"},{\"id\":3,\"employee_name\":\"AshtonCox\",\"employee_salary\":86000,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":4,\"employee_name\":\"CedricKelly\",\"employee_salary\":433060,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":5,\"employee_name\":\"AiriSatou\",\"employee_salary\":162700,\"employee_age\":33,\"profile_image\":\"\"},{\"id\":6,\"employee_name\":\"BrielleWilliamson\",\"employee_salary\":372000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":7,\"employee_name\":\"HerrodChandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":8,\"employee_name\":\"RhonaDavidson\",\"employee_salary\":327900,\"employee_age\":55,\"profile_image\":\"\"},{\"id\":9,\"employee_name\":\"ColleenHurst\",\"employee_salary\":205500,\"employee_age\":39,\"profile_image\":\"\"},{\"id\":10,\"employee_name\":\"SonyaFrost\",\"employee_salary\":103600,\"employee_age\":23,\"profile_image\":\"\"},{\"id\":11,\"employee_name\":\"JenaGaines\",\"employee_salary\":90560,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":12,\"employee_name\":\"QuinnFlynn\",\"employee_salary\":342000,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":13,\"employee_name\":\"ChardeMarshall\",\"employee_salary\":470600,\"employee_age\":36,\"profile_image\":\"\"},{\"id\":14,\"employee_name\":\"HaleyKennedy\",\"employee_salary\":313500,\"employee_age\":43,\"profile_image\":\"\"},{\"id\":15,\"employee_name\":\"TatyanaFitzpatrick\",\"employee_salary\":385750,\"employee_age\":19,\"profile_image\":\"\"},{\"id\":16,\"employee_name\":\"MichaelSilva\",\"employee_salary\":198500,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":17,\"employee_name\":\"PaulByrd\",\"employee_salary\":725000,\"employee_age\":64,\"profile_image\":\"\"},{\"id\":18,\"employee_name\":\"GloriaLittle\",\"employee_salary\":237500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":19,\"employee_name\":\"BradleyGreer\",\"employee_salary\":132000,\"employee_age\":41,\"profile_image\":\"\"},{\"id\":20,\"employee_name\":\"DaiRios\",\"employee_salary\":217500,\"employee_age\":35,\"profile_image\":\"\"},{\"id\":21,\"employee_name\":\"JenetteCaldwell\",\"employee_salary\":345000,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":22,\"employee_name\":\"YuriBerry\",\"employee_salary\":675000,\"employee_age\":40,\"profile_image\":\"\"},{\"id\":23,\"employee_name\":\"CaesarVance\",\"employee_salary\":106450,\"employee_age\":21,\"profile_image\":\"\"},{\"id\":24,\"employee_name\":\"DorisWilder\",\"employee_salary\":85600,\"employee_age\":23,\"profile_image\":\"\"}],\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        List<Employee> employeeList = employeeService.getEmployeesByNameSearch("tiger");
        Assertions.assertEquals("Tiger 1", employeeList.get(0).getName());
    }

    @Test
    void testGetEmployeesByNameEmptyData() {
        String restApiResponse = "{\"status\":\"success\",\"data\":[],\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        List<Employee> employeeList = employeeService.getEmployeesByNameSearch("Test");
        // In case of empty data, employee list should be taken from rawdata
        Assertions.assertEquals(1,employeeList.size());
        Assertions.assertEquals("Test rawdata", employeeList.get(0).getName());
    }

    @Test
    void testGetEmployeesByNameSearchNoMatch() {
        String restApiResponse = "{\"status\":\"success\",\"data\":[{\"id\":1,\"employee_name\":\"Tiger Nixon1\",\"employee_salary\":320800,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":2,\"employee_name\":\"GarrettWinters\",\"employee_salary\":170750,\"employee_age\":63,\"profile_image\":\"\"},{\"id\":3,\"employee_name\":\"AshtonCox\",\"employee_salary\":86000,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":4,\"employee_name\":\"CedricKelly\",\"employee_salary\":433060,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":5,\"employee_name\":\"AiriSatou\",\"employee_salary\":162700,\"employee_age\":33,\"profile_image\":\"\"},{\"id\":6,\"employee_name\":\"BrielleWilliamson\",\"employee_salary\":372000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":7,\"employee_name\":\"HerrodChandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":8,\"employee_name\":\"RhonaDavidson\",\"employee_salary\":327900,\"employee_age\":55,\"profile_image\":\"\"},{\"id\":9,\"employee_name\":\"ColleenHurst\",\"employee_salary\":205500,\"employee_age\":39,\"profile_image\":\"\"},{\"id\":10,\"employee_name\":\"SonyaFrost\",\"employee_salary\":103600,\"employee_age\":23,\"profile_image\":\"\"},{\"id\":11,\"employee_name\":\"JenaGaines\",\"employee_salary\":90560,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":12,\"employee_name\":\"QuinnFlynn\",\"employee_salary\":342000,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":13,\"employee_name\":\"ChardeMarshall\",\"employee_salary\":470600,\"employee_age\":36,\"profile_image\":\"\"},{\"id\":14,\"employee_name\":\"HaleyKennedy\",\"employee_salary\":313500,\"employee_age\":43,\"profile_image\":\"\"},{\"id\":15,\"employee_name\":\"TatyanaFitzpatrick\",\"employee_salary\":385750,\"employee_age\":19,\"profile_image\":\"\"},{\"id\":16,\"employee_name\":\"MichaelSilva\",\"employee_salary\":198500,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":17,\"employee_name\":\"PaulByrd\",\"employee_salary\":725000,\"employee_age\":64,\"profile_image\":\"\"},{\"id\":18,\"employee_name\":\"GloriaLittle\",\"employee_salary\":237500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":19,\"employee_name\":\"BradleyGreer\",\"employee_salary\":132000,\"employee_age\":41,\"profile_image\":\"\"},{\"id\":20,\"employee_name\":\"DaiRios\",\"employee_salary\":217500,\"employee_age\":35,\"profile_image\":\"\"},{\"id\":21,\"employee_name\":\"JenetteCaldwell\",\"employee_salary\":345000,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":22,\"employee_name\":\"YuriBerry\",\"employee_salary\":675000,\"employee_age\":40,\"profile_image\":\"\"},{\"id\":23,\"employee_name\":\"CaesarVance\",\"employee_salary\":106450,\"employee_age\":21,\"profile_image\":\"\"},{\"id\":24,\"employee_name\":\"DorisWilder\",\"employee_salary\":85600,\"employee_age\":23,\"profile_image\":\"\"}],\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        List<Employee> employeeList = employeeService.getEmployeesByNameSearch("coiwjcoermcoreomcro");
        Assertions.assertEquals(1,employeeList.size());
        Assertions.assertEquals("coiwjcoermcoreomcro rawdata", employeeList.get(0).getName());
    }

    @Test
    void testGetEmployeesByNameNoDataField() {
        String restApiResponse = "{\"status\":\"success\",\"message\":\"Successfully!All records has been fetched.\"}";

        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        List<Employee> employeeList = employeeService.getEmployeesByNameSearch("xxxx");
        Assertions.assertEquals(1,employeeList.size());
        Assertions.assertEquals("xxxx rawdata", employeeList.get(0).getName());
    }

    @Test
    void testGetEmployeeById() {

        String restApiResponse = "{\"status\":\"success\",\"data\":{\"id\":7,\"employee_name\":\"HerrodChandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"},\"message\":\"Successfully!All Record has been fetched.\"}";

        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employee/7",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        Employee employee = employeeService.getEmployeeById("7");

        // Actual Data does not have _mockData appended in the name of the employee for id 7
        Assertions.assertEquals(
                "HerrodChandler",
                employee.getName()

        );
    }

    @Test
    void testGetEmployeeByIdNullResponse() {

        String restApiResponse = "{\"status\":\"success\",\"data\":null,\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employee/7",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        Assertions.assertThrows(RestApiExecutionException.class, ()-> employeeService.getEmployeeById("7"));
    }

    @Test
    void testGetEmployeeByIdJsonParsingError() {

        String restApiResponse = "{\"status\":\"success\",\"data\":{\"id\":7,\"employee_names\":\"HerrodChandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"},\"message\":\"Successfully!Record has been fetched.\"}";

        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employee/7",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        // In case of Parsing exception , Exception is delegated.
        Assertions.assertThrows(Exception.class, ()-> employeeService.getEmployeeById("7"));
    }

    @Test
    void testGetHighestSalary() {
        String restApiResponse = "{\"status\":\"success\",\"data\":[{\"id\":1,\"employee_name\":\"Tiger Nixon1\",\"employee_salary\":10000000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":2,\"employee_name\":\"GarrettWinters\",\"employee_salary\":170750,\"employee_age\":63,\"profile_image\":\"\"},{\"id\":3,\"employee_name\":\"AshtonCox\",\"employee_salary\":86000,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":4,\"employee_name\":\"CedricKelly\",\"employee_salary\":433060,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":5,\"employee_name\":\"AiriSatou\",\"employee_salary\":162700,\"employee_age\":33,\"profile_image\":\"\"},{\"id\":6,\"employee_name\":\"BrielleWilliamson\",\"employee_salary\":372000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":7,\"employee_name\":\"HerrodChandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":8,\"employee_name\":\"RhonaDavidson\",\"employee_salary\":327900,\"employee_age\":55,\"profile_image\":\"\"},{\"id\":9,\"employee_name\":\"ColleenHurst\",\"employee_salary\":205500,\"employee_age\":39,\"profile_image\":\"\"},{\"id\":10,\"employee_name\":\"SonyaFrost\",\"employee_salary\":103600,\"employee_age\":23,\"profile_image\":\"\"},{\"id\":11,\"employee_name\":\"JenaGaines\",\"employee_salary\":90560,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":12,\"employee_name\":\"QuinnFlynn\",\"employee_salary\":342000,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":13,\"employee_name\":\"ChardeMarshall\",\"employee_salary\":470600,\"employee_age\":36,\"profile_image\":\"\"},{\"id\":14,\"employee_name\":\"HaleyKennedy\",\"employee_salary\":313500,\"employee_age\":43,\"profile_image\":\"\"},{\"id\":15,\"employee_name\":\"TatyanaFitzpatrick\",\"employee_salary\":385750,\"employee_age\":19,\"profile_image\":\"\"},{\"id\":16,\"employee_name\":\"MichaelSilva\",\"employee_salary\":198500,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":17,\"employee_name\":\"PaulByrd\",\"employee_salary\":725000,\"employee_age\":64,\"profile_image\":\"\"},{\"id\":18,\"employee_name\":\"GloriaLittle\",\"employee_salary\":237500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":19,\"employee_name\":\"BradleyGreer\",\"employee_salary\":132000,\"employee_age\":41,\"profile_image\":\"\"},{\"id\":20,\"employee_name\":\"DaiRios\",\"employee_salary\":217500,\"employee_age\":35,\"profile_image\":\"\"},{\"id\":21,\"employee_name\":\"JenetteCaldwell\",\"employee_salary\":345000,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":22,\"employee_name\":\"YuriBerry\",\"employee_salary\":675000,\"employee_age\":40,\"profile_image\":\"\"},{\"id\":23,\"employee_name\":\"CaesarVance\",\"employee_salary\":106450,\"employee_age\":21,\"profile_image\":\"\"},{\"id\":24,\"employee_name\":\"DorisWilder\",\"employee_salary\":85600,\"employee_age\":23,\"profile_image\":\"\"}],\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);
        Integer highestSalary = employeeService.getHighestSalary();
        Assertions.assertEquals(
                10000000,
                highestSalary
        );
    }

    @Test
    void testGetHighestSalaryNoDataField() {
        String restApiResponse = "{\"status\":\"success\",\"message\":\"Successfully!All record has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);
        Integer highestSalary = employeeService.getHighestSalary();
        Assertions.assertEquals(2432432,highestSalary);
    }

    @Test
    void testGetTopNHighestEarningEmployeeNames() {
        String restApiResponse = "{\"status\":\"success\",\"data\":[{\"id\":1,\"employee_name\":\"Tiger Nixon1\",\"employee_salary\":10000000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":2,\"employee_name\":\"GarrettWinters\",\"employee_salary\":170750,\"employee_age\":63,\"profile_image\":\"\"},{\"id\":3,\"employee_name\":\"AshtonCox\",\"employee_salary\":86000,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":4,\"employee_name\":\"CedricKelly\",\"employee_salary\":433060,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":5,\"employee_name\":\"AiriSatou\",\"employee_salary\":162700,\"employee_age\":33,\"profile_image\":\"\"},{\"id\":6,\"employee_name\":\"BrielleWilliamson\",\"employee_salary\":372000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":7,\"employee_name\":\"HerrodChandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":8,\"employee_name\":\"RhonaDavidson\",\"employee_salary\":327900,\"employee_age\":55,\"profile_image\":\"\"},{\"id\":9,\"employee_name\":\"ColleenHurst\",\"employee_salary\":205500,\"employee_age\":39,\"profile_image\":\"\"},{\"id\":10,\"employee_name\":\"SonyaFrost\",\"employee_salary\":103600,\"employee_age\":23,\"profile_image\":\"\"},{\"id\":11,\"employee_name\":\"JenaGaines\",\"employee_salary\":90560,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":12,\"employee_name\":\"QuinnFlynn\",\"employee_salary\":342000,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":13,\"employee_name\":\"ChardeMarshall\",\"employee_salary\":470600,\"employee_age\":36,\"profile_image\":\"\"},{\"id\":14,\"employee_name\":\"HaleyKennedy\",\"employee_salary\":313500,\"employee_age\":43,\"profile_image\":\"\"},{\"id\":15,\"employee_name\":\"TatyanaFitzpatrick\",\"employee_salary\":385750,\"employee_age\":19,\"profile_image\":\"\"},{\"id\":16,\"employee_name\":\"MichaelSilva\",\"employee_salary\":198500,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":17,\"employee_name\":\"PaulByrd\",\"employee_salary\":725000,\"employee_age\":64,\"profile_image\":\"\"},{\"id\":18,\"employee_name\":\"GloriaLittle\",\"employee_salary\":237500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":19,\"employee_name\":\"BradleyGreer\",\"employee_salary\":132000,\"employee_age\":41,\"profile_image\":\"\"},{\"id\":20,\"employee_name\":\"DaiRios\",\"employee_salary\":217500,\"employee_age\":35,\"profile_image\":\"\"},{\"id\":21,\"employee_name\":\"JenetteCaldwell\",\"employee_salary\":345000,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":22,\"employee_name\":\"YuriBerry\",\"employee_salary\":675000,\"employee_age\":40,\"profile_image\":\"\"},{\"id\":23,\"employee_name\":\"CaesarVance\",\"employee_salary\":106450,\"employee_age\":21,\"profile_image\":\"\"},{\"id\":24,\"employee_name\":\"DorisWilder\",\"employee_salary\":85600,\"employee_age\":23,\"profile_image\":\"\"}],\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);
        List<String> employeeList = employeeService.getTopHighestEarningEmployeeNames();
        Assertions.assertEquals("Tiger Nixon1",employeeList.get(0));
        Assertions.assertEquals("PaulByrd", employeeList.get(1));
        Assertions.assertEquals("YuriBerry",employeeList.get(2));
    }

    @Test
    void testGetTopNHighestEarningEmployeeNamesNoDataField() {
        String restApiResponse = "{\"status\":\"success\",\"message\":\"Successfully!All records has been fetched.\"}";

        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);
        List<String> employeeList = employeeService.getTopHighestEarningEmployeeNames();
        Assertions.assertEquals("Employee1_rawdata",employeeList.get(0));
        Assertions.assertEquals("Employee9_rawdata", employeeList.get(8));
        Assertions.assertEquals("Employee10_rawdata",employeeList.get(9));
    }

    @Test
    void testTopTenEmployeesEmptyResponse() {
        String restApiResponse = "{\"status\":\"success\",\"data\":[],\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        List<String> employeeList = employeeService.getTopHighestEarningEmployeeNames();
        // In case of empty data, employee list should be taken from rawdata
        Assertions.assertEquals(10,employeeList.size());
        Assertions.assertEquals("Employee10_rawdata", employeeList.get(9));
    }

    @Test
    void testGetTopNHighestEarningEmployeeNamesLessThan10Employees() {
        String restApiResponse = "{\"status\":\"success\",\"data\":[{\"id\":1,\"employee_name\":\"Tiger Nixon1\",\"employee_salary\":10000000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":2,\"employee_name\":\"GarrettWinters\",\"employee_salary\":170750,\"employee_age\":63,\"profile_image\":\"\"},{\"id\":3,\"employee_name\":\"AshtonCox\",\"employee_salary\":86000,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":4,\"employee_name\":\"CedricKelly\",\"employee_salary\":433060,\"employee_age\":22,\"profile_image\":\"\"}],\"message\":\"Successfully!All records has been fetched.\"}";

        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);
        List<String> employeeList = employeeService.getTopHighestEarningEmployeeNames();
        Assertions.assertEquals(
                4,
                employeeList.size()
        );
    }

    @Test
    void testCreateEmployee() {

        String restApiResponse = "{\"status\":\"success\",\"data\":{\"employee_name\":\"GP\",\"employee_salary\":\"800000\",\"employee_age\":\"34\",\"id\":7494},\"message\":\"Successfully!Record has been added.\"}";
        HashMap<String, Object> employeeData = new HashMap<>();
        restUtil.when(
                        () -> RestUtil.postCall(
                                "https://dummy.restapiexample.com/api/v1/create",
                                employeeData,
                                String.class
                        ))
                .thenReturn(restApiResponse);

        Employee employee = employeeService.createEmployee(employeeData);

        Assertions.assertEquals(
                "GP",
                employee.getName()

        );
    }

    @Test
    void testCreateEmployeeRestApiError() {

        HashMap<String, Object> employeeData = new HashMap<>();
        restUtil.when(
                        () -> RestUtil.postCall(
                                "https://dummy.restapiexample.com/api/v1/create",
                                employeeData,
                                String.class
                        ))
                .thenThrow(new RestClientException(""));

        Assertions.assertThrows(EmployeeNotCreatedException.class, () -> employeeService.createEmployee(employeeData));
    }

    @Test
    void testCreateEmployeeEmptyData() {

        String restApiResponse = "{\"status\":\"success\",\"data\":{},\"message\":\"Successfully! All records has been fetched.\"}";

        HashMap<String, Object> employeeData = new HashMap<>();
        restUtil.when(
                        () -> RestUtil.postCall(
                                "https://dummy.restapiexample.com/api/v1/create",
                                employeeData,
                                String.class
                        ))
                .thenReturn(restApiResponse);

        Employee employee = employeeService.createEmployee(employeeData);

        // In case of empty data, employee name should be null
        Assertions.assertNull(employee.getName());
    }

    @Test
    void testCreateEmployeeJsonParsingError() {

        String restApiResponse = "{\"status\":\"success\",\"data\":{\"name\":\"Gaurav\",\"salary\":\"800000\",\"age\":\"34\",\"id\":7494},\"message\":\"Successfully! Record has been added.\"}";
        HashMap<String, Object> employeeData = new HashMap<>();
        restUtil.when(
                        () -> RestUtil.postCall(
                                "https://dummy.restapiexample.com/api/v1/create",
                                employeeData,
                                String.class
                        ))
                .thenReturn(restApiResponse);

        // In case of Parsing exception , Exception is delegated.
        Assertions.assertThrows(EmployeeNotCreatedException.class, () -> employeeService.createEmployee(employeeData));
    }

    @Test
    void testDeleteEmployeeById() {
        String restApiResponse = "{\"status\":\"success\",\"data\":[{\"id\":1,\"employee_name\":\"GP\",\"employee_salary\":320800,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":2,\"employee_name\":\"GarrettWinters\",\"employee_salary\":170750,\"employee_age\":63,\"profile_image\":\"\"},{\"id\":3,\"employee_name\":\"AshtonCox\",\"employee_salary\":86000,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":4,\"employee_name\":\"CedricKelly\",\"employee_salary\":433060,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":5,\"employee_name\":\"AiriSatou\",\"employee_salary\":162700,\"employee_age\":33,\"profile_image\":\"\"},{\"id\":6,\"employee_name\":\"BrielleWilliamson\",\"employee_salary\":372000,\"employee_age\":61,\"profile_image\":\"\"},{\"id\":7,\"employee_name\":\"HerrodChandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":8,\"employee_name\":\"RhonaDavidson\",\"employee_salary\":327900,\"employee_age\":55,\"profile_image\":\"\"},{\"id\":9,\"employee_name\":\"ColleenHurst\",\"employee_salary\":205500,\"employee_age\":39,\"profile_image\":\"\"},{\"id\":10,\"employee_name\":\"SonyaFrost\",\"employee_salary\":103600,\"employee_age\":23,\"profile_image\":\"\"},{\"id\":11,\"employee_name\":\"JenaGaines\",\"employee_salary\":90560,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":12,\"employee_name\":\"QuinnFlynn\",\"employee_salary\":342000,\"employee_age\":22,\"profile_image\":\"\"},{\"id\":13,\"employee_name\":\"ChardeMarshall\",\"employee_salary\":470600,\"employee_age\":36,\"profile_image\":\"\"},{\"id\":14,\"employee_name\":\"HaleyKennedy\",\"employee_salary\":313500,\"employee_age\":43,\"profile_image\":\"\"},{\"id\":15,\"employee_name\":\"TatyanaFitzpatrick\",\"employee_salary\":385750,\"employee_age\":19,\"profile_image\":\"\"},{\"id\":16,\"employee_name\":\"MichaelSilva\",\"employee_salary\":198500,\"employee_age\":66,\"profile_image\":\"\"},{\"id\":17,\"employee_name\":\"PaulByrd\",\"employee_salary\":725000,\"employee_age\":64,\"profile_image\":\"\"},{\"id\":18,\"employee_name\":\"GloriaLittle\",\"employee_salary\":237500,\"employee_age\":59,\"profile_image\":\"\"},{\"id\":19,\"employee_name\":\"BradleyGreer\",\"employee_salary\":132000,\"employee_age\":41,\"profile_image\":\"\"},{\"id\":20,\"employee_name\":\"DaiRios\",\"employee_salary\":217500,\"employee_age\":35,\"profile_image\":\"\"},{\"id\":21,\"employee_name\":\"JenetteCaldwell\",\"employee_salary\":345000,\"employee_age\":30,\"profile_image\":\"\"},{\"id\":22,\"employee_name\":\"YuriBerry\",\"employee_salary\":675000,\"employee_age\":40,\"profile_image\":\"\"},{\"id\":23,\"employee_name\":\"CaesarVance\",\"employee_salary\":106450,\"employee_age\":21,\"profile_image\":\"\"},{\"id\":24,\"employee_name\":\"DorisWilder\",\"employee_salary\":85600,\"employee_age\":23,\"profile_image\":\"\"}],\"message\":\"Successfully! All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        String employeeName = employeeService.deleteEmployee("1");

        // Actual Data does not have _mockData appended in the name of the employee for id 7
        Assertions.assertEquals("GP",employeeName);
    }

    @Test
    void testDeleteEmployeeEmptyResponse() {
        String restApiResponse = "{\"status\":\"success\",\"data\":[],\"message\":\"Successfully!All records has been fetched.\"}";
        restUtil.when(
                        () -> RestUtil.getCall(
                                "https://dummy.restapiexample.com/api/v1/employees",
                                String.class
                        ))
                .thenReturn(restApiResponse);

        Assertions.assertThrows(EmployeeNotFoundException.class, ()-> employeeService.deleteEmployee("100"));
    }
}
