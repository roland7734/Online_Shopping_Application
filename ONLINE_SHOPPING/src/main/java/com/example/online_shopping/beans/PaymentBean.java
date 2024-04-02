package com.example.online_shopping.beans;
import java.sql.Timestamp;

public class PaymentBean {
    private int paymentId;
    private int orderId;
    private Timestamp paymentDate;
    private double amount;
    private String paymentMethod;
    private String transactionId;

    // Constructors
    public PaymentBean() {
        // Default constructor
    }

    public PaymentBean(int paymentId, int orderId, Timestamp paymentDate, double amount, String paymentMethod, String transactionId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
    }

    // Getter and Setter methods

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "PaymentBean{" +
                "paymentId=" + paymentId +
                ", orderId=" + orderId +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
