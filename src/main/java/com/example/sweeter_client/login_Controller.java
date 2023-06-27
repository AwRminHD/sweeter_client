package com.example.sweeter_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class login_Controller {
    @FXML
    Button login_button;
    @FXML
    TextField Username_textfield;
    @FXML
    PasswordField Password_textfeild;
    @FXML
    Label wrong_pass_label;
    @FXML
    Button sign_up_button;

    public void userLogIn(ActionEvent event) throws Exception {
        if (Username_textfield.getText().length() == 0 || Password_textfeild.getText().length() == 0)
            wrong_pass_label.setText("please enter all the fields");
        else {
            try {
                URL url = new URL("http://localhost:8080/sessions/" + Username_textfield.getText() + "/" + Password_textfeild.getText());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputline;
                StringBuffer response1 = new StringBuffer();
                while ((inputline = in.readLine()) != null) {
                    response1.append(inputline);
                }
                in.close();
                String response = response1.toString();
                if (response.equals("userID or userPassWord is incorrect")) {
                    wrong_pass_label.setText("Username or Password is incorrect");
                }
                else {
                    HelloApplication m = new HelloApplication();
                    Clear();
                    m.changeScene(2);
                }
            }
            catch (ConnectException e) {
                wrong_pass_label.setText("connection failed");
            }
        }
    }
    public void Clear() {
        Username_textfield.setText("");
        Password_textfeild.setText("");
        wrong_pass_label.setText("");
    }
    public void setSign_up_button(ActionEvent event) throws Exception {
        HelloApplication m = new HelloApplication();
        Clear();
        m.changeScene(3);
    }
}
