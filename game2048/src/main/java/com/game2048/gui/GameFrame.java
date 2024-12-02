package com.game2048.gui;

import com.game2048.core.Direction;
import com.game2048.core.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame {
    private final Grid grid;
    private final JPanel gamePanel;

    public GameFrame(Grid grid, java.util.function.Consumer<Direction> onKeyPress) {
        this.grid = grid;
        this.gamePanel = new GamePanel(grid);

        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setResizable(false);
        add(gamePanel);
        setFocusable(true);

        // KeyListener for arrow keys
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

    public void repaint() {
        gamePanel.repaint();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
