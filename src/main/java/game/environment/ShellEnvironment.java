package game.environment;

import static java.util.Map.entry;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import game.Game;
import game.actions.Action;
import game.actions.ActionType;
import game.actions.Actions;
import game.cards.Card;
import game.cards.CardPair;
import game.cards.CardSuit;
import game.player.Player;

public class ShellEnvironment implements Environment {

	private Scanner sc;
	
	private static final Map<CardSuit, String> SYMBOL_MAP = Map.ofEntries(
			entry(CardSuit.CLUBS, "♣"),
			entry(CardSuit.DIAMONDS, "♦"),
			entry(CardSuit.HEARTS, "♥"),
			entry(CardSuit.SPADES, "♠"));

	public ShellEnvironment() {
		sc = new Scanner(System.in);
	}

	@Override
	public Action getInput(Game game) {
		// TODO Auto-generated method stub
		int curPlayerIndex = game.getNumCurrentPlayer();

		drawState(game);

		Player curPlayer = game.getPlayers().get(curPlayerIndex);
		long callValue = game.getCallChipCount();

		Action action = null;
		while (action == null) {
			System.out.print("What do you wanna do ");
			if (curPlayer.getLastAction().getActionType() == ActionType.FOLD) {
				System.out.println("");
				throw new IllegalStateException("Expected active player");
			}

			if (callValue > curPlayer.getChipCount()) {
				action = handleAllInFoldCase(curPlayer.getChipCount(), curPlayer);
			}
			else if (callValue == curPlayer.getBetChipCount()) {
				action = handleCheckRaiseFoldCase(callValue);
			}
			else {
				action = handleCallRaiseFoldCase(callValue);
			}

			if (!action.isValid(curPlayer)) {
				System.out.println("Invalid input");
				action = null;
				continue;
			}
		}
		drawBorder();
		return action;
	}

	private void drawState(Game game) {
		// TODO Auto-generated method stub
		Player curPlayer = game.getPlayers().get(game.getNumCurrentPlayer());
		System.out.println("Drawn cards: ");
		drawCards(game.getCardsOnBoard());
		System.out.println("");
		drawPlayerActions(game);

		System.out.println("Your chip stack size: " + (curPlayer.getChipCount() - curPlayer.getBetChipCount()) + " chips");

		System.out.println("Hand:");
		drawCards(curPlayer.getCards().getCardList());
	}

	private void drawBorder() {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("==============================");
		System.out.println("==============================");
		System.out.println();
	}

	private Action handleCheckRaiseFoldCase(long callValue) {
		// TODO Auto-generated method stub
		System.out.print("(Check/Raise/Fold): ");
		Action action = null;
		while(true) {
			String input = sc.nextLine();
			if (input.equalsIgnoreCase("Check")) {
				action = Actions.getCheckAction();
				break;
			}
			else if(input.equalsIgnoreCase("Raise")) {
				action = handleRaise(callValue);
				break;
			}
			else if(input.equalsIgnoreCase("Fold")) {
				action = handleFold();
				break;
			}
			System.out.println("Invalid input: " + input);
			System.out.print("What do you wanna do(Check/Raise/Fold): ");
		}
		return action;


	}

	private Action handleAllInFoldCase(long callValue, Player curPlayer) {
		// TODO Auto-generated method stub
		System.out.print("(AllIn/Fold): ");
		Action action = null;
		while (true) {
			String input = sc.nextLine();
			if (input.equalsIgnoreCase("AllIn")) {
				action = Actions.getAllInAction(curPlayer.getChipCount(), callValue);
				break;
			}
			else if (input.equalsIgnoreCase("Fold")) {
				action = handleFold();
				break;
			}
			System.out.println("Invalid input: " + input);
			System.out.print("What do you wanna do(AllIn/Fold): ");
		}
		return action;

	}

	private Action handleCallRaiseFoldCase(long callValue) {
		// TODO Auto-generated method stub
		System.out.print("(Call/Raise/Fold): ");
		Action action = null;
		while (true) {
			String input = sc.nextLine();
			if (input.equalsIgnoreCase("Call")) {
				action = handleCall(callValue);
				break;
			}
			else if (input.equalsIgnoreCase("Raise")) {
				action = handleRaise(callValue);
				break;
			}
			else if (input.equalsIgnoreCase("Fold")) {
				action = handleFold();
				break;
			}
			System.out.println("Invalid input: " + input);
			System.out.print("What do you wanna do(Call/Raise/Fold): ");
		}
		return action;
	}


	private Action handleRaise(Long callValue) {
		// TODO Auto-generated method stub
		Long raiseValue = null;
		while (raiseValue == null) {
			System.out.print("Raise to: ");
			try {
				raiseValue = Long.parseLong(sc.nextLine());
			}
			catch(NumberFormatException e) {
				System.out.println("Invalid value");
				continue;
			}
		}
		return Actions.getRaiseAction(callValue, raiseValue);
	}

	private Action handleFold() {
		return Actions.getFoldAction();
	}

	private Action handleCall(long callValue) {
		// TODO Auto-generated method stub
		return Actions.getCallAction(callValue);
	}

	private void drawPlayerActions(Game game) {
		int currentPlayerIndex = game.getNumCurrentPlayer();
		List<Player> players = game.getPlayers();
		// TODO Auto-generated method stub
		int i;
		for (i = game.getNumCurrentPlayer() + 1; i != currentPlayerIndex; i = (i + 1) % players.size()) {
			Player p = players.get(i);
			if (p.getLastAction().getActionType() == ActionType.NONE &&
					(p.getBetChipCount() == 0 || game.getCallChipCount() == 0)) {
				continue;
			}
			if (!game.getActivePlayers().contains(p)) {
				continue;
			}
			System.out.print("Player " + (i + 1));
			if (i == game.getNumSmallBlindPlayer()) {
				System.out.print("(Small blind)");
			}
			else if (i == game.getNumSmallBlindPlayer() + 1) {
				System.out.print("(Big blind)");
			}
			System.out.print(": ");
			if (p.getLastAction().getActionType() == ActionType.NONE &&
					game.getCallChipCount() != 0) {
				System.out.println("Put in blind of " + p.getBetChipCount() + " chips");
			}
			else {
				System.out.println(p.getLastAction().toString());
			}
		}
		System.out.print("Player " + (i + 1));
		if (i == game.getNumSmallBlindPlayer()) {
			System.out.print("(Small blind)");
		}
		else if (i == game.getNumSmallBlindPlayer() + 1) {
			System.out.print("(Big blind)");
		}
		System.out.print(": ");
		System.out.println("YOU");

	}


	private void drawCards(List<Card> cardsOnBoard) {
		// TODO Auto-generated method stub
		/*	 
		 _____
		|♠    |
		|	  |
		|  K  |
		|	  |
		|_____|
			
		 */

		drawTop(cardsOnBoard);
		drawEdges(cardsOnBoard.size(), 1);

		drawCenter(cardsOnBoard);

		drawEdges(cardsOnBoard.size(), 1);
		drawBottom(cardsOnBoard);
		
		System.out.println("");

	}

	private void drawBottom(List<Card> cardsOnBoard) {
		// TODO Auto-generated method stub
		for (int i = 0; i < cardsOnBoard.size(); i++) {
			System.out.print("|_____|");
			System.out.print(" ");
		}
		System.out.println();
	}

	private void drawCenter(List<Card> cardsOnBoard) {
		// TODO Auto-generated method stub
		for (int i = 0; i < cardsOnBoard.size(); i++) {
			System.out.print("|  ");
			if (cardsOnBoard.get(i).getValue() > 9) {
				System.out.print(cardsOnBoard.get(i).getRank().name().substring(0, 1));
			}
			else {
				System.out.print(cardsOnBoard.get(i).getValue());
			}
			System.out.print("  |");
			System.out.print(" ");
		}
		System.out.println();
	}

	private void drawTop(List<Card> cardsOnBoard) {
		for (int i = 0; i < cardsOnBoard.size(); i++) {
			System.out.print(" _____ ");
			System.out.print(" ");
		}
		System.out.println();
		
		for (int i = 0; i < cardsOnBoard.size(); i++) {
			System.out.print("|");
			System.out.print(SYMBOL_MAP.get(cardsOnBoard.get(i).getSuit()));
			System.out.print("    | ");
		}
		System.out.println();
	}
	
	private void drawEdges(int size, int count) {
		// TODO Auto-generated method stub
		for (int j = 0; j < count; j++) {	
			for (int i = 0; i < size; i++) {
				System.out.print("|     |");
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	@Override
	public void updatePlayerAction(Action curAction, int numCurrentPlayer) {
		// TODO Auto-generated method stub
		waitRand();
		System.out.println("Player " + (numCurrentPlayer + 1) +": " + curAction.toString());
		drawBorder();
	}

	@Override
	public void updateBoard(List<Card> drawnCards) {
		// TODO Auto-generated method stub
		System.out.println("Drawing cards!");
		drawBorder();
		waitRand();
		drawCards(drawnCards);
		drawBorder();
	}

	private void waitRand() {
		int rand = (int) (Math.random() * 3 + 1);
		try {
			TimeUnit.SECONDS.sleep(rand);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateResults(Game game, Map<Player, Long> winnings) {
		// TODO Auto-generated method stub
		
		System.out.println("Drawn cards:");
		drawCards(game.getCardsOnBoard());
		
		List<Player> players = game.getActivePlayers();
		for (int i = 0; i < players.size(); i++) {
			CardPair pair = players.get(i).getCards();
			System.out.printf("Player %d cards:\n", i + 1);
			drawCardPair(pair);
		}
		for (int i = 0; i < players.size(); i++) {
			if (winnings.containsKey(players.get(i))) {
				System.out.printf("Player %d: won %d chips\n", i + 1, winnings.get(players.get(i)));
			}
		}

		drawBorder();
	}

	private void drawCardPair(CardPair pair) {
		// TODO Auto-generated method stub
		drawTop(pair.getCardList());
		drawEdges(2, 1);
		
		drawCenter(pair.getCardList());
		
		drawEdges(2, 1);
		drawBottom(pair.getCardList());
		System.out.println("");
		
	}
}
