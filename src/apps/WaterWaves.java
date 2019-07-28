package apps;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import base.Drawable;
import base.Frame;
import util.SimplexNoise;

public class WaterWaves implements Drawable {

	// Entry
	public static void main(String[] args) {
		// Make frame
		new Frame(new WaterWaves());
	}

	// Height difference in lines
	private static final int LINE_HEI = 12;
	// Max line offset size
	private static final int LINE_MAX_SIZE = 16;

	// Noise space scale
	private static final double NOI_SPACE = 0.03;
	// Noise time scale
	private static final double NOI_TIME = 1;

	// Double time running
	private double time = 0;
	// Each layer's color
	private final Color[] layerColors;

	// Basic random
	private final Random rand = new Random();
	// Background Color
	private static final Color BGCOL = new Color(0, 80, 160);
	// Foreground Color
	private static final Color FGCOL = new Color(0, 220, 220);
	// Possible colors for waves
	private static final Color[] COLORS = new Color[] { new Color(0, 80, 160), new Color(0, 60, 120),
			new Color(0, 160, 160), new Color(0, 0, 180), new Color(0, 0, 100), new Color(60, 0, 160),
			 new Color(100, 100, 200) };

	// Constructor
	private WaterWaves() {
		// Make layer colors
		layerColors = new Color[1 + getHeight() / LINE_HEI];
		for (int i = 0; i < layerColors.length; i++) {
			do {
				layerColors[i] = COLORS[rand.nextInt(COLORS.length)];
			} while (i > 0 && layerColors[i] == layerColors[i - 1]);
		}
	}

	// Draw
	@Override
	public void draw(Graphics2D g, int i, double seconds) {
		// Background
		g.setColor(BGCOL);
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
			// Fill shape
			Color chosen = layerColors[y / LINE_HEI];
			g.setColor(chosen);
			g.fill(construct);
		}
		// Draw bounds
		g.setClip(null);
		g.setColor(FGCOL);
		g.setStroke(new BasicStroke(5));
		g.draw(bounds);

	}

	// Get max offset
	private double getOffset(int x, int y) {
		return 1;
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
