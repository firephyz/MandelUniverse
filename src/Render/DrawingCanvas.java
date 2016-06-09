package Render;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DrawingCanvas extends JPanel {
	
	Mandelbrot mandel;
	BufferedImage image;
	
	public DrawingCanvas(Mandelbrot mandel) {
		this.mandel = mandel;
	}
	
	public void setImage(BufferedImage image) {this.image = image;}
	
	public void beginDraw() {
		mandel.getMaker().render();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
}
