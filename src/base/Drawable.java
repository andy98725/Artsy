package base;

import java.awt.Graphics2D;

public interface Drawable {

	// Get name
	public String getName();
	// Draw frame
	public void draw(Graphics2D g, int i, double seconds);

	// Get width
	public int getWidth();

	// Get height
	public int getHeight();
}
