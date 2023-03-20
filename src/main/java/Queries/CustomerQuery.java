package Queries;

import Controllers.LoginController;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Project: C195Assessment
 * Package: java.Queries
 * // SQL Queries for customers
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 *<p>
 *     This class holds all SQL database queries for customers
 *</p>
 */

public abstract class CustomerQuery implements CustomerDAO {

    /**
     * This is a lambda expression from the CustomerDAO interface that deletes a customer given the customer ID.
     */

    public static

            CustomerDAO deleteCustomer = id -> {
        try {
            return CustomerQuery.deleteCustomer(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };


    /**
     * This SQL query gets all customers from the database.
     * @return ObservableList(Customer)
     * @throws SQLException SQL Query
     */

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        DBConnection.openConnection();
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, countries.Country FROM customers c " +
                    "LEFT JOIN first_level_divisions fld ON c.Division_ID = fld.Division_ID " +
                    "LEFT JOIN countries ON fld.Country_ID = countries.Country_ID";
        Query.query(sql);
        ResultSet rs = Query.getResult();

        while (rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int division = rs.getInt("Division_ID");

            Customer customer = new Customer(id, name, address, postal, phone, division);

            allCustomers.add(customer);
        }

        DBConnection.closeConnection();

        return allCustomers;
    }

    /**
     * This method inserts a customer into the database.
     * @param customer The customer object being inserted.
     * @return Returns an int of the rows affected in the database; if the insert was successful or not.
     * @throws SQLException SQLException
     */

    public static int addCustomer(Customer customer) throws SQLException {
        DBConnection.openConnection();

        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setString(5, LoginController.getUsername());
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(7, LoginController.getUsername());
        ps.setInt(8, customer.getDivision());

        return ps.executeUpdate();
    }

    /**
     * This method updates a customer from the database.
     * @param customer The customer object being updated
     * @return Returns an int of the rows affected in the database; if the update was successful or not.
     * @throws SQLException SQLException
     */

    public static int updateCustomer(Customer customer) throws SQLException {
        DBConnection.openConnection();
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = " + customer.getId();

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(6, LoginController.getUsername());
        ps.setInt(7, customer.getDivision());

        return ps.executeUpdate();
    }

    /**
     * This method deletes a customer from the database.
     * @param customerID The customer ID of the customer being searched.
     * @return Returns an int of the rows affected in the database; if delete was successful or not.
     * @throws SQLException SQLException
     */

    public static int deleteCustomer(int customerID) throws SQLException {
        DBConnection.openConnection();
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setInt(1, customerID);

        return ps.executeUpdate();
    }

    /**
     * This method checks if the customer already exists in the database.
     * @param customerID The ID of the customer being searched.
     * @return Returns 1 or 0; 1 if the customer exists, 0 if otherwise.
     * @throws SQLException SQLException
     */

    public static int checkIfExistingCustomer(int customerID) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT Customer_ID FROM customers WHERE Customer_ID = " + customerID;
        Query.query(sql);
        ResultSet rs = Query.getResult();
        int returnTrue;

        if (rs.next()) {
            returnTrue = 1;
        }
        else {
            returnTrue = 0;
        }

        return returnTrue;
    }
}
