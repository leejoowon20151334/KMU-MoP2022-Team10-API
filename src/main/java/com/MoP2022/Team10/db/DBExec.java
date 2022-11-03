package com.MoP2022.Team10.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBExec {

    public static void update(String q, ArrayList<String> val){
        try{
            Connection con = Conn.getConnection();
            PreparedStatement pst = con.prepareStatement(q);
            for(int i=0;i<val.size();i++){
                pst.setString(i+1,val.get(i));
            }
            pst.executeUpdate();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public static ResultSet select(String q,ArrayList<String> val){
        ResultSet rs = null;
        try{
            Connection con = Conn.getConnection();
            PreparedStatement pst = con.prepareStatement(q);
            for(int i=0;i<val.size();i++){
                pst.setString(i+1,val.get(i));
            }
            rs = pst.executeQuery();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return rs;
    }
}
