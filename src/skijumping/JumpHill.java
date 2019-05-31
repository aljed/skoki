package skijumping;

import java.util.Random;

public class JumpHill {

	private String name;
	private double start_point;
	private double middle_point;
	private double end_point;
	private double max_y;
	private double x_bench;
	private double y_bench;
	private double c_a;
	private double wind;

	private boolean landing_function_set;
	private boolean run_on_function_set;
	
	private final int WIND_RANGE = 10;
	private final int MAX_WIND = 5;

	private double c_p;
	private double c_v;
	private double l_a, l_b, l_c, l_d;

	public JumpHill(String p_name, double p_start_point, double p_middle_point, double p_end_point)
			throws JumpHillException {
		if (p_middle_point < p_start_point || p_middle_point > p_end_point)
			throw new JumpHillException();
		if (p_start_point < 0 || p_middle_point < 0 || p_end_point < 0)
			throw new JumpHillException();
		name = p_name;
		start_point = x_bench = p_start_point;
		middle_point = p_middle_point;
		end_point = p_end_point;
		landing_function_set = false;
		run_on_function_set = false;
	}

	public void setRunOnFunction(double a, double p, double v) throws JumpHillException {
		if (a < 0)
			throw new JumpHillException();
		if (run_on_function_set == true)
			throw new JumpHillException();
		c_a = a;
		c_p = p;
		c_v = v;
		run_on_function_set = true;
		y_bench = findYDuringRunOn(start_point);
	}

	public void setLandingFunction(double a, double b, double c, double d) throws JumpHillException {
		if (l_d != 0)
			throw new JumpHillException();
		if (landing_function_set == true)
			throw new JumpHillException();
		l_a = a;
		l_b = b;
		l_c = c;
		l_d = d;
		landing_function_set = true;
	}

	public double findAngleDuringRunOn(double x) throws JumpHillException {
		if (run_on_function_set == false)
			throw new JumpHillException();
		double slope = 2 * c_a * x + 2 * c_a * c_p;
		return Math.atan(-slope);
	}

	public double findYDuringRunOn(double x) throws JumpHillException {
		if (run_on_function_set == false)
			throw new JumpHillException();
		return Math.pow(x + c_p, 2) * c_a + c_v;
	}

	public double findAngleDuringLanding(double x) throws JumpHillException {
		if (landing_function_set == false)
			throw new JumpHillException();
		double slope = 3 * l_a * x * x + 2 * l_b * x + l_c;
		return Math.atan(-slope);
	}

	public double findYDuringLanding(double x) throws JumpHillException {
		if (landing_function_set == false)
			throw new JumpHillException();
		return Math.pow(x, 3) * l_a + x * x * l_b + x * l_c + l_d;
	}

	public void setMaxY(double max_y) {
		this.max_y = max_y;
	}
	
	public double getXBenchLocation() {
		return x_bench;
	}

	public double getYBenchLocation() throws JumpHillException {
		if (run_on_function_set == false)
			throw new JumpHillException();
		return y_bench;
	}
	
	public double getMiddlePoint() {
		return middle_point;
	}
	
	public double getWind() {
		Random generator = new Random();
		wind = (generator.nextInt(WIND_RANGE) - MAX_WIND);
		return wind;
	}
	
	public String getName() {
		return name;
	}

	public double getMaxY() {
		return max_y;
	}


	public double getStartPoint() {
		return start_point;
	}

	public double getEndPoint() {
		return end_point;
	}

}
