package com.example.online_shopping.Services;

import com.example.online_shopping.beans.PaymentBean;
import com.example.online_shopping.beans.VoucherBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.online_shopping.constants.Constants.*;

public class PaymentService {

    public static boolean addPayment(int orderid) {
        boolean flag=false;
        int paymentid=0;
        do {
            paymentid ++;

        }while(isIDFound(paymentid)==true);
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);
            if (conn != null) {
                String sql="(select current_timestamp as paymentdate, o.totalamount as amount, " +
                        " 'TR' || replace(replace(replace(replace(replace(replace(current_timestamp::text, '-', ''), ' ', ''), ':', ''), '.', ''), '+', ''), ' ' ,'') as transactionid " +
                        " from public.orders o " +
                        " where o.orderid = ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, orderid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    PaymentBean payment = new PaymentBean(paymentid,orderid,rs.getTimestamp("paymentdate"),
                            rs.getDouble("amount"),"Credit Card",rs.getString("transactionid"));
                    flag = insertPayment(payment);
                }
                ps.close();
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;

    }

    private static boolean insertPayment(PaymentBean payment)
    {
        boolean flag=false;
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);
            if (conn != null) {
                String sql="insert into public.payments (paymentid, orderid, paymentdate, amount, paymentmethod, transactionid) values (?,?,?,?,?,?)";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, payment.getPaymentId());
                ps.setInt(2,payment.getOrderId());
                ps.setTimestamp(3,payment.getPaymentDate());
                ps.setDouble(4,payment.getAmount());
                ps.setString(5, payment.getPaymentMethod());
                ps.setString(6, payment.getTransactionId());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                        flag=true;
                }
                ps.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

        private static boolean isIDFound(int id) {
            boolean ok=false;
            try {
                Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
                if (conn != null) {

                    PreparedStatement ps = conn.prepareStatement("select * from payments where paymentid=?");
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


}

