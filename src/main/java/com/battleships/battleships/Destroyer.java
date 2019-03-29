package com.battleships.battleships;

import org.springframework.stereotype.Component;

@Component
public class Destroyer extends BattleshipSquare{

    private final String type = "Destroyer";
    private String state = "2";
    private final int space = 2;
    private int spaceCount = 2;

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
