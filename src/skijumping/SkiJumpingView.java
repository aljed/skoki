package skijumping;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class SkiJumpingView extends JPanel {
	
	private static final long serialVersionUID = 8755619075423881551L;
	
	private int DEFAULT_HEIGHT, DEFAULT_WIDTH = 1200;
	private double max_y;
	
	private GameModel model;
	private Controller controller;
	private JumperLocation jl;
	private JumperImage jumper_view;
	
	private int big_x = DEFAULT_WIDTH, big_y = DEFAULT_HEIGHT;
	private String BACKGROUND_PATH;
	private BufferedImage background_im, whole_im, image;
		
	public SkiJumpingView(GameModel p_model, Controller p_controller) {

		model = p_model;
		controller = p_controller;
		
		jumper_view = new JumperImage();

		Action space = new KeyPressed( ()-> { controller.spacePressed(); });
		Action arrow_up = new KeyPressed( ()->  { 
			controller.moveUp();
		});
		Action arrow_down = new KeyPressed( ()->  {
			controller.moveDown();
		});
		InputMap imap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(KeyStroke.getKeyStroke("SPACE"), "space");
		imap.put(KeyStroke.getKeyStroke("UP"), "up");
		imap.put(KeyStroke.getKeyStroke("DOWN"), "down");
		ActionMap amap = getActionMap();
		amap.put("space", space);
		amap.put("up", arrow_up);
		amap.put("down", arrow_down);
		addMouseWheelListener(new Scroll());
	
	}
	
	public void setHill(String name, double p_max_y) {
		BACKGROUND_PATH = "gfx/"+name+".png";
		max_y = p_max_y;
		try {
			image = ImageIO.read(new File(BACKGROUND_PATH));
		}
		catch(IOException e) {}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		DEFAULT_WIDTH = (int) screenSize.getWidth();
		DEFAULT_HEIGHT = image.getHeight() * DEFAULT_WIDTH / image.getWidth();
		if(DEFAULT_HEIGHT + 100 > screenSize.getHeight()) {
			DEFAULT_HEIGHT = (int) screenSize.getHeight() - 100;
			DEFAULT_WIDTH = image.getWidth() * DEFAULT_HEIGHT / image.getHeight();
		}
		big_y = DEFAULT_HEIGHT;
		big_x = DEFAULT_WIDTH;
		scale(0);
	}
	
	public void scale(int coefficient) {
		big_y += coefficient * 100;
		big_x += coefficient * 100 * DEFAULT_WIDTH / DEFAULT_HEIGHT;
		if(big_x < DEFAULT_WIDTH) big_x = DEFAULT_WIDTH;
		if(big_y < DEFAULT_HEIGHT) big_y = DEFAULT_HEIGHT;
		background_im = new BufferedImage(big_x, big_y, BufferedImage.TYPE_3BYTE_BGR);
		background_im.createGraphics().drawImage(image.getScaledInstance(big_x, big_y, 	Image.SCALE_SMOOTH),0,0,null);
		whole_im = new BufferedImage(big_x, big_y, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		jl = model.getJumperLocation();
		Graphics2D g2d = (Graphics2D) g.create();
		BufferedImage whole_jumper_im = jumper_view.getWhole_jumper_im();
 		int x = (int) (jl.x * big_y / max_y);
		int y = (int) (-jl.y * big_y / max_y + big_y - JumperImage.VERTICAL_SHIFT);
		whole_im.createGraphics().drawImage(background_im, 0, 0, null);
		whole_im.createGraphics().drawImage(whole_jumper_im, x, y, null);
		if(x - DEFAULT_WIDTH / 3 > big_x - DEFAULT_WIDTH) x = big_x - 2 * DEFAULT_WIDTH / 3 - 1;
		if(y - DEFAULT_HEIGHT / 3 > big_y - DEFAULT_HEIGHT) y = big_y - 2 * DEFAULT_HEIGHT / 3 - 1;
		if(x - DEFAULT_WIDTH / 3 < 0) x = DEFAULT_WIDTH / 3;
		if ( y - DEFAULT_HEIGHT / 3 < 0) y = DEFAULT_HEIGHT / 3;	
		g2d.drawImage(whole_im.getSubimage(x - DEFAULT_WIDTH / 3, y - DEFAULT_HEIGHT / 3, DEFAULT_WIDTH, DEFAULT_HEIGHT), 0, 0, null);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void updateGameView() {
		jl = model.getJumperLocation();
		jumper_view.rotateHim(jl.skis_angle, jl.legs_angle, jl.thigs_angle, jl.body_angle);
		repaint();
	}
	
	private class KeyPressed extends AbstractAction{

		private static final long serialVersionUID = 1L;
		Runnable to_perform;
		
		public KeyPressed(Runnable to_perform_) {
			to_perform = to_perform_;
		}
		
		public void actionPerformed(ActionEvent event) {
			to_perform.run();
		}
	}
	
	private class Scroll implements MouseWheelListener{

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			scale(e.getWheelRotation());
		}
		
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}
