package neural.util;

import java.util.List;

import neural.activation.ActivationFunction;
import neural.activation.LinearBoundedActivationFunction;
import neural.activation.ReLUActivationFunction;
import neural.activation.SigmoidActivationFunction;
import neural.crossing.CrossingAlgorithm;
import neural.crossing.MeanCrossingAlgorithm;
import neural.crossing.RandomCrossingAlgorithm;
import neural.fitness.ChipsAvgFitnessTracker;
import neural.fitness.DefaultFitnessAlgorithm;
import neural.fitness.FitnessAlgorithm;
import neural.fitness.MoveEvalFitnessTracker;
import neural.fitness.RankFitnessTracker;
import neural.fitness.StrengthChipRatioFitnessTracker;
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
	public static final ActivationFunction LINEAR_BOUNDED = new LinearBoundedActivationFunction();
	public static final CrossingAlgorithm RANDOM = new RandomCrossingAlgorithm();
	public static final CrossingAlgorithm MEAN = new MeanCrossingAlgorithm();
	public static final FitnessAlgorithm AVG_CHIPS = new DefaultFitnessAlgorithm(new ChipsAvgFitnessTracker());
	public static final FitnessAlgorithm MOVE_EVAL = new DefaultFitnessAlgorithm(new MoveEvalFitnessTracker());
	public static final FitnessAlgorithm STRENGTH_CHIP_RATIO = new DefaultFitnessAlgorithm(new StrengthChipRatioFitnessTracker());
	public static final FitnessAlgorithm RANK = new DefaultFitnessAlgorithm(new RankFitnessTracker());
	public static final MutationAlgorithm STD_DEV = new StandardDeviationMutationAlgorithm();
	public static final Initializator XAVIER_INIT = new XavierInitializator();
	public static final int DEFAULT_GENERATION_SIZE = 50;
	public static final int DEFAULT_NUM_OF_GENERATIONS = 100;
	public static final int DEFAULT_ELITISM_COUNT = 2;
	
	public static final List<ActivationFunction> ACTIVATION_FUNCTIONS = List.of(SIGMOID, RELU, LINEAR_BOUNDED);
	
	public static final List<CrossingAlgorithm> CROSSING_ALGORITHM = List.of(RANDOM, MEAN);
	
	public static final List<FitnessAlgorithm> FITNESS_FUNCTIONS = List.of(AVG_CHIPS, MOVE_EVAL, STRENGTH_CHIP_RATIO, RANK);

	
	public static final IEvolutionParams DEFAULT_EVOLUTION_PARAMS = new EvolutionParams(TOURNAMENT_SELECTION, STD_DEV, RANDOM, AVG_CHIPS, 
			XAVIER_INIT, DEFAULT_GENERATION_SIZE, DEFAULT_NUM_OF_GENERATIONS, DEFAULT_ELITISM_COUNT);
	
	public static final List<Integer> DEFAULT_LAYER_SIZES = List.of(48, 20, 20, 10, 2);
	public static final INetworkParams DEFAULT_POKER_NETWORK_PARAMS = new NetworkParams(DEFAULT_LAYER_SIZES, SIGMOID, LINEAR_BOUNDED, SIGMOID);
}
