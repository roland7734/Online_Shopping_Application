package com.example.online_shopping.Controllers;

import com.example.online_shopping.Services.AlertService;
import com.example.online_shopping.Services.DatabaseConnectionService;
import com.example.online_shopping.Services.StringValidationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static com.example.online_shopping.constants.Constants.*;
import com.example.online_shopping.Services.UserService;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML
    private Label alreadyCreatedAccountLabel;

    @FXML
    private Label usernameNotUniqueLabel;
    @FXML
    private Label successfulRegLabel;
    @FXML
    private TextArea addressTextArea;
    @FXML
    private Label failedRegistrationLabel;
    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameTextField;
    @FXML
    private Label compulsoryFieldsLabel;
    @FXML
    private Label matchPasswordsLabel;

@FXML
    protected void onRegisterButtonClick() {

     if (areCompulsoryFieldsProvided() && doPasswordsMatch()) {

         if (!StringValidationService.containsOnlyLetters(lastNameTextField.getText())) {
             AlertService.openAlertWarning("Please introduce a valid name. Last name should contain only letters.");
         } else {
             if (!StringValidationService.containsOnlyLetters(firstNameTextField.getText())) {
                 AlertService.openAlertWarning("Please introduce a valid name. The first name should contain only letters.");
             } else {
                 if (!StringValidationService.hasEmailPattern(emailTextField.getText())) {
                     AlertService.openAlertWarning("Please introduce a valid email address.");
                 } else {
                     if (!StringValidationService.containsOnlyNumbersOfSize10(phoneNumberTextField.getText())) {
                         AlertService.openAlertWarning("Please introduce a valid phone number.");
                     } else {
                         Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
                         if (conn != null) {

                             String status = UserService.registerUser(usernameTextField.getText(), passwordTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), addressTextArea.getText(), phoneNumberTextField.getText(), emailTextField.getText());
                             switch (status) {
                                 case "The Account is already created!":
                                     AlertService.openAlertWarning("The provided Email is Already Associated with an Account!");
                                     break;

                                 case "The username must be unique!":
                                     AlertService.openAlertWarning("The username must be unique!");
                                     break;
                                 case "RegistrationController Successful!":
                                     AlertService.openAlertInfo("Registration was successful! Feel free to log in to your account and begin your shopping experience.");
                                     onLoginButtonClick();
                                     break;
                                 default:
                                     AlertService.openAlertError("Registration Failed!");
                                     break;
                             }

                         }
                     }
                 }
             }
         }
     } else {
            if(areCompulsoryFieldsProvided() == false)
            {
                AlertService.openAlertWarning("All fields are compulsory!");

            }
            else {
                AlertService.openAlertWarning("Passwords do not match!");
            }
        }
    }

    private boolean areCompulsoryFieldsProvided()
    {
        return !addressTextArea.getText().isEmpty() &&
                !confirmPasswordTextField.getText().isEmpty() &&
                !emailTextField.getText().isEmpty() &&
                !firstNameTextField.getText().isEmpty() &&
                !lastNameTextField.getText().isEmpty() &&
                !passwordTextField.getText().isEmpty() &&
                !phoneNumberTextField.getText().isEmpty() &&
                !usernameTextField.getText().isEmpty();
    }

    private boolean doPasswordsMatch() {
        // Check if passwords match
        return passwordTextField.getText().equals(confirmPasswordTextField.getText());
    }

    @FXML
    protected void onLoginButtonClick()
    {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/user-login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }
}
