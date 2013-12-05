import processing.core.*;
import java.util.ArrayList;

/**
 * A simple data point. This will be drawn as a circle.
 * 
 * @author Dan Feng
 */
public class Dot {

	static int colors[] = { 0xFFFFD700, 0xFFC0C0C0, 0xFFDA8A67 };
	static float radius   = 6,
				 diameter = radius*2;
	
	PApplet papa;	// reference to Olympia to allow use of Processing library
	
	float x, y;
	
	boolean highlighted = false;
	
	int year;
	ArrayList<String> athlete;
	int medal;
	ArrayList<String> country;
	float score;
	
	/**
	 * Constructor.
	 * 
	 * @param papa reference to Olympia PApplet
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param medalist the lucky winner
	 */
	public Dot(PApplet papa, float x, float y)
	{
		this.papa = papa;
		this.x = x;
		this.y = y;
		this.athlete = new ArrayList<String>();
		this.country = new ArrayList<String>();
	}
	
	public Dot setAthlete(int year, String athlete, int medal, String country, float score)
	{
		this.year = year;
		this.athlete.add(athlete);
		this.medal = medal;
		this.country.add(country);
		this.score = score;
		
		return this;
	}
	
	/**
	 * Append an athlete's name and country if people tied.
	 * 
	 * @param athlete runner-up, second best
	 * @param country his nationality
	 */
	public void addAthlete(String athlete, String country)
	{
		this.athlete.add(athlete);
		this.country.add(country);
	}
	
	/**
	 * Drawer.
	 */
	public void draw()
	{
		papa.strokeWeight(1f);
		papa.fill(colors[medal]);
		papa.ellipse(x, y, diameter, diameter);
	}
	
	public void highlight()
	{
		// highlighted = !highlighted;
		papa.strokeWeight(2f);
		papa.fill(0xFFFF0000);	// colors[medal], 1);
		papa.ellipse(x, y, diameter, diameter);
	}
	
	/**
	 * Highlighter.
	 */
	public boolean moused()
	{
		return highlighted = (PApplet.abs(x - papa.mouseX) <= radius) && (PApplet.abs(y - papa.mouseY) <= radius);
	}
	
	public ArrayList<Dot> matchAthlete(Dot[] dots, int[] indices, ArrayList<Dot> matches)
	{
		for (String ath : athlete) {
			for (int i = indices[0]; i < indices.length; i++) {
				for (String ath2: dots[indices[i]].athlete) {
					if (ath.equals(ath2)) {
						matches.add(dots[indices[i]]);
					}
				}
			}
		}
		return matches;
	}
	
	public String toString()
	{
		return year + ", " + athlete.get(0) + ", " + medal + ", " + country.get(0) + ", " + score;
	}
}
