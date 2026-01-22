package com.project.UKCookHouse.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.*;

@RestController
public class RecipeRestController {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @CrossOrigin(origins = "*")
    @GetMapping("/recipes")
    public List<Map<String, Object>> getRecipes() {
        List<Map<String, Object>> recipes = new ArrayList<>();
        String query = "SELECT recipe_name, recipe_name_kn, recipe_image, recipe_description, recipe_description_kn FROM recipes ORDER BY recipe_name";
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("name_en", rs.getString("recipe_name"));
                recipe.put("name_kn", rs.getString("recipe_name_kn"));
                recipe.put("description_en", rs.getString("recipe_description"));
                recipe.put("description_kn", rs.getString("recipe_description_kn"));
                recipe.put("image_url", rs.getString("recipe_image"));
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
