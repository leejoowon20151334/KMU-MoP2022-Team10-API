package com.MoP2022.Team10.db.service;

import com.MoP2022.Team10.db.DBExec;
import com.MoP2022.Team10.db.model.IngredientModel;
import com.MoP2022.Team10.db.model.RecipeModel;
import com.MoP2022.Team10.db.model.RecipeTypeModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RecipeService {

    public ArrayList<RecipeModel> getRecipeByName(String name){
        String q = "select a.* from Recipes a where a.name like concat('%', ? ,'%')";
        ArrayList<String> val = new ArrayList<>();
        val.add(name);
        ArrayList<RecipeModel> result = new ArrayList<>();

        try {
            ResultSet rs = DBExec.select(q,val);
            result = parseDBRecipe(rs);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    public ArrayList<RecipeModel> getRecipe(int recipeId){
        String q = "select a.* from Recipes a where a.id = ? ";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(recipeId));
        ArrayList<RecipeModel> result = new ArrayList<>();

        try {
            ResultSet rs = DBExec.select(q,val);
            result = parseDBRecipe(rs);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    public ArrayList<RecipeTypeModel> getRecipeType(int recipeid){
        String q = "select b.* from RecipeCategoryMatch a inner join RecipeCategory b on a.categoryId=b.id " +
                "where a.recipeId = ? ";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(recipeid));
        ArrayList<RecipeTypeModel> result = new ArrayList<>();

        try {
            ResultSet rs = DBExec.select(q,val);
            RecipeTypeModel model;
            while (rs.next()) {
                model = new RecipeTypeModel();
                model.typeId=rs.getInt("id");
                model.typeName = rs.getString("name");
                result.add(model);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    public ArrayList<String> getProcedure(int recipeid){
        String q = "select * from Procedures where recipeId = ? order by `order` asc";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(recipeid));
        ArrayList<String> result = new ArrayList<>();

        try {
            ResultSet rs = DBExec.select(q,val);
            while (rs.next()) {
                result.add(rs.getString("description"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    private ArrayList<RecipeModel> parseDBRecipe(ResultSet rs) throws SQLException {
        IngredientService ingredientService = new IngredientService();
        RecipeModel model;
        ArrayList<RecipeModel> result = new ArrayList<>();
        while (rs.next()) {
            model = new RecipeModel();
            model.id=rs.getInt("id");
            model.name = rs.getString("name");
            model.time=rs.getInt("time");
            model.difficulty = rs.getInt("difficulty");
            model.description = rs.getString("description");
            model.img = rs.getString("img");
            model.type = getRecipeType(model.id);
            model.ingredients =ingredientService.getRecipeIngredient(model.id);
            model.procedure =getProcedure(model.id);
            result.add(model);
        }
        return result;
    }
}
