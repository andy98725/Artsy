package base;

import javax.swing.JFrame;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	// Framerate of application
	public static final int FRAMERATE = 30;

	// Make JFRame to display drawable
	public Frame(Drawable target) {
		// Set size
		setSize(target.getWidth(), target.getHeight());
		setResizable(false);
		// Set title
		setTitle(target.getName());
		// Add drawable component
		add(new DrawableComponent(target));
		pack();
		// Add reset on space
		// Exit on close
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Center
		setLocationRelativeTo(null);
		// Display
		setVisible(true);
		// Make draw loop
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					drawLoop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "Draw Loop").run();
	}

	// Loop to redraw
	private void drawLoop() throws InterruptedException {
		// Previous frame timing
		long prevFrame = System.nanoTime();
		// Main loop
		while (true) {
			// Redraw
			repaint();
			// Calculate pause time (in MS)
			long sleep = (long) (1000.0 / FRAMERATE) - (System.nanoTime() - prevFrame) / 1000000;
			if (sleep > 0) {
				// Delay thread
				Thread.sleep(sleep);
			}
		}
	}
}
