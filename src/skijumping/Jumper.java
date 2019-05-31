package skijumping;

import java.util.Random;

public class Jumper {

	private String name;
	private int weight;
	private JumperLocation location;
	private double x_velocity;
	private double y_velocity;
	private JumpHill jumphill;
	private double skills;
	private double latest_jump_length;
	private double current_wind_speed;
	private boolean jumphill_set;
	private Controller controller;
	
	private static final double X_ACC = -1;
	private static final double GRAVITY = 9.81;
	private static final int SLEEP_TIME = 9;
	private static final double ALLOWED_ANGlE_RANGE = 0.1;
	
	public void setController(Controller p_controller) {
		controller = p_controller;
	}

	Jumper(String p_name, int p_weight, double p_skills) throws JumperException {
		if (p_skills < 1)
			throw new JumperException();
		if (p_weight < 1)
			throw new JumperException();
		name = p_name;
		weight = p_weight;
		skills = p_skills;
		location = new JumperLocation();
		location.state = new RunOn();
	}

	Jumper(String p_name, int p_weight) throws JumperException {
		this(p_name, p_weight, 1);
	}

	public String getName() {
		return name;
	}

	public void setJumpingHill(JumpHill jh) throws JumpHillException, JumperException {
		jumphill = jh;
		jumphill_set = true;
		sitOnBench();
	}

	public void reset() throws JumpHillException, JumperException {
		location = new JumperLocation();
		x_velocity = y_velocity = 0;
		location.state = new RunOn();
		sitOnBench();
	}

	public void sitOnBench() throws JumpHillException, JumperException {
		if (!(location.state instanceof RunOn))
			throw new JumperException();
		if (!jumphill_set)
			throw new JumpHillException();
		location.x = jumphill.getXBenchLocation();
		location.y = jumphill.getYBenchLocation();
		location.setAngles(0, 0, -Math.PI / 2, 0);
		current_wind_speed = jumphill.getWind();
	}

	public void startFlight() throws JumpHillException, JumperException {

		if(controller != null)
			controller.updateWind(current_wind_speed);
		
		if (!(location.state instanceof Jump))
			throw new JumperException();
		double angle;
		while (location.state instanceof Jump) {
			if (location.x >= jumphill.getMiddlePoint()) {
				location.state = new Fly();
				jump();
				return;
			}
			angle = jumphill.findAngleDuringRunOn(location.x);
			location.modifySkisAngle(angle - location.skis_angle);
			location.x = findX(angle, SLEEP_TIME / 1000.0);
			location.y = jumphill.findYDuringRunOn(location.x);
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
			}
		}
		double skills_impact_on_yvel = (skills - new Random().nextFloat()) / skills;
		double weight_impact_on_yvel = (1000.0 - weight) / 1000.0;
		y_velocity += 10 * skills_impact_on_yvel * weight_impact_on_yvel;
	}

	private double findX(double angle, double time_range) {
		double acc_along_hill = GRAVITY * Math.sin(angle);
		double x_acc = acc_along_hill * Math.cos(angle);
		double x = location.x + x_velocity * time_range + 0.5 * x_acc * time_range * time_range;
		x_velocity += time_range * x_acc;
		return x;
	}

	private void fly(double x_acc, double y_acc, double time_range) {
		location.x += x_velocity * time_range + 0.5 * x_acc * time_range * time_range;
		x_velocity += time_range * x_acc;
		location.y += y_velocity * time_range + 0.5 * y_acc * time_range * time_range;
		y_velocity += time_range * y_acc;
		location.modifySkisAngle(new Random().nextDouble() * -0.0015 / skills *  -current_wind_speed );
		try {
			Thread.sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
		}
	}

	public void jump() throws JumpHillException, JumperException {
		if (!(location.state instanceof Fly))
			throw new JumperException();
		location.setAngles(location.skis_angle, 0, 0, 0);
		double y_acc = -(GRAVITY + current_wind_speed);
		double time_range = 0.001 * SLEEP_TIME;
		while (location.state instanceof Fly) {
			fly(X_ACC, y_acc, time_range);
			if (location.y < jumphill.findYDuringLanding(location.x)
					|| location.x < jumphill.getMiddlePoint() && location.y < jumphill.findYDuringRunOn(location.x)) {
				location.skis_angle = location.legs_angle = location.thigs_angle = location.body_angle = 1;
				nextState();
				if(controller != null)
					controller.updateFailInfo("nie przyjêto pozycji do l¹dowania");
				return;
			}
		}
	}

	public void land() throws JumpHillException, JumperException {
		if (!(location.state instanceof Land))
			throw new JumperException();

		double angle;
		double y_acc = -(GRAVITY + current_wind_speed);
		double time_range = 0.001 * SLEEP_TIME;

		//setting jumper in landing position
		location.setAngles(location.skis_angle, 0, -Math.PI / 2, -Math.PI / 2 + Math.PI / 6);

		if (location.x < jumphill.getMiddlePoint() ) {
			while(location.y > jumphill.findYDuringRunOn(location.x) && !(location.state instanceof RunOn))
				fly(X_ACC, y_acc, time_range);
			if((location.state instanceof RunOn))
				return;
			if(controller != null )
				controller.updateFailInfo("nie wolno l¹dowaæ na rozbiegu");
			location.y = jumphill.findYDuringRunOn(location.x);
			location.modifyBodyAngle(1);
			return;
		}

		while (location.y > jumphill.findYDuringLanding(location.x) && !(location.state instanceof RunOn)) {
			fly(X_ACC, y_acc, time_range);
		}

		if (jumphill.findAngleDuringLanding(location.x) + ALLOWED_ANGlE_RANGE < location.skis_angle
				|| jumphill.findAngleDuringLanding(location.x) - ALLOWED_ANGlE_RANGE > location.skis_angle) {
			location.setAngles(1, 1, 1, 1);
			if(controller != null)
				controller.updateFailInfo("nie dostosowano pozycji nart do kszta³tu skoczni");
			return;
		}

		latest_jump_length = location.x - jumphill.getMiddlePoint();
		skills += 0.1 / skills;
		if(controller != null)
			controller.updateJumpLength(latest_jump_length);

		while (!(location.state instanceof RunOn)) {
			if (location.x > jumphill.getEndPoint())
				return;
			angle = jumphill.findAngleDuringLanding(location.x);
			location.skis_angle = angle;
			location.x = findX(angle, SLEEP_TIME / 1000.0);
			location.y = jumphill.findYDuringLanding(location.x);
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
			}
		}
	}

	public void nextState() throws JumperException {
		if (!jumphill_set)
			throw new JumperException();
		location.state = location.state.nextState();
	}

	public JumperLocation getLocation() {
		JumperLocation jl = new JumperLocation();
		jl.thigs_angle = location.thigs_angle;
		jl.skis_angle = location.skis_angle;
		jl.legs_angle = location.legs_angle;
		jl.body_angle = location.body_angle;
		jl.state = location.state;
		jl.x = location.x;
		jl.y = location.y;
		return jl;
	}

	public void setAngles(JumperLocation jl) {
		location.thigs_angle = jl.thigs_angle;
		location.skis_angle = jl.skis_angle;
		location.legs_angle = jl.legs_angle;
		location.body_angle = jl.body_angle;
	}

	public int getWeight() {
		return weight;
	}

	public double getSkills() {
		return skills;
	}
}
