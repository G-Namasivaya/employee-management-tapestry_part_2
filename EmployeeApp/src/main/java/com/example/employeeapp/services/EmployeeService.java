package com.example.employeeapp.services;

import com.example.employeeapp.entities.Employee;
import java.util.List;

public interface EmployeeService {
    void save(Employee employee);
    List<Employee> getEmployeeData();
    Employee getById(Long id);
}
