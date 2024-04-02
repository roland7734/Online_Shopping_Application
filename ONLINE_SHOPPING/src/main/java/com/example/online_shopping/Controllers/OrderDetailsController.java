package com.example.online_shopping.Controllers;

import com.example.online_shopping.Services.DatabaseConnectionService;
import com.example.online_shopping.Services.OrderService;
import com.example.online_shopping.beans.OrderBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.example.online_shopping.constants.Constants.*;

public class OrderDetailsController {

    @FXML
    private VBox orderDetailsVBox;

    @FXML
    private AnchorPane noOrdersAnchorPane;

    @FXML
    private HBox ordersHBox;
    @FXML
    private ScrollPane scroll;


    public void setData(int userid) {

        scroll.setVvalue(0);
        orderDetailsVBox.getChildren().clear();
        orderDetailsVBox.setSpacing(20);
        List<OrderBean> ordersList = OrderService.getOrdersOfSpecificUser(userid);
        try {
            if (ordersList != null) {
                for (int i = 0; i < ordersList.size(); i++) {
                    ordersHBox.setVisible(true);
                    noOrdersAnchorPane.setVisible(false);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/order-item.fxml"));
                    VBox vBox = fxmlLoader.load();

                    OrderItemController orderItemController = fxmlLoader.getController();
                    orderItemController.setData(ordersList.get(i));
                    orderDetailsVBox.setVgrow(vBox, Priority.ALWAYS);
                    orderDetailsVBox.getChildren().add(vBox);
                }
            } else {
                ordersHBox.setVisible(false);
                noOrdersAnchorPane.setVisible(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
