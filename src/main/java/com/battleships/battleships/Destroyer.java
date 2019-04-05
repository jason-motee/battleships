package com.battleships.battleships;

import org.springframework.stereotype.Component;

@Component
public class Destroyer extends BattleshipSquare{

    public Destroyer() {
        super("Destroyer", "2", 2, 2);
    }

}
