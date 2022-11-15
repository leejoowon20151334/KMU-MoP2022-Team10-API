package com.MoP2022.Team10.mapping;

import com.MoP2022.Team10.db.service.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping
public class Map extends OncePerRequestFilter {

    public final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        if(userDataService.signUp(name))
            res.data = "success";
        else
            res.data = "fail";
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

    @GetMapping("/addUserIngredient")
    public ResponseEntity<ResDefault> addUserIngredient(
            @RequestParam(value = "userId")int userId,
            @RequestParam(value = "ingredientId")int ingredientId,
            @RequestParam(value = "count")int count,
            @RequestParam(value = "expire")String expire
    ) {
        ResDefault res = new ResDefault();
        IngredientService ingredientService = new IngredientService();
        System.out.println(userId + " " + ingredientId+" "+count+" "+expire);
        if(ingredientService.addUserIngredient(userId,ingredientId,count, LocalDate.parse(expire,dateTimeFormatter)))
            res.data = "success";
        else
            res.data = "fail";
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/addFavorite")
    public ResponseEntity<ResDefault> addFavorite(
            @RequestParam(value = "userId")int userId,
            @RequestParam(value = "recipeId")int recipeId
    ) {
        ResDefault res = new ResDefault();
        FavoriteService favoriteService = new FavoriteService();
        if(favoriteService.addFavorite(userId,recipeId))
            res.data = "success";
        else
            res.data = "fail";
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/deleteFavorite")
    public ResponseEntity<ResDefault> deleteFavorite(
            @RequestParam(value = "userId")int userId,
            @RequestParam(value = "recipeId")int recipeId
    ) {
        ResDefault res = new ResDefault();
        FavoriteService favoriteService = new FavoriteService();
        if(favoriteService.deleteFavorite(userId,recipeId))
            res.data = "success";
        else
            res.data = "fail";
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/startPush")
    public ResponseEntity<ResDefault> startPush(
            @RequestParam(value = "userId")int userId,
            @RequestParam(value = "pushId")String pushId
    ) {
        ResDefault res = new ResDefault();
        PushStatusService pushStatusService = new PushStatusService();
        if(pushStatusService.startPush(userId,pushId))
            res.data = "success";
        else
            res.data = "fail";
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/stopPush")
    public ResponseEntity<ResDefault> stopPush(@RequestParam(value = "userId")int userId) {
        ResDefault res = new ResDefault();
        PushStatusService pushStatusService = new PushStatusService();
        if(pushStatusService.stopPush(userId))
            res.data = "success";
        else
            res.data = "fail";
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/getRecipe")
    public ResponseEntity<ResDefault> getRecipe(@RequestParam(value = "name")String name) {
        ResDefault res = new ResDefault();
        RecipeService recipeService = new RecipeService();
        res.data = recipeService.getRecipeByName(name);
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/getRecipeDetail")
    public ResponseEntity<ResDefault> getRecipe(@RequestParam(value = "recipeId")int recipeId) {
        ResDefault res = new ResDefault();
        RecipeService recipeService = new RecipeService();
        res.data = recipeService.getRecipe(recipeId);
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }
}
