package com.battleships.battleships;

import org.springframework.stereotype.Component;

@Component
public class Cruiser extends BattleshipSquare{

    public Cruiser() {
        super("Cruiser", "3", 3, 3);
    }

}
