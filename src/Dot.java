import processing.core.*;

/**
 * A simple data point. This will be drawn as a circle.
 * 
 * @author Dan Feng
 */
public class Dot {

	PApplet papa;	// reference to Olympia to allow use of Processing library
	
	Graphrame grapheme;
	
	static int colors[] = { 0xFFFFD700, 0xFFC0C0C0, 0xFFDA8A67 };
	static float radius   = 8,
				 diameter = 16;
	
	boolean highlighted = false;
	
	float x, y;
	Medalist medalist;
	
	/**
	 * Constructor.
	 * 
	 * @param papa reference to Olympia PApplet
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param medalist the lucky winner
	 */
	public Dot(PApplet papa, Graphrame grapheme, float x, float y, Medalist medalist)
	{
		this.papa = papa;
		this.grapheme = grapheme;
		this.x = x;
		this.y = y;
		this.medalist = medalist;
	}
	
	/**
	 * Append an athlete's name and country if people tied.
	 * 
	 * @param athlete runner-up, second best
	 * @param country his nationality
	 */
	public void addAthlete(String athlete, String country)
	{
		this.medalist.athlete += "/" + athlete;
		this.medalist.country += "/" + country;
	}
	
	/**
	 * Drawer.
	 */
	public void draw()
	{
		int color = colors[medalist.medal.ordinal()];
		papa.strokeWeight(1f);
		
		if(highlighted)
		{	
			papa.line(x, y, grapheme.x, y);
			papa.line(x, y, x, grapheme.y + grapheme.h);
			
			papa.line(x, y, x + 36, y - 36);
			papa.fill(color);
			papa.rect(x + 36, y - 76, papa.textWidth(medalist.athlete) + 4, 40, 4);	// height 40
			
			papa.fill(0);
			papa.textAlign(PConstants.LEFT);
			papa.text(medalist.athlete, x + 36 + 2, y - 76 + 12);
			papa.text(medalist.country, x + 36 + 2, y - 76 + 24);
			papa.text(medalist.score  , x + 36 - 1, y - 76 + 36);
		}
		
		papa.fill(color);
		papa.ellipse(x, y, diameter, diameter);
	}
	
	/**
	 * Highlighter.
	 */
	public boolean highlight()
	{
		return highlighted = ((PApplet.abs(x - papa.mouseX) <= radius) && (PApplet.abs(y - papa.mouseY) <= radius));
	}
}
