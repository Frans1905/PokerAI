package game.environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import game.Game;
import game.actions.Action;
import game.actions.Actions;
import game.cards.Card;
import game.evaluator.BlindEvaluator;
import game.evaluator.jmp.JmpEvaluator;
import game.player.Player;
import neural.NeuralNetwork;
import neural.fitness.FitnessTracker;

public class SimpleNeuralNetworkEnvironment implements NeuralEnvironment, Serializable {

	private final static float ALLIN_CUTOFF = 10f;
	private NeuralNetwork net;
	private FitnessTracker tracker;
	private List<Player> playedThisRound;
	private int index;
	private static final JmpEvaluator jmpEvaluator = new JmpEvaluator();

	
	public SimpleNeuralNetworkEnvironment(NeuralNetwork net) {
		this(net, null);
	}
	
	public SimpleNeuralNetworkEnvironment(NeuralNetwork net, FitnessTracker tracker) {
		this.net = net;
		this.tracker = tracker;
		this.playedThisRound = new ArrayList<>();
	}
	
	public SimpleNeuralNetworkEnvironment() {
		// TODO Auto-generated constructor stub
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
		/**
		if (raiseValueSig < 3f) {
			raiseMult = 2;
		}
		else if (raiseValueSig < 6f) {
			raiseMult = 3;
		}
		else if (raiseValueSig <= 10f) {
			raiseMult = 4;
		}
		**/
		//long raiseval = Math.min(Math.max(game.getCallChipCount(), 15) * raiseMult, thisp.getChipCount());
		long raiseval = (long) Math.max(game.getMinimumRaiseValue(), (raiseValueSig / 10f) * (thisp.getChipCount() + thisp.getTotalBetChipCount()) - thisp.getTotalBetChipCount());
		raiseval= Math.min(thisp.getChipCount(), raiseval);
		if (raiseval == thisp.getChipCount()) {
			return Actions.getAllInAction(raiseval, game.getCallChipCount());
		}
		else {
			return Actions.getRaiseAction(game.getCallChipCount(), raiseval);
		}
	}

	private INDArray parseNeuralInput(Game game) {
		// TODO Auto-generated method stub
		INDArray input = Nd4j.create(3);
		int index = 0;
		Player thisp = game.getPlayers().get(this.index);
		float strength = 0;
		if (game.getCardsOnBoard().size() < 3) {
			List<Card> cards = new ArrayList<>(game.getCardsOnBoard());
			cards.add(thisp.getCards().getFirstCard());
			cards.add(thisp.getCards().getSecondCard());
			strength = BlindEvaluator.evaluateCards(cards);
		}
		else {
			strength = jmpEvaluator.evaluatePlayer(game, thisp);
		}
		input.putScalar(index++, strength);
		
		float maxChips = (float)Game.STARTING_CHIP_COUNT * game.getPlayers().size();

		input.putScalar(index++, (float)(thisp.getTotalBetChipCount() + thisp.getBetChipCount()) / (float)(thisp.getChipCount() + thisp.getTotalBetChipCount()));
		input.putScalar(index++, Math.min(1, (float)(thisp.getTotalBetChipCount() + game.getCallChipCount()) / (float)(thisp.getChipCount() + thisp.getTotalBetChipCount())));
		

		/**
		input.putScalar(index++, thisp.getChipCount() / maxChips);
		input.putScalar(index++, (thisp.getBetChipCount() + thisp.getTotalBetChipCount()) / maxChips);
		input.putScalar(index++, Math.max(0, game.getCallChipCount() - thisp.getBetChipCount() )/ maxChips);
		input.putScalar(index++, game.getPotChipCount() / maxChips);
		input.putScalar(index++, Math.min(1f, (game.getMinimumRaiseValue() / maxChips) ));
		
		if (thisp.getChipCount() == 0)
		{
			input.putScalar(index++, 1f);
			input.putScalar(index++, 1f);
		}
		else
		{
			input.putScalar(index++, Math.min(1, (float) game.getMinimumRaiseValue() / thisp.getChipCount()));
			input.putScalar(index++, Math.min(1, (float) game.getCallChipCount() / thisp.getChipCount()));
		}
		**/
		return input;
		
	}

	@Override
	public void updatePlayerAction(Game game, Action curAction, int numCurrentPlayer) {
		// TODO Auto-generated method stub
		this.playedThisRound.add(game.getPlayers().get(numCurrentPlayer));
		if (numCurrentPlayer != this.index) return;
		this.tracker.informAction(net, curAction, numCurrentPlayer, game.getPlayers().get(this.index));
	}

	@Override
	public void updateBoard(List<Card> drawnCards) {
		// TODO Auto-generated method stub
		this.playedThisRound.clear();
	}

	@Override
	public void updateResults(Game game, Map<Player, Integer> strengthResults, Map<Player, Long> winnings) {
		// TODO Auto-generated method stub
		this.playedThisRound.clear();
		Player p = game.getPlayers().get(index);
		List<Integer> values = new ArrayList<>(strengthResults.values());
		Collections.sort(values);
		this.tracker.addRoundResults(net, game, index);
	}

	@Override
	public void updateEnd(Game game, int numCurrentPlayer) {
		// TODO Auto-generated method stub
		tracker.addGameResults(net, game, numCurrentPlayer);
	}

	@Override
	public void setIndex(int index) {
		// TODO Auto-generated method stub
		this.index = index;
	}

	@Override
	public NeuralEnvironment duplicate(NeuralNetwork net, FitnessTracker fitness) {
		// TODO Auto-generated method stub
		return new SimpleNeuralNetworkEnvironment(net, fitness);
	}

	
}
