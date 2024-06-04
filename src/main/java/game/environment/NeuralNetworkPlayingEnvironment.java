package game.environment;

import neural.NeuralNetwork;
import neural.fitness.NullTracker;

public class NeuralNetworkPlayingEnvironment extends NeuralNetworkEnvironment {
	
	public NeuralNetworkPlayingEnvironment(NeuralNetwork net) {
		super(net, new NullTracker());
	}
}

