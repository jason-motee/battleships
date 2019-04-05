package com.battleships.battleships;

import java.util.Random;

public class ComputerPlayer {

    public void pickRandomCoordinate() {
        Random random = new Random();

        char randomLetter = Character.highSurrogate(random.nextInt(10));

        System.out.println(randomLetter);
    }

    public static void main(String[] args) {
        ComputerPlayer com = new ComputerPlayer();
        System.out.println();
    }
}
