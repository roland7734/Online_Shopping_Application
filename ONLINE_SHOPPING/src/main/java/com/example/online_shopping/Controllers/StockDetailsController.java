package com.example.online_shopping.Controllers;

import com.example.online_shopping.Listeners.RemoveProductButtonClickListener;
import com.example.online_shopping.Services.AlertService;
import com.example.online_shopping.Services.ProductService;
import com.example.online_shopping.Listeners.UpdateProductButtonClickListener;
import com.example.online_shopping.beans.ProductBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.List;

public class StockDetailsController {


    @FXML
    private VBox itemLayout;

    @FXML
    private ScrollPane scroll;
    private UpdateProductButtonClickListener updateProductButtonClickListener;
    private RemoveProductButtonClickListener removeProductButtonClickListener;

    public void updateProduct(ProductBean product)
    {
        Stage stage = (Stage) itemLayout.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/add-update-product.fxml"));

        Scene scene;
        try {
            Parent root = fxmlLoader.load();
            scene = new Scene(root);
            AddUpdateProductController addUpdateProductController = fxmlLoader.getController();
            addUpdateProductController.setUpdate(product);
            stage.setTitle("Update Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void removeProduct(ProductBean product)
    {
        if(ProductService.deleteProduct(product.getProductId()))
            AlertService.openAlertInfo("The "+ product.getProductName()+ " product was removed successfully!");
        else AlertService.openAlertError("Couldn't delete "+ product.getProductName()+ " product!");
        setData();
    }

    public void setData()
    {
        scroll.setVvalue(0);
        updateProductButtonClickListener=product->updateProduct(product);
        removeProductButtonClickListener=product -> removeProduct(product);
        List<Pair<ProductBean, String>> productList= ProductService.fetchProductsfromDatabaseWithCategory();
        itemLayout.getChildren().clear();
        itemLayout.setSpacing(10);

        try {
            for (int i = 0; i < productList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/stock-item.fxml"));
                HBox hBox = fxmlLoader.load();

                StockItemController stockItemController = fxmlLoader.getController();
                stockItemController.setData(productList.get(i), updateProductButtonClickListener, removeProductButtonClickListener);
                itemLayout.getChildren().add(hBox);

            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
