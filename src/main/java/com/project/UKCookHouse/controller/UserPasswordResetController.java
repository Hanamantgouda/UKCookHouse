package com.project.UKCookHouse.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserPasswordResetController {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @PostMapping("/reset-password")
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> requestData) {
        Map<String, Object> response = new HashMap<>();

        String email = requestData.get("email");
        String newPassword = requestData.get("newPassword");

        if (email == null || newPassword == null || email.isEmpty() || newPassword.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Email and new password are required!");
            return response;
        }

        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Check if user exists
            String checkQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    response.put("status", "error");
                    response.put("message", "No user found with this email!");
                    return response;
                }
            }

            // Hash new password
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // Update password in DB
            String updateQuery = "UPDATE users SET password = ? WHERE email = ?";
            try (PreparedStatement pst = con.prepareStatement(updateQuery)) {
                pst.setString(1, hashedPassword);
                pst.setString(2, email);
                pst.executeUpdate();

                response.put("status", "success");
                response.put("message", "Password updated successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Database error: " + e.getMessage());
        }

        return response;
    }
}
