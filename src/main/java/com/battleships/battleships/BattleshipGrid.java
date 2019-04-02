package com.battleships.battleships;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BattleshipGrid {

    private BattleshipSquare[][] board = new BattleshipSquare[9][9];

    public void initialiseGrid() {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                setSquare(new BattleshipSquare(), row, column);
            }
        }
    }

    public void insertBattleShipIntoRandomPosition(BattleshipSquare battleship) {
        Random random = new Random();
        int randomNumber = random.nextInt(2);

        if (randomNumber == 0) {
            insertBattleshipIntoRandomRowPosition(battleship);
        } else {
            insertBattleshipIntoRandomColumnPosition(battleship);
        }
    }

    public void insertBattleshipIntoRandomRowPosition(BattleshipSquare battleship) {
        Random random = new Random();
        int size = battleship.getSpace();
        int row = random.nextInt(board.length);
        int column = random.nextInt((board.length - size + 1));

        int checkCount = 0;

        for (int i = 0; i < size; i++) {
            if(getSquare(row, column + i).getType().equals("Square")){
                checkCount += 1;
            }
        }

        if (checkCount == size) {
            for (int i = 0; i < size; i++) {
                setSquare(battleship, row, column + i);
            }
        } else {
            this.insertBattleshipIntoRandomRowPosition(battleship);
        }
    }

    public void insertBattleshipIntoRandomColumnPosition(BattleshipSquare battleship) {
        Random random = new Random();
        int size = battleship.getSpace();
        int row = random.nextInt((board.length - size + 1));
        int column = random.nextInt((board.length));

        int checkCount = 0;

        for (int i = 0; i < size; i++) {
            if (getSquare(row + i, column).getType().equals("Square")) {
                checkCount += 1;
            }
        }

        if (checkCount == size) {
            for (int i = 0; i < size; i++) {
                setSquare(battleship, row + i, column);
            }
        } else {
            this.insertBattleshipIntoRandomColumnPosition(battleship);
        }
    }

    public void printGrid() {
        for (int row = 0; row < board.length; row++) {
            System.out.print(board.length - row + " ");
            for (int column = 0; column < board.length; column++) {
                System.out.print(getSquare(row, column).getState() + " ");
            }
            System.out.println("");
        }
        System.out.println("  A B C D E F G H I");
    }

    public BattleshipSquare[][] getBoard() {
        return board;
    }

    public BattleshipSquare getSquare(int row, int column) {
        return board[row][column];
    }

    public void setSquare(BattleshipSquare battleshipSquare, int row, int column) {
        board[row][column] = battleshipSquare;
    }
}
