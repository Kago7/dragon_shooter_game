//import classes
package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DemonBoss {

	//declare local vars
	private ImageView iviewBoss;
	private Image imgBoss;
	private int xPos, yPos, width, height;
	private int dir;
	public final int UP=90, DOWN=270;
	
	
	//default constructor
	DemonBoss() {
		
		//init all local vars
		imgBoss = new Image("file:Boss.gif");
		iviewBoss = new ImageView(imgBoss);
		
		width = (int) imgBoss.getWidth();
		height = (int) imgBoss.getHeight();
		
		yPos =  0;
		dir = DOWN;
		
		xPos = 640 - width;
		
		iviewBoss.setX(xPos);
		iviewBoss.setY(yPos);
		
		
	}
	
	
	
	//create methods
	
	//move the boss
	public void move() {
		
		//move demon based on the direction 
		if (dir == UP) {
			
			yPos-=6;
			
			//check that demon not touching top of screen if so reverse direction
			if (yPos <= 6) {
				dir = DOWN;
			}
			
		} else if (dir == DOWN) {
			
			yPos += 6;
			
			//check that dragon not touching bottom of screen if so reverse the direction
			if (yPos > 480-height-6) {
				dir = UP;
			}
		}
		
		//update the imageview to new y
		iviewBoss.setY(yPos);
		
		
	}
	//return the imageview
	public ImageView getImage() {
		return iviewBoss;
	}
	//return the x pos
	public int getX() {
		return xPos;
	}
	
	//return the y pos
	public int getY() {
		return yPos;
	}
	
	
}
