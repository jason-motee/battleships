package com.battleships.battleships;

import org.springframework.stereotype.Component;

@Component
public class Carrier extends BattleshipSquare {

    public Carrier() {
        super("Carrier", "5", 5, 5);
    }

}
