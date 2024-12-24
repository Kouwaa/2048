package com.game2048;

import java.util.Scanner;
import com.game2048.core.GameLogic;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size;
        while (true) {
            System.out.print("Enter the grid size (between 4 and 8): ");
            try {
                size = Integer.parseInt(scanner.nextLine());
                if (size >= 4 && size <= 8) {
                    break;
                } else {
                    System.out.println("Invalid grid size. Please enter a size between 4 and 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 4 and 8.");
            }
        }

        new GameLogic(size);
    }
}


