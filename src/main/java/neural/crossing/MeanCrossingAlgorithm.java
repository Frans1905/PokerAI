package neural.crossing;

import java.util.Map;

import neural.Layer;
import neural.NeuralNetwork;

public class MeanCrossingAlgorithm implements CrossingAlgorithm{

	@Override
	public void cross(NeuralNetwork child, NeuralNetwork parent1, NeuralNetwork parent2,
			Map<NeuralNetwork, Float> fitnesses) {
		// TODO Auto-generated method stub
		for (int i = 0; i < parent1.getLayers().size(); i++) {
			Layer l1 = parent1.getLayers().get(i);
			Layer l2 = parent2.getLayers().get(i);
			Layer childLayer = child.getLayers().get(i);
			
			childLayer.setWeights(l1.getWeights().add(l2.getWeights()).mul(0.5));
			childLayer.setBiases(l1.getBiases().add(l2.getBiases()).mul(0.5));
		}
	}

}
