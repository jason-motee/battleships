package com.battleships.battleships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

@Service
public class BattleshipGame {

    private final BattleshipGrid battleshipGrid;
    private final BattleshipSquare battleshipSquare;
    private final Carrier carrier;
    private final Battleship battleship;
    private final Cruiser cruiser;
    private final Submarine submarine;
    private final Destroyer destroyer;

    @Autowired
    public BattleshipGame(BattleshipGrid battleshipGrid,
                          BattleshipSquare battleshipSquare,
                          Carrier carrier,
                          Battleship battleship,
                          Cruiser cruiser,
                          Submarine submarine,
                          Destroyer destroyer) {
        this.battleshipGrid = battleshipGrid;
        this.battleshipSquare = battleshipSquare;
        this.carrier = carrier;
        this.battleship = battleship;
        this.cruiser = cruiser;
        this.submarine = submarine;
        this.destroyer = destroyer;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public Battleship getBattleship() {
        return battleship;
    }

    public Cruiser getCruiser() {
        return cruiser;
    }

    public Submarine getSubmarine() {
        return submarine;
    }

    public Destroyer getDestroyer() {
        return destroyer;
    }

    public BattleshipGrid getBattleshipGrid() {
        return battleshipGrid;
    }

    public void addShipsToGrid(BattleshipSquare... ships) {
        battleshipGrid.initialiseGrid();

        for (BattleshipSquare ship : ships) {
            battleshipGrid.insertBattleShipIntoRandomPosition(ship);
        }

    }

    public void hit(int row, int column) {
        BattleshipSquare squareAimedAt = battleshipGrid.getSquare(row, column);
        String squareAimedAtType = squareAimedAt.getType();

        if (squareAimedAtType.equals(carrier.getType())) {
            changePositionToAHitState(row, column, carrier);

        } else if (squareAimedAtType.equals(battleship.getType())) {
            changePositionToAHitState(row, column, battleship);

        } else if (squareAimedAtType.equals(cruiser.getType())) {
            changePositionToAHitState(row, column, cruiser);

        } else if (squareAimedAtType.equals(submarine.getType())) {
            changePositionToAHitState(row, column, submarine);

        } else if (squareAimedAtType.equals(destroyer.getType())) {
            changePositionToAHitState(row, column, destroyer);

        } else if (squareAimedAt.getState().equals("*")) {
            System.out.println("This has already been hit!");

        } else if (squareAimedAt.getState().equals("X")) {
            System.out.println("This ship has already been destroyed!");

        } else if(squareAimedAt.getState().equals("o")) {
            System.out.println("You've already tried this coordinate!");

        } else if (squareAimedAtType.equals(battleshipSquare.getType())) {
            System.out.println("Target missed!");
            squareAimedAt.setState("o");

        }
    }

    private void changePositionToAHitState(int row, int column, BattleshipSquare ship) {
        System.out.println(ship.getType() + " hit!");
        battleshipGrid.getSquare(row, column).minusOneSpaceCount();
        battleshipGrid.setSquare(new BattleshipSquare(), row, column);
        battleshipGrid.getSquare(row, column).setState("*");
        battleshipGrid.getSquare(row, column).setDestroyedType(ship.getType());
        checkForDestroyedShips(ship);
    }

    private void checkForDestroyedShips(BattleshipSquare ship) {
        if (ship.getSpaceCount() == 0) {
            for (int row = 0; row < battleshipGrid.getBoard().length; row++) {
                for (int column = 0; column < battleshipGrid.getBoard().length; column++) {
                    BattleshipSquare battleshipSquare = battleshipGrid.getSquare(row, column);

                    if (battleshipSquare.getDestroyedType().equals(ship.getType())) {
                        battleshipSquare.setState("X");
                    }
                }
            }
            System.out.println("You sunk a " + ship.getType() + "!");
        }
    }

    private int[] coordinateConverter(String letter, String number) {
        int userColumnCoordinate = letter.charAt(0) - 'a';
        int userRowCoordinate = battleshipGrid.getBoard().length - Integer.parseInt(number);

        return new int[]{userRowCoordinate, userColumnCoordinate};
    }

    private boolean shipsAreStillSailing(Carrier carrier, Battleship battleship, Cruiser cruiser, Submarine submarine, Destroyer destroyer) {
        return carrier.getSpaceCount() != 0
                || battleship.getSpaceCount() != 0
                || cruiser.getSpaceCount() != 0
                || submarine.getSpaceCount() != 0
                || destroyer.getSpaceCount() != 0;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Battleships");
        System.out.println("Enter 1p or 2p...");

        Scanner playerInput = new Scanner(System.in);
        String numberOfPlayers = playerInput.nextLine();

        if (numberOfPlayers.equals("1p")) {
            Carrier carrier = new Carrier();
            Battleship battleship = new Battleship();
            Cruiser cruiser = new Cruiser();
            Submarine submarine = new Submarine();
            Destroyer destroyer = new Destroyer();

            BattleshipGrid battleshipGrid = new BattleshipGrid();
            BattleshipSquare battleshipSquare = new BattleshipSquare();
            BattleshipGame battleshipGame = new BattleshipGame(battleshipGrid,
                    battleshipSquare,
                    carrier,
                    battleship,
                    cruiser,
                    submarine,
                    destroyer);
            battleshipGame.addShipsToGrid(carrier, battleship, cruiser, submarine, destroyer);

            while (battleshipGame.shipsAreStillSailing(carrier, battleship, cruiser, submarine, destroyer)) {
                System.out.println("Ready player One");
                battleshipGame.getBattleshipGrid().printGrid();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String userInput = br.readLine();
                String[] userInputArray = userInput.toLowerCase().trim().split("");
                int[] userCoordinates = battleshipGame.coordinateConverter(userInputArray[0], userInputArray[1]);
                battleshipGame.hit(userCoordinates[0], userCoordinates[1]);
                System.out.println("-------------------");
            }
            System.out.println("You Win!");

        } else if (numberOfPlayers.equals("2p")) {
            System.out.println("Enter player 1 name...");
            String playerOneName = playerInput.nextLine();
            System.out.println("Enter player 2 name...");
            String playerTwoName = playerInput.nextLine();

            BattleshipGrid battleshipGridOne = new BattleshipGrid();
            BattleshipSquare battleshipSquareOne = new BattleshipSquare();
            Carrier carrierOne = new Carrier();
            Battleship battleshipOne = new Battleship();
            Cruiser cruiserOne = new Cruiser();
            Submarine submarineOne = new Submarine();
            Destroyer destroyerOne = new Destroyer();

            BattleshipGrid battleshipGridTwo = new BattleshipGrid();
            BattleshipSquare battleshipSquareTwo = new BattleshipSquare();
            Carrier carrierTwo = new Carrier();
            Battleship battleshipTwo = new Battleship();
            Cruiser cruiserTwo = new Cruiser();
            Submarine submarineTwo = new Submarine();
            Destroyer destroyerTwo = new Destroyer();

            BattleshipGame battleshipGameOne = new BattleshipGame(battleshipGridOne, battleshipSquareOne, carrierOne, battleshipOne, cruiserOne, submarineOne, destroyerOne);
            BattleshipGame battleshipGameTwo = new BattleshipGame(battleshipGridTwo, battleshipSquareTwo, carrierTwo, battleshipTwo, cruiserTwo, submarineTwo, destroyerTwo);
            TwoPlayerMode twoPlayerMode = new TwoPlayerMode(battleshipGameOne, battleshipGameTwo);

            BattleshipGame playerOneGame = twoPlayerMode.createGameOne();
            BattleshipGame playerTwoGame = twoPlayerMode.createGameTwo();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String winnerName = "";

            while (true) {
                System.out.println("Ready up " + playerOneName);
                playerOneGame.getBattleshipGrid().printGrid();

                String userInput = br.readLine();
                String[] userInputArray = userInput.toLowerCase().trim().split("");

                playerOneGame.processUserInput(playerOneGame, userInputArray);

                if (!playerOneGame.shipsAreStillSailing(playerOneGame.getCarrier(),
                        playerOneGame.getBattleship(),
                        playerOneGame.getCruiser(),
                        playerOneGame.getSubmarine(),
                        playerOneGame.getDestroyer())) {
                    winnerName = playerOneName;
                    break;
                }

                System.out.println("----------------------------------------------------------------------------- ");

                System.out.println(" ");
                System.out.println("Ready up " + playerTwoName);
                playerTwoGame.getBattleshipGrid().printGrid();

                String userInputTwo = br.readLine();
                String[] userInputArrayTwo = userInputTwo.toLowerCase().trim().split("");

                playerTwoGame.processUserInput(playerTwoGame, userInputArrayTwo);

                if (!playerTwoGame.shipsAreStillSailing(playerTwoGame.getCarrier(),
                        playerTwoGame.getBattleship(),
                        playerTwoGame.getCruiser(),
                        playerTwoGame.getSubmarine(),
                        playerTwoGame.getDestroyer())) {
                    winnerName = playerTwoName;
                    break;
                }

                System.out.println("----------------------------------------------------------------------------- ");
            }
            System.out.println(winnerName + " wins!");
        }
    }

    private void processUserInput(BattleshipGame playerOneGame, String[] userInputArray) {
        int[] userCoordinates = playerOneGame.coordinateConverter(userInputArray[0], userInputArray[1]);
        playerOneGame.hit(userCoordinates[0], userCoordinates[1]);
        playerOneGame.getBattleshipGrid().printGrid();
    }
}