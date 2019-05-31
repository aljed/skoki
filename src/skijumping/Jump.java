package skijumping;

public class Jump implements State {

	@Override
	public State nextState() {
		return new Fly();
	}

}
