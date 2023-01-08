package application;

//import classes



import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Baby {
	
	//declare local vars
	private Image imgBaby;	
	private ImageView iviewBaby;
	private int xPos, yPos, width, height, speed;
	private Random rand;
	
	//default constructor
	Baby() {
		
		//init all local vars
		imgBaby = new Image("file:Baby.gif");
		iviewBaby = new ImageView(imgBaby);

		width = (int) imgBaby.getWidth();
		height = (int) imgBaby.getHeight();
		
		rand = new Random();
		
		speed = rand.nextInt(5)+3;
		

		iviewBaby.setX(0);
		iviewBaby.setY(0);
		
		xPos = 0;
		yPos = 0;
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
	//move the baby based on random speed and update iview
	public void move() {
		xPos -= speed;
		iviewBaby.setX(xPos);
	}
	//return the imageview
	public ImageView getImage() {
		return iviewBaby;
	}
	//set location xy of baby and update the ivew
	public void setLocation(int x, int y) {
		
		//set local x and y to ones passed in
		xPos = x;
		yPos = y;
		
		//update image view x and y pos
		iviewBaby.setX(xPos);
		iviewBaby.setY(yPos);
	}

	
	
	
	
}
