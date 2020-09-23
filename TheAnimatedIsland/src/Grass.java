import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Grass extends Circle implements GeographicalFeature {

	// instance attributes
	private int size;
	private char symbol = '#'; 
	private int x; // horizontal position on grid
	private int y; // vertical position on grid
	private int gridSize; // pixel size of grid squares
	private boolean isDead;
	private Island island; // island grass belongs to, if any

	// constructor
	public Grass(int size,  int x, int y, int gridSize) {
//		super(gridSize * x + gridSize / 2, gridSize * y + gridSize / 2, gridSize / 2);
		super(gridSize * x + gridSize, gridSize * y + gridSize, gridSize / 2);
		this.setFill(Color.DARKOLIVEGREEN); // grasses are green
		this.size = size;
		this.x = x;
		this.y = y;
		this.isDead = false;
		this.gridSize = gridSize;
		// set island to null by default
		this.island = null;
	}
	
	// getters and setters
	public int getSize() {
		return size;
	}

	// increase and decrease size. overloaded methods - optional increment argument.
	public void increaseSize() {
		this.size++;
	}
	public void decreaseSize() {
		this.size--;
	}
	public void increaseSize(int increment) {
		this.size = this.size + increment;
	}
	public void decreaseSize(int increment) {
		this.size = this.size - increment;
	}

	public char getSymbol() {
		return symbol;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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

}
