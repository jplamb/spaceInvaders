package Practice;

public class Missile extends Sprite{
	private final int MISSILE_SPEED = 10;
	private final int MISSILE_DURATION = Board.B_WIDTH / 3; //Added v1.1
	private int startLoc;
	
	public Missile(int x, int y){
		super(x,y);
		startLoc = x; //added v1.1
		initMissile();
	}
	
	/* Missile set up */
	private void initMissile(){
		loadImage("missile.png");
		getImageDimensions();
	}
	
	/* Missile move */
	public void move(){
		x += MISSILE_SPEED;
		/* Missiles expire after a period of time */
		if (x > MISSILE_DURATION + startLoc){
			vis = false;
		}
	}

}
