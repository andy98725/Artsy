package base;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class DrawableComponent extends JComponent {
	private static final long serialVersionUID = 1L;

	// Target to draw
	private final Drawable target;
	// Frame count
	private int frameCount = 0;
	// Delta time
	private long previousDrawTime = System.nanoTime();

	// Target drawable
	public DrawableComponent(Drawable target) {
		// Set
		this.target = target;
		// Set size
		setPreferredSize(new Dimension(target.getWidth(), target.getHeight()));
	}

	// Paint component
	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		// Use anti aliasing
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Get new delta
		final long newTime = System.nanoTime();
		// Draw
		target.draw(g, frameCount++, toSeconds(newTime - previousDrawTime));
		// Update
		previousDrawTime = newTime;
	}

	// Convert from nanoseconds to seconds
	private double toSeconds(long deltaTime) {
		return deltaTime / 1000000000.0;
	}
}
