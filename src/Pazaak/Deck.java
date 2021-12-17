package Pazaak;

public class Deck
{
    private Card head;


    public Deck()
    {
        this.head = null;
    }

    public Card getHead()
    {
        return this.head;
    }

    //used for generating the deck
    public void push(int value)
    {
        Card oldHead = this.head;
        this.head = new Card(value);
        this.head.setNext(oldHead);
    }
    
    public void push(Card card) {
		Card oldHead = head;
		head = card;
		head.setNext(oldHead);
	}

    //drawing a card
    public Card pop()
    {
        if (this.head == null)
        {
            return null;
        } else
        {
            Card result = this.head;
            this.head = this.head.getNext();
            return result;
        }
    }

    //checking if we can actually draw a card (not empty)
    public boolean isEmpty()
    {
        if (this.head == null)
        {
            return true;
        }
        return false;
    }

    //just used for testing, making sure deck is populated (should be 40 when made)
    public int size()
    {
        Card current = this.head;
        int count = 0;
        if (this.head == null)
        {
            return count;
        } else
        {
            count++;
            current = current.getNext();
        }

        while (current != null)
        {
            count++;
            current = current.getNext();
        }

        return count;
    }


}
