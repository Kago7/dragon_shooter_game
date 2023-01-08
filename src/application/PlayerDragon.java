package application;

//import classes



import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerDragon {
	
	//declare local vars
	private ImageView iviewPlayer;
	private Image imgPlayer;
	private int xPos, yPos, width, height;
	private int dir;
	public final int UP=90, DOWN=270;
	
	
	//create constructor default
	PlayerDragon() {
		
		//init all local vars
		dir = xPos = yPos = 0;
		
		imgPlayer = new Image("file:Dragon.gif");
		iviewPlayer = new ImageView(imgPlayer);
		
		width = (int) imgPlayer.getWidth();
		height = (int) imgPlayer.getHeight();
		
		iviewPlayer.setX(xPos);
		iviewPlayer.setY(yPos);
	}
	
	//create methods
	
	//return the width
	public int getWidth() {
		return width;
	}
	
	//return the height
	public int getHeight() {
		return height;
	}
	
	//return the xpos
	public int getX() {
		return xPos;
	}
	
	//return the ypos
	public int getY() {
		return yPos;
	}
	
	//set the y pos
	public void setY(int y) { 
		yPos = y;
		
		
	}
	
	//move the player
	public void move() {
		
		//move dragon based on the direction 
		if (dir == UP) {
			
			//check that dragon not touching top of screen
			if (yPos > 4) {
				yPos-=4;
			}
			
		} else if (dir == DOWN) {
			
			//check that dragon not touching bottom of screen
			if (yPos < 480-height) {
				yPos+=4;
			}
		}
		
		//update the imageview to new y
		iviewPlayer.setY(yPos);
		
		
	}
	
	//return the imageview
	public ImageView getImage() {
		return iviewPlayer;
	}
	
	//update imageview xy to xpos and y pos
	public void setLocation() {
		iviewPlayer.setX(xPos);
		iviewPlayer.setY(yPos);
	}
	
	//return the dir
	public int getDirection() {
		return dir;
	}
	
	//set the direction of player
	public void setDirection(int dir) {
		this.dir = dir;
	}
	
	
}
