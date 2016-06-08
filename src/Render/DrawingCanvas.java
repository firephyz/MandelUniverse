package Render;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawingCanvas extends JPanel {
	
	public DrawingCanvas() {
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(new Color((int)(Math.random() * 256), 
							 (int)(Math.random() * 256), 
							 (int)(Math.random() * 256)));
		Triangle.fillTriangle(g, Triangle.randomPoint(this.getWidth(), this.getHeight()), 
								 Triangle.randomPoint(this.getWidth(), this.getHeight()), 
								 Triangle.randomPoint(this.getWidth(), this.getHeight()));
	}

}
