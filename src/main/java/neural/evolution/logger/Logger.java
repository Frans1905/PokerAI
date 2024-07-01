package neural.evolution.logger;

import java.util.Map;

import game.actions.ActionType;
import neural.NeuralNetwork;

public interface Logger {

	void log(Map<NeuralNetwork, Float> fitnesses, Map<ActionType, Integer> map);
	
	void start();
}
