package neural;

import org.nd4j.linalg.api.ndarray.INDArray;

public interface Layer {
	
	public INDArray getWeights();
	
	public void setWeights(INDArray ar);
	
	public INDArray getBiases();
	
	public void setBiases(INDArray ar);
	
	public INDArray getActivations();
	
	public void setActivations(INDArray ar);
	
	public INDArray propagate(INDArray ar);
}
