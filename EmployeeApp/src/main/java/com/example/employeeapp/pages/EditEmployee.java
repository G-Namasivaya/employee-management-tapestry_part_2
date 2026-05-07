package com.example.employeeapp.pages;

import com.example.employeeapp.dao.EmployeeDao;
import com.example.employeeapp.entities.Employee;
import com.example.employeeapp.entities.Permission;
import com.example.employeeapp.services.EmployeeService;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.upload.services.UploadedFile;

public class EditEmployee {

    @SessionState(create = false)
    private Employee loggedInUser;
    private boolean loggedInUserExists;

    @Property
    private Employee employee;

    @Property
    private String selectedRole;

    @Property
    private List<String> selectedPermissions;

    @Property
    private UploadedFile uploadedImage;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private EmployeeDao employeeDao;

    @InjectComponent
    private Form editForm;

    public List<String> getRoleOptions() {
        return Arrays.asList("ADMIN", "EMPLOYEE", "MANAGER");
    }

    public List<String> getPermissionOptions() {
        return Arrays.asList("EDIT", "VIEW");
    }

    public ValueEncoder<String> getStringEncoder() {
        return new ValueEncoder<String>() {
            @Override
            public String toClient(String value) { return value; }
            @Override
            public String toValue(String clientValue) { return clientValue; }
        };
    }

    // Security Check: Only people with "EDIT" permission or ADMIN role can enter this page
    Object onActivate(Long id) {
        if (!loggedInUserExists || !loggedInUser.hasPermission("EDIT")) {
            return EmployeeList.class; // Redirect to list if unauthorized
        }
        
        employee = employeeService.getById(id);
        if (employee == null) {
            return EmployeeList.class;
        }

        // Prepopulate Role and Permissions into the UI fields
        if (employee.getRole() != null) {
            selectedRole = employee.getRole().getName();
        }
        
        selectedPermissions = new ArrayList<>();
        if (employee.getPermissions() != null) {
            for (Permission p : employee.getPermissions()) {
                selectedPermissions.add(p.getName());
            }
        }

        return null;
    }

    Long onPassivate() {
        return employee != null ? employee.getId() : null;
    }

    Object onSuccessFromEditForm() {
        // Map Role
        if (selectedRole != null && !selectedRole.isEmpty()) {
            employee.setRole(employeeDao.getRoleByName(selectedRole));
        } else {
            employee.setRole(null);
        }

        // Map Permissions
        Set<Permission> perms = new HashSet<>();
        if (selectedPermissions != null) {
            for (String pName : selectedPermissions) {
                perms.add(employeeDao.getPermissionByName(pName));
            }
        }
        employee.setPermissions(perms);

        // Handle Image Upload
        if (uploadedImage != null) {
            try {
                InputStream is = uploadedImage.getStream();
                byte[] bytes = IOUtils.toByteArray(is);
                employee.setImageData(bytes);
                employee.setImageType(uploadedImage.getContentType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        employeeService.save(employee);
        return EmployeeList.class;
    }
}
