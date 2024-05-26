package com.example.rqchallenge.employees.intergation;

import com.example.rqchallenge.employees.RqChallengeApplication;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.util.RawDataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RqChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {
    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testGetAllEmployees()
    {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/employees"),
                HttpMethod.GET, entity, String.class);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetEmployeeByName()
    {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/search/GP"),
                HttpMethod.GET, entity, String.class);

        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetEmployeeById()
    {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/employee/1"),
                HttpMethod.GET, entity, String.class);

        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testHighestSalary()
    {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/highestSalary"),
                HttpMethod.GET, entity, String.class);

        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testTopTenEmployees()
    {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/topTenHighestEarningEmployeeNames"),
                HttpMethod.GET, entity, String.class);

        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testCreateEmployee()
    {
        Employee employee = RawDataHelper.getEmployeeById("123");
        HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1"),
                HttpMethod.POST, entity, String.class);

        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteEmployee()
    {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/1"),
                HttpMethod.DELETE, entity, String.class);

        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
