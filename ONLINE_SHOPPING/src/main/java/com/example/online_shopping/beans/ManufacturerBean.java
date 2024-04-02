package com.example.online_shopping.beans;
public class ManufacturerBean {
    private int manufacturerId;
    private String manufacturerName;
    private String address;
    private String website;
    private String contactEmail;
    private String contactPhone;

    // Constructors
    public ManufacturerBean() {
        // Default constructor
    }

    public ManufacturerBean(int manufacturerId, String manufacturerName, String address, String website, String contactEmail, String contactPhone) {
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
        this.address = address;
        this.website = website;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    // Getter and Setter methods

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        return "ManufacturerBean{" +
                "manufacturerId=" + manufacturerId +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                '}';
    }
}
