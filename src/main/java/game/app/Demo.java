package game.app;

import java.util.List;

import game.Game;
import game.environment.ShellPlayerEnvironment;
import game.evaluator.jmp.JmpEvaluator;
import game.player.DefaultPlayer;
import game.player.Player;
import game.player.stub.CallPlayer;
import game.player.stub.FoldPlayer;
import game.player.stub.RaisePlayer;

public class Demo {

	public static void main(String[] args) {
		ShellPlayerEnvironment env = new ShellPlayerEnvironment();
		Player p = new DefaultPlayer("Fran", env);
		JmpEvaluator eval = new JmpEvaluator();
		Game game = new Game(List.of(new CallPlayer(), new FoldPlayer(), p, new RaisePlayer()), 
				eval);
		while(true) {
			game.setupNewRound();
			game.playRound();
		}
	}
}
