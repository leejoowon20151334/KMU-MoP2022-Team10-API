package com.MoP2022.Team10.mapping;

import com.MoP2022.Team10.db.service.*;
import com.MoP2022.Team10.mapping.res.ResDefault;
import com.MoP2022.Team10.process.TestProcess;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
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
            res.data = userDataService.getUserIdByName(name);
        else
            res.data = 0;
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/getUserId")
    public ResponseEntity<ResDefault> getUserId(@RequestParam(value = "name")String name) {
        ResDefault res = new ResDefault();
        UserDataService userDataService = new UserDataService();
        res.data = userDataService.getUserIdByName(name);
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

    @GetMapping("/getMyEvaluation")
    public ResponseEntity<ResDefault> getMyEvaluation(@RequestParam(value = "userId")int userId) {
        ResDefault res = new ResDefault();
        RecipeService recipeService = new RecipeService();
        res.data = recipeService.getMyEvaluation(userId);
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

    @GetMapping("/getFavorite")
    public ResponseEntity<ResDefault> getFavorite(@RequestParam(value = "userId")int userId) {
        ResDefault res = new ResDefault();
        RecipeService recipeService = new RecipeService();
        res.data = recipeService.getUserRecipe(userId);
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/isFavorite")
    public ResponseEntity<ResDefault> isFavorite(
            @RequestParam(value = "userId")int userId,
            @RequestParam(value = "recipeId")int recipeId
    ) {
        ResDefault res = new ResDefault();
        FavoriteService favoriteService = new FavoriteService();
        if(favoriteService.isFavorite(userId,recipeId))
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

    @GetMapping("/addUserSearchLog")
    public ResponseEntity<ResDefault> addUserSearchLog(
            @RequestParam(value = "userId")int userId,
            @RequestParam(value = "recipeId")int recipeId
    ) {
        ResDefault res = new ResDefault();
        UserDataService userDataService = new UserDataService();
        if(userDataService.addUserSearchLog(userId,recipeId))
            res.data = "success";
        else
            res.data = "fail";
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/userSearchLog")
    public ResponseEntity<ResDefault> userSearchLog(@RequestParam(value = "userId")int userId) {
        ResDefault res = new ResDefault();
        RecipeService recipeService = new RecipeService();
        res.data = recipeService.getUserRecipeLog(userId);
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/addUserEvaluation")
    public ResponseEntity<ResDefault> addUserEvaluation(
            @RequestParam(value = "userId")int userId,
            @RequestParam(value = "recipeId")int recipeId,
            @RequestParam(value = "evaluation")int evaluation
    ) {
        ResDefault res = new ResDefault();
        UserDataService userDataService = new UserDataService();
        if(userDataService.addUserEvaluation(userId,recipeId,evaluation))
            res.data = "success";
        else
            res.data = "fail";
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/getUserEvaluation")
    public ResponseEntity<ResDefault> getUserEvaluation(
            @RequestParam(value = "userId")int userId,
            @RequestParam(value = "recipeId")int recipeId
    ) {
        ResDefault res = new ResDefault();
        UserDataService userDataService = new UserDataService();
        res.data=userDataService.getUserEvaluation(userId,recipeId);
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/getEvaluation")
    public ResponseEntity<ResDefault> getEvaluation(
            @RequestParam(value = "recipeId")int recipeId
    ) {
        ResDefault res = new ResDefault();
        UserDataService userDataService = new UserDataService();
        res.data=userDataService.getEvaluation(recipeId);
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
    public ResponseEntity<ResDefault> getRecipeDetail(@RequestParam(value = "recipeId")int recipeId) {
        ResDefault res = new ResDefault();
        RecipeService recipeService = new RecipeService();
        res.data = recipeService.getRecipe(recipeId);
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @GetMapping("/useRecipe")
    public ResponseEntity<ResDefault> useRecipe(
            @RequestParam(value = "userId")int userId,
            @RequestParam(value = "recipeId")int recipeId
    ) {
        ResDefault res = new ResDefault();
        RecipeService recipeService = new RecipeService();
        if(recipeService.useRecipe(userId,recipeId))
            res.data = "success";
        else
            res.data = "fail";
        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }

    @PostMapping(value = "/imageRecognition",produces="application/json; charset=utf-8")
    public ResponseEntity<ResDefault> imageRecognition(@RequestBody String imgStr) {
        ResDefault res = new ResDefault();

        try {
            byte[] img = javax.xml.bind.DatatypeConverter.parseBase64Binary(imgStr);

            //이미지 내용물 확인
            //BufferedImage final_buffered_image = ImageIO.read(new ByteArrayInputStream(img));
            //ImageIO.write(final_buffered_image , "png", new File("image.png") );
        }catch (Exception e){
            System.out.println(e.toString());
        }

        // 이미지 -> 재료목록 변환 소스 작성




        return new ResponseEntity<ResDefault>(res, res.headers, res.statusCode);
    }
}
