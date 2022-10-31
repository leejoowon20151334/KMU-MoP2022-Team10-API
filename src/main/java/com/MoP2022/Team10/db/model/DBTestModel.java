package com.MoP2022.Team10.db.model;

import java.util.HashMap;

public class DBTestModel {

    public int a;
    public String b;

    public void setA(String a){
        this.a = Integer.parseInt(a);
    }

    public void setB(String b){
        this.b = b;
    }

    public HashMap<String,String> toHashMap(){
        HashMap<String,String> testMap = new HashMap<>();
        testMap.put("a", Integer.toString(a));
        testMap.put("b",b);
        return testMap;
    }
}
