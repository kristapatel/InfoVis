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
		background(255);	// refresh only on mouseMoved()
		
		grapheme.draw();
		drawDots(goldDots);
		drawDots(silverDots);
		drawDots(bronzeDots);
	}
	
	public void mouseMoved()
	{
		highlightDots(goldDots);
		highlightDots(silverDots);
		highlightDots(bronzeDots);
	}
	
	public void drawDots(ArrayList<Dot> dotList)
	{
		connektor(dotList);
		for(Dot dot : dotList)
			dot.draw();
	}
	
	public void highlightDots(ArrayList<Dot> dotList)
	{
		for(Dot dot : dotList)
			dot.highlight();
	}
	
	/**
	 * This is to draw the connecting lines.
	 * 
	 * @param dotList
	 */
	public void connektor(ArrayList<Dot> dotList)
	{
		noFill();
		strokeWeight(2f);
		strokeJoin(PConstants.ROUND);
		
		beginShape();
		for(Dot dot : dotList) {
			vertex(dot.x, dot.y);
		}
		endShape();
	}
	
	public void loadSet(String filename)
	{
		String[] things = loadStrings(filename);
		float[] scores = new float[things.length];
		
		int i = 0;
		for(String thing : things)
		{
			String[] sub = thing.split(",");
			scores[i++] = Float.parseFloat(sub[5]);
			addDot(sub);
		}
		
		grapheme.setScores(scores);
		grapheme.setXTicks(summer);
		grapheme.setYTicks(yscale, 10);
		
		plantDots(goldDots);
		plantDots(silverDots);
		plantDots(bronzeDots);
	}
	
	/**
	 * This is to add a Dot to the appropriate list given a String array of the things.
	 * 
	 * @param sub
	 */
	public void addDot(String[] sub)
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
	
	/**
	 * This is to set the x/y-coordinates of each Dot.
	 * @param dotList
	 */
	public void plantDots(ArrayList<Dot> dotList)
	{
		for(Dot dot : dotList)
			grapheme.plantDot(dot);
	}
}
