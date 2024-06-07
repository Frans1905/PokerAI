package game.evaluator.jmp;

import static java.util.Map.entry;

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
	
	
	public JmpEvaluator() {

	}
	
	@Override
	public Map<Player, Integer> evaluatePlayers(Game game) {
		// TODO Auto-generated method stub
		Map<Player, Integer> map = new HashMap<>();
		for (Player p : game.getPlayers()) {
			if (p.getChipCount() == 0 && p.getTotalBetChipCount() == 0) {
				continue;
			}
			if (!game.getActivePlayers().contains(p)) {
				map.put(p, Integer.MAX_VALUE);
				continue;
			}
			int value = findStrongestCombination(p.getCards(), game.getCardsOnBoard());
			map.put(p, value);
		}
		return map;
	}

	public int findStrongestCombination(CardPair cards, List<Card> cardsOnBoard) {
		// TODO Auto-generated method stub
		List<game.evaluator.jmp.Card> jmpCardsOnBoard = cardsOnBoard.stream().map((c) -> 
			new game.evaluator.jmp.Card(c.getValue() - 2, SUIT_MAP.get(c.getSuit()))).toList();
		game.evaluator.jmp.Card[] jmpCards = new game.evaluator.jmp.Card[7];
		jmpCards[0] = new game.evaluator.jmp.Card(cards.getFirstCard().getValue() - 2, 
				SUIT_MAP.get(cards.getFirstCard().getSuit()));
		jmpCards[1] = new game.evaluator.jmp.Card(cards.getSecondCard().getValue() - 2, 
				SUIT_MAP.get(cards.getSecondCard().getSuit()));
		for (int i = 2; i < 7; i++) {
			Card c = cardsOnBoard.get(i - 2);
			jmpCards[i] = new game.evaluator.jmp.Card(c.getValue() - 2, 
					SUIT_MAP.get(c.getSuit()));
		}
		
		int min = Integer.MAX_VALUE;
		
		for (int i = 0; i < 6; i++) {
			for (int j = i + 1; j < 7; j++) {
				int index = 0;
				game.evaluator.jmp.Card[] cardArr = new game.evaluator.jmp.Card[5]; 
				for (int k = 0; k < 7; k++) {
					if (k != i && k != j) {
						cardArr[index++] = jmpCards[k];
					}
				}
				int value = Hand.evaluate(cardArr);
				if (value < min) {
					min = value;
				}
			}
		}
		
		return min;
	}

}
