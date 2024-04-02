package com.example.online_shopping.Controllers;

import com.example.online_shopping.Services.AlertService;
import com.example.online_shopping.Services.StringValidationService;
import com.example.online_shopping.Services.VoucherService;
import com.example.online_shopping.beans.VoucherBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AdminCreateVoucherController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField voucherCodeTextField;

    @FXML
    private TextField voucherDicountTextField;

    @FXML
    private DatePicker voucherEndDatePicker;

    @FXML
    private DatePicker voucherStartDatePicker;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        voucherStartDatePicker.setEditable(false);
        voucherEndDatePicker.setEditable(false);
        voucherStartDatePicker.setDayCellFactory(picker -> new DateCellTodayOrLater());
        voucherEndDatePicker.setDayCellFactory(picker -> new DateCellTodayOrLater());
    }

    private static class DateCellTodayOrLater extends DateCell {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            setDisable(item.isBefore(LocalDate.now()));
        }
    }
    @FXML
    void onCancelButtonClick() {
        openAdminHomePage();
    }



    @FXML
    void onCreateVoucherButtonClick() {
        if(!areFieldsNotEmpty())
            AlertService.openAlertWarning("Please fill in all fields!");
        else {
            if (!StringValidationService.containsOnlyAlphanumeric(voucherCodeTextField.getText()))
                AlertService.openAlertWarning("The voucher code should contain only alphanumeric characters!");
            else {
                if (!StringValidationService.containsOnlyNumbersOfLength(voucherDicountTextField.getText())) {
                    AlertService.openAlertWarning("The vocuher discount percentage should contain an integer number from 1 to 100!");
                } else {
                    if (!StringValidationService.isStartBeforeOrEqualEndDate(voucherStartDatePicker.getValue(), voucherEndDatePicker.getValue())) {
                        AlertService.openAlertWarning("The voucher's start date should precede its end date!");
                    } else {
                        VoucherBean voucher = new VoucherBean(voucherCodeTextField.getText(), Integer.parseInt(voucherDicountTextField.getText()),
                                java.sql.Date.valueOf(voucherStartDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                                java.sql.Date.valueOf(voucherEndDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), true);


                        if (VoucherService.createVoucher(voucher))
                            AlertService.openAlertInfo("The voucher was created successfully!");
                        else AlertService.openAlertError("Couldn't create the voucher!");

                        openAdminHomePage();
                    }
                }
            }
        }

    }
    private void openAdminHomePage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/admin-home-page.fxml"));

        Scene scene;
        try {
            Parent root = fxmlLoader.load();
            AdminHomePageController adminHomePageController = fxmlLoader.getController();
            scene = new Scene(root);
            stage.setTitle("Admin Home Page");
            stage.setScene(scene);
            adminHomePageController.onVoucherButtonClick();
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean areFieldsNotEmpty() {
        return !voucherCodeTextField.getText().isEmpty() &&
                !voucherDicountTextField.getText().isEmpty() &&
                voucherStartDatePicker.getValue() != null &&
                voucherEndDatePicker.getValue() != null;
    }
}

