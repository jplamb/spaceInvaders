package Practice;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
	private final int ICRAFT_X = 200; //Updated v1.1
	private final int ICRAFT_Y = 500; //Updated v1.1
	public static int B_WIDTH; //Update v1.2
	public static int B_HEIGHT; //Update v1.2
	private Timer timer;
	private Craft craft;
	private ArrayList<Alien> aliens;
	private boolean ingame;
	private final int DELAY = 15;
	private final int NO_OF_LEVELS = 5; //Added v1.1
	private int CURRENT_LEVEL = 1; //Added v1.1
	private final int STARTING_ALIENS = 30; //Added v1.1
	private int SCORE = 0; //Added v1.1
	private int HIGHSCORE = 0;
	
	/* Declare board object; updated v1.2 */
	public Board(int width, int height){
		
		B_WIDTH = width; //Added v1.2
		B_HEIGHT = height; //Added v1.2
		initBoard();
	}

	/* Initiate board object and run start up processes */
	private void initBoard(){
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		ingame = true;
		
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		
		craft = new Craft(ICRAFT_X,ICRAFT_Y);
		
		initAliens(STARTING_ALIENS);

		timer = new Timer(DELAY,this);
		timer.start();
		
	}
	
	/* Alien creation, startup processes */
	public void initAliens(int quantity){
		int[][] pos = alienInitPos(quantity); //Generate random starting positions; added v1.1
		aliens = new ArrayList<>();
		
		for(int[] p : pos) {
			aliens.add(new Alien(p[0],p[1]));
		}
	}
	
	/* Determine if game is still running and which objects to draw */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if (ingame){
			drawObjects(g);
		} else{
			
			drawGameOver(g);
		}
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	/* Draw player, missiles, and aliens */
	private void drawObjects(Graphics g){
		if(craft.isVisible()){
			g.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
		}
		
		ArrayList<Missile> ms = craft.getMissiles();
		
		for (Missile m : ms){
			if(m.isVisible()){
				g.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
		}
		
		for(Alien a : aliens){
			if (a.isVisible()){
				g.drawImage(a.getImage(), a.getX(),  a.getY(), this);
			}
		}
		
		g.setColor(Color.WHITE);;
		g.drawString("Aliens left: " + aliens.size(), 5, 15);
		g.drawString("Level: " + CURRENT_LEVEL, B_WIDTH - 50, 15); //Added v1.1
		g.drawString("Score: " + SCORE, B_WIDTH / 2 - 100, 15); //Added v1.1
		
		/* Calculate high score */
		if(SCORE > HIGHSCORE){
			HIGHSCORE = SCORE;
		}
		g.drawString("Highscore: " + HIGHSCORE, B_WIDTH / 2 + 100, 15); //Added v1.1
	}
	
	/* Display game over messages */
	public void drawGameOver(Graphics g){
		/* Display game over message */
		String msg = "Game Over";
		
		Font smallGO = new Font("Helvetica", Font.BOLD, 18);
		FontMetrics fm = getFontMetrics(smallGO);
		
		g.setColor(Color.white);
		g.setFont(smallGO);
		g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
		
		/* Display score when game end; added v1.1 */
		String scoreMSG = "Score: " + SCORE;
		Font scoreGO = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics scoreFM = getFontMetrics(scoreGO);
		
		g.setColor(Color.white);;
		g.setFont(scoreGO);
		g.drawString(scoreMSG, (B_WIDTH - scoreFM.stringWidth(scoreMSG)) / 2, B_HEIGHT / 2 + 20);
		
		/* Draw highscore; added v1.1*/
		String highscoreMsg;
		if(SCORE == HIGHSCORE){
			 highscoreMsg = "New highscore!!  " + HIGHSCORE;
		}
		else{
			 highscoreMsg = "Highscore: " + HIGHSCORE;
		}
		Font highscoreGO = new Font("Helvetica",Font.BOLD, 16);
		FontMetrics highscoreFM = getFontMetrics(highscoreGO);
		g.drawString(highscoreMsg, (B_WIDTH - highscoreFM.stringWidth(highscoreMsg)) / 2, B_HEIGHT / 2 - 80);
		
		while (!ingame){
			//Wait for keypress to restart game
			// boolean continueKey (KeyEvent e);
			if(keyPressed.getKeyCode() == KeyEvent.VK_SPACE){
			ingame = true;
			}
		}
		
		
	}
	
	/* Redraw screen every DELAY */
	@Override
	public void actionPerformed(ActionEvent e){
		
		inGame();
		
		updateCraft();
		updateMissiles();
		updateAliens();
		
		checkCollisions();
		
		repaint();
	}
	
	/* Stop running program if game ends */
	private void inGame(){
		if(!ingame){
			timer.stop();
		}
	}
	
	/* Move craft */
	public void updateCraft(){
		if(craft.isVisible()){
			craft.move();
		}
	}
	
	/* Move missiles */
	public void updateMissiles(){
		ArrayList<Missile> ms = craft.getMissiles();
		
		for (int i = 0; i < ms.size(); i++){
			Missile m = (Missile) ms.get(i);
			
			if(m.isVisible()){
				m.move();
			} else{
				ms.remove(i);
			}
		}
	}
	
	/* Update aliens */
	private void updateAliens(){
		if(aliens.isEmpty()){
			/* Go to next level upon alien destruction; added v1.1 */
			if(CURRENT_LEVEL < NO_OF_LEVELS){ 
				CURRENT_LEVEL += 1;
				initAliens(STARTING_ALIENS * CURRENT_LEVEL);
			}
			else{
			ingame = false;
			return;
			}
		}
		
		for(int i = 0; i < aliens.size(); i++){
			Alien a = aliens.get(i);
			if(a.isVisible()){
				a.move(craft.getY()); //Changed move method; v1.1
				
			}else{
				aliens.remove(i);
			}
		}
	}
	
	/* Check for collisions */
	public void checkCollisions(){
		Rectangle r3 = craft.getBounds();
		
		for(Alien alien : aliens){
			Rectangle r2 = alien.getBounds();
			/* Check for alien and craft collision */
			if(r3.intersects(r2)){
				craft.setVisible(false);
				alien.setVisible(false);
				ingame = false;
			}
		}
		
		ArrayList<Missile> ms = craft.getMissiles();
		
		for (Missile m : ms){
			Rectangle r1 = m.getBounds();
			
			for(Alien alien : aliens){
				Rectangle r2 = alien.getBounds();
				/* Check for alien and missile collision */
				if(r1.intersects(r2)){
					m.setVisible(false);
					alien.setVisible(false);
					SCORE += 10; //Increment score if alien destroyed; added v1.1
				}
			}
		}
	}
	
	private class TAdapter extends KeyAdapter{
		
		@Override
		public void keyReleased(KeyEvent e){
			craft.keyReleased(e);;
		}
		
		@Override
		public void keyPressed(KeyEvent e){
			craft.keyPressed(e);
		}
	}
	
	/* Generate random starting positions for aliens; added v1.1 */
	private int[][] alienInitPos(int count){
		Random posGen = new Random();
		int[][] pos = new int[count][2];
		
		for(int i = 0; i < count; i++){
			pos[i][0] = posGen.nextInt(B_WIDTH-B_WIDTH/5) + B_WIDTH/5; //Safety zone so aliens start upstream of player
			pos[i][1] = posGen.nextInt(B_HEIGHT);
		}
		
		return pos;
	}
}