package com.example.employeeapp.pages;

import com.example.employeeapp.entities.Employee;
import org.apache.tapestry5.annotations.SessionState;

public class Logout {

    @SessionState(create = false)
    private Employee loggedInUser;
    private boolean loggedInUserExists;

    Object onActivate() {
        if (loggedInUserExists) {
            loggedInUser = null; // Clear session
        }
        return Login.class;
    }
}
