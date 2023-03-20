package Controllers;

import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import Queries.CountryQuery;
import Queries.CustomerQuery;
import Queries.FirstLevelDivisionQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Project: C195Assessment
 * Package: java.Controllers
 * // Controller for adding a customer
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This controller class is responsible for adding customers to the mySQL database.
 * </p>
 */

public class AddCustomerController implements Initializable {
    public TextField customerIDText;
    public Button backButton;
    public Button addCustomerButton;
    public TextField customerNameText;
    public TextField addressText;
    public TextField postalCodeText;
    public TextField phoneText;
    public ComboBox<FirstLevelDivision> divisionComboBox;
    public ComboBox<Country> countryComboBox;

    /**
     * This initializer method sets values of the combo boxes by calling SQL queries.
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
        try {
            divisionComboBox.setItems(FirstLevelDivisionQuery.getAllDivisions());
            countryComboBox.setItems(CountryQuery.getAllCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called when the add button is clicked. It adds the customer to the database after checking all input fields for validity.
     * @param actionEvent Action Event
     * @throws SQLException SQLException
     * @throws IOException IOException
     */

    public void onAddCustomer(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            int id = 0;
            String name = customerNameText.getText();
            String address = addressText.getText();
            String postal = postalCodeText.getText();
            String phone = phoneText.getText();
            int division = divisionComboBox.getValue().getDivisionID();

            if (!allFieldsFilled()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Values");
                alert.setContentText("There was a problem adding the appointment. Please check and verify the values.");
                alert.showAndWait();
                return;
            }

            Customer customer = new Customer(id, name, address, postal, phone, division);

            int rowsAffected = CustomerQuery.addCustomer(customer);

            if (rowsAffected > 0) {
                System.out.println("Insert Successful!");
            } else {
                System.out.println("Insert Failed!");
            }

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/all-customers-report.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Schedule");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (NullPointerException e) {
            Alert nullValue = new Alert(Alert.AlertType.ERROR);
            nullValue.setTitle("Empty Values");
            nullValue.setContentText("There was a problem adding the appointment. Please check and verify the values.");
            nullValue.showAndWait();
        }
    }

    /**
     * This method fills the division combo box with the necessary first-level divisions from the selected country.
     * @throws SQLException SQLException
     */

    public void onDivisionComboBoxSelection() throws SQLException {
        if (countryComboBox.getSelectionModel().isEmpty()) {
            Country country = CountryQuery.getCountry(divisionComboBox.getValue().getCountryID());
            countryComboBox.setValue(country);
        }
    }

    /**
     * This method fills the country combo box with the country from the selected first-level division.
     * @throws SQLException SQLException
     */

    public void onCountryComboBoxSelection() throws SQLException {
        if (divisionComboBox.getSelectionModel().isEmpty()) {
            ObservableList<FirstLevelDivision> fld = FirstLevelDivisionQuery.getFilteredDivisions(countryComboBox.getValue().getCountryID());
            divisionComboBox.setItems(fld);
        } else if (divisionComboBox.getValue().getCountryID() != countryComboBox.getValue().getCountryID()) {
            ObservableList<FirstLevelDivision> fld = FirstLevelDivisionQuery.getFilteredDivisions(countryComboBox.getValue().getCountryID());
            divisionComboBox.setItems(fld);
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

    /**
     * This method checks if all text boxes/selections are filled.
     * @return Returns true if all values are filled, false if otherwise.
     */

    public boolean allFieldsFilled() {
        if (customerNameText.getText().isEmpty()) {
            return false;
        }
        if (addressText.getText().isEmpty()) {
            return false;
        }
        if (postalCodeText.getText().isEmpty()) {
            return false;
        }
        if (phoneText.getText().isEmpty()) {
            return false;
        }
        return divisionComboBox.getValue() != null;
    }
}
