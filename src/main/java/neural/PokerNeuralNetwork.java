package neural;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import neural.crossing.CrossingAlgorithm;
import neural.initialization.Initializator;
import neural.mutation.MutationAlgorithm;
import neural.util.INetworkParams;

public class PokerNeuralNetwork implements NeuralNetwork, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Layer> layers;
	private INetworkParams params;
	
	public PokerNeuralNetwork(INetworkParams params) {
		this.layers = new ArrayList<>();
		List<Integer> layerSizes = params.getLayers();
		int prevSize = layerSizes.get(0);
		for (int i = 1; i < layerSizes.size(); i++) {
			int curSize = layerSizes.get(i);
			this.layers.add(new DefaultLayer(curSize, prevSize));
			prevSize = curSize;
		}
		this.params = params;
	}
	
	public PokerNeuralNetwork(PokerNeuralNetwork other) {
		this.layers = other.getLayers();
		this.params = other.getParams();
	}
	
	@Override
	public void fromParents(NeuralNetwork parent1, NeuralNetwork parent2, CrossingAlgorithm alg, Map<NeuralNetwork, Float> fitnesses) {
		// TODO Auto-generated method stub
		if (alg == null) {
			throw new IllegalArgumentException("Crossing algorithm null");
		}
		alg.cross(this, parent1, parent2, fitnesses);
	}

	@Override
	public void mutate(MutationAlgorithm alg) {
		// TODO Auto-generated method stub
		if (alg == null) {
			throw new IllegalArgumentException("Mutation algorithm null");
		}
		alg.mutate(this);
	}

	@Override
	public void copy(NeuralNetwork source) {
		// TODO Auto-generated method stub
		return ;
	}

	@Override
	public void initialize(Initializator i) {
		// TODO Auto-generated method stub
		i.initialize(this);
	}

	public List<Layer> getLayers() {
		return layers;
	}

	@Override
	public INDArray process(INDArray input) {
		// TODO Auto-generated method stub
		for (int i = 0; i < layers.size(); i++) {
			//System.out.println(input);
			input = layers.get(i).propagate(input);
			if (i == layers.size() - 1) break;
			//System.out.println(input);
			//System.out.println(layers.get(i).getWeights());
			params.getHiddenLayerActivation().activate(input);
			//System.out.println(input);
		}
		List<INDArray> output = new ArrayList<>();
		INDArray inputClass = input.getScalar(0);
		INDArray inputReg = input.getScalar(1);
		params.getOutputLayerRegressionActivation().activate(inputReg);
		params.getOutputLayerClassActivation().activate(inputClass);
		if (inputClass.getFloat(0) >= 0.8f  && inputReg.getFloat(0) > 0f) {
			int one = 1;
		}
		output.add(inputClass);
		output.add(inputReg);
		return Nd4j.create(output, 2);
	}
	
	@Override
	public INetworkParams getParams() {
		return this.params;
	}
	
	

}
