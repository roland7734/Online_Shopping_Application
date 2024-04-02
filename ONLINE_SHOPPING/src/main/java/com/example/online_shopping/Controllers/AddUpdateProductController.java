package com.example.online_shopping.Controllers;


import com.example.online_shopping.Services.*;
import com.example.online_shopping.beans.BrandBean;
import com.example.online_shopping.beans.CategoryBean;
import com.example.online_shopping.beans.ManufacturerBean;
import com.example.online_shopping.beans.ProductBean;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddUpdateProductController {

    @FXML
    private Button uploadButton;

    @FXML
    private Button addUpdateButton;

    @FXML
    private Label addUpdateLabel;

    @FXML
    private ComboBox<String> brandComboBox;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ComboBox<String> manufacturerComboBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField urlTextField;
    private boolean update;
    private int productId;

    @FXML
    void onAddUpdateButtonClick() {
        if(!areFieldsNonNull())
            AlertService.openAlertWarning("Please fill in all fields!");
        else {
            if(!StringValidationService.isValidPrice(priceTextField.getText()))
                AlertService.openAlertWarning("The provided price field is invalid. Please introduce a valid price!");
            else {
                if(!StringValidationService.containsOnlyNumbers(quantityTextField.getText()))
                {
                    AlertService.openAlertWarning("The provided quantity is invalid. Please introduce a number!");
                }
                else {
                    if(update)
                    {
                        ProductBean product = new ProductBean(productId,
                                nameTextField.getText(), categoryComboBox.getSelectionModel().getSelectedIndex() + 1,
                                brandComboBox.getSelectionModel().getSelectedIndex() + 1, Double.parseDouble(priceTextField.getText()), Integer.parseInt(quantityTextField.getText()),
                                urlTextField.getText(), descriptionTextField.getText(), manufacturerComboBox.getSelectionModel().getSelectedIndex() + 1);
                        if(ProductService.updateProduct(product))
                            AlertService.openAlertInfo("The product was updated successfully!");
                        else AlertService.openAlertError("The product couldn't be updated!");
                    }
                    else {
                        ProductBean product = new ProductBean(ProductService.createNewProductId(),
                                nameTextField.getText(), categoryComboBox.getSelectionModel().getSelectedIndex() + 1,
                                brandComboBox.getSelectionModel().getSelectedIndex() + 1, Double.parseDouble(priceTextField.getText()), Integer.parseInt(quantityTextField.getText()),
                                urlTextField.getText(), descriptionTextField.getText(), manufacturerComboBox.getSelectionModel().getSelectedIndex() + 1);
                        if (ProductService.createProduct(product))
                            AlertService.openAlertInfo("The product was created successfully!");
                        else AlertService.openAlertError("Couldn't create the product!");

                    }
                    Stage stage = (Stage) addUpdateButton.getScene().getWindow();
                    stage.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/admin-home-page.fxml"));
                    Scene scene;
                    try {
                        Parent root = fxmlLoader.load();
                        scene = new Scene(root);
                        stage.setTitle("Admin Home Page");
                        AdminHomePageController adminHomePageController=fxmlLoader.getController();
                        adminHomePageController.onStockButtonClick();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
            }
        }



    }

    @FXML
    void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/online_shopping/admin-home-page.fxml"));
        Scene scene;
        try {
            Parent root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setTitle("Admin Home Page");
            AdminHomePageController adminHomePageController=fxmlLoader.getController();
            adminHomePageController.onStockButtonClick();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public void setUpdate(ProductBean product) {
        this.update=true;
        this.productId=product.getProductId();
        List<CategoryBean> categoryList = CategoryService.getCategories();
        for (CategoryBean category : categoryList) {
            categoryComboBox.getItems().add(category.getCategoryName());
        }
        List<BrandBean> brandList = BrandService.getBrands();
        for (BrandBean brand : brandList) {
            brandComboBox.getItems().add(brand.getBrandName());
        }
        List<ManufacturerBean> manufacturerList = ManufacturerService.getManufacturers();
        for (ManufacturerBean manufacturer : manufacturerList) {
            manufacturerComboBox.getItems().add(manufacturer.getManufacturerName());
        }
        brandComboBox.getSelectionModel().select(product.getBrandId()-1);
        manufacturerComboBox.getSelectionModel().select(product.getManufacturerId()-1);
        categoryComboBox.getSelectionModel().select(product.getCategoryId()-1);
        nameTextField.setText(product.getProductName());
        descriptionTextField.setText(product.getDescription());
        priceTextField.setText(String.valueOf(product.getPrice()));
        quantityTextField.setText(String.valueOf(product.getStockQuantity()));
        urlTextField.setText(product.getImageUrl());
        addUpdateLabel.setText("Update Product");
        addUpdateButton.setText("Update");
    }

    public void setAddProduct() {
        this.update=false;
        List<CategoryBean> categoryList = CategoryService.getCategories();
        for (CategoryBean category : categoryList) {
            categoryComboBox.getItems().add(category.getCategoryName());
        }
        List<BrandBean> brandList = BrandService.getBrands();
        for (BrandBean brand : brandList) {
            brandComboBox.getItems().add(brand.getBrandName());
        }
        List<ManufacturerBean> manufacturerList = ManufacturerService.getManufacturers();
        for (ManufacturerBean manufacturer : manufacturerList) {
            manufacturerComboBox.getItems().add(manufacturer.getManufacturerName());
        }
        addUpdateLabel.setText("Add Product");
        addUpdateButton.setText("Add");
    }

    @FXML
    public void onUploadButtonClick()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files","*.jpg", "*.png", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile!=null)
        {
            urlTextField.setText(selectedFile.getName());
            moveImageToFolder(selectedFile, "/com/example/online_shopping/ProductsImages/");

        }

    }
    private void moveImageToFolder(File sourceFile, String destinationFolderPathString) {
        try {
            URL destinationFolderUrl = getClass().getResource(destinationFolderPathString);

            Path destinationFolderPath = Paths.get(destinationFolderUrl.toURI());

            Path destinationPath = destinationFolderPath.resolve(sourceFile.getName());

            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public boolean areFieldsNonNull() {
        return brandComboBox != null &&
                categoryComboBox != null &&
                descriptionTextField != null &&
                manufacturerComboBox != null &&
                nameTextField != null &&
                priceTextField != null &&
                quantityTextField != null &&
                urlTextField != null;
    }
}
