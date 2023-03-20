package Controllers;

import Model.Appointment;
import Model.Contact;
import Queries.ContactQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
 * // Controller for viewing the schedules of contacts
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This controller class is responsible for viewing the appointments of specified contacts.
 * </p>
 */

public class ContactScheduleController implements Initializable {
    public ComboBox<Contact> contactNameComboBox;
    public Button backButton;
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

    ObservableList<Appointment> Appointments = FXCollections.observableArrayList();

    /**
     * This initializer method sets the values of the table and contact combo box.
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


        try {
            contactNameComboBox.setItems(ContactQuery.getAllContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
     * This method loads the correct values into the table based on the selected item in the combo box.
     * @throws SQLException SQLException
     */

    public void onContactNameComboBox() throws SQLException {
        customerAppointmentsTable.getItems().clear();

        try {
            Appointments.addAll(ContactQuery.getAllAppointments(contactNameComboBox.getSelectionModel().getSelectedItem()));
        } catch (SQLException e) {
            throw new SQLException();
        }

        customerAppointmentsTable.setItems(Appointments);
    }

}

