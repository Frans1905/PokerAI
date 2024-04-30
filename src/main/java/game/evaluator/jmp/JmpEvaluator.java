package game.evaluator.jmp;

import static java.util.Map.entry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Game;
import game.cards.Card;
import game.cards.CardPair;
import game.cards.CardSuit;
import game.evaluator.Evaluator;
import game.player.Player;

public class JmpEvaluator implements Evaluator {

	public static final Map<CardSuit, Integer> SUIT_MAP = Map.ofEntries(
			entry(CardSuit.CLUBS, 0x8000),
			entry(CardSuit.DIAMONDS, 0x4000),
			entry(CardSuit.HEARTS, 0x2000),
			entry(CardSuit.SPADES, 0x1000));
	
	Game game;
	
	public JmpEvaluator(Game game) {
		this.game= game;
	}
	
	@Override
	public Map<Player, Integer> evaluatePlayers() {
		// TODO Auto-generated method stub
		Map<Player, Integer> map = new HashMap<>();
		for (Player p : game.getActivePlayers()) {
			int value = findStrongestCombination(p.getCards(), game.getCardsOnBoard());
			map.put(p, value);
		}
		return map;
	}

	private int findStrongestCombination(CardPair cards, List<Card> cardsOnBoard) {
		// TODO Auto-generated method stub
		List<game.evaluator.jmp.Card> jmpCardsOnBoard = cardsOnBoard.stream().map((c) -> 
			new game.evaluator.jmp.Card(c.getValue() - 2, SUIT_MAP.get(c.getSuit()))).toList();
		game.evaluator.jmp.Card[] jmpCards = (game.evaluator.jmp.Card[]) cards.getCardList().stream().map((c) -> 
			new game.evaluator.jmp.Card(c.getValue() - 2, SUIT_MAP.get(c.getSuit()))).toArray();
		
		int max = 0;
		
		for (int i = 0; i < jmpCardsOnBoard.size() - 2; i++) {
			for (int j = i + 1; j < jmpCardsOnBoard.size() - 1; j++) {
				for (int k = j + 1; k < jmpCardsOnBoard.size(); k++) {
					game.evaluator.jmp.Card[] cardArr = Arrays.copyOf(jmpCards, 5);
					cardArr[2] = jmpCardsOnBoard.get(i);
					cardArr[3] = jmpCardsOnBoard.get(j);
					cardArr[4] = jmpCardsOnBoard.get(k);
					int value = Hand.evaluate(cardArr);
					if (value > max) {
						max = value;
					}
					
				}
			}
		}
		
		return max;
	}

}
