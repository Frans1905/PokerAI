package neural.util;

import neural.activation.ActivationFunction;
import neural.crossing.CrossingAlgorithm;
import neural.fitness.FitnessAlgorithm;
import neural.mutation.MutationAlgorithm;

public class NeuralNetworkParams implements INeuralNetworkParams {
	
	private ActivationFunction hiddenActivation;
	private ActivationFunction outputActivation;
	private MutationAlgorithm mutationAlg;
	private CrossingAlgorithm crossingAlg;
	private FitnessAlgorithm fitnessAlg;
	
	
	
	public NeuralNetworkParams(ActivationFunction hiddenActivation, ActivationFunction outputActivation,
			MutationAlgorithm mutationFunc, CrossingAlgorithm crossingAlg, FitnessAlgorithm fitnessAlg) {
		super();
		this.hiddenActivation = hiddenActivation;
		this.outputActivation = outputActivation;
		this.mutationAlg = mutationFunc;
		this.crossingAlg = crossingAlg;
		this.fitnessAlg = fitnessAlg;
	}

	@Override
	public ActivationFunction getHiddenLayerActivation() {
		// TODO Auto-generated method stub
		return this.hiddenActivation;
	}

	@Override
	public ActivationFunction getOutputLayerActivation() {
		// TODO Auto-generated method stub
		return this.outputActivation;
	}

	@Override
	public CrossingAlgorithm getCrossingAlgorithm() {
		// TODO Auto-generated method stub
		return this.crossingAlg;
	}

	@Override
	public MutationAlgorithm getMutationAlgorithm() {
		// TODO Auto-generated method stub
		return this.mutationAlg;
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

	public MutationAlgorithm getMutationAlg() {
		return mutationAlg;
	}

	public void setMutationAlg(MutationAlgorithm mutationAlg) {
		this.mutationAlg = mutationAlg;
	}

	public CrossingAlgorithm getCrossingAlg() {
		return crossingAlg;
	}

	public void setCrossingAlg(CrossingAlgorithm crossingAlg) {
		this.crossingAlg = crossingAlg;
	}

	public ActivationFunction getHiddenActivation() {
		return hiddenActivation;
	}

	public ActivationFunction getOutputActivation() {
		return outputActivation;
	}

	public FitnessAlgorithm getFitnessAlg() {
		return fitnessAlg;
	}

	public void setFitnessAlg(FitnessAlgorithm fitnessAlg) {
		this.fitnessAlg = fitnessAlg;
	}
	
}
