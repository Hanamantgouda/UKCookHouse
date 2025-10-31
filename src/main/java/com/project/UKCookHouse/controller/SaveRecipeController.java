package com.project.UKCookHouse.controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class SaveRecipeController {

    private final String URL = "jdbc:postgresql://localhost:5432/UK_COOK_HOUSE";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    // --- Request class ---
    public static class SaveRecipeRequest {
        public int user_id;
        public int recipe_id;
    }

    /** ✅ Save Recipe */
    @PostMapping("/saveRecipe")
    public Map<String, Object> saveRecipe(@RequestBody SaveRecipeRequest request) {
        Map<String, Object> response = new HashMap<>();
        String query = "INSERT INTO save_recipe (u_id, r_u_id) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, request.user_id);
            stmt.setInt(2, request.recipe_id);
            stmt.executeUpdate();

            response.put("status", "saved");
            response.put("message", "Recipe saved successfully.");

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                response.put("status", "exists");
                response.put("message", "Recipe already saved.");
            } else {
                response.put("status", "error");
                response.put("message", e.getMessage());
            }
        }
        return response;
    }

    /** ❌ Unsave Recipe */
    @DeleteMapping("/unsaveRecipe")
    public Map<String, Object> unsaveRecipe(@RequestParam int userId, @RequestParam int recipeId) {
        Map<String, Object> response = new HashMap<>();
        String query = "DELETE FROM save_recipe WHERE u_id = ? AND r_u_id = ?";

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, recipeId);
            int deleted = stmt.executeUpdate();

            if (deleted > 0) {
                response.put("status", "unsaved");
                response.put("message", "Recipe removed from saved list.");
            } else {
                response.put("status", "not_found");
                response.put("message", "Recipe not found in saved list.");
            }

        } catch (SQLException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    /** 🔍 Check if recipe is saved */
    @GetMapping("/isRecipeSaved")
    public Map<String, Object> isRecipeSaved(@RequestParam int userId, @RequestParam int recipeId) {
        Map<String, Object> response = new HashMap<>();
        String query = "SELECT 1 FROM save_recipe WHERE u_id = ? AND r_u_id = ?";

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, recipeId);
            ResultSet rs = stmt.executeQuery();

            response.put("saved", rs.next());

        } catch (SQLException e) {
            response.put("error", e.getMessage());
        }
        return response;
    }
}
