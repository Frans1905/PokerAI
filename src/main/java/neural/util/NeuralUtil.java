package neural.util;

import neural.activation.ActivationFunction;
import neural.activation.SigmoidActivationFunction;
import neural.crossing.CrossingAlgorithm;
import neural.crossing.RandomCrossingAlgorithm;
import neural.fitness.ChipsAvgFitnessAlgorithm;
import neural.fitness.FitnessAlgorithm;
import neural.mutation.MutationAlgorithm;
import neural.mutation.StandardDeviationMutationAlgorithm;

public class NeuralUtil {

	public static final ActivationFunction SIGMOID = new SigmoidActivationFunction();
	public static final CrossingAlgorithm RANDOM = new RandomCrossingAlgorithm();
	public static final FitnessAlgorithm AVG_CHIPS = new ChipsAvgFitnessAlgorithm();
	public static final MutationAlgorithm STD_DEV = new StandardDeviationMutationAlgorithm();
	
	public static final INeuralNetworkParams DEFAULT_PARAMS = new NeuralNetworkParams(SIGMOID, SIGMOID, STD_DEV, RANDOM, AVG_CHIPS);
}
