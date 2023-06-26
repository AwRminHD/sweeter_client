module com.example.sweeter_client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sweeter_client to javafx.fxml;
    exports com.example.sweeter_client;
}