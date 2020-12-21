package com.ovenfoot.adventofcode2020.day13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Day13 {
    Logger logger = Logger.getLogger(Day13.class.getName());

    public void runPartOne(List<String> input) {
        logger.fine(String.format("running based on input %s", input));
        Integer startingTimestamp = Integer.parseInt(input.get(0));
        logger.info(String.format("Earliest timestamp is %d", startingTimestamp));
        List<Integer> busNumbers = getBusNumbersFromRaw(input.get(1));
        logger.info(String.format("Got bus numbers %s", busNumbers));

        BusIdTimeStampPair nextBus = new BusIdTimeStampPair(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (Integer busNumber : busNumbers) {
            BusIdTimeStampPair nextBusForId = getNextBus(startingTimestamp, busNumber);
            if (nextBusForId.timestamp < nextBus.timestamp) {
                nextBus = nextBusForId;
            }
        }
        logger.info(String.format("Next bus is bus %d arriving at %d", nextBus.busId, nextBus.timestamp));

        Integer result = (nextBus.timestamp - startingTimestamp) * nextBus.busId;
        logger.info(String.format("Result is %d", result));
    }

    public void runTestCase() {
        List<String> testInput = Arrays.asList("939", "7,13,x,x,59,x,31,19");
        runPartOne(testInput);
    }

    public BusIdTimeStampPair getNextBus(Integer currentTimestamp, Integer busId) {
        int leftover = currentTimestamp % busId;
        int nextTimestamp = currentTimestamp + (busId - leftover);
        return new BusIdTimeStampPair(busId, nextTimestamp);
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

    public class BusIdTimeStampPair {
        public Integer busId;
        public Integer timestamp;

        public BusIdTimeStampPair(int busIdIn, int timestampIn) {
            busId = busIdIn;
            timestamp = timestampIn;
        }
    }
}
