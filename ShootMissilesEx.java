package Practice;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class ShootMissilesEx extends JFrame{
	
	public ShootMissilesEx(){
		initUI();
	}
	
	private void initUI(){
		add(new Board());
		
		setSize(400,300);
		setResizable(false);
		
		setTitle("Shooting missiles");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				ShootMissilesEx ex = new ShootMissilesEx();
				ex.setVisible(true);
			}
		});
	}

}
