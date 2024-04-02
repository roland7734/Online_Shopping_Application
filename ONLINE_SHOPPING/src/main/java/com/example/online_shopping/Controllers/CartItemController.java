package com.example.online_shopping.Controllers;

import com.example.online_shopping.Listeners.MinusButtonClickListener;
import com.example.online_shopping.Listeners.PlusButtonClickListener;
import com.example.online_shopping.Listeners.RemoveButtonClickListener;
import com.example.online_shopping.Services.CartItemService;
import com.example.online_shopping.beans.CartItemBean;
import com.example.online_shopping.beans.CustomerBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartItemController {

    @FXML
    private Button plusButton;

    @FXML
    private Button minusButton;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Button removeButton;

    @FXML
    private Label subtotalLabel;
    private CartItemBean item;

    private PlusButtonClickListener plusButtonClickListener;
    private MinusButtonClickListener minusButtonClickListener;
    private RemoveButtonClickListener removeButtonClickListener;
    private CustomerBean customer;


    public void setData(CartItemBean item, CustomerBean customer, PlusButtonClickListener plusButtonClickListener, MinusButtonClickListener minusButtonClickListener, RemoveButtonClickListener removeButtonClickListener) {
        this.item = item;
        this.customer = customer;
        this.plusButtonClickListener = plusButtonClickListener;
        this.minusButtonClickListener = minusButtonClickListener;
        this.removeButtonClickListener = removeButtonClickListener;
        Image img = new Image(getClass().getResourceAsStream("/com/example/online_shopping/ProductsImages/" + item.getImageUrl()));
        productImage.setImage(img);
        productNameLabel.setText(item.getProductName());
        productPriceLabel.setText("$" + item.getPrice());
        subtotalLabel.setText("$" + CartItemService.getSubtotal(customer.getUserId(), item.getProductId()));
        quantityLabel.setText(String.valueOf((item.getQuantity())));

    }


    @FXML
    void onPlusButtonClick() {
        plusButtonClickListener.onPlusButtonClick(item);
    }

    @FXML
    void onMinusButtonClick() {
        minusButtonClickListener.onMinusButtonClick(item);
    }

    @FXML
    void onRemoveButtonClick() {
        removeButtonClickListener.onRemoveButtonClick(item);
    }


}