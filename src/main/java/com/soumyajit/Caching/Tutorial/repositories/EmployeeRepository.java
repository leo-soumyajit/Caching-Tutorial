package com.soumyajit.Caching.Tutorial.repositories;

import com.soumyajit.Caching.Tutorial.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByEmail(String email);
}
