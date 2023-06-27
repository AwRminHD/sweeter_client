package com.example.sweeter_client;

import com.example.sweeter_client.models.User;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

public class signin_Controller implements Initializable {
    @FXML
    TextField FirstName_tf;
    @FXML
    TextField LastName_tf;
    @FXML
    TextField Username_tf;
    @FXML
    TextField Password_tf;
    @FXML
    TextField Email_tf;
    @FXML
    TextField Phone_tf;
    @FXML
    DatePicker datePicker;
    @FXML
    ComboBox Countries;
    @FXML
    Label GreenLabel;
    @FXML
    Label wrong_label;
    String Country = "";
    String date = "";
    public void goBack() throws Exception {
        Clear();
        HelloApplication m = new HelloApplication();
        m.changeScene(1 );
    }
    public void Select(ActionEvent event) {
        Country = Countries.getSelectionModel().getSelectedItem().toString();
    }
    public void getDate(ActionEvent event) {
        date = datePicker.getValue().toString();
    }
    public void Register_pressed(ActionEvent event) throws Exception {
        boolean a1 = FirstName_tf.getText().length() == 0;
        boolean a2 = LastName_tf.getText().length() == 0;
        boolean a3 = Username_tf.getText().length() == 0;
        boolean a4 = Password_tf.getText().length() == 0;
        boolean a5 = Email_tf.getText().length() == 0;
        boolean a6 = Phone_tf.getText().length() == 0;
        boolean a7 = Country.length() == 0;
        boolean a8 = date.length() == 0;
        System.out.println(date + " " + Country);
        if (a1 || a2 || a3 || a4 || (a5 && a6) || a7 || a8) {
            wrong_label.setText("please enter all fields");
        }
        else if (Email_tf.getText().length() != 0 && !isValidEmailAddress(Email_tf.getText())) {
            wrong_label.setText("Email is invalid");
        }
        else if (!isValidPass(Password_tf.getText())) {
            System.out.println(Password_tf.getText());
            wrong_label.setText("Password is invalid");
        }
        else {
            try {
                String response;
                {
                    URL url = new URL("http://localhost:8080/users/" + Username_tf.getText());
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
                    response = response1.toString();
                }
                if (!response.equals("No User")) {
                    wrong_label.setText("Username exist");
                }
                else {
                    DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    Date date1 = format.parse(date);
                    User user = new User(Username_tf.getText(), FirstName_tf.getText(), LastName_tf.getText(), Email_tf.getText(), Phone_tf.getText(), Password_tf.getText(), Country, date1);
                    //sending post request
                    URL url = new URL("http://localhost:8080/users/" + Username_tf.getText());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");

//                    ObjectMapper objectMapper = new ObjectMapper();
//                    String json = objectMapper.writeValueAsString(user);

                    GreenLabel.setText("Register completed");
                }
            }
            catch (ConnectException e) {
                wrong_label.setText("connection failed");
            }
        }
    }
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isValidPass(String pass) {
        boolean cap = false, small = false;
        for (char c: pass.toCharArray()) {
            if (Character.isLowerCase(c))
                small = true;
            if (Character.isUpperCase(c))
                cap = true;
        }
        return cap && small && pass.length() >= 6;
    }
    public void Clear() {
        FirstName_tf.setText("");
        LastName_tf.setText("");
        Username_tf.setText("");
        Phone_tf.setText("");
        Password_tf.setText("");
        Email_tf.setText("");
        GreenLabel.setText("");
        wrong_label.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList <String> list = FXCollections.observableArrayList("IRAN", "USA", "IRAQ", "TURKEY", "SPAIN", "UK", "DENMARK", "RUSSIA");
        Countries.setItems(list);
    }
}
