package com.example.online_shopping.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OrderProductItemController {

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label qtyLabel;

    public void setData(String URL, String name, int quantity, Double price)
    {
        Image image = new Image(getClass().getResourceAsStream("/com/example/online_shopping/ProductsImages/" + URL));
        productImage.setImage(image);
        productNameLabel.setText(name);
        qtyLabel.setText(String.valueOf(quantity));
        priceLabel.setText("$"+price);
    }


}
