package com.game2048.core;

import java.util.Random;
import com.game2048.util.Constants;

public class Grid {
    private final Tile[][] grid;
    private final int size; // Dynamic grid size
    private final Random random;

    public Grid(int size) {
        this.size = size;
        this.grid = new Tile[size][size];
        this.random = new Random();

        // Initialize grid with empty tiles
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Tile();
            }
        }

        // Add two random tiles at the start
        addRandomTile();
        addRandomTile();
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }

    public void move(Direction direction) {
        // Movement logic, implemented dynamically based on size
        switch (direction) {
            case UP -> moveUp();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
        }
        addRandomTile(); // Add a new tile after each valid move
    }

    private void moveUp() {
        // Implement vertical upward movement logic
    }

    private void moveDown() {
        // Implement vertical downward movement logic
    }

    private void moveLeft() {
        // Implement horizontal left movement logic
    }

    private void moveRight() {
        // Implement horizontal right movement logic
    }

    private void addRandomTile() {
        int x, y;
        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (!grid[x][y].isEmpty());

        grid[x][y].setValue(random.nextInt(10) < 9 ? 2 : 4);
    }

    public boolean canMove() {
        // Check if there are valid moves left
        return true; // Replace with logic
    }

    public boolean hasWon() {
        // Check if a tile has reached the winning value
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                if (tile.getValue() == Constants.WIN_TILE) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                builder.append(tile).append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
