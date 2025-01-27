package com.game2048.core;

import com.game2048.gui.GameFrame;
import com.game2048.util.DatabaseManager;

public class GameLogic {
    private Grid grid;
    private final GameFrame gameFrame;
    private final DatabaseManager dbManager;
    private final String username;

    public GameLogic(int size, String username) {
        this.grid = new Grid(size);
        this.gameFrame = new GameFrame(grid, this::handleKeyPress, this::restartGame, this::changeGridSize);
        this.dbManager = new DatabaseManager();
        this.username = username;
    }

    public void handleKeyPress(Direction direction) {
        int points = grid.move(direction);
        if (points > 0) {
            gameFrame.updateScore(points);
        }

        // Check game status
        if (grid.hasWon()) {
            // Enregistrer le score en cas de victoire
            saveScore();
            gameFrame.showMessage("Congratulations! You've won!");
        } else if (!grid.canMove()) {
            // Enregistrer le score en cas de défaite
            saveScore();
            gameFrame.showMessage("Game Over! No more moves available.");
        }

        // Update the UI
        gameFrame.repaint();
    }

    /**
     * Sauvegarde le score actuel du joueur dans la base de données.
     */
    private void saveScore() {
        int score = gameFrame.getScore(); // Récupère le score actuel depuis l'interface graphique.
        dbManager.insertScore(username, score); // Insère le score associé à l'utilisateur dans la base de données.
    }

    /**
     * Redémarre le jeu en réinitialisant la grille et le score.
     */
    public void restartGame() {
        this.grid = new Grid(grid.getSize()); // Crée une nouvelle grille avec la taille actuelle.
        gameFrame.resetScore(); // Réinitialise le score affiché dans l'interface graphique.
        gameFrame.updateGrid(this.grid); // Met à jour la grille affichée dans l'interface graphique.
        gameFrame.repaint(); // Redessine l'interface graphique pour refléter les changements.
        gameFrame.requestFocusInWindow(); // Redonne le focus à la fenêtre pour détecter les entrées clavier.
    }

    /**
     * Change la taille de la grille du jeu et réinitialise la partie.
     *
     * @param size La nouvelle taille de la grille (par exemple, 4x4, 5x5, etc.).
     */
    public void changeGridSize(int size) {
        this.grid = new Grid(size); // Crée une nouvelle grille avec la taille spécifiée.
        gameFrame.resetScore(); // Réinitialise le score affiché dans l'interface graphique.
        gameFrame.updateGrid(this.grid); // Met à jour la grille affichée dans l'interface graphique.
        gameFrame.requestFocusInWindow(); // Redonne le focus à la fenêtre pour détecter les entrées clavier.
    }

}
