package game.environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import game.Game;
import game.actions.Action;
import game.actions.ActionType;
import game.actions.Actions;
import game.cards.Card;
import game.cards.CardPair;
import game.cards.CardRank;
import game.cards.CardSuit;
import game.player.Player;
import neural.NeuralNetwork;
import neural.fitness.FitnessTracker;

public class NeuralNetworkEnvironment implements Environment {

	private final static float ALLIN_CUTOFF = 10f;
	private NeuralNetwork net;
	private FitnessTracker tracker;
	private List<Player> playedThisRound;
	private int index;
	
	public NeuralNetworkEnvironment(NeuralNetwork net) {
		this(net, null);
	}
	
	public NeuralNetworkEnvironment(NeuralNetwork net, FitnessTracker tracker) {
		this.net = net;
		this.tracker = tracker;
		this.playedThisRound = new ArrayList<>();
	}
	
	@Override
	public Action getInput(Game game) {
		// TODO Auto-generated method stub
		INDArray neuralInput = parseNeuralInput(game);
		INDArray output = this.net.process(neuralInput);
		return parseOutput(output, game);
	}

	private Action parseOutput(INDArray output, Game game) {
		// TODO Auto-generated method stub
		Player thisp = game.getPlayers().get(this.index);
		float moveVal = output.getFloat(0);
		Action act;
		if (moveVal < 0.30f) {
			act = Actions.getFoldAction();
		}
		else if (moveVal < 0.80f) {
			if (game.getCallChipCount() == thisp.getBetChipCount()) {
				act = Actions.getCheckAction(game.getCallChipCount());
			}
			else if(game.getCallChipCount() >= thisp.getChipCount()) {
				act = Actions.getAllInAction(thisp.getChipCount(), game.getCallChipCount());
			}
			else {
				act = Actions.getCallAction(game.getCallChipCount());
			}
		}
		else {
			float raiseValueSig = output.getFloat(1);
			if (raiseValueSig > ALLIN_CUTOFF || game.getMinimumRaiseValue() >= thisp.getChipCount()) {
				act = Actions.getAllInAction(thisp.getChipCount(), game.getCallChipCount());
			}
			else {
				act = parseRaiseAction(game, thisp, raiseValueSig);
				/**
				long value = (long) (game.getMinimumRaiseValue() + raiseValueSig * (thisp.getChipCount() - game.getMinimumRaiseValue()));
				act = Actions.getRaiseAction(game.getCallChipCount(), value);
				**/
			}
		}
		return act;
	}

	private Action parseRaiseAction(Game game, Player thisp, float raiseValueSig) {
		// TODO Auto-generated method stub
		int raiseMult = 0;
		/**
		if (raiseValueSig < 0.2f) {
			raiseMult = 2;
		}
		else if (raiseValueSig < 0.4f) {
			raiseMult = 3;
		}
		else if (raiseValueSig < 0.6f) {
			raiseMult = 4;
		}
		else if (raiseValueSig < 0.8f) {
			raiseMult = 5;
		}
		else if (raiseValueSig < 0.9f) {
			raiseMult = 6;
		}
		**/
		if (raiseValueSig < 3f) {
			raiseMult = 2;
		}
		else if (raiseValueSig < 6f) {
			raiseMult = 3;
		}
		else if (raiseValueSig <= 10f) {
			raiseMult = 4;
		}
		long raiseval = Math.min(Math.max(game.getCallChipCount() * raiseMult, 5), thisp.getChipCount());
		if (raiseval == thisp.getChipCount()) {
			return Actions.getAllInAction(raiseval, game.getCallChipCount());
		}
		else {
			return Actions.getRaiseAction(game.getCallChipCount(), raiseval);
		}
	}

	private INDArray parseNeuralInput(Game game) {
		// TODO Auto-generated method stub
		INDArray input = Nd4j.create(48);
		List<Card> cardsOnBoard = game.getCardsOnBoard();
		int index = 0;
		for (int i = 0; i < 5; i++) {
			float rank;
			float suit;
			if (i >= cardsOnBoard.size()) {
				rank = 0;
				suit = 0;
			}
			else {
				rank = cardsOnBoard.get(i).getValue();
				suit = cardsOnBoard.get(i).getRank().ordinal() + 1;
			}
			index = parseCardValue(input, index, rank, suit);
		}
		
		CardPair playerCards = game.getPlayers().get(this.index).getCards();
		float rank1 = playerCards.getFirstCard().getValue();
		float suit1 = playerCards.getFirstCard().getSuit().ordinal() + 1;
		float rank2 = playerCards.getSecondCard().getValue();
		float suit2 = playerCards.getSecondCard().getSuit().ordinal() + 1;
		index = parseCardValue(input, index, rank1, suit1);
		index = parseCardValue(input, index, rank2, suit2);
		input.putScalar(index++, this.index / (game.getPlayers().size() - 1));
		input.putScalar(index++, game.getNumSmallBlindPlayer() / (game.getPlayers().size() - 1));
		input.putScalar(index++, game.getPotChipCount() / (game.getPlayers().size() * Game.STARTING_CHIP_COUNT));
		Player thisp = game.getPlayers().get(this.index);
		input.putScalar(index++, thisp.getChipCount() / Game.STARTING_CHIP_COUNT);
		input.putScalar(index++, thisp.getTotalBetChipCount() / (float)thisp.getChipCount());
		//put into input
		for (int i = 0; i < game.getPlayers().size(); i++) {
			int playerIndex = (game.getNumSmallBlindPlayer() + i) % game.getPlayers().size();
			if (playerIndex == this.index) {
				continue;
			}
			Player p = game.getPlayers().get(playerIndex);
			if (p.getLastAction().getActionType() == ActionType.FOLD &&
					!this.playedThisRound.contains(p)) {
				input.putScalar(index++, 0);
				input.putScalar(index++, 0);
				continue;
			}
			else if(!game.getActivePlayers().contains(p) && p.getLastAction().getActionType() != ActionType.FOLD) {
				input.putScalar(index++, -1);
				input.putScalar(index++, 0);
				continue;
			}
			if (game.getActivePlayers().contains(p) &&
					!this.playedThisRound.contains(p)) {
				input.putScalar(index++, (ActionType.NONE.ordinal() + 1) / 6);
				input.putScalar(index++, 0);
				continue;
			}
			
			input.putScalar(index++, (p.getLastAction().getActionType().ordinal() + 1) / 6);
			input.putScalar(index++, p.getLastAction().getMoveValue() / (float)p.getChipCount());
		}
		return input;
	}

	private int parseCardValue(INDArray input, int index, float rank, float suit) {
		// TODO Auto-generated method stub
		CardSuit[] values = CardSuit.values();
		input.putScalar(index++, rank / (float)(CardRank.ACE.ordinal() + 1));
		for (int i = 0; i < 4; i++) {
			if (values[i].ordinal() + 1 == suit) {
				input.putScalar(index++, 1);
			}
			else {
				input.putScalar(index++, 0);
			}
		}
		return index;
	}

	@Override
	public void updatePlayerAction(Game game, Action curAction, int numCurrentPlayer) {
		// TODO Auto-generated method stub
		this.playedThisRound.add(game.getPlayers().get(numCurrentPlayer));
		this.tracker.informAction(net, curAction);
	}

	@Override
	public void updateBoard(List<Card> drawnCards) {
		// TODO Auto-generated method stub
		this.playedThisRound.clear();;
	}

	@Override
	public void updateResults(Game game, Map<Player, Integer> strengthResults, Map<Player, Long> winnings) {
		// TODO Auto-generated method stub
		this.playedThisRound.clear();
		/**
		Player thisp = game.getPlayers().get(index);
		if (winnings.getOrDefault(thisp, -1l) == 0 && thisp.getLastAction().getActionType() == ActionType.RAISE ||
				thisp.getLastAction().getActionType() == ActionType.ALLIN) {
			tracker.subtractPoints(this.net, 500);
		}
		**/
		Player p = game.getPlayers().get(index);
		List<Integer> values = new ArrayList<>(strengthResults.values());
		Collections.sort(values);
		this.tracker.addRoundResults(net, winnings.getOrDefault(p, 0l), values.indexOf(strengthResults.get(p)));
	}

	@Override
	public void updateEnd(Game game, int numCurrentPlayer) {
		// TODO Auto-generated method stub
		tracker.addGameResults(net, (float) game.getPlayers().get(numCurrentPlayer).getChipCount());
	}

	@Override
	public void setIndex(int index) {
		// TODO Auto-generated method stub
		this.index = index;
	}

}
