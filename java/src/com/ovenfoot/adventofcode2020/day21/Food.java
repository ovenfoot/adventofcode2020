package com.ovenfoot.adventofcode2020.day21;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Food {
    Set<String> ingredients;
    Set<String> allergens;

    public Food(Set<String> ingredients, Set<String> allergens) {
        this.ingredients = ingredients;
        this.allergens = allergens;
    }

    public Food(List<String> ingredients, List<String> allergens) {
        this.ingredients = new HashSet<>(ingredients);
        this.allergens = new HashSet<>(allergens);
    }

    public String toString() {
        return String.format("{\"ingredients\": %s\", \"allergens\": %s}",
                this.ingredients, this.allergens);
    }
}
