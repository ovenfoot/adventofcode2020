package com.ovenfoot.adventofcode2020.day20;

import java.util.*;
import java.util.logging.Logger;

public class Day20 {
    static private Logger logger = Logger.getLogger(Day20.class.getName());

    public void runPartOne(List<String> input) {
        List<SingleTile> tileList = SingleTile.parseTileList(input);
        Set<SingleTile> tileSet = new HashSet<>(tileList);
        Map<Edge, Set<SingleTile>> edgeCountsMap = new HashMap<>();

        // match up unique edges and tiles
        for (SingleTile tile : tileList) {
            for (Edge edge : tile.getEdges()) {
                if(!edgeCountsMap.containsKey(edge)) {
                    edgeCountsMap.put(edge, new HashSet<>());
                }
                Set<SingleTile> tilesWithThisEdge = edgeCountsMap.get(edge);
                tilesWithThisEdge.add(tile);
            }
        }

        // Any tile with precisely two unique edges is a corner piece
        Set<SingleTile> cornerTiles = new HashSet<>();
        for (SingleTile tile : tileList) {
            int uniqueEdgeCount = 0;
            for (Edge edge : tile.getEdges()) {
                Set<SingleTile> tilesWithThisEdge = edgeCountsMap.get(edge);
                if (tilesWithThisEdge.size() == 1) {
                    logger.fine(String.format("Tile %d has unique edge %s", tile.getId(), edge));
                    uniqueEdgeCount++;
                }
            }
            if (uniqueEdgeCount == 2) {
                logger.fine(String.format("Tile %d is a corner piece with two unique edges", tile.getId()));
                cornerTiles.add(tile);
            }
        }

    }

    public void runRandomTests() {
        Edge edge1 = new Edge("..##.#..#.");
        Edge edge2 = new Edge(".#..#.##..");

        if (edge1.equals(edge2)) {
            logger.info("Test successful");
        }

        Set<Edge> edgeSet = new HashSet<>();
        edgeSet.add(edge1);
        edgeSet.add(edge2);

        logger.info(String.format("Edge Set %s", edgeSet.toString()));

        if (edgeSet.contains(edge1) && edgeSet.contains(edge2)) {
            logger.info("Test ultimately successful");
        }
    }
}
