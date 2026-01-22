package com.project.UKCookHouse.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class RecipeDifficultyFilterController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @GetMapping("/recipesByDifficulty")
    public List<Map<String, Object>> getRecipesByDifficulty(@RequestParam String level) {
        List<Map<String, Object>> recipeList = new ArrayList<>();

        String query = "SELECT recipe_name, recipe_name_kn, recipe_description, recipe_description_kn, " +
                "recipe_image, cooking_time, difficulty_level " +
                "FROM recipes WHERE LOWER(difficulty_level) = LOWER(?) " +
                "ORDER BY cooking_time ASC";

        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, level);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("recipe_name", rs.getString("recipe_name"));
                recipe.put("recipe_description", rs.getString("recipe_description"));
                recipe.put("recipe_image", rs.getString("recipe_image"));
                recipe.put("cooking_time", rs.getInt("cooking_time"));
                recipe.put("difficulty_level", rs.getString("difficulty_level"));
                recipe.put("recipe_name_kn", rs.getString("recipe_name_kn"));
                recipe.put("recipe_description_kn", rs.getString("recipe_description_kn"));
                recipeList.add(recipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Database error: " + e.getMessage());
            recipeList.add(error);
        }

        return recipeList;
    }
}
