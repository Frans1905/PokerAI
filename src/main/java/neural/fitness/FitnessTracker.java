package neural.fitness;

import java.util.Map;

import game.Game;
import game.actions.Action;
import neural.NeuralNetwork;

public interface FitnessTracker {
	void addPoints(NeuralNetwork net, float points);
	void addGameResults(NeuralNetwork net, float chips);
	void addRoundResults(NeuralNetwork net, Game game, int index);
	void informAction(NeuralNetwork net, Action action);
	void subtractPoints(NeuralNetwork net, float points);
	Map<NeuralNetwork, Float> getFitnesses();
	void reset();
}
