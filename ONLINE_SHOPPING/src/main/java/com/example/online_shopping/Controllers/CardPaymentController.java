package com.example.online_shopping.Controllers;

import com.example.online_shopping.Services.AlertService;
import com.example.online_shopping.Services.PlaceOrderService;
import com.example.online_shopping.Services.StringValidationService;
import com.example.online_shopping.beans.CustomerBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CardPaymentController {

    @FXML
    private TextField cardCVVTextField;

    @FXML
    private TextField cardExpiryDateTextField;

    @FXML
    private TextField cardNameTextField;

    @FXML
    private TextField cardNumberTextField;

    @FXML
    private Button payButton;

    @FXML
    private Button cancelButton;
    private CustomerBean customer;
    private String address;
    private String voucher;

    public void setCustomer(CustomerBean customer, String address, String voucher) {
        this.customer = customer;
        this.address = address;
        this.voucher = voucher;
    }

    @FXML
    void onPayButtonClick() {
        if (!areFieldsNotEmpty())
            AlertService.openAlertWarning("Please fill in all fields!");
        else {
            if (!StringValidationService.containsOnlyLettersSpaceHyphen(cardNameTextField.getText())) {
                AlertService.openAlertWarning("Card Name Field is not valid. Please insert a valid name!");
            } else {
                if (!StringValidationService.isValidCardFormat(cardNumberTextField.getText())) {
                    AlertService.openAlertWarning("Card Number Field is not valid. Please insert a valid card number! (e.g.: 1234-8655-5436-7364)");
                } else {
                    if (!StringValidationService.isValidExpiryDate(cardExpiryDateTextField.getText())) {
                        AlertService.openAlertWarning("Card Expiry Date Field is not valid!");
                    } else {
                        if (!StringValidationService.isValidCVV(cardCVVTextField.getText())) {
                            AlertService.openAlertWarning("Card CVV Field is not valid. Please insert a valid CVV!");
                        } else {
                            Stage stage = (Stage) payButton.getScene().getWindow();
                            stage.close();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/user-home-page.fxml"));

                            Scene scene;
                            try {
                                Parent root = fxmlLoader.load();
                                UserHomePageController userHomePageController = fxmlLoader.getController();
                                userHomePageController.setCustomer(customer);
                                scene = new Scene(root);
                                stage.setTitle("User Home Page");
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                System.err.println(e.getMessage());
                            }
                            if (PlaceOrderService.placeOrderWithPayment(customer.getUserId(), address, voucher))
                                AlertService.openAlertInfo("Payment Processed Successfully.");
                            else AlertService.openAlertError("Unable to Process your order. Please try again.");

                        }
                    }
                }
            }
        }
    }

    @FXML
    void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/place-order.fxml"));

        Scene scene;
        try {
            Parent root = fxmlLoader.load();
            PlaceOrderController placeOrderController = fxmlLoader.getController();
            placeOrderController.setCustomer(customer);
            scene = new Scene(root);
            stage.setTitle("Place Order");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private boolean areFieldsNotEmpty() {
        return !cardCVVTextField.getText().isEmpty() &&
                !cardExpiryDateTextField.getText().isEmpty() &&
                !cardNameTextField.getText().isEmpty() &&
                !cardNumberTextField.getText().isEmpty();
    }

}
