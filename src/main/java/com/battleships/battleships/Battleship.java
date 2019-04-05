package com.battleships.battleships;

import org.springframework.stereotype.Component;

@Component
public class Battleship extends BattleshipSquare{

    public Battleship() {
        super("Battleship", "4", 4, 4);
    }

}
