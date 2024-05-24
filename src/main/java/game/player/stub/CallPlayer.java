package game.player.stub;

import game.Game;
import game.actions.Action;
import game.actions.Actions;
import game.environment.NoneEnvironment;
import game.player.DefaultPlayer;

public class CallPlayer extends DefaultPlayer {
	
	public CallPlayer() {
		super("CallPlayer", new NoneEnvironment());
	}
		
	
	@Override
	public Action getPlayerAction(Game game) {
		// TODO Auto-generated method stub
		setBetChipCount(game.getCallChipCount());

		Action a;
		if (game.getCallChipCount() == 0) {
			a = Actions.getCheckAction(0);
		}
		else {
			a = Actions.getCallAction(game.getCallChipCount());
		}
		setLastAction(a);
		return a;
	}

}
