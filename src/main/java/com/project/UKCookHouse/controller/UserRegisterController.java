package com.project.UKCookHouse.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserRegisterController {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();

        String username = userData.get("username");
        String email = userData.get("email");
        String password = userData.get("password");

        if (username == null || email == null || password == null) {
            response.put("status", "error");
            response.put("message", "All fields are required!");
            return response;
        }

        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Check if email already exists
            String checkQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    response.put("status", "error");
                    response.put("message", "Email already exists!");
                    return response;
                }
            }

            // Hash password before storing
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            String insertQuery = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(insertQuery)) {
                pst.setString(1, username);
                pst.setString(2, email);
                pst.setString(3, hashedPassword);

                pst.executeUpdate();

                response.put("status", "success");
                response.put("message", "User registered successfully!");
            }

        } catch (SQLException e) {
            response.put("status", "error");
            response.put("message", "Database error: " + e.getMessage());
        }

        return response;
    }
}
