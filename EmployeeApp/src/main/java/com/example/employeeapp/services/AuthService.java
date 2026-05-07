package com.example.employeeapp.services;

import com.example.employeeapp.entities.Employee;

public interface AuthService {
    Employee authenticate(String username, String password);
}
