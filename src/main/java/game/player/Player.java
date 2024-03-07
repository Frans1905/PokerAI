package game.player;

import game.actions.Action;

public interface Player {
	Action getPlayerAction();
	long getChipCount();
	void setChipCount(long chipCount);
	String getPlayerName();
}
