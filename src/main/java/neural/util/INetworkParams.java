package neural.util;

import java.util.List;

import neural.activation.ActivationFunction;

public interface INetworkParams {
	
	List<Integer> getLayers();

	ActivationFunction getOutputLayerRegressionActivation();
	
	ActivationFunction getOutputLayerClassActivation();

	ActivationFunction getHiddenLayerActivation();
	
}
