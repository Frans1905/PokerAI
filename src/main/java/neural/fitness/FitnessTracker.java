package neural.fitness;

import java.util.Map;

import game.actions.Action;
import neural.NeuralNetwork;

public interface FitnessTracker {
	void addPoints(NeuralNetwork net, float points);
	void addGameResults(NeuralNetwork net, float chips);
	void addRoundResults(NeuralNetwork net, float chips, int position);
	void informAction(NeuralNetwork net, Action action);
	void subtractPoints(NeuralNetwork net, float points);
	Map<NeuralNetwork, Float> getFitnesses();
	void reset();
}
