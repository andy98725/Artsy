package apps;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;

import base.Drawable;
import base.Frame;
import util.SimplexNoise;

public class SimpleWaves implements Drawable {

	// Entry
	public static void main(String[] args) {
		// Make frame
		new Frame(new SimpleWaves());
	}

	// Height difference in lines
	private static final int LINE_HEI = 48;
	// Max line offset size
	private static final int LINE_MAX_SIZE = 64;

	// Noise space scale
	private static final double NOI_SPACE = 0.02;
	// Noise time scale
	private static final double NOI_TIME = 1;

	// Double time running
	private double time = 0;

	// Draw
	@Override
	public void draw(Graphics2D g, int i, double seconds) {
		// Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		// Increment time
		time += seconds;
		// Make circle bounds
		final double circleRad = getWidth() * 5 / 12;
		final Ellipse2D bounds = new Ellipse2D.Double(getWidth() / 2 - circleRad, getHeight() / 2 - circleRad,
				2 * circleRad, 2 * circleRad);
		g.setClip(bounds);
		// Loop over shape
		for (int y = 0; y < getHeight(); y += LINE_HEI) {
			// Polygon traits
			final int POLYGON_ARR_LEN = getWidth() + 2;
			final int[] XLOCS = new int[POLYGON_ARR_LEN], YLOCS = new int[POLYGON_ARR_LEN];
			// Add bottom 2 points
			XLOCS[POLYGON_ARR_LEN - 2] = getWidth();
			XLOCS[POLYGON_ARR_LEN - 1] = 0;
			YLOCS[POLYGON_ARR_LEN - 2] = getHeight();
			YLOCS[POLYGON_ARR_LEN - 1] = getHeight();

			// Determine each point along
			for (int x = 0; x < getWidth(); x++) {
				// Get noise
				final double noise = SimplexNoise.noise(NOI_SPACE * x, NOI_SPACE * y, NOI_TIME * time);
				// Get yloc
				final int yloc = y + (int) Math.round(LINE_MAX_SIZE * getOffset(x, y) * noise);
				// Set xlocs and ylocs
				XLOCS[x] = x;
				YLOCS[x] = yloc;
			}
			// Make new polygon
			Polygon construct = new Polygon(XLOCS, YLOCS, POLYGON_ARR_LEN);
			// Darken inside
			g.setColor(Color.BLACK);
			g.fill(construct);
			// Draw border
			g.setColor(Color.WHITE);
			g.draw(construct);
		}
		// Draw bounds
		g.setClip(null);
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(5));
		g.draw(bounds);

	}

	// Get max offset
	private double getOffset(int x, int y) {
		// Return distance from center
		double dist = Math.hypot(getWidth() / 2 - x, getHeight() / 2 - y);
		// Max dist is just radius
		final double maxDist = getWidth() / 2;
		// At max dist, it's 0. At min dist, it's 1
		return (maxDist - dist) / maxDist;
	}

	@Override
	public String getName() {
		return "Simple Waves";
	}

	@Override
	public int getWidth() {
		return 512;
	}

	@Override
	public int getHeight() {
		return 512;
	}
}
