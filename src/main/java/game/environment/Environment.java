package game.environment;

import java.util.List;
import java.util.Map;

import game.Game;
import game.actions.Action;
import game.cards.Card;
import game.player.Player;

public interface Environment {

	public Action getInput(Game game);

	void updatePlayerAction(Game game, Action curAction, int numCurrentPlayer);
	
	void updateBoard(List<Card> drawnCards);

	void updateResults(Game game, Map<Player, Long> winnings);
	
	void updateEnd(Game game, int numCurrentPlayer);
	
	void setIndex(int index);
}
