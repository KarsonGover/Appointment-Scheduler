package Model;

/**
 * Project: C195Assessment
 * Package: java.Model
 * // Base contact class
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This is the base class for contacts of the program.
 * </p>
 */

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Constructor for the Contact class
     * @param contactID the contact ID to set
     * @param contactName the contact name to set
     * @param email the email to set
     */

    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Getter for the contact ID
     * @return the contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter for the contact ID
     * @param contactID the contactID to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter for the contact name
     * @return the contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for the contact name
     * @param contactName the contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for the email
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the email
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Overrides the toString() method for user display
     * @return the contact name in String format for the contact combo box in the GUI (the variable is of type Contact)
     */

    @Override
    public String toString() {
        return contactName;
    }


}
