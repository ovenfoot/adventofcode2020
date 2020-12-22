package com.ovenfoot;

import com.ovenfoot.adventofcode2020.day13.Day13;
import com.ovenfoot.adventofcode2020.day22.Day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import java.util.stream.Collectors;

public class Main {
    private static Logger log = Logger.getLogger(Main.class.getName());
    private static Level globalLogLevel = Level.FINEST;
    public static void main(String[] args) {
        init();
        String filename = args[0];
        List<String> inputList = convertInputFileToStringList(filename);
        log.fine(String.format("%s", inputList.toString()));

//        Day13 day13instance = new Day13();
////        day13instance.runPartOne(inputList);
//        day13instance.runTestCase();
////        day13instance.runPartTwo(inputList.get(1));

        Day22 day22Instance = new Day22();
        day22Instance.runPart1(inputList);
    }

    private static void init() {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        log.addHandler(handler);
        Logger.getAnonymousLogger().addHandler(handler);

        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(globalLogLevel);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(globalLogLevel);
        }
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
