package game.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import game.Game;
import game.environment.Environment;
import game.environment.NeuralNetworkPlayingEnvironment;
import game.environment.ShellPlayerEnvironment;
import game.evaluator.jmp.JmpEvaluator;
import game.player.DefaultPlayer;
import game.player.Player;
import game.player.stub.CallPlayer;
import game.player.stub.FoldPlayer;
import game.player.stub.RaisePlayer;
import neural.PokerNeuralNetwork;
import neural.evolution.Evolution;
import neural.evolution.logger.DefaultLogger;
import neural.evolution.logger.Logger;
import neural.mutation.MutationAlgorithm;
import neural.mutation.StandardDeviationMutationAlgorithm;
import neural.strategy.NetworkStrategy;
import neural.strategy.PlayingStrategy;
import neural.strategy.SaveStrategy;
import neural.util.EvolutionParams;
import neural.util.IEvolutionParams;
import neural.util.INetworkParams;
import neural.util.NetworkParams;
import neural.util.NeuralUtil;

public class PokerAI {
	
	private static Scanner sc = new Scanner(System.in);
	private static final int DEFAULT_ROUND_LIMIT = 50;
	
	public static void main(String[] args) {
		
			System.out.println("/============================================\\\r\n"
				             + "|| ____         _                 _     ___ ||\r\n"
				             + "|||  _ \\  ___  | | __ ___  _ __  / \\   |_ _|||\r\n"
				             + "||| |_) |/ _ \\ | |/ // _ \\| '__|/ _ \\   | | ||\r\n"
				             + "|||  __/| (_) ||   <|  __/| |  / ___ \\  | | ||\r\n"
				             + "|||_|    \\___/ |_|\\_\\\\___||_| /_/   \\_\\|___|||\r\n"
				             + "\\============================================/\n"
				             + "By Fran Širić\n\n");
		
		System.out.println("1 - Play demo");
		System.out.println("2 - Train network");
		System.out.println("3 - Play against network");
		System.out.println("4 - Exit\n");
		List<String> options = List.of("1","2","3","4");
		String opt = null;
		while(true) {
			System.out.print("Choose option number: ");
			opt = sc.next();
			if (options.contains(opt)) {
				break;
			}
		}
		
		switch(opt) {
		case "1":
			playDemo();
			System.exit(0);
		case "2":
			trainNetwork();
			System.exit(0);
		case "3":
			playNetwork();
			System.exit(0);
		case "4":
			System.exit(0);
		default:
			System.exit(0);
		}
	}
	
	private static void playNetwork() {
		// TODO Auto-generated method stub
		String num;
		while(true) {
			System.out.print("How many players at table(2-8): ");
			num = sc.next();
			if (num.length() == 1 || Character.isDigit(num.charAt(0)) ||
					Integer.parseInt(num) >= 2 && Integer.parseInt(num) <= 8) {
				break;
			}
		}
		
		File folder = new File("./resources/networks");
		File[] netFiles = folder.listFiles();
		
		if (netFiles.length == 0) {
			System.out.println("Sorry! There are no networks trained. Please train at least one network before playing!");
			System.exit(0);
		}
		
		List<Player> players = new ArrayList<>();
		for (int i = 0; i < Integer.parseInt(num) - 1; i++) {
			System.out.println(String.format("Choose network for player %d:", i + 1));
			for (int j = 0; j < netFiles.length; j++) {
				System.out.printf("%d - %s\n", j + 1, netFiles[j].getName().split("\\.")[0]);
			}
			int netNum = -1;
			while (true) {
				System.out.print("Choose network(number): ");
				try {
					netNum = sc.nextInt();
				}
				catch (Exception e) {
					netNum = -1;
					System.out.println("Invalid input. Input a number.");
				}
				if (netNum >= 1 && netNum <= netFiles.length) {
					break;
				}
				System.out.println("Invalid input. Choose one of the numbers on the screen");
			}
			File netFile = netFiles[netNum - 1];
			PokerNeuralNetwork net = (PokerNeuralNetwork) loadNetwork(netFile);
			Environment env = new NeuralNetworkPlayingEnvironment(net);
			Player p = new DefaultPlayer("neural", env);
			players.add(p);
		}
		System.out.println();
		
		Environment env = new ShellPlayerEnvironment();
		Player p = new DefaultPlayer("player", env);
		players.add(p);
		
		JmpEvaluator eval = new JmpEvaluator();
		
		System.out.println("Starting game...\n");
		
		Game g = new Game(players, eval);
		
		for (int i = 0; i < DEFAULT_ROUND_LIMIT && !g.isOver(); i++) {
			g.setupNewRound();
			g.playRound();
		}
		g.endMatch();
		System.exit(0);
	}

	private static Object loadNetwork(File netFile) {
		// TODO Auto-generated method stub
		Object result = null;
		try (FileInputStream fis = new FileInputStream(netFile);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			result = ois.readObject();
		}
		catch (Exception e) {
			System.out.println("Could not load network: ");
			e.printStackTrace();
			System.exit(0);
		}
		return result;
	}

	private static void trainNetwork() {
		// TODO Auto-generated method stub
		MutationAlgorithm mut = new StandardDeviationMutationAlgorithm(0.1f, 0.3f, 0.1f, 0.1f);
		INetworkParams netparams = new NetworkParams(List.of(48, 60, 40, 20, 2), NeuralUtil.LINEAR_BOUNDED , NeuralUtil.SIGMOID, NeuralUtil.RELU);
		IEvolutionParams evoparams = new EvolutionParams(NeuralUtil.TOURNAMENT_SELECTION, 
				mut, NeuralUtil.MEAN, NeuralUtil.STRENGTH_CHIP_RATIO, 
				NeuralUtil.XAVIER_INIT, 50, 200, 5);
		Evolution evo = new Evolution(evoparams, netparams);
		
		Logger deflog = new DefaultLogger();
		evo.addLogger(deflog);
		
		NetworkStrategy play = new PlayingStrategy();
		NetworkStrategy save = new SaveStrategy();
		evo.addStrategy(save);
		evo.addStrategy(play);
		
		evo.start();
	}

	public static void playDemo() {
		ShellPlayerEnvironment env = new ShellPlayerEnvironment();
		Player p = new DefaultPlayer("Player", env);
		JmpEvaluator eval = new JmpEvaluator();
		Game game = new Game(List.of(new CallPlayer(), new FoldPlayer(), p, new RaisePlayer()), 
				eval);
		for (int i = 0; i < 50 && !game.isOver(); i++) {
			game.setupNewRound();
			game.playRound();
		}
		game.endMatch();
	}
}
