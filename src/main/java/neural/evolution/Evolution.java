package neural.evolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import neural.NeuralNetwork;
import neural.PokerNeuralNetwork;
import neural.evolution.logger.Logger;
import neural.strategy.NetworkStrategy;
import neural.util.IEvolutionParams;
import neural.util.INetworkParams;

public class Evolution {

	private IEvolutionParams evoparams;
	private INetworkParams netparams;
	private List<NetworkStrategy> strategies;
	private List<Logger> loggers;
	
	
	public Evolution(IEvolutionParams evoparams, INetworkParams netparams) {
		this.evoparams = evoparams;
		this.netparams = netparams;
		this.loggers = new ArrayList<>();
		this.strategies = new ArrayList<>();
	}
	
	public void addLogger(Logger logger) {
		this.loggers.add(logger);
	}
	
	public void start() {
		System.out.println("Evolution started...");
		startLoggers();
		Map<NeuralNetwork, Float> fitnesses = null;
		List<NeuralNetwork> nets = initializeNetworks();
		for (int i = 0; i < evoparams.getNumOfGenerations(); i++) {
			evoparams.getFitnessAlgorithm().clearNetworks();
			evoparams.getFitnessAlgorithm().addNetworks(nets);
			fitnesses = evoparams.getFitnessAlgorithm().calculateFitness();
			log(fitnesses);
			nets = createNewPopulation(fitnesses);
		}
		endLoggers();
		NeuralNetwork best = getBestNetwork(fitnesses);
		executeStrategies(best);
	}

	private void executeStrategies(NeuralNetwork best) {
		// TODO Auto-generated method stub
		for (NetworkStrategy s : strategies) {
			s.accept(best);
		}
	}

	private NeuralNetwork getBestNetwork(Map<NeuralNetwork, Float> fitnesses) {
		// TODO Auto-generated method stub
		float max = 0;
		NeuralNetwork best = null;
		for (NeuralNetwork net : fitnesses.keySet()) {
			if (fitnesses.get(net) > max) {
				max = fitnesses.get(net);
				best = net;
			}
		}
		return best;
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

	private List<NeuralNetwork> initializeNetworks() {
		// TODO Auto-generated method stub
		List<NeuralNetwork> nets = new ArrayList<>();
		for (int i = 0; i < this.evoparams.getGenerationSize(); i++) {
			NeuralNetwork net = new PokerNeuralNetwork(netparams);
			net.initialize(evoparams.getInitializator());
			nets.add(net);
		}
		return nets;
	}
	
	public void addStrategy(NetworkStrategy s) {
		this.strategies.add(s);
	}
}
