package com.game2048.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        // Logo du jeu
        ImageIcon logoIcon = new ImageIcon("/home/kawtar/Downloads/logo.png"); // Chemin du logo
        Image scaledLogo = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel principal pour les composants
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(primaryColor);

        // Titre du jeu
        JLabel titleLabel = new JLabel("Bienvenue à 2048");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Espacement après le titre
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(titleLabel);

        // Espacement après le logo
        mainPanel.add(Box.createVerticalStrut(20));

        // Champs d'identifiant
        JLabel usernameLabel = new JLabel("Identifiant:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(Color.DARK_GRAY);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(300, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Champ de mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setForeground(Color.DARK_GRAY);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Bouton de connexion
        JButton loginButton = new JButton("Se connecter");
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

        // Ajout des composants au panneau principal
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(loginButton);

        // Ajouter le panneau principal à la fenêtre
        add(mainPanel, BorderLayout.CENTER);

        // Rendre la fenêtre visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
