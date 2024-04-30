package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import game.actions.Action;
import game.actions.ActionType;
import game.actions.Actions;
import game.cards.Card;
import game.cards.CardPair;
import game.dealer.Dealer;
import game.environment.Environment;
import game.evaluator.DefaultEvaluator;
import game.evaluator.Evaluator;
import game.player.Player;

public class Game {
	public static final long DEFAULT_SMALL_BLIND = 15;
	
	private long smallBlind;
	private long potChipCount;
	private long callChipCount;
	
	private List<Player> players;
	private List<Player> activePlayers;
	
	private List<Card> cardsOnBoard;

	
	private int numCurrentPlayer;
	private int numSmallBlindPlayer;
	
	Dealer dealer;
	Evaluator evaluator;
	Environment env;
		
	boolean readyForRound;
	
	public Game(List<Player> players, Environment env) {
		if (players.size() > 10 || players.size() < 2) {
			throw new IllegalStateException("Expected number of players between"
					+ "10 and 2");
		}
		this.env = env;
		resetGame(players);
	}

	public void resetGame(List<Player> players) {
		this.players = new ArrayList<>(players);
		
		smallBlind = DEFAULT_SMALL_BLIND;
		potChipCount = 0;
		callChipCount = 0;
		
		numCurrentPlayer = 0;
		numSmallBlindPlayer = -1;
		
		activePlayers = new ArrayList<>(players.size());
		cardsOnBoard = new ArrayList<>(5);
		
		this.dealer = new Dealer();
		
		this.evaluator = new DefaultEvaluator(this);
		
		readyForRound = false;
	}
	
	public void setupNewRound() {
		numSmallBlindPlayer++;
		numCurrentPlayer = numSmallBlindPlayer;	
		activePlayers.clear();
		activePlayers.addAll(players);
		resetPlayers();
		callChipCount = smallBlind * 2;
		potChipCount = 0;
		cardsOnBoard.clear();
		dealer.reshuffleCards();
		
		readyForRound = true;
	}

	private void resetPlayers() {
		// TODO Auto-generated method stub
		for (Player p : players) {
			p.setUpPlayerForNewRound();
		}
	}

	public void playRound() {
		if (readyForRound = false) {
			throw new IllegalStateException("Game is not set up for new round!");
		}
		drawPlayerCards();
		takeBlinds();
		while(true) {
			Action curAction = getNextAction();
			env.updatePlayerAction(curAction, numCurrentPlayer);
			if (handleAction(curAction)) {
				break;
			}
		}
		Map<Player, Integer> strengthResults = evaluator.evaluatePlayers();
		Map<Player, Long> winnings = dealer.divideChipsFromPot(strengthResults, this.getPotChipCount());
		env.updateResults(this, winnings);
		
		readyForRound = false;
	}
	
	public boolean handleAction(Action curAction) {
		if (curAction.getActionType() == ActionType.FOLD) {
			activePlayers.remove(players.get(numCurrentPlayer));
		}
		if (curAction.getActionType() != ActionType.RAISE &&
				checkPastActions()) {
			if (cardsOnBoard.size() == 5) {
				return true;
			}
			addChipsToPot();
			drawBoardCard();
			env.updateBoard(cardsOnBoard);
			callChipCount = 0;
			numCurrentPlayer = numSmallBlindPlayer - 1;
			resetPlayerActions();	
		}
		else if (curAction.getActionType() == ActionType.RAISE) {
			callChipCount = curAction.getMoveValue();
		}
		return false;
	}
	


	private void addChipsToPot() {
		// TODO Auto-generated method stub
		for (int i = 0; i < players.size(); i++) {
			takePlayerChipsOnTable(i);
		}
	}

	private void drawBoardCard() {
		// TODO Auto-generated method stub
		if (cardsOnBoard.size() >= 3) {
			cardsOnBoard.add(dealer.drawCard());
		}
		else {
			cardsOnBoard.add(dealer.drawCard());
			cardsOnBoard.add(dealer.drawCard());
			cardsOnBoard.add(dealer.drawCard());
		}
	}

	private boolean checkPastActions() {
		// TODO Auto-generated method stub
		/*OptionalLong maxChipValueOpt = activePlayers.stream().filter(p -> p.getLastAction().getActionType() != ActionType.NONE).
				mapToLong(p -> p.getLastAction().getMoveValue()).max();
		if (maxChipValueOpt.isEmpty()) return false;
		long maxChipValue = maxChipValueOpt.getAsLong();
		*/
		for (Player p : activePlayers) {
			Action lastAction = p.getLastAction();
			int playerIndex = players.indexOf(p);
			if(p.getLastAction().getActionType() == ActionType.ALLIN) {
				continue;
			}
			if (lastAction.getActionType() == ActionType.NONE) {
				return false;
			}
			else if (lastAction.getActionType() == ActionType.CHECK && 
					callChipCount != 0  ||
					p.getBetChipCount() < smallBlind * 2 ||
					playerIndex == numSmallBlindPlayer + 1 &&
					p.getBetChipCount() == smallBlind + 2) {
				return false;
			}
			else if (p.getBetChipCount() != callChipCount) {
				return false;
			}
		}
		return true;
	}
	
	private void resetPlayerActions() {
		for (Player p : players) {
			p.setLastAction(Actions.getNoneAction());
		}
	}

	public Action getNextAction() {
		// TODO Auto-generated method stub
		getNextPlayer();
		Player curPlayer = players.get(numCurrentPlayer);
		return curPlayer.getPlayerAction(this);
	}
	
	private int getFirstPlayer() {
		// TODO Auto-generated method stub
		numCurrentPlayer = numSmallBlindPlayer;
		return getNextPlayer();
	}

	private int getNextPlayer() {
		do {
			numCurrentPlayer = ++numCurrentPlayer % players.size();
		} while(!activePlayers.contains(players.get(numCurrentPlayer)));
		return numCurrentPlayer;
	}

	private void drawPlayerCards() {
		// TODO Auto-generated method stub
		for (Player p: players) {
			p.setCards(dealer.dealPair());
		}
	}
	
	public void takeBlinds() {
		if (numCurrentPlayer != numSmallBlindPlayer) {
			throw new IllegalStateException("Expected current player to be small blind, small blind index: " + numSmallBlindPlayer + ", currPlayerIndex: " + numCurrentPlayer);
		}
		players.get(numCurrentPlayer++).takeSmallBlind(smallBlind);
		players.get(numCurrentPlayer).takeBigBlind(smallBlind);
	}
	
	private long takePlayerChipsOnTable(int index) {
		long amount = players.get(index).takeBetChips();
		potChipCount += amount;
		return amount;
	}

	public long getSmallBlind() {
		return smallBlind;
	}

	public long getPotChipCount() {
		return potChipCount;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Player> getActivePlayers() {
		return activePlayers;
	}

	public int getNumCurrentPlayer() {
		return numCurrentPlayer;
	}

	public int getNumSmallBlindPlayer() {
		return numSmallBlindPlayer;
	}

	public List<Card> getCardsOnBoard() {
		return cardsOnBoard;
	}
	
	public long getCallChipCount() {
		return callChipCount;
	}
}
