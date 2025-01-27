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

        String imagePath = "images/2048_game_logo.png";//contenue dans le dossier images
        File imageFile = new File(imagePath);
        ImageIcon logoIcon = new ImageIcon(imageFile.getAbsolutePath());
        Image scaledLogo = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); // le logo
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(primaryColor);

        // Titre principal de la fenêtre
        JLabel titleLabel = new JLabel("Bienvenue à 2048"); 
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Police en gras et grande taille
        titleLabel.setForeground(Color.DARK_GRAY); // Couleur du texte en gris foncé
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer horizontalement le texte
        mainPanel.add(Box.createVerticalStrut(40)); // Espacement avant le titre
        mainPanel.add(titleLabel); // Ajouter le titre au panneau principal
        mainPanel.add(Box.createVerticalStrut(40)); // Espacement après le titre
        mainPanel.add(logoLabel); // Ajouter le logo au panneau principal
        mainPanel.add(Box.createVerticalStrut(40)); // Espacement après le logo

        JLabel usernameLabel = new JLabel("Identifiant:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Taille de police
        usernameLabel.setForeground(Color.DARK_GRAY);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(500, 50)); // le champ de texte
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20)); // Texte plus lisible
        mainPanel.add(usernameField);

        mainPanel.add(Box.createVerticalStrut(30)); // Espacement entre le champ et le bouton

        JButton loginButton = new JButton("Jouer");
        loginButton.setFont(new Font("Arial", Font.BOLD, 24)); // Taille de police
        loginButton.setBackground(new Color(230, 150, 100));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 50)); //Dimension du bouton
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
        bestScoresButton.setFont(new Font("Arial", Font.BOLD, 24)); // Taille de police 
        bestScoresButton.setBackground(new Color(230, 150, 100));
        bestScoresButton.setForeground(Color.WHITE);
        bestScoresButton.setFocusPainted(false);
        bestScoresButton.setPreferredSize(new Dimension(300, 50)); // Dimension du bouton
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
        scoresFrame.setSize(500, 600); // Taille
        scoresFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scoresFrame.setLayout(new BorderLayout());
        scoresFrame.setLocationRelativeTo(null);

        // Titre
        JLabel titleLabel = new JLabel("Top 10 Meilleurs Scores", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Taille de police 
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Liste des scores
        JList<String> scoresList = new JList<>(topScores.toArray(new String[0]));
        scoresList.setFont(new Font("Arial", Font.PLAIN, 20)); // Taille de police 
        scoresList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scoresList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel avec barre de défilement
        JScrollPane scrollPane = new JScrollPane(scoresList);

        // Ajouter les composants
        scoresFrame.add(titleLabel, BorderLayout.NORTH);
        scoresFrame.add(scrollPane, BorderLayout.CENTER);

        // Bouton de fermeture
        JButton closeButton = new JButton("Fermer");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20)); // Taille de police      
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
