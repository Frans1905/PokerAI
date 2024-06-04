package neural.util;

import java.util.List;

import neural.activation.ActivationFunction;
import neural.activation.ReLUActivationFunction;
import neural.activation.SigmoidActivationFunction;
import neural.crossing.CrossingAlgorithm;
import neural.crossing.RandomCrossingAlgorithm;
import neural.fitness.DefaultFitnessAlgorithm;
import neural.fitness.FitnessAlgorithm;
import neural.initialization.Initializator;
import neural.initialization.XavierInitializator;
import neural.mutation.MutationAlgorithm;
import neural.mutation.StandardDeviationMutationAlgorithm;
import neural.selection.SelectionAlgorithm;
import neural.selection.TournamentSelectionAlgorithm;

public class NeuralUtil {

	public static final SelectionAlgorithm TOURNAMENT_SELECTION = new TournamentSelectionAlgorithm(15);
	public static final ActivationFunction SIGMOID = new SigmoidActivationFunction();
	public static final ActivationFunction RELU = new ReLUActivationFunction();
	public static final CrossingAlgorithm RANDOM = new RandomCrossingAlgorithm();
	public static final FitnessAlgorithm AVG_CHIPS = new DefaultFitnessAlgorithm();
	public static final MutationAlgorithm STD_DEV = new StandardDeviationMutationAlgorithm();
	public static final Initializator XAVIER_INIT = new XavierInitializator();
	public static final int DEFAULT_GENERATION_SIZE = 50;
	public static final int DEFAULT_NUM_OF_GENERATIONS = 100;
	
	public static final IEvolutionParams DEFAULT_EVOLUTION_PARAMS = new EvolutionParams(TOURNAMENT_SELECTION, STD_DEV, RANDOM, AVG_CHIPS, 
			XAVIER_INIT, DEFAULT_GENERATION_SIZE, DEFAULT_NUM_OF_GENERATIONS);
	
	public static final List<Integer> DEFAULT_LAYER_SIZES = List.of(48, 20, 20, 10, 2);
	public static final INetworkParams DEFAULT_POKER_NETWORK_PARAMS = new NetworkParams(DEFAULT_LAYER_SIZES, SIGMOID, SIGMOID);
}
