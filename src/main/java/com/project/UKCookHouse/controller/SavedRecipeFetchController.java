package com.project.UKCookHouse.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class SavedRecipeFetchController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @GetMapping("/savedRecipes")
    public List<Map<String, Object>> getSavedRecipes(@RequestParam int userId) {
        List<Map<String, Object>> savedRecipes = new ArrayList<>();

        String query = """
            SELECT r.recipe_id, r.recipe_name, r.recipe_name_kn, r.recipe_image, 
                   r.recipe_description, r.recipe_description_kn
            FROM recipes r
            INNER JOIN save_recipe s ON r.recipe_id = s.r_u_id
            WHERE s.u_id = ?
            ORDER BY r.recipe_name;
        """;

        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("recipe_id", rs.getInt("recipe_id"));
                recipe.put("name_en", rs.getString("recipe_name"));
                recipe.put("name_kn", rs.getString("recipe_name_kn"));
                recipe.put("description_en", rs.getString("recipe_description"));
                recipe.put("description_kn", rs.getString("recipe_description_kn"));
                recipe.put("image_url", rs.getString("recipe_image"));
                savedRecipes.add(recipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Database error: " + e.getMessage());
            savedRecipes.add(error);
        }

        return savedRecipes;
    }
}
