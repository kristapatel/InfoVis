/**
 * Container for medalist data.
 * 
 * @author Dan Feng
 */
public class Medalist {

	public static enum Medal {
		GOLD, SILVER, BRONZE;
	}
	
	int year;
	float score;
	String athlete;
	String country;
	Medal medal;
	
	public Medalist(int year, float score, String athlete, String country, Medal medal)
	{
		this.year = year;
		this.score = score;
		this.athlete = athlete;
		this.country = country;
		this.medal = medal;
	}
}
