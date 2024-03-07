package game.environment;

import game.Game;
import game.actions.Action;

public interface Environment {

	public Action getInput(Game game);
}
