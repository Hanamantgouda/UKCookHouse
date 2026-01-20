package com.project.UKCookHouse.controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class RatingFetchController {

    private final String URL = "jdbc:postgresql://localhost:5432/UK_COOK_HOUSE";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    @GetMapping("/getRecipeRating")
    public Map<String, Object> getRecipeRating(@RequestParam int recipeId) {
        Map<String, Object> response = new HashMap<>();

        String query = "SELECT COALESCE(AVG(rating), 0) AS avg_rating, COUNT(*) AS total_ratings FROM ratings WHERE r_id = ?";

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, recipeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double avgRating = rs.getDouble("avg_rating");
                int totalRatings = rs.getInt("total_ratings");

                response.put("recipe_id", recipeId);
                response.put("average_rating", Math.round(avgRating * 10.0) / 10.0); // round to 1 decimal
                response.put("total_ratings", totalRatings);
            } else {
                response.put("recipe_id", recipeId);
                response.put("average_rating", 0.0);
                response.put("total_ratings", 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.put("error", "Database error: " + e.getMessage());
        }

        return response;
    }
}
