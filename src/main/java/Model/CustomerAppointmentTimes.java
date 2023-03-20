package Model;

import java.time.LocalDateTime;

/**
 * Project: C195Assessment
 * Package: java.Model
 * // Base customer appointment start/end times class
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This class holds the start and end times of a specific customer.
 * </p>
 */

public class CustomerAppointmentTimes {
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Constructor for the CustomerAppointmentTimes class
     * @param start the start time to set
     * @param end the end time to set
     */

    public CustomerAppointmentTimes (LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Getter for the start time
     * @return the start time
     */

    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Setter for the start time
     * @param start the start time to set
     */

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Getter for the end time
     * @return the end time
     */

    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Setter for the end time
     * @param end the end time to set
     */

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
