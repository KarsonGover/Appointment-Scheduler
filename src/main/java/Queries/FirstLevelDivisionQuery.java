package Queries;

import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Project: C195Assessment
 * Package: java.Queries
 * // SQL Queries for first-level divisions
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 *<p>
 *     This class holds all SQL database queries for first-level divisions
 *</p>
 */

public class FirstLevelDivisionQuery {

    /**
     * This method gets all the first-level divisions from the database.
     * @return Returns an ObservableList(FirstLevelDivision).
     * @throws SQLException SQLException
     */

    public static ObservableList<FirstLevelDivision> getAllDivisions() throws SQLException {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sql = "SELECT * FROM first_level_divisions";
        Query.query(sql);
        ResultSet rs = Query.getResult();


        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("COUNTRY_ID");

            FirstLevelDivision fld = new FirstLevelDivision(divisionID, divisionName, countryID);

            allDivisions.add(fld);
        }

        DBConnection.closeConnection();

        return allDivisions;
    }

    /**
     * This method gets all the first-level divisions of the specified country from the database.
     * @param countryID The country ID being searched.
     * @return Returns an ObservableList(FirstLevelDivision).
     * @throws SQLException SQLException
     */

    public static ObservableList<FirstLevelDivision> getFilteredDivisions(int countryID) throws SQLException {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sql = "SELECT fld.Division_ID, fld.Division, fld.COUNTRY_ID FROM client_schedule.first_level_divisions AS fld WHERE fld.COUNTRY_ID IN " +
                "(SELECT c.Country_ID FROM client_schedule.countries AS c WHERE c.Country_ID = " + countryID + ")";

        Query.query(sql);
        ResultSet rs = Query.getResult();

        while (rs.next()) {
            int id = rs.getInt("Division_ID");
            String name = rs.getString("Division");
            int cntryID = rs.getInt("COUNTRY_ID");

            FirstLevelDivision fld = new FirstLevelDivision(id, name, cntryID);

            allDivisions.add(fld);
        }

        return allDivisions;
    }

    /**
     * This method gets the first-level division of the specified division ID from the database.
     * @param divisionID The division ID being searched.
     * @return Returns a first-level division object.
     * @throws SQLException SQLException
     */

    public static FirstLevelDivision getDivision(int divisionID) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT Division_ID, Division, COUNTRY_ID FROM client_schedule.first_level_divisions WHERE Division_ID = " + divisionID;
        Query.query(sql);
        ResultSet rs = Query.getResult();

        rs.next();
        int id = rs.getInt("Division_ID");
        String name = rs.getString("Division");
        int countryID = rs.getInt("COUNTRY_ID");

        return new FirstLevelDivision(id, name, countryID);
    }
}
