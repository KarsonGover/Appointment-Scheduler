package Queries;

import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Project: C195Assessment
 * Package: java.Queries
 * // SQL Queries for contacts
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 *<p>
 *     This class holds all SQL database queries for contacts
 *</p>
 */

public class ContactQuery {

    /**
     * This SQL query gets all contacts from the database
     * @return ObservableList(Contact)
     * @throws SQLException SQL Query
     */

    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sql = "SELECT * FROM contacts";
        Query.query(sql);
        ResultSet rs = Query.getResult();


        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact contact = new Contact(contactID, contactName, email);

            allContacts.add(contact);
        }

        DBConnection.closeConnection();

        return allContacts;
    }

    /**
     * This SQL query gets all appointments from the database from a specific contact
     * @param contact The contact the query uses to search for the contact in the database
     * @return ObservableList(Appointment)
     * @throws SQLException SQL Query
     */

    public static ObservableList<Appointment> getAllAppointments(Contact contact) throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID," +
                " User_ID, contacts.Contact_Name FROM appointments LEFT JOIN contacts ON" +
                " appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Contact_ID = '" + contact.getContactID() + "'";
        Query.query(sql);
        ResultSet rs = Query.getResult();


        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDate date = rs.getDate("Start").toLocalDate();
            LocalTime start = rs.getTime("Start").toLocalTime();
            LocalTime end = rs.getTime("End").toLocalTime();
            LocalDateTime startTime = LocalDateTime.of(date, start);
            LocalDateTime endTime = LocalDateTime.of(date, end);
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            Appointment appointmentResult = new Appointment(appointmentID, title, description, location,
                    type, startTime, endTime, customerID, userID, contact);

            allAppointments.add(appointmentResult);
        }

        DBConnection.closeConnection();

        return allAppointments;
    }


}
