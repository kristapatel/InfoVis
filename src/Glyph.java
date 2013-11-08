import processing.core.PApplet;
import processing.core.PConstants;
import java.util.ArrayList;

/**
 * To contain graph specifics.
 * 
 * @author Dan Feng
 */
public class Glyph {

	PApplet papa;	// reference to Olympia to allow use of Processing library
	
	static int[] summers = { 1896, 1900, 1904, 1908, 1912, 1916, 1920, 
							 1924, 1928, 1932, 1936, 1940, 1944, 1948, 
							 1952, 1956, 1960, 1964, 1968, 1972, 1976, 
							 1980, 1984, 1988, 1992, 1996, 2000, 2004, 2008 };
	
	static int[] winters = { 1924, 1928, 1932, 1936, 1940, 1944, 1948, 
							 1952, 1956, 1960, 1964, 1968, 1972, 1976, 
							 1980, 1984, 1988, 1992, 1994, 1998, 2002, 2006 };
	
	int width = 1000, height = 600;
	float x, y;
	
	ArrayList<Dot> golds;
	ArrayList<Dot> silvers;
	ArrayList<Dot> bronzes;

	/**
	 * Constructor.
	 * 
	 * @param x master x-coordinate
	 * @param y master y-coordinate
	 * @param golds for gold winners
	 * @param silvers for silver winners
	 * @param bronzes for bronze losers
	 * @param papa reference to Olympia PApplet
	 */
	public Glyph(float x, float y, ArrayList<Dot> golds, ArrayList<Dot> silvers, ArrayList<Dot> bronzes, PApplet papa)
	{
		this.x = x;
		this.y = y;
		this.golds = golds;
		this.silvers = silvers;
		this.bronzes = bronzes;
		this.papa = papa;
	}
	
	/**
	 * Drawer.
	 */
	public void draw()
	{
		papa.noFill();
		papa.strokeWeight(4f);
		papa.strokeJoin(PConstants.MITER);
		papa.beginShape();
		papa.vertex(x, y);
		papa.vertex(x, y + height);
		papa.vertex(x + width, y + height);
		papa.endShape();
		
		xTicks();
		yTicks();
	}
	
	/**
	 * Draw tick marks.
	 */
	private void xTicks()
	{
		int tix = winters.length;
		float gap = width/(tix + 1);
		float position = x;
		
		for(int i = 0; i < tix; i++)
			papa.line(position += gap, y + height + 1, position, y + height + 8);
	}
	
	/**
	 * Draw tick marks.
	 */
	private void yTicks()
	{
		
	}
}
