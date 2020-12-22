package com.ovenfoot.adventofcode2020.day22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    public List<Integer> deck;

    public Player() {
        deck = new ArrayList<>();
    }
    public Player(List<Integer> deck) {
        this.deck = deck;
    }

    public Integer drawTopCard() {
        return deck.remove(0);
    }

    public void addCard(Integer card) {
        deck.add(card);
    }

    public void addCards(Integer cardOne, Integer cardTwo) {
        deck.add(cardOne);
        deck.add(cardTwo);
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public String toString() {
        return deck.toString();
    }

    public Integer calculateScore() {
        Collections.reverse(deck);
        Integer score = 0;
        Integer multiplier = 1;
        for (Integer card : deck) {
            score += multiplier * card;
            multiplier++;
        }
        return score;
    }
}
