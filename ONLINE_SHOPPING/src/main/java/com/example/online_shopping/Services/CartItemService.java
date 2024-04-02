package com.example.online_shopping.Services;

import com.example.online_shopping.beans.CartItemBean;
import com.example.online_shopping.beans.CustomerBean;
import com.example.online_shopping.beans.ProductBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.online_shopping.constants.Constants.*;

public class CartItemService {


    private static boolean isIDFound(int id) {
            boolean ok=false;
            try {

                Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
                if (conn != null) {

                    PreparedStatement ps = conn.prepareStatement("select * from cart where cartid=?");
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        ok=true;
                    }
                    ps.close();
                    rs.close();
                }
            } catch(SQLException e)
            {
                e.printStackTrace();
            }
            return ok;

    }


    public static boolean addToCart(CustomerBean customer, ProductBean product) {
        boolean flag=false;
        int cartid=0;
        do {
            cartid ++;

        }while(isIDFound(cartid)==true);
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement("insert into cart(cartid,userid,productid,quantity) values(?,?,?,?)");
                ps.setInt(1,cartid);
                ps.setInt(2, customer.getUserId());
                ps.setInt(3, product.getProductId());
                ps.setInt(4, 1);
                int rowsAffectedUser = ps.executeUpdate();
                if (rowsAffectedUser> 0) {
                        flag = true;
                    }
                ps.close();
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;

    }


    public static boolean removeFromCart(int customerid, int productid) {
        boolean flag=false;
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement("DELETE FROM Cart WHERE productid = ? and userid = ?");
                ps.setInt(1,productid);
                ps.setInt(2, customerid);
                int rowsAffectedUser = ps.executeUpdate();
                if (rowsAffectedUser> 0) {
                    flag = true;
                }
                ps.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;

    }

    public static boolean isInCart(CustomerBean customer, ProductBean product) {
        boolean flag=false;
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement("Select * from Cart WHERE productid = ? and userid = ?");
                ps.setInt(1,product.getProductId());
                ps.setInt(2, customer.getUserId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    flag=true;
                }
                ps.close();
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;

    }

    public static boolean updateCartItemQuantity(CustomerBean customer, CartItemBean item, int quantity) {
        boolean flag=false;
        if (quantity==0)
        {
            return removeFromCart(customer.getUserId(),item.getProductId());
        }
        else {
            try {
                Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
                if (conn != null) {
                    PreparedStatement ps = conn.prepareStatement("update cart set Quantity = ? WHERE UserID = ? AND ProductID = ?");
                    ps.setInt(1, quantity);
                    ps.setInt(2, customer.getUserId());
                    ps.setInt(3, item.getProductId());
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        flag = true;
                    }
                    ps.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;


    }


    public static List<CartItemBean> fetchCartListForCostumer(CustomerBean customer) {
        List<CartItemBean> cartList = new ArrayList<>();

        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "SELECT * FROM cart join products on (products.productid=cart.productid) join customers on (customers.userid=cart.userid) where products.isdeleted = false and customers.userid=? order by cart.cartid";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,customer.getUserId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    CartItemBean item = new CartItemBean(
                            rs.getInt("cartid"),
                            rs.getInt("userid"),
                            rs.getInt("productid"),
                            rs.getInt("quantity"),
                            rs.getString("productname"),
                            rs.getDouble("price"),
                            rs.getString("imageurl")
                    );
                    cartList.add(item);
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartList;
    }

    public static boolean deleteCart(int userid) {
        boolean flag=false;
        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {

                PreparedStatement ps = conn.prepareStatement("delete from public.cart where (userid, productid) in " +
                        "(select c.userid, c.productid from public.cart c join public.orders o on c.userid = o.userid " +
                        "where c.userid = ?)");
                ps.setInt(1,userid);
                int rowsAffectedUser = ps.executeUpdate();
                if (rowsAffectedUser> 0) {
                    flag = true;
                }
                ps.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;

    }

    public static boolean isCartQuantityAvailableInStock(int userid) {
        boolean ok=true;

        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "SELECT p.productname, p.stockquantity, c.quantity\n" +
                        "FROM public.products p\n" +
                        "JOIN public.cart c ON p.productid = c.productid\n" +
                        "WHERE c.userid = ? AND p.stockquantity < c.quantity;\n";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,userid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    ok = false;
                    if (rs.getInt(2) > 1)
                        AlertService.openAlertError("Only " + rs.getInt(2) +" "+ rs.getString(1) + " products are currently available!");
                    else if (rs.getInt(2) == 1) {
                        AlertService.openAlertError("Only one " + rs.getString(1) + " product is currently available!");
                    } else
                        AlertService.openAlertError("No " + rs.getString(1) + " products are currently available!");
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ok;
    }


    public static double getSubtotal(int userid, int productid)
    {
        double subtotal=0;
        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "SELECT SUM(p.price * c.quantity) AS subtotal\n" +
                        "FROM public.cart c\n" +
                        "JOIN public.products p ON c.productid = p.productid\n" +
                        "WHERE c.userid = ? and p.productid = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,userid);
                ps.setInt(2,productid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    subtotal=rs.getDouble("subtotal");
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subtotal;
    }

    public static double getTotalAmount(int userid)
    {
        double total=0;
        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "SELECT SUM(p.price * c.quantity) AS total_amount\n" +
                        "FROM public.cart c\n" +
                        "JOIN public.products p ON c.productid = p.productid\n" +
                        "WHERE c.userid = ? and p.isdeleted=false";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,userid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    total=rs.getDouble("total_amount");
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }




}
