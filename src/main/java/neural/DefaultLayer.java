package neural;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class DefaultLayer implements Layer {

	private INDArray weights;
	private INDArray biases;
	private INDArray activations;
	
	public DefaultLayer(int size, int prevSize) {
		weights = Nd4j.zeros(size, prevSize);
		biases = Nd4j.zeros(size);
		activations = Nd4j.zeros(size);
	}
	
	@Override
	public INDArray getWeights() {
		// TODO Auto-generated method stub
		return weights;
	}

	@Override
	public void setWeights(INDArray ar) {
		// TODO Auto-generated method stub
		weights = ar;
	}

	@Override
	public INDArray getBiases() {
		// TODO Auto-generated method stub
		return biases;
	}

	@Override
	public void setBiases(INDArray ar) {
		// TODO Auto-generated method stub
		biases = ar;
	}

	@Override
	public INDArray getActivations() {
		// TODO Auto-generated method stub
		return activations;
	}

	@Override
	public void setActivations(INDArray ar) {
		// TODO Auto-generated method stub
		activations = ar;
	}

	@Override
	public INDArray propagate(INDArray ar) {
		// TODO Auto-generated method stub
		this.weights.mul(ar, this.activations);
		this.activations.addi(this.biases);
		return this.activations;
	}

}
