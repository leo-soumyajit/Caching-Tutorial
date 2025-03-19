package com.soumyajit.Caching.Tutorial.controllers;

import com.soumyajit.Caching.Tutorial.dto.EmployeeDto;
//import com.soumyajit.Caching.Tutorial.entities.SalaryAccount;
import com.soumyajit.Caching.Tutorial.services.EmployeeService;
//import com.soumyajit.Caching.Tutorial.services.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDto);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto createdEmployeeDto = employeeService.createNewEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployeeDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/incrementBalance/{accountId}")
//    public ResponseEntity<SalaryAccount> incrementBalance(@PathVariable Long accountId) {
//        SalaryAccount salaryAccount = salaryAccountService.incrementBalance(accountId);
//        return ResponseEntity.ok(salaryAccount);
//    }
}
