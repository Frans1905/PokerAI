package neural.fitness;

import java.util.Map;

import game.Game;
import game.actions.Action;
import game.player.Player;
import neural.NeuralNetwork;

public class NullTracker implements FitnessTracker {

	@Override
	public void addPoints(NeuralNetwork net, float points) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGameResults(NeuralNetwork net, Game game, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRoundResults(NeuralNetwork net, Game game, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informAction(NeuralNetwork net, Action action, int playerIndex, Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subtractPoints(NeuralNetwork net, float points) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<NeuralNetwork, Float> getFitnesses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FitnessTracker duplicate() {
		// TODO Auto-generated method stub
		return new NullTracker();
	}

}
