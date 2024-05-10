package neural.fitness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.Game;
import game.environment.Environment;
import game.environment.NeuralNetworkEnvironment;
import game.evaluator.jmp.JmpEvaluator;
import game.player.DefaultPlayer;
import game.player.Player;
import neural.NeuralNetwork;

public class CashAvgFitnessAlgorithm implements FitnessAlgorithm {

	private List<NeuralNetwork> neuralnets;
	private Map<NeuralNetwork, Integer> fitnesses;
	private Game game;
	private int roundLimit;
	private int numberOfMatches;
	private int numOfPlayersPerTable;
	private static final int DEFAULT_ROUND_LIMIT = 50;
	private static final int DEFAULT_MATCH_NUMBER = 5;
	private static final int DEFAULT_PLAYERS_PER_TABLE = 5;

	
	public CashAvgFitnessAlgorithm(int roundLimit, int numberOfMatches, int playersPerTable) {
		this.roundLimit = roundLimit;
		this.numberOfMatches = numberOfMatches;
		this.numOfPlayersPerTable = playersPerTable;
		this.fitnesses = new HashMap<NeuralNetwork, Integer>();
		this.neuralnets = new ArrayList<>();
		this.game = new Game(new JmpEvaluator());
	}
	
	public CashAvgFitnessAlgorithm() {
		this(DEFAULT_ROUND_LIMIT, DEFAULT_MATCH_NUMBER, DEFAULT_PLAYERS_PER_TABLE);
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
	public Map<NeuralNetwork, Integer> calculateFitness() {
		// TODO Auto-generated method stub
		if (neuralnets.size() % this.numOfPlayersPerTable != 0) {
			throw new IllegalStateException("Collection of neural nets to be calculated must be a multiple of 5");
		}
		Map<NeuralNetwork, Integer> fitnessMap = new HashMap<>();
		for (int i = 0; i < numberOfMatches; i++) {
			List<List<Player>> tables = arrangeTables();
			playAllGames(tables);
		}
		return null;
	}

	private void playAllGames(List<List<Player>> tables) {
		// TODO Auto-generated method stub
		for (List<Player> list : tables) {
			game.resetGame(list);
			
			for (int i = 0; i < roundLimit; i++) {
				game.setupNewRound();
				game.playRound();
			}
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
				Environment env = new NeuralNetworkEnvironment(net);
				Player p = new DefaultPlayer("neural", env);
				table.add(p);
			}
			out.add(table);
		}
		return out;
	}

	@Override
	public Map<NeuralNetwork, Integer> getFitnesses() {
		// TODO Auto-generated method stub
		return null;
	}

}
