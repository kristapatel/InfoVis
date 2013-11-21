import processing.core.*;

import java.util.ArrayList;

/**
 * My precious.
 * 
 * @author Dan Feng
 */
public class Olympia extends PApplet {

	private static final long serialVersionUID = 1L;
	
	final int summer = 0, 
			  winter = 1;
	
	int yscale = 0;
	
	ArrayList<Dot> goldDots   = new ArrayList<Dot>();
	ArrayList<Dot> silverDots = new ArrayList<Dot>();
	ArrayList<Dot> bronzeDots = new ArrayList<Dot>();
	
	Graphrame grapheme;
	
	public static void main(String[] args)
	{
		PApplet.main(new String[] { "Olympia" });
	}

	public void setup()
	{
		size(1280, 800);
		background(255);
		textSize(11);
		
		grapheme = new Graphrame(this);
		loadSet("Book1.csv");
	}
	
	public void draw()
	{
		background(255);
		grapheme.draw();
	}
	
	public void mouseMoved()	// highlighting needs work
	{
		// for(Dot dot : goldDots)   dot.highlight();
		// for(Dot dot : silverDots) dot.highlight();
		// for(Dot dot : bronzeDots) dot.highlight();
		grapheme.highlight();
	}
	
	public void mouseReleased(){
		grapheme.checkMenu();
		
	}
	
	public void loadSet(String filename)
	{
		String[] things = loadStrings(filename);
		float[] scores = new float[things.length];
		
		for(int i = 0; i < things.length; i++)
		{
			String[] sub = things[i].split(",");
			scores[i] = Float.parseFloat(sub[5]);
			parseDot(sub);
		}
		
		grapheme.setScores(scores);
		
		grapheme.plantXTicks(summer);
		grapheme.plantYTicks(yscale);
		
		grapheme.setGold(goldDots);
		grapheme.setSilver(silverDots);
		grapheme.setBronze(bronzeDots);
	}
	
	/**
	 * This is to add a Dot to the appropriate list given a String array of the things.
	 * 
	 * @param sub
	 */
	public void parseDot(String[] sub)
	{
		float score = Float.parseFloat(sub[5]);
		if(score <= 0) return;
		
		int year = Integer.parseInt(sub[0]);
		String athlete = sub[2];
		String country = sub[4];
		Medalist medalist;
		
		int index;
		ArrayList<Dot> dotList;
		
		if(sub[3].equals("GOLD"))
		{
			index = 0;
			dotList = goldDots;
		}
		else if(sub[3].equals("SILVER"))
		{
			index = 1;
			dotList = silverDots;
		}
		else
		{
			index = 2;
			dotList = bronzeDots;
		}
		medalist = new Medalist(year, score, athlete, country, Medalist.Medal.values()[index]);
		
		for(Dot d : dotList)
		{
			if(d.medalist.year == medalist.year && d.medalist.medal == medalist.medal)
			{
				d.addAthlete(medalist.athlete, medalist.country);
				return;
			}
		}
		dotList.add(new Dot(this, grapheme, 0, 0, medalist));
	}
}
