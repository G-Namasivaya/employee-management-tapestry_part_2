PART 2 - The continuation of tapestry project

Implement some roles and permissions using a database table config. 
If an employee has Edit access only then show the edit button.
Roles are different than permissions 
Every person can have 1 role
Every person can have list of permissions
Admin role by default will have all permissions
Employee will have few permissions

Create a new js file to validate the wrong age of employee in the edit page.

Use various types of links - ActionLink, EventLink and PageLink is a single page and learn its usage.  
In Employee View page show 3 links 
Actionlink called [IMAGE] - on clicking this show any image of the employee in a popup.
EventLink called [PROMOTE]  - on clicking this the user should be promoted to Manager Designation. Display the new designation of the employee in the same view page.
This can be made using a component also and try the event bubbling concept here.
PageLink on the right hand side top corner that says [CLOSE] . On clicking this logout front the UI and go back to login screen.
 
Make a new component for an employee's birthday. 
If it is the employee's birthday then wish him Happy Birthday on the Employee View page.  Call this component -”EmpBday.java”.
If it is a girl employee show a happy birthday banner in Pink and if its a boy employee then show it in blue. For displaying the banner and text, create a new component called -”WishBanner.java” . Use the MarkupWriter concept in WishBanner to print the banner in UI.
Use the WishBanner component inside the EmpBday component.
Show use of Event Bubbling using a component nested inside another component

Create a search page where employee names and ID can be searched.
Use tsearch and tsvector approach in postgresql

Create a mixin in the project for showing autocomplete feature in the search text box of point 5 above in search page -- When user types in a letter or word the code automatically modifies a text field to provide for auto-completion of text using values retrieved from the server
