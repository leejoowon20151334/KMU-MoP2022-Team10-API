package com.MoP2022.Team10.db.service;

import com.MoP2022.Team10.db.DBExec;
import com.MoP2022.Team10.db.model.RecipeModel;
import com.MoP2022.Team10.db.model.RecipeTypeModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RecipeService {

    public ArrayList<RecipeModel> getRecipeByName(String name){
        String q = "select a.*,(select avg(evaluation) as evaluation from userevaluation where recipeId = a.id limit 1) as evaluation from Recipes a where a.name like concat('%', ? ,'%')";
        ArrayList<String> val = new ArrayList<>();
        val.add(name);
        ArrayList<RecipeModel> result = new ArrayList<>();

        try {
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            result = parseDBRecipe(rs,false);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    public ArrayList<RecipeModel> getRecipe(int recipeId){
        String q = "select a.*,(select avg(evaluation) as evaluation from userevaluation where recipeId = a.id limit 1) as evaluation from Recipes a where a.id = ? ";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(recipeId));
        ArrayList<RecipeModel> result = new ArrayList<>();

        try {
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            result = parseDBRecipe(rs);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    public ArrayList<RecipeModel> getUserRecipe(int userId){
        String q = "select a.*,(select avg(evaluation) as evaluation from userevaluation where recipeId = a.id limit 1) as evaluation from Recipes a inner join UserFavorites b on a.id=b.recipeId where b.userId = ? ";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        ArrayList<RecipeModel> result = new ArrayList<>();

        try {
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            result = parseDBRecipe(rs,false);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    /*public boolean useRecipe(int userId, int recipeId){
        String q = "select * from userIngredients ";
        ArrayList<RecipeModel> recipeList = getRecipe(recipeId);

    }*/

    public ArrayList<RecipeModel> getUserRecipeLog(int userId){
        String q = "select distinct b.*,(select avg(evaluation) as evaluation from userevaluation where recipeId = b.id limit 1) as evaluation from userrecipelog a inner join recipes b on a.recipeId = b.id\n" +
                "where a.userId= ? order by `date` desc limit 10";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        ArrayList<RecipeModel> result = new ArrayList<>();
        try {
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            result = parseDBRecipe(rs,false);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }
    public ArrayList<RecipeModel>getMyEvaluation(int userId){
        String q = "select * from UserFavorites f right join userevaluation e on e.userId = f.userId left join Recipes r on e.recipeId = r.id where f.userId = ?";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        ArrayList<RecipeModel> result = new ArrayList<>();
        try {
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            result = parseDBRecipe(rs,false);
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
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            RecipeTypeModel model;
            for(HashMap<String,String> item : rs){
                model = new RecipeTypeModel();
                model.typeId= Integer.parseInt(item.get("id"));
                model.typeName = item.get("name");
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
            ArrayList<HashMap<String,String>> rs = DBExec.select(q,val);
            for(HashMap<String,String> item : rs){
                result.add(item.get("description"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    private ArrayList<RecipeModel> parseDBRecipe(ArrayList<HashMap<String,String>> rs) throws SQLException {
        return parseDBRecipe(rs,true);
    }

    private ArrayList<RecipeModel> parseDBRecipe(ArrayList<HashMap<String,String>> rs,boolean getDetail) throws SQLException {
        IngredientService ingredientService = new IngredientService();
        RecipeModel model;
        ArrayList<RecipeModel> result = new ArrayList<>();
        for(HashMap<String,String> item : rs){
            model = new RecipeModel();
            model.id= Integer.parseInt(item.get("id"));
            model.name = item.get("name");
            model.time= Integer.parseInt(item.get("time"));
            model.difficulty = Integer.parseInt(item.get("difficulty"));
            model.description = item.get("description");
            model.img = item.get("img");
            if(item.containsKey("evaluation"))
                model.evaluation = ((double)Math.round(Double.parseDouble(item.get("evaluation")) * 10)) / 10;
            if(getDetail) {
                model.type = getRecipeType(model.id);
                model.ingredients = ingredientService.getRecipeIngredient(model.id);
                model.procedure = getProcedure(model.id);
            }

            result.add(model);
        }
        return result;
    }
}
