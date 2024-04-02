package com.example.online_shopping.Services;

import com.example.online_shopping.beans.VoucherBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.online_shopping.constants.Constants.*;

public class VoucherService {


    public static boolean isVoucherFoundAndActive(String voucherCode) {
        boolean ok = false;
        try {
            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);
            if (conn != null) {

                PreparedStatement ps = conn.prepareStatement("select * from vouchers where vouchercode=? AND CURRENT_DATE BETWEEN startdate AND enddate AND isactive = true");
                ps.setString(1, voucherCode);
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

    public static boolean setVoucherInactive(String voucher) {
        boolean ok = false;
        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "update public.vouchers set isactive = false where vouchercode = ? and isactive = true";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, voucher);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    ok = true;
                }
                ps.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static boolean setVouchersInactiveWhenExpired() {
        boolean ok = false;
        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "update public.vouchers set isactive = false where CURRENT_DATE > enddate";
                PreparedStatement ps = conn.prepareStatement(sql);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    ok = true;
                }
                ps.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static List<VoucherBean> fetchDataFromDatabase() {

        List<VoucherBean> voucherList = new ArrayList<>();

        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "SELECT * FROM vouchers order by isactive desc, startdate, enddate";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    VoucherBean voucher = new VoucherBean(
                            rs.getString("vouchercode"),
                            rs.getInt("discountpercentage"),
                            rs.getDate("startdate"),
                            rs.getDate("enddate"),
                            rs.getBoolean("isActive"));
                    voucherList.add(voucher);
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voucherList;

    }

    public static boolean createVoucher(VoucherBean voucher) {
        boolean ok = false;
        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "INSERT INTO public.vouchers (vouchercode, discountpercentage, startdate, enddate, isactive)\n" +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, voucher.getVoucherCode());
                ps.setInt(2, voucher.getDiscountPercentage());
                ps.setDate(3, voucher.getStartDate());
                ps.setDate(4, voucher.getEndDate());
                ps.setBoolean(5, voucher.isActive());
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
