import processing.core.PImage;


public class CountryMedals {
	private int gCount, sCount, bCount;
	public int x, y;
	private String country;
	private PImage flag;
	
	public CountryMedals(String country, PImage flag, int x, int y) {
		this.country = country;
		this.flag = flag;
		this.x = x;
		this.y = y;
		gCount = 0;
		sCount = 0;
		bCount = 0;
	}
	
	public void incrementGold() {
		gCount++;
	}
	
	public void incrementSilver() {
		sCount++;
	}
	
	public void incrementBronze() {
		bCount++;
	}
	
	public String getCountry() {
		return country;
	}
	
	public PImage getFlag() {
		return flag;
	}
	
	public int getGold() {
		return gCount;
	}
	
	public int getSilver() {
		return sCount;
	}
	
	public int getBronze() {
		return bCount;
	}
	
	public int getHeight() {
		return flag.height;
	}
	
	public int getWidth() {
		return flag.width;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setFlag(PImage flag) {
		this.flag = flag;
	}
	
	public void setGold(int count) {
		gCount = count;
	}
	
	public void setSilver(int count) {
		sCount = count;
	}
	
	public void setBronze(int count) {
		bCount = count;
	}
	
}
