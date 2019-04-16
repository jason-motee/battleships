package com.battleships.battleships;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

//Runnable?
@SpringBootApplication
public class BattleshipsApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory
			.getLogger(BattleshipsApplication.class);

	public static void main(String[] args) throws IOException {
        SpringApplication app = new SpringApplication(BattleshipsApplication.class);
        BattleshipGame game = app.run().getBean(BattleshipGame.class);
        System.out.println("Welcome to Battleships");
        System.out.println("Enter 1p or 2p...");
        Scanner playerInput = new Scanner(System.in);
        String numberOfPlayers = playerInput.nextLine();

        if (numberOfPlayers.equals("1p")) {
            game.startOnePlayerGame(playerInput);

        } else if (numberOfPlayers.equals("2p")) {
            game.startTwoPlayerGame(playerInput);

        }
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING : command line runner");

		for (int i = 0; i < args.length; ++i) {
			LOG.info("args[{}]: {}", i, args[i]);
		}
	}
}
