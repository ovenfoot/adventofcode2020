package com.ovenfoot.adventofcode2020.day23;

import java.util.*;
import java.util.logging.Logger;

public class Day23 {
    private static Logger logging = Logger.getLogger(Day23.class.getName());
    private Map<Integer, Cup> allCupMap = new HashMap<>();

    public void runPart1(String input, Integer iterations) {
        Map<Integer, Cup> cupMap = createCupMapFromInput(input);
        Integer firstCupValue = getFirstCupValue(input);
        Cup firstCup = cupMap.get(firstCupValue);
        Integer maxCupValue = cupMap.keySet().stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
        Integer minCupValue = cupMap.keySet().stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
        logging.info(String.format("Min cup %d, Max cup %d", minCupValue, maxCupValue));

        Cup currentCup = firstCup;
        for (int i = 0; i < iterations; i++) {
            logging.info(String.format("-- move %d --", i + 1));
            if (i == 19) {
                logging.info("break here");
            }
            currentCup = playOneMovePartOne(cupMap, currentCup, minCupValue, maxCupValue);
        }
        logging.info(String.format("Final: %s", cupsInOrderToString(cupMap.get(1))));

    }

    public static String cupsInOrderToString(Cup firstCup) {
        Set<Integer> visitedCups = new HashSet<>();
        String returnString = "[";
        int i = 0;
        for (Cup currentCup = firstCup; i < 25 ; currentCup = currentCup.clockWise) {
            if (visitedCups.contains(currentCup.getValue())) {
                break;
            }
            returnString += currentCup.getValue().toString() + ", ";
            visitedCups.add(currentCup.getValue());
            i++;
        }
        // Remove last ", "
        returnString = returnString.substring(0, returnString.length() - 2);
        returnString += "]";
        return returnString;
    }

    public void testPart1() {
        runPart1("853192647", 100);
    }

    public void testPart2() {
        runPart2("853192647", 10000000);
    }

    public void runPart2(String initialInput, Integer iterations) {
        Map<Integer, Cup> cups = initPart2(initialInput);
        logging.info(String.format("Initialised cups: %s", cupsInOrderToString(cups.get(999990))));
        Integer maxCupValue = 1000000;
        Integer minCupValue = 1;
        Integer firstCupVal = getFirstCupValue(initialInput);
        Cup currentCup = cups.get(firstCupVal);

        for (int i = 0; i < iterations; i++) {
            logging.info(String.format("-- move %d --", i + 1));
            currentCup = playOneMovePartOne(cups, currentCup, minCupValue, maxCupValue);
        }
        logging.info(String.format("Final: %s", cupsInOrderToString(cups.get(1))));
        calculatePart2Score(cups);
    }

    public Map<Integer, Cup> initPart2(String initialInput) {
        Map<Integer, Cup> cups = createCupMapFromInput(initialInput);
        Integer firstCupValue = getFirstCupValue(initialInput);
        Cup firstCup = cups.get(firstCupValue);
        Cup lastCup = firstCup.counterClockwise;

        Cup prevCup = lastCup;
        for (int i = cups.size() + 1; i <= 1000000; i++) {
            Cup currentCup = new Cup(i);
            prevCup.clockWise = currentCup;
            currentCup.counterClockwise = prevCup;
            cups.put(i, currentCup);
            prevCup = currentCup;
        }

        prevCup.clockWise = firstCup;
        firstCup.counterClockwise = prevCup;
        logging.fine(String.format("Finished adding 1 million. Last three cw %d %d %d",
                lastCup.getValue(), lastCup.clockWise.getValue(), lastCup.clockWise.clockWise.getValue()));
        logging.fine(String.format("Finished adding 1 million. First three ccw %d %d %d",
                firstCup.getValue(), firstCup.counterClockwise.getValue(),
                firstCup.counterClockwise.counterClockwise.getValue()));
        return cups;
    }

    public Long calculatePart2Score(Map<Integer, Cup> cups) {
        Cup cupOne = cups.get(1);

        Long clockWiseOne = new Long(cupOne.clockWise.getValue());
        Long clockWiseTwo = new Long (cupOne.clockWise.clockWise.getValue());
        Long result = clockWiseOne * clockWiseTwo;
        logging.info(String.format("The two cups clockwise of 1 are %d and %d, multiplying to give a result of %d",
                clockWiseOne, clockWiseTwo, result));
        return result;
    }

    public Cup playOneMovePartOne(Map<Integer, Cup> cups, Cup currentCup,
                                            Integer minCupValue, Integer maxCupValue) {
        logging.info(String.format("current cup: %s", currentCup));
        List<Cup> cupsInHand = currentCup.removeFromClockwise(1, 3);
        logging.info(String.format("pick up: %s", cupsInHand));
        Integer destinationCupValue = getDestinationCupValue(cupsInHand, currentCup, maxCupValue, minCupValue);
        Cup destinationCup = cups.get(destinationCupValue);
        logging.info(String.format("destination: %s", destinationCup));
        logging.info(String.format("current state: %s", cupsInOrderToString(currentCup)));
        destinationCup.insertClockwise(cupsInHand);
        logging.info(String.format("result: %s", cupsInOrderToString(currentCup)));
        return currentCup.clockWise;
    }

    public Integer getIndexOfNextCup(List<Integer> cups, Integer currentCup) {
        return (findIndexOfCup(cups, currentCup) + 1) % cups.size();
    }

    public static int findIndexOfCup(List<Integer> cups, Integer cupToFind) {
        boolean found = false;
        int index = 0;
        for(int i = 0; !found; i++) {
            if (i >= cups.size()) {
                logging.info(String.format("Couldnt find %d. Previous was %d", cupToFind, cups.get(i)));
                logging.info("we have a problem");
            }
            if (cups.get(i).equals(cupToFind)) {
                found = true;
                index = i;
            }
        }
        return index;
    }
    public static List<Integer> placeCupsFromHand(List<Integer> cupsNotInHand, List<Integer> cupsInHand,
                                                  Integer destination) {

        int i = findIndexOfCup(cupsNotInHand, destination);
        for (int j = 0; j < cupsInHand.size(); j++) {
            int position = (i + 1 + j) % (cupsInHand.size() + cupsNotInHand.size());
            cupsNotInHand.add(position, cupsInHand.get(j));
        }
        return cupsNotInHand;
    }

    public static List<Integer> placeCupsInHand(List<Integer> cups, Integer nthMove) {
        List<Integer> cupsInHand = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if ((nthMove + 1) < cups.size()) {
                cupsInHand.add(cups.remove(nthMove + 1));
            } else {
                cupsInHand.add(cups.remove(0));
            }
        }
        return cupsInHand;
    }

    public static Integer getDestinationCupValue(List<Cup> cupsInHand, Cup currentCup,
                                            Integer maxCup, Integer minCup) {
        Integer destinationCupValue = currentCup.getValue() - 1;
        if (destinationCupValue < minCup) {
            destinationCupValue = maxCup;
        }

        List<Integer> cupsInHandValues = new ArrayList<>();
        for (Cup cup : cupsInHand) {
            cupsInHandValues.add(cup.getValue());
        }

        while (cupsInHandValues.contains(destinationCupValue) ||
                destinationCupValue.equals(currentCup.getValue())) {
            destinationCupValue--;
            if (destinationCupValue < minCup) {
                destinationCupValue = maxCup;
            }
        }
        return destinationCupValue;
    }

    public static Integer getFirstCupValue(String input) {
        String characterString = Character.toString(input.charAt(0));
        return Integer.parseInt(characterString);
    }

    public static Map<Integer, Cup> createCupMapFromInput(String input) {
        Map<Integer, Cup> cupMapOut = new HashMap<>();
        Cup currentCup = null;
        Cup firstCup = null;
        for (int i = 0; i < input.length(); i++) {
            String character = Character.toString(input.charAt(i));
            Integer value = Integer.parseInt(character);
            Cup newCup = new Cup(value);
            if (currentCup != null) {
                currentCup.clockWise = newCup;
            }
            newCup.counterClockwise = currentCup;
            currentCup = newCup;
            cupMapOut.put(value, newCup);
            if (i == 0) {
                // Save the first cup
                firstCup = newCup;
            }
        }

        // Finish the circle
        currentCup.clockWise = firstCup;
        firstCup.counterClockwise = currentCup;
        logging.info(String.format("From %s got %s", input, cupMapOut));
        logging.info(String.format("In order we got %s",cupsInOrderToString(firstCup)));
        return cupMapOut;
    }
}
