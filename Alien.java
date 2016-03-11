package Practice;

import java.util.Random;

public class Alien extends Sprite{
	
	private Random ranGen; //Added v1.1
	private final int APPROACH_SPEED = 5; //Expressed as frequency (# of times per 100 cycles)
	private int MOVE_SPEED = 5; //Added v1.1
	
	public Alien(int x, int y){
		super(x, y);
		
		initAlien();
	}
	
	private void initAlien(){
		loadImage("alien.png");
		getImageDimensions();
	}
	
	/* Move aliens */
	public void move(int shipYpos){ //Changed method v1.1
		if(x < 0){
			x = Board.B_WIDTH;
		}
		
		Random ranGen = new Random();
		
		/* Added 'wiggle' effect...aliens appear to shimmer on their rickety ships; v1.1 */
		if( ranGen.nextInt(9) == 8){
			y -= 1;
		}
		
		if( ranGen.nextInt(9) == 7){
			y += 1;
		}
		
		/* Aliens move closer to the player; added v1.1 */
		if(ranGen.nextInt(100 / APPROACH_SPEED) == 9){
			if(y > shipYpos){
				y -= MOVE_SPEED; //updated v1.1
			}
			else{
				y += MOVE_SPEED; //updated v1.1
			}
		}
		
		x -= 1;
	}

}
