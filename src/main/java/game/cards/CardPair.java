package game.cards;

import java.util.List;

public class CardPair {

	private Card card1;
	private Card card2;
	
	public CardPair(Card c1, Card c2) {
		card1 = c1;
		card2 = c2;
	}
	
	public List<Card> getCardList() {
		return List.of(card1, card2);
	}
	
	public Card getFirstCard() {
		return card1;
	}
	
	public Card getSecondCard() {
		return card2;
	}
}
