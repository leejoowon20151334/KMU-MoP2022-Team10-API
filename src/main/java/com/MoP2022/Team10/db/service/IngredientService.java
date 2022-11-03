package com.MoP2022.Team10.db.service;

import com.MoP2022.Team10.db.Conn;
import com.MoP2022.Team10.db.DBExec;
import com.MoP2022.Team10.db.model.IngredientModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class IngredientService {

    public ArrayList<IngredientModel> getIngredientByName(String name){
        String q = "select a.* from Ingredients a where a.name like '%?%'";
        ArrayList<String> val = new ArrayList<>();
        val.add(name);
        ArrayList<IngredientModel> result = new ArrayList<>();

        try {
            ResultSet rs = DBExec.select(q,val);
            IngredientModel model;
            while (rs.next()) {
                model = new IngredientModel();
                model.id=rs.getInt("id");
                model.name=rs.getString("name");
                model.defaultExpiration=rs.getInt("expiration");
                model.unit=rs.getString("unit");
                result.add(model);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return result;
    }

    //같은날짜에 유통기한이 끝나는 식재료가 있는지 확인
    //없으면 0 있으면 UserIngredient 테이블 id값
    public int userIngredientExists(int userId, int ingredientId,LocalDate expire){

        String q = "select id from UserIngredients where " +
                "userId = ? and " +
                "ingredientId = ? and " +
                "expirationDate = ? " +
                "limit 1";
        int id = 0;
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        val.add(Integer.toString(ingredientId));
        val.add(expire.toString());
        try{
            ResultSet rs = DBExec.select(q,val);
            if(rs.next())
                id = rs.getInt("id");
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return id;
    }

    public void addIngredient(int userId, int ingredientId, int count, LocalDate expire){
        int id = userIngredientExists(userId,ingredientId,expire);
        if(id!=0){
            plusIngredient(id,count);
            return;
        }

        String q = "insert into UserIngredients " +
                "(`userId`,`ingredientId`,`count`,`expirationDate`) " +
                "values( ? , ? , ? , ? )";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        val.add(Integer.toString(ingredientId));
        val.add(Integer.toString(count));
        val.add(expire.toString());
        DBExec.update(q,val);
    }

    public void plusIngredient(int id,int count){
        String q = "update UserIngredients set `count`=(`count` + ?) where id = ?";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(count));
        val.add(Integer.toString(id));
        DBExec.update(q,val);
    }
}
