package com.example.online_shopping.Controllers;

import com.example.online_shopping.Services.AlertService;
import com.example.online_shopping.Services.DatabaseConnectionService;
import com.example.online_shopping.Services.OrderService;
import com.example.online_shopping.Listeners.StatusButtonListener;
import com.example.online_shopping.beans.OrderBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.example.online_shopping.constants.Constants.*;

public class AdminOrderDetailsController {

    @FXML
    private VBox orderDetailsVBox;
    @FXML
    private ScrollPane scroll;
    private StatusButtonListener statusButtonListener;

    public void updateOrderStatus(int orderid) {
        AlertService.openAlertInfo(OrderService.updateOrderStatus(orderid));
        setData();
    }


    public void setData() {
        statusButtonListener = (orderid) -> updateOrderStatus(orderid);
        orderDetailsVBox.getChildren().clear();
        orderDetailsVBox.setSpacing(20);

        List<OrderBean> ordersList = OrderService.getOrders();
        try {
            for (int i = 0; i < ordersList.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/admin-order-item.fxml"));
                VBox vBox = fxmlLoader.load();

                AdminOrderItemController adminOrderItemController = fxmlLoader.getController();
                adminOrderItemController.setData(ordersList.get(i), statusButtonListener);
                orderDetailsVBox.setVgrow(vBox, Priority.ALWAYS);
                orderDetailsVBox.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}