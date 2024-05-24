package game.player;

import game.Game;
import game.actions.Action;
import game.actions.ActionType;
import game.actions.Actions;
import game.cards.CardPair;
import game.environment.Environment;

public class DefaultPlayer implements Player{

	public static final long STARTING_CHIP_COUNT = 3000;
	private String name;
	private long chipCount;
	private Action lastAction;
	private long betChipCount;
	private long totalBetChipCount;
	private int tableIndex;
	
	private CardPair cards;
	
	private Environment env;
	
	public DefaultPlayer() {
		this("anonymous", null);
	}

	
	public DefaultPlayer(String name, Environment env) {
		this.name = name;
		chipCount = STARTING_CHIP_COUNT;
		this.env = env;
		setUpPlayerForNewRound();
	}
	
	public void setUpPlayerForNewRound() {
		this.lastAction = Actions.getNoneAction();
		this.betChipCount = 0;
		this.totalBetChipCount = 0;
	}
	
	@Override
	public Action getPlayerAction(Game game) {
		// TODO Auto-generated method stub
		Action act = env.getInput(game);
		if (act.getActionType() == ActionType.CALL || 
			act.getActionType() == ActionType.RAISE) {
			setBetChipCount(act.getMoveValue());
		}
		setLastAction(act);
		return act;
		
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

	@Override
	public long takeBetChips() {
		// TODO Auto-generated method stub
		long amount = takeChips(betChipCount);
		setTotalBetChipCount(totalBetChipCount + amount);
		setBetChipCount(0);
		return amount;
	}
	
	public void setBetChipCount(long amount) {
		betChipCount = amount;
	}
	
	public void setTotalBetChipCount(long amount) {
		totalBetChipCount = amount;
	}
	
	@Override
	public long getBetChipCount() {
		return betChipCount;
	}

	@Override
	public long takeSmallBlind(long smallBlind) {
		// TODO Auto-generated method stub
		betChipCount = chipCount < smallBlind ? chipCount : smallBlind;
		return betChipCount;
	}

	@Override
	public long takeBigBlind(long smallBlind) {
		// TODO Auto-generated method stub
		betChipCount = chipCount < smallBlind * 2 ? chipCount : smallBlind * 2;
		return betChipCount;
	}


	@Override
	public void setCards(CardPair pair) {
		// TODO Auto-generated method stub
		cards = pair;
	}


	@Override
	public CardPair getCards() {
		// TODO Auto-generated method stub
		return cards;
	}


	@Override
	public Long getTotalBetChipCount() {
		// TODO Auto-generated method stub
		return totalBetChipCount;
	}


	@Override
	public Environment getEnvironment() {
		// TODO Auto-generated method stub
		return this.env;
	}


	@Override
	public void assignIndex(int index) {
		// TODO Auto-generated method stub
		this.tableIndex = index;
		this.getEnvironment().setIndex(index);
	}

}
