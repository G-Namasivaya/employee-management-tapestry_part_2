package com.example.employeeapp.pages;

import com.example.employeeapp.dao.EmployeeDao;
import com.example.employeeapp.entities.Employee;
import com.example.employeeapp.entities.Permission;
import com.example.employeeapp.services.EmployeeService;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.upload.services.UploadedFile;

public class AddEmployee {

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

    @InjectComponent("employeeForm")
    private Form form;

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

    void onPrepareForRender() {
        if (employee == null) {
            employee = new Employee();
        }
    }

    void onPrepareForSubmit() {
        if (employee == null) {
            employee = new Employee();
        }
    }

    void onValidateFromEmployeeForm() {
        if (employee.getAge() != null && employee.getAge() <= 0) {
            form.recordError("Age must be greater than 0.");
        }
    }

    Object onSuccessFromEmployeeForm() {
        // Map Role
        if (selectedRole != null && !selectedRole.isEmpty()) {
            employee.setRole(employeeDao.getRoleByName(selectedRole));
        }

        // Map Permissions
        if (selectedPermissions != null && !selectedPermissions.isEmpty()) {
            Set<Permission> perms = new HashSet<>();
            for (String pName : selectedPermissions) {
                perms.add(employeeDao.getPermissionByName(pName));
            }
            employee.setPermissions(perms);
        }

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
