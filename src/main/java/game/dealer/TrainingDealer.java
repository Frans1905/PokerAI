package game.dealer;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.cards.Deck;

public class TrainingDealer extends Dealer {
	private List<Card> drawn;
	private List<Card> roundCards;
	private int roundIndex;
	private int maxIndex;
	
	public TrainingDealer(int playerCount) {
		super();
		this.drawn = new ArrayList<>();
		this.roundCards = new ArrayList<>();
		this.roundIndex = -1;
		this.maxIndex = playerCount;
		drawTrainingBoardCards();
	}
	
    private void drawTrainingBoardCards() {
    	// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
			Card c = super.drawCard();
			drawn.add(c);
		}
	}
    
	@Override	
	public Card drawCard() {
		return roundCards.remove(0);
	}
	
	@Override
	public void reshuffleCards() {
		roundIndex++;
		if (roundIndex == maxIndex) {
			drawn.clear();
			this.cardDeck.reshuffle();
			drawTrainingBoardCards();
			roundIndex = 0;
		}
		roundCards.clear();
		for (int i = 0; i < 5; i++) {
			roundCards.add(drawn.get(i));
		}
	}
	
	
}
