package com.ovenfoot.adventofcode2020.day23;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class Day23 {
    private static Logger logging = Logger.getLogger(Day23.class.getName());

    public void runPart1(String input, Integer iterations) {
        List<Integer> cups = getCupListFromInput(input);
        Integer maxCupValue = cups.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
        Integer minCupValue = cups.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
        logging.info(String.format("Min cup %d, Max cup %d", minCupValue, maxCupValue));

        Integer nthCup = 0;
        for (int i = 0; i < iterations; i++) {
            logging.info(String.format("-- move %d --", i + 1));
            nthCup = playOneMovePartOne(cups, nthCup, minCupValue, maxCupValue);
        }
        logging.info(String.format("Final: %s", cups));

    }

    public void testPart1() {
        runPart1("389125467", 10);
    }

    public void testPart2() {
        runPart2("389125467", 10000000);
    }

    public void runPart2(String initialInput, Integer iterations) {
        List<Integer> cups = initPart2(initialInput);
        Integer maxCupValue = 1000000;
        Integer minCupValue = 1;

        Integer nthCup = 0;
        for (int i = 0; i < iterations; i++) {
            logging.info(String.format("-- move %d --", i + 1));
            nthCup = playOneMovePartOne(cups, nthCup, minCupValue, maxCupValue);
        }
        logging.info(String.format("Final: %s", cups));
        calculatePart2Score(cups);
    }

    public List<Integer> initPart2(String initialInput) {
        List<Integer> cups = getCupListFromInput(initialInput);
        for (int i = cups.size(); i <= 1000000; i++) {
            cups.add(i);
        }
        return cups;
    }

    public Integer calculatePart2Score(List<Integer> cups) {
        Integer pos = findIndexOfCup(cups, 1);
        Integer clockWiseOne = cups.get((pos + 1) % cups.size());
        Integer clockWiseTwo = cups.get((pos + 2) % cups.size());
        Integer result = clockWiseOne * clockWiseTwo;
        logging.info(String.format("The two cups clockwise of 1 are %d and %d, multiplying to give a result of %d",
                clockWiseOne, clockWiseTwo, result));
        return result;
    }

    public Integer playOneMovePartOne(List<Integer> cups, Integer nthMove,
                                            Integer minCupValue, Integer maxCupValue) {
//        logging.info(String.format("cups: %s", cups));
        Integer currentCup = cups.get(nthMove);
        logging.info(String.format("current cup: %d", currentCup));
        List<Integer> cupsInHand = placeCupsInHand(cups, nthMove);
        logging.info(String.format("pick up: %s", cupsInHand));
        Integer destinationCup = getDestinationCup(cups, cupsInHand, currentCup, maxCupValue, minCupValue);
        logging.info(String.format("destination: %d", destinationCup));
        cups = placeCupsFromHand(cups, cupsInHand, destinationCup);
//        logging.info(String.format("result: %s", cups));
        return getIndexOfNextCup(cups, currentCup);
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

    public static Integer getDestinationCup(List<Integer> allCups, List<Integer> cupsInHand, Integer currentCup,
                                            Integer maxCup, Integer minCup) {
        Integer destinationCup = currentCup - 1;
        if (destinationCup < minCup) {
            destinationCup = maxCup;
        }
        while (cupsInHand.contains(destinationCup) || destinationCup.equals(currentCup)) {
            destinationCup--;
            if (destinationCup < minCup) {
                destinationCup = maxCup;
            }
        }
        return destinationCup;
    }

    public static List<Integer> getCupListFromInput(String input) {
        List<Integer> cupLabelsOut = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            String character = Character.toString(input.charAt(i));
            cupLabelsOut.add(Integer.parseInt(character));
        }
        logging.info(String.format("From %s got %s", input, cupLabelsOut));
        return cupLabelsOut;
    }
}
