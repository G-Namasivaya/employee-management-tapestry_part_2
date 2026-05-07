package com.example.employeeapp.pages;

import com.example.employeeapp.entities.Employee;
import com.example.employeeapp.services.EmployeeService;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import java.util.Base64;

public class EmployeeDetail {

    @SessionState(create = false)
    private Employee loggedInUser;
    private boolean loggedInUserExists;

    @Property
    private Employee employee;

    @Property
    private boolean showImagePopup;

    @Property
    private String message;

    @Inject
    private EmployeeService employeeService;

    @InjectComponent
    private Zone imageZone;

    @InjectComponent
    private Zone detailZone;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    Object onActivate(Long id) {
        if (!loggedInUserExists)
            return Login.class;
        employee = employeeService.getById(id);
        if (employee == null) {
            return EmployeeList.class;
        }
        return null;
    }

    Long onPassivate() {
        return employee != null ? employee.getId() : null;
    }

    // [IMAGE] ActionLink triggered
    void onActionFromShowImage() {
        showImagePopup = true;
        ajaxResponseRenderer.addRender(imageZone);
    }

    void onActionFromCloseImage() {
        showImagePopup = false;
        ajaxResponseRenderer.addRender(imageZone);
    }

    // [PROMOTE] Bubbled Event from PromoteButton
    @OnEvent("promoteToManager")
    void handlePromoteEvent(Long empId) {
        Employee empToPromote = employeeService.getById(empId);
        if (empToPromote != null) {
            empToPromote.setDesignation("Manager");
            employeeService.save(empToPromote);
            employee = empToPromote; // update current context
            message = "Employee successfully promoted to Manager!";
        }
        ajaxResponseRenderer.addRender(detailZone);
    }

    public boolean isShowImagePopup() {
        return showImagePopup;
    }

    public String getImageDataUri() {
        if (employee != null && employee.getImageData() != null) {
            String type = employee.getImageType() != null ? employee.getImageType() : "image/jpeg";
            String b64 = Base64.getEncoder().encodeToString(employee.getImageData());
            return "data:" + type + ";base64," + b64;
        }
        return null;
    }
}
