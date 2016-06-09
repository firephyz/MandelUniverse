package Render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import javax.swing.JPanel;

public class FractalRenderer implements Runnable {
	
	// Canvas info
	private MandelPanel canvas;
	BufferedImage image;
	ImageSaver saver;
	private int WIDTH, HEIGHT;
	
	// Virtual window info
	private double zoomLevel;
	private double xPos, yPos; // Position of the window in the complex plane
	private double width, height; // Viewing dimensions of the window in the complex plane
	
	// Fractal info
	private int MAX_ITER;
	
	// Progress info
	private int currentImageNum;
	private int numberToRender;
	
	public FractalRenderer(MandelPanel drawingPanel) {
		this.canvas = drawingPanel;
		this.saver = new ImageSaver();
		drawingPanel.updateSaver(saver);
		this.image = new BufferedImage(drawingPanel.getWidth(), 
				   					   drawingPanel.getHeight(), 
				   					   BufferedImage.TYPE_INT_RGB);
		this.WIDTH = drawingPanel.getWidth();
		this.HEIGHT = drawingPanel.getHeight();
		
		this.setZoomLevel(1.0);
		this.xPos = -0.73879999557041615;
		this.yPos = 0.2102096483249081;
		
		this.MAX_ITER = 200;
	}
	
	public void setZoomLevel(double zoom) {
		this.zoomLevel = zoom;
		double correctionRatio = (double)HEIGHT / WIDTH;
		this.width = 4.0 / zoomLevel;
		this.height = 4.0 / zoomLevel * correctionRatio;
	}
	
	@Override
	public void run() {
		this.numberToRender = 500;
		canvas.setNumberToRender(numberToRender);
		renderFractal();
		canvas.setReadyToDisplay(true);
	}
	
	private void renderFractal() {
		
		Graphics2D g = image.createGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		try {
			saver.clearImages();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(currentImageNum = 0; currentImageNum < numberToRender; ++currentImageNum) {
			adjustIterLimit(currentImageNum);
			
			for(int y = 0; y < HEIGHT; ++y) {
				for (int x = 0; x < WIDTH; ++x) {
					
					// Perform calculation
					int count = iterate(x, y);
					
					// Color that pixel
					colorPixel(g, x, y, count);
				}
			}
			
			saver.saveImage(image);
			
			this.setZoomLevel(zoomLevel * 1.05);
		}
	}
	
	private void adjustIterLimit(int i) {
		
		if (i == 0) {
			this.MAX_ITER = 200;
		}
		else if (i == 130) {
			this.MAX_ITER = 350;
		}
		else if (i == 210) {
			this.MAX_ITER = 500;
		}
		else if (i == 300) {
			this.MAX_ITER = 700;
		}
		else if (i == 400) {
			this.MAX_ITER = 850;
		}
		else if (i == 460) {
			this.MAX_ITER = 1000;
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
	public double getProgress() {return (double)currentImageNum / numberToRender;}
}
