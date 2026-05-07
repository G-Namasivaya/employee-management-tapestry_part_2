package com.example.employeeapp.pages;

import com.example.employeeapp.entities.Employee;
import com.example.employeeapp.dao.EmployeeDao;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import java.util.List;

public class SearchEmployee {

    @SessionState(create = false)
    private Employee loggedInUser;
    private boolean loggedInUserExists;

    @Property
    private String searchQuery;

    @Property
    private List<Employee> searchResults;

    @Property
    private Employee currentEmployee;

    @Inject
    private EmployeeDao employeeDao;

    Object onActivate() {
        if (!loggedInUserExists) return Login.class;
        return null;
    }

    void onSuccessFromSearchForm() {
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            searchResults = employeeDao.search(searchQuery);
        }
    }
}
