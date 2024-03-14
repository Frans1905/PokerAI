package game.player.stub;

import game.Game;
import game.actions.Action;
import game.actions.Actions;
import game.player.DefaultPlayer;

public class CallPlayer extends DefaultPlayer {

	@Override
	public Action getPlayerAction(Game game) {
		// TODO Auto-generated method stub
		setBetChipCount(game.getCallChipCount());

		Action a;
		if (game.getCallChipCount() == 0) {
			a = Actions.getCheckAction();
		}
		else {
			a = Actions.getCallAction(game.getCallChipCount());
		}
		setLastAction(a);
		return a;
	}

}
