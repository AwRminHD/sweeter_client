package com.example.sweeter_client;

import com.example.sweeter_client.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stg;
    private static Scene MainMenu;
    private static Scene ForUpage;
    private static Scene SignIn;
    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        stage.setResizable(false);

        MainMenu = new Scene((new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"))).load());
        ForUpage = new Scene((new FXMLLoader(HelloApplication.class.getResource("forUpage.fxml"))).load());
        SignIn = new Scene((new FXMLLoader(HelloApplication.class.getResource("SigninMenu.fxml"))).load());

        stage.setTitle("Sweeter");
        stage.setScene(MainMenu);
        stage.show();
    }
    public void changeScene(int x) throws IOException {
        if (x == 1)
            stg.setScene(MainMenu);
        else if (x == 2)
            stg.setScene(ForUpage);
        else if (x == 3)
            stg.setScene(SignIn);
    }

    public static void main(String[] args) {
        launch();
    }
}