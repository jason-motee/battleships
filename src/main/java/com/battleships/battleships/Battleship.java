package com.battleships.battleships;

import org.springframework.stereotype.Component;

@Component
public class Battleship extends BattleshipSquare{

    private final String type = "Battleship";
    private String state = "4";
    private final int space = 4;
    private int spaceCount = 4;

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getSpace() {
        return space;
    }

    public int getSpaceCount() {
        return spaceCount;
    }

    public void minusOneSpaceCount() {
        this.spaceCount -= 1;
    }
}
