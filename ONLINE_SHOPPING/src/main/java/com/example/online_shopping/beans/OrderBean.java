package com.example.online_shopping.beans;
import java.sql.Timestamp;

public class OrderBean {
    private int orderId;
    private int userId;
    private String address;
    private String voucherName;
    private Timestamp orderDate;
    private double totalAmount;
    private String status;
    private double totalAmountBeforeDiscount;
    private int discount;
    private String paymentMethod;

    // Constructors
    public OrderBean() {
        // Default constructor
    }

    public OrderBean(int orderId, int userId, String address, String voucherName, Timestamp orderDate, double totalAmount, String status, double totalAmountBeforeDiscount,int discount, String paymentMethod) {
        this.orderId = orderId;
        this.userId = userId;
        this.address = address;
        this.voucherName = voucherName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.totalAmountBeforeDiscount=totalAmountBeforeDiscount;
        this.discount=discount;
        this.paymentMethod=paymentMethod;
    }

    // Getter and Setter methods

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public double getTotalAmountBeforeDiscount(){return totalAmountBeforeDiscount;}
    public void setTotalAmountBeforeDiscount(double totalAmountBeforeDiscount) {this.totalAmountBeforeDiscount=totalAmountBeforeDiscount;}
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    @Override
    public String toString() {
        return "OrderBean{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", addressId=" + address +
                ", promotionId=" + voucherName +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", totalAmountBeforeDiscount="+totalAmountBeforeDiscount+
                '}';
    }
}
