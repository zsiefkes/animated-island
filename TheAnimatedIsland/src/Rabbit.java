import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Rabbit extends ImageView implements Animal {

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
		super(new Image("rabbit.png", gridSize, gridSize, false, false));
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
		return (energy < 35);
	}
	
	public boolean isThirsty() {
		return (hydration < 35);
	}

	public boolean drinkWater() {
		// check if rabbit is at water source and increase hydration if so
		if (island.hasWater(x, y)) {
			increaseHydration(50);
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
			increaseEnergy(50);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean seekWater() {
		// flag whether movement has occurred
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
			// if no nearby water is detected, move randomly in search of water.
			movement = move(Math.random(), (int)(Math.random()*2 + 1));
		}
		// if no movement has occurred, keep attempting to move randomly up to 10 tries
		int numTries = 0;
		while (!movement && numTries < 10) {
			movement = move(Math.random(), (int)(Math.random()*2 + 1));
			numTries++;
		}
		
		return movement;
	}
	
	
	// detect grass up to two cells away (square of side length 5 cells) and move towards the grass if it is detected. otherwise, move randomly 
	public boolean seekFood() {
		
		// flag whether movement has occurred
		boolean movement = false;
		
		// first check if rabbit is currently on a patch of grass
		if (island.hasGrass(x, y) != null) {
			
			// rabbit is currently at a patch of grass. feed self and do not move.
			feedSelf();
			

		} else if (island.hasGrass(x + 1, y) != null || island.hasGrass(x + 1, y + 1) != null || island.hasGrass(x + 1, y - 1) != null || island.hasGrass(x + 1, y + 2) != null || island.hasGrass(x + 1, y - 2) != null) {
			// try to move east first if there is food towards the east
			movement = move(0.3, 1);
		} else if (island.hasGrass(x + 2, y) != null || island.hasGrass(x + 2, y + 1) != null || island.hasGrass(x + 2, y - 1) != null || island.hasGrass(x + 2, y + 2) != null || island.hasGrass(x + 2, y - 2) != null) {
			movement = move(0.3, 2);
			
		
		} else if (island.hasGrass(x - 1, y) != null || island.hasGrass(x - 1, y + 1) != null || island.hasGrass(x - 1, y - 1) != null || island.hasGrass(x - 1, y + 2) != null || island.hasGrass(x - 1, y - 2) != null) {
			// try to move west first if there is food towards the west
			movement = move(0.8, 1);
		} else if (island.hasGrass(x - 2, y) != null || island.hasGrass(x - 2, y + 1) != null || island.hasGrass(x - 2, y - 1) != null || island.hasGrass(x - 2, y + 2) != null || island.hasGrass(x - 2, y - 2) != null) {
			movement = move(0.8, 2);
			
		} else if (island.hasGrass(x, y - 1) != null) {
			// if there is food directly north, head north
			movement = move(0.1, 1);
		} else if (island.hasGrass(x, y - 2) != null) {
			movement = move(0.1, 2);
			
		} else if (island.hasGrass(x, y + 1) != null) {
			// if there is food directly south, head south
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

	// returns coordinates of top-left point of square image for displaying on grid
	public int getPosX() {
		return x * gridSize; 
	}
	public int getPosY() {
		return y * gridSize; 
	}
	
	// positions within grid coordinates
	public int getGridX() {
		return x;
	}
	public int getGridY() {
		return y;
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
