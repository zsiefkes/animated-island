import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Grass extends ImageView implements GeographicalFeature {

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
		super(new Image("grass.png", gridSize, gridSize, false, false));
//		super(gridSize * x + gridSize / 2, gridSize * y + gridSize / 2, gridSize / 2);
//		super(gridSize * x + gridSize, gridSize * y + gridSize, gridSize / 2);
//		this.setFill(Color.DARKOLIVEGREEN); // grasses are green
		this.size = size;
		this.x = x;
		this.y = y;
		this.isDead = false;
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
	
	public int getSize() {
		return size;
	}

	// increase and decrease size of plant by optional increment argument.
	public void increaseSize(int increment) {
		this.size = this.size + increment;
	}
	public void decreaseSize(int increment) {
		this.size = this.size - increment;
	}
	// overloaded methods taking no argument - increase and decrease size of plant by 1
	public void increaseSize() {
		this.size++;
	}
	public void decreaseSize() {
		this.size--;
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
