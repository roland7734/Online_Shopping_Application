package com.example.online_shopping.beans;

public class OrderItemBean extends ProductBean{
    private int orderId;
    private int productId;
    private int quantity;
    private double subtotal;


    // Constructors
    public OrderItemBean() {
        // Default constructor
    }

    public OrderItemBean(int orderId, int productId, int quantity, double subtotal, String URL,String productname) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.imageUrl=URL;
        this.productName=productname;
    }

    // Getter and Setter methods

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "OrderItemBean{" +
                " orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                ", productname=" + productName +
                ", URL="+ imageUrl+
                '}';
    }
}
