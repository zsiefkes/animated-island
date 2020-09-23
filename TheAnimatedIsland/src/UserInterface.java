import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class UserInterface extends Application {

	// set display sizes. width and height of window are determined by gridWidth, gridHeight and radius of object avatars (circles).
	private int radius = 10;
	private int gridWidth = 20, gridHeight = 10;
	private int width = gridWidth * 2 * radius + radius;
	private int btnHeight = 40;
	private int height = gridHeight * 2 * radius + radius + btnHeight;
	
	// declare island related fields
	private Island island;
	private ArrayList<Rabbit> rabbits;
	private ArrayList<Kiwi> kiwis;
	private ArrayList<Grass> grasses;
	private ArrayList<Water> waters;
	
	// declare javaFX objects 
	Button playPauseButton;
	boolean isPaused;
	VBox pane;
	Timeline timeline;
	
	// ----------------------------------------------------------------------------------------- //
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// create island
		island = new Island(gridWidth, gridHeight, radius * 2);
		island.genAnimals(10);
		island.genGrass(20);
		island.genWater(10);
		island.reportNumAnimals();

		// fetch animals and geographic features
		rabbits = island.getRabbits();
		kiwis = island.getKiwis();
		grasses = island.getGrasses();
		waters = island.getWaters();
		
		// create a group to hold the animals and geographic features
		Group islandObjects = new Group();
		for (Rabbit r : rabbits) {
			islandObjects.getChildren().add(r);
		}
		for (Kiwi k : kiwis) {
			islandObjects.getChildren().add(k);
		}
		for (Grass g : grasses) {
			islandObjects.getChildren().add(g);
		}
		for (Water w : waters) {
			islandObjects.getChildren().add(w);
		}

		// define keyframe action
		KeyFrame frame = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// each new frame should update the island.
				island.updateIsland();

				// update positions and remove nodes from group if they have died
				for (Rabbit r : rabbits) {
					// what if the rabbit dies? give it a check!
					if (r.isDead()) {
						islandObjects.getChildren().remove(r);
					} else {
						// update the position
						r.setTranslateX(r.getPosCenterX());
						r.setTranslateX(r.getPosCenterY());
					}
					// print rabbit info
					System.out.println(r.toString());
				}
				for (Kiwi k : kiwis) {
					if (k.isDead()) {
						islandObjects.getChildren().remove(k);
					} else {
						// update the position
						k.setTranslateX(k.getPosCenterX());
						k.setTranslateX(k.getPosCenterY());
					}
					System.out.println(k.toString());
				}
				for (Grass g : grasses) {
					if (g.isDead()) {
						islandObjects.getChildren().remove(g);
					}
				}
				island.reportNumAnimals();
			}
			
		});
		
		// instantiate timeline and add keyframe.
//		final Timeline timeline = new Timeline();
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(frame);
//		timeline.play();
//		isPaused = false;
		isPaused = true;
		
		// create play/pause button to control island animation
		playPauseButton = new Button();
		playPauseButton.setText("Play");
		playPauseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (isPaused) {
					// restart the timeline play... how we do this?
					timeline.play();
					playPauseButton.setText("Pause");
					isPaused = false;
				} else {
					timeline.pause();
					playPauseButton.setText("Play");
					isPaused = true;
				}
			}
		});
		
		// create a vbox to store the animal group and also the um. play/pause button
		pane = new VBox();
		pane.getChildren().add(islandObjects);
		pane.getChildren().add(playPauseButton);
		pane.setAlignment(Pos.BOTTOM_CENTER);
		
		// create scene on islandObjects group
		Scene scene = new Scene(pane, width, height);
		
		primaryStage.setTitle("The Animated Island");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
