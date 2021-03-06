package ru.otus.spring01.service;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;

@Component
public class IOStreamFactory {

    public InputStream getInputStream() {
        return System.in;
    }

    public PrintStream getPrintStream() {
        return System.out;
    }
}
