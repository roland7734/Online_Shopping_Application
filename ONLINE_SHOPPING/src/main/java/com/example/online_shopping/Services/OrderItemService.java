package com.example.online_shopping.Services;

import com.example.online_shopping.beans.OrderItemBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.online_shopping.constants.Constants.*;

public class OrderItemService {

    public static boolean createOrderItems(int orderid)
    {
        boolean ok=false;
        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "INSERT INTO public.orderitems (orderid, productid, quantity, subtotal)\n" +
                        "SELECT\n" +
                        "    o.orderid,\n" +
                        "    c.productid,\n" +
                        "    c.quantity,\n" +
                        "    p.price * c.quantity AS subtotal\n" +
                        "FROM public.cart c\n" +
                        "JOIN public.orders o ON c.userid = o.userid\n" +
                        "JOIN public.products p ON c.productid = p.productid\n" +
                        "WHERE o.orderid = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,orderid);
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected>0)
                {
                    ok=true;
                }
                ps.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }


    public static List<OrderItemBean> getItemsOfSpecificOrder(int orderid)
    {
        List<OrderItemBean> orderItemsList = new ArrayList<>();

        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "SELECT\n" +
                        "    p.imageurl AS url,\n" +
                        "    p.productname AS name,\n" +
                        "    oi.productid AS productid, \n"+
                        "    oi.quantity AS qty,\n" +
                        "    oi.subtotal AS subtotal\n" +
                        "FROM\n" +
                        "    public.orderitems oi\n" +
                        "JOIN\n" +
                        "    public.products p ON oi.productid = p.productid\n" +
                        "WHERE\n" +
                        "    oi.orderid = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,orderid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    OrderItemBean orderItem = new OrderItemBean(orderid,rs.getInt("productid"),
                            rs.getInt("qty"),rs.getDouble("subtotal"),rs.getString("URL"),
                            rs.getString("name"));
                    orderItemsList.add(orderItem);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItemsList;

    }
}
