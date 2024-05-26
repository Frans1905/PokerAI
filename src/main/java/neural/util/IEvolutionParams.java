package neural.util;

import neural.crossing.CrossingAlgorithm;
import neural.fitness.FitnessAlgorithm;
import neural.initialization.Initializator;
import neural.mutation.MutationAlgorithm;
import neural.selection.SelectionAlgorithm;

public interface IEvolutionParams {
	
	SelectionAlgorithm getSelectionAlgorithm();
	
	CrossingAlgorithm getCrossingAlgorithm();
	
	MutationAlgorithm getMutationAlgorithm();
	
	FitnessAlgorithm getFitnessAlgorithm();
	
	Initializator getInitializator();
	
	int getGenerationSize();
	
	int getNumOfGenerations();
}
