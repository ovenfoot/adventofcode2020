package com.ovenfoot.adventofcode2020.day22;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Day22 {
    private Player playerOne = new Player("1");
    private Player playerTwo = new Player("2");
    private static Logger logger = Logger.getLogger(Day22.class.getName());
    private Integer partTwoGameNumber = 0;

    public void runPart1(List<String> input) {
        initialisePlayers(input);
        for (int roundNumber = 1; !playerOne.isEmpty() && !playerTwo.isEmpty(); roundNumber++) {
            logger.info(String.format("-- Round %d --", roundNumber));
            playPartOneRound();
        }

        Player winningPlayer = playerOne.isEmpty() ? playerTwo : playerOne;
        logger.info(String.format("Score is %d", winningPlayer.calculateScore()));
    }

    public void runPart2(List<String> input) {
        initialisePlayers(input);
        Player winner = playPartTwoGame(this.playerOne, this.playerTwo, partTwoGameNumber);
        logger.info("== Post-game results ==");
        logger.info(String.format("Player 1's deck: %s", playerOne));
        logger.info(String.format("Player 2's deck: %s", playerTwo));

        logger.info(String.format("Winner: %s. Score %d", winner.getId(), winner.calculateScore()));
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

    public Player playPartTwoGame(Player playerOneIn, Player playerTwoIn, Integer previousPartTwoGameNumber) {
        GameMemory gameMemory = new GameMemory();
        Integer thisGameNumber = ++partTwoGameNumber;
        logger.info(String.format("=== Game %d ===", thisGameNumber));
        // TODO: Check for infini-looping winnning condition
        for (int roundNumber = 1; !playerOneIn.isEmpty() && !playerTwoIn.isEmpty(); roundNumber++) {
            if (gameMemory.stateExists(playerOneIn, playerTwoIn)) {
                logger.info("Infinite loop detected, player one wins");
                return playerOneIn;
            }
            gameMemory.addMemory(playerOneIn, playerTwoIn);
            logger.info(String.format("-- Round %d (Game %d)--", roundNumber, thisGameNumber));
            Player roundWinner = playPartTwoRound(playerOneIn, playerTwoIn, thisGameNumber);
            logger.info(String.format("Player %s win rounds %d of game %d",
                    roundWinner.getId(), roundNumber, thisGameNumber));
        }
        Player winningPlayer = playerOneIn.isEmpty() ? playerTwoIn : playerOneIn;
        logger.info(String.format("The winner of game %d is player %s!", thisGameNumber, winningPlayer.getId()));
        logger.info(String.format("Anyway back to game %d", previousPartTwoGameNumber));
        return winningPlayer;
    }

    public Player playPartTwoRound(Player playerOneIn, Player playerTwoIn, Integer gameNumber) {
        logger.info(String.format("Player 1's deck: %s", playerOneIn));
        logger.info(String.format("Player 2's deck: %s", playerTwoIn));

        Integer playerOneCard = playerOneIn.drawTopCard();
        Integer playerTwoCard = playerTwoIn.drawTopCard();
        Player winner = null;
        logger.info(String.format("Player 1 plays %d, Player 2 plays %d", playerOneCard, playerTwoCard));
        if (checkSubGameCondition(playerOneIn, playerOneCard, playerTwoIn, playerTwoCard)) {
            winner = playPartTwoGame(playerOneIn.copy(), playerTwoIn.copy(), gameNumber);

        } else {
            if (playerOneCard > playerTwoCard) {
                winner = playerOneIn;
            } else if (playerTwoCard > playerOneCard) {
                winner = playerTwoIn;
            }
        }

        if (winner.getId() == playerOneIn.getId()) {
            playerOneIn.addCards(playerOneCard, playerTwoCard);
            winner = playerOneIn; // This is messy because of the deep copy implications
        } else if (winner.getId() == playerTwoIn.getId()) {
            playerTwoIn.addCards(playerTwoCard, playerOneCard);
            winner = playerTwoIn; // This is messy beacuse of the deep copy implications
        }
        return winner;
    }

    private boolean checkSubGameCondition(Player playerOne, Integer playerOneCard,
                                          Player playerTwo, Integer playerTwoCard) {
        if (playerOne.getDeckSize() >= playerOneCard &&
                playerTwo.getDeckSize() >= playerTwoCard) {
            return true;
        }
        return false;
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

    private class GameMemory {
        Set<String> previousStates;

        public GameMemory() {
            this.previousStates = new HashSet<>();
        }

        public void addMemory(Player playerOneIn, Player playerTwoIn) {
            previousStates.add(getStringRepresentation(playerOneIn, playerTwoIn));
        }

        public boolean stateExists(Player playerOneIn, Player playerTwoIn){
            return previousStates.contains(getStringRepresentation(playerOneIn, playerTwoIn));
        }

        private String getStringRepresentation(Player playerOne, Player playerTwo) {
            return playerOne.toString() + playerTwo.toString();
        }
    }
}
