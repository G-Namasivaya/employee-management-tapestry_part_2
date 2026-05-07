package com.example.employeeapp.dao;

import com.example.employeeapp.entities.Employee;
import java.util.List;

public interface EmployeeDao {
    void save(Employee employee);
    List<Employee> getEmployeeData();
    Employee getById(Long id);
    Employee findByCredentials(String username, String password);
    List<Employee> search(String query);
    
    com.example.employeeapp.entities.Role getRoleByName(String name);
    com.example.employeeapp.entities.Permission getPermissionByName(String name);
}
