package com.example.employeeapp.components;

import com.example.employeeapp.entities.Employee;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ComponentResources;
import java.util.Calendar;
import java.util.Date;

public class EmpBday {

    @Parameter(required = true)
    @Property
    private Employee employee;

    @Inject
    private ComponentResources componentResources;

    public boolean isBirthdayToday() {
        if (employee == null || employee.getDob() == null)
            return false;

        Calendar today = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(employee.getDob());

        return today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == dob.get(Calendar.DAY_OF_MONTH);
    }

}
