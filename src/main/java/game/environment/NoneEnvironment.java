package game.environment;

import java.util.List;
import java.util.Map;

import game.Game;
import game.actions.Action;
import game.cards.Card;
import game.player.Player;

public class NoneEnvironment implements Environment {

	@Override
	public Action getInput(Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePlayerAction(Game game, Action curAction, int numCurrentPlayer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoard(List<Card> drawnCards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateResults(Game game, Map<Player, Long> winnings) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEnd(Game game, int numCurrentPlayer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIndex(int index) {
		// TODO Auto-generated method stub
		
	}

}
