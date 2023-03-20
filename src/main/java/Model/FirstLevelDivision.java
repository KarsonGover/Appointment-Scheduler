package Model;

/**
 * Project: C195Assessment
 * Package: java.Model
 * // Base first-level division class
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This is the base class for first-level divisions of the program.
 * </p>
 */

public class FirstLevelDivision {

    private int divisionID;
    private String divisionName;
    private int countryID;

    /**
     * Constructor for the FirstLevelDivision class
     * @param divisionID the division id to set
     * @param divisionName the division name to set
     * @param countryID the country ID to set
     */

    public FirstLevelDivision(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * Getter for the division ID
     * @return the divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter for the division ID
     * @param divisionID the divisionID to set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Getter for the division name
     * @return the divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Setter for the division name
     * @param divisionName the divisionName to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Getter for the country ID
     * @return the countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter for the country ID
     * @param countryID the countryID to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Overrides the toString() method for user display
     * @return the division name in String format for the division combo box in the GUI
     */

    @Override
    public String toString() {
        return divisionName;
    }
}
