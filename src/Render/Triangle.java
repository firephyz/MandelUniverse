package Render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Triangle {
	
	public static void fillTriangle(Graphics g, Point p1, Point p2, Point p3) {
		
		double endX, endY;
		endX = p2.x;
		endY = p2.y;
		
		int move;
		double slope = (double)(p3.y - p2.y) / (p3.x - p2.x);
		
		// Find out if we should increment the y endpoint or the x endpoint
		boolean incX = true;
		if (Math.abs(p3.y - p2.y) > Math.abs(p3.x - p2.x)) {
			incX = false;
		}
		
		// Draw lines to fill the triangle
		while((int)endX != p3.x && (int)endY != p3.y) {
			
			if (incX) {
				// Determine direction of movement
				if (p3.x > p2.x) {
					move = 1;
				}
				else {
					move = -1;
				}
				
				//Draw line
				g.drawLine(p1.x, p1.y, (int)endX, (int)endY);
				
				// Advance end point
				int oldEndX = (int)endX;
				endX += move;
				endY += slope * move;
				
				// Draw extra line if needed
				if (oldEndX != (int)endX) {
					g.drawLine(p1.x, p1.y, oldEndX, (int)endY);
				}
			}
			else {
				// Determine direction of movement
				if (p3.y > p2.y) {
					move = 1;
				}
				else {
					move = -1;
				}

				//Draw line
				g.drawLine(p1.x, p1.y, (int)endX, (int)endY);
				
				// Advance end point
				int oldEndY = (int)endY;
				endY += move;
				endX += slope * move;
				
				// Draw extra line if needed
				if (oldEndY != (int)endY) {
					g.drawLine(p1.x, p1.y, (int)endX, oldEndY);
				}
			}
		}
	}
	
	public static Point randomPoint(int width, int height) {
		int x = (int)(Math.random() * width);
		int y = (int)(Math.random() * height);
		return new Point(x, y);
	}
}
