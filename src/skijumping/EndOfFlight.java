package skijumping;

public class EndOfFlight implements State {

	@Override
	public State nextState() {
		return new EndOfFlight();
	}

}
