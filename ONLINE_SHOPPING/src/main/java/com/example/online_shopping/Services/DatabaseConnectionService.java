package com.example.online_shopping.Services;
import java.sql.*;


public class DatabaseConnectionService {

    private static Connection conn;

    public static Connection connect_to_db(String dbname, String user, String pass) {
        if(conn == null) {

            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return conn;
    }

}