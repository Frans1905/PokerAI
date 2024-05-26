package neural.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import neural.NeuralNetwork;

public class TournamentSelectionAlgorithm implements SelectionAlgorithm {

	private int tournamentSize;
	private static Random rand = new Random();
	
	public TournamentSelectionAlgorithm(int tournamentSize) {
		this.tournamentSize = tournamentSize;
	}
	
	@Override
	public NeuralNetwork select(Map<NeuralNetwork, Float> fitnesses) {
		// TODO Auto-generated method stub
		List<NeuralNetwork> nets = new ArrayList<>(fitnesses.keySet());
		int bestIndex = rand.nextInt(nets.size());
		float bestFitness = fitnesses.get(nets.get(bestIndex));
		for (int i = 0; i < tournamentSize; i++) {
			int index = rand.nextInt(nets.size());
			if (fitnesses.get(nets.get(index)) > bestFitness) {
				bestIndex = index;
				bestFitness = fitnesses.get(nets.get(index));
			}
		}
		return nets.get(bestIndex);
	}

}
