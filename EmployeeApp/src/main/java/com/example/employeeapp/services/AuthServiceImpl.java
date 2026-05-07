package com.example.employeeapp.services;

import com.example.employeeapp.dao.EmployeeDao;
import com.example.employeeapp.entities.Employee;
import com.example.employeeapp.entities.Role;

public class AuthServiceImpl implements AuthService {

    private final EmployeeDao employeeDao;

    public AuthServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee authenticate(String username, String password) {
        // Fallback 1: Hardcoded Admin (Can Edit everything)
        if ("admin".equals(username) && "admin".equals(password)) {
            Employee admin = new Employee();
            admin.setId(1001L);
            admin.setUsername("admin");
            admin.setName("System Administrator");
            admin.setDesignation("Director");
            admin.setRole(new Role("ADMIN"));
            return admin;
        }

        // Fallback 2: Hardcoded standard user (Cannot Edit)
        if ("user".equals(username) && "user".equals(password)) {
            Employee user = new Employee();
            user.setId(1002L);
            user.setUsername("user");
            user.setName("Standard Employee");
            user.setDesignation("Clerk");
            // No ADMIN role, no permissions = can't edit
            return user;
        }

        // Otherwise check database
        return employeeDao.findByCredentials(username, password);
    }
}
