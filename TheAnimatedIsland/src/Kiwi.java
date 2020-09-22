import javafx.scene.shape.Circle;

public class Kiwi extends Circle implements Animal {
	// instance attributes
	private char symbol = 'K';
	private int x; // horizontal position
	private int y; // vertical position
	private int energy;
	private int hydration;
	private Island island; // island the kiwi belongs to, if any.

	// overloaded constructor, takes no arguments and sets pre-defined attributes
	public Kiwi() {
		this.x = 0;
		this.y = 0;
		this.energy = 100;
		this.hydration = 50;
		this.island = null;
	}

	// overloaded constructor function taking position and energy level as
	// arguments. does not initiate with an island.
	public Kiwi(int x, int y, int energy) {
		this.x = x;
		this.y = y;
		this.energy = energy;
		this.hydration = 50;
		this.island = null;
	}

	// change the kiwi's position, 1 coordinate at a time, either North, East, South or West
	// takes double between 0 and 1 as direction and determines movement using quadrants
	public void move(double direction) {

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
			}
		}

		// set new position
		this.x = newX;
		this.y = newY;
	}

	// food related methods
	public boolean isHungry() {
		// return whether energy level is below a certain threshold
		return (energy < 6);
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
	
	// kiwis can feed anywhere on grubs under the ground :) 
	public boolean feedSelf() {
		increaseEnergy(2);
		return true;
	}
	
	public void seekFood() {
		// kiwis can feed anywhere. 
		feedSelf();
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

	public Island getIsland() {
		return island;
	}

	public void setIsland(Island island) {
		this.island = island;
	}

}
