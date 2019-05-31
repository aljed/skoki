package skijumping;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class JumperImage {

	public static final int VERTICAL_SHIFT = 100;
	
	private static final String SKIS_PATH = "gfx/narta.png";
	private static final String LEGS_PATH = "gfx/dol.png";
	private static final String THIGHS_PATH = "gfx/uda.png";
	private static final String BODY_PATH = "gfx/gora.png";
	private static final int JUMPER_SIZE = 200;
	private static final double[] SHIFT_COEFFICIENS = { 97, 0.4, 18, 21, 13, 10, 16, 17, 14};
	
	private BufferedImage skis_im, legs_im, thigh_im, body_im, whole_jumper_im;

	public JumperImage() {
		try {
			skis_im = ImageIO.read(new File(SKIS_PATH));
			legs_im = ImageIO.read(new File(LEGS_PATH));
			thigh_im = ImageIO.read(new File(THIGHS_PATH));
			body_im = ImageIO.read(new File(BODY_PATH));
		} catch (IOException e) {
		}
		whole_jumper_im = new BufferedImage(JUMPER_SIZE, JUMPER_SIZE, BufferedImage.TRANSLUCENT);
	}

	public void rotateHim(double skis_angle, double skis_legs_angle, double legs_thigs_angle, double thigs_body_angle) {

		Graphics2D g = (Graphics2D) whole_jumper_im.createGraphics();
		g.setBackground(new Color(255, 255, 255, 0)); // transparency
		g.clearRect(0, 0, JUMPER_SIZE, JUMPER_SIZE);

		AffineTransform transform = AffineTransform.getRotateInstance(skis_angle, 0, 0);
		AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		int tx = 0;
		int ty = VERTICAL_SHIFT;
		g.drawImage(skis_im, operation, tx, ty);

		double c = 0.9;

		transform = AffineTransform.getRotateInstance(skis_legs_angle, 0, 0);
		operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		tx += (int) (SHIFT_COEFFICIENS[0] * SHIFT_COEFFICIENS[1] * Math.cos(-skis_angle) + SHIFT_COEFFICIENS[2] * c * Math.sin(skis_legs_angle));
		ty += (int) (-SHIFT_COEFFICIENS[0] * SHIFT_COEFFICIENS[1] * Math.sin(-skis_angle) - SHIFT_COEFFICIENS[2] * c * Math.cos(skis_legs_angle));
		g.drawImage(legs_im, operation, tx, ty);

		transform = AffineTransform.getRotateInstance(legs_thigs_angle, 0, 0);
		operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		tx += (int) (SHIFT_COEFFICIENS[3] * c * Math.cos(-skis_legs_angle) + SHIFT_COEFFICIENS[4] * c * Math.sin(legs_thigs_angle));
		ty += (int) (-SHIFT_COEFFICIENS[2] * c * Math.sin(-skis_legs_angle) - SHIFT_COEFFICIENS[5] * c * Math.cos(legs_thigs_angle));
		g.drawImage(thigh_im, operation, tx, ty);

		transform = AffineTransform.getRotateInstance(thigs_body_angle, 0, 0);
		operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		tx += (int) (SHIFT_COEFFICIENS[6] * c * Math.cos(-legs_thigs_angle) + SHIFT_COEFFICIENS[7] * c * Math.sin(thigs_body_angle));
		ty += (int) (-SHIFT_COEFFICIENS[5] * c * Math.sin(-legs_thigs_angle) - SHIFT_COEFFICIENS[8] * c * Math.cos(thigs_body_angle));
		g.drawImage(body_im, operation, tx, ty);
	}

	public BufferedImage getWhole_jumper_im() {
		return whole_jumper_im;
	}
}
