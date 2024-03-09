package game.actions;

public class Actions {

	private static final Action FOLD_ACTION = new FoldAction();
	private static final Action NONE_ACTION = new NoneAction(); 
	
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
		
		public long getRelativeMoveValue() {
			return 1;
		}
		
		@Override
		public String toString() {
			return "Called for " + callValue + " chips";
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
		public long getRelativeMoveValue() {
			// TODO Auto-generated method stub
			return raiseValue / callValue;
		}
		
		@Override
		public String toString() {
			return "Raised to " + raiseValue + " chips";
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
		public long getRelativeMoveValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public String toString() {
			return "Folded";
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
		public long getRelativeMoveValue() {
			// TODO Auto-generated method stub
			throw new IllegalStateException("No value for none move");
		}
		
		@Override
		public String toString() {
			return "None";
		}
	}
}
