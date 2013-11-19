import processing.core.*;

import java.util.ArrayList;

/**
 * To contain graph specifics.
 * 
 * @author Dan Feng
 */
public class Graphrame {

	PApplet papa;	// reference to Olympia to allow use of Processing library
	
	static int[] summers = { 1900, 1904, 1908, 1912, 1916, 1920, 1924, 
							 1928, 1932, 1936, 1940, 1944, 1948, 1952, 
							 1956, 1960, 1964, 1968, 1972, 1976, 1980, 
							 1984, 1988, 1992, 1996, 2000, 2004, 2008 };	// ignore 1906
	
	static int[] winters = { 1924, 1928, 1932, 1936, 1940, 1944, 1948, 1952, 1956, 1960, 1964, 
							 1968, 1972, 1976, 1980, 1984, 1988, 1992, 1994, 1998, 2002, 2006 };
	
	float x = 100, 
		  y = 100, 
	  	  h = 600, 
		  w = 1000;
	
	int[] years;	// set with xTicks
	
	int yscale,
		ymarks = 10;		// set with yTicks
	
	float lbound, ubound;	// set with yTicks
	float[] scores;
	
	float[] xTicks, yTicks;
	float[] yLabels;		// set with yTicks
	
	ArrayList<Dot> aurum, argentum, aerea;
	
	Dot highlighted;

	/**
	 * Constructor.
	 * 
	 * @param papa reference to Olympia PApplet
	 */
	public Graphrame(PApplet papa)
	{
		this.papa = papa;
	}

	/**
	 * Crudely returns index of the sought-after year in either summers or winters.
	 * 
	 * @param year 1900 - 2008
	 * @return its index in either summers or winters. Simple -1 if bad year.
	 */
	public int indexOfYear(int year)
	{
		for(int i = 0; i < years.length; i++)
		{
			if(years[i] == year)
				return i;
		}
		return -1;
	}
	
	/**
	 * Sets the scores[] array.
	 * 
	 * @param scores copy that
	 */
	public void setScores(float[] scores)
	{
		this.scores = scores;
	}
	
	public void setGold(ArrayList<Dot> dotList)
	{
		aurum = dotList;
		for(Dot dot : aurum)
			plantDot(dot);
	}
	
	public void setSilver(ArrayList<Dot> dotList)
	{
		argentum = dotList;
		for(Dot dot : argentum)
			plantDot(dot);
	}
	
	public void setBronze(ArrayList<Dot> dotList)
	{
		aerea = dotList;
		for(Dot dot : aerea)
			plantDot(dot);
	}
	
	public void highlight()
	{
		highlighted = null;
		
		for(Dot dot : aurum)
			if(dot.highlight())
			{
				highlighted = dot;
				break;
			}
		
		if(highlighted == null)
			for(Dot dot : argentum)
				if(dot.highlight())
				{
					highlighted = dot;
					break;
				}
		
		if(highlighted == null)
			for(Dot dot : aerea)
				if(dot.highlight())
				{
					highlighted = dot;
					break;
				}
	}
	
	/**
	 * Drawer.
	 */
	public void draw()
	{
		drawAxes();
		drawTicks();
		drawConnektor();
		drawDots();
		
		if(highlighted != null)
			highlighted.draw();
	}
	
	private void drawAxes()
	{
		papa.noFill();
		papa.strokeWeight(4f);
		papa.strokeJoin(PConstants.MITER);
		papa.beginShape();
		papa.vertex(x, y);
		papa.vertex(x, y + h);
		papa.vertex(x + w, y + h);
		papa.endShape();
	}

	private void drawTicks()
	{
		papa.textAlign(PConstants.CENTER);
		papa.fill(0);
		
		for(int i = 0; i < xTicks.length; i++)
		{
			papa.line(xTicks[i], y + h + 1, xTicks[i], y + h + 8);
			papa.text(years[i], xTicks[i], y + h + 24);
		}
		
		for(int i = 0; i < yTicks.length; i++)
		{
			papa.line(x - 1, yTicks[i], x - 8, yTicks[i]);
			papa.text((int)yLabels[i], x - 24, yTicks[i] + 4);
		}
	}

	private void drawConnektor()
	{
		papa.noFill();
		papa.strokeWeight(2f);
		papa.strokeJoin(PConstants.ROUND);
		
		papa.beginShape();
		for(Dot dot : aurum) papa.vertex(dot.x, dot.y);
		papa.endShape();
		
		papa.beginShape();
		for(Dot dot : argentum) papa.vertex(dot.x, dot.y);
		papa.endShape();
		
		papa.beginShape();
		for(Dot dot : aerea) papa.vertex(dot.x, dot.y);
		papa.endShape();
	}
	
	private void drawDots()
	{
		for(Dot dot : aurum) dot.draw();
		for(Dot dot : argentum) dot.draw();
		for(Dot dot : aerea) dot.draw();
	}
	
	/**
	 * Tick marks for summer or winter years.
	 * 
	 * @param season 0 for summer and 1 for winter.
	 */
	public void plantXTicks(int season)
	{
		years = season == 0 ? summers : winters;
		
		float gap = w/years.length,
			  xPos = x;
		
		xTicks = new float[years.length];
		
		for(int i = 0; i < years.length; i++)
		{
			xPos += gap;
			xTicks[i] = xPos;
		}
	}
	
	/**
	 * Specify 0 or 1 for "scaling mode" and the number of the tick marks you want.
	 * 
	 * @param scale 0 to begin at 0, 1 to begin at lower bound.
	 * @param ymarks the number of tick marks you want.
	 */
	public void plantYTicks(int scale)
	{
		yscale = scale;
		
		ubound = upperBound(max(scores));
		lbound = (scale == 0) ? 0 : lowerBound(min(scores));

		float increment = (ubound - lbound)/(ymarks),
			  gap = h/ymarks,
			  yPos = y + h;
		
		yTicks  = new float[ymarks];
		yLabels = new float[ymarks];
		
		for(int i = 0; i < ymarks; i++)
		{
			yTicks[i]  = yPos -= gap;
			yLabels[i] = lbound + increment*(i + 1);	// min += increment;
		}
	}
	
	/**
	 * Sets a Dot object's x/y coordinate.
	 * 
	 * @param dot the dot
	 */
	public void plantDot(Dot dot)
	{
		dot.x = xTicks[indexOfYear(dot.medalist.year)];
		dot.y = (y + h)	- (dot.medalist.score - lbound)/(ubound - lbound)*(y + h - yTicks[yTicks.length - 1]);
	}
	
	/**
	 * Does its best to round a number down.
	 * 
	 * @param min
	 * @return
	 */
	private float lowerBound(float min)
	{
		int bound = (int)min;		
		int magnitude = 0;
		
		while(bound/Math.pow(10, magnitude) >= 10)
			magnitude++;
		
		bound /= Math.pow(10, magnitude);
		bound *= Math.pow(10, magnitude);
		
		return bound;
	}
	
	/**
	 * Does its best to round a number up.
	 * 
	 * @param max
	 * @return
	 */
	private float upperBound(float max)
	{
		int bound = (int)max;
		int magnitude = 0;
		
		while(bound/Math.pow(10, magnitude) >= 10)
			magnitude++;
		
		bound /= Math.pow(10, magnitude);
		bound *= Math.pow(10, magnitude);
		bound += Math.pow(10, magnitude);
		
		return bound;
	}
	
	/**
	 * Finds the smallest float in a float[] array. Ignores non-positive numbers. 
	 * @param ation the float[] array
	 * @return the smallest float in it
	 */
	private float min(float ation[])
	{
		float min = Float.MAX_VALUE;
		
		for(float f : ation)
		{
			if(f < min && f > 0)
				min = f;
		}
		
		return min;
	}
	
	/**
	 * Finds the largest float in a float[] array. Ignores non-positive numbers.
	 * 
	 * @param ation the float[] array
	 * @return the largest float in it
	 */
	private float max(float ation[])
	{
		float max = Float.MIN_VALUE;
		
		for(float f : ation)
		{
			if(f > max && f > 0)
				max = f;
		}
		
		return max;
	}
}
