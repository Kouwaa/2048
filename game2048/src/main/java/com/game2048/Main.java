package com.game2048;

import java.util.Scanner;
import com.game2048.core.GameLogic;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the grid size (default is 4): ");
        int size;
        try {
            size = Integer.parseInt(scanner.nextLine());
            if (size < 2) {
                System.out.println("Grid size must be at least 2. Using default size 4.");
                size = 4;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using default size 4.");
            size = 4;
        }

        new GameLogic(size);
    }
}
