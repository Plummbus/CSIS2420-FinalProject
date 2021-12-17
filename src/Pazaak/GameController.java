package Pazaak;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class GameController {
	private static Random rand = new Random();
	private static Player player;
	private static Opponent opponent;
	public static Deck deck;
	private static boolean endGame = false;
	private static Scanner sc = new Scanner(System.in);
	
	public static boolean winner;	//if either of these becomes true, a round ends
	public static boolean loser;
	
	public static void main(String[] args) throws FileNotFoundException {
		welcome();
		start();
	}
	
	private static void start() throws FileNotFoundException {
		player = createPlayer();
		do {
			mainMenu();
		} while (!endGame);
		
		System.out.println("\n\n\nGoodbye!");
	}
	
	public static void mainMenu() throws FileNotFoundException {
		System.out.println("Please select an option: "
				+ "\n1. Play Pazaak (press 4 first)"
				+ "\n2. Display Player Stats"
				+ "\n3. Exit"
				+ "\n4. Create deck and test shuffle."
				+ "\n5. Pop a card off the deck (press 4 first)"
				+ "\n6. Create opponent from csv");
		int input = sc.nextInt();
		switch (input) {
		case 1:
			//System.out.println("valid 1");
			pazaak();
			break;
		case 2:
			HelperFunctions.diagnosticMessage(player);
			mainMenu();
			break;
		case 3:
			endGame = true;
			break;
		case 4:
			testShuffle();
			mainMenu();
			break;
		case 5:
			popCard();
			mainMenu();
		case 6:
			displayOpponent();
			mainMenu();
		default:
			System.out.println("\n\nOnly integers from 1 to 3 are valid inputs. Returning to main menu...\n\n");
			mainMenu();
		}
	}
	
	private static void pazaak() {
		placeBet();
		chooseHandCards();
		confirmInfo();
	}
	
	public static void startGame() throws FileNotFoundException {
		opponent = new Opponent(rand);
//		deckForGame();
//		popCard();
		HelperFunctions.startGameGreeting(player, opponent, sc, rand);
		gameLoop();
	}
	
	private static void gameLoop() {
		winner = false;
		loser = false;
		//deckForGame();
		//popCard();
		do {
			playerTurn();
			opponentTurn();
		} while (!winner && !loser);
		endGame = true;
	}
	
	private static void playerTurn() {
		String message = String.format("Your current point total is: %d"
				+ "\nWould you like to play a card from your hand or draw?"
				+ "\n1. Play a Card"
				+ "\n2. Draw from Deck", player.getPointTotal());
		System.out.println(message);
		
		playerTurnManager();
		//HelperFunctions.playerTurnManager(player, deck, sc);
	}
	
	private static void opponentTurn() {
		String message = "";
		System.out.println("\n------------------------------\n"
				+ opponent.getName() + "'s turn:");
		if (!opponent.standStatus()) {
			if (opponent.getPointTotal() < 6) {
				HelperFunctions.drawFromDeck(opponent);
			} else if (opponent.getPointTotal() >= 6 && opponent.getPointTotal() <= 11) {
				if (opponent.handcardSize() == 0) {
					HelperFunctions.drawFromDeck(opponent);
				} else {
					int value = opponent.playCard();
					opponent.updatePointTotal(value);
					message = String.format("%s plays a %d from their hand!"
							+ "\nTheir point total is now: %d", 
							opponent.getName(), value, opponent.getPointTotal());
				}
			} else if (opponent.getPointTotal() > 11 && opponent.getPointTotal() < 17) {
				HelperFunctions.drawFromDeck(opponent);
			} else {
				opponent.setStand(true);
				message = String.format("%s chooses to stand!"
						+ "\nTheir point total is: %d",
						opponent.getName(), opponent.getPointTotal());
			}
		} else {
			message = String.format("%s chooses to stand!"
					+ "\nTheir point total is: %d",
					opponent.getName(), opponent.getPointTotal());
		}
		
		System.out.println(message);
		
		
	}
	
	private static void playerTurnManager() {
		boolean flag = false;
		do {
			try {
				int input = sc.nextInt();
				if (input == 1) {
					//System.out.println("1 is hit");
					playFromHand();
					
					flag = false;
				} else if (input == 2) {
					//System.out.println("1 is hit");
//					int value = deck.pop().getValue();
//					System.out.println("Value is " + value);
					drawFromDeck();
					
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
	
	public static void drawFromDeck() {
		//System.out.println("drawFromDeck hit");
		//sc.next();
		int value = deck.pop().getValue();
		player.updatePointTotal(value);
		String message = String.format("%s has drawn a %d"
				+ "\nNew point total is: %d", player.getName(), value, player.getPointTotal());
		System.out.println(message);
	}
	
	public static void playFromHand() {
		String message = String.format("Which card would you like to play?"
				+ "\n1. %d" 
				+ "\n1. %d"
				+ "\n1. %d"
				+ "\n1. %d",
				player.getModifier(1), player.getModifier(2), player.getModifier(3), player.getModifier(4));
		System.out.println(message);
		boolean flag = false;
		String failureMessage = "Please enter a number between 1 and 4.";
		int value = 0;
		do {
			try {
				int input = sc.nextInt();
				switch (input) {
				case 1:
					value = player.getModifier(input);
					flag = false;
					break;
				case 2:
					value = player.getModifier(input);
					flag = false;
					break;
				case 3:
					value = player.getModifier(input);
					flag = false;
					break;
				case 4:
					value = player.getModifier(input);
					flag = false;
					break;
				default:
					System.out.println(failureMessage);
					flag = true;
				}
			} catch (Exception e) {
				System.out.println(failureMessage);
				sc.next();
				flag = true;
			}
		} while (flag);
		
		player.updatePointTotal(value);
		String cardMessage = String.format("%s plays a %d from their hand!"
				+ "\nNew point total is: %d",
				player.getName(), value, player.getPointTotal());
		System.out.println(cardMessage);
	}
	
	
	private static void confirmInfo() {
		HelperFunctions.fullDiagnosticMessage(player);
		System.out.println("Please confirm the data displayed is correct."
				+ "\nIf it is correct, enter 1 to continue and play Pazaak."
				+ "\nIf it is wrong, enter 2 to delete data and return to the main menu.");
		HelperFunctions.confirmInfo(player, sc);
	}
	
	private static void chooseHandCards() {
		System.out.println("\n-----------------------------------"
				+ "\nNext, you will choose the 4 hand cards you can play during your game!"
				+ "\nCard values must be between -6 and 6 (both inclusive) and must not be 0.");
		for (int i = 1; i < 5; i++) {
			String prompt = String.format("Please enter the value for Card #%d", i);
			System.out.println(prompt);
			HelperFunctions.addCardToHand(player, sc, i);
		}
	}
	
	private static void placeBet() {
		String message = String.format("\n%-10s: %-10d", "Money", player.getMoney());
		System.out.println("\n-----------------------------------"
				+ "\nHow much would you like to bet this round?"
				+ message);
		
		HelperFunctions.checkBet(player, sc);
		HelperFunctions.randomBetStatements(player, rand);
	}
	
	private static void welcome() {
		String message = "Goodday and welcome to Console Pazaak!. The game is played vs the dealer(computer) and with a deck of 240 cards."
				+ "\nYou can bet as much as you like (have) each round. Winning a game earns you 1.5x your bet amount. Losing"
				+ "\nmakes you lose the amount you bet. The game ends when you run out of money or exit the program.";
		System.out.println(message);
	}
	
	private static Player createPlayer() {
		System.out.println("Please enter your name: ");
		String name = sc.nextLine();
		System.out.println(String.format("Hello, %s! We hope you enjoy playing.", name));
		return new Player(name);
	}
	
	private static void deckForGame() {
		deck = new Deck();
		createDeck(deck);
		shuffleDeck(deck);
	}
	
	private static void createDeck(Deck deck) {
		//in Pazaak, the deck is made of 4 copies of cards valued from 1-10.
		//Since this is like a casino version of Pazaak, we'll take the same approach casinos take
		//with Blackjack and use 6 decks to make our game deck, making the total card count 240 cards
		for (int i = 0; i < 24; i++) {	//loop for 4 copies
			for (int j = 1; j < 11; j++) {	//loop for card values
				deck.push(new Card(j));
			}
		}
	}
	
	private static Deck shuffleDeck(Deck deck) {
		Deck newDeck = new Deck();
		ArrayList<Card> shuffleList = new ArrayList<Card>();
		while (!deck.isEmpty()) {
			shuffleList.add(deck.pop());
		}
		Collections.shuffle(shuffleList);
		for (Card card : shuffleList) {
			newDeck.push(card);
		}
		return newDeck;
	}
	
	private static void testShuffle() {
		Deck temp = new Deck();
		createDeck(temp);
		deck = shuffleDeck(temp);
		Card current = deck.getHead();
		while (current != null) {
			String message = String.format("Card value: %d", current.getValue());
			System.out.println(message);
			current = current.getNext();		
		}
		System.out.println("Total number of cards in deck:" + deck.size()
				+ "\n--------------------------------\n");
	}
	
	private static void popCard() {
		int value = deck.pop().getValue();
		String message = String.format("Value of card drawn: %d"
				+ "\nCards left in deck: %d"
				+ "\n-------------------------------------\n", value, deck.size());
		System.out.print(message);
	}
	
	public static void displayOpponent() throws FileNotFoundException {
		Opponent tempOpponent = new Opponent(rand);
		String message = String.format("-----------------------------------"
				+ "\n%-12s: %-10s"
				+ "\n%-12s: %-10d"
				+ "\n%-12s: %-10d"
				+ "\n-----------------------------------",
				"Opponent", tempOpponent.getName(), "Wins", tempOpponent.getWins(), "Losses", tempOpponent.getLosses());
		
		String handCards = String.format("%-12s: %d, %d, %d, %d"
				+ "\n-----------------------------------", "Hand Cards", tempOpponent.getModifier(1), tempOpponent.getModifier(2), tempOpponent.getModifier(3), tempOpponent.getModifier(4));
		System.out.println(message);
		System.out.println(handCards);
	}	
}
