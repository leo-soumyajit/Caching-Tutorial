package com.soumyajit.Caching.Tutorial.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeDto{
    private Long id;
    private String email;
    private String name;
    private Long salary;
}
