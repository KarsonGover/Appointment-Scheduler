package Controllers;

import Model.Appointment;
import Queries.AppointmentQuery;
import Queries.DBConnection;
import Queries.UserQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Project: C195Assessment
 * Package: java.Controllers
 * // Controller for the login view
 * <p>
 * User: Karson Gover
 * Date: 02/08/2023
 * Time: 4:22 PM
 * <p>
 * Created with IntelliJ IDEA
 * <p>
 *     This controller class is responsible for logging the user into the program and writing login data into login_activity.txt.
 * </p>
 */

public class LoginController implements Initializable {

    public TextField usernameText;
    public Button loginButton;
    public Label countryLabel;
    public PasswordField passwordText;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label loginLabel;
    public Label loggingInFromLabel;
    ResourceBundle rb = ResourceBundle.getBundle("com/example/c195assessment/RB", Locale.getDefault());

    private static String user;

    /**
     * This method logs the user into the program when then log in button is clicked.
     * It also reads the username and date, then prints the information and successful/failed login into a .txt file in the root folder.
     * @param actionEvent ActionEvent
     * @throws SQLException SQLException
     * @throws IOException IOException
     */

    public void onLogin(ActionEvent actionEvent) throws SQLException, IOException {
        DBConnection.openConnection();

        String username = usernameText.getText();
        String password = passwordText.getText();

        String fileName = "login_activity.text";

        FileWriter fileWriter = new FileWriter(fileName, true);

        PrintWriter outputFile = new PrintWriter(fileWriter);

        try {
            if (UserQuery.login(username, password)) {
                Locale.setDefault(new Locale("en", "US"));
                user = usernameText.getText();

                outputFile.println("   " + getUsername() + "   |   " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/yyyy  H:m:s")) + "   |   " + "Success   ");
                outputFile.close();
                System.out.println("File Written!");

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/c195assessment/main-schedule.fxml")));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Schedule");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

                // 15-minute appointment reminder when logging in
                ObservableList<Appointment> appointment =  AppointmentQuery.getAllAppointments();
                ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();

                for (Appointment a : appointment) {
                    if (LocalDateTime.now().plusMinutes(15).isAfter(a.getStartTime()) & a.getStartTime().isAfter(LocalDateTime.now())) {
                        upcomingAppointments.add(a);
                    }
                }
                if (!upcomingAppointments.isEmpty()) {
                    for (Appointment a : upcomingAppointments) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("15-minute Appointment Reminder");
                        alert.setContentText("You have an upcoming appointment within 15 minutes." + System.getProperty("line.separator") +
                                "Appointment ID: " + a.getId() + System.getProperty("line.separator") + "Start Time: " + a.getFormattedStartTime());
                        alert.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Upcoming Appointments");
                    alert.setContentText("You have no upcoming appointments.");
                    alert.showAndWait();
                }
            }
            else {
                outputFile.println("   " + getUsername() + "   |   " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/yyyy  H:m:s")) + "   |   " + "Failed   ");
                outputFile.close();
                System.out.println("File Written!");

                Alert badLogin = new Alert(Alert.AlertType.ERROR);
                badLogin.setTitle(rb.getString("Error"));
                badLogin.setContentText(rb.getString("bad-login"));
                badLogin.showAndWait();
            }
        } catch (IOException e) {
            Alert badLogin = new Alert(Alert.AlertType.ERROR);
            badLogin.setTitle(rb.getString("Error"));
            badLogin.setContentText(rb.getString("Error loading main screen form."));
            badLogin.showAndWait();
        }
    }

    /**
     * This initializer method sets the text in this form to French/English based on the region of the user's system.
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
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        loginLabel.setText(rb.getString("login"));
        loggingInFromLabel.setText(rb.getString("logging-in-from"));
        countryLabel.setText(rb.getString("country"));
    }

    /**
     * Getter for the username the user entered when attempting to log in.
     * @return Returns the username.
     */

    public static String getUsername() {
        return user;
    }
}
