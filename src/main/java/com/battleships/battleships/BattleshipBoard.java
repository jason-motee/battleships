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

    public void applyCoordinates(int row, int column) {
        BattleshipSquare squareAimedAt = battleshipGrid.getSquare(row, column);
        battleshipGrid.setSquare(squareAimedAt.hit(), row, column);
        checkForDestroyedShips(squareAimedAt);
    }

    private void checkForDestroyedShips(BattleshipSquare ship) {
        if (ship.getSpaceCount() == 0 && !ship.getType().equals("Empty Square")) {
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

    public void processUserInput(BattleshipBoard game, String[] userInputArray) {
        int[] userCoordinates = game.coordinateConverter(userInputArray[0], userInputArray[1]);
        game.applyCoordinates(userCoordinates[0], userCoordinates[1]);
        game.getBattleshipGrid().printGrid();
    }

    private int[] coordinateConverter(String letter, String number) {
        int userColumnCoordinate = letter.charAt(0) - 'a';
        int userRowCoordinate = battleshipGrid.getBoard().length - Integer.parseInt(number);

        return new int[] {userRowCoordinate, userColumnCoordinate};
    }

    public boolean allShipsHaveSunk(Carrier carrier, Battleship battleship, Cruiser cruiser, Submarine submarine, Destroyer destroyer) {
        return carrier.getSpaceCount() == 0
                && battleship.getSpaceCount() == 0
                && cruiser.getSpaceCount() == 0
                && submarine.getSpaceCount() == 0
                && destroyer.getSpaceCount() == 0;
    }

    public void hideShips(boolean isHidden) {
        if(isHidden) {
            carrier.hideState();
            battleship.hideState();
            cruiser.hideState();
            submarine.hideState();
            destroyer.hideState();
        }
    }
}