package game.actions;

public interface Action {
	public ActionType getMove();
	public double getMoveValue();
	public double getRelativeMoveValue();
}
