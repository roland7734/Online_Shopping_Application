package com.example.online_shopping.beans;

public class ProductBean {
    private int productId;
    protected String productName;
    private int categoryId;
    private int brandId;
    private double price;
    private int stockQuantity;
    protected String imageUrl;
    private String description;
    private int manufacturerId;

    // Constructors
    public ProductBean() {
        // Default constructor
    }

    public ProductBean(int productId, String productName, int categoryId, int brandId, double price, int stockQuantity, String imageUrl, String description, int manufacturerId) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.description = description;
        this.manufacturerId = manufacturerId;
    }

    // Getter and Setter methods

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", manufacturerId=" + manufacturerId +
                '}';
    }
}
