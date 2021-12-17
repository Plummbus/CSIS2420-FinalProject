package Pazaak;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Opponent {

	private String name;
	private int pointTotal;
	private int wins;
	private int losses;
	private ArrayList<HandCard> handcard = new ArrayList<HandCard>();
	private static ArrayList<String> namesArray = new ArrayList<String>();
	private static Random rand;
	private boolean stand;
	
//	private String[] possibleNames = {
//			"Jawa", "Dodonna", "Taggi", "Motti", "Deckard"
//	};
	
	private int[] possibleCards;
	
	public Opponent(Random rand) throws FileNotFoundException {
		buildNamesArray();
		
		this.rand = rand;
		this.name = randomName();
		this.pointTotal = 0;
		this.wins = rand.nextInt(21);
		this.wins = rand.nextInt(21);
		this.possibleCards = possibleCardsArray();
		this.fillHand();
		this.stand = false;
	}
	
	public int handcardSize() {
		return this.handcard.size();
	}
	
	private static String randomName() {
		return namesArray.get(rand.nextInt(namesArray.size()));
	}
	
	private static void buildNamesArray() throws FileNotFoundException {
		File file = new File(".\\src\\Pazaak\\starWarsNames.csv");
		Scanner sc = new Scanner(file);
		
		String name;
		
		while (sc.hasNextLine()) {
			name = sc.nextLine();
			namesArray.add(name);
		}
		
		sc.close();
	}
	

	private int[] possibleCardsArray() {
		int[] temp = new int[12];
		for (int i = -6; i < 6; i++) {
			if (i != 0) {
				temp[i + 6] = i;
			}
		}
		return temp;
	}
	
	private void fillHand() {
		for (int i = 1; i < 5; i++) {
			this.addHandCard(possibleCards[rand.nextInt(possibleCards.length)]);
		}
	}
	
	private void addHandCard(int val) {
		this.handcard.add(new HandCard(val));
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getPointTotal() {
		return this.pointTotal;
	}
	
	public void updatePointTotal(int num) {
		this.pointTotal += num;
	}
	
	public int getWins() {
		return this.wins;
	}
	
	public int getLosses() {
		return this.losses;
	}
	
	public int playCard() {
		return this.handcard.remove(rand.nextInt(handcard.size())).getValue();
	}
	
	public int getModifier(int i) {
		return this.handcard.get(i-1).getValue();
	}
	
	public boolean standStatus() {
		return this.stand;
	}
	
	public void setStand(boolean b) {
		this.stand = b;
	}
	
	
}
