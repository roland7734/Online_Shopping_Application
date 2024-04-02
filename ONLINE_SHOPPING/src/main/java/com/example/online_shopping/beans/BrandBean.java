package com.example.online_shopping.beans;

public class BrandBean {
    private int brandId;
    private String brandName;

    // Constructors
    public BrandBean() {
        // Default constructor
    }

    public BrandBean(int brandId, String brandName) {
        this.brandId = brandId;
        this.brandName = brandName;
    }

    // Getter and Setter methods

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "BrandBean{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
