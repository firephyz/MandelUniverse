package Render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.JPanel;

public class FractalRenderer implements Runnable {
	
	// Canvas info
	private JPanel canvas;
	BufferedImage image;
	private int WIDTH, HEIGHT;
	
	// Virtual window info
	private double zoomLevel;
	private double xPos, yPos; // Position of the window in the complex plane
	private double width, height;
	
	// Fractal info
	private int MAX_ITER;
	
	public FractalRenderer(JPanel canvas) {
		this.canvas = canvas;
		this.image = new BufferedImage(canvas.getWidth(), 
				   					   canvas.getHeight(), 
				   					   BufferedImage.TYPE_INT_RGB);
		this.WIDTH = canvas.getWidth();
		this.HEIGHT = canvas.getHeight();
		
		this.zoomLevel = 2;
		double correctionRatio = (double)HEIGHT / WIDTH;
		this.width = 4.0 / zoomLevel;
		this.height = 4.0 / zoomLevel * correctionRatio;
		this.xPos = -1.0;
		this.yPos = 0.0;
		
		this.MAX_ITER = 200;
	}
	
	@Override
	public void run() {
		
		Graphics2D g = image.createGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for(int y = 0; y < HEIGHT; ++y) {
			for (int x = 0; x < WIDTH; ++x) {
				
				// Perform calculation
				int count = iterate(x, y);
				
				// Color that pixel
				colorPixel(g, x, y, count);
			}
		}
	}
	
	private int iterate(int x, int y) {
		
		double movingX = 0;
		double movingY = 0;
		int count = 0;
		
		for(count = 0; count < MAX_ITER; ++count) {
			double newX = calculatePoint(movingX, 
									 movingY, 
									 getPixelPos(x, GET_X), 
									 getPixelPos(y, GET_Y),
									 GET_X);
			movingY = calculatePoint(movingX, 
					  				 movingY, 
					  				 getPixelPos(x, GET_X), 
					  				 getPixelPos(y, GET_Y),
					  				 GET_Y);
			movingX = newX;
			
			// If it escapes, break
			if (distance(movingX, movingY) >= 2) {
				break;
			}
		}
		
		return count;
	}
	
	private void colorPixel(Graphics2D g, int x, int y, int count) {
		
		float colorRatio = (float)count / MAX_ITER;
		Color color = new Color(colorRatio, colorRatio, colorRatio);
		
		if (count == MAX_ITER) {
			g.setColor(color);
		}
		else {
			g.setColor(color);
		}
		
		g.drawLine(x, y, x, y);
	}
	
	private double distance(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}
	
	private double calculatePoint(double movingX, 
						   double movingY, 
						   double x, 
						   double y, 
						   int mode) {
		if (mode == GET_X) {
			return movingX * movingX - movingY * movingY + x;
		}
		else if (mode == GET_Y) {
			return 2 * movingX * movingY + y;
		}
		
		// Unreachable case if the function is called correctly
		return 0.0;
	}
	
	private int getLineLength(double length) {
		return (int)(length / width * WIDTH);
	}
	
	private final int GET_X = 0;
	private final int GET_Y = 1;
	private int getPointPos(double pos, int mode) {
		
		double center = xPos;
		int multiplier = WIDTH;
		if (mode == GET_Y) {
			center = yPos;
			multiplier = HEIGHT;
		}
		
		double translatedPos = pos - center;
		
		return (int)((translatedPos / width * multiplier) + multiplier / 2);	
	}
	
	public double getPixelPos(int pixel, int mode) {
		double center = xPos;
		double multiplier = WIDTH / width;
		double translation = width / 2;
		if (mode == GET_Y) {
			center = yPos;
			multiplier = -HEIGHT / height;
			translation = -height / 2;
		}
		
		return pixel / multiplier + center - translation;
	}
	
	public BufferedImage getImage() {return this.image;}
}
