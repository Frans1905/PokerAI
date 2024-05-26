package neural.initialization;

import java.util.List;

import org.nd4j.linalg.api.ndarray.INDArray;

import neural.Layer;
import neural.NeuralNetwork;

public class XavierInitializator implements Initializator {

	public XavierInitializator() {
		
	}
	
	@Override
	public void initialize(NeuralNetwork net) {
		// TODO Auto-generated method stub
	
		List<Layer> layers = net.getLayers();
		for (int k = 0; k < layers.size(); k++) {
			INDArray weights = layers.get(k).getWeights();
			int nin = weights.rows();
			int nout = weights.columns();
			double limit = Math.sqrt(6.0 / (nin + nout));
			for (int i = 0; i < weights.rows(); i++) {
				for (int j = 0; j < weights.columns(); j++) {
					float val = (float) (Math.random() * 2 * limit - limit);
					weights.putScalar(i, j, val);
				}
			}
		}
	}

}
