package com.example.online_shopping.Controllers;


import com.example.online_shopping.Listeners.DeactivateVoucherButtonListener;
import com.example.online_shopping.Services.AlertService;
import com.example.online_shopping.beans.VoucherBean;
import  com.example.online_shopping.Services.VoucherService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminVoucherController {

    @FXML
    private Button addVoucherButton;

    @FXML
    private VBox itemLayout;
    @FXML
    private ScrollPane scroll;
    private DeactivateVoucherButtonListener deactivateVoucherButtonListener;
    private VoucherBean voucher;

    public void deactivateVoucher(VoucherBean voucher)
    {
        if(VoucherService.setVoucherInactive(voucher.getVoucherCode()))
            AlertService.openAlertInfo("The voucher was inactivated!");
        else AlertService.openAlertError("Couldn't deactivate the voucher!");
        setData();
    }

    public void setData()
    {
        deactivateVoucherButtonListener=(voucher) -> deactivateVoucher(voucher);
        List<VoucherBean> voucherList = VoucherService.fetchDataFromDatabase();
        itemLayout.getChildren().clear();
        itemLayout.setSpacing(1);
        try {
            for (int i = 0; i < voucherList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/admin-voucher-item.fxml"));
                HBox hBox = fxmlLoader.load();

                AdminVoucherItemController adminVoucherItemController  = fxmlLoader.getController();
                adminVoucherItemController.setData(voucherList.get(i),deactivateVoucherButtonListener);
                hBox.setMaxHeight(82);
                hBox.setMinHeight(82);
                hBox.setPrefHeight(82);
                itemLayout.getChildren().add(hBox);

            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    void onAddVoucherButtonClick() {

        Stage stage = (Stage) addVoucherButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/admin-create-voucher.fxml"));

        Scene scene ;
        try {
            Parent root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setTitle("Create Voucher");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }

}
