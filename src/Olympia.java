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
	ArrayList<CountryMedals> countryMedalsList;
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
		image(loadImage("gold.png"), 5, 785);
		image(loadImage("silver.png"), 5, 825);
		image(loadImage("bronze.png"), 5, 865);
		CountryMedals country;
		textSize(16);
		fill(0);
		for(int i=0; i < countryMedalsList.size(); i++) {
			country = countryMedalsList.get(i);
			image(country.getFlag(), 55 + i*45, 760);
			text(country.getGold(), 68 + i*45, 801);
			text(country.getSilver(), 68 + i*45, 841);
			text(country.getSilver(), 68 + i*45, 881);
		}
	}
	
	public void mouseMoved()
	{
		// background(255);
		// grapheme.draw();
		grapheme.highlight();
	}
	
	public void mouseReleased(){
		grapheme.checkMenu();
		int[] ranges = grapheme.checkSlider();
		checkDots(ranges[0], ranges[1]);
		grapheme.plant(yscale);

	}
	
	public void checkDots(int yearLower, int yearUpper){
		for (Dot d : allDots){
			if (d.year < yearLower || d.year > yearUpper){
				d.drawn = false;
			}
			else{
				d.drawn = true;
			}
			if (d.score == 0){
				d.drawn = false;
			}
			
		}
		
	}
	
	public void loadSet(String filename)
	{
		grapheme = new Graphrame(this);
		allDots = new ArrayList<Dot>();
		countryMedalsList = new ArrayList<CountryMedals>();
		
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
		grapheme.setUpSlider(Integer.parseInt(loads[0].substring(0, 4)), Integer.parseInt(loads[loads.length-1].substring(0, 4)));
		
		
		for(String sub : loads)
			parser(sub.split(","));
		
		for(Dot d: allDots) {
//			Dot d = allDots.get(0);
//				for (int i = 0; i < d.country.size(); i++) {
//					System.out.println("size is: " + d.country.size() + "      and index " + i + " is " + d.country.get(i));
//				}
			boolean alreadyHaveCountry = false;
			String country = d.country.get(0);
			for(int i=0; i < countryMedalsList.size(); i++) {
				CountryMedals currentCountry = countryMedalsList.get(i);
				if (currentCountry.getCountry().equals(country)) {
					alreadyHaveCountry = true;
					if (d.medal == 0)
						currentCountry.incrementGold();
					else if (d.medal == 1)
						currentCountry.incrementSilver();
					else if (d.medal == 2)
						currentCountry.incrementBronze();
				}
			}
			if (!alreadyHaveCountry) {
				String flag;
				String name = d.country.get(0);
				if (name.equals("United States of America"))
					flag = "usa.png";
				else if (name.equals("Algeria"))
					flag = "alg.png";
				else if (name.equals("Australia/New Zealand"))
					flag = "anz.png";
				else if (name.equals("Australia"))					
					flag = "aus.png";
				else if (name.equals("Bahamas"))
					flag = "bah.png";
				else if (name.equals("Belgium"))
					flag = "bel.png";
				else if (name.equals("Burundi"))
					flag = "bdi.png";
				else if (name.equals("Belarus"))
					flag = "blr.png";
				else if (name.equals("Bohemia"))
					flag = "boh.png";
				else if (name.equals("Canada"))
					flag = "can.png";
				else if (name.equals("Colombia"))
					flag = "col.png";
				else if (name.equals("Cuba"))
					flag = "cub.png";
				else if (name.equals("Czechoslovakia"))
					flag = "cze.png";
				else if (name.equals("Dominican Republic"))
					flag = "dom.png";
				else if (name.equals("Estonia"))
					flag = "est.png";
				else if (name.equals("Ethiopia"))
					flag = "eth.png";
				else if (name.equals("Finland"))
					flag = "fin.png";
				else if (name.equals("France"))
					flag = "fra.png";
				else if (name.equals("West Germany"))
					flag = "frg.png";
				else if (name.equals("Great Britain"))
					flag = "gbr.png";
				else if (name.equals("Germany"))
					flag = "ger.png";
				else if (name.equals("East Germany"))
					flag = "gdr.png";
				else if (name.equals("Greece"))
					flag = "gre.png";
				else if (name.equals("Hungary"))
					flag = "hun.png";
				else if (name.equals("Ireland"))
					flag = "irl.png";
				else if (name.equals("Italy"))
					flag = "ita.png";
				else if (name.equals("Jamaica"))
					flag = "jam.png";
				else if (name.equals("Kenya"))
					flag = "ken.png";
				else if (name.equals("Saudi Arabia"))
					flag = "ksa.png";
				else if (name.equals("Lithuania"))
					flag = "ltu.png";
				else if (name.equals("Mexico"))
					flag = "mex.png";
				else if (name.equals("Morocco"))
					flag = "mar.png";
				else if (name.equals("The Netherlands"))
					flag = "ned.png";
				else if (name.equals("Nigeria"))
					flag = "ngr.png";
				else if (name.equals("New Zealand"))
					flag = "nzl.png";
				else if (name.equals("Philippines"))
					flag = "phi.png";
				else if (name.equals("Poland"))
					flag = "pol.png";
				else if (name.equals("Portugal"))
					flag = "por.png";
				else if (name.equals("Russia"))
					flag = "rsa.png";
				else if (name.equals("Senegal"))
					flag = "sen.png";
				else if (name.equals("Sri Lanka"))
					flag = "sri.png";
				else if (name.equals("Switzerland"))
					flag = "sui.png";
				else if (name.equals("Sweden"))
					flag = "swe.png";
				else if (name.equals("Tanzania"))
					flag = "tan.png";
				else if (name.equals("Tunisia"))
					flag = "tun.png";
				else if (name.equals("Soviet Union"))
					flag = "urs.png";
				else if (name.equals("Zambia"))
					flag = "zam.png";
				else
					flag = "what.png";
				CountryMedals newCountry = new CountryMedals(d.country.get(0), loadImage(flag));
				if (d.medal == 0)
					newCountry.incrementGold();
				else if (d.medal == 1)
					newCountry.incrementSilver();
				else if (d.medal == 2)
					newCountry.incrementBronze();
				countryMedalsList.add(newCountry);
			}
		}
		
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
