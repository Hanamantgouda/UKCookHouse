package com.project.UKCookHouse.controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@RestController
public class RecipeMatchController {

    private final String url = "jdbc:postgresql://localhost:5432/UK_COOK_HOUSE";
    private final String username = "postgres";
    private final String password = "postgres";

    @CrossOrigin(origins = "*")
    @PostMapping("/recipes/search")
    public Map<String, List<Map<String, Object>>> searchRecipes(@RequestBody Map<String, Object> body) {
        List<String> ingredients = (List<String>) body.get("ingredients");
        int ingredientCount = ingredients.size();
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Map<String, Object>> exactMatches = new ArrayList<>();
        List<Map<String, Object>> partialMatches = new ArrayList<>();

        if (ingredientCount == 0) return result;

        // Build CSV for SQL IN clause
        StringBuilder inClause = new StringBuilder();
        for (int i = 0; i < ingredientCount; i++) {
            inClause.append("'").append(ingredients.get(i).toLowerCase().trim()).append("'");
            if (i < ingredientCount - 1) inClause.append(",");
        }

        // Exact match query
        String exactQuery = "SELECT r.recipe_name, r.recipe_image, r.recipe_description " +
                "FROM recipes r " +
                "JOIN ( " +
                "    SELECT r_i_id AS recipe_id " +
                "    FROM recipe_ingredients " +
                "    GROUP BY r_i_id " +
                "    HAVING COUNT(*) = " + ingredientCount + " " +
                "       AND COUNT(CASE WHEN lower(trim(ing_name)) IN (" + inClause + ") THEN 1 END) = " + ingredientCount + " " +
                ") matched ON matched.recipe_id = r.recipe_id";

        // Partial match query
        String partialQuery = "SELECT DISTINCT r.recipe_name, r.recipe_image, r.recipe_description " +
                "FROM recipes r " +
                "JOIN recipe_ingredients ri ON ri.r_i_id = r.recipe_id " +
                "WHERE lower(trim(ri.ing_name)) IN (" + inClause + ")";

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement stExact = con.createStatement();
             ResultSet rsExact = stExact.executeQuery(exactQuery);
             Statement stPartial = con.createStatement();
             ResultSet rsPartial = stPartial.executeQuery(partialQuery)) {

            while (rsExact.next()) {
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("name", rsExact.getString("recipe_name"));
                recipe.put("image", rsExact.getString("recipe_image"));
                recipe.put("description", rsExact.getString("recipe_description"));
                exactMatches.add(recipe);
            }

            while (rsPartial.next()) {
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("name", rsPartial.getString("recipe_name"));
                recipe.put("image", rsPartial.getString("recipe_image"));
                recipe.put("description", rsPartial.getString("recipe_description"));
                partialMatches.add(recipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        result.put("exactMatches", exactMatches);
        result.put("partialMatches", partialMatches);
        return result;
    }
}
