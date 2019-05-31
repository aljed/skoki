package skijumping;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

public class View extends JPanel {

	private GameModel model;
	private Controller controller;

	private SkiJumpingView game_view;

	private static final long serialVersionUID = -8398214943023894258L;
	private static final String RADIO_BUTTONS_LABEL = "Skocznie do wyboru";
	private static final String ACCIDENT_LABEL = "Skok zakoñczy³ siê wypadkiem: ";
	private static final String LENGTH_LABEL = "D³ugoœæ skoku: ";
	private static final String WIND_LABEL = "Prêdkoœæ wiatru na progu: ";
	private static final String START_BUTTON_LABEL = "Zacznij grê";
	private static final String STOP_BUTTON_LABEL = "Zmieñ skoczniê";
	private static final String RESET_BUTTON_LABEL = "Reset";

	private JPanel control_panel = new JPanel();
	private JPanel radio_button_panel = new JPanel();
	private JPanel buttons_panel = new JPanel();
	private JPanel info_panel = new JPanel();
	JLabel jump_quality = new JLabel();
	JLabel jump_length = new JLabel();
	JLabel first_wind = new JLabel();
	private JButton start_button, stop_button, reset_button;
	private JFrame frame;
	private ButtonGroup group;
	Vector<JRadioButton> hills_buttons;

	public View(JFrame p_frame) {
		frame = p_frame;
		setLayout(new BorderLayout());
		control_panel.setLayout(new BorderLayout());
		control_panel.add(radio_button_panel, BorderLayout.NORTH);
		control_panel.add(buttons_panel, BorderLayout.SOUTH);
		add(control_panel, BorderLayout.SOUTH);
		Border radio_buttons_border = BorderFactory.createEtchedBorder();
		radio_button_panel.setBorder(BorderFactory.createTitledBorder(radio_buttons_border, RADIO_BUTTONS_LABEL));
		group = new ButtonGroup();

		info_panel.add(jump_length);
		info_panel.add(first_wind);
		info_panel.add(jump_quality);

		control_panel.add(info_panel, BorderLayout.CENTER);
	}

	public void updateFailInfo(String info) {
		jump_quality.setText(ACCIDENT_LABEL + info);
		jump_quality.setVisible(true);
		jump_length.setVisible(false);
		frame.pack();
	}
	
	public void updateLengthCommunicate(double wind) {
		jump_length.setText(LENGTH_LABEL + Double.toString(wind).subSequence(0, 5));
		jump_length.setVisible(true);
		first_wind.setVisible(true);
	}
	
	public void updateWindCommunicate(double velocity) {
		first_wind.setText(WIND_LABEL + Double.toString(velocity));
		info_panel.setVisible(true);
		first_wind.setVisible(true);
		frame.pack();
	}
	
	public void goIntoGameMode() {
		turnOnGameView();
		game_view.requestFocus();
		frame.pack();
		controller.run();
	}

	public void setModel(GameModel p_model) {
		model = p_model;
		game_view = new SkiJumpingView(model, controller);
		add(game_view, BorderLayout.NORTH);
		game_view.setVisible(false);
		manageButtons();
	}

	public void setController(Controller p_controller) {
		controller = p_controller;
	}

	public void updateView() {
		game_view.updateGameView();
	}
	
	public void turnOnGameView() {
		info_panel.setVisible(false);
		reset_button.setVisible(true);
		stop_button.setVisible(true);
		start_button.setVisible(false);
		radio_button_panel.setVisible(false);
		game_view.setVisible(true);
	}
	
	public void turnOffGameView() {
		info_panel.setVisible(false);
		game_view.setVisible(false);
		reset_button.setVisible(false);
		stop_button.setVisible(false);
		start_button.setVisible(true);
		radio_button_panel.setVisible(true);
	}

	private void manageButtons() {
		hills_buttons = new Vector<>();
		Vector<JumpHill> all_hills = model.getAllHills();

		for (JumpHill current_jumphill : all_hills) {
			String name = current_jumphill.getName();
			JRadioButton current_button = new JRadioButton(name);
			hills_buttons.add(current_button);
			group.add(current_button);
			radio_button_panel.add(current_button);
			current_button.addActionListener((param) -> {
				try {
					model.setJumpingHill(current_jumphill);
				} catch (SkiJumpingException e) {
				}
				game_view.setHill(name, current_jumphill.getMaxY());
			});
		}

		start_button = new JButton(START_BUTTON_LABEL);
		stop_button = new JButton(STOP_BUTTON_LABEL);
		reset_button = new JButton(RESET_BUTTON_LABEL);

		start_button.addActionListener((param) -> {
			goIntoGameMode();
		});
		stop_button.addActionListener((param) -> {
			turnOffGameView();
			controller.stop();
			controller.reset();
			frame.pack();
		});
		reset_button.addActionListener((param) -> {
			game_view.requestFocus();
			controller.reset();
			jump_quality.setVisible(false);
			jump_length.setVisible(false);
			info_panel.setVisible(false);
		});

		buttons_panel.add(start_button);
		buttons_panel.add(reset_button);
		buttons_panel.add(stop_button);
		turnOffGameView();
		frame.pack();
	}
}
