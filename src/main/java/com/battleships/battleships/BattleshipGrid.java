package com.battleships.battleships;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BattleshipGrid {

    private BattleshipSquare[][] board = new BattleshipSquare[9][9];

    public void initialiseGrid() {
        for (int vertical = 0; vertical < board.length; vertical++) {
            for (int horizontal = 0; horizontal < board.length; horizontal++) {
               board[vertical][horizontal] = new BattleshipSquare();
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

        //check row
        for (int i = 0; i < size; i++) {
            if(board[row][column + i].getState().equals("?")){
                checkCount += 1;
            }
        }

        if (checkCount == size) {
            for (int i = 0; i < size; i++) {
                board[row][column + i] = battleship;
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

        //check column
        for (int i = 0; i < size; i++) {
            if (board[row + i][column].getState().equals("?")) {
                checkCount += 1;
            }
        }

        if (checkCount == size) {
            for (int i = 0; i < size; i++) {
                board[row + i][column] = battleship;
            }
        } else {
            this.insertBattleshipIntoRandomColumnPosition(battleship);
        }
    }

    public void printGrid() {
        for (int i = 0; i < board.length; i++) {
            System.out.print(board.length - i + " ");
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j].getState() + " ");
            }
            System.out.println("");
        }
        System.out.println("  A B C D E F G H I");
    }

    public BattleshipSquare[][] getBoard() {
        return board;
    }

}
