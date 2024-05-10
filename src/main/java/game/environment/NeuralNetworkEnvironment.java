package game.environment;

import java.util.List;
import java.util.Map;

import game.Game;
import game.actions.Action;
import game.cards.Card;
import game.player.Player;
import neural.NeuralNetwork;
import neural.fitness.FitnessTracker;

public class NeuralNetworkEnvironment implements Environment {

	NeuralNetwork net;
	FitnessTracker tracker;
	
	public NeuralNetworkEnvironment(NeuralNetwork net, FitnessTracker tracker) {
		this.net = net;
		this.tracker = tracker;
	}
	
	@Override
	public Action getInput(Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePlayerAction(Action curAction, int numCurrentPlayer) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void updateBoard(List<Card> drawnCards) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void updateResults(Game game, Map<Player, Long> winnings) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void updateEnd(Game game, int numCurrentPlayer) {
		// TODO Auto-generated method stub
		tracker.addGameResults(net, (float) game.getPlayers().get(numCurrentPlayer).getChipCount());
	}

}
