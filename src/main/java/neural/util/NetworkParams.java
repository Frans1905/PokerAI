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
	private ActivationFunction outAct;
	private ActivationFunction inAct;
	
	public NetworkParams(List<Integer> layers, ActivationFunction outAct, ActivationFunction inAct) {
		this.layers = layers;
		this.outAct = outAct;
		this.inAct = inAct;
	}

	@Override
	public List<Integer> getLayers() {
		// TODO Auto-generated method stub
		return layers;
	}

	@Override
	public ActivationFunction getOutputLayerActivation() {
		// TODO Auto-generated method stub
		return outAct;
	}

	@Override
	public ActivationFunction getHiddenLayerActivation() {
		// TODO Auto-generated method stub
		return inAct;
	}
}
