package neural.fitness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.Game;
import game.environment.Environment;
import game.environment.NeuralEnvironment;
import game.environment.NeuralNetworkEnvironment;
import game.evaluator.jmp.JmpEvaluator;
import game.player.DefaultPlayer;
import game.player.Player;
import neural.NeuralNetwork;

public class DefaultFitnessAlgorithm implements FitnessAlgorithm {

	FitnessTracker tracker;
	private List<NeuralNetwork> neuralnets;
	private Map<NeuralNetwork, Float> fitnesses;
	private Game game;
	private int roundLimit;
	private int numberOfMatches;
	private int numOfPlayersPerTable;
	private NeuralEnvironment netEnvironment;
	private static final int DEFAULT_ROUND_LIMIT = 100;
	private static final int DEFAULT_MATCH_NUMBER = 5;
	private static final int DEFAULT_PLAYERS_PER_TABLE = 5;
	private static final FitnessTracker DEFAULT_FITNESS_TRACKER = new ChipsAvgFitnessTracker();
	private static final NeuralEnvironment DEFAULT_NEURAL_ENV = new NeuralNetworkEnvironment();
	
	public DefaultFitnessAlgorithm(int roundLimit, int numberOfMatches, int playersPerTable, FitnessTracker tracker) {
		this.tracker = tracker;
		this.roundLimit = roundLimit;
		this.numberOfMatches = numberOfMatches;
		this.numOfPlayersPerTable = playersPerTable;
		this.fitnesses = new HashMap<NeuralNetwork, Float>();
		this.neuralnets = new ArrayList<>();
		netEnvironment = DEFAULT_NEURAL_ENV; 
		this.game = new Game(new JmpEvaluator());
	}
	
	@Override
	public void setEnvironment(NeuralEnvironment env) {
		this.netEnvironment = env;
	}
	
	public DefaultFitnessAlgorithm(FitnessTracker tracker) {
		this(DEFAULT_ROUND_LIMIT, DEFAULT_MATCH_NUMBER, DEFAULT_PLAYERS_PER_TABLE, tracker);
	}
	
	public DefaultFitnessAlgorithm() {
		this(DEFAULT_ROUND_LIMIT, DEFAULT_MATCH_NUMBER, DEFAULT_PLAYERS_PER_TABLE, DEFAULT_FITNESS_TRACKER);
	}
		
	@Override
	public void addNetwork(NeuralNetwork net) {
		// TODO Auto-generated method stub
		/*Environment netEnv = new NeuralNetworkEnvironment(net);
		Player neuralPlayer = new DefaultPlayer("neural", netEnv);
		game.addPlayer(neuralPlayer);*/
		neuralnets.add(net);
	}

	@Override
	public Map<NeuralNetwork, Float> calculateFitness() {
		// TODO Auto-generated method stub
		if (neuralnets.size() % this.numOfPlayersPerTable != 0) {
			throw new IllegalStateException("Collection of neural nets to be calculated must be a multiple of 5");
		}
		tracker.reset();
		Map<NeuralNetwork, Float> fitnessMap = new HashMap<>();
		for (int i = 0; i < numberOfMatches; i++) {
			List<List<Player>> tables = arrangeTables();
			playAllGames(tables);
		}
		this.fitnesses = tracker.getFitnesses();
		return this.fitnesses;
	}

	private void playAllGames(List<List<Player>> tables) {
		// TODO Auto-generated method stub
		for (List<Player> list : tables) {
			game.resetGame(list);
			for (int i = 0; i < roundLimit && !game.isOver(); i++) {
				game.setupNewRound();
				game.playRound();
			}
			game.endMatch();
		}
	}

	private List<List<Player>> arrangeTables() {
		// TODO Auto-generated method stub
		List<List<Player>> out = new ArrayList<>(neuralnets.size() / this.numOfPlayersPerTable);
		Set<NeuralNetwork> netsInPlay = new HashSet<>();
		
		for (int i = 0; i < neuralnets.size() / this.numOfPlayersPerTable; i++) {
			List<Player> table = new ArrayList<>(this.numOfPlayersPerTable);
			for (int j = 0; j < this.numOfPlayersPerTable; j++) {
				int index = (int) (Math.random() * neuralnets.size());
				while (netsInPlay.contains(neuralnets.get(index))) {
					index = (index + 1) % neuralnets.size();
				}
				NeuralNetwork net = neuralnets.get(index);
				netsInPlay.add(net);
				Environment env = netEnvironment.duplicate(net, tracker);
				Player p = new DefaultPlayer("neural", env);
				table.add(p);
			}
			out.add(table);
		}
		return out;
	}

	@Override
	public Map<NeuralNetwork, Float> getFitnesses() {
		// TODO Auto-generated method stub
		return this.fitnesses;
	}

	@Override
	public void addNetworks(List<NeuralNetwork> nets) {
		// TODO Auto-generated method stub
		for (NeuralNetwork net : nets) {
			addNetwork(net);
		}
	}

	@Override
	public void clearNetworks() {
		// TODO Auto-generated method stub
		this.neuralnets.clear();
	}

	@Override
	public FitnessTracker getTracker() {
		// TODO Auto-generated method stub
		return this.tracker;
	}

}
