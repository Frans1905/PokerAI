package neural;

import java.util.ArrayList;
import java.util.List;

import org.nd4j.linalg.api.ndarray.INDArray;

import neural.randomizer.Randomizer;
import neural.util.INeuralNetworkParams;

public class PokerNeuralNetwork implements NeuralNetwork {

	private List<Layer> layers;
	private INeuralNetworkParams params;
	
	public PokerNeuralNetwork(INeuralNetworkParams params) {
		this.layers = null;
		this.params = params;
	}
	
	public PokerNeuralNetwork(int size) {
		this.layers = new ArrayList<Layer>(size);
	}
	
	public PokerNeuralNetwork(List<Layer> layers) {
		this.layers = layers;
	}
	
	public PokerNeuralNetwork(PokerNeuralNetwork other) {
		this.layers = other.getLayers();
	}
	
	@Override
	public void fromParents(NeuralNetwork parent1, NeuralNetwork parent2) {
		// TODO Auto-generated method stub
		if (params.getCrossingAlgorithm() == null) {
			throw new IllegalArgumentException("Crossing algorithm null");
		}
		params.getCrossingAlgorithm().cross(this, parent1, parent2);
	}

	@Override
	public void mutate() {
		// TODO Auto-generated method stub
		if (params.getMutationAlgorithm() == null) {
			throw new IllegalArgumentException("Mutation algorithm null");
		}
		params.getMutationAlgorithm().mutate(this);
	}

	@Override
	public void copy(NeuralNetwork source) {
		// TODO Auto-generated method stub
		return ;
	}

	@Override
	public void randomize(Randomizer r) {
		// TODO Auto-generated method stub
		for(int i = 0; i < layers.size(); i++) {
			INDArray ar = layers.get(i).getWeights();
			randomizeNDArray(ar, r);
			INDArray ar2 = layers.get(i).getBiases();
			randomizeNDArray(ar2, r);
		}
	}

	private void randomizeNDArray(INDArray ar, Randomizer r) {
		// TODO Auto-generated method stub
		for (int i = 0; i < ar.rows(); i++) {
			for (int j = 0; j < ar.columns(); j++) {
				ar.putScalar(i, j, r.getSmallRandom());
			}
		}
	}

	public List<Layer> getLayers() {
		return layers;
	}

	@Override
	public INDArray process(INDArray input) {
		// TODO Auto-generated method stub
		for (int i = 0; i < layers.size(); i++) {
			input = layers.get(i).propagate(input);
			params.getHiddenLayerActivation().activate(input);
		}
		params.getOutputLayerActivation().activate(input);
		return input;
	}
	
	

}
