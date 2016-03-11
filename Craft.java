package Practice;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Craft extends Sprite{
	
	private int dx;
	private int dy;
	private long lastFire = 60;
	private ArrayList<Missile> missiles;
	private int MISSILE_DELAY = 150; //Added v1.1
	private int CRAFT_SPEED = 3; //Added v1.1

	/* Craft object declaration */
	public Craft(int x, int y){
		super(x,y);
		
		initCraft();
	}
	
	/* Craft starting conditions */
	private void initCraft(){
		missiles = new ArrayList<>();
		loadImage("craft.png");
		getImageDimensions();
	}
	
	/* Craft movement */
	public void move(){
		x += dx;
		y += dy;
		
		if(x < 1){
			x = 1;
		}
		
		if(y < 1){
			y = 1;
		}
	}
	
	public ArrayList getMissiles(){
		return missiles;
	}
	
	private long timeDiff(long time){
		return System.currentTimeMillis() - time;
	}
	
	/* Determine keystroke */
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		/* Fire missile, and delay firing */
		if(key == KeyEvent.VK_SPACE && timeDiff(lastFire) > MISSILE_DELAY){
			
			fire();
			lastFire = System.currentTimeMillis();
		}
		
		if(key == KeyEvent.VK_LEFT)
		{
			dx = -CRAFT_SPEED;
		}
		
		if(key == KeyEvent.VK_RIGHT){
			dx = CRAFT_SPEED;
		}
		
		if(key == KeyEvent.VK_UP){
			dy = -CRAFT_SPEED;
		}
		
		if(key == KeyEvent.VK_DOWN){
			dy = CRAFT_SPEED;
		}
	}
	
	/* Add missile in front of player */
	public void fire(){
		missiles.add(new Missile(x + width, y + height/2));
	}
	
	/* On key release, stop moving */
	public void keyReleased (KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT){
			dx = 0;
		}
		
		if (key == KeyEvent.VK_RIGHT){
			dx = 0;
		}
		
		if(key == KeyEvent.VK_UP){
			dy = 0;
		}
		
		if(key == KeyEvent.VK_DOWN){
			dy = 0;
		}
	}

}
