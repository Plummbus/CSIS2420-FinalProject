package Pazaak;

import java.util.Random;
import java.util.Scanner;

public class HelperFunctions {

	
	public static void checkBet(Player player, Scanner sc) {
		String failureMessage = "Player bet must be between 1 and their current money total."
				+ "\nPlease enter another bet: ";
		boolean betFlag = false;
		do {
			try {
				int bet = sc.nextInt();
				if (bet >= 1 && bet <= player.getMoney()) {
					player.setCurrentBet(bet);
					betFlag = false;
				} else {
					System.out.println(failureMessage);
					betFlag = true;
				}
			} catch(Exception e) {
				System.out.println(failureMessage);
				betFlag = true;
				sc.next(); //moving scanner forward to avoid infinite loop
			}
		} while (betFlag);
	}
	
	public static void randomBetStatements(Player player, Random rand) {
		String[] phrases = {
				"Lets hope they win!",
				"Good luck to you and have fun!",
				"Wow, we've got a high-roller here!",
				"Now that's a lot of money!",
				"I'm rooting for you!",
				"Never before have I seen so much cash!",
				"Whoa, I wouldn't be so cavalier with all that money!",
				"I'm sure we're looking at a winner here!",
				"Make sure to cash out before you spend it all!"
		};
		System.out.println(String.format("%s has bet %d! %s", player.getName(), player.getCurrentBet(), phrases[rand.nextInt(phrases.length)]));
	}
	
	public static void addCardToHand(Player player, Scanner sc, int i) {
		boolean cardFlag = false;
		String failureMessage = String.format("Card values must be between -6 and 6 (both inclusive) and must not be 0. \nPlease enter the value for Card #%d", i);
		do {
			try {
				int value = sc.nextInt();
				if (-6 <= value && value <= 6 && value != 0) {
					player.addHandCard(value);
					String successMessage = String.format("%d successfully added as Card #%d", value, i);
					System.out.println(successMessage);
					cardFlag = false;
				} else {
					System.out.println(failureMessage);
					cardFlag= true;
				}
			} catch (Exception e) {
				System.out.println(failureMessage);
				cardFlag= true;
				sc.next();	//gotta move that scanner forward, gotta stop forgetting that bit
			}
		} while (cardFlag);
	}
	
	public static void diagnosticMessage(Player player) {
		String message = String.format("-----------------------------------"
				+ "\n%-12s: %-10s"
				+ "\n%-12s: %-10d"
				+ "\n%-12s: %-10d"
				+ "\n%-12s: %-10d"
				+ "\n%-12s: %-10d"
				+ "\n-----------------------------------",
				"Player", player.getName(), "Money", player.getMoney(), "Current Bet", player.getCurrentBet(), "Wins", player.getWins(), "Losses", player.getLosses());
		System.out.println(message);
	}
	
	public static void fullDiagnosticMessage(Player player) {
		diagnosticMessage(player);
		String handCards = String.format("%-12s: %d, %d, %d, %d"
				+ "\n-----------------------------------", "Hand Cards", player.getModifier(1), player.getModifier(2), player.getModifier(3), player.getModifier(4));
		System.out.println(handCards);
	}
	
	public static void confirmInfo(Player player, Scanner sc) {
		boolean dataFlag = false;
		String failureMessage = ("Please enter 1 to confirm, 2 for main menu.");

		do {
			try {
				int input = sc.nextInt();
				switch (input) {
				case 1:
					GameController.startGame();
					dataFlag = false;
					break;
				case 2:
					player.setCurrentBet(0);
					player.wipeHand();
					dataFlag = false;
					GameController.mainMenu();
					break;
				default:
					System.out.println(failureMessage);
					dataFlag = true;
					break;
				}
			} catch (Exception e) {
				System.out.println(failureMessage);
				dataFlag = true;
				sc.next();
			}
		} while (dataFlag);
	}
	
	//might add random phrases later if i have time
	public static void startGameGreeting(Player player, Opponent opponent, Scanner sc, Random rand) {
		String intro = String.format(""
				+ "And a game of Pazaak begins!!!"
				+ "\nToday, we welcome newcomer cardshark, %s"
				+ ", who will be facing off against the dastardly and cunning, %s!", 
				player.getName(), opponent.getName());
		System.out.println(intro);
	}
	
	public static void playerTurnManager(Player player, Deck deck, Scanner sc) {
		boolean flag = false;
		do {
			try {
				int input = sc.nextInt();
				if (input == 1) {
					System.out.println("1 is hit");
					drawFromDeck(player, deck, sc);
					flag = false;
				} else if (input == 2) {
					System.out.println("2 is hit");
					drawFromDeck(player, deck, sc);
					flag = false;
				} else {
					System.out.println("wrong num try again");
					flag = true;
				}
			} catch (Exception e) {
				System.out.println("only nums");
				flag = true;
				sc.next();
			}
		} while (flag);
	}

	
	public static void drawFromDeck(Player player, Deck deck, Scanner sc) {
		System.out.println("drawFromDeck hit");
		sc.next();
		int value = deck.pop().getValue();
		System.out.println("Value is " + value);
	}
	
	/*
	public static void drawFromDeck(Player player) {
		System.out.println("Player points: " + player.getPointTotal());
		System.out.println("Card draw: " + GameController.deck.pop().getValue());
		
		int value = GameController.deck.pop().getValue();
		player.updatePointTotal(value);
		String message = String.format("%s has drawn a %d"
				+ "\nNew point total is: %d", player.getName(), value, player.getPointTotal());
		System.out.println(message);
	}
	*/
	
	
	public static void drawFromDeck(Opponent opponent) {
		int value = GameController.deck.pop().getValue();
		opponent.updatePointTotal(value);
		String message = String.format("%s has drawn a %d"
				+ "\nNew point total is: %d"
				+ "\n------------------------------", opponent.getName(), value, opponent.getPointTotal());
		System.out.println(message);
	}
	
}
