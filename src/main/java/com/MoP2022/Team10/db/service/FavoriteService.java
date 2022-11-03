package com.MoP2022.Team10.db.service;

import com.MoP2022.Team10.db.DBExec;

import java.util.ArrayList;

public class FavoriteService {

    public boolean addFavorite(int userId,int recipeId){
        String q = "insert into UserFavorites (`userId`,`recipeId`) values( ? , ? ) " +
                "on duplicate key update userId = ? , recipeId = ? ";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        val.add(Integer.toString(recipeId));
        val.add(Integer.toString(userId));
        val.add(Integer.toString(recipeId));
        return DBExec.update(q,val);
    }

    public boolean deleteFavorite(int userId,int recipeId){
        String q = "delete from UserFavorites where userId = ? and recipeId = ? ";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        val.add(Integer.toString(recipeId));
        return DBExec.update(q,val);
    }
}
