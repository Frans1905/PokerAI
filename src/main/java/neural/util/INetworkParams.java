package neural.util;

import java.util.List;

import game.environment.NeuralEnvironment;
import neural.activation.ActivationFunction;

public interface INetworkParams {
	
	NeuralEnvironment getEnvironment();
	
	List<Integer> getLayers();

	ActivationFunction getOutputLayerRegressionActivation();
	
	ActivationFunction getOutputLayerClassActivation();

	ActivationFunction getHiddenLayerActivation();
	
}
