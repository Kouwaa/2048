package com.game2048.gui;

import com.game2048.core.Direction;
import com.game2048.core.Grid;
import com.game2048.gui.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame {
    private Grid grid;
    private final JPanel gamePanel;
    private final JLabel scoreLabel;
    private int score;

    public GameFrame(Grid grid, java.util.function.Consumer<Direction> onKeyPress, Runnable onRestart) {
        this.grid = grid;
        this.score = 0; // Initial score
        this.gamePanel = new GamePanel(grid);

        // Calculate dynamic window size
        int tileSize = 200; // Size of each tile
        int margin = 50;    // Space for padding and UI elements
        int windowWidth = grid.getSize() * tileSize + margin;
        int windowHeight = grid.getSize() * tileSize + margin + 100; // Add space for headers

        // Create a panel for the header (score and restart button)
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        // Create a label for the score
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        headerPanel.add(scoreLabel, BorderLayout.WEST);

        // Create a restart button
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRestart.run(); // Call the restart logic
            }
        });
        headerPanel.add(restartButton, BorderLayout.EAST);

        // Set up the main frame
        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(windowWidth, windowHeight); // Use dynamic size
        setResizable(false);
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        setFocusable(true);

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

        setVisible(true);
    }

    public void updateScore(int points) {
        score += points;
        scoreLabel.setText("Score: " + score);
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
