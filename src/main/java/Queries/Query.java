package Queries;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Project: C195Assessment
 * Package: java.Queries
 * // SQL ResultSet Query
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 *<p>
 *     This class holds the query preparation for ResultSets
 *</p>
 */

public class Query {

    private static ResultSet result;

    /**
     * This method executes the result set query.
     * @param q The SQL statement.
     */

    public static void query (String q) {
        try {
            Statement statement = DBConnection.connection.createStatement();
            // determine query execution
            if(q.startsWith("SELECT"))
                result = statement.executeQuery(q);
            if(q.startsWith("DELETE") || q.startsWith("INSERT") || q.startsWith("UPDATE"))
                statement.executeUpdate(q);

        } catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    /**
     * This method returns the result of the SQL statement.
     * @return Returns the result of the SQL statement.
     */

    public static ResultSet getResult(){
        return result;
    }
}

