package neural.evolution.logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import game.actions.ActionType;
import neural.NeuralNetwork;

public class DefaultLogger implements Logger {

	private long startTime;
	private int generationCount;
	
	public DefaultLogger() {
		this.generationCount = 1;
	}

	@Override
	public void log(Map<NeuralNetwork, Float> fitnesses, Map<ActionType, Integer> actions) {
		// TODO Auto-generated method stub
		long elapsedTime = System.nanoTime() - startTime;
		System.out.println("===================================================");
		System.out.printf("Elapsed time: %f s\n", elapsedTime / 1000000000f);
		System.out.printf("Generation: %d\n", this.generationCount++);
		List<Float> values = new ArrayList<>(fitnesses.values());
		Collections.sort(values);
		float sum = 0;
		float median;
		if (values.size() % 2 == 0) {
			median = values.get(values.size() / 2);
		}
		else {
			median = (values.get((int)(values.size() / 2)) + values.get((int)(values.size() / 2) + 1)) / 2; 
		}
		for (Float val : values) {
			sum += val;
		}
		float mean = sum / values.size();
		System.out.printf("Mean fitness: %f\n", mean);
		System.out.printf("Median fitness: %f\n", median);
		System.out.printf("Max fitness: %f\n\n", values.get(values.size() - 1));
		System.out.println("Action ||  Count || Part\n");
		int actionsum = 0;
		for (int value : actions.values()) {
			actionsum += value;
		}
		for (Entry<ActionType, Integer> entry : actions.entrySet()) {
			System.out.printf("%6s || %6d || %.2f%%\n", entry.getKey().toString(), entry.getValue(), (float)entry.getValue() * 100f / (float)actionsum);
		}
		System.out.println("");
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		this.startTime = System.nanoTime();
	}

	
	
}
