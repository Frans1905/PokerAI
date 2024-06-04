package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.actions.Action;
import game.actions.ActionType;
import game.actions.Actions;
import game.cards.Card;
import game.dealer.Dealer;
import game.evaluator.Evaluator;
import game.player.Player;

public class Game {
	public static final long DEFAULT_SMALL_BLIND = 15;
	public static final long STARTING_CHIP_COUNT = 3000;
	
	private long smallBlind;
	private long potChipCount;
	private long callChipCount;
	private long minimumRaiseValue;
	
	private List<Player> players;
	private List<Player> activePlayers;
	
	private List<Card> cardsOnBoard;

	private int numOfBrokePlayers;
	private int numCurrentPlayer;
	private int numSmallBlindPlayer;
	
	Dealer dealer;
	Evaluator evaluator;
		
	boolean readyForRound;
	
	public Game(List<Player> players, Evaluator ev) {
		this.evaluator = ev;
		resetGame(players);
	}
	
	public Game(Evaluator ev) {
		this(new ArrayList<>(), ev);

	}

	public void resetGame(List<Player> players) {
		this.players = players;
		informPlayersPositions();
		
		smallBlind = DEFAULT_SMALL_BLIND;
		potChipCount = 0;
		callChipCount = 0;
		minimumRaiseValue = 0;
		numOfBrokePlayers = 0;
		
		numCurrentPlayer = 0;
		numSmallBlindPlayer = -1;
		
		activePlayers = new ArrayList<>(players.size());
		cardsOnBoard = new ArrayList<>(5);
		givePlayersChips();
		
		this.dealer = new Dealer();
		
		readyForRound = false;
	}
	
	
	private void givePlayersChips() {
		// TODO Auto-generated method stub
		for (Player p : this.getPlayers()) {
			p.setChipCount(Game.STARTING_CHIP_COUNT);
		}
	}

	private void informPlayersPositions() {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.getPlayers().size(); i++) {
			this.getPlayers().get(i).assignIndex(i);
		}
	}

	public void setupNewRound() {
		if (players.size() > 8 || players.size() < 2) {
			throw new IllegalStateException("Expected number of players between"
					+ "8 and 2");
		}
		activePlayers.clear();
		addActivePlayers(); 
		resetPlayers();
		numSmallBlindPlayer = getFirstPlayer();
		callChipCount = smallBlind * 2;
		minimumRaiseValue = smallBlind * 4;
		potChipCount = 0;
		cardsOnBoard.clear();
		dealer.reshuffleCards();
		
		readyForRound = true;
	}
	
	private void addActivePlayers() {
		for (Player p : this.players) {
			if (p.getChipCount() > 0) {
				activePlayers.add(p);
			}
		}
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
			if (activePlayers.size() == 1) {
				soloPlayerRoundEnd(activePlayers.get(0));
				return;
			}
			Action curAction = Actions.getNoneAction();
			if (!checkIfAllin()) {
				curAction = getNextAction();
				updatePlayersAction(curAction, numCurrentPlayer);
			}
			if (handleAction(curAction)) {
				break;
			}
		}
		Map<Player, Integer> strengthResults = evaluator.evaluatePlayers(this);
		Map<Player, Long> winnings = dealer.divideChipsFromPot(strengthResults, this.getPotChipCount());
		updatePlayerResult(this, strengthResults, winnings);
		
		readyForRound = false;
	}
	
	private void soloPlayerRoundEnd(Player player) {
		// TODO Auto-generated method stub
		addChipsToPot();
		Map<Player,Integer> strengthResults = new HashMap<>();
		strengthResults.put(player, 1);
		for (Player p : this.players) {
			if (p != player) {
				strengthResults.put(p, Integer.MAX_VALUE);
			}
		}
		Map<Player, Long> winnings = new HashMap<>();
		winnings.put(player, potChipCount);
		player.giveChips(potChipCount);
		updatePlayerResult(this, strengthResults, winnings);
		readyForRound = false;
	}

	private boolean checkIfAllin() {
		// TODO Auto-generated method stub
		int count = 0;
		Player nonAllinPlayer = null;;
		for (Player p : activePlayers) {
			if (p.getLastAction().getActionType() != ActionType.ALLIN) {
				if (count == 1 || p.getBetChipCount() != this.callChipCount) {
					return false;
				}
				nonAllinPlayer = p;
				count++;
			}
		}
		if (count == 1) nonAllinPlayer.setLastAction(Actions.getCheckAction(this.callChipCount));
		return true;
	}

	private void updatePlayerResult(Game game, Map<Player, Integer> strengthResults, Map<Player, Long> winnings) {
		// TODO Auto-generated method stub
		for (Player p : this.getPlayers()) {
			if (p.getChipCount() == 0) {
				numOfBrokePlayers++;
			}
			p.getEnvironment().updateResults(this, strengthResults, winnings);
		}

	}

	private void updatePlayersAction(Action curAction, int numCurrentPlayer2) {
		// TODO Auto-generated method stub
		for (Player p : this.players) {
			p.getEnvironment().updatePlayerAction(this, curAction, numCurrentPlayer);
		}

	}

	public boolean handleAction(Action curAction) {
		if (curAction.getActionType() == ActionType.FOLD) {
			activePlayers.remove(players.get(numCurrentPlayer));
		}
		else if (curAction.getActionType() == ActionType.RAISE) {
			minimumRaiseValue = 2 * (curAction.getMoveValue() - callChipCount) + callChipCount;
			callChipCount = curAction.getMoveValue();
		}
		else if (curAction.getActionType() == ActionType.ALLIN && curAction.getMoveValue() > this.callChipCount) {
			minimumRaiseValue = 2 * (curAction.getMoveValue() - callChipCount) + callChipCount;
			callChipCount = curAction.getMoveValue();
		}
		if (curAction.getActionType() != ActionType.RAISE &&
				checkPastActions()) {
			addChipsToPot();
			if (cardsOnBoard.size() == 5) {
				return true;
			}
			drawBoardCard();
			updatePlayersBoard(cardsOnBoard);
			callChipCount = 0;
			minimumRaiseValue = 5;
			numCurrentPlayer = numSmallBlindPlayer - 1;
			resetRoundActions();	
		}

		return false;
	}

	private void resetRoundActions() {
		// TODO Auto-generated method stub
		for (Player p : activePlayers) {
			if (p.getLastAction().getActionType() != ActionType.ALLIN) {
				p.setLastAction(Actions.getNoneAction());
			}
		}
	}

	private void updatePlayersBoard(List<Card> cardsOnBoard2) {
		// TODO Auto-generated method stub
		for (Player p : this.getPlayers()) {
			p.getEnvironment().updateBoard(cardsOnBoard);
		}
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
			if(p.getLastAction().getActionType() == ActionType.ALLIN) {
				continue;
			}
			if (checkCondition(p)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkCondition(Player p) {
		Action lastAction = p.getLastAction();
		int playerIndex = players.indexOf(p);
		if (lastAction.getActionType() == ActionType.NONE) {
			return true;
		}
		if (this.cardsOnBoard.size() == 0) {
			if (playerIndex == numSmallBlindPlayer + 1 && p.getBetChipCount() == smallBlind * 2 &&
					lastAction.getActionType() != ActionType.CHECK) {
				return true;
			}
		}
		if (p.getBetChipCount() != callChipCount) {
			return true;
		}
		return false;
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
		} while(!activePlayers.contains(players.get(numCurrentPlayer)) ||
				players.get(numCurrentPlayer).getLastAction().getActionType() == ActionType.ALLIN);
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
		players.get(numCurrentPlayer).takeSmallBlind(smallBlind);
		numCurrentPlayer = getNextPlayer();
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
	
	public void addPlayer(Player p) {
		if (this.players.size() > 8) {
			throw new IllegalStateException("Game cannot have more than 8 players");
		}
		this.players.add(p);
	}

	public boolean isOver() {
		// TODO Auto-generated method stub
		return numOfBrokePlayers == this.players.size() - 1;
	}
	
	public void endMatch() {
		for (int i = 0; i < this.getPlayers().size(); i++) {
			this.getPlayers().get(i).getEnvironment().updateEnd(this, i);
		}
	}

	public long getMinimumRaiseValue() {
		return minimumRaiseValue;
	}

	public void setMinimumRaiseValue(long minimumRaiseValue) {
		this.minimumRaiseValue = minimumRaiseValue;
	}
	
	
}
