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

    public Player copy(Integer numCards) {
        List<Integer> newDeck = new ArrayList<>();
        for (int i = 0; i < numCards; i++) {
            // Dirty deserialisation hack
            Integer newCard = Integer.parseInt(deck.get(i).toString());
            newDeck.add(newCard);
        }
        String newId = this.getId();

        return new Player(newId, newDeck);
    }

    public boolean hasTheSameDeck(Player other) {
        if (other.getDeckSize() != this.getDeckSize()) {
            return false;
        }

        for (int i = 0; i < this.getDeckSize(); i++) {
            if (this.getDeck().get(i) != other.getDeck().get(i)) {
                return false;
            }
        }

        return true;
    }


}
