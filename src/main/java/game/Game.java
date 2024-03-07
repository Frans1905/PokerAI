package game;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.cards.Deck;
import game.player.Player;

public class Game {
	public static final long DEFAULT_SMALL_BLIND = 15;
	
	private long smallBlind;
	private long potChipCount;
	private long callChipCount;
	
	private List<Player> players;
	private List<Player> activePlayers;
	
	private int numCurrentPlayer;
	private int numSmallBlindPlayer;
	
	private Deck cardDeck;
	
	private List<Card> cardsOnBoard;
	
	public Game(ArrayList<Player> players) {
		if (players.size() > 10 || players.size() < 2) {
			throw new IllegalStateException("Expected number of players between"
					+ "10 and 2");
		}
		this.players = new ArrayList<>(players);
		smallBlind = DEFAULT_SMALL_BLIND;
		potChipCount = 0;
		numCurrentPlayer = 0;
		numSmallBlindPlayer = 0;
		cardDeck = new Deck();
	}
	
	public void nextRound() {
		numSmallBlindPlayer++;
		numCurrentPlayer = numSmallBlindPlayer;	
		activePlayers.clear();
		activePlayers.addAll(players);
		callChipCount = smallBlind * 2;
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

	public Deck getCardDeck() {
		return cardDeck;
	}

	public List<Card> getCardsOnBoard() {
		return cardsOnBoard;
	}
	
	public long getCallChipCount() {
		return callChipCount;
	}
	
	
	
}
