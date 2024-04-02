package com.example.online_shopping.Controllers;


import com.example.online_shopping.Services.AlertService;
import com.example.online_shopping.Services.PlaceOrderService;
import com.example.online_shopping.Services.VoucherService;
import com.example.online_shopping.beans.CustomerBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {

    @FXML
    private TextArea addressTextArea;

    @FXML
    private Button cancelButton;

    @FXML
    private Button finishButton;

    @FXML
    private ComboBox<String> paymentComboBox;

    @FXML
    private TextField voucherTextField;
    private CustomerBean customer;

    public void setCustomer(CustomerBean customer)
    {
        this.customer=customer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        paymentComboBox.getItems().addAll("Cash on Delivery","Card");
        paymentComboBox.setOnAction(event -> {
            String selectedOption = paymentComboBox.getSelectionModel().getSelectedItem();

            if (selectedOption == null) {
                finishButton.setVisible(false);
            } else if (selectedOption.equals("Cash on Delivery")) {
                finishButton.setText("Finish");
                finishButton.setVisible(true);
            } else if (selectedOption.equals("Card")) {
                finishButton.setText("Pay");
                finishButton.setVisible(true);
            }
        });
    }

    @FXML
    void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
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
            userHomePageController.onCartButtonClick();
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }

    @FXML
    void onFinishButtonClick() {
        if(addressTextArea.getText().isEmpty())
        {
            AlertService.openAlertWarning("Delivery Address Field is Empty!");
        }
        else {
            if (!voucherTextField.getText().isEmpty() && VoucherService.isVoucherFoundAndActive(voucherTextField.getText()) == false) {
                AlertService.openAlertError("Invalid Voucher Code. Please insert another code if you have one or proceed to payment.");
            } else {
                String selectedOption = paymentComboBox.getSelectionModel().getSelectedItem();
                if (selectedOption.equals("Cash on Delivery")) {
                    Stage stage = (Stage) finishButton.getScene().getWindow();
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
                    if (PlaceOrderService.placeOrderWithoutPayment(customer.getUserId(), addressTextArea.getText(), voucherTextField.getText()))
                        AlertService.openAlertInfo("Order Placed Successfully!");
                    else AlertService.openAlertError("Unable to Process your Order!");

                } else if (selectedOption.equals("Card")) {
                    Stage stage = (Stage) finishButton.getScene().getWindow();
                    stage.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/card-payment.fxml"));

                    Scene scene;
                    try {
                        Parent root = fxmlLoader.load();
                        CardPaymentController cardPaymentController = fxmlLoader.getController();
                        cardPaymentController.setCustomer(customer,addressTextArea.getText(),voucherTextField.getText());
                        scene = new Scene(root);
                        stage.setTitle("Card Payment");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }

                }

            }
        }

    }

}
