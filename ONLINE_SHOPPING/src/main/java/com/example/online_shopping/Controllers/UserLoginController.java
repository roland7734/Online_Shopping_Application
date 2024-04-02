package com.example.online_shopping.Controllers;

import com.example.online_shopping.Services.AlertService;
import com.example.online_shopping.Services.UserService;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class UserLoginController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button createNewAccountButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label incorrectCredentials;
    @FXML
    protected void onLoginButtonClick() {
        // Perform database connection and user verification here
        String username = usernameText.getText();
        String password = passwordField.getText();
        if (UserService.isCustomerInDatabase(username, password)) {
            // User exists in the database, perform login action
            incorrectCredentials.setVisible(false);
            // Perform further actions upon successful login
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/user-home-page.fxml"));

            Scene scene;
            try {
                Parent root = fxmlLoader.load();
                UserHomePageController userHomePageController = fxmlLoader.getController();
                userHomePageController.setCustomer(UserService.getCustomer(username));

                scene = new Scene(root);
                stage.setTitle("User Home Page");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        } else {
            // User doesn't exist in the database or invalid credentials
            AlertService.openAlertError("User doesn't exist in the database or invalid data was introduced.");
            usernameText.clear();
            passwordField.clear();
        }
    }

    @FXML
        protected void onCreateNewAccountButtonClick()
        {
            Stage stage = (Stage) createNewAccountButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/registration.fxml"));

            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            stage.setTitle("Registration");
            stage.setScene(scene);
            stage.show();

        }
    @FXML
    void onCancelButtonClick(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/start-page.fxml"));

        Scene scene;
        try {
            Parent root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setTitle("Start Page");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}