package com.battleships.battleships;

public class BattleshipSquare {

    private String type;
    private String state;
    private int space;
    private int spaceCount;
    private String destroyedType = "None";

    public BattleshipSquare() {
        this("Empty Square", "-", 0, 0);
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

    public BattleshipSquare hit() {
        if(!this.getType().equals("Empty Square")) {
            System.out.println(this.getType() + " hit!");
            this.spaceCount -= 1;
            BattleshipSquare battleshipSquare = new BattleshipSquare();
            battleshipSquare.setState("*");
            battleshipSquare.setDestroyedType(this.getType());
            return battleshipSquare;
        } else if (this.getDestroyedType().equals("None")){
            System.out.println("Target missed!");
            BattleshipSquare battleshipSquare = this;
            battleshipSquare.setState("o");
            return this;
        }
        return this;
    }

    public void hideState() {
        setState("-");
    }
}
