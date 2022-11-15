package com.MoP2022.Team10.db.model;

import java.util.ArrayList;

public class RecipeModel {
    public int id;
    public String name;
    public int time;
    public int difficulty;
    public String description;
    public String img;
    public ArrayList<RecipeTypeModel> type;
    public ArrayList<IngredientModel> ingredients;
    public ArrayList<String> procedure;
}
