package com.ovenfoot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import java.util.stream.Collectors;

public class Main {
    private static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        init();
        String filename = args[0];
        List<String> inputList = convertInputFileToStringList(filename);
//        System.out.println(inputList);
        log.info(String.format("%s", inputList.toString()));
    }

    private static void init() {
        log.setLevel(Level.ALL);
        // Unknown why this logs to stderr as well
        // log.addHandler(new StreamHandler(System.out, new SimpleFormatter()));
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
