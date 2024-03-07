package game.actions;

public class Actions {

	private static final Action FOLD_ACTION = new FoldAction();
	private static final Action NONE_ACTION = new NoneAction(); 
	
	public static Action getCallAction(double callValue) {
		return new CallAction(callValue);
	}
	
	public static Action getRaiseAction(double callValue, double raiseValue) {
		return new RaiseAction(callValue, raiseValue);
	}
	
	public static Action getFoldAction() {
		return FOLD_ACTION;
	}
	
	public static Action getNoneAction() {
		return NONE_ACTION;
	}
	
	
	private static class CallAction implements Action {

		private double callValue;
		
		public CallAction(double callValue) {
			this.callValue = callValue;
		}
		
		@Override
		public ActionType getActionType() {
			// TODO Auto-generated method stub
			return ActionType.CALL;
		}

		@Override
		public double getMoveValue() {
			// TODO Auto-generated method stub
			return callValue;
		}
		
		public double getRelativeMoveValue() {
			return 1;
		}
		
		@Override
		public String toString() {
			return "Called for " + callValue + " chips";
		}
	}
	
	private static class RaiseAction implements Action {
		private double raiseValue;
		private double callValue;
		public RaiseAction(double callValue, double raiseValue) {
			this.callValue = callValue;
			this.raiseValue = raiseValue;
		}
		@Override
		public ActionType getActionType() {
			// TODO Auto-generated method stub
			return ActionType.RAISE;
		}
		@Override
		public double getMoveValue() {
			// TODO Auto-generated method stub
			return raiseValue;
		}
		@Override
		public double getRelativeMoveValue() {
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
		public double getMoveValue() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getRelativeMoveValue() {
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
		public double getMoveValue() {
			// TODO Auto-generated method stub
			throw new IllegalStateException("No value for none move");
		}

		@Override
		public double getRelativeMoveValue() {
			// TODO Auto-generated method stub
			throw new IllegalStateException("No value for none move");
		}
		
		@Override
		public String toString() {
			return "None";
		}
	}
}
