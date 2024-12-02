package com.game2048.core;

public class Tile {
    private int value;

    public Tile() {
        this.value = 0; // Empty tile
    }

    public Tile(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String toString() {
        return value == 0 ? "." : String.valueOf(value);
    }
}
