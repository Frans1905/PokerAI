package game.player.stub;

import game.Game;
import game.actions.Action;
import game.actions.Actions;
import game.environment.NoneEnvironment;
import game.player.DefaultPlayer;

public class FoldPlayer extends DefaultPlayer {
	
	public FoldPlayer() {
		super("FoldPlayer", new NoneEnvironment());
	}
	
	@Override
	public Action getPlayerAction(Game game) {
		// TODO Auto-generated method stub
		setLastAction(Actions.getFoldAction());
		return Actions.getFoldAction();
	}
}
