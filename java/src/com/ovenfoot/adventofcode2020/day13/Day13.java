package com.ovenfoot.adventofcode2020.day13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Day13 {
    Logger logger = Logger.getLogger(Day13.class.getName());

    public void run(List<String> input) {
        logger.fine(String.format("running based on input %s", input));
        Integer startingTimestamp = Integer.parseInt(input.get(0));
        logger.info(String.format("Earliest timestamp is %d", startingTimestamp));
        List<Integer> busNumbers = getBusNumbersFromRaw(input.get(1));
        logger.info(String.format("Got bus numbers %s", busNumbers));
    }

    public void runTestCase() {
        List<String> testInput = Arrays.asList("939", "7,13,x,x,59,x,31,19");
        run(testInput);
    }

    public static List<Integer> getBusNumbersFromRaw(String rawBusNumbers) {
        List<String> rawSplit = Arrays.asList(rawBusNumbers.split(","));
        List<Integer> busList = new ArrayList<>();

        for (String rawBusNumber : rawSplit) {
            if (!rawBusNumber.equals("x")) {
                busList.add(Integer.parseInt(rawBusNumber));
            }
        }

        return busList;
    }
}
