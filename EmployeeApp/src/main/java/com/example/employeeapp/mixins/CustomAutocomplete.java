package com.example.employeeapp.mixins;

import com.example.employeeapp.dao.EmployeeDao;
import com.example.employeeapp.entities.Employee;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.http.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.util.TextStreamResponse;

import java.util.List;

public class CustomAutocomplete {

    @Inject
    private ComponentResources resources;

    @InjectContainer
    private TextField textField;

    @Inject
    private EmployeeDao employeeDao;

    @AfterRender
    void afterRender(MarkupWriter writer) {
        Link link = resources.createEventLink("autocomplete");
        String clientId = textField.getClientId();
        String url = link.toURI();
        
        writer.element("script", "type", "text/javascript");
        writer.writeRaw(
            "setTimeout(function() {\n" +
            "  var input = document.getElementById('" + clientId + "');\n" +
            "  if (!input) return;\n" +
            "  var container = document.createElement('div');\n" +
            "  container.className = 'autocomplete-suggestions';\n" +
            "  container.style.display = 'none';\n" +
            "  input.parentNode.appendChild(container);\n" +
            "  input.addEventListener('input', function(e) {\n" +
            "      var val = this.value;\n" +
            "      container.innerHTML = '';\n" +
            "      if (!val) { container.style.display = 'none'; return; }\n" +
            "      var xhr = new XMLHttpRequest();\n" +
            "      var sep = '" + url + "'.indexOf('?') !== -1 ? '&' : '?';\n" +
            "      xhr.open('GET', '" + url + "' + sep + 't:ac=' + encodeURIComponent(val), true);\n" +
            "      xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');\n" +
            "      xhr.onload = function() {\n" +
            "          if (xhr.status === 200) {\n" +
            "              var suggestions = JSON.parse(xhr.responseText);\n" +
            "              if (suggestions && suggestions.length > 0) {\n" +
            "                  container.style.display = 'block';\n" +
            "                  container.style.width = input.offsetWidth + 'px';\n" +
            "                  suggestions.forEach(function(match) {\n" +
            "                      var div = document.createElement('div');\n" +
            "                      div.className = 'autocomplete-suggestion';\n" +
            "                      div.innerHTML = match;\n" +
            "                      div.addEventListener('click', function(e) {\n" +
            "                          input.value = this.innerText.split(' (ID:')[0];\n" +
            "                          container.style.display = 'none';\n" +
            "                      });\n" +
            "                      container.appendChild(div);\n" +
            "                  });\n" +
            "              } else { container.style.display = 'none'; }\n" +
            "          }\n" +
            "      };\n" +
            "      xhr.send();\n" +
            "  });\n" +
            "  document.addEventListener('click', function(e) { if (e.target !== input) container.style.display = 'none'; });\n" +
            "}, 100);"
        );
        writer.end();
    }

    @OnEvent("autocomplete")
    Object onAutocomplete(@RequestParameter(value = "t:ac", allowBlank = true) String query) {
        JSONArray matches = new JSONArray();
        if (query != null && !query.trim().isEmpty()) {
            List<Employee> results = employeeDao.search(query);
            for (Employee emp : results) {
                matches.put(emp.getName() + " (ID: " + emp.getId() + ")");
            }
        }
        return new TextStreamResponse("application/json", matches.toString());
    }
}
