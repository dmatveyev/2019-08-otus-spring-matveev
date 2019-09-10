package ru.otus.spring01.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class SystemIOService implements IOService {

    private PrintStream out;
    private Scanner scanner;

    public SystemIOService(PrintStream out, InputStream in) {
        this.out = out;
        this.scanner = new Scanner(in);
    }

    @Override
    public void printString(String string) {
        out.println(string);
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public int readInt() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            out.println("Please choose correct answer!");
        }
        String answer = scanner.nextLine();
        return Integer.parseInt(answer);
    }
}
