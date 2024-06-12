package neural.util;

import neural.crossing.CrossingAlgorithm;
import neural.fitness.FitnessAlgorithm;
import neural.initialization.Initializator;
import neural.mutation.MutationAlgorithm;
import neural.selection.SelectionAlgorithm;

public class EvolutionParams implements IEvolutionParams {
	
	private SelectionAlgorithm selectionAlg;
	private MutationAlgorithm mutationAlg;
	private CrossingAlgorithm crossingAlg;
	private FitnessAlgorithm fitnessAlg;
	private Initializator initializator;
	private int generationSize;
	private int numOfGenerations;
	private int elitismCount;
	
	
	public EvolutionParams(SelectionAlgorithm selectionAlg, MutationAlgorithm mutationFunc, CrossingAlgorithm crossingAlg, FitnessAlgorithm fitnessAlg,
			Initializator initializator, int generationSize, int numOfGenerations, int elitismCount) {
		super();
		this.selectionAlg = selectionAlg;
		this.mutationAlg = mutationFunc;
		this.crossingAlg = crossingAlg;
		this.fitnessAlg = fitnessAlg;
		this.initializator = initializator;
		this.generationSize = generationSize;
		this.numOfGenerations = numOfGenerations;
		this.elitismCount = elitismCount;
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

	@Override
	public FitnessAlgorithm getFitnessAlgorithm() {
		return fitnessAlg;
	}

	public void setFitnessAlg(FitnessAlgorithm fitnessAlg) {
		this.fitnessAlg = fitnessAlg;
	}

	public int getGenerationSize() {
		return generationSize;
	}

	public void setGenerationSize(int generationSize) {
		this.generationSize = generationSize;
	}

	public int getNumOfGenerations() {
		return numOfGenerations;
	}

	public void setNumOfGenerations(int numOfGenerations) {
		this.numOfGenerations = numOfGenerations;
	}



	@Override
	public Initializator getInitializator() {
		// TODO Auto-generated method stub
		return this.initializator;
	}



	@Override
	public SelectionAlgorithm getSelectionAlgorithm() {
		// TODO Auto-generated method stub
		return selectionAlg;
	}



	@Override
	public int getElitismCount() {
		// TODO Auto-generated method stub
		return this.elitismCount;
	}
	
	
}
