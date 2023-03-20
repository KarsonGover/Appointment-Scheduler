package Controllers;

import Model.Appointment;
import Model.Contact;
import Model.CustomerAppointmentTimes;
import Queries.AppointmentQuery;
import Queries.ContactQuery;
import Queries.CustomerQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Project: C195Assessment
 * Package: java.Controllers
 * // Controller for updating the appointment
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This controller class is responsible for updating appointments in the mySQL database.
 * </p>
 */

public class UpdateAppointmentController implements Initializable {
    public Button addAppointmentButton;
    public TextField appointmentIDText;
    public TextField titleText;
    public TextField descriptionText;
    public TextField locationText;
    public TextField typeText;
    public TextField customerIDText;
    public ComboBox<LocalTime> startTimeComboBox;
    public ComboBox<LocalTime> endTimeComboBox;
    public Button backButton;
    public ComboBox<Contact> contactNameComboBox;
    public TextField userIDText;
    public DatePicker datePicker;
    public TableView<Appointment> customerAppointmentsTable;
    public TableColumn<Object, Object> appointmentIDColumn;
    public TableColumn<Object, Object> titleColumn;
    public TableColumn<Object, Object> descriptionColumn;
    public TableColumn<Object, Object> locationColumn;
    public TableColumn<Object, Object> typeColumn;
    public TableColumn<Object, Object> startTimeColumn;
    public TableColumn<Object, Object> endTimeColumn;
    public TableColumn<Object, Object> customerIDColumn;
    public TableColumn<Object, Object> userIDColumn;
    public TableColumn<Object, Object> contactNameColumn;

    LocalTime start1 = LocalTime.of(8, 0);
    LocalTime end1 = LocalTime.of(22, 0);
    LocalTime start2 = LocalTime.of(8, 30);
    LocalTime end2 = LocalTime.of(22, 30);

    /**
     * This initializer method sets values in the tableview, combo boxes, and text fields by calling SQL queries.
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appointmentIDText.setText(String.valueOf(MainScheduleController.selectedAppointment.getId()));
        titleText.setText(MainScheduleController.selectedAppointment.getTitle());
        descriptionText.setText(MainScheduleController.selectedAppointment.getDescription());
        locationText.setText(MainScheduleController.selectedAppointment.getLocation());
        typeText.setText(MainScheduleController.selectedAppointment.getType());
        customerIDText.setText(String.valueOf(MainScheduleController.selectedAppointment.getCustomerID()));
        userIDText.setText(String.valueOf(MainScheduleController.selectedAppointment.getUserID()));
        datePicker.setValue(MainScheduleController.selectedAppointment.getStartTime().toLocalDate());
        startTimeComboBox.getSelectionModel().select(MainScheduleController.selectedAppointment.getStartTime().toLocalTime());
        endTimeComboBox.getSelectionModel().select(MainScheduleController.selectedAppointment.getEndTime().toLocalTime());

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStartTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedEndTime"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            contactNameComboBox.setItems(ContactQuery.getAllContacts());
            customerAppointmentsTable.setItems(AppointmentQuery.getAllAppointments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Contact c : contactNameComboBox.getItems()) {
            if (MainScheduleController.selectedAppointment.getContact().getContactName().equals(c.getContactName())) {
                contactNameComboBox.setValue(c);
                break;
            }
        }


        while (start1.isBefore(end1) & end1 != null) {
            startTimeComboBox.getItems().add(start1);
            start1 = start1.plusMinutes(30);
        }

        while (start2.isBefore(end2) & end2 != null) {
            endTimeComboBox.getItems().add(start2);
            start2 = start2.plusMinutes(30);
        }

    }

    /**
     * This method is called when the update button is clicked. It updates the appointment in the database after checking all input fields for validity.
     * @param actionEvent Action Event
     * @throws SQLException SQLException
     * @throws IOException IOException
     */

    public void onUpdateButton(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            int appointmentID = Integer.parseInt(appointmentIDText.getText());
            String appointmentTitle = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            String type = typeText.getText();
            int customerID = Integer.parseInt(customerIDText.getText());
            LocalDate date = datePicker.getValue();
            LocalTime start = startTimeComboBox.getValue();
            LocalTime end = endTimeComboBox.getValue();
            Contact contact = contactNameComboBox.getValue();
            int userID = Integer.parseInt(userIDText.getText());

            if (!allFieldsFilled()) {
                Alert nullValue = new Alert(Alert.AlertType.ERROR);
                nullValue.setTitle("Empty Values");
                nullValue.setContentText("There was a problem adding the appointment. Please check and verify the values.");
                nullValue.showAndWait();
                return;
            }

            if (!checkIfCustomerExists(customerID)) {
                return;
            }

            if (checkIfWeekend(date)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Scheduling Error");
                alert.setContentText("An appointment cannot be scheduled on the weekends.");
                alert.showAndWait();
                return;
            }

            LocalDateTime startTime = LocalDateTime.of(date, start);
            LocalDateTime endTime = LocalDateTime.of(date, end);

            if (!checkOverlapTimes(customerID, appointmentID, startTime, endTime)) {
                return;
            }

            // Start/End time check
            if (!startEndCompare()) {
                Alert badLogin = new Alert(Alert.AlertType.ERROR);
                badLogin.setTitle("Start Time/End Time Conflict");
                badLogin.setContentText("Start time must be before end time.");
                badLogin.showAndWait();

                endTimeComboBox.setValue(startTimeComboBox.getValue().plusMinutes(30));
            }

            Appointment appointment = new Appointment(appointmentID, appointmentTitle, description, location, type, startTime, endTime, customerID, userID, contact);

            int rowsAffected = AppointmentQuery.updateAppointment(appointment);

            if (rowsAffected > 0) {
                System.out.println("Insert Successful!");
            } else {
                System.out.println("Insert Failed!");
            }

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/main-schedule.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Schedule");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (NumberFormatException e) {
            Alert nullValue = new Alert(Alert.AlertType.ERROR);
            nullValue.setTitle("Empty Values");
            nullValue.setContentText("There was a problem adding the appointment. Please check and verify the values.");
            nullValue.showAndWait();
        }
    }

    /**
     * This method is called when the back button is clicked. It returns to the mains screen.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/main-schedule.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method checks if all text boxes/selections are filled.
     * @return Returns true if all values are filled, false if otherwise.
     */

    public boolean allFieldsFilled() {
        if (titleText.getText().isEmpty()) {
            return false;
        }
        if (descriptionText.getText().isEmpty()) {
            return false;
        }
        if (locationText.getText().isEmpty()) {
            return false;
        }
        if (typeText.getText().isEmpty()) {
            return false;
        }
        if (customerIDText.getText().isEmpty()) {
            return false;
        }
        if (datePicker.getValue() == null) {
            return false;
        }
        if (startTimeComboBox.getValue() == null) {
            return false;
        }
        if (endTimeComboBox.getValue() == null) {
            return false;
        }
        if (contactNameComboBox.getValue() == null) {
            return false;
        }
        return !userIDText.getText().isEmpty();
    }

    /**
     * This method checks if the given customer exists.
     * @param customerID The ID of the customer that is being validated.
     * @return Returns true if the SQL query returns a row of the existing customer, returns false otherwise.
     * @throws SQLException SQLException
     */

    public static boolean checkIfCustomerExists(int customerID) throws SQLException {
        int customerRowsAffected = CustomerQuery.checkIfExistingCustomer(customerID);

        if (customerRowsAffected == 1) {
            System.out.println("Insert Successful!");
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer");
            alert.setContentText("The customer you provided does not exist.");
            alert.showAndWait();
            System.out.println("Insert Failed!");
            return false;
        }
    }

    /**
     * This method checks if the start time of the appointment is after or equal to the end time.
     * @return Returns true if the start time of the appointment is after or equal to the end time, false otherwise.
     */

    public boolean startEndCompare() {
        // Start/End time check
        if (startTimeComboBox.getValue() == null || endTimeComboBox.getValue() == null) {
            return true;
        }

        else return !startTimeComboBox.getValue().isAfter(endTimeComboBox.getValue()) && !startTimeComboBox.getValue().equals(endTimeComboBox.getValue());
    }

    /**
     * This method executes a query that gets all appointment times of the specified customer, then compares those values with the start/end times of the appointment the user wants to add.
     * If those two sets of times are overlapping, it throws an error that the user must fix.
     * @param id The ID of the customer
     * @param start The start time of the appointment
     * @param end The end time of the appointment
     * @return Returns true if the two sets of appointment times don't overlap, false otherwise.
     * @throws SQLException SQLException
     */

    public boolean checkOverlapTimes(int id, int appointmentID, LocalDateTime start, LocalDateTime end) throws SQLException {
        ObservableList<CustomerAppointmentTimes> allTimes = AppointmentQuery.getCustomerAppointmentTimes(id, appointmentID);

        for (CustomerAppointmentTimes times :  allTimes) {
            if ((start.isAfter(times.getStart()) & start.isBefore(times.getEnd())) || start.equals(times.getStart())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Conflict");
                alert.setContentText("This customer already has an appointment during the given time. Please choose a different time." + System.getProperty("line.separator"));
                alert.showAndWait();
                return false;
            }
            else if ((end.isAfter(times.getStart()) & end.isBefore(times.getEnd())) || end.equals(times.getEnd())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Conflict");
                alert.setContentText("This customer already has an appointment during the given time. Please choose a different time." + System.getProperty("line.separator"));
                alert.showAndWait();
                return false;
            }
            else if (start.isBefore(times.getStart()) & end.isAfter(times.getEnd())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Overlap");
                alert.setContentText("This customer already has an appointment during the given time. Please choose a different time." + System.getProperty("line.separator"));
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks if the date of the appointment the user want to add is on the weekend, which is outside business hours.
     * @param date The date of the appointment the user wants to add
     * @return Returns true if the day of the appointment lies on a weekend, false otherwise.
     */

    public boolean checkIfWeekend(LocalDate date) {
        DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
