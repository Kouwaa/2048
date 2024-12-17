package com.game2048.core;

import com.game2048.gui.GameFrame;
import com.game2048.gui.GamePanel;

public class GameLogic {
    private Grid grid;
    private final GameFrame gameFrame;

    public GameLogic(int size) {
        this.grid = new Grid(size);
        this.gameFrame = new GameFrame(grid, this::handleKeyPress, this::restartGame);
    }

    public void handleKeyPress(Direction direction) {
        int points = grid.move(direction); // Assume move() now returns points earned
        if (points > 0) {
            gameFrame.updateScore(points);
        }

        // Check game status
        if (grid.hasWon()) {
            gameFrame.showMessage("Congratulations! You've won!");
        } else if (!grid.canMove()) {
            gameFrame.showMessage("Game Over! No more moves available.");
        }

        // Update the UI
        gameFrame.repaint();
    }



    public void restartGame() {
        // Reset the grid
        this.grid = new Grid(grid.getSize());

        // Reset the score
        gameFrame.resetScore();

        // Update the grid in the game frame
        gameFrame.updateGrid(this.grid);

        // Ensure UI is refreshed
        gameFrame.repaint();

        // Refocus the frame for key events
        gameFrame.requestFocusInWindow();
    }


}
