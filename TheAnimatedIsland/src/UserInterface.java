import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class UserInterface extends Application {

	// set display sizes. width and height of window are determined by gridWidth, gridHeight and gridSize (length of square grid tiles)
	private int gridSize = 20;
	private int gridWidth = 30, gridHeight = 15;
	private int width = gridWidth * gridSize;
	private int height = gridHeight * gridSize;
	private int btnHeight = 40; // additional height to add to bottom of window for pause/play button
	
	// declare island related fields
	private Island island;
	private ArrayList<Rabbit> rabbits;
	private ArrayList<Kiwi> kiwis;
	private ArrayList<Grass> grasses;
	private ArrayList<Water> waters;
	
	// declare javaFX objects 
	Button playPauseButton;
	boolean isPaused;
	VBox layout;
	Timeline timeline;
	Pane islandObjects;
	Scene scene;
	
	// ----------------------------------------------------------------------------------------- //
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// create island
		island = new Island(gridWidth, gridHeight, gridSize);
		island.genAnimals(20);
		island.genGrass(40);
		island.genWater(20);
		island.reportNumAnimals();

		// fetch animals and geographic features
		rabbits = island.getRabbits();
		kiwis = island.getKiwis();
		grasses = island.getGrasses();
		waters = island.getWaters();
		
		// create a pane to hold the animals and geographic features
		islandObjects = new Pane();
		islandObjects.setMinSize(width, height);
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

		// set initial positions
		positionGeographicalFeatures();
		updateIslandObjects();
		
		// define keyframe action
		KeyFrame frame = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// each new frame should update the island.
				island.updateIsland();

				// update positions and remove nodes from group if they have died
				updateIslandObjects();
			}
			
		});
		
		// instantiate timeline and add keyframe.
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		isPaused = true;
		
		// create play/pause button to control island animation
		playPauseButton = new Button();
		playPauseButton.setText("Play");
		playPauseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (isPaused) {
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
		layout = new VBox();
		layout.getChildren().add(islandObjects);
		layout.getChildren().add(playPauseButton);
		layout.setAlignment(Pos.BOTTOM_CENTER);
		
		// create scene on islandObjects group
		scene = new Scene(layout, width, height + btnHeight);
		
		primaryStage.setTitle("The Animated Island");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void updateIslandObjects() {
		for (Rabbit r : rabbits) {
			// check if rabbit has died
			if (r.isDead()) {
				islandObjects.getChildren().remove(r);
			} else {
				// update the position
				r.setX(r.getPosX());
				r.setY(r.getPosY());
			}
			// print rabbit info
			System.out.println(r.toString());
		}
		for (Kiwi k : kiwis) {
			if (k.isDead()) {
				islandObjects.getChildren().remove(k);
			} else {
				// update the position
				k.setX(k.getPosX());
				k.setY(k.getPosY());
			}
			System.out.println(k.toString());
		}
		for (Grass g : grasses) {
			// check if plant is dead and remove if so
			if (g.isDead()) {
				islandObjects.getChildren().remove(g);
			}
		}
		island.reportNumAnimals();
	}
	
	private void positionGeographicalFeatures() {
		for (Grass g : grasses) {
			// set position
			g.setX(g.getPosX());
			g.setY(g.getPosY());
		}
		for (Water w : waters) {
			// set position
			w.setX(w.getPosX());
			w.setY(w.getPosY());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
