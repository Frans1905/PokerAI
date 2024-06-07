package game.evaluator;

import java.util.List;
import java.util.Map;

import game.Game;
import game.cards.Card;
import game.cards.CardPair;
import game.player.Player;

public interface Evaluator {

	public Map<Player, Integer> evaluatePlayers(Game game);
	
	int findStrongestCombination(CardPair pair, List<Card> cardsOnTable);
}
