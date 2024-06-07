package test.game;

import java.util.List;

import game.cards.Card;
import game.dealer.Dealer;
import game.evaluator.Evaluator;
import game.player.Player;

public class TestGame extends game.Game {

	public TestGame(List<Player> players, Evaluator ev) {
		super(players, ev);
	}
	
	public void setBoardCards(Card c1, Card c2, Card c3, Card c4, Card c5)
	{
		getCardsOnBoard().clear();
		getCardsOnBoard().add(c1);
		getCardsOnBoard().add(c2);
		getCardsOnBoard().add(c3);
		getCardsOnBoard().add(c4);
		getCardsOnBoard().add(c5);
	}

	
	public void takeChipsToPot()
	{
		addChipsToPot();
	}

}
