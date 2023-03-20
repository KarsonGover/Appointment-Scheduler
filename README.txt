Title: Appointment Scheduler

This application is an appointment scheduler that enables the users to add, update, delete appointments and customers. The user is able to run multiple reports and queries that connect to a mysql database.

Author: Karson Gover
Date: 3/20/2023
Contact Information:
			
			kgover6@wgu.edu
			(360) 742-2557

Student Application Version: QAM2 TASK 1: JAVA APPLICATION DEVELOPMENT

IDE: IntelliJ IDEA 2022.3.3 (Community Edition)
JDK: Oracle OpenJDK version 17.0.5
JavaFX: JavaFX version 17.0.2
MYSQL: com.mysql:mysql-connector-j:8.0.32

Directions:
	
	This program has very basic tasks the user can accomplish with simple text field inputs and buttons. 

	Login: The user opens the program to see a login screen with username and password text fields, with the text on the screen changing to English/French depending on the local system's region. Log in to proceed.

	Main page: Once the user logs in, they may get an appointment reminder if there is an appointment scheduled to start within the next 15 minutes of them logging in. They can view all schedules from the local database through three filters; All (all appointments), Month (all appointments within the current month), and Week (all appointments seven days from the current day). There are buttons on the top of the screen;
		
		- "Appointments By Country": This button pulls a report that enables the user to select a country from a drop-down combo box and view all appointments and the total amount of appointments of the selected country. ***ADDITIONAL REPORT***

		- "Contact Schedules": This button pulls a report that enables the user to select a contact from a drop-down combo box and view all appointments of the selected contact.

		- "Total Appointments Report": This button pulls a report that enables the user to select a filter of either "Type" or "Month" from a drop-down combo box and view the total amount of appointments of the selected filter.

		- "All Customer Records": This button lets the user view all customers in the local database in a table view on the screen. The user has access to viewing a customer's personal information and appointments, adding a customer, updating an existing customer, and deleting a customer (in order to delete a customer, all appointments associated with the customer must be deleted first".

	Main page (Continued): The user will also see buttons near the bottom of the screen:
		
		- "Add Appointment": This button allows the user to fill in text fields to add an appointment to the database.

		- "Update Appointment": This button allows the user to alter pre-existing text fields to update an appointment within the database.
	
		- "Delete Appointment": This buttons allows the user to delete the selected appointment.


