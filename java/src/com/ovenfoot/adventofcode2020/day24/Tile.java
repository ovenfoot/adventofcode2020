package com.ovenfoot.adventofcode2020.day24;

import java.util.HashMap;
import java.util.Map;

public class Tile {
    private Map<Direction, Tile> adjacentTiles;
    private String colour = "white";

    public Tile() {
        this.adjacentTiles = new HashMap<>();
    }

    public Tile getAdjacentTile(Direction direction) {
        if (!adjacentTiles.containsKey(direction)) {
            Tile newTile = new Tile();
            this.placeAdjacentTile(direction, newTile);
            newTile.placeAdjacentTile(direction.getReverseDirection(), this);
        }
        return adjacentTiles.get(direction);
    }

    public void flipTile() {
        if (colour.equals("white")) {
            colour = "black";
        } else if (colour.equals("black")) {
            colour = "white";
        }
    }

    public boolean isBlack() {
        return colour.equals("black");
    }

    public void placeAdjacentTile(Direction direction, Tile tile) {
        adjacentTiles.put(direction, tile);
    }
}
