package neural.fitness;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import neural.NeuralNetwork;

public class FitnessTracker {

	Map<NeuralNetwork, Float> fitnesses;
	Map<NeuralNetwork, Integer> gamesPlayed;
	Map<NeuralNetwork, Float> bonusFitnesses;
	
	public FitnessTracker() {
		reset();
	}
	
	public void addPoints(NeuralNetwork net, float points) {
		bonusFitnesses.put(net, bonusFitnesses.getOrDefault(net, 0f) + points);
		fitnesses.put(net, fitnesses.getOrDefault(net, 0f) + points);
	}
	
	public void addGameResults(NeuralNetwork net, float chips) {
		int numOfMatches = gamesPlayed.getOrDefault(net, 0);
		float fitness = fitnesses.getOrDefault(net, 0f);
		float bonusFitness = fitnesses.getOrDefault(net, 0f);
		fitness -= bonusFitness;
		fitness += chips;
		fitness /= ++numOfMatches;
		fitness += bonusFitness;
		gamesPlayed.put(net, numOfMatches);
		fitnesses.put(net, fitness);
	}
	
	public void subtractPoints(NeuralNetwork net, float points) {
		addPoints(net, -points);
	}
	
	public Map<NeuralNetwork, Float> getFitnesses() {
		return fitnesses;
	}
	
	public void reset() {
		this.fitnesses = new HashMap<>();
		this.gamesPlayed = new HashMap<>();
		this.bonusFitnesses = new HashMap<>();
	}
}
