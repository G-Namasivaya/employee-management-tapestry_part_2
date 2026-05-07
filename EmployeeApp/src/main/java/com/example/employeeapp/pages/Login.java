package com.example.employeeapp.pages;

import com.example.employeeapp.services.AuthService;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.annotations.SessionState;
import com.example.employeeapp.entities.Employee;

public class Login {

    @Property
    private String username;

    @Property
    private String password;

    @SessionState
    private Employee loggedInUser;

    @Inject
    private AuthService authService;

    @InjectComponent("loginForm")
    private Form form;

    void onValidateFromLoginForm() {
        if (username != null && password != null) {
            Employee emp = authService.authenticate(username, password);
            if (emp == null) {
                form.recordError("Invalid user name or password.");
            } else {
                loggedInUser = emp;
            }
        }
    }

    Object onSuccessFromLoginForm() {
        return EmployeeList.class;
    }
}
