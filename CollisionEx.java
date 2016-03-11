package Practice;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class CollisionEx extends JFrame {
	
	/* Set game window size; added v1.2 */
	private static final int WINDOW_WIDTH = 1920;
	private static final int WINDOW_HEIGHT = 1080;
	//private static final int TOP_PANE_HEIGHT = 200;
	
	public CollisionEx(){
		
		initUI();
	}
	
	/* Initialize user interface */
	private void initUI(){
		add(new Board(WINDOW_WIDTH, WINDOW_HEIGHT)); //Updated v1.2
		
		setResizable(false);
		pack();
		
		setTitle("Collision");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				CollisionEx ex = new CollisionEx();
				ex.setVisible(true);
			}
		});
	}

}
