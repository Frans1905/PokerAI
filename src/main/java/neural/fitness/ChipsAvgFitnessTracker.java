package neural.fitness;

import java.util.HashMap;
import java.util.Map;

import game.Game;
import game.actions.Action;
import game.player.Player;
import neural.NeuralNetwork;

public class ChipsAvgFitnessTracker implements FitnessTracker {

	Map<NeuralNetwork, Float> fitnesses;
	Map<NeuralNetwork, Integer> gamesPlayed;
	Map<NeuralNetwork, Float> bonusFitnesses;
	
	public ChipsAvgFitnessTracker() {
		reset();
	}
	
	public void addPoints(NeuralNetwork net, float points) {
		bonusFitnesses.put(net, bonusFitnesses.getOrDefault(net, 0f) + points);
		fitnesses.put(net, fitnesses.getOrDefault(net, 0f) + points);
	}
	
	public void addGameResults(NeuralNetwork net, Game game, int index) {
		int numOfMatches = gamesPlayed.getOrDefault(net, 0);
		float fitness = fitnesses.getOrDefault(net, 0f);
		float bonusFitness = fitnesses.getOrDefault(net, 0f);
		fitness -= bonusFitness;
		fitness *= numOfMatches;
		fitness += game.getPlayers().get(index).getChipCount();
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

	@Override
	public void informAction(NeuralNetwork net, Action action, int playerIndex, Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRoundResults(NeuralNetwork net, Game game, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FitnessTracker duplicate() {
		// TODO Auto-generated method stub
		return new ChipsAvgFitnessTracker();
	}
}
