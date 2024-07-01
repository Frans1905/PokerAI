package neural.util;

import java.io.Serializable;
import java.util.List;

import game.environment.NeuralEnvironment;
import game.environment.SimpleNeuralNetworkEnvironment;
import neural.activation.ActivationFunction;

public class NetworkParams implements INetworkParams, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final List<Integer> DEFAULT_LAYERS = List.of();
	
	private NeuralEnvironment netEnv;
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

	public NetworkParams(NeuralEnvironment netEnv, List<Integer> of,
			ActivationFunction outRegAct, ActivationFunction outClassAct, ActivationFunction inAct) {
		// TODO Auto-generated constructor stub
		this.netEnv = netEnv;
		this.layers = of;
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

	@Override
	public NeuralEnvironment getEnvironment() {
		// TODO Auto-generated method stub
		return this.netEnv;
	}
}
