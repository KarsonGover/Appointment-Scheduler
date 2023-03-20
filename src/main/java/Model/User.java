package Model;

/**
 * Project: C195Assessment
 * Package: java.Model
 * // Base user class
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This is the base class for users of the program.
 * </p>
 */

public class User {
    private int userID;
    private String userName;
    private String password;

    /**
     * Constructor for the user class
     * @param userID the user ID to set
     * @param userName the username to set
     * @param password the password to set
     */

    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;

    }

    /**
     * Getter for the user ID
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for the user ID
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for the username
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the username
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
