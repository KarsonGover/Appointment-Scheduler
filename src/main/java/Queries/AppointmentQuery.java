package Queries;


import Controllers.LoginController;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * Project: C195Assessment
 * Package: java.Queries
 * // SQL Queries for appointments
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 *<p>
 *     This class holds all SQL database queries for appointments
 *</p>
 */

public abstract class AppointmentQuery implements AppointmentDAO {

    /**
     * This is a lambda expression from the AppointmentDAO interface that deletes an appointment given the appointment ID
     */

    public static AppointmentDAO delete = id -> {
        try {
            return AppointmentQuery.deleteAppointment(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    /**
     * This SQL query gets all appointments from the database
     * @return ObservableList(Appointment)
     * @throws SQLException SQL Query
     */

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID," +
                " User_ID, contacts.Contact_Name, contacts.Contact_ID, contacts.Email FROM appointments LEFT JOIN contacts ON" +
                " appointments.Contact_ID = contacts.Contact_ID";
        Query.query(sql);
        ResultSet rs = Query.getResult();

        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            String contactName = rs.getString("Contact_Name");
            int contactID = rs.getInt("Contact_ID");
            String contactEmail = rs.getString("Email");

            Contact contact = new Contact(contactID, contactName, contactEmail);

            Appointment appointmentResult = new Appointment(appointmentID, title, description, location,
                                                    type, startTime, endTime, customerID, userID, contact);

            allAppointments.add(appointmentResult);
        }

        DBConnection.closeConnection();

        return allAppointments;
    }

    /**
     * This SQL query gets the count of all appointments by type and by month
     * @param filterName The filter of the data (type/month)
     * @return ObservableList(ItemCount)
     * @throws SQLException SQL Query
     */

    public static ObservableList<ItemCount> getAllAppointmentsTypeMonth(String filterName) throws SQLException {
        ObservableList<ItemCount> allCounts = FXCollections.observableArrayList();
        DBConnection.openConnection();

        if (filterName.equals("Type")) {

            String sql = "SELECT Type, COUNT(*) AS Count FROM appointments GROUP BY Type";
            Query.query(sql);
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                String type = rs.getString("Type");
                int count = rs.getInt("Count");

                ItemCount itemCount = new ItemCount(type, count);

                allCounts.add(itemCount);
            }
        }

        else if (filterName.equals("Month")) {
            String sql = "SELECT MONTHNAME(Start) AS Start, COUNT(*) AS Count FROM client_schedule.appointments GROUP BY MONTHNAME(Start)";
            Query.query(sql);
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                String month = rs.getString("Start");
                int count = rs.getInt("Count");

                ItemCount itemCount = new ItemCount(month, count);
                allCounts.add(itemCount);
            }
        }
        DBConnection.closeConnection();
        return allCounts;
    }

    /**
     * This SQL query gets all appointments by country
     * @param filter The country filter of the data (U.S/UK/Canada)
     * @return ObservableList(Appointment)
     * @throws SQLException SQL Query
     */

    public static ObservableList<Appointment> getAllAppointmentsByCountry (Country filter) throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBConnection.openConnection();

        switch (filter.getCountryName()) {
            case "U.S", "UK", "Canada" -> {

                String sql = """
                        SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, c.Contact_Name, c.Contact_ID, c.Email, countries.Country
                        FROM client_schedule.appointments a
                        \tLEFT JOIN client_schedule.contacts c ON a.Contact_ID = c.Contact_ID
                        \tLEFT JOIN client_schedule.customers cu ON a.Customer_ID = cu.Customer_ID
                        \tLEFT JOIN client_schedule.first_level_divisions ON cu.Division_ID = first_level_divisions.Division_ID
                        \tLEFT JOIN client_schedule.countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID
                        WHERE Country = '""" + filter + "'";

                Query.query(sql);
                ResultSet rs = Query.getResult();

                while (rs.next()) {
                    int appointmentID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");

                    String contactName = rs.getString("Contact_Name");
                    int contactID = rs.getInt("Contact_ID");
                    String contactEmail = rs.getString("Email");

                    Contact contact = new Contact(contactID, contactName, contactEmail);

                    Appointment appointment = new Appointment(appointmentID, title, description, location,
                            type, startTime, endTime, customerID, userID, contact);

                    allAppointments.add(appointment);
                }
            }
        }

        DBConnection.closeConnection();
        return allAppointments;
    }

    /**
     * This SQL query inserts an appointment into the database
     * @param appointment The appointment object that gets inserted into the database
     * @return Returns an int from the executeUpdate() method, returns amount of rows affected by the query
     * @throws SQLException SQL Query
     */

    public static int addAppointment(Appointment appointment) throws SQLException {
        DBConnection.openConnection();
        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartTime()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getEndTime()));
        ps.setString(7, LoginController.getUsername());
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(9, LoginController.getUsername());
        ps.setInt(10, appointment.getCustomerID());
        ps.setInt(11, appointment.getUserID());
        ps.setInt(12, appointment.getContact().getContactID());

        return ps.executeUpdate();
    }

    /**
     * This SQL query updates an appointment in the database
     * @param appointment The appointment object that gets updated from the database
     * @return Returns an int from the executeUpdate() method, returns amount of rows affected by the query
     * @throws SQLException SQL Query
     */

    public static int updateAppointment(Appointment appointment) throws SQLException {
        DBConnection.openConnection();

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartTime()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getEndTime()));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, LoginController.getUsername());
        ps.setInt(9, appointment.getCustomerID());
        ps.setInt(10, appointment.getUserID());
        ps.setInt(11, appointment.getContact().getContactID());
        ps.setInt(12, appointment.getId());


        return ps.executeUpdate();
    }

    /**
     * This SQL query deletes an appointment from the database
     * @param appointmentID The appointment ID the query uses to search for the appointment
     * @return Returns an int from the executeUpdate() method, returns amount of rows affected by the query
     * @throws SQLException SQL Query
     */

    public static int deleteAppointment(int appointmentID) throws SQLException {
        DBConnection.openConnection();

        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setInt(1, appointmentID);

        return ps.executeUpdate();
    }

    /**
     * This SQL query gets all appointments by from a customer
     * @param customerID The customer ID that is used to search for the appointments associated with it
     * @return ObservableList(Appointment)
     * @throws SQLException SQL Query
     */

    public static ObservableList<Appointment> getAllAppointmentsByCustomerID(int customerID) throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, a.Contact_ID, c.Contact_Name, c.Email\n" +
                "FROM client_schedule.appointments AS a\n" +
                "\tLEFT JOIN client_schedule.contacts AS c ON a.Contact_ID = c.Contact_ID\n" +
                "WHERE a.Customer_ID = " + customerID;

        Query.query(sql);
        ResultSet rs = Query.getResult();

        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int cID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            String contactName = rs.getString("Contact_Name");
            int contactID = rs.getInt("Contact_ID");
            String contactEmail = rs.getString("Email");

            Contact contact = new Contact(contactID, contactName, contactEmail);

            Appointment appointmentResult = new Appointment(appointmentID, title, description, location,
                    type, startTime, endTime, cID, userID, contact);

            allAppointments.add(appointmentResult);
        }

        return allAppointments;
    }

    /**
     * This SQL query gets all appointments within the current month
     * @param currentMonth This parameter holds the value of the current month of the system's calendar
     * @return ObservableList(Appointment)
     * @throws SQLException SQL Query
     */

    public static ObservableList<Appointment> getAllAppointmentsByMonth(Month currentMonth) throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, a.Contact_ID, c.Contact_Name, c.Email\n" +
                "FROM client_schedule.appointments AS a\n" +
                "\tLEFT JOIN client_schedule.contacts AS c ON a.Contact_ID = c.Contact_ID\n" +
                "WHERE MONTHNAME(Start) = '" + currentMonth + "'";

        Query.query(sql);
        ResultSet rs = Query.getResult();

        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int cID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            String contactName = rs.getString("Contact_Name");
            int contactID = rs.getInt("Contact_ID");
            String contactEmail = rs.getString("Email");

            Contact contact = new Contact(contactID, contactName, contactEmail);

            Appointment appointmentResult = new Appointment(appointmentID, title, description, location,
                    type, startTime, endTime, cID, userID, contact);

            allAppointments.add(appointmentResult);
        }

        return allAppointments;
    }

    /**
     * This SQL query gets all appointments within the current week (7 days from the current day)
     * @return ObservableList(Appointment)
     * @throws SQLException SQL Query
     */

    public static ObservableList<Appointment> getAllAppointmentsByWeek() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sql = """
                SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, a.Contact_ID, c.Contact_Name, c.Email
                FROM client_schedule.appointments AS a
                \tLEFT JOIN client_schedule.contacts AS c ON a.Contact_ID = c.Contact_ID
                WHERE a.Start < date_add(current_date(), INTERVAL 7 DAY)""";

        Query.query(sql);
        ResultSet rs = Query.getResult();

        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int cID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            String contactName = rs.getString("Contact_Name");
            int contactID = rs.getInt("Contact_ID");
            String contactEmail = rs.getString("Email");

            Contact contact = new Contact(contactID, contactName, contactEmail);

            Appointment appointmentResult = new Appointment(appointmentID, title, description, location,
                    type, startTime, endTime, cID, userID, contact);

            allAppointments.add(appointmentResult);
        }

        return allAppointments;
    }

    /**
     * This SQL query gets all appointment times from the specified customer. It's used to check overlaps when adding an appointment
     * @param customerID The customer ID that is used to search for the customer in the database
     * @return ObservableList(CustomerAppointmentTimes)
     * @throws SQLException SQL Query
     */

    public static ObservableList<CustomerAppointmentTimes> getCustomerAppointmentTimes(int customerID) throws SQLException {
        DBConnection.openConnection();
        ObservableList<CustomerAppointmentTimes> allTimes = FXCollections.observableArrayList();
        String sql = "SELECT Start, End FROM client_schedule.appointments WHERE Customer_ID = " + customerID;
        Query.query(sql);
        ResultSet rs = Query.getResult();

        while (rs.next()) {
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

            CustomerAppointmentTimes result = new CustomerAppointmentTimes(start, end);

            allTimes.add(result);
        }

        return allTimes;
    }

    /**
     * This SQL query gets all appointment times from the specified customer. It's used to check overlaps when updating an appointment. The appointment ID is a parameter to prevent the query from including the
     * appointment being updated. Without it, the appointment times would always overlap
     * @param customerID The customer ID that is used to search for the customer in the database
     * @param appointmentID The appointment ID that is used to search for the appointment being updated, to exclude it from the query
     * @return ObservableList(CustomerAppointmentTimes)
     * @throws SQLException SQL Query
     */

    public static ObservableList<CustomerAppointmentTimes> getCustomerAppointmentTimes(int customerID, int appointmentID) throws SQLException {
        DBConnection.openConnection();
        ObservableList<CustomerAppointmentTimes> allTimes = FXCollections.observableArrayList();
        String sql = "SELECT Start, End FROM client_schedule.appointments WHERE Customer_ID = " + customerID + " AND Appointment_ID != " + appointmentID;
        Query.query(sql);
        ResultSet rs = Query.getResult();

        while (rs.next()) {
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

            CustomerAppointmentTimes result = new CustomerAppointmentTimes(start, end);

            allTimes.add(result);
        }

        return allTimes;
    }
}

