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
        for (int i = 0; i < iterations; i++) {
            logging.info(String.format("-- move %d --", i + 1));
            cups = playOneMovePartOne(cups, minCupValue, maxCupValue);
        }

    }

    public void testPart1() {
        runPart1("389125467", 1);
    }

    public List<Integer> playOneMovePartOne(List<Integer> cups, Integer minCupValue, Integer maxCupValue) {
        logging.info(String.format("cups: %s", cups));
        Integer currentCup = cups.get(0);
        logging.info(String.format("current cup: %d", currentCup));
        List<Integer> cupsInHand = placeCupsInHand(cups);
        logging.info(String.format("pick up: %s", cupsInHand));
        Integer destinationCup = getDestinationCup(cups, cupsInHand, currentCup, maxCupValue, minCupValue);
        logging.info(String.format("destination: %d", destinationCup));
        cups = placeCupsFromHand(cups, cupsInHand, destinationCup);
        logging.info(String.format("result: %s", cups));
        return cups;
    }

    public static List<Integer> placeCupsFromHand(List<Integer> cupsNotInHand, List<Integer> cupsInHand,
                                                  Integer destination) {

        boolean placed = false;
        for (int i = 0; !placed; i++) {
            if (cupsNotInHand.get(i) == destination) {
                cupsNotInHand.addAll(i + 1, cupsInHand);
                placed = true;
            }
        }
        return cupsNotInHand;
    }

    public static List<Integer> placeCupsInHand(List<Integer> cups) {
        List<Integer> cupsInHand = new ArrayList<>();
        cupsInHand.add(cups.remove(1));
        cupsInHand.add(cups.remove(1));
        cupsInHand.add(cups.remove(1));
        return cupsInHand;
    }

    public static Integer getDestinationCup(List<Integer> allCups, List<Integer> cupsInHand, Integer currentCup,
                                            Integer maxCup, Integer minCup) {
        Integer destinationCup = currentCup - 1;
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
