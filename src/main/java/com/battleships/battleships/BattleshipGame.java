package com.battleships.battleships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public BattleshipGrid getBattleshipGrid() {
        return battleshipGrid;
    }

    public void addShipsToGrid(BattleshipSquare... ships){
        battleshipGrid.initialiseGrid();

        for (BattleshipSquare ship:ships) {
            battleshipGrid.insertBattleShipIntoRandomPosition(ship);
        }
    }

    public void hit(int row, int column) {
        BattleshipSquare squareAimedAt = battleshipGrid.getBoard()[row][column];
        String squareAimedAtType = squareAimedAt.getType();
        if (squareAimedAtType.equals(carrier.getType())){
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
        } else if (squareAimedAtType.equals(battleshipSquare.getType())) {
            System.out.println("Target missed!");
            squareAimedAt.setState("o");
        }
    }

    private void changePositionToAHitState(int row, int column, BattleshipSquare ship) {
        System.out.println(ship.getType() + " hit!");
        battleshipGrid.getBoard()[row][column].minusOneSpaceCount();
        battleshipGrid.getBoard()[row][column] = new BattleshipSquare();
        battleshipGrid.getBoard()[row][column].setState("*");
        battleshipGrid.getBoard()[row][column].setDestroyedType(ship.getType());
        checkForDestroyedShips(ship);
    }

    private void checkForDestroyedShips(BattleshipSquare ship) {
        if (ship.getSpaceCount() == 0) {
            for (int row = 0; row < battleshipGrid.getBoard().length; row++) {
                for (int column = 0; column < battleshipGrid.getBoard().length; column++) {
                    BattleshipSquare battleshipSquare = battleshipGrid.getBoard()[row][column];

                    if (battleshipSquare.getDestroyedType().equals(ship.getType())) {
                        battleshipSquare.setState("X");
                    }
                }
            }
            System.out.println("You sunk a " + ship.getType() + "!");
        }
    }

    public int[] coordinateConverter(String letter, String number) {
        char[] letterToCharArray = letter.toCharArray();
        char letterToChar = letterToCharArray[0];

        int userColumnCoordinate = letterToChar - 'a';
        int userRowCoordinate = battleshipGrid.getBoard().length - Integer.parseInt(number);

        return new int[]{userRowCoordinate, userColumnCoordinate};
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Battleships");
        System.out.println(" ");

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
        battleshipGame.getBattleshipGrid().printGrid();

        while (shipsAreStillSailing(carrier, battleship, cruiser, submarine, destroyer)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String userInput = br.readLine();
            String[] userInputArray = userInput.toLowerCase().trim().split("\\s+");
            String[] methodInputArray = {"", ""};

            System.arraycopy(userInputArray, 0, methodInputArray, 0, userInputArray.length);

            int[] userCoordinates = battleshipGame.coordinateConverter(methodInputArray[0], methodInputArray[1]);

            battleshipGame.hit(userCoordinates[0], userCoordinates[1]);
            battleshipGame.getBattleshipGrid().printGrid();
        }

        System.out.println("You win!!");

    }

    private static boolean shipsAreStillSailing(Carrier carrier, Battleship battleship, Cruiser cruiser, Submarine submarine, Destroyer destroyer) {
        return carrier.getSpaceCount() != 0
                || battleship.getSpaceCount() != 0
                || cruiser.getSpaceCount() != 0
                || submarine.getSpaceCount() != 0
                || destroyer.getSpaceCount() != 0;
    }
}

