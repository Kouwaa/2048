package com.game2048.core;

import com.game2048.gui.GameFrame;

public class GameLogic {
    private final Grid grid;
    private final GameFrame gameFrame;

    public GameLogic(int size) {
        this.grid = new Grid(size);
        this.gameFrame = new GameFrame(grid, this::handleKeyPress);
    }

    public void handleKeyPress(Direction direction) {
        grid.move(direction);

        // Check game status
        if (grid.hasWon()) {
            gameFrame.showMessage("Congratulations! You've won!");
        } else if (!grid.canMove()) {
            gameFrame.showMessage("Game Over! No more moves available.");
        }

        // Update the UI
        gameFrame.repaint();
    }
}
