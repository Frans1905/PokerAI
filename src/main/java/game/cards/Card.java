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

	public CardSuit getSuit() {
		return suit;
	}

	public CardRank getRank() {
		return rank;
	}
	
	@Override
	public String toString() {
		if (this.getValue() < 10) {
			return Integer.toString(getValue());
		}
		return this.getRank().toString();
	}
	
}
