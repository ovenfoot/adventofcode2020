package com.ovenfoot.adventofcode2020.day22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    public List<Integer> deck;
    public String id;

    public Player(String id) {
        this.deck = new ArrayList<>();
        this.id = id;
    }

    private Player(String id, List<Integer> deck) {
        this.deck = deck;
        this.id = id;
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

    public Integer getDeckSize() {
        return deck.size();
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

    public String getId() {
        return id;
    }

    public List<Integer> getDeck() {
        return deck;
    }

    public Player copy() {
        List<Integer> newDeck = new ArrayList<>();
        for (Integer card : this.getDeck()) {
            // Dirty deserialisation hack
            Integer newCard = Integer.parseInt(card.toString());
            newDeck.add(newCard);
        }
        String newId = this.getId();

        return new Player(newId, newDeck);
    }
}
