package game.cards;

public class Card {
	private CardSuit suit;
	private CardRank rank;
	
	public Card(CardSuit suit, CardRank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	public int getValue() {
		return rank.ordinal() + 2; 
	}
}
