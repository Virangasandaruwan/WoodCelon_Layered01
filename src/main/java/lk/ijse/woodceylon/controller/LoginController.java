package lk.ijse.woodceylon.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.geometry.Pos;
import lk.ijse.woodceylon.App;

public class LoginController implements Initializable {

    @FXML
    private Hyperlink frogetPassword;
    @FXML
    private Button si_login;
    @FXML
    private PasswordField si_password;
    @FXML
    private TextField si_username;
    @FXML
    private AnchorPane main_form;
    @FXML
    private HBox indicatorBox;
    @FXML
    private TextField si_password_text;
    @FXML
    private javafx.scene.control.CheckBox chk_show_password;

    private String[] imagePaths = {
        "/images/login_bg1.jpg",
        "/images/login_bg2.jpg",
        "/images/login_bg3.jpg",
        "/images/login_bg4.jpg",
        "/images/login_bg5.jpg",
        "/images/login_bg6.jpg",
        "/images/login_bg7.jpg",
        "/images/login_bg8.jpg",
        "/images/login_bg9.jpg",
        "/images/login_bg10.jpg",
        "/images/login_bg11.jpg"
    };

    private int currentImageIndex = 0;
    private Timeline imageSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        main_form.setOnMouseClicked(this::handleBackgroundClick);
        startImageSlideshow();
        createIndicators();
    }

    private void handleBackgroundClick(MouseEvent event) {
        currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
        loadBackgroundImage(imagePaths[currentImageIndex]);
        updateIndicators();
    }

    private void startImageSlideshow() {
        loadBackgroundImage(imagePaths[0]);
        updateIndicators();

        imageSlider = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
            loadBackgroundImage(imagePaths[currentImageIndex]);
            updateIndicators();
        }));
        imageSlider.setCycleCount(Timeline.INDEFINITE);
        imageSlider.play();
    }

    private void loadBackgroundImage(String imagePath) {
        try {
            java.net.URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl == null) {
                System.out.println("Image not found: " + imagePath);
                return;
            }

            Image image = new Image(imageUrl.toExternalForm());
            BackgroundImage bgImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, true)
            );

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.4), main_form);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(1.0);//0.3

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.4), main_form);
            fadeIn.setFromValue(1.0);//0.3
            fadeIn.setToValue(1.0);

            fadeOut.setOnFinished(e -> {
                main_form.setBackground(new Background(bgImage));
                fadeIn.play();
            });

            fadeOut.play();

        } catch (Exception e) {
            System.out.println("Error loading image: " + imagePath);
            e.printStackTrace();
        }
    }

    private void createIndicators() {
        indicatorBox.getChildren().clear();
        for (int i = 0; i < imagePaths.length; i++) {
            Circle dot = new Circle(8);
            dot.setFill(i == 0 ? Color.WHITE : Color.rgb(255, 255, 255, 0.4));
            dot.setStroke(Color.WHITE);
            dot.setStrokeWidth(1.5);

            final int index = i;
            dot.setOnMouseClicked(e -> {
                currentImageIndex = index;
                loadBackgroundImage(imagePaths[currentImageIndex]);
                updateIndicators();
                imageSlider.stop();
                imageSlider.playFromStart();
            });

            indicatorBox.getChildren().add(dot);
        }
        indicatorBox.setAlignment(Pos.CENTER);
        indicatorBox.setSpacing(12);
    }

    private void updateIndicators() {
        for (int i = 0; i < indicatorBox.getChildren().size(); i++) {
            Circle dot = (Circle) indicatorBox.getChildren().get(i);
            if (i == currentImageIndex) {
                dot.setFill(Color.WHITE);
                dot.setRadius(10);
            } else {
                dot.setFill(Color.rgb(255, 255, 255, 0.4));
                dot.setRadius(8);
            }
        }
    }

    @FXML
    private void Login() {
        try {
            String realUsername = "admin";
            String realPassword = "123";
            String Username = si_username.getText().trim();
            String Password = chk_show_password.isSelected() ? si_password_text.getText() : si_password.getText();

            if (Username.equalsIgnoreCase(realUsername) && Password.equals(realPassword)) {
                App.setRoot("AdminLayer");
            } else {
                new Alert(Alert.AlertType.ERROR, "User Name or Password incorrect!").show();
            }
        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, "Unable to load Admin dashboard!").show();
            ex.printStackTrace();
        }
    }

 
    private void showError(String title, String msg) {
        new Alert(Alert.AlertType.ERROR, msg, javafx.scene.control.ButtonType.OK)
                .show();
    }

    @FXML
    void showPasswordOnAction(javafx.event.ActionEvent event) {
        if (chk_show_password.isSelected()) {

            si_password_text.setText(si_password.getText());
            si_password_text.setVisible(true);
            si_password.setVisible(false);
        } else {

            si_password.setText(si_password_text.getText());
            si_password.setVisible(true);
            si_password_text.setVisible(false);
        }
    }
}
