package neural.activation;

import org.nd4j.linalg.api.ndarray.INDArray;

public class SigmoidActivationFunction implements ActivationFunction {

	@Override
	public void activate(INDArray input) {
		// TODO Auto-generated method stub
		for (int i = 0; i < input.rows(); i++) {
			float val = input.getFloat(i, 0);
			val = (float) (1f / (1f + Math.pow(Math.E, -val)));
			input.put(i, 0, val);
		}
	}
	
}
