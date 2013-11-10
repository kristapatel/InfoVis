import processing.core.*;

import java.util.ArrayList;

/**
 * My precious.
 * 
 * @author Dan Feng
 */
public class Olympia extends PApplet {

	private static final long serialVersionUID = 1L;
	
	ArrayList<Dot> goldDots = new ArrayList<Dot>();
	ArrayList<Dot> silverDots = new ArrayList<Dot>();
	ArrayList<Dot> bronzeDots = new ArrayList<Dot>();
	
	Graphrame grapheme;
	
	int season = 0;
	int yScale = 1;
	
	public static void main(String args[])
	{
		PApplet.main(new String[] { "Olympia" });
	}
	
	public void load(String filename)
	{
		String things[] = loadStrings(filename);
		float scores[] = new float[100];
		
		int i = 0;
		for(String thing : things)
		{
			String sub[] = thing.split(",");
			scores[i++] = Float.parseFloat(sub[5]);
			addDot(sub);
		}
		
		grapheme = new Graphrame(this, 100, 100);
		grapheme.draw();
		
		grapheme.scores = scores;
		float xs[] = grapheme.xTicks(season);
		float ys[] = grapheme.yTicks(yScale, 11);		
		plantDots(xs, ys);
	}

	public void setup()
	{
		size(1280, 800);
		background(255);
		textSize(11);
		load("Book2.csv");
	}
	
	public void draw()
	{
		for(Dot dot : bronzeDots)
			dot.draw();
		for(Dot dot : silverDots)
			dot.draw();
		for(Dot dot : goldDots)
			dot.draw();
	}
	
	public void mouseMoved()
	{
		for(Dot dot : goldDots)
			dot.highlight();
		for(Dot dot : silverDots)
			dot.highlight();
		for(Dot dot : bronzeDots)
			dot.highlight();
	}
	
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
			if(d.medalist[0].year == medalist.year && d.medalist[0].medal == medalist.medal)
			{
				d.addMedalist(medalist);
				return;
			}
		}
		
		dotList.add(new Dot(this, 0, 0, new Medalist[] { medalist }));
	}
	
	public void plantDots(float xs[], float ys[])
	{
		// for each dot in each list
		// look at the year
		// find index of that year in static year array
		// use that to index x's
		for(Dot d : goldDots) {
			d.x = xs[grapheme.indexOfYear(season, d.medalist[0].year)];
		}
		for(Dot d : silverDots) {
			d.x = xs[grapheme.indexOfYear(season, d.medalist[0].year)];
		}
		for(Dot d : bronzeDots) {
			d.x = xs[grapheme.indexOfYear(season, d.medalist[0].year)];
		}
		
		// to find y's, calculate position between smallest and largest in y's
		// 
		// use all x/y indices to draw connectors
		// (bottom of graph) + (ratio of score to max score)*(vertical area) + (the initial vertical offset, crudely calculated)
		float minY = yScale == 0 ? (grapheme.y + grapheme.height) : ys[0];
		float maxY = ys[ys.length - 1];
		for(Dot d : goldDots) {
			d.y = (grapheme.y + grapheme.height) - ((d.medalist[0].score - grapheme.minS)/(grapheme.maxS - grapheme.minS))*(minY - maxY) + (ys[1] - ys[0]);
		}
		for(Dot d : silverDots) {
			d.y = (grapheme.y + grapheme.height) - ((d.medalist[0].score - grapheme.minS)/(grapheme.maxS - grapheme.minS))*(minY - maxY) + (ys[1] - ys[0]);
		}
		for(Dot d : bronzeDots) {
			d.y = (grapheme.y + grapheme.height) - ((d.medalist[0].score - grapheme.minS)/(grapheme.maxS - grapheme.minS))*(minY - maxY) + (ys[1] - ys[0]);
		}
		
//		papa.noFill();
//		papa.strokeWeight(4f);
//		papa.strokeJoin(PConstants.MITER);
//		papa.beginShape();
//		papa.vertex(x, y);
//		papa.vertex(x, y + height);
//		papa.vertex(x + width, y + height);
//		papa.endShape();
		
		noFill();
		strokeWeight(2f);
		strokeJoin(PConstants.ROUND);
		
		beginShape();
		for(Dot d : goldDots) {
			vertex(d.x, d.y);
		} endShape();
		
		beginShape();
		for(Dot d : silverDots) {
			vertex(d.x, d.y);
		} endShape();
		
		beginShape();
		for(Dot d : bronzeDots) {
			vertex(d.x, d.y);
		} endShape();
	}
}
