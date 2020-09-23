import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Water extends Circle implements GeographicalFeature {
	// instance attributes
	private char symbol = '~'; 
	private int x; // horizontal position on grid
	private int y; // vertical position on grid
	private int gridSize; // pixel size of grid squares
	private Island island; // island water belongs to, if any

	// constructor
	public Water(int x, int y, int gridSize) {
//		super(gridSize * x + gridSize / 2, gridSize * y + gridSize / 2, gridSize / 2);
		super(gridSize * x + gridSize, gridSize * y + gridSize, gridSize / 2);
		this.setFill(Color.LIGHTSKYBLUE); // water is blue
		this.x = x;
		this.y = y;
		this.gridSize = gridSize;
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
