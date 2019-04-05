package com.battleships.battleships;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BattleshipGridConfig.class)
public class BattleshipBoardTest {

    @Autowired
    @Qualifier("battleshipSquareOne")
    private BattleshipSquare battleshipSquare;

    @Autowired
    @Qualifier("carrierOne")
    private Carrier carrier;

    @Autowired
    @Qualifier("battleshipOne")
    private Battleship battleship;

    @Autowired
    @Qualifier("cruiserOne")
    private Cruiser cruiser;

    @Autowired
    @Qualifier("destroyerOne")
    private Destroyer destroyer;

    @Autowired
    @Qualifier("submarineOne")
    private Submarine submarine;

    @Autowired
    @Qualifier("boardOne")
    private BattleshipBoard battleshipBoard;

    @Before
    public void setUp() {

    }

    @Test
    public void addShipsToGameAndCheckTheGrid() {
        battleshipBoard.addShipsToGrid(carrier, battleship, cruiser, destroyer, submarine);
        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipBoard.getBattleshipGrid().getBoard())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        assertThat(battleshipGridAsList).contains(carrier, battleship, cruiser, destroyer, submarine);
    }

    @Test
    public void givenAnEmptySpaceIsHit_ThenTheGridContainsMissedValue () {
        battleshipBoard.addShipsToGrid();
        battleshipBoard.hit(0,0);

        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipBoard.getBattleshipGrid().getBoard())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        assertThat(battleshipGridAsList.get(0).getState()).isEqualTo("o");
    }

    @Test
    public void givenASpaceContainingCarrierIsHit_ThenTheGridContainsAHitValue() {
        battleshipBoard.addShipsToGrid(carrier);

        int row = 0;
        int column = 0;

        Outer: for (int i = 0; i < battleshipBoard.getBattleshipGrid().getBoard().length; i++) {
            for (int j = 0; j < battleshipBoard.getBattleshipGrid().getBoard().length; j++) {
                if (battleshipBoard.getBattleshipGrid().getSquare(i, j).getType().equals(carrier.getType())) {
                    battleshipBoard.hit(i, j);
                    row = i;
                    column = j;
                    break Outer;
                }
            }
        }

        assertThat(battleshipBoard.getBattleshipGrid().getBoard()[row][column].getState()).isEqualTo("*");
    }

    @Test
    public void givenAllSpacesContainingBattleshipIsHit_ThenTheGridContainsFourDestroyedValues() {
        battleshipBoard.addShipsToGrid(battleship);

        for (int row = 0; row < battleshipBoard.getBattleshipGrid().getBoard().length; row++) {
            for (int column = 0; column < battleshipBoard.getBattleshipGrid().getBoard().length; column++) {
                if (battleshipBoard.getBattleshipGrid().getSquare(row, column).getType().equals(battleship.getType())) {
                    battleshipBoard.hit(row, column);
                }
            }
        }

        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipBoard.getBattleshipGrid().getBoard())
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