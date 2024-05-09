package neural.util;

import neural.crossing.CrossingAlgorithm;
import neural.mutation.MutationAlgorithm;
import neural.transformation.ActivationFunction;

public interface INeuralNetworkParams {

	ActivationFunction getHiddenLayerActivation();
	
	ActivationFunction getOutputLayerActivation();
	
	CrossingAlgorithm getCrossingAlgorithm();
	
	MutationAlgorithm getMutationAlgorithm();
}
