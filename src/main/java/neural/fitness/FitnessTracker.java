package neural.fitness;

import java.util.HashMap;
import java.util.Map;

import game.Game;
import game.actions.Action;
import game.actions.ActionType;
import game.player.Player;
import neural.NeuralNetwork;

public interface FitnessTracker {
	Map<ActionType, Integer> actions = new HashMap<>();
	void addPoints(NeuralNetwork net, float points);
	void addGameResults(NeuralNetwork net, float chips);
	void addRoundResults(NeuralNetwork net, Game game, int index);
	default void informAction(NeuralNetwork net, Action action, int playerIndex, Player p) {
		actions.put(action.getActionType(), actions.getOrDefault(action.getActionType(), 0) + 1);
	};
	default Map<ActionType, Integer> getActions() {
		return actions;
	}
	void subtractPoints(NeuralNetwork net, float points);
	Map<NeuralNetwork, Float> getFitnesses();
	default void reset() {
		actions.clear();
	};
}
