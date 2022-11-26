package com.MoP2022.Team10.db.service;

import com.MoP2022.Team10.db.DBExec;
import com.MoP2022.Team10.db.model.IngredientModel;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class UserDataService {

    public boolean signUp(String userName){
        String q = "insert into Users (`name`) values( ? )";
        ArrayList<String> val = new ArrayList<>();
        val.add(userName);
        return DBExec.update(q,val);
    }

    public boolean changeName(int userId, String name){
        String q = "UPDATE users SET name = ? WHERE (`id` = ?)";
        ArrayList<String> val = new ArrayList<>();
        val.add(name);
        val.add(Integer.toString(userId));
        return DBExec.update(q,val);
    }

    public int getUserIdByName(String userName){
        int result = 0;
        String q = "select id from Users where `name` = ? limit 1";
        ArrayList<String> val = new ArrayList<>();
        val.add(userName);
        try{
            ArrayList<HashMap<String,String>>  rs = DBExec.select(q,val);
            for(HashMap<String,String> item : rs) {
                result = Integer.parseInt(item.get("id"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return result;
    }

    public boolean addUserSearchLog(int userId, int recipeId){
        String q = "insert into userRecipeLog (`userId`,`recipeId`,`date`) " +
                "values( ? , ? , now() )";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        val.add(Integer.toString(recipeId));
        return DBExec.update(q,val);
    }

    public boolean addUserEvaluation(int userId, int recipeId,int evaluation){
        String q = "insert into userevaluation (userId,recipeId,evaluation) values( ? , ? , ? )\n" +
                "on duplicate key update evaluation = ? ;";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        val.add(Integer.toString(recipeId));
        val.add(Integer.toString(evaluation));
        val.add(Integer.toString(evaluation));
        return DBExec.update(q,val);
    }

    public double getUserEvaluation(int userId,int recipeId){
        double result = 0;
        String q = "select evaluation from userevaluation where userId = ? and recipeId = ? ";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        val.add(Integer.toString(recipeId));
        try{
            ArrayList<HashMap<String,String>>  rs = DBExec.select(q,val);
            for(HashMap<String,String> item : rs) {
                result = Double.parseDouble(item.get("evaluation"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return result;
    }

    public double getEvaluation(int recipeId){
        double result = 0;
        String q = "select avg(evaluation) as evaluation from userevaluation where recipeId = ? ";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(recipeId));
        try{
            ArrayList<HashMap<String,String>>  rs = DBExec.select(q,val);
            for(HashMap<String,String> item : rs) {
                result = Double.parseDouble(item.get("evaluation"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return result;
    }
}
