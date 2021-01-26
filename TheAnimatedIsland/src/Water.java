import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Water extends ImageView implements GeographicalFeature {
	private char symbol = '~'; 
	private int x; // horizontal position on grid
	private int y; // vertical position on grid
	private int gridSize; // pixel size of grid squares
	private Island island; // island water belongs to, if any

	// constructor
	public Water(int x, int y, int gridSize) {
		super(new Image("water.png", gridSize, gridSize, false, false));
		this.x = x;
		this.y = y;
		this.gridSize = gridSize;
		// set island to null by default
		this.island = null;
	}
	
	// getters and setters
	
	// returns coordinates of top-left point of square image for displaying on grid
	public int getPosX() {
		return x * gridSize; 
	}
	public int getPosY() {
		return y * gridSize; 
	}

	public char getSymbol() {
		return symbol;
	}

	public int getGridX() {
		return x;
	}

	public int getGridY() {
		return y;
	}

	public Island getIsland() {
		return island;
	}

	public void setIsland(Island island) {
		this.island = island;
	}

}
