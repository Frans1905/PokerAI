package game.environment;

import neural.NeuralNetwork;
import neural.fitness.FitnessTracker;

public interface NeuralEnvironment extends Environment {
	NeuralEnvironment duplicate(NeuralNetwork net, FitnessTracker fitness);
}
