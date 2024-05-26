package neural.evolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import neural.NeuralNetwork;
import neural.PokerNeuralNetwork;
import neural.evolution.logger.Logger;
import neural.util.IEvolutionParams;
import neural.util.INetworkParams;

public class Evolution {

	private IEvolutionParams evoparams;
	private INetworkParams netparams;
	private List<Logger> loggers;
	
	
	public Evolution(IEvolutionParams evoparams, INetworkParams netparams) {
		this.evoparams = evoparams;
		this.netparams = netparams;
	}
	
	public void addLogger(Logger logger) {
		this.loggers.add(logger);
	}
	
	public void start() {
		startLoggers();
		List<NeuralNetwork> nets = createNetworks();
		for (int i = 0; i < evoparams.getNumOfGenerations(); i++) {
			evoparams.getFitnessAlgorithm().addNetworks(nets);
			Map<NeuralNetwork, Float> fitnesses = evoparams.getFitnessAlgorithm().calculateFitness();
			nets = createNewPopulation(fitnesses);
			log(fitnesses);
		}
		endLoggers();
	}

	private void endLoggers() {
		// TODO Auto-generated method stub
		
	}

	private void log(Map<NeuralNetwork, Float> fitnesses) {
		// TODO Auto-generated method stub
		for (Logger log : loggers) {
			log.log(fitnesses);
		}
	}

	private void startLoggers() {
		// TODO Auto-generated method stub
		for (Logger log : loggers) {
			log.start();
		}
	}

	private List<NeuralNetwork> createNewPopulation(Map<NeuralNetwork, Float> fitnesses) {
		// TODO Auto-generated method stub
		List<NeuralNetwork> output = new ArrayList<>();
		for (int i = 0; i < evoparams.getGenerationSize(); i++) {
			NeuralNetwork parent1 = evoparams.getSelectionAlgorithm().select(fitnesses);
			NeuralNetwork parent2 = evoparams.getSelectionAlgorithm().select(fitnesses);
			NeuralNetwork child = new PokerNeuralNetwork(netparams);
			child.fromParents(parent1, parent2, evoparams.getCrossingAlgorithm());
			child.mutate(evoparams.getMutationAlgorithm());
			output.add(child);
		}
		return output;
	}

	private List<NeuralNetwork> createNetworks() {
		// TODO Auto-generated method stub
		List<NeuralNetwork> nets = new ArrayList<>();
		for (int i = 0; i < this.evoparams.getGenerationSize(); i++) {
			NeuralNetwork net = new PokerNeuralNetwork(netparams);
			net.initialize(evoparams.getInitializator());
			nets.add(net);
		}
		return nets;
	}
}
