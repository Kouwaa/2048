package com.game2048.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

import com.game2048.core.GameLogic;
import com.game2048.util.DatabaseManager;

public class LoginFrame extends JFrame {
    private final DatabaseManager dbManager = new DatabaseManager();

    public LoginFrame() {
        setTitle("2048 Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 900);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        Color primaryColor = new Color(255, 200, 140);

        String imagePath = "images/2048_game_logo.png";
        File imageFile = new File(imagePath);
        ImageIcon logoIcon = new ImageIcon(imageFile.getAbsolutePath());
        Image scaledLogo = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Agrandir le logo
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(primaryColor);

        JLabel titleLabel = new JLabel("Bienvenue à 2048");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Taille de police augmentée
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(40)); // Espacement augmenté
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40)); // Espacement augmenté
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(40));

        JLabel usernameLabel = new JLabel("Identifiant:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Taille de police augmentée
        usernameLabel.setForeground(Color.DARK_GRAY);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(500, 50)); // Agrandir le champ de texte
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20)); // Texte plus lisible
        mainPanel.add(usernameField);

        mainPanel.add(Box.createVerticalStrut(30)); // Espacement entre le champ et le bouton

        JButton loginButton = new JButton("Jouer");
        loginButton.setFont(new Font("Arial", Font.BOLD, 24)); // Taille de police augmentée
        loginButton.setBackground(new Color(230, 150, 100));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 50)); // Agrandir le bouton
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un identifiant.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SwingUtilities.invokeLater(() -> new GameLogic(4, username));
            dispose();
        });
        mainPanel.add(loginButton);

        mainPanel.add(Box.createVerticalStrut(20));

        JButton bestScoresButton = new JButton("Afficher les meilleurs scores");
        bestScoresButton.setFont(new Font("Arial", Font.BOLD, 24)); // Taille de police augmentée
        bestScoresButton.setBackground(new Color(230, 150, 100));
        bestScoresButton.setForeground(Color.WHITE);
        bestScoresButton.setFocusPainted(false);
        bestScoresButton.setPreferredSize(new Dimension(300, 50)); // Agrandir le bouton
        bestScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bestScoresButton.addActionListener(e -> {
            List<String> topScores = dbManager.getTopScores();
            if (topScores.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun score disponible.", "Meilleurs Scores", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showTopScores(topScores); // Appel de la méthode pour afficher les scores dans une liste stylisée
            }
        });
        mainPanel.add(bestScoresButton);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Affiche les meilleurs scores dans une fenêtre avec une liste stylisée.
     */
    private void showTopScores(List<String> topScores) {
        JFrame scoresFrame = new JFrame("Top 10 Meilleurs Scores");
        scoresFrame.setSize(500, 600); // Taille augmentée
        scoresFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scoresFrame.setLayout(new BorderLayout());
        scoresFrame.setLocationRelativeTo(null);

        // Titre
        JLabel titleLabel = new JLabel("Top 10 Meilleurs Scores", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Taille de police augmentée
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Liste des scores
        JList<String> scoresList = new JList<>(topScores.toArray(new String[0]));
        scoresList.setFont(new Font("Arial", Font.PLAIN, 20)); // Taille de police augmentée
        scoresList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scoresList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel avec barre de défilement
        JScrollPane scrollPane = new JScrollPane(scoresList);

        // Ajouter les composants
        scoresFrame.add(titleLabel, BorderLayout.NORTH);
        scoresFrame.add(scrollPane, BorderLayout.CENTER);

        // Bouton de fermeture
        JButton closeButton = new JButton("Fermer");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20)); // Taille de police augmentée
        closeButton.addActionListener(e -> scoresFrame.dispose());
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        scoresFrame.add(buttonPanel, BorderLayout.SOUTH);

        scoresFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
