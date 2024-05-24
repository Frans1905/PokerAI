package game.player;

import game.Game;
import game.actions.Action;
import game.cards.CardPair;
import game.environment.Environment;

public interface Player {
	void setUpPlayerForNewRound();
	Action getPlayerAction(Game game);
	Action getLastAction();
	void setLastAction(Action action);
	long getChipCount();
	void setChipCount(long chipCount);
	
	void setCards(CardPair pair);
	CardPair getCards();
	
	default void giveChips(long amount) {
		setChipCount(getChipCount() + amount);
	}
	
	default long takeChips(long amount) {
		if (amount > getChipCount()) {
			amount = getChipCount();
			setChipCount(0);
			return amount;
			
		}
		else {
			setChipCount(getChipCount() - amount);
			return amount;
		}
	}
	
	long takeSmallBlind(long smallBlind);
	long takeBigBlind(long smallBlind);
	long takeBetChips();
	long getBetChipCount();
	String getPlayerName();
	Long getTotalBetChipCount();
	Environment getEnvironment();
	void assignIndex(int index);
}
