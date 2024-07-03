package neural.fitness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Game;
import game.player.Player;
import neural.NeuralNetwork;

public class RankFitnessTracker implements FitnessTracker {

	Map<NeuralNetwork, Float> fitnesses;
	Map<NeuralNetwork, Long> gamesPlayed;
	
	public RankFitnessTracker() {
		fitnesses = new HashMap<>();
		gamesPlayed = new HashMap<>();
	}
	
	@Override
	public void reset() {
		FitnessTracker.actions.clear();
		this.fitnesses.clear();
		this.gamesPlayed.clear();
	}
	
	@Override
	public void addPoints(NeuralNetwork net, float points) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGameResults(NeuralNetwork net, Game game, int index) {
		// TODO Auto-generated method stub
		Player thisp = game.getPlayers().get(index);
		this.gamesPlayed.put(net, this.gamesPlayed.getOrDefault(net, 0l) + 1);
		if (thisp.getChipCount() == 0) {
			return;
		}
		List<Long> values = new ArrayList<>();
		for (Player p : game.getPlayers()) {
			values.add(p.getChipCount());
		}
		Collections.sort(values);
		Collections.reverse(values);
		float points = 0;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) == thisp.getChipCount()) {
				points = 5 - (i + 1);
			}
		}
		this.fitnesses.put(net, this.fitnesses.getOrDefault(net, 0f) + points);
	}

	@Override
	public void addRoundResults(NeuralNetwork net, Game game, int index) {
		// TODO Auto-generated method stub
		if (game.getPlayers().get(index).getChipCount() != 0) {
			return;
		}
		if (game.getPlayers().get(index).getChipCount() == 0 && 
				game.getPlayers().get(index).getTotalBetChipCount() == 0) {
			return;
		}
		float points = 0;
		for (Player p : game.getPlayers()) {
			if (p.getChipCount() == 0 && p.getTotalBetChipCount() == 0) {
				points++;
			}
		}
		fitnesses.put(net, fitnesses.getOrDefault(game, 0f) + points);
	}

	@Override
	public void subtractPoints(NeuralNetwork net, float points) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<NeuralNetwork, Float> getFitnesses() {
		// TODO Auto-generated method stub
		for (NeuralNetwork net : fitnesses.keySet()) {
			fitnesses.put(net, fitnesses.get(net) / (float)gamesPlayed.get(net));
		}
		return fitnesses;
	}

	@Override
	public FitnessTracker duplicate() {
		// TODO Auto-generated method stub
		return null;
	}

}
