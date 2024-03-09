package game.actions;

public interface Action {
	public ActionType getActionType();
	public long getMoveValue();
	public long getRelativeMoveValue();
}
