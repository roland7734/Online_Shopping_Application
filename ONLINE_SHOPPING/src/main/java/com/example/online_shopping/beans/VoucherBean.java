package com.example.online_shopping.beans;

import java.sql.Date;

public class VoucherBean {
    private String voucherCode;
    private int discountPercentage;
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private boolean isActive;

    // Default constructor
    public VoucherBean() {
    }

    // Parameterized constructor
    public VoucherBean(String voucherCode, int discountPercentage, java.sql.Date startDate, java.sql.Date endDate, boolean isActive) {
        this.voucherCode = voucherCode;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        updateStatusBasedOnDate();
    }

    // Getter and Setter methods for each property

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public java.sql.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void updateStatusBasedOnDate() {
        Date currentDate = new Date(System.currentTimeMillis());
        if (currentDate.after(endDate)) {
            isActive = false;
        }
    }

    @Override
    public String toString() {
        return "VoucherBean{" +
                "voucherCode='" + voucherCode + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isActive=" + isActive +
                '}';
    }
}
