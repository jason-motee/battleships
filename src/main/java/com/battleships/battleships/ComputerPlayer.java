package com.battleships.battleships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ComputerPlayer {

    private boolean hasHitAShip = false;
    private List<String[]> usedCoordinates = new ArrayList<>();
    private String[] computerCoordinateArray;


    public String[] getCoordinate(){
        if(hasHitAShip){
            pickRandomCoordinate();
            return this.computerCoordinateArray;
        } else {
            pickRandomCoordinate();
            return this.computerCoordinateArray;
        }
    }

    private void pickRandomCoordinate() {
        Random random = new Random();
        int numberBoundary = random.nextInt(9);
        char randomCoordinateLetter = (char) (numberBoundary + 'a');
        int randomCoordinateNumber = random.nextInt(9) + 1;

        String[] generatedCoordinateArray = {Character.toString(randomCoordinateLetter), Integer.toString(randomCoordinateNumber)};

        if(coordinateHasBeenUsedBefore(generatedCoordinateArray)){
            System.out.println("recursion: " + Arrays.toString(generatedCoordinateArray));
            pickRandomCoordinate();
        } else {
            computerCoordinateArray = generatedCoordinateArray;
            usedCoordinates.add(generatedCoordinateArray);
            for (String[] coord : usedCoordinates) {
                System.out.println(Arrays.toString(coord));
            }
            System.out.println("comp input: " + Arrays.toString(generatedCoordinateArray));
        }
    }

    private boolean coordinateHasBeenUsedBefore(String[] generatedCoordinateArray) {
        boolean hasBeenUsed = false;
        for (String[] usedCoordinate : usedCoordinates) {
            if (Arrays.equals(usedCoordinate, generatedCoordinateArray)) {
                hasBeenUsed = true;
            }
        }
        System.out.println(hasBeenUsed);
        return hasBeenUsed;
    }
}
