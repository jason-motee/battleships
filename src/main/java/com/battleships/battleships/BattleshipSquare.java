package com.battleships.battleships;

public class BattleshipSquare {

    private String type;
    private String state;
    private int space;
    private int spaceCount;
    private String hitValue;
    private String hitMessage;
    private String destroyedType = "None";

    public BattleshipSquare() {
        this("Empty Square", "-", 0, 0, "o", "Target Missed!");
    }

    public BattleshipSquare(String type, String state, int space, int spaceCount, String hitValue, String hitMessage) {
        this.type = type;
        this.state = state;
        this.space = space;
        this.spaceCount = spaceCount;
        this.hitValue = hitValue;
        this.hitMessage = hitMessage;
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

    public String getDestroyedType() {
        return destroyedType;
    }

    public void setDestroyedType(String destroyedType) {
        this.destroyedType = destroyedType;
    }

    public void printHitMessage() {
        System.out.println(hitMessage);
    }

    public BattleshipSquare hit() {
        printHitMessage();
        this.spaceCount -= 1;
        BattleshipSquare battleshipSquare = new BattleshipSquare();
        battleshipSquare.setState(this.hitValue);
        battleshipSquare.setDestroyedType(this.getType());
        return battleshipSquare;
    }

    public void hideState() {
        setState("-");
    }
}
