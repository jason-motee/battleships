package com.battleships.battleships;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BattleshipGame {

    private BattleshipBoard battleshipBoardOne;
    private BattleshipBoard battleshipBoardTwo;
    private ComputerPlayer computerPlayer;

    public BattleshipGame(BattleshipBoard battleshipBoardOne,
                          BattleshipBoard battleshipBoardTwo,
                          ComputerPlayer computerPlayer) {
        this.battleshipBoardOne = battleshipBoardOne;
        this.battleshipBoardTwo = battleshipBoardTwo;
        this.computerPlayer = computerPlayer;
    }

    public  void startTwoPlayerGame(Scanner playerInput) throws IOException {
        System.out.println("Enter player 1 name...");
        String playerOneName = playerInput.nextLine();
        System.out.println("Enter player 2 name...");
        String playerTwoName = playerInput.nextLine();


        battleshipBoardOne.addShipsToGrid(battleshipBoardOne.getCarrier(),
                battleshipBoardOne.getBattleship(),
                battleshipBoardOne.getCruiser(),
                battleshipBoardOne.getSubmarine(),
                battleshipBoardOne.getDestroyer());
        battleshipBoardOne.hideShips(false);

        battleshipBoardTwo.addShipsToGrid(battleshipBoardTwo.getCarrier(),
                battleshipBoardTwo.getBattleship(),
                battleshipBoardTwo.getCruiser(),
                battleshipBoardTwo.getSubmarine(),
                battleshipBoardTwo.getDestroyer());
        battleshipBoardTwo.hideShips(false);

        BattleshipBoard playerOneGame = battleshipBoardOne;
        BattleshipBoard playerTwoGame = battleshipBoardTwo;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String winnerName;

        while (true) {
            String[] userInputArray = promptUserForInput(playerOneName, playerOneGame, bufferedReader);
            playerOneGame.processInput(playerOneGame, userInputArray);

            if (playerOneGame.allShipsHaveSunk(playerOneGame.getCarrier(),
                    playerOneGame.getBattleship(),
                    playerOneGame.getCruiser(),
                    playerOneGame.getSubmarine(),
                    playerOneGame.getDestroyer())) {
                winnerName = playerOneName;
                break;
            }

            System.out.println("-------------------");
            String[] userInputArrayTwo = promptUserForInput(playerTwoName, playerTwoGame, bufferedReader);
            playerTwoGame.processInput(playerTwoGame, userInputArrayTwo);

            if (playerTwoGame.allShipsHaveSunk(playerTwoGame.getCarrier(),
                    playerTwoGame.getBattleship(),
                    playerTwoGame.getCruiser(),
                    playerTwoGame.getSubmarine(),
                    playerTwoGame.getDestroyer())) {
                winnerName = playerTwoName;
                break;
            }

            System.out.println("-------------------");
        }
        System.out.println(winnerName + " wins!");
    }

    public void startOnePlayerGame(Scanner playerInput) throws IOException {
        System.out.println("Enter player 1 name...");
        String playerOneName = playerInput.nextLine();
        String computerName = "Computer";

        //
        battleshipBoardOne.addShipsToGrid(battleshipBoardOne.getCarrier(),
                battleshipBoardOne.getBattleship(),
                battleshipBoardOne.getCruiser(),
                battleshipBoardOne.getSubmarine(),
                battleshipBoardOne.getDestroyer());

        battleshipBoardTwo.addShipsToGrid(battleshipBoardTwo.getCarrier(),
                battleshipBoardTwo.getBattleship(),
                battleshipBoardTwo.getCruiser(),
                battleshipBoardTwo.getSubmarine(),
                battleshipBoardTwo.getDestroyer());

        BattleshipBoard playerOneGame = battleshipBoardOne;
        BattleshipBoard computerGame = battleshipBoardTwo;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String winnerName;

        //
        while (true) {
            String[] userInputArray = promptUserForInput(playerOneName, playerOneGame, bufferedReader);
            playerOneGame.processInput(playerOneGame, userInputArray);

            if (playerOneGame.allShipsHaveSunk(playerOneGame.getCarrier(),
                    playerOneGame.getBattleship(),
                    playerOneGame.getCruiser(),
                    playerOneGame.getSubmarine(),
                    playerOneGame.getDestroyer())) {
                winnerName = playerOneName;
                break;
            }

            System.out.println("-------------------");
            String[] computerInputArray = promptComputerForInput(computerName, computerGame);
            computerGame.processInput(computerGame, computerInputArray);
            computerPlayer.checkForHitShip(computerGame.getDestroyedType(computerGame, computerInputArray));

            if (computerGame.allShipsHaveSunk(computerGame.getCarrier(),
                    computerGame.getBattleship(),
                    computerGame.getCruiser(),
                    computerGame.getSubmarine(),
                    computerGame.getDestroyer())) {
                winnerName = computerName;
                break;
            }

            System.out.println("-------------------");
        }
        System.out.println(winnerName + " wins!");
    }

    private String[] promptUserForInput(String playerName, BattleshipBoard playerGame, BufferedReader br) throws IOException {
        System.out.println("Ready up " + playerName);
        playerGame.getBattleshipGrid().printGrid();

        String userInput = br.readLine();
        return userInput.toLowerCase().trim().split("");
    }

    private String[] promptComputerForInput(String playerName, BattleshipBoard playerGame) {
        System.out.println("Ready up " + playerName);
        playerGame.getBattleshipGrid().printGrid();

        return computerPlayer.getCoordinate();
    }

}