package com.ovenfoot.adventofcode2020.day24;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Day24 {
    static private Logger logger = Logger.getLogger(Day24.class.getName());

    public void testRunPart1() {
        List<Direction> directionList = getDirectionsFromInputLine("sesenwnenenewseeswwswswwnenewsewsw");
    }
    public static List<Direction> getDirectionsFromInputLine(String input) {
        List<Direction> directionList = new ArrayList<>();
        for (int i = 0; i< input.length(); i++) {
            String directionRaw = input.substring(i, i+1);
            if (directionRaw.equals("s") || directionRaw.equals("n")) {
                // s and n never appear by themselves, only as part of nw or sw etc.
                directionRaw = input.substring(i, i+2);
                i++;
            }
            logger.finest(String.format("Raw direction string is %s", directionRaw));
            Direction direction = Direction.stringToDirection(directionRaw);
            directionList.add(direction);
        }
        logger.info(String.format("From %s parsed %s", input, directionList));
        return directionList;
    }
}
