import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Kiwi extends ImageView implements Animal {
	// instance attributes
	private char symbol = 'K';
	private int x; // horizontal position
	private int y; // vertical position
	private int gridSize; // pixel size of grid squares
	private int energy;
	private int hydration;
	private boolean isDead;
	private Island island; // island the kiwi belongs to, if any.

	// constructor function taking position energy level and gridSize as arguments. does not initiate with an island.
	public Kiwi(int x, int y, int energy, int gridSize) {
		super(new Image("kiwi.png", gridSize, gridSize, false, false));
		this.x = x;
		this.y = y;
		this.energy = energy;
		this.gridSize = gridSize;
		this.hydration = 50;
		this.island = null;
	}

	// change the kiwi's position, 1 coordinate at a time, either North, East, South or West
	// takes double between 0 and 1 as direction and determines movement using quadrants
	public boolean move(double direction) {

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
			newY = initY - 1;

		} else if (direction < 0.5) {
			// move east
			newX = initX + 1;

		} else if (direction < 0.75) {
			// move south
			newY = initY + 1;

		} else if (direction < 1) {
			// move west
			newX = initX - 1;
		}

		// if the kiwi belongs to a island,
		if (this.island != null) {

			// first check it did not move out of the island's borders. obtain island width
			// and height
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

	// food related methods
	public boolean isHungry() {
		// return whether energy level is below a certain threshold
		return (energy < 20);
	}
	
	public boolean isThirsty() {
		// return whether hydration level is below a certain threshold
		return (hydration < 40);
	}
 
	public boolean drinkWater() {
		// check if kiwi is at water source and increase hydration if so. return whether a drink was taken. 
		if (island.hasWater(x, y)) {
			increaseHydration(40);
			return true;
		} else {
			return false;
		}
	}
	
	// kiwis can feed anywhere on grubs under the ground :) 
	public boolean feedSelf() {
		increaseEnergy(20);
		return true;
	}
	
	public boolean seekFood() {
		// kiwis can feed anywhere. 
		feedSelf();
		return true;
	}
	

	public boolean seekWater() {
		// flag whether movement has occurred
		boolean movement = false;
		
		// kiwis can detect water up to two grid squares away (square of side length 5 cells)
		if (island.hasWater(x, y) != false) {
			// kiwi is currently at a patch of water. feed self and do not move.
			drinkWater();
			
			// if water is detected, move towards that patch of water
		} else if (island.hasWater(x + 1, y) || island.hasWater(x + 1, y + 1) || island.hasWater(x + 1, y - 1) || island.hasWater(x + 1, y + 2) || island.hasWater(x + 1, y - 2) || island.hasWater(x + 2, y) || island.hasWater(x + 2, y + 1) || island.hasWater(x + 2, y - 1) || island.hasWater(x + 2, y + 2) || island.hasWater(x + 2, y - 2)) {
			// try to move east first if there is water towards the east
			movement = move(0.3);
			
		} else if (island.hasWater(x - 1, y) || island.hasWater(x - 1, y + 1) || island.hasWater(x - 1, y - 1) || island.hasWater(x - 1, y + 2) || island.hasWater(x - 1, y - 2) || island.hasWater(x - 2, y) || island.hasWater(x - 2, y + 1) || island.hasWater(x - 2, y - 1)  || island.hasWater(x - 2, y + 2) || island.hasWater(x - 2, y - 2)) {
			// try to move west first if there is water towards the west
			movement = move(0.8);
			
		} else if (island.hasWater(x, y - 1) || island.hasWater(x, y - 2)) {
			// if there is water directly north, head north
			movement = move(0.1);
			
		} else if (island.hasWater(x, y + 1) || island.hasWater(x, y + 2)) {
			// if there is water directly south, head south
			movement = move (0.6);
			
		} else {
			// if no nearby water was detected, move randomly in search of food.
			movement = move(Math.random());
		}
		
		// if no movement has occurred, keep attempting to move randomly up to 10 tries
		int numTries = 0;
		while (!movement && numTries < 10) {
			movement = move(Math.random());
			numTries++;
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

	public char getSymbol() {
		return symbol;
	}

	public int getEnergy() {
		return energy;
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

	public int getHydration() {
		return hydration;
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

	public boolean isDead() {
		return isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public Island getIsland() {
		return island;
	}

	public void setIsland(Island island) {
		this.island = island;
	}

	@Override
	public String toString() {
		return "Kiwi [x=" + x + ", y=" + y + ", energy=" + energy + ", hydration=" + hydration + ", isDead=" + isDead
				+ "]";
	}

}
