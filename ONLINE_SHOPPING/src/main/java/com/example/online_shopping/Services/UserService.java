package com.example.online_shopping.Services;

import com.example.online_shopping.beans.CustomerBean;
import com.example.online_shopping.beans.UserBean;

import java.sql.*;
import java.util.Random;

import static com.example.online_shopping.constants.Constants.*;


public class UserService {


    public static String registerUser(String username, String password, String firstName, String lastName, String address, String phone, String email) {
        int userid;
        do {
            userid = new Random().nextInt(101) + 100;

        } while (isIDFound(userid) == true);
        UserBean user = new UserBean(userid, username, password);
        CustomerBean customer = new CustomerBean(firstName, lastName, userid, address, phone, email, username, password);
        return registerUser(user, customer);

    }

    public static String registerUser(UserBean user, CustomerBean customer) {
        String status = "RegistrationController Failed!";
        boolean isAlreadyRegistered = isEmailUnique(customer.getEmail());
        if (isAlreadyRegistered) {
            status = "The Account is already created!";
            return status;
        }
        if (isUsernameUnique(user.getUsername())) {
            status = "The username must be unique!";
            return status;
        }
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement("insert into users(userid,username,password,type_user) values(?,?,?,'customer')");
                ps.setInt(1, user.getUserId());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                int rowsAffectedUser = ps.executeUpdate();
                if (rowsAffectedUser > 0) {
                    ps = conn.prepareStatement("insert into customers(firstname,lastname,userid,address,phone,email) values(?,?,?,?,?,?)");
                    ps.setString(1, customer.getFirstName());
                    ps.setString(2, customer.getLastName());
                    ps.setInt(3, customer.getUserId());
                    ps.setString(4, customer.getAddress());
                    ps.setString(5, customer.getPhone());
                    ps.setString(6, customer.getEmail());
                    int rowsAffectedCustomer = ps.executeUpdate();
                    if (rowsAffectedCustomer > 0) {
                        status = "RegistrationController Successful!";
                    }

                }
                ps.close();
            }

        } catch (SQLException e) {
            status = "Error: " + e.getMessage();
            e.printStackTrace();
        }
        return status;


    }

    public static boolean isEmailUnique(String email) {
        boolean flag = false;

        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {

                PreparedStatement ps = conn.prepareStatement("select * from customers where email=?");

                ps.setString(1, email);

                ResultSet rs = ps.executeQuery();

                if (rs.next())
                    flag = true;
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return flag;
    }

    public static boolean isUsernameUnique(String username) {
        boolean flag = false;

        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {

                PreparedStatement ps = conn.prepareStatement("select * from users where username=?");

                ps.setString(1, username);

                ResultSet rs = ps.executeQuery();

                if (rs.next())
                    flag = true;
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return flag;
    }

    public static boolean isIDFound(int id) {
        boolean ok = false;
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {

                PreparedStatement ps = conn.prepareStatement("select * from users where userid=?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    ok = true;
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static CustomerBean getCustomer(String username) {

        CustomerBean customer = new CustomerBean();
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement("select * from users join customers on (users.userid=customers.userid) where username=? ");
                ps.setString(1, username);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    customer = new CustomerBean(
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getInt("userid"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("password"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static boolean isCustomerInDatabase(String username, String password) {
        boolean customerExists = false;
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;

        try {
            conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {
                String query = "SELECT * FROM users WHERE username = ? AND password = ? AND type_user='customer' ";
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();

                if (rs.next()) {
                    customerExists = true;
                } else {
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerExists;
    }

    public static boolean isAdminInDatabase(String username, String password) {
        boolean adminExists = false;
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;

        try {
            conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {
                String query = "SELECT * FROM users WHERE username = ? AND password = ? AND type_user='admin' ";
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();

                if (rs.next()) {
                    adminExists = true;
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adminExists;
    }

}

