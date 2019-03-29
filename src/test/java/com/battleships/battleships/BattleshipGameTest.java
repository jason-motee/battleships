package com.battleships.battleships;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BattleshipGridConfig.class)
public class BattleshipGameTest {

    @Autowired
    private BattleshipSquare battleshipSquare;

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

    }

    @Test
    public void addShipsToGameAndCheckTheGrid() {
        battleshipGame.addShipsToGrid(carrier, battleship, cruiser, destroyer, submarine);
        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipGame.getBattleshipGrid().getBoard())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        assertThat(battleshipGridAsList).contains(carrier, battleship, cruiser, destroyer, submarine);
    }

    @Test
    public void givenAnEmptySpaceIsHit_ThenTheGridContainsMissedValue () {
        battleshipGame.addShipsToGrid();
        battleshipGame.hit(0,0);

        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipGame.getBattleshipGrid().getBoard())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        assertThat(battleshipGridAsList.get(0).getState()).isEqualTo("o");
    }

    @Test
    public void givenASpaceContainingCarrierIsHit_ThenTheGridContainsAHitValue() {
        battleshipGame.addShipsToGrid(carrier);

        int row = 0;
        int column = 0;

        Outer: for (int i = 0; i < battleshipGame.getBattleshipGrid().getBoard().length; i++) {
            for (int j = 0; j < battleshipGame.getBattleshipGrid().getBoard().length; j++) {
                if (battleshipGame.getBattleshipGrid().getSquare(i, j).getType().equals(carrier.getType())) {
                    battleshipGame.hit(i, j);
                    row = i;
                    column = j;
                    break Outer;
                }
            }
        }

        assertThat(battleshipGame.getBattleshipGrid().getBoard()[row][column].getState()).isEqualTo("*");
    }

    @Test
    public void givenAllSpacesContainingBattleshipIsHit_ThenTheGridContainsFourDestroyedValues() {
        battleshipGame.addShipsToGrid(battleship);

        for (int row = 0; row < battleshipGame.getBattleshipGrid().getBoard().length; row++) {
            for (int column = 0; column < battleshipGame.getBattleshipGrid().getBoard().length; column++) {
                if (battleshipGame.getBattleshipGrid().getSquare(row, column).getType().equals(battleship.getType())) {
                    battleshipGame.hit(row, column);
                }
            }
        }

        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipGame.getBattleshipGrid().getBoard())
                .flatMap(Arrays::stream)
                .filter(square -> square.getState().equals("X"))
                .collect(Collectors.toList());

        assertThat(battleshipGridAsList.size()).isEqualTo(4);
        for (BattleshipSquare battleshipSquare : battleshipGridAsList) {
            assertThat(battleshipSquare.getState()).isEqualTo("X");
        }
    }

    @Test
    public void givenAllSpacesContainingAnyShipIsHit_ThenTheGameEnds() {

    }

}