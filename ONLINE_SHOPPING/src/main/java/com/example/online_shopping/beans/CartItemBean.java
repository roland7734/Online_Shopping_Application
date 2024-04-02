package com.example.online_shopping.beans;

public class CartItemBean extends ProductBean{

    private int cartId;
    private int userId;
    private int productId;
    private int quantity;
    private double price;
    // Default constructor
    public CartItemBean() {
    }

    // Parameterized constructor
    public CartItemBean(int cartID, int userID, int productID, int quantity, String productName, double productPrice, String productURL) {
        this.cartId = cartID;
        this.userId = userID;
        this.productId = productID;
        this.quantity = quantity;
        this.productName=productName;
        this.price=productPrice;
        this.imageUrl=productURL;
    }

    // Getter and setter methods

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartID) {
        this.cartId = cartID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userID) {
        this.userId = userID;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productID) {
        this.productId = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // toString method for easy debugging and logging

    @Override
    public String toString() {
        return "CartItemBean{" +
                "cartID=" + cartId +
                ", userID=" + userId +
                ", productID=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
