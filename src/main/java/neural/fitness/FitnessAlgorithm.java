package neural.fitness;

import java.util.List;
import java.util.Map;

import game.environment.NeuralEnvironment;
import neural.NeuralNetwork;

public interface FitnessAlgorithm {
	void setEnvironment(NeuralEnvironment env);
	void addNetwork(NeuralNetwork net);
	void addNetworks(List<NeuralNetwork> net);
	void clearNetworks();
	Map<NeuralNetwork, Float> calculateFitness();
	Map<NeuralNetwork, Float> getFitnesses();
	FitnessTracker getTracker();
}
