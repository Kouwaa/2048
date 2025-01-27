package com.game2048.core;

import java.util.Random;
import com.game2048.util.Constants;

public class Grid {
    private final Tile[][] grid;
    private final int size; // Taille dynamique de la grille
    private final Random random;

    public Grid(int size) {
        this.size = size;
        this.grid = new Tile[size][size];
        this.random = new Random();

        // Initialiser la grille avec des tuiles vides
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Tile();
            }
        }

        // Ajouter deux tuiles aléatoires au début
        addRandomTile();
        addRandomTile();
    }


    public Tile[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }

    /**
     * Effectue une copie profonde de la grille actuelle.
     */
    private Tile[][] copyGrid() {
        Tile[][] copy = new Tile[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                copy[row][col] = new Tile(grid[row][col].getValue()); // Deep copy of the grid
            }
        }
        return copy;
    }


    /**
     * Vérifie si deux grilles sont identiques.
     */
    private boolean isGridEqual(Tile[][] otherGrid) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col].getValue() != otherGrid[row][col].getValue()) {
                    return false; // La grille a changé
                }
            }
        }
        return true; // La grille n'a pas changé
    }


    /**
     * Effectue un mouvement dans une direction donnée et retourne le score obtenu.
     */
    public int move(Direction direction) {
        // Sauvegarder l'état initial de la grille avant le mouvement
        Tile[][] initialGridState = copyGrid();

        int points = 0;

        // Appeler la méthode de mouvement appropriée
        switch (direction) {
            case UP -> points = moveUp();
            case DOWN -> points = moveDown();
            case LEFT -> points = moveLeft();
            case RIGHT -> points = moveRight();
        }

        // Ajouter une tuile aléatoire uniquement si l'état de la grille a changé
        if (!isGridEqual(initialGridState)) {
            addRandomTile();
        }

        return points; // Retourner le score obtenu lors de ce mouvement
}

    /**
     * Effectue un mouvement vers le haut.
     */
    private int moveUp() {
        int points = 0; // Points accumulés pour ce mouvement

        for (int col = 0; col < size; col++) {
            int[] mergedRow = new int[size]; // Suivre les cellules fusionnées pour éviter les doubles fusions
            for (int row = 1; row < size; row++) {
                if (!grid[row][col].isEmpty()) {
                    int currentRow = row;
                    while (currentRow > 0 && grid[currentRow - 1][col].isEmpty()) {
                        // Déplacer la tuile vers le haut
                        grid[currentRow - 1][col].setValue(grid[currentRow][col].getValue());
                        grid[currentRow][col].setValue(0);
                        currentRow--;
                    }

                    // Vérifier si une fusion est possible avec la tuile au-dessus
                    if (currentRow > 0 && grid[currentRow - 1][col].getValue() == grid[currentRow][col].getValue()
                            && mergedRow[currentRow - 1] == 0) {
                        grid[currentRow - 1][col].setValue(grid[currentRow - 1][col].getValue() * 2);
                        points += grid[currentRow - 1][col].getValue(); // Add points for the merge
                        grid[currentRow][col].setValue(0);
                        mergedRow[currentRow - 1] = 1; // Mark this row as merged
                    }
                }
            }
        }

        return points;
    }





    private int moveDown() {
        int points = 0; // Accumulate points for this move

        for (int col = 0; col < size; col++) {
            int[] mergedRow = new int[size]; // Track merged cells to avoid double merges
            for (int row = size - 2; row >= 0; row--) {
                if (!grid[row][col].isEmpty()) {
                    int currentRow = row;
                    while (currentRow < size - 1 && grid[currentRow + 1][col].isEmpty()) {
                        // Move tile downward
                        grid[currentRow + 1][col].setValue(grid[currentRow][col].getValue());
                        grid[currentRow][col].setValue(0);
                        currentRow++;
                    }

                    // Check if we can merge with the tile below
                    if (currentRow < size - 1 && grid[currentRow + 1][col].getValue() == grid[currentRow][col].getValue()
                            && mergedRow[currentRow + 1] == 0) {
                        grid[currentRow + 1][col].setValue(grid[currentRow + 1][col].getValue() * 2);
                        points += grid[currentRow + 1][col].getValue(); // Add points for the merge
                        grid[currentRow][col].setValue(0);
                        mergedRow[currentRow + 1] = 1; // Mark this row as merged
                    }
                }
            }
        }

        return points;
    }




    private int moveLeft() {
        int points = 0; // Accumulate points for this move

        for (int row = 0; row < size; row++) {
            int[] mergedCol = new int[size]; // Track merged cells to avoid double merges
            for (int col = 1; col < size; col++) {
                if (!grid[row][col].isEmpty()) {
                    int currentCol = col;
                    while (currentCol > 0 && grid[row][currentCol - 1].isEmpty()) {
                        // Move tile to the left
                        grid[row][currentCol - 1].setValue(grid[row][currentCol].getValue());
                        grid[row][currentCol].setValue(0);
                        currentCol--;
                    }

                    // Check if we can merge with the tile to the left
                    if (currentCol > 0 && grid[row][currentCol - 1].getValue() == grid[row][currentCol].getValue()
                            && mergedCol[currentCol - 1] == 0) {
                        grid[row][currentCol - 1].setValue(grid[row][currentCol - 1].getValue() * 2);
                        points += grid[row][currentCol - 1].getValue(); // Add points for the merge
                        grid[row][currentCol].setValue(0);
                        mergedCol[currentCol - 1] = 1; // Mark this column as merged
                    }
                }
            }
        }
        return points;
    }





    private int moveRight() {
        int points = 0; // Accumulate points for this move

        for (int row = 0; row < size; row++) {
            int[] mergedCol = new int[size]; // Track merged cells to avoid double merges
            for (int col = size - 2; col >= 0; col--) {
                if (!grid[row][col].isEmpty()) {
                    int currentCol = col;
                    while (currentCol < size - 1 && grid[row][currentCol + 1].isEmpty()) {
                        // Move tile to the right
                        grid[row][currentCol + 1].setValue(grid[row][currentCol].getValue());
                        grid[row][currentCol].setValue(0);
                        currentCol++;
                    }

                    // Check if we can merge with the tile to the right
                    if (currentCol < size - 1 && grid[row][currentCol + 1].getValue() == grid[row][currentCol].getValue()
                            && mergedCol[currentCol + 1] == 0) {
                        grid[row][currentCol + 1].setValue(grid[row][currentCol + 1].getValue() * 2);
                        points += grid[row][currentCol + 1].getValue(); // Add points for the merge
                        grid[row][currentCol].setValue(0);
                        mergedCol[currentCol + 1] = 1; // Mark this column as merged
                    }
                }
            }
        }

        return points;
    }



    /**
     * Ajoute une tuile aléatoire dans une position vide.
     */
    private void addRandomTile() {
        int x, y;
        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (!grid[x][y].isEmpty());

        // 90% de chance d'ajouter une tuile de valeur 2, 10% une tuile de valeur 4
        grid[x][y].setValue(random.nextInt(10) < 9 ? 2 : 4);
    }


    /**
     * Vérifie si des mouvements sont possibles.
     */
    public boolean canMove() {
    // Iterate through the entire grid
    for (int row = 0; row < size; row++) {
        for (int col = 0; col < size; col++) {
            Tile current = grid[row][col];

            // Vérifier si la case est vide
            if (current.isEmpty()) {
                return true;
            }

            // Vérifier si une fusion est possible avec une case voisine            
            if (row > 0 && current.getValue() == grid[row - 1][col].getValue()) { // Up
                return true;
            }

            if (row < size - 1 && current.getValue() == grid[row + 1][col].getValue()) { // Down
                return true;
            }

            if (col > 0 && current.getValue() == grid[row][col - 1].getValue()) { // Left
                return true;
            }

            if (col < size - 1 && current.getValue() == grid[row][col + 1].getValue()) { // Right
                return true;
            }
        }
    }

    // Aucun mouvement possible
    return false;
    }


    /**
     * Vérifie si une tuile a atteint la valeur gagnante.
     */
    public boolean hasWon() {
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
