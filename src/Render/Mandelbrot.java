package Render;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class Mandelbrot extends JFrame{
	
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private boolean isFullscreen = true;
	
//	@Override
//	public void actionPerformed(ActionEvent arg0) {
//		JFrame frame = (JFrame)Mandelbrot.getFrames()[0];
//		frame.dispose();
//		System.exit(0);
//	}
	
	public Mandelbrot() {
		
		setupWindow();
		System.out.println(this.getWidth() + ", " + this.getHeight());
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
		
		JMenuBar menu = new JMenuBar();
		JButton button = new JButton("Test");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = (JFrame)Mandelbrot.getFrames()[0];
				frame.dispose();
				System.exit(0);
			}
		});
		menu.add(button);
		menu.setVisible(true);
		
		// Finalize and present
		this.setJMenuBar(menu);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Mandelbrot();
	}
}
