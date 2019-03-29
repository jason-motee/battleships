package com.battleships.battleships;

import org.springframework.context.annotation.Bean;

public class BattleshipGridConfig {

    @Bean
    public Carrier carrier() {
        return new Carrier();
    }

    @Bean
    public Battleship battleShip() {
        return new Battleship();
    }

    @Bean
    public Cruiser cruiser() {
        return new Cruiser();
    }

    @Bean
    public Destroyer destroyer() {
        return new Destroyer();
    }

    @Bean
    public Submarine submarine() {
        return new Submarine();
    }

    @Bean
    public BattleshipSquare battleshipSquare() {
        return new BattleshipSquare();
    }

    @Bean
    public BattleshipGrid battleshipGrid() {
        return new BattleshipGrid();
    }

    @Bean
    public BattleshipGame battleshipGame(BattleshipGrid battleshipGrid,
                                         BattleshipSquare battleshipSquare,
                                         Carrier carrier,
                                         Battleship battleship,
                                         Cruiser cruiser,
                                         Submarine submarine,
                                         Destroyer destroyer) {
        return new BattleshipGame(battleshipGrid, battleshipSquare, carrier, battleship, cruiser, submarine, destroyer);
    }
}
