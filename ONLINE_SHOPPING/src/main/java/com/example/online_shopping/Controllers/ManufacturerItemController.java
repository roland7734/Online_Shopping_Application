package com.example.online_shopping.Controllers;

import com.example.online_shopping.beans.ManufacturerBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ManufacturerItemController {
    @FXML
    private Label addressLabel;

    @FXML
    private Label companyNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label websiteLabel;

    public void setData(ManufacturerBean manufacturer)
    {
        companyNameLabel.setText(manufacturer.getManufacturerName());
        emailLabel.setText(manufacturer.getContactEmail());
        addressLabel.setText(manufacturer.getAddress());
        phoneLabel.setText(manufacturer.getContactPhone());
        websiteLabel.setText(manufacturer.getWebsite());
    }
}
