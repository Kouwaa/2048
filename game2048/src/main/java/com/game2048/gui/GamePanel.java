
package com.game2048.gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.game2048.core.Grid;
import com.game2048.core.Tile;
import com.game2048.gui.GamePanel;




public class GamePanel extends JPanel {
    private Grid grid;

    public GamePanel(Grid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(400, 400)); // Adjust size as needed
    }

    public void setGrid(Grid newGrid) {
        this.grid = newGrid; // Update the grid
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int tileSize = getWidth() / grid.getSize();
        int tileGap = 5;

        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                Tile tile = grid.getGrid()[row][col];
                int x = col * tileSize + tileGap;
                int y = row * tileSize + tileGap;

                // Draw tile background
                g2d.setColor(tile.isEmpty() ? Color.LIGHT_GRAY : getTileColor(tile.getValue()));
                g2d.fillRoundRect(x, y, tileSize - tileGap, tileSize - tileGap, 15, 15);

                // Draw tile value
                if (!tile.isEmpty()) {
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, tileSize / 3));
                    String value = String.valueOf(tile.getValue());
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(value);
                    int textHeight = fm.getAscent();
                    g2d.drawString(value, x + (tileSize - tileGap - textWidth) / 2, y + (tileSize - tileGap + textHeight) / 2);
                }
            }
        }
    }

    private Color getTileColor(int value) {
        return switch (value) {
            case 2 -> Color.WHITE;
            case 4 -> Color.LIGHT_GRAY;
            case 8 -> Color.ORANGE;
            case 16 -> Color.PINK;
            case 32 -> Color.RED;
            case 64 -> Color.MAGENTA;
            case 128 -> Color.CYAN;
            case 256 -> Color.BLUE;
            case 512 -> Color.GREEN;
            case 1024 -> Color.YELLOW;
            case 2048 -> Color.BLACK;
            default -> Color.DARK_GRAY;
        };
    }
}
