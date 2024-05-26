package neural;

import java.util.List;

import org.nd4j.linalg.api.ndarray.INDArray;

import neural.crossing.CrossingAlgorithm;
import neural.initialization.Initializator;
import neural.mutation.MutationAlgorithm;
import neural.util.INetworkParams;

public interface NeuralNetwork {

	void fromParents(NeuralNetwork parent1, NeuralNetwork parent2, CrossingAlgorithm alg);
	void mutate(MutationAlgorithm alg);
	void copy(NeuralNetwork source);
	void initialize(Initializator i);
	INDArray process(INDArray input);
	List<Layer> getLayers();
	INetworkParams getParams();
}
