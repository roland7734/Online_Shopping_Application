package com.example.online_shopping.Services;

import com.example.online_shopping.beans.ManufacturerBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.online_shopping.constants.Constants.*;

public class ManufacturerService {

    public static List<ManufacturerBean> getManufacturers() {
        List<ManufacturerBean> manufacturersList = new ArrayList<>();

        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "SELECT * FROM manufacturers order by manufacturerid";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ManufacturerBean manufacturer = new ManufacturerBean(rs.getInt("manufacturerid"),
                            rs.getString("manufacturername"), rs.getString("address"), rs.getString("website"),
                            rs.getString("contactemail"),rs.getString("contactphone"));
                    manufacturersList.add(manufacturer);
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturersList;
    }
}
