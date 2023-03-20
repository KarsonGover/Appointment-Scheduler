package Controllers;

import Model.Appointment;
import Queries.AppointmentQuery;
import javafx.collections.FXCollections;
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
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Project: C195Assessment
 * Package: java.Controllers
 * // Controller for the 'main hub' of the application. It views the appointment schedule and leads the user to other parts of the program
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This controller class is responsible for viewing the appointment schedule and leading the user to other parts of the program.
 * </p>
 */

public class MainScheduleController implements Initializable {

    public ComboBox<String> weekMonthAllComboBox;
    public Label welcomeUserLabel;
    public Button customerRecordsButton;
    public Button appointmentReportsButton;
    public Button contactScheduleButton;
    public Button deleteAppointmentButton;
    public Button updateAppointmentButton;
    public Button addAppointmentButton;
    public Button appointmentsByCountryButton;
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

    LocalDate date = LocalDate.now();
    Month currentMonth = date.getMonth();

    ObservableList<String> options = FXCollections.observableArrayList("All", "Week", "Month");

    public static Appointment selectedAppointment;
    String user = LoginController.getUsername();
    String nl = System.getProperty("line.separator");

    /**
     * This initializer method sets the values of the table, filter combo box, fills the 'Welcome, user' label text with the user's username,
     * and notifies the user if an appointment is starting within the next 15 minutes.
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        weekMonthAllComboBox.setItems(options);
        weekMonthAllComboBox.getSelectionModel().selectFirst();
        welcomeUserLabel.setText("Welcome, " + user);

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
            customerAppointmentsTable.setItems(AppointmentQuery.getAllAppointments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called when the add appointment button is clicked. It loads the add appointment scene.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/add-appointment.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method is called when the update appointment button is clicked. It loads the update appointment scene.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        selectedAppointment = customerAppointmentsTable.getSelectionModel().getSelectedItem();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/update-appointment.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method is called when the delete appointment button is clicked. It deletes the selected appointment.
     * @throws SQLException IOException
     */

    public void onDeleteButton() throws SQLException {
        selectedAppointment = customerAppointmentsTable.getSelectionModel().getSelectedItem();

        int appointmentID = selectedAppointment.getId();

        int rowsAffected = AppointmentQuery.delete.delete(appointmentID);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Appointment");
        if (rowsAffected > 0) {
            alert.setContentText("Appointment ID: " + selectedAppointment.getId() + nl + "Appointment Type: " + selectedAppointment.getType() + nl);
            alert.setHeaderText("Appointment Successfully Deleted");
        } else {
            alert.setContentText("Delete Failed!");
            alert.setHeaderText("Appointment failed to be deleted");
        }
        alert.showAndWait();

        try {
            customerAppointmentsTable.setItems(AppointmentQuery.getAllAppointments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is called when the all customers button is clicked. It loads the all customers scene.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onAllCustomers(ActionEvent actionEvent) throws IOException {
        selectedAppointment = customerAppointmentsTable.getSelectionModel().getSelectedItem();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/all-customers-report.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method is called when the contact schedule button is clicked. It loads the contact schedule scene.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onContactSchedule(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/contact-schedule.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method is called when the total appointments button is clicked. It loads the total appointments scene.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onTotalAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/total-appointments-report.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method is called when the appointments by country button is clicked. It loads the appointments by country scene.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onAppointmentsByCountry (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/appointments-by-country-report.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method is called when the filter combo box is selected. It sets the values of the table with the selected filter.
     * @throws SQLException SQLException
     */

    public void onWeekMonthAllComboBox() throws SQLException {
        customerAppointmentsTable.getItems().clear();

        if (weekMonthAllComboBox.getValue().equals("All")) {
            customerAppointmentsTable.setItems(AppointmentQuery.getAllAppointments());
        }
        else if (weekMonthAllComboBox.getValue().equals("Month")) {
            customerAppointmentsTable.setItems(AppointmentQuery.getAllAppointmentsByMonth(currentMonth));
        }
        else if (weekMonthAllComboBox.getValue().equals("Week")) {
            customerAppointmentsTable.setItems(AppointmentQuery.getAllAppointmentsByWeek());
        }

    }

}
