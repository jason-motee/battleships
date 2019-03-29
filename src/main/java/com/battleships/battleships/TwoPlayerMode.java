package com.battleships.battleships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwoPlayerMode {
    private BattleshipGame battleshipGameOne;
    private BattleshipGame battleshipGameTwo;

    @Autowired
    public TwoPlayerMode(BattleshipGame battleshipGameOne, BattleshipGame battleshipGameTwo) {
        this.battleshipGameOne = battleshipGameOne;
        this.battleshipGameTwo = battleshipGameTwo;
    }

    public BattleshipGame getBattleshipGameOne() {
        return battleshipGameOne;
    }


    public BattleshipGame createGameOne() {
        battleshipGameOne.addShipsToGrid(battleshipGameOne.getCarrier(),
                battleshipGameOne.getBattleship(),
                battleshipGameOne.getCruiser(),
                battleshipGameOne.getSubmarine(),
                battleshipGameOne.getDestroyer());
        return battleshipGameOne;
    }

    public BattleshipGame createGameTwo() {
        battleshipGameTwo.addShipsToGrid(battleshipGameTwo.getCarrier(),
                battleshipGameTwo.getBattleship(),
                battleshipGameTwo.getCruiser(),
                battleshipGameTwo.getSubmarine(),
                battleshipGameTwo.getDestroyer());
        return battleshipGameTwo;
    }
}
