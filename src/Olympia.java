import processing.core.*;
import java.util.ArrayList;

/**
 * My precious.
 * 
 * @author Dan Feng
 */
public class Olympia extends PApplet {

	private static final long serialVersionUID = 1L;
	
	int summer = 0, 
	    winter = 1;
	int yscale = 1;
	Graphrame grapheme;
	ArrayList<Dot> allDots;	
	
	public static void main(String[] args)
	{
		PApplet.main(new String[] { "Olympia" });
	}

	public void setup()
	{
		size(1280, 800);
		frameRate(8);
		textSize(11);
		
		loadSet("tf_discus_men.csv");
	}
	
	public void draw()
	{
		
	}
	
	public void mouseMoved()
	{
		background(255);
		grapheme.draw();
		grapheme.highlight();
	}
	
	public void loadSet(String filename)
	{
		grapheme = new Graphrame(this);
		allDots = new ArrayList<Dot>();
		
		for(String sub : loadStrings(filename))
			parser(sub.split(","));
		
		grapheme.handOver(allDots);
		grapheme.initialize(summer, yscale);
	}
	
	/**
	 * This is to add a Dot to the appropriate list given a String array of the things.
	 * 
	 * @param sub
	 */
	public void parser(String[] sub)
	{
		int year = Integer.parseInt(sub[0]);
		String athlete = sub[2];
		String metal = sub[3];
		String country = sub[4];
		float score = Float.parseFloat(sub[5]);
	
		if(score == 0)
			return;
		
		int medal;		
		if(metal.equals("GOLD")) {
			medal = 0;
		}
		else if(metal.equals("SILVER")) {
			medal = 1;
		}
		else {
			medal = 2;
		}
		
		for(Dot d : allDots) {
			if(d.year == year && d.medal == medal) {
				d.addAthlete(athlete, country);
				return;
			}
		}
		allDots.add(new Dot(this, 0, 0).setAthlete(year, athlete, medal, country, score));
	}
}
