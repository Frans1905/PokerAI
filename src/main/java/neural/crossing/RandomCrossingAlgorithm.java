package neural.crossing;

import java.util.Map;

import org.nd4j.linalg.api.ndarray.INDArray;

import neural.Layer;
import neural.NeuralNetwork;

public class RandomCrossingAlgorithm implements CrossingAlgorithm {

	public RandomCrossingAlgorithm() {
		
	}
	
	@Override
	public void cross(NeuralNetwork child, NeuralNetwork parent1, NeuralNetwork parent2, Map<NeuralNetwork, Float> fitnesses) {
		// TODO Auto-generated method stub
		float fitness1 = fitnesses.get(parent1);
		float fitness2 = fitnesses.get(parent2);
		if (fitness1 < 0 || fitness2 < 0) {
			float min = Math.abs(Math.min(fitness1, fitness2));
			fitness1 += 2 * min;
			fitness2 += 2 * min;
		}
		
		for (int k = 0; k < parent1.getLayers().size(); k++) {
			Layer l1 = parent1.getLayers().get(k);
			Layer l2 = parent2.getLayers().get(k);
			
			INDArray l1weights = l1.getWeights();
			INDArray l2weights = l2.getWeights();
			INDArray childweights = child.getLayers().get(k).getWeights();
			
			INDArray l1biases = l1.getBiases();
			INDArray l2biases = l2.getBiases();
			INDArray childbiases = child.getLayers().get(k).getBiases();
			
			for (int i = 0; i < l1weights.rows(); i++) {
				double rand = Math.random();
				
				float bias;
				float fraction = fitness1 / (fitness2 + fitness2);
				if (rand < fraction) {
					bias = l1biases.getFloat(i);
				}
				else {
					bias = l2biases.getFloat(i);
				}
				childbiases.putScalar(i, bias);
				
				for (int j = 0; j < l1weights.columns(); j++) {
					float weight;
					if (rand < fraction) {
						weight = l1weights.getFloat(i, j);
					}
					else {
						weight = l2weights.getFloat(i, j);
					}
					childweights.putScalar(i, j, weight);
				}
			}
			
		}
	}
	
}
