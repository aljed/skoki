package skijumping;

public class Land implements State {
	@Override
	public State nextState() {
		return new EndOfFlight();
	}
}
