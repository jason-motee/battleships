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
    private BattleshipBoard battleshipBoardOne;

    @Autowired
    @Qualifier("boardTwo")
    private BattleshipBoard battleshipBoardTwo;

    @Autowired
    @Qualifier("battleshipTwo")
    private Battleship battleshipTwo;

    @Before
    public void setUp() {

    }

    @Test
    public void addShipsToGameAndCheckTheGrid() {
        battleshipBoardOne.addShipsToGrid(carrier, battleship, cruiser, destroyer, submarine);
        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipBoardOne.getBattleshipGrid().getBoard())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        assertThat(battleshipGridAsList).contains(carrier, battleship, cruiser, destroyer, submarine);
    }

    @Test
    public void givenAnEmptySpaceIsHit_ThenTheGridContainsMissedValue() {
        battleshipBoardOne.addShipsToGrid();
        battleshipBoardOne.applyCoordinates(0, 0);

        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipBoardOne.getBattleshipGrid().getBoard())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        assertThat(battleshipGridAsList.get(0).getState()).isEqualTo("o");
    }

    @Test
    public void givenASpaceContainingCarrierIsHit_ThenTheGridContainsAHitValue() {
        battleshipBoardOne.addShipsToGrid(carrier);

        int row = 0;
        int column = 0;

        Outer:
        for (int i = 0; i < battleshipBoardOne.getBattleshipGrid().getBoard().length; i++) {
            for (int j = 0; j < battleshipBoardOne.getBattleshipGrid().getBoard().length; j++) {
                if (battleshipBoardOne.getBattleshipGrid().getSquare(i, j).getType().equals(carrier.getType())) {
                    battleshipBoardOne.applyCoordinates(i, j);
                    row = i;
                    column = j;
                    break Outer;
                }
            }
        }

        assertThat(battleshipBoardOne.getBattleshipGrid().getSquare(row, column).getState()).isEqualTo("*");
    }

    @Test
    public void givenAllSpacesContainingBattleshipIsHit_ThenTheGridContainsFourDestroyedValues() {
        battleshipBoardTwo.addShipsToGrid(battleshipTwo);

        for (int row = 0; row < battleshipBoardTwo.getBattleshipGrid().getBoard().length; row++) {
            for (int column = 0; column < battleshipBoardTwo.getBattleshipGrid().getBoard().length; column++) {
                if (battleshipBoardTwo.getBattleshipGrid().getSquare(row, column).getType().equals(battleshipTwo.getType())) {
                    battleshipBoardTwo.applyCoordinates(row, column);
                }
            }
        }

        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipBoardTwo.getBattleshipGrid().getBoard())
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
        battleshipBoardOne.addShipsToGrid(carrier, battleship, cruiser, submarine, destroyer);

        for (int row = 0; row < battleshipBoardOne.getBattleshipGrid().getBoard().length; row++) {
            for (int column = 0; column < battleshipBoardOne.getBattleshipGrid().getBoard().length; column++) {
                if (battleshipBoardOne.getBattleshipGrid().getSquare(row, column).getType().equals(carrier.getType())
                        || battleshipBoardOne.getBattleshipGrid().getSquare(row, column).getType().equals(battleship.getType())
                        || battleshipBoardOne.getBattleshipGrid().getSquare(row, column).getType().equals(submarine.getType())
                        || battleshipBoardOne.getBattleshipGrid().getSquare(row, column).getType().equals(cruiser.getType())
                        || battleshipBoardOne.getBattleshipGrid().getSquare(row, column).getType().equals(destroyer.getType())) {
                    battleshipBoardOne.applyCoordinates(row, column);
                }
            }
        }
        assertThat(battleshipBoardOne.allShipsHaveSunk(carrier, battleship, cruiser, submarine, destroyer)).isEqualTo(true);
    }
}