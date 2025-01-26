package com.game2048.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.game2048.core.GameLogic;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        // Configuration de base de la fenêtre
        setTitle("2048 Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Centrer la fenêtre

        // Couleur principale
        Color primaryColor = new Color(255, 200, 140); // Orange clair

        // Charger l'image du logo
        String imagePath = "images/2048_game_logo.png"; // Chemin relatif à la racine du projet
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            System.err.println("Image introuvable : " + imagePath);
        }
        ImageIcon logoIcon = new ImageIcon(imageFile.getAbsolutePath());
        Image scaledLogo = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel principal pour les composants
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(primaryColor);

        // Titre du jeu
        JLabel titleLabel = new JLabel("Bienvenue à 2048");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20)); // Espacement au-dessus
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20)); // Espacement après le titre

        // Ajouter le logo
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(20)); // Espacement après le logo

        // Champs d'identifiant
        JLabel usernameLabel = new JLabel("Identifiant:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(Color.DARK_GRAY);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(300, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(usernameField);

        mainPanel.add(Box.createVerticalStrut(20)); // Espacement après le champ texte

        // Bouton de connexion
        JButton loginButton = new JButton("Jouer");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(230, 150, 100));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir la fenêtre de jeu avec un GameLogic fonctionnel
                SwingUtilities.invokeLater(() -> {
                    new GameLogic(4); // Initialisation avec la taille par défaut
                });
                dispose(); // Fermer la fenêtre de login
            }
        });
        mainPanel.add(loginButton);

        mainPanel.add(Box.createVerticalStrut(10)); // Espacement entre les boutons

        // Bouton pour afficher les meilleurs scores
        JButton bestScoresButton = new JButton("Afficher les meilleurs scores");
        bestScoresButton.setFont(new Font("Arial", Font.BOLD, 16));
        bestScoresButton.setBackground(new Color(230, 150, 100));
        bestScoresButton.setForeground(Color.WHITE);
        bestScoresButton.setFocusPainted(false);
        bestScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bestScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action pour afficher les meilleurs scores (à implémenter plus tard)
                JOptionPane.showMessageDialog(LoginFrame.this, 
                        "Cette fonctionnalité sera implémentée.", 
                        "Meilleurs Scores", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mainPanel.add(bestScoresButton);

        // Ajouter le panneau principal à la fenêtre
        add(mainPanel, BorderLayout.CENTER);

        // Rendre la fenêtre visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
