package Render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class MandelPanel extends JPanel {
	
	private ImageSaver saver;
	private FractalRenderer renderer;
	private boolean readyToDisplay;
	
	private int numberToRender;
	private int imageIndex = 0;
	
	public MandelPanel() {
		readyToDisplay = false;
		renderer = null;
		saver = null;
		this.numberToRender = 0;
	}
	
	public void updateSaver(ImageSaver saver) {this.saver = saver;}
	public void setRenderer(FractalRenderer renderer) {this.renderer = renderer;}
	public void setReadyToDisplay(boolean value) {this.readyToDisplay = value;}
	public void setNumberToRender(int i) {this.numberToRender = i;}
	
	@Override
	public void paint(Graphics g) {
		
		boolean finishedDisplaying = false;
		
		if (readyToDisplay) {
			if (!finishedDisplaying) {
				finishedDisplaying = displayImages(g);
			}
		}
		else {
			drawLoadingScreen(g);
		}
	}
	
	public boolean displayImages(Graphics g) {
		
		BufferedImage image = null;
		
		image = saver.getImage(imageIndex);
		g.drawImage(image, 0, 0, null);
		++imageIndex;
		
		if (imageIndex == this.numberToRender) {
			return true; // True, we are done
		}
		else {
			return false; // False we are not done
		}
	}
	
	public void drawLoadingScreen(Graphics g) {
		
		int width = (int)(this.getWidth() * 0.9);
		int height = (int)(this.getWidth() * 0.05);
		
		g.setColor(Color.black);
		g.drawRect(this.getWidth() / 2 - 1 - width / 2, 
				   this.getHeight() / 2 - 1 - height / 2, 
				   width + 2, 
				   height + 2);
		
		g.fillRect(this.getWidth() / 2 - width / 2, 
				   this.getHeight() / 2 - height / 2, 
				   (int)(width * renderer.getProgress()), 
				   height + 2);
	}
}
