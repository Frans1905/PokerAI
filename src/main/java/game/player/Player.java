package game.player;

import game.actions.Action;

public interface Player {
	Action getPlayerAction();
	Action getLastAction();
	void setLastAction(Action action);
	long getChipCount();
	void setChipCount(long chipCount);
	String getPlayerName();
}
