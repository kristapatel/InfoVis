import processing.core.PApplet;
import processing.core.PConstants;

/**
 * To contain graph specifics.
 * 
 * @author Dan Feng
 */
public class Graphrame {

	PApplet papa;	// reference to Olympia to allow use of Processing library
	
	int[] summers = { 1900, 1904, 1908, 1912, 1916, 1920, 1924, 
					  1928, 1932, 1936, 1940, 1944, 1948, 1952, 
					  1956, 1960, 1964, 1968, 1972, 1976, 1980, 
					  1984, 1988, 1992, 1996, 2000, 2004, 2008 };	// ignore 1906
	
	int[] winters = { 1924, 1928, 1932, 1936, 1940, 1944, 1948, 
					  1952, 1956, 1960, 1964, 1968, 1972, 1976, 
					  1980, 1984, 1988, 1992, 1994, 1998, 2002, 2006 };
	
	int width = 1000, height = 600;
	float x, y;
	float scores[];
	float minS, maxS;

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
	public Graphrame(PApplet papa, float x, float y)
	{
		this.papa = papa;
		this.x = x;
		this.y = y;
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
	}
	
	public int indexOfYear(int season, int year)
	{
		int weather[] = season == 0 ? summers : winters;
		for(int i = 0; i < weather.length; i++)
		{
			if(weather[i] == year)
				return i;
		}
		return -1;
	}
	
	/**
	 * Draws and labels x-axis tick marks. Varies only based on summer/winter.
	 * 
	 * @param season 0 for summer and 1 for winter
	 */
	public float[] xTicks(int season)
	{
		int weather[] = season == 0 ? summers : winters;
		float gap = width/weather.length;
		float position = x;
		
		float ticks[] = new float[weather.length];
		
		for(int i = 0; i < weather.length; i++)
		{
			position += gap;
			ticks[i] = position;
			papa.line(position, y + height + 1, position, y + height + 8);
			
			papa.fill(0);
			papa.textAlign(PConstants.CENTER);
			papa.text(weather[i], position, y + height + 24);
		}
		return ticks;
	}
	
	/**
	 * Draws and labels y-axis tick marks. Varies based on range of scores[].
	 * 
	 * @param scale 0 if the scale is to start at 0, 1 if the scale is to start at a calculated lower bound.
	 * @param marks the number of tick marks to draw.
	 * @return the y-coordinates of each tick mark.
	 */
	public float[] yTicks(int scale, int marks)
	{
		float max = maxS = upperBound(max(scores));
		float min = minS = scale == 0 ? 0 : lowerBound(min(scores));

		float increment = (max - min)/(marks - 1);	
		float gap = height/marks;
		float position = y + height;
		
		float ticks[] = new float[marks];
		
		if(scale == 0)
			min += increment;
		
		for(int i = 0; i < marks; i++)
		{
			ticks[i] = position -= gap;
			papa.line(x - 1, position, x - 8, position);
			
			papa.fill(0);
			papa.textAlign(PConstants.RIGHT, PConstants.CENTER);
			papa.text((int)min, x - 24, position);
			min += increment;
		}
		return ticks;
	}
	
	/**
	 * Does its best to round a number down.
	 * 
	 * @param min
	 * @return
	 */
	public float lowerBound(float min)
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
	public float upperBound(float max)
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
	public float min(float ation[])
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
	public float max(float ation[])
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
