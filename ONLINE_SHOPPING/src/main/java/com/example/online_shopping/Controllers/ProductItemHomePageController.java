package com.example.online_shopping.Controllers;

import com.example.online_shopping.Listeners.ProductListener;
import com.example.online_shopping.beans.ProductBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductItemHomePageController {

    @FXML
    private Button addToCartButton;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    private Button seeDetailsButton;
    private ProductBean product;
    private ProductListener listener;

    @FXML
    private void onSeeDetailsClick() {
        listener.onClickListener(product);
    }

    public void setData(ProductBean product, ProductListener listener) {

        this.product = product;
        this.listener = listener;
        productName.setText(product.getProductName());
        productPrice.setText("Price: $" + product.getPrice());
        Image image = new Image(getClass().getResourceAsStream("/com/example/online_shopping/ProductsImages/" + product.getImageUrl()));
        productImage.setImage(image);


    }
}
