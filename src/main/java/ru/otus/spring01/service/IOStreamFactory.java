package ru.otus.spring01.service;

import java.io.InputStream;
import java.io.PrintStream;

public class IOStreamFactory {

    public InputStream getInputStream() {
        return System.in;
    }

    public PrintStream getPrintStream() {
        return System.out;
    }
}
