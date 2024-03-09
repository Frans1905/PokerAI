package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalLong;

import game.actions.Action;
import game.actions.ActionType;
import game.actions.Actions;
import game.cards.Card;
import game.cards.CardPair;
import game.dealer.Dealer;
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
	private List<Long> playerRoundChipsInPot;
	
	private List<CardPair> playerCards; 
	private List<Card> cardsOnBoard;

	
	private int numCurrentPlayer;
	private int numSmallBlindPlayer;
	
	Dealer dealer;
	Evaluator evaluator;
		
	boolean readyForRound;
	
	public Game(List<Player> players) {
		if (players.size() > 10 || players.size() < 2) {
			throw new IllegalStateException("Expected number of players between"
					+ "10 and 2");
		}
		this.players = new ArrayList<>(players);
		
		smallBlind = DEFAULT_SMALL_BLIND;
		potChipCount = 0;
		callChipCount = 0;
		playerRoundChipsInPot = new ArrayList<>(players.size());
		
		numCurrentPlayer = 0;
		numSmallBlindPlayer = 0;
		
		playerCards = new ArrayList<>(players.size());
		this.dealer = new Dealer();
		
		this.evaluator = new DefaultEvaluator(this);
		
		readyForRound = false;
	}
	
	public void setupNewRound() {
		numSmallBlindPlayer++;
		numCurrentPlayer = numSmallBlindPlayer;	
		activePlayers.clear();
		activePlayers.addAll(players);
		resetPlayerActions();
		callChipCount = smallBlind * 2;
		resetChipsInPot();
		cardsOnBoard.clear();
		dealer.reshuffleCards();
		
		readyForRound = true;
	}

	private void resetPlayerActions() {
		// TODO Auto-generated method stub
		for (Player p : players) {
			p.setLastAction(Actions.getNoneAction());
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
			if (handleAction(curAction)) {
				break;
			}
		}
		Map<Player, Long> results = evaluator.evaluatePlayers();
		for (Entry<Player, Long> e : results.entrySet()) {
			e.getKey().giveChips(e.getValue());
		}
		readyForRound = false;
	}

	public boolean handleAction(Action curAction) {
		if (curAction.getActionType() == ActionType.FOLD) {
			activePlayers.remove(numCurrentPlayer);
		}
		if (curAction.getActionType() != ActionType.RAISE &&
				checkPastActions()) {
			if (cardsOnBoard.size() == 5) {
				return true;
			}
			addChipsToPot();
			drawBoardCard();
			numCurrentPlayer = numSmallBlindPlayer;
			resetPlayerActions();	
		}
		else if (curAction.getActionType() == ActionType.RAISE) {
			callChipCount = curAction.getMoveValue();
		}
		return false;
	}
	


	private void addChipsToPot() {
		// TODO Auto-generated method stub
		potChipCount = activePlayers.size() * callChipCount;
		callChipCount = 0;
	}

	private void drawBoardCard() {
		// TODO Auto-generated method stub
		cardsOnBoard.add(dealer.drawCard());
	}

	private boolean checkPastActions() {
		// TODO Auto-generated method stub
		OptionalLong maxChipValueOpt = activePlayers.stream().filter(p -> p.getLastAction().getActionType() != ActionType.NONE).
				mapToLong(p -> p.getLastAction().getMoveValue()).max();
		if (maxChipValueOpt.isEmpty()) return false;
		long maxChipValue = maxChipValueOpt.getAsLong();
		for (Player p : activePlayers) {
			Action lastAction = p.getLastAction();
			if (lastAction.getActionType() == ActionType.NONE) {
				return false;
			}
			else if (lastAction.getActionType() == ActionType.CHECK
					&& maxChipValue != 0) {
				return false;
			}
			else if (lastAction.getMoveValue() != maxChipValue) {
				return false;
			}
		}
		return true;
	}

	public Action getNextAction() {
		// TODO Auto-generated method stub
		getNextPlayer();
		Player curPlayer = players.get(numCurrentPlayer);
		return curPlayer.getPlayerAction();
	}
	
	private int getFirstPlayer() {
		// TODO Auto-generated method stub
		numCurrentPlayer = numSmallBlindPlayer;
		return getNextPlayer();
	}

	private int getNextPlayer() {
		do {
			numCurrentPlayer++;
		} while(!activePlayers.contains(players.get(numCurrentPlayer)));
		return numCurrentPlayer;
	}

	private void drawPlayerCards() {
		// TODO Auto-generated method stub
		dealer.dealPairs(playerCards);
	}
	
	private void takeBlinds() {
		players.get(numCurrentPlayer++).takeChips(smallBlind);
		players.get(numCurrentPlayer++).takeChips(smallBlind * 2); 
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
	
	private void resetChipsInPot() {
		for (int i = 0; i < players.size(); i++) {
			playerRoundChipsInPot.set(i, (long) 0);
		}
	}
	
	
}
