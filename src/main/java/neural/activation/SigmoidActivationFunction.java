package neural.activation;

import java.io.Serializable;

import org.nd4j.linalg.api.ndarray.INDArray;

public class SigmoidActivationFunction implements ActivationFunction, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void activate(INDArray input) {
		// TODO Auto-generated method stub
		for (int i = 0; i < input.length(); i++) {
			float val = input.getFloat(i);
			val = (float) (1f / (1f + Math.pow(Math.E, -val)));
			input.putScalar(i, val);
		}
	}
	
}
