package game;

import java.util.ArrayList;

import game.cards.Deck;
import game.player.Player;

public class Game {
	public static final long DEFAULT_SMALL_BLIND = 15;
	
	private long smallBlind;
	private long potChipCount;
	
	private ArrayList<Player> players;
	private int numCurrentPlayer;
	private int numSmallBlindPlayer;
	
	private Deck cardDeck;
	
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
	}
	
}
