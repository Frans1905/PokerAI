package neural.fitness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import game.Game;
import game.actions.Action;
import game.actions.ActionType;
import game.evaluator.Evaluator;
import game.player.Player;
import neural.NeuralNetwork;

public class MoveEvalFitnessTracker implements FitnessTracker {

	private Map<NeuralNetwork, List<Action>> actions;
	private Map<NeuralNetwork, Float> points;
	
	public MoveEvalFitnessTracker() {
		reset();
	}
	
	@Override
	public void addPoints(NeuralNetwork net, float points) {
		this.points.put(net, this.points.getOrDefault(net, 0f) + points);
	}

	@Override
	public void addGameResults(NeuralNetwork net, Game game, int index) {
		// TODO Auto-generated method stub
		if (game.getPlayers().get(index).getChipCount() == 0) {
			subtractPoints(net, 500);
		}
	}

	@Override
	public void subtractPoints(NeuralNetwork net, float points) {
		// TODO Auto-generated method stub
		addPoints(net, -points);
	}

	@Override
	public Map<NeuralNetwork, Float> getFitnesses() {
		// TODO Auto-generated method stub
		return points;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		this.actions = new HashMap<>();
		this.points = new HashMap<>();
	}

	@Override
	public void informAction(NeuralNetwork net, Action action, int playerIndex, Player player) {
		// TODO Auto-generated method stub
		points.putIfAbsent(net, 0f);
		actions.putIfAbsent(net, new LinkedList<>());
		if (player.getIndex() == playerIndex) {
			actions.get(net).add(action);
		}
	}

	@Override
	public void addRoundResults(NeuralNetwork net, Game game, int index) {
		// TODO Auto-generated method stub
		if (game.getCardsOnBoard().size() != 5) {
			if (game.getActivePlayers().contains(game.getPlayers().get(index))) {
				addPoints(net, 80);
			}
			this.actions.get(net).clear();
			return;
		}
		int raise = 0;
		int allin = 0;
		int fold = 0;
		Player p = game.getPlayers().get(index);
		for (Action a : this.actions.get(net)) {
			if (a.getActionType() == ActionType.ALLIN) {
				allin = 1;
			}
			else if (a.getActionType() == ActionType.RAISE) {
				raise = 1;
			}
			else if (a.getActionType() == ActionType.FOLD) {
				fold = 1;
			}
		}
		if (allin == 0 && raise == 0 && fold == 0) {
			
		}
		Evaluator ev = game.getEvaluator();
		List<Integer> strengths = new ArrayList<>();
		int min = Integer.MAX_VALUE;
		Player maxPlayer = null;
		int playerStrength = 0;

		for (Player player : game.getPlayers()) {
			if (!game.getActivePlayers().contains(player)) {
				strengths.add(Integer.MAX_VALUE);
				continue;
			}
			int strength = ev.findStrongestCombination(player.getCards(), game.getCardsOnBoard());
			strengths.add(strength);
			if (player == p) {
				playerStrength = strength;
			}
			if (strength < min) {
				min = strength;
				maxPlayer = player;
			}
		}
		
		
		if (allin == 1) {
			if (min == playerStrength){
				addPoints(net, 10f * (1 - playerStrength / 7462f) * p.getTotalBetChipCount());
			}
			else {
				subtractPoints(net, p.getTotalBetChipCount() * 10f * playerStrength / 7462f);
			} 	
		}
		else if (min != playerStrength) {
			addPoints(net, 20f * playerStrength / 7462f);
		}
		else {
			subtractPoints(net, 20f * (1 - (playerStrength / 7462f)));
		}
		if (raise == 1) {
			if (!game.getActivePlayers().contains(p)) {
				subtractPoints(net, p.getTotalBetChipCount() * 4f * playerStrength / 7462f);
			}
			else if (min == playerStrength) {
				addPoints(net, p.getTotalBetChipCount() * 4f * (1 - playerStrength / 7462f));
			}
			else {
				subtractPoints(net, p.getTotalBetChipCount() * 4f * playerStrength / 7462f);
			}
		}
		else if (min != playerStrength) {
			addPoints(net, 20f * playerStrength / 7462f);
		}
		else {
			subtractPoints(net, 20f * (1 - (playerStrength / 7462f)));
		}
		if (fold == 1) {
			float betChips = game.getPotChipCount() / game.getActivePlayers().size();
			if (min < playerStrength) {
				addPoints(net, betChips * 6f * playerStrength / 7462f);
			}
			else {
				subtractPoints(net, betChips * 6f * (1 - (playerStrength / 7462f)));
			}
		}
		else if (min != playerStrength) {
			subtractPoints(net, 20f * playerStrength / 7462f);
		}
		else {
			addPoints(net, 20f * (1 -(playerStrength / 7462f)));
		}
		this.actions.get(net).clear();
	}

	@Override
	public FitnessTracker duplicate() {
		// TODO Auto-generated method stub
		return new MoveEvalFitnessTracker();
	}

}
