package neural.util;

import java.io.Serializable;
import java.util.List;

import neural.activation.ActivationFunction;

public class NetworkParams implements INetworkParams, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final List<Integer> DEFAULT_LAYERS = List.of();
	
	private List<Integer> layers;
	private ActivationFunction outRegAct;
	private ActivationFunction outClassAct;
	private ActivationFunction inAct;
	
	public NetworkParams(List<Integer> layers, ActivationFunction outRegAct, ActivationFunction outClassAct, ActivationFunction inAct) {
		this.layers = layers;
		this.outRegAct = outRegAct;
		this.outClassAct = outClassAct;
		this.inAct = inAct;
	}

	@Override
	public List<Integer> getLayers() {
		// TODO Auto-generated method stub
		return layers;
	}

	@Override
	public ActivationFunction getOutputLayerRegressionActivation() {
		// TODO Auto-generated method stub
		return outRegAct;
	}

	@Override
	public ActivationFunction getHiddenLayerActivation() {
		// TODO Auto-generated method stub
		return inAct;
	}

	@Override
	public ActivationFunction getOutputLayerClassActivation() {
		// TODO Auto-generated method stub
		return outClassAct;
	}
}
