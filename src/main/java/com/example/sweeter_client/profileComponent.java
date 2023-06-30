package com.example.sweeter_client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;

import static com.example.sweeter_client.signin_Controller.toStringArray;
import static java.lang.System.in;

public class profileComponent extends AnchorPane {
    Label user_idLabel;
    Circle circleClipProfile;
    ImageView HeaderImageView;
    ImageView AvatarImageView;
    Label followersLabel;
    Label followingLabel;
    Label NumberFollowersLabel;
    Label NumberFollowingLabel;
    Button FollowButton;
    Label nameLabel;
    Label bioLabel;
    Label webLabel;
    Label locLabel;
    TextField webTextField;
    TextField locTextField;

    JFXTextArea bioTextArea;
    User user;
    Bio bio;
    Image AvatarImage;
    Image HeaderImage;
    Rectangle rectangleClip;
    public profileComponent(User user, Bio bio) throws IOException {
        user_idLabel = new Label("@" + user.getId());
        AvatarImage = getAvatar(user);
        HeaderImage = getHeader(user);
        HeaderImageView = new ImageView(HeaderImage);
        AvatarImageView = new ImageView(AvatarImage);
        followersLabel = new Label("Followers");
        followingLabel = new Label("Following");
        NumberFollowersLabel = new Label(Integer.toString(NumFollowers(user)));
        NumberFollowingLabel = new Label(Integer.toString(NumFollowing(user)));
        FollowButton = new Button();

        nameLabel = new Label(user.getFirstName());
        bioLabel = new Label("Bio");
        webLabel = new Label("Website");
        locLabel = new Label("Location");

        bioTextArea = new JFXTextArea();
        bioTextArea.setText(bio.getBiography());
        webTextField = new TextField();
        webTextField.setText(bio.getWebsite());
        locTextField = new TextField();
        locTextField.setText(bio.getLocation());

        circleClipProfile = new Circle(50);
        HeaderImageView.setFitWidth(580);
        HeaderImageView.setFitHeight(200);
        rectangleClip = new Rectangle(600, 200);
        HeaderImageView.setClip(rectangleClip);
        this.user = user;
        this.bio = bio;
        setConfig();
        setLocation();
        setAction();
//        this.getChildren().addAll(user_idLabel,HeaderImageView, AvatarImageView,followersLabel, followingLabel, NumberFollowersLabel, NumberFollowingLabel,
//                                    FollowButton, nameLabel, bioLabel, webLabel, locLabel, nameTextField, bioTextArea, webTextField, locTextField);
        this.getChildren().addAll(circleClipProfile, HeaderImageView, user_idLabel, nameLabel, followersLabel, followingLabel, NumberFollowersLabel, NumberFollowingLabel,
                bioLabel, bioTextArea, webLabel, locLabel, webTextField, locTextField);
        if (!HelloApplication.loggedin_user.getId().equals(user.getId()))
            this.getChildren().add(FollowButton);
    }

    private void setConfig() throws IOException {
        this.setPrefSize(580, 740);
        this.setStyle("-fx-background-color: #192841");
        user_idLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        user_idLabel.setTextFill(Paint.valueOf("gray"));
        circleClipProfile.setFill(new ImagePattern(AvatarImage));
        nameLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        nameLabel.setTextFill(Paint.valueOf("white"));

        followersLabel.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        followersLabel.setTextFill(Paint.valueOf("white"));

        followingLabel.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        followingLabel.setTextFill(Paint.valueOf("white"));

        NumberFollowersLabel.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        NumberFollowersLabel.setTextFill(Paint.valueOf("white"));

        NumberFollowingLabel.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        NumberFollowingLabel.setTextFill(Paint.valueOf("white"));

        FollowButton.setPrefSize(260, 30);
        FollowButton.setStyle("-fx-background-color: gray");
        if (IsFollowing(HelloApplication.loggedin_user, user))
            FollowButton.setText("UnFollow");
        else
            FollowButton.setText("Follow");

        FollowButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

        bioLabel.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 25));
        bioLabel.setTextFill(Paint.valueOf("white"));

        bioTextArea.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        bioTextArea.setPrefSize(400, 200);
        bioTextArea.setStyle("-fx-text-fill: white;" + "-jfx-focus-color: #192841;" + "-jfx-unfocus-color: #192841;") ;
        bioTextArea.setEditable(false);

        webLabel.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 25));
        webLabel.setTextFill(Paint.valueOf("white"));

        locLabel.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 25));
        locLabel.setTextFill(Paint.valueOf("white"));

        webTextField.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        webTextField.setPrefSize(400, 15);
        webTextField.setEditable(false);
        webTextField.setStyle("-fx-text-fill: white;" + "-fx-background-color: #192841;");

        locTextField.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        locTextField.setPrefSize(400, 15);
        locTextField.setEditable(false);
        locTextField.setStyle("-fx-text-fill: white;" + "-fx-background-color: #192841;");
    }
    private void setLocation() {
        AnchorPane.setTopAnchor(circleClipProfile, 220.0);
        AnchorPane.setLeftAnchor(circleClipProfile, 10.0);

        AnchorPane.setLeftAnchor(nameLabel, 120.0);
        AnchorPane.setTopAnchor(nameLabel, 230.0);

        AnchorPane.setLeftAnchor(user_idLabel, 120.0);
        AnchorPane.setTopAnchor(user_idLabel, 265.0);

        AnchorPane.setLeftAnchor(followersLabel, 290.0);
        AnchorPane.setTopAnchor(followersLabel, 240.0);

        AnchorPane.setLeftAnchor(followingLabel, 450.0);
        AnchorPane.setTopAnchor(followingLabel, 240.0);

        AnchorPane.setLeftAnchor(NumberFollowersLabel, 290.0);
        AnchorPane.setTopAnchor(NumberFollowersLabel, 270.0);

        AnchorPane.setLeftAnchor(NumberFollowingLabel, 450.0);
        AnchorPane.setTopAnchor(NumberFollowingLabel, 270.0);

        AnchorPane.setLeftAnchor(FollowButton, 290.0);
        AnchorPane.setTopAnchor(FollowButton, 305.0);

        AnchorPane.setLeftAnchor(bioLabel, 30.0);
        AnchorPane.setTopAnchor(bioLabel, 370.0);

        AnchorPane.setLeftAnchor(bioTextArea, 150.0);
        AnchorPane.setTopAnchor(bioTextArea, 365.0);

        AnchorPane.setLeftAnchor(webLabel, 10.0);
        AnchorPane.setTopAnchor(webLabel, 600.0);

        AnchorPane.setLeftAnchor(locLabel, 10.0);
        AnchorPane.setTopAnchor(locLabel, 670.0);

        AnchorPane.setLeftAnchor(webTextField, 150.0);
        AnchorPane.setTopAnchor(webTextField, 595.0);

        AnchorPane.setLeftAnchor(locTextField, 150.0);
        AnchorPane.setTopAnchor(locTextField, 665.0);
    }
    private void setAction() {
        FollowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("checking");
                try {

                    if (!IsFollowing(HelloApplication.loggedin_user, user)) {
                        try {
                            String response;
                            URL url = new URL("http://localhost:8080/follows/" + HelloApplication.loggedin_user.getId() + "/" + user.getId());
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("POST");
                            con.setRequestProperty("JWT", HelloApplication.loggedin_user.getId() + "," + HelloApplication.token);


                            int responseCode = con.getResponseCode();
                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String inputline;
                            StringBuffer response1 = new StringBuffer();
                            while ((inputline = in.readLine()) != null) {
                                response1.append(inputline);
                            }
                            in.close();
                            response = response1.toString();

                            if (response.equals("Done!")) {
                                System.out.println("Followed");
                                FollowButton.setText("UnFollow");
                            }
                            else {
                                System.out.println("connection failed");
                            }


                        }
                        catch (ConnectException e) {
                            System.out.println("Connection failed");
                        }
                    }
                    else {
                        try {
                            System.out.println("checking2");
                            String response;
                            URL url = new URL("http://localhost:8080/follows/" + HelloApplication.loggedin_user.getId() + "/" + user.getId());
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestProperty("JWT", HelloApplication.loggedin_user.getId() + "," + HelloApplication.token);
                            con.setRequestMethod("DELETE");

                            int responseCode = con.getResponseCode();
                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String inputline;
                            StringBuffer response1 = new StringBuffer();
                            while ((inputline = in.readLine()) != null) {
                                response1.append(inputline);
                            }
                            in.close();
                            response = response1.toString();

                            if (response.equals("Done!")) {
                                System.out.println("UnFollowed");
                                FollowButton.setText("Follow");
                            }
                            else {
                                System.out.println("connection failed");
                            }
                        }
                        catch (ConnectException e) {
                            System.out.println("Connection failed");
                        }
                    }

                    NumberFollowersLabel.setText(Integer.toString(NumFollowers(user)));
                    NumberFollowingLabel.setText(Integer.toString(NumFollowing(user)));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        followersLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UserView_controller.users = new ArrayList<>();
                try {
                    String response;
                    URL url = new URL("http://localhost:8080/follows");
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

                    JSONArray jsonObject = new JSONArray(response);
                    String[] follows = toStringArray(jsonObject);
                    for (String t : follows) {
                        JSONObject obj = new JSONObject(t);
                        Follow f = new Follow(obj.getString("follower"), obj.getString("followed"));
                        if (f.getFollowed().equals(user.getId())) {
                            UserView_controller.users.add(getUser(f.getFollower()));
                        }
                    }
                    HelloApplication m = new HelloApplication();
                    m.changeScene(9);
                } catch (IOException e) {
                    System.out.println("Connection failed");
                }
            }
        });
        followingLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UserView_controller.users = new ArrayList<>();
                try {
                    String response;
                    URL url = new URL("http://localhost:8080/follows");
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

                    JSONArray jsonObject = new JSONArray(response);
                    String[] follows = toStringArray(jsonObject);
                    for (String t : follows) {
                        JSONObject obj = new JSONObject(t);
                        Follow f = new Follow(obj.getString("follower"), obj.getString("followed"));
                        if (f.getFollower().equals(user.getId())) {
                            UserView_controller.users.add(getUser(f.getFollowed()));
                        }
                    }
                    HelloApplication m = new HelloApplication();
                    m.changeScene(9);
                } catch (IOException e) {
                    System.out.println("Connection failed");
                }
            }
        });
    }
    public User getUser(String user_id) throws IOException {
        try {
            HelloApplication m = new HelloApplication();
            URL url = new URL("http://localhost:8080/users/" + user_id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader in1 = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputline1;
            StringBuffer response2 = new StringBuffer();
            while ((inputline1 = in1.readLine()) != null) {
                response2.append(inputline1);
            }
            in.close();
            String response = response2.toString();
            JSONObject obj = new JSONObject(response);
            User user = new User(obj.getString("id"), obj.getString("firstName"), obj.getString("lastName"), obj.getString("email"), obj.getString("phoneNumber"), obj.getString("password"), obj.getString("country"), new Date(obj.getLong("birthday")));
            return user;
        }
        catch (ConnectException e) {
            return new User("", "", "", "", "", "", "", new Date());
        }
    }
    public boolean IsFollowing(User follower, User following) throws IOException {
        try {
            String response;
            URL url = new URL("http://localhost:8080/follows");
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

            JSONArray jsonObject = new JSONArray(response);
            String[] follows = toStringArray(jsonObject);
            for (String t: follows) {
                JSONObject obj = new JSONObject(t);
                Follow f = new Follow(obj.getString("follower"), obj.getString("followed"));
                if (f.getFollower().equals(follower.getId()) && f.getFollowed().equals(following.getId()))
                    return true;
            }
            return false;
        }
        catch (ConnectException e) {
            System.out.println("Connection failed");
            return false;
        }
    }

    public int NumFollowers(User user) throws IOException {
        try {
            String response;
            URL url = new URL("http://localhost:8080/follows");
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

            JSONArray jsonObject = new JSONArray(response);
            String[] follows = toStringArray(jsonObject);
            int res = 0;
            for (String t: follows) {
                JSONObject obj = new JSONObject(t);
                Follow f = new Follow(obj.getString("follower"), obj.getString("followed"));
                if (f.getFollowed().equals(user.getId()))
                    res++;
            }
            return res;
        }
        catch (ConnectException e) {
            System.out.println("Connection failed");
            return 0;
        }
    }
    public int NumFollowing(User user) throws IOException {
        try {
            String response;
            URL url = new URL("http://localhost:8080/follows");
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

            JSONArray jsonObject = new JSONArray(response);
            String[] follows = toStringArray(jsonObject);
            int res = 0;
            for (String t: follows) {
                JSONObject obj = new JSONObject(t);
                Follow f = new Follow(obj.getString("follower"), obj.getString("followed"));
                if (f.getFollower().equals(user.getId()))
                    res++;
            }
            return res;
        }
        catch (ConnectException e) {
            System.out.println("Connection failed");
            return 0;
        }
    }
    public Image getAvatar(User user) throws IOException {
        try {
            String response;
            URL url = new URL("http://localhost:8080/media/" + user.getId() + "/Avatar/png");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            String t = con.getHeaderField("content-length");
            if (t == null || Integer.parseInt(t) == 7) {
                Image image = new Image(Path.of("src/main/resources/com/example/sweeter_client/assets/default.png").toUri().toString());
                return image;
            }

            Files.copy(con.getInputStream(), Path.of("src/main/resources/com/example/sweeter_client/assets/Avatar.png"), StandardCopyOption.REPLACE_EXISTING);
            Image image = new Image(Path.of("src/main/resources/com/example/sweeter_client/assets/Avatar.png").toUri().toString());
            return image;

        }
        catch (ConnectException e) {
            Image image = new Image(Path.of("src/main/resources/com/example/sweeter_client/assets/default.png").toUri().toString());
            return image;
        }
    }
    public Image getHeader(User user) throws IOException {
        try {
            String response;
            URL url = new URL("http://localhost:8080/media/" + user.getId() + "/Header/png");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            String t = con.getHeaderField("content-length");
            if (t == null || Integer.parseInt(t) == 7) {
                Image image = new Image(Path.of("src/main/resources/com/example/sweeter_client/assets/default.png").toUri().toString());
                return image;
            }

            Files.copy(con.getInputStream(), Path.of("src/main/resources/com/example/sweeter_client/assets/Header.png"), StandardCopyOption.REPLACE_EXISTING);
            Image image = new Image(Path.of("src/main/resources/com/example/sweeter_client/assets/Header.png").toUri().toString());
            return image;
        }
        catch (ConnectException e) {
            Image image = new Image(Path.of("src/main/resources/com/example/sweeter_client/assets/default.png").toUri().toString());
            return image;
        }
    }
}
