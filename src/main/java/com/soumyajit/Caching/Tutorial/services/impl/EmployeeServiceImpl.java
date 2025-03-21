package com.soumyajit.Caching.Tutorial.services.impl;

import com.soumyajit.Caching.Tutorial.dto.EmployeeDto;
import com.soumyajit.Caching.Tutorial.entities.Employee;
import com.soumyajit.Caching.Tutorial.exceptions.ResourceNotFoundException;
import com.soumyajit.Caching.Tutorial.repositories.EmployeeRepository;
import com.soumyajit.Caching.Tutorial.services.EmployeeService;
import com.soumyajit.Caching.Tutorial.services.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final SalaryAccountService salaryAccountService;

    @Override
    @Cacheable(cacheNames = "employees",key = "#id")
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
        log.info("Successfully fetched employee with id: {}", id);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    @CachePut(cacheNames = "employees",key="#result.id")
    @Transactional
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        log.info("Creating new employee with email: {}", employeeDto.getEmail());
        List<Employee> existingEmployees = employeeRepository.findByEmail(employeeDto.getEmail());

        if (!existingEmployees.isEmpty()) {
            log.error("Employee already exists with email: {}", employeeDto.getEmail());
            throw new RuntimeException("Employee already exists with email: " + employeeDto.getEmail());
        }
        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(newEmployee);

        salaryAccountService.createAccount(savedEmployee);

        log.info("Successfully created new employee with id: {}", savedEmployee.getId());
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    @CachePut(cacheNames = "employees",key="#id")
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Updating employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });

        if (!employee.getEmail().equals(employeeDto.getEmail())) {
            log.error("Attempted to update email for employee with id: {}", id);
            throw new RuntimeException("The email of the employee cannot be updated");
        }

        modelMapper.map(employeeDto, employee);
        employee.setId(id);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated employee with id: {}", id);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    @CacheEvict(cacheNames = "employees",key="#id")
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        boolean exists = employeeRepository.existsById(id);
        if (!exists) {
            log.error("Employee not found with id: {}", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }

        employeeRepository.deleteById(id);
        log.info("Successfully deleted employee with id: {}", id);
    }
}