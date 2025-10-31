package com.project.UKCookHouse.controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class testController {

    private final String URL = "jdbc:postgresql://localhost:5432/UK_COOK_HOUSE";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    /**
     * Endpoint: /getRecipesByIngredients
     * Example Request:
     *   POST /getRecipesByIngredients
     *   Body: { "ingredients": ["rice", "onion", "turmeric powder", "cumin"] }
     *
     * Returns recipes that match at least 70% of the user-selected ingredients.
     */
    @PostMapping("/search")
    public List<Map<String, Object>> getRecipesByIngredients(@RequestBody Map<String, Object> requestBody) {
        List<Map<String, Object>> recipes = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<String> ingredients = (List<String>) requestBody.get("ingredients");

        if (ingredients == null || ingredients.isEmpty()) {
            return Collections.singletonList(Map.of("error", "No ingredients provided"));
        }

        String query =
                "WITH selected AS ( " +
                        "    SELECT lower(trim(unnest(?::text[]))) AS sel_ing " +
                        "), recipe_stats AS ( " +
                        "    SELECT " +
                        "        r.recipe_id, r.recipe_name, r.recipe_image, r.recipe_description, " +
                        "        COUNT(DISTINCT lower(trim(ri.ing_name))) FILTER (WHERE ri.ing_name IS NOT NULL) AS total_recipe_ings, " +
                        "        COUNT(DISTINCT lower(trim(ri.ing_name))) FILTER ( " +
                        "            WHERE lower(trim(ri.ing_name)) IN (SELECT sel_ing FROM selected) " +
                        "        ) AS matched_count " +
                        "    FROM recipes r " +
                        "    JOIN recipe_ingredients ri ON ri.r_i_id = r.recipe_id " +
                        "    GROUP BY r.recipe_id, r.recipe_name, r.recipe_image, r.recipe_description " +
                        ") " +
                        "SELECT recipe_id, recipe_name, recipe_image, recipe_description, " +
                        "       matched_count, total_recipe_ings, " +
                        "       (total_recipe_ings - matched_count) AS missing_count, " +
                        "       ROUND((matched_count * 100.0 / NULLIF(total_recipe_ings, 0)), 2) AS match_percentage " +
                        "FROM recipe_stats " +
                        "WHERE matched_count >= 1 " +
                        "  AND (total_recipe_ings - matched_count) = 1 " +
                        "  AND NOT (matched_count = 1 AND total_recipe_ings > 2) " +
                        "ORDER BY match_percentage DESC;";



        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {

            // Convert ingredient list to SQL array
            Array sqlArray = con.createArrayOf("text", ingredients.toArray());
            ps.setArray(1, sqlArray);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("recipe_id", rs.getInt("recipe_id"));
                recipe.put("recipe_name", rs.getString("recipe_name"));
                recipe.put("recipe_image", rs.getString("recipe_image"));
                recipe.put("recipe_description", rs.getString("recipe_description"));
                recipe.put("match_percentage", rs.getDouble("match_percentage"));
                recipes.add(recipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            recipes.clear();
            recipes.add(Map.of("error", "Database error: " + e.getMessage()));
        }

        return recipes;
    }
}
