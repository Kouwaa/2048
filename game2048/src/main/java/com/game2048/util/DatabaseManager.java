package com.game2048.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/Jeu2048"; // URL de la base
    private static final String USER = "polytech"; // Nom d'utilisateur
    private static final String PASSWORD = "polytech"; // Mot de passe

    // Méthode pour se connecter à la base de données
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Méthode pour insérer un score
    public void insertScore(String username, int score) {
        String sql = "INSERT INTO high_scores (username, score) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer les 10 meilleurs scores
    public List<String> getTopScores() {
        String sql = "SELECT username, score FROM high_scores ORDER BY score DESC LIMIT 10";
        List<String> topScores = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                topScores.add(username + " - " + score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topScores;
    }
}
