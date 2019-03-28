package com.battleships.battleships;

public class BattleshipSquare {

    private final String type = "Square";
    private String state = "?";
    private int space = 0;
    private int spaceCount = 0;
    private String destroyedType = "None";

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
}
