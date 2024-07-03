package game.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import game.Game;
import game.environment.Environment;
import game.environment.NeuralEnvironment;
import game.environment.NeuralNetworkEnvironment;
import game.environment.ShellPlayerEnvironment;
import game.environment.SimpleNeuralNetworkEnvironment;
import game.evaluator.jmp.JmpEvaluator;
import game.player.DefaultPlayer;
import game.player.Player;
import game.player.stub.CallPlayer;
import game.player.stub.FoldPlayer;
import game.player.stub.RaisePlayer;
import neural.PokerNeuralNetwork;
import neural.activation.ActivationFunction;
import neural.evolution.Evolution;
import neural.evolution.logger.DefaultLogger;
import neural.evolution.logger.Logger;
import neural.fitness.DefaultFitnessAlgorithm;
import neural.fitness.FitnessAlgorithm;
import neural.fitness.NullTracker;
import neural.fitness.RankFitnessTracker;
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
			
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
		
			System.out.println("/============================================\\\r\n"
				             + "|| ____         _                 _     ___ ||\r\n"
				             + "|||  _ \\  ___  | | __ ___  _ __  / \\   |_ _|||\r\n"
				             + "||| |_) |/ _ \\ | |/ // _ \\| '__|/ _ \\   | | ||\r\n"
				             + "|||  __/| (_) ||   <|  __/| |  / ___ \\  | | ||\r\n"
				             + "|||_|    \\___/ |_|\\_\\\\___||_| /_/   \\_\\|___|||\r\n"
				             + "\\============================================/\n"
				             + "By Fran Siric\n\n");
		
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
			Environment env = net.getParams().getEnvironment().duplicate(net, new NullTracker());
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
		System.out.println("Evolution settings");
		System.out.println("1 - Default parameters");
		System.out.println("2 - Custom parameters");
		String input = "";
		while (true) {
			System.out.print("Choose option: ");
			input = sc.next();
			if (input.equals("1") || input.equals("2")) {
				break;
			}
		}
		INetworkParams netparams;
		IEvolutionParams evoparams;
		if (input.equals("1")) {
			FitnessAlgorithm fitnessAlg = new DefaultFitnessAlgorithm(100, 75, 5, new RankFitnessTracker());
			MutationAlgorithm mut = new StandardDeviationMutationAlgorithm(0.2f, 0.3f, 0.3f, 0.3f);
			netparams = new NetworkParams(new SimpleNeuralNetworkEnvironment(), List.of(3, 20, 15, 10, 2), NeuralUtil.LINEAR_BOUNDED , NeuralUtil.SIGMOID, NeuralUtil.RELU);
			evoparams = new EvolutionParams(NeuralUtil.TOURNAMENT_SELECTION, 
				mut, NeuralUtil.MEAN, fitnessAlg, 
				NeuralUtil.XAVIER_INIT, 50, 200, 5);
		}
		else {
			netparams = getUserNetParams();
			evoparams = getUserEvoParams();
		}
		Evolution evo = new Evolution(evoparams, netparams);
		
		Logger deflog = new DefaultLogger();
		evo.addLogger(deflog);
		
		NetworkStrategy play = new PlayingStrategy();
		NetworkStrategy save = new SaveStrategy();
		evo.addStrategy(save);
		evo.addStrategy(play);
		
		evo.start();
	}

	private static IEvolutionParams getUserEvoParams() {
		// TODO Auto-generated method stub
		int generationSize = 0;
		String input = "";
		while (true) {
			System.out.print("Choose generation size(5-200): ");
			input = sc.next();
			try {
				generationSize = Integer.parseInt(input);
			}
			catch (Exception e) {
				
			}
			if (generationSize >= 5 && generationSize <= 200) {
				break;
			}
		}
		
		int numOfIterations = 0;
		while (true) {
			System.out.print("Choose iteration count(1-100000): ");
			input = sc.next();
			try {
				numOfIterations = Integer.parseInt(input);
			}
			catch (Exception e) {
				
			}
			if (numOfIterations >= 1 && numOfIterations <= 100000) {
				break;
			}
		}
		
		int elitism = 0;
		while (true) {
			System.out.print("Choose elitism count(0-generation size): ");
			input = sc.next();
			try {
				elitism = Integer.parseInt(input);
			}
			catch (Exception e) {
				
			}
			if (elitism >= 0 && elitism <= generationSize) {
				break;
			}
		}
		
		int roundLimit = 0;
		while (true) {
			System.out.print("Choose round limit(1-1000): ");
			input = sc.next();
			try {
				roundLimit = Integer.parseInt(input);
			}
			catch (Exception e) {
				
			}
			if (roundLimit >= 1 && roundLimit <= 1000) {
				break;
			}
		}
		
		int matchesCount = 0;
		while (true) {
			System.out.print("Choose number of matches network plays in one generation(1-1000): ");
			input = sc.next();
			try {
				matchesCount = Integer.parseInt(input);
			}
			catch (Exception e) {
				
			}
			if (matchesCount >= 1 && matchesCount <= 1000) {
				break;
			}
		}
		
		List<FitnessAlgorithm> fitnessFuncs = new ArrayList<>();
		System.out.println("Choose fitness function");
		for (int i = 0; i < NeuralUtil.FITNESS_FUNCTIONS.size(); i++) {
			System.out.println(String.format("%d - %s", i + 1, NeuralUtil.FITNESS_FUNCTIONS.get(i).toString()));
			fitnessFuncs.add(NeuralUtil.FITNESS_FUNCTIONS.get(i));
		}
		
		int option = -1;
		while(true) {
			System.out.print("Choose option: ");
			input = sc.next();
			try {
				option = Integer.parseInt(input);
			}
			catch(Exception e) {
				
			}
			if (option > 0 && option <= NeuralUtil.FITNESS_FUNCTIONS.size()) {
				break;
			}
		}
		FitnessAlgorithm fitfunc = fitnessFuncs.get(option - 1);
		
		int mutationChance = 0;
		while (true) {
			System.out.print("Choose mutation chance(1-100 %): ");
			input = sc.next();
			try {
				mutationChance = Integer.parseInt(input);
			}
			catch (Exception e) {
				
			}
			if (mutationChance >= 1 && mutationChance <= 100) {
				break;
			}
		}
		
		int bigMutationChance = 0;
		while (true) {
			System.out.print("Choose big mutation chance(1-100 %): ");
			input = sc.next();
			try {
				bigMutationChance = Integer.parseInt(input);
			}
			catch (Exception e) {
				
			}
			if (bigMutationChance >= 1 && bigMutationChance <= 100) {
				break;
			}
		}
		
		float weightStdDev = 0;
		while (true) {
			System.out.print("Choose weight mutation standard deviation(0.0-10.0): ");
			input = sc.next();
			try {
				weightStdDev = Float.parseFloat(input);
			}
			catch (Exception e) {
				
			}
			if (weightStdDev >= 0f && weightStdDev <= 10f) {
				break;
			}
		}
		
		float biasStdDev = 0;
		while (true) {
			System.out.print("Choose bias mutation standard deviation(0.0-10.0): ");
			input = sc.next();
			try {
				biasStdDev = Float.parseFloat(input);
			}
			catch (Exception e) {
		
			}
			if (biasStdDev >= 0f && biasStdDev <= 10f) {
				break;
			}
		}
		MutationAlgorithm mut = new StandardDeviationMutationAlgorithm(mutationChance, bigMutationChance, weightStdDev, biasStdDev);
		return new EvolutionParams(NeuralUtil.TOURNAMENT_SELECTION, mut, NeuralUtil.MEAN, fitfunc, NeuralUtil.XAVIER_INIT, 
				generationSize, numOfIterations, elitism);
	}

	private static INetworkParams getUserNetParams() {
		// TODO Auto-generated method stub
		System.out.println("Choose neural environment");
		System.out.println("1 - Complex environment - complex and abstract inputs");
		System.out.println("2 - Simple environment - simpler and less abstract inputs (recommended)");
		String input = "";
		while (true) {
			System.out.print("Choose option: ");
			input = sc.next();
			if (input.equals("1") || input.equals("2")) {
				break;
			}
		}
		NeuralEnvironment env = null;
		List<Integer> layers = new ArrayList<>();
		String envInput = new String(input);
		if (input.equals("1")) {
			env = new NeuralNetworkEnvironment();
			layers.add(1);
		}
		else {
			env = new SimpleNeuralNetworkEnvironment();
			layers.add(2);
		}
		
		int numOfHiddenLayers = 0;
		while(true) {
			System.out.print("Choose number of hidden layers(1-20): ");
			input = sc.next();
			try {
				numOfHiddenLayers = Integer.parseInt(input);
			}
			catch(Exception e) {
				
			}
			if (numOfHiddenLayers > 0 && numOfHiddenLayers < 20) {
				break;
			}
		}
		
		for (int i = 0; i < numOfHiddenLayers; i++) {
			int num = 0;
			while(true) {
				System.out.print(String.format("Choose size of layer %d(2-100): " , i + 1));
				input = sc.next();
				try {
					num = Integer.parseInt(input);
				}
				catch(Exception e) {
					
				}
				if (num >= 2 && num <= 100) {
					break;
				}
			}
			layers.add(num);
		}
		layers.add(2);
		
		List<ActivationFunction> activations = new ArrayList<>();
		System.out.println("Choose hidden layer activation function");
		for (int i = 0; i < NeuralUtil.ACTIVATION_FUNCTIONS.size(); i++) {
			System.out.println(String.format("%d - %s", i + 1, NeuralUtil.ACTIVATION_FUNCTIONS.get(i).toString()));
			activations.add(NeuralUtil.ACTIVATION_FUNCTIONS.get(i));
		}
		
		int option = -1;
		while(true) {
			System.out.print("Choose option: ");
			input = sc.next();
			try {
				option = Integer.parseInt(input);
			}
			catch(Exception e) {
				
			}
			if (option > 0 && option <= NeuralUtil.ACTIVATION_FUNCTIONS.size()) {
				break;
			}
		}
		ActivationFunction actfunc = activations.get(option - 1);
		
		return new NetworkParams(env, layers, NeuralUtil.LINEAR_BOUNDED, NeuralUtil.SIGMOID, actfunc);
		
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
