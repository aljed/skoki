package skijumping;

import javax.swing.Timer;

public class Controller {

	private GameModel model;
	private View view;
	private Thread thread;
	private Timer timer;
	
	private static final int SLEEP_TIME = 9;

	public Controller(GameModel p_model) {
		model = p_model;
	}

	public void run() {
		timer = new Timer(SLEEP_TIME, (param) -> {
			view.updateView();
		});
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public void setView(View p_view) {
		view = p_view;
	}

	public void reset() {
		try {
			model.resetJumper();
			model.sitOnBench();
		} catch (SkiJumpingException e) {
		}
	}

	public void moveUp() {
		if (model.getJumperLocation().state instanceof Fly) {
			JumperLocation jl = model.getJumperLocation();
			jl.modifySkisAngle(-0.01);
			model.setJumperLocation(jl);
			view.updateView();
		}
	}

	public void moveDown() {
		if (model.getJumperLocation().state instanceof Fly) {
			JumperLocation jl = model.getJumperLocation();
			jl.modifySkisAngle(0.01);
			model.setJumperLocation(jl);
			view.updateView();
		}
	}

	public void spacePressed() {
		try {
			model.nextState();
		} catch (JumperException e) {
		}
		if (model.getJumperLocation().state instanceof Jump) {
			start();
		} else if (model.getJumperLocation().state instanceof Fly) {
			jump();
		} else if (model.getJumperLocation().state instanceof Land) {
			land();
		}
	}

	private void start() {
		thread = new Thread(() -> {
			try {
				model.startFlight();
			} catch (SkiJumpingException e) {
			}
		});
		thread.start();
	}

	private void jump() {
		thread = new Thread(() -> {
			try {
				model.jump();
			} catch (SkiJumpingException e) {
			}
		});
		thread.start();
	}

	private void land() {
		thread = new Thread(() -> {
			try {
				model.land();
			} catch (SkiJumpingException e) {
			}
		});
		thread.start();
	}
	
	public void updateJumpLength(double length) {
		view.updateLengthCommunicate(length);
	}
	
	public void updateWind(double velocity) {
		view.updateWindCommunicate(velocity);
	}
	
	public void updateFailInfo(String info) {
		view.updateFailInfo(info);
	}
}
