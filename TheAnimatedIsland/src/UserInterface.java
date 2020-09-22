import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class UserInterface extends Application {

	// so, instead of user interface creating a bunch of circles, instead it's going to interface with the island class which holds an arraylist of rabbits???
	
	// for now we can ignore all the rest of the functionality, it shooould work and the only thing we should really need to change in the end is the drawworld method. as in don't run it lol, instead update a frame.
	// bunch of logic to write in this here application subclass, though. to draw that shit.
	// need to:
	// - decide on a height and width for the grid
	// - radius will be circles will be ?? half of that
	// - each rabbit will have its own velocity... don't need to store the arraylist here, can just grab from the rabbits
	// since the rabbits are stored on the island in a arraylist, maybe we just use a um getter function from here to access them? or do we want to send them from somewhere within island when we invoke
	// nope nope nope this is the starting point.... the main method here will need to instantiate the island, create the rabbits, populate with grass, and then launch the UI
	// grab rabbits from UI
	
	// so, i think the updateworld logic should now live (or be called) by the keyframe handler?
	
	// what if I bring some logic in here from the circle class and get rid of what I don't need and adjust. that might work
	
	// ----------------------------- copied from circle animation --------------------------------- //
	// window width and height
	// this should maybe depend on the island's width and height...
	// we should set the radius of the circles, first
	int radius = 10;
	// width and height could be number of ummmm grid squares in each direction, multipled by two times the radius :)
	int gridWidth = 40, gridHeight = 30;
	int width = gridWidth * 2 * radius;
	int height = gridHeight * 2 * radius;
	
	// initial default velocities. not applicable..... ??
//	private static float dx = -3f, dy = -3f;

	// fields for storing velocities
//	private static ArrayList<Float> dxArr = new ArrayList<Float>();
//	private static ArrayList<Float> dyArr = new ArrayList<Float>();

	// want to add multiple circles and store them in an arraylist
//	private static int numCircles = 5;
//	private static ArrayList<Circle> circles = new ArrayList<Circle>();
	
	// instead of this, we want to grab the list of rabbits from the island class. let's go over there and convert from animals to rabbits for now... eh?
	// alternatively, you could set Animal as a class that extends circle instead of um. an interface. give that a go? i think everwhere it's used in the Island class should still work with that change?. nope
	
	// idea!! we could maintain the animal interface ( turns out the code breaks if you treat it as a superclass instead), and just have all the implementing classes extend the circle class themselves. oh crap then they don't implement animal lol
	
	// no way .... i think it might work?+!?!?!?! seems to need to be in this order
	// public class Rabbit extends Circle implements Animal
	
	
	// create multiple circles and populate velocity arrays
//	private static void createCircles(int numCircles) {
//		for (int i = 0; i < numCircles; i++) {
//			Circle circle = new Circle((i+1)*70, (i+1)*50, radius);
//			circles.add(circle);
//			dxArr.add(dx);
//			dyArr.add(dy);
//		}
//	}

	// ----------------------------------------------------------------------------------------- //
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		// nope nope nope this is the starting point.... the main method here will need to instantiate the island, create the rabbits, populate with grass, and then launch the UI
		// eg. 
		// create island
		Island island = new Island(30, 10);
		
		// generate a handful of rabbits
		island.genAnimals(10);
		
		// add grass and water 
		island.genGrass(20);
		island.genWater(20);
		
		// run island animations with 10 updates and report number of living rabbits at end.
		island.animateIsland(10);
		// animateIsland will now have to start the timeline? not sure, we'll get to that.
		island.reportNumAnimals();

		// launch UI
//		launch(args);
	}
}
