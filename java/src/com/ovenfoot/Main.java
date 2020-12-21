package com.ovenfoot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import java.util.stream.Collectors;

public class Main {
    private static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        init();
        String filename = args[0];
        List<String> inputList = convertInputFileToStringList(filename);
        log.fine(String.format("%s", inputList.toString()));
    }

    private static void init() {
        log.setLevel(Level.FINEST);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.FINEST);
        log.addHandler(handler);
        Logger.getAnonymousLogger().addHandler(handler);
    }

    private static List<String> convertInputFileToStringList(String filePath) {
        List<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            list = br.lines().collect(Collectors.toList());
        } catch (Exception e) {
            // Whoopsie doodle
        }

        return list;
    }
}
