package Model;

import Queries.CountryQuery;
import Queries.FirstLevelDivisionQuery;

import java.sql.SQLException;

/**
 * Project: C195Assessment
 * Package: java.Model
 * // Base customer class
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This is the base class for customers of the program.
 * </p>
 */

public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int division;

    /**
     * Constructor for the Customer class
     * @param id the ID to set
     * @param name the name to set
     * @param address the address to set
     * @param postalCode the postal code to set
     * @param phone the phone to set
     * @param division the first-level division to set (state)
     */

    public Customer (int id, String name, String address, String postalCode, String phone, int division) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
    }

    /**
     * Getter for the id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the id
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the address
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for the address
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for the postal code
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for the postal code
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter for the phone
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for the phone
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for the first-level division
     * @return the division
     */
    public int getDivision() {
        return division;
    }

    /**
     * Setter for the first-level division
     * @param division the division to set
     */
    public void setDivision(int division) {
        this.division = division;
    }

    /**
     * This method is a database SQL query that pulls the country name from the customer database with a specific first-level division
     * @return Returns the country name of type String
     * @throws SQLException this query throws a SQL Exception
     */

    public String getCountry() throws SQLException {
        return CountryQuery.getCountryNameByDivision(division);
    }

    /**
     * This method is a database SQL query that pulls the name of a first-level division from the customer database with the provided first-level division ID
     * @return Returns the first-level division name of type String
     * @throws SQLException this query throws a SQL Exception
     */

    public String getDivisionName() throws SQLException {
        return FirstLevelDivisionQuery.getDivision(division).getDivisionName();
    }
}
