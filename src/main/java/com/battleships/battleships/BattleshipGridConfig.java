package com.battleships.battleships;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
    public BattleshipGame battleshipGame(BattleshipGrid battleshipGrid) {
        return new BattleshipGame(battleshipGrid);
    }
}
