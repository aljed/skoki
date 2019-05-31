package skijumping;

public class JumperLocation {

	public double x, y;
	public double legs_angle, thigs_angle, body_angle, skis_angle;
	public State state;

	public void modifySkisAngle(double change) {
		modifyLegsAngle(change);
		skis_angle += change;
	}

	public void modifyLegsAngle(double change) {
		modifyThigsAngle(change);
		legs_angle += change;
	}

	public void modifyThigsAngle(double change) {
		modifyBodyAngle(change);
		thigs_angle += change;
	}

	public void modifyBodyAngle(double change) {
		body_angle += change;
	}

	public void setAngles(double p_skis_angle, double p_legs_angle, double p_thigs_angle, double p_body_angle) {
		skis_angle = p_skis_angle;
		legs_angle = p_legs_angle;
		body_angle = p_body_angle;
		thigs_angle = p_thigs_angle;

	}
}
