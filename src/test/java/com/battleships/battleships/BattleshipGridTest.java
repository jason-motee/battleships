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
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BattleshipGridConfig.class)
public class BattleshipGridTest {

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
    @Qualifier("battleshipSquareOne")
    private BattleshipSquare battleshipSquare;

    @Autowired
    @Qualifier("battleshipGridOne")
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
    public void checkGridForADestroyer() {
        battleshipGrid.insertBattleShipIntoRandomPosition(destroyer);
        battleshipGrid.printGrid();

        List<BattleshipSquare> battleshipGridAsList = getBattleshipSquaresList(destroyer.getType());

        assertThat(battleshipGridAsList.size()).isEqualTo(2);
        assertThat(battleshipGridAsList.get(0)).isEqualTo(destroyer);
        assertThat(battleshipGridAsList.get(1)).isEqualTo(destroyer);
    }

    @Test
    public void checkGridForSubmarine() {
        battleshipGrid.insertBattleshipIntoRandomRowPosition(submarine);
        battleshipGrid.printGrid();

        List<BattleshipSquare> battleshipSquaresList = getBattleshipSquaresList(submarine.getType());

        assertThat(battleshipSquaresList.size()).isEqualTo(3);
        for (BattleshipSquare square:battleshipSquaresList) {
            assertThat(square).isEqualTo(submarine);
        }
    }

    @Test
    public void checkGridForCruiser() {
        battleshipGrid.insertBattleshipIntoRandomRowPosition(cruiser);
        battleshipGrid.printGrid();

        List<BattleshipSquare> battleshipSquaresList = getBattleshipSquaresList(cruiser.getType());

        assertThat(battleshipSquaresList.size()).isEqualTo(3);

        for (BattleshipSquare square : battleshipSquaresList) {
            assertThat(square).isEqualTo(cruiser);
        }
    }

    @Test
    public void checkGridForBattleship() {
        battleshipGrid.insertBattleshipIntoRandomRowPosition(battleship);
        battleshipGrid.printGrid();

        List<BattleshipSquare> battleshipSquaresList = getBattleshipSquaresList(battleship.getType());

        assertThat(battleshipSquaresList.size()).isEqualTo(4);
        for (BattleshipSquare square : battleshipSquaresList) {
            assertThat(square).isEqualTo(battleship);
        }
    }

    @Test
    public void checkGridForCarrier() {
        battleshipGrid.insertBattleshipIntoRandomRowPosition(carrier);
        battleshipGrid.printGrid();

        List<BattleshipSquare> battleshipSquaresList = getBattleshipSquaresList(carrier.getType());

        assertThat(battleshipSquaresList.size()).isEqualTo(5);
        for (BattleshipSquare square : battleshipSquaresList) {
            assertThat(square).isEqualTo(carrier);
        }
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
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        assertThat(battleshipGridAsList).contains(carrier);
        assertThat(battleshipGridAsList).contains(battleship);
        assertThat(battleshipGridAsList).contains(cruiser);
        assertThat(battleshipGridAsList).contains(submarine);
        assertThat(battleshipGridAsList).contains(destroyer);
    }

    private List<BattleshipSquare> getBattleshipSquaresList(String type) {
        return Arrays.stream(battleshipGrid.getBoard())
                .flatMap(Arrays::stream)
                .filter(square -> square.getType().equals(type))
                .collect(Collectors.toList());
    }
}
