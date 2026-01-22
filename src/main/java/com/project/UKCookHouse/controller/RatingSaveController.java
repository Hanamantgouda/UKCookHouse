package com.project.UKCookHouse.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class RatingSaveController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

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

        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
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
