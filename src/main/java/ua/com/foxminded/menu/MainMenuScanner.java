package ua.com.foxminded.menu;

import ua.com.foxminded.exception.MainMenuException;

import java.util.Scanner;

public class MainMenuScanner {

    private Scanner scanner;

    int readInt() {
        startEnter();
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else throw new MainMenuException("You entered a non-integer number");
    }

    long readLong() {
        startEnter();
        if (scanner.hasNextLong()) {
            return scanner.nextLong();
        } else throw new MainMenuException("You entered a non-long number");
    }

    String readString() {
        startEnter();
        return scanner.nextLine();
    }

    private void startEnter() {
        this.scanner = new Scanner(System.in);
    }
}
