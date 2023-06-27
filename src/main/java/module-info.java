module com.example.sweeter_client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;


    opens com.example.sweeter_client to javafx.fxml;
    exports com.example.sweeter_client;
}