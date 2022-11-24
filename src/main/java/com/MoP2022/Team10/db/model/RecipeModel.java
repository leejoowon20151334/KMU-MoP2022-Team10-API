package com.MoP2022.Team10.db.model;

import java.util.ArrayList;

public class RecipeModel {
    public int id;
    public String name;
    public int time;
    public int difficulty;
    public String description;
    public double evaluation;
    public String img;
    public ArrayList<RecipeTypeModel> type = new ArrayList<>();
    public ArrayList<IngredientModel> ingredients = new ArrayList<>();
    public ArrayList<String> procedure = new ArrayList<>();
}
