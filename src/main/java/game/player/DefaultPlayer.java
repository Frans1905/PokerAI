package game.player;

import game.actions.Action;

public class DefaultPlayer implements Player{

	public static final long STARTING_CHIP_COUNT = 3000;
	private String name;
	private long chipCount;
	
	public DefaultPlayer() {
		this("anonymous");
	}
	
	public DefaultPlayer(String name) {
		this.name = name;
		chipCount = STARTING_CHIP_COUNT;
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

}
