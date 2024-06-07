package neural.activation;

import java.io.Serializable;

import org.nd4j.linalg.api.ndarray.INDArray;

public class LinearBoundedActivationFunction implements ActivationFunction, Serializable {

	@Override
	public void activate(INDArray input) {
		// TODO Auto-generated method stub
		for (int i = 0; i < input.length(); i++) {
			float val = input.getFloat(i);
			val = Math.max(0, Math.min(10, val));
			input.putScalar(i, val);
		}
	}

	
}
