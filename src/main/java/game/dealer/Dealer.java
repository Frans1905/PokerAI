package game.dealer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import game.actions.ActionType;
import game.cards.Card;
import game.cards.CardPair;
import game.cards.Deck;
import game.player.Player;

public class Dealer {

	Deck cardDeck;
	Map<Player, Long> winnings;

	public Dealer() {
		this.cardDeck = new Deck();
		this.winnings = new HashMap<>();
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
	
	public Map<Player, Long> getLastWinnings() {
		return this.winnings;
	}

	public Map<Player, Long> divideChipsFromPot(Map<Player, Integer> strengthResults, long potChipCount) {
		// TODO Auto-generated method stub
		Map<Player, Long> winnings = new HashMap<>();
		List<Player> maxPlayers = new LinkedList<>();
		for (Entry<Player, Integer> entry : strengthResults.entrySet()) {
			if (entry.getKey().getLastAction().getActionType() == ActionType.NONE) {
				continue;
			}
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
			Player p = maxPlayers.get(index);
			long min = values.get(index);
			long max = values.get(index++);
			profitPlayers.add(p);
			while (index < maxPlayers.size() &&
					Integer.compare(strengthResults.get(p), strengthResults.get(maxPlayers.get(index)))== 0) {
				min = Long.min(min, values.get(index));
				max = Long.max(max, values.get(index));
				profitPlayers.add(maxPlayers.get(index++));
			}

			if (max == 0) {
				continue;
			}

			while(true) {
				
				long sum = 0;
				List<Player> removeList = new ArrayList<>();
				for (int i = start; i < values.size(); i++) {
					long chips = Long.min(values.get(i), min);
					sum += chips;
					potChipCount -= chips;
					values.set(i, values.get(i) - chips);
					if (values.get(i) == 0 && profitPlayers.contains(maxPlayers.get(i))) {
						removeList.add(maxPlayers.get(i));
					}
				}

				for (Player player : profitPlayers) {
					long wonChips = (long) (sum / profitPlayers.size());
					player.giveChips(wonChips);					
					winnings.put(player, winnings.getOrDefault(player, 0l) + wonChips);
				}
				
				for (Player removed : removeList) {
					profitPlayers.remove(removed);
				}
				
				if (profitPlayers.size() == 0) break;
				
				max = Long.MIN_VALUE;
				min = Long.MAX_VALUE;
				for (int i = start; i < index; i++) {
					if (!profitPlayers.contains(maxPlayers.get(i))) continue;
					min = Long.min(min, values.get(i));
					max = Long.max(max, values.get(i));
				}

				if (max == 0) {
					break;
				}
			}
		}
		this.winnings = winnings;
		return winnings;

	}
}
