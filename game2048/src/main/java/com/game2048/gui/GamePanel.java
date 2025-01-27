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
    private Grid grid; // La grille de jeu

    public GamePanel(Grid grid) {
        this.grid = grid;

        // Définir la taille préférée du panneau en fonction de la taille de la grille et des constantes
        setPreferredSize(new Dimension(
            grid.getSize() * Constants.TILE_SIZE + (grid.getSize() - 1) * Constants.TILE_GAP,
            grid.getSize() * Constants.TILE_SIZE + (grid.getSize() - 1) * Constants.TILE_GAP
        ));
        setBackground(Colors.BACKGROUND); // Définir la couleur de fond
    }

    /**
     * Met à jour la grille de jeu et redessine le panneau.
     * @param newGrid La nouvelle grille à afficher.
     */
    public void setGrid(Grid newGrid) {
        this.grid = newGrid;
        repaint(); // Redessine le panneau avec la nouvelle grille
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Appel au comportement par défaut

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Améliorer la qualité du rendu

        int tileSize = Constants.TILE_SIZE; // Taille des tiles
        int tileGap = Constants.TILE_GAP; // Espacement entre les tiles

        // Efface le panneau en remplissant tout l'arrière-plan avec la couleur de fond
        g2d.setColor(Colors.BACKGROUND);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Parcours de chaque position dans la grille pour dessiner les tiles
        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                Tile tile = grid.getGrid()[row][col]; // Récupère la tuile actuelle
                int x = col * (tileSize + tileGap); // Coordonnée X
                int y = row * (tileSize + tileGap); // Coordonnée Y

                // Dessiner l'arrière-plan de la tuile
                g2d.setColor(tile.isEmpty() ? Colors.VOID : getTileColor(tile.getValue()));
                g2d.fillRoundRect(x, y, tileSize, tileSize, 15, 15); // Dessin de la tuile avec des bords arrondis

                // Si la tuile contient une valeur, la dessiner
                if (!tile.isEmpty()) {
                    g2d.setColor(tile.getValue() == 2 || tile.getValue() == 4 ? Colors.DARK : Colors.LIGHT); // Couleur du texte
                    g2d.setFont(new Font("Arial", Font.BOLD, tileSize / 3)); // Taille de la police adaptée à la taille de la tuile
                    String value = String.valueOf(tile.getValue()); // Récupère la valeur de la tuile
                    FontMetrics fm = g2d.getFontMetrics(); // Permet de centrer le texte
                    int textWidth = fm.stringWidth(value); // Largeur du texte
                    int textHeight = fm.getAscent(); // Hauteur du texte
                    // Dessine la valeur au centre de la tuile
                    g2d.drawString(value, x + (tileSize - textWidth) / 2, y + (tileSize + textHeight) / 2 - 5);
                }
            }
        }
    }

    /**
     * Retourne la couleur correspondant à la valeur de la tuile.
     * @param value La valeur de la tuile.
     * @return La couleur associée.
     */
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
            default -> Colors.VOID; // Couleur par défaut pour des valeurs non prévues
        };
    }
}
