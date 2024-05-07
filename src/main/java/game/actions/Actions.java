package game.actions;

import game.player.Player;

public class Actions {

	private static final Action FOLD_ACTION = new FoldAction();
	private static final Action NONE_ACTION = new NoneAction(); 
	private static final Action CHECK_ACTION = new CheckAction();
	
	public static Action getCallAction(long callValue) {
		return new CallAction(callValue);
	}
	
	public static Action getRaiseAction(long callValue, long raiseValue) {
		return new RaiseAction(callValue, raiseValue);
	}
	
	public static Action getFoldAction() {
		return FOLD_ACTION;
	}
	
	public static Action getNoneAction() {
		return NONE_ACTION;
	}
	
	public static Action getCheckAction() {
		return CHECK_ACTION;
	}
	
	public static Action getAllInAction(long allInValue, long callValue) {
		return new AllInAction(allInValue, callValue);
	}
	
	
	private static class CallAction implements Action {

		private long callValue;
		
		public CallAction(long callValue) {
			this.callValue = callValue;
		}
		
		@Override
		public ActionType getActionType() {
			// TODO Auto-generated method stub
			return ActionType.CALL;
		}

		@Override
		public long getMoveValue() {
			// TODO Auto-generated method stub
			return callValue;
		}
		
		@Override
		public float getRelativeMoveValue() {
			return 1;
		}
		
		@Override
		public String toString() {
			return "Called for " + callValue + " chips";
		}

		@Override
		public boolean isValid(Player curPlayer) {
			// TODO Auto-generated method stub
			return true;
		}
	}
	
	private static class RaiseAction implements Action {
		private long raiseValue;
		private long callValue;
		public RaiseAction(long callValue, long raiseValue) {
			this.callValue = callValue;
			this.raiseValue = raiseValue;
		}
		@Override
		public ActionType getActionType() {
			// TODO Auto-generated method stub
			return ActionType.RAISE;
		}
		@Override
		public long getMoveValue() {
			// TODO Auto-generated method stub
			return raiseValue;
		}
		@Override
		public float getRelativeMoveValue() {
			// TODO Auto-generated method stub
			return (float)raiseValue / callValue;
		}
		
		@Override
		public String toString() {
			return "Raised to " + raiseValue + " chips";
		}
		@Override
		public boolean isValid(Player curPlayer) {
			// TODO Auto-generated method stub
			if (this.getMoveValue() > curPlayer.getChipCount() ||
					this.getRelativeMoveValue() < 2) {
				return false;
			}
			return true;
		}
	}
	
	private static class FoldAction implements Action {

		@Override
		public ActionType getActionType() {
			// TODO Auto-generated method stub
			return ActionType.FOLD;
		}

		@Override
		public long getMoveValue() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float getRelativeMoveValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public String toString() {
			return "Folded";
		}

		@Override
		public boolean isValid(Player curPlayer) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	
	private static class NoneAction implements Action {

		@Override
		public ActionType getActionType() {
			// TODO Auto-generated method stub
			return ActionType.NONE;
		}

		@Override
		public long getMoveValue() {
			// TODO Auto-generated method stub
			throw new IllegalStateException("No value for none move");
		}

		@Override
		public float getRelativeMoveValue() {
			// TODO Auto-generated method stub
			throw new IllegalStateException("No value for none move");
		}
		
		@Override
		public String toString() {
			return "None";
		}

		@Override
		public boolean isValid(Player curPlayer) {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
	private static class CheckAction implements Action {
		
		
		@Override
		public ActionType getActionType() {
			return ActionType.CHECK;
		}

		@Override
		public long getMoveValue() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float getRelativeMoveValue() {
			// TODO Auto-generated method stub
			throw new IllegalStateException("Not possible to relativize check value");
		}
		
		@Override
		public String toString() {
			return "Checked";
		}

		@Override
		public boolean isValid(Player curPlayer) {
			// TODO Auto-generated method stub
			return true;
		}
	}
	
	private static class AllInAction implements Action {
		
		long allInValue;
		long callValue;
		
		public AllInAction(long allInValue, long callValue) {
			this.allInValue = allInValue;
			this.callValue = callValue;
		}
		
		@Override
		public ActionType getActionType() {
			return ActionType.ALLIN;
		}

		@Override
		public long getMoveValue() {
			// TODO Auto-generated method stub
			return allInValue;
		}

		@Override
		public float getRelativeMoveValue() {
			// TODO Auto-generated method stub
			return (allInValue / (float)callValue);
		}
		
		@Override
		public String toString() {
			return "All in for " + getMoveValue() + " chips";
		}

		@Override
		public boolean isValid(Player curPlayer) {
			// TODO Auto-generated method stub
			return true;
		}
	}
	
}
