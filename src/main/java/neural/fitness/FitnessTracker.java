package neural.fitness;

import java.util.Map;

import game.Game;
import game.actions.Action;
import game.player.Player;
import neural.NeuralNetwork;

public interface FitnessTracker {
	void addPoints(NeuralNetwork net, float points);
	void addGameResults(NeuralNetwork net, float chips);
	void addRoundResults(NeuralNetwork net, Game game, int index);
	void informAction(NeuralNetwork net, Action action, int playerIndex, Player p);
	void subtractPoints(NeuralNetwork net, float points);
	Map<NeuralNetwork, Float> getFitnesses();
	void reset();
}
