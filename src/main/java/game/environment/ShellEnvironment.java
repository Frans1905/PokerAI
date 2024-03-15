package game.environment;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import game.Game;
import game.actions.Action;
import game.actions.ActionType;
import game.actions.Actions;
import game.cards.Card;
import game.player.Player;

public class ShellEnvironment implements Environment {

	private Scanner sc;

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
		System.out.println("Raised to " + Long.toString(raiseValue) + " chips");
		return Actions.getRaiseAction(callValue, raiseValue);
	}

	private Action handleFold() {
		System.out.println("Folded");
		return Actions.getFoldAction();
	}

	private Action handleCall(long callValue) {
		// TODO Auto-generated method stub
		System.out.println("Called for " + Long.toString(callValue) + " chips");
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
		|     |
		|	  |
		|  K  |
		|	  |
		|_____|

		 */

		for (int i = 0; i < cardsOnBoard.size(); i++) {
			System.out.print(" _____ ");
			System.out.print(" ");
		}

		System.out.println();

		for (int i = 0; i < 2; i++) {
			drawEdges(cardsOnBoard.size());
			System.out.println();

		}

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


		drawEdges(cardsOnBoard.size());
		System.out.println();

		for (int i = 0; i < cardsOnBoard.size(); i++) {
			System.out.print("|_____|");
			System.out.print(" ");
		}
		System.out.println();
	}

	private void drawEdges(int size) {
		// TODO Auto-generated method stub
		for (int i = 0; i < size; i++) {
			System.out.print("|     |");
			System.out.print(" ");
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
}
