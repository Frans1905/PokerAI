package neural.fitness;

import java.util.HashMap;
import java.util.Map;

import game.Game;
import game.actions.Action;
import game.player.Player;
import neural.NeuralNetwork;

public class StrengthChipRatioFitnessTracker implements FitnessTracker {

	private Map<NeuralNetwork, Float> fitnesses;
	
	public StrengthChipRatioFitnessTracker() {
		fitnesses = new HashMap<>();
	}
	
	@Override
	public synchronized void addPoints(NeuralNetwork net, float points) {
		// TODO Auto-generated method stub
		fitnesses.put(net, fitnesses.getOrDefault(net, 0f) + points);
		
	}

	@Override
	public synchronized void addGameResults(NeuralNetwork net, Game game, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void addRoundResults(NeuralNetwork net, Game game, int index) {
		// TODO Auto-generated method stub
		Player p = game.getPlayers().get(index);
		if (game.getCardsOnBoard().size() != 5) {
			this.fitnesses.putIfAbsent(net, 0f);
			return;
		}
		if (p.getTotalBetChipCount() + p.getChipCount() == 0) {
			this.fitnesses.putIfAbsent(net, 0f);
			return;
		}
		int strength = game.getEvaluator().findStrongestCombination(p.getCards(), game.getCardsOnBoard());
		float chipFraction = (float)p.getTotalBetChipCount() / (float)(p.getTotalBetChipCount() + p.getChipCount());
		
		float strengthFraction = 1 - (strength / 7462f);
		double diff = Math.abs(chipFraction - Math.pow(strengthFraction, 2));
		long win = game.getDealer().getLastWinnings().getOrDefault(p, 0l);
		double coeff = Math.max(Math.log10(win) ,1);
		addPoints(net, (float)(coeff / (diff * 100)));
		
	}

	@Override
	public synchronized void informAction(NeuralNetwork net, Action action, int playerIndex, Player p) {
		// TODO Auto-generated method stub
		FitnessTracker.super.informAction(net, action, playerIndex, p);
	}

	@Override
	public synchronized void subtractPoints(NeuralNetwork net, float points) {
		// TODO Auto-generated method stub
		addPoints(net, -points);
	}

	@Override
	public Map<NeuralNetwork, Float> getFitnesses() {
		// TODO Auto-generated method stub
		return this.fitnesses;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		FitnessTracker.super.reset();
		fitnesses.clear();
	}

	@Override
	public FitnessTracker duplicate() {
		// TODO Auto-generated method stub
		return new StrengthChipRatioFitnessTracker();
	}

}
