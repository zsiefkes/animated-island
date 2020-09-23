import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserInterface extends Application {

	// set display sizes. width and height of window are determined by gridWidth, gridHeight and radius of object avatars (circles).
	private int radius = 10;
	private int gridWidth = 20, gridHeight = 10;
	private int width = gridWidth * 2 * radius + radius;
	private int height = gridHeight * 2 * radius + radius;
	
	// declare island related fields
	private Island island;
	private ArrayList<Rabbit> rabbits;
	private ArrayList<Kiwi> kiwis;
	private ArrayList<Grass> grasses;
	private ArrayList<Water> waters;
	
	// ----------------------------------------------------------------------------------------- //
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// create island
		island = new Island(gridWidth, gridHeight, radius * 2);
		island.genAnimals(10);
		island.genGrass(20);
		island.genWater(10);
		island.reportNumAnimals();

		rabbits = island.getRabbits();
		kiwis = island.getKiwis();
		grasses = island.getGrasses();
		waters = island.getWaters();
		
		// create a group
		Group root = new Group();
		
		// add animals and geographic features to the group
		for (Rabbit r : rabbits) {
			root.getChildren().add(r);
		}
		for (Kiwi k : kiwis) {
			root.getChildren().add(k);
		}
		for (Grass g : grasses) {
			root.getChildren().add(g);
		}
		for (Water w : waters) {
			root.getChildren().add(w);
		}
		
		// create scene on root group
		Scene scene = new Scene(root, width, height);
	
		KeyFrame frame = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// each new frame should update the island.
				island.updateIsland();

				// update positions and remove nodes from group if they have died
				for (Rabbit r : rabbits) {
					// what if the rabbit dies? give it a check!
					if (r.isDead()) {
						root.getChildren().remove(r);
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
						root.getChildren().remove(k);
					} else {
						// update the position
						k.setTranslateX(k.getPosCenterX());
						k.setTranslateX(k.getPosCenterY());
					}
					System.out.println(k.toString());
				}
				for (Grass g : grasses) {
					if (g.isDead()) {
						root.getChildren().remove(g);
					}
				}
				island.reportNumAnimals();
			}
		});
		
		// create timeline and add keyframe.
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
		
		primaryStage.setTitle("The Animated Island");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
