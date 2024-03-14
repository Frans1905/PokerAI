package game.environment;

import java.util.List;

import game.Game;
import game.actions.Action;
import game.cards.Card;

public interface Environment {

	public Action getInput(Game game);

	public void updatePlayerAction(Action curAction, int numCurrentPlayer);
	
	public void updateBoard(List<Card> drawnCards);
}
