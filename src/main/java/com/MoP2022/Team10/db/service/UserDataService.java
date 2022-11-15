package com.MoP2022.Team10.db.service;

import com.MoP2022.Team10.db.DBExec;
import com.MoP2022.Team10.db.model.IngredientModel;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDataService {

    public boolean signUp(String userName){
        String q = "insert into Users (`name`) values( ? )";
        ArrayList<String> val = new ArrayList<>();
        val.add(userName);
        return DBExec.update(q,val);
    }

    public int getUserIdByName(String userName){
        int result = 0;
        String q = "select id from Users where `name` = ? limit 1";
        System.out.println(userName);
        ArrayList<String> val = new ArrayList<>();
        val.add(userName);
        try{
            ResultSet rs = DBExec.select(q,val);
            if(rs.next())
                result = rs.getInt("id");
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return result;
    }
}
