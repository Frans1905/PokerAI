package neural.fitness;

import java.util.Map;

import neural.NeuralNetwork;

public interface FitnessAlgorithm {
	void addNetwork(NeuralNetwork net);
	void calculateFitness();
	Map<NeuralNetwork, Integer> getFitnesses();
}
