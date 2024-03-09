import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import game.Game;
import game.actions.Action;
import game.actions.Actions;
import game.player.Player;

@ExtendWith(MockitoExtension.class)
class GameTest {
	
	Player callPlayer;
	Player foldPlayer;
	Player raisePlayer;
	Game game;

	@BeforeAll
	void setup() {
		callPlayer = Mockito.mock(Player.class);
		foldPlayer = Mockito.mock(Player.class);
		raisePlayer = Mockito.mock(Player.class);
		
		game = new Game(List.of(callPlayer, foldPlayer, raisePlayer));
		
		Mockito.when(callPlayer.getPlayerAction()).thenReturn(
				Actions.getCallAction(game.getCallChipCount()));
		
		Mockito.when(raisePlayer.getPlayerAction()).thenReturn(
				Actions.getRaiseAction(game.getCallChipCount(), game.getCallChipCount() + 50));
		
		Mockito.when(foldPlayer.getPlayerAction()).thenReturn(
				Actions.getFoldAction());
	}
	
	@Test
	void testGameConstructor() {
		assertDoesNotThrow(() -> {
			Game game = new Game(List.of(callPlayer, foldPlayer, raisePlayer));
		});
		assertEquals(Game.DEFAULT_SMALL_BLIND, game.getSmallBlind());
		assertEquals(3, game.getPlayers().size());
		assertEquals(0, game.getPotChipCount());
		assertEquals(0, game.getNumCurrentPlayer());
		assertEquals(0, game.getNumSmallBlindPlayer());
		assertEquals(3, game.getActivePlayers().size());
		assertEquals(0, game.getCallChipCount());
		assertEquals(0, game.getCardsOnBoard().size());
	}
	
	@Test
	void testGameOneAction() {
		assertDoesNotThrow(() -> {
			game.setupNewRound();
		});

		Action action = game.getNextAction();
		
		game.handleAction(action);
		
		assertEquals(Game.DEFAULT_SMALL_BLIND, game.getSmallBlind());
		assertEquals(3, game.getPlayers().size());
		assertEquals(0, game.getPotChipCount());
		assertEquals(0, game.getNumCurrentPlayer());
		assertEquals(0, game.getNumSmallBlindPlayer());
		assertEquals(3, game.getActivePlayers().size());
		assertEquals(30, game.getCallChipCount());
		assertEquals(0, game.getCardsOnBoard().size());
	}
	
	@Test
	void testGameOneTurn() {
		
	}

}
