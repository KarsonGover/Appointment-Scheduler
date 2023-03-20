package Model;

import java.time.*;

/**
 * Project: C195Assessment
 * Package: java.Model
 * // Base appointment class
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This is the base class for appointments of the program.
 * </p>
 */

public class Appointment {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int customerID;
    private int userID;
    private Contact contact;

    /**
     * Constructor for the Appointment class.
     * @param id the appointment ID to set
     * @param title the title to set
     * @param description the description to set
     * @param location the location to set
     * @param type the type to set
     * @param startTime the start time to set
     * @param endTime the end time to set
     * @param customerID the customer ID to set
     * @param userID the user ID to set
     * @param contact the contact ID to set
     */

    public Appointment (int id, String title, String description, String location, String type,
                        LocalDateTime startTime, LocalDateTime endTime, int customerID, int userID, Contact contact) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contact = contact;
    }

    /**
     * Getter for the Appointment ID
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the Appointment ID
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for the title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the title
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Getter for the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Getter for the location
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for the location
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Getter for the type
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for the type
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /** Getter for the start time
     * @return the startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /** Getter for the formatted start time (M/d/yyyy h:m:s)
     * @return the formattedStartTime for correct display on tableview
     */
    public String getFormattedStartTime() {
        return startTime.toLocalDate().toString() + " " + startTime.toLocalTime().toString();
    }

    /**
     * Setter for the start time
     * @param startTime the startTime to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** Getter for the end time
     * @return the endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /** Getter for the formatted end time (M/d/yyyy h:m:s)
     * @return the formattedEndTime for correct display on tableview
     */
    public String getFormattedEndTime() {
        return endTime.toLocalDate().toString() + " " + endTime.toLocalTime().toString();
    }

    /**
     * Setter for the end time
     * @param endTime the endTime to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /** Getter for the customer ID
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter for the customer ID
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** Getter for the user ID
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

    /** Getter for the contact
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Setter for the contact
     * @param contact the contact to set
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

}
