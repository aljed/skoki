package skijumping;

import java.util.Vector;

public class GameModel {

	private Jumper jumper;
	private JumpHill current_jump_hill;
	private Vector<JumpHill> all_hills;
	private GameData game_data;
	
	public GameModel() {

		game_data = new GameData();
		jumper = game_data.getSavedJumper();
		Vector <String> jump_hills = game_data.getJumpingHillsData();
		all_hills = new Vector<>();
		
		try {
			for(int i=0; i<jump_hills.size(); i += GameData.NUMBER_OF_JUMPHILL_PARAMS) {
				JumpHill new_jumphill = new JumpHill(jump_hills.get(i), Double.parseDouble(jump_hills.get(i+8)), Double.parseDouble(jump_hills.get(i+9)), Double.parseDouble(jump_hills.get(i+10)));
				all_hills.add(new_jumphill);
				new_jumphill.setRunOnFunction(Double.parseDouble(jump_hills.get(i+1)), Double.parseDouble(jump_hills.get(i+2)), Double.parseDouble(jump_hills.get(i+3)));
				new_jumphill.setLandingFunction(Double.parseDouble(jump_hills.get(i+4)), Double.parseDouble(jump_hills.get(i+5)), Double.parseDouble(jump_hills.get(i+6)),Double.parseDouble(jump_hills.get(i+7)));
				new_jumphill.setMaxY(Double.parseDouble(jump_hills.get(i+11)));
				
			}
		}
		catch(JumpHillException e) {}
	}
	
	public void setController(Controller p_controller) {
		jumper.setController(p_controller);
	}
	
	public Vector<JumpHill> getAllHills() {
		return all_hills;
	}

	public void setJumpingHill(JumpHill jh) throws JumpHillException, JumperException {
		current_jump_hill = jh;
		jumper.setJumpingHill(current_jump_hill);
	}
	
	public void resetJumper() throws JumpHillException, JumperException {
		jumper.reset();
	}
	
	public void sitOnBench() throws JumpHillException, JumperException {
		jumper.sitOnBench();
	}

	public void startFlight() throws JumpHillException, JumperException {
		jumper.startFlight();
	}
	
	public void jump() throws JumpHillException, JumperException {
		jumper.jump();
	}
	
	public void land() throws JumpHillException, JumperException {
		jumper.land();
		game_data.save(jumper);
	}

	 public void nextState() throws JumperException {
		 jumper.nextState();
	 }
	
	public JumperLocation getJumperLocation() {
		return jumper.getLocation();
	}
	
	public void setJumperLocation(JumperLocation jl) {
		jumper.setAngles(jl);
	}
	
	public String getJumperName() {
		return jumper.getName();
	}
}
