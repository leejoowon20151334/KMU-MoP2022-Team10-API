package com.MoP2022.Team10.db.service;

import com.MoP2022.Team10.db.DBExec;

import java.util.ArrayList;

public class PushStatusService {

    public boolean startPush(int userId,String pushId){
        String q = "update Users set `pushStatus`=1, `pushId`= ? where id = ? ";
        ArrayList<String> val = new ArrayList<>();
        val.add(pushId);
        val.add(Integer.toString(userId));
        return DBExec.update(q,val);
    }

    public boolean stopPush(int userId){
        String q = "update Users set `pushStatus`=0 where id = ? ";
        ArrayList<String> val = new ArrayList<>();
        val.add(Integer.toString(userId));
        return DBExec.update(q,val);
    }
}
