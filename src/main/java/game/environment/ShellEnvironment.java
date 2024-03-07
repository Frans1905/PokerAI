package game.environment;

import java.util.List;
import java.util.Scanner;

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
		
		drawCards(game.getCardsOnBoard());
		drawPlayerActions(game.getActivePlayers(), curPlayerIndex);
		
		Player curPlayer = game.getPlayers().get(curPlayerIndex);
		long callValue = game.getCallChipCount();
		
		Action action = null;
		while (action == null) {
			System.out.println("What do you wanna do ");
			if (curPlayer.getLastAction().getActionType() == ActionType.FOLD) {
				throw new IllegalStateException("Expected active player");
			}
			
			if (callValue > curPlayer.getChipCount()) {
				handleAllInFold(curPlayer.getChipCount());
			}
			else if (true /*todo*/) {
				action = handleCallRaiseFoldCase(callValue);
			}
		}
		
		return null;
	}

	private Action handleAllInFold(long callValue) {
		// TODO Auto-generated method stub
		System.out.println("(AllIn/Fold): ");
		String input = sc.nextLine();
		Action action = null;
		if (input.equalsIgnoreCase("AllIn")) {
			action = Actions.getCallAction(callValue);
		}
		else if (input.equalsIgnoreCase("Fold")) {
			action = Actions.getFoldAction();
		}
		return action;
	
	}

	private Action handleCallRaiseFoldCase(long callValue) {
		// TODO Auto-generated method stub
		System.out.print("Call/Raise/Fold): ");
		String input = sc.nextLine();
		Action action = null;
		if (input.equalsIgnoreCase("Call")) {
			action = handleCall(callValue);
		}
		else if (input.equalsIgnoreCase("Raise")) {
			action = handleRaise(callValue);
		}
		else if (input.equalsIgnoreCase("Fold")) {
			action = handleFold();
		}
		return action;
	}
	

	private Action handleRaise(Long callValue) {
		// TODO Auto-generated method stub
		Long raiseValue = null;
		while (raiseValue == null) {
			System.out.println("Raise to: ");
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
		System.out.println("Called for " + Long.toString(callValue) + " chips");
		return Actions.getCallAction(callValue);
	}

	private void drawPlayerActions(List<Player> players, int currentPlayerIndex) {
		// TODO Auto-generated method stub
		for (Player p : players) {
			System.out.print("Player " + players.indexOf(p) + ": ");
			if (players.indexOf(p) == currentPlayerIndex) {
				System.out.println("YOU");
				continue;
			}
			System.out.println(p.getLastAction().toString());
		}
	}

	private void drawCards(List<Card> cardsOnBoard) {
		// TODO Auto-generated method stub
	/*	 _____
		|     |
		|	  |
		|  K  |
		|	  |
		|_____|
		
	 */
		System.out.println("Drawn cards: ");

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
			System.out.print(cardsOnBoard.get(i).getRank().name().substring(0, 0));
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

}
