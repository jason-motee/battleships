package com.battleships.battleships;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BattleshipGridConfig {

    @Bean("carrierOne")
    public Carrier carrierOne() {
        return new Carrier();
    }

    @Bean("battleshipOne")
    public Battleship battleshipOne() {
        return new Battleship();
    }

    @Bean("cruiserOne")
    public Cruiser cruiserOne() {
        return new Cruiser();
    }

    @Bean("destroyerOne")
    public Destroyer destroyerOne() {
        return new Destroyer();
    }

    @Bean("submarineOne")
    public Submarine submarineOne() {
        return new Submarine();
    }

    @Bean("battleshipSquareOne")
    public BattleshipSquare battleshipSquare() {
        return new BattleshipSquare();
    }

    @Bean("battleshipGridOne")
    public BattleshipGrid battleshipGrid() {
        return new BattleshipGrid();
    }


    @Bean(name = "boardOne")
    public BattleshipBoard battleshipBoardOne(@Qualifier("battleshipGridOne") BattleshipGrid battleshipGrid,
                                              @Qualifier("battleshipSquareOne") BattleshipSquare battleshipSquare,
                                              @Qualifier("carrierOne") Carrier carrier,
                                              @Qualifier("battleshipOne") Battleship battleship,
                                              @Qualifier("cruiserOne") Cruiser cruiser,
                                              @Qualifier("submarineOne") Submarine submarine,
                                              @Qualifier("destroyerOne") Destroyer destroyer) {
        return new BattleshipBoard(battleshipGrid, battleshipSquare, carrier, battleship, cruiser, submarine, destroyer);

    }

    @Bean("carrierTwo")
    public Carrier carrierTwo() {
        return new Carrier();
    }

    @Bean("battleshipTwo")
    public Battleship battleshipTwo() {
        return new Battleship();
    }

    @Bean("cruiserTwo")
    public Cruiser cruiserTwo() {
        return new Cruiser();
    }

    @Bean("destroyerTwo")
    public Destroyer destroyerTwo() {
        return new Destroyer();
    }

    @Bean("submarineTwo")
    public Submarine submarineTwo() {
        return new Submarine();
    }

    @Bean("battleshipSquareTwo")
    public BattleshipSquare battleshipSquareTwo() {
        return new BattleshipSquare();
    }

    @Bean("battleshipGridTwo")
    public BattleshipGrid battleshipGridTwo() {
        return new BattleshipGrid();
    }

    @Bean(name = "boardTwo")
    public BattleshipBoard battleshipBoardTwo(@Qualifier("battleshipGridTwo") BattleshipGrid battleshipGrid,
                                              @Qualifier("battleshipSquareTwo") BattleshipSquare battleshipSquare,
                                              @Qualifier("carrierTwo") Carrier carrier,
                                              @Qualifier("battleshipTwo") Battleship battleship,
                                              @Qualifier("cruiserTwo") Cruiser cruiser,
                                              @Qualifier("submarineTwo") Submarine submarine,
                                              @Qualifier("destroyerTwo") Destroyer destroyer) {
        return new BattleshipBoard(battleshipGrid, battleshipSquare, carrier, battleship, cruiser, submarine, destroyer);

    }

    @Bean
    public BattleshipGame battleshipGame(@Qualifier("boardOne") BattleshipBoard battleshipBoardOne,
                                         @Qualifier("boardTwo") BattleshipBoard battleshipBoardTwo) {
        return new BattleshipGame(battleshipBoardOne, battleshipBoardTwo);

    }
}
