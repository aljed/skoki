package skijumping;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.junit.Assert;

public class Window {

	private SJFrame frame;
	private GameModel model;
	private Controller controller;
	private View view;

	private static final String WINDOW_TITLE = "SkiJumping";

	public static void main(String[] args) {
		new Window().run();
	}

	public void run() {
		EventQueue.invokeLater(() -> {

			frame = new SJFrame();

			view = new View(frame);
			model = new GameModel();
			controller = new Controller(model);

			controller.setView(view);
			view.setController(controller);
			view.setModel(model);
			model.setController(controller);

			frame.add(view);
			frame.pack();

		});
	}

	class SJFrame extends JFrame {

		private static final long serialVersionUID = 1L;

		public SJFrame() {
			setTitle(WINDOW_TITLE);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}
	}
}
