package com.example.online_shopping.Controllers;

import com.example.online_shopping.Listeners.DeactivateVoucherButtonListener;
import com.example.online_shopping.beans.VoucherBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AdminVoucherItemController {

    @FXML
    private Button deactivateButton;

    @FXML
    private Label discountVoucherLabel;

    @FXML
    private Label endDateLabel;

    @FXML
    private Label isActiveLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label vocuherCodeLabel;
    private VoucherBean voucher;

    private DeactivateVoucherButtonListener deactivateVoucherButtonListener;

    public void setData(VoucherBean voucher, DeactivateVoucherButtonListener deactivateVoucherButtonListener)
    {
        this.voucher=voucher;
        this.deactivateVoucherButtonListener=deactivateVoucherButtonListener;
        vocuherCodeLabel.setText(voucher.getVoucherCode());
        discountVoucherLabel.setText(voucher.getDiscountPercentage()+"%");
        startDateLabel.setText(String.valueOf(voucher.getStartDate()));
        endDateLabel.setText(String.valueOf(voucher.getEndDate()));
        if (voucher.isActive())
        {
            isActiveLabel.setText("Yes");
        }
        else isActiveLabel.setText("No");
    }


    @FXML
    void onDeactivateButtonClick() {
        deactivateVoucherButtonListener.onDeactivateVoucherButtonClick(voucher);

    }

}