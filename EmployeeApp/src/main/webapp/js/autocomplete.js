function initCustomAutocomplete(spec) {
    var inputElement = document.getElementById(spec.inputId);
    if (!inputElement) return;

    var container = document.createElement("div");
    container.className = "autocomplete-suggestions";
    container.style.display = "none";
    inputElement.parentNode.appendChild(container);

    var currentFocus = -1;

    inputElement.addEventListener("input", function(e) {
        var val = this.value;
        closeAllLists();
        if (!val) return false;
        
        currentFocus = -1;
        
        // Make AJAX request to Tapestry Event Link
        var xhr = new XMLHttpRequest();
        var separator = spec.url.indexOf('?') !== -1 ? '&' : '?';
        xhr.open("GET", spec.url + separator + "t:ac=" + encodeURIComponent(val), true);
        xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        
        xhr.onload = function() {
            if (xhr.status === 200) {
                // Tapestry returns JSON array
                var response = JSON.parse(xhr.responseText);
                
                // Usually Tapestry wraps JSON responses or just returns the bare array based on configuration.
                // If it returns a bare array:
                var suggestions = response;
                
                if (suggestions && suggestions.length > 0) {
                    container.style.display = "block";
                    container.style.width = inputElement.offsetWidth + "px";
                    
                    suggestions.forEach(function(match) {
                        var div = document.createElement("div");
                        div.className = "autocomplete-suggestion";
                        div.innerHTML = match;
                        
                        div.addEventListener("click", function(e) {
                            inputElement.value = this.innerText.split(" (ID:")[0];
                            closeAllLists();
                        });
                        container.appendChild(div);
                    });
                }
            }
        };
        xhr.send();
    });

    function closeAllLists() {
        while (container.firstChild) {
            container.removeChild(container.firstChild);
        }
        container.style.display = "none";
    }

    document.addEventListener("click", function (e) {
        if (e.target !== inputElement) closeAllLists();
    });
}
