package com.ovenfoot.adventofcode2020.day21;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {
    static Logger logging = Logger.getLogger(Day21.class.getName());


    public void runPart1(List<String> input) {
        List<Food> allFood = new ArrayList<>();
        Set<String> allIngredients = new HashSet<>();
        Set<String> allAllergens = new HashSet<>();
        for (String line : input) {
            Food food = parseFood(line);
            allAllergens.addAll(food.allergens);
            allIngredients.addAll(food.ingredients);
            allFood.add(food);
        }

        Map<String, Set<String>> allergenToPossibleIngredientsMap = new HashMap<>();

        // Populate map of list of sets
        for (Food food : allFood) {
            for (String allergen : food.allergens) {
                if (!allergenToPossibleIngredientsMap.containsKey(allergen)) {
                    allergenToPossibleIngredientsMap.put(allergen, new HashSet<>(food.ingredients));
                }
                Set<String> possibleIngredients = allergenToPossibleIngredientsMap.get(allergen);
                // do set intersection to find smallest list
                logging.finer(String.format("For allergen %s we are interesecting %s with %s",
                        allergen, possibleIngredients, food.ingredients));
                possibleIngredients.retainAll(food.ingredients);
                allergenToPossibleIngredientsMap.put(allergen, possibleIngredients);
            }
        }
        logging.fine(String.format("After allergen set intersection, we get %s", allergenToPossibleIngredientsMap));

        Set<String> foodWithAllergens = new HashSet<>();
        for (String allergen : allergenToPossibleIngredientsMap.keySet()) {
            Set<String> foodListFromAllergen = allergenToPossibleIngredientsMap.get(allergen);
            foodWithAllergens.addAll(foodListFromAllergen);
        }
        logging.finer(String.format("Ingredients with allergens %s", foodWithAllergens));
        Set<String> ingredientsWithNoAllergens = new HashSet<>(allIngredients);
        ingredientsWithNoAllergens.removeAll(foodWithAllergens);
        logging.finer(String.format("Ingredients with no allergens %s", ingredientsWithNoAllergens));

        // Count Occurences with no allergens
        int counter = 0;
        for (Food food : allFood) {
            Set<String> ingredientsTmp = new HashSet<>(food.ingredients);
            ingredientsTmp.retainAll(ingredientsWithNoAllergens);
            logging.finest(String.format("Food %s has the following without allergens %s", food, ingredientsTmp));
            counter += ingredientsTmp.size();
        }
        logging.info(String.format("Finally non allergent count is %d", counter));

        Map<String, String> definitiveAllergenToIngredientMap = findDefinitivePairs(allergenToPossibleIngredientsMap);
        SortedMap<String, String> sortedAllergenToIngredientMap = new TreeMap<>(definitiveAllergenToIngredientMap);
        String finalString = "";
        for(String allergen: sortedAllergenToIngredientMap.keySet()){
            String ingredient = sortedAllergenToIngredientMap.get(allergen);
            finalString += ingredient + ",";
        }
        logging.info(String.format("Final String %s", finalString));
//        Map<String, Set<String>> ingredientToPossibleAllergensMap = new HashMap<>();
//        for (Food food : allFood) {
//            for (String ingredient : food.ingredients) {
//                if (!ingredientToPossibleAllergensMap.containsKey(ingredient)) {
//                    ingredientToPossibleAllergensMap.put(ingredient, new HashSet<>(food.allergens));
//                }
//                Set<String> possibleIngredients = ingredientToPossibleAllergensMap.get(ingredient);
//                // do set intersection to find smallest list
//                logging.finer(String.format("For allergen %s we are intersecting %s with %s",
//                        ingredient, possibleIngredients, food.allergens));
//                possibleIngredients.addAll(food.allergens);
//                ingredientToPossibleAllergensMap.put(ingredient, possibleIngredients);
//            }
//        }
//
//        logging.fine(String.format("After ingredient set intersection, we get %s", ingredientToPossibleAllergensMap));
//
//        // Iterate through both lists and try to get a solid answer
//        Map<String, String> finalAllergenToIngredientMap = new HashMap<>();
//        for (String allergen: allergenToPossibleIngredientsMap.keySet()) {
//            Set<String> possibleIngredients = allergenToPossibleIngredientsMap.get(allergen);
//            logging.finer(String.format("Allergen %s has possible ingredients %s", allergen, possibleIngredients));
//            for (String ingredient : possibleIngredients) {
//                logging.finer(String.format("Checking Allergen %s to match with Ingredient %s", allergen, ingredient));
//                Set<String> possibleAllergensForIngredient = ingredientToPossibleAllergensMap.get(ingredient);
//                logging.finer(String.format("Ingredient %s has possible allergens %s",
//                        ingredient, possibleAllergensForIngredient));
//                if (possibleAllergensForIngredient.contains(allergen)){
//                    // This is canonically the right ingredient
//                    if (finalAllergenToIngredientMap.containsKey(allergen)) {
//                        logging.info(String.format("ERROR this is bad. Found duplicate for %s with %s",
//                                allergen, ingredient));
//                    }
//                    logging.info(String.format("Found allergen %s that matches ingredient %s", allergen, ingredient));
//                    finalAllergenToIngredientMap.put(allergen ,ingredient);
//                }
//            }
//        }
    }

    public Map<String, String> findDefinitivePairs(Map<String, Set<String>> allergenToPossibleIngredientMapIn) {
        Map<String, Set<String>> allergenToPossibleIngredientMap = new HashMap<>(allergenToPossibleIngredientMapIn);
        Map<String, String> finalAllergenToIngredientMap = new HashMap<>();
        Set<String> accountedForIngredients = new HashSet<>();
        int targetSize = allergenToPossibleIngredientMap.keySet().size();
        logging.info(String.format("Aiming to find %d ingredients", targetSize));
        while (finalAllergenToIngredientMap.size() < targetSize) {
            for (String allergen : allergenToPossibleIngredientMap.keySet()) {
                Set<String> possibleIngredients = allergenToPossibleIngredientMap.get(allergen);
                logging.finest(String.format("Looking at allergen %s with possible ingredients %s", allergen, possibleIngredients));
                possibleIngredients.removeAll(accountedForIngredients);
                if (possibleIngredients.size() == 1) {
                    String onlyIngredient = possibleIngredients.iterator().next();
                    accountedForIngredients.add(onlyIngredient);
                    finalAllergenToIngredientMap.put(allergen, onlyIngredient);
                    logging.fine(String.format("Matched allergen %s with ingredient %s", allergen, onlyIngredient));
                }
            }

            for (String matchedAllergen: finalAllergenToIngredientMap.keySet()) {
                allergenToPossibleIngredientMap.remove(matchedAllergen);
            }
        }
        return finalAllergenToIngredientMap;
    }

    public Food parseFood(String input) {
        // ingredients
        String ingredientsOnlyRaw = input.split(" \\(")[0];
        List<String> ingredients = Arrays.asList(ingredientsOnlyRaw.split(" "));
//        logging.fine(String.format("Food found %s", ingredients));

        // Allergens
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(input);
        String allergensRaw = "";
        while(matcher.find()){
            allergensRaw = matcher.group(1);
            // do something with the word (like putting it in your hashtable)
        }
        // Parse out contains
        allergensRaw = allergensRaw.replace("contains ", "");
        List<String> allergens = Arrays.asList(allergensRaw.split(", "));
//        logging.fine(String.format("Allergens found: %s", allergens));

        Food food = new Food(ingredients, allergens);
        logging.info(String.format("From %s we got Food %s", input, food));
        return food;
    }
}
