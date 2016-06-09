package Render;

import java.util.TimerTask;
import javax.swing.JPanel;

	public class CanvasRenderer extends TimerTask {
	
	private JPanel canvas;
	
	public CanvasRenderer(JPanel canvas) {
		this.canvas = canvas;
	}

	@Override
	public void run() {
		canvas.repaint();
	}
}