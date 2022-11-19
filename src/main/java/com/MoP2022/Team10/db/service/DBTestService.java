package com.MoP2022.Team10.db.service;

import com.MoP2022.Team10.db.Conn;
import com.MoP2022.Team10.db.DBExec;
import com.MoP2022.Team10.db.model.DBTestModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBTestService {

    public ArrayList<DBTestModel> fetchData() {
        String q = "select a,b from test limit 10";

        ArrayList<DBTestModel> result = new ArrayList<>();
        ArrayList<String> val = new ArrayList<>();
        DBTestModel model;
        ArrayList<HashMap<String,String>>  rs = DBExec.select(q,val);
        try {
            for(HashMap<String,String> item : rs) {
                model = new DBTestModel();
                model.setA(item.get("a"));
                model.setB(item.get("b"));
                result.add(model);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

}
