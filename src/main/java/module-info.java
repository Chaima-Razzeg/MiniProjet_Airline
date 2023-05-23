module com.example.airline {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.airline to javafx.fxml;
    exports com.example.airline;

    opens com.example.airline.entities to javafx.base;

}