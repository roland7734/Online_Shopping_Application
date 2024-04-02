package com.example.online_shopping.Controllers;

import com.example.online_shopping.Services.ManufacturerService;
import com.example.online_shopping.beans.ManufacturerBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ManufacturersDetailsController {

    @FXML
    private VBox itemLayout;
    @FXML
    private ScrollPane scroll;

    public void setData()
    {
        scroll.setVvalue(0);
        List<ManufacturerBean> manufacturerList = ManufacturerService.getManufacturers();
        itemLayout.getChildren().clear();
        try {
            for (int i = 0; i < manufacturerList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/manufacturer-item.fxml"));
                HBox hBox = fxmlLoader.load();

                ManufacturerItemController manufacturerItemController  = fxmlLoader.getController();
                manufacturerItemController.setData(manufacturerList.get(i));
                hBox.setMaxHeight(82);
                hBox.setMinHeight(82);
                hBox.setPrefHeight(82);
                itemLayout.getChildren().add(hBox);

            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
