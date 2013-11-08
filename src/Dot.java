import processing.core.PApplet;

/**
 * A simple data point. This will be drawn as a circle.
 * 
 * @author Dan Feng
 */
public class Dot {

	PApplet papa;	// reference to Olympia to allow use of Processing library
	
	static final int Au = 0xFFFFD700;	// gold
	static final int Ag = 0xFFC0C0C0;	// silver
	static final int Cu = 0xFFFF69B4;	// bronze
	static final int Fe = 0xFFFF0000;	// highlight
	static final float radius = 8;
	
	boolean highlighted = false;
	
	float x, y;
	Medalist medalist[];
	
	/**
	 * Constructor.
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param medalist the lucky winner
	 * @param papa reference to Olympia PApplet
	 */
	public Dot(float x, float y, Medalist medalist[], PApplet papa)
	{
		this.x = x;
		this.y = y;
		this.medalist = medalist;
		this.papa = papa;
	}
	
	/**
	 * Drawer.
	 */
	public void draw()
	{
		papa.noStroke();
		papa.fill(highlighted ? Fe : Au);
		papa.ellipse(x, y, radius*2, radius*2);
	}
	
	/**
	 * Highlighter.
	 */
	public void highlight()
	{
		highlighted = ((PApplet.abs(x - papa.mouseX) <= radius) && (PApplet.abs(y - papa.mouseY) <= radius));
	}
}
