package neural.util;

import neural.crossing.CrossingAlgorithm;
import neural.mutation.MutationAlgorithm;
import neural.transformation.ActivationFunction;

public class NeuralNetworkParams implements INeuralNetworkParams {
	
	private ActivationFunction hiddenActivation;
	private ActivationFunction outputActivation;
	private MutationAlgorithm mutationAlg;
	
	
	
	public NeuralNetworkParams(ActivationFunction hiddenActivation, ActivationFunction outputActivation,
			MutationAlgorithm mutationFunc) {
		super();
		this.hiddenActivation = hiddenActivation;
		this.outputActivation = outputActivation;
		this.mutationAlg = mutationFunc;
	}

	@Override
	public ActivationFunction getHiddenLayerActivation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivationFunction getOutputLayerActivation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CrossingAlgorithm getCrossingAlgorithm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutationAlgorithm getMutationAlgorithm() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setHiddenActivation(ActivationFunction hiddenActivation) {
		this.hiddenActivation = hiddenActivation;
	}

	public void setOutputActivation(ActivationFunction outputActivation) {
		this.outputActivation = outputActivation;
	}

	public void setMutationAlgorithm(MutationAlgorithm mutationFunc) {
		this.mutationAlg = mutationFunc;
	}

	
	
}
