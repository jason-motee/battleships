package com.battleships.battleships;

public class BattleshipSquare {

    private String type = "Square";
    private String state = "-";
    private int space = 0;
    private int spaceCount = 0;
    private String destroyedType = "None";

    public BattleshipSquare() {
    }

    public BattleshipSquare(String type, String state, int space, int spaceCount) {
        this.type = type;
        this.state = state;
        this.space = space;
        this.spaceCount = spaceCount;
    }

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

    public String getDestroyedType() {
        return destroyedType;
    }

    public void setDestroyedType(String destroyedType) {
        this.destroyedType = destroyedType;
    }

    public void hideState() {
        setState("-");
    }
}
