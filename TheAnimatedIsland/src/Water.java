import javafx.scene.shape.Circle;

public class Water extends Circle implements GeographicalFeature {
	// instance attributes
	private char symbol = '~'; 
	private int x; 
	private int y;
	private Island island; // island water belongs to, if any

	// constructor
	public Water(int x, int y) {
		this.x = x;
		this.y = y;
		// set island to null by default
		this.island = null;
	}
	
	// getters and setters
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
