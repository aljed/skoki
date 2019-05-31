package skijumping;

public class Fly implements State {

	@Override
	public State nextState() {
		return new Land();
	}

}
