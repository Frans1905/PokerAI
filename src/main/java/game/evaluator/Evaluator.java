package game.evaluator;

import java.util.Map;

import game.player.Player;

public interface Evaluator {

	public Map<Player, Long> evaluatePlayers();
}
