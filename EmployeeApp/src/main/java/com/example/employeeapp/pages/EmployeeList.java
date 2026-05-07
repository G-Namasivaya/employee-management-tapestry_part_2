package com.example.employeeapp.pages;

import com.example.employeeapp.entities.Employee;
import com.example.employeeapp.services.EmployeeService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import java.util.List;

public class EmployeeList {

    @SessionState(create = false)
    private Employee loggedInUser;
    private boolean loggedInUserExists;

    @Property
    private Employee currentEmployee;

    @Inject
    private EmployeeService employeeService;

    Object onActivate() {
        if (!loggedInUserExists) return Login.class;
        return null;
    }

    public List<Employee> getEmployees() {
        return employeeService.getEmployeeData();
    }

    // Role/Permission check for Edit Button
    public boolean isCanEdit() {
        return loggedInUserExists && loggedInUser.hasPermission("EDIT");
    }
}
