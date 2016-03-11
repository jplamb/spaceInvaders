package Practice;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class Sprite {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean vis;
	protected Image image;
	
	/* Sprite object declaration */
	public Sprite(int x, int y){
		this.x = x;
		this.y = y;
		vis = true;
	}
	
	/* Load object icon/image */
	protected void loadImage(String imageName){
		ImageIcon ii = new ImageIcon(imageName);
		image = ii.getImage();
	}
	
	/* Return image dimensions */
	protected void getImageDimensions(){
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	/* Return image object */
	public Image getImage(){
		return image;
	}
	
	/* Return sprite's X location */
	public int getX(){
		return x;
	}
	
	/* Return sprite's Y location */
	public int getY(){
		return y;
	}
	
	/* Get sprite's visibility property */
	public boolean isVisible(){
		return vis;
	}
	
	/* Set sprite's visibility property */
	public void setVisible(Boolean visible){
		vis = visible;
	}
	
	/* Get sprite's hitbox */
	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);
	}
}
