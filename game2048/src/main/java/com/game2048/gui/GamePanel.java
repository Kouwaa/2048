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
import com.game2048.util.Constants;
import com.game2048.util.Colors;

public class GamePanel extends JPanel {
    private Grid grid;

    public GamePanel(Grid grid) {
        this.grid = grid;

        // Mise à jour des dimensions pour les nouvelles tailles des tiles
        setPreferredSize(new Dimension(
            grid.getSize() * Constants.TILE_SIZE + (grid.getSize() - 1) * Constants.TILE_GAP,
            grid.getSize() * Constants.TILE_SIZE + (grid.getSize() - 1) * Constants.TILE_GAP
        ));
        setBackground(Colors.BACKGROUND);
    }

    public void setGrid(Grid newGrid) {
        this.grid = newGrid;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int tileSize = Constants.TILE_SIZE; // Taille des tiles définie dans Constants
        int tileGap = Constants.TILE_GAP; // Espace entre les tiles

        // Efface le fond
        g2d.setColor(Colors.BACKGROUND);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Dessine les tiles
        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                Tile tile = grid.getGrid()[row][col];
                int x = col * (tileSize + tileGap);
                int y = row * (tileSize + tileGap);

                // Dessiner l'arrière-plan des tiles
                g2d.setColor(tile.isEmpty() ? Colors.VOID : getTileColor(tile.getValue()));
                g2d.fillRoundRect(x, y, tileSize, tileSize, 15, 15);

                // Dessiner la valeur des tiles
                if (!tile.isEmpty()) {
                    g2d.setColor(tile.getValue() == 2 || tile.getValue() == 4 ? Colors.DARK : Colors.LIGHT);
                    g2d.setFont(new Font("Arial", Font.BOLD, tileSize / 3)); // Taille de police ajustée à la taille des tiles
                    String value = String.valueOf(tile.getValue());
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(value);
                    int textHeight = fm.getAscent();
                    g2d.drawString(value, x + (tileSize - textWidth) / 2, y + (tileSize + textHeight) / 2 - 5);
                }
            }
        }
    }

    private Color getTileColor(int value) {
        return switch (value) {
            case 2 -> Colors.GREY2;
            case 4 -> Colors.GREY4;
            case 8 -> Colors.ORANGE8;
            case 16 -> Colors.ORANGE16;
            case 32 -> Colors.RED32;
            case 64 -> Colors.RED64;
            case 128 -> Colors.YELLOW128;
            case 256 -> Colors.YELLOW256;
            case 512 -> Colors.YELLOW512;
            case 1024 -> Colors.YELLOW1024;
            case 2048 -> Colors.YELLOW2048;
            default -> Colors.VOID;
        };
    }
}
