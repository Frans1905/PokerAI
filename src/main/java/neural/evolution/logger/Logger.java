package neural.evolution.logger;

import java.util.Map;

import neural.NeuralNetwork;

public interface Logger {

	void log(Map<NeuralNetwork, Float> fitnesses);
	
	void start();
}
