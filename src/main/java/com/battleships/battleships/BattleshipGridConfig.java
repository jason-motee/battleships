package com.battleships.battleships;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BattleshipGridConfig {

    @Bean(name = "carrierOne")
    public Carrier carrierOne() {
        return new Carrier();
    }

    @Bean(name = "battleshipOne")
    public Battleship battleshipOne() {
        return new Battleship();
    }

    @Bean(name = "cruiserOne")
    public Cruiser cruiserOne() {
        return new Cruiser();
    }

    @Bean(name = "destroyerOne")
    public Destroyer destroyerOne() {
        return new Destroyer();
    }

    @Bean(name = "submarineOne")
    public Submarine submarineOne() {
        return new Submarine();
    }

    @Bean(name = "battleshipSquareOne")
    public BattleshipSquare battleshipSquare() {
        return new BattleshipSquare();
    }

    @Bean(name = "battleshipGridOne")
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

    @Bean(name = "carrierTwo")
    public Carrier carrierTwo() {
        return new Carrier();
    }

    @Bean(name = "battleshipTwo")
    public Battleship battleshipTwo() {
        return new Battleship();
    }

    @Bean(name = "cruiserTwo")
    public Cruiser cruiserTwo() {
        return new Cruiser();
    }

    @Bean(name = "destroyerTwo")
    public Destroyer destroyerTwo() {
        return new Destroyer();
    }

    @Bean(name = "submarineTwo")
    public Submarine submarineTwo() {
        return new Submarine();
    }

    @Bean(name = "battleshipSquareTwo")
    public BattleshipSquare battleshipSquareTwo() {
        return new BattleshipSquare();
    }

    @Bean(name = "battleshipGridTwo")
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
                                         @Qualifier("boardTwo") BattleshipBoard battleshipBoardTwo,
                                         ComputerPlayer computerPlayer) {
        return new BattleshipGame(battleshipBoardOne, battleshipBoardTwo, computerPlayer);

    }

    @Bean
    public ComputerPlayer computerPlayer() {
        return new ComputerPlayer();
    }
}
