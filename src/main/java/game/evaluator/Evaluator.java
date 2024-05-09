package game.evaluator;

import java.util.Map;

import game.Game;
import game.player.Player;

public interface Evaluator {

	public Map<Player, Integer> evaluatePlayers(Game game);
}
