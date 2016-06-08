package Render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class Mandelbrot extends JFrame{
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	private boolean isFullscreen = false;
	private JPanel canvas;
	
	public Mandelbrot() {
		
		setupWindow();
		
		Timer tmr = new Timer();
		tmr.schedule(new MyTask(this.canvas), 1, 30);
	}
	
	private class MyTask extends TimerTask {
		
		private JPanel canvas;
		
		public MyTask(JPanel canvas) {
			this.canvas = canvas;
		}

		@Override
		public void run() {
			canvas.repaint();
		}
	}
	
	private void setupWindow() {
		
		// Configure main window
		this.setTitle("MandelBrot");
		this.setBackground(new Color(0, 0, 0));
		if (isFullscreen) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setUndecorated(true);
		}
		else {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			this.setSize(WIDTH, HEIGHT);
			this.setLocation(
					toolkit.getScreenSize().width / 2 - this.getWidth() / 2,
					toolkit.getScreenSize().height / 2 - this.getHeight() / 2);
		}
		
		// Configure the menu bar
		GridLayout menuLayout;
		if (this.isFullscreen) {
			menuLayout = new GridLayout(0, 20);
		}
		else {
			menuLayout = new GridLayout(0, 7);
		}
		menuLayout.setHgap(3);
		menuLayout.setVgap(3);
		JMenuBar menu = new JMenuBar();
		menu.setBackground(new Color((float)0.5, (float)0.5, (float)1));
		menu.setBorder(new EtchedBorder());
		menu.setLayout(menuLayout);
		// Configure file menu
		JMenu fileMenu = new JMenu();
		fileMenu.setBorderPainted(true);
		fileMenu.setBorder(new BevelBorder(0));
		fileMenu.setText("Menu");
		JMenuItem closeItem = new JMenuItem();
		closeItem.setText("Close");
		closeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = (JFrame)Mandelbrot.getFrames()[0];
				frame.dispose();
				System.exit(0);
			}
		});
		fileMenu.add(closeItem);
		
		menu.add(fileMenu);
		menu.setVisible(true);
		
		// Configure drawing area
		this.canvas = new DrawingCanvas();
		canvas.setOpaque(true);
		canvas.setVisible(true);
		
		// Finalize and present
		this.setJMenuBar(menu);
		this.add(canvas);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Mandelbrot();
	}
}
