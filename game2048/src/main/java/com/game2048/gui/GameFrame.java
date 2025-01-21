package com.game2048.gui;

import com.game2048.core.Direction;
import com.game2048.core.Grid;


import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class GameFrame extends JFrame {
    private Grid grid;
    private final JPanel gamePanel;
    private final JLabel scoreLabel;
    private final JLabel bestScoreLabel; 
    private int score;
    private int bestScore;


    public GameFrame(Grid grid, Consumer<Direction> onKeyPress, Runnable onRestart, Consumer<Integer> onGridSizeChange) {
        this.grid = grid;
        this.score = 0; // Initial score
        this.bestScore = 0; // Initial best score
        this.gamePanel = new GamePanel(grid);

        // Set up the main frame
        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false); // Disable manual resizing
        setFocusable(true);

        // Create a panel for the header (score and restart button)
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        // Create a label for the score
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        headerPanel.add(scoreLabel, BorderLayout.WEST);

        // Create a label for the best score
        bestScoreLabel = new JLabel("Meilleur score: " + bestScore);
        bestScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        bestScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a restart button
        ImageIcon resetIcon = new ImageIcon("/home/kawtar/Downloads/reset.png"); // Chemin vers l'image d'origine
        Image scaledImage = resetIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionner l'image
        ImageIcon scaledResetIcon = new ImageIcon(scaledImage); // Créer une nouvelle icône avec l'image redimensionnée

        // Créer le bouton reset avec l'icône redimensionnée
        JButton restartButton = new JButton(scaledResetIcon);
        restartButton.setPreferredSize(new Dimension(50, 50)); // Ajuster la taille du bouton
        restartButton.setFocusPainted(false); // Supprimer l'indicateur de focus
        restartButton.setBorder(BorderFactory.createEmptyBorder()); // Supprimer la bordure
        restartButton.setContentAreaFilled(false); // Supprimer le remplissage
        restartButton.addActionListener(e -> onRestart.run()); // Action de réinitialisation


        // Create a panel to arrange the header items
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BorderLayout());
        scorePanel.add(scoreLabel, BorderLayout.WEST);
        scorePanel.add(bestScoreLabel, BorderLayout.CENTER);

        // Add the score panel and reset button to the header
        headerPanel.add(scorePanel, BorderLayout.CENTER);


        // Ajouter le bouton au panneau d'en-tête
        headerPanel.add(restartButton, BorderLayout.EAST);


        // Ajouter un espace autour du bouton
        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setLayout(new BorderLayout());
        buttonWrapper.setBackground(headerPanel.getBackground());
        buttonWrapper.add(restartButton, BorderLayout.CENTER);

        // Ajouter le bouton de redémarrage à l'en-tête
        headerPanel.add(buttonWrapper, BorderLayout.EAST);



        // Create buttons for grid size
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column, 10px gap
        buttonPanel.setBackground(new Color(240, 240, 240)); // Background color for the panel


        // Loop to create level buttons
        for (int size = 4; size <= 8; size++) {
            final int gridSize = size; // Taille de la grille
            JButton button = new JButton(size + " x " + size);

            // Appliquer un style moderne
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(255, 230, 180)); // Bleu clair
            button.setForeground(Color.DARK_GRAY);
            button.setFocusPainted(false); // Supprimer l'indicateur de focus
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 150, 100), 2), // Bordure externe
                BorderFactory.createEmptyBorder(5, 15, 5, 15))); // Marges internes

            // Action sur clic
            button.addActionListener(e -> {
                onGridSizeChange.accept(gridSize);
                resizeWindow(gridSize);
            });
            buttonPanel.add(button);
        }


        // Add the button panel to the far right with extra space
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(new Color(240, 240, 240)); // Match the button panel background
        rightPanel.add(Box.createHorizontalStrut(20), BorderLayout.WEST); // Add extra space to the left of buttons
        rightPanel.add(buttonPanel, BorderLayout.EAST);

        // Add the header and panels to the frame
        add(headerPanel, BorderLayout.NORTH);   
        add(gamePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Add KeyListener for arrow keys
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

        resizeWindow(grid.getSize()); // Initial resizing
        setVisible(true);
    }

    /**
     * Dynamically resizes the window based on the grid size.
     * @param gridSize The size of the grid (e.g., 4, 5, 6, etc.).
     */
    private void resizeWindow(int gridSize) {
        int tileSize = 100; // Example tile size
        int gap = 10;       // Gap between tiles
        int margin = 50;    // Space for borders and padding

        int windowWidth = gridSize * (tileSize + gap) + margin + 35; // Add extra space for buttons
        int windowHeight = gridSize * (tileSize + gap) + margin - 7 ; // Add space for headers

        setSize(windowWidth, windowHeight); // Resize the window
        setLocationRelativeTo(null); // Center the window on the screen
    }


    /**
     * Updates the current score and checks if it's a new best score.
     **/
    public void updateScore(int points) {
        score += points;
        scoreLabel.setText("Score: " + score);

        // Check if we have a new best score
        if (score > bestScore) {
            bestScore = score;
            bestScoreLabel.setText("Best: " + bestScore);
        }
    }

    public void updateGrid(Grid newGrid) {
        this.grid = newGrid; // Update the grid reference
        ((GamePanel) gamePanel).setGrid(newGrid); // Update the grid in the panel
        repaint(); // Repaint the UI to reflect changes
    }

    public void repaint() {
        gamePanel.repaint();
    }

    public void resetScore() {
        score = 0;
        scoreLabel.setText("Score: " + score);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
