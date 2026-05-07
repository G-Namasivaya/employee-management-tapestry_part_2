package com.example.employeeapp.services;

import com.example.employeeapp.dao.EmployeeDao;
import com.example.employeeapp.entities.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public void save(Employee employee) {
        employeeDao.save(employee);
    }

    @Override
    public List<Employee> getEmployeeData() {
        return employeeDao.getEmployeeData();
    }

    @Override
    public Employee getById(Long id) {
        return employeeDao.getById(id);
    }
}
