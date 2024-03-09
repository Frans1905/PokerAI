package game.evaluator;

import java.util.Map;

import game.Game;
import game.player.Player;

public class DefaultEvaluator implements Evaluator {

	private Game game;
	
	public DefaultEvaluator(Game game) {
		this.game = game;
	}
	
	@Override
	public Map<Player, Long> evaluatePlayers() {
		// TODO Auto-generated method stub
		return null;
	}

}
