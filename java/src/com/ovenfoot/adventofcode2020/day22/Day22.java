package com.ovenfoot.adventofcode2020.day22;

import java.util.List;
import java.util.logging.Logger;

public class Day22 {
    private Player playerOne = new Player();
    private Player playerTwo = new Player();
    private static Logger logger = Logger.getLogger(Day22.class.getName());

    public void runPart1(List<String> input) {
        initialisePlayers(input);
        for (int roundNumber = 1; !playerOne.isEmpty() && !playerTwo.isEmpty(); roundNumber++) {
            logger.info(String.format("-- Round %d --", roundNumber));
            playPartOneRound();
        }

        Player winningPlayer = playerOne.isEmpty() ? playerTwo : playerOne;
        logger.info(String.format("Score is %d", winningPlayer.calculateScore()));
    }

    public void playPartOneRound() {
        logger.info(String.format("Player 1's deck: %s", playerOne));
        logger.info(String.format("Player 2's deck: %s", playerTwo));

        Integer playerOneCard = playerOne.drawTopCard();
        Integer playerTwoCard = playerTwo.drawTopCard();
        logger.info(String.format("Player 1 plays %d, Player 2 plays %d", playerOneCard, playerTwoCard));

        if (playerOneCard > playerTwoCard) {
            logger.info("Player 1 wins the round!");
            playerOne.addCards(playerOneCard, playerTwoCard);
        } else if (playerTwoCard > playerOneCard) {
            logger.info("Player 2 wins the round!");
            playerTwo.addCards(playerTwoCard, playerOneCard);
        }

    }

    private void initialisePlayers(List<String> input) {
        // Maintain State
        String currentPlayer = "Nada";

        for (String inputLine : input) {
            if (inputLine.equals("Player 1:")) {
                currentPlayer = inputLine;
                logger.fine("Found Player 1 cards");
            } else if (inputLine.equals("Player 2:")) {
                logger.fine("Found Player 2 cards");
                currentPlayer = inputLine;
            } else if (!inputLine.isEmpty()) {
                if (currentPlayer.equals("Player 1:")) {
                    Integer card = Integer.parseInt(inputLine);
                    logger.fine(String.format("Adding %d to Player 1 deck", card));
                    playerOne.addCard(card);
                } else if (currentPlayer.equals("Player 2:")) {
                    Integer card = Integer.parseInt(inputLine);
                    logger.fine(String.format("Adding %d to Player 2 deck", card));
                    playerTwo.addCard(card);
                }
            }
        }

        logger.info(String.format("Initialised cards. {playerOne: %s, playerTwo: %s}", playerOne, playerTwo));
    }
}
