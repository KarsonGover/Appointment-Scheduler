package Controllers;

import Model.Customer;
import Queries.CustomerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
 * // Controller for viewing all customers
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This controller class is responsible for viewing all customers and their information in a table and enabling the user to add, view, or delete customers.
 * </p>
 */

public class AllCustomersReportController implements Initializable {
    public Button backButton;
    public Button addCustomerButton;
    public Button updateCustomerButton;
    public Button viewCustomerButton;
    public TableView<Model.Customer> customerTable;
    public TableColumn<Object, Object> addressColumn;
    public TableColumn<Object, Object> postalCodeColumn;
    public TableColumn<Object, Object> phoneColumn;
    public TableColumn<Object, Object> divisionColumn;
    public TableColumn<Object, Object> customerNameColumn;
    public TableColumn<Object, Object> customerIDColumn;
    public TableColumn<Object, Object> countryColumn;

    public static Customer selectedCustomer;

    /**
     * This initializer method sets the values of the table.
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
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        try {
            customerTable.setItems(CustomerQuery.getAllCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
     * This method is called when the add button is clicked. It loads the add customer scene.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/add-customer.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method is called when the update button is clicked. It loads the update customer scene.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/update-customer.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method is called when the view button is clicked. It loads the view customer scene.
     * @param actionEvent Action Event
     * @throws IOException IOException
     */

    public void onViewCustomer(ActionEvent actionEvent) throws IOException {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/view-customer.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
