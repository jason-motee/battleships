package com.battleships.battleships;

import org.springframework.stereotype.Component;

@Component
public class Submarine extends BattleshipSquare {

    public Submarine() {
        super("Submarine", "3", 3, 3);
    }

}