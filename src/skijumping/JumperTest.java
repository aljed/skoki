package skijumping;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class JumperTest {

	@Test
	void givenJumperWhenCreatingWithWrongParametersItThrows() {
		try {
			new Jumper("Stoch", 60, 0);
			assert (false);
		} catch (JumperException e) {
			assert (true);
		}
		try {
			new Jumper("Stoch", 0, 1);
			assert (false);
		} catch (JumperException e) {
			assert (true);
		}
	}

	@Test
	void givenJumperWhenCreatedWihCorrectParametersThenHeHasThem() {
		try {
			Jumper jumper = new Jumper("Stoch", 60, 1);
			Assert.assertTrue(jumper.getName() == "Stoch");
			Assert.assertTrue(jumper.getWeight() == 60);
		} catch (JumperException e) {
		}
	}

	@Test
	void givenJumperWithoutJumphillWhenNextStateCalledItThrows() {
		try {
			new Jumper("Stoch", 60, 1).nextState();
			assert (false);
		} catch (JumperException e) {
			assert (true);
		}
	}

	@Test
	void givenJumperWithoutJumphillWhenSitOnBenchCalledItThrows() {
		try {
			new Jumper("Stoch", 60, 1).sitOnBench();
			assert (false);
		} catch (SkiJumpingException e) {
			assert (true);
		}
	}

	@Test
	void givenJumperSittingOnBenchWhenTryingToJumpThenItThrows() {
		try {
			new Jumper("Stoch", 60, 1).jump();
			assert (false);
		} catch (SkiJumpingException e) {
			assert (true);
		}
	}
	
	@Test
	void givenJumperInRunOnPositionWithoutJumphillWhenAskingForNextStateThenItThrows() {
		try {
			new Jumper("Stoch", 60, 1).nextState();
			assert (false);
		} catch (SkiJumpingException e) {
			assert (true);
		}
	}
	
	@Test
	void givenJumperInRunOnPositionWhenAskingForNextStateThenItJumps() {
		try {
			Jumper jumper = new Jumper("Stoch", 60, 1);
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130); 
			jh.setRunOnFunction(0,1,2);
			jh.setLandingFunction(1,2,3,5);
			jumper.setJumpingHill(jh);
			jumper.nextState();
			Assert.assertTrue(jumper.getLocation().state instanceof Jump);
		} catch (SkiJumpingException e) {
			assert (false);
		}
	}
	
	@Test
	void givenJumperInRunOnPositionWhenTryingToJumpThenItThrows() {
		try {
			Jumper jumper = new Jumper("Stoch", 60, 1);
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130); 
			jh.setRunOnFunction(0,1,2);
			jh.setLandingFunction(1,2,3,5);
			jumper.setJumpingHill(jh);
			jumper.jump();
			assert (false);
		} catch (SkiJumpingException e) {
			assert (true);
		}
	}
	
	@Test
	void givenJumperInRunOnPositionWhenTryingToLandThenItThrows() {
		try {
			Jumper jumper = new Jumper("Stoch", 60, 1);
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130); 
			jh.setRunOnFunction(0,1,2);
			jh.setLandingFunction(1,2,3,5);
			jumper.setJumpingHill(jh);
			jumper.land();
			assert (false);
		} catch (SkiJumpingException e) {
			assert (true);
		}
	}

}
