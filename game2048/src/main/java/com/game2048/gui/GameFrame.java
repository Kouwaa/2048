package com.game2048.gui;

import com.game2048.core.Direction;
import com.game2048.core.Grid;
import com.game2048.util.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;

public class GameFrame extends JFrame {
    private Grid grid;
    private final JPanel gamePanel;
    private final JLabel scoreLabel;
    private final JLabel bestScoreLabel;
    private int score;
    private int bestScore;
    private final DatabaseManager dbManager;

    public GameFrame(Grid grid, Consumer<Direction> onKeyPress, Runnable onRestart, Consumer<Integer> onGridSizeChange) {
        this.grid = grid;
        this.score = 0;
        this.bestScore = 0;
        this.gamePanel = new GamePanel(grid);
        this.dbManager = new DatabaseManager();

        // Configuration de la fenêtre principale
        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setFocusable(true);

        // Panneau supérieur (header) : Score, Meilleur score et bouton Restart
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        // Label du score
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        headerPanel.add(scoreLabel, BorderLayout.WEST);

        // Label du meilleur score
        bestScoreLabel = new JLabel("Meilleur score: " + bestScore);
        bestScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        bestScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(bestScoreLabel, BorderLayout.CENTER);

        // Bouton Restart
        ImageIcon resetIcon = new ImageIcon("images/reset.png");
        Image scaledImage = resetIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledResetIcon = new ImageIcon(scaledImage);
        JButton restartButton = new JButton(scaledResetIcon);
        restartButton.setPreferredSize(new Dimension(50, 50));
        restartButton.setFocusPainted(false);
        restartButton.setBorder(BorderFactory.createEmptyBorder());
        restartButton.setContentAreaFilled(false);
        restartButton.addActionListener(e -> onRestart.run());
        headerPanel.add(restartButton, BorderLayout.EAST);

        // Ajouter le header en haut
        add(headerPanel, BorderLayout.NORTH);

        // Panneau des niveaux (côté droit)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Création des boutons pour les niveaux
        for (int size = 4; size <= 8; size++) {
            final int gridSize = size;
            JButton button = new JButton(size + " x " + size);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(255, 230, 180));
            button.setForeground(Color.DARK_GRAY);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 150, 100), 2),
                    BorderFactory.createEmptyBorder(5, 15, 5, 15)
            ));
            button.addActionListener(e -> {
                onGridSizeChange.accept(gridSize);
                resizeWindow(gridSize);
            });
            buttonPanel.add(button);
        }

        // Ajouter les panneaux de jeu et des niveaux
        add(gamePanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(new Color(240, 240, 240));
        rightPanel.add(Box.createHorizontalStrut(20), BorderLayout.WEST);
        rightPanel.add(buttonPanel, BorderLayout.EAST);
        add(rightPanel, BorderLayout.EAST);

        // Boutons en bas : Meilleurs Scores et Retour au Login
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Bouton "Meilleurs Scores"
        JButton showScoresButton = new JButton("Afficher les Meilleurs Scores");
        showScoresButton.setFont(new Font("Arial", Font.BOLD, 14));
        showScoresButton.setBackground(new Color(230, 150, 100));
        showScoresButton.setForeground(Color.WHITE);
        showScoresButton.setFocusPainted(false);
        showScoresButton.addActionListener(e -> showTopScores());
        bottomPanel.add(showScoresButton);

        // Bouton "Retour au Login"
        JButton backToLoginButton = new JButton("Retour au Login");
        backToLoginButton.setFont(new Font("Arial", Font.BOLD, 14));
        backToLoginButton.setBackground(new Color(230, 150, 100));
        backToLoginButton.setForeground(Color.WHITE);
        backToLoginButton.setFocusPainted(false);
        backToLoginButton.addActionListener(e -> {
            SwingUtilities.invokeLater(LoginFrame::new); // Ouvre la fenêtre de login
            dispose(); // Ferme la fenêtre actuelle
        });
        bottomPanel.add(backToLoginButton);

        // Ajouter le panneau inférieur
        add(bottomPanel, BorderLayout.SOUTH);

        // Gestion des touches fléchées pour les mouvements
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> onKeyPress.accept(Direction.UP);
                    case KeyEvent.VK_DOWN -> onKeyPress.accept(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> onKeyPress.accept(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> onKeyPress.accept(Direction.RIGHT);
                }
            }
        });

        resizeWindow(grid.getSize());
        setVisible(true);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Affiche les meilleurs scores dans une liste stylisée.
     */
    private void showTopScores() {
        List<String> topScores = dbManager.getTopScores();
        if (topScores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun score disponible.", "Meilleurs Scores", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame scoresFrame = new JFrame("Top 10 Meilleurs Scores");
        scoresFrame.setSize(300, 400);
        scoresFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scoresFrame.setLayout(new BorderLayout());
        scoresFrame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Top 10 Meilleurs Scores", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JList<String> scoresList = new JList<>(topScores.toArray(new String[0]));
        scoresList.setFont(new Font("Arial", Font.PLAIN, 16));
        scoresList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scoresList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(scoresList);

        JButton closeButton = new JButton("Fermer");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.addActionListener(e -> {
            scoresFrame.dispose(); // Fermer la fenêtre des scores
            requestFocusInWindow(); // Redonner le focus à la fenêtre principale
        });
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        scoresFrame.add(titleLabel, BorderLayout.NORTH);
        scoresFrame.add(scrollPane, BorderLayout.CENTER);
        scoresFrame.add(buttonPanel, BorderLayout.SOUTH);

        scoresFrame.setVisible(true);
    }


    public void updateScore(int points) {
        score += points;
        scoreLabel.setText("Score: " + score);
        if (score > bestScore) {
            bestScore = score;
            bestScoreLabel.setText("Meilleur score: " + bestScore);
        }
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
        scoreLabel.setText("Score: " + score);
    }

    public void updateGrid(Grid newGrid) {
        this.grid = newGrid;
        ((GamePanel) gamePanel).setGrid(newGrid);
        repaint();
    }

    private void resizeWindow(int gridSize) {
        int tileSize = 100;
        int gap = 10;
        int margin = 50;

        int windowWidth = gridSize * (tileSize + gap) + margin + 35;
        int windowHeight = gridSize * (tileSize + gap) + margin + 35;

        setSize(windowWidth, windowHeight);
        setLocationRelativeTo(null);
    }
}
