package Controllers;

import Model.ItemCount;
import Queries.AppointmentQuery;
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
 * // Controller for viewing the total appointments by type and month
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This controller class is responsible for viewing the total number of appointments by type and by month.
 * </p>
 */

public class AppointmentReportsController implements Initializable {
    public Button backButton;
    public ComboBox<String> monthTypeComboBox;
    public TableView<ItemCount> totalAppointmentsTable;
    public TableColumn<Object, Object> filterByColumn;
    public TableColumn<Object, Object> totalColumn;


    ObservableList<String> options = FXCollections.observableArrayList("Type", "Month");
    ObservableList<ItemCount> counts = FXCollections.observableArrayList();

    /**
     * This initializer method sets the values of the table and filter combo box.
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

        monthTypeComboBox.setItems(options);

        filterByColumn.setCellValueFactory(new PropertyValueFactory<>("filter"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
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
     */

    public void onMonthTypeComboBox() {
        totalAppointmentsTable.getItems().clear();

        if (monthTypeComboBox.getSelectionModel().getSelectedItem().equals("Type")) {
            filterByColumn.setText("Type");
        } else if (monthTypeComboBox.getSelectionModel().getSelectedItem().equals("Month")) {
            filterByColumn.setText("Month");
        }

        try {
            counts.addAll(AppointmentQuery.getAllAppointmentsTypeMonth(monthTypeComboBox.getSelectionModel().getSelectedItem()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        totalAppointmentsTable.setItems(counts);
    }

}

