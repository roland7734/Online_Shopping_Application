package com.example.online_shopping.Services;

import com.example.online_shopping.beans.OrderBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.online_shopping.constants.Constants.*;

public class OrderService {

    public static int createNewOrderId()
    {
        int orderid;
        do {
            orderid = new Random().nextInt(1000000) + 1000000;

        }while(isIDFound(orderid));
        return orderid;
    }
    public static boolean createOrder(int orderid,int userid,String address)
    {
        boolean ok=false;
        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "insert into public.orders (orderid, userid, orderdate, totalamount, status, address, totalamountbeforediscount) \n" +
                        "select ? as orderid, ? as userid, current_timestamp as orderdate, \n" +
                        "       sum(p.price * c.quantity) as totalamount, 'Pending' as status, ? as address, sum(p.price * c.quantity) as totalamountbeforediscount \n" +
                        "from public.cart c \n" +
                        "join public.products p on c.productid = p.productid \n" +
                        "where c.userid = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,orderid);
                ps.setInt(2,userid);
                ps.setString(3,address);
                ps.setInt(4,userid);

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

    private static boolean isIDFound(int orderid) {
            boolean ok=false;
            try {
                Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
                if (conn != null) {

                    PreparedStatement ps = conn.prepareStatement("select * from orders where orderid=?");
                    ps.setInt(1, orderid);
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



    public static boolean addVoucherToOrder(int orderid, String voucher)
    {
        boolean ok=false;
        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "update public.orders set vouchername = ? , totalamount = totalamount * (1 - cast(v.discountpercentage as numeric) / 100.0) " +
                        "from public.vouchers v where public.orders.orderid = ? and v.vouchercode = ? and " +
                        "v.isactive = true and current_date between v.startdate and v.enddate";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,voucher);
                ps.setInt(2,orderid);
                ps.setString(3,voucher);
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

    public static String getNextStatus(String currentStatus)
    {
        return switch (currentStatus) {
            case "Pending" -> "Order Confirmed";
            case "Order Confirmed" -> "Shipped";
            case "Shipped" -> "Out for Delivery";
            case "Out for Delivery" -> "Delivered";
            default -> "No Action";
        };
    }
    public static String updateOrderStatus(int orderid) {
        String status="Couldn't update the Order Status";

        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String currentStatus="Pending";
                String sql1="SELECT status FROM public.orders WHERE orderid = ?";
                PreparedStatement ps1 = conn.prepareStatement(sql1);
                ps1.setInt(1,orderid);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next())
                {
                    currentStatus=rs1.getString(1);
                }
                ps1.close();
                rs1.close();

                if(currentStatus.equals("Delivered"))
                    status = "The order was already delivered!";
                else {
                    String sql2 = "UPDATE public.orders SET status = ? WHERE orderid = ?";
                    PreparedStatement ps2 = conn.prepareStatement(sql2);
                    ps2.setString(1, getNextStatus(currentStatus));
                    ps2.setInt(2, orderid);
                    int rowsAffected = ps2.executeUpdate();
                    if (rowsAffected > 0) {
                        status = "Order Status updated successfully!";
                    }
                    ps2.close();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;

    }

    public static List<OrderBean> getOrders()
    {
        List<OrderBean> ordersList = new ArrayList<>();

        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "SELECT *,\n" +
                        "       COALESCE(paymentmethod, 'Cash On Delivery') AS adjusted_paymentmethod\n" +
                        "FROM orders\n" +
                        "LEFT JOIN vouchers ON orders.vouchername = vouchers.vouchercode\n" +
                        "LEFT JOIN payments ON orders.orderid = payments.orderid\n" +
                        "ORDER BY orderdate DESC";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    OrderBean order = new OrderBean(rs.getInt("orderid"),rs.getInt("userid"),rs.getString("address"),
                            rs.getString("vouchername"),rs.getTimestamp("orderdate"),rs.getDouble("totalamount"),
                            rs.getString("status"),rs.getDouble("totalamountbeforediscount"),rs.getInt("discountpercentage"),
                            rs.getString("adjusted_paymentmethod"));
                    ordersList.add(order);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;

    }


    public static List<OrderBean> getOrdersOfSpecificUser(int userid)
    {
        List<OrderBean> ordersList = new ArrayList<>();

        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "SELECT *,\n" +
                        "       COALESCE(paymentmethod, 'Cash On Delivery') AS adjusted_paymentmethod\n" +
                        "FROM orders\n" +
                        "LEFT JOIN vouchers ON orders.vouchername = vouchers.vouchercode\n" +
                        "LEFT JOIN payments ON orders.orderid = payments.orderid\n" +
                        "WHERE userid = ?\n" +
                        "ORDER BY orderdate DESC";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,userid);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                {
                    do
                    {
                        OrderBean order = new OrderBean(rs.getInt("orderid"),rs.getInt("userid"),rs.getString("address"),
                                rs.getString("vouchername"),rs.getTimestamp("orderdate"),rs.getDouble("totalamount"),
                                rs.getString("status"),rs.getDouble("totalamountbeforediscount"),rs.getInt("discountpercentage"),
                                rs.getString("adjusted_paymentmethod"));
                        ordersList.add(order);
                    }while (rs.next());
                }
                else
                {
                    ordersList=null;
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;

    }

}

