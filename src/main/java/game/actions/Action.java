package game.actions;

public interface Action {
	public ActionType getActionType();
	public double getMoveValue();
	public double getRelativeMoveValue();
}
