module com.example.reservationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires javafx.media;
    requires java.sql;



    opens com.example.reservationsystem to javafx.fxml;
    opens com.example.reservationsystem.controllers to javafx.fxml;
    exports com.example.reservationsystem;
}