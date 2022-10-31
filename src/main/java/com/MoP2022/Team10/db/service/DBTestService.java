package com.MoP2022.Team10.db.service;

import com.MoP2022.Team10.db.Conn;
import com.MoP2022.Team10.db.model.DBTestModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBTestService {

    public List<DBTestModel> fetchData() throws SQLException {
        String q = "select a,b from test limit 10";

        List<DBTestModel> result = null;
        try (Connection con = Conn.getConnection();
             PreparedStatement pst = con.prepareStatement(q);
             ResultSet rs = pst.executeQuery();) {
            result = new ArrayList<>();
            DBTestModel model;
            while (rs.next()) {
                model = new DBTestModel();
                model.setA(rs.getString("a"));
                model.setB(rs.getString("b"));
                result.add(model);
            }
        }
        return result;
    }

}
