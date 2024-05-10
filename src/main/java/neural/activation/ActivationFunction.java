package neural.activation;

import org.nd4j.linalg.api.ndarray.INDArray;

public interface ActivationFunction {
	void activate(INDArray input);
}
