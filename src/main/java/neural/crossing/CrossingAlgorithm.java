package neural.crossing;

import java.util.Map;

import neural.NeuralNetwork;

public interface CrossingAlgorithm {
	public void cross(NeuralNetwork child, NeuralNetwork parent1, NeuralNetwork parent2, Map<NeuralNetwork, Float> fitnesses);
}
