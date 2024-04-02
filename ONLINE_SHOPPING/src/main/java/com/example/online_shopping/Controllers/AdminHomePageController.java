package com.example.online_shopping.Controllers;


import com.example.online_shopping.Listeners.ProductListener;
import com.example.online_shopping.Services.AlertService;
import com.example.online_shopping.Services.ProductService;
import com.example.online_shopping.Services.VoucherService;
import com.example.online_shopping.beans.ProductBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminHomePageController implements Initializable {
    @FXML
    private AnchorPane principalPageAnchorPane;

    @FXML
    private AnchorPane HomePageAnchorPane;

    @FXML
    private AnchorPane ProductAnchorPane;

    @FXML
    private Button addProductButton;

    @FXML
    private Text descriptionText;

    @FXML
    private Text discover1Text;

    @FXML
    private Text discover1Text1;

    @FXML
    private Text discover2Text;

    @FXML
    private Text discover2Text1;

    @FXML
    private MenuItem electronicsChoiceButton;

    @FXML
    private MenuItem gamingChoiceButton;

    @FXML
    private GridPane grid;

    @FXML
    private HBox hBox;

    @FXML
    private Button homePageButton;

    @FXML
    private MenuItem kitchenChoiceButton;

    @FXML
    private MenuItem laptopsChoiceButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button ordersButton;

    @FXML
    private MenuItem phonesChoiceButton;

    @FXML
    private Text priceText;

    @FXML
    private ImageView productImg;

    @FXML
    private Text productNameText;

    @FXML
    private Button productsButton;

    @FXML
    private Button removeButton;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button searchButton;

    @FXML
    private Text searchInfoText;

    @FXML
    private Text searchInfoText1;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button stockButton;

    @FXML
    private MenuItem tabletsButton;

    @FXML
    private Rectangle topBar;

    @FXML
    private MenuItem tvChoiceButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button voucherButton;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane withoutMenuAnchorPaneForOtherPages;

    @FXML
    private AnchorPane withoutMenuHomePageAnchorPane;

    private ProductListener listener;
    private ProductBean product;

    @FXML
    void onAddProductButtonClick() {
        Stage stage = (Stage) addProductButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/add-update-product.fxml"));

        Scene scene;
        try {
            Parent root = fxmlLoader.load();
            scene = new Scene(root);
            AddUpdateProductController addUpdateProductController = fxmlLoader.getController();
            addUpdateProductController.setAddProduct();
            stage.setTitle("Add Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


    @FXML
    void onOrdersButtonClick() {
        withoutMenuHomePageAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.setVisible(true);
        principalPageAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.getChildren().clear();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/admin-order-details.fxml"));
        AnchorPane anchorPane;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AdminOrderDetailsController adminOrderDetailsController = fxmlLoader.getController();
        adminOrderDetailsController.setData();
        withoutMenuAnchorPaneForOtherPages.getChildren().add(anchorPane);

    }


    @FXML
    void onUpdateClick() {
        Stage stage = (Stage) updateButton.getScene().getWindow();
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

    @FXML
    void onRemoveButtonClick() {
        if(ProductService.deleteProduct(product.getProductId()))
            AlertService.openAlertInfo("The "+ product.getProductName()+" product was removed successfully!");
        else AlertService.openAlertError("Couldn't remove the "+ product.getProductName()+" product!");
        onProductsButtonClick();
    }


    @FXML
    void onStockButtonClick() {
        withoutMenuHomePageAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.setVisible(true);
        principalPageAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.getChildren().clear();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/stock-details.fxml"));
        AnchorPane anchorPane;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StockDetailsController stockDetailsController = fxmlLoader.getController();
        stockDetailsController.setData();
        withoutMenuAnchorPaneForOtherPages.getChildren().add(anchorPane);

    }




    public void setChoosenProduct(ProductBean product)
    {
        this.product=product;
        HomePageAnchorPane.setVisible(false);
        ProductAnchorPane.setVisible(true);
        productNameText.setText(product.getProductName());
        descriptionText.setText("Description: "+product.getDescription());
        priceText.setText("Price: $"+product.getPrice());
        Image image=new Image(getClass().getResourceAsStream("/com/example/online_shopping/ProductsImages/"+product.getImageUrl()));
        productImg.setImage(image);
    }


    public void insertIntoGrid(List<ProductBean> productList) {
        HomePageAnchorPane.setVisible(true);
        ProductAnchorPane.setVisible(false);
        listener = product -> setChoosenProduct(product);
        if (productList.size() > 0) {

            int column = 0;
            int row = 1;

            try {
                for (int i = 0; i < productList.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/product-item-home-page.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ProductItemHomePageController productItemHomePageController = fxmlLoader.getController();
                    productItemHomePageController.setData(productList.get(i), listener);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }
                    grid.add(anchorPane, column++, row);
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);

                    grid.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(10));
                    scroll.setVvalue(0);

                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        else {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/no-results-product-search.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                grid.add(anchorPane,0,1);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);

                grid.setMaxHeight(Region.USE_PREF_SIZE);

            } catch(IOException e)
            {
                System.out.println(e);
            }



        }
    }


    @Override

    public void initialize(URL location, ResourceBundle resources) {

        withoutMenuHomePageAnchorPane.setVisible(true);
        withoutMenuAnchorPaneForOtherPages.setVisible(false);
        principalPageAnchorPane.setVisible(true);
        HomePageAnchorPane.setVisible(true);
        ProductAnchorPane.setVisible(false);
        grid.getChildren().clear();

    }

    @FXML
    public void onSearchButtonClick()
    {
        withoutMenuHomePageAnchorPane.setVisible(true);
        withoutMenuAnchorPaneForOtherPages.setVisible(false);
        principalPageAnchorPane.setVisible(false);

        String toSearch=searchTextField.getText();
        List<ProductBean> productList = ProductService.fetchSearchProductsFromDatabase(toSearch);
        grid.getChildren().clear();
        insertIntoGrid(productList);

    }
    @FXML
    public void onHomePageButtonClick() {

        withoutMenuHomePageAnchorPane.setVisible(true);
        withoutMenuAnchorPaneForOtherPages.setVisible(false);
        principalPageAnchorPane.setVisible(true);
        HomePageAnchorPane.setVisible(true);
        ProductAnchorPane.setVisible(false);
        grid.getChildren().clear();



    searchTextField.clear();

    }

    @FXML
    public void onProductsButtonClick()
    {
        withoutMenuHomePageAnchorPane.setVisible(true);
        withoutMenuAnchorPaneForOtherPages.setVisible(false);
        principalPageAnchorPane.setVisible(false);

        List<ProductBean> productList = ProductService.fetchProductsFromDatabase();
        grid.getChildren().clear();
        searchTextField.clear();
        insertIntoGrid(productList);
    }

    public void onCategoryClick(String category)
    {
        withoutMenuHomePageAnchorPane.setVisible(true);
        withoutMenuAnchorPaneForOtherPages.setVisible(false);
        principalPageAnchorPane.setVisible(false);
        List<ProductBean> productList = ProductService.fetchSpecificCategoryProductsFromDatabase(category);
        grid.getChildren().clear();
        insertIntoGrid(productList);
    }

    @FXML
    public void onPhonesChoiceButtonClick(){
        onCategoryClick("Phones");
    }
    @FXML
    public void onTabletsChoiceButtonClick(){
        onCategoryClick("Tablets");

    }
    @FXML
    public void onTvChoiceButtonClick(){
        onCategoryClick("TVs");

    }
    @FXML
    public void onLaptopsChoiceButtonClick(){
        onCategoryClick("Laptops");

    }
    @FXML
    public void onKitchenChoiceButtonClick(){
        onCategoryClick("Kitchen Appliances");
    }
    @FXML
    public void onElectronicsChoiceButtonClick(){
        onCategoryClick("Consumer Electronics");
    }
    @FXML
    public void onGamingChoiceButtonClick(){
        onCategoryClick("Gaming");
    }

    @FXML
    void onVoucherButtonClick() {
        withoutMenuHomePageAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.setVisible(true);
        principalPageAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.getChildren().clear();
        VoucherService.setVouchersInactiveWhenExpired();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/admin-voucher.fxml"));
        AnchorPane anchorPane;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AdminVoucherController adminVoucherController = fxmlLoader.getController();
        adminVoucherController.setData();

        withoutMenuAnchorPaneForOtherPages.getChildren().add(anchorPane);
    }

    @FXML
    public void onLogOutButtonClick()
    {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/start-page.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        stage.setTitle("Start Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onManufacturersButtonClick() {
        withoutMenuHomePageAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.setVisible(true);
        principalPageAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/manufacturers-details.fxml"));
        AnchorPane anchorPane;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ManufacturersDetailsController manufacturersDetailsController = fxmlLoader.getController();
        manufacturersDetailsController.setData();

        withoutMenuAnchorPaneForOtherPages.getChildren().add(anchorPane);
    }

}