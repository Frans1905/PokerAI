package neural.util;

import java.util.List;

import neural.activation.ActivationFunction;
import neural.crossing.CrossingAlgorithm;
import neural.mutation.MutationAlgorithm;

public interface INetworkParams {
	
	List<Integer> getLayers();

	ActivationFunction getOutputLayerActivation();

	ActivationFunction getHiddenLayerActivation();
	
}
