package Pazaak;

public class HandCard {

	private int value;

	//will be some int between -6 and 6
    public HandCard(int v)
    {
        this.value = v;
    }

    public int getValue() {
    	return this.value;
    }
}
