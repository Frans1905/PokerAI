package game.dealer;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.cards.CardPair;
import game.cards.Deck;

public class Dealer {

	Deck cardDeck;
	
	public Dealer() {
		this.cardDeck = new Deck();
	}
	
	public List<CardPair> dealPairs(int numOfPlayers) {
		List<CardPair> pairs = new ArrayList<CardPair>(numOfPlayers);
		dealPairs(pairs);
		return pairs;
	}
	
	public void dealPairs(List<CardPair> pairs) {
		for (int i = 0; i < pairs.size(); i++) {
			pairs.set(i, new CardPair(drawCard(), drawCard()));
		}
	}
	
	public void reshuffleCards() {
		cardDeck.reshuffle();
	}
	
	public Card drawCard() {
		return cardDeck.drawCard();
	}
}
