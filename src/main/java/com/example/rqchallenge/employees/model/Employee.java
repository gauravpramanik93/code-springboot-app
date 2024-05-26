package com.example.rqchallenge.employees.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee implements Serializable{

    private static final long serialVersionUID = 2066898942344634291L;
    @JsonProperty("id")
    private String id;
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_salary")
    private Integer salary;
    @JsonProperty("employee_age")
    private Integer age;
    @JsonProperty("profile_image")
    private String profileImage;
}