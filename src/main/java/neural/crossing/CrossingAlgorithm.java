package neural.crossing;

import neural.NeuralNetwork;

public interface CrossingAlgorithm {
	public void cross(NeuralNetwork child, NeuralNetwork parent1, NeuralNetwork parent2);
}
