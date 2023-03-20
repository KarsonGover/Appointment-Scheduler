package Queries;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Project: C195Assessment
 * Package: java.Queries
 * // SQL Queries for users
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 *<p>
 *     This class holds all SQL database queries for users
 *</p>
 */

public abstract class UserQuery {

    /**
     * This SQL query searches for a user in the database that matches the credentials the user provided when logging in
     * @param username the username the user is attempting to log in with
     * @param password the password the user is attempting to log in with
     * @return Returns the result of the query; either it returns the username and password of the matched user on success or returns null for both values if there is no match
     * @throws SQLException SQL query
     */

    public static boolean login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = '" + username + "' and Password = '" + password + "'";
        Query.query(sql);
        ResultSet rs = Query.getResult();

        return rs.next();
    }
}