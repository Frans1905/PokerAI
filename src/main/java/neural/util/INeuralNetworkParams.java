package neural.util;

import neural.activation.ActivationFunction;
import neural.crossing.CrossingAlgorithm;
import neural.mutation.MutationAlgorithm;

public interface INeuralNetworkParams {

	ActivationFunction getHiddenLayerActivation();
	
	ActivationFunction getOutputLayerActivation();
	
	CrossingAlgorithm getCrossingAlgorithm();
	
	MutationAlgorithm getMutationAlgorithm();
}
