package com.project.UKCookHouse.controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class RecipeTimeFilterController {

    private final String URL = "jdbc:postgresql://localhost:5432/UK_COOK_HOUSE";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    /**
     * API Endpoint: /recipesByTime
     * Example: /recipesByTime?time=30
     */
    @GetMapping("/recipesByTime")
    public List<Map<String, Object>> getRecipesByTime(@RequestParam int time) {
        List<Map<String, Object>> recipeList = new ArrayList<>();

        String query = "SELECT recipe_name, recipe_name_kn, recipe_description, recipe_description_kn, recipe_image, cooking_time " +
                "FROM recipes WHERE cooking_time <= ? ORDER BY cooking_time ASC";

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, time);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("recipe_name", rs.getString("recipe_name"));
                recipe.put("recipe_description", rs.getString("recipe_description"));
                recipe.put("recipe_image", rs.getString("recipe_image"));
                recipe.put("cooking_time", rs.getInt("cooking_time"));
                recipe.put("recipe_name_kn",rs.getString("recipe_name_kn"));
                recipe.put("recipe_description_kn",rs.getString("recipe_description_kn"));
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
