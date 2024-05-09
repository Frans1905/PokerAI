package neural.mutation;

import java.util.Random;

import org.nd4j.linalg.api.ndarray.INDArray;

import neural.Layer;
import neural.NeuralNetwork;

public class StandardDeviationMutationAlgorithm  implements MutationAlgorithm{

	private float mutationChance;
	private float stDev;
	private static final float DEFAULT_STDEV = 0.5f;
	private static final float DEFAULT_MUTATION_CHANCE = 0.2f;
	
	
	public StandardDeviationMutationAlgorithm(float mutationChance, float stDev) {
		super();
		this.mutationChance = mutationChance;
		this.stDev = stDev;
	}
	
	public StandardDeviationMutationAlgorithm() {
		this.mutationChance = DEFAULT_MUTATION_CHANCE;
		this.stDev = DEFAULT_STDEV;
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
				float num = biases.getFloat(i, 0);
				num += r.nextGaussian(0, stDev);
				weights.putScalar(i, 0, num);
			}
			for (int j = 0; j < weights.columns(); j++) {
				if (Math.random() <= mutationChance) {
					float num = weights.getFloat(i, j);
					num += r.nextGaussian(0, stDev);
					weights.putScalar(i, j, num);
				}
			}
		}
	}
	
	

}
