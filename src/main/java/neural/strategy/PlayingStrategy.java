package neural.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import game.Game;
import game.environment.Environment;
import game.environment.ShellPlayerEnvironment;
import game.evaluator.Evaluator;
import game.evaluator.jmp.JmpEvaluator;
import game.player.DefaultPlayer;
import game.player.Player;
import neural.NeuralNetwork;
import neural.fitness.NullTracker;

public class PlayingStrategy implements NetworkStrategy {

	private Scanner sc;
	private int tableSize;
	private static final int DEFAULT_TABLE_SIZE = 5;
	
	public PlayingStrategy(int tableSize) {
		this.tableSize = tableSize;
		sc = new Scanner(System.in);
	}
	
	public PlayingStrategy() {
		this(DEFAULT_TABLE_SIZE);
	}
	
	@Override
	public void accept(NeuralNetwork net) {
		// TODO Auto-generated method stub
		String answer = null;
		while(true) {
			System.out.print("Do you want to play versus the network(yes/no): ");
			answer = sc.next();
			if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")) {
				break;
			}
		}
		if (answer.equalsIgnoreCase("no")) {
			return;
		}
		List<Player> players = new ArrayList<>();
		for (int i = 0; i < tableSize - 1; i++) {
			Environment env = net.getParams().getEnvironment().duplicate(net, new NullTracker()); 
			Player p = new DefaultPlayer("neural", env);
			players.add(p);
		}
		Environment env = new ShellPlayerEnvironment();
		Player p = new DefaultPlayer("player", env);
		players.add(p);
		Evaluator eval = new JmpEvaluator();
		Game game = new Game(players, eval);
		for (int i = 0; i < 50 && !game.isOver(); i++) {
			game.setupNewRound();
			game.playRound();
		}
		game.endMatch();
	}
	
}
