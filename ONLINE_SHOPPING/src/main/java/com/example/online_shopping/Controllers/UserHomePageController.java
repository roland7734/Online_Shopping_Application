package com.example.online_shopping.Controllers;

import com.example.online_shopping.Listeners.ProductListener;
import com.example.online_shopping.Services.CartItemService;
import com.example.online_shopping.beans.CustomerBean;
import com.example.online_shopping.beans.ProductBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import java.io.IOException;

import com.example.online_shopping.Services.ProductService;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class UserHomePageController implements Initializable {


    @FXML
    private AnchorPane principalPageAnchorPane;
    @FXML
    private Rectangle topBar;

    @FXML
    private AnchorPane withoutMenuAnchorPaneForOtherPages;
    @FXML
    private AnchorPane withoutMenuAnchorPane;
    @FXML
    private AnchorPane HomePageAnchorPane;

    @FXML
    private AnchorPane ProductAnchorPane;
    @FXML
    private Button addToCartPrincipalButton;

    @FXML
    private Button cartButton;

    @FXML
    private Text descriptionText;

    @FXML
    private Text discover1Text;

    @FXML
    private Text discover2Text;

    @FXML
    private MenuItem electronicsChoiceButton;

    @FXML
    private MenuItem gamingChoiceButton;

    @FXML
    private GridPane grid;

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
    private Button profileButton;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button searchButton;

    @FXML
    private Text searchInfoText;

    @FXML
    private TextField searchTextField;

    @FXML
    private MenuItem tabletsButton;

    @FXML
    private MenuItem tvChoiceButton;
    private ProductBean product;
    private CustomerBean customer;
    private ProductListener listener;




    public void setCustomer(CustomerBean customer)
    {
        this.customer=customer;
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
        boolean isInCart = CartItemService.isInCart(customer,product);
        if (isInCart) {
            addToCartPrincipalButton.setStyle("-fx-background-color: red;");
            addToCartPrincipalButton.setText("Remove from Cart");
        } else {
            addToCartPrincipalButton.setStyle("-fx-background-color: #00ff00;");
            addToCartPrincipalButton.setText("Add to Cart");
        }
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

                }
            } catch (IOException e) {
                System.out.println(e);
            }
            scroll.setVvalue(0);
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

        withoutMenuAnchorPane.setVisible(true);
        withoutMenuAnchorPaneForOtherPages.setVisible(false);
        principalPageAnchorPane.setVisible(true);
        HomePageAnchorPane.setVisible(true);
        ProductAnchorPane.setVisible(false);
        grid.getChildren().clear();



}

@FXML
public void onSearchButtonClick()
{
    withoutMenuAnchorPane.setVisible(true);
    withoutMenuAnchorPaneForOtherPages.setVisible(false);
    principalPageAnchorPane.setVisible(false);

    String toSearch=searchTextField.getText();
    List<ProductBean> productList = ProductService.fetchSearchProductsFromDatabase(toSearch);
    grid.getChildren().clear();
    insertIntoGrid(productList);

}
@FXML
    public void onHomePageButtonClick() {

    withoutMenuAnchorPane.setVisible(true);
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
    withoutMenuAnchorPane.setVisible(true);
    withoutMenuAnchorPaneForOtherPages.setVisible(false);
    principalPageAnchorPane.setVisible(false);


    List<ProductBean> productList = ProductService.fetchProductsFromDatabase();
    grid.getChildren().clear();
    searchTextField.clear();
    insertIntoGrid(productList);
}

public void onCategoryClick(String category)
{
    withoutMenuAnchorPane.setVisible(true);
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
    public void onAddToCartButtonClick()
    {
        withoutMenuAnchorPane.setVisible(true);
        withoutMenuAnchorPaneForOtherPages.setVisible(false);
        principalPageAnchorPane.setVisible(false);
        boolean isInCart = CartItemService.isInCart(customer,product);

        if (!isInCart) {
            CartItemService.addToCart(customer, product);
            addToCartPrincipalButton.setStyle("-fx-background-color: red;");
            addToCartPrincipalButton.setText("Remove from Cart");
        } else {
            CartItemService.removeFromCart(customer.getUserId(), product.getProductId());
            addToCartPrincipalButton.setStyle("-fx-background-color: #00ff00;");
            addToCartPrincipalButton.setText("Add to Cart");
        }
    }

    @FXML
    public void onCartButtonClick() {
        withoutMenuAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.setVisible(true);
        principalPageAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.getChildren().clear();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/cart-details.fxml"));
        AnchorPane anchorPane;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CartDetailsController cartDetailsController = fxmlLoader.getController();
        cartDetailsController.setData(customer);
        withoutMenuAnchorPaneForOtherPages.getChildren().add(anchorPane);

    }

    @FXML
    public void onOrdersButtonClick()
    {
        withoutMenuAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.setVisible(true);
        principalPageAnchorPane.setVisible(false);
        withoutMenuAnchorPaneForOtherPages.getChildren().clear();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/online_shopping/order-details.fxml"));
        AnchorPane anchorPane;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        OrderDetailsController orderDetailsController = fxmlLoader.getController();
        orderDetailsController.setData(customer.getUserId());
        withoutMenuAnchorPaneForOtherPages.getChildren().add(anchorPane);
    }


}