
//import classes
package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fireball {

	// declare local vars
	double x, y;
	Image fball;
	ImageView ivfball;
	final int UP = 0, RIGHT = 90, DOWN = 180, LEFT = 270;
	
	//default constructor and xy pos input
	Fireball(double x, double y) {
		
		//init all local vars
		this.x = x;
		this.y = y;
		
		fball = new Image("file:Fireball.gif");
		ivfball = new ImageView(fball);
		
		ivfball.setX(x);
		ivfball.setY(y);
		
	}
	
	//create methods
	
	//return the imageview
	public ImageView getImage() {
		return ivfball;
	}
	
	//move the fireball and update iview
	public void move() {
		x+=6;
		ivfball.setX(x);
	}
	
	//return the xpos
	public double getX() {
		return x;
	}

}
