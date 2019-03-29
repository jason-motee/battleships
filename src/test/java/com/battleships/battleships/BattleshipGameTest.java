package com.battleships.battleships;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BattleshipGridConfig.class)
public class BattleshipGameTest {

    @Autowired
    private Carrier carrier;

    @Autowired
    private Battleship battleship;

    @Autowired
    private Cruiser cruiser;

    @Autowired
    private Destroyer destroyer;

    @Autowired
    private Submarine submarine;

    @Autowired
    private BattleshipGame battleshipGame;

    @Before
    public void setUp() {
        battleshipGame.addShipsToGrid(carrier, battleship, cruiser, destroyer, submarine);
    }

    @Test
    public void addShipsToGame() {
        battleshipGame.addShipsToGrid(carrier, battleship, cruiser, destroyer, submarine);
        battleshipGame.getBattleshipGrid().printGrid();
    }

    @Test
    public void hitShip() {
        battleshipGame.hit(0,0);
        battleshipGame.getBattleshipGrid().printGrid();

        battleshipGame.hit(0,1);
        battleshipGame.hit(0,2);
        battleshipGame.hit(0,3);
        battleshipGame.hit(0,4);
        battleshipGame.hit(0,5);
        battleshipGame.hit(0,6);
        battleshipGame.hit(0,7);
        battleshipGame.hit(0,8);

        battleshipGame.getBattleshipGrid().printGrid();
    }
}