package com.example.employeeapp.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PromoteButton {

    @Parameter(required = true)
    private Long employeeId;

    @Inject
    private ComponentResources componentResources;

    // Triggered when EventLink [PROMOTE] is clicked
    void onPromote() {
        // We trigger an event that bubbles up to the container page
        componentResources.triggerEvent("promoteToManager", new Object[] { employeeId }, null);
    }
}
