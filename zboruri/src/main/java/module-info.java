module com.example.zboruri {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.zboruri to javafx.fxml;
    exports com.example.zboruri;

    exports com.example.zboruri.Controller;
    opens com.example.zboruri.Controller to javafx.fxml;
    opens com.example.zboruri.Domain to java.base;
    exports com.example.zboruri.Domain;
}