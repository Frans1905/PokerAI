package neural.util;

import neural.activation.ActivationFunction;
import neural.crossing.CrossingAlgorithm;
import neural.mutation.MutationAlgorithm;

public interface IEvolutionParams {

	ActivationFunction getHiddenLayerActivation();
	
	ActivationFunction getOutputLayerActivation();
	
	CrossingAlgorithm getCrossingAlgorithm();
	
	MutationAlgorithm getMutationAlgorithm();
	
	int getGenerationSize();
	
	int getNumOfGenerations();
}
