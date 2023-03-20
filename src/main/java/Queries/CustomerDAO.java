package Queries;

import java.sql.SQLException;

/**
 * Project: C195Assessment
 * Package: java.Queries
 * // Interface for deleting customers
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 *<p>
 *     This class holds a delete method for customers
 *</p>
 */

public interface CustomerDAO {
    int delete(int id) throws SQLException;
}
