package game.environment;

import java.util.List;
import java.util.Map;

import game.Game;
import game.actions.Action;
import game.cards.Card;
import game.player.Player;
import neural.NeuralNetwork;

public class NeuralNetworkEnvironment implements Environment {

	NeuralNetwork net;
	
	public NeuralNetworkEnvironment(NeuralNetwork net) {
		this.net = net;
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

}
