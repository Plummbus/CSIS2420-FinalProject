package Pazaak;

import java.util.ArrayList;

public class Player {

	private String name;
	private int money;
	private int currentBet;
	private int pointTotal;
	private boolean wonBet;
	private ArrayList<HandCard> handcard;
	private int wins;
	private int losses;
	private boolean stand;
	
	public Player (String name) {
		this.name = name;
		this.money = 500;
		this.currentBet = 0;
		this.pointTotal = 0;
		this.wonBet = false;
		this.handcard = new ArrayList<HandCard>();
		this.wins = 0;
		this.losses = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public int getCurrentBet() {
		return this.currentBet;
	}
	
	public int getPointTotal() {
		return this.pointTotal;
	}
	
	public boolean hasWonBet() {
		return this.wonBet;
	}
	
	public int getWins() {
		return this.wins;
	}
	
	public int getLosses() {
		return this.losses;
	}
	
	public void setCurrentBet(int bet) {
		this.currentBet = bet;
	}
	
	public void setWonBet(boolean value) {
		this.wonBet = value;
	}
	
	public void win() {
		this.wins++;
	}
	
	public void lose() {
		this.losses++;
	}
	
	//console will direct player to choose between 1-4
	//those line up with HandCard indices 0-3
	//
	public int getModifier(int i) {
		return this.handcard.get(i-1).getValue();
	}
	
	public void addHandCard(int val) {
		this.handcard.add(new HandCard(val));
	}
	
	public void wipeHand() {
		this.handcard = new ArrayList<HandCard>();
	}
	
	public void updateMoney() {
		if (hasWonBet()) {
			this.money += Math.round(getCurrentBet() * 1.5f);
		} else {
			this.money -= getCurrentBet();
		}
	}
	
	public void updatePointTotal(int num) {
		this.pointTotal += num;
	}
	
	public boolean standStatus() {
		return this.stand;
	}
	
	public void setStand(boolean b) {
		this.stand = b;
	}
}
