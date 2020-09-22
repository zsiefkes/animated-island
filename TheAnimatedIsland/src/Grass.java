import javafx.scene.shape.Circle;

public class Grass extends Circle implements GeographicalFeature {

	// instance attributes
	private int size;
	private char symbol = '#'; 
	private int x; 
	private int y;
	private Island island; // island grass belongs to, if any

	// constructor
	public Grass(int size,  int x, int y) {
		this.size = size;
		this.x = x;
		this.y = y;
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


	public Island getIsland() {
		return island;
	}

	public void setIsland(Island island) {
		this.island = island;
	}

}
