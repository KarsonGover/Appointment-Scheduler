package Model;

/**
 * Project: C195Assessment
 * Package: java.Model
 * // Base country class
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This is the base class for countries of the program.
 * </p>
 */

public class Country {

    private int countryID;
    private String countryName;

    /**
     * Constructor for the Country class
     * @param countryID the country ID to set
     * @param countryName the country name to set
     */

    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * Getter for the country ID
     * @return the countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /** Setter for the country ID
     * @param countryID the countryID to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Getter for the country name
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Setter for the country name
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Overrides the toString() method for user display
     * @return the country name in String format for the country combo box in the GUI (the variable is of type Country)
     */
    @Override
    public String toString() {
        return countryName;
    }
}
