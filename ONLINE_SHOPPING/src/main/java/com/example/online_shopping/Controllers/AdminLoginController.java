package com.example.online_shopping.Controllers;

import com.example.online_shopping.Services.AlertService;
import com.example.online_shopping.Services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameText;

    @FXML
    void onLoginButtonClick() {
        String username = usernameText.getText();
        String password = passwordField.getText();
        if (UserService.isAdminInDatabase(username, password)) {

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/admin-home-page.fxml"));

            Scene scene;
            try {
                Parent root = fxmlLoader.load();
                scene = new Scene(root);
                stage.setTitle("Admin Home Page");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        } else {
            AlertService.openAlertError("Wrong Credentials. Please try again!");
            usernameText.clear();
            passwordField.clear();
        }


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
