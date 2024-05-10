package neural.fitness;

import java.util.Map;

import neural.NeuralNetwork;

public interface FitnessAlgorithm {
	void addNetwork(NeuralNetwork net);
	Map<NeuralNetwork, Float> calculateFitness();
	Map<NeuralNetwork, Float> getFitnesses();
}
