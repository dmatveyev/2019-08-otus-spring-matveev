package ru.otus.spring01.service;

import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Scanner;

@Component
public class SystemIOService implements IOService {

    private PrintStream out;
    private Scanner scanner;

    public SystemIOService(IOStreamFactory ioStreamFactory) {
        this.out = ioStreamFactory.getPrintStream();
        this.scanner = new Scanner(ioStreamFactory.getInputStream());
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
