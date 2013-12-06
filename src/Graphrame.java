import processing.core.*;

import java.util.ArrayList;

import controlP5.*;


/**
 * To contain graph specifics.
 * 
 * @author Dan Feng
 */
public class Graphrame {

	PApplet papa;	// reference to Olympia to allow use of Processing library
	 
	
	float x = 50, 
		  y = 100, 
	  	  h = 600, 
		  w = 1000;
	
	int[] years;	// set with xTicks
	boolean[] yearsDrawn; //if the years should be drawn
	
	String scoreUnit; //the unit the score is in (e.g. meters or s)
	
	int xLower, xUpper; //bounds for tick marks (years)
	
	int yscale,
		ymarks = 10;		// set with yTicks
	
	float lbound, ubound;	// set with yTicks
	float[] scores;
	
	float[] xTicks, yTicks;
	ArrayList<Float> xTicks2 = new ArrayList<Float>();
	float[] yLabels;		// set with yTicks
	
	String title = "";
	
	Dot[] metalli;
	int[] gindex, sindex, bindex;
	
	ArrayList<Dot> highlightedDots = new ArrayList<Dot>();
	Dot mouseDot = null;

	boolean drawGold = true;
	boolean drawSilver = true;
	boolean drawBronze = true;
	Range yearSlider;
	DropdownList sportList;
	ControlP5 control;
	

	/**
	 * Constructor.
	 * 
	 * @param papa reference to Olympia PApplet
	 */
	public Graphrame(PApplet papa)
	{
		this.papa = papa;
		control = new ControlP5(papa);
		sportList = control.addDropdownList("sportsList");
		sportList.setPosition(1125, 500);
		customizeList(sportList);
		//setUpSlider();


	}
	
	public void setUpSlider(){
		yearSlider = control.addRange("yearSlider");
		yearSlider.setPosition(1125, 600);
		customizeSlider(yearSlider);
	}
	
	public void setUpSlider(int min, int max){
		yearSlider = control.addRange("yearSlider");
		yearSlider.setPosition(1125, 600);
		customizeSlider(yearSlider);
		yearSlider.setRange(min, max);
		yearSlider.setRangeValues(min, max);
	}
	
	public void begin(ArrayList<Dot> dots)
	{
		metalli = dots.toArray(new Dot[dots.size()]);
		/*for (Dot d : metalli){
			System.out.println(d.toString());
		}*/
		int gi = 0, si = 0, bi = 0, fi = 0;
		int size = metalli.length;
		gindex = new int[size/3];
		sindex = new int[size/3];
		bindex = new int[size/3];
		scores = new float[size];
		
		for(int i = 0; i < size; i++)
		{
			scores[fi++] = metalli[i].score;
			int m = metalli[i].medal;
			
			if(m == 0)
				gindex[gi++] = i;
			else if (m == 1)
				sindex[si++] = i;
			else
				bindex[bi++] = i;
		}
	}
	
	public void plant(int yscale)
	{
		plantXTicks();
		plantYTicks(yscale);
		for(Dot dot : metalli)
			plantDot(dot);
	}
	
	/**
	 * Tick marks for summer or winter years.
	 * 
	 * @param season 0 for summer and 1 for winter.
	 */
	private void plantXTicks()
	{
		
		
		int numTicks = (xUpper - xLower)/4 + 1;
		float gap = w/numTicks;
		float xPos = x;
		
		xTicks = new float[numTicks]; //this is an array of floats with the positions of the tickmarks for each year
		years = new int[numTicks];	//this is an arrays of ints with the years 
		
		for(int i = 0; i < numTicks; i++)
		{
			xPos += gap;
			xTicks[i] = xPos;
			years[i] = xLower + 4*i;
		}

		


	}
	
	/**
	 * Specify 0 or 1 for "scaling mode" and the number of the tick marks you want.
	 * 
	 * @param scale 0 to begin at 0, 1 to begin at lower bound.
	 * @param ymarks the number of tick marks you want.
	 */
	private void plantYTicks(int scale)
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
	private void plantDot(Dot dot)
	{
		if (dot.score != 0 && dot.year >= xLower && dot.year <= xUpper){
			dot.x = xTicks[indexOfYear(dot.year)];
			dot.y = (y + h)	- (dot.score - lbound)/(ubound - lbound)*(y + h - yTicks[yTicks.length - 1]);
		}
	}
	
	public void highlight()
	{
		if(highlightedDots.size() != 0)
		{
			boolean anyHighlighted = false;
			mouseDot = null;
			//if none of the dots are moused over, highlight nothing
			for (Dot highlightDot : highlightedDots){
				if (highlightDot.moused()){
					anyHighlighted = true;
					mouseDot = highlightDot;	// I did this
					break;
				}
			}
			if (!anyHighlighted){
				highlightedDots.clear();
			}
			//for (int i = highlightedDots.size() - 1; i >= 0; i--){
			//	if (!highlightedDots.get(i).moused()){
			//		highlightedDots.remove(i);
			//	}
			//}
		}
		else
		{
			ArrayList<Dot> athleteMatches = new ArrayList<Dot>();
			for(Dot dot : metalli) {
				if(dot.moused()) {
					if (dot.medal == 0 && drawGold){
						highlightedDots.add(dot);
						//returns an array of  dots that matches the athlete 
						athleteMatches = dot.matchAthlete(metalli, gindex, athleteMatches);
						if (drawSilver){
							athleteMatches = dot.matchAthlete(metalli, sindex, athleteMatches);
						}
						if (drawBronze){
							athleteMatches = dot.matchAthlete(metalli, bindex, athleteMatches);
						}
						break;
					}
					if (dot.medal == 1 && drawSilver){
						highlightedDots.add(dot);
						athleteMatches = dot.matchAthlete(metalli, sindex, athleteMatches);
						if (drawGold){
							athleteMatches = dot.matchAthlete(metalli, gindex, athleteMatches);
						}
						if (drawBronze){
							athleteMatches = dot.matchAthlete(metalli, bindex, athleteMatches);
						}
						break;
					}
					if (dot.medal == 2 && drawBronze){
						highlightedDots.add(dot);
						athleteMatches = dot.matchAthlete(metalli, bindex, athleteMatches);
						if (drawGold){
							athleteMatches = dot.matchAthlete(metalli, gindex, athleteMatches);
						}
						if (drawSilver){
							athleteMatches = dot.matchAthlete(metalli, sindex, athleteMatches);
						}
						break;
					}
				}
			}					
			for (Dot match : athleteMatches){
				highlightedDots.add(match);
			}
		}
	}
	
	/**
	 * Drawer.
	 */
	public void draw()
	{
		papa.textAlign(PConstants.CENTER);
		papa.textSize(24);
		papa.fill(0);
		papa.text(title, (x + w)/2, y);
		
		drawMenu();
		drawAxes();
		drawTicks();
		drawConnektors();
		drawDots();

		
		if(highlightedDots.size() != 0) {	// != null) {
			drawHighlighted();
		}
		
		if(mouseDot != null) {
			drawMoused();
		}
	}
	
	
	private void drawMenu(){
		if (drawGold){
			papa.fill(0xFFFFD700);
		}
		else{
			papa.fill(0xFFFFFFF);
		}
		papa.rect(1150, 200, 20, 20);
		
		if (drawSilver){
			papa.fill(0xFFC0C0C0);
		}
		else{
			papa.fill(0xFFFFFF);
		}
		papa.rect(1150, 235, 20, 20);
		
		if (drawBronze){
			papa.fill(0xFFDA8A67);
		}
		else{
			papa.fill(0xFFFFFF);
		}
		papa.rect(1150, 270, 20, 20);
		
		papa.fill(0x000000);
		papa.textSize(16);
		papa.textAlign(2);
		papa.text("Show Gold", 1175, 215);
		papa.text("Show Silver", 1175, 250);
		papa.text("Show Bronze", 1175, 285);
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
		papa.textSize(11);
		int year = xLower;
		int yearHigh = xUpper;

		for(int i = 0; i < xTicks.length; i++)
		{
			papa.line(xTicks[i], y + h + 1, xTicks[i], y + h + 8);
			papa.text(year, xTicks[i], y + h + 24);
			year = year + 4;
		}
		
		for(int i = 0; i < yTicks.length; i++)
		{
			papa.line(x - 1, yTicks[i], x - 8, yTicks[i]);
			papa.text(((int)yLabels[i] +" " + scoreUnit), x - 24, yTicks[i] + 4);
		}
	}

	private void drawConnektors()	// select ability
	{
		papa.noFill();
		papa.strokeWeight(2f);
		papa.strokeJoin(PConstants.ROUND);
		
		if (drawGold){
			papa.beginShape();
			for(int i : gindex){
				if (metalli[i].drawn){
					papa.vertex(metalli[i].x, metalli[i].y);
				}
			}
			papa.endShape();
		}
		
		if (drawSilver){
			papa.beginShape();
			for(int i : sindex){
				if (metalli[i].drawn){
					papa.vertex(metalli[i].x, metalli[i].y);
				}
			}
			papa.endShape();
		}
		
		if (drawBronze){
			papa.beginShape();
			for(int i : bindex){
				if (metalli[i].drawn){
					papa.vertex(metalli[i].x, metalli[i].y);
				}
			}
			papa.endShape();
		}
	}
	
	private void drawDots()	// select ability
	{
		if (drawGold){
			papa.beginShape();
			for (int i : gindex){
				if (metalli[i].drawn){
					metalli[i].draw();
				}
			}
		}
		
		if (drawSilver){
			papa.beginShape();
			for (int i : sindex){
				if (metalli[i].drawn){
					metalli[i].draw();
				}
			}
		}
		
		if (drawBronze){
			papa.beginShape();
			for (int i : bindex){
				if (metalli[i].drawn){
					metalli[i].draw();
				}
			}
		}
	}
	
	private void drawHighlighted()
	{
//		for (Dot highlighted : highlightedDots){
//			papa.line(highlighted.x, highlighted.y, x, highlighted.y);
//			papa.line(highlighted.x, highlighted.y, highlighted.x, y + h);
//			
//			papa.line(highlighted.x, highlighted.y, highlighted.x + 36, highlighted.y - 36);
//			papa.fill(Dot.colors[highlighted.medal]);
//			papa.rect(highlighted.x + 36, highlighted.y - 76, papa.textWidth(highlighted.athlete.get(0)) + 4, 40, 4);	// height 40
//			
//			papa.fill(0);
//			papa.textAlign(PConstants.LEFT);
//			papa.text(highlighted.athlete.get(0), highlighted.x + 36 + 2, highlighted.y - 76 + 12);
//			papa.text(highlighted.country.get(0), highlighted.x + 36 + 2, highlighted.y - 76 + 24);
//			papa.text(highlighted.score  , highlighted.x + 36 - 1, highlighted.y - 76 + 36);
//			
//			highlighted.draw();
//		}
		
		for(Dot highlighted : highlightedDots) {
			highlighted.highlight();
		}
	}
	
	private void drawMoused()
	{
		papa.strokeWeight(1f);
		papa.line(mouseDot.x, mouseDot.y, x, mouseDot.y);
		papa.line(mouseDot.x, mouseDot.y, mouseDot.x, y + h);
		
		papa.line(mouseDot.x, mouseDot.y, mouseDot.x + 36, mouseDot.y - 36);
		papa.fill(Dot.colors[mouseDot.medal]);
		papa.rect(mouseDot.x + 36, mouseDot.y - 76, Math.max(papa.textWidth(mouseDot.athlete.get(0)) + 4, papa.textWidth(mouseDot.country.get(0)) +4), 40, 4);	// height 40
		
		papa.fill(0);
		papa.textAlign(PConstants.LEFT);
		papa.text(mouseDot.athlete.get(0), mouseDot.x + 36 + 2, mouseDot.y - 76 + 12);
		papa.text(mouseDot.country.get(0), mouseDot.x + 36 + 2, mouseDot.y - 76 + 24);
		papa.text(mouseDot.score  , mouseDot.x + 36 - 1, mouseDot.y - 76 + 36);
		
		mouseDot.draw();
	}

	/**
	 * Crudely returns index of the sought-after year in either summers or winters.
	 * 
	 * @param year 1900 - 2008
	 * @return its index in either summers or winters. Simple -1 if bad year.
	 */
	private int indexOfYear(int year)
	{
		for(int i = 0; i < years.length; i++) {
			if(years[i] == year)
				return i;
		}
		return -1;
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
	
	public void customizeSlider(Range slider){
		
		slider.setRange(1900, 2008);
		slider.setRangeValues(1900, 2008);
		slider.setHeight(100);


	}
	
	public void checkMenu(){
		if (PApplet.abs(papa.mouseX - 1150) <= 20 && PApplet.abs(papa.mouseY - 200) <= 20){
			drawGold = !drawGold;
		}
		else if (PApplet.abs(papa.mouseX - 1150) <= 20 && PApplet.abs(papa.mouseY - 235) <= 20){
			drawSilver = !drawSilver;
		}
		else if (PApplet.abs(papa.mouseX - 1150) <= 20 && PApplet.abs(papa.mouseY - 270) <= 20){
			drawBronze = !drawBronze;
		}
	}
	
	public int[] checkSlider(){
		int sliderMin = Math.round((int)yearSlider.getLowValue()/4)*4;
		int sliderMax = Math.round((int)yearSlider.getHighValue()/4)*4;		
		int[] rangeVals = new int[2];


		xUpper = sliderMax;
		xLower = sliderMin;
		rangeVals[0] = xLower;
		rangeVals[1] = xUpper;
		return rangeVals;
	}
	/**
	 * This method formats the dropdown list.
	 * 
	 * @param list Dropdown list object
	 */
	public void customizeList(DropdownList list){
		list.setBackgroundColor(papa.color(190));
		list.setItemHeight(20);
		list.setBarHeight(15);
		list.setWidth(150);
		list.addItem("Men's Discus Throw", 0);
		list.addItem("Men's 5000m Track", 1);
		list.addItem("Men's 400m Hurdles", 2);
		list.addItem("Women's 400M Swimming", 3);
		//list.addItem("Women's 100M Freestyle Swimming", 4);
		list.setColorBackground(papa.color(60));
		list.setColorActive(papa.color(255, 128));
	}
	
	
}
