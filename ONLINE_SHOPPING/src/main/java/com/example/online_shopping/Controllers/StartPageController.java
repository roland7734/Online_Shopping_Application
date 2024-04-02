package com.example.online_shopping.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartPageController {

    @FXML
    private Button adminButton;

    @FXML
    private Button userButton;

    @FXML
    void onAdminButtonClick() {

        Stage stage = (Stage) adminButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/admin-login.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Admin Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void onUserButtonClick() {
        Stage stage = (Stage) userButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/user-login.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("User Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}