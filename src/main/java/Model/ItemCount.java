package Model;

/**
 * Project: C195Assessment
 * Package: java.Model
 * // Base item count class
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This class holds information for the total amount of appointments of the appropriate filter type.
 * </p>
 */

public class ItemCount {

    private String filter;
    private int count;

    /**
     * Constructor for the ItemCount class
     * @param filter the filter to set
     * @param count the count to set
     */

    public ItemCount(String filter, int count) {
        this.filter = filter;
        this.count = count;
    }

    /**
     * Getter for the search filter (appointment type or month)
     * @return the filter
     */

    public String getFilter() {
        return filter;
    }

    /**
     * Setter for the filter
     * @param filterName the filter name to set
     */

    public void setFilter(String filterName) {
        this.filter = filterName;
    }

    /**
     * Getter for the total count of appointments
     * @return the count
     */

    public int getCount() {
        return count;
    }

    /**
     * Setter for the total count of appointments
     * @param count the count to set
     */

    public void setCount(int count) {
        this.count = count;
    }
}
