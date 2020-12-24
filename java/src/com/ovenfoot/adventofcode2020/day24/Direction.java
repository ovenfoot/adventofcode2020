package com.ovenfoot.adventofcode2020.day24;

enum Direction {
    SOUTH_WEST,
    WEST,
    NORTH_WEST,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    INVALID;

    public static Direction stringToDirection(String input) {
        switch (input) {
            case "sw":
                return SOUTH_WEST;
            case "w":
                return WEST;
            case "nw":
                return NORTH_WEST;
            case "ne":
                return NORTH_EAST;
            case "e":
                return EAST;
            case "se":
                return SOUTH_EAST;
        }
        return INVALID;
    }

    @Override
    public String toString() {
        switch(this) {
            case SOUTH_WEST:
                return "sw";
            case WEST:
                return "w";
            case NORTH_WEST:
                return "nw";
            case NORTH_EAST:
                return "ne";
            case EAST:
                return "e";
            case SOUTH_EAST:
                return "se";
        }
        return "invalid";
    }
}


