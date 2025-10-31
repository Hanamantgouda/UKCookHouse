package com.project.UKCookHouse.controller;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ValidateEmailController {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/UK_COOK_HOUSE";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";

    @PostMapping("/validate-email")
    public Map<String, Object> validateEmail(@RequestBody Map<String, String> requestData) {
        Map<String, Object> response = new HashMap<>();

        String email = requestData.get("email");

        // Validate input
        if (email == null || email.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Email is required!");
            response.put("exists", false);
            return response;
        }

        String query = "SELECT user_id, username, email FROM users WHERE email = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Email exists
                response.put("status", "success");
                response.put("message", "Email exists!");
                response.put("exists", true);
                response.put("user_id", rs.getInt("user_id"));
                response.put("username", rs.getString("username"));
            } else {
                // Email does not exist
                response.put("status", "error");
                response.put("message", "Email not found.");
                response.put("exists", false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Database connection error: " + e.getMessage());
            response.put("exists", false);
        }

        return response;
    }
}
