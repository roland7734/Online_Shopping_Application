package com.example.online_shopping.beans;
public class CustomerBean extends UserBean{
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;

    // Constructors
    public CustomerBean() {
        // Default constructor
    }

    public CustomerBean(String firstName, String lastName, int userId, String address, String phone, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.username=username;
        this.password=password;
    }

    // Getter and Setter methods

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CustomerBean{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userId=" + userId +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
