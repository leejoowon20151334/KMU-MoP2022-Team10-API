package com.MoP2022.Team10.mapping;

import com.MoP2022.Team10.db.service.DBTestService;
import com.MoP2022.Team10.db.service.IngredientService;
import com.MoP2022.Team10.db.service.UserDataService;
import com.MoP2022.Team10.mapping.res.ResDefault;
import com.MoP2022.Team10.process.TestProcess;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping
public class Map extends OncePerRequestFilter {

    // set HTTP Headers
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Content-Type", "application/json; charset=utf-8;");
        filterChain.doFilter(request, response);
    }

    //sample
    @GetMapping("/")
    public ResponseEntity<ResDefault> index() {
        ResDefault res = new ResDefault();
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/dbTest")
    public ResponseEntity<ResDefault> dbTest() throws SQLException {
        ResDefault res = new ResDefault();
        DBTestService db = new DBTestService();
        res.data = db.fetchData();
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/processTest")
    public ResponseEntity<ResDefault> processTest(){
        ResDefault res = new ResDefault();
        TestProcess process = new TestProcess();
        res.data = process.test();
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/processDBTest")
    public ResponseEntity<ResDefault> processDBTest() {
        ResDefault res = new ResDefault();
        TestProcess process = new TestProcess();
        res.data = process.dbTest();
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/signUp")
    public ResponseEntity<ResDefault> signUp(@RequestParam(value = "name")String name) {
        ResDefault res = new ResDefault();
        UserDataService userDataService = new UserDataService();
        userDataService.signUp(name);
        res.data = "success";
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }


    @GetMapping("/getIngredient")
    public ResponseEntity<ResDefault> getIngredient(@RequestParam(value = "name")String name) {
        ResDefault res = new ResDefault();
        IngredientService ingredientService = new IngredientService();
        res.data = ingredientService.getIngredientByName(name.replace(" ",""));
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/userIngredient")
    public ResponseEntity<ResDefault> userIngredient(@RequestParam(value = "userId")int userId) {
        ResDefault res = new ResDefault();
        IngredientService ingredientService = new IngredientService();
        res.data = ingredientService.getUserIngredients(userId);
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }
}
