package game;

import java.util.List;

import game.environment.ShellEnvironment;
import game.evaluator.jmp.JmpEvaluator;
import game.player.DefaultPlayer;
import game.player.Player;
import game.player.stub.CallPlayer;
import game.player.stub.FoldPlayer;
import game.player.stub.RaisePlayer;

public class Demo {

	public static void main(String[] args) {
		ShellEnvironment env = new ShellEnvironment();
		Player p = new DefaultPlayer("Fran", env);
		JmpEvaluator eval = new JmpEvaluator();
		Game game = new Game(List.of(new CallPlayer(), new FoldPlayer(), p, new RaisePlayer()), 
				env,
				eval);
		while(true) {
			game.setupNewRound();
			game.playRound();
		}
	}
}
