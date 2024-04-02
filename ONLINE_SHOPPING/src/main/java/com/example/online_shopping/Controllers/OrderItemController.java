package com.example.online_shopping.Controllers;

import com.example.online_shopping.Services.DatabaseConnectionService;
import com.example.online_shopping.Services.OrderItemService;
import com.example.online_shopping.beans.OrderBean;
import com.example.online_shopping.beans.OrderItemBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.online_shopping.constants.Constants.*;

public class OrderItemController {

    @FXML
    private HBox hbox1;

    @FXML
    private HBox hbox2;

    @FXML
    private HBox hbox3;
    @FXML
    private Label addressLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label discountLabel;

    @FXML
    private Label orderIdLabel;

    @FXML
    private VBox orderVBox;

    @FXML
    private Label statusLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private Label totalSumLabel;

    public void setData(OrderBean order) {
        orderVBox.setVgrow(hbox1, Priority.NEVER);
        orderVBox.setVgrow(hbox2, Priority.NEVER);
        orderVBox.setVgrow(hbox3, Priority.NEVER);

        hbox1.setMinHeight(45);
        hbox1.setPrefHeight(45);
        hbox1.setMaxHeight(45);
        hbox3.setMinHeight(45);
        hbox3.setPrefHeight(45);
        hbox3.setMaxHeight(45);
        hbox2.setMinHeight(57);
        hbox2.setPrefHeight(57);
        hbox2.setMaxHeight(57);
        try {

            orderVBox.setSpacing(10);
            List<OrderItemBean> orderItemsList = OrderItemService.getItemsOfSpecificOrder(order.getOrderId());
            for (int i = 0; i < orderItemsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/order-product-item.fxml"));
                HBox hBox = fxmlLoader.load();

                OrderProductItemController orderProductItemController = fxmlLoader.getController();
                orderProductItemController.setData(orderItemsList.get(i).getImageUrl(), orderItemsList.get(i).getProductName(), orderItemsList.get(i).getQuantity(), orderItemsList.get(i).getSubtotal());
                orderVBox.setVgrow(hBox, Priority.NEVER);
                orderVBox.getChildren().add(hBox);

            }
            orderVBox.getChildren().add(createSpace(10));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orderIdLabel.setText("STX" + order.getOrderId());
            dateLabel.setText(dateFormat.format(order.getOrderDate()));
            addressLabel.setText(order.getAddress());
            totalSumLabel.setText("$" + order.getTotalAmountBeforeDiscount());
            discountLabel.setText(order.getDiscount() + "%");
            paymentMethodLabel.setText(order.getPaymentMethod());
            amountLabel.setText("$" + order.getTotalAmount());
            statusLabel.setText(order.getStatus());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Region createSpace(double height) {
        Region space = new Region();
        space.setPrefHeight(height);
        return space;
    }


}