package com.game2048;

import com.game2048.core.Direction;
import com.game2048.core.GameLogic;
import com.game2048.core.Grid;
import com.game2048.core.Tile;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    /**
     * Test: Vérification du calcul correct du score après un mouvement simple.
     */
    @Test
    public void testScoreCalculation() {
        Grid grid = new Grid(4);
        grid.getGrid()[0][0].setValue(2);
        grid.getGrid()[0][1].setValue(2);
        grid.getGrid()[0][2].setValue(0);
        grid.getGrid()[0][3].setValue(0);

        grid.getGrid()[1][0].setValue(0);
        grid.getGrid()[1][1].setValue(0);
        grid.getGrid()[1][2].setValue(0);
        grid.getGrid()[1][3].setValue(0);

        grid.getGrid()[2][0].setValue(0);
        grid.getGrid()[2][1].setValue(0);
        grid.getGrid()[2][2].setValue(0);
        grid.getGrid()[2][3].setValue(0);

        grid.getGrid()[3][0].setValue(0);
        grid.getGrid()[3][1].setValue(0);
        grid.getGrid()[3][2].setValue(0);
        grid.getGrid()[3][3].setValue(0);

        printGrid(grid);
        int score = grid.move(Direction.LEFT);
        printGrid(grid);

        assertEquals("Le score doit être 4 après une fusion de 2+2", 4, score);
    }

    /**
     * Test: Vérifier qu'un mouvement n'est pas possible si toutes les cases sont pleines et non fusionnables.
     */
    @Test
    public void testNoMovesLeft() {
        Grid grid = new Grid(4);

        // Remplir la grille avec des valeurs non fusionnables
        int[][] nonMovableGrid = {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        };

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                grid.getGrid()[row][col].setValue(nonMovableGrid[row][col]);
            }
        }

        assertFalse("Le jeu devrait détecter qu'il n'y a plus de mouvements possibles", grid.canMove());
    }

    /**
     * Test: Vérification de la victoire si une tuile de valeur 2048 est atteinte.
     */
    @Test
    public void testWinningCondition() {
        Grid grid = new Grid(4);
        grid.getGrid()[0][0].setValue(2048);

        assertTrue("Le jeu devrait détecter la condition de victoire", grid.hasWon());
    }

    /**
     * Test: Vérification que la fusion ne se fait qu'une seule fois par mouvement.
     */
    @Test
    public void testSingleMergePerMove() {
        Grid grid = new Grid(4);

        // Ajouter les valeurs pour tester la fusion
        grid.getGrid()[0][0].setValue(2);
        grid.getGrid()[0][1].setValue(2);
        grid.getGrid()[0][2].setValue(2);
        grid.getGrid()[0][3].setValue(2);

        grid.getGrid()[1][0].setValue(0);
        grid.getGrid()[1][1].setValue(0);
        grid.getGrid()[1][2].setValue(0);
        grid.getGrid()[1][3].setValue(0);

        grid.getGrid()[2][0].setValue(0);
        grid.getGrid()[2][1].setValue(0);
        grid.getGrid()[2][2].setValue(0);
        grid.getGrid()[2][3].setValue(0);

        grid.getGrid()[3][0].setValue(0);
        grid.getGrid()[3][1].setValue(0);
        grid.getGrid()[3][2].setValue(0);
        grid.getGrid()[3][3].setValue(0);


        int score = grid.move(Direction.LEFT);


        // La fusion doit produire [4, 4, 0, 0] et un score de 8
        assertEquals("Le score après un mouvement gauche doit être 8", 8, score);
        assertEquals("La première case doit contenir 4", 4, grid.getGrid()[0][0].getValue());
        assertEquals("La deuxième case doit contenir 4", 4, grid.getGrid()[0][1].getValue());
    }


    /**
     * Test: Vérification qu'une tuile aléatoire est ajoutée après un mouvement valide.
     */
    @Test
    public void testRandomTileAddedAfterMove() {
        Grid grid = new Grid(4);

        // Forcer des valeurs initiales
        grid.getGrid()[0][0].setValue(2);
        grid.getGrid()[0][1].setValue(2);
        grid.getGrid()[0][2].setValue(0);
        grid.getGrid()[0][3].setValue(0);

        grid.getGrid()[1][0].setValue(0);
        grid.getGrid()[1][1].setValue(0);
        grid.getGrid()[1][2].setValue(0);
        grid.getGrid()[1][3].setValue(0);

        grid.getGrid()[2][0].setValue(0);
        grid.getGrid()[2][1].setValue(0);
        grid.getGrid()[2][2].setValue(0);
        grid.getGrid()[2][3].setValue(0);

        grid.getGrid()[3][0].setValue(0);
        grid.getGrid()[3][1].setValue(0);
        grid.getGrid()[3][2].setValue(0);
        grid.getGrid()[3][3].setValue(0);

        // Effectuer un mouvement valide (fusion des deux tuiles)
        grid.move(Direction.LEFT);


        // Compter les tuiles non vides après le mouvement
        int nonEmptyTiles = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (!grid.getGrid()[row][col].isEmpty()) {
                    nonEmptyTiles = nonEmptyTiles + 1;
                }
            }
        }

        // Vérifier qu'il y a exactement 2 tuiles non vides
        assertEquals("Après un mouvement, la grille devrait avoir 2 tuiles non vides", 2, nonEmptyTiles);
    }

        /**
     * Test: Vérification du mouvement vers la droite.
     */
    @Test
    public void testMoveRight() {
        Grid grid = new Grid(4);

        // Initialiser une ligne avec des tuiles pour le test
        grid.getGrid()[0][0].setValue(2);
        grid.getGrid()[0][1].setValue(2);
        grid.getGrid()[0][2].setValue(4);
        grid.getGrid()[0][3].setValue(4);

        grid.getGrid()[1][0].setValue(0);
        grid.getGrid()[1][1].setValue(0);
        grid.getGrid()[1][2].setValue(0);
        grid.getGrid()[1][3].setValue(0);

        grid.getGrid()[2][0].setValue(0);
        grid.getGrid()[2][1].setValue(0);
        grid.getGrid()[2][2].setValue(0);
        grid.getGrid()[2][3].setValue(0);

        grid.getGrid()[3][0].setValue(0);
        grid.getGrid()[3][1].setValue(0);
        grid.getGrid()[3][2].setValue(0);
        grid.getGrid()[3][3].setValue(0);

        // Effectuer un mouvement vers la droite
        printGrid(grid);
        int score = grid.move(Direction.RIGHT);
        printGrid(grid);

        // La fusion doit produire [0, 0, 4, 8] et un score de 12
        assertEquals("Le score après un mouvement droite doit être 12", 12, score);
        assertEquals("La troisième case de la ligne doit contenir 4", 4, grid.getGrid()[0][2].getValue());
        assertEquals("La quatrième case de la ligne doit contenir 8", 8, grid.getGrid()[0][3].getValue());
    }

    /**
     * Test: Vérification du mouvement vers le haut.
     */
    @Test
    public void testMoveUp() {
        Grid grid = new Grid(4);

        // Initialiser une colonne avec des tuiles pour le test
        grid.getGrid()[0][0].setValue(2);
        grid.getGrid()[1][0].setValue(2);
        grid.getGrid()[2][0].setValue(0);
        grid.getGrid()[3][0].setValue(0);

        grid.getGrid()[0][1].setValue(4);
        grid.getGrid()[1][1].setValue(4);
        grid.getGrid()[2][1].setValue(0);
        grid.getGrid()[3][1].setValue(0);

        grid.getGrid()[0][2].setValue(0);
        grid.getGrid()[1][2].setValue(0);
        grid.getGrid()[2][2].setValue(0);
        grid.getGrid()[3][2].setValue(0);

        grid.getGrid()[0][3].setValue(0);
        grid.getGrid()[1][3].setValue(0);
        grid.getGrid()[2][3].setValue(0);
        grid.getGrid()[3][3].setValue(0);

        // Effectuer un mouvement vers le haut
        printGrid(grid);
        int score = grid.move(Direction.UP);
        printGrid(grid);

        // La fusion doit produire [4, 8, 0, 0] dans la première colonne et un score de 12
        assertEquals("Le score après un mouvement haut doit être 12", 12, score);
        assertEquals("La première case de la colonne doit contenir 4", 4, grid.getGrid()[0][0].getValue());
        assertEquals("La deuxième case de la colonne doit contenir 8", 8, grid.getGrid()[0][1].getValue());
    }

    /**
     * Test: Vérification du mouvement vers le bas.
     */
    @Test
    public void testMoveDown() {
        Grid grid = new Grid(4);

        // Initialiser une colonne avec des tuiles pour le test
        grid.getGrid()[0][0].setValue(2);
        grid.getGrid()[1][0].setValue(2);
        grid.getGrid()[2][0].setValue(0);
        grid.getGrid()[3][0].setValue(0);

        grid.getGrid()[0][1].setValue(4);
        grid.getGrid()[1][1].setValue(4);
        grid.getGrid()[2][1].setValue(0);
        grid.getGrid()[3][1].setValue(0);

        grid.getGrid()[0][2].setValue(0);
        grid.getGrid()[1][2].setValue(0);
        grid.getGrid()[2][2].setValue(0);
        grid.getGrid()[3][2].setValue(0);

        grid.getGrid()[0][3].setValue(0);
        grid.getGrid()[1][3].setValue(0);
        grid.getGrid()[2][3].setValue(0);
        grid.getGrid()[3][3].setValue(0);

        // Effectuer un mouvement vers le bas
        printGrid(grid);
        int score = grid.move(Direction.DOWN);
        printGrid(grid);

        // La fusion doit produire [0, 0, 4, 8] dans la première colonne et un score de 12
        assertEquals("Le score après un mouvement bas doit être 12", 12, score);

        assertEquals("La troisième case de la colonne doit contenir 4", 4, grid.getGrid()[3][0].getValue());
        assertEquals("La quatrième case de la colonne doit contenir 8", 8, grid.getGrid()[3][1].getValue());
    }


    /**
     * Test: Vérification qu'un mouvement invalide (aucune tuile déplacée) ne génère pas de nouvelle tuile.
     */
    @Test
    public void testNoRandomTileAfterInvalidMove() {
        Grid grid = new Grid(4);

        // Remplir la première ligne sans possibilité de déplacement
        grid.getGrid()[0][0].setValue(2);
        grid.getGrid()[0][1].setValue(4);
        grid.getGrid()[0][2].setValue(8);
        grid.getGrid()[0][3].setValue(16); 

        grid.getGrid()[1][0].setValue(0);
        grid.getGrid()[1][1].setValue(0);
        grid.getGrid()[1][2].setValue(0);
        grid.getGrid()[1][3].setValue(0);


        grid.getGrid()[2][0].setValue(0);
        grid.getGrid()[2][1].setValue(0);
        grid.getGrid()[2][2].setValue(0);
        grid.getGrid()[2][3].setValue(0);

        grid.getGrid()[3][0].setValue(0);
        grid.getGrid()[3][1].setValue(0);
        grid.getGrid()[3][2].setValue(0);
        grid.getGrid()[3][3].setValue(0);

        System.out.println("État initial de la grille :");
        printGrid(grid);

        // Copier l'état initial de la grille
        Tile[][] initialState = deepCopyGrid(grid.getGrid());

        // Effectuer un mouvement invalide (vers le haut)
        grid.move(Direction.UP);

        System.out.println("État final de la grille après mouvement UP :");
        printGrid(grid);

        // Vérifier que la grille n'a pas changé
        assertTrue("La grille ne doit pas changer après un mouvement invalide",
                gridsAreEqual(initialState, grid.getGrid()));
    }

    private void printGrid(Grid grid) {
        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                System.out.print(grid.getGrid()[row][col].getValue() + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }





    /**
     * Test: Vérification d'un cas spécifique considéré comme un "piège".
     */
    @Test
    public void testTrapScenario() {
        Grid grid = new Grid(4);

        // Initialiser une grille qui nécessite une séquence précise de mouvements
        grid.getGrid()[0][0].setValue(2);
        grid.getGrid()[0][1].setValue(4);
        grid.getGrid()[0][2].setValue(2);
        grid.getGrid()[0][3].setValue(4);

        grid.getGrid()[1][0].setValue(4);
        grid.getGrid()[1][1].setValue(2);
        grid.getGrid()[1][2].setValue(4);
        grid.getGrid()[1][3].setValue(2);

        // Tenter de déplacer dans une direction
        grid.move(Direction.LEFT);

        // Valider que le piège est géré correctement
        assertTrue("Le jeu ne devrait pas être bloqué dans un piège", grid.canMove());
    }


    /**
     * Méthode utilitaire pour copier une grille.
     */
    private Tile[][] deepCopyGrid(Tile[][] originalGrid) {
        int size = originalGrid.length;
        Tile[][] copy = new Tile[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                copy[row][col] = new Tile(originalGrid[row][col].getValue());
            }
        }
        return copy;
    }

    /**
     * Méthode utilitaire pour comparer deux grilles.
     */
    private boolean gridsAreEqual(Tile[][] grid1, Tile[][] grid2) {
        if (grid1.length != grid2.length) return false;

        for (int row = 0; row < grid1.length; row++) {
            for (int col = 0; col < grid1[row].length; col++) {
                if (grid1[row][col].getValue() != grid2[row][col].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    private int countNonEmptyTiles(Grid grid) {
        int count = 0;
        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                if (!grid.getGrid()[row][col].isEmpty()) {
                    count++;
                }
            }
        }
        return count;
    }

}