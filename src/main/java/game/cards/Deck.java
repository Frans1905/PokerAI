package game.cards;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	private List<Card> cards;
	
	public Deck() {
		cards = new ArrayList<>(52);
		initDeck();
	}

	private void initDeck() {
		for (CardSuit suit : CardSuit.values()) {
			for (CardRank rank : CardRank.values()) {
				Card card = new Card(suit, rank);
				cards.add(card);
			}
		}
	}
	
	public int getNumberOfCards() {
		return cards.size();
	}
	
	public Card drawCard() {
		int rand = (int)(Math.random() * getNumberOfCards());
		return cards.remove(rand);
	}
	
	public void reshuffle() {
		cards.clear();
		initDeck();
	}
}
