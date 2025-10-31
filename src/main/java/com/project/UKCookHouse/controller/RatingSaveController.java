package com.project.UKCookHouse.controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class RatingSaveController {

    private final String URL = "jdbc:postgresql://localhost:5432/UK_COOK_HOUSE";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    /**
     * Endpoint: POST /saveRating
     * Stores or updates a user's rating for a recipe.
     *
     * Example JSON body:
     * {
     *   "u_id": 1,
     *   "r_id": 5,
     *   "rating": 4
     * }
     */
    @PostMapping("/saveRating")
    public Map<String, Object> saveRating(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        int userId = (int) request.get("u_id");
        int recipeId = (int) request.get("r_id");
        int rating = (int) request.get("rating");

        String insertOrUpdateQuery = """
            INSERT INTO ratings (u_id, r_id, rating)
            VALUES (?, ?, ?)
            ON CONFLICT (u_id, r_id)
            DO UPDATE SET rating = EXCLUDED.rating;
        """;

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement ps = con.prepareStatement(insertOrUpdateQuery)) {

            ps.setInt(1, userId);
            ps.setInt(2, recipeId);
            ps.setInt(3, rating);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                response.put("message", "✅ Rating saved successfully.");
            } else {
                response.put("message", "⚠️ No rows affected. Please check your input.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.put("error", "Database error: " + e.getMessage());
        }

        return response;
    }
}
