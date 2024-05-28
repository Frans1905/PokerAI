package game.app;

import neural.evolution.Evolution;
import neural.evolution.logger.DefaultLogger;
import neural.evolution.logger.Logger;
import neural.strategy.NetworkStrategy;
import neural.strategy.SaveStrategy;
import neural.util.IEvolutionParams;
import neural.util.INetworkParams;
import neural.util.NeuralUtil;

public class PokerAI {
	
	public static void main(String[] args) {
		INetworkParams netparams = NeuralUtil.DEFAULT_POKER_NETWORK_PARAMS;
		IEvolutionParams evoparams = NeuralUtil.DEFAULT_EVOLUTION_PARAMS;
		Evolution evo = new Evolution(evoparams, netparams);
		
		Logger deflog = new DefaultLogger();
		evo.addLogger(deflog);
		
		NetworkStrategy save = new SaveStrategy();
		evo.addStrategy(save);
		
		evo.start();
	}
}
