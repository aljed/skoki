package skijumping;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class JumpHillTest {

	@Test
	void givenJumphillWhenIncorrectPointsThenItThrows() {
		try{
			new JumpHill("Zakopane", -1, 50, 130);
			new JumpHill("Zakopane", 60, 50, 130);
			new JumpHill("Zakopane", 0, 130, 50);
			assert(false);
		}
		catch (JumpHillException e) {
			assert(true);
		}
		
	}
	
	@Test
	void givenJumphillWithRunOnSetWhenFindingYCoordinateThenItIsCorrect() {
		try{
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130); 
			jh.setRunOnFunction(0, 1, 2);
			Assert.assertTrue(jh.findYDuringRunOn(0) == 2);
			jh.setRunOnFunction(0, 1, 3);
		}
		catch (JumpHillException e) {	}
	}
	
	@Test
	void givenJumphillWhenSettingWrongRunOnFunctionThenItThrows() {
		try{
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130); 
			jh.setRunOnFunction(-1,0,0);
			assert(false);
		}
		catch (JumpHillException e) {
			assert(true);
		}
	}
	
	@Test
	void givenJumphillWhenTryingToResetRunOnFunctionThenItThrows() {
		try {
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130); 
			jh.setRunOnFunction(0,1,2);
			jh.setRunOnFunction(1,2,3);
			assert(false);
		}
		catch (JumpHillException e) {
			assert(true);
		}
	}
	
	@Test
	void givenJumphillWhenTryingLandingFunctionThenItThrows() {
		try {
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130);
			jh.setLandingFunction(0,1,2,4);
			jh.setLandingFunction(1,2,3,5);
			assert(false);
		}
		catch (JumpHillException e) {
			assert(true);
		}
	}

	@Test
	void givenJumphillWhenGettingMiddlePointThenItIsCorrect() {
		try {
			double middle_point = 50;
			JumpHill jh = new JumpHill("Zakopane", 0, middle_point, 130);
			Assert.assertTrue( jh.getMiddlePoint() == middle_point);
		}
		catch (JumpHillException e) {}
	}
	
	@Test
	void givenJumphillWithConstantRunOnFunctionWhenGettingRunOnAngleThenItIsIsZero() {
		try {
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130);
			jh.setRunOnFunction(0, 1, 2);
			Assert.assertTrue( jh.findAngleDuringRunOn(5) == 0 ); 
		}
		catch (JumpHillException e) {}
	} 
	
	@Test
	void givenJumphillWithConstantLandingFunctionWhenGettingRunOnAngleThenItIsIsZero() {
		try {
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130);
			jh.setLandingFunction(0, 0, 0, 5);
			Assert.assertTrue( jh.findAngleDuringLanding(5) == 0 ); 
		}
		catch (JumpHillException e) {}
	} 
	
	@Test
	void givenJumphillWithRunOnFunctionWhenGettingYPositionThenItIsCorrect() {
		try {
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130);
			jh.setRunOnFunction(1, 2, 3);
			int i = 0;
			if ( ! (jh.findYDuringRunOn(i) == Math.pow(i-2, 2) + 3) )
				assert(false);
		}
		catch (JumpHillException e) {}
		assert(true);
	} 
	
	@Test
	void givenJumphillWithLandingFunctionWhenGettingYPositionThenItIsCorrect() {
		try {
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130);
			jh.setLandingFunction(0, 0, 0, 5);
			Assert.assertTrue( jh.findYDuringLanding(5) == 5 ); 
		}
		catch (JumpHillException e) {}
	} 
	
	@Test
	void givenJumphillWhenGettingWindThenItIsInCorrectRange() {
		try {
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130);
			double wind = jh.getWind();
			Assert.assertTrue(wind <= 5  && wind >= -5 ); 
		}
		catch (JumpHillException e) {}
	}
	
	@Test
	void givenJumphillWhenSettingMaxYThenItIsCorrect() {
		try {
			double max_y = 10;
			JumpHill jh = new JumpHill("Zakopane", 0, 50, 130);
			jh.setMaxY(10);
			Assert.assertTrue(max_y == jh.getMaxY()); 
		}
		catch (JumpHillException e) {}
	}
	
	@Test
	void givenJumphillWhenGettingXBenchThenItEqualsStartPoint() {
		try {
			double start_point = 10;
			JumpHill jh = new JumpHill("Zakopane", start_point, 50, 130);
			Assert.assertTrue(jh.getXBenchLocation() == start_point); 
		}
		catch (JumpHillException e) {}
	}
	
	@Test
	void givenJumphillWhenGettingYBenchThenItEqualsYValueInStartPoint() {
		try {
			double start_point = 10;
			JumpHill jh = new JumpHill("Zakopane", start_point, 50, 130);
			jh.setRunOnFunction(0, 1, 2);
			Assert.assertTrue(jh.getYBenchLocation() == 2); 
		}
		catch (JumpHillException e) {}
	}
	
}


