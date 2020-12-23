package com.ovenfoot.adventofcode2020.day20;

import com.ovenfoot.adventofcode2020.day23.Cup;

import java.util.Set;

public class Edge {
    private String rawValue;

    public Edge(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getValue() {
        return rawValue;
    }

    public String getReverseValue() {
        return getReverseString(rawValue);
    }

    public Edge getReverseEdge() {
        return new Edge(getReverseString(rawValue));
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Edge) {
            Edge otherEdge = (Edge) other;
            // They are equivalent if they are equal in either direction
            if (otherEdge.getValue().equals(this.rawValue)) {
                return true;
            }  else {
                if (otherEdge.getReverseValue().equals(this.rawValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return rawValue;
    }

    @Override
    public int hashCode() {
        return rawValue.hashCode() + getReverseValue().hashCode();
    }

    public static Set<Edge> insertIntoSet(Set<Edge> edgeSet, Edge edgeIn){
        if (!edgeSet.contains(edgeIn.getReverseEdge())) {
            // reverse is the equivalent, so only add if the reverse isnt in
            edgeSet.add(edgeIn);
        }
        return edgeSet;
    }

    public static boolean setContains(Set<Edge> edgeSet, Edge edgeIn) {
        return edgeSet.contains(edgeIn) || edgeSet.contains(edgeIn.getReverseEdge());
    }

    public static String getReverseString(String input) {
        StringBuilder reverseStringBuilder = new StringBuilder(input);
        return reverseStringBuilder.reverse().toString();
    }
}
