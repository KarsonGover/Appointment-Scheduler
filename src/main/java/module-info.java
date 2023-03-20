module com.example.c195assessment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    exports Controllers;
    opens Controllers to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
    opens com.example.c195assessment to javafx.fxml;
    exports com.example.c195assessment;
}