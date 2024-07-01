package game.evaluator;

import java.util.List;

import game.Game;
import game.actions.ActionType;
import game.cards.Card;
import game.cards.CardPair;
import game.cards.CardRank;
import game.cards.CardSuit;
import game.evaluator.jmp.JmpEvaluator;
import game.player.Player;

public class BlindEvaluator {
	
	final static Card C1 = new Card(CardSuit.CLUBS, CardRank.ACE);
	final static Card C2 = new Card(CardSuit.DIAMONDS, CardRank.ACE);
	final static Card C3 = new Card(CardSuit.HEARTS, CardRank.TWO);
	final static Card C4 = new Card(CardSuit.SPADES, CardRank.FIVE);
	final static Card C5 = new Card(CardSuit.HEARTS, CardRank.SEVEN);
	final static int MAX_HAND_STRENGTH = new JmpEvaluator().findStrongestCombination(new CardPair(C1, C2), List.of(C3, C4, C5));

	public static float evaluateCards(List<Card> cards) {
		float maxval = 0;
		for (int i = 0; i < cards.size() - 1; i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				float strength = evaluateHandStrength(new CardPair(cards.get(i), cards.get(j)));
				if (strength > maxval) {
					maxval = strength;
				}
			}
		}
		return maxval;
	}

	public static float evaluateHandStrength(CardPair pair) {

		String ranks = pair.getFirstCard().toString().substring(0,1) + pair.getSecondCard().toString().substring(0,1);
		if (pair.getFirstCard().getRank().ordinal() < pair.getSecondCard().getRank().ordinal())
			ranks = ranks.substring(1) + ranks.substring(0,1);



		boolean suited = pair.getFirstCard().getSuit().ordinal() == pair.getSecondCard().getSuit().ordinal();

		double strength = 0.0;

		// Evaluate suited hands
		if (suited) {
			switch (ranks) {
			case "AK": case "AQ": case "AJ": case "KQ": case "KJ": case "QJ":
				strength = 0.90;
				break;
			case "AT": case "KT": case "QT": case "JT":
				strength = 0.80;
				break;
			case "A9": case "K9": case "Q9": case "J9": case "T9":
				strength = 0.70;
				break;
			case "98": case "87": case "76": case "65": case "54":
				strength = 0.60;
				break;
			default:
				strength = 0.2;
				break;
			}
		} else {  // Evaluate offsuit hands
			switch (ranks) {
			case "AA": case "KK": case "QQ": case "JJ":
				strength = 0.95;
				break;
			case "AK": case "AQ": case "AJ": case "KQ": case "KJ": case "QJ":
				strength = 0.85;
				break;
			case "TT": case "99": case "88":
				strength = 0.75;
				break;
			case "77": case "66": case "55": case "AT": case "KT": case "QT": case "JT":
				strength = 0.65;
				break;
			default:
				strength = 0.2;
				break;
			}
		}

		float maxRatio = MAX_HAND_STRENGTH / 7462f;
		return (float) ((1f - maxRatio) - maxRatio * (1 - strength));
	}

	/**
	 * @param p
	 * @param g
	 * @return
	 */
	public static ActionType  prefferedAction(Player p, Game g) {
		double strength = evaluateHandStrength(p.getCards());

		float callThreshold = 0.79f;
		float raiseThreshold = 0.89f;

		if (strength > raiseThreshold)
			return ActionType.RAISE;
		else if (strength > callThreshold || p.getBetChipCount() >= g.getCallChipCount())	        // Decision making based on hand strength
			return ActionType.CALL;
		else 
			return ActionType.FOLD;

	}

}
