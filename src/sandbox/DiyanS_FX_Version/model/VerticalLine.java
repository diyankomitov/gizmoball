package model;

import physics.LineSegment;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class VerticalLine {

	private int xpos;
	private int ypos;
	private int width;
	private LineSegment ls;

	public VerticalLine(int x, int y, int w) {
		xpos = x;
		ypos = y;
		width = w;
		ls = new LineSegment(x, y, x + w, y);
	}

	public LineSegment getLineSeg() {
		return ls;
	}

	public int getX() {
		return xpos;
	}

	public int getY() {
		return ypos;
	}

	public int getWidth() {
		return width;
	}

}
