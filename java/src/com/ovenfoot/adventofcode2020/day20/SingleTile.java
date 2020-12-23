package com.ovenfoot.adventofcode2020.day20;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleTile {
    // Represent edges as bits
    // Compare edges as backwards and forwards bits
    // How to store rotation and edges?
    // Store exposed edges?
    // Tiles at the edges won't line up with any other tiles
    // fucking lol.
    private static Logger logger = Logger.getLogger(SingleTile.class.getName());
    private static int EDGE_SIZE = 10;
    private Integer id;
    private List<Edge> edges;

    public SingleTile(int id, List<Edge> edges) {
        this.id = id;
        this.edges = edges;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public int getId() {
        return id;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public static List<SingleTile> parseTileList(List<String> input) {
        List<SingleTile> tiles = new ArrayList<>();
        for (int i = 0; i < input.size(); i+= EDGE_SIZE + 2) {
            String line = input.get(i);
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(line);
            Integer id = 0;
            while (m.find()) {
                id = Integer.parseInt(m.group(0));
            }

            logger.finer(String.format("Found tile %d", id));

            List<Edge> edgeList = new ArrayList<>();
            String topEdge = input.get(i + 1);
            String bottomEdge = input.get(i + EDGE_SIZE);
            String left = "";
            String right = "";
            for (int j = 1; j <= EDGE_SIZE; j++) {
                line = input.get(i + j);
                left += line.substring(0, 1);
                right += line.substring(EDGE_SIZE - 1, EDGE_SIZE);
            }

            edgeList.add(new Edge(topEdge));
            edgeList.add(new Edge(bottomEdge));
            edgeList.add(new Edge(left));
            edgeList.add(new Edge(right));

            SingleTile newTile = new SingleTile(id, edgeList);
            logger.info(String.format("Parsed tile %s", newTile));
            tiles.add(newTile);
        }
        return tiles;
    }

    @Override
    public String toString() {
        String outputString = "Tile " + id.toString() + ":\n";
        for (Edge edge : edges) {
            outputString += edge.toString() + "\n";
        }
        return outputString;
    }
}
