package neural.fitness;

import java.util.Map;

import game.actions.Action;
import neural.NeuralNetwork;

public class NullTracker implements FitnessTracker {

	@Override
	public void addPoints(NeuralNetwork net, float points) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGameResults(NeuralNetwork net, float chips) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRoundResults(NeuralNetwork net, float chips, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informAction(NeuralNetwork net, Action action) {
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

}