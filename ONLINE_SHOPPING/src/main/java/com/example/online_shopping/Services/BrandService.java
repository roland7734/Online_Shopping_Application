package com.example.online_shopping.Services;

import com.example.online_shopping.beans.BrandBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.online_shopping.constants.Constants.*;

public class BrandService {
    public static List<BrandBean> getBrands() {
        List<BrandBean> brandsList = new ArrayList<>();

        try{

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME,USER,PASSWORD);

            if(conn!=null)
            {
                String sql = "SELECT * FROM brands order by brandid";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    BrandBean brand = new BrandBean(rs.getInt("brandid"),rs.getString("brandname"));
                    brandsList.add(brand);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandsList;

    }
}
