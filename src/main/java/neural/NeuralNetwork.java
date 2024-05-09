package neural;

import java.util.List;

import org.nd4j.linalg.api.ndarray.INDArray;

import neural.randomizer.Randomizer;

public interface NeuralNetwork {

	void fromParents(NeuralNetwork parent1, NeuralNetwork parent2);
	void mutate();
	void copy(NeuralNetwork source);
	void randomize(Randomizer r);
	INDArray process(INDArray input);
	List<Layer> getLayers();
}
