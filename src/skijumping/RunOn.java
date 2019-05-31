package skijumping;

public class RunOn implements State {

	@Override
	public State nextState() {
		return new Jump();
	}
	
}
