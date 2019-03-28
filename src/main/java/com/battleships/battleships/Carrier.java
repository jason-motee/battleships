package com.battleships.battleships;

public class Carrier extends BattleshipSquare {

    private final String type = "Carrier";
    private String state = "5";
    private final int space = 5;
    private int spaceCount = 5;

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
