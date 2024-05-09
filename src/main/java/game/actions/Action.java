package game.actions;

import game.player.Player;

public interface Action {
	public ActionType getActionType();
	public long getMoveValue();
	public float getRelativeMoveValue();
	public boolean isValid(Player curPlayer);
}
