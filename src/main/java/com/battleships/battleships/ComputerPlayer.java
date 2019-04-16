package com.battleships.battleships;

import java.util.*;

//
public class ComputerPlayer {

    private BattleshipBoard battleshipBoard;
    private int numberOfHitsOnCarrier = 0;
    private List<String[]> usedCoordinates = new ArrayList<>();
    private String[] computerCoordinateArray;

    private List<String[]> hitArray = new ArrayList<>();
    private List<String[]> fourCoordinatesList = new ArrayList<>();
    private List<String[]> lineOfCoordinatesList = new ArrayList<>();
    private List<String[]> positiveLineOfCoordinatesList = new ArrayList<>();
    private List<String[]> negativeLineOfCoordinatesList = new ArrayList<>();
    private List<List<String[]>> bothLinesOfCoordinates = new ArrayList<>();
    private int bothLinesListIndex = 0;

    private boolean lastMoveWasHit;

    public ComputerPlayer(BattleshipBoard battleshipBoard) {
        this.battleshipBoard = battleshipBoard;
    }

    public String[] getCoordinate() {
        if(numberOfHitsOnCarrier == 1) {
            getAllFourCoordinatesAroundInitialHit();
            return this.computerCoordinateArray;
        } else if (numberOfHitsOnCarrier > 1) {
            getLineOfCoordinates();
            return this.computerCoordinateArray;
        } else {
            pickRandomCoordinate();
            return this.computerCoordinateArray;
        }
    }

    private void getLineOfCoordinates() {
        if (hitArray.get(0)[0].equals(hitArray.get(1)[0])) {
            hitArray.sort(Comparator.comparing(strings -> strings[1]));
            getVerticalCoordinates(hitArray.get(0), hitArray.get(1));
        } else {
            hitArray.sort(Comparator.comparing(strings -> strings[0]));
            getHorizontalCoordinates(hitArray.get(0), hitArray.get(1));
        }
    }

    private void getHorizontalCoordinates(String[] coordinatesOne, String[] coordinatesTwo) {
        for (int offset = -1; offset >= -(battleshipBoard.getCarrier().getSpace() - 2); offset--) {
            negativeLineOfCoordinatesList.add(offsetLetterCoordinate(coordinatesOne, offset));
        }

        for (int offset = 1; offset <= battleshipBoard.getCarrier().getSpace() - 2; offset++) {
            positiveLineOfCoordinatesList.add(offsetLetterCoordinate(coordinatesTwo, offset));
        }

        negativeLineOfCoordinatesList.removeIf(this::isOutOfBounds);
        positiveLineOfCoordinatesList.removeIf(this::isOutOfBounds);

        bothLinesOfCoordinates.add(negativeLineOfCoordinatesList);
        bothLinesOfCoordinates.add(positiveLineOfCoordinatesList);


        if ((!lastMoveWasHit) || bothLinesOfCoordinates.get(bothLinesListIndex).size() == 0) {
            bothLinesListIndex = 1;
        }

        for (String[] coordinate : bothLinesOfCoordinates.get(bothLinesListIndex)) {
            if (!coordinateHasBeenUsedBefore(coordinate)) {
                computerCoordinateArray = coordinate;
                usedCoordinates.add(coordinate);
                break;
            }
        }
    }

    private void getVerticalCoordinates(String[] coordinatesOne, String[] coordinatesTwo) {
        for (int offset = -1; offset >= -(battleshipBoard.getCarrier().getSpace() - 2); offset--) {
            negativeLineOfCoordinatesList.add(offsetNumberCoordinate(coordinatesOne, offset));
        }

        for (int offset = 1; offset <= battleshipBoard.getCarrier().getSpace() - 2; offset++) {
            positiveLineOfCoordinatesList.add(offsetNumberCoordinate(coordinatesTwo, offset));
        }

        negativeLineOfCoordinatesList.removeIf(this::isOutOfBounds);
        positiveLineOfCoordinatesList.removeIf(this::isOutOfBounds);

        bothLinesOfCoordinates.add(negativeLineOfCoordinatesList);
        bothLinesOfCoordinates.add(positiveLineOfCoordinatesList);

        int index = 0;
        if ((!lastMoveWasHit) || bothLinesOfCoordinates.get(index).size() == 0) {
            index = 1;
        }

        for (String[] coordinate : bothLinesOfCoordinates.get(index)) {
            if (!coordinateHasBeenUsedBefore(coordinate)) {
                computerCoordinateArray = coordinate;
                usedCoordinates.add(coordinate);
                break;
            }
        }
    }

    private void getAllFourCoordinatesAroundInitialHit() {
        List<Map<String, Boolean>> listOfMap = calculateBestPath(battleshipBoard.getCarrier());
        Map<String, Boolean> offsetIsPresent = listOfMap.get(0);
        Map<String, Boolean> pathIsGood = listOfMap.get(1);

        if(offsetIsPresent.get("Positive") && pathIsGood.get("Positive")) {
            fourCoordinatesList.add(offsetLetterCoordinate(hitArray.get(0), 1));
            fourCoordinatesList.add(offsetLetterCoordinate(hitArray.get(0), -1));
        }

        if (!offsetIsPresent.get("Positive") && pathIsGood.get("Positive")) {
            fourCoordinatesList.add(offsetLetterCoordinate(hitArray.get(0), 1));
        }

        if (offsetIsPresent.get("Negative") && pathIsGood.get("Negative")) {
            fourCoordinatesList.add(offsetLetterCoordinate(hitArray.get(0), 1));
            fourCoordinatesList.add(offsetLetterCoordinate(hitArray.get(0), -1));
        }

        if (!offsetIsPresent.get("Negative") && pathIsGood.get("Negative")) {
            fourCoordinatesList.add(offsetLetterCoordinate(hitArray.get(0), -1));
        }

        fourCoordinatesList.add(offsetLetterCoordinate(hitArray.get(0), 1));
        fourCoordinatesList.add(offsetLetterCoordinate(hitArray.get(0), -1));
        fourCoordinatesList.add(offsetNumberCoordinate(hitArray.get(0), 1));
        fourCoordinatesList.add(offsetNumberCoordinate(hitArray.get(0), -1));

        fourCoordinatesList.removeIf(this::isOutOfBounds);

        for (String[] coordinate: fourCoordinatesList) {
            if(!coordinateHasBeenUsedBefore(coordinate)){
                computerCoordinateArray = coordinate;
                usedCoordinates.add(coordinate);
                break;
            }
        }
    }

    private List<Map<String, Boolean>> calculateBestPath(BattleshipSquare typeOfSquare) {
        int outOfBoundsPositiveOffset = 0;
        int outOfBoundsNegativeOffset = 0;
        Map<String, Boolean> offsetIsPresent = new HashMap<>();
        offsetIsPresent.put("Positive", false);
        offsetIsPresent.put("Negative", false);

        Map<String, Boolean> pathIsGood = new HashMap<>();
        pathIsGood.put("Positive", false);
        pathIsGood.put("Negative", false);

        for (int i = 1; i < typeOfSquare.getSpace(); i++) {
            if(isOutOfBounds(offsetLetterCoordinate(hitArray.get(0), i))){
                outOfBoundsPositiveOffset += 1;
                offsetIsPresent.replace("Positive", true);
            }
        }

        for (int i = -1; i > -(typeOfSquare.getSpace()); i++) {
            if (isOutOfBounds(offsetLetterCoordinate(hitArray.get(0), i))) {
                outOfBoundsNegativeOffset += 1;
                offsetIsPresent.replace("Negative", true);
            }
        }

        for (int i = (0 - outOfBoundsPositiveOffset); i < (typeOfSquare.getSpace() - outOfBoundsPositiveOffset); i++) {
            if (battleshipBoard.getDestroyedType(this.battleshipBoard, offsetLetterCoordinate(hitArray.get(0), i)).equals("None")
                    || battleshipBoard.getDestroyedType(this.battleshipBoard, offsetLetterCoordinate(hitArray.get(0), i)).equals(typeOfSquare.getType())) {
                pathIsGood.replace("Positive", true);
            }
        }

        for (int i = (0 - outOfBoundsNegativeOffset); i < (typeOfSquare.getSpace() - outOfBoundsNegativeOffset); i++) {
            if (battleshipBoard.getDestroyedType(this.battleshipBoard, offsetLetterCoordinate(hitArray.get(0), i)).equals("None")
                    || battleshipBoard.getDestroyedType(this.battleshipBoard, offsetLetterCoordinate(hitArray.get(0), i)).equals(typeOfSquare.getType())) {
                pathIsGood.replace("Negative", true);
            }
        }

        List<Map<String, Boolean>> listOfMaps = new ArrayList<>();
        listOfMaps.add(offsetIsPresent);
        listOfMaps.add(pathIsGood);

        return listOfMaps;
    }

    private boolean isOutOfBounds(String[] coordinate) {
        return coordinate[0].charAt(0) > 105
                || coordinate[0].charAt(0) <  97
                || Integer.parseInt(coordinate[1]) > 9
                || Integer.parseInt(coordinate[1]) < 1;
    }

    private String[] offsetLetterCoordinate(String[] coordinates, int offset) {
        char letterCoordinate = coordinates[0].charAt(0);
        letterCoordinate += offset;
        Character letterCoordinateObj = letterCoordinate;
        String letterCoordinateString = letterCoordinateObj.toString();
        String[] newTargetArray = new String[]{coordinates[0], coordinates[1]};
        newTargetArray[0] = letterCoordinateString;
        return newTargetArray;
    }

    private String[] offsetNumberCoordinate(String[] coordinates, int offset) {
        int numberCoordinate = Integer.parseInt(coordinates[1]);
        numberCoordinate += offset;
        String numberCoordinateString = Integer.toString(numberCoordinate);
        String[] newTargetArray = new String[]{coordinates[0], coordinates[1]};
        newTargetArray[1] = numberCoordinateString;
        return newTargetArray;
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

    //
    public void checkForHitShip(String destroyedType) {
        //
        if (destroyedType.equals(battleshipBoard.getCarrier().getType())) {
            hitArray.add(computerCoordinateArray);
            //
            numberOfHitsOnCarrier += 1;
            lastMoveWasHit = true;
        } else {
            lastMoveWasHit = false;
        }
    }

    //
    public static void main(String[] args) {
        int a = 0;
        System.out.println(a - 1);
    }
}
