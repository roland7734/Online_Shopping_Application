package com.example.online_shopping.Services;

import com.example.online_shopping.beans.BrandBean;
import com.example.online_shopping.beans.CategoryBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.online_shopping.constants.Constants.*;

public class CategoryService {
    public static List<CategoryBean> getCategories() {
        List<CategoryBean> categoriesList = new ArrayList<>();

        try {

            Connection conn = DatabaseConnectionService.connect_to_db(DBNAME, USER, PASSWORD);

            if (conn != null) {
                String sql = "SELECT * FROM categories order by categoryid";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    CategoryBean category = new CategoryBean(rs.getInt("categoryid"),rs.getString("categoryname"));
                    categoriesList.add(category);
                }
                ps.close();
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoriesList;

    }
}
