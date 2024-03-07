package game.player;

import game.actions.Action;

public class DefaultPlayer implements Player{

	public static final long STARTING_CHIP_COUNT = 3000;
	private String name;
	private long chipCount;
	private Action lastAction;
	
	public DefaultPlayer() {
		this("anonymous");
	}
	
	public DefaultPlayer(String name) {
		this.name = name;
		chipCount = STARTING_CHIP_COUNT;
		this.lastAction = null;
	}
	
	@Override
	public Action getPlayerAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChipCount() {
		// TODO Auto-generated method stub
		return chipCount;
	}
	
	@Override
	public void setChipCount(long chipCount) {
		this.chipCount = chipCount;
	}
	
	@Override
	public String getPlayerName() {
		return name;
	}

	@Override
	public Action getLastAction() {
		// TODO Auto-generated method stub
		return lastAction;
	}

	@Override
	public void setLastAction(Action action) {
		// TODO Auto-generated method stub
		lastAction = action;
	}

}
