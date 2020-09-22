import javafx.scene.shape.Circle;

// want this class to have everything the rabbit class did in TheIsland but also be able to use circle attributes like getCenter, getTranslateX, translateX etc.
public class Rabbit extends Circle implements Animal {

	// i just want the rabbit to be able to move. let's start by making our userinterface display circles associated to rabbits. steps to that:
	// - rabbits already are circles, great
	// - instead of circles, we could just have an image represent each animal instead of a symbol, and print that shit..... but whatever, let's start with circles I guess so we can use gettranslate and all that rubbish?
	// I don't fit in with these normies, but it's fine... my firends love love love me <3 ^^
	// the assignment criteria specified that we start with circles, so. let's do that.

	// instance attributes
	private char symbol = 'R';
	private int x; // horizontal position
	private int y; // vertical position
	private int energy;
	private int hydration;
	private Island island; // island the rabbit belongs to, if any.

	// overloaded constructor, takes no arguments and sets pre-defined attributes
	public Rabbit() {		
		this.x = 0;
		this.y = 0;
		this.energy = 100;
		this.hydration = 50;
		this.island = null;
	}

	// overloaded constructor function taking position and energy level as arguments. does not initiate with an island.
	public Rabbit(int x, int y, int energy) {
		this.x = x;
		this.y = y;
		this.energy = energy;
		this.hydration = 50;
		this.island = null;
	}

	// change the rabbit's position, distance cells at a time, either North, East, South or West
	// takes double between 0 and 1 as direction and determines movement using quadrants
	public void move(double direction, int distance) {

		// store initial position
		int initX = this.x;
		int initY = this.y;

		// declare variables to store new position. initialize new coordinates as
		// initial coordinates.
		int newX = initX;
		int newY = initY;
		
		// argument direction should be between 0 and 1. if not, no movement happens
		if (direction < 0.25) {
			// move north
			newY = initY - distance;

		} else if (direction < 0.5) {
			// move east
			newX = initX + distance;

		} else if (direction < 0.75) {
			// move south
			newY = initY + distance;

		} else if (direction < 1) {
			// move west
			newX = initX - distance;
		}

		// if the rabbit belongs to a island,
		if (this.island != null) {

			// first check it did not move out of the island's borders. obtain island width and height
			int width = this.island.getWidth();
			int height = this.island.getHeight();

			// send it to the opposite side if it does! this island is a torus!
			// note that the coordinates begin at 0 and end at width - 1 and height - 1

			// check x coordinate
			if (newX < 0) {
				newX = width - 1;
			} else if (newX > width - 1) {
				newX = 0;
			}

			// check y coordinate
			if (newY < 0) {
				newY = height - 1;
			} else if (newY > height - 1) {
				newY = 0;
			}

			// next, check it did not move to a spot already occupied by another animal.
			if (island.isOccupied(newX, newY)) {

				// if it is, move it back to its original position
				newX = initX;
				newY = initY;
			}
		}

		// set new position
		this.x = newX;
		this.y = newY;
	}
	
	// overloaded method. if no distance provided, apply default or pick randomly between options.
	public void move(double direction) {
		// rabbits can move one or two cells at a time. pick randomly.
		int distance = (int)(Math.random()*2 + 1);
		move(direction, distance);
	}
	
	// food related methods
	public boolean isHungry() {
		// return whether energy level is below a certain threshold
		return (energy < 10);
	}
	
	public boolean isThirsty() {
		return (hydration < 6);
	}

	public boolean drinkWater() {
		// check if rabbit is at water source and increase hydration if so
		if (island.hasWater(x, y)) {
			increaseHydration(20);
			return true;
		} else {
			return false;
		}
	}
	
	// check if the rabbit is on a patch of grass, and feed on the grass if so. return whether feeding took place
	public boolean feedSelf() {
		Grass grass = island.hasGrass(x, y);
		if (grass != null) {
			grass.decreaseSize(4);
			increaseEnergy(10);
			return true;
		} else {
			return false;
		}
	}
	
	public void seekFood() {
		// rabbits can detect grass up up to two cells away (square of side length 5 cells)
		if (island.hasGrass(x, y) != null) {
			// rabbit is currently at a patch of grass. feed self and do not move.
			feedSelf();
			
		// if grass is detected, move towards that patch of grass
		// try to move east first if there is food towards the east
		} else if (island.hasGrass(x + 1, y) != null || island.hasGrass(x + 1, y + 1) != null || island.hasGrass(x + 1, y - 1) != null || island.hasGrass(x + 1, y + 2) != null || island.hasGrass(x + 1, y - 2) != null) {
			move(0.3, 1);
		} else if (island.hasGrass(x + 2, y) != null || island.hasGrass(x + 2, y + 1) != null || island.hasGrass(x + 2, y - 1) != null || island.hasGrass(x + 2, y + 2) != null || island.hasGrass(x + 2, y - 2) != null) {
			move(0.3, 2);
			
		// try to move west first if there is food towards the west
		} else if (island.hasGrass(x - 1, y) != null || island.hasGrass(x - 1, y + 1) != null || island.hasGrass(x - 1, y - 1) != null || island.hasGrass(x - 1, y + 2) != null || island.hasGrass(x - 1, y - 2) != null) {
			move(0.8, 1);
		} else if (island.hasGrass(x - 2, y) != null || island.hasGrass(x - 2, y + 1) != null || island.hasGrass(x - 2, y - 1) != null || island.hasGrass(x - 2, y + 2) != null || island.hasGrass(x - 2, y - 2) != null) {
			move(0.8, 2);
			
		// if there is food directly north, head north
		} else if (island.hasGrass(x, y - 1) != null) {
			move(0.1, 1);
		} else if (island.hasGrass(x, y - 2) != null) {
			move(0.1, 2);
			
		// if there is food directly south, head south
		} else if (island.hasGrass(x, y + 1) != null) {
			move (0.6, 1);
		} else if (island.hasGrass(x, y + 2) != null) {
			move (0.6, 2);
			
		} else {
			// if no nearby grass is detected, move randomly in search of food.
			move(Math.random(), 2);
		}
	}

	// Getters and setters
	public char getSymbol() {
		return symbol;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getEnergy() {
		return energy;
	}

	public int getHydration() {
		return hydration;
	}

	public void increaseEnergy(int energy) {
		this.energy = this.energy + energy;
	}
	public void decreaseEnergy(int energy) {
		this.energy = this.energy - energy;
	}
	// overloaded methods. if no argument provided, adjust energy by 1. 
	public void increaseEnergy() {
		this.energy++;
	}
	public void decreaseEnergy() {
		this.energy--;
	}
	
	public void increaseHydration(int hydration) {
		this.hydration = this.hydration + hydration;
	}
	public void decreaseHydration(int hydration) {
		this.hydration = this.hydration - hydration;
	}
	// overloaded methods. if no argument provided, adjust hydration by 1. 
	public void increaseHydration() {
		this.hydration++;
	}
	public void decreaseHydration() {
		this.hydration--;
	}

	// nodes have their own id built in. i don't even use these ids anyway so. remove.
//	public int getId() {
//		return id;
//	}
//
	public Island getIsland() {
		return island;
	}

	public void setIsland(Island island) {
		this.island = island;
	}

}
