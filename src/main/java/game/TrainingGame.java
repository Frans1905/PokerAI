package game;

import java.util.List;

import game.cards.CardPair;
import game.dealer.TrainingDealer;
import game.evaluator.Evaluator;
import game.player.Player;

public class TrainingGame extends Game{
	int roundCount;

	public TrainingGame(Evaluator ev) {
		super(ev);
		this.dealer = new TrainingDealer(5);
		this.roundCount = -1;

		// TODO Auto-generated constructor stub
	}

	public TrainingGame(List<Player> players, Evaluator ev) {
		super(players, ev);
		// TODO Auto-generated constructor stub
		this.roundCount = -1;
		this.dealer = new TrainingDealer(players.size());
	}
	
	@Override
	public void resetGame(List<Player> players) {
		super.resetGame(players);
		this.roundCount = -1;
		this.dealer = new TrainingDealer(players.size());
	}
	
	@Override
	protected void drawPlayerCards() {
		roundCount = (roundCount + 1) % this.getPlayers().size();
		if (roundCount == 0) {
			for (Player p : this.getPlayers()) {
				p.setCards(dealer.dealPair());
			}
			return;
		}
		CardPair temp = this.getPlayers().get(0).getCards();
		for (int i = 1; i < this.getPlayers().size(); i++) {
			this.getPlayers().get(i - 1).setCards(this.getPlayers().get(i).getCards());
		}
		this.getPlayers().get(this.getPlayers().size() - 1).setCards(temp);
	}
	
	
}
