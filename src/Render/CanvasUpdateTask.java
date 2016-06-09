package Render;

import java.util.TimerTask;
import javax.swing.JPanel;

	public class CanvasUpdateTask extends TimerTask {
	
	private JPanel canvas;
	
	public CanvasUpdateTask(JPanel canvas) {
		this.canvas = canvas;
	}

	@Override
	public void run() {
		canvas.repaint();
	}
}