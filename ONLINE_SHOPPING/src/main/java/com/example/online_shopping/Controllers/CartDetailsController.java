package com.example.online_shopping.Controllers;

import com.example.online_shopping.Listeners.MinusButtonClickListener;
import com.example.online_shopping.Listeners.PlusButtonClickListener;
import com.example.online_shopping.Listeners.RemoveButtonClickListener;
import com.example.online_shopping.Services.CartItemService;
import com.example.online_shopping.beans.CartItemBean;
import com.example.online_shopping.beans.CustomerBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CartDetailsController {

    @FXML
    private VBox itemLayout;
    @FXML
    private HBox cartHBox;
    @FXML
    private AnchorPane noProductsInCartAnchorPane;

    @FXML
    private Button payButton;

    @FXML
    private Label totalAmountLabel;
    @FXML
    private ScrollPane scroll;

    private CustomerBean customer;
    private PlusButtonClickListener plusButtonClickListener;
    private MinusButtonClickListener minusButtonClickListener;
    private RemoveButtonClickListener removeButtonClickListener;


    public void modifyData(CartItemBean item, int quantity)
    {
        CartItemService.updateCartItemQuantity(customer,item,quantity);
        setData(customer);
    }



    public void setData(CustomerBean customer)
    {
        plusButtonClickListener=(item) -> modifyData(item,item.getQuantity()+1);
        minusButtonClickListener=(item) -> modifyData(item,item.getQuantity()-1);
        removeButtonClickListener=(item) -> modifyData(item,0);
        this.customer=customer;
        List<CartItemBean> cartList;
        cartList= CartItemService.fetchCartListForCostumer(customer);
        itemLayout.getChildren().clear();
        if(cartList.size()==0)
        {
            cartHBox.setVisible(false);
            payButton.setVisible(false);
            totalAmountLabel.setVisible(false);
            noProductsInCartAnchorPane.setVisible(true);
        }
        else {
                cartHBox.setVisible(true);
                payButton.setVisible(true);
                totalAmountLabel.setVisible(true);
                noProductsInCartAnchorPane.setVisible(false);
            try {
                for (int i = 0; i < cartList.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/cart-item.fxml"));
                    HBox hBox = fxmlLoader.load();

                    CartItemController cartItemController = fxmlLoader.getController();
                    cartItemController.setData(cartList.get(i), customer, plusButtonClickListener, minusButtonClickListener, removeButtonClickListener);
                    itemLayout.getChildren().add(hBox);


                }
                totalAmountLabel.setText("");
                totalAmountLabel.setText("Total Amount: $" + CartItemService.getTotalAmount(customer.getUserId()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

    }
    @FXML
    public void onPayButtonClick()
    {
        if(CartItemService.isCartQuantityAvailableInStock(customer.getUserId())) {
            Stage stage = (Stage) payButton.getScene().getWindow();
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

    }

}
