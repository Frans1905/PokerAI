package game.dealer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import game.cards.Card;
import game.cards.CardPair;
import game.cards.Deck;
import game.player.Player;

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

	public CardPair dealPair() {
		return new CardPair(drawCard(), drawCard());
	}

	public void dealPairs(List<CardPair> pairs) {
		for (int i = 0; i < pairs.size(); i++) {
			pairs.set(i, dealPair());
		}
	}

	public void reshuffleCards() {
		cardDeck.reshuffle();
	}

	public Card drawCard() {
		return cardDeck.drawCard();
	}

	public Map<Player, Long> divideChipsFromPot(Map<Player, Integer> strengthResults, long potChipCount) {
		// TODO Auto-generated method stub
		Map<Player, Long> winnings = new HashMap<>();
		List<Player> maxPlayers = new LinkedList<>();
		for (Entry<Player, Integer> entry : strengthResults.entrySet()) {
			for (int i = 0; i <= maxPlayers.size(); i++) {
				if (i == maxPlayers.size()) {
					maxPlayers.add(entry.getKey());
					break;
				}
				else if (strengthResults.get(maxPlayers.get(i)) > entry.getValue()) {
					maxPlayers.add(i, entry.getKey());
					break;
				}
			}
		}

		int index = 0;
		List<Long> values = new LinkedList<>();
		for (int i = 0; i < maxPlayers.size(); i++) {
			values.add(maxPlayers.get(i).getTotalBetChipCount());
		}

		while (potChipCount > 0) {
			int start = index;
			List<Player> profitPlayers = new LinkedList<>();
			Player p = maxPlayers.get(index++);
			profitPlayers.add(p);
			long min = Long.MAX_VALUE;

			while (index < maxPlayers.size() &&
					strengthResults.get(p) == strengthResults.get(maxPlayers.get(index))) {
				min = Long.min(min, values.get(index));
				profitPlayers.add(maxPlayers.get(index++));
			}

			if (min == 0) {
				continue;
			}

			while(true) {
				long sum = 0;
				for (int i = start; i < values.size(); i++) {
					long chips = Long.min(values.get(i), min);
					sum += chips;
					potChipCount -= chips;
					values.set(i, values.get(i) - chips);
				}

				for (Player player : profitPlayers) {
					long wonChips = (long) (sum / profitPlayers.size());
					player.giveChips(wonChips);					
					winnings.put(player, winnings.getOrDefault(player, 0l) + wonChips);
				}

				for (int i = start; i < index; i++) {
					min = Long.min(min, values.get(i));
				}

				if (min == 0) {
					break;
				}
			}
		}
		return winnings;

	}
}
