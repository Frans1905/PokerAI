package neural.fitness;

import java.util.List;
import java.util.Map;

import neural.NeuralNetwork;

public interface FitnessAlgorithm {
	void addNetwork(NeuralNetwork net);
	void addNetworks(List<NeuralNetwork> net);
	void clearNetworks();
	Map<NeuralNetwork, Float> calculateFitness();
	Map<NeuralNetwork, Float> getFitnesses();
}
