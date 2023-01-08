/* NAME: Karl Gonsalves
 * DATE: OCT. 29, 2021
 * COURSE: ICS4U1
 * PROGRAM DESCRIPTION: Initially the background, score label, player, and boss are on the screen. 
 * 	the boss moves up and down, and the player can be moved up or down with arrow keys. the demon boss 
 * 	starts to spawn baby and demons which moves towards the left at random speeds. 
 * 	You can shoot fireballs-unlimited as long as you repress space-to destroy these and face point increase 
 * 	for hitting demon and point decrease for hitting baby. Colliding with them with the player itself 
 * 	results in contrary point changes as aforementioned. The score is updated in the above label for player to see.
 *  Finally, when the score reaches 1500+ or less than 0 the game ends with customized alerts for the win and
 *  lose conditions. In the background, we have 5 classes that control all the objects: player, fireball, baby, 
 *  demons, demon boss. There is 1 main class which handles the logic that makes the game. 
 *  This is mainly the score label-changes the score on screen, the spawn timer-rolls dice for chance of 
 *  spawning monsters by demon boss, the win and lose conditions-score less than 0 and greater equal to 1500,and
 *  the animation timer-moves all the objects and checks for collisions and out of bounds.   
 */

//import classes
package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EvilClutchesMain extends Application {

	// declare vars global
	public int score = 0;

	// declare vars local
	private ImageView background;
	private Label lblScore;
	private boolean goUp = false, goDown = false, goFire = false;
	private AnimationTimer at;
	private ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
	private ArrayList<Demon> demons = new ArrayList<Demon>();
	private ArrayList<Baby> babies = new ArrayList<Baby>();
	private int fireballCount = -1, demonCount = -1, babyCount = -1;
	private Timeline spawntimer;
	private KeyFrame kfSpawner;
	private Random rnd;

	public void start(Stage primaryStage) {
		try {

			// setup scene and background image disable resize
			background = new ImageView(new Image("file:Background.bmp"));
			Pane root = new Pane();
			Scene scene = new Scene(root, 640, 480);
			primaryStage.setResizable(false);

			// setup label for score and position in center no matter how many digits score set font and color and weight
			lblScore = new Label("SCORE: 0");
			lblScore.setFont(Font.font("Tahoma",FontWeight.BOLD ,36));
			lblScore.setTextFill(Color.RED);
			lblScore.setPrefWidth(250);
			lblScore.setLayoutY(0);
			lblScore.setLayoutX((scene.getWidth() / 2) - (lblScore.getPrefWidth() / 2));
			lblScore.setAlignment(Pos.CENTER);

			// create a player object and boss object
			PlayerDragon player = new PlayerDragon();
			DemonBoss boss = new DemonBoss();

			// create random object
			rnd = new Random();

			// setup keyboard events for key pressed and released up and down arrow keys and
			// set up key typed space key for fireballs
			// arrow keys control booleans which control animation timer for smooth movement
			// space triggers a boolean so you cant hold and spam fireballs.
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) {

					if (e.getCode() == KeyCode.UP) {
						goUp = true;
					} else if (e.getCode() == KeyCode.DOWN) {
						goDown = true;
					} else if (e.getCode() == KeyCode.SPACE) {

						if (goFire == false) {

							goFire = true;

							// add fireballs to arraylist and scene with a count storing number of objects
							fireballs.add(new Fireball(player.getX() + player.getWidth()/2, player.getY()));
							fireballCount++;
							root.getChildren().add(fireballs.get(fireballCount).getImage());

						}
					}
				}
			});

			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) {

					if (e.getCode() == KeyCode.UP) {
						goUp = false;
					} else if (e.getCode() == KeyCode.DOWN) {
						goDown = false;
					} else if (e.getCode() == KeyCode.SPACE) {
						goFire = false;
					}
				}
			});

			// setup keyframe for baby and demon spawning
			// //add monsters to arraylist with a count storing number of objects
			// respectively
			kfSpawner = new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					if (rnd.nextInt(50) == 1) { // gen demon 1/50 % chance
						demons.add(new Demon(boss.getX(), boss.getY()));
						demonCount += 1;
						root.getChildren().add(demons.get(demonCount).getImage());

					}

					if (rnd.nextInt(100) == 1) { // gen baby 1/100 % chance
						babies.add(new Baby());
						babyCount += 1;
						babies.get(babyCount).setLocation(boss.getX(), boss.getY());
						root.getChildren().add(babies.get(babyCount).getImage());
					}

				}
			});

			// setup and start timeline timer
			spawntimer = new Timeline(kfSpawner);
			spawntimer.setCycleCount(Timeline.INDEFINITE);
			spawntimer.play();

			// setup animation timer
			at = new AnimationTimer() {
				public void handle(long val) {

					// update the score label
					lblScore.setText("SCORE: " + score);

					// handle movement keyboard of player dragon and set direction based on key
					// pressed
					if (goUp == true) {
						player.setDirection(player.UP);
						player.move();
					} else if (goDown == true) {
						player.setDirection(player.DOWN);
						player.move();
					}

					// move fireballs if existing in arraylist and remove from list if out of bounds
					if (fireballs.size() > 0) {

						for (int a = 0; a < fireballs.size(); a++) {
							fireballs.get(a).move();

							if (fireballs.get(a).getX() > 640) {
								root.getChildren().remove(fireballs.get(a).getImage());
								fireballs.remove(a);
								fireballCount--;
							}
						}
					}

					// constantly move demon boss up and down screen
					boss.move();

					// move all the demons if existing and remove from list if out of bounds
					if (demons.size() > 0) {

						for (int a = 0; a < demons.size(); a++) {
							demons.get(a).move();

							if (demons.get(a).getX() < 0 - demons.get(a).getWidth()) {
								root.getChildren().remove(demons.get(a).getImage());
								demons.remove(a);
								demonCount--;
							}
						}
					}

					// move all the babies if existing and remove from list if out of bounds
					if (babies.size() > 0) {

						for (int a = 0; a < babies.size(); a++) {
							babies.get(a).move();

							if (babies.get(a).getX() < 0 - babies.get(a).getWidth()) {
								root.getChildren().remove(babies.get(a).getImage());
								babies.remove(a);
								babyCount--;
							}
						}
					}

					/*****************************************COLLISIONS**************************************************/

					// check for fireball collisions with babies and demons
					for (int a = 0; a < fireballs.size(); a++) { // check each fireball intersects for all demons
																	// and
																	// babies

						for (int b = 0; b < demons.size(); b++) { // check intersect for all demons
							if (!(a >= fireballs.size())) {
								if (demons.get(b).getImage().getBoundsInParent()
										.intersects(fireballs.get(a).getImage().getBoundsInParent())) {

									// increase score by 100 and remove both colliding objects from scene and array
									// and decrease counts
									score += 100;
									root.getChildren().remove(demons.get(b).getImage());
									root.getChildren().remove(fireballs.get(a).getImage());
									demons.remove(b);
									fireballs.remove(a);
									demonCount--;
									fireballCount--;

								}
							}
						}

						for (int b = 0; b < babies.size(); b++) { // check intersect for all babies
							if (!(a >= fireballs.size())) {
								if (babies.get(b).getImage().getBoundsInParent()
										.intersects(fireballs.get(a).getImage().getBoundsInParent())) {

									// decrease score by 100 and remove both colliding objects from scene and array
									// and decrease counts
									score -= 100;
									root.getChildren().remove(babies.get(b).getImage());
									root.getChildren().remove(fireballs.get(a).getImage());
									babies.remove(b);
									fireballs.remove(a);
									babyCount--;
									fireballCount--;

								}
							}
						}

					}

					// check for player collisions with babies and demons
					// check each player intersects for all demons and babies

					for (int a = 0; a < demons.size(); a++) { // check intersect for all demons
						if (demons.get(a).getImage().getBoundsInParent()
								.intersects(player.getImage().getBoundsInParent())) {

							// decrease score by 50 and remove monster colliding objects from scene and
							// array and decrease counts
							score -= 50;
							root.getChildren().remove(demons.get(a).getImage());

							demons.remove(a);

							demonCount--;

						}
					}

					for (int a = 0; a < babies.size(); a++) { // check intersect for all babies
						if (babies.get(a).getImage().getBoundsInParent()
								.intersects(player.getImage().getBoundsInParent())) {

							// increase score by 150 and remove monster colliding objects from scene and
							// array and decrease counts
							score += 150;
							root.getChildren().remove(babies.get(a).getImage());

							babies.remove(a);

							babyCount--;

						}
					}

					/*********************************************COLLISIONS********************************************/

					// create thread for alerts
					// check if points equals a win or loss condition
					// create alerts for score
					Platform.runLater(new Runnable() {
						public void run() {

							// if either condiitons are met, update score, setup alerts, stop timers, show
							// alert, and exit game.
							if (score >= 1500) {
								
								Alert alertwin = new Alert(AlertType.NONE, "");
								alertwin.getButtonTypes().add(ButtonType.OK);
								alertwin.setHeaderText(null);
								alertwin.setTitle("Evil Clutches");
								alertwin.setGraphic(new ImageView(new Image("file:Baby_alert.gif")));
								alertwin.setContentText("You saved your babies!\nYou Win!\nYour score is: " + score);
								lblScore.setText("SCORE: " + score);
								at.stop();
								spawntimer.stop();

								alertwin.showAndWait();

								System.exit(0);

							} else if (score < 0) {
								
								Alert alertlose = new Alert(AlertType.NONE, "");
								alertlose.getButtonTypes().add(ButtonType.OK);
								alertlose.setHeaderText(null);
								alertlose.setTitle("Evil Clutches");
								alertlose.setGraphic(new ImageView(new Image("file:Demon_alert.gif")));
								alertlose.setContentText("Game Over!\nTry Again.");
								lblScore.setText("SCORE: " + score);
								at.stop();
								spawntimer.stop();

								alertlose.showAndWait();

								System.exit(0);

							}

						}
					});
				}

			};

			// start animation timer
			at.start();

			// get the children
			root.getChildren().addAll(background, lblScore, player.getImage(), boss.getImage());

			// show the scene and set title
			primaryStage.setTitle("Evil Clutches");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
