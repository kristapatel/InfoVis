import processing.core.*;

public class Menu {
	PApplet papa; 
	int startX, startY;
	
	public Menu(int startX, int startY, PApplet papa){
		this.startX = startX;
		this.startY = startY;
		this.papa = papa;
	}
	
	public void draw(){
		papa.noStroke();
		papa.rect(startX, startY, 20, 20);
	}
}
