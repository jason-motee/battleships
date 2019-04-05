package com.battleships.battleships;

public class BattleshipBoard {

    private BattleshipGrid battleshipGrid;
    private BattleshipSquare battleshipSquare;
    private Carrier carrier;
    private Battleship battleship;
    private Cruiser cruiser;
    private Submarine submarine;
    private Destroyer destroyer;

    public BattleshipBoard(BattleshipGrid battleshipGrid,
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

        } else if (squareAimedAt.getState().equals("o")) {
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

    public int[] coordinateConverter(String letter, String number) {
        int userColumnCoordinate = letter.charAt(0) - 'a';
        int userRowCoordinate = battleshipGrid.getBoard().length - Integer.parseInt(number);

        return new int[]{userRowCoordinate, userColumnCoordinate};
    }

    public boolean allShipsHaveSunk(Carrier carrier, Battleship battleship, Cruiser cruiser, Submarine submarine, Destroyer destroyer) {
        return carrier.getSpaceCount() == 0
                && battleship.getSpaceCount() == 0
                && cruiser.getSpaceCount() == 0
                && submarine.getSpaceCount() == 0
                && destroyer.getSpaceCount() == 0;
    }

    public void processUserInput(BattleshipBoard playerOneGame, String[] userInputArray) {
        int[] userCoordinates = playerOneGame.coordinateConverter(userInputArray[0], userInputArray[1]);
        playerOneGame.hit(userCoordinates[0], userCoordinates[1]);
        playerOneGame.getBattleshipGrid().printGrid();
    }
}