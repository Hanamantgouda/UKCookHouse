package com.project.UKCookHouse.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.*;

@RestController
public class RecipeStepsController {

    @CrossOrigin(origins = "*")
    @GetMapping("/recipe-steps")
    public List<Map<String, Object>> getRecipeSteps(@RequestParam String recipeName) {
        List<Map<String, Object>> stepsList = new ArrayList<>();

        String url = "jdbc:postgresql://localhost:5432/UK_COOK_HOUSE";
        String username = "postgres";
        String password = "postgres";

        // SQL joins recipes and recipe_steps, filtered by recipe name, ordered by step_number
        String query = "SELECT rs.step_number, rs.step " +
                "FROM recipe_steps rs " +
                "JOIN recipes r ON rs.r_s_id = r.recipe_id " +
                "WHERE r.recipe_name = ? " +
                "ORDER BY rs.step_number ASC";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, recipeName);  // set recipe name

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> stepMap = new HashMap<>();
                    stepMap.put("step_number", rs.getInt("step_number"));
                    stepMap.put("step", rs.getString("step"));
                    stepsList.add(stepMap);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stepsList;
    }
}
