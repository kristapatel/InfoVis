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
	
	Glyph graphrame = new Glyph(100, 100, goldDots, silverDots, bronzeDots, this);
	
	public static void main(String args[])
	{
		PApplet.main(new String[] {"Olympia"});
	}

	public void setup()
	{
		goldDots.add(new Dot(200, 640, null, this));
		
		size(1280, 800);
		background(255);
		
		graphrame.draw();
	}
	
	public void draw()
	{
		goldDots.get(0).draw();
	}
	
	public void mouseMoved()
	{
		goldDots.get(0).highlight();
	}
}
