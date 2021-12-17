package Pazaak;

public class Card {


    private int value;
    private Card next;

    public Card(int v)
    {
        this.value = v;
        this.next = null;
    }

    public void setNext(Card card)
    {
        this.next = card;
    }

    public Card getNext()
    {
        return this.next;
    }

    public int getValue()
    {
        return this.value;
    }
	
	@Override
	public String toString() {
		return String.format("%d", value);
	}
}