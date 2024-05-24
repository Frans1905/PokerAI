package game.player.stub;

import game.Game;
import game.actions.Action;
import game.actions.Actions;
import game.environment.NoneEnvironment;
import game.player.DefaultPlayer;

public class RaisePlayer extends DefaultPlayer {

	public RaisePlayer() {
		super("RaisePlayer", new NoneEnvironment());
	}
	
	@Override
	public Action getPlayerAction(Game game) {
		// TODO Auto-generated method stub
		setBetChipCount(game.getCallChipCount() + 50);
		Action a = Actions.getRaiseAction(game.getCallChipCount(), game.getCallChipCount() + 50);
		setLastAction(a);
		return a;
	}
}
