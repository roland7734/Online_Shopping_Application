package com.example.online_shopping.beans;

public class UserBean {
    protected int userId;
    protected String username;
    protected String password;

    // Constructors
    public UserBean() {
        // Default constructor
    }

    public UserBean(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    // Getter and Setter methods

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
