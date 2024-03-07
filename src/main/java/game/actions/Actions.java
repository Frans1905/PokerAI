package game.actions;

public class Actions {

	public static Action getCallAction(double callValue) {
		return new CallAction(callValue);
	}
	
	public static Action getRaiseAction(double callValue, double raiseValue) {
		return new RaiseAction(callValue, raiseValue);
	}
	
	public static Action getFoldAction() {
		return new FoldAction();
	}
	
	public static Action getNoneAction() {
		return new NoneAction();
	}
	
	
	private static class CallAction implements Action {

		private double callValue;
		
		public CallAction(double callValue) {
			this.callValue = callValue;
		}
		
		@Override
		public ActionType getMove() {
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
	}
	
	private static class RaiseAction implements Action {
		private double raiseValue;
		private double callValue;
		public RaiseAction(double callValue, double raiseValue) {
			this.callValue = callValue;
			this.raiseValue = raiseValue;
		}
		@Override
		public ActionType getMove() {
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
	}
	
	private static class FoldAction implements Action {

		@Override
		public ActionType getMove() {
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
		
	}
	
	private static class NoneAction implements Action {

		@Override
		public ActionType getMove() {
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
		
	}
}
