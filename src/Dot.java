import processing.core.PApplet;

/**
 * A simple data point. This will be drawn as a circle.
 * 
 * @author Dan Feng
 */
public class Dot {

	PApplet papa;	// reference to Olympia to allow use of Processing library
	
	static final int colors[] = { 0xFFFFD700, 0xFFC0C0C0, 0xFFDA8A67 };
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
	public Dot(PApplet papa, float x, float y, Medalist medalist[])
	{
		this.papa = papa;
		this.x = x;
		this.y = y;
		this.medalist = medalist;
	}
	
	public void addMedalist(Medalist medalist)
	{
		Medalist medalists[] = new Medalist[this.medalist.length];
		
		for(int i = 0; i < medalists.length - 1; i++)
			medalists[i] = this.medalist[i];
		
		medalists[medalists.length - 1] = medalist;
	}
	
	/**
	 * Drawer.
	 */
	public void draw()
	{
		// if(medalist[0].score <= 0) return;
		
		if(highlighted)
			papa.fill(0xFFFF0000);	// future DoD
		else
			papa.fill(colors[medalist[0].medal.ordinal()]);

		papa.noStroke();
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
