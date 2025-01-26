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

    private void saveScore() {
        int score = gameFrame.getScore();
        dbManager.insertScore(username, score);
    }

    public void restartGame() {
        this.grid = new Grid(grid.getSize());
        gameFrame.resetScore();
        gameFrame.updateGrid(this.grid);
        gameFrame.repaint();
        gameFrame.requestFocusInWindow();
    }

    public void changeGridSize(int size) {
        this.grid = new Grid(size);
        gameFrame.resetScore();
        gameFrame.updateGrid(this.grid);
        gameFrame.requestFocusInWindow();
    }
}
