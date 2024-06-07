import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import game.Game;
import game.actions.Action;
import game.actions.Actions;
import game.cards.Card;
import game.cards.CardPair;
import game.cards.CardRank;
import game.cards.CardSuit;
import game.environment.Environment;
import game.environment.NoneEnvironment;
import game.environment.ShellPlayerEnvironment;
import game.evaluator.Evaluator;
import game.evaluator.jmp.JmpEvaluator;
import game.player.DefaultPlayer;
import game.player.Player;
import game.player.stub.CallPlayer;
import game.player.stub.FoldPlayer;
import game.player.stub.RaisePlayer;
import test.game.Deck;
import test.game.TestGame;
import test.game.TestPlayer;

@ExtendWith(MockitoExtension.class)
class DealerTests {
	
	static TestPlayer playerOne;
	static TestPlayer playerTwo;
	static TestPlayer playerThree;
	static TestGame game;

	@BeforeEach
	 void setup() {
		Environment env = new NoneEnvironment();
		playerOne = new TestPlayer("p1",env);
		playerTwo = new TestPlayer("p2",env);
		playerThree = new TestPlayer("p3",env);
		

		Evaluator ev = new JmpEvaluator();

		game = new TestGame(List.of(playerOne, playerTwo, playerThree), ev);
		game.setupNewRound();
	}
	
	@AfterEach
	void reset() {
		game.resetGame(List.of(playerOne, playerTwo, playerThree));
	}
	
	@Test
	void testPlayerOneWins_100_from_each() {
	
		setupPlayerBet(playerOne,100L,
				Deck.ACE_CLUBS,
				Deck.ACE_SPADES 
				);

		setupPlayerBet(playerTwo,100L,
				Deck.ACE_DIAMONDS,
				Deck.KING_DIAMONDS 
				);

		
		setupPlayerBet(playerThree,100L,
				Deck.ACE_HEARTS,
				Deck.KING_HEARTS 
				);
		
		setBoardAndDivideChips(
				Deck.TWO_CLUBS, 
				Deck.THREE_DIAMONDS,
				Deck.SIX_CLUBS, 
				Deck.SEVEN_DIAMONDS,
				Deck.TEN_SPADES);
		
		assertEquals(3200L, playerOne.getChipCount());
		assertEquals(2900L, playerTwo.getChipCount());
		assertEquals(2900L, playerThree.getChipCount());
		
	}

	@Test
	void testPlayerOneWins_with_low_bet_then_player_two() {
	
		setupPlayerBet(playerOne,100L,
				Deck.ACE_CLUBS,
				Deck.ACE_SPADES 
				);

		setupPlayerBet(playerTwo,200L,
				Deck.KING_CLUBS,
				Deck.KING_DIAMONDS 
				);

		
		setupPlayerBet(playerThree,300L,
				Deck.ACE_HEARTS,
				Deck.KING_HEARTS 
				);
		
		setBoardAndDivideChips(
				Deck.TWO_CLUBS, 
				Deck.THREE_DIAMONDS,
				Deck.SIX_CLUBS, 
				Deck.SEVEN_DIAMONDS,
				Deck.TEN_SPADES);
		
		assertEquals(3200L, playerOne.getChipCount());
		assertEquals(3000L, playerTwo.getChipCount());
		assertEquals(2800L, playerThree.getChipCount());
		
	}
	

	@Test
	void testPlayerOneWins_with_high_bet_then_player_two() {
	
		setupPlayerBet(playerOne,300L,
				Deck.ACE_CLUBS,
				Deck.ACE_SPADES 
				);

		setupPlayerBet(playerTwo,200L,
				Deck.KING_CLUBS,
				Deck.KING_DIAMONDS 
				);

		
		setupPlayerBet(playerThree,100L,
				Deck.ACE_HEARTS,
				Deck.KING_HEARTS 
				);
		
		setBoardAndDivideChips(
				Deck.TWO_CLUBS, 
				Deck.THREE_DIAMONDS,
				Deck.SIX_CLUBS, 
				Deck.SEVEN_DIAMONDS,
				Deck.TEN_SPADES);
		
		assertEquals(3300L, playerOne.getChipCount());
		assertEquals(2800L, playerTwo.getChipCount());
		assertEquals(2900L, playerThree.getChipCount());
		
	}
	
	@Test
	void testPlayerOneWins_with_bet_in_middle() {
	
		setupPlayerBet(playerOne,300L,
				Deck.ACE_CLUBS,
				Deck.ACE_SPADES 
				);

		setupPlayerBet(playerTwo,500L,
				Deck.KING_CLUBS,
				Deck.KING_DIAMONDS 
				);

		
		setupPlayerBet(playerThree,100L,
				Deck.ACE_HEARTS,
				Deck.KING_HEARTS 
				);
		
		setBoardAndDivideChips(
				Deck.TWO_CLUBS, 
				Deck.THREE_DIAMONDS,
				Deck.SIX_CLUBS, 
				Deck.SEVEN_DIAMONDS,
				Deck.TEN_SPADES);
		
		assertEquals(3400L, playerOne.getChipCount());
		assertEquals(2700L, playerTwo.getChipCount());
		assertEquals(2900L, playerThree.getChipCount());
		
	}
	
	

	@Test
	void testAllPlayers_AllIn() {
	
		setupPlayerAllIn(playerOne,
				Deck.ACE_CLUBS,
				Deck.KING_CLUBS 
				);

		setupPlayerAllIn(playerTwo,
				Deck.ACE_DIAMONDS,
				Deck.KING_DIAMONDS 
				);

		
		setupPlayerAllIn(playerThree,
				Deck.ACE_HEARTS,
				Deck.KING_HEARTS 
				);
		
		setBoardAndDivideChips(
				Deck.TWO_CLUBS, 
				Deck.THREE_DIAMONDS,
				Deck.SIX_CLUBS, 
				Deck.SEVEN_DIAMONDS,
				Deck.TEN_SPADES);
		
		assertEquals(3000L, playerOne.getChipCount());
		assertEquals(3000L, playerTwo.getChipCount());
		assertEquals(3000L, playerThree.getChipCount());
		
	}
	
	@Test
	void testAllPlayers_same_strength_not_all_Same_chips_count() {
	
		playerOne.setChipCount(1000L);
		setupPlayerAllIn(playerOne,
				Deck.ACE_CLUBS,
				Deck.KING_CLUBS 
				);

		setupPlayerAllIn(playerTwo,
				Deck.ACE_DIAMONDS,
				Deck.KING_DIAMONDS 
				);

		
		setupPlayerAllIn(playerThree,
				Deck.ACE_HEARTS,
				Deck.KING_HEARTS 
				);
		
		setBoardAndDivideChips(
				Deck.TWO_CLUBS, 
				Deck.THREE_DIAMONDS,
				Deck.SIX_CLUBS, 
				Deck.SEVEN_DIAMONDS,
				Deck.TEN_SPADES);
		
		assertEquals(1000L, playerOne.getChipCount());
		assertEquals(3000L, playerTwo.getChipCount());
		assertEquals(3000L, playerThree.getChipCount());
		
	}

	

	private Player setupPlayerAllIn(Player player,  Card c1, Card c2 ) {

		CardPair cp = new CardPair(c1, c2);
		player.setCards(cp);
		Long chips = player.getChipCount();
		player.setLastAction(Actions.getAllInAction(chips, chips));
		player.setBetChipCount(chips);

		return player;
	}
	

	
	private Player setupPlayerBet(Player player, Long betChips,  Card c1, Card c2 ) {

		CardPair cp = new CardPair(c1, c2);
		player.setCards(cp);
		player.setLastAction(Actions.getCallAction(betChips));
		player.setBetChipCount(betChips);

		return player;
	}
	
	
	private void setBoardAndDivideChips(Card c1, Card c2, Card c3, Card c4, Card c5) {
		game.setBoardCards(c1,c2,c3,c4,c5);
		game.takeChipsToPot();
		
		Map<Player, Integer> strengthResults = game.getEvaluator().evaluatePlayers(game);
		Map<Player, Long> winnings = game.getDealer().divideChipsFromPot(strengthResults, game.getPotChipCount());
		game.updatePlayerResult(game, strengthResults, winnings);
	}

}
