import processing.core.*;
import java.util.ArrayList;
import controlP5.*;

/**
 * My precious.
 * 
 * @author Dan Feng
 */
public class Olympia extends PApplet {

	private static final long serialVersionUID = 1L;
	
	ControlP5 cp5;
	DropdownList sportList;
	int dropdownCount = 0;
	
	int summer = 0, 
	    winter = 1;
	int yscale = 1;
	Graphrame grapheme;
	ArrayList<Dot> allDots;	
	boolean scoreMode;
	
	public static void main(String[] args)
	{
		PApplet.main(new String[] { "Olympia" });
	}

	public void setup()
	{
		size(1280, 900);
		frameRate(8);
		textSize(11);
		loadSet("tf_discus_men.csv");
		scoreMode = true;
		//The following is for the dropdown menu
		cp5 = new ControlP5(this);
		sportList = cp5.addDropdownList("sportsList");
		sportList.setPosition(1125, 400);
		customizeList(sportList);
	}
	
	public void draw()
	{
		background(255);
		grapheme.draw();
	}
	
	public void mouseMoved()
	{
		// background(255);
		// grapheme.draw();
		grapheme.highlight();
	}
	
	public void mouseReleased(){
		grapheme.checkMenu();
	}
	
	public void loadSet(String filename)
	{
		grapheme = new Graphrame(this);
		allDots = new ArrayList<Dot>();
		
		String[] loads = loadStrings(filename);
		grapheme.title = loads[0].split(",")[1]; //keep this for now
		
		//The score unit is used for drawing the y-axis ticks and more descriptive titles
		if (filename.equals("tf_discus_men.csv")){
			grapheme.scoreUnit = "m";
			grapheme.title = "Men's Discus Throw";
		}
		else if (filename.equals("tf_400m_women.csv")){
			grapheme.scoreUnit = "s";
			grapheme.title = "Women's 400m Swimming";
		}
		grapheme.xLower = Integer.parseInt(loads[0].substring(0, 4));
		grapheme.xUpper = Integer.parseInt(loads[loads.length-1].substring(0, 4));
		
		
		for(String sub : loads)
			parser(sub.split(","));
		
		grapheme.begin(allDots);
		grapheme.plant(summer, yscale);
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
	
	public void customizeList(DropdownList list){
		list.setBackgroundColor(color(190));
		list.setItemHeight(20);
		list.setBarHeight(15);
		list.setWidth(150);
		list.addItem("Men's Discus Throw", 0);
		list.addItem("Women's 400M Swimming", 1);
		list.addItem("Women's 100M Freestyle Swimming", 2);
		list.setColorBackground(color(60));
		list.setColorActive(color(255, 128));
	}
	
	public void controlEvent(ControlEvent event) {
		  // DropdownList is of type ControlGroup.
		  // A controlEvent will be triggered from inside the ControlGroup class.
		  // therefore you need to check the originator of the Event with
		  // if (theEvent.isGroup())
		  // to avoid an error message thrown by controlP5.

		  if (event.isGroup()) {
		    // check if the Event was triggered from a ControlGroup
			  if (event.getGroup().getValue() == 0){
				  grapheme.scoreUnit = "meters";
				  loadSet("tf_discus_men.csv");
			  }
			  else if (event.getGroup().getValue() == 1){
				  loadSet("tf_400m_women.csv");
			  }
			  else if (event.getGroup().getValue() == 2){
				  loadSet("swim_100m_free_women.csv");
			  }
		  } 

		}
}
