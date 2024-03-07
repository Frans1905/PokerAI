package game.environment;

import game.actions.Action;

public class ShellEnvironment implements Environment {
	
	
	@Override
	public Action getInput() {
		// TODO Auto-generated method stub
		System.out.println("Drawn cards: ");
		return null;
	}

}
