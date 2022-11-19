package com.MoP2022.Team10.db.service;

import com.MoP2022.Team10.db.Conn;
import com.MoP2022.Team10.db.DBExec;
import com.MoP2022.Team10.db.model.IngredientModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class IngredientService {

    public ArrayList<IngredientModel> getIngredientByName(String name){
        String q = "select a.* from Ingredients a where a.name like concat('%', ? ,'%')";
        ArrayList<String> val = new ArrayList<>();
        val.add(name);
        ArrayList<IngredientModel> result = new ArrayList<>();

        try {
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            IngredientModel model;
            for(HashMap<String,String> item : rs){
                model = new IngredientModel();
                model.id= Integer.parseInt(item.get("id"));
                model.name=item.get("name");
                model.defaultExpiration= Integer.parseInt(item.get("expiration"));
                model.unit=item.get("unit");
                model.img = item.get("img");
                result.add(model);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return result;
    }

    public ArrayList<IngredientModel> getRecipeIngredient(int recipeId){
        String q = "select a.count,b.* from RecipeIngredientMatch a inner join Ingredients b on a.ingredientId=b.id " +
                " where a.recipeId = ? order by b.id asc";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(recipeId));
        ArrayList<IngredientModel> result = new ArrayList<>();

        try {
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            IngredientModel model;
            for(HashMap<String,String> item : rs){
                model = new IngredientModel();
                model.id= Integer.parseInt(item.get("id"));
                model.name=item.get("name");
                model.defaultExpiration= Integer.parseInt(item.get("expiration"));
                model.unit=item.get("unit");
                model.count = Integer.parseInt(item.get("count"));
                model.img = item.get("img");
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
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            for(HashMap<String,String> item : rs) {
                id = Integer.parseInt(item.get("id"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return id;
    }

    public boolean addUserIngredient(int userId, int ingredientId, int count, LocalDate expire){
        int id = userIngredientExists(userId,ingredientId,expire);
        if(id!=0)
            return plusIngredient(id,count);

        String q = "insert into UserIngredients " +
                "(`userId`,`ingredientId`,`count`,`expirationDate`) " +
                "values( ? , ? , ? , ? )";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        val.add(Integer.toString(ingredientId));
        val.add(Integer.toString(count));
        val.add(expire.toString());
        return DBExec.update(q,val);
    }

    public boolean plusIngredient(int id,int count){
        String q = "update UserIngredients set `count`=(`count` + ?) where id = ?";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(count));
        val.add(Integer.toString(id));
        return DBExec.update(q,val);
    }

    public ArrayList<IngredientModel> getUserIngredients(int userId){
        String q = "select a.ingredientId,a.count,a.expirationDate,b.name,b.expiration,b.unit,b.img " +
                "from UserIngredients a left join Ingredients b on a.ingredientId=b.id " +
                "where a.userId = ? order by a.id asc";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        ArrayList<IngredientModel> result = new ArrayList<>();
        try {
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            for(HashMap<String,String> item : rs) {
                IngredientModel model = new IngredientModel();
                model.id= Integer.parseInt(item.get("ingredientId"));
                model.name=item.get("name");
                model.defaultExpiration= Integer.parseInt(item.get("expiration"));
                model.unit=item.get("unit");
                model.count= Integer.parseInt(item.get("count"));
                model.expirationDate=LocalDate.parse(item.get("expirationDate"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                model.img = item.get("img");
                result.add(model);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }
}
