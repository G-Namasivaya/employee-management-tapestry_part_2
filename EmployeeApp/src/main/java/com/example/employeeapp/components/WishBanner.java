package com.example.employeeapp.components;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.BeginRender;

public class WishBanner {

    @Parameter(required = true)
    private String gender;

    @BeginRender
    void beginRender(MarkupWriter writer) {
        String color = "M".equalsIgnoreCase(gender) || "BOY".equalsIgnoreCase(gender) ? "blue" : "pink";

        // Use MarkupWriter to directly write DOM elements
        writer.element("div", 
            "style", "background-color: " + color + "; color: white; padding: 15px; text-align: center; font-size: 20px; font-weight: bold; border-radius: 5px; margin-top: 10px;"
        );
        writer.write("🎉 Happy Birthday! 🎉");
        writer.end(); // close div
    }
}
