package com.MoP2022.Team10.process;

import com.MoP2022.Team10.db.model.DBTestModel;
import com.MoP2022.Team10.db.service.DBTestService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestProcess {

    public HashMap<String, String> test() {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("val1", "1");
        testMap.put("val2", "2");
        testMap.put("val3", "3");
        testMap.put("val4", "4");
        return testMap;
    }

    public List<HashMap<String, String>> dbTest() throws SQLException {
        DBTestService db = new DBTestService();
        List<DBTestModel> data = db.fetchData();

        List<HashMap<String, String>> newData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++)
            newData.add(data.get(i).toHashMap());

        return newData;
    }

}
