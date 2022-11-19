package com.MoP2022.Team10.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

public class DBExec {

    public static boolean update(String q, ArrayList<String> val){
        boolean result = false;
        Connection con = null;
        PreparedStatement pst = null;

        try{
            con = Conn.getConnection();
            pst = con.prepareStatement(q);
            for(int i=0;i<val.size();i++){
                pst.setString(i+1,val.get(i));
            }
            pst.executeUpdate();
            result = true;
        }catch (Exception e) {
            System.out.println(e.toString());
        }

        try {
            if(pst!=null)
                pst.close();
            if(con!=null)
                con.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    public static ArrayList<HashMap<String,String>> select(String q, ArrayList<String> val){
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement pst = null;
        ArrayList<HashMap<String,String>> map = new ArrayList<>();
        try{
            con = Conn.getConnection();
            pst = con.prepareStatement(q);
            for(int i=0;i<val.size();i++){
                pst.setString(i+1,val.get(i));
            }
            rs = pst.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            while (rs.next()){
                HashMap<String,String> row = new HashMap<>();
                for(int i=1; i<=columns; ++i){
                    row.put(md.getColumnName(i),rs.getString(i));
                }
                map.add(row);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }

        try {
            if(pst!=null)
                pst.close();
            if(con!=null)
                con.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return map;
    }
}
