package ua.com.foxminded.menu;

import ua.com.foxminded.exception.MainMenuException;

import java.util.Scanner;

public class MainMenuScanner {

    private Scanner scanner = new Scanner(System.in);


    int readInt() {
        this.scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else throw new MainMenuException("You entered a non-integer number");
    }

    long readLong() {
        this.scanner = new Scanner(System.in);
        if (scanner.hasNextLong()) {
            return scanner.nextLong();
        } else throw new MainMenuException("You entered a non-long number");
    }

    String readString() {
        this.scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
