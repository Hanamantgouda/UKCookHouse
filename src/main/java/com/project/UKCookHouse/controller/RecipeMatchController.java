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
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Map<String, Object>> exactMatches = new ArrayList<>();
        List<Map<String, Object>> partialMatches = new ArrayList<>();

        if (ingredients == null || ingredients.isEmpty()) return result;

        // ✅ Build comma-separated ingredient list for exact match query only
        StringBuilder arrayBuilder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            arrayBuilder.append("'").append(ingredients.get(i).toLowerCase().trim().replace("'", "''")).append("'");
            if (i < ingredients.size() - 1) arrayBuilder.append(",");
        }
        String ingredientArray = arrayBuilder.toString();

        // ✅ Exact Match Query
        String exactQuery =
                "WITH selected AS ( " +
                        "    SELECT unnest(ARRAY[" + ingredientArray + "]::text[]) AS ing_name " +
                        ") " +
                        "SELECT r.recipe_id, r.recipe_name, r.recipe_image, r.recipe_description " +
                        "FROM recipes r " +
                        "JOIN recipe_ingredients ri ON r.recipe_id = ri.r_i_id " +
                        "GROUP BY r.recipe_id, r.recipe_name, r.recipe_image, r.recipe_description " +
                        "HAVING ARRAY_AGG(DISTINCT lower(trim(ri.ing_name))) <@ (SELECT ARRAY_AGG(lower(trim(ing_name))) FROM selected) " +
                        "   AND COUNT(DISTINCT ri.ing_name) = (SELECT COUNT(*) FROM selected);";

        // ✅ Partial Match Query (Only one missing ingredient)
        String partialQuery =
                "WITH selected AS ( " +
                        "    SELECT lower(trim(unnest(?::text[]))) AS sel_ing " +
                        "), recipe_stats AS ( " +
                        "    SELECT " +
                        "        r.recipe_id, r.recipe_name, r.recipe_image, r.recipe_description, " +
                        "        COUNT(DISTINCT lower(trim(ri.ing_name))) FILTER (WHERE ri.ing_name IS NOT NULL) AS total_ingredients, " +
                        "        COUNT(DISTINCT lower(trim(ri.ing_name))) FILTER ( " +
                        "            WHERE lower(trim(ri.ing_name)) IN (SELECT sel_ing FROM selected) " +
                        "        ) AS matched_count " +
                        "    FROM recipes r " +
                        "    JOIN recipe_ingredients ri ON ri.r_i_id = r.recipe_id " +
                        "    GROUP BY r.recipe_id, r.recipe_name, r.recipe_image, r.recipe_description " +
                        ") " +
                        "SELECT recipe_id, recipe_name, recipe_image, recipe_description, " +
                        "       matched_count, total_ingredients, " +
                        "       (total_ingredients - matched_count) AS missing_count, " +
                        "       ROUND((matched_count * 100.0 / NULLIF(total_ingredients, 0)), 2) AS match_percentage " +
                        "FROM recipe_stats " +
                        "WHERE matched_count >= 1 " +
                        "  AND (total_ingredients - matched_count) <= 2 " +
                        "  AND NOT (matched_count = 1 AND total_ingredients > 2) " +
                        "ORDER BY match_percentage DESC;";

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement stExact = con.createStatement();
             ResultSet rsExact = stExact.executeQuery(exactQuery);
             PreparedStatement psPartial = con.prepareStatement(partialQuery)) {

            // ✅ Set the PostgreSQL array parameter for partial query
            Array sqlArray = con.createArrayOf("text", ingredients.toArray());
            psPartial.setArray(1, sqlArray);
            ResultSet rsPartial = psPartial.executeQuery();

            // ✅ Exact matches
            while (rsExact.next()) {
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("name", rsExact.getString("recipe_name"));
                recipe.put("image", rsExact.getString("recipe_image"));
                recipe.put("description", rsExact.getString("recipe_description"));
                exactMatches.add(recipe);
            }

            // ✅ Partial matches (only one missing)
            while (rsPartial.next()) {
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("name", rsPartial.getString("recipe_name"));
                recipe.put("image", rsPartial.getString("recipe_image"));
                recipe.put("description", rsPartial.getString("recipe_description"));
                recipe.put("missingCount", rsPartial.getInt("missing_count"));
                recipe.put("matchPercentage", rsPartial.getDouble("match_percentage"));
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
