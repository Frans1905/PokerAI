package game.evaluator;

import java.util.List;
import java.util.Map;

import game.Game;
import game.cards.Card;
import game.cards.CardPair;
import game.player.Player;

public class DefaultEvaluator implements Evaluator {

	
	public DefaultEvaluator(Game game) {
	}
	
	@Override
	public Map<Player, Integer> evaluatePlayers(Game game) {
		// TODO Auto-generated method stub
		Long potChipCount = game.getPotChipCount();
		for (Player p : game.getActivePlayers()) {
			findStrongestCombination(p.getCards(), game.getCardsOnBoard());
		}
		return null;
	}

	private void findStrongestCombination(CardPair cards, List<Card> cardsOnBoard) {
		// TODO Auto-generated method stub
		
	}

}
