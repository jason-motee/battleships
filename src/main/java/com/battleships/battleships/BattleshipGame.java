package com.battleships.battleships;

import org.springframework.beans.factory.annotation.Autowired;

public class BattleshipGame {

    private BattleshipGrid battleshipGrid;

    @Autowired
    private BattleshipSquare battleshipSquare;

    @Autowired
    private Carrier carrier;

    @Autowired
    private Battleship battleship;

    @Autowired
    private Cruiser cruiser;

    @Autowired
    private Submarine submarine;

    @Autowired
    private Destroyer destroyer;

    public BattleshipGame(BattleshipGrid battleshipGrid) {
        this.battleshipGrid = battleshipGrid;
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
}

