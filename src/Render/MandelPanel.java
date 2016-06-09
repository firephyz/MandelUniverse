package Render;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class MandelPanel extends JPanel {
	
	BufferedImage image;
	
	public MandelPanel() {
	}
	
	public void setImage(BufferedImage image) {this.image = image;}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
}
