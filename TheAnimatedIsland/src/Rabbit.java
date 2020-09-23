import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Rabbit extends Circle implements Animal {

	// i just want the rabbit to be able to move. let's start by making our userinterface display circles associated to rabbits. steps to that:
	// - instead of circles, we could just have an image represent each animal instead of a symbol, and print that shit..... but whatever, let's start with circles I guess so we can use gettranslate and all that rubbish?

	// instance attributes
	private char symbol = 'R';
	private int x; // horizontal position on grid
	private int y; // vertical position on grid
	private int gridSize; // pixel size of grid squares
	private int energy;
	private int hydration;
	private boolean isDead;
	private Island island; // island the rabbit belongs to, if any.

	// constructor function taking position and energy level as arguments. does not initiate with an island.
	public Rabbit(int x, int y, int energy, int gridSize) {
		// rabbits inherit from the Circle class, and so also need to be instantiated with the attributes a Circle requires, i.e. translateX, translateY, and radius
		// the x, y position should be the gridSize * x + gridSize / 2, gridSize * y. radius is gridSize / 2
//		super(gridSize * x + gridSize / 2, gridSize * y + gridSize / 2, gridSize / 2);
		super(gridSize * x + gridSize, gridSize * y + gridSize, gridSize / 2);
		this.setFill(Color.DARKGRAY); // rabbits are grey

		this.x = x;
		this.y = y;
		this.gridSize = gridSize;
		this.energy = energy;
		this.hydration = 50;
		this.isDead = false;
		this.island = null;
	}

	// change the rabbit's position, distance cells at a time, either North, East, South or West
	// takes double between 0 and 1 as direction and determines movement using quadrants
	// returns true if movement occurs, false if position to move to is occupied and thus no movement occurs
	public boolean move(double direction, int distance) {

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
				
				return false;
			}
		}

		// if position changed, set new position
		if (newX != initX || newY != initY) {
			this.x = newX;
			this.y = newY;
			return true;
		} else {
			return false;
		}
	}
	
	// overloaded method. if no distance provided, apply default or pick randomly between options.
	public boolean move(double direction) {
		// rabbits can move one or two cells at a time. pick randomly.
		int distance = (int)(Math.random()*2 + 1);
		return move(direction, distance);
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
	
	public boolean seekWater() {
		// flag whether movement has occured
		boolean movement = false;
		
		// rabbits can detect water up up to two cells away (square of side length 5 cells)
		if (island.hasWater(x, y) != false) {
			// rabbit is currently at a patch of water. feed self and do not move.
			drinkWater();
			
			// if grass is detected, move towards that patch of water
			// try to move east first if there is water towards the east
		} else if (island.hasWater(x + 1, y) != false || island.hasWater(x + 1, y + 1) != false || island.hasWater(x + 1, y - 1) != false || island.hasWater(x + 1, y + 2) != false || island.hasWater(x + 1, y - 2) != false) {
			movement = move(0.3, 1);
		} else if (island.hasWater(x + 2, y) != false || island.hasWater(x + 2, y + 1) != false || island.hasWater(x + 2, y - 1) != false || island.hasWater(x + 2, y + 2) != false || island.hasWater(x + 2, y - 2) != false) {
			movement = move(0.3, 2);
			
			// try to move west first if there is water towards the west
		} else if (island.hasWater(x - 1, y) != false || island.hasWater(x - 1, y + 1) != false || island.hasWater(x - 1, y - 1) != false || island.hasWater(x - 1, y + 2) != false || island.hasWater(x - 1, y - 2) != false) {
			movement = move(0.8, 1);
		} else if (island.hasWater(x - 2, y) != false || island.hasWater(x - 2, y + 1) != false || island.hasWater(x - 2, y - 1) != false || island.hasWater(x - 2, y + 2) != false || island.hasWater(x - 2, y - 2) != false) {
			movement = move(0.8, 2);
			
			// if there is water directly north, head north
		} else if (island.hasWater(x, y - 1) != false) {
			movement = move(0.1, 1);
		} else if (island.hasWater(x, y - 2) != false) {
			movement = move(0.1, 2);
			
			// if there is water directly south, head south
		} else if (island.hasWater(x, y + 1) != false) {
			movement = move (0.6, 1);
		} else if (island.hasWater(x, y + 2) != false) {
			movement = move (0.6, 2);
			
		} else {
			// if no nearby grass is detected, move randomly in search of water.
			movement = move(Math.random(), (int)(Math.random()*2 + 1));
		}
		// if no movement has occurred, keep attempting to move randomly
		while (!movement) {
			movement = move(Math.random(), (int)(Math.random()*2 + 1));
		}
		
		return movement;
	}
	
	
	public boolean seekFood() {
		// flag whether movement has occured
		boolean movement = false;
		
		// rabbits can detect grass up up to two cells away (square of side length 5 cells)
		if (island.hasGrass(x, y) != null) {
			// rabbit is currently at a patch of grass. feed self and do not move.
			feedSelf();
			
		// if grass is detected, move towards that patch of grass
		// try to move east first if there is food towards the east
		} else if (island.hasGrass(x + 1, y) != null || island.hasGrass(x + 1, y + 1) != null || island.hasGrass(x + 1, y - 1) != null || island.hasGrass(x + 1, y + 2) != null || island.hasGrass(x + 1, y - 2) != null) {
			movement = move(0.3, 1);
		} else if (island.hasGrass(x + 2, y) != null || island.hasGrass(x + 2, y + 1) != null || island.hasGrass(x + 2, y - 1) != null || island.hasGrass(x + 2, y + 2) != null || island.hasGrass(x + 2, y - 2) != null) {
			movement = move(0.3, 2);
			
		// try to move west first if there is food towards the west
		} else if (island.hasGrass(x - 1, y) != null || island.hasGrass(x - 1, y + 1) != null || island.hasGrass(x - 1, y - 1) != null || island.hasGrass(x - 1, y + 2) != null || island.hasGrass(x - 1, y - 2) != null) {
			movement = move(0.8, 1);
		} else if (island.hasGrass(x - 2, y) != null || island.hasGrass(x - 2, y + 1) != null || island.hasGrass(x - 2, y - 1) != null || island.hasGrass(x - 2, y + 2) != null || island.hasGrass(x - 2, y - 2) != null) {
			movement = move(0.8, 2);
			
		// if there is food directly north, head north
		} else if (island.hasGrass(x, y - 1) != null) {
			movement = move(0.1, 1);
		} else if (island.hasGrass(x, y - 2) != null) {
			movement = move(0.1, 2);
			
		// if there is food directly south, head south
		} else if (island.hasGrass(x, y + 1) != null) {
			movement = move (0.6, 1);
		} else if (island.hasGrass(x, y + 2) != null) {
			movement = move (0.6, 2);
			
		} else {
			// if no nearby grass is detected, move randomly in search of food.
			movement = move(Math.random(), (int)(Math.random()*2 + 1));
		}
		// if no movement has occurred, keep attempting to move randomly
		while (!movement) {
			movement = move(Math.random(), (int)(Math.random()*2 + 1));
		}
		
		return movement;
	}

	// Getters and setters

	// return position of center of circle for purpose of displaying in GUI
	// getters that multiply x, y coordinates by the gridSize and add half of it! :)
	public int getPosCenterX() {
//		return x * gridSize + gridSize / 2; 
		return x * gridSize + gridSize; 
	}
	public int getPosCenterY() {
//		return y * gridSize + gridSize / 2; 
		return y * gridSize + gridSize; 
	}
	
	// positions within grid
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

	public char getSymbol() {
		return symbol;
	}

	public boolean isDead() {
		return isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
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

	public Island getIsland() {
		return island;
	}

	public void setIsland(Island island) {
		this.island = island;
	}

	@Override
	public String toString() {
		return "Rabbit [x=" + x + ", y=" + y + ", energy=" + energy + ", hydration=" + hydration + ", isDead=" + isDead
				+ "]";
	}
	

}
