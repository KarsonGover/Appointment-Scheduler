package Controllers;

import Model.Appointment;
import Model.FirstLevelDivision;
import Queries.*;
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
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Project: C195Assessment
 * Package: java.Controllers
 * // Controller for viewing the customer
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 *<p>
 *     This class is the controller for viewing the customer's personal information and appointments.
 *</p>
 */

public class ViewCustomerController implements Initializable {
    public TextField customerIDText;
    public TextField customerNameText;
    public TextField addressText;
    public TextField postalCodeText;
    public TextField phoneText;
    public ComboBox<FirstLevelDivision> divisionComboBox;
    public Button backButton;
    public TableColumn<Object, Object> typeColumn;
    public TableView<Model.Appointment> customerAppointmentsTable;
    public TableColumn<Object, Object> appointmentIDColumn;
    public TableColumn<Object, Object> titleColumn;
    public TableColumn<Object, Object> descriptionColumn;
    public TableColumn<Object, Object> locationColumn;
    public TableColumn<Object, Object> startTimeColumn;
    public TableColumn<Object, Object> endTimeColumn;
    public TableColumn<Object, Object> customerIDColumn;
    public TableColumn<Object, Object> userIDColumn;
    public TableColumn<Object, Object> contactNameColumn;
    public Button deleteCustomerButton;
    public Button deleteAppointmentButton;
    public ComboBox<Model.Country> countryComboBox;

    Appointment selectedAppointment;
    public static String nl = System.getProperty("line.separator");

    /**
     * This initializer method sets values of the text fields and tableview by calling SQL queries.
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        // Customer Information
        try {
            divisionComboBox.setItems(FirstLevelDivisionQuery.getAllDivisions());
            countryComboBox.setItems(CountryQuery.getAllCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        customerIDText.setText(String.valueOf(AllCustomersReportController.selectedCustomer.getId()));
        customerNameText.setText(AllCustomersReportController.selectedCustomer.getName());
        addressText.setText(AllCustomersReportController.selectedCustomer.getAddress());
        postalCodeText.setText(AllCustomersReportController.selectedCustomer.getPostalCode());
        phoneText.setText(AllCustomersReportController.selectedCustomer.getPhone());

        try {
            divisionComboBox.setValue(FirstLevelDivisionQuery.getDivision(AllCustomersReportController.selectedCustomer.getDivision()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            countryComboBox.setValue(CountryQuery.getCountryByDivisionID(divisionComboBox.getValue().getDivisionID()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Appointment Table
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
            customerAppointmentsTable.setItems(AppointmentQuery.getAllAppointmentsByCustomerID(Integer.parseInt(customerIDText.getText())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called when the delete appointment button is clicked. It deletes the selected appointment.
     * @throws SQLException IOException
     */

    public void onDeleteAppointmentButton() throws SQLException {
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
            customerAppointmentsTable.setItems(AppointmentQuery.getAllAppointmentsByCustomerID(Integer.parseInt(customerIDText.getText())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called when the delete customer button is clicked. It deletes the currently viewed customer.
     * The customer can only be deleted if there are no appointments scheduled for this customer.
     * @param actionEvent ActionEvent
     * @throws SQLException SQLException
     * @throws IOException IOException
     */

    public void onDeleteCustomerButton(ActionEvent actionEvent) throws SQLException, IOException {
        ObservableList<Appointment> appointments = customerAppointmentsTable.getItems();
        int customerID = Integer.parseInt(customerIDText.getText());

        if (!appointments.isEmpty()) {
            System.out.println("Delete Failed!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Customer");
            alert.setContentText("Customer failed to be deleted. Please delete all customer appointments before deleting customer.");
            alert.setHeaderText("Delete Failed");
            alert.showAndWait();
        }
        else {
            int rowsAffected = CustomerQuery.deleteCustomer.delete(customerID);

            if (rowsAffected > 0) {
                System.out.println("Delete Successful!");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Customer");
                alert.setContentText("Delete Successful!");
                alert.setHeaderText("Customer Deleted");
                alert.showAndWait();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/all-customers-report.fxml")));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Schedule");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } else {
                System.out.println("Delete Failed!");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Customer");
                alert.setContentText("Customer failed to be deleted. Please delete all customer appointments before deleting customer.");
                alert.setHeaderText("Delete Failed");
                alert.showAndWait();
            }
        }
    }

    /**
     * This method is called when the back button is clicked. It returns to the all-customers scene.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/all-customers-report.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
