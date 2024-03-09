package game.player;

import game.actions.Action;

public interface Player {
	Action getPlayerAction();
	Action getLastAction();
	void setLastAction(Action action);
	long getChipCount();
	void setChipCount(long chipCount);
	default void giveChips(long amount) {
		setChipCount(getChipCount() + amount);
	}
	default void takeChips(long amount) {
		if (amount > getChipCount()) {
			setChipCount(0);
		}
		else {
			setChipCount(getChipCount() - amount);
		}
	}
	String getPlayerName();
}
