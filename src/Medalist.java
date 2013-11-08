/**
 * Container for medalist data.
 * 
 * @author Dan Feng
 */
public class Medalist {

	public enum Medal {
		GOLD, SILVER, BRONZE;
	}
	
	int year;
	float score;
	String medalist;
	String country;
	Medal medal;
	
	public Medalist(int year, float score, String medalist, String country, Medal medal)
	{
		this.year = year;
		this.score = score;
		this.medalist = medalist;
		this.country = country;
		this.medal = medal;
	}
}
