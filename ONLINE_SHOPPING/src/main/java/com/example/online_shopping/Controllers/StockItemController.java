package com.example.online_shopping.Controllers;


import com.example.online_shopping.Listeners.RemoveProductButtonClickListener;
import com.example.online_shopping.Listeners.UpdateProductButtonClickListener;
import org.apache.commons.lang3.tuple.Pair;
import com.example.online_shopping.beans.ProductBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StockItemController {

    @FXML
    private Label categoryLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Label priceLabel;

    @FXML
    private Label productDescritpionLabel;

    @FXML
    private ImageView productIamge;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Button updateButton;
    private ProductBean product;



    private UpdateProductButtonClickListener updateProductButtonClickListener;
    private RemoveProductButtonClickListener removeProductButtonClickListener;


    public void setData(Pair<ProductBean, String> pair, UpdateProductButtonClickListener updateProductButtonClickListener,RemoveProductButtonClickListener removeProductButtonClickListener)
    {
        this.product=pair.getLeft();
        this.updateProductButtonClickListener=updateProductButtonClickListener;
        this.removeProductButtonClickListener=removeProductButtonClickListener;
        categoryLabel.setText(pair.getRight());
        priceLabel.setText("$"+pair.getLeft().getPrice());
        productNameLabel.setText(pair.getLeft().getProductName());
        productDescritpionLabel.setText(pair.getLeft().getDescription());
        quantityLabel.setText(String.valueOf(pair.getLeft().getStockQuantity()));
        Image img=new Image(getClass().getResourceAsStream("/com/example/online_shopping/ProductsImages/" + pair.getLeft().getImageUrl()));
        productIamge.setImage(img);

    }

    @FXML
    void onDeleteButtonClick() {
        removeProductButtonClickListener.onRemoveProductButtonClickListener(product);
    }

    @FXML
    void onUpdateButtonClick() {
        updateProductButtonClickListener.onUpdateProductButtonClickListener(product);
    }

}
