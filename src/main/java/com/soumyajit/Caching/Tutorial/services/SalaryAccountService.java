package com.soumyajit.Caching.Tutorial.services;

import com.soumyajit.Caching.Tutorial.entities.Employee;
import com.soumyajit.Caching.Tutorial.entities.SalaryAccount;

public interface SalaryAccountService {
    void createAccount(Employee employee);

    SalaryAccount incrementBalance(Long accountId);
}
