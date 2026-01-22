package com.project.UKCookHouse.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ValidateEmailController {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

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

        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
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
