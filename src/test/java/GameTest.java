import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import game.Game;
import game.actions.Action;
import game.environment.Environment;
import game.environment.ShellPlayerEnvironment;
import game.evaluator.Evaluator;
import game.evaluator.jmp.JmpEvaluator;
import game.player.Player;
import game.player.stub.CallPlayer;
import game.player.stub.FoldPlayer;
import game.player.stub.RaisePlayer;

@ExtendWith(MockitoExtension.class)
class GameTest {
	
	static Player callPlayer;
	static Player foldPlayer;
	static Player raisePlayer;
	static Game game;

	@BeforeAll
	static void setup() {
		callPlayer = new CallPlayer();
		foldPlayer = new FoldPlayer();
		raisePlayer = new RaisePlayer();
		
		Environment env = new ShellPlayerEnvironment();
		Evaluator ev = new JmpEvaluator();

		game = new Game(List.of(callPlayer, foldPlayer, raisePlayer), ev);
		
	}
	
	@AfterEach
	void reset() {
		game.resetGame(List.of(callPlayer, foldPlayer, raisePlayer));
	}
	
	@Test
	void testGameConstructor() {
		Environment env = new ShellPlayerEnvironment();
		Evaluator ev = new JmpEvaluator();
		assertDoesNotThrow(() -> {
			Game game = new Game(List.of(callPlayer, foldPlayer, raisePlayer), ev);
		});
		assertEquals(Game.DEFAULT_SMALL_BLIND, game.getSmallBlind());
		assertEquals(3, game.getPlayers().size());
		assertEquals(0, game.getPotChipCount());
		assertEquals(0, game.getNumCurrentPlayer());
		assertEquals(-1, game.getNumSmallBlindPlayer());
		assertEquals(0, game.getActivePlayers().size());
		assertEquals(0, game.getCallChipCount());
		assertEquals(0, game.getCardsOnBoard().size());
	}
	
	@Test
	void testGameOneAction() {
		assertDoesNotThrow(() -> {
			game.setupNewRound();
		});

		game.takeBlinds();
		
		Action action = game.getNextAction();
		
		game.handleAction(action);
		
		assertEquals(Game.DEFAULT_SMALL_BLIND, game.getSmallBlind());
		assertEquals(3, game.getPlayers().size());
		assertEquals(0, game.getPotChipCount());
		assertEquals(2, game.getNumCurrentPlayer());
		assertEquals(0, game.getNumSmallBlindPlayer());
		assertEquals(3, game.getActivePlayers().size());
		assertEquals(80, game.getCallChipCount());
		assertEquals(0, game.getCardsOnBoard().size());
	}
	
	@Test
	void testGameOneTurn() {
		
		assertDoesNotThrow(() -> {
			game.setupNewRound();
		});

		
		game.takeBlinds();
		
		Action action = game.getNextAction();
		game.handleAction(action);
		action = game.getNextAction();
		game.handleAction(action);
		action = game.getNextAction();
		game.handleAction(action);
		
		assertEquals(3, game.getPlayers().size());
		assertEquals(190, game.getPotChipCount());
		assertEquals(-1, game.getNumCurrentPlayer());
		assertEquals(0, game.getNumSmallBlindPlayer());
		assertEquals(2, game.getActivePlayers().size());
		assertEquals(0, game.getCallChipCount());
		assertEquals(3, game.getCardsOnBoard().size());
	}
	
	

}
