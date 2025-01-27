package com.game2048.core;


public class Tile {
    private int value; // Valeur de la tuile (0 représente une tuile vide)

    /**
     * Constructeur par défaut.
     * Initialise une tuile vide avec une valeur de 0.
     */
    public Tile() {
        this.value = 0; // Tuile vide
    }

    /**
     * Constructeur avec une valeur spécifiée.
     * @param value La valeur initiale de la tuile.
     */
    public Tile(int value) {
        this.value = value;
    }

    /**
     * Retourne la valeur actuelle de la tuile.
     * @return La valeur de la tuile.
     */
    public int getValue() {
        return value;
    }

    /**
     * Met à jour la valeur de la tuile.
     * @param value La nouvelle valeur de la tuile.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Vérifie si la tuile est vide.
     * @return `true` si la valeur de la tuile est 0, sinon `false`.
     */
    public boolean isEmpty() {
        return value == 0;
    }

    /**
     * Retourne une représentation en chaîne de la tuile.
     * Une tuile vide est représentée par ".", sinon par sa valeur.
     * @return Une chaîne représentant la tuile.
     */
    @Override
    public String toString() {
        return value == 0 ? "." : String.valueOf(value);
    }
}
