package neural.mutation;

import java.util.Random;

import org.nd4j.linalg.api.ndarray.INDArray;

import neural.Layer;
import neural.NeuralNetwork;

public class StandardDeviationMutationAlgorithm  implements MutationAlgorithm{

	private float mutationChance;
	private float bigMutationChance;
	private float stDevWeight;
	private float stDevBias;
	private static final float DEFAULT_STDEV_WEIGHT = 0.5f;
	private static final float DEFAULT_STDEV_BIAS = 0.5f;
	private static final float DEFAULT_MUTATION_CHANCE = 0.2f;
	private static final float DEFAULT_BIG_MUTATION_CHANCE = 0.2f;
	
	
	public StandardDeviationMutationAlgorithm(float mutationChance, float bigMutationChance, float stDevWeight, float stDevBias) {
		super();
		this.mutationChance = mutationChance;
		this.bigMutationChance = bigMutationChance;
		this.stDevWeight = stDevWeight;
		this.stDevBias = stDevBias;
	}
	
	public StandardDeviationMutationAlgorithm() {
		this.mutationChance = DEFAULT_MUTATION_CHANCE;
		this.stDevWeight = DEFAULT_STDEV_WEIGHT;
		this.stDevBias = DEFAULT_STDEV_BIAS;
		this.mutationChance = DEFAULT_BIG_MUTATION_CHANCE;
	}



	@Override
	public void mutate(NeuralNetwork net) {
		// TODO Auto-generated method stub
		for (int i = 0; i < net.getLayers().size(); i++) {
			Layer l = net.getLayers().get(i);
			mutateLayer(l);
		}
	}

	private void mutateLayer(Layer l) {
		// TODO Auto-generated method stub
		INDArray weights = l.getWeights();
		INDArray biases = l.getBiases();
		Random r = new Random();
		for (int i = 0; i < weights.rows(); i++) {
			if (Math.random() <= mutationChance) {
				float num = biases.getFloat(i);
				num += r.nextGaussian(0, Math.random() < bigMutationChance ? 2 * stDevBias : stDevBias);
				biases.putScalar(i, num);
			}
			for (int j = 0; j < weights.columns(); j++) {
				if (Math.random() <= mutationChance) {
					float num = weights.getFloat(i, j);
					num += r.nextGaussian(0, Math.random() < bigMutationChance ? 2 * stDevWeight : stDevWeight);
					weights.putScalar(i, j, num);
				}
			}
		}
	}
	
	

}
