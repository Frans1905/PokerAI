package neural.selection;

import java.util.Map;

import neural.NeuralNetwork;

public interface SelectionAlgorithm {

	NeuralNetwork select(Map<NeuralNetwork, Float> fitnesses);
}
