document.addEventListener("DOMContentLoaded", function() {
    const editForm = document.getElementById("editForm");
    const ageInput = document.getElementById("ageInput");

    if (editForm && ageInput) {
        editForm.addEventListener("submit", function(event) {
            let ageValue = parseInt(ageInput.value, 10);
            
            if (isNaN(ageValue) || ageValue < 18 || ageValue > 100) {
                alert("Invalid Age! Please enter a valid age between 18 and 100.");
                event.preventDefault(); // Stop form submission
            }
        });
    }
});
