
//import classes
package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//extends DemonBoss to easily get the x and y pos from demon boss to make same spawn location of boss for demon
public class Demon extends DemonBoss {

	// declare local vars
	private Image imgDemon;
	private ImageView iviewDemon;
	private double xPos, yPos, width, height, speed;
	private Random rnd;

	// constructor with x and y
	Demon(double x, double y) {
		
		//init all local vars
		xPos = x;
		yPos = y;

		imgDemon = new Image("file:Demon.gif");
		iviewDemon = new ImageView(imgDemon);

		width = imgDemon.getWidth();
		height = imgDemon.getHeight();
		
		rnd = new Random();
		
		speed = rnd.nextInt(5)+3;
		

		iviewDemon.setX(super.getX());
		iviewDemon.setY(super.getY());

	}

	// create methods
	
	//return the width
	public double getWidth() {
		return width;
	}

	//return the height
	public double getHeight() {
		return height;
	}
	
	//return the xpos
	public int getX() {
		return (int) xPos;
	}
	//return the ypos
	public int getY() {
		return (int) yPos;
	}
	//move the demon based on random speed and update iview x and y.
	public void move() {
		xPos -=speed;
		iviewDemon.setX(xPos);
		iviewDemon.setY(yPos);
	}
	//return the imageview
	public ImageView getImage() {
		return iviewDemon;
	}

}
