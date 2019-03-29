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
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BattleshipGridConfig.class)
public class BattleshipGridTest {

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
    private BattleshipSquare battleshipSquare;

    @Autowired
    private BattleshipGrid battleshipGrid;

    @Before
    public void setUp() {
        battleshipGrid.initialiseGrid();
    }

    @Test
    public void checkGridCountIsEqualTo81() {
        battleshipGrid.printGrid();

        int gridCount = (int) Arrays.stream(battleshipGrid.getBoard())
                .flatMapToInt(square -> IntStream.range(0, battleshipGrid
                        .getBoard().length))
                .count();

        assertThat(gridCount).isEqualTo(81);
    }

    @Test
    public void checkGridForABattleshipOne() {
        battleshipGrid.insertBattleShipIntoRandomPosition(destroyer);
        battleshipGrid.printGrid();

        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipGrid.getBoard())
                .flatMap(Arrays::stream).collect(Collectors.toList());

        assertThat(battleshipGridAsList).contains(destroyer);
    }

    @Test
    public void checkGridForABattleshipTwoInRow() {
        battleshipGrid.insertBattleshipIntoRandomRowPosition(submarine);
        battleshipGrid.printGrid();

        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipGrid.getBoard())
                .flatMap(Arrays::stream).collect(Collectors.toList());

        assertThat(battleshipGridAsList).contains(submarine);
    }

    @Test
    public void checkGridForDestroyerAndSubmarine() {
        battleshipGrid.insertBattleShipIntoRandomPosition(destroyer);
        battleshipGrid.insertBattleShipIntoRandomPosition(submarine);
        battleshipGrid.printGrid();

        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipGrid.getBoard())
                .flatMap(Arrays::stream).collect(Collectors.toList());

        assertThat(battleshipGridAsList).contains(destroyer);
        assertThat(battleshipGridAsList).contains(submarine);
    }

    @Test
    public void checkGridForAllShipTypes() {
        battleshipGrid.insertBattleShipIntoRandomPosition(carrier);
        battleshipGrid.insertBattleShipIntoRandomPosition(battleship);
        battleshipGrid.insertBattleShipIntoRandomPosition(cruiser);
        battleshipGrid.insertBattleShipIntoRandomPosition(submarine);
        battleshipGrid.insertBattleShipIntoRandomPosition(destroyer);
        battleshipGrid.printGrid();

        List<BattleshipSquare> battleshipGridAsList = Arrays.stream(battleshipGrid.getBoard())
                .flatMap(Arrays::stream).collect(Collectors.toList());

        assertThat(battleshipGridAsList).contains(carrier);
        assertThat(battleshipGridAsList).contains(battleship);
        assertThat(battleshipGridAsList).contains(cruiser);
        assertThat(battleshipGridAsList).contains(submarine);
        assertThat(battleshipGridAsList).contains(destroyer);
    }
}
