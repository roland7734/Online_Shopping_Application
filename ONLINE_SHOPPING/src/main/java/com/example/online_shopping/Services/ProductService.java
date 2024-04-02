package com.example.online_shopping.Services;

import com.example.online_shopping.beans.ProductBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.online_shopping.constants.Constants.*;
import org.apache.commons.lang3.tuple.Pair;


public class ProductService {


    public static List<ProductBean> fetchProductsFromDatabase() {
        List<ProductBean> productList = new ArrayList<>();

        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "SELECT * FROM products where isdeleted=false order by productname";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ProductBean product = new ProductBean(
                            rs.getInt("productid"),
                            rs.getString("productname"),
                            rs.getInt("categoryid"),
                            rs.getInt("brandid"),
                            rs.getDouble("price"),
                            rs.getInt("stockquantity"),
                            rs.getString("imageurl"),
                            rs.getString("description"),
                            rs.getInt("manufacturerid")
                    );
                    productList.add(product);
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public static List<ProductBean> fetchSearchProductsFromDatabase(String termToBeSearched) {
        List<ProductBean> productList = new ArrayList<>();
        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "SELECT * FROM products where lower(productname) like ? and isdeleted=false order by productname";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + termToBeSearched.toLowerCase() + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ProductBean product = new ProductBean(
                            rs.getInt("productid"),
                            rs.getString("productname"),
                            rs.getInt("categoryid"),
                            rs.getInt("brandid"),
                            rs.getDouble("price"),
                            rs.getInt("stockquantity"),
                            rs.getString("imageurl"),
                            rs.getString("description"),
                            rs.getInt("manufacturerid")
                    );
                    productList.add(product);
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public static List<ProductBean> fetchSpecificCategoryProductsFromDatabase(String category) {
        List<ProductBean> productList = new ArrayList<>();

        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "\n" +
                        "SELECT * FROM products join categories on (products.categoryid=categories.categoryid) where lower(categories.categoryname) = ? and isdeleted=false order by productname";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, category.toLowerCase());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ProductBean product = new ProductBean(
                            rs.getInt("productid"),
                            rs.getString("productname"),
                            rs.getInt("categoryid"),
                            rs.getInt("brandid"),
                            rs.getDouble("price"),
                            rs.getInt("stockquantity"),
                            rs.getString("imageurl"),
                            rs.getString("description"),
                            rs.getInt("manufacturerid")
                    );
                    productList.add(product);
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public static List<Pair<ProductBean, String>> fetchProductsfromDatabaseWithCategory() {
        List<Pair<ProductBean, String>> productList = new ArrayList<>();

        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "SELECT * FROM products JOIN categories on products.categoryid=categories.categoryid where isdeleted=false order by productname";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ProductBean product = new ProductBean(
                            rs.getInt("productid"),
                            rs.getString("productname"),
                            rs.getInt("categoryid"),
                            rs.getInt("brandid"),
                            rs.getDouble("price"),
                            rs.getInt("stockquantity"),
                            rs.getString("imageurl"),
                            rs.getString("description"),
                            rs.getInt("manufacturerid")
                    );

                    productList.add(Pair.of(product, rs.getString("categoryname")));
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public static boolean decreaseBoughtProductsStockQuantity(int userid) {
        boolean ok = false;
        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "UPDATE public.products p\n" +
                        "SET stockquantity = stockquantity - c.quantity\n" +
                        "FROM public.cart c\n" +
                        "WHERE p.productid = c.productid\n" +
                        "AND c.userid = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, userid);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0)
                    ok = true;
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static boolean deleteProduct(int productId) {
        boolean ok = false;
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "UPDATE public.products\n" +
                        "SET isdeleted = true\n" +
                        "WHERE productid = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, productId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0)
                    ok = true;
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static boolean updateProduct(ProductBean product) {
        boolean ok = false;
        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "UPDATE public.products SET productname = ?, " +
                        "    categoryid = ?, brandid = ?, price = ?, stockquantity = ?, imageurl = ?," +
                        "    description = ?, manufacturerid = ? WHERE  productid = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, product.getProductName());
                ps.setInt(2, product.getCategoryId());
                ps.setInt(3, product.getBrandId());
                ps.setDouble(4, product.getPrice());
                ps.setInt(5, product.getStockQuantity());
                ps.setString(6, product.getImageUrl());
                ps.setString(7, product.getDescription());
                ps.setInt(8, product.getManufacturerId());
                ps.setInt(9, product.getProductId());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0)
                    ok = true;
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    private static boolean isIDFound(int id) {
        boolean ok = false;
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {

                PreparedStatement ps = conn.prepareStatement("select * from products where productid=?");
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

    public static int createNewProductId() {
        int id;
        do {
            id = new Random().nextInt(1000000) + 10;

        } while (isIDFound(id) == true);
        return id;
    }

    public static boolean createProduct(ProductBean product) {
        boolean ok = false;
        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "INSERT INTO public.products (productname, categoryid, brandid, price, stockquantity, imageurl, description, manufacturerid, productid)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, product.getProductName());
                ps.setInt(2, product.getCategoryId());
                ps.setInt(3, product.getBrandId());
                ps.setDouble(4, product.getPrice());
                ps.setInt(5, product.getStockQuantity());
                ps.setString(6, product.getImageUrl());
                ps.setString(7, product.getDescription());
                ps.setInt(8, product.getManufacturerId());
                ps.setInt(9, product.getProductId());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0)
                    ok = true;
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }
}

