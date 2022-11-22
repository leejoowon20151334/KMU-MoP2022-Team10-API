package com.MoP2022.Team10.db.model;

import java.time.LocalDate;

public class IngredientModel {

    //기본정보
    public int id;
    public String name;
    public int defaultExpiration;
    public String unit;
    public String img;

    //사용자별 정보
    public double count;
    public LocalDate expirationDate;

}
