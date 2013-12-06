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
	
	//ControlP5 cp5;
	//DropdownList sportList;
	//Slider yearSlider;
	//int dropdownCount = 0;
	
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
	}
	
	public void draw()
	{
		background(255);
		grapheme.draw();
		//grapheme.drawSlider(yearSlider);
	}
	
	public void mouseMoved()
	{
		// background(255);
		// grapheme.draw();
		grapheme.highlight();
	}
	
	public void mouseReleased(){
		grapheme.checkMenu();
		grapheme.checkSlider();

	}
	
	public void loadSet(String filename)
	{
		grapheme = new Graphrame(this);
		allDots = new ArrayList<Dot>();
		
		String[] loads = loadStrings(filename);
		
		//The score unit is used for drawing the y-axis ticks and more descriptive titles
		if (filename.equals("tf_discus_men.csv")){
			grapheme.scoreUnit = "m";
			grapheme.title = "Men's Discus Throw";
		}
		else if (filename.equals("tf_400m_women.csv")){
			grapheme.scoreUnit = "s";
			grapheme.title = "Women's 400m Swimming";
		}
		else if (filename.equals("track_5000m_men.csv")){
			grapheme.scoreUnit = "s";
			grapheme.title = "Men's 5000m";
		}
		else if (filename.equals("track_400m_hurdles_men.csv")){
			grapheme.scoreUnit = "s";
			grapheme.title = "Men's 400m Hurdles";
		}
		else{
			grapheme.title = loads[0].split(",")[1];
		}
		grapheme.xLower = Integer.parseInt(loads[0].substring(0, 4));
		grapheme.xUpper = Integer.parseInt(loads[loads.length-1].substring(0, 4));
		
		
		for(String sub : loads)
			parser(sub.split(","));
		
		grapheme.begin(allDots);
		grapheme.plant(yscale);
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
		String country = setCountry(sub[4].trim());
		String stringScore = sub[5];
		float score;
		
		if (stringScore.contains(":")) {
			String[] tokens = stringScore.split(":");
			int minutes = Integer.parseInt(tokens[0]);
			float seconds = Float.parseFloat(tokens[1]);
			score = minutes*60 + seconds;
		} else
			score = Float.parseFloat(stringScore);
		
		boolean drawn = false;
		if (score != 0){
			drawn = true;
		}
		
		
		int medal;
		if(metal.trim().equals("GOLD")) {
			medal = 0;
		}
		else if(metal.trim().equals("SILVER")) {
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
		allDots.add(new Dot(this, 0, 0, drawn).setAthlete(year, athlete, medal, country, score));
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
				  loadSet("tf_discus_men.csv");
			  }
			  else if (event.getGroup().getValue() == 1){
				  loadSet("track_5000m_men.csv");
			  }
			  else if (event.getGroup().getValue() == 2){
				  loadSet("track_400m_hurdles_men.csv");
			  }
			  else if (event.getGroup().getValue() == 3){
				  loadSet("tf_400m_women.csv");
			  }
			  else if (event.getGroup().getValue() == 4){
				  loadSet("swim_100m_free_women.csv");
			  }
		  } 

		}
	
	/**
	 * This method returns the full country name given the country code
	 * @par
	 */
	public String setCountry(String countryCode){
		if (countryCode.equals("ALG")){
			return "Algeria";
		}
		else if (countryCode.equals("ANZ")){
			return "Australia/New Zealand";
		}
		else if (countryCode.equals("AUS")){
			return "Australia";
		}
		else if (countryCode.equals("BAH")){
			return "Bahamas";
		}
		else if (countryCode.equals("BEL")){
			return "Belgium";
		}
		else if (countryCode.equals("BDI")){
			return "Burundi";
		}
		else if (countryCode.equals("BLR")){
			return "Belarus";
		}
		else if (countryCode.equals("BOH")){
			return "Bohemia";
		}
		else if (countryCode.equals("CAN")){
			return "Canada";
		}
		else if (countryCode.equals("COL")){
			return "Colombia";
		}
		else if (countryCode.equals("CUB")){
			return "Cuba";
		}
		else if (countryCode.equals("CZE")){
			return "Czechoslovakia";
		}
		else if (countryCode.equals("DOM")){
			return "Dominican Republic";
		}
		else if (countryCode.equals("EST")){
			return "Estonia";
		}
		else if (countryCode.equals("ETH")){
			return "Ethiopia";
		}
		else if (countryCode.equals("FIN")){
			return "Finland";
		}
		else if (countryCode.equals("FRA")){
			return "France";
		}
		else if (countryCode.equals("FRG")){
			return "West Germany";
		}
		else if (countryCode.equals("GBR")){
			return "Great Britain";
		}
		else if (countryCode.equals("GER")){
			return "Germany";
		}
		else if (countryCode.equals("GDR")){
			return "East Germany";
		}
		else if (countryCode.equals("GRE")){
			return "Greece";
		}
		else if (countryCode.equals("HUN")){
			return "Hungary";
		}
		else if (countryCode.equals("IRL")){
			return "Ireland";
		}
		else if (countryCode.equals("ITA")){
			return "Italy";
		}
		else if (countryCode.equals("JAM")){
			return "Jamaica";
		}
		else if (countryCode.equals("KEN")){
			return "Kenya";
		}
		else if (countryCode.equals("KSA")){
			return "Saudi Arabia";
		}
		else if (countryCode.equals("LTU")){
			return "Lithuania";
		}
		else if (countryCode.equals("MEX")){
			return "Mexico";
		}
		else if (countryCode.equals("MAR")){
			return "Morocco";
		}
		else if (countryCode.equals("NED")){
			return "The Netherlands";
		}
		else if (countryCode.equals("NGR")){
			return "Nigeria";
		}
		else if (countryCode.equals("NZL")){
			return "New Zealand";
		}
		else if (countryCode.equals("PHI")){
			return "Philippines";
		}
		else if (countryCode.equals("POL")){
			return "Poland";
		}
		else if (countryCode.equals("POR")){
			return "Portugal";
		}
		else if (countryCode.equals("RSA")){
			return "Russia";
		}
		else if (countryCode.equals("SEN")){
			return "Senegal";
		}
		else if (countryCode.equals("SRI")){
			return "Sri Lanka";
		}
		else if (countryCode.equals("SUI")){
			return "Switzerland";
		}
		else if (countryCode.equals("SWE")){
			return "Sweden";
		}
		else if (countryCode.equals("TAN")){
			return "Tanzania";
		}
		else if (countryCode.equals("TUN")){
			return "Tunisia";
		}
		else if (countryCode.equals("URS")){
			return "Soviet Union";
		}
		else if (countryCode.equals("USA")){
			return "United States of America";
		}
		else if (countryCode.equals("ZAM")){
			return "Zambia";
		}
		else{
			return countryCode;
		}
		
	}
}
