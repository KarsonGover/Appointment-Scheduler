package Queries;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Project: C195Assessment
 * Package: java.Queries
 * // SQL Queries for countries
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 *<p>
 *     This class holds all SQL database queries for countries.
 *</p>
 */

public class CountryQuery {

    /**
     * This SQL query gets all countries from the database.
     * @return ObservableList(Contact)
     * @throws SQLException SQL Query
     */

    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sql = "SELECT * FROM countries";
        Query.query(sql);
        ResultSet rs = Query.getResult();


        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");

            Country country = new Country(countryID, countryName);

            allCountries.add(country);
        }

        DBConnection.closeConnection();

        return allCountries;
    }

    /**
     * This method gets a country object based on the given country ID.
     * @param countryID The ID of the country being searched for.
     * @return Returns a country object from the database.
     * @throws SQLException SQLException
     */

    public static Country getCountry(int countryID) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT * FROM countries WHERE Country_ID = " + countryID;
        Query.query(sql);
        ResultSet rs = Query.getResult();


        rs.next();
        int id = rs.getInt("Country_ID");
        String countryName = rs.getString("Country");

        Country country = new Country(id, countryName);

        DBConnection.closeConnection();

        return country;
    }

    /**
     * This method gets the country name based on the given first-level division ID.
     * @param divisionID The ID of the first-level division being searched for.
     * @return Returns the country name from the database of the specified division.
     * @throws SQLException SQLException
     */


    public static String getCountryNameByDivision(int divisionID) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT c.Country FROM client_schedule.countries AS c LEFT JOIN client_schedule.first_level_divisions AS fld ON c.Country_ID = fld.COUNTRY_ID WHERE fld.Division_ID = " + divisionID;
        Query.query(sql);
        ResultSet rs = Query.getResult();

        rs.next();

        return rs.getString("Country");
    }

    /**
     * This method gets a country object based on the given first-level division ID.
     * @param divisionID The ID of the first-level division being searched for.
     * @return Returns a country object from the database.
     * @throws SQLException SQLException
     */

    public static Country getCountryByDivisionID (int divisionID) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT c.Country_ID, c.Country FROM client_schedule.countries AS c LEFT JOIN client_schedule.first_level_divisions AS fld ON c.Country_ID = fld.COUNTRY_ID WHERE fld.Division_ID = " + divisionID;
        Query.query(sql);
        ResultSet rs = Query.getResult();

        rs.next();
        int id = rs.getInt("Country_ID");
        String name = rs.getString("Country");

        return new Country(id, name);
    }

    /**
     * This method returns the total amount of appointments within a specific country.
     * @param countryID The country ID being searched for.
     * @return Returns the total amount of appointments by country.
     * @throws SQLException SQLException
     */

    public static int getCountryCount (int countryID) throws SQLException {
        int count = 0;
        DBConnection.openConnection();
        String sql = """
                    SELECT COUNT(*) AS count
                    FROM client_schedule.appointments a
                    \tLEFT JOIN client_schedule.contacts c ON a.Contact_ID = c.Contact_ID
                    \tLEFT JOIN client_schedule.customers cust ON a.Customer_ID = cust.Customer_ID
                    \tLEFT JOIN client_schedule.first_level_divisions ON cust.Division_ID = first_level_divisions.Division_ID
                    \tLEFT JOIN client_schedule.countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID
                    WHERE countries.Country_ID = '""" + countryID + "'";
        Query.query(sql);
        ResultSet rs = Query.getResult();

        while (rs.next()) {
            count = rs.getInt("count");
        }

        return count;
    }
}
